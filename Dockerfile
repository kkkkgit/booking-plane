# Start with a base image containing Java runtime for backend build
FROM openjdk:23-slim AS backend-build

# Set the working directory for the application
WORKDIR /app/backend

# Copy Maven wrapper and POM file
COPY backend/mvnw backend/pom.xml ./
COPY backend/.mvn ./.mvn

# Make Maven wrapper executable
RUN chmod +x ./mvnw

# Build all dependencies (will be cached if no changes)
RUN ./mvnw dependency:go-offline -B

# Copy the project source
COPY backend/src ./src

# Package the application
RUN ./mvnw package -DskipTests

# Node stage for building frontend
FROM node:20-alpine AS frontend-build

# Set the working directory for the frontend
WORKDIR /app/frontend

# Copy package files
COPY frontend/package.json frontend/package-lock.json ./

# Install dependencies
RUN npm install

# Copy frontend source
COPY frontend/public ./public
COPY frontend/src ./src
COPY frontend/tsconfig.json ./

# Build the frontend
RUN npm run build

# Final stage with both backend and frontend
FROM openjdk:23-slim

# Install Nginx to serve the frontend
RUN apt-get update && apt-get install -y nginx

# Set up PostgreSQL
RUN apt-get install -y postgresql postgresql-contrib

# Copy backend JAR
WORKDIR /app
COPY --from=backend-build /app/backend/target/bookingplane-0.0.1-SNAPSHOT.jar ./app.jar

# Copy built frontend to Nginx directory
COPY --from=frontend-build /app/frontend/build /var/www/html

# Create an Nginx configuration file
RUN echo 'server {\n\
    listen 80;\n\
    root /var/www/html;\n\
    index index.html;\n\
\n\
    location / {\n\
        try_files $uri $uri/ /index.html;\n\
    }\n\
\n\
    location /api/ {\n\
        proxy_pass http://localhost:8080/;\n\
        proxy_set_header Host $host;\n\
        proxy_set_header X-Real-IP $remote_addr;\n\
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;\n\
    }\n\
}' > /etc/nginx/sites-available/default

# Create a startup script to run all services
RUN echo '#!/bin/bash\n\
# Initialize PostgreSQL database\n\
service postgresql start\n\
su - postgres -c "psql -c \"CREATE DATABASE flightdb;\"\n\
psql -c \"CREATE USER kristopalmik WITH PASSWORD '\''aUp25yt5Z@qa6fJxpL'\'';\"\n\
psql -c \"ALTER USER kristopalmik WITH SUPERUSER;\"\n\
psql -c \"GRANT ALL PRIVILEGES ON DATABASE flightdb TO kristopalmik;\"\n\
psql -c \"\\\\c flightdb\";\n\
psql -c \"GRANT ALL PRIVILEGES ON SCHEMA public TO kristopalmik;\"\n\
psql -c \"ALTER SCHEMA public OWNER TO kristopalmik;\"\n\
"\n\
\n\
# Start Nginx\n\
service nginx start\n\
\n\
# Start Spring Boot application\n\
java -jar /app/app.jar\n\
' > /app/start.sh

RUN chmod +x /app/start.sh

# Expose ports
EXPOSE 80 8080

# Set the startup command
CMD ["/app/start.sh"]