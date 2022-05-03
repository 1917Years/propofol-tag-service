package propofol.tagservice.domain.exception;

import java.util.NoSuchElementException;

public class NotFoundTagException extends NoSuchElementException {
    public NotFoundTagException() {
        super();
    }

    public NotFoundTagException(String s) {
        super(s);
    }
}
