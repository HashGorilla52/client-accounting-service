package com.lde.clientaccountingservice.dto;

import com.lde.clientaccountingservice.entity.ClientStatus;
import com.lde.clientaccountingservice.entity.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateClientAccountRequest(@Size(max = 255)
                                         String fullName,
                                         Gender gender,
                                         ClientStatus status) {
}
