package com.nokelservices.testsimple.repositories;

import com.nokelservices.testsimple.models.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneRepository extends JpaRepository<Phone, Long> {
}
