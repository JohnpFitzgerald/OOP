package classes;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import screens.DisplayBox;
/**
 * Procedures carried out by the dentists
 * 
 */
public class Procedure extends PaymentAccount{

	//Variables contained only in this class
	private String performedBy;
	private double cost;
	private boolean deleted;
	//constructor for class procedure
	public Procedure(String name, double cost){
		super(name,cost,0.0,0.0,"Procedure");
		cost = super.getDebit();
		setDeleted(false);
	}

	public Procedure(String name, double cost,String dentist){
		super(name,cost,0.0,0.0,"Procedure");
		cost = super.getDebit();
		setPerformedBy(dentist);
		setDeleted(false);
	}

	public String toString(){
		String returnString = getName() + "  €" +getCost();
		if (getPerformedBy()!= null){
			returnString = getPerformedBy() +": " +returnString;
		}
		return returnString;
	}

	public void changeCost(){
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Price Change");
		Label label = new Label(getName() + " costs €" + getCost());
		Label newLabel = new Label("New Cost:");
		TextField newCost = new TextField();
		HBox newHB = new HBox(10);
		newHB.getChildren().addAll(newLabel,newCost);
		Button confirm = new Button("Confirm");
		confirm.setOnAction(e->{
			try{
				double cost = Double.parseDouble(newCost.getText());
			}catch (NumberFormatException nfe){
				DisplayBox.display("Error","Cost must be numerical");
				return;
			}
			setCost(Double.parseDouble(newCost.getText()));
			window.close();
		});
		Button cancel = new Button("Cancel");
		cancel.setOnAction(e->window.close());
		HBox buttons = new HBox(10);
		buttons.getChildren().addAll(confirm,cancel);
		VBox vBox = new VBox(10);
		vBox.setPadding(new Insets(10));
		vBox.setAlignment(Pos.CENTER);
		vBox.getChildren().addAll(label,newHB,buttons);
		window.setScene(new Scene(vBox,300,200));
		window.showAndWait();
	}

	public void setCost(double cost) {
		super.setDebit(cost);
		this.cost = cost;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public void setPerformedBy(String performedBy) {
		this.performedBy = performedBy;
	}

	public double getCost() {
		return super.getDebit();
	}

	public boolean isDeleted() {
		return deleted;
	}

	public String getPerformedBy() {
		return performedBy;
	}

}

