package internetprovider.common.exceptions.varieties;

import internetprovider.common.exceptions.ProviderException;

public class WrongServiceIdException extends ProviderException {
    public WrongServiceIdException(String message){
        super(message);
    }
}
