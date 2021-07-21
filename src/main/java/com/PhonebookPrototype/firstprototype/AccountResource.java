package com.PhonebookPrototype.firstprototype;

//import java.awt.desktop.UserSessionEvent;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.PhonebookPrototype.firstprototype.models.Account;
import com.PhonebookPrototype.firstprototype.models.Contact;
import com.PhonebookPrototype.firstprototype.service.ContactsService;
import com.google.gson.Gson;
import com.PhonebookPrototype.firstprototype.service.AccountsService;


@CrossOrigin
@RestController
@RequestMapping("/")
public class AccountResource {
	@Autowired
	private AccountsService acc_service;
	@Autowired
	private ContactsService con_service;
	
	public AccountResource(AccountsService acc_service) {
		this.acc_service = acc_service;
	}
	
	/* Register User */
	@RequestMapping(value="register", method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONObject> create(@RequestBody JSONObject account) throws URISyntaxException{
		try {
			// Get Credentials
			String username=account.get("username").toString();
			String password=account.get("password").toString();
			String first_name=account.get("first_name").toString();
			String last_name=account.get("last_name").toString();
			String phone = "";
			String email = "";
			
			JSONObject reply = new JSONObject();
			
			// Check username
			Account user = new Account(username.toLowerCase(), password);
			boolean auth = acc_service.validate(user);
			if (!auth) {
				reply.put("msg", "Username already exists!");
				return new ResponseEntity<JSONObject>(reply, HttpStatus.OK);
			}
			else {
				reply.put("msg", "Registration success!");
			}
			
			// Create Account
			Account result = acc_service.register(user);
			
			// Create Contact
			long uid=user.id();
			Contact contact = new Contact(first_name,last_name, phone, email, -1, uid);
			con_service.register(contact);
			
			// Link Contact ID to User
			long cid=contact.id();
			acc_service.link(uid, cid);
			
			return ResponseEntity.created(new URI("accounts/"+result.id())).body(reply); 
		}    
		catch(Exception e) {
			return new ResponseEntity<JSONObject>(HttpStatus.CONFLICT);
		}
	}
	
	/* Login User */
	@RequestMapping(value="login", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONObject> login(@RequestBody JSONObject user) throws URISyntaxException{
		try {
			// Get Credentials
			String username=user.get("username").toString();
			String password=user.get("password").toString();
			
			// Authenticate; return user id 
			Long login = acc_service.login(username.toLowerCase(), password);
			
			JSONObject reply = new JSONObject();
			
			// Successful Login:
			if (login > 0) {
			    // Response
			    reply.put("msg", "Login success!");
			    reply.put("user_id", login);
			    // Create headers
			    HttpHeaders headers = new HttpHeaders();
			    headers.set("msg","Login success!");
			    // Return ID as URL param
			    Account m_user = acc_service.getUserById(login);
			    return ResponseEntity.created(new URI("login/"+m_user.id())).headers(headers).body(reply); 
				}
			
			// Deny; return to Login page w/ Error
			else {
				reply.put("msg", "Login fail!");
			    return new ResponseEntity<JSONObject>(reply, HttpStatus.OK);
				}
		}
		catch(Exception e) {
			JSONObject reply = new JSONObject();
			reply.put("msg", "Login fail!");
		    return new ResponseEntity<JSONObject>(reply, HttpStatus.CONFLICT);
		}
	}
	
	/* Get user Profile in Dashboard */
	@RequestMapping(value="dashboard", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> dashboard(@RequestBody long uid) throws URISyntaxException{
		String reply = "";
		try {
			Account user = acc_service.getUserById(uid);
			Contact contact = con_service.getById(user.contact_id());			
			ResponseEntity<String> response = ResponseEntity.status(HttpStatus.OK).body(contact.toJSON());
			return response;
		}
		catch(Exception e) {
			return new ResponseEntity<String>(reply, HttpStatus.CONFLICT);
		}
	}
	
	/* Create a new Contact and Add to User Contacts List */
	@RequestMapping(value="contacts", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createContact(@RequestBody JSONObject data) throws URISyntaxException{
		String reply = "";		
		try {
			// Get values
			String first_name=data.get("first_name").toString();
			String last_name=data.get("last_name").toString();
			String phone = data.get("phone").toString();
			String email = data.get("email").toString();
			long uid = Long.parseLong(data.get("user_id").toString());
			// Create a contact & Bound to User, Set RefUser to none
			Contact contact = new Contact(first_name,last_name, phone, email, uid, -1);
			con_service.register(contact);
			
			ResponseEntity<String> response = ResponseEntity.created(new URI("contacts/"+contact.id())).body(contact.toJSON());
//			ResponseEntity<String> response = ResponseEntity.status(HttpStatus.OK).body("Contact created successfully");
			return response;
		}
		catch(Exception e) {
			return new ResponseEntity<String>(reply, HttpStatus.CONFLICT);
		}
	}
	
	/* View Single contact */
	@RequestMapping(value="view", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> viewContact(@RequestBody long cid) {
		String reply = "";
			Contact contact = con_service.getById(cid);
			ResponseEntity<String> response = ResponseEntity.status(HttpStatus.OK).body(contact.toJSON());
			return response;
	}
	
	/* Return List of Personal Contacts */
	@RequestMapping(value="contacts", method=RequestMethod.GET, params={"user"}, produces=MediaType.APPLICATION_JSON_VALUE)
	public List<JSONObject> listContacts(@RequestParam("user") long uid) {	
		List<JSONObject> targetlist = con_service.findContactByRefUser(uid);
		return targetlist;
	}
	
	/* Remove a contact */
	@RequestMapping(value="contacts/{cid}", method=RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> removeContact(@PathVariable("cid") long cid) {
		con_service.delete(cid, false);
		return ResponseEntity.ok().build();
	}
	
	/* Update a contact */
	@RequestMapping(value="contacts", method=RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateContact(@RequestBody JSONObject data) throws URISyntaxException{
		String reply = "";
		try {
			long uid = Long.parseLong(data.get("user_id").toString());
			long cid = Long.parseLong(data.get("contact_id").toString());
			String first_name=data.get("first_name").toString();
			String last_name=data.get("last_name").toString();
			String phone = data.get("phone").toString();
			String email = data.get("email").toString();
			System.out.println(first_name);
			
			con_service.update(cid, first_name, last_name, phone, email, uid);
			Contact contact = con_service.getById(cid);
			
			ResponseEntity<String> response = ResponseEntity.created(new URI("contacts/"+cid)).body(contact.toJSON());
			return response;
		}
		catch(Exception e) {
			return new ResponseEntity<String>(reply, HttpStatus.CONFLICT);
		}
	}


	
	
	

	
	
	
//	@RequestMapping(path = "dashboards/{id}", method = RequestMethod.GET)
//	@ResponseBody
//	public String dashboards(@PathVariable("id") long id) {
//
//		// Add contacts
//		String first_name="Vinai";
//		String last_name="Trinitipakdee";
//		String phone = "+9764310";
//		String email = "31553@gmail.com";
//		long uid = -1; // Unique reference to an existing user
//		Contact contact = new Contact(first_name, last_name, phone, email, uid);
//		con_service.register(contact);
//		
//		System.out.println("  ---  ");
//		System.out.println("Added a new contact");
//		System.out.println(con_service.list_all());
//		
//		// Update contacts
//		uid = id;
//		long cid = 2;
//		first_name="";
//		last_name="Chubchu";
//		phone = "nonumber";
//		email = "invalid@gmail.com";
//		con_service.update(cid, first_name, last_name, phone, email, uid);
//		
//		System.out.println("Updated an existing contact");
//		System.out.println(con_service.read_all());
//		
//		// View one
//		contact = con_service.getById(cid);
//		System.out.println("Viewing a contact");
//		System.out.println(contact.toString());
//		
//		// Remove contacts
//		cid = 1;
//		con_service.delete(cid, false);
//		System.out.println("Removed a contact");
//		System.out.println(con_service.read_all());
//		
//		// List contacts
//		String contacts = con_service.read_all();
//		System.out.println("List all");
//		System.out.println(contacts);
//		
//	   String v = "Get a specific Foo with id=" + id;
//	   return v;
//	}

}
