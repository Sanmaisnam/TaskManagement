package com.epita.project.management.functionality;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.epita.project.service.transaction.FiletransactionDAO;
import com.epita.project.service.transaction.UserInsertion;

public class EpitrelloDataServerice {
	String nextline ="\n";
	int temp , temp1;
	String sstemp ;
	//file object 
	FiletransactionDAO fl = new FiletransactionDAO() ;
	//mapping task and user , to have total amount of time 
	Map< String,Integer> mapping =  new HashMap<>(); 
	//object of the class userinsertion 
	UserInsertion ui = new UserInsertion();
	// collection of time 
	ArrayList<Integer> timerCollector =  new  ArrayList<>();
	
	// collection of object for every task 
	ArrayList<TaskManager> collect = new ArrayList<>();
	
	// collection of every List
	ArrayList<String>  listEle = new ArrayList<>();
	
	//collection of user 
	ArrayList<String> userlist = new ArrayList<>();
	
	// collecting all user  name , saving it into an array of object 
	public String addUser(String username) throws SQLException, Exception {	
		
		if (userlist.contains(username)) {
			return username+" User already exist" ;
		}
		else 
		{
		userlist.add(username);		
		ui.insertIntoDB(username);
		}
		return "success " ; 
	}
	
	//collection of every list name 
	public  String addList(String listname) {
		 
		 if (listEle.contains(listname)) {

				return listname+" list already exist" ;
			}
		 else {
			 listEle.add(listname);
		 }
		 		
		 return "success" ; 
	 }
	
	// Adding task by creating object for every element added 
	public  String addTask (String listname , String taskname , int time , int priority, String description ) {
		// time and priority needs to be positive 
		if (time < 0 || priority < 0) {
			return "time and priority needs to be positive" ;
		}
		// checking if the list exists  
		if (!listEle.contains(listname)) {
			
			return "list "+listname+" does not exist";
		}
		
		// check if a passed task is not already define 
		for (TaskManager tm : collect) {
			if(tm.getTaskname().equals(taskname) ) {
				return "task "+taskname+" already exist";
		}
		}
		
		// if every other condition  fails then the list task is created  
		collect.add(new TaskManager(listname, taskname, time, priority, description));	
			return "success" ;
		}
	
	// modifying a task property 
	public  String editTask(String taskname , int time , int priority, String description) {
		
		for (TaskManager tm : collect) {
			if(tm.getTaskname().equals(taskname) ) {
				tm.editTask(taskname, time, priority, description);
				return "modified, task success" ;
			}
		}
		
		return "unsucessfull modification, "+taskname+" task does not exist";
	}
	
	
	//Assigning task 
	public  String assignTask(String taskname , String username) {
		if(!userlist.contains(username)) {
			return "unsuccess , "+username+" does not exist in the system";
		}
		for (TaskManager tm : collect) {
			if(tm.getTaskname().equals(taskname) ) {
				tm.assignTask(username);
			}
		}
		
		return "success, assigned to "+username ;
	}
	
	//print selecting task definition
	// print user by performance 
	public String printUsersByPerformance() throws IOException {
		
		//clear the map in case of previous used 
		mapping.clear();
		//clear the time collector in case of a previous used
		timerCollector.clear();
		// collecting task time , where user is assigned 
		for (TaskManager tm :collect) {
			if (tm.getUsername() != null ) {
				timerCollector.add(tm.getEstimatedtime());
			}
		}
		Collections.sort(timerCollector);// sort the time 
		Collections.reverse(timerCollector); // reverse the list to have descending order
		// display all user according to their performance and time 
		groupingTimeOfTask();
		//  display user by total performance
		final Map<String, Integer> treeMap = new TreeMap<>(mapping);
		for (Entry<String, Integer> entry : treeMap.entrySet()) {
			String k = entry.getKey();
			int v = entry.getValue();
			System.out.println(k + ", total performance: " + v);
			fl.write(k + ", total performance: " + v);
		}
		return nextline ;		
	}

	public void groupingTimeOfTask() {
		for(Integer i : timerCollector ) {
			for (TaskManager tm : collect ) {
				 if (tm.getEstimatedtime() == i && tm.getUsername() != null) {
					 
					 if(mapping.containsKey(tm.getUsername())) {
						 
						 temp = mapping.get(tm.getUsername()) + tm.getEstimatedtime() ;
						 mapping.put(tm.getUsername(),temp);
						 
					 		}
					 else {
						 mapping.put(tm.getUsername(),tm.getEstimatedtime());
					 		} }}}
	}

	public  String printTask (String taskname) throws IOException {
		
		for (TaskManager tm : collect) {
			if(tm.getTaskname().equals(taskname )) {
				System.out.println(tm.getTaskname());
				System.out.println("Description : "+tm.getDescription());
				System.out.println("Estimated Time : "+tm.getEstimatedtime());
				System.out.println("Priority : "+tm.getPriority());
				
				if (tm.getUsername() == null) {
					System.out.println("Unassigned");	
					fl.write("Unassigned");
				}
				else {
					System.out.println("Assigned to  "+tm.getUsername());
					fl.write(tm.getUsername());
				}
					
				fl.write(tm.getTaskname());
				fl.write(tm.getDescription());
				fl.write(("+tm.getEstimatedtime()+"));
				fl.write("+tm.getPriority()+");
				
				return nextline;
			}
			
		}
		
		return taskname+" task not found";
		
	}

	//mark a task state as completed by giving the boolean true  
	
