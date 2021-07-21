package com.PhonebookPrototype.firstprototype.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.Gson;

@Entity
@Table(name = "Contacts")
public class Contact{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long contact_id;

	@Column(name = "ref_user_id")
	private long ref_user_id;

	@Column(name = "first_name")
	private String first_name;
	
	@Column(name = "last_name")
	private String last_name;
	
	@Column(name = "phone")
	private String phone="";
	
	@Column(name = "email")
	private String email="";
	
	@Column(name = "bound_user_id")
	private long bound_user_id;
	
	// Default New Contact
	public Contact(String first_name, String last_name, String phone, String email, long bid, long uid) {
		this.first_name = first_name;
		this.last_name = last_name;
		this.phone = phone;
		this.email = email;
		this.ref_user_id = uid;
		this.bound_user_id = bid;
	}
	
	// Update Contact
	public void update(String first_name, String last_name, String phone, String email, long bid) {
		// Empty String
		String nil="";
		
		if (!first_name.equals(nil)) { 
			this.first_name = first_name;}
		if (!last_name.equals(nil)) { 
			this.last_name = last_name;}
		if (!phone.equals(nil)) { 
			this.phone = phone;}
		if (!email.equals(nil)) { 
			this.email = email;}
		
		this.bound_user_id = bid;
	}
	
	// Convert to String
	@Override
	public String toString() {
		String f = String.format("{contact_id: '%1$s', first_name: '%2$s', last_name: '%3$s', phone: '%4$s', email: '%5$s', ref_user_id: %6$s}",
				contact_id, first_name, last_name, phone, email, ref_user_id);
		return f;
	}
	
	public String toJSON() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
	
	// Get ID
	public long id() {
		return this.contact_id;
	}
	
	// Get reference user ID
	public long ref_user_id() {
		return this.ref_user_id;
	}
	
	
	public Contact(){
		
	}
	
}
