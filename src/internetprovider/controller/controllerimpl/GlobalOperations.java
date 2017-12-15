package internetprovider.controller.controllerimpl;

import internetprovider.common.entity.sequence.Sequence;
import internetprovider.common.entity.Client;
import internetprovider.common.entity.Service;
import internetprovider.common.exceptions.varieties.DateFormatException;
import internetprovider.common.exceptions.varieties.DbAccessException;
import internetprovider.common.exceptions.varieties.WrongServiceTypeException;
import internetprovider.controller.Controller;
import internetprovider.model.clientmanager.ClientManager;
import internetprovider.model.servicemanager.ServiceManager;

import java.util.ArrayList;
import java.util.Collection;

public class GlobalOperations implements Controller{
    private ArrayList<Client> clients;
    private ArrayList<Service> services;
    private ClientManager clientManager;
    private ServiceManager serviceManager;
    private Sequence clientSequence, serviceSequence;

    public GlobalOperations(ClientManager clientManager, ServiceManager serviceManager){
        this.clientManager = clientManager;
        this.serviceManager = serviceManager;
    }

    @Override
    public void initController() throws DbAccessException, DateFormatException, WrongServiceTypeException {
        clients = (ArrayList<Client>) clientManager.getAllClients();
        services = (ArrayList<Service>) serviceManager.getAllServices();
        clientSequence = new Sequence(clients);
        serviceSequence = new Sequence(services);

    }

    @Override
    public void addClient(Client newClient) {
        newClient.setId(clientSequence.getNextId());
        clients.add(newClient);
    }

    @Override
    public void deleteClient(int clientId) {
        if(!clients.isEmpty()) {
            Collection clientServices = new ArrayList<>();
            for (Service nextService : services) {
                if (((Integer) nextService.getClientId()).equals(clientId)) {
                    clientServices.add(nextService);
                }
            }
            services.removeAll(clientServices);
            for (Client nextClient : clients) {
                if (((Integer) nextClient.getId()).equals(clientId)) {
                    clients.remove(nextClient);
                    return;
                }
            }
            //throw new WrongClientIdException("No such client");
        } //else throw new NoClientsException("No clients yet added");
    }

    @Override
    public Collection<Client> getAllClients() {
        return clients;
    }

    @Override
    public void addService(Service newService) throws WrongServiceTypeException {
        if(!clients.isEmpty()) {
            ArrayList<Service> clientServices = (ArrayList<Service>) getClientServices(newService.getClientId());
            if(!clientServices.isEmpty() && clientServices != null) {
                for (Service nextClientService : clientServices) {
                    if(nextClientService.getType().equals(newService.getType()))
                        throw new WrongServiceTypeException("This client already has a service with this type");
                }
            }
            for (Client client : clients) {
                if (((Integer) client.getId()).equals(newService.getClientId())) {
                    newService.setId(serviceSequence.getNextId());
                    services.add(newService);
                    return;
                }
            }
            //throw new WrongClientIdException("No such client");
        } //else throw new NoClientsException("No clients yet added");
    }

    @Override
    public void deleteService(int serviceId) {
        if(!services.isEmpty()) {
            for (Service nextService : services) {
                if (((Integer) nextService.getId()).equals(serviceId)) {
                    services.remove(nextService);
                    return;
                }
            }
        }
    }

    @Override
    public void updateService(Service service) {
        if(!services.isEmpty()) {
            for (Service nextService : services) {
                if (((Integer) nextService.getId()).equals(service.getId())) {
                    nextService.setName(service.getName());
                    nextService.setStatus(service.getStatus());
                    nextService.setStartDate(service.getStartDate());
                    nextService.setEndDate(service.getEndDate());
                    return;
                }
            }
        }
    }

    @Override
    public Collection<Service> getAllServices() {
        return services;
    }

    @Override
    public void commit() throws DbAccessException {
        clientManager.commit();
        serviceManager.commit();
    }

    @Override
    public Collection<Service> getClientServices(int clientId) {
        if(!services.isEmpty()) {
            ArrayList<Service> clientServices = new ArrayList<>();
            for (Service nextService : services) {
                if (((Integer) nextService.getClientId()).equals(clientId)) {
                    clientServices.add(nextService);
                }
            }
            return clientServices;
        } else
            return null;
    }
}
