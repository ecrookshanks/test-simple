package com.nokelservices.testsimple.repositories;

import com.nokelservices.testsimple.models.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}
