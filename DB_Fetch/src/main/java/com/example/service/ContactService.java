package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.ContactDto;
import com.example.model.Contact;
import com.example.repo.ContactRepository;

@Service
public class ContactService {
	
    @Autowired
    private ContactRepository contactRepository;


//    public List<Contact> getFilteredContacts() {
//        return contactRepository.findAll().stream()
//                .filter(contact -> !contact.getName().isEmpty())
//                .filter(contact -> !(contact.getName().isEmpty() && Character.isDigit(contact.getMobileNumber().charAt(0))))
//                .collect(Collectors.toList());
//    }
    
    public List<Contact> fetchDataWithCondition() {
        List<Contact> contacts = contactRepository.findAll();
        return contacts.stream()
                .map(contact -> {
                    // Skip the row if name is empty
                    if (contact.getName() == null || contact.getName().isEmpty()) {
                        return null;
                    }
                    // Skip entire name column if it's empty
                    if (contact.getName().isEmpty()) {
                        contact.setName(null);
                    }
                    return contact;
                })
                .filter(contact -> contact != null)
                .collect(Collectors.toList());
    }
    
//    public List<Contact> fetchContacts() {
//        List<Contact> contacts = contactRepository.findAll();
//        // Filter contacts
//        return contacts.stream()
//                .filter(contact -> contact.getName() != null && !contact.getName().isEmpty())
//                .collect(Collectors.toList());
//    }
    
    
//    public List<Contact> fetchContacts() {
//        List<Contact> contacts = contactRepository.findAll();
//        
//
//        // Check if all names are empty or null
//        boolean allNamesEmpty = contacts.stream()
//                .allMatch(contact -> contact.getName() == null || contact.getName().isEmpty());
//
////        // If all names are empty, skip printing the name column
//        if (allNamesEmpty) {
//            return contacts.stream()
//                    .map(contact -> new Contact(contact.getId(),contact.getMobileNumber(), contact.getAddress(), contact.getCity()))
//                    .collect(Collectors.toList());
//
//        }
//        
//        // If all names are empty, skip printing the name column
//        if (allNamesEmpty) {
//        	
//        	List<Contact> getDatas = contacts.stream()
//                  .map(contact -> new Contact(contact.getId(),contact.getMobileNumber(), contact.getAddress(), contact.getCity()))
//                  .collect(Collectors.toList());
//        	
//           ContactDto contactDto =  (ContactDto) getDatas.stream().map(getData -> new ContactDto(getData.getId(), getData.getMobileNumber(), getData.getAddress(), getData.getCity()));
//           return  (List<Contact>) contactDto;
//
//        }
//
//        return contacts;
//    }
    
//    public List<?> fetchContactsDto() {
//        List<Contact> contacts = contactRepository.findAll();
//
//        // Check if all names are empty or null
//        boolean allNamesEmpty = contacts.stream()
//                .allMatch(contact -> contact.getName() == null || contact.getName().isEmpty());
//
//        // If all names are empty, skip printing the name column
//        if (allNamesEmpty) {
//            return contacts.stream()
//                  .map(contact -> new ContactDto(contact.getId(), contact.getMobileNumber(), contact.getAddress(), contact.getCity()))
//                  .collect(Collectors.toList());
//        }
//
//     // If one or two names are empty or null, ignore those rows and map the remaining fields to ContactDto
//        return contacts.stream()
//                .filter(contact -> contact.getName() != null && !contact.getName().isEmpty())
//                .map(contact -> new Contact(contact.getId(), contact.getName(), contact.getMobileNumber(), contact.getAddress(), contact.getCity()))
//                .collect(Collectors.toList());
//
//    }
    
  public List<Object> fetchContactsDto() {
  List<Contact> contacts = contactRepository.findAll();
  List<Object> result=new ArrayList<>();

  // Check if all names are empty or null
  boolean allNamesEmpty = contacts.stream()
          .allMatch(contact -> contact.getName() == null || contact.getName().isEmpty());

  // If all names are empty, skip printing the name column
  if (allNamesEmpty) {
       result=  contacts.stream()
            .map(contact -> new ContactDto(contact.getId(), contact.getMobileNumber(), contact.getAddress(), contact.getCity()))
            .collect(Collectors.toList());
       System.out.println(result.toString());
      return result;
  }

// If one or two names are empty or null, ignore those rows and map the remaining fields to ContactDto
  result= contacts.stream()
          .map(contact -> new Contact(contact.getId(), contact.getName(), contact.getMobileNumber(), contact.getAddress(), contact.getCity()))
          .collect(Collectors.toList());
  System.out.println(result.toString());
  
  
  return result;
  
}
    
}
