package childtracker.roti.com.childtracker.dto;


import java.util.ArrayList;

public class NotificationsDto {

    private ArrayList<NotificationsMetaData> result;

    public ArrayList<NotificationsMetaData> getResult() {
        return result;
    }

    public void setResult(ArrayList<NotificationsMetaData> result) {
        this.result = result;
    }

    public class NotificationsMetaData{
        private String memberName;
        private String memberPhoneNo;
        private String memberMessage;
        private String memberLocation;

        public String getMemberName() {
            return memberName;
        }

        public void setMemberName(String memberName) {
            this.memberName = memberName;
        }

        public String getMemberPhoneNo() {
            return memberPhoneNo;
        }

        public void setMemberPhoneNo(String memberPhoneNo) {
            this.memberPhoneNo = memberPhoneNo;
        }

        public String getMemberMessage() {
            return memberMessage;
        }

        public void setMemberMessage(String memberMessage) {
            this.memberMessage = memberMessage;
        }

        public String getMemberLocation() {
            return memberLocation;
        }

        public void setMemberLocation(String memberLocation) {
            this.memberLocation = memberLocation;
        }
    }


}
