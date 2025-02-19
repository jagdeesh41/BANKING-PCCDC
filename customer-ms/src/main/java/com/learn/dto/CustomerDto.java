package com.learn.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(
        name = "customerDto",
        description = "Schema to hold customer information and account info"
)
public class CustomerDto {

    private String customerId;

    @NotEmpty(message = "Name of the customer cannot be empty")
    @Size(min = 5, max = 30 , message = "The length of the customer should be between 5 and 30")
    private String name;

    @NotEmpty(message = "Email address cannot be empty")
    @Email(message = "Email address should be valid value")
    private String email;

    @NotEmpty(message = "Mobile Number cannot be empty")
    @Pattern(regexp = "(^$|[\\d]{10})",message = "Mobile Number must be 10  digit")
    private String mobileNumber;

    private Boolean activeSw;

    private Boolean communicationSw;
}
