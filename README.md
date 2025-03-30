# HelloTaxi
Simple Taxi booking service

## Available APIs

### LocationController
- `GET /locations` → Get all locations
- `POST /locations/register` → Create/Register a new location

### DriverController
- `GET /drivers` → Get all drivers
- `POST /drivers/register` → Create/Register a new driver

### TaxiController
- `GET /taxis` → Get all taxis
- `POST /taxis/register` → Register a new taxi

### BookingController
- `GET /bookings` → Get all bookings
- `POST /bookings/initiate` → Create a new booking
- `PATCH /bookings/accept` → Accept a booking
- `PATCH /bookings/pickup` → Mark a booking as picked up
- `PATCH /bookings/complete` → Mark a booking as completed

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


