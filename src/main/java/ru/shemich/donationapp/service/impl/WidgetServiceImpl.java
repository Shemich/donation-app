package ru.shemich.donationapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.shemich.donationapp.model.Widget;
import ru.shemich.donationapp.repository.WidgetRepository;
import ru.shemich.donationapp.service.WidgetService;

@Service
public class WidgetServiceImpl implements WidgetService {

    private final WidgetRepository widgetRepository;

    @Autowired
    public WidgetServiceImpl(WidgetRepository widgetRepository) {
        this.widgetRepository = widgetRepository;
    }

    @Override
    public Widget getById(Long id) {
        return widgetRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Widget widget) {
        widgetRepository.save(widget);
    }

    @Override
    public Widget update(Widget widget, Widget widgetDetails) {
/*        if (personDetails.getName() != null) person.setName(personDetails.getName());*/
        return widget;
    }

    @Override
    public void delete(Long id) {
        widgetRepository.deleteById(id);
    }
}
