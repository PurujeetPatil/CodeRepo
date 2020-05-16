package jdbc;

import java.sql.*;
import java.util.*;

public class a_1 {
	
	static Scanner scn;
	
	@SuppressWarnings("null")
	public static void main(String[] args) throws SQLException {
		
		//Declarations & initializations
		String ch1, ch2 = null;
		int colCount=-1;
		String dbname, tableName;
		Vector<String> input1 = new Vector<String>();
		scn = new Scanner(System.in);
		
		//Server connection
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:1234/", "root", "puru1234");
		Statement q = conn.createStatement();
		
		//Display list of databases
		printS("Available Databases : \n==============\n");
		ResultSet r1 = conn.getMetaData().getCatalogs();
		while(r1.next()) {
			System.out.println(r1.getString(1));
		}
		r1.close();
		
		//Choosing Database
		printS("\n==============\nChoose Database");
		dbname = scn.nextLine();
		q.execute("use " + dbname);
		
		//choosing table
		printS("Available Tables : \n==============\n");
		ResultSet r0 = q.executeQuery("show tables");
		while(r0.next()) {
			System.out.println(r0.getString(1));
		}
		
		printS("\n==============\nChoose Table");
		tableName = scn.nextLine();
//		ResultSet r2 = q.executeQuery("select * from " + tableName);
//		ResultSetMetaData r2md = r2.getMetaData();
//		colCount = r2md.getColumnCount();
		
		//Menu
		while(true) {
			//Display menu
			printS("\n\nMenu\n1 - Display\n2 - Insert\n3 - Update\n4 - Delete\n5 - DatabaseManager\n6 - Exit\nEnter corresponding number...");
			//Accept choice
			ch1 = scn.nextLine();
			
			//changing metadata corresponding to tableName
			ResultSet r2 = q.executeQuery("select * from " + tableName);
			ResultSetMetaData r2md = r2.getMetaData();
			colCount = r2md.getColumnCount();
			
			switch(ch1) {
			case "1":
				//display entries
				for(int i=1; i<=colCount; i++)
					System.out.print(r2md.getColumnName(i) + "\t");
				
				ResultSet r3 = q.executeQuery("select * from " + tableName);
				while(r3.next()) {
					System.out.print("\n");
					for(int i=1; i<=colCount; i++)
						System.out.print(r3.getString(i) + "\t");
				}
				r3.close();
				break;
				
			case "2":
				//insert
				printS("Enter data for following fields");
				for(int i=1; i<=colCount; i++)
					System.out.print(r2md.getColumnName(i) + "\t");
				printS(" ");
				for(int i=0; i<colCount; i++)
					input1.add(scn.nextLine()); //= scn.nextLine();
				
				q.execute(insertQueryMaker(colCount, tableName, input1, r2md));
				input1.clear();
				break;
				
			case "3":
				//update
				//-check if entry exists
				printS("Enter " + r2md.getColumnName(1) + "to search");
				String empID1 = scn.nextLine();
				ResultSet r4 = q.executeQuery("select * from " + tableName + " where " + r2md.getColumnName(1) + " = " + empID1);

				if(r4.next()) {
					
					//-accept new Values
					printS("Entry Exists !!\nEnter data for following fields");
					for(int i=1; i<=colCount; i++)
						System.out.print(r2md.getColumnName(i) + "\t");
					printS(" ");
					for(int i=0; i<colCount; i++)
						input1.add(scn.nextLine()); 
					
					//-execute Query
					q.execute(updateQueryMaker(colCount, tableName, empID1, input1, r2md));
					input1.clear();
					//printS(updateQueryMaker(colCount, tableName, empID, input1, r2md));
				} 
				else {
					printS("Entry doesn't exist !!");
				}
				r4.close();
				break;
				
			case "4":
				//delete
				String empID2;
				printS("Enter " + r2md.getColumnName(1) + " to be deleted");
				empID2 = scn.nextLine();
				ResultSet r5 = q.executeQuery("select * from " + tableName + " where " + r2md.getColumnName(1) + " = " + empID2);
				
				//-check if exists
				if(r5.next()) {
					//-delete entry
					q.executeUpdate("delete from " + tableName + " where " + r2md.getColumnName(1) + "=" + empID2);
					printS("Deleted successfully!!");
				}
				else {
					printS("[ERR] Entry doesn't exist !!");
				}
				r5.close();
				break;
				
			case "5":
				//table menu
				printS("\n\nTable Manager\n1 - Display Tables\n2 - New Table\n3 - Change Table\n4 - Drop Table\n5 - Exit\nEnter corresponding number...");
				ch2 = scn.nextLine();
				switch(ch2) {
				case "1":
					//display table
					printS("Available Tables : \n==============\n");
					r0 = q.executeQuery("show tables");
					while(r0.next()) {
						System.out.println(r0.getString(1));
					}
					printS("\n==============\n");
					break;
					
				case "2":
					//new table
					int colNo;
					printS("Enter table Name : ");
					String query1 = "create table " + scn.nextLine() +"(";
					printS("Enter number of columns");
					colNo = Integer.parseInt(scn.nextLine());
					for(int i=0; i<colNo; i++) {
						printS("Enter Column Name : ");
						query1 = query1 + scn.nextLine() + " ";
						printS("Enter Column datatype");
						query1 = query1 + dataType() + ",";
					}
					query1 = query1.substring(0, query1.length()-1) + ")";
					q.execute(query1);
					
					
					break;
					
				case "3":
					//change table
					boolean flag = false;
					printS("Change Table\n\nEnter Name of table : ");
					String newTableName = scn.nextLine();
					r0 = q.executeQuery("show tables");
					while(r0.next()) {
						if(newTableName.equals(r0.getString(1))) {
							printS("Table Changed Successfully\n");
							tableName = newTableName;
							flag = true;
							break;
						}
					}
					if(!flag)	
						printS("[ERR] Table doesnt exist !");
					break;
					
				case "4":
					//drop table
					boolean flag1 = false;
					printS("Enter Table Name you want to drop : ");					
					String table2drop = scn.nextLine();
					r0 = q.executeQuery("show tables");
					while(r0.next()) {
						if(table2drop.equals(r0.getString(1)) && !table2drop.equals(tableName)) {
							printS("Table dropped Successfully\n");
							q.execute("drop table " + table2drop);
							flag1 = true;
							break;
						}
					}
					if(!flag1)	
						printS("[ERR] Table doesnt exist or table is currently in use, change table first !");
					break;
					
				case "5":
					//exit menu
					break;
					
				default:
					//print wrong choice
					printS("[ERR] Wrong Choice");
					continue;
					
				}
				break;
				
			case "6":
				printS("\nGoodBye");
				r0.close();
				scn.close();
				return;
				//exit
				
			default:
				//print wrong choice
				continue;
			
			}
		}
		//scn.close();
	}
	
