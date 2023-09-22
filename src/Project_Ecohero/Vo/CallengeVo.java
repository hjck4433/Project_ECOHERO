package Project_Ecohero.Vo;

import java.sql.Date;

public class CallengeVo {
    public class Challenge{
        private String chlName;
        private String chlIcon;
        private String chlDesc;
        private Date date;
        private String chlLevel;

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

        public Challenge(String chlName, String chlIcon, String chlDesc, Date date, String chlLevel) {
            this.chlName = chlName;
            this.chlIcon = chlIcon;
            this.chlDesc = chlDesc;
            this.date = date;
            this.chlLevel = chlLevel;
        }
    }
}
