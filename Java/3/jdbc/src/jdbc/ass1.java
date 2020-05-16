package jdbc;

import java.sql.*;
import java.util.Scanner;

public class ass1 {
	public static void main(String[] args) {
		try {
			Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:1234/dbms", "root", "puru1234");
			Statement myStmt = myConn.createStatement();
			
			String ID, Salary, tempID;
			String Name;
			Scanner ip = new Scanner(System.in);
			
			//display
			ResultSet myRs = myStmt.executeQuery("select * from employee");
			System.out.println("ID : Name : Salary\n--------------------");
			while(myRs.next()) {
				System.out.println(myRs.getInt("ID") + " : "+ myRs.getString("Name") + " : " + myRs.getInt("Salary"));
			}
			
			//insert query
			System.out.println("Enter ID, Name, Salary");
			ID = ip.nextLine();
			Name = ip.nextLine();
			Salary = ip.nextLine();
			myStmt.execute("insert into employee values(" + ID + ", '" + Name + "', " + Salary + ")");
			
			//display
			myRs = myStmt.executeQuery("select * from employee");
			System.out.println("ID : Name : Salary\n--------------------");
			while(myRs.next()) {
				System.out.println(myRs.getInt("ID") + " : "+ myRs.getString("Name") + " : " + myRs.getInt("Salary"));
			}
			
			//Update
			System.out.println("Enter ID you want to update");
			tempID = ip.nextLine();
			System.out.println("ID : Name : Salary\n--------------------");
			ID = ip.nextLine();
			Name = ip.nextLine();
			Salary = ip.nextLine();
			myStmt.executeUpdate("update employee set ID=" + ID + ",Name='" + Name + "' ,Salary=" + Salary + " where ID=" + tempID);
			
			//display
			myRs = myStmt.executeQuery("select * from employee");
			System.out.println("ID : Name : Salary\n--------------------");
			while(myRs.next()) {
				System.out.println(myRs.getInt("ID") + " : "+ myRs.getString("Name") + " : " + myRs.getInt("Salary"));
			}
			
			//delete
			System.out.println("Enter Id you want to delete ");
			tempID = ip.nextLine();
			myStmt.executeUpdate("delete from employee where ID=" + tempID);
			
			//display
			myRs = myStmt.executeQuery("select * from employee");
			System.out.println("ID : Name : Salary\n--------------------");
			while(myRs.next()) {
				System.out.println(myRs.getInt("ID") + " : "+ myRs.getString("Name") + " : " + myRs.getInt("Salary"));
			}
			
			ip.close();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
