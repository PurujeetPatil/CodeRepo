package jdbc;

import java.sql.*;
import java.util.Vector;


public class test1 {
	
	public static void main(String[] args) {
		try {
	        //Class.forName(JDBCDriver); //Register JDBC Driver
	
	        System.out.println("Creating a connection...");
	        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:1234", "root", "puru1234"); //Open a connection
	        Statement q = conn.createStatement();
	        
	        ResultSet resultSet = conn.getMetaData().getCatalogs();
	        
	        Vector<String> input = new Vector<String>();
	        
	
	        while (resultSet.next()) {
	
	          String databaseName = resultSet.getString(1);
	          //System.out.println(databaseName);
	            if(databaseName.equals("dbms")){
	                System.out.println("Exists");
	                q.execute("use dbms");
	                int colCount;
	                ResultSet result = q.executeQuery("select *from employee");
	                ResultSetMetaData rsmd = result.getMetaData();
//	                System.out.println(rsmd.getColumnName(1));
	                colCount = rsmd.getColumnCount();
	                System.out.println(rsmd.getColumnType(2));
	                for(int i=1; i<=colCount; i++)
						System.out.print(rsmd.getColumnName(i) + "\t");
	                while(result.next()) {
	                	System.out.print("\n");
						for(int i=1; i<=colCount; i++)
							System.out.print(result.getString(i) + "\t");
					}
	                
//	                String query, dbname="employee";
//	                String[] input = {"123", "qwe", "098"};
//					query = "insert into " + dbname + " values(";
//					for(int i=0; i<colCount; i++) {
//						//System.out.println(query);
//						//query.concat(input[i] + ",");
//						query = query + input[i] + ", ";
//					}
//					query = query.substring(0, query.length()-2);
//					query = query + ")";
//					System.out.println(query);
//	                ResultSet myRs = q.executeQuery("select * from employee");
//	    			System.out.println("ID : Name : Salary\n--------------------");
//	    			while(myRs.next()) {
//	    				System.out.println(myRs.getInt("ID") + " : "+ myRs.getString("Name") + " : " + myRs.getInt("Salary"));
//	    			}
	            }
	        }
	        resultSet.close();
	
	    }
	    catch(Exception e){
	        e.printStackTrace();
	    }
	}
}
