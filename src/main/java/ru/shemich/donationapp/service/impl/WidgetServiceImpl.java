package ru.shemich.donationapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import ru.shemich.donationapp.api.request.DonateRequest;
import ru.shemich.donationapp.model.Widget;
import ru.shemich.donationapp.repository.WidgetRepository;
import ru.shemich.donationapp.security.Blake3;
import ru.shemich.donationapp.service.WidgetService;

@Service
@Setter
@ConfigurationProperties(prefix = "hash")
public class WidgetServiceImpl implements WidgetService {

    private final WidgetRepository widgetRepository;
    private String secret;

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
        //TODO: подумать как уменьшить обращения к бд
        Blake3 hasher = Blake3.newInstance();
        String hash = secret + widget.getId() + widget.getStreamerId();
        hasher.update(hash.getBytes());
        String hexhash = hasher.hexdigest();
        widget.setHash(hexhash);
        widgetRepository.save(widget);
    }

    @Override
    public void update(Widget widget, DonateRequest request) {
        widget.setDonateAuthor(request.getDonaterNickname());  //  TODO: проверка на анонимность
        widget.setDonateMessage(request.getMessage());
        widget.setAmount(request.getAmount());
        widgetRepository.save(widget);
    }

    @Override
    public void delete(Long id) {
        widgetRepository.deleteById(id);
    }
}
