package classes;

// Dentist Class is a child of person also

public class Dentist extends Person{

	//Variables here for logging on - private for this class
	private String ID, username, password;



	//Constructor for the dentist 
	public Dentist(String name, String address, String phone, String email, String username, String password) {
		super(name, address, phone, email);
		setID(ID); 
		setUsername(username);
		setPassword(password);

	}

	public String toString(){
		String output = super.toString();
		output = "Username: " + username + "\n" + output + "\n---------------------------";
		return output;
	}

	public String getID() {
		return ID;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}



}