	public String completeTask(String taskname) {
		for (TaskManager tm : collect) {
			if(tm.getTaskname().equals(taskname)) {
				tm.taskState =true ;
				return "success,Task completed ";
			}
	}
		return "Task does not exist ";

}
	
	public String moveTask(String taskname , String listname) {
		if (!listEle.contains(listname)) {
			return listname+" list not found ";
		}
		for (TaskManager tm :collect ) {
			if(tm.getTaskname().equals(taskname)) {
				sstemp = tm.getDescription();
				temp = tm.getPriority();
				temp1 = tm.getEstimatedtime();
				collect.remove(collect.indexOf(tm));
				addTask(listname, taskname,temp1, temp , sstemp);
				return "task moved ,success";
			}
		}
		return "unsucess move "+taskname+" not found ";
	}
	public String printList(String listname ) throws IOException {
		if(!listEle.contains(listname)) {
			return listname+" does not exist";
					} 
		System.out.println("List "+listname+"   ");
		for (TaskManager tm : collect) {
			if(tm.getListname().equals(listname )) {
	
				System.out.println(tm.getPriority()+" | "+tm.getTaskname()+" | "+tm.getUsername()+" | "+tm.getEstimatedtime()+"h");
				fl.write(tm.getPriority()+" | "+tm.getTaskname()+" | "+tm.getUsername()+" | "+tm.getEstimatedtime()+"h");
			}
			
			}
		return nextline;
		
	}
	public String printUsersByWorkload() throws IOException {
		//clear the map in case of previous used 
		mapping.clear();
		//clear the time collector in case of a previous used
		timerCollector.clear();
		// collecting task time , where user is assigned 
				// collecting task , where user is assigned 
				for (TaskManager tm :collect) {
					if (tm.getUsername() != null ) {
						if (mapping.containsKey(tm.getUsername()))
						{
							temp = mapping.get(tm.getUsername()) + 1 ;
							mapping.put(tm.getUsername(),temp);
						}
						else {							
							mapping.put(tm.getUsername(),1);
						}
					}
				} 
				for (Entry<String, Integer> entry : mapping.entrySet()) {
					String k = entry.getKey();
					int v = entry.getValue();
					System.out.println(k + ", has to complete " + v+" task");
					fl.write( k + ", has to complete " + v+" task");
	}
		return nextline;

	}
	// deletion of a particular task 
	public String deleteTask(String taskname) {
		for (TaskManager tm : collect) {
			if(tm.getTaskname().equals(taskname)) {
				collect.remove(tm);
				return "success ,task removed";
			}
		
	}
		return "task named "+taskname+" not found";

}
	
	public String printUnassignedTasksByPriority() throws IOException {
			// reset the map 
				mapping.clear();
				timerCollector.clear();
				for (TaskManager tm : collect) {
					if(tm.getUsername() == null ) {
						mapping.put(tm.getTaskname(), tm.getPriority());
			}}
				final Map<String, Integer> treeMap = new TreeMap<>(mapping);
				for (Entry<String, Integer> entry : treeMap.entrySet()) {
					String k = entry.getKey();
					int v = entry.getValue();
					System.out.println(v +" | "+k+" | "+getrighttime(k)+"h");
					fl.write( v +" | "+k+" | "+getrighttime(k)+"H");
			}
				return nextline;
	}
	public int getrighttime(String s) {
		
		for (TaskManager tm : collect) {
			
			if (tm.getTaskname() == s ) {
				return tm.getEstimatedtime();}
		
		}
		return 0  ;
		}



	// display all unfinished task by priority 
	public String printAllUnfinishedTasksByPriority() throws IOException {
		// reset the map 
		mapping.clear();

		for (TaskManager tm : collect) {
			if(Boolean.FALSE.equals(tm.getTaskState())) {
				mapping.put(tm.getTaskname(), tm.getPriority());
			}
	}
		// to display it in descending order
		final Map<String, Integer> treeMap = new TreeMap<String, Integer>(mapping);
		for (Entry<String, Integer> entry : treeMap.entrySet()) {
			String k = entry.getKey();
			int v = entry.getValue();
			System.out.println(v+" | "+k+" | "+getrighttime(k)+"h");
			
			fl.write(v+" | "+k+" | "+getrighttime(k)+"h");

	}
		return nextline;
		}

	public String printAllLists() throws IOException {
		
		for (String s : listEle) {
			System.out.println("list  "+s);
			for (TaskManager tm : collect) {
				if(tm.getListname().equals(s)) {	
					System.out.println(tm.getPriority()+" | "+tm.getTaskname()+" | "+tm.getUsername()+" | "+tm.getEstimatedtime()+"h");
					fl.write(tm.getPriority()+" | "+tm.getTaskname()+" | "+tm.getUsername()+" | "+tm.getEstimatedtime()+"h");				
				}
				
			}
			System.out.println(nextline);
		}
		return nextline;
	}

	public String printUserTasks(String string) throws IOException {
		for (TaskManager tm : collect) {
			if(tm.getUsername().equals(string)) {
				System.out.println(tm.getPriority()+" | "+tm.getTaskname()+" | "+tm.getUsername()+" | "+tm.getEstimatedtime()+"h");
				fl.write(tm.getPriority()+" | "+tm.getTaskname()+" | "+tm.getUsername()+" | "+tm.getEstimatedtime()+"h");
			
				return nextline ;
			}

	}
		return string+" not foud";
		}
}
