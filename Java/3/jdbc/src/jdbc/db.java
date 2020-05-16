package jdbc;

import java.util.*;
import java.io.Console;
import java.sql.*;

public class db {
	public static void main(String[] args) {
		
		
		String usr, pass;
		int choice;
		Scanner scn = new Scanner(System.in);
		//Console console = System.console();
		
		System.out.print("Enter Username : ");
		usr = scn.nextLine();
		System.out.print("Enter Password : ");
		pass = scn.nextLine();
		//pass = String.valueOf(console.readPassword());
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:1234/dbms", usr, pass);
			
			while(true) {
				switch(choice) {
				case 1:
					//Display
				
				case 2:
					//Insert
					
				case 3:
					//Update
					
				case 4:
					//delete
				
				case 5:
					//exit
					
				de
				}
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
}
