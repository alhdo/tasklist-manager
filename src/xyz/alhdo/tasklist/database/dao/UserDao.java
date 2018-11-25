package xyz.alhdo.tasklist.database.dao;

import xyz.alhdo.tasklist.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao extends DAO<User> {
    public UserDao(Connection connection) {
        super(connection);
    }

    @Override
    public boolean create(User obj) {
        try {
            String query = "INSERT INTO users(nom,prenom,adresse,telephone,email) VALUES(?,?,?,?,?)";
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);

            preparedStatement.setString(1,obj.getNom());
            preparedStatement.setString(2,obj.getPrenom());
            preparedStatement.setString(3, obj.getAdresse());
            preparedStatement.setString(4, obj.getTelephone());
            preparedStatement.setString(5, obj.getEmail());

            preparedStatement.execute();
            return true;
        }catch (SQLException e){

            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean delete(User obj) {
        try {
            String query = "DELETE  FROM users WHERE id= ?";
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, obj.getId());

            preparedStatement.executeUpdate();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(User obj) {
        try {
            String request = "UPDATE users SET nom = ?, prenom = ?, adresse = ?, telephone =?, email = ? WHERE id =?";
            PreparedStatement preparedStatement = this.connection.prepareStatement(request);
            preparedStatement.setString(1,obj.getNom());
            preparedStatement.setString(2, obj.getPrenom());
            preparedStatement.setString(3, obj.getAdresse());
            preparedStatement.setString(4, obj.getTelephone());
            preparedStatement.setString(5, obj.getEmail());
            preparedStatement.setInt(6,obj.getId());
            preparedStatement.executeUpdate();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public User find(int id) {
        User user = new User();
        try {
            String query = "SELECT * FROM users where id = ?";

           PreparedStatement preparedStatement = this.connection.prepareStatement(query);
           preparedStatement.setInt(1,id);
           ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                user = new User(resultSet.getInt("id"),
                resultSet.getString("nom"),
                        resultSet.getString("prenom"));
                user.setAdresse(resultSet.getString("adresse"));
                user.setTelephone(resultSet.getString("telephone"));
                user.setEmail(resultSet.getString("email"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return user;
    }
    public User findUser(String email) {
        User user = new User();
        try {
            String query = "SELECT * FROM users where email = ?;";


            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                user = new User(resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"));
                user.setAdresse(resultSet.getString("adresse"));
                user.setTelephone(resultSet.getString("telephone"));
                user.setEmail(resultSet.getString("email"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return user;
    }

    public List<User> find(String searchString){
        List<User> users = new ArrayList<>();
        try {
            String query = "SELECT * FROM users where nom = ? OR  prenom = ? OR adresse = ? OR telephone = ? OR email = ?";

            PreparedStatement preparedStatement = this.connection.prepareStatement(query);

            preparedStatement.setString(1,searchString);
            preparedStatement.setString(2, searchString);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
              User  user = new User(resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"));
                user.setAdresse(resultSet.getString("adresse"));
                user.setTelephone(resultSet.getString("telephone"));
                user.setEmail(resultSet.getString("email"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public List<User> loadAll() {
        List<User> users = new ArrayList<>();
        try {
            String request= "SELECT * FROM users";
            PreparedStatement preparedStatement =this.connection.prepareStatement(request);
            ResultSet resultSet=preparedStatement.executeQuery();

            while (resultSet.next()){
                User u = new User();
                u.setId(resultSet.getInt("id"));
                u.setNom(resultSet.getString("nom"));
                u.setPrenom(resultSet.getString("prenom"));
                u.setAdresse(resultSet.getString("adresse"));
                u.setTelephone(resultSet.getString("telephone"));
                u.setEmail(resultSet.getString("email"));

//                Ajouter les listes task

                users.add(u);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return users;
    }
}
