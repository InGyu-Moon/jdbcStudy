package day0126;

import oracle.DbConnect;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.EnumMap;
import java.util.Enumeration;
import java.util.Scanner;

public class HelloCrud {
    DbConnect db = new DbConnect();

    public void insert(){
        String sql;
        Scanner sc = new Scanner(System.in);

        System.out.print("이름을 입력하세요: ");
        String name = sc.nextLine();
        System.out.print("주소를 입력하세요: ");
        String addr = sc.nextLine();

        sql ="insert into hello values(seq1.nextval,'"+name+"', '"+addr+"', sysdate)";
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

        System.out.println("시쿼스\t이름\t\t주소\t\t가입날짜");
        System.out.println("=========================================");

        String sql = "select * from hello";
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        conn = db.getOracle();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int num = rs.getInt("num");
                String name = rs.getString("name");
                String addr = rs.getString("addr");
                String sdate = rs.getString("sdate");

                System.out.println(num + "\t\t" + name + "\t\t" + addr + "\t\t" + sdate);
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
        String num;
        String sql="";

        System.out.println("삭제할 번호 입력");
        num=sc.nextLine();

        sql="delete from hello where num="+num;
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


    }

    //수정하려는 데이타 조회
    public boolean getOneData(String num)
    {
        boolean flag=false;//num에 해당하는 데이타가있으면 true,없으면 false

        String sql="select * from hello where num="+num;

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
        String num,name,addr;

        System.out.println("수정할 번호를 입력");
        num=sc.nextLine();

        if(!this.getOneData(num))
        {
            System.out.println("해당번호는 존재하지 않습니다");
            return; //메서드 종료
        }

        System.out.println("수정할 이름으로 변경해주세요");
        name=sc.nextLine();
        System.out.println("수정할 주소로 변경해주세요");
        addr=sc.nextLine();

        String sql="update hello set name='"+name+"',addr='"+addr+"' where num="+num;
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

    public static void main(String[] args) {
        HelloCrud helloCrud = new HelloCrud();

        Scanner sc = new Scanner(System.in);
        while(true){

            System.out.println("**hello db test**");
            System.out.println("1.insert  2.select  3.delete  4.update  9.exit");
            System.out.print("입력: ");
            int n = Integer.parseInt(sc.nextLine());
            if (n == 1) {
                helloCrud.insert();
            } else if (n == 2) {
                helloCrud.select();
            } else if (n == 3) {
                helloCrud.delete();
            } else if (n == 4) {
                helloCrud.update();
            }
            else if (n == 9) {
                System.out.println("exit...");
                break;
            }

        }

    }


}
