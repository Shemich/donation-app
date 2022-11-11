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
@Table(name = "streamers")
public class Streamer {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", length = 6, nullable = false)
    Long id;
    @Column(name = "nickname")
    String nickname;
    @Column(name = "widget_id")
    Long widgetId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Streamer streamer = (Streamer) o;
        return id != null && Objects.equals(id, streamer.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
