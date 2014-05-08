/*
NAME:   XIAOYANG JU
ID: 7342138151
*/
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBexecute {
    Connection mainConnection = null;
    Statement mainStatement = null;
    ResultSet mainResultset = null;

    public void connect(){
        try {
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
            String URL = "jdbc:oracle:thin:@localhost:1521:hw2";
            String userName = "system";
            String password = "hw2";
            mainConnection = DriverManager.getConnection(URL, userName, password);
            mainStatement = mainConnection.createStatement();
        }
        catch (Exception e)
        {
            System.out.println( "Error while connecting to DB: "+ e.toString() );
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public void insert(String query){
        try {
            mainStatement.executeUpdate(query);
        }
        catch (Exception e) {
            System.out.println(" insert Error: " + e.toString() );
        }
    }

    public void delete(String data){
        try {
            mainStatement.executeUpdate("delete from "+data);
        }
        catch(Exception e) {
            System.out.println( " delete Error: " + e.toString() );
        }   
    }

    public void close() {
        try {
            mainConnection.close();
        }
        catch (Exception e) {
            System.out.println( " close Error: " + e.toString() );
        }
    }

    public ResultSet getResultSet(String query) {
        try {
            mainResultset = mainStatement.executeQuery(query);
        }
        catch (Exception e) {
            System.out.println( " query executing Error: " + e.toString() );
        }
        return mainResultset;
    }
}



















