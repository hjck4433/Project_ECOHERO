package Project_Ecohero.Vo;

import java.sql.Date;

public class MembersVo {
    private String userId;
    private String userAlias;
    private String userName;
    private String userEmail;
    private String userPhone;
    private String joinDate;
    private String heroGrade;
    private int userPoint;
    private int days;

    public MembersVo(){};

    public MembersVo(String userId, String userAlias){
        this.userId = userId;
        this.userAlias = userAlias;
    };

    public MembersVo(String userId, String userName, String userAlias, String heroGrade, int userPoint, String userEmail, String userPhone, String joinDate, int days) {
        this.userId = userId;
        this.userName = userName;
        this.userAlias = userAlias;
        this.heroGrade = heroGrade;
        this.userPoint = userPoint;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
        this.joinDate = joinDate;
        this.days = days;
    }

    // 회원가입 시 중복 확인을 위해 불러오는 회원정보를 리스트로 담기 위한 생성자
    public MembersVo(String userId, String userAlias, String userEmail, String userPhone) {
        this.userId = userId;
        this.userAlias = userAlias;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserAlias() {
        return userAlias;
    }

    public void setUserAlias(String userAlias) {
        this.userAlias = userAlias;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public String getHeroGrade() {
        return heroGrade;
    }

    public void setHeroGrade(String heroGrade) {
        this.heroGrade = heroGrade;
    }

    public int getUserPoint() {
        return userPoint;
    }

    public void setUserPoint(int userPoint) {
        this.userPoint = userPoint;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }
}
