package Project_Ecohero.Vo;

import java.sql.Date;

public class ChallengeVo {

    private String chlName;
    private String chlIcon;
    private String chlDesc;
    private Date date;
    private String chlLevel;
    private int chlPoint; // 챌린지 포인트 추가

    public ChallengeVo() {
    }
    // challengeSelect를 위한 생성자
    public ChallengeVo(String chlName, String chlIcon, String chlLevel) {
        this.chlName = chlName;
        this.chlIcon = chlIcon;
        this.chlLevel = chlLevel;
    }
    // challengeDetails를 위한 생성자
    public ChallengeVo(String chlName, String chlLevel, String chlDesc, Date date, int chlPoint) {
        this.chlName = chlName;
        this.chlLevel = chlLevel;
        this.chlDesc = chlDesc;
        this.date = date;
        this.chlPoint = chlPoint;
    }
    // addChallenge를 위한 생성자
    public ChallengeVo(String chlName, String chlIcon, String chlDesc, String chlLevel) {//오버로딩. 호출하는 이름은 똑같은데 매개변수의 차이가 있음. 메소드
        this.chlName = chlName;
        this.chlIcon = chlIcon;
        this.chlDesc = chlDesc;
        this.chlLevel = chlLevel;
    }

    public ChallengeVo(String chlName, String chlLevel, String chlDesc, String chlDate, int chlPoint) {
    }

    public String getChlName() {
        return chlName;
    }

    public void setChlName(String chlName) {
        this.chlName = chlName;
    }

    public String getChlIcon() {
        return chlIcon;
    }

    public void setChlIcon(String chlIcon) {
        this.chlIcon = chlIcon;
    }

    public String getChlDesc() {
        return chlDesc;
    }

    public void setChlDesc(String chlDesc) {
        this.chlDesc = chlDesc;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getChlLevel() {
        return chlLevel;
    }

    public void setChlLevel(String chlLevel) {
        this.chlLevel = chlLevel;
    }

    public int getChlPoint() {
        return chlPoint;
    }

    public void setChlPoint(int chlPoint) {
        this.chlPoint = chlPoint;
    }





    }
