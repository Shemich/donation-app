package ru.shemich.donationapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shemich.donationapp.model.Widget;

public interface WidgetRepository extends JpaRepository<Widget, Long> {
}
