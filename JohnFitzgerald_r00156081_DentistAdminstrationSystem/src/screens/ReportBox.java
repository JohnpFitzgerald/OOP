package screens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

//Screen to show reports produced by the system


public class ReportBox  {

	public static void display(String title, String message){
		Stage window = new Stage();
		window.setTitle(title);
		window.initModality(Modality.APPLICATION_MODAL);

		TextArea report = new TextArea(message);
		Button ok = new Button ("OK");
		ok.setOnAction(e->window.close());

		VBox layout = new VBox(10);
		layout.setPadding(new Insets(10));
		layout.getChildren().addAll(report, ok);
		layout.setAlignment(Pos.CENTER);
		window.setScene(new Scene(layout));
		window.showAndWait();
	}
}
