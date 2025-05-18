# JavaFX User Authentication System with Secure Password Hashing

[![Java](https://img.shields.io/badge/Java-17+-orange.svg)](https://www.java.com/en/)
[![JavaFX](https://img.shields.io/badge/JavaFX-17+-blueviolet.svg)](https://openjfx.io/)
[![PostgreSQL](https://img.shields.io/badge/Database-PostgreSQL-blue.svg)](https://www.postgresql.org/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

This project, developed by second-year Computer Engineering students at FIEK, is a complete user registration and authentication system. Built using Java, JavaFX, and PostgreSQL, it securely handles user credentials through PBKDF2 with HMAC SHA-256 password hashing and salt generation.

It includes an intuitive JavaFX GUI for login and registration, robust field validation, and real-time feedback through alerts and input control.

## Developed by

* Enkel Berisha ([@EnkelBerisha](https://github.com/enkelberisha1))
* Engji Osmani ([@EngjiOsmani](https://github.com/engjiosmani))
* Enes Spahiu ([@EnesSpahiu](https://github.com/enesispahiu))
* Enis Morina ([@EnisMorina](https://github.com/enismorina1))

## Clone the Repository

```bash
git clone https://github.com/engjiosmani/PasswordHasher.git
```

## Project Description

This JavaFX desktop application implements a complete sign-up and login flow with secure password storage using PBKDF2. It demonstrates how modern security standards can be implemented in a simple and clean desktop GUI application.

The system allows users to:

- Create a new account with proper validations
- Automatically generate salt for every user
- Store passwords securely in the database using hash + salt
- Log in using email and password, with backend verification
- Toggle visibility for password fields
- Receive feedback via dialogs for success or failure
- Store and retrieve user data using a PostgreSQL database

## Code Structure

```text
src/
├── application/
│   └── app.java                      # Entry point of the application
├── controller/
│   ├── SignInController.java         # Handles sign-in logic
│   └── SignUpController.java         # Handles sign-up logic
├── utils/
│   ├── DBConnector.java              # PostgreSQL DB connection
│   ├── DBUserFunctions.java          # Queries for user registration/login
│   ├── PasswordHasher.java          # Hashing and salting logic (PBKDF2)
│   ├── Session.java                  # Session management
│   └── Users.java                    # User model
resources/
├── fxml/
│   ├── signin.fxml                   # Login interface
│   └── signup.fxml                   # Registration interface
├── images/
│   ├── eye.png                       # Toggle icon for visible password
│   └── hidden.png                    # Toggle icon for hidden password
└── database/
    └── passwordhasher.sql           # SQL script for table creation

```

## Password Security

The application uses PBKDF2WithHmacSHA256 to hash passwords with a randomly generated salt. Each user gets a unique salt, and only the hashed password + salt are stored in the database.

### Hashing Parameters:

- Algorithm: PBKDF2WithHmacSHA256
- Salt length: 16 bytes
- Iterations: 65536
- Key length: 256 bits

## How to Run

### Requirements:

- Java 17 or newer
- JavaFX SDK installed and configured
- PostgreSQL server running

### Setup PostgreSQL:

```sql
CREATE DATABASE passwordhasher;

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(100) UNIQUE NOT NULL,
    password_hash TEXT NOT NULL,
    salt TEXT NOT NULL,
    firstName VARCHAR(100) NOT NULL,
    lastName VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL
);
```

### Update credentials:

Edit `DBConnector.java`:

```java
private static final String DB_URL = "jdbc:postgresql://localhost/passwordhasher";
private static final String USER = "java";
private static final String PASSWORD = "123";
```

### Run the application:

Execute the `main` method in `app.java` to launch the GUI.

## Example Workflow

1. User signs up with a new email and password
2. A random salt is generated
3. The password is hashed using PBKDF2 with the salt
4. Hash and salt are saved in the database
5. During login, the password is re-hashed and verified

## Screenshots

![image](https://github.com/user-attachments/assets/d3e5621b-2155-4b40-ba73-1a80dbb08d25)

![image](https://github.com/user-attachments/assets/7ea3b79b-e9d9-4e98-8634-5bd6769752a0)

## License

This project is licensed under the [MIT License](https://opensource.org/licenses/MIT)
