package application.Layaout;


import java.lang.reflect.GenericArrayType;
import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import application.AppRunner;
import application.Entity.ReportEntity;
import application.SQLQuery.SQL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class CreateOrUpdateWindowController implements Initializable {


	//private	MainWindowController mainApp = new MainWindowController();

	AppRunner mainApp;

	@FXML
	public void setMainApp(AppRunner mainApp){
		this.mainApp = mainApp;
	}

	private Stage dialogeStage;

	@FXML
	public void setDialogStage(Stage dialogStage) {
		this.dialogeStage = dialogStage;
	}

	private static boolean flag;

	private static int index;

	public CreateOrUpdateWindowController(){
		// System.out.println("До initialize");
		this.flag = MainWindowController.getFlag();
		this.index = MainWindowController.getIndex();

	}





	@FXML
	public	 TextField project_name  = new TextField();
	@FXML
	private TextField related_version = new TextField();
	@FXML
	private TextField corrected_version = new TextField();
	@FXML
	private TextField performer = new TextField(); 
	@FXML
	private TextField project_status = new TextField();
	@FXML
	private TextArea description = new TextArea();

	@FXML
	private DatePicker datePicker = new DatePicker();

	@FXML
	private ComboBox<String> type = new ComboBox<>();

	@FXML
	private ComboBox<String> priority = new ComboBox<>();

	@FXML
	private ComboBox<String> strictness = new ComboBox<>();

	@FXML
	private ComboBox<String> testEnviorment = new ComboBox<>();

	@FXML
	private Label dateLabel = new Label();

	@FXML
	private Label descLabel = new Label();

	@FXML
	private Button cancel = new Button();

	@FXML
	private Button save = new Button();

	@FXML
	private void btnCloseAction(){
		this.dialogeStage.close();
	}
	@FXML
	private void btnSaveAction(){
		try {
			if(index != -1){
				if(checkIfCorrect()){

					CreateOrUpdate();


				}else{
					Alert alert = new Alert(Alert.AlertType.WARNING);
					alert.setTitle("Warning!");
					alert.setHeaderText("Обязательные поля не заполнены!");
					alert.showAndWait();
					return;
				}
			}
		}catch (ArrayIndexOutOfBoundsException e){}
	}

	@FXML
	public void CreateOrUpdate(){

		//if flag = true - Update
		if(flag){
			try(PreparedStatement preStatement = SQL.UPDATE.getPreStatementQuery()){
				preStatement.setString(1, getProject_name().getText());

				preStatement.setString(2,getType().getValue());
				preStatement.setString(3,getPriority().getValue());
				preStatement.setString(4,getRelated_version().getText());
				preStatement.setString(5,getCorrected_version().getText());
				if(getDatePicker().getValue() != null){
				preStatement.setString(6,getDatePicker().getValue().toString());
				}else{
					preStatement.setDate(6,null);
				}
				preStatement.setString(7,getPerformer().getText());
				preStatement.setString(8,getStrictness().getValue());
				preStatement.setString(9,getTestEnviorment().getValue());
				preStatement.setString(10,getProject_status().getText());
				preStatement.setString(11,getDescription().getText());
				preStatement.setInt(12,MainWindowController.getList().get(index).getId());
				
				preStatement.executeUpdate();
				MainWindowController.getList().remove(index);
				MainWindowController.getList().add(index,CreateReportEntity());
				
				this.dialogeStage.close();
			}catch (SQLException exc){
				exc.printStackTrace(System.out);	
			}catch (NullPointerException exc){	
				exc.printStackTrace(System.out);	
				}
		}	 
		if(!flag){ //Create
			try(PreparedStatement preStatement = SQL.INSERT.getPreStatementQuery()){
				preStatement.setString(1, getProject_name().getText());

				preStatement.setString(2,getType().getValue());
				preStatement.setString(3,getPriority().getValue());
				preStatement.setString(4,getRelated_version().getText());
				preStatement.setString(5,getCorrected_version().getText());
				if(getDatePicker().getValue() != null){
				preStatement.setString(6,getDatePicker().getValue().toString());
				}else{
					preStatement.setDate(6,null);
				}
				preStatement.setString(7,getPerformer().getText());
				preStatement.setString(8,getStrictness().getValue());
				preStatement.setString(9,getTestEnviorment().getValue());
				preStatement.setString(10,getProject_status().getText());
				preStatement.setString(11,getDescription().getText());
				
				preStatement.executeUpdate();
				
				
				MainWindowController.getList().add(CreateReportEntity());
				
				this.dialogeStage.close();
			}catch (SQLException exc){	
			exc.printStackTrace(System.out);	
			}catch (NullPointerException exc){	
				exc.printStackTrace(System.out);	
				}

		}
	}



	private ObservableList<String> typeList = 
			FXCollections.observableArrayList(
					"Баг",
					"Задача"
					);
	private ObservableList<String> priorityList = 
			FXCollections.observableArrayList(
					"Критический",
					"Высокий",
					"Средний",
					"Низкий"
					);
	private  ObservableList<String> strictnessList = 
			FXCollections.observableArrayList(
					"Критическая",
					"Большая",
					"Средняя",
					"Низкая"
					);
	private  ObservableList<String> testEnviourmentList = 
			FXCollections.observableArrayList(
					"SIT",
					"UAT",
					"PDN"
					);



	@Override
	public void initialize(URL location, ResourceBundle resources) {

		type.setItems(typeList);
		priority.setItems(priorityList);
		strictness.setItems(strictnessList);
		testEnviorment.setItems(testEnviourmentList);

		if(flag){
			try{
				// Устанавливаем значения всех полей

				project_name.setText(MainWindowController.getList().get(index).getProject_name());
				type.setPromptText(MainWindowController.getList().get(index).getProject_type());
				priority.setPromptText(MainWindowController.getList().get(index).getPriority());
				strictness.setPromptText(MainWindowController.getList().get(index).getStrictness());
				testEnviorment.setPromptText(MainWindowController.getList().get(index).getTest_environment());
				type.setValue(MainWindowController.getList().get(index).getProject_type());
				priority.setValue(MainWindowController.getList().get(index).getPriority());
				strictness.setValue(MainWindowController.getList().get(index).getStrictness());
				testEnviorment.setValue(MainWindowController.getList().get(index).getTest_environment());
				related_version.setText(MainWindowController.getList().get(index).getRelated_version());
				corrected_version.setText(MainWindowController.getList().get(index).getCorrected_version());
				performer.setText(MainWindowController.getList().get(index).getPerformer());
				project_status.setText(MainWindowController.getList().get(index).getProject_status());
				description.setText(MainWindowController.getList().get(index).getDescription());

				datePicker.setValue(MainWindowController.getList().get(index).getFinal_date().toLocalDate());

			}catch(NullPointerException exc){
			//	exc.printStackTrace(System.out);
			}
		}
	}


	public TextField getProject_name() {
		return project_name;
	}
	public TextField getRelated_version() {
		return related_version;
	}
	public TextField getCorrected_version() {
		return corrected_version;
	}
	public TextField getPerformer() {
		return performer;
	}
	public TextField getProject_status() {
		return project_status;
	}
	public TextArea getDescription() {
		return description;
	}
	public DatePicker getDatePicker() {
		return datePicker;
	}
	public ComboBox<String> getType() {
		return type;
	}
	public ComboBox<String> getPriority() {
		return priority;
	}
	public ComboBox<String> getStrictness() {
		return strictness;
	}
	public ComboBox<String> getTestEnviorment() {
		return testEnviorment;
	}


	public boolean checkIfCorrect(){
		boolean correct  = true;
		if(getProject_name().getText().length() < 1) correct  = false;
		if(getType().getValue() != "Задача" && getType().getValue() != "Баг") correct  = false;
		if(getPriority().getValue() != "Критический" && getPriority().getValue() != "Высокий" && getPriority().getValue() !="Средний" && getPriority().getValue() !="Низкий") correct  =false;
		if(getCorrected_version().getText().length() < 1 )correct  = false;
		if(getPerformer().getText().length() < 1 )correct  = false;
		if (getStrictness().getValue() != "Критическая" && getStrictness().getValue() !="Большая"&& getStrictness().getValue() !="Средняя" && getStrictness().getValue() !="Низкая")correct  = false;
		if(getTestEnviorment().getValue() != "SIT" && getTestEnviorment().getValue() != "UAT" && getTestEnviorment().getValue() !="PDN") correct  = false;
		if(getProject_status().getText().length() < 1)correct  = false;
		if(getDescription().getText().length() < 1) correct  = false;

	
		return correct;
	}


	private ReportEntity CreateReportEntity(){

		ReportEntity reportEntity = null;
		Date date = null;
		try{
			if(datePicker.getValue() == null){
				date = null;
			}
			else{
				date = Date.valueOf(datePicker.getValue());
			}
			reportEntity = new ReportEntity(getProject_name().getText(), getType().getValue(),
					getPriority().getValue(), getRelated_version().getText(),getCorrected_version().getText(),
					date,getPerformer().getText(),getStrictness().getValue(),
					getTestEnviorment().getValue(),getProject_status().getText(),getDescription().getText());
		}catch(NullPointerException exc){}
		return reportEntity;
	}

}