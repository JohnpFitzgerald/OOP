package classes;

import lists.PatientList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import screens.DisplayBox;


//Pop up window to add a new patient

public class AddPatient {

	public static void display(PatientList allPatients){

		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);

		GridPane PatientPane = new GridPane();
		PatientPane.setHgap(10);
		PatientPane.setVgap(10);
		//PatientPane.setGridLinesVisible(true);

		Label nameLabel = new Label("Name:");
		TextField nameField = new TextField();

		Label addressLabel = new Label("Address:");
		TextArea addressEntry = new TextArea();
		addressEntry.setPrefColumnCount(3);
		addressEntry.setPrefRowCount(3);
		Label emailLabel = new Label("e-mail:");
		TextField emailField = new TextField();
		Label phoneLabel = new Label ("mob or landline \nNumber:");
		TextField phoneField = new TextField();
		Label submission = new Label("");
		submission.setPrefHeight(85);

		Button submit = new Button("Submit");
		submit.setOnAction(e -> {
			String submissionText ="";

			//Make sure Name and Address fields aren't blank
			if (nameField.getText().equals("")){
				submissionText = "Name can't be blank\n";
			}
			if (addressEntry.getText().equals("")){

				submissionText = submissionText + "Address can't be blank\n";
			}

			//check email address has an @ and . in there somewhere
			if (!(emailField.getText().contains("@")&&emailField.getText().contains("."))&&!emailField.getText().equals("")){
				submissionText = submissionText + "email Address invalid\n";
			}

			//Checks to ensure phone number is a number 10 digits long
			if(!phoneField.getText().equals("")) {

				try {
					int i = Integer.parseInt(phoneField.getText());
				} catch (NumberFormatException nfe) {
					submissionText = submissionText + "Phone Number invalid\n";
				}
				if(phoneField.getText().length()!=10&&!submissionText.contains("Mobile")){
					submissionText = submissionText + "Phone Number should be 10 digits long\n";
				}
			}
			submission.setText(submissionText);

			if (submissionText.equals("")){
				allPatients.addPatient(new Patient(nameField.getText(),addressEntry.getText(),emailField.getText(),
						phoneField.getText()));
				DisplayBox.display("Success","Patient Added Successfully");
				window.close();
			}


		});
		Button reset = new Button ( "Reset");
		reset.setOnAction(e->{
			nameField.clear();
			addressEntry.clear();
			emailField.clear();
			phoneField.clear();

		});
		Button cancel = new Button ("Cancel");
		cancel.setOnAction(e->window.close());
		//add all our stuff to the pane
		PatientPane.add(nameLabel,1,1);
		PatientPane.add(addressLabel,1,3);
		PatientPane.add(emailLabel,1,5);
		PatientPane.add(phoneLabel,1,7);
		PatientPane.add(nameField,3,1);
		PatientPane.add(addressEntry,3,3);
		PatientPane.add(emailField,3,5);
		PatientPane.add(phoneField,3,7); 
		PatientPane.add(submit,4,10);
		PatientPane.add(reset,5,10);
		PatientPane.add(cancel,6,10);
		PatientPane.add(submission,3,12,15,3);
		window.setTitle("Add a new Patient");
		window.setScene(new Scene(PatientPane,750,450));
		window.showAndWait();
	}

}
