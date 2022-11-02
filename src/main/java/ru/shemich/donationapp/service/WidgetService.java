package ru.shemich.donationapp.service;

import ru.shemich.donationapp.model.Widget;

public interface WidgetService {

    Widget getById(Long id);
    void save(Widget widget);
    Widget update(Widget widget, Widget widgetDetails);
    void delete(Long id);

}
