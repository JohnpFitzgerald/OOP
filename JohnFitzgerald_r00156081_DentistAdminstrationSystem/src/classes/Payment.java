package classes;


import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Payment extends PaymentAccount{

	//Constructor
	public Payment(String method, double amount){
		super(method,0.00,amount,0.00,"Payment");
	}

}

