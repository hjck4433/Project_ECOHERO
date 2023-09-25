package Project_Ecohero.Dao;

import Project_Ecohero.Common.Common;
import Project_Ecohero.Vo.FeedVo;
import Project_Ecohero.Vo.MembersVo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import java.util.stream.IntStream;

public class MembersDao {

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Scanner sc = new Scanner(System.in);

    // 회원가입 시 중복 체크를 위해 모든 회원 정보 불러오기 / 단, 비밀번호 및 가입시 자동 입력 되는 항목 제외
    public List<MembersVo> selectMembersInfo(){
        List<MembersVo> mvl = new ArrayList<>();
        try{
            conn = Common.getConnection();
            String sql = "SELECT USER_ID, USER_ALIAS, USER_EMAIL, USER_PHONE FROM MEMBERS";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while(rs.next()){
                String userId = rs.getString("USER_ID");
                String userAlias = rs.getString("USER_ALIAS");
                String userEmail = rs.getString("USER_EMAIL");
                String userPhone = rs.getString("USER_PHONE");
                mvl.add(new MembersVo(userId,userAlias,userEmail,userPhone));
            }
            Common.close(rs);
            Common.close(pstmt);
            Common.close(conn);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return mvl;
    }


    // 로그인 하는 경우 아이디 비밀번호 기준으로 회원 테이블 확인 + 회원정보 수정 시에도 활용
    public boolean checkMember(String userId, String userPw) {
        boolean isMember = false;
        try{
            conn = Common.getConnection();
            String sql = "SELECT COUNT(*) FROM MEMBERS WHERE USER_ID = ? AND USER_PW = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,userId);
            pstmt.setString(2,userPw);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                if(rs.getInt("COUNT(*)") == 1) {
                    isMember = true;
                }
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        Common.close(rs);
        Common.close(pstmt);
        Common.close(conn);
        return isMember;
    }

    // 회원가입을 하는 경우 MEMBERS 테이블에 추가
    // 회원가입을 하기 위해서는 아이디, 비밀번호, 닉네임, 이름, 이메일, 핸드폰번호를 입력
    public void insertNewMember() {
        // 모든 회원정보중 필요한 정보들만 불러와 리스트에 담아 줌
        List<MembersVo> mvl = selectMembersInfo();

        System.out.println("회원 정보를 입력 하세요.");

        // 회원정보 입력 START!

        // 아이디 입력
        String userId;
        while(true) {
            System.out.print("아이디 : ");
            userId = sc.next();
            String check = userId;
            // 중복 체크
            if(mvl.stream().filter(n -> check.equals(n.getUserId())).findAny().orElse(null) != null) {
                System.out.println("이미 사용중인 아이디 입니다.");
            }else break;
        }

        // 비밀번호 입력
        String userPw ;
        while(true) {
            System.out.print("비밀번호(8자 이상 20자 이하) : ");
            userPw = sc.next();
            if(userPw.length() < 8) System.out.println("비밀번호는 8자 이상 입력해주세요");
            else if (userPw.length() > 20) System.out.println("비밀번호는 20자 이하로 입력해주세요");
            else if (userPw.indexOf('&') >= 0) System.out.println("&는 비밀번호로 사용할수 없습니다.");
            else break;
        }

        // 닉네임 입력
        String userAlias;
        while(true) {
            System.out.print("닉네임 : ");
            userAlias = sc.next();
            String check = userAlias;
            // 중복 체크
            if(mvl.stream().filter(n -> check.equals(n.getUserAlias())).findAny().orElse(null) != null) {
                System.out.println("이미 사용중인 닉네임 입니다.");
            }else break;
        }

        // 이름 입력
        System.out.print("이름 : ");
        String userName = sc.next();

        // 이메일 입력
        String userEmail;
        while(true) {
            System.out.print("이메일 : ");
            userEmail = sc.next();
            String check = userEmail;
            //중복 체크
            if(mvl.stream().filter(n -> check.equals(n.getUserEmail())).findAny().orElse(null) != null) {
                System.out.println("이미 사용중인 이메일 입니다.");
            }else break;
        }

        // 핸드폰 번호 입력
        String userPhone;
        while(true) {
            System.out.print("핸드폰번호 : ");
            userPhone = sc.next();
            String check = userPhone;
            //중복 체크
            if(mvl.stream().filter(n -> check.equals(n.getUserPhone())).findAny().orElse(null) != null) {
                System.out.println("이미 사용중인 번호 입니다.");
            }else break;
        }

        // 모든 입력이 완료되면 MEMBERS 테이블에 추가
        String sql = "INSERT INTO MEMBERS(USER_ID, USER_PW, USER_ALIAS, USER_NAME, USER_EMAIL,USER_PHONE) VALUES(?,?,?,?,?,?)";

        try{
            conn = Common.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,userId);
            pstmt.setString(2, userPw);
            pstmt.setString(3, userAlias);
            pstmt.setString(4, userName);
            pstmt.setString(5, userEmail);
            pstmt.setString(6, userPhone);
            pstmt.executeUpdate();
        }catch (Exception e) {
            e.printStackTrace();
        }

        Common.close(pstmt);
        Common.close(conn);
        System.out.println("회원가입이 완료되었습니다. 메인메뉴로 이동합니다.");
    }

    ////////////////////// 내 정보 보기 관련 메소드 ///////////////////////////////

    // 회원 정보 조회
    // 현재 로그인 된 회원 정보를 불러와서 MembersVo객체에 담아주는 메소드
    //curr => current (현재)
    public MembersVo currMemberInfo(String userId) {
        MembersVo currMv = null;
        try{
            conn = Common.getConnection();
            String sql = "SELECT USER_NAME, USER_ALIAS, HERO_GRADE, USER_POINT ,USER_EMAIL, USER_PHONE, TO_CHAR(JOIN_DATE, 'YYYY-MM-DD') JOIN_DATE, TRUNC(SYSDATE - JOIN_DATE) DAYS FROM MEMBERS M JOIN GRADE G ON M.USER_POINT BETWEEN G.LO_POINT AND G.HI_POINT WHERE M.USER_ID = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,userId);
            rs = pstmt.executeQuery();
            if(rs.next()){
                String userName = rs.getString("USER_NAME"); // 이름
                String userAlias = rs.getString("USER_ALIAS"); // 닉네임
                String heroGrade = rs.getString("HERO_GRADE"); // 등급
                int userPoint = rs.getInt("USER_POINT"); // 포인트
                String userEmail = rs.getString("USER_EMAIL"); //이메일
                String userPhone = rs.getString("USER_PHONE"); // 전화번호
                String joinDate = rs.getString("JOIN_DATE"); // 가입날짜
                int days = rs.getInt("DAYS"); // 챌린지 참여 기간

                currMv = new MembersVo(userId,userName,userAlias,heroGrade,userPoint,userEmail,userPhone,joinDate,days);
            }

        }catch(Exception e) {
            e.printStackTrace();
        }
        Common.close(rs);
        Common.close(pstmt);
        Common.close(conn);
        return currMv;
    }

    // 내 정보 보기 출력
    // 매개변수로 currentMemberInfo(cmi) 활용
    public void printMemberInfo(MembersVo cmi) {
        System.out.println("=".repeat(5)+"내 정보"+"=".repeat(5));
        System.out.println("아이디 : " + cmi.getUserId());
        System.out.println("이름 : " + cmi.getUserName());
        System.out.println("닉네임 : " + cmi.getUserAlias());
        System.out.println("회원등급 : " + cmi.getHeroGrade());
        System.out.println("포인트 : " + cmi.getUserPoint());
        System.out.println("이메일 : " + cmi.getUserEmail());
        System.out.println("핸드폰번호 : " + cmi.getUserPhone());
        System.out.println("회원가입날짜 : " + cmi.getJoinDate());
        System.out.println("챌린지 기간 : D+" + cmi.getDays());
        System.out.println("=".repeat(20));
    }

    // 내 정보 수정 -> 현주씨가 작성한거에서 가져와서 수정함
    // 매개변수로 currentMemberInfo (cmi)활용 => 수정하지 않는 항목의 기존 값을 가져오기 위함 임
    public void membersUpdate(MembersVo cml, String userId) {
        // 중복확인을 위해 위에 만들어둔 전체 회원정보를 리스트로 담아주는 selectMembersInfo 활용
        List<MembersVo> mvl = selectMembersInfo();

        // 수정할 비밀번호 입력
        String userPw="";
        while(true) {
            System.out.print("변경할 비밀번호(8자 이상 20자 이하) : ");
            userPw = sc.next();
            // 해당 부분 수정원치 않는 경우 KEYWORD(임시로 no 로 설정)를 받아서 그 KEYWORD가 들어오면
            // 기존 내용 유지
            if(userPw.equalsIgnoreCase("no")) break;
            else if(userPw.length() < 8) System.out.println("비밀번호는 8자 이상 입력해주세요");
            else if (userPw.length() > 20) System.out.println("비밀번호는 20자 이하로 입력해주세요");
            else if (userPw.indexOf('&') >= 0) System.out.println("&는 비밀번호로 사용할수 없습니다.");
            else break;
        }


        // 수정할 닉네임 입력
//        String userAlias;
//        while(true) {
//            System.out.print("변경할 닉네임을 입력하세요 : ");
//            userAlias = sc.next();
//            String check = userAlias;
//            // 중복 체크
//            if(mvl.stream().filter(n -> check.equals(n.getUserAlias())).findAny().orElse(null) != null) {
//                System.out.println("이미 사용중인 닉네임 입니다.");
//            }else if (userAlias.equalsIgnoreCase("no")){ // 아무것도 입력안하면 기존 값 유지
//                userAlias = cml.getUserAlias();
//                break;
//            }else break;
//        }

        // 수정할 이메일 입력
        String userEmail="";
        while(true) {
            System.out.print("변경할 이메일을 입력하세요: ");
            userEmail = sc.next();
            String check = userEmail;
            //중복 체크
            // 회원정보 리스트에 스트림으로 필터를 걸어서 하나라도 일치하는게 있다면 값 반환 없으면 null 반환
            // for문 활용 대신 stream() 사용
            if(mvl.stream().filter(n -> check.equals(n.getUserEmail())).findAny().orElse(null) != null) {
                System.out.println("이미 사용중인 이메일 입니다.");
            }else if(userEmail.equalsIgnoreCase("no")){
                // 해당 부분 수정원치 않는 경우 KEYWORD(임시로 no 로 설정)를 받아서 그 KEYWORD가 들어오면
                // 기존 내용 유지
                userEmail = cml.getUserEmail();
                break;
            }else break;
        }

        // 수정할 핸드폰 번호 입력
        String userPhone ="";
        while(true) {
            System.out.print("변경할 핸드폰 번호를 입력하세요 : ");
            userPhone = sc.next();
            String check = userPhone;
            //중복 체크
            // 회원정보 리스트에 스트림으로 필터를 걸어서 하나라도 일치하는게 있다면 값 반환 없으면 null 반환
            // for문 활용 대신 stream() 사용
            if(mvl.stream().filter(n -> check.equals(n.getUserPhone())).findAny().orElse(null) != null) {
                System.out.println("이미 사용중인 번호 입니다.");
            }else if (userPhone.equalsIgnoreCase("no")){
                // 해당 부분 수정원치 않는 경우 KEYWORD(임시로 no 로 설정)를 받아서 그 KEYWORD가 들어오면
                // 기존 내용 유지
                userPhone = cml.getUserPhone();
                break;
            }else break;
        }


        String sql = "";
        if(userPw.equalsIgnoreCase("no")) {
            sql = "UPDATE MEMBERS SET USER_EMAIL=?, USER_PHONE=? WHERE USER_ID = ?";
            try{
                conn = Common.getConnection();
                pstmt = conn.prepareStatement(sql);
                //pstmt.setString(2,userAlias);
                pstmt.setString(1,userEmail);
                pstmt.setString(2,userPhone);
                pstmt.setString(3, userId);
                pstmt.executeUpdate();
            }catch(Exception e)  {
                e.printStackTrace();
            }
        }else {
            sql = "UPDATE MEMBERS SET USER_PW =?, USER_EMAIL=?, USER_PHONE=? WHERE USER_ID = ?";
            try{
                conn = Common.getConnection();
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, userPw);
                //pstmt.setString(3,userAlias);
                pstmt.setString(2,userEmail);
                pstmt.setString(3,userPhone);
                pstmt.setString(4, userId);
                pstmt.executeUpdate();
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        Common.close(pstmt);
        Common.close(conn);
        System.out.println("회원정보 수정이 완료되었습니다.");

    }
    /// 내 피드 보기 //////////////////////////////////////////////////////
    // 로그인 한 user의 피드만 select해서 리스트에 담기
    public List<FeedVo> myFeed(String userId){
        List<FeedVo> myFvl = new ArrayList<>();
        try{
            conn = Common.getConnection();
            String sql = "SELECT  F.FEED_NUM, CHL_NAME, ECO_TXT, (SELECT COUNT(*) FROM GOOD G WHERE F.FEED_NUM = G.FEED_NUM) AS GOOD_NUM FROM FEED F WHERE USER_ALIAS = (SELECT USER_ALIAS FROM MEMBERS WHERE USER_ID = ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,userId);
            rs = pstmt.executeQuery();
            while(rs.next()){
                int feedNum = rs.getInt("FEED_NUM");
                String chlName = rs.getString("CHL_NAME");
                String ecoTxt = rs.getString("ECO_TXT");
                int goodNum = rs.getInt("GOOD_NUM");

                myFvl.add(new FeedVo(feedNum,chlName,ecoTxt,goodNum));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        Common.close(rs);
        Common.close(pstmt);
        Common.close(conn);
        return myFvl;
    }

    /// 피드 순서대로 출력
    public void printMyFeed(List<FeedVo> myFvl){
        for(FeedVo fv : myFvl) {
            System.out.print("고유번호 : " + fv.getFeedNum());
            System.out.print(", 챌린지명 : " + fv.getChlName());
            System.out.print(", 내용 : " + fv.getEcoTxt());
            System.out.print(", 좋아요 : " + fv.getGoodNum()+"\n");
            System.out.println("=".repeat(40));
        }
    }

    /////////////// 내 피드 수정하기 ///////////////////////////////////
    // 매개변수 : 수정할 피드를 담은 FeedVo 객체, sql문의 WHERE 절에 활용하기 위한 feedNum
    public void updateMyFeed(FeedVo myFv, int feedNum){

        System.out.print("수정할 사진을 입력하세요 : ");
        String ecoImg = sc.next();
        if(ecoImg.equalsIgnoreCase("no")){
            // 해당 부분 수정원치 않는 경우 KEYWORD(임시로 no 로 설정)를 받아서 그 KEYWORD가 들어오면
            // 기존 내용 유지
            ecoImg = myFv.getEcoImg();
        }

        System.out.print("수정할 내용을 입력하세요 : ");
        String ecoTxt = sc.next();
        if(ecoTxt.equalsIgnoreCase("no")){
            // 해당 부분 수정원치 않는 경우 KEYWORD(임시로 no 로 설정)를 받아서 그 KEYWORD가 들어오면
            // 기존 내용 유지
            ecoTxt = myFv.getEcoTxt();
        }

        String sql = "UPDATE FEED SET ECO_IMG = ?, ECO_TXT = ? WHERE FEED_NUM = ?";
        try{
            conn = Common.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,ecoImg);
            pstmt.setString(2,ecoTxt);
            pstmt.setInt(3, feedNum);

            pstmt.executeUpdate();

        }catch(Exception e) {
            e.printStackTrace();
        }
        Common.close(pstmt);
        Common.close(conn);
        System.out.println("피드 수정이 완료 되었습니다.");
    }


    ///////////// 내 피스 삭제하기 ///////////////////////////////////
    public void deleteMyFeed(int feedNum) {
        try{
            // 좋아요가 있으면 고유번호를 참조 하고 있는 값이 있어 삭제가 일어나지 않기 때문에
            // GOOD 테이블에서 삭제하려는 고유넘버관련 행을 먼저 삭제
            conn = Common.getConnection();
            String sql = "DELETE FROM GOOD WHERE FEED_NUM = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,feedNum);
            pstmt.executeUpdate();

            // GOOD 테이블에서 삭제하고 난 후
            // 피드테이블에서 해당 피드관련 행 삭제
            sql = "DELETE FROM FEED WHERE FEED_NUM = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,feedNum);
            pstmt.executeUpdate();
        }catch(Exception e) {
            e.printStackTrace();
        }
        Common.close(pstmt);
        Common.close(conn);
        System.out.println("피드가 삭제되었습니다.");
    }


    /////// 내 정보 메뉴 //////////////////////////////////////////////////
    public void memberMenu(String userId){
        // 메인메뉴로 돌아가기 선택할 때까지 반복
        while(true) {
            // 내 정보 보기 진입 시 현재 로그인 중인 회원의 정보를 불러옴
            MembersVo currMv = currMemberInfo(userId);
            // 불러온 정보 출력
            printMemberInfo(currMv);
            // 내 정보 보기 메뉴의 하위 메뉴 출력
            System.out.print("[1]회원정보 수정 [2]내 피드 정보 [3]메인메뉴로 돌아가기 : ");
            // 메뉴 선택
            int sel = sc.nextInt();
            switch(sel) {
                case 1: // [1] 회원정보 수정 선택 시
                    while(true) {// 로그인한 회원의 비밀번호와 일치하게 입력할 때 까지 반복
                        System.out.print("회원정보를 수정하려면 비밀번호를 입력하세요 : ");
                        String userPw = sc.next();
                        // 비밀번호 일치여부 확인
                        if(!checkMember(userId,userPw)){
                            System.out.println("비밀번호를 잘 못 입력하셨습니다.");
                        }else break; //일치하면 while문 탈출
                    }
                    // 회원정보 수정 메소드 실행
                    membersUpdate(currMv, userId);
                    break;
                case 2: // [2] 내 피드 보기 메뉴 진입
                    while(true) { //[3] 내 정보로 돌아가기 선택하기 전까지 반복
                        System.out.println("=".repeat(5)+"피드 리스트"+"=".repeat(5));
                        // 로그인한 유저의 피드를 리스트에 담고
                        List<FeedVo> myFvl = myFeed(userId);
                        // 피드 리스트를 출력
                        printMyFeed(myFvl);
                        // 내 피드 보기의 메뉴 선택 출력
                        System.out.print("[1] 피드 수정하기 [2] 피드 삭제하기 [3] 내 정보로 돌아가기 : ");
                        int myFeedSel = sc.nextInt();
                        switch(myFeedSel) { 
                            case 1 : //[1] 피드 수정하기 진입
                                // 수정할 피드를 담을 FeedVo객체 초기화
                                FeedVo myFv = null;
                                // 선택 고유번호의 피드 정보의 인덱스
                                OptionalInt searchIdx;
                                // 고유번호를 저장할 변수
                                int feedNum;
                                while(true){ // 로그인한 회원이 작성한 고유번호를 입력 했을 때만 빠져나옴
                                    System.out.print("수정할 피드의 고유번호 입력 : ");
                                    feedNum = sc.nextInt();
                                    // 람다식에서 고유번호를 지닌 리스트의 인덱스를 찾기 위해 사용하는 변수
                                    int check = feedNum;
                                    // 입력한 고유번호에 해당하는 피드가 있는지
                                    searchIdx = IntStream.range(0, myFvl.size()) // 피드 길이만큼 반복해서
                                            .filter(i -> myFvl.get(i).getFeedNum()==check) // 확인
                                            .findFirst(); // 일치하는 내용의 첫번째 인덱스 반환
                                    if(searchIdx.getAsInt() < 0){ //인덱스 시작이 0이므로 0보다 작은 값이 나오면 일치 항목 없음
                                        System.out.println("피드의 고유번호를 잘 못 선택하셨습니다.");
                                    }else break; // 피드에 해당 고유번호 존재 하면 빠져나옴
                                }
                                // 해당 피드를 수정하는 메소드 호출 
                                // 매개변수로 해당 피드 / 고유번호 사용
                                // myFvl.get(searchIdx.getAsInt())
                                // 현재회원의 피드 리스트.get(고유번호가 포함된 피드의 인덱스(int형만 올 수 있음))
                                // searchIdx는 Optionalidx 타입으로 int값으로 형변환을 해줘야 함
                                updateMyFeed(myFvl.get(searchIdx.getAsInt()),feedNum);
                                break;
                            case 2:
                                while(true){ // 피드 존재 여부 확인은 수정과 동일
                                    System.out.print("삭제할 피드의 고유번호 입력 : ");
                                    feedNum = sc.nextInt();
                                    int check = feedNum;
                                    searchIdx = IntStream.range(0, myFvl.size())
                                            .filter(i -> myFvl.get(i).getFeedNum()==check)
                                            .findFirst();
                                    if(searchIdx.getAsInt() < 0){
                                        System.out.println("피드의 고유번호를 잘 못 선택하셨습니다.");
                                    }else break;
                                }
                                // 선택한 피드를 delete / delete는 고유번호만 있어도 가능
                                deleteMyFeed(feedNum);
                                break;
                            case 3 : // 내정보로 돌아감
                                System.out.println("내 정보로 돌아갑니다.");
                                break;
                            default : System.out.println("메뉴를 잘 못 선택하셨습니다.");
                        }
                        if(myFeedSel == 3) break; // [3] 내정보를 돌아감 선택시 while문 탈출
                    } continue; // 내 정보 보기 메뉴로 돌아가기 위한 continue
                case 3:
                    System.out.println("메인 메뉴로 돌아갑니다.");
                    return;
                default: System.out.println("메뉴를 잘 못 선택 하셨습니다.");
            }
        }
    }
}

