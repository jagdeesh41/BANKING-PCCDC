package com.learn.query.handler;

import com.learn.dto.ProfileDto;
import com.learn.query.FindProfileQuery;
import com.learn.service.IProfileService;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProfileQueryHandler {

    private final IProfileService profileService;
    @QueryHandler
    public ProfileDto findProfile(FindProfileQuery findCustomerQuery)
    {
        return profileService.fetchProfile(findCustomerQuery.getMobileNumber());
    }

}
