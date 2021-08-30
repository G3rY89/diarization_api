package com.voicesearch.diarization.repository;

import com.voicesearch.diarization.model.UserEnroll;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<UserEnroll, Integer>{

    UserEnroll getByUserName(String userName);
}
