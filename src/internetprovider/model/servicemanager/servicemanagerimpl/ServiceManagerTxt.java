package internetprovider.model.servicemanager.servicemanagerimpl;

import internetprovider.common.entity.Service;
import internetprovider.common.entity.ServiceType;
import internetprovider.common.exceptions.varieties.DateFormatException;
import internetprovider.common.exceptions.varieties.DbAccessException;
import internetprovider.common.exceptions.varieties.WrongServiceTypeException;
import internetprovider.model.servicemanager.ServiceManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

public class ServiceManagerTxt implements ServiceManager {

    private File servicesFile;
    private ArrayList<Service> services;
    private DateFormat dateFormat;

    public ServiceManagerTxt(String serviceFilePath, DateFormat dateFormat) {
        servicesFile = new File(serviceFilePath);
        services = new ArrayList<>();
        this.dateFormat = dateFormat;
    }

    @Override
    public Collection<Service> getAllServices() throws DbAccessException, DateFormatException, WrongServiceTypeException {
        Scanner serviceScanner;
        try {
            serviceScanner = new Scanner(servicesFile);
        } catch (FileNotFoundException e) {
            throw new DbAccessException("Service file access denied");
        }
        while(serviceScanner.hasNextLine() && serviceScanner.hasNext()){
            Service nextService;
            int id, clientId;
            String name, stringType;
            ServiceType type;
            boolean status;
            Date startDate, endDate;
            try {
                id = serviceScanner.nextInt();
                clientId = serviceScanner.nextInt();
                name = serviceScanner.next();
                stringType = serviceScanner.next();
                if (stringType.equalsIgnoreCase("tv")) {
                    type = ServiceType.TV;
                } else if (stringType.equalsIgnoreCase("phone")) {
                    type = ServiceType.PHONE;
                } else if (stringType.equalsIgnoreCase("internet")) {
                    type = ServiceType.INTERNET;
                } else {
                    StringBuffer message = new StringBuffer();
                    message.append("No such service type in ").append(services.size()).append("line");
                    throw new WrongServiceTypeException(message.toString());
                }
                status = serviceScanner.nextBoolean();
                startDate = dateFormat.parse(serviceScanner.next());
                endDate = dateFormat.parse(serviceScanner.next());
                nextService = new Service(id, clientId, name, type, status, startDate, endDate);
                services.add(nextService);
            } catch (ParseException e) {
                StringBuffer message = new StringBuffer();
                message.append("Wrong data format in ").append(services.size() + 1).append("-line in service file.\n").append("Correct date format - ").append(dateFormat.toString());
                throw new DateFormatException(message.toString());
            }
        }
        serviceScanner.close();
        return services;
    }

    @Override
    public void commit() throws DbAccessException {
        FileWriter serviceWriter;
        try {
            serviceWriter = new FileWriter(servicesFile, false);
            for(Service nextService:services){
                StringBuffer serviceString = new StringBuffer();
                serviceString.append(nextService.getId()).append(' ');
                serviceString.append(nextService.getClientId()).append(' ');
                serviceString.append(nextService.getName()).append(' ');
                serviceString.append(nextService.getType()).append(' ');
                serviceString.append(nextService.getStatus()).append(' ');
                serviceString.append(dateFormat.format(nextService.getStartDate())).append(' ');
                serviceString.append(dateFormat.format(nextService.getEndDate())).append('\n');
                serviceWriter.write(serviceString.toString());
            }
            serviceWriter.flush();
            serviceWriter.close();
        } catch (IOException e) {
            throw new DbAccessException("Service file access denied");
        }
    }
}
