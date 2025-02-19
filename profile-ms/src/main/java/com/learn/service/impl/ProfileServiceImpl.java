package com.learn.service.impl;

import com.learn.dto.ProfileDto;
import com.learn.entity.Profile;
import com.learn.exception.ResourceNotFoundException;
import com.learn.repo.ProfileRepository;
import com.learn.service.IProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileServiceImpl implements IProfileService
{
    private ProfileRepository profileRepository;


    @Override
    public ProfileDto fetchProfile(String mobileNumber)
    {

        Profile profile = profileRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(()-> new ResourceNotFoundException("profile","mobileNumber",mobileNumber));

        ProfileDto profileDto = new ProfileDto();
        BeanUtils.copyProperties(profile,profileDto);
        return profileDto;
    }
}
