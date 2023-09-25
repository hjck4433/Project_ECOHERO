package Project_Ecohero.Dao;

import Project_Ecohero.Common.Common;
import Project_Ecohero.Vo.FeedVo;
import Project_Ecohero.Vo.MembersVo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FeedDao {
    boolean isGood;

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Scanner sc = new Scanner(System.in);

    // Feed_Num만 내림차순 정렬해서 불러오기
    public List<Integer> feedNumSelect() {
        // 정렬된 Feed_NUM 을 담을 리스트 객체
        List<Integer> fnl = new ArrayList<>();
        try{
            conn = Common.getConnection();
            String sql = "SELECT FEED_NUM FROM FEED ORDER BY FEED_NUM DESC";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while(rs.next()) {
                // 내림 차순 순서대로 리스트에 담기
                fnl.add(rs.getInt("FEED_NUM"));
            }
            Common.close(rs);
            Common.close(pstmt);
            Common.close(conn);

        }catch(Exception e) {
            e.printStackTrace();
        }
        // Feed_Num 리스트 반환
        return fnl;
    }

    // 최신 등록 순으로 하나씩 닉네임, 사진, 챌린지명, 텍스트, 좋아요 순으로 출력
    public FeedVo feedSelect(List<Integer> fnl, int feedNumIdx) { // 매개 변수로 인덱스를 지정한 int값 지정
        // 부러온 피드 정보를 담을 리스트 객체
        FeedVo selFeed = null;

        try{
            // db 연결
            conn = Common.getConnection();
            //  sql 변수에 sql문 값 할당
            String sql = "SELECT (SELECT USER_ALIAS FROM MEMBERS WHERE USER_ID = (SELECT USER_ID FROM FEED WHERE FEED_NUM = ?)) USER_ALIAS, ECO_IMG, CHL_NAME, ECO_TXT, (SELECT COUNT(*) FROM GOOD G WHERE F.FEED_NUM = G.FEED_NUM) GOOD_NUM FROM FEED F WHERE F.FEED_NUM = ?";
            // db에 보낼 준비
            pstmt = conn.prepareStatement(sql);
            // 첫번째 ? 에 feedNumSelect메소드로 받은 고유번호 리스트의 num번째 인덱스에서 고유번호값을 가져와서 넣어줌
            pstmt.setInt(1, fnl.get(feedNumIdx));
            pstmt.setInt(2, fnl.get(feedNumIdx));
            // 쿼리를 db로 날리고 결과를 반환 받음
            rs = pstmt.executeQuery();
            if(rs.next()) {
                String userAlias = rs.getString("USER_ALIAS");
                String ecoImg = rs.getString("ECO_IMG");
                String chlName = rs.getString("CHL_NAME");
                String ecoTxt = rs.getString("ECO_TXT");
                int goodNum = rs.getInt("GOOD_NUM");
                // 받은 값을 feedVo객체를 생성해서 리스트에 추가
                selFeed = new FeedVo(userAlias,ecoImg,chlName,ecoTxt,goodNum);
            }
            Common.close(rs);
            Common.close(pstmt);
            Common.close(conn);
        } catch(Exception e) {
            e.printStackTrace();
        }

        //리스트 반환
        return selFeed;
    }

    // 피드를 하나씩 출력
    public void printFeed(FeedVo fv) {
        System.out.println("닉네임 : " + fv.getUserAlias());
        System.out.println("이미지 : " + fv.getEcoImg());
        System.out.println("챌린지명 : " + fv.getChlName());
        System.out.println("내용 : " + fv.getEcoTxt());
        System.out.println("좋아요 : " + fv.getGoodNum());
        System.out.println("=".repeat(20));
    }

    // 로그인한 유저가 해당 피드에 좋아요를 했는지 확인
    public boolean isGood(int feedNumIdx, String userId) {
        try{
            conn = Common.getConnection();
            String sql = "SELECT COUNT(*) FROM GOOD WHERE FEED_NUM = ? AND USER_ID = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,feedNumIdx);
            pstmt.setString(2,userId);
            rs = pstmt.executeQuery();

            // 결과가 0이면 좋아요를 한 적이 없음
            if(rs.next() && rs.getInt("COUNT(*)") == 0) {
                this.isGood = false;
            }else { // 그 외는 좋아요를 이미 함
                this.isGood = true;
            }
            Common.close(rs);
            Common.close(pstmt);
            Common.close(conn);
        } catch(Exception e) {
            e.printStackTrace();
        }

        return isGood;
    }

    // 좋아요 여부에 따라 다른 메뉴 보여주고 선택한 메뉴 반환
    public int selSubMenu(boolean isGood) {
        int sel = 0;
        if (!isGood) { // isGood 이 false이면 [1] 좋아요
            System.out.print("[1]좋아요 [2]계속 구경하기 [3]글쓰기 [4]메인메뉴로 돌아가기 : ");
            sel = sc.nextInt();
        }else { // isGood 이 true이면 [1] 좋아요취소
            System.out.print("[1]좋아요취소 [2]계속 구경하기 [3]글쓰기 [4]메인메뉴로 돌아가기 : ");
            sel = sc.nextInt();
        }
        return sel;
    }

    // 좋아요 수 증가를 위해 GOOD 테이블에 추가 / 좋아요 수 감소를 위해 GOOD 테이블에서 삭제
    public void goodMenu(int feedNum, String userId) {
        try{
            conn = Common.getConnection();
            String sql = "";
            if(!isGood){
                sql = "INSERT INTO GOOD (FEED_NUM, USER_ID) VALUES(?,?)";
            }else {
                sql = "DELETE FROM GOOD WHERE FEED_NUM = ? AND USER_ID = ?";
            }
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,feedNum);
            pstmt.setString(2,userId);
            pstmt.executeUpdate();

            Common.close(pstmt);
            Common.close(conn);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    // 새 피드를 추가할 때 참고 할 수 있는 챌린지명 출력이 필요 함
    // 챌리지명을 리스트로 담아오는 메소드
    public List<String> chlNameList(){
        List<String> chlList = new ArrayList<>();
        try{
            conn = Common.getConnection();
            String sql = "SELECT CHL_NAME FROM CHALLENGE";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while(rs.next()) {
                String chlName = rs.getString("CHL_NAME");

                chlList.add(chlName);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(rs);
        Common.close(pstmt);
        Common.close(conn);
        return chlList;
    }
    // 챌린지 이름만 프린트
    public void printChlNameList(List<String> chlNameList){
        System.out.print("입력 가능한 챌린지 명 : ");
        for(String chl : chlNameList) System.out.print(chl + " / ");
        System.out.println();
    }


    // 새 피드 추가 하기 - 사진, 글 내용, 챌린지명
    public void insertFeed(String userId) {
        sc.nextLine();
        String ecoImg;
        while(true) {
            System.out.print("사진 입력 : ");
            ecoImg = sc.nextLine();
            if(ecoImg.getBytes().length > 300){
                System.out.println("입력 가능 허용 범위를 초과하였습니다. 영문 기준 300, 한글 기준 100자까지 입력 가능합니다.");
            }else break;
        }

        String ecoTxt;
        while(true) {
            System.out.print("내용 입력 : ");
            ecoTxt = sc.nextLine();
            if(ecoImg.getBytes().length > 300){
                System.out.println("입력 가능 허용 범위를 초과하였습니다. 영문 기준 300, 한글 기준 100자까지 입력 가능합니다.");
            }else break;
        }

        List<String> cnl = chlNameList();
        printChlNameList(cnl);
        String chlName;
        while(true) {
            System.out.print("챌린지명 입력 : ");
            chlName = sc.next();
            if(!cnl.contains(chlName)){
                System.out.println("없는 챌린지 입니다.");
                printChlNameList(cnl);
            }else break;
        }

        int sel;
        while(true) {
            System.out.print("[1] 업로드 [2] 취소 : ");
            sel = sc.nextInt();
            if (sel == 1 || sel == 2 ) break;
            else System.out.println("메뉴를 잘 못 선택하셨습니다.");
        }

        String sql = "INSERT INTO FEED (FEED_NUM, USER_ID, ECO_IMG, CHL_NAME, ECO_TXT) VALUES(FEED_NUM_SEQ.NEXTVAL,?,?,?,?)";
        if (sel == 1) {
            try{
                conn = Common.getConnection();
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, userId);
                pstmt.setString(2, ecoImg);
                pstmt.setString(3, chlName);
                pstmt.setString(4, ecoTxt);

                pstmt.executeUpdate();

                Common.close(pstmt);
                Common.close(conn);

            }catch(Exception e) {
                e.printStackTrace();
            }
            updateUserPoint(userId, chlName);
            System.out.println("피드가 업로드 되었습니다.");
        }else {
            System.out.println("피드 작성을 취소하셨습니다.");
        }
    }
    // 호출될 때 기준으로 현재 유저 포인트 조회
    public int currUserPoint(String userId) {
        int currPoint = 0;
        try{
            conn = Common.getConnection();
            String sql = "SELECT USER_POINT FROM MEMBERS WHERE USER_ID = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
            rs = pstmt.executeQuery();

            if(rs.next()) {
                currPoint = rs.getInt("USER_POINT");
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(rs);
        Common.close(pstmt);
        Common.close(conn);

        return currPoint;
    }

    // 작성한 피드의 포인트 구하기
    public int getUpdatePoint(String chlName) {
        int updatePoint = 0;
        try{
            conn = Common.getConnection();
            String sql = "SELECT POINT FROM ECO_LV WHERE LEVELS IN (SELECT CHL_LEVEL  FROM CHALLENGE WHERE CHL_NAME = ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,chlName);
            rs = pstmt.executeQuery();

            if(rs.next()) {
                updatePoint = rs.getInt("POINT");
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(rs);
        Common.close(pstmt);
        Common.close(conn);

        return updatePoint;
    }

    // 새 피드를 추가하면 글의 난이도에 해당 하는 포인트가 USER_POINT에 UPDATE
    public void updateUserPoint(String userId, String chlName) {
        int updatePoint = getUpdatePoint(chlName);
        try{
            conn = Common.getConnection();
            String sql = "UPDATE MEMBERS SET USER_POINT = USER_POINT + ? WHERE USER_ID = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, updatePoint);
            pstmt.setString(2, userId);

            pstmt.executeUpdate();

            Common.close(pstmt);
            Common.close(conn);
        }catch(Exception e) {
            e.printStackTrace();
        }
        int currUserPoint = currUserPoint(userId);
        int prevUserPoint = currUserPoint - updatePoint;

        System.out.print(prevUserPoint+"에 "+updatePoint+"점이 적립되어 "+currUserPoint+"가 되었습니다!!");
    }


    //Main에서 호출할 피드 메뉴 - feedMenu()만 호출하면 메인메뉴로 돌아가기 선택 전까지 해당메뉴 순환
    public void feedMenu(String userId, String userAlias) {
        int feedNumIdx = 0;
        List<Integer> fnl = feedNumSelect();
        while(true) {
            printFeed(feedSelect(fnl,feedNumIdx));
            int feedNum = fnl.get(feedNumIdx); // 현재 피드 고유번호
            int sel = selSubMenu(isGood(feedNum, userId)); // 임시로 직접 값입력
            switch (sel){
                case 1:
                    goodMenu(feedNum, userId);
                    break;
                case 2:
                    System.out.println();
                    feedNumIdx ++;
                    break;
                case 3:
                    insertFeed(userId);
                    feedNumIdx = 0;
                    fnl = feedNumSelect();
                    break;
                case 4:
                    System.out.println("메인메뉴로 돌아갑니다.");
                    return;
                default :
                    System.out.println("메뉴를 잘 못 선택하셨습니다.");
                    continue;
            }
            if (feedNumIdx == fnl.size()) feedNumIdx = 0;
        }
    }

}
