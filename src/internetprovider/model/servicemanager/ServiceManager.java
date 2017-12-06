package internetprovider.model.servicemanager;

import internetprovider.common.entity.Service;
import internetprovider.common.exceptions.varieties.DateFormatException;
import internetprovider.common.exceptions.varieties.DbAccessException;
import internetprovider.common.exceptions.varieties.WrongServiceTypeException;

import java.util.Collection;

public interface ServiceManager {
    //void addService(Service servicemanager);
    //void deleteService(int serviceId);
    Collection<Service> getAllServices() throws DbAccessException, DateFormatException, WrongServiceTypeException;
    //void updateService();
    void commit() throws DbAccessException;
}
