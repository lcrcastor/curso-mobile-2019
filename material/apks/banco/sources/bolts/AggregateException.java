package bolts;

import java.util.List;

public class AggregateException extends Exception {
    private static final long serialVersionUID = 1;
    private List<Exception> a;

    public AggregateException(List<Exception> list) {
        super("There were multiple errors.");
        this.a = list;
    }

    public List<Exception> getErrors() {
        return this.a;
    }
}
