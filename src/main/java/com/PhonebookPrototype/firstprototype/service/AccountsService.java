package com.PhonebookPrototype.firstprototype.service;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.PhonebookPrototype.firstprototype.models.Account;
import com.PhonebookPrototype.firstprototype.repository.Accounts;
import com.google.gson.Gson;

import java.util.List;

@Service
public class AccountsService {
	// Accounts Repository
	private Accounts users;
	@Autowired
	public AccountsService(Accounts users) {
		this.users=users;
	}
	
	// Create User
	public Account register(Account user) {
		return users.save(user);
	}
	
	// Link Contact ID
	public Account link(long uid, long cid) {
		Account user=users.findById(uid).get();
		user.link(cid);
		return users.save(user);
	}
	
	// Login
	public long login(String username, String password) {
		long fail = 0;
		List<Account> result = users.findUser(username, password);		
		if (result.isEmpty()) {
			return fail;
		}
		else {
			Account user = users.findUser(username,password).get(0);
			boolean auth=user.login(username, password);
			if (auth){return user.id();}
			else {return fail;}
		}
	}
	
	// Check for same usernames, deny access if true
	public boolean validate(Account user) {
		List<String> uids = users.findByUsername(user.username());
		if (!uids.isEmpty()) {
			return false;
		}
		else {
			return true;
		}
	}
	
	// Retrieve All usernames from DB
	public List<String> usernames(){
		return users.findAllUsernames();
	}
	
	// Get Account by Id
	public Account getUserById(long uid) {
		return users.getById(uid);
	}
	
	// Get Uniq Contacts
	public List<Long> getUniqContacts(long uid){
		return users.getUniqContacts(uid);
	}
}
