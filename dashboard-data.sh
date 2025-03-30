#!/bin/bash

BASE_URL="http://localhost:8080"

# Function to create locations
echo "Populating Locations..."
for i in {1..20}; do
  curl -s -X POST "$BASE_URL/locations/register" -H "Content-Type: application/json" -d "{\"name\": \"Location $i\"}"
done
echo "Created 20 Locations."

# Function to create drivers
echo "Populating Drivers..."
for i in {1..20}; do
  curl -s -X POST "$BASE_URL/drivers/register" -H "Content-Type: application/json" -d "{\"name\": \"Driver $i\"}"
done
echo "Created 20 Drivers."

# Function to create taxis
echo "Populating Taxis..."
for i in {1..20}; do
  curl -s -X POST "$BASE_URL/taxis/register" -H "Content-Type: application/json" -d "{\"licensePlate\": \"ABC$i\", \"status\": \"AVAILABLE\"}"
done
echo "Created 20 Taxis."

# Function to generate random date within a range
generate_random_date() {
  start_date="2025-03-01"
  end_date="2025-03-10"
  
  start_seconds=$(date -j -f "%Y-%m-%d" "$start_date" +%s)
  end_seconds=$(date -j -f "%Y-%m-%d" "$end_date" +%s)
  
  random_seconds=$((RANDOM % (end_seconds - start_seconds + 1) + start_seconds))
  
  random_date=$(date -r "$random_seconds" "+%Y-%m-%dT%H:%M:%S")
  echo $random_date
}

# Function to generate completion time (must be later than the booking time)
generate_completion_time() {
  booking_time=$1
  # Add 30 minutes to the booking time to make completion time later
  completion_time=$(date -j -v +30M -f "%Y-%m-%dT%H:%M:%S" "$booking_time" "+%Y-%m-%dT%H:%M:%S")
  echo $completion_time
}

echo "Populating Bookings..."
booking_ids=()
booking_times=()  # Array to store booking times
for i in {1..50}; do
  source=$((RANDOM % 20 + 1))
  destination=$((RANDOM % 20 + 1))
  while [ "$source" -eq "$destination" ]; do
    destination=$((RANDOM % 20 + 1))
  done
  
  booking_time=$(generate_random_date)  # Store generated date
  category="PREMIUM"
  
  # Add booking time to the array
  booking_times+=("$booking_time")
  
  booking_id=$(curl -s -X POST "$BASE_URL/bookings/initiate" -H "Content-Type: application/json" -d "{\"source\": {\"id\": $source}, \"destination\": {\"id\": $destination}, \"category\": \"$category\", \"bookingTime\": \"$booking_time\"}")
  echo "Created booking: $booking_id with time: $booking_time"
  booking_ids+=($booking_id)
done

# Move 20 bookings to ACCEPTED
for i in {0..19}; do
  available_taxi=$(curl -s -X GET "$BASE_URL/taxis" | jq -r '.[] | select(.status == "AVAILABLE") | .id' | head -n 1)
  if [ -n "$available_taxi" ]; then
    curl -s -X PATCH "$BASE_URL/bookings/accept" -H "Content-Type: application/json" -d "{\"id\": ${booking_ids[$i]}, \"taxi\": {\"id\": $available_taxi}}"
    echo "Booking ${booking_ids[$i]} ACCEPTED with Taxi $available_taxi"
  fi
done

# Move 20 bookings to PICKEDUP
for i in {0..19}; do
  curl -s -X PATCH "$BASE_URL/bookings/pickup" -H "Content-Type: application/json" -d "{\"id\": ${booking_ids[$i]}}"
  echo "Booking ${booking_ids[$i]} PICKEDUP"
done

# Move 10 bookings to COMPLETED
for i in {0..9}; do
  completion_time=$(generate_completion_time "${booking_times[$i]}")  # Pass the correct booking time from the array
  curl -s -X PATCH "$BASE_URL/bookings/complete" -H "Content-Type: application/json" -d "{\"id\": ${booking_ids[$i]}, \"completionTime\": \"$completion_time\"}"
  echo "Booking ${booking_ids[$i]} COMPLETED with completion time: $completion_time"
done

# Move 10 more INITIATED bookings to ACCEPTED
for i in {20..29}; do
  available_taxi=$(curl -s -X GET "$BASE_URL/taxis" | jq -r '.[] | select(.status == "AVAILABLE") | .id' | head -n 1)
  if [ -n "$available_taxi" ]; then
    curl -s -X PATCH "$BASE_URL/bookings/accept" -H "Content-Type: application/json" -d "{\"id\": ${booking_ids[$i]}, \"taxi\": {\"id\": $available_taxi}}"
    echo "Booking ${booking_ids[$i]} ACCEPTED with Taxi $available_taxi"
  fi
done

echo "Data population complete!"
