package screens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lists.DentistList;
import lists.PatientList;
import lists.ProcedureList;

import java.util.ArrayList;

import classes.Dentist;
import classes.PaymentAccount;
import classes.Invoice;
import classes.Patient;
import classes.AddPatient;
import classes.Procedure;
import lists.PatientList;

public class MainMenu {

	//create some tableviews for dentist, patient, history
	//and pateient labels for use on the screen main menu

	static TableView procTable, dentistTable, patientTable,oldProcTable,patHist;
	static Label nameLabel, addressLabel, emailLabel, phoneLabel;

	public static void display(DentistList allDentists,PatientList allPatients, ProcedureList allProcedures,String username) {
		Stage window = new Stage();
		window.setTitle("Dentist Administration - Logged in as " +username);
		window.initModality(Modality.APPLICATION_MODAL);
		window.setOnCloseRequest(e->{
			e.consume();
			if(ConfirmBox.display("Exit without saving?","Discard Changes","Return")){
				window.close();
			}
		});

		//buttons and  actions on anchor page
		Button cancel = new Button("Quit without Saving");
		cancel.setOnAction(e->window.close());
		Button saveQuit = new Button("Save and Quit");
		saveQuit.setOnAction(e->{

			allPatients.exportPatients();
			allProcedures.exportProcedures();
			window.close();
		});
		//place buttons on screen
		HBox exitButtons = new HBox(10);
		exitButtons.getChildren().addAll(cancel, saveQuit);

		//Add tabs to main screen
		TabPane tabPane = new TabPane();

		//Patient Management Tab
		Tab patientManTab = new Tab("Main Patient page");
		patientManTab.setClosable(false);
		//add a patient to the system
		Button addPatient = new Button("Add a Patient");
		addPatient.setOnAction(e->{
			AddPatient.display(allPatients);
			patientTable.setItems(allPatients.getPatients(false));
		});

		//delete a patient from the system
		Button deletePatient = new Button("Delete Patient");
		deletePatient.setOnAction(e->{
			if(patientTable.getSelectionModel().getSelectedItem()!=null){
				Patient p = (Patient) patientTable.getSelectionModel().getSelectedItem();
				if(ConfirmBox.display("Are you sure you want to permanently delete patient details? \n"
						+p.toString(),"Delete","Cancel"))
					p.setDeleted(true);
				patientTable.setItems(allPatients.getPatients(false));
			}
		});
		HBox patientButtons = new HBox(10);
		patientButtons.setPadding(new Insets(10));
		patientButtons.getChildren().addAll(addPatient, deletePatient);

		patientTable = allPatients.patientTable(false);
		patientTable.setMaxHeight(200);
		patientTable.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue)->{
			if(newValue != null) {
				Patient p = (Patient) newValue;
				nameLabel.setText("Name: " + p.getName());
				addressLabel.setText("Address: " + p.getAddress());
				emailLabel.setText("email: " + p.getEmail());
				phoneLabel.setText("Phone Number: " + p.getPhone());
				patHist.setItems(p.getPatientHistory().getFinVList());
			}
		});

		Label patDetails = new Label("Patient Details");
		patDetails.setAlignment(Pos.TOP_LEFT);
		Button editPatient = new Button("Edit Patient Details");
		editPatient.setOnAction(e->{
			if(patientTable.getSelectionModel().getSelectedItem()!=null){
				Patient p = (Patient) patientTable.getSelectionModel().getSelectedItem();
				p.editPatient();
			}
			patientTable.refresh();
			//need next line to update labels
			patientTable.setItems(allPatients.getPatients(false));
		});
		editPatient.setAlignment(Pos.TOP_RIGHT);
		HBox spacer = new HBox();
		HBox.setHgrow(spacer,Priority.ALWAYS);
		HBox patientDetails = new HBox(10);
		patientDetails.getChildren().addAll(patDetails,spacer,editPatient);

		GridPane gridPane = new GridPane();
		gridPane.setVgap(10);
		gridPane.setHgap(10);
		//gridPane.setGridLinesVisible(true);

		nameLabel = new Label("Name: ");
		addressLabel = new Label("Address: ");
		emailLabel = new Label("email: ");
		phoneLabel = new Label("Home/Mob Number: ");

		gridPane.add(nameLabel, 1, 1, 40, 3);
		gridPane.add(addressLabel, 1, 3, 40, 8);
		gridPane.add(phoneLabel, 31, 1, 20, 3);
		gridPane.add(emailLabel, 31, 7, 30, 3);

		//TextArea patHist = new TextArea("Patient History");
		patHist = new TableView();
		patHist.setMaxHeight(300);
		//easier than coding a placeholding table
		Patient placeHolder = new Patient("","","","");
		placeHolder.setNextPatientNum(placeHolder.getPatientNum());
		patHist = placeHolder.getPatientHistory().getTable();

		ChoiceBox<String> procChoice = new ChoiceBox<>();
		for(Procedure p : allProcedures)
			procChoice.getItems().add(p.getName());
		Button addPr = new Button("Add Procedure");
		addPr.setOnAction(e->{
			if(patientTable.getSelectionModel().getSelectedItem() != null && procChoice.getValue() != null){
				double cost = 0;
				for (Procedure pr: allProcedures){
					if(pr.getName().equals(procChoice.getValue())){
						cost = pr.getCost();
						break;
					}
				}
				Procedure pr = new Procedure(procChoice.getValue(),cost,username);
				Patient pat = (Patient) patientTable.getSelectionModel().getSelectedItem();
				pat.getPatientHistory().add(pr);
				patHist.setItems(pat.getPatientHistory().getFinVList());
			}
		});
		HBox spacingLeft = new HBox();
		HBox.setHgrow(spacingLeft,Priority.ALWAYS);
		Button remove = new Button("Remove");
		remove.setOnAction(e->{
			if(patientTable.getSelectionModel().getSelectedItem() != null &&
					patHist.getSelectionModel().getSelectedItem()!=null){
				Patient p = (Patient) patientTable.getSelectionModel().getSelectedItem();
				PaymentAccount f = (PaymentAccount) patHist.getSelectionModel().getSelectedItem();
				p.getPatientHistory().remove(f);
				patHist.setItems(p.getPatientHistory().getFinVList());
			}
		});
		HBox spacingRight = new HBox();
		HBox.setHgrow(spacingRight,Priority.ALWAYS);

		Button generateInv = new Button("Generate Invoice");
		generateInv.setOnAction(e->{
			if(patientTable.getSelectionModel().getSelectedItem() != null){
				Patient pat = (Patient) patientTable.getSelectionModel().getSelectedItem();
				pat.getPatientHistory().add(new Invoice(pat.calculateAccountBalance()));
				patHist.setItems(pat.getPatientHistory().getFinVList());
			}
		});
		Button addPay = new Button("Add Payment");
		addPay.setOnAction(e->{
			if(patientTable.getSelectionModel().getSelectedItem() != null){
				Patient pat = (Patient) patientTable.getSelectionModel().getSelectedItem();
				pat.getPatientHistory().addPayment();
				patHist.setItems(pat.getPatientHistory().getFinVList());
			}
		});
		Button acState = new Button("Account Statement");
		acState.setOnAction(e->{
			if(patientTable.getSelectionModel().getSelectedItem() != null){
				Patient pat = (Patient) patientTable.getSelectionModel().getSelectedItem();
				ReportBox.display("Statement",pat.getPatientHistory().toString());
			}
		});
		HBox processPatients = new HBox(10);
		processPatients.getChildren().addAll(addPr,procChoice,spacingLeft,remove,spacingRight, generateInv, addPay, acState);
		processPatients.setAlignment(Pos.CENTER_RIGHT);

		VBox patMan = new VBox(10);
		patMan.setPadding(new Insets(10));
		patMan.getChildren().addAll(patientButtons, patientTable, new Separator(), patientDetails,gridPane,new Separator(),
				new Label("Patient details"),patHist, processPatients);

		patientManTab.setContent(patMan);


		//Clinic details tab
		Tab clinicManTab = new Tab("Clinic details");
		clinicManTab.setClosable(false);

		//Dentist tab
		Label dentistLab = new Label("Dentist details");
		Button addDent = new Button("Add New Dentist");
		addDent.setOnAction(e->{
			allDentists.newDentist();
			dentistTable.setItems(allDentists.dentObsList());
		});
		Button deleteDent = new Button("Delete Dentist");
		deleteDent.setOnAction(e->{
			if(dentistTable.getSelectionModel().getSelectedItem()!=null){
				Dentist d = (Dentist) dentistTable.getSelectionModel().getSelectedItem();
				allDentists.removeDentist(d);
				dentistTable.setItems(allDentists.dentObsList());
			}
		});

		HBox dentButtons = new HBox(10);
		dentistTable = allDentists.dentTableView();
		dentistTable.setMaxHeight(150);
		dentButtons.getChildren().addAll(addDent, deleteDent);


		//Procedures tab
		Label procLab = new Label("Procedures");
		TabPane procTabPane = new TabPane();
		procTabPane.setPadding(new Insets(10));

		Tab activeProc = new Tab("Current Procedures");
		activeProc.setClosable(false);
		procTable = allProcedures.displayProcedures(true);
		procTable.setMaxHeight(150);
		Button addProc = new Button("Add New Procedure");
		addProc.setOnAction(e->{
			allProcedures.newProcedure();
			procTable.setItems(allProcedures.procedureObservableList(true));
			procChoice.getItems().clear();
			for (Procedure pr : allProcedures)
				if(!pr.isDeleted())
					procChoice.getItems().add(pr.getName());
		});
		Button deleteProc = new Button("Discontinue Procedure");
		deleteProc.setOnAction(e->{
			if(procTable.getSelectionModel().getSelectedItem() != null) {
				Procedure p = (Procedure) procTable.getSelectionModel().getSelectedItem();
				allProcedures.deleteProcedure(p.getName());
				procTable.setItems(allProcedures.procedureObservableList(true));
				oldProcTable.setItems(allProcedures.procedureObservableList(false));
				procChoice.getItems().clear();
				for (Procedure pr : allProcedures)
					if(!pr.isDeleted())
						procChoice.getItems().add(pr.getName());
			}
		});
		Button changeProc = new Button("Change Procedure Price");
		changeProc.setOnAction(e->{
			if(procTable.getSelectionModel().getSelectedItem() != null){
				Procedure p = (Procedure) procTable.getSelectionModel().getSelectedItem();
				p.changeCost();
				procTable.refresh();
			}
		});
		HBox procButtons = new HBox(10);
		procButtons.getChildren().addAll(addProc, deleteProc, changeProc);
		VBox activeProcVBox = new VBox(10);
		activeProcVBox.setPadding(new Insets(10));
		activeProcVBox.getChildren().addAll(procButtons,procTable);
		activeProc.setContent(activeProcVBox);

		Tab oldProc = new Tab("Old Procedures");
		oldProc.setClosable(false);
		oldProcTable = allProcedures.displayProcedures(false);
		oldProcTable.setMaxHeight(150);

		Button restoreProc = new Button("Restore Procedure");
		restoreProc.setOnAction(e->{
			if(oldProcTable.getSelectionModel().getSelectedItem() != null) {
				Procedure p = (Procedure) oldProcTable.getSelectionModel().getSelectedItem();
				allProcedures.restoreProcedure(p.getName());
				procTable.setItems(allProcedures.procedureObservableList(true));
				oldProcTable.setItems(allProcedures.procedureObservableList(false));
				procChoice.getItems().clear();
				for (Procedure pr : allProcedures)
					if(!pr.isDeleted())
						procChoice.getItems().add(pr.getName());
			}
		});
		VBox oldProcVBox = new VBox(10);
		oldProcVBox.setPadding(new Insets(10));
		oldProcVBox.getChildren().addAll(restoreProc,oldProcTable);
		oldProc.setContent(oldProcVBox);

		procTabPane.getTabs().addAll(activeProc,oldProc);


		//Reports tab
		Label reportsLab = new Label("Reports");
		Button allPatientsReport = new Button("All Patients");
		allPatientsReport.setOnAction(e->{
			ReportBox.display("System Report", allPatients.toString());
		});
		HBox reportButtons = new HBox(10);
		reportButtons.getChildren().addAll(allPatientsReport);

		VBox clinicVBox = new VBox(10);
		clinicVBox.getChildren().addAll(dentistLab, dentButtons, dentistTable, new Separator(),
				procLab, procTabPane, new Separator(),
				reportsLab, reportButtons);
		clinicVBox.setPadding(new Insets(25));
		clinicManTab.setContent(clinicVBox);


		tabPane.getTabs().addAll(patientManTab,clinicManTab);


		//sett the buttons outside the tabs pane
		AnchorPane anchorPane = new AnchorPane();
		anchorPane.getChildren().addAll(tabPane, exitButtons);
		AnchorPane.setTopAnchor(exitButtons, 3.0);
		AnchorPane.setRightAnchor(exitButtons, 5.0);
		AnchorPane.setTopAnchor(tabPane, 1.0);
		AnchorPane.setRightAnchor(tabPane, 1.0);
		AnchorPane.setLeftAnchor(tabPane, 1.0);
		AnchorPane.setBottomAnchor(tabPane, 1.0);

		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(anchorPane);


		window.setScene(new Scene(borderPane, 800, 800));
		window.show();
	}


}
