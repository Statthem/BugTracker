package application;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.DBConnector.DBConnector;
import application.Entity.ReportEntity;
import application.Layaout.CreateOrUpdateWindowController;
import application.Layaout.MainWindowController;
import application.SQLQuery.SQL;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class AppRunner extends Application{
	 
	
	SQL sql;
	
	public AppRunner(){
	}
	
   
   
    @FXML
    private ObservableList<ReportEntity> list;
    @FXML
    public ObservableList<ReportEntity> getList(){
		 return list;
	 }
    public void populateList(){
    	
		sql = SQL.SELECT_ALL;

		       list = FXCollections.observableArrayList();

		        String sqlGetAll = sql.getQuery();

		        try (Connection connection = DBConnector.getConnection();
		        	  Statement statement = connection.createStatement()){
		            ResultSet resultSet =  statement.executeQuery(sqlGetAll);
		            while (resultSet.next()){
		                ReportEntity report = new ReportEntity(
		                        resultSet.getInt(1),
		                        resultSet.getString(2),
		                        resultSet.getString(3),
		                        resultSet.getString(4),
		                        resultSet.getString(5),
		                        resultSet.getString(6),
		                        resultSet.getDate(7),
		                        resultSet.getString(8),
		                        resultSet.getString(9),
		                        resultSet.getString(10),
		                        resultSet.getString(11),
		                        resultSet.getString(12)
		                        );
		                list.add(report);
		            }

		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		      
		    }
	
	private Stage primaryStage;

	public static void main(String[] args) {
		//Connection connection = DBConnector.getConnection();
		launch(args);
		
		DBConnector.closeConnection();
	}

	public void initFxmlLoader() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(AppRunner.class.getResource("MainWindowLayaout.fxml"));
		SplitPane pane = (SplitPane) loader.load();
		
		MainWindowController controller = loader.getController();
		controller.setMainApp(this);
		Scene scene = new Scene(pane);
		
		
		//CRUD CRUDob = new CRUD();
		//CRUDob.setMainApp(this);
		
		this.primaryStage.setScene(scene);
		this.primaryStage.show();
	}

	public void start(Stage primaryStage) throws IOException {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("BugTracker2.0");

		this.initFxmlLoader();
	}
	
	public void newCreateOrUpdateWindow(boolean flag,ObservableList<ReportEntity> list,int index) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(AppRunner.class.getResource("CreateOrUpdateWindowLayaout.fxml"));
		AnchorPane page = (AnchorPane) loader.load();

		
		
		
		Stage dialogStage = new Stage();
		dialogStage.setTitle("Window");
		dialogStage.initOwner(primaryStage);
		dialogStage.initModality(Modality.WINDOW_MODAL);
		
		
		Scene scene = new Scene(page);
		dialogStage.setScene(scene);
	    
		
		CreateOrUpdateWindowController controller = loader.getController();
		controller.setMainApp(this);
		//System.out.println(this.getList().toString());
		controller.setDialogStage(dialogStage);
		
		
	    
		
		
		dialogStage.show();
	}
	
	
	
	
	
	    
}