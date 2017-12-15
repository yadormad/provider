package internetprovider.controller;

import internetprovider.common.entity.Client;
import internetprovider.common.entity.Service;
import internetprovider.common.exceptions.varieties.DateFormatException;
import internetprovider.common.exceptions.varieties.DbAccessException;
import internetprovider.common.exceptions.varieties.WrongServiceTypeException;

import java.util.Collection;

public interface Controller {
    void initController() throws DbAccessException, DateFormatException, WrongServiceTypeException;
    void addClient(Client newClient);
    void deleteClient(int clientNumber);
    Collection<Client> getAllClients();
    void addService(Service newService) throws WrongServiceTypeException;
    void deleteService(int serviceNumber);
    void updateService(Service service);
    Collection<Service> getAllServices();
    void commit() throws DbAccessException;
    Collection<Service> getClientServices(int clientId);
}
