package ru.shemich.donationapp.api.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DonateRequest {
    Long amount;
    String message;
    String donaterNickname;
    Boolean isPrivate;
}
