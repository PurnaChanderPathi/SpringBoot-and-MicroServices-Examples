package com.jdbc.student;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class StudentDatabase {
	
	private static Connection connection = null;
	private static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		StudentDatabase studentDatabase = new StudentDatabase();
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String dbURL ="jdbc:mysql://localhost:3306/jdbcdb";
			String username = "Purnachander";
			String password = "Purna@123";
			connection = DriverManager.getConnection(dbURL, username, password);
			
			System.out.println("Enter choice");
			System.out.println("1. Insert record");
			System.out.println("2. Select record");
			System.out.println("3. Callable Statement: Select records");
			System.out.println("4. Callable Statement: Select records by roll number");
			System.out.println("5 Update record");
			System.out.println("6. delete Record");
			int choice = Integer.parseInt(scanner.nextLine());
			
			switch (choice) {
			case 1: 
				studentDatabase.insertRecord();
				break;
			case 2:
				studentDatabase.selectRecord();
				break;
			case 3:
				studentDatabase.selectAllRecords();
				break;
			case 4:
				studentDatabase.selectRecordByRollNumber();
				break;
			case 5:
				studentDatabase.updateRecord();
				break;
			case 6:
				studentDatabase.deleteRecord();
			default:
				break;			
			}
		
			
		} catch (Exception e) {
			throw new RuntimeException("Something went wrong");
		}

	}
	private void insertRecord() throws SQLException {
		//System.out.println("inside insert record()");
		String sql = "insert into student(name,percentage,address) values (?,?,?)";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		System.out.println("Enter name");
		preparedStatement.setString(1, scanner.nextLine());
		System.out.println("Enter percentage");
		preparedStatement.setDouble(2, Double.parseDouble(scanner.nextLine()));
		System.out.println("Enter address");
		preparedStatement.setString(3, scanner.nextLine());
		int row = preparedStatement.executeUpdate();
		if(row > 0) {
			System.out.println("Record inserted successfully");
		}
	}
	private void selectRecord() throws SQLException {
		System.out.println("Select method to call");
		System.out.println("Enter roll number to find result");
		int number = Integer.parseInt(scanner.nextLine());
		String sql = "select * from student where roll_number = "+number;
		Statement statement = connection.createStatement();
		ResultSet result = statement.executeQuery(sql);
		
		if(result.next()) {
			int rollNumber = result.getInt("roll_number");
			String name = result.getString("name");
			double percentage = result.getDouble("percentage");
			String address = result.getString("address");
			
			System.out.println("Roll Number is: "+rollNumber);
			System.out.println("name is: "+name);
			System.out.println("percentage is: "+percentage);
			System.out.println("address is: "+address);
		}else {
			System.out.println("No Record Found");
		}
	}
	
	private void selectAllRecords() throws SQLException {
		//System.out.println("i am in my Method");
		CallableStatement callableStatement = connection.prepareCall("{ call GET_ALL() }");
		ResultSet result = callableStatement.executeQuery();
		
		while(result.next()) {
			System.out.println("Roll number is "+result.getInt("roll_number"));
			System.out.println("Name is "+result.getString("name"));
			System.out.println("Percentage is"+result.getDouble("percentage"));
			System.out.println("Address is "+result.getString("address"));
		}
	}
	
	private void selectRecordByRollNumber() throws SQLException {
		System.out.println("Enter Roll Number to fetch details");
		int roll = Integer.parseInt(scanner.nextLine());
		CallableStatement callableStatement = connection.prepareCall("{ call GET_RECORD(?) }");
		callableStatement.setInt(1, roll);
		ResultSet result = callableStatement.executeQuery();
		
		while(result.next()) {
			System.out.println("Roll number is "+result.getInt("roll_number"));
			System.out.println("Name is "+result.getString("name"));
			System.out.println("Percentage is"+result.getDouble("percentage"));
			System.out.println("Address is "+result.getString("address"));
		}
	}
	
	private void updateRecord() throws SQLException {
		System.out.println("Enter Roll Number to update record");
		int roll = Integer.parseInt(scanner.nextLine());
		String sql = "select * from student where roll_number = "+roll;
		Statement statement = connection.createStatement();
		ResultSet result = statement.executeQuery(sql);
		if(result.next()) {
			int rollNumber = result.getInt("roll_number");
			String name = result.getString("name");
			double percentage = result.getDouble("percentage");
			String address = result.getString("address");
			
			System.out.println("Roll number is "+rollNumber);
			System.out.println("Name is "+name);
			System.out.println("Percentage is"+percentage);
			System.out.println("Address is "+address);
			
			System.out.println("What do you want to update?");
			System.out.println("1. name");
			System.out.println("2. percentage");
			System.out.println("3. address");
			
			int choice = Integer.parseInt(scanner.nextLine());
			String sqlQuery = "update student set ";
			switch (choice) {
			case 1: 
				System.out.println("Enter new name ");
				String newName = scanner.nextLine();
				sqlQuery+="name = ? where roll_number = "+rollNumber;
				PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
				preparedStatement.setString(1, newName);
				int row = preparedStatement.executeUpdate();
				if(row>0) {
					System.out.println("Record Updated Successfully..!");
				}
				break;
			case 2:
				System.out.println("Enter new percentage ");
				double newPercentage = Double.parseDouble(scanner.nextLine());
				sqlQuery+="percentage = ? where roll_number = "+rollNumber;
				PreparedStatement preparedStatement1 = connection.prepareStatement(sqlQuery);
				preparedStatement1.setDouble(1, newPercentage);	
				int row1 = preparedStatement1.executeUpdate();
				if(row1>0) {
					System.out.println("Record Updated Successfully..!");
				}
				break;
			case 3:
				System.out.println("Enter new address ");
				String newAddress = scanner.nextLine();
				sqlQuery+="address = ? where roll_number = "+rollNumber;
				PreparedStatement preparedStatement2 = connection.prepareStatement(sqlQuery);
				preparedStatement2.setString(1, newAddress);
				int row2 = preparedStatement2.executeUpdate();
				if(row2>0) {
					System.out.println("Record Updated Successfully..!");
				}

			default:
				break;
			}
			
		}else {
			System.out.println("Record not found");
		}
	}
	
	public void deleteRecord() throws SQLException {
		System.out.println("Enter roll number to delete");
		int roll = Integer.parseInt(scanner.nextLine());
		String sql = "delete from student where roll_number = "+roll;
		Statement statement = connection.createStatement();
		int row4 = statement.executeUpdate(sql);
		if(row4 > 0) {
			System.out.println("Successfully Deleted..!");
		}
		
	}

}
