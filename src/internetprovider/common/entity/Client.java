package internetprovider.common.entity;

import internetprovider.common.entity.sequence.ProviderEntity;

public class Client implements ProviderEntity {

    private int id;
    private String name, info;

    public Client(int id, String name, String info) {
        this.id = id;
        this.name = name;
        this.info = info;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }

    /*public static final Comparator<Client> COMPARE_BY_ID = new Comparator<Client>() {
        @Override
        public int compare(Client previousClient, Client nextClient) {
            return previousClient.getId() - nextClient.getId();
        }
    };*/
}
