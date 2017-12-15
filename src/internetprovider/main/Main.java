package internetprovider.main;

import internetprovider.controller.Controller;
import internetprovider.controller.controllerimpl.GlobalOperations;
import internetprovider.model.clientmanager.ClientManager;
import internetprovider.model.clientmanager.clientmanagerimpl.ClientManagerTxt;
import internetprovider.model.servicemanager.ServiceManager;
import internetprovider.model.servicemanager.servicemanagerimpl.ServiceManagerTxt;
import internetprovider.view.View;
import internetprovider.view.viewimpl.ConsoleView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        //инициализация модели
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateFormat.setLenient(false);
        String clientsPathFile = "datebase/txt/clients.txt";
        String servicePathFile = "datebase/txt/services.txt";
        ClientManager clientManager = new ClientManagerTxt(clientsPathFile);
        ServiceManager serviceManager = new ServiceManagerTxt(servicePathFile, dateFormat);
        //инициализация контроллера
        Controller controller = new GlobalOperations(clientManager, serviceManager);
        //инициализация вьюхи
        View view = new ConsoleView(controller, dateFormat);
        view.start();
    }
}
