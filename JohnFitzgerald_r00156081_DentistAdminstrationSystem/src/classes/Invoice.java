package classes;

public class Invoice extends PaymentAccount {

	public Invoice(double amount){
		super("Issued",0.00,0.00,amount,"Invoice");
	}
}

