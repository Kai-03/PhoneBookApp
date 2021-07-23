package com.PhonebookPrototype.firstprototype.repository;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

//import org.springframework.data.repository.query.Param;

import com.PhonebookPrototype.firstprototype.models.Account;


public interface Accounts extends JpaRepository<Account, Long>, JpaSpecificationExecutor<Account> {


	@Query(value = "SELECT * FROM Accounts WHERE username = ?1", nativeQuery=true)
	List<String> findByUsername(String username);
	
	// Retruieve all usernames
	@Query(value = "SELECT username FROM Accounts", nativeQuery=true)
	List<String> findAllUsernames();
	
	// Select accounts with matching username and password:
	@Query(value = "SELECT * FROM Accounts WHERE username = ?1 AND password = ?2", 
			  nativeQuery = true)
	List<Account> findUser(String username, String password);
	
	// Select uniq_contacts from Account:
	@Query(value = "SELECT uniq_contacts FROM Accounts WHERE user_id = ?1", 
			  nativeQuery = true)
	List<Long> getUniqContacts(long uid);

	
}
