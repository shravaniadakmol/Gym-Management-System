package com.project;

import java.sql.*;
import java.util.Scanner;

public class WomenGym {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner sc = new Scanner(System.in);
        Connection connection = null;
        try {
            // Step 1: Loading the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Step 2: Establishing a database connection
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/womengym", "root", "root");

            int operation;
            do {
            	System.out.println("Gym Operations");
            	System.out.println("1. Add Member");
            	System.out.println("2. Add Trainer");
            	System.out.println("3. Add Equipment");
            	System.out.println("4. View Member");
            	System.out.println("5. View Trainer");
            	System.out.println("6. View Equipment");
            	System.out.println("7. Exit");
            	System.out.println("Enter Operations: ");
            	operation=sc.nextInt();
            	sc.nextLine();
            	
            	switch(operation) {
            	case 1:
            		addMembers(connection,sc);
            		break;
            		
            	case 2: 
                    addTrainers(connection, sc);
                    break;

                case 3:
                    addEquipments(connection, sc);
                    break;

                case 4:
                    viewMembers(connection);
                    break;

                case 5:
                	viewTrainers(connection);
                    break;

                case 6:
                    viewEquipments(connection);
                    break;

                case 7:
                    System.out.println("Exiting program.");
                    break;

                default:
                    System.out.println("Invalid operation. Please enter a valid operation number.");
                    break;
                	
            	}
            	
            	
            }
            while(operation !=7);

	}
        
        catch(ClassNotFoundException e)
        {
        	System.out.println("MySQL JDBC driver not found.");
            e.printStackTrace();
        }
       
        catch (SQLException e) {
            System.out.println("Database error:");
            e.printStackTrace();
        } 
        
        finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                sc.close();
            } 
            
            catch (SQLException e) {
                System.out.println("Error closing connection:");
                e.printStackTrace();
            }
            
        }

}
	private static void addMembers(Connection connection,Scanner sc)throws SQLException
	{
		System.out.println("Enter Member Name: ");
		String name=sc.nextLine();
		System.out.println("Enter Member's Mobile Number: ");
		String mobile_number=sc.nextLine();
		System.out.println("Enter Member's Age: ");
		int age=sc.nextInt();
		sc.nextLine();
		
		 String insertSql = "INSERT INTO members (name, mobile_number, age) VALUES (?, ?, ?)";
	        PreparedStatement insertStatement = connection.prepareStatement(insertSql);
	        insertStatement.setString(1, name);
	        insertStatement.setString(2, mobile_number);
	        insertStatement.setInt(3, age);
	        
	        int rowsAffected = insertStatement.executeUpdate();
	        if (rowsAffected > 0) {
	            System.out.println("SUCCESSFULLY ADDED A MEMBER !!");
	        } else {
	            System.out.println("FAILED TO ADD MEMBER.");
	}
}
	
    private static void addTrainers(Connection connection, Scanner sc) throws SQLException {
        System.out.println("Enter Trainer Name: "); 
        String trainer_name = sc.nextLine();
        System.out.println("Enter Trainer's Mobile Number: ");
        String tcontact = sc.nextLine();
        System.out.println("Enter Trainer's Age: ");
        int tage = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter Trainer's Salary: ");
        int salary = sc.nextInt();
        sc.nextLine();

        String insertSql = "INSERT INTO trainers (trainer_name, tcontact, tage, salary) VALUES (?, ?, ?, ?)";
        PreparedStatement insertStatement = connection.prepareStatement(insertSql);
        insertStatement.setString(1, trainer_name);
        insertStatement.setString(2, tcontact);
        insertStatement.setInt(3, tage);
        insertStatement.setInt(4, salary);

        int rowsAffected = insertStatement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("TRAINER ADDED SUCCESSFULLY !!");
        } else {
            System.out.println("FAILED TO ADD TRAINER.");
        }
    }
    
    
    private static void addEquipments(Connection connection, Scanner sc) throws SQLException {
    	viewMembers(connection);
    	System.out.println("Enter member id: ");
    	int memberId = sc.nextInt();
    	sc.nextLine();
    	
    	viewTrainers(connection);
    	System.out.println("Enter trainer id: ");
    	int trainerId = sc.nextInt();
    	sc.nextLine();
    	
    	System.out.println("Enter today's Workout: ");
    	String workout = sc.nextLine();
    	
    	System.out.println("Enter Equipments you are going to use: ");
    	String eqlist = sc.nextLine();
    	
    	String insertSql = "INSERT INTO equipments (member_id, trainer_id, workout, equipment_list) VALUES (?, ?, ?, ?)";
        PreparedStatement insertStatement = connection.prepareStatement(insertSql);
        insertStatement.setInt(1, memberId);
        insertStatement.setInt(2, trainerId);
        insertStatement.setString(3, workout);
        insertStatement.setString(4, eqlist);

        int rowsAffected = insertStatement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("EQUIPMENT ADDED SUCCESSFULLY !!");
        } else {
            System.out.println("FAILED TO ADD EQUIPMENT.");	
    }
    	
}
    
    
    private static void viewMembers(Connection connection) throws SQLException {
        String retrieveSql = "SELECT * FROM members";
        try (Statement stmt = connection.createStatement(); ResultSet resultSet = stmt.executeQuery(retrieveSql)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("member_id");
                String name = resultSet.getString("name");
                String mobile_number = resultSet.getString("mobile_number");
                int age = resultSet.getInt("age");
                System.out.println("ID: " + id + ", Name: " + name + ", Mobile_number: " + mobile_number +", Age: " + age);
            }
        }
    }
    
    
    private static void viewTrainers(Connection connection) throws SQLException {
        String retrieveSql = "SELECT * FROM trainers";
        try (Statement stmt = connection.createStatement(); ResultSet resultSet = stmt.executeQuery(retrieveSql)) {
            while (resultSet.next()) {
                int t_id = resultSet.getInt("trainer_id");
                String trainer_name = resultSet.getString("trainer_name");
                String tcontact = resultSet.getString("tcontact");
                int tage = resultSet.getInt("tage");
                int salary = resultSet.getInt("salary");
                System.out.println("ID: " + t_id + ", Name: " + trainer_name + ", Contact: " + tcontact +", Age: " + tage + ", Salary: " + salary);
            }
        }
    }
    
    private static void viewEquipments(Connection connection) throws SQLException {
    	String retrieveSql = "SELECT e.equipment_id, m.name AS name ,t.trainer_name, e.workout, e.equipment_list " +
                "FROM equipments e " +
                "INNER JOIN members m ON e.member_id = m.member_id " +
                "INNER JOIN trainers t ON e.trainer_id = t.trainer_id";
        try (Statement stmt = connection.createStatement(); ResultSet resultSet = stmt.executeQuery(retrieveSql)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("equipment_id");
                String name = resultSet.getString("name");
                String trainer_name = resultSet.getString("trainer_name");
                String workout = resultSet.getString("workout");
                String eqlist = resultSet.getString("equipment_list");
                System.out.println("ID: " + id + ", Name: " + name + ", Trainer: " + trainer_name + ", Workout: " + workout + ", Equipments used: " + eqlist );
            }
        }
    }
    
    
}
    
    
    
    
    
    

 