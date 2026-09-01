# Sunrise Dental Clinic — Appointment & Patient Management System

CIS6003 Advanced Programming project implementing the Sunrise Dental Clinic scenario.

## Features

- secure username/password authentication;
- authorized staff sessions and logout;
- register patient appointment details;
- automatic unique appointment number;
- dentist and treatment database records;
- validation of required fields and phone number;
- prevents same dentist/date/time double booking;
- search by appointment number;
- complete appointment-details screen;
- treatment + consultation fee bill calculation;
- printable patient receipt;
- appointment status update;
- daily appointment report;
- total billed revenue report;
- staff help section;
- MySQL persistence;
- REST web services;
- 3-tier / MVC architecture;
- Repository, Service Layer and Strategy patterns;
- JUnit/Mockito automated tests;
- GitHub Actions CI workflow;
- Docker Compose localhost deployment.

## Quick start

Recommended:

```bash
docker compose up --build
```

Open:

```text
http://localhost:8080
```

Login:

```text
admin / Admin@123
```

More detailed instructions:

```text
docs/LOCALHOST_GUIDE.md
```

## Database tables

The application stores all required appointment information in MySQL:

- `user_accounts`
- `patients`
- `dentists`
- `treatments`
- `appointments`
- `bills`

Required scenario information is stored across normalized `patients` and `appointments` records:

- appointment number;
- patient name;
- address;
- contact number;
- dentist;
- treatment type;
- appointment date;
- appointment time.

## REST web services

Authenticated endpoints:

```text
GET    /api/v1/appointments/{appointmentNumber}
GET    /api/v1/appointments?date=YYYY-MM-DD
POST   /api/v1/appointments
POST   /api/v1/bills/{appointmentNumber}
GET    /api/v1/bills/{billNumber}
```
## Localhost
Application URL: http://localhost:8080

## Technology
Java 17
Spring Boot
MySQL
Maven

## Important academic note

You should understand and be able to explain the code and design choices during any viva/interview.
Capture your own screenshots, Git history, test results and deployment evidence. Do not claim
development activity that you did not actually perform.
