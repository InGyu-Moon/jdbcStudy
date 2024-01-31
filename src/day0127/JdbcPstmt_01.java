package day0127;

import oracle.DbConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class JdbcPstmt_01 {

    DbConnect db = new DbConnect();
    Scanner sc = new Scanner(System.in);


    public void insert(){

        Connection conn = db.getOracle();

        PreparedStatement pstmt = null;

        String type = sc.nextLine();
        String sang = sc.nextLine();
        int su = Integer.parseInt(sc.nextLine());
        int dan = Integer.parseInt(sc.nextLine());



        String sql = "insert into values (seq1.nextval,?,?,?,?,sysdate)";

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,type);
            pstmt.setString(2,sang);
            pstmt.setInt(3,su);
            pstmt.setInt(4,dan);

            int i = pstmt.executeUpdate();
            if(i==1)
                System.out.println("insert 성공");
            else
                System.out.println("insert 실패");


        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            db.dbClose(pstmt,conn);
        }


    }



    public static void main(String[] args) {

    }




}
