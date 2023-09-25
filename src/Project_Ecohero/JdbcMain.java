package Project_Ecohero;

import Project_Ecohero.Dao.FeedDao;
import Project_Ecohero.Dao.MembersDao;
import Project_Ecohero.Dao.MyPageDao;
import Project_Ecohero.Vo.MembersVo;

import java.util.Scanner;

public class JdbcMain {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
//        FeedDao dao = new FeedDao();
//        dao.feedMenu();
        MembersDao mDao = new MembersDao();

        boolean isLogin = false;
        String userId ="";
        System.out.println("에코히어로에 오신걸 환영합니다!");
        System.out.println("이용을 위해서는 로그인을 해야합니다. 회원이 아니라면 가입해주세요 :)");
        while (true) {
            System.out.print("[1] 로그인 [2]회원가입 [3] 종료하기 : ");
            int sel = sc.nextInt();
            switch(sel){
                case 1:
                    System.out.print("아이디 : ");
                    userId = sc.next();
                    System.out.print("비밀번호 : ");
                    String userPw = sc.next();
                    boolean memRs = mDao.checkMember(userId, userPw);
                    if (memRs) {
                        System.out.println("로그인 성공!");
                        isLogin = true;
                        break;
                    }else {
                        System.out.println("아이디 또는 비밀번호를 확인해주세요.");
                        continue;
                    }
                case 2:
                    mDao.insertNewMember();
                    isLogin = true;
                    break;
                case 3:
                    System.out.println("ECOHERO를 종료합니다.");
                    return;
                default :
                    System.out.println("메뉴를 잘 못 선택하셨습니다.");
            }
            if (isLogin) break;
        }
        while (isLogin) {
            MyPageDao myPageDao = new MyPageDao();
            MembersVo currMv = myPageDao.currMemberInfo(userId);
            System.out.println("Welcome to ECOHERO!!!");
            System.out.print("[1]챌린지 보기 [2] 피드 보기 [3] 내 정보 보기 [4] 종료하기 : ");
            int selMain = sc.nextInt();
            switch(selMain) {
                case 1 :
                    System.out.println("챌린지 메인 자리입니다!");
                    break;
                case 2 :
                    FeedDao feedDao = new FeedDao();
                    feedDao.feedMenu(currMv.getUserId(), currMv.getUserAlias());
                    break;
                case 3 :
                    myPageDao.memberMenu(userId);
                    break;
                case 4 :
                    System.out.println("ECOHERO를 종료합니다");
                    isLogin = false;
                    break;
                default :
                    System.out.println("메뉴를 잘 못 선택하셨습니다.");
            }
        }
    }
}
