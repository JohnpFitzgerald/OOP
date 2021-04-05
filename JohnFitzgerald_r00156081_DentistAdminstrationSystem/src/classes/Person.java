package classes;


import java.io.Serializable;

/**
 * This is the parent class for Dentist and Patient
 */
public abstract class Person implements Serializable {

	//create person variables here - private as we only want them used here
	private String name; 
	private String address; 
	private String phone; 
	private String email;


	//Person Constructor 
	public Person(String name, String address, String phone,String email){
		setName(name);
		setAddress(address);
		setPhone(phone);
		setEmail(email);

	}


	//Methods
	public String toString(){
		return ("Name: " +name + "\nAddress: " + address +"\ne-mail: " + email +
				"\nHome Phone: "+phone + "\n");
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}


}
