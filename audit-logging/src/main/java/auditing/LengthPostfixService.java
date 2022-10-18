package auditing;

public class LengthPostfixService implements PostfixService {

    @Override
    public String calculatePostfix(Auditable object) {
        return Integer.toString(object.toString().length());
    }
}
