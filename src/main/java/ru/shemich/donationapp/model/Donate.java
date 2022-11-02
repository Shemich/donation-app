package ru.shemich.donationapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
@Table(name = "donations")
public class Donate {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", length = 6, nullable = false)
    Long id;
    @Column(name = "message")
    String text;
    @Column(name = "is_private")
    Boolean isPrivate;
    @Column(name = "amount")
    Long amount;
    @Column(name = "date_of_creation")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    Date dateOfExpiration;
    @Column(name = "donater_nickname")
    String donaterNickname;
    @Column(name = "streamer_nickname")
    String streamerNickname;
}
