package com.epita.project.management.functionality;

public class TaskManager {
	
	String  listname ;
	String  taskname ;
	int     estimatedtime ;
	int     priority  ;
	String  description ;
	String  username = null ;
	Boolean taskState = false;
	
	// getters and Setters 
	
	public Boolean getTaskState() {
		return taskState;
	}

	public String getUsername() {
		return username;
	}

	public String getListname() {
		return listname;
	}

	public void setListname(String listname) {
		this.listname = listname;
	}

	public String getTaskname() {
		return taskname;
	}

	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}

	public int getEstimatedtime() {
		return estimatedtime;
	}

	public void setEstimatedtime(int estimatedtime) {
		this.estimatedtime = estimatedtime;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	
	//addtask to the system , 
	// create object of reference for every task added 
	
	public TaskManager(String listname, String taskname, int estimatedtime, int priority, String description) {
		this.listname = listname;
		this.taskname = taskname;
		this.estimatedtime = estimatedtime;
		this.priority = priority;
		this.description = description;
	}
	
	// editTask , to modify some property of an object 
	
	public void editTask( String taskname, int estimatedtime, int priority, String description) {
		this.taskname = taskname;
		this.estimatedtime = estimatedtime;
		this.priority = priority;
		this.description = description;
	}

	
	// add a user to a selecting collection 
	
	public void assignTask(String username) {
		this.username = username ;
	}
	
	

}
