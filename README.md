# CGI suvepraktika ülesanne

Alustasin projekti ülesseadmist koos Claude 3.7 Sonnet'iga.
Sellega seadsin valmis endale frontendi ja backendi. Koos nendega seadsin üles ka Git workflow.
Ma pole varem Spring Bootist midagi kuulnud, siis ma võtsin endale ette YouTube tutoriali
link: https://www.youtube.com/watch?v=9SGDpanrc8U. Lähenen selle videoga,
et kirjutada valmis enda projekt. Flight klassi jaoks sain kasutada üksühele AmigosCode videot.
PostgresSQL võtsin endale database'ks samast videost, et lihtsustada enda tööd. Sellega sain ka 
database ühendatud Spring Boot'iga. Sarnaselt videole tahtsin ka välja arvutada 
lennuaja. Selle jaoks vaatasin manualit, ning koos Claude abiga sain loogilise koodi valmis.

## Docker Image

### Download the Docker Image
Docker Image saab tõmmata selle command'iga:

```bash
docker pull yourusername/your-image-name:tag

docker run -p 3000:80 -p 8080:8080 --name flight-app flight-booking-app"
```

Minna leheküljele http://localhost:3000/ peale Docker Image alla tõmbamist.
