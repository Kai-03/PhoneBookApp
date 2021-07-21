package com.PhonebookPrototype.firstprototype;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
			
			// Check username exists
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
			
			// Link Contact ID to User Account
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
			    reply.put("access", "true");
			    // Create headers
			    HttpHeaders headers = new HttpHeaders();
			    headers.set("msg","Login success!");
			    // Return ID as URL param
			    Account m_user = acc_service.getUserById(login);
			    return ResponseEntity.created(new URI("login/"+m_user.id())).headers(headers).body(reply); 
				}
			
			// Deny; return to Login page w/ Error
			else {
				reply.put("msg", "Invalid username/password");
				reply.put("access", "false");
			    return new ResponseEntity<JSONObject>(reply, HttpStatus.OK);
				}
		}
		catch(Exception e) {
			JSONObject reply = new JSONObject();
			reply.put("msg", "Login fail!");
		    return new ResponseEntity<JSONObject>(reply, HttpStatus.CONFLICT);
		}
	}
	
	/* Get User Contact Info in Dashboard */
	@RequestMapping(value="dashboard", params={"profile"}, method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public String dashboard(@RequestParam("profile") long uid) {
		Account user = acc_service.getUserById(uid);
		String contact = con_service.getById(user.contact_id()).toJSON();
		
		return contact;
	}
			
	
	/* Modify User Profile in Dashboard */
	@RequestMapping(value="dashboard", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> dashboard(@RequestBody JSONObject data) throws URISyntaxException{
		String reply = "";
		try {
			// Get values
			String first_name=data.get("first_name").toString();
			String last_name=data.get("last_name").toString();
			String phone = data.get("phone").toString();
			String email = data.get("email").toString();
			long cid = Long.parseLong(data.get("contact_id").toString());
			long uid = Long.parseLong(data.get("ref_user_id").toString());
			con_service.update(cid,first_name,last_name,phone,email, uid);
			reply="Ok";
			ResponseEntity<String> response = ResponseEntity.status(HttpStatus.OK).body(reply);
			return response;
		}
		catch(Exception e) {
			return new ResponseEntity<String>(reply, HttpStatus.CONFLICT);
		}
	}
}
