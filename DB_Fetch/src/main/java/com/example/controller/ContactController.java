package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.model.Contact;
import com.example.service.ContactService;


@Controller
public class ContactController {

    @Autowired
    private ContactService contactService;

//    @GetMapping("/contacts")
//    public List<Contact> getFilteredContacts() {
//        return contactService.getFilteredContacts();
//    }
    
    @GetMapping
    public List<Contact> fetchDataWithCondition() {
        return contactService.fetchDataWithCondition();
    }
    
//    @GetMapping("/contacts")
//    public List<Object> getContacts() {
//        return contactService.fetchContactsDto();
//    }   
    
    
    @GetMapping("/contacts")
    public String getContacts(Model model) {
        List<Object> contacts = contactService.fetchContactsDto();
        model.addAttribute("contacts", contacts);
        return "dbFetch";
    }
    
}
