package lists;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

import classes.Patient;
/**
 * An ArrayList of Patients
 * Methods to:
 * Load all patients from files on boot
 * get next available digit for patient number for construction of patient
 * Add patient
 * Write all patients to file on exit
 * Return List of patients
 */
public class PatientList implements Iterable<Patient> {

	//Variables
	ArrayList<Patient> patientList;


	//Constructor
	public PatientList(){
		patientList = new ArrayList<Patient>();
	}



	//loads the patient records from files
	public void loadPatients(){

		for (int x = 1; x <= totalPatients() ; x++){
			try{
				FileInputStream fis = new FileInputStream(".\\patients\\Patient"+x+".ser");
				ObjectInputStream ois = new ObjectInputStream(fis);
				patientList.add((Patient) ois.readObject());
				ois.close();
				fis.close();
			}catch (IOException ioe){
				ioe.printStackTrace();
				return;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	//finds the total number of patient files on system
	public int totalPatients(){

		int patientTotal = 0;
		final File file = new File(".\\patients\\");
		for (final File child : file.listFiles()){
			if (child.getName().toLowerCase().substring(0, 7).equals("patient")
					&& child.getName().toLowerCase().substring(child.getName().length() - 3, child.getName().length())
					.equals("ser")) {
				patientTotal++;
			}
		}
		return patientTotal;
	}

	//adds patient to patient list
	public void addPatient(Patient p) {
		patientList.add(p);
	}

	//remove patient from patient list
	public void removePatient(Patient patient){
		patientList.remove(patient);
	}

	public void exportPatients(){
		//Writes patient list to file
		int writeCounter = 1;
		for (Object o: patientList){
			Patient p = (Patient) o;
			try {
				FileOutputStream fos = new FileOutputStream(".\\patients\\Patient"+writeCounter+".ser");
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(p);
				oos.close();
				fos.close();
				System.out.println("Saved as Patient"+writeCounter);
				writeCounter++;
			}catch (IOException ioe){
				ioe.printStackTrace();
			}
		}
	}

	public ObservableList<Patient> getPatients(boolean deleted){
		//returns an Observable List of patients, to populate TableView
		ObservableList<Patient> patients = FXCollections.observableArrayList();
		for (Patient p: patientList){
			if(p.isDeleted() == deleted)
				patients.add(p);
		}
		return patients;
	}

	public TableView patientTable(boolean deleted){
		TableView table = new TableView();
		TableColumn<Patient,String> patientNo = new TableColumn("#");
		patientNo.setCellValueFactory(new PropertyValueFactory<>("patientNum"));
		TableColumn<Patient,String> Name = new TableColumn("Name");
		Name.setCellValueFactory(new PropertyValueFactory<>("name"));
		TableColumn<Patient,String> address = new TableColumn("Address");
		address.setCellValueFactory(new PropertyValueFactory<>("address"));
		TableColumn<Patient,String> email = new TableColumn("eMail");
		email.setCellValueFactory(new PropertyValueFactory<>("email"));
		TableColumn<Patient,String> phone = new TableColumn("phone");
		phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
		phone.getColumns().addAll(phone);
		table.setItems(getPatients(deleted));
		table.getColumns().addAll(patientNo,Name,address,email,phone);
		return table;
	}

	@Override
	public Iterator<Patient> iterator() {
		//Makes PatientList iterable
		return patientList.iterator();
	}

	public String toString(){
		String output="";
		for (Object o : patientList){
			Patient p = (Patient) o;
			output = output + p.toString() + "\n*****************************\n";
		}
		return output;
	}
}
