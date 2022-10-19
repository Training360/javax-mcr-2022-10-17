package employees;

import auditing.Auditable;
import auditing.PostfixService;
import org.springframework.stereotype.Service;

@Service
public class HashPostfixService implements PostfixService {
    @Override
    public String calculatePostfix(Auditable object) {
        return Integer.toString(object.hashCode());
    }
}
