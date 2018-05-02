package application.Layaout;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.ResourceBundle;

import application.AppRunner;
import application.DBConnector.DBConnector;
import application.Entity.ReportEntity;
import application.SQLQuery.SQL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainWindowController implements Initializable {
	private static boolean  flag;
	public static boolean getFlag(){
		return flag;
	}
	private void setFlag(boolean f){
		flag = f;
	}
	
	private static int index;
	public static int getIndex(){
		return index;
	}
	private void setIndex(int i){
		index = i;
	}
	
	 private SQL sql;
	
	 
	 static AppRunner mainApp = new AppRunner();
		
		@FXML
		public void setMainApp(AppRunner mainApp){
		this.mainApp = mainApp;
		}
		
 private static	ObservableList<ReportEntity> list;	
 
 @FXML
 public static ObservableList<ReportEntity> getList(){
	 return list;
 }
	

	@FXML
	private TableView<ReportEntity> table = new TableView<ReportEntity>();

	@FXML
	private TableColumn<ReportEntity, String> project_name
	= new TableColumn<ReportEntity, String>("Название");
	
	@FXML
	private TableColumn<ReportEntity, String> project_type
	= new TableColumn<ReportEntity, String>("Тип");
	
	@FXML
	private TableColumn<ReportEntity, String> priority
	= new TableColumn<ReportEntity, String>("Приоритет");
	
	@FXML
	private TableColumn<ReportEntity, String> related_version
	= new TableColumn<ReportEntity, String>("Связаная версия");
	
	@FXML
	private TableColumn<ReportEntity, String> corected_version
	= new TableColumn<ReportEntity, String>("Исправленая версия");
	
	@FXML
	private TableColumn<ReportEntity, Date> final_date
	= new TableColumn<ReportEntity, Date>("Конечная дата");
	
	@FXML
	private TableColumn<ReportEntity, String> performer
	= new TableColumn<ReportEntity, String>("Исполнитель");
	@FXML
	private TableColumn<ReportEntity, String> strictness
	= new TableColumn<ReportEntity, String>("Строгость");
	
	@FXML
	private TableColumn<ReportEntity, String> test_environment
	= new TableColumn<ReportEntity, String>("Среда тестирования");
	
	@FXML
	private TableColumn<ReportEntity, String> project_status
	= new TableColumn<ReportEntity, String>("Статус");
	
	@FXML
	private TableColumn<ReportEntity, String> description
	= new TableColumn<ReportEntity, String>("Описание");
	
	@FXML
	Button btnCreate = new Button();
	
	@FXML
	private void CreateAction(){
		flag = false;
		setFlag(flag);
		
		try {
			mainApp.newCreateOrUpdateWindow(flag,list,index);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}
	
	
	@FXML
	Button btnUpdate = new Button();
	
	@FXML
	private void UpdateAction(){
		flag = true;
		setFlag(flag);
		try {
			setIndex(table.getSelectionModel().getSelectedIndex());
			mainApp.newCreateOrUpdateWindow(flag,list,index);
		} catch (IOException e1) {
			e1.printStackTrace();
			
		
		}
		
		
		
		

	}
	
	@FXML
	Button btnDelete = new Button();
	
	@FXML
	private void DeleteAction(){
		 try {
             int selectedIndex = table.getSelectionModel().getSelectedIndex();
             if(selectedIndex != -1){
            	 index = selectedIndex;
                int id = list.get(selectedIndex).getId();
                 System.out.println("id = " + id);
				DBConnector.getConnection().createStatement().executeUpdate(SQL.DELETE.getQuery() + id);
             }
			} catch (Exception e) {
				e.printStackTrace();
			}
		 System.out.println("index :" +index);
			list.remove(index);

	}
	
	
	
	 public MainWindowController() {
		mainApp.populateList();
		 list = mainApp.getList();
	}
	 
	
	
	 
	 public TableView<ReportEntity> getTable(){
		 return table;
	 }
	 
	@FXML
	private boolean  setTable(){
		 table.setItems(mainApp.getList());
		
		 return true;
	}
	
	
	
	//Инициализирующий метод JavaFx(выполняеться сразу после загрузки класса)
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		 project_name.setCellValueFactory(new PropertyValueFactory<>("project_name"));
	        project_type.setCellValueFactory(new PropertyValueFactory<>("project_type"));
	        priority.setCellValueFactory(new PropertyValueFactory<>("priority"));
	        related_version.setCellValueFactory(new PropertyValueFactory<>("related_version"));
	        corected_version.setCellValueFactory(new PropertyValueFactory<>("corrected_version"));
	        final_date.setCellValueFactory(new PropertyValueFactory<>("final_date"));
	        strictness.setCellValueFactory(new PropertyValueFactory<>("strictness"));
	        test_environment.setCellValueFactory(new PropertyValueFactory<>("test_environment"));
	        project_status.setCellValueFactory(new PropertyValueFactory<>("project_status"));
	        description.setCellValueFactory(new PropertyValueFactory<>("description"));
	        performer.setCellValueFactory(new PropertyValueFactory<>("performer"));

	       
	       setTable();
		
	}

}
