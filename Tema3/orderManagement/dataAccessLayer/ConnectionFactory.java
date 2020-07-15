package orderManagement.dataAccessLayer;

import java.sql.*;
import java.util.logging.Logger;

public class ConnectionFactory {

    private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DBURL = "jdbc:mysql://localhost:3306/order_management?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "march299";

    private static ConnectionFactory singleInstance = new ConnectionFactory();


    private ConnectionFactory(){
            try {
                Class.forName(DRIVER);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

    }

    /**
     * Method used for enstablishing a new connection
     * @return returns the connection status and enstablishes the connection
     */
    public static Connection getConnection()  {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DBURL, USER, PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;

    }

    /**
     * Method used for closing a connection
     * @param connection name of the connection
     */
    public static void close(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method used for closing a statement
     * @param statement name of the statement we are closing
     */
    public static void close (Statement statement)  {
        try {
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method used for closing a result set
     * @param resultSet name of the result set we are closign
     */
    public static void close(ResultSet resultSet){
        try {
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
