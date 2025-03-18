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
