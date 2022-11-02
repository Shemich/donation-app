package ru.shemich.donationapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.shemich.donationapp.api.request.DonateRequest;
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
    public void update(Widget widget, DonateRequest request) {
        widget.setDonateAuthor(request.getDonaterNickname());  //  TODO: проверка на анонимность
        widget.setDonateMessage(request.getMessage());
        widgetRepository.save(widget);
    }

    @Override
    public void delete(Long id) {
        widgetRepository.deleteById(id);
    }
}
