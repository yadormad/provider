package internetprovider.common.exceptions.varieties;

import internetprovider.common.exceptions.ProviderException;

public class WrongServiceTypeException extends ProviderException {

    public WrongServiceTypeException(String message) {
        super(message);
    }
}
