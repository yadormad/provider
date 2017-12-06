package internetprovider.model.clientmanager;

import internetprovider.common.entity.Client;
import internetprovider.common.exceptions.varieties.DbAccessException;

import java.util.Collection;

public interface ClientManager {
    Collection<Client> getAllClients() throws DbAccessException;
    //void addClient(Client clientmanager);
    //void deleteClient(Client clientmanager);
    void commit() throws DbAccessException;
}
