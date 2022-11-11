package ru.shemich.donationapp.api.request;

import lombok.Data;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
public class AuthenticationRequest {
    String login;
    String password;
}