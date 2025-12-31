package com.mycompany.contact.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.mycompany.contact.dto.Contact;
import com.mycompany.contact.service.ContactService;

@RestController  
@CrossOrigin(origins = "http://localhost:4200")
public class Contactcontroller {
	
	@Autowired
	private ContactService contactservice;

    // POST endpoint to receive data
    // Handles POST requests to: /addcontacts
    @PostMapping("/add")
    public String receiveContact(@RequestBody Contact contact) {
       //received contact should be saved in the hashmap object
    	
    	contactservice.savetohashmapobj(contact.getName(),contact.getPhone());
    	
        return "Contact received successfully!";
    }
    
    
   @GetMapping("/show")
   public  Map<String,String> sendcontactstofront(){
    	
    	return contactservice.sendcontactstofront();
    }
}
