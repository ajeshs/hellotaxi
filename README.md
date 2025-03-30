# HelloTaxi
Simple Taxi booking service

## Summary
This is a simple taxi booking application built using SpringBoot
1. The data is stored in an in-memory DB called H2DB. Being an in-memory DB, all data will be lost on restart of the application.
2. There are locations which are identified by a number for simplicity. These can be added using the LocationController APIs. 
3. There are Drivers, which can be added using the DriverController APIs. 
4. There are Taxis, which have an associated driver, location and status (AVAILABLE/BOOKED).
5. There are Bookings, which have an associated Taxi, Status (INITIATED, ACCEPTED, PICKEDUP, COMPLETED), category(NORMAL/PREMIUM), bookingTime and completionTime.
6. The Booking status moves in the order, INITIATED -> ACCEPTED -> PICKEDUP -> COMPLETED
7. Pricing part is out of scope currently

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

### Stop the Application
Send the interrupt signal (control+c in mac)

### Run the script to populate data (useful for dashboard)
In the root of the repo, there is file named, dashboard-data.sh
Go to the containing folder and run the following
```sh
chmod 777 dashboard-data.sh

./dashboard-data.sh
```
This populates the locations, drivers, taxis and the bookings with various statuses



## Available APIs

### DB Access

1. Go to http://localhost:8080/h2-console
2. Set the JDBC URL as jdbc:h2:mem:testdb
3. Click on Connect


### AnalyticsController (for dashboard)
- `GET /analytics` → Fetch analytics data
```
Dashboard can be accessed at 
http://localhost:8080/analytics
```

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




