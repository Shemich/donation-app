package ru.shemich.donationapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
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
}
