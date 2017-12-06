package internetprovider.common.exceptions.varieties;

import internetprovider.common.exceptions.ProviderException;

public class WrongClientIdException extends ProviderException {
    public WrongClientIdException(String message){
        super(message);
    }
}
