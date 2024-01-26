package oracle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbConnect {

    public static final String ORACLE_DB = "jdbc:oracle:thin:@localhost:1521:XE";

    public Connection getOracle(){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(ORACLE_DB, "root", "1234");
        } catch (SQLException e) {
            System.out.println("로컬 오라클 연결 실패");
        }
        return conn;
    }

    public void dbClose(ResultSet rs, Statement stmt, Connection conn) {
        try {
            if(rs != null)
                rs.close();
            if(stmt != null)
                stmt.close();
            if(conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void dbClose(Statement stmt, Connection conn) {
        try {
            if(stmt != null)
                stmt.close();
            if(conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void dbClose(ResultSet rs, PreparedStatement stmt, Connection conn) {
        try {
            if(rs != null)
                rs.close();
            if(stmt != null)
                stmt.close();
            if(conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void dbClose(PreparedStatement stmt, Connection conn) {
        try {
            if(stmt != null)
                stmt.close();
            if(conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
    }
}
