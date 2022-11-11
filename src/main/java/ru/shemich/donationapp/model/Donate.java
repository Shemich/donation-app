package ru.shemich.donationapp.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@FieldDefaults(level = PRIVATE)
@Table(name = "donations")
public class Donate {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", length = 6, nullable = false)
    Long id;
    @Column(name = "message")
    String message;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Donate donate = (Donate) o;
        return id != null && Objects.equals(id, donate.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
