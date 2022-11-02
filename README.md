# Трек Web: Веб-приложение для сбора донатов от Альфа-Банка

Инновационный способ интеграции в контент стриминговой платформы для донатов стримерам. 

Оно должно содержать базовую аналитику для стримера, быть доступным в РФ и отображать факт доната в контенте с сообщением. 

Реализуемое API:

* `GET /widget/{widgetId}` – страница виджета;
* `GET /donate` – информация по всем донатам;
* `GET /donate/{donateId}` – информация по донату;
* `POST /donate/{streamerNickname}` – создание нового доната стримеру;
