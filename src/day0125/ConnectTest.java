package day0125;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectTest {

    static final String URL="jdbc:oracle:thin:@localhost:1521:XE";

    public void connectSawon(){
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        String sql = "select * from sawon order by num";


        try {
            conn= DriverManager.getConnection(URL,"root","1234");
            System.out.println("오라클 드라이버 연결 성공!!!");

            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            int i=1;
            while(rs.next()){
                int num = rs.getInt("num");
                String name = rs.getString("name");
                String gender = rs.getString("gender");
                String buseo = rs.getString("buseo");
                int pay = rs.getInt("pay");

                System.out.println(num+"\t"+name+"\t"+gender+"\t"+buseo+"\t"+pay);
            }

        } catch (SQLException e) {
            System.out.println("e = " + e);
            System.out.println("오라클 드라이버 연결 실패!!!");
        }finally {
            try {
                if (rs != null)
                    rs.close();
                if(stmt != null)
                    stmt.close();
                if(conn != null)
                    conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void connectShop(){
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        String sql = "select cart2.idx, cart2.num,shop.sangpum, shop.color,cart2.cnt,cart2.guipday from cart2, shop where cart2.num=shop.num";

        try {
            conn= DriverManager.getConnection(URL,"root","1234");
            System.out.println("오라클 드라이버 연결 성공!!!");

            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while(rs.next()){
                int idx = rs.getInt("idx");
                String num = rs.getString("num");
                String sangpum = rs.getString("sangpum");
                String color = rs.getString("color");
                int cnt = rs.getInt("cnt");
                String guipday = rs.getString("guipday");

                System.out.println(idx + "\t" + num + "\t" + sangpum + "\t" + color + "\t" + cnt + "\t" + guipday);
            }

        } catch (SQLException e) {
            System.out.println("e = " + e);
            System.out.println("오라클 드라이버 연결 실패!!!");
        }finally {
            try {
                if (rs != null)
                    rs.close();
                if(stmt != null)
                    stmt.close();
                if(conn != null)
                    conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void sawonRead(){
        //부서별 인원수 평균 급여
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String sql = "select buseo, count(*) cnt, round(avg(pay),0) pay from sawon group by buseo";

        System.out.println("\t[부서별 평균 급여 및 인원 구하기]");
        System.out.println("부서명  인원\t평균급여");
        System.out.println("=============================");

        try {
            conn= DriverManager.getConnection(URL,"root","1234");
            //System.out.println("오라클 드라이버 연결 성공!!!");

            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while(rs.next()){
                String buseo = rs.getString("buseo");
                int cnt = rs.getInt("cnt");
                int pay = rs.getInt("pay");

                System.out.println(buseo + "\t" + cnt + "\t" + pay);
            }

        } catch (SQLException e) {
            System.out.println("e = " + e);
            System.out.println("오라클 드라이버 연결 실패!!!");
        }finally {
            try {
                if (rs != null)
                    rs.close();
                if(stmt != null)
                    stmt.close();
                if(conn != null)
                    conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


    }

    public static void main(String[] args) {
        ConnectTest ct = new ConnectTest();
        ct.sawonRead();
    }
}
