package com.nokelservices.testsimple;

import com.nokelservices.testsimple.models.Contact;
import com.nokelservices.testsimple.models.Phone;
import com.nokelservices.testsimple.models.UserAddress;
import com.nokelservices.testsimple.models.UserName;
import com.nokelservices.testsimple.repositories.ContactRepository;
import com.nokelservices.testsimple.repositories.PhoneRepository;
import com.nokelservices.testsimple.repositories.UserAddressRepository;
import com.nokelservices.testsimple.repositories.UserNameRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration
public class LoadInitialData {
    private static final Logger log = LoggerFactory.getLogger(LoadInitialData.class);

    @Bean
    CommandLineRunner initContactDatabase(ContactRepository contactRepo,
                                          PhoneRepository phoneRepo,
                                          UserAddressRepository addressRepo,
                                          UserNameRepository nameRepo) {

        CreateContactOne(contactRepo, phoneRepo, addressRepo, nameRepo);
        CreateContactTwo(contactRepo, phoneRepo, addressRepo, nameRepo);
        CreateContactThree(contactRepo, phoneRepo, addressRepo, nameRepo);
        CreateContactFour(contactRepo, phoneRepo, addressRepo, nameRepo);

        return args -> {
            log.info("Sample contact data created!");
        };
    }

    private void CreateContactFour(ContactRepository contactRepo, PhoneRepository phoneRepo, UserAddressRepository addressRepo, UserNameRepository nameRepo) {
        ArrayList<Phone> phoneList = new ArrayList<>();
        phoneList.add(CreateHomePhone(phoneRepo, "703-254-5219"));
        phoneList.add(CreateWorkPhone(phoneRepo, "656-658-6584"));

        UserAddress address = CreateAddress(addressRepo, "123 Nowhere", "Blowing Rock", "NC", "36521");
        UserName name = CreateUser(nameRepo, "Fred", "B", "Flintstone");

        Contact c = new Contact(name, address,
                phoneList,
                "contact4@mail.com");

        Contact nc = contactRepo.save(c);

    }

    private void CreateContactThree(ContactRepository contactRepo, PhoneRepository phoneRepo, UserAddressRepository addressRepo, UserNameRepository nameRepo) {
        ArrayList<Phone> phoneList = new ArrayList<>();
        phoneList.add(CreateMobilePhone(phoneRepo, "904-254-2549"));
        phoneList.add(CreateHomePhone(phoneRepo, "904-444-6556"));

        UserAddress address = CreateAddress(addressRepo, "454 FloatAway Lane", "Jax", "FL", "32605");
        UserName name = CreateUser(nameRepo, "Mama", "Z", "Turnquist");

        Contact c = new Contact(name, address,
                phoneList,
                "contact2@mail.com");

        Contact nc = contactRepo.save(c);

    }

    private void CreateContactTwo(ContactRepository contactRepo, PhoneRepository phoneRepo, UserAddressRepository addressRepo, UserNameRepository nameRepo) {
        ArrayList<Phone> phoneList = new ArrayList<>();
        phoneList.add(CreateMobilePhone(phoneRepo, "214-254-6284"));
        phoneList.add(CreateWorkPhone(phoneRepo, "214-665-6224"));

        UserAddress address = CreateAddress(addressRepo, "545 Big Town", "Dallas", "TX", "61452");
        UserName name = CreateUser(nameRepo, "Ronny", "B", "Bad");

        Contact c = new Contact(name, address,
                phoneList,
                "contact2@mail.com");

        Contact nc = contactRepo.save(c);

    }

    private void CreateContactOne(ContactRepository contactRepo, PhoneRepository phoneRepo, UserAddressRepository addressRepo, UserNameRepository nameRepo) {
        ArrayList<Phone> phoneList = new ArrayList<>();
        phoneList.add(CreateMobilePhone(phoneRepo, "312-254-5219"));
        phoneList.add(CreateWorkPhone(phoneRepo, "303-658-6584"));

        UserAddress address = CreateAddress(addressRepo, "123 Some Way", "Somewhere", "DE", "25101");
        UserName name = CreateUser(nameRepo, "Johnny", "B", "Good");

        Contact c = new Contact(name, address,
                phoneList,
                "contact1@mail.com");

        Contact nc = contactRepo.save(c);

    }

    private UserAddress CreateAddress(UserAddressRepository repo,
                                      String street, String city,
                                      String state, String zip){
        UserAddress ua = new UserAddress(street, city, state, zip);
        return repo.save(ua);
    }

    private Phone CreateMobilePhone(PhoneRepository repo, String num){
        Phone ph = new Phone(num, Phone.PHONE_MOBILE);
        log.info(String.format("Created Phone %s of type %s", ph.getNumber(), ph.getType()));
        return ph;
    }

    private Phone CreateWorkPhone(PhoneRepository repo, String num){
        Phone ph = new Phone(num, Phone.PHONE_WORK);
        log.info(String.format("Created Phone %s of type %s", ph.getNumber(), ph.getType()));
        return ph;
    }

    private Phone CreateHomePhone(PhoneRepository repo, String num){
        Phone ph = new Phone(num, Phone.PHONE_HOME);
        log.info(String.format("Created Phone %s of type %s", ph.getNumber(), ph.getType()));
        return ph;
    }

    private UserName CreateUser(UserNameRepository repo, String first, String middle, String last){
        UserName un = new UserName(first, middle, last);
        return repo.save(un);
    }


}
