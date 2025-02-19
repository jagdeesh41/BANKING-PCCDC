package com.learn.repo;

import com.learn.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile,Integer> {

    Optional<Profile> findByMobileNumber(String mobileNumber);



}
