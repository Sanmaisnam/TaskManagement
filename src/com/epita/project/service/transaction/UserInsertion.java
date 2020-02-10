package com.epita.project.service.transaction;

import java.sql.SQLException;

public class UserInsertion {
	
	DatabaseDAO dao = new DatabaseDAO();

	public void insertIntoDB(String username) throws SQLException, Exception {
		dao.insertionOfRecord(username);
	}
	

}
