package ru.shemich.donationapp.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@FieldDefaults(level = PRIVATE)
@Table(name = "widgets")
public class Widget {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", length = 6, nullable = false)
    Long id;
    @Column(name = "donate_message")
    String donateMessage;
    @Column(name = "donate_author")
    String donateAuthor;
    @Column(name = "amount")
    Long amount;
    @Column(name = "hash")
    String hash;
    @Column(name = "streamer_id")
    Long streamerId;
    @Column(name = "donate_id")
    Long donateId;
    @Column(name = "tip_id")
    String tipId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Widget widget = (Widget) o;
        return id != null && Objects.equals(id, widget.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}