# 🔐 Java JDBC Login & User Management System
A console-based Login and User Management System built using Java and JDBC, supporting secure user authentication and account operations with a MySQL database.
This project demonstrates core JDBC concepts, PreparedStatement usage, and basic security practices in Java.
# 📌 Features
✅ User Registration.\
✅ User Login (with credential verification).\
✅ Password Reset.\
✅ Account Deletion (with password + confirmation).\
✅ Password validation rules.\
✅ SQL Injection protection using PreparedStatement.\
✅ Menu-driven console interface.
# 🛠️ Technologies Used
1. Java (JDK 8+)
2. JDBC
3. MySQL 
4. Scanner (Console Input)
# 🗄️ Database Schema
create database user_db;\
use user_db;\
create table users (\
	userid VARCHAR(50) PRIMARY KEY,\
    pass VARCHAR(15) NOT NULL\
    );
# 🚀 How to Run the Project
1. Clone the repository:
   git clone https://github.com/Praveen2005OP/JDBC_Login_System.git
2. Open the project in any Java IDE (Eclipse / IntelliJ / VS Code).
3. Update database credentials in the code:
   static final String JDBC_URL = "jdbc:mysql://localhost:3306/user_db";\
   static final String DB_USER = "root";\
   static final String DB_PASSWORD = "your_password";
4. Make sure MySQL Server is running.
5. Run the Main.java file.
# 🔑 Password Rules
1. Minimum 8 characters.
2. At least one uppercase letter.
3. At least one lowercase letter.
4. At least one special character.
5. At least one number.
# 🔐 Security Practices Used
1. PreparedStatement to prevent SQL Injection.
2. Password verification before sensitive operations.
3. Confirmation prompt before account deletion.
>⚠️ Note: Passwords are stored in plain text for learning purposes.\
>In real-world applications, passwords should be hashed before storing.
# 📂 Project Structure
src/\
 └── data/\
     └── user_DataBase/\
         └── Main.java
# 📚 Learning Outcomes
1. Understanding JDBC architecture.
2. Working with PreparedStatement and ResultSet.
3. Implementing CRUD operations.
4. Handling database connections safely.
5. Designing menu-driven console applications.
# 👨‍💻 Author
Praveen Kumar Sharma\
Engineering Student\
GLA University
# 📄 License
This project is for educational purposes only.
