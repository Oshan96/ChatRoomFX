package lk.ijse.gdse41.publicChatServer.control;

import lk.ijse.gdse41.publicChatCommon.control.ChatController;
import lk.ijse.gdse41.publicChatCommon.model.User;
import lk.ijse.gdse41.publicChatCommon.observer.ChatObserver;
import lk.ijse.gdse41.publicChatServer.service.ChatService;
import lk.ijse.gdse41.publicChatServer.service.impl.ChatServiceImpl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by oshan on 19-Nov-17.
 */
public class ChatControllerImpl extends UnicastRemoteObject implements ChatController {

    private ChatService service=new ChatServiceImpl();

    public ChatControllerImpl() throws RemoteException {
    }

    @Override
    public boolean notifyAllClients(String username, String message) throws RemoteException {
        return service.notifyAllClients(username,message);
    }

    @Override
    public ArrayList<User> getAll() throws RemoteException, SQLException {
        return service.getAll();
    }

    @Override
    public User get(String username) throws RemoteException, SQLException {
        return service.get(username);
    }

    @Override
    public boolean addChatObserver(ChatObserver chatObserver) throws RemoteException {
        return service.addChatObserver(chatObserver);
    }

    @Override
    public boolean removeChatObserver(ChatObserver chatObserver) throws RemoteException {
        return service.removeChatObserver(chatObserver);
    }

    @Override
    public boolean isReserved(String username) throws RemoteException {
        return service.isReserved(username);
    }

    @Override
    public boolean checkCredentials(String username, String password) throws RemoteException, SQLException {
        return service.checkCredentials(username,password);
    }

    @Override
    public boolean updateClientList() throws RemoteException {
        return service.updateClientList();
    }

    @Override
    public boolean addNewUser(User user) throws RemoteException, SQLException {
        return service.addNewUser(user);
    }
}
