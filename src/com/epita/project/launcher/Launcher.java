package com.epita.project.launcher;

import java.sql.SQLException;

import com.epita.project.management.functionality.EpitrelloDataServerice;
import com.epita.project.service.transaction.DatabaseDAO;

public class Launcher {

	public static void main(String[] args) throws SQLException, Exception {
 
		

		EpitrelloDataServerice dataserverice = new EpitrelloDataServerice();
		// creation of the data base 
		DatabaseDAO dao = new DatabaseDAO();
		dao.createDatabase();

		System.out.println( dataserverice.addUser("Thomas") ); // addUser(string username)
		System.out.println( dataserverice.addUser("AmirAli") );
		System.out.println( dataserverice.addUser("Rabih") );

		System.out.println( dataserverice.addList("Code") ); //addList(string name)
		System.out.println( dataserverice.addList("Description") );
		System.out.println( dataserverice.addList("Misc") );

	    
		 System.out.println( dataserverice.addTask("Code", "Do Everything", 12, 1, "Write the whole code") ); 
		    /* addTask(string list, string name, unsigned int estimatedTime, unsigned int priority, string description) */
		 System.out.println( dataserverice.editTask("Do Everything", 12, 10, "Write the whole code") );
		    /* editTask(string task, unsigned int estimatedTime, unsigned int priority, string description) */
		 
		 System.out.println( dataserverice.assignTask("Do Everything", "Rabih") ); // assignTask(string task, string user)
		 System.out.println( dataserverice.printTask("Do Everything") ); // printTask(string task)
		 
		 
		 System.out.println( dataserverice.addTask("Code", "Destroy code formatting", 1, 2, "Rewrite the whole code in a worse format") );
		 System.out.println( dataserverice.assignTask("Destroy code formatting", "Thomas") );

	     System.out.println( dataserverice.addTask("Description", "Write Description", 3, 1, "Write the damn description") );
	     System.out.println( dataserverice.assignTask("Write Description", "AmirAli") );
	     System.out.println( dataserverice.addTask("Misc", "Upload Assignment", 1, 1, "Upload it") );

	     System.out.println( dataserverice.completeTask("Do Everything") ); // completeTask(string task)
	     
	     System.out.println( dataserverice.printUsersByPerformance() );
	     System.out.println( dataserverice.printUsersByWorkload() );
	     System.out.println( dataserverice.printUnassignedTasksByPriority() );
	     
		 System.out.println( dataserverice.deleteTask("Upload Assignment") ); // deleteTask(string task)
		 System.out.println( dataserverice.printAllUnfinishedTasksByPriority() );
			
		 System.out.println( dataserverice.addTask("Misc", "Have fun", 10, 2, "Just do it") );
		 System.out.println( dataserverice.moveTask("Have fun","Code") ); // moveTask(string task, string list)
		 System.out.println( dataserverice.printTask("Havefun") );
			
		
		 System.out.println( dataserverice.printList("Code") );
		 System.out.println( dataserverice.printAllLists() );
		
		 System.out.println( dataserverice.printUserTasks("AmirAli") ); 
		 System.out.println( dataserverice.printUnassignedTasksByPriority());
		 System.out.println( dataserverice.printAllUnfinishedTasksByPriority() );
		
	    //write  output into a file, that is handle by the class FitetransactionDAO file is named saveOutput.txt
	    
	    //Save user into database 
	    
	    //part handle by user insertio DAO 
		
		
	}}
