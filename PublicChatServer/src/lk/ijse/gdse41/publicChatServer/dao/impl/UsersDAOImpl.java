package lk.ijse.gdse41.publicChatServer.dao.impl;

import lk.ijse.gdse41.publicChatCommon.model.User;
import lk.ijse.gdse41.publicChatServer.dao.UsersDAO;
import lk.ijse.gdse41.publicChatServer.dao.dbConnection.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by oshan on 18-Nov-17.
 */
public class UsersDAOImpl implements UsersDAO {
    private Connection connection;
    private PreparedStatement stm;

    public UsersDAOImpl(){
        try {
            connection= DBConnection.getConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean add(User user) throws SQLException {
        String sql="INSERT INTO users VALUES (?,?,?,?,?,?,?,?)";
        stm=connection.prepareStatement(sql);
        stm.setObject(1,user.getUsername());
        stm.setObject(2,user.getPassword());
        stm.setObject(3,user.getName());
        stm.setObject(4,user.getDob());
        stm.setObject(5,user.getGender());
        stm.setObject(6,user.getAddress());
        stm.setObject(7,user.getNic());
        stm.setObject(8,user.getContact());
        return stm.executeUpdate()>0;
    }

    @Override
    public boolean delete(String username) throws SQLException {
        String sql="DELETE FROM users WHERE username=?";
        stm=connection.prepareStatement(sql);
        stm.setObject(1,username);
        return stm.executeUpdate()>0;
    }

    @Override
    public boolean update(User user) throws SQLException {
        String sql="UPDATE users SET address=?,contact=? WHERE username=?";
        stm=connection.prepareStatement(sql);
        stm.setObject(1,user.getAddress());
        stm.setObject(1,user.getContact());
        stm.setObject(1,user.getUsername());
        return stm.executeUpdate()>0;
    }

    @Override
    public ArrayList<User> get() throws SQLException {
        ArrayList<User> usersList=new ArrayList<>();
        String sql="SELECT * FROM users";
        stm=connection.prepareStatement(sql);
        ResultSet rst=stm.executeQuery();
        while(rst.next()){
            usersList.add(
                    new User(
                            rst.getString(1),
                            rst.getString(2),
                            rst.getString(3),
                            rst.getString(4),
                            rst.getString(5),
                            rst.getString(6),
                            rst.getString(7),
                            rst.getString(8)
                    )
            );
        }
        return usersList;
    }

    @Override
    public User get(String username) throws SQLException {
        String sql="SELECT * FROM users WHERE username=?";
        stm=connection.prepareStatement(sql);
        stm.setObject(1,username);
        ResultSet rst=stm.executeQuery();
        if(rst.next()){
            return new User(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7),
                    rst.getString(8)
            );
        }
        return null;
    }
}
