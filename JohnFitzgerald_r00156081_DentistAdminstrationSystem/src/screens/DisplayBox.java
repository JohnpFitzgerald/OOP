package screens;



import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Pop up box for delivering 
 * messages 
 */
public class DisplayBox {

	public static void display(String title, String message){
		Stage window = new Stage();
		window.setTitle(title);
		window.initModality(Modality.APPLICATION_MODAL);

		Label label = new Label(message);
		Button ok = new Button ("OK");
		ok.setOnAction(e->window.close());

		VBox layout = new VBox(10);
		layout.setPadding(new Insets(10));
		layout.getChildren().addAll(label, ok);
		layout.setAlignment(Pos.CENTER);
		window.setScene(new Scene(layout));
		window.showAndWait();
	}


}
