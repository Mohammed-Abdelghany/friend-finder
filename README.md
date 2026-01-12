# Friend Finder

A social networking platform built with **Spring Boot (backend)** and **Angular (frontend)**. The project supports user authentication, profile management with image upload, posts, friends management, and more.

---

## Project Structure

```
friend-finder/
├── backend/         # Spring Boot backend
│   ├── src/main/java/com/example/friendfinder
│   │   ├── config/         # Security & JWT config, filters
│   │   ├── controller/     # REST controllers (Auth, Upload, User)
│   │   ├── model/          # JPA Entities (User, Role, Post, Friend, etc.)
│   │   ├── repo/           # Spring Data JPA repositories
│   │   ├── service/        # Business logic services
│   │   └── FriendFinderApplication.java
│   └── src/main/resources
│       └── static/assets/uploads/   # Uploaded profile images
│
├── frontend/        # Angular frontend
│   ├── src/app
│   │   ├── auth/           # Login, Register, Authentication service
│   │   ├── profile/        # Profile components & service
│   │   ├── posts/          # Posts components & service
│   │   ├── friends/        # Friends components & service
│   │   └── shared/         # Shared services (e.g., UserProfileService)
│   └── angular.json
└── README.md
```

---

## Backend Features

- **Authentication & Authorization**
  - JWT-based authentication
  - Role-based access control
- **User Management**
  - Registration with profile image upload
  - Profile updates (bio, email, profile image)
- **Posts**
  - Create, read, update, delete posts
  - Like and comment functionality
- **Friends**
  - Send/accept friend requests
  - View friends list
- **Image Upload**
  - Profile images stored in `/static/assets/uploads/`
  - Unique and clean file names
- **Security**
  - Spring Security with filters
  - CORS configuration for Angular frontend

---

## Frontend Features

- **Authentication**
  - Login & register forms
  - JWT token stored in local storage
- **Profile**
  - View and edit profile
  - Display uploaded profile image
- **Posts**
  - Create, edit, delete posts
  - Like/unlike posts
- **Friends**
  - Send friend requests
  - Accept/decline requests
  - Friends list display
- **Reusable Services**
  - UserProfileService to share user data across components
  - AuthService for login, register, and HTTP requests

---

## Setup Instructions

### Backend

1. Navigate to backend folder:

```bash
cd backend
```

2. Configure application properties (`application.properties`) with your database credentials and JWT secret.

3. Build and run Spring Boot:

```bash
./mvnw spring-boot:run
```

- Backend runs on `http://localhost:9090`

### Frontend

1. Navigate to frontend folder:

```bash
cd frontend
```

2. Install dependencies:

```bash
npm install
```

3. Run Angular development server:

```bash
ng serve
```

- Frontend runs on `http://localhost:4200`

---

## Notes

- Uploaded images are accessible at:

```
http://localhost:9090/assets/uploads/<filename>
```

- Use **UserProfileService** to avoid repeating profile image HTTP requests in multiple components.
- Ensure backend is running before using frontend to avoid API errors.

---

## Technologies

- **Backend:** Java, Spring Boot, Spring Security, JPA/Hibernate, MySQL/Oracle
- **Frontend:** Angular, TypeScript, RxJS, Bootstrap/Material (optional)
- **Authentication:** JWT
- **File Uploads:** Spring Boot MultipartFile, saved in `static/assets/uploads`
- **Version Control:** Git & GitHub

