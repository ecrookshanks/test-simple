package com.nokelservices.testsimple.repositories;

import com.nokelservices.testsimple.models.UserName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserNameRepository extends JpaRepository<UserName, Long> {
}
