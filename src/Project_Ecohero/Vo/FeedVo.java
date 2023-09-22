package Project_Ecohero.Vo;

public class FeedVo {
    private int feedNum;
    private String userAlias;
    private String ecoImg;
    private String chlName;
    private String ecoTxt;

    public FeedVo(int feedNum, String userAlias, String ecoImg, String chlName, String ecoTxt) {
        this.feedNum = feedNum;
        this.userAlias = userAlias;
        this.ecoImg = ecoImg;
        this.chlName = chlName;
        this.ecoTxt = ecoTxt;
    }

    public int getFeedNum() {
        return feedNum;
    }

    public void setFeedNum(int feedNum) {
        this.feedNum = feedNum;
    }

    public String getUserAlias() {
        return userAlias;
    }

    public void setUserAlias(String userAlias) {
        this.userAlias = userAlias;
    }

    public String getEcoImg() {
        return ecoImg;
    }

    public void setEcoImg(String ecoImg) {
        this.ecoImg = ecoImg;
    }

    public String getChlName() {
        return chlName;
    }

    public void setChlName(String chlName) {
        this.chlName = chlName;
    }

    public String getEcoTxt() {
        return ecoTxt;
    }

    public void setEcoTxt(String ecoTxt) {
        this.ecoTxt = ecoTxt;
    }
}
