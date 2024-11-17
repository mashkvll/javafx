package com.example.demonn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static java.sql.DriverManager.getConnection;



public class DB {
    public static String current_user_role;
    public static int current_user_id;
    public static String current_user_fio;

    private final String HOST = "localhost";
    private final String PORT = "13306";
    private final String DB_NAME = "exampledb";
    private final String LOGIN = "exampleuser"; // Если OpenServer, то здесь mysql напишите
    private final String PASS = "examplepassword"; // Если OpenServer, то здесь mysql напишите

    private Connection dbConn = null;

    private Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connStr = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME + "?characterEncoding=UTF8";
        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConn = getConnection(connStr, LOGIN, PASS);
        return dbConn;

        //PreparedStatement statement = getDbConnection().prepareStatement(String);
    }

    public boolean check_user(String login, String password) throws ClassNotFoundException, SQLException {
        String sql = "SELECT * FROM users WHERE loginUser = '" + login + "' AND passwordUser = '" + password + "'";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            current_user_id = resultSet.getInt("idUser");
            current_user_fio = resultSet.getString("fioUser");

            sql = "SELECT * FROM roles WHERE idRole = '" + resultSet.getInt("idRole") + "'";
            statement = getDbConnection().prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                current_user_role = resultSet.getString("nameRole");
                return true;
            }
        }
        return false;
    }

    public void set_guest(){
        current_user_id = 0;
        current_user_fio = "Гость";
        current_user_role = "Гость";
    }


    public void setlikes(int inc, int attractId) throws ClassNotFoundException, SQLException {
        String sql = "UPDATE attract SET countPositiv = countPositiv+"+inc+" WHERE idAttr = "+attractId+";";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.executeUpdate();
    }
    public void setdislikes(int inc, int attractId) throws ClassNotFoundException, SQLException {
        String sql = "UPDATE attract SET countNegativ = countNegativ+"+inc+" WHERE idAttr = "+attractId+";";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.executeUpdate();
    }
    public int getlikes(int idAttract) throws ClassNotFoundException, SQLException {
        String sql = "SELECT * FROM attract WHERE idAttr = "+idAttract+";";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            return resultSet.getInt("countPositiv");
        }
        return 0;
    }
    public int getdislikes(int idAttract) throws ClassNotFoundException, SQLException {
        String sql = "SELECT * FROM attract WHERE idAttr = "+idAttract+";";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            return resultSet.getInt("countNegativ");
        }
        return 0;
    }

    public ResultSet get_reviews() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM reviews WHERE idUser = "+current_user_id+";";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        return resultSet;
    }

    public void add_review(String text) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO reviews(textRev,idUser) VALUES('"+text+"','"+current_user_id+"')";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.executeUpdate();
    }


    public String get_category(int idAttract) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM category c, attract a WHERE a.idAttr="+idAttract+" and a.idCat=c.idCat;";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            return resultSet.getString(2);
        }
        return null;
    }

    public ResultSet get_users() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM users";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        return resultSet;
    }

    public ResultSet get_attracts() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM attract";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        return resultSet;
    }

    public boolean create_order(String userEmail, String attractName, LocalDate date) throws SQLException, ClassNotFoundException {
        int idUser = 0;
        int idAttract = 0;


        String sql = "SELECT idUser FROM users WHERE loginUser = '"+userEmail+"'";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            idUser = resultSet.getInt(1);
        }

        sql = "SELECT idAttr FROM attract WHERE nameAttr = '"+attractName+"'";
        statement = getDbConnection().prepareStatement(sql);
        resultSet = statement.executeQuery();
        while (resultSet.next()){
            idAttract = resultSet.getInt(1);
        }

        if ((idUser != 0)&&(idAttract != 0)){
            sql = "INSERT INTO orders(idUser,idAttr,dateAttr) VALUES('"+idUser+"','"+idAttract+"','"+date+"')";
            statement = getDbConnection().prepareStatement(sql);
            return statement.executeUpdate() == 1;
        }
        return false;

    }
}
