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
> POST /drone HTTP/1.1 <br/>
> Host: localhost:8080 <br/>
> Content-Type: application/json <br/>
> 
```json
{
    "serialNumber": "B2025",
    "model": "LIGHTWEIGHT",
    "state": "IDLE",
    "weightLimit": 500,
    "batteryCapacity": 100
}
```

```json
{
  "id": 3,
  "serialNumber": "B2025",
  "model": "LIGHTWEIGHT",
  "weightLimit": 500,
  "batteryCapacity": 100,
  "state": "IDLE",
  "createdAt": "2022-02-03T22:51:12.525587",
  "updatedAt": "2022-02-03T22:51:12.525587"
}
```
#### Load Medication
> PUT /drone/medication/3 HTTP/1.1 <br/>
> Host: localhost:8080 <br/>
> Content-Type: application/json <br/>
>
```json
{
    "name": "PARACETAMOL",
    "weight": 150,
    "code": "PARA304",
    "image": "http://image.io"
}
```

```json
{
    "id": 16,
    "name": "PARACETAMOL",
    "weight": 150,
    "code": "PARA304",
    "image": "http://image.io",
    "createdAt": "2022-02-03T22:51:43.957103",
    "updatedAt": "2022-02-03T22:51:43.957103"
}
```
#### Get Drone Medication
> GET /drone/medication/3 HTTP/1.1 <br/>
> Host: localhost:8080 <br/>
> Content-Type: application/json <br/>
>

```json
[
    {
        "id": 16,
        "name": "PARACETAMOL",
        "weight": 150,
        "code": "PARA304",
        "image": "http://image.io",
        "createdAt": "2022-02-03T22:51:43.957103",
        "updatedAt": "2022-02-03T22:51:43.957103"
    }
]
```
#### Drone Available to load
> GET /drone/available HTTP/1.1 <br/>
> Host: localhost:8080 <br/>
> Content-Type: application/json <br/>
>

```json
[
    {
        "id": 1,
        "serialNumber": "B2023",
        "model": "LIGHTWEIGHT",
        "weightLimit": 400,
        "batteryCapacity": 90,
        "state": "LOADING",
        "createdAt": "2022-02-03T21:54:42.556699",
        "updatedAt": "2022-02-03T21:54:42.556699"
    },
    {
        "id": 3,
        "serialNumber": "B2025",
        "model": "LIGHTWEIGHT",
        "weightLimit": 500,
        "batteryCapacity": 100,
        "state": "LOADING",
        "createdAt": "2022-02-03T22:51:12.525587",
        "updatedAt": "2022-02-03T22:51:12.525587"
    }
]
```
#### Get Drone Battery Capacity
> GET /drone/3/battery-capacity HTTP/1.1 <br/>
> Host: localhost:8080 <br/>
> Content-Type: application/json <br/>
>

```json
{
    "id": 3,
    "serialNumber": "B2025",
    "batteryCapacity": 100
}
```