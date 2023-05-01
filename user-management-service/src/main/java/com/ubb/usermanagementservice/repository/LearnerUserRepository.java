package com.ubb.usermanagementservice.repository;

import com.ubb.usermanagementservice.model.LearnerUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LearnerUserRepository extends CrudRepository<LearnerUser, Long> {
    LearnerUser findByEmail(final String email);
    LearnerUser findByUsername(final String username);
}
