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

/*
User - сущность в которой хранится информация о приложении, которое имеет
доступ к базе данных и может работать с базами данных и аргументационным алгоритмом
 */

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE)
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", length = 6, nullable = false)
    Long applicationId;
    @Column(name = "token")
    String token;
    @Column(name = "login")
    String login;
    @Column(name = "password")
    String password;
    @Column(name = "token_date_of_expiration")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    Date dateOfExpiration;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return applicationId != null && Objects.equals(applicationId, user.applicationId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}