package lists;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;
import screens.DisplayBox;
import classes.PaymentAccount;
import classes.Payment;

/**
 * A list of financial transactions
 */
public class PaymentsList implements Iterable<PaymentAccount>, Serializable {

	ArrayList<PaymentAccount> payList;


	//Constructor
	public PaymentsList(){
		payList = new ArrayList<PaymentAccount>();
	}



	//Methods
	public void add(PaymentAccount f){
		payList.add(f);
	}

	public void remove(PaymentAccount f){
		payList.remove(f);
	}

	public void addPayment(){
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);

		ChoiceBox<String> paymentType = new ChoiceBox<>();
		paymentType.getItems().addAll("Card","Cash");
		Label euro = new Label("€");
		TextField payAmount = new TextField();
		HBox entry = new HBox(10);
		entry.getChildren().addAll(paymentType,euro,payAmount);
		entry.setAlignment(Pos.CENTER);

		Label message = new Label();

		HBox buttons = new HBox(10);
		Button confirm = new Button("Confirm Payment");
		confirm.setOnAction(e->{
			message.setText("");
			if(paymentType.getValue()==null)
				message.setText("Please choose payment type\n");
			if(payAmount.getText().equals(""))
				message.setText(message.getText() + "Payment value must be entered");
			if(message.getText().equals("")){
				try{
					double cost = Double.parseDouble(payAmount.getText());
				}catch (NumberFormatException nfe){
					DisplayBox.display("Error","Payment must be numerical");
					return;
				}
				add(new Payment(paymentType.getValue(),Double.parseDouble(payAmount.getText())));
				window.close();
			}
		});
		Button cancel = new Button("Cancel");
		cancel.setOnAction(e->window.close());
		buttons.getChildren().addAll(confirm, cancel);
		buttons.setAlignment(Pos.CENTER);

		VBox vbox = new VBox(10);
		vbox.setPadding(new Insets(10));
		vbox.getChildren().addAll(entry,buttons,message);
		vbox.setAlignment(Pos.CENTER);

		window.setScene(new Scene(vbox,300,150));
		window.showAndWait();
	}

	public TableView getTable(){
		TableView finTable = new TableView();
		TableColumn<PaymentAccount,String> type = new TableColumn("Transaction");
		type.setCellValueFactory(new PropertyValueFactory<>("type"));
		TableColumn<PaymentAccount,String> name = new TableColumn("Type");
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		TableColumn<PaymentAccount,String> debit = new TableColumn("Debit");
		debit.setCellValueFactory(new PropertyValueFactory<>("debit"));
		TableColumn<PaymentAccount,String> credit = new TableColumn("Credit");
		credit.setCellValueFactory(new PropertyValueFactory<>("credit"));
		TableColumn<PaymentAccount,String> amount = new TableColumn("Amount");
		amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
		finTable.setItems(getFinVList());
		finTable.getColumns().addAll(type,name,debit,credit,amount);
		return finTable;
	}

	public ObservableList<PaymentAccount> getFinVList(){
		ObservableList<PaymentAccount> finVList = FXCollections.observableArrayList();
		for (PaymentAccount f: payList){
			finVList.add(f);
		}
		return finVList;
	}

	public String toString(){
		double balance = 0;
		String returnString = "";
		for (PaymentAccount f :payList){
			returnString = returnString + f.toString() +"\n";
			balance = balance + f.credit - f.debit;
		}
		returnString = returnString +"Account Balance: €" +(balance);
		return returnString;
	}

	@Override
	public Iterator<PaymentAccount> iterator() {
		return payList.iterator();
	}

}

