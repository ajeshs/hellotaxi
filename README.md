# HelloTaxi
Simple Taxi booking service

## Available APIs

### LocationController
- `POST /locations/register` → Create/Register a new location
```
Example:

curl --location 'http://localhost:8080/locations/register' \
--header 'Content-Type: application/json' \
--data '{
    "name":"location1"
}'
```

- `GET /locations` → Get all locations
```
Example:

curl --location 'http://localhost:8080/locations'
```

### DriverController
- `POST /drivers/register` → Create/Register a new driver

```
Example:

curl --location 'http://localhost:8080/drivers/register' \
--header 'Content-Type: application/json' \
--data '{
    "name":"driver1",
    "address":"address1",
    "phone":"phone1"
}'
```

- `GET /drivers` → Get all drivers
```
Example:

curl --location 'http://localhost:8080/drivers'

```

### TaxiController
- `POST /taxis/register` → Register a new taxi
```
Example:

curl --location 'http://localhost:8080/taxis/register' \
--header 'Content-Type: application/json' \
--data '{
    "licensePlate": "licensePlate1",
    "status": "AVAILABLE",
    "driver": {
        "id": 1
    },
    "location": {
        "id": 1
    }
}'

```
- `GET /taxis` → Get all taxis
```
Example:

curl --location 'http://localhost:8080/taxis'

```

- `POST /taxis/status` → Update the status of a taxi
```
Example:

curl --location --request PATCH 'http://localhost:8080/taxis/status' \
--header 'Content-Type: application/json' \
--data '{
    "id": "1",
    "status": "BOOKED" 
}'
```

### BookingController
- `GET /bookings` → Get all bookings
```
Example:

curl --location 'http://localhost:8080/bookings'
```

- `POST /bookings/initiate` → Create a new booking
```
Example:

curl --location 'http://localhost:8080/bookings/initiate' \
--header 'Content-Type: application/json' \
--data '{
    "source": {
        "id": 1
    },
    "destination": {
        "id": 2
    },
    "status": "INITIATED",
    "bookingTime": "2025-03-31T12:34:56",
    "category": "PREMIUM"
}'

```
- `PATCH /bookings/accept` → Accept a booking
```
Example:

curl --location --request PATCH 'http://localhost:8080/bookings/accept' \
--header 'Content-Type: application/json' \
--data '{
    "id": 1,
    "taxi": {
        "id":1
    }
}'

```
- `PATCH /bookings/pickup` → Mark a booking as picked up
```
Example:

curl --location --request PATCH 'http://localhost:8080/bookings/pickup' \
--header 'Content-Type: application/json' \
--data '{
    "id": 1
}'

```
- `PATCH /bookings/complete` → Mark a booking as completed
```
Example:

curl --location --request PATCH 'http://localhost:8080/bookings/complete' \
--header 'Content-Type: application/json' \
--data '{
    "id": 1
}'
```

### AnalyticsController
- `GET /analytics` → Fetch analytics data

## Setup Instructions

### Clone the Repository
```sh
git clone git@github.com:ajeshs/hellotaxi.git
cd hellotaxi
```

### Build the Project
```sh
mvn clean package
```

### Run the Application
```sh
java -jar target/hellotaxi-0.0.1-SNAPSHOT.jar
```
By default, the application runs on port 8080.

### Run the script to populate data (useful for dashboard)
In the root of the repo, there is file named, dashboard-data.sh
Go to the containing folder and run the following
```sh
chmod 777 dashboard-data.sh
./dashboard-data.sh
```
This populates the locations, drivers, taxis and the bookings with various statuses


