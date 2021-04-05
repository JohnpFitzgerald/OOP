package main;

import classes.Dentist;
import javafx.application.Application;
import javafx.stage.Stage;
import lists.DentistList;
import lists.PatientList;
import lists.ProcedureList;
import screens.OpeningScreen;



public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception{

		//ArrayList<Dentist> allDentists = new ArrayList<Dentist>();
		DentistList allDentists = new DentistList();
		//login details for the 2 dentists
		//ID;username;password;date
		//create instances for both dentists
		Dentist dentist1 = new Dentist("dennis denture","toothsville","0211234567","molar@teeth.com","test","test");
		Dentist dentist2 = new Dentist("denise denture","incisor mansions","0211234890","caps@teeth.com","test2","test2");
		allDentists.addDentist(dentist1);
		allDentists.addDentist(dentist2);

		PatientList allPatients = new PatientList();
		allPatients.loadPatients();

		ProcedureList allProcedures = new ProcedureList();
		allProcedures.loadProcedures();

		OpeningScreen.display(allDentists,allPatients,allProcedures);
	}


	public static void main(String[] args) {
		launch(args);
	}
}

