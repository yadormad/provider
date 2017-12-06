package internetprovider.view.viewimpl;

import internetprovider.common.entity.Client;
import internetprovider.common.entity.Service;
import internetprovider.common.entity.ServiceType;
import internetprovider.common.entity.sequence.ProviderEntity;
import internetprovider.common.exceptions.ProviderException;
import internetprovider.common.exceptions.varieties.*;
import internetprovider.controller.Controller;
import internetprovider.view.View;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ConsoleView implements View {
    private Controller controller;
    private SimpleDateFormat dateFormat;
    private ServiceType[] serviceTypes;
    private Scanner scanner;
    
    public ConsoleView(Controller controller, DateFormat dateFormat){
        this.controller = controller;
        this.dateFormat = (SimpleDateFormat)dateFormat;
        scanner = new Scanner(System.in);
        serviceTypes = ServiceType.values();
    }
    
    @Override
    public void start(){
        try {
            controller.initController();
            boolean ask = askAndDo();
            while (ask)
                ask = askAndDo();
        } catch (ProviderException e) {
            System.err.println(e.getMessage());
        }
    }

    private boolean askAndDo() {
        try {
            String comand;
            String clientName, clientInfo, serviceName;
            Date startDate, endDate;
            ServiceType serviceType;
            int serviceId, clientId;
            System.out.println("Enter comand (enter 'help' to get a description of all the commands):");
            comand = scanner.next();
            switch (comand) {
                case "help":
                    StringBuffer help = new StringBuffer();
                    help.append("allclients - shows all clients in datebase\n");
                    help.append("services - shows all services of the client\n");
                    help.append("addclient - adds new client in datebase\n");
                    help.append("addservice - adds new service to the client\n");
                    help.append("delclient - removes the client\n");
                    help.append("delservice - removes the service\n");
                    help.append("updservice - changes the service\n");
                    help.append("commit - saves changes to the datebase\n");
                    help.append("quit - finish the programm");
                    System.out.println(help.toString());
                    break;
                case "allclients":
                    showAllClients();
                    break;
                case "services":
                    if (controller.getAllClients().isEmpty()) throw new NoClientsException("No clients yet added");
                    if (controller.getAllServices().isEmpty()) throw new NoServicesException("No services yet added");

                    System.out.println("Enter client id:");
                    clientId = scanner.nextInt();
                    if (!checkId(clientId, controller.getAllClients())) throw new WrongClientIdException("No such client"); //проверка в контроллере?

                    showClientServices(clientId);
                    break;
                case "addclient":
                    System.out.println("Enter client name:");
                    clientName = scanner.next();
                    System.out.println("Enter client information:");
                    clientInfo = scanner.next();
                    addClient(clientName, clientInfo);
                    break;
                case "addservice":
                    if (controller.getAllClients().isEmpty()) throw new NoClientsException("No clients yet added");
                    System.out.println("Enter client id:");
                    clientId = scanner.nextInt();
                    if (!checkId(clientId, controller.getAllClients())) throw new WrongClientIdException("No such client");
                    System.out.println("Enter service name:");
                    serviceName = scanner.next();
                    System.out.println("Enter one of the following service types:");
                    for (ServiceType type : serviceTypes) {
                        System.out.println(type.toString());
                    }
                    try {
                        serviceType = ServiceType.valueOf(scanner.next().toUpperCase());
                    } catch (IllegalArgumentException e) {
                        throw new WrongServiceTypeException("Wrong service type");
                    }
                    System.out.println("Enter start service date. Date format - " + dateFormat.toLocalizedPattern() + ':');
                    try {
                        startDate = dateFormat.parse(scanner.next());
                    } catch (ParseException e) {
                        throw new DateFormatException("Wrong date format");
                    }
                    System.out.println("Enter end service date:");
                    try {
                        endDate = dateFormat.parse(scanner.next());
                    } catch (ParseException e) {
                        throw new DateFormatException("Wrong date format");
                    }
                    if (startDate.after(endDate)) throw new DateOrderException("Wrong date order");
                    addService(clientId, serviceName, serviceType, startDate, endDate);
                    System.out.println("Service added");
                    break;
                case "delclient":
                    if (controller.getAllClients().isEmpty()) throw new NoClientsException("No clients yet added");
                    System.out.println("Enter client id:");
                    clientId = scanner.nextInt();
                    if (!checkId(clientId, controller.getAllClients())) throw new WrongClientIdException("No such client");
                    deleteClient(clientId);
                    break;
                case "delservice":
                    if (controller.getAllServices().isEmpty()) throw new NoServicesException("No services yet added");
                    System.out.println("Enter service id:");
                    serviceId = scanner.nextInt();
                    if (!checkId(serviceId, controller.getAllServices())) throw new WrongServiceIdException("No such service");
                    deleteService(serviceId);
                    break;
                case "updservice":
                    if (controller.getAllServices().isEmpty()) throw new NoServicesException("No services yet added");
                    System.out.println("Enter service number:");
                    serviceId = scanner.nextInt();
                    if (!checkId(serviceId, controller.getAllServices())) throw new WrongServiceIdException("No such service");
                    System.out.println("Enter service name:");
                    serviceName = scanner.next();
                    System.out.println("Enter one of the following service types:");
                    for (ServiceType type : serviceTypes) {
                        System.out.println(type.toString());
                    }
                    try {
                        serviceType = ServiceType.valueOf(scanner.next().toUpperCase());
                    } catch (IllegalArgumentException e) {
                        throw new WrongServiceTypeException("Wrong service type");
                    }
                    System.out.println("Enter start service date. Date format - " + dateFormat.toLocalizedPattern() + ':');
                    try {
                        startDate = dateFormat.parse(scanner.next());
                    } catch (ParseException e) {
                        throw new DateFormatException("Wrong date format");
                    }
                    System.out.println("Enter end service date:");
                    try {
                        endDate = dateFormat.parse(scanner.next());
                    } catch (ParseException e) {
                        throw new DateFormatException("Wrong date format");
                    }
                    if (startDate.after(endDate)) throw new DateOrderException("Wrong date order");
                    updateService(serviceId, serviceName, serviceType, startDate, endDate);
                    System.out.println("Service updated");
                    break;
                case "commit":
                    try {
                        commit();
                    } catch (DbAccessException e) {
                        System.err.println(e.getMessage());
                        return false;
                    }
                    break;
                case "quit":
                    return false;
                default:
                    System.out.println("Wrong comand");
                    break;
            }
        } catch (ProviderException e) {
            System.err.println(e.getMessage());
            /*try {
                Thread.sleep(100);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }*/
            System.out.println("Try again");
        } catch (InputMismatchException e) {
            scanner.next();
            System.err.println("Id must be integer");
            System.out.println("Try again");
        }
        return true;
    }

    private boolean checkId(int id, Collection<? extends ProviderEntity> entities) {
        for(ProviderEntity nextEntity:entities){
            if(((Integer)nextEntity.getId()).equals(id)) {
                return true;
            }
        }
        return false;
    }

    private void addClient(String clientName, String clientInfo) {
        Client newClient = new Client(0, clientName, clientInfo);
        controller.addClient(newClient);
        System.out.println("Client added");
    }

    private void deleteClient(int clientNumber) throws WrongClientIdException, NoClientsException {
        controller.deleteClient(clientNumber);
        System.out.println("Client deleted");
    }

    private void showAllClients() throws NoClientsException {
        if(!controller.getAllClients().isEmpty()) {
            System.out.println("Number\tName\tInfo");
            for (Client nextClient : controller.getAllClients()) {
                StringBuffer clientString = new StringBuffer();
                clientString.append(nextClient.getId()).append('\t');
                clientString.append(nextClient.getName()).append('\t');
                clientString.append(nextClient.getInfo());
                System.out.println(clientString);
            }
        }
        else {
            throw new NoClientsException("No clients yet added");
        }
    }

    private void addService(int clientId, String serviceName, ServiceType serviceType, Date startDate, Date endDate) {
        Service newService = new Service(0, clientId, serviceName, serviceType, false, startDate, endDate);
        controller.addService(newService);
    }

    private void deleteService(int serviceId) throws WrongServiceIdException, NoServicesException {
        controller.deleteService(serviceId);
        System.out.println("Service deleted");
    }

    private void updateService(int serviceId, String serviceName, ServiceType serviceType, Date startDate, Date endDate) {
        Service newService;
        newService = new Service(serviceId, 0, serviceName, serviceType, false, startDate, endDate);
        controller.updateService(newService);
    }

    private void showClientServices(int clientId) throws NoServicesException {
        ArrayList<Service> clientServices = (ArrayList<Service>) controller.getClientServices(clientId);
        if (!clientServices.isEmpty()) {
            System.out.println("Id\t\tName\t\tType\t\tStatus\t\tStart Date\t\tEnd Date");
            for (Service nextService : clientServices) {
                StringBuffer serviceString = new StringBuffer();
                serviceString.append(nextService.getId()).append("\t\t");
                serviceString.append(nextService.getName()).append("\t\t");
                serviceString.append(nextService.getType()).append("\t\t");
                serviceString.append(nextService.getStatus()).append("\t\t");
                serviceString.append(dateFormat.format(nextService.getStartDate())).append("\t\t");
                serviceString.append(dateFormat.format(nextService.getEndDate()));
                System.out.println(serviceString);
            }
        } else throw new NoServicesException("This client has no services");
    }

    private void commit() throws DbAccessException {
        controller.commit();
        System.out.println("Commit complete");
    }
}
