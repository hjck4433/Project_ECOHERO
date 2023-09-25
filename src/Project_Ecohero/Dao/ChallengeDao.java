package Project_Ecohero.Dao;

import Project_Ecohero.Common.Common;
import Project_Ecohero.Vo.ChallengeVo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class ChallengeDao {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;


    // 챌린지 리스트 선택 -> 모든 챌린지의 목록 생성(리스트)
    public List<ChallengeVo> challengeSelect() {
        List<ChallengeVo> list = new ArrayList<>();
        try {
            conn = Common.getConnection();
            String sql = "SELECT CHL_NAME, CHL_ICON, CHL_LEVEL FROM CHALLENGE ORDER BY CHL_LEVEL"; ///
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

    // 챌린지 세부 내용 조회
    // -> 하나하나의 챌린지 정보만을 조회하고 반횐하므로 리스트로 작성하지 않음
    public ChallengeVo challengeDetails(String chlName){
    ChallengeVo challenge = null; // 챌린지 정보 담을 객체 초기화
        try{
            conn = Common.getConnection();
            String sql = "SELECT CHL_NAME, CHL_LEVEL, CHL_DESC, TO_CHAR(CHL_DATE, 'YVYY-MM-DD'), CHL_POINT FROM CHALLENGE WHERE CHL_NAME = ?";
            pstmt = conn.prepareStatement(sql); // 보낼 쿼리문을 준비하고 있다
            pstmt.setString(1, chlName);        // ?에 챌린지 명으로 검색하기 위해서
            rs = pstmt.executeQuery();

            if (rs.next()) {
                chlName = rs.getString("CHL_NAME");   // 챌린지 이름
                String chlLevel = rs.getString("CHL_LEVEL"); // 챌린지 난이도
                String chlDesc = rs.getString("CHL_DESC");   // 챌린지 설명
                String chlDate = rs.getString(4);            // 챌린지 시작 날짜
                int chlPoint = rs.getInt("Chl_POINT");              // 획득 가능 포인트
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
        public void addChallenge(ChallengeVo challenge) {
            try {
                conn = Common.getConnection();
                String sql = "INSERT INTO CHALLENGE(CHL_NAME, CHL_ICON, CHL_DESC, CHL_LEVEL) VALUES(?, ?, ?, ?)";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, challenge.getChlName());
                pstmt.setString(2, challenge.getChlIcon());
                pstmt.setString(3, challenge.getChlDesc());
                pstmt.setString(4, challenge.getChlLevel());
                pstmt.executeUpdate();

                Common.close(pstmt);
                Common.close(conn);
            } catch (Exception e) {
                e.printStackTrace();

            }

    }
}