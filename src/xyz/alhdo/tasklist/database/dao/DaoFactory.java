package xyz.alhdo.tasklist.database.dao;

import xyz.alhdo.tasklist.database.SQLiteJDBCDriverConnection;

import java.sql.Connection;

public class DaoFactory {
    protected static final Connection connection = SQLiteJDBCDriverConnection.getInstance();

    /**
     * Retourne un objet User connecter avec la Base de donnee
     * @return DAO
     */
    public static DAO getUserDao(){
        return new UserDao(connection);
    }

    /**
     * Retourne un objet Task connecter avec la Base de donnee
     * @return DAO
     */

    public static DAO getTaskDao(){
        return  new TaskDao(connection);
    }
}
