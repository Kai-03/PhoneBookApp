package com.PhonebookPrototype.firstprototype.repository;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.PhonebookPrototype.firstprototype.models.Contact;

public interface Contacts extends JpaRepository<Contact, Long> {
	// Retrieve all matching user_ids
	@Query(value = "SELECT * FROM Contacts WHERE bound_user_id = ?1", nativeQuery=true)
	List<JSONObject> findContactByRefUser(long bound_user_id);
}
