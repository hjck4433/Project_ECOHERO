package Project_Ecohero.Dao;

import Project_Ecohero.Common.Common;
import Project_Ecohero.Vo.ChallengeVo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class ChallengeDao {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Scanner sc = new Scanner(System.in);

    // 챌린지 리스트 선택 -> 모든 챌린지의 목록 생성(리스트)
    public List<ChallengeVo> challengeSelect() {
        List<ChallengeVo> list = new ArrayList<>();
        try {
            conn = Common.getConnection();
            String sql = "SELECT CHL_NAME, CHL_ICON, CHL_LEVEL FROM CHALLENGE ORDER BY CHL_LEVEL"; //
            pstmt = conn.prepareStatement(sql); // 보낼 쿼리문을 준비하고 있다
            rs = pstmt.executeQuery();//반환을 위한 것. set 형식으로 리스트 받겠다.

            //한행씩 가져옴
            while (rs.next()) {
                String chlName = rs.getString("CHL_NAME");
                String chlIcon = rs.getString("CHL_ICON");
                String chlLevel = rs.getString("CHL_LEVEL");
                list.add(new ChallengeVo(chlName, chlIcon, chlLevel));
            }
            Common.close(rs);
            Common.close(pstmt);
            Common.close(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 챌린지 명을 입력 받아서 챌린지 세부 내용 조회
    // -> 하나하나의 챌린지 정보만을 조회하고 반환하므로 리스트로 작성하지 않음
    public ChallengeVo challengeDetails(List<ChallengeVo> chvl){

        ChallengeVo challenge = new ChallengeVo(); // 챌린지 정보 담을 객체 초기화
        String chlName;
        while(true) {
            System.out.print("조회할 챌린지 이름을 입력하세요: ");
            chlName = sc.next();
            String check = chlName;
            if(chvl.stream().filter(n -> check.equals(n.getChlName())).findAny().orElse(null) == null){
                System.out.println("없는 챌린지명입니다.");
            }else break;
        }

        try{
            conn = Common.getConnection();
            String sql = "SELECT CHL_LEVEL , CHL_DESC, TO_CHAR(CHL_DATE, 'YYYY-MM-DD') AS CHL_DATE, L.POINT\n" +
                    "FROM CHALLENGE C JOIN ECO_LV L ON C.CHL_LEVEL = L.LEVELS\n" + // JOIN 해서 레벨을 기준으로 포인트 값을 반환
                    "WHERE CHL_NAME = ?";
            pstmt = conn.prepareStatement(sql); // 보낼 쿼리문을 준비하고 있다
            pstmt.setString(1, chlName);        // ?에 챌린지 명으로 검색하기 위해서
            rs = pstmt.executeQuery();

            if (rs.next()) {
                String chlLevel = rs.getString("CHL_LEVEL"); // 챌린지 난이도
                String chlDesc = rs.getString("CHL_DESC");   // 챌린지 설명
                String chlDate = rs.getString("CHL_DATE");            // 챌린지 시작 날짜
                int chlPoint = rs.getInt("POINT");              // 획득 가능 포인트
                challenge = new ChallengeVo(chlName, chlLevel, chlDesc, chlDate, chlPoint); // 챌린지 정보
        }
            Common.close(rs);
            Common.close(pstmt);
            Common.close(conn);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return challenge;
    }

    // 내 챌린지 추가 기능
    public void addChallenge(List<ChallengeVo> challengeSelect) {
        String chlName;
        while(true) {
            System.out.print("등록하고 싶은 챌린지명을 입력해주세요 : ");
            chlName = sc.nextLine();
            String check = chlName;
            if(challengeSelect.stream().filter(n -> check.equals(n.getChlName())).findAny().orElse(null) != null){
                System.out.println("이미 사용 중인 챌린지명입니다.");
            }
            else if(chlName.length() > 40) System.out.println("챌린지명은 영문 40자, 한글 13자 이하로 입력해주세요.");
            else break;
        }
        String chlIcon;
        while(true){
            System.out.print("등록하고 싶은 챌린지 아이콘을 입력해주세요. : ");
            chlIcon = sc.next() ;
            String check = chlIcon;
            if(challengeSelect.stream().filter(n -> check.equals(n.getChlIcon())).findAny().orElse(null) != null) {
                System.out.println("이미 사용 중인 이미지 입니다.");
            }
            else break;
        }
        String chlDesc;
        while(true){
            System.out.print("챌린지 설명을 입력해주세요. : ");
            chlDesc = sc.next();
            if(chlDesc.length() > 300) System.out.println("챌린지 설명은 최대 300바이트 이내로 입력해주세요.");
            else break;
        }
        String chlLevel;
        while(true) {
            System.out.print("챌린지 난이도를 입력해주세요(*/**/***) : ");
            chlLevel = sc.next();
            String check = chlLevel;
            if (challengeSelect.stream().filter(n -> check.equals(n.getChlLevel())).findAny().orElse(null) == null) {
                System.out.println("챌린지 난이도는 (*/**/***) 중의 하나로 입력해주세요.");
            } else break;
        }
        try {
            conn = Common.getConnection();
            String sql = "INSERT INTO CHALLENGE(CHL_NAME, CHL_ICON, CHL_DESC, CHL_DATE, CHL_LEVEL) VALUES(?, ?, ?, sysdate, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, chlName);
            pstmt.setString(2, chlIcon);
            pstmt.setString(3, chlDesc);
            pstmt.setString(4, chlLevel);
            pstmt.executeUpdate();

            Common.close(pstmt);
            Common.close(conn);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    public void chlMenu(){
        List<ChallengeVo> cni = challengeSelect(); // 리스트 불러오기
        while(true) {
            System.out.println("챌린지 목록");
            for (ChallengeVo challenge : cni) { // 메뉴
                System.out.println("이름: " + challenge.getChlName());
                System.out.println("아이콘: " + challenge.getChlIcon());
                System.out.println("난이도: " + challenge.getChlLevel());
                System.out.println();
            }
            System.out.print("[1] 상세 설명 보기, [2] 내 챌린지 추가하기, [3] 메인메뉴로 돌아가기");
            int sel = sc.nextInt();
            switch(sel){
                case 1: // 상세 설명 보기
                    ChallengeVo challengeDetails = challengeDetails(cni); //challengeDetails 메소드를 받아서 출력해주려고
                    if (challengeDetails != null) {
                        System.out.println("챌린지 이름: " + challengeDetails.getChlName());
                        System.out.println("난이도: " + challengeDetails.getChlLevel());
                        System.out.println("설명: " + challengeDetails.getChlDesc());
                        System.out.println("시작 날짜: " + challengeDetails.getDate());
                        System.out.println("포인트: " + challengeDetails.getChlPoint());
                    } else {
                        System.out.println("챌린지를 찾을 수 없습니다.");
                    }
                    break;
                case 2: // 내 챌린지 추가하기
                    addChallenge(cni);
                    System.out.println("챌린지가 추가되었습니다.");
                    cni = challengeSelect();
                    break;

                case 3: // 메인메뉴로 돌아가기
                    System.out.println("메인메뉴로 돌아갑니다.");
                    return;
                default:
                    System.out.print("메뉴를 잘 못 선택하셨습니다.");
            }

        }
    }
}















