```markdown
# Crime Record Management System

A console-based Java application for managing criminal records, complaints, and FIR registrations. Designed for use by police departments to streamline record-keeping and complaint processing.

## Features

- **User Authentication**:  
  - Admin and regular user roles.
  - Admins can add criminals and convert complaints to FIRs.
- **Criminal Management** (Admin-only):  
  - Add criminal records with details like crime type, date, and punishment.
- **Complaint Management**:  
  - File complaints with incident descriptions and dates.
  - View pending complaints.
  - Convert valid complaints to FIRs (Admin-only).
- **Database Integration**:  
  - MySQL backend for data persistence.

## Prerequisites

- Java JDK 8 or later
- MySQL Server
- MySQL Connector/J (included in project dependencies)

## Installation & Setup

### 1. Database Setup

1. Create a MySQL database named `crime_db`:
   ```sql
   CREATE DATABASE crime_db;
   USE crime_db;
   ```

2. Create tables:
   ```sql
   -- Users table
   CREATE TABLE users (
       id INT AUTO_INCREMENT PRIMARY KEY,
       username VARCHAR(50) UNIQUE NOT NULL,
       password VARCHAR(50) NOT NULL,
       role ENUM('ADMIN', 'USER') NOT NULL
   );

   -- Criminals table
   CREATE TABLE criminals (
       id INT AUTO_INCREMENT PRIMARY KEY,
       name VARCHAR(100) NOT NULL,
       crime_type VARCHAR(50) NOT NULL,
       crime_date DATE NOT NULL,
       punishment_years INT NOT NULL
   );

   -- Complaints table
   CREATE TABLE complaints (
       id INT AUTO_INCREMENT PRIMARY KEY,
       complainant_name VARCHAR(100) NOT NULL,
       description TEXT NOT NULL,
       incident_date DATE NOT NULL,
       status ENUM('PENDING', 'FIR_REGISTERED') DEFAULT 'PENDING',
       police_station_id INT NOT NULL
   );

   -- FIRs table
   CREATE TABLE firs (
       id INT AUTO_INCREMENT PRIMARY KEY,
       complaint_id INT NOT NULL,
       registration_date DATE NOT NULL,
       FOREIGN KEY (complaint_id) REFERENCES complaints(id)
   );
   ```

3. Insert a sample admin user:
   ```sql
   INSERT INTO users (username, password, role) VALUES ('admin', 'admin123', 'ADMIN');
   ```

### 2. Configure Database Connection

Update the `DatabaseConnection.java` file with your MySQL credentials:
```java
private static final String URL = "jdbc:mysql://localhost:3306/crime_db";
private static final String USER = "your_mysql_username";
private static final String PASSWORD = "your_mysql_password";
```

### 3. Run the Application

Compile and execute the `CrimeRecordSystem` class:
```bash
javac -cp .:mysql-connector-java-8.0.23.jar main/CrimeRecordSystem.java
java -cp .:mysql-connector-java-8.0.23.jar main.CrimeRecordSystem
```

## Usage Guide

1. **Login**:  
   - Use `admin/admin123` for admin access.
   - Regular users can register via SQL (e.g., `INSERT INTO users ...`).

2. **Main Menu**:
   ```
   === Crime Record Management System ===
   Username: admin
   Password: admin123

   ===== Main Menu =====
   1. Add Criminal Record
   2. File Complaint
   3. View Pending Complaints
   4. Convert Complaint to FIR
   5. Exit
   ```

3. **Add Criminal** (Admin-only):  
   - Provide criminal name, crime type, date, and punishment years.

4. **File Complaint**:  
   - Enter complainant name, incident description, date, and police station ID.

5. **View Pending Complaints**:  
   - Lists all complaints with `PENDING` status.

6. **Convert to FIR** (Admin-only):  
   - Enter a complaint ID to register an FIR.

## Project Structure

```
src/
├── main/
│   └── CrimeRecordSystem.java       # Entry point
├── dao/
│   ├── CriminalDAO.java             # Criminal database operations
│   ├── ComplaintDAO.java            # Complaint and FIR operations
│   └── UserDAO.java                 # User authentication
├── model/
│   ├── Criminal.java                # Criminal entity
│   └── Complaint.java               # Complaint entity
└── util/
    └── DatabaseConnection.java      # Manages DB connections
```

## Dependencies

- **MySQL Connector/J**: Ensure the JAR file is in your classpath.  
  Download: [MySQL Connector/J 8.0](https://dev.mysql.com/downloads/connector/j/)

## Troubleshooting

- **Database Connection Failed**:  
  - Verify MySQL is running.
  - Check credentials in `DatabaseConnection.java`.
- **Invalid Date Format**:  
  - Use `yyyy-MM-dd` format for dates (e.g., `2023-10-25`).

## License

Open-source under MIT License.
