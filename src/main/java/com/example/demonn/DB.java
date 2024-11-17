package com.example.demonn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static java.sql.DriverManager.getConnection;

public class DB {
    // Статические переменные для хранения текущего пользователя
    public static String current_user_role; // Роль текущего пользователя
    public static int current_user_id; // ID текущего пользователя
    public static String current_user_fio; // ФИО текущего пользователя

    // Параметры подключения к базе данных
    private final String HOST = "localhost"; // Хост базы данных
    private final String PORT = "13306"; // Порт базы данных
    private final String DB_NAME = "exampledb"; // Имя базы данных
    private final String LOGIN = "exampleuser"; // Логин для подключения к базе данных
    private final String PASS = "examplepassword"; // Пароль для подключения к базе данных

    private Connection dbConn = null; // Переменная для хранения соединения с базой данных

    // Метод для получения соединения с базой данных
    private Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connStr = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME + "?characterEncoding=UTF8";
        Class.forName("com.mysql.cj.jdbc.Driver"); // Загрузка драйвера MySQL

        dbConn = getConnection(connStr, LOGIN, PASS); // Установка соединения с базой данных
        return dbConn;
    }

    // Метод для проверки пользователя по логину и паролю
    public boolean check_user(String login, String password) throws ClassNotFoundException, SQLException {
        String sql = "SELECT * FROM users WHERE loginUser = '" + login + "' AND passwordUser = '" + password + "'";
        PreparedStatement statement = getDbConnection().prepareStatement(sql); // Подготовка SQL-запроса
        ResultSet resultSet = statement.executeQuery(); // Выполнение запроса и получение результата

        while (resultSet.next()) { // Обработка результатов запроса
            current_user_id = resultSet.getInt("idUser"); // Получение ID пользователя
            current_user_fio = resultSet.getString("fioUser"); // Получение ФИО пользователя

            sql = "SELECT * FROM roles WHERE idRole = '" + resultSet.getInt("idRole") + "'";
            statement = getDbConnection().prepareStatement(sql); // Подготовка запроса для получения роли пользователя
            resultSet = statement.executeQuery(); // Выполнение запроса на получение роли

            while (resultSet.next()) { // Обработка результатов запроса на роль
                current_user_role = resultSet.getString("nameRole"); // Получение названия роли
                return true; // Успешная проверка пользователя
            }
        }
        return false; // Пользователь не найден или неверные данные
    }

    // Метод для установки гостевого пользователя
    public void set_guest() {
        current_user_id = 0; // ID гостя равен 0
        current_user_fio = "Гость"; // ФИО гостя
        current_user_role = "Гость"; // Роль гостя
    }

    // Метод для установки лайков к достопримечательности
    public void setlikes(int inc, int attractId) throws ClassNotFoundException, SQLException {
        String sql = "UPDATE attract SET countPositiv = countPositiv+" + inc + " WHERE idAttr = " + attractId + ";";
        PreparedStatement statement = getDbConnection().prepareStatement(sql); // Подготовка SQL-запроса на обновление лайков
        statement.executeUpdate(); // Выполнение обновления в базе данных
    }

    // Метод для установки дизлайков к достопримечательности
    public void setdislikes(int inc, int attractId) throws ClassNotFoundException, SQLException {
        String sql = "UPDATE attract SET countNegativ = countNegativ+" + inc + " WHERE idAttr = " + attractId + ";";
        PreparedStatement statement = getDbConnection().prepareStatement(sql); // Подготовка SQL-запроса на обновление дизлайков
        statement.executeUpdate(); // Выполнение обновления в базе данных
    }

    // Метод для получения количества лайков у достопримечательности
    public int getlikes(int idAttract) throws ClassNotFoundException, SQLException {
        String sql = "SELECT * FROM attract WHERE idAttr = " + idAttract + ";";
        PreparedStatement statement = getDbConnection().prepareStatement(sql); // Подготовка SQL-запроса на получение лайков
        ResultSet resultSet = statement.executeQuery(); // Выполнение запроса и получение результата

        while (resultSet.next()) {
            return resultSet.getInt("countPositiv"); // Возвращаем количество лайков
        }
        return 0; // Если нет результатов, возвращаем 0
    }

    // Метод для получения количества дизлайков у достопримечательности
    public int getdislikes(int idAttract) throws ClassNotFoundException, SQLException {
        String sql = "SELECT * FROM attract WHERE idAttr = " + idAttract + ";";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            return resultSet.getInt("countNegativ");
        }
        return 0;
    }

    // Метод для получения отзывов текущего пользователя
    public ResultSet get_reviews() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM reviews WHERE idUser = " + current_user_id + ";";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        return resultSet;
    }

    // Метод для добавления отзыва текущего пользователя
    public void add_review(String text) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO reviews(textRev,idUser) VALUES('" + text + "','" + current_user_id + "')";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.executeUpdate();
    }

    // Метод для получения категории достопримечательности по её ID
    public String get_category(int idAttract) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM category c, attract a WHERE a.idAttr=" + idAttract + " and a.idCat=c.idCat;";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()){
            return resultSet.getString(2); // Возвращаем название категории из результата запроса
        }
        return null;
    }

    // Метод для получения всех пользователей из базы данных
    public ResultSet get_users() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM users";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        return resultSet;
    }

    // Метод для получения всех достопримечательностей из базы данных
    public ResultSet get_attracts() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM attract";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        return resultSet;
    }

    // Метод для создания заказа на экскурсию пользователем по электронной почте и названию достопримечательности на определённую дату
    public boolean create_order(String userEmail, String attractName, LocalDate date) throws SQLException, ClassNotFoundException {
        int idUser = 0;
        int idAttract = 0;

        String sql = "SELECT idUser FROM users WHERE loginUser = '" + userEmail + "'";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()){
            idUser = resultSet.getInt(1);
        }

        sql = "SELECT idAttr FROM attract WHERE nameAttr = '" + attractName + "'";
        statement = getDbConnection().prepareStatement(sql);
        resultSet = statement.executeQuery();

        while (resultSet.next()){
            idAttract = resultSet.getInt(1);
        }

        if ((idUser != 0)&&(idAttract != 0)){
            sql = "INSERT INTO orders(idUser,idAttr,dateAttr) VALUES('" + idUser + "','" + idAttract + "','" + date + "')";
            statement = getDbConnection().prepareStatement(sql);
            return statement.executeUpdate() == 1; // Возвращаем результат выполнения операции вставки в базу данных (успех или неудача)
        }

        return false;
    }
}