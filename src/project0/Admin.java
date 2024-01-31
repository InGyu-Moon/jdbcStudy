package project0;

import oracle.DbConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Admin {
    DbConnect db = new DbConnect();
    Scanner sc = new Scanner(System.in);
    public boolean checkIsAdminByMemberId(int memberId){
        boolean flag = false;

        String sql = "select role from member where member_id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        conn = db.getOracle();
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,memberId);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                String role = rs.getString("role");
                if(role.equals("ADMIN"))
                    flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            db.dbClose(rs,pstmt,conn);
        }
        return flag;
    }
    public void selectAll(){
        System.out.println("이름\t\t국어\t\t영어\t\t수학\t\t평균");
        System.out.println("=========================================");

        String sql = "SELECT ss.member_id, ss.korean, ss.english, ss.math, m.username, " +
                "(ss.korean + ss.english + ss.math) / 3 AS avg " +
                "FROM student_scores ss, member m " +
                "WHERE ss.member_id = m.member_id " +
                "ORDER BY avg DESC";

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        conn = db.getOracle();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int korean = rs.getInt("korean");
                int english = rs.getInt("english");
                int math = rs.getInt("math");
                double avg = rs.getDouble("avg");
                String username = rs.getString("username");
                System.out.println(username + "\t" + korean + "\t\t" + english + "\t\t" + math + "\t\t" + String.format("%.2f", avg));

            }
            System.out.println("=========================================");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            db.dbClose(rs,stmt,conn);
        }
    }
}
