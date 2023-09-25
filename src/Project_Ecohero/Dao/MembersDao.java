package Project_Ecohero.Dao;

import Project_Ecohero.Common.Common;
import Project_Ecohero.Common.Util;
import Project_Ecohero.Vo.FeedVo;
import Project_Ecohero.Vo.MembersVo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import java.util.regex.Pattern;
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

            Util ut = new Util();
            // 중복 체크
            if(mvl.stream().filter(n -> check.equals(n.getUserId())).findAny().orElse(null) != null) {
                System.out.println("이미 사용중인 아이디 입니다.");
            }else if (!ut.checkInputOnlyNumberAndAlphabet(userId)) System.out.println("영문과 숫자 조합만 사용해주세요.");
            else if (userId.length() <= 5) System.out.println("ID는 5자 이상 입력해주세요");
            else if (userId.length() >= 20) System.out.println("ID는 20자 이하로 입력해주세요");
            else break;
        }

        // 비밀번호 입력
        String userPw ;
        while(true) {
            System.out.print("비밀번호(8자 이상 20자 이하) : ");
            userPw = sc.next();
            if(userPw.length() <= 8) System.out.println("비밀번호는 8자 이상 입력해주세요");
            else if (userPw.length() >= 20) System.out.println("비밀번호는 20자 이하로 입력해주세요");
            else if (userPw.indexOf('&') >= 0) System.out.println("&는 비밀번호로 사용할수 없습니다.");
            else break;
        }

        // 닉네임 입력 - byte → int 변환해서 글자길이 x, 인트나 바이트 길이로 기준.
        String userAlias;
        while(true) {
            System.out.print("닉네임 : ");
            userAlias = sc.next();
            String check = userAlias;

            int intA = userAlias.getBytes().length;

            // 중복 체크
            if(mvl.stream().filter(n -> check.equals(n.getUserAlias())).findAny().orElse(null) != null) {
                System.out.println("이미 사용중인 닉네임 입니다.");
            }
            else if (intA <= 2) System.out.print("닉네임은 2자 이상 입력해주세요");
            else if (intA >= 30) System.out.print("길이제한을 초과하였습니다 (한글은 10자, 영어는 30자)");
            else break;
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

            String pattern2 = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$";

            //중복 체크
            if(mvl.stream().filter(n -> check.equals(n.getUserEmail())).findAny().orElse(null) != null) {
                System.out.println("이미 사용중인 이메일 입니다.");
            }
            else if (!Pattern.matches(pattern2, check)) System.out.println("올바른 이메일 형식이 아닙니다.");
            else break;
        }

        // 핸드폰 번호 입력  - 13자리만 허용 (비워둘 수 없습니다 → 자동적용)
        String userPhone;
        while(true) {
            System.out.println("핸드폰번호 : ");
            userPhone = sc.next();
            String check = userPhone;
            //중복 체크
            if(mvl.stream().filter(n -> check.equals(n.getUserPhone())).findAny().orElse(null) != null) {
                System.out.println("이미 사용중인 번호 입니다.");
            }
            else if (userPhone.length() != 13) System.out.print("전화번호는 (-)포함 13글자로 작성하세요.");
            else break;
        }

        // 모든 입력이 완료되면 MEMBERS 테이블에 추가
        String sql = "INSERT INTO MEMBERS(USER_ID, USER_PW, USER_ALIAS, USER_NAME, USER_EMAIL,USER_PHONE) VALUES(?,?,?,?,?,?)";

        try{
            conn = Common.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
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

    /// 회원가입 또는 로그인 성공 시 아이디 닉네임이 저장된 MembersVo 객체 반환
    public MembersVo idSet(String userId) {
        MembersVo membersVo = new MembersVo();
        try{
            conn = Common.getConnection();
            String sql = "SELECT MEMBERS USER_ALIAS \n" +
                    "FROM MEMBERS\n" +
                    "WHERE USER_ID = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
            rs = pstmt.executeQuery();

            if(rs.next()) { // 결과가 나오면.
                String userAlias = rs.getString("USER_ALIAS");
                membersVo = new MembersVo(userId, userAlias);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(rs);
        Common.close(pstmt);
        Common.close(conn);

        return membersVo;
    }

}