	static void printR(ResultSet r) throws SQLException {
		while(r.next()) {
			System.out.println(r);
		}
	}
	
	static void printS(String s) {
		System.out.println(s);
	}
	
	//Insert Query Maker
	static String insertQueryMaker(int colCount, String tableName, Vector<String> input1, ResultSetMetaData r2md) throws SQLException {
		String query;
		query = "insert into " + tableName + " values(";
		for(int i=0; i<colCount; i++) {
			
			if(r2md.getColumnType(i+1) == 12)
				query = query + "'" + input1.elementAt(i) + "', ";
			else
				query = query + input1.elementAt(i) + ", ";
		}
		query = query.substring(0, query.length()-2);
		query = query + ")";
		return query;
	}
	
	static String updateQueryMaker(int colCount, String tableName, String empID, Vector<String> input1, ResultSetMetaData r2md) throws SQLException {
		String query;
		query = "update " + tableName + " set ";
		for(int i=0; i<colCount; i++) {
			
			if(r2md.getColumnType(i+1) == 12)
				query = query + r2md.getColumnName(i+1) + " = " + "'" + input1.elementAt(i) + "', ";
			else
				query = query + r2md.getColumnName(i+1) + " = " + input1.elementAt(i) + ", ";
		}
		query = query.substring(0, query.length()-2);
		query = query + " where " + r2md.getColumnName(1) + "=" + empID;
		return query;
	}
	
	static String dataType() {
		String ch;
		printS("Enter datatype for field\n1 - INT\n2 - VARCHAR(20)\n3 - DATE");
		ch = scn.nextLine();
		while(true) {
			switch(ch) {
			case "1":
				return "int";
				
			case "2":
				return "varchar(20)";
				
			case "3":
				return "date";
			
			default:
				continue;
			}
		}	
	}
}
