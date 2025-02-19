package com.learn.service;


import com.learn.dto.ProfileDto;

public interface IProfileService {

    ProfileDto fetchProfile (String mobileNumber);

}
