package Project_Ecohero.Vo;

import java.sql.Date;

public class MembersVo {
    private String userId;
    private String userAlias;
    private String userName;
    private String userEmail;
    private String userPhone;
    private Date joinDate;
    private int userPoint;

    MembersVo(){};

    public MembersVo(String userId, String userAlias, String userName, String userEmail, String userPhone, Date joinDate, int userPoint) {
        this.userId = userId;
        this.userAlias = userAlias;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
        this.joinDate = joinDate;
        this.userPoint = userPoint;
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

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public int getUserPoint() {
        return userPoint;
    }

    public void setUserPoint(int userPoint) {
        this.userPoint = userPoint;
    }
}
