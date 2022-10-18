package auditing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class AuditService {

    private ObjectMapper objectMapper;

    private PostfixService postfixService;

    public void audit(Auditable object) {
        // {"name": "John Doe"} // 16
        // {"name": "John Doe"} // 12fa45
        try {
            var prefix = objectMapper.writeValueAsString(object);
            var postfix = postfixService.calculatePostfix(object);
            log.info(prefix + " " + postfix);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Can not jsonify", e);
        }
    }
}
