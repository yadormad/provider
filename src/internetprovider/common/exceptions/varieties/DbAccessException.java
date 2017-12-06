package internetprovider.common.exceptions.varieties;

import internetprovider.common.exceptions.ProviderException;

public class DbAccessException extends ProviderException {
    public DbAccessException(String message) {
        super(message);
    }
}
