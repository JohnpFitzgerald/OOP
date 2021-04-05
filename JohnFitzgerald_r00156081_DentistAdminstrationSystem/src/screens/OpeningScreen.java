package screens;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lists.DentistList;
import lists.PatientList;
import lists.ProcedureList;
import java.util.Date;
import classes.Dentist;

/**
 * This is the first screen that will open on boot up
 * it should display a login screen
 * username test
 * password test
 * 
 * Has option to login or view Procedure List
 */
public class OpeningScreen {

	public static void display(DentistList allDentists,PatientList allPatients, ProcedureList allProcedures){

		Stage window = new Stage();
		window.setTitle("Dental System Summer 2019");

		Label openLabel = new Label("John Fitzgerald - r00156081 Project");
		Label dateLabel = new Label (" " + new Date());

		Label userLabel = new Label("Username: ");
		TextField userField = new TextField();
		HBox userLine = new HBox(10);
		userLine.getChildren().addAll(userLabel,userField);
		userLine.setAlignment(Pos.CENTER);

		Label pwdLabel = new Label("Password:  ");
		PasswordField pwdField = new PasswordField();
		HBox pwdLine = new HBox(10);
		pwdLine.getChildren().addAll(pwdLabel,pwdField);
		pwdLine.setAlignment(Pos.CENTER);

		CheckBox rememberMe = new CheckBox("Remember Me");
		Label loginMessage = new Label("Please log in to continue");

		Button loginButton = new Button("Log In");
		loginButton.setOnAction(e->{
			for (Object o : allDentists ){
				Dentist d = (Dentist) o;
				if (userField.getText().toLowerCase().equals(d.getUsername().toLowerCase())) {
					if (loginMessage.getText().equals("Correct Password Entered")) {
						if(!rememberMe.isSelected()){
							userField.clear();
							pwdField.clear();
						}
						MainMenu.display(allDentists,allPatients,allProcedures,d.getUsername());
					}
					break;
				}
				loginMessage.setText("Incorrect username");
			}
		});
		Button clearButton = new Button("Clear");
		clearButton.setOnAction(e->{
			userField.clear();
			pwdField.clear();
			rememberMe.setSelected(false);
		});
		HBox buttonLine = new HBox(10);
		buttonLine.getChildren().addAll(loginButton,clearButton);
		buttonLine.setAlignment(Pos.CENTER);
		Separator separator = new Separator();
		Button procButton = new Button("Procedure List");
		procButton.setOnAction(e->allProcedures.display());

		VBox vbox = new VBox(10);
		vbox.setAlignment(Pos.CENTER);
		vbox.setPadding(new Insets(10));
		vbox.getChildren().addAll(openLabel,dateLabel,userLine,pwdLine,rememberMe,buttonLine,loginMessage,separator,procButton);

		window.setScene(new Scene(vbox,300,300));
		window.show();

	}
}
