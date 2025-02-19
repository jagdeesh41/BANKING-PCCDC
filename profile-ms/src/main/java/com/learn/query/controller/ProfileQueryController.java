package com.learn.query.controller;


import com.learn.dto.ProfileDto;
import com.learn.query.FindProfileQuery;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/apii")
@Validated
@RequiredArgsConstructor
public class ProfileQueryController {

    private final QueryGateway queryGateway;
    @GetMapping("/profile")
    public ResponseEntity<ProfileDto> fetchProfileDetails(@RequestParam("mobileNumber")
            @Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile Number must be 10 digits") String mobileNumber)
    {
        FindProfileQuery findProfileQuery = new FindProfileQuery(mobileNumber);

        ProfileDto profileDto = queryGateway.query(findProfileQuery, ResponseTypes.instanceOf(ProfileDto.class)).join();
        return ResponseEntity.ok().body(profileDto);
    }

}
