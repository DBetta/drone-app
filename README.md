## Drones
### Tools used
1. Java 17
2. Postgres
3. Docker
4. Liquibase
5. Intellij Idea
6. Postman
7. Spring boot - reactive

### How to build and run
#### Starting docker postgres
- At the root of the project run: 
```shell
docker compose up -f docker/docker-compose.yml up -d
```
#### Run the application via:
```shell
./gradlew -pdrone clean booRun
```

