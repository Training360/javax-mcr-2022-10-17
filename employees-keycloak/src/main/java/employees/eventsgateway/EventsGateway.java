package employees.eventsgateway;

import employees.Gateway;
import lombok.AllArgsConstructor;
import org.springframework.jms.core.JmsTemplate;

@Gateway
@AllArgsConstructor
public class EventsGateway {

    private JmsTemplate jmsTemplate;

    public void sendMessage(String message) {
        var event = new MessageEvent(message);

        jmsTemplate.convertAndSend("eventsQueue", event);
    }
}
