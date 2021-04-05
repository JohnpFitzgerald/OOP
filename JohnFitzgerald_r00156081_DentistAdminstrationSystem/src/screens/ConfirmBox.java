package screens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Pop up to get user to confirm action. This returns 
 * a boolean value
 */
public class ConfirmBox {

	static Boolean response;

	public static boolean display(){
		String message = new String("Are you sure?");
		String confirmText = new String("Confirm");
		String cancelText = new String("Cancel");
		return display(message,confirmText,cancelText);
	}

	public static boolean display(String message,String confirmText, String cancelText){
		Stage window = new Stage();
		window.setTitle("Confirm Action");
		window.initModality(Modality.APPLICATION_MODAL);
		window.setOnCloseRequest(e-> {
			e.consume();
			window.close();
			response=false;
		});

		Label label = new Label(message);
		Button confirm = new Button(confirmText);
		confirm.setOnAction(e ->{
			window.close();
			response = true;
		});
		Button cancel = new Button(cancelText);
		cancel.setOnAction(e->{
			window.close();
			response = false;
		});
		HBox buttons = new HBox(10);
		buttons.setAlignment(Pos.CENTER);
		buttons.getChildren().addAll(confirm,cancel);

		VBox vbox = new VBox(10);
		vbox.setPadding(new Insets(10));
		vbox.setAlignment(Pos.CENTER);
		vbox.getChildren().addAll(label,buttons);

		window.setScene(new Scene(vbox,300,250));
		window.showAndWait();
		return response;
	}

}

