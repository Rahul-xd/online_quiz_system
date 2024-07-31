# Online Quiz System

## Project Description
The Online Quiz System is a Java-based web application that allows users to create, manage, and participate in quizzes. It includes features like user authentication, quiz creation, and score tracking.

## Features
- User Registration and Login
- Quiz Creation with auto-generated Quiz IDs
- User-friendly interface for taking quizzes
- Admin panel for managing quizzes
- Database integration using MySQL

## Technologies Used
- **Java**: Core application logic
- **JSP** and **Servlets**: Web layer
- **MySQL**: Database
- **JDBC**: Database connectivity
- **HikariCP**: Connection pooling
- **Logback**: Logging
- **jBCrypt**: Password encryption
- **JSTL**: JSTL for JSP

## Setup Instructions

### Prerequisites
- JDK 8 or higher
- Apache Tomcat
- MySQL

### Installation

1. **Clone the repository**
- git clone <repository-url>
2. **Database Setup**
- Create a MySQL database (e.g., 'quizdb') and execute the SQL scripts located in the `sql` directory.
- Set the following environment variables:
  - `DB_URL`: Database URL (e.g., `jdbc:mysql://localhost:3306/quizdb`)
  - `DB_USER`: Database username
  - `DB_PASSWORD`: Database password

3. **Build and Deploy**
- Import the project into your IDE (Eclipse/IntelliJ).
- Add the necessary libraries (located in `lib/`) to the project build path.
- Deploy the project to your Apache Tomcat server.

### Running the Application
- Start the Tomcat server and access the application at `http://localhost:8080/OnlineQuizSystem`.

## Usage
- **User Registration and Login**: Users can register and log in to create or participate in quizzes.
- **Quiz Management**: Users can create quizzes with multiple questions and share the quiz ID with others.
- **Taking Quizzes**: Users can take quizzes by entering a valid quiz ID.

## Contributing
1. Fork the repository
2. Create a new branch (`git checkout -b feature-branch`)
3. Make your changes
4. Commit your changes (`git commit -am 'Add new feature'`)
5. Push to the branch (`git push origin feature-branch`)
6. Create a new Pull Request

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments
- **[HikariCP]**: Connection Pool
- **[jBCrypt]**: Password encryption
- **[Logback](http://logback.qos.ch/)**: Logging framework
- **[JSTL](https://jstl.java.net/)**: JavaServer Pages Standard Tag Library
