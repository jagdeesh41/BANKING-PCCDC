package com.learn.mapper;

import com.learn.dto.ProfileDto;
import com.learn.entity.Profile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    Profile toAccounts(ProfileDto profileDto);
    ProfileDto toAccountsDto(Profile profile);
}
