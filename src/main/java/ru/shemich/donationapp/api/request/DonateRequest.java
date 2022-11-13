package ru.shemich.donationapp.api.request;

import lombok.Data;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
public class DonateRequest {
    Long amount;
    String message;
    String donaterNickname;
    Boolean isPrivate;
    String country;
}
