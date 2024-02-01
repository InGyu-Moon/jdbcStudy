package project0;

import oracle.DbConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.Scanner;

public class Member {
    DbConnect db = new DbConnect();
    Scanner sc = new Scanner(System.in);

    //로그인 확인
    public int login(String name, String pw){
        int memberId = 0;

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        String sql = "select member_id, password from member where username = " + "'"+name+"'";

        conn = db.getOracle();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            if(rs.next()){
                if(Objects.equals(pw, rs.getString("password")))
                    memberId = rs.getInt("member_id");
            }

        } catch (SQLException e) {
            System.out.println("e = " + e);
        } finally {
            db.dbClose(rs, stmt, conn);
        }
        return memberId;
    }

    public void newMember(String name, String pw) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        conn = db.getOracle();
        String sql = "insert into member values (seq_member.nextval,?,?,sysdate,?)";

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,name);
            pstmt.setString(2,pw);
            pstmt.setString(3,"STUDENT");
            pstmt.executeUpdate();
            System.out.println("\n---회원가입이 완료되었습니다---\n");
        } catch (SQLException e) {
            System.out.println("e = " + e);
        } finally {
            db.dbClose(pstmt,conn);
        }
    }

    public String findUsernameByMemberId(int memberId){


        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String sql = "select username from member where member_id = ?";

        conn = db.getOracle();
        try {
            pstmt=conn.prepareStatement(sql);
            pstmt.setInt(1, memberId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("username");
            }
        } catch (SQLException e) {
            System.out.println("e = " + e);
        } finally {
            db.dbClose(rs,pstmt,conn);
        }
        return null;
    }
}
