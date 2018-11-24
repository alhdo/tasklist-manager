package xyz.alhdo.tasklist.database.dao;

import xyz.alhdo.tasklist.models.Task;
import xyz.alhdo.tasklist.utils.DateUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDao extends DAO<Task> {
    public TaskDao(Connection connection) {
        super(connection);
    }

    @Override
    public boolean create(Task obj) {
        try {
            String generatedColumns[] = { "id" };
            String query = "INSERT INTO tasks(nom, description, datedebut, datefin, etat) VALUES (?,?,?,?,?);";
            PreparedStatement preparedStatement = this.connection.prepareStatement(query, generatedColumns);

            preparedStatement.setString(1,obj.getNom());
            preparedStatement.setString(2, obj.getDescription());
            preparedStatement.setDate(3 , DateUtil.transformUtilToSql(obj.getDateDebut()));
            preparedStatement.setDate(4, DateUtil.transformUtilToSql(obj.getDateFin()));
            preparedStatement.setInt(5, obj.getEtat());

            preparedStatement.execute();

            if(obj.getUser()!=null){
                // Here you insert in the midlle table
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if(resultSet.next()){
                    String q = "INSERT INTO user_tasks (user_id, task_id) VALUES (?,?);";

                    PreparedStatement pStat = this.connection.prepareStatement(q);

                    pStat.setInt(1,obj.getUser().getId());
                    pStat.setInt(2, resultSet.getInt(1));
                    pStat.execute();

                }


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
        List<Task> tasks = new ArrayList<>();
        try{
            String request= "SELECT * FROM tasks";
            PreparedStatement preparedStatement =this.connection.prepareStatement(request);
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                Task task = new Task();
                task.setId(resultSet.getInt("id"));
                task.setDescription(resultSet.getString("description"));
                task.setNom(resultSet.getString("nom"));
                task.setDateFin(resultSet.getDate("datefin"));
                task.setDateDebut(resultSet.getDate("datedebut"));
                task.setEtat(resultSet.getInt("etat"));
                tasks.add(task);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return tasks;
    }
}
