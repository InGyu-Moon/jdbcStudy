package day0126;

import oracle.DbConnect;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class QuizStuInfo {

    DbConnect db = new DbConnect();
    public void insert(){
        String sql;
        Scanner sc = new Scanner(System.in);

        System.out.print("이름을 입력하세요: ");
        String s_name = sc.nextLine();
        System.out.print("성별을 입력하세요: ");
        String s_gender = sc.nextLine();
        System.out.print("나이를 입력하세요: ");
        String s_age = sc.nextLine();
        System.out.print("연락처를 입력하세요: ");
        String s_hp = sc.nextLine();

        sql ="insert into stuinfo values(seq_info.nextval,'"+s_name+"', '"+s_gender+"', '"+s_age+"', '"+s_hp+"', sysdate)";
        System.out.println("sql = " + sql);

        Connection conn = null;
        Statement stmt = null;

        conn = db.getOracle();
        try {
            stmt = conn.createStatement();
            stmt.execute(sql);
            System.out.println("**data 추가 완료**");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            db.dbClose(stmt,conn);
        }
    }
    public void select(){

        System.out.println("시쿼스\t이름\t\t\t성별\t\t나이\t\t전화번호\t\t가입날짜");
        System.out.println("=============================================================");

        String sql = "select * from stuinfo order by s_no";
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        conn = db.getOracle();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int s_no = rs.getInt("s_no");
                String s_name = rs.getString("s_name");
                String s_gender = rs.getString("s_gender");
                String s_age = rs.getString("s_age");
                String s_hp = rs.getString("s_hp");
                String gaipday = rs.getString("gaipday");

                System.out.println(s_no + "\t\t" + s_name + "\t\t" + s_gender + "\t\t" + s_age + "\t\t" + s_hp + "\t\t" + gaipday);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            db.dbClose(rs,stmt,conn);
        }
    }
    public void delete()
    {
        //삭제할 번호 입력후 삭제
        Scanner sc=new Scanner(System.in);
        String s_no;
        String sql="";

        System.out.println("삭제할 번호 입력");
        s_no=sc.nextLine();

        sql="delete from stuinfo where s_no="+s_no;
        System.out.println(sql);

        Connection conn=null;
        Statement stmt=null;

        conn=db.getOracle();
        try {
            stmt=conn.createStatement();

            int a=stmt.executeUpdate(sql); //성공한 레코드의갯수

            //없는 번호 입력시 실제 삭제가 안되므로 0이반환
            if(a==0)
                System.out.println("없는 데이타 번호입니다");
            else
                System.out.println("***삭제되었습니다***");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            db.dbClose(stmt, conn);
        }
        System.out.println("=========================================");


    }

    //수정하려는 데이타 조회
    public boolean getOneData(String s_no)
    {
        boolean flag=false;//num에 해당하는 데이타가있으면 true,없으면 false

        String sql="select * from stuinfo where s_no="+s_no;

        Connection conn=null;
        Statement stmt=null;
        ResultSet rs=null;

        conn=db.getOracle();
        try {
            stmt=conn.createStatement();
            rs=stmt.executeQuery(sql);

            //한개만 조회할경우는 if문
            if(rs.next()) //데이타가 있는경우
                flag=true;
            else
                flag=false;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            db.dbClose(rs, stmt, conn);
        }



        return flag;
    }

    //수정
    public void update()
    {
        //수정할 시퀀스를 입력후 이름,주소 입력
        Scanner sc=new Scanner(System.in);
        String s_no, s_name, s_gender, s_age, s_hp;

        System.out.println("수정할 번호를 입력");
        s_no=sc.nextLine();

        if(!this.getOneData(s_no))
        {
            System.out.println("해당번호는 존재하지 않습니다");
            return; //메서드 종료
        }

        //학생명,성별,나이 ,연락처 수정
        System.out.println("수정할 이름으로 변경해주세요");
        s_name=sc.nextLine();
        System.out.println("수정할 성별로 변경해주세요");
        s_gender=sc.nextLine();
        System.out.println("수정할 나이로 변경해주세요");
        s_age=sc.nextLine();
        System.out.println("수정할 연락처로 변경해주세요");
        s_hp=sc.nextLine();

        String sql="update stuinfo set s_name='"+s_name+"',s_gender='"+s_gender+"',s_age='"+s_age+"',s_hp='"+s_hp+"' where s_no="+s_no;
        System.out.println(sql);

        Connection conn=null;
        Statement stmt=null;

        conn=db.getOracle();
        try {
            stmt=conn.createStatement();

            int a=stmt.executeUpdate(sql);

            if(a==0)
                System.out.println("수정할 데이타가 존재하지 않습니다");
            else
                System.out.println("***수정되었습니다***");

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            db.dbClose(stmt, conn);
        }
    }

    public void search(){
        Scanner sc = new Scanner(System.in);
        System.out.print("검색할 내용을 입력하세요: ");
        String input = sc.nextLine();

        System.out.println("시쿼스\t이름\t성별\t나이\t전화번호\t가입날짜");
        System.out.println("=========================================");

        String sql = "select * from stuinfo where s_name like '%"+ input +"%' order by s_no";
        //System.out.println("sql = " + sql);

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        conn = db.getOracle();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int s_no = rs.getInt("s_no");
                String s_name = rs.getString("s_name");
                String s_gender = rs.getString("s_gender");
                String s_age = rs.getString("s_age");
                String gaipday = rs.getString("gaipday");

                System.out.println(s_no + "\t\t" + s_name + "\t\t" + s_gender + "\t\t" + s_age + "\t\t" + gaipday);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            db.dbClose(rs,stmt,conn);
        }
        System.out.println("=========================================");
    }




    public static void main(String[] args) {
        QuizStuInfo quizStuInfo = new QuizStuInfo();

        Scanner sc = new Scanner(System.in);
        while(true){

            System.out.println("**stuinfo db test**");
            System.out.println("1.학생정보 입력  2.학생정보 출력  3.학생정보 삭제  4.학생정보 수정  5. 학생 검색  9.종료");
            System.out.print("입력: ");
            int n = Integer.parseInt(sc.nextLine());
            if (n == 1) {
                quizStuInfo.insert();
            } else if (n == 2) {
                quizStuInfo.select();
            } else if (n == 3) {
                quizStuInfo.delete();
            } else if (n == 4) {
                quizStuInfo.update();
            } else if (n == 5) {
                quizStuInfo.search();
            }
            else if (n == 9) {
                System.out.println("exit...");
                break;
            }

        }
    }
}
