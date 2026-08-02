
package com.Project1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.regex.*;


public class AdminEmployee{
	
	Connection con = null;
	Statement st = null;
	private String currentUsername;

	
	
	void createLoginConnection() throws SQLException {
		this.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaLoginDB","root","MYPASSWORD");
		this.st = con.createStatement();
	}
	
	
	
	boolean validateLogin(String username, String password) throws SQLException {
		Scanner scanner = new Scanner(System.in);
		String query = "Select role from loginDetails where username = '"+username+"' and password = '"+password+"' ";
		ResultSet rs = this.st.executeQuery(query);
		
		this.currentUsername = username; 
		
		
		if(rs.next()) {
			String role = rs.getString("role");
			
			
			if(role.equals("admin")) {
				System.out.println("Welcome To Admin Panel");
				showAdminMenu(scanner);
			} 
			
			if(role.equals("employee")) {
				System.out.println("Welcome To Employee Dashboard");
				showEmployeeMenu(scanner,username);
			}
			
			return true;
		} else {
			System.out.println("Invalid Username/Password");
			return false;
		}
	}
	
	
	
	
	public static boolean isValidEmail(String email) {
		String email_regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9-]+\\.[A-Za-z]{2,6}$";
		return Pattern.matches(email_regex, email);
	}
	
	public static boolean isValidMobile(String mobile) {
		String mobile_regex = "^[6-9]\\d{9}$";
		return Pattern.matches(mobile_regex,mobile);
	}
	
	public static boolean isValidDOB(String dob) {
		String dob_regex = "^(0?[1-9]|[12][0-9]|3[01])-(0?[1-9]|1[0-2])-(19|20)\\d{2}$";
		return Pattern.matches(dob_regex,dob);
	}
	
	
	
	
	
	void addEmployee(Scanner scanner) throws SQLException {
		
		int empID;
		
		while(true) {
			try {
				System.out.print("Enter Employee ID: ");
				empID = scanner.nextInt();
				scanner.nextLine();
				if(empID>0) {
					break;
				}
				System.out.println("ID must be positive.");
			}
			catch(Exception e) {
				System.out.println("Invalid ID. Please enter a number.");
				scanner.nextLine();
			}
		}
		
		
		/*------------------------------------------------------------------*/
		/*------------------------------------------------------------------*/
		

		System.out.print("Enter Employee Name: ");
		String empName = scanner.nextLine().trim();
		
		
		/*------------------------------------------------------------------*/
		/*------------------------------------------------------------------*/
		
		
		double empSalary;
		while(true) {
			try {
				System.out.print("Enter Employee Salary: ");
				empSalary = scanner.nextDouble();
				scanner.nextLine();
				if(empSalary>0) {
					break;
				}
				System.out.println("Salary must be greater than zero.");
			}
			catch(Exception e) {
				System.out.println("Invalid input. Please enter a numeric value.");
				scanner.nextLine();
			}
		}
		
		
		/*------------------------------------------------------------------*/
		/*------------------------------------------------------------------*/
		
		
		
		System.out.print("Enter Employee Designation: ");
		String empDesignation = scanner.nextLine().trim(); 
		
		
		/*------------------------------------------------------------------*/
		/*------------------------------------------------------------------*/
		
		
		String empEmail;
		while(true) {
			System.out.print("Enter Employee Email ID: ");
			empEmail = scanner.nextLine().trim();
			if(isValidEmail(empEmail)) {
				break;
			}
			System.out.println("Invalid email format. Please try again.");
		}
		
		
		/*------------------------------------------------------------------*/
		/*------------------------------------------------------------------*/
		
		
		String empMob;
		while(true) {
			System.out.print("Enter Employee Mobile Number: ");
			empMob = scanner.nextLine().trim();
			if(isValidMobile(empMob)) {
				break;
			}
			System.out.println("Invalid mobile number. Must be 10 digits starting with 6-9 ");
		}
		
		
		/*------------------------------------------------------------------*/
		/*------------------------------------------------------------------*/
		
		
		System.out.print("Enter Employee Address: ");
		String empAddress = scanner.nextLine().trim();
		
		
		/*------------------------------------------------------------------*/
		/*------------------------------------------------------------------*/
		
		
		String empDOB;
		while(true) {
			System.out.print("Enter Employee DOB:(DD-MM-YYYY): ");
			empDOB = scanner.nextLine().trim();
			if(isValidDOB(empDOB)) {
				break;
			}
			System.out.println("Invalid DOB format. Please Use DD-MM-YYY.");
		}
		
		
		/*------------------------------------------------------------------*/
		/*------------------------------------------------------------------*/
		
		String createPassword;
		String confirmPassword;
		
		while(true) {
			System.out.print("Create Password: ");
			createPassword = scanner.nextLine().trim();
			
			System.out.print("Confirm Password: ");
			confirmPassword = scanner.nextLine().trim();
			
			if(createPassword.equals(confirmPassword)) {
				System.out.println("Password Confirm.");
				break;
			} else {
				System.out.println("Passwords do not match. Please try again!!!");
			}
			
		}
		
		/*------------------------------------------------------------------*/
		/*------------------------------------------------------------------*/
		
		String role = "employee";
		
		
		/*------------------------------------------------------------------*/
		/*------------------------------------------------------------------*/
		
		

		
		String sql1 = "insert into employeeDetails values(?,?,?,?,?,?,?,?)";
		PreparedStatement stmt1 = con.prepareStatement(sql1);
		
		
		String sql2 = "insert into loginDetails (username,password,role) values(?,?,?)";
		PreparedStatement stmt2 = con.prepareStatement(sql2);
		
		stmt1.setInt(1,empID);
		stmt1.setString(2,empName);
		stmt1.setDouble(3, empSalary);
		stmt1.setString(4,empDesignation);
		stmt1.setString(5, empEmail);
		stmt1.setString(6,empMob);
		stmt1.setString(7, empAddress);
		stmt1.setString(8, empDOB);
		
		
		stmt2.setString(1, empName);
		stmt2.setString(2, confirmPassword);
		stmt2.setString(3, role);
		
		stmt1.executeUpdate();
		stmt2.executeUpdate();
		
		System.out.println("Employee added successfully!!! ");
		
		
		/*-------------------------------------------------------*/
		/*-------------------------------------------------------*/
		/*-------------------------------------------------------*/
		
		System.out.println();
		System.out.println("----------------------------------");
		System.out.println("----------------------------------");
		System.out.println("Enter ''yes'' or ''y'' for Admin Menu!!!");
		System.out.println("Or enter anything to EXIT!!! ");
		String adminContinue = scanner.next();	
		System.out.println("----------------------------------");
		System.out.println("----------------------------------");
		
		if(adminContinue.equalsIgnoreCase("yes") 
				|| 
		   adminContinue.equalsIgnoreCase("y")) {
			showAdminMenu(scanner);
		}
		
		
		
	}
	
	
	
	
	
	
	
	void retriveEmpDetails(Scanner scanner) throws SQLException {
		System.out.println("Please Enter A Choice: ");
		System.out.println("1. Retrieve All Employees");
		System.out.println("2. Search By Employee ID");
		System.out.println("3. Search By Employee Name");
		
		int choice = scanner.nextInt();
		scanner.nextLine();
		
		ResultSet rs = null;
		
		if(choice == 1) {
			System.out.println("----------------------");
			rs = this.st.executeQuery(" select * from employeeDetails ");	
		} else if(choice == 2) {
			System.out.print("Enter Employee ID: ");
			int empID = scanner.nextInt();
			rs = this.st.executeQuery(" select * from employeeDetails where empID = "+empID);
		} else if (choice == 3) {
			System.out.print("Enter Employee Name: ");
			String empName = scanner.nextLine().trim();
			rs = this.st.executeQuery("select * from employeeDetails where empName LIKE '"+empName+"%'");
		} else {
			System.out.println("Invalid Choice.");
			return;
		}
		
		System.out.println();
		
		while(rs.next()){
			System.out.println("ID: "+rs.getInt(1));
			System.out.println("Name: "+rs.getString(2));
			System.out.println("Salary: "+rs.getDouble(3));
			System.out.println("Department: "+rs.getString(4));
			System.out.println("Email: "+rs.getString(5));
			System.out.println("Mobile: "+rs.getString(6));
			System.out.println("Address: "+rs.getString(7));
			System.out.println("DOB: "+rs.getString(8));
			
			System.out.println("----------------------");
			System.out.println();
			
		}
		
		/*-------------------------------------------------------*/
		/*-------------------------------------------------------*/
		/*-------------------------------------------------------*/
		
		System.out.println();
		System.out.println("----------------------------------");
		System.out.println("----------------------------------");
		System.out.println("Enter ''yes'' or ''y'' for Admin Menu!!!");
		System.out.println("Or enter anything to EXIT!!! ");
		String adminContinue = scanner.next();	
		System.out.println("----------------------------------");
		System.out.println("----------------------------------");
		
		if(adminContinue.equalsIgnoreCase("yes") || adminContinue.equalsIgnoreCase("y")) {
			showAdminMenu(scanner);
		}
		
		rs.close();
	}
	
	
	
	
	
	void updateEmpDetails(Scanner scanner) throws SQLException {
		System.out.println("Enter Employee ID: ");
		int id = scanner.nextInt();
		scanner.nextLine();
		
		ResultSet rs = this.st.executeQuery("select * from employeeDetails where empID = "+id);
		
		if(rs.next()) {
			
			String updateQuery = "update employeeDetails set ";
			boolean hasUpdates = false;
			
			System.out.println();
			System.out.println("To Update Enter Value, (to skip, --> leave blank or Enter --> 'none') ");
			System.out.println("----------------------------------------------------------------------");
			System.out.println("----------------------------------------------------------------------");
			System.out.println();
			
			
			int updateEmpID;
			while(true) {
				try {
					System.out.print("Enter Updated EMP-ID: ");
					String idInput = scanner.nextLine().trim();
					if(idInput.isEmpty() 
							|| 
							idInput.equalsIgnoreCase("none")) {
						System.out.println("Salary update skipped.");
						break;
					}
					
					updateEmpID = Integer.parseInt(idInput);
					if(updateEmpID>0) {
						updateQuery += " empID = "+updateEmpID+", ";
						hasUpdates = true;
						break;
						} else {
							System.out.println("Employee ID must be greater than zero.");
						}
					
				} catch(Exception e) {
					System.out.println("Invalid input. Please enter a numeric value.");
					scanner.nextLine();
				}
			}
			
			
			
			/*------------------------------------------------------------------*/
			/*------------------------------------------------------------------*/
			
			
			System.out.print("Enter Updated Name: ");
			String updateName = scanner.nextLine().trim();
			if(!updateName.isEmpty() && !updateName.equalsIgnoreCase("none")) {
				updateQuery += "empName = '" +updateName+"', ";
				hasUpdates = true;
			} else {
			    System.out.println("Name update skipped.");
			}
			
			/*------------------------------------------------------------------*/
			/*------------------------------------------------------------------*/
			
			
		double updateSalary;
		while(true) {
			try {
				System.out.print("Enter Updated Salary: ");
				String salaryInput = scanner.nextLine().trim();
				if(salaryInput.isEmpty() || salaryInput.equalsIgnoreCase("none") ) {
					System.out.println("Salary update skipped.");
					break;
					}
				
				updateSalary = Double.parseDouble(salaryInput);
				if(updateSalary>0) {
					updateQuery += " empSalary = "+updateSalary+", ";
					hasUpdates = true;
					break;
					} else {
						System.out.println("Salary must be greater than zero.");
					}
				
				}
			
				 catch(Exception e) {
					 System.out.println("Invalid input. Please enter a numeric value.");
					 scanner.nextLine();
				}
			}
			
			
			
			/*------------------------------------------------------------------*/
			/*------------------------------------------------------------------*/
			
			
			System.out.print("Enter Updated Designation: ");
			String updateDesignation = scanner.nextLine().trim();
			if(!updateDesignation.isEmpty() && !updateDesignation.equalsIgnoreCase("none")) {
				updateQuery += "empDesignation = '" +updateDesignation+"', ";
				hasUpdates = true;
			} else {
			    System.out.println("Designation update skipped.");
			}
			
			
			/*------------------------------------------------------------------*/
			/*------------------------------------------------------------------*/
			
			
			String updateEmail;
			while(true) {
				System.out.print("Enter Updated Email: ");
				updateEmail = scanner.nextLine().trim();
				if(updateEmail.isEmpty() || updateEmail.equalsIgnoreCase("none")) {
					System.out.println("Email update skipped.");
					break;
				}
				
				if(isValidEmail(updateEmail)) {
					updateQuery += " empEmail = '"+updateEmail+"', ";
					hasUpdates = true;
					break;
				} else {
					System.out.println("Invalid email format. Please try again.");
				}
			}
		
			
			
			
			/*------------------------------------------------------------------*/
			/*------------------------------------------------------------------*/
			
			
			String updateMob;
			while(true) {
				System.out.print("Enter Updated Mobile Number: ");
				updateMob = scanner.nextLine().trim();
				if(updateMob.isEmpty() || updateMob.equalsIgnoreCase("none")) {
					System.out.println("Mobile update skipped.");
					break;
				}
				if(isValidMobile(updateMob)) {
					updateQuery += " empMob = '"+updateMob+"', ";
					hasUpdates = true;
					break;
						
				} else {
					System.out.println("Invalid mobile number. Must be 10 digits starting with 6-9 ");
				}
			}
	
	
			

			/*------------------------------------------------------------------*/
			/*------------------------------------------------------------------*/
			
			
			
			 System.out.print("Enter Updated Address: ");
			 String updateAdd = scanner.nextLine().trim();
			 
			 if (!updateAdd.isEmpty() && !updateAdd.equalsIgnoreCase("none")) {
				 updateQuery += "empAddress = '" + updateAdd + "', ";
		         hasUpdates = true;
		     } else {
		    	 System.out.println("Address update skipped.");
		     }
			
			
			/*------------------------------------------------------------------*/
			/*------------------------------------------------------------------*/
			
			
			
		    String updateDOB;
		    while(true) {
		    	System.out.print("Enter Employee DOB:(DD-MM-YYYY): ");
			    updateDOB = scanner.nextLine().trim();
				if(updateDOB.isEmpty() || updateDOB.equalsIgnoreCase("none")) {
					System.out.println("DOB update skipped.");
					break;
				}
				if(isValidDOB(updateDOB)) {
					updateQuery += "empDOB = '" + updateDOB + "', ";
			        hasUpdates = true;
			        break;
						
				} else {
					System.out.println("Invalid DOB format. Please Use DD-MM-YYY.");
				}
		    }
	
			
		     
			/*------------------------------------------------------------------*/
			/*------------------------------------------------------------------*/
			
		    
		     
		    if(hasUpdates) {
		    	updateQuery = updateQuery.substring(0,updateQuery.length() -2);
		    	updateQuery += " where empID = " + id;
		    	this.st.executeUpdate(updateQuery);
		    	System.out.println("Employee details updated successfully.");
		    } else {
		    	System.out.println("No Valid Fields Entered!!! Nothing Updated!!!");
		    }
		     
		     
		    /*------------------------------------------------------------------*/
		    /*------------------------------------------------------------------*/
			
		} else {
			System.out.println("Invalid Employee ID!!!");
			rs.close();
		}
		
		
		/*-------------------------------------------------------*/
		/*-------------------------------------------------------*/
		/*-------------------------------------------------------*/
		
		System.out.println();
		System.out.println("----------------------------------");
		System.out.println("----------------------------------");
		System.out.println("Enter ''yes'' or ''y'' for Admin Menu!!!");
		System.out.println("Or enter anything to EXIT!!! ");
		String adminContinue = scanner.next();	
		System.out.println("----------------------------------");
		System.out.println("----------------------------------");
		
		if(adminContinue.equalsIgnoreCase("yes") || adminContinue.equalsIgnoreCase("y")) {
			showAdminMenu(scanner);
		}
		
	}
	
	
	
	
	void deleteEmpDetails(Scanner scanner) throws SQLException {
		System.out.print("Enter ID: ");
		int id = scanner.nextInt();
		scanner.nextLine();
		
		ResultSet rs = this.st.executeQuery("select * from employeeDetails where empID = "+id);
		
		if(rs.next()) {
			rs.close();
			this.st.execute(" delete from employeeDetails where empID = "+id+" ");
			System.out.println("Record Deleted");
		} else {

	        System.out.println("Employee ID not found. No record deleted.");
			rs.close();
		}
		
		
		/*-------------------------------------------------------*/
		/*-------------------------------------------------------*/
		/*-------------------------------------------------------*/
		
		System.out.println();
		System.out.println("----------------------------------");
		System.out.println("----------------------------------");
		System.out.println("Enter ''yes'' or ''y'' for Admin Menu!!!");
		System.out.println("Or enter anything to EXIT!!! ");
		String adminContinue = scanner.next();	
		System.out.println("----------------------------------");
		System.out.println("----------------------------------");
		
		if(adminContinue.equalsIgnoreCase("yes") || adminContinue.equalsIgnoreCase("y")) {
			showAdminMenu(scanner);
		}
		
	}
	
	
	
	
	void showAdminMenu(Scanner scanner) throws SQLException {
		boolean validInput = false;
		
		while(!validInput) {
			System.out.println();
			System.out.println("Admin Options: ");
			System.out.println("1. Add Employee");
			System.out.println("2. Retrive Empolyee Details");
			System.out.println("3. Update Employee Details");
			System.out.println("4. Delete Employee Details");
			System.out.println("5. Exit");
			
			try {
				System.out.println("Enter your choice: ");
				int choice = scanner.nextInt();
				scanner.nextLine();
				
				switch(choice) {
					case 1:
						addEmployee(scanner);
						validInput = true;
						break;
					case 2:
						retriveEmpDetails(scanner);
						validInput = true;
						break;
					case 3:
						updateEmpDetails(scanner);
						validInput = true;
						break;
					case 4:
						deleteEmpDetails(scanner);
						validInput = true;
						break;
					case 5:
						System.out.println("Exiting Admin Panel...");
						validInput = true;
						break;
					default:
						System.out.println("Invalid choice. Please enter 1 or 2.");
				}
				
			}catch(Exception e) {
				System.out.println("Exception!!! Invalid input!!! ");
				scanner.nextLine();
			}
		}
		
	}
	
	
	
	
	
	void empRetrieves(Scanner scanner) throws SQLException {
		System.out.println("Please Enter A Choice: ");
		System.out.println("1. Search By Employee ID");
		System.out.println("2. Search By Employee Name");
		
		int choice = scanner.nextInt();
		scanner.nextLine();
		
		ResultSet rs = null;
		
		if (choice == 1) {
			System.out.print("Enter Employee ID: ");
			int empID = scanner.nextInt();
			rs = this.st.executeQuery(" select * from employeeDetails where empID = "+empID);
		} else if (choice == 2) {
			System.out.print("Enter Employee Name: ");
			String empName = scanner.nextLine().trim();
			rs = this.st.executeQuery("select * from employeeDetails where empName LIKE '"+empName+"%'");
		} else {
			System.out.println("Invalid Choice.");
			return;
		}
		
		System.out.println();
		
		while(rs.next()){
			System.out.println("ID: "+rs.getInt(1));
			System.out.println("Name: "+rs.getString(2));
			System.out.println("Email: "+rs.getString(5));
			System.out.println("Mobile: "+rs.getString(6));
			System.out.println("Address: "+rs.getString(7));
			System.out.println("DOB: "+rs.getString(8));
			
			System.out.println("----------------------");
			System.out.println();
			
		}
		
		
		/*-------------------------------------------------------*/
		/*-------------------------------------------------------*/
		/*-------------------------------------------------------*/
		
		System.out.println();
		System.out.println("----------------------------------");
		System.out.println("----------------------------------");
		System.out.println("Enter ''yes'' or ''y'' for Employee Menu!!!");
		System.out.println("Or enter anything to EXIT!!! ");
		String empContinue = scanner.next();	
		System.out.println("----------------------------------");
		System.out.println("----------------------------------");
		
		if(empContinue.equalsIgnoreCase("yes") || empContinue.equalsIgnoreCase("y")) {
			showEmployeeMenu(scanner,currentUsername);
		}
		
		
		rs.close();
	}
	
	
	
	
	void empUpdates(Scanner scanner,String username) throws SQLException {
		
		ResultSet rs = this.st.executeQuery("select * from employeeDetails where empName = '"+username+"' ");
		
		if(rs.next()) {
			
			String updateQuery = "update employeeDetails set ";
			boolean hasUpdates = false;
			
			System.out.println();
			System.out.println("To Update Enter Value, (to skip, --> leave blank or Enter --> 'none') ");
			System.out.println("----------------------------------------------------------------------");
			System.out.println("----------------------------------------------------------------------");
			System.out.println();
			
			
			System.out.print("Enter Updated Name: ");
			String updateName = scanner.nextLine().trim();
			if(!updateName.isEmpty() && !updateName.equalsIgnoreCase("none")) {
				updateQuery += "empName = '" +updateName+"', ";
				hasUpdates = true;
			} else {
			    System.out.println("Name update skipped.");
			}
			
			/*------------------------------------------------------------------*/
			/*------------------------------------------------------------------*/
			
			
			String updateEmail;
			while(true) {
				System.out.print("Enter Updated Email: ");
				updateEmail = scanner.nextLine().trim();
				if(updateEmail.isEmpty() || updateEmail.equalsIgnoreCase("none")) {
					System.out.println("Email update skipped.");
					break;
				}
				
				if(isValidEmail(updateEmail)) {
					updateQuery += " empEmail = '"+updateEmail+"', ";
					hasUpdates = true;
					break;
				} else {
					System.out.println("Invalid email format. Please try again.");
				}
			}
		
			
			
			
			/*------------------------------------------------------------------*/
			/*------------------------------------------------------------------*/
			
			
			String updateMob;
			while(true) {
				System.out.print("Enter Updated Mobile Number: ");
				updateMob = scanner.nextLine().trim();
				if(updateMob.isEmpty() || updateMob.equalsIgnoreCase("none")) {
					System.out.println("Mobile update skipped.");
					break;
				}
				if(isValidMobile(updateMob)) {
					updateQuery += " empMob = '"+updateMob+"', ";
					hasUpdates = true;
					break;
						
				} else {
					System.out.println("Invalid mobile number. Must be 10 digits starting with 6-9 ");
				}
			}
	
	
			

			/*------------------------------------------------------------------*/
			/*------------------------------------------------------------------*/
			
			
			
			 System.out.print("Enter Updated Address: ");
			 String updateAdd = scanner.nextLine().trim();
			 
			 if (!updateAdd.isEmpty() && !updateAdd.equalsIgnoreCase("none")) {
				 updateQuery += "empAddress = '" + updateAdd + "', ";
		         hasUpdates = true;
		     } else {
		    	 System.out.println("Address update skipped.");
		     }
			
			
			/*------------------------------------------------------------------*/
			/*------------------------------------------------------------------*/
			
			
			
		    String updateDOB;
		    while(true) {
		    	System.out.print("Enter Employee DOB:(DD-MM-YYYY): ");
			    updateDOB = scanner.nextLine().trim();
				if(updateDOB.isEmpty() || updateDOB.equalsIgnoreCase("none")) {
					System.out.println("DOB update skipped.");
					break;
				}
				if(isValidDOB(updateDOB)) {
					updateQuery += "empDOB = '" + updateDOB + "', ";
			        hasUpdates = true;
			        break;
						
				} else {
					System.out.println("Invalid DOB format. Please Use DD-MM-YYY.");
				}
		    }
	
			
		     
			/*------------------------------------------------------------------*/
			/*------------------------------------------------------------------*/
			
		    
		     
		    if(hasUpdates) {
		    	updateQuery = updateQuery.substring(0,updateQuery.length() -2);
		    	updateQuery += " where empName = '"+username+"' ";
		    	this.st.executeUpdate(updateQuery);
		    	System.out.println("Employee details updated successfully.");
		    } else {
		    	System.out.println("No Valid Fields Entered!!! Nothing Updated!!!");
		    }
		     
		     
		    /*------------------------------------------------------------------*/
		    /*------------------------------------------------------------------*/
			
		    String sqlEmpName = "update loginDetails set username = '"+updateName+"'  where username = '"+username+"' ";
		    this.st.execute(sqlEmpName);
		    
			
			/*------------------------------------------------------------------*/
		    /*------------------------------------------------------------------*/
			
		    
		    
		} else {
			System.out.println("Invalid Employee ID!!!");
			rs.close();
		}
		
		
		
		/*-------------------------------------------------------*/
		/*-------------------------------------------------------*/
		/*-------------------------------------------------------*/
		
		System.out.println();
		System.out.println("----------------------------------");
		System.out.println("----------------------------------");
		System.out.println("Enter ''yes'' or ''y'' for Employee Menu!!!");
		System.out.println("Or enter anything to EXIT!!! ");
		String empContinue = scanner.next();	
		System.out.println("----------------------------------");
		System.out.println("----------------------------------");
		
		if(empContinue.equalsIgnoreCase("yes") || empContinue.equalsIgnoreCase("y")) {
			showEmployeeMenu(scanner,currentUsername);
		}
		
		
	}
	
	
	
	
	void empPassword(Scanner scanner, String username) throws SQLException {
		
		ResultSet rs = this.st.executeQuery("select password from loginDetails where username = '"+username+"' ");
		
		System.out.print("Enter Current Password: ");
		String currPassword = scanner.nextLine().trim();
		
		if(rs.next()) {
			String passwordDB = rs.getString("password");
			
			if(currPassword.equals(passwordDB)) {
				String createPassword;
				String confirmPassword;
				
				while(true) {
					System.out.print("Create New Password: ");
					createPassword = scanner.nextLine().trim();
					
					System.out.print("Confirm New Password: ");
					confirmPassword = scanner.nextLine().trim();
					
					if(createPassword.equals(confirmPassword)) {
						 String sqlEmpPassword = "update loginDetails set password = '"+confirmPassword+"'  where username = '"+username+"' ";
						 this.st.execute(sqlEmpPassword);
						System.out.println("Password Confirmed.");
						break;
					} else {
						System.out.println("Passwords do not match. Please try again!!!");
					}
					
				}
			} 
		
		} else {
			System.out.println("Invalid Password!!!");
		}
		
		
		/*-------------------------------------------------------*/
		/*-------------------------------------------------------*/
		/*-------------------------------------------------------*/
		
		System.out.println();
		System.out.println("----------------------------------");
		System.out.println("----------------------------------");
		System.out.println("Enter ''yes'' or ''y'' for Employee Menu!!!");
		System.out.println("Or enter anything to EXIT!!! ");
		String empContinue = scanner.next();	
		System.out.println("----------------------------------");
		System.out.println("----------------------------------");
		
		if(empContinue.equalsIgnoreCase("yes") || empContinue.equalsIgnoreCase("y")) {
			showEmployeeMenu(scanner,currentUsername);
		}
		
		
	}
	
	
	
	void showEmployeeMenu(Scanner scanner,String username) throws SQLException {
		boolean validInput = false;
		
		while(!validInput) {
			System.out.println();
			System.out.println("Employee Options: ");
			System.out.println("1. Retrieve Employee Records");
			System.out.println("2. Update Your Records"); 
			System.out.println("3. Change Your Password"); 
			System.out.println("4. Exit");
			
			try {
				System.out.println("Enter your choice: ");
				int choice = scanner.nextInt();
				scanner.nextLine();
				
				switch(choice) {
					case 1:
						empRetrieves(scanner);
						validInput = true;
						break;
					case 2:
						empUpdates(scanner,username);
						validInput = true;
						break;
					case 3:
						empPassword(scanner,username);
						validInput = true;
						break;
					case 4:
						System.out.println("Exiting Employee Dashboard...");
						validInput = true;
						break;
					default:
						System.out.println("Invalid choice. Please enter 1 or 2.");
				}
				
			}catch(Exception e) {
				System.out.println("Exception!!! Invalid input!!! ");
				scanner.nextLine();
			}
		}
		
	}
	
	
	
	
	
	void closeLoginConnection() throws SQLException {
		con.close();
		st.close();
	}
	
	
	
	

	
	public static void main(String[] args) throws SQLException {
		Scanner scanner = new Scanner(System.in);
		
		AdminEmployee jdbc = new AdminEmployee();
		
		jdbc.createLoginConnection();
		
		boolean loggedIn = false;
		
		while(!loggedIn) {
			System.out.print("Enter username: ");
			String username = scanner.next().trim();
			
			System.out.print("Enter password: ");
			String password = scanner.next().trim();
			
			loggedIn = jdbc.validateLogin(username,password);
			
		}
		
		jdbc.closeLoginConnection();
		
		scanner.close();
	}
}


