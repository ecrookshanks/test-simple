package com.nokelservices.testsimple.controllers;


import com.nokelservices.testsimple.models.*;
import com.nokelservices.testsimple.repositories.ContactRepository;
import com.nokelservices.testsimple.repositories.PhoneRepository;
import com.nokelservices.testsimple.repositories.UserAddressRepository;
import com.nokelservices.testsimple.repositories.UserNameRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class ContactController {

    private static final Logger log = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private UserNameRepository userNameRepository;

    @Autowired
    private UserAddressRepository userAddressRepository;

    @Autowired
    private PhoneRepository phoneRepository;



    @GetMapping("/contacts")
    public ResponseEntity<List<Contact>> getAllContacts(){
        List<Contact> contacts = contactRepository.findAll();

        String resp = String.format("Returning %d contacts", contacts.size());
        log.info(resp);
        return new ResponseEntity<>(contacts, HttpStatus.OK);
    }

    @GetMapping("/contacts/{id}")
    public ResponseEntity<Contact> getSingleContact(@PathVariable Long id){
        Optional<Contact> contact = contactRepository.findById(id);

        if (contact.isPresent()) {
            log.info(String.format("Found 1 record for id=%d", id));
            return new ResponseEntity<>(contact.get(), HttpStatus.OK);
        }
        else {
            log.info(String.format("No records found for id=%d", id));
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/contacts/{id}")
    public ResponseEntity<Contact> updateSingleContact(@PathVariable Long id, @RequestBody Contact contact){
        Optional<Contact> existingContact = contactRepository.findById(id);
        if (existingContact.isEmpty()){
            return new ResponseEntity("", HttpStatus.NOT_FOUND);
        }

        Contact contactData = existingContact.get();

        contactData.setEmail(contact.getEmail());

        // TODO: Set Subobject properties
        UserAddress existingAddress = contactData.getUserAddress();
        existingAddress.setZip(contact.getUserAddress().getZip());
        existingAddress.setStreet(contact.getUserAddress().getStreet());
        existingAddress.setCity(contact.getUserAddress().getCity());
        existingAddress.setState(contact.getUserAddress().getState());

        UserName existingUserName = contactData.getUserName();
        existingUserName.setFirst(contact.getUserName().getFirst());
        existingUserName.setLast(contact.getUserName().getLast());
        existingUserName.setMiddle(contact.getUserName().getMiddle());

        // remove old phones
        for(Phone p: contactData.getPhones()){
            p.setContact(null);
            phoneRepository.save(p);
        }

        contactData.setPhones(contact.getPhones());

        Contact updatedContact = contactRepository.save(contactData);

        return new ResponseEntity<>(updatedContact, HttpStatus.OK);
    }

    @PostMapping("/contacts")
    public ResponseEntity<Contact> createNewContact(@RequestBody Contact contact){

        UserAddress address = contact.getUserAddress();
        UserName userName = contact.getUserName();
        List<Phone> phones = contact.getPhones();

        UserAddress newUserAddress = userAddressRepository.save(address);
        UserName newUserName = userNameRepository.save(userName);

        Contact newContact = new Contact(newUserName, newUserAddress, phones, contact.getEmail());

        Contact savedContact = this.contactRepository.save(newContact);

        return new ResponseEntity<>(savedContact, HttpStatus.OK);
    }

    @DeleteMapping("/contacts/{id}")
    public ResponseEntity<HttpStatus> removeSingleContact(@PathVariable Long id){
        Optional<Contact> contact = contactRepository.findById(id);
        if (contact.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        for(Phone p: contact.get().getPhones()){
            phoneRepository.delete(p);
        }

        contactRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/contacts/call-list")
    public ResponseEntity<List<HomePhone>> getCallList(){
        List<Contact> allContacts = contactRepository.findAll();
        List<HomePhone> filtered = allContacts.stream()
                .map(c -> makeHomePhone(c))
                .filter(hp -> hp != null)
                .collect(Collectors.toList());

        if (filtered.stream().count() > 0) {
            return new ResponseEntity(filtered, HttpStatus.OK);
        }
        else {
            return new ResponseEntity("", HttpStatus.NOT_FOUND);
        }

    }

    // This private method examines the passed-in contact's phone
    // list.  If a home-phone is found it will create a HomePhone
    // object with the appropriate information and return it,
    // otherwise it will return null so the entry can be filtered out.
    private HomePhone makeHomePhone(Contact contact) {
        List<Phone> phones = contact.getPhones();

        for(Phone p: phones){
            if (p.getType() == Phone.PHONE_HOME){
                return new HomePhone(contact.getUserName(), p.getNumber());
            }
        }
        return null;
    }

}
