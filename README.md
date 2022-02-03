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

### APi Endpoints
#### Register drone
> http://localhost:8080/drone
```json
{
    "serialNumber": "B2025",
    "model": "LIGHTWEIGHT",
    "state": "IDLE",
    "weightLimit": 500,
    "batteryCapacity": 100
}
```
#### Load Medication
> http://localhost:8080/drone/medication/3
```json
{
    "name": "PARACETAMOL",
    "weight": 150,
    "code": "PARA304",
    "image": "http://image.io"
}
```
#### Get Drone Medication
> http://localhost:8080/drone/medication/3
#### Drone Available to load
> http://localhost:8080/drone/available
#### Get Drone Battery Capacity
> htpp://localhost:8080/drone/3/battery-capacity