package internetprovider.common.entity;

import internetprovider.common.entity.sequence.ProviderEntity;

import java.util.Date;

public class Service implements ProviderEntity {
    private int id, clientId;
    private String name;
    private ServiceType type;
    private boolean status;
    private Date startDate,  endDate;

    public Service(int id, int clientId, String name, ServiceType type, boolean status, Date startDate, Date endDate) {
        this.id = id;
        this.clientId = clientId;
        this.name = name;
        this.type = type;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ServiceType getType() {
        return type;
    }

    public void setType(ServiceType type) {
        this.type = type;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

   /* public static final Comparator<Service> COMPARE_BY_ID = new Comparator<Service>() {
        @Override
        public int compare(Service previousService, Service nextService) {
            return previousService.getId() - nextService.getId();
        }
    };*/
}
