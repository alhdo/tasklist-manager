package xyz.alhdo.tasklist.database;

import java.io.File;
import java.nio.file.Path;
import java.sql.*;

public class SQLiteJDBCDriverConnection {
    /**
     * Connect to a sample database
     */
    public static Connection connection;

    private String url = "jdbc:sqlite:database/tasklist.db";

    private SQLiteJDBCDriverConnection(){
        try {
            File file = new File("database");
//            String url = "";
            if(!file.exists() || !file.isDirectory()){
                file.mkdir();

            }
            connection = DriverManager.getConnection(url);
            initializeDatabase();

        } catch (SQLException e) {
           e.printStackTrace();
        }
    }
    public static Connection getInstance() {
        if(connection==null){
            new SQLiteJDBCDriverConnection();
        }
        return connection;

    }

    private static void initializeDatabase(){
        String createUserTable = "CREATE TABLE IF NOT EXISTS users (id integer PRIMARY KEY AUTOINCREMENT, nom text NOT NULL, prenom text NOT NULL, adresse text, telephone text, email text);";

        String createTaskTable = "CREATE TABLE IF NOT EXISTS tasks (id integer PRIMARY KEY AUTOINCREMENT, nom text NOT NULL, description text, datedebut DATETIME, datefin DATETIME, etat integer  DEFAULT 0);";

        String createUserTaskTable = "CREATE TABLE IF NOT EXISTS user_tasks (id integer PRIMARY KEY AUTOINCREMENT, user_id integer NOT NULL, task_id integer NOT NULL);";

        try {
            Statement statement = connection.createStatement();
            statement.execute(createUserTable);
            statement.execute(createTaskTable);
            statement.execute(createUserTaskTable);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }



}
