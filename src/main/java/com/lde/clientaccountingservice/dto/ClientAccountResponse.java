package com.lde.clientaccountingservice.dto;

import com.lde.clientaccountingservice.entity.ClientStatus;
import com.lde.clientaccountingservice.entity.Gender;
import java.time.Instant;

public record ClientAccountResponse(long id,
                                    String fullName,
                                    Gender gender,
                                    ClientStatus status,
                                    Instant createDttm,
                                    Instant modifyDttm) {
}
