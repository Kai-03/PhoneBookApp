package com.PhonebookPrototype.firstprototype.service;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.PhonebookPrototype.firstprototype.models.Contact;
import com.PhonebookPrototype.firstprototype.repository.Contacts;

@Service
public class ContactsService {
	// Contacts Repository
	private Contacts contacts;
	
	@Autowired
	public ContactsService(Contacts contacts) {
		this.contacts=contacts;
	}
	
	// Create contact
	public Contact register(Contact contact) {
		return contacts.save(contact);
	}
	
	// Update contact
	public Contact update(long cid, String first_name, String last_name, String phone, String email, long userid)  {
		Contact contact=contacts.findById(cid).get();
		contact.update(first_name, last_name, phone, email, userid);
		return contacts.save(contact);
	}
	
	// Delete contact
	public void delete(long cid, boolean force) {
		Contact contact = contacts.getById(cid);
		// Delete contact in Database only if there is no reference User
		if (contact.ref_user_id() < 0) {
			contacts.deleteById(cid);
		}
		// Forced delete: If User decides to delete Account
		if (force == true) {
			contacts.deleteById(cid);
		}
	}
	
	// List All contacts
	public List<Contact> list_all() {
		return contacts.findAll();
	}
	
	// GetByRefUser
	public List<JSONObject> findContactByRefUser(long uid) {
		return contacts.findContactByRefUser(uid);
	}
	
	// List one
	public Contact getById(long cid) {
		Contact contact=contacts.findById(cid).get();
		return contact;
	}
	
	// List Readable contacts
	public String read_all() {
		List<Contact> list = contacts.findAll();
		String all = "";
		for (Contact i : list) {
			all += i.toString();
			all += "  ";
		}
		return all;
	}
	

	
	
}