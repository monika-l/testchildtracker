package childtracker.roti.com.childtracker.dto;


import java.util.ArrayList;

public class MemberDto {

    private ArrayList<MemberMetadata> result;

    public ArrayList<MemberMetadata> getResult() {
        return result;
    }

    public void setResult(ArrayList<MemberMetadata> result) {
        this.result = result;
    }

    public class MemberMetadata{
        private String memberName;
        private String memberPhoneNo;
        private String memberImage;

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

        public String getMemberImage() {
            return memberImage;
        }

        public void setMemberImage(String memberImage) {
            this.memberImage = memberImage;
        }
    }


}
