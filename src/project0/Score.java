package project0;

import oracle.DbConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Score {
    DbConnect db = new DbConnect();
    Scanner sc = new Scanner(System.in);

    public void insert(int memberId){
        if(checkByMemberId(memberId)){
            System.out.println("\n---성적 입력 실패---");
            System.out.println("---이미 성적을 입력하셨습니다---\n");
            return;
        }

        System.out.println("\n---성적을 입력하세요---");

        System.out.print("국어: ");
        int korean = Integer.parseInt(sc.nextLine());
        System.out.print("영어: ");
        int english = Integer.parseInt(sc.nextLine());
        System.out.print("수학: ");
        int math = Integer.parseInt(sc.nextLine());

        Connection conn = null;
        PreparedStatement pstmt = null;

        String sql ="insert into STUDENT_SCORES values(seq_member.nextval,?,?,?,?)";

        conn = db.getOracle();

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,memberId);
            pstmt.setInt(2,korean);
            pstmt.setInt(3,english);
            pstmt.setInt(4,math);

            int i = pstmt.executeUpdate();
            if(i==1)
                System.out.println("---성적 입력이 완료되었습니다---\n");
            else
                System.out.println("---성적 입력에 실패하였습니다---\n");

        } catch (SQLException e) {
            System.out.println("e = " + e);
        }finally {
            db.dbClose(pstmt,conn);
        }
    }

    public boolean checkByMemberId(int memberId){
        boolean flag = false;
        String sql = "select * from STUDENT_SCORES where member_id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        conn = db.getOracle();
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,memberId);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            db.dbClose(rs,pstmt,conn);
        }
        return flag;
    }

    public void selectByMemberId(int memberId){

        String sql = "select korean,english,math from STUDENT_SCORES where member_id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        conn = db.getOracle();
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,memberId);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("\n---성적을 출력합니다---");

                System.out.println("국어: " + rs.getString("korean"));
                System.out.println("영어: " + rs.getString("english"));
                System.out.println("수학: " + rs.getString("math"));
                System.out.println("---성적을 출력 완료---\n");
            }
            else{
                System.out.println("\n---성적 조회 실패---");
                System.out.println("---입력된 성적이 없습니다. 성적을 입력하세요---\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            db.dbClose(rs,pstmt,conn);
        }
    }
    public void updateByMemberId(int memberId){
        if(!checkByMemberId(memberId)){
            System.out.println("\n---성적 수정 실패---");
            System.out.println("---입력된 성적이 없습니다. 성적을 입력하세요---\n");
            return;
        }

        System.out.println("\n---성적을 수정합니다. 다시 입력해주세요---");
        System.out.print("국어: ");
        int korean = Integer.parseInt(sc.nextLine());
        System.out.print("영어: ");
        int english = Integer.parseInt(sc.nextLine());
        System.out.print("수학: ");
        int math = Integer.parseInt(sc.nextLine());

        String sql="update STUDENT_SCORES set korean=?,english=?,math=? where member_id=?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        conn = db.getOracle();

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,korean);
            pstmt.setInt(2,english);
            pstmt.setInt(3,math);
            pstmt.setInt(4,memberId);

            int i = pstmt.executeUpdate();
            if(i==1)
                System.out.println("\n---성적 수정이 완료되었습니다---\n");
            else
                System.out.println("\n---성적 수정에 실패하였습니다---\n");

        } catch (SQLException e) {
            System.out.println("e = " + e);
        } finally {
            db.dbClose(pstmt, conn);
        }
    }

    public void deleteByMemberId(int memberId){
        if(!checkByMemberId(memberId)){
            System.out.println("\n---성적 삭제 실패---");
            System.out.println("---입력된 성적이 없습니다. 성적을 입력하세요---\n");
            return;
        }
        System.out.println("\n---성적을 삭제합니다---");

        String sql="delete from STUDENT_SCORES where member_id=?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        conn = db.getOracle();

        try {
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,memberId);

            int i = pstmt.executeUpdate();
            if(i==1)
                System.out.println("---성적 삭제가 완료되었습니다---\n");
            else
                System.out.println("---성적 삭제가 실패하였습니다---\n");

        } catch (SQLException e) {
            System.out.println("e = " + e);
        } finally {
            db.dbClose(pstmt, conn);
        }
    }

}
