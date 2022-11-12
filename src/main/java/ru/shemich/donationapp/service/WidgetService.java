package ru.shemich.donationapp.service;

import ru.shemich.donationapp.api.request.DonateRequest;
import ru.shemich.donationapp.model.Donate;
import ru.shemich.donationapp.model.Widget;

public interface WidgetService {

    Widget getById(Long id);
    void save(Widget widget);
    void update(Widget widget, DonateRequest request);
    void delete(Long id);
//    Widget create(WidgetRequest request, Long streamerZId);

}
