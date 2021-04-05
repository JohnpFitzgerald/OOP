package classes;

import java.io.Serializable;
import java.util.Date;

/**
 * Parent class for Procedure, Payment and Invoice
 * Allows patientHistory to be an ArrayList of type PaymentAccount
 */
public class PaymentAccount implements Serializable {

	//Variables
	String name, type;
	public Double debit;
	public Double credit;
	Double amount;
	Date date;


	//Constructor
	public PaymentAccount(String name, Double debit, Double credit, Double amount,String type) {
		this.name = name;
		this.type = type;
		this.debit = debit;
		this.credit = credit;
		this.amount = amount;
		setDate();
	}



	//Methods
	public String toString(){
		String returnString = type;
		switch (type){
		case "Procedure":
			returnString = returnString + " cost €" + debit;
			break;
		case "Payment":
			returnString = returnString + " by " + name + " €" +credit;
			break;
		case "Invoice":
			returnString = returnString + " generated for €" + amount;
		}
		return returnString;
	}



	//Setters and Getters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getDebit() {
		return debit;
	}

	public void setDebit(Double debit) {
		this.debit = debit;
	}

	public Double getCredit() {
		return credit;
	}

	public void setCredit(Double credit) {
		this.credit = credit;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate() {
		this.date = new Date();
	}

}
