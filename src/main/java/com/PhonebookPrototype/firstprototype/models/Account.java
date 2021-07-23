package com.PhonebookPrototype.firstprototype.models;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.google.gson.Gson;

@Entity
@Table(name = "Accounts")
public class Account{
	// Unique Account ID
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long user_id;
	
	// Account credentials
	@Column(name = "username")
	private String username;
	@Column(name = "password")
	private String password;
	
	// Linked Contact ID
	@Column(name = "contact_id")
	private long contact_id;
	
	// Contact IDs List
	@Column(name = "uniq_contacts")
	private Long[] uniq_contacts;
	
	// Default call
	public Account(){
		
	}
	
	// Default New
	public Account(String username, String password) {
		this.username = username;
		this.password = password;
	}

/* Actions */
	
	// Get uid
	public long id(){
		return user_id;
	}
	// Get username
	public String username(){
		return username;
	}
	// Get contact_id
	public long contact_id(){
		return contact_id;
	}
	
	// Get uniq_contacts array //
	public List<Long> contactList(){
		List<Long> targetlist = Arrays.asList(uniq_contacts);
		return targetlist;
	}
	
	// Add to contacts array
	public void addUserToContacts(Long cid){
		
	}
	
	// Convert self to JSON
	public String toJSON() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
	
	// Get login
	public boolean login(String u, String p) {
		if (this.username.equals(u) && this.password.equals(p)) {
			return true;
		}
		else {return false;}
	}
	
	// Link to Contacts, assign cid
	public void link(long cid) {
		this.contact_id=cid;
	}
	
	// Return String data
	public String toStrings() {
		return "Account [id=" + user_id + ", username=" + username + ", pass=" + 
				password + "]";
	}
	

	
}
