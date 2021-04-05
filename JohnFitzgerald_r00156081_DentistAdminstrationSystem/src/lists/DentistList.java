package lists;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.xml.soap.Text;
import classes.Dentist;
import screens.DisplayBox;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Manage dentist lists here
 */
public class DentistList implements Iterable<Dentist>{

	//variable arraylist for dentists
	private ArrayList<Dentist> dentistsList;


	//Constructor for my list
	public DentistList(){
		dentistsList = new ArrayList();
	}



	//Methods 
	public TableView dentTableView(){
		TableView table = new TableView();
		TableColumn<Dentist,String> name = new TableColumn("Name");
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		TableColumn<Dentist,String> username = new TableColumn("Username");
		username.setCellValueFactory(new PropertyValueFactory<>("username"));
		table.setItems(dentObsList());
		table.getColumns().addAll(name, username);
		return table;
	}
	//get dentists from our lists
	public ObservableList<Dentist> dentObsList(){
		ObservableList<Dentist> dentists = FXCollections.observableArrayList();
		for (Dentist d : dentistsList){
			dentists.add(d);
		}
		return dentists;

	}

	public void newDentist(){
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);

		GridPane dentistPane = new GridPane();
		dentistPane.setHgap(10);
		dentistPane.setVgap(10);


		Label nameLabel = new Label("Name:");
		TextField nameField = new TextField();
		Label addressLabel = new Label("Address:");
		TextArea addressEntry = new TextArea();
		addressEntry.setPrefColumnCount(3);
		addressEntry.setPrefRowCount(3);
		Label emailLabel = new Label("e-mail:");
		TextField emailField = new TextField();
		Label phoneLabel = new Label("Mobile-Landline \nNumber:");
		TextField phoneField = new TextField();
		Label userName = new Label("Username:");
		TextField userNameField = new TextField();
		Label passwordLabel = new Label("Password:");
		TextField passwordField = new TextField();
		Label submission = new Label("");
		submission.setPrefHeight(85);

		Button submit = new Button("Submit");
		submit.setOnAction(e -> {
			String submissionText ="";

			//check name and address
			if (nameField.getText().equals("")){
				submissionText = "Name can't be blank\n";
			}
			if (addressEntry.getText().equals("")){

				submissionText = submissionText + "Address can't be blank\n";
			}

			//check email address
			if (!(emailField.getText().contains("@")&&emailField.getText().contains("."))&&!emailField.getText().equals("")){
				submissionText = submissionText + "email Address invalid\n";
			}

			//Check phone number value is a number
			if(!phoneField.getText().equals("")) {
				try {
					int i = Integer.parseInt(phoneField.getText());
				} catch (NumberFormatException nfe) {
					submissionText = submissionText + "Home Number invalid\n";
				}
			}

			if (userNameField.getText().equals("")){
				submissionText = submissionText + "Enter a valid username\n";
			}
			if (passwordField.getText().equals("")){
				submissionText = submissionText + "Password can't be blank\n 8 chars ";
			}


			submission.setText(submissionText);

			if (submissionText.equals("")){
				addDentist(new Dentist(nameField.getText(),addressEntry.getText(),emailField.getText(),
						phoneField.getText(),userNameField.getText(),passwordField.getText()));
				DisplayBox.display("Success","Dentist Added Successfully");
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

		dentistPane.add(nameLabel,1,1);
		dentistPane.add(addressLabel,1,3);
		dentistPane.add(emailLabel,1,5);
		dentistPane.add(phoneLabel,1,7);
		dentistPane.add(userName,5,3);
		//dentistPane.add(password,5,4);

		dentistPane.add(nameField,3,1);
		dentistPane.add(addressEntry,3,3);
		dentistPane.add(emailField,3,5);
		dentistPane.add(phoneField,3,7);
		dentistPane.add(userNameField,7,3);
		//dentistPane.add(passwordField,7,4);

		dentistPane.add(submit,4,10);
		dentistPane.add(reset,5,10);
		dentistPane.add(cancel,6,10);

		dentistPane.add(submission,3,12,15,3);

		window.setTitle("Adding Patient");
		window.setScene(new Scene(dentistPane,750,450));
		window.showAndWait();
	}



	public void addDentist(Dentist d){
		dentistsList.add(d);
	}

	public void removeDentist(Dentist d){
		dentistsList.remove(d);
	}

	@Override
	public Iterator<Dentist> iterator() {
		return dentistsList.iterator();
	}

}
