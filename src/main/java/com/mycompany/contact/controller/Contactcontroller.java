package com.mycompany.contact.controller;

import org.springframework.web.bind.annotation.*;
import com.mycompany.contact.dto.Contact;

@RestController  
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/addcontacts") // Base path
public class Contactcontroller {

    // POST endpoint to receive data
    // Handles POST requests to: /addcontacts
    @PostMapping
    public String receiveContact(@RequestBody Contact contact) {
        System.out.println("Received: " + contact.getName());
        System.out.println("Received: " + contact.getPhone());
        return "Contact received successfully!";
    }
}
