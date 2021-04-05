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

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;
import screens.DisplayBox;
import classes.Procedure;

/**
 * Arraylist to store Procedures
 * Can load procedures from file, write to file
 * Can be added to, deleted from
 */
public class ProcedureList implements Iterable<Procedure>{

	//Variables
	ArrayList<Procedure> procList;


	//Constructor
	public ProcedureList(){
		procList = new ArrayList<Procedure>();
	}



	//Methods
	public void addProcedure(Procedure p){
		procList.add(p);
	}

	public void deleteProcedure(String name){
		for (Procedure p : procList){
			if(p.getName().equals(name))
				p.setDeleted(true);
		}
	}

	public void restoreProcedure(String name){
		for (Procedure p : procList){
			if(p.getName().equals(name))
				p.setDeleted(false);
		}
	}

	public void removeProcedure(Procedure p){
		procList.remove(p);
	}

	public void exportProcedures(){
		int writeCounter = 1;
		for (Object o: procList){
			Procedure p = (Procedure) o;
			try {
				FileOutputStream fos = new FileOutputStream(".\\procedures\\Procedure_"+p.getName()+".ser");
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(p);
				oos.close();
				fos.close();
				System.out.println("Saved as Procedure_"+p.getName()+".ser");
				writeCounter++;
			}catch (IOException ioe){
				ioe.printStackTrace();
			}
		}
	}

	public void loadProcedures(){
		final File file = new File(".\\procedures\\");
		for (final File child : file.listFiles()){
			if (child.getName().toLowerCase().substring(0, 9).equals("procedure")
					&& child.getName().toLowerCase().substring(child.getName().length() - 3, child.getName().length())
					.equals("ser")) {
				try{
					FileInputStream fis = new FileInputStream(".\\procedures\\"+child.getName());
					ObjectInputStream ois = new ObjectInputStream(fis);
					procList.add((Procedure) ois.readObject());
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
	}

	public TableView displayProcedures(boolean active){
		TableView table = new TableView();
		TableColumn<Procedure,String> name = new TableColumn("Name");
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		TableColumn<Procedure,String> cost = new TableColumn("Cost");
		cost.setCellValueFactory(new PropertyValueFactory<>("debit"));
		table.setItems(procedureObservableList(active));
		table.getColumns().addAll(name, cost);
		return table;
	}

	public ObservableList<Procedure> procedureObservableList(boolean active){
		//Allows different list to be generated for active/retired procedures
		ObservableList<Procedure> procedures = FXCollections.observableArrayList();
		if(active){
			for (Procedure p: procList){
				if(!p.isDeleted())
					procedures.add(p);
			}
			return procedures;}
		for (Procedure p: procList) {
			if (p.isDeleted())
				procedures.add(p);
		}
		return procedures;
	}

	public void display(){
		Stage window = new Stage();
		window.setTitle("All Available Procedures");
		window.initModality(Modality.APPLICATION_MODAL);
		Button print = new Button("Print Procedure List");
		VBox vBox = new VBox(10);
		vBox.getChildren().addAll(displayProcedures(true),print);
		vBox.setAlignment(Pos.CENTER);
		window.setScene(new Scene(vBox,500,500));
		window.showAndWait();
	}

	public void newProcedure(){
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Add New Procedure");
		Label name = new Label("Procedure Name:");
		TextField nameField = new TextField("");
		HBox nameBox = new HBox(10);
		nameBox.getChildren().addAll(name, nameField);
		Label costLab = new Label("Cost: ");
		TextField costField = new TextField();
		HBox costBox = new HBox(10);
		costBox.getChildren().addAll(costLab, costField);
		Button confirm = new Button("Confirm");
		confirm.setOnAction(e->{
			if(!nameField.getText().equals("")&&!costField.getText().equals("")){
				try{
					double cost = Double.parseDouble(costField.getText());
				}catch (NumberFormatException nfe){
					DisplayBox.display("Error","Cost must be numerical");
					return;
				}
				for (Procedure p : procList){
					if (p.getName().equals(nameField.getText())){
						DisplayBox.display("Error","Procedure already exists with that name" +
								"\nPlease choose another");
						return;
					}
				}
				addProcedure(new Procedure(nameField.getText(),Double.parseDouble(costField.getText())));
				DisplayBox.display("Success","Procedure added successfully");
				window.close();
			}else {
				DisplayBox.display("Error", "Both fields must be completed!");
			}
		});
		Button cancel = new Button("Cancel");
		cancel.setOnAction(e->window.close());
		HBox buttons = new HBox(10);
		buttons.setAlignment(Pos.CENTER);
		buttons.getChildren().addAll(confirm,cancel);
		VBox vbox = new VBox(10);
		vbox.setPadding(new Insets(10));
		vbox.getChildren().addAll(name,nameField,new Separator(),costLab,costField,new Separator(),buttons);
		window.setScene(new Scene(vbox,300,300));
		window.showAndWait();
	}

	public String toString(){
		String output ="";
		for (Object o : procList){
			Procedure p = (Procedure) o;
			if(!p.isDeleted())
				output = output + p.toString() +"\n";
		}
		return output;
	}

	@Override
	public Iterator<Procedure> iterator() {
		return procList.iterator();
	}

}

