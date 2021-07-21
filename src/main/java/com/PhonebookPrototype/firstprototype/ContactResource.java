package com.PhonebookPrototype.firstprototype;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.PhonebookPrototype.firstprototype.models.Contact;
import com.PhonebookPrototype.firstprototype.service.ContactsService;

@CrossOrigin
@RestController
@RequestMapping("/")
public class ContactResource {
	@Autowired
	private ContactsService con_service;
	
	public ContactResource(ContactsService con_service) {
		this.con_service = con_service;
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
			return response;
		}
		catch(Exception e) {
			return new ResponseEntity<String>(reply, HttpStatus.CONFLICT);
		}
	}
	
	/* View Single contact */
	@RequestMapping(value="dashboard/single", params= {"view"}, method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> viewContact(@RequestParam("view") long cid) {
		System.out.println("Retrieveing");
		System.out.println(cid);
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
			
			con_service.update(cid, first_name, last_name, phone, email, uid);
			Contact contact = con_service.getById(cid);
			
			ResponseEntity<String> response = ResponseEntity.created(new URI("contacts/"+cid)).body(contact.toJSON());
			return response;
		}
		catch(Exception e) {
			return new ResponseEntity<String>(reply, HttpStatus.CONFLICT);
		}
	}
	
	
}
