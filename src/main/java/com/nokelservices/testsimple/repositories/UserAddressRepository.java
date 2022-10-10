package com.nokelservices.testsimple.repositories;

import com.nokelservices.testsimple.models.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {
}
