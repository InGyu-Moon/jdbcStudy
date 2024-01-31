package project0;

import java.util.Scanner;

public class MainProject {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Member member = new Member();
        Score score = new Score();
        Admin admin = new Admin();

        while (true) {
            System.out.println("1. 로그인   2. 회원가입   3. 관리자 로그인   9. 종료");
            int input = Integer.parseInt(sc.nextLine());

            // 1. 로그인
            if (input == 1) {
                //로그인 시도
                int memberId = tryLogin(sc, member); // 로그인 실패시 0 을 return 한다.

                // 로그인 성공
                if (memberId != 0) {
                    System.out.println("\n---로그인 성공---\n");

                    // 로그인 성공시 성적 입력,조회,수정,삭제 진행
                    scoreCrud(sc, score, memberId);
                }
                // 로그인 실패
                else {
                    System.out.println("\n---로그인 실패 : 이름과 비밀번호를 확인해주세요---\n");
                }
            }
            // 2. 회원가입
            else if (input == 2) {
                tryNewMember(sc, member);
            }
            // 3. 관리자 로그인
            else if (input == 3) {
                //로그인 시도
                int memberId = tryLogin(sc, member); // 로그인 실패시 0 을 return 한다.

                // 로그인 성공 && 관리자 인지 검사
                if (memberId != 0 && admin.checkIsAdminByMemberId(memberId)) {
                    System.out.println("\n---관리자 로그인 성공---\n");

                    //admin 성적 조회
                    adminMethod(sc, admin);
                }
                // 그외의 경우 즉, 로그인 실패 또는 로그인을 성공했지만 관리자가 아님
                else {
                    System.out.println("\n---로그인 실패 : 관리자 로그인에 실패헀습니다---\n");
                }
            }
            // 9. 종료
            else if (input == 9) {
                System.out.println("\n---종료---");
                break;
            }
        }
    }

    private static void adminMethod(Scanner sc, Admin admin) {
        while (true) {
            System.out.println("1. 전체 학생 성적 조회   9.로그아웃");
            int adminInput = Integer.parseInt(sc.nextLine());

            // 1. 성적 입력
            if (adminInput == 1) {
                admin.selectAll(); // 입력
            }
            // 9.로그아웃
            else if (adminInput == 9) {
                System.out.println("\n---로그아웃 되었습니다---\n");
                break;
            }
        }
    }


    private static void scoreCrud(Scanner sc, Score score, int memberId) {
        while (true) {
            System.out.println("1. 성적 입력   2. 성적 조회   3. 성적 수정   4. 성적 삭제   9.로그아웃");
            int scoreInput = Integer.parseInt(sc.nextLine());
            // 1. 성적 입력
            if (scoreInput == 1) {
                score.insert(memberId); // 입력
            }
            // 2. 성적 조회
            else if (scoreInput == 2) {
                score.selectByMemberId(memberId); // 조회
            }
            // 3. 성적 수정
            else if (scoreInput == 3) {
                score.updateByMemberId(memberId); // 수정
            }
            // 4. 성적 삭제
            else if (scoreInput == 4) {
                score.deleteByMemberId(memberId); // 삭제
            }
            // 9.로그아웃
            else if (scoreInput == 9) {
                System.out.println("\n---로그아웃 되었습니다---\n");
                break;
            }
        }
    }

    private static void tryNewMember(Scanner sc, Member member) {
        System.out.print("이름: ");
        String name = sc.nextLine();
        System.out.print("비밀번호: ");
        String pw = sc.nextLine();
        member.newMember(name, pw);
    }

    private static int tryLogin(Scanner sc, Member member) {
        System.out.println("\n---로그인 시도중---");
        System.out.print("이름: ");
        String name = sc.nextLine();
        System.out.print("비밀번호: ");
        String pw = sc.nextLine();

        return member.login(name, pw);
    }
}
