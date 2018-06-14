package childtracker.roti.com.childtracker.dto;


public class GenerateOtpDto {


    private UserProfileMetaData userProfile;
    private boolean isDiscountedUser;
    private String discountCode;
    private String discountValue;


    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public String getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(String discountValue) {
        this.discountValue = discountValue;
    }

    public boolean isDiscountedUser() {
        return isDiscountedUser;
    }

    public void setDiscountedUser(boolean discountedUser) {
        isDiscountedUser = discountedUser;
    }

    public UserProfileMetaData getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfileMetaData userProfile) {
        this.userProfile = userProfile;
    }

    public class UserProfileMetaData{

     private String userId;
     private String awsDeviceId;
     private String firstname;
     private String lastname;
     private String email;
     private String mobile;
     private String isFbUser;
     private String isGoogleUser;
     private String profile_pic;
     private String currentPaymentMode;

        public String getCurrentPaymentMode() {
            return currentPaymentMode;
        }

        public void setCurrentPaymentMode(String currentPaymentMode) {
            this.currentPaymentMode = currentPaymentMode;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getAwsDeviceId() {
            return awsDeviceId;
        }

        public void setAwsDeviceId(String awsDeviceId) {
            this.awsDeviceId = awsDeviceId;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getIsFbUser() {
            return isFbUser;
        }

        public void setIsFbUser(String isFbUser) {
            this.isFbUser = isFbUser;
        }

        public String getIsGoogleUser() {
            return isGoogleUser;
        }

        public void setIsGoogleUser(String isGoogleUser) {
            this.isGoogleUser = isGoogleUser;
        }

        public String getProfile_pic() {
            return profile_pic;
        }

        public void setProfile_pic(String profile_pic) {
            this.profile_pic = profile_pic;
        }
    }
}
