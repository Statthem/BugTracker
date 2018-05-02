package application.SQLQuery;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import application.DBConnector.DBConnector;
import application.Layaout.CreateOrUpdateWindowController;
import application.Layaout.MainWindowController;

public enum SQL {
	SELECT_ALL("Select * From report ; "),

	DELETE("DELETE FROM report WHERE id = "),

	INSERT("INSERT INTO report (" +"project_name," +
			"project_type, priority," + "related_version," + "corrected_version," +
			"final_date," + "performer," + "strictness," + "test_environment," +
			"project_status," + "description" + " ) VALUES (?,?,?,?,?,?,?,?,?,?,?)"),

	UPDATE("update report set project_name = ?,project_type = ?,priority = ?,related_version = ?,corrected_version = ?,"
			+ "final_date = ?,performer = ?,strictness = ?,test_environment= ?,project_status = ?,description = ? where id = ?");


	private final String query;

	private static CreateOrUpdateWindowController ob;

	public void setConroller(CreateOrUpdateWindowController controller){
		ob = controller;
	}

	//private static MainWindowController Mainob = new MainWindowController();

	SQL(String query){
		this.query = query;
	}

	public  String getQuery(){
		return query;
	}

	public PreparedStatement getPreStatementQuery(){
		PreparedStatement preStatement = null;
		try {
			preStatement = DBConnector.getConnection().prepareStatement(getQuery());
		} catch (SQLException e) {
			e.printStackTrace();
		}
				
			return preStatement;
	}

	

	


}


