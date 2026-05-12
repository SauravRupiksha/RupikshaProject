package com.rupiksha.insurance.Insurance.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FetchRequestDTO {

    @NotBlank(message = "Account Number is required")
    @Pattern(regexp = "^[A-Za-z0-9]{6,20}$", message = "Invalid account/policy number")
    private String accountNumber;

    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid mobile number")
    private String mobileNo;

    @NotBlank(message = "DOB is required")
    @Pattern(
            regexp = "^\\d{4}/\\d{2}/\\d{2}$",
            message = "DOB must be in format yyyy/MM/dd"
    )
    private String dob;

    @Email(message = "Invalid email format")
    private String email;
}