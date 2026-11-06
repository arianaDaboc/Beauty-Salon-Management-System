# Beauty Salon Management System

A **JavaFX desktop application** for running a beauty salon: customers browse services and book appointments, while administrators manage staff, services, categories and the appointment schedule. Data is stored in PostgreSQL and accessed through JDBC.

📄 [Full project documentation (Word)](oop_documentation.docx)

---

## Features

### Customers
- Register and log in
- Browse the service catalogue with prices and durations
- Book an appointment by choosing a service, a staff member, a date and a time
- View their own appointments

### Administrators
- Admin dashboard
- Manage services and service categories (add, edit, remove)
- Manage staff and their working hours
- View, modify and cancel appointments

### Business rules
- Role-based access: **ADMIN** and **CUSTOMER**
- No double booking: a staff member can't have two appointments at the same time
- Appointments must be in the future
- Usernames are unique and service prices must be positive
- Passwords are stored as salted PBKDF2 hashes, never in plain text

## Tech stack

Java · JavaFX (FXML) · PostgreSQL · JDBC · layered OOP architecture

## Architecture

The app is split into layers, each with a single responsibility:

```text
JavaFX UI (FXML views + controllers)
        │
        ▼
Service layer      business rules and validation
        │
        ▼
Repository layer   SQL through JDBC, maps rows to objects
        │
        ▼
PostgreSQL         7 tables
```

The **model** classes (`User`, `Role`, `Service`, `ServiceCategory`, `Staff`, `StaffWorkingHours`, `Appointment`) are shared by every layer.

## Database

`baza_date.sql` creates and fills the schema:

| Table | Contents |
|---|---|
| `role` | ADMIN, CUSTOMER |
| `app_user` | accounts, password hashes and role |
| `service_category` | makeup, hair, nails, cosmetics, barber |
| `service` | services with price and duration |
| `staff` | salon employees and their specialisation |
| `staff_working_hours` | weekly working hours per employee |
| `appointment` | bookings that link a customer, a service and a staff member |

## Getting started

### Prerequisites
- JDK 17+ with [JavaFX SDK](https://openjfx.io/)
- PostgreSQL
- PostgreSQL JDBC driver (`postgresql-*.jar`)

### 1. Create the database

```bash
createdb -U postgres beauty_salon
psql -U postgres -d beauty_salon -f baza_date.sql
```

### 2. Check the connection settings

The connection is configured in `src/database/DatabaseConnection.java`:

```java
URL      = "jdbc:postgresql://localhost:5432/beauty_salon"
USER     = "postgres"
PASSWORD = "postgres"
```

Change these values if your PostgreSQL setup is different.

### 3. Run the application

Open the project in IntelliJ IDEA (or another IDE), then:

1. Mark `src` as the sources root and `resources` as the resources root.
2. Add the JavaFX SDK and the PostgreSQL JDBC driver as libraries.
3. Add the VM options `--module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml`.
4. Run the JavaFX entry point `ui.main` (`src/ui/main.java`).

Register a new account to log in as a customer. To get an administrator, set that user's `role_id` to the ADMIN role in `app_user`.

## Project structure

```text
src/
  model/        data classes that mirror the database tables
  repository/   JDBC data access, one repository per table
  service/      business logic and validation
  database/     database connection
  ui/
    main.java     JavaFX entry point
    controllers/  JavaFX controllers (login, booking, admin screens…)
    session/      logged-in user session
  util/         password hashing, scene loading
resources/views/  FXML screens
baza_date.sql     database schema and sample data
oop_documentation.docx   detailed project documentation
```
