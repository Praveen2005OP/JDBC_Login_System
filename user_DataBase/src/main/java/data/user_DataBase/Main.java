package data.user_DataBase;

import java.util.Scanner;
import java.sql.*;

public class Main {
	static final String JDBC_URL = "jdbc:mysql://localhost:3306/user_db";
    static final String DB_USER = "root";
    static final String DB_PASSWORD = "Pr@220405";
    
    static Connection conn;
    static Scanner input = new Scanner(System.in);
    
    public static void main(String[] args) {
		boolean run = true;
    	try {
    		conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
    		while(run) {
    			System.out.println("1. Login");
    			System.out.println("2. Create New Account");
    			System.out.println("3. Forget Password");
    			System.out.println("4. Delete Account");
    			System.out.println("5. To exit");
    			String userinput = input.nextLine();
    			if(userinput.equals("1")) {
    				correctPassword();
    			}
    			else if(userinput.equals("2")) {
    				addUser();
    			}
    			else if(userinput.equals("3")) {
    				resetPassword();
    			}
    			else if(userinput.equals("4")) {
    				deleteAccount();
    			}
    			else if(userinput.equals("5")) {
    				run=false;
    				conn.close();
    				input.close();
    			}
    			else {
    				System.out.println("Invalid input!!!!!!");
    			}
    		}
    	}
    	catch(SQLException e) {
    		System.out.println("Database Connection Error: "+ e.getMessage());
    	}
	}
    
    static boolean containsUpperCase(String pass) {
    	boolean capital = false;
    	for(int i=0;i<pass.length();i++) {
    		if(Character.isUpperCase(pass.charAt(i))) {
    			capital = true;
    		}
    	}
    	return capital;
    }
    
    static boolean containsLowerCase(String pass) {
    	boolean lower = false;
    	for(int i=0;i<pass.length();i++) {
    		if(Character.isLowerCase(pass.charAt(i))) {
    			lower = true;
    		}
    	}
    	return lower;
    }
    
    static boolean containsSpecialCharacter(String pass) {
    	boolean special = false;
    	if(pass.contains("!")||pass.contains("@")||pass.contains("-")||pass.contains("_")||pass.contains("#")||pass.contains("$")||pass.contains("%")||pass.contains("^")||pass.contains("&")||pass.contains("*")||pass.contains("(")||pass.contains(")")||pass.contains("{")||pass.contains("}")||pass.contains("[")||pass.contains("]")) {
    		special = true;
    	}
    	return special;
    }
    
    static boolean passwordChecker(String pass) {
    	if(pass.length()<8) {
    		System.out.println("Password length must be greater then 8.");
    		return false;
    	}
    	else if(!containsUpperCase(pass)) {
    		System.out.println("Password must contain one capital letter.");
    		return false;
    	}
    	else if(!containsLowerCase(pass)) {
    		System.out.println("Password must contain one lower letter.");
    		return false;
    	}
    	else if(!containsSpecialCharacter(pass)) {
    		System.out.println("Password must contain one special character from these symbols !@#$%^&*(){}[]");
    		return false;
    	}
    	else {
    		System.out.println("All good.");
    		return true;
    	}
    }  
    
    static boolean userExists(String id) throws SQLException {
    	try (PreparedStatement ps = conn.prepareStatement("SELECT 1 FROM users WHERE userid = ?")) {
    	    ps.setString(1, id);
    	    try (ResultSet rs = ps.executeQuery()) {
    	        return rs.next();
    	    }
    	}
    }
    
    static void correctPassword() throws SQLException {
    	System.out.print("Enter UserId: ");
    	String userid = input.nextLine();
    	if(userid.length()==0) { System.out.println("Provide User Id!!!"); return; }
    	if(!userExists(userid)) { System.out.println("User doesn't exists."); return; }
    	System.out.print("Enter the password: ");
    	String pass = input.nextLine();
    	try(PreparedStatement ps = conn.prepareStatement("SELECT pass FROM users WHERE userid=?")) {
    		ps.setString(1, userid);
    		try (ResultSet rs = ps.executeQuery()) {
    			if(rs.next() && pass.equals(rs.getString(1))) {
    				System.out.println("You have successfully logged in.");
    			}
    			else {
    				System.out.println("Invalid password or userid!");
    			}
    		}
    	}
    }
    
    static void addUser() throws SQLException {
    	System.out.print("Enter User's ID: ");
    	String id = input.nextLine();
    	if(id.length()==0) {
    		System.out.println("UserID required!!!");
    		return;
    	}
    	if(userExists(id)) {
    		System.out.println("User does exist!!");
    		return;
    	}
    	System.out.print("Enter password: ");
    	String pass = input.nextLine();
    	if(!passwordChecker(pass)) {
    		return;
    	}
    	try(PreparedStatement ps = conn.prepareStatement("INSERT INTO users (userid, pass) VALUES (?, ?)")){
    		ps.setString(1, id);
    		ps.setString(2, pass);
    		ps.executeUpdate();
    	}
    }
    
    static void resetPassword() throws SQLException {
    	System.out.print("Enter User's ID: ");
    	String id = input.nextLine();
    	if(id.length()==0) {
    		System.out.println("UserID required!!!");
    		return;
    	}
    	if(!userExists(id)) {
    		System.out.println("User doesn't exist!!");
    		return;
    	}
    	System.out.print("Enter new password(if left blank new password will not be set): ");
    	String pass = input.nextLine();
    	
    	try(PreparedStatement ps = conn.prepareStatement("SELECT pass FROM users WHERE userid =?")){
    		ps.setString(1, id);
    		try(ResultSet rs = ps.executeQuery()) {
    			if(rs.next() && pass.length()==0) {
    				pass = rs.getString(1);
    			}
    		}
    	}
    	
    	if(!passwordChecker(pass)) {
    		return;
    	}
    	
    	try(PreparedStatement ps = conn.prepareStatement("UPDATE users SET pass=? WHERE userid=?")){
    		ps.setString(1, pass);
    		ps.setString(2, id);
    		ps.executeUpdate();
    	}
    }
    
    static void deleteAccount() throws SQLException {
    	System.out.print("Enter the UserID: ");
    	String id = input.nextLine();
    	if(id.length()==0) {
    		System.out.println("UserID required!!");
    		return;
    	}
    	if(!userExists(id)) {
    		System.out.println("User doesn't exist!!");
    		return;
    	}
    	System.out.print("Enter the password: ");
    	String pass = input.nextLine();
    	try(PreparedStatement ps = conn.prepareStatement("SELECT pass FROM users WHERE userid=?")){
    		ps.setString(1, id);
    		try(ResultSet rs = ps.executeQuery()){
    			if(rs.next() && pass.equals(rs.getString(1))) {
    				System.out.print("Are you sure you want to delete your account(Yes/No): ");
    				String confirm = input.nextLine();
    				if(confirm.toLowerCase().equals("yes")) {
				    	try(PreparedStatement ps1 = conn.prepareStatement("DELETE FROM users WHERE userid=?")) {
				    		ps1.setString(1, id);
				    		ps1.executeUpdate();
				    		System.out.println("Your account is deleted successfully.");
				    	}
    				}
    				else if(confirm.toLowerCase().equals("no")) {	
    					System.out.println("Your account was not deleted.");
    				}
    				else {
    					System.out.println("Invalid input!!");
    				}
    			}
    			else {
    				System.out.println("Password is not correct!!!\nTry again.");
    			}
    		}
    	}
    }
}
 