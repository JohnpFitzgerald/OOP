package classes;


import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lists.PaymentsList;
import screens.DisplayBox;



/**
 * This is a child class of person. It also handles 
 * the patient details [id, payments and balances]
 */
public class Patient extends Person {

	//Variables-------------------------------------------------
	private static int patientCounter = 1;
	private int patientNum;
	private boolean deleted;
	private PaymentsList patientHistory;
	private double accountBalance;


	//Constructor for patient here
	public Patient(String name,String address, String phone, String email){
		super(name,address,phone,email);
		setPatientNum();
		setDeleted(false);
		setPatientHistory(new PaymentsList());
	}


	//Methods maintaining patient details and
	//tracking financial accounts
	public double calculateAccountBalance() {
		accountBalance = 0;
		for(PaymentAccount f: getPatientHistory()){
			accountBalance = accountBalance - f.debit + f.credit;
		}

		return accountBalance;
	}

	public static void setNextPatientNum(int i){
		//Method to artificially inflate patientcounter
		//static so can be called by null patient
		patientCounter = i;
	}

	public void editPatient(){
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);

		//gridpane for the patient data entry screen
		GridPane patientPane = new GridPane();
		patientPane.setHgap(10);
		patientPane.setVgap(10);

		//create the labels and fields for the patient details here
		Label nameLabel = new Label("Name:");
		TextField nameField = new TextField(getName());
		Label addressLabel = new Label("Address:");
		TextArea addressEntry = new TextArea(getAddress());
		addressEntry.setPrefColumnCount(3);
		addressEntry.setPrefRowCount(3);
		Label emailLabel = new Label("e-mail:");
		TextField emailField = new TextField(getEmail());
		Label phoneLabel = new Label ("Mob-Landline \nNumber:");
		TextField phoneField = new TextField(getPhone());

		Label submission = new Label("");
		submission.setPrefHeight(85);

		Button submit = new Button("Submit");
		//validate on submit
		submit.setOnAction(e -> {
			String submissionText ="";

			//Make sure Name and Address fields aren't blank
			if (nameField.getText().equals("")){
				submissionText = "Name can't be blank\n";
			}
			if (addressEntry.getText().equals("")){

				submissionText = submissionText + "Address can't be blank\n";
			}

			//check email address has an @ and . in the format somewhere
			if (!(emailField.getText().contains("@")&&emailField.getText().contains("."))&&!emailField.getText().equals("")){
				submissionText = submissionText + "email Address invalid\n";
			}

			//Check to make sure phone number value is a valid  number
			if(!phoneField.getText().equals("")) {
				try {
					int i = Integer.parseInt(phoneField.getText());
				} catch (NumberFormatException nfe) {
					submissionText = submissionText + "Phone Number invalid\n";
				}
			}

			submission.setText(submissionText);

			if (submissionText.equals("")){
				setName(nameField.getText());

				setAddress(addressEntry.getText());
				setEmail(emailField.getText());
				setPhone(phoneField.getText());
				//confirm the updates
				DisplayBox.display("Patient Details Updated","");
				window.close();
			}
		});
		//empty all fields here
		Button reset = new Button ( "Clear");
		reset.setOnAction(e->{
			nameField.clear();
			addressEntry.clear();
			emailField.clear();
			phoneField.clear();

		});
		Button cancel = new Button ("Cancel");
		cancel.setOnAction(e->window.close());

		patientPane.add(nameLabel,1,1);   
		patientPane.add(addressLabel,1,3);
		patientPane.add(emailLabel,1,5);
		patientPane.add(phoneLabel,1,7);


		patientPane.add(nameField,3,1);
		patientPane.add(addressEntry,3,3);
		patientPane.add(emailField,3,5);
		patientPane.add(phoneField,3,7);


		patientPane.add(submit,4,10);
		patientPane.add(reset,5,10);
		patientPane.add(cancel,6,10);

		patientPane.add(submission,3,12,15,3);

		window.setTitle("Update Patient details");
		window.setScene(new Scene(patientPane,750,450));
		window.showAndWait();
	}

	public String toString(){
		return getPatientNum() + ": " + getName() +" " + "\nAddress: " +getAddress() + "\nemail: "+getEmail()
		+"\nHome Number: " +getPhone() +"\n-------------------------\n"+ getPatientHistory().toString();
	}

	public void setPatientNum() {
		this.patientNum = patientCounter++;
	}

	public int getPatientNum() {
		return patientNum;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public double getAccountBalance() {
		return accountBalance;
	}


	public PaymentsList getPatientHistory() {
		return patientHistory;
	}


	public void setPatientHistory(PaymentsList patientHistory) {
		this.patientHistory = patientHistory;
	}

}

