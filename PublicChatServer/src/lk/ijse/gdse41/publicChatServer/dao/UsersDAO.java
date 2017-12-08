package lk.ijse.gdse41.publicChatServer.dao;

import lk.ijse.gdse41.publicChatCommon.model.User;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by oshan on 18-Nov-17.
 */
public interface UsersDAO {
    boolean add(User user) throws SQLException;
    boolean delete(String username)throws SQLException;
    boolean update(User user)throws SQLException;
    ArrayList<User> get()throws SQLException;
    User get(String username)throws SQLException;
}
