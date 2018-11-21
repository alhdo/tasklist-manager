package xyz.alhdo.tasklist.database.dao;

import xyz.alhdo.tasklist.models.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TaskDao extends DAO<Task> {
    public TaskDao(Connection connection) {
        super(connection);
    }

    @Override
    public boolean create(Task obj) {
        try {
            String query = "INSERT INTO tasks(nom, description, datedebut, datefin, etat) VALUES (?,?,?,?,?);";
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);

            preparedStatement.setString(1,obj.getNom());
            preparedStatement.setString(2, obj.getDescription());
//            preparedStatement.setDate(3 , obj.getDateDebut());
//            preparedStatement.setDate(4, obj.getDateFin());
            preparedStatement.setInt(5, obj.getEtat());

            preparedStatement.execute();

            if(obj.getUser()!=null){
                // Here you insert in the midlle table
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Task obj) {
        return false;
    }

    @Override
    public boolean update(Task obj) {
        return false;
    }

    @Override
    public Task find(int id) {
        Task task = new Task();
        try {
            String query = "SELECT * FROM tasks where id = "+id;

            ResultSet resultSet = this.connection.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY).executeQuery(query);
            if(resultSet.first()){
                task = new Task(resultSet.getString("nom"));
                task.setDescription(resultSet.getString("description"));
                task.setEtat(resultSet.getInt(resultSet.getInt("etat")));
                // Here fill date
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return task;
    }

    @Override
    public List<Task> loadAll() {
        return null;
    }
}
