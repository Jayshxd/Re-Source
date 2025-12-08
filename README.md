# ==Re-Source - Easy Asset Management System==

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.7-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0+-blue.svg)](https://www.mysql.com/)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)

## 📋 Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
  - [Configuration](#configuration)
- [API Documentation](#api-documentation)
- [Database Schema](#database-schema)
- [Security](#security)
- [Email Notifications](#email-notifications)
- [Project Status](#project-status)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)

## 🎯 Overview

**Re:Source** is a comprehensive asset management system built with Spring Boot that helps organizations efficiently track and manage their assets. The system provides a complete solution for asset allocation, employee management, and detailed tracking of asset assignments with automated email notifications and JWT-based security.

This application enables organizations to:
- Maintain a centralized database of assets
- Track asset assignments to employees
- Monitor asset returns and conditions
- Generate analytics and reports
- Manage employee authentication and authorization

## ✨ Features

### Asset Management
- ✅ **CRUD Operations**: Create, read, update, and delete assets
- ✅ **Bulk Operations**: Create multiple assets at once
- ✅ **Advanced Search**: Search assets by name, serial number, or asset type
- ✅ **Asset Tracking**: View complete assignment history for each asset
- ✅ **Asset Types**: Support for various asset categories

### Employee Management
- ✅ **User Registration**: Register new employees with role-based access
- ✅ **Bulk Registration**: Register multiple employees simultaneously
- ✅ **Authentication**: Secure JWT-based login system
- ✅ **Employee Search**: Find employees by name, email, or username
- ✅ **Assignment History**: Track all assets assigned to each employee

### Asset Tracking System
- ✅ **Asset Assignment**: Assign assets to employees with issue date/time
- ✅ **Asset Return**: Record asset returns with condition notes
- ✅ **Expected Return Dates**: Set and track expected return dates
- ✅ **Status Tracking**: Monitor whether assets are currently issued or returned
- ✅ **Condition Monitoring**: Track asset condition at return
- ✅ **Analytics Dashboard**: View comprehensive tracking analytics

### Security & Notifications
- ✅ **JWT Authentication**: Secure token-based authentication
- ✅ **Role-Based Access Control**: Manage user permissions with roles
- ✅ **Email Notifications**: Automated email notifications for important events
- ✅ **Password Encryption**: Secure password storage with Spring Security
- ✅ **CORS Support**: Cross-origin resource sharing enabled

## 🛠 Technology Stack

### Backend
- **Java 17**: Modern Java version with latest features
- **Spring Boot 3.5.7**: Latest Spring Boot framework
- **Spring Data JPA**: Database operations and ORM
- **Spring Security**: Authentication and authorization
- **Spring Mail**: Email notification service
- **MySQL**: Relational database management
- **Lombok**: Reduces boilerplate code
- **Maven**: Dependency management and build tool

### Security & Authentication
- **JWT (JSON Web Tokens)**: Token-based authentication
- **JJWT 0.12.3**: JWT implementation library
- **BCrypt**: Password encryption

### Additional Features
- **Hibernate**: ORM framework with MySQL dialect
- **Spring DevTools**: Development-time features
- **Scheduled Tasks**: Automated background jobs

### Frontend
- **React 18**: Modern React with hooks
- **Vite**: Next-generation frontend tooling
- **Tailwind CSS**: Utility-first CSS framework
- **React Router**: Client-side routing
- **Axios**: HTTP client for API requests
- **Recharts**: Data visualization library
- **Lucide React**: Icon library

## 🚀 Getting Started

### Prerequisites

Before running this application, ensure you have the following installed:

- **Java 17** or higher ([Download](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html))
- **MySQL 8.0** or higher ([Download](https://dev.mysql.com/downloads/))
- **Maven 3.6+** (included via Maven Wrapper)
- **Git** (for cloning the repository)

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/Jayshxd/Re-Source.git
   cd Re-Source
   ```

2. **Create MySQL Database**
   ```sql
   CREATE DATABASE resourceDB;
   ```
   Note: The application will automatically create the database if it doesn't exist (configured with `createDatabaseIfNotExist=true`).

3. **Configure Application Properties**
   
   Update `src/main/resources/application.properties` with your database and email credentials:
   
   ```properties
   # Database Configuration
   spring.datasource.url=jdbc:mysql://localhost:3306/resourceDB?createDatabaseIfNotExist=true
   spring.datasource.username=your_mysql_username
   spring.datasource.password=your_mysql_password
   
   # JWT Secret Key (Change this for production!)
   jwt.secrete.key=YourSecretKeyHere
   
   # Email Configuration
   spring.mail.username=your_email@gmail.com
   spring.mail.password=your_app_specific_password
   ```

4. **Build the project**
   ```bash
   ./mvnw clean install
   ```
   
   For Windows:
   ```bash
   mvnw.cmd clean install
   ```

5. **Run the backend application**
   ```bash
   ./mvnw spring-boot:run
   ```
   
   For Windows:
   ```bash
   mvnw.cmd spring-boot:run
   ```

The backend application will start on `http://localhost:8080`

### Running the Frontend

1. **Navigate to the frontend directory**
   ```bash
   cd frontend
   ```

2. **Install dependencies**
   ```bash
   npm install
   ```

3. **Configure environment variables**
   
   Create a `.env` file (copy from `.env.example`):
   ```
   VITE_API_BASE_URL=http://localhost:8080
   ```

4. **Start the development server**
   ```bash
   npm run dev
   ```

The frontend application will start on `http://localhost:5173`

For production build:
```bash
npm run build
```

See `/frontend/README.md` for detailed frontend documentation.

### Configuration

#### Database Configuration
The application uses MySQL with Hibernate for database operations. Key configurations:

- **Auto DDL**: `spring.jpa.hibernate.ddl-auto=update` - Automatically updates schema
- **SQL Logging**: `spring.jpa.show-sql=true` - Shows SQL queries in console
- **Dialect**: MySQL dialect for optimized queries

#### Email Configuration
Configure SMTP settings for email notifications:

- **Gmail Users**: Use [App Passwords](https://support.google.com/accounts/answer/185833) instead of regular passwords
- **Port 587**: TLS encryption
- **Port 465**: SSL encryption (alternative)

#### Security Configuration
- **JWT Secret Key**: Change the default secret key in production
- **Token Expiration**: Configure in `JwtUtil.java`
- **CORS**: Currently allows all origins (`*`) - restrict in production

## 📚 API Documentation

### Authentication Endpoints

#### Register Employee 
```http
POST /auth/register
Content-Type: application/json

{
  "name": "John Doe",
  "username": "johndoe",
  "email": "john@example.com",
  "password": "securePassword123"
}
```

#### Bulk Register Employees 
```http
POST /auth/bulk/register
Content-Type: application/json

[
  {
    "name": "Employee 1",
    "username": "emp1",
    "email": "emp1@example.com",
    "password": "password1"
  },
  {
    "name": "Employee 2",
    "username": "emp2",
    "email": "emp2@example.com",
    "password": "password2"
  }
]
```

#### Login
```http
POST /auth/login
Content-Type: application/json

{
  "username": "johndoe",
  "password": "securePassword123"
}

Response:
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "employeeInfo": {
    "id": 1,
    "name": "John Doe",
    "username": "johndoe",
    "email": "john@example.com"
  }
}
```

### Asset Management Endpoints

#### Create Asset
```http
POST /assets
Authorization: Bearer <token>
Content-Type: application/json

{
  "name": "Dell Laptop",
  "serialNumber": "DL123456",
  "assetType": "Laptop"
}
```

#### Create Assets in Bulk
```http
POST /assets/bulk
Authorization: Bearer <token>
Content-Type: application/json

[
  {
    "name": "HP Laptop",
    "serialNumber": "HP001",
    "assetType": "Laptop"
  },
  {
    "name": "Dell Monitor",
    "serialNumber": "DM001",
    "assetType": "Monitor"
  }
]
```

#### Search Assets
```http
GET /assets/search?name=Laptop&assetType=Laptop
Authorization: Bearer <token>
```

#### Update Asset (Full Update)
```http
PUT /assets/{id}
Authorization: Bearer <token>
Content-Type: application/json

{
  "name": "Updated Asset Name",
  "serialNumber": "NEW123",
  "assetType": "Updated Type"
}
```

#### Update Asset (Partial Update)
```http
PATCH /assets/{id}
Authorization: Bearer <token>
Content-Type: application/json

{
  "name": "Updated Name Only"
}
```

#### Delete Asset
```http
DELETE /assets/{id}
Authorization: Bearer <token>
```

#### Get Asset Tracks
```http
GET /assets/{assetId}/tracks
Authorization: Bearer <token>
```

### Employee Endpoints

#### Get All Employees
```http
GET /emps/all?name=John&email=john@example.com
Authorization: Bearer <token>
```

#### Get Employee Tracks
```http
GET /emps/{empId}/tracks
Authorization: Bearer <token>
```

#### Count Employee Tracks
```http
GET /emps/count/{empId}/tracks
Authorization: Bearer <token>
```

### Track Management Endpoints

#### Assign Asset
```http
POST /tracks/assignAsset
Authorization: Bearer <token>
Content-Type: application/json

{
  "employeeId": 1,
  "assetId": 1,
  "expectedReturnDate": "2024-12-31"
}
```

#### Return Asset
```http
POST /tracks/returnAsset
Authorization: Bearer <token>
Content-Type: application/json

{
  "trackId": 1,
  "assetCondition": "Good",
  "returnDate": "2024-11-20",
  "returnTime": "14:30:00"
}
```

#### Search Tracks
```http
GET /tracks/search?isReturned=false&assetCondition=Good
Authorization: Bearer <token>
```

#### Update Track (Full)
```http
PUT /tracks/{trackId}
Authorization: Bearer <token>
Content-Type: application/json

{
  "expectedReturnDate": "2024-12-31",
  "assetCondition": "Excellent"
}
```

#### Update Track (Partial)
```http
PATCH /tracks/{trackId}
Authorization: Bearer <token>
Content-Type: application/json

{
  "expectedReturnDate": "2024-12-31"
}
```

#### Delete Track
```http
DELETE /tracks/{trackId}
Authorization: Bearer <token>
```

#### Get Analytics
```http
GET /tracks/analytics
Authorization: Bearer <token>

Response:
{
  "totalTracks": 100,
  "activeAssignments": 45,
  "returnedAssets": 55,
  "overdueAssets": 5
}
```

## 🗄 Database Schema

### Entity Relationships

```
Employee (1) ----< (N) Track (N) >---- (1) Asset
    |                                       
    |                                       
    └------ (N) employee_roles (N) ------┘
                      |
                      v
                    Role
```

### Tables

#### Employees Table
- `id` (Primary Key)
- `name`
- `username` (Unique)
- `email` (Unique)
- `password` (Encrypted)
- `created_at`
- `updated_at`

#### Assets Table
- `id` (Primary Key)
- `name`
- `serial_number`
- `asset_type`
- `created_at`
- `updated_at`

#### Tracks Table
- `id` (Primary Key)
- `employee_id` (Foreign Key)
- `asset_id` (Foreign Key)
- `issue_date`
- `issue_time`
- `return_date`
- `return_time`
- `asset_condition`
- `is_returned`
- `expected_return_date`
- `created_at`
- `updated_at`

#### Roles Table
- `id` (Primary Key)
- `name`

## 🔐 Security

### Authentication Flow

1. **User Registration**: New users register with credentials
2. **Password Encryption**: Passwords encrypted using BCrypt
3. **Login**: User provides credentials
4. **JWT Generation**: System generates JWT token upon successful authentication
5. **Token Usage**: Client includes token in Authorization header for subsequent requests
6. **Token Validation**: Server validates token for each protected endpoint

### Security Features

- **JWT-based Authentication**: Stateless authentication mechanism
- **BCrypt Password Hashing**: Industry-standard password encryption
- **Role-Based Access Control**: Different permissions for different user roles
- **Spring Security Integration**: Comprehensive security framework
- **CORS Configuration**: Cross-origin resource sharing control
- **Token Filter**: Validates JWT tokens on each request

### Best Practices for Production

1. Change the default JWT secret key
2. Use environment variables for sensitive configuration
3. Implement token refresh mechanism
4. Add rate limiting
5. Restrict CORS to specific origins
6. Use HTTPS in production
7. Implement proper error handling
8. Add request logging and monitoring

## 📧 Email Notifications

The application includes an email notification service for important events:

- **Asset Assignment Notifications**: Notify employees when assets are assigned
- **Return Reminders**: Automated reminders for expected return dates
- **System Notifications**: Administrative notifications

### Email Configuration Requirements

- Valid SMTP server credentials
- App-specific password for Gmail users
- Proper firewall configuration for SMTP ports
- TLS/SSL enabled

## 📊 Project Status

### ✅ Completed Features
- [x] Basic CRUD APIs for all entities
- [x] JWT-based authentication and authorization
- [x] Asset management system
- [x] Employee management system
- [x] Asset tracking and assignment system
- [x] Email notification service
- [x] Analytics and reporting
- [x] Bulk operations support
- [x] Advanced search functionality
- [x] **Modern React Frontend** with Vite and Tailwind CSS
- [x] **Responsive UI** with dark theme
- [x] **Interactive Dashboard** with charts and analytics
- [x] **Complete CRUD interfaces** for all entities

### 🚧 In Progress
- [ ] Advanced analytics dashboard
- [ ] Report generation (PDF/Excel)

### 🔮 Future Enhancements
- [ ] Mobile application
- [ ] Real-time notifications
- [ ] Asset depreciation tracking
- [ ] Maintenance scheduling
- [ ] Asset lifecycle management
- [ ] QR code integration
- [ ] Advanced reporting and charts
- [ ] Multi-tenant support
- [ ] Asset location tracking
- [ ] Document management for assets

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. **Fork the repository** 
2. **Create a feature branch**
   ```bash
   git checkout -b feature/AmazingFeature
   ```
3. **Commit your changes**
   ```bash
   git commit -m 'Add some AmazingFeature'
   ```
4. **Push to the branch** 
   ```bash
   git push origin feature/AmazingFeature
   ```
5. **Open a Pull Request**

### Coding Standards
- Follow Java naming conventions
- Write meaningful commit messages
- Add comments for complex logic
- Include unit tests for new features
- Update documentation as needed

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 📞 Contact

**Project Owner**: Jayesh

**Repository**: [https://github.com/Jayshxd/Re-Source](https://github.com/Jayshxd/Re-Source)

For questions, suggestions, or issues, please open an issue on GitHub.

---

## 🙏 Acknowledgments

- Spring Boot community for excellent documentation
- All contributors who help improve this project
- Open source community for inspiration and support

---

**Made with ❤️ using Spring Boot**
