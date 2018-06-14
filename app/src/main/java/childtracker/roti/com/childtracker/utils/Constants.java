package childtracker.roti.com.childtracker.utils;


public class Constants {
    public static final String EXTRA_MOBILE = "extra_mobile";
    public static final String EXTRA_PASSWORD = "extra_password";
    public static final String EXTRA_EMAIL = "extra_email";
    public static final String EXTRA_PHOTO = "extra_photo";
    public static final String EXTRA_NAME = "extra_name";
    public static final String EXTRA_AGE = "extra_age";
    public static final String EXTRA_HEIGHT = "extra_height";
    public static final String EXTRA_FATHER_NAME = "extra_father";
    public static final String EXTRA_MOTHER_NAME = "extra_mother";
    public static final String EXTRA_COMMENT = "extra_comment";
    public static final String EXTRA_ADDRESS = "extra_address";
    public static final String EXTRA_MEMBER_DETAILS = "extra_member_details";
    public static final String EXTRA_LAT = "extra_lat";
    public static final String EXTRA_LNG = "extra_lng";
    public static final String EXTRA_LAT2 = "extra_lat2";
    public static final String EXTRA_LNG2 = "extra_lng2";


    public static final String SHARED_PREF_USER_ID = "shared_pref_user_id";
    public static final String SHARED_PREF_USER_PASSWORD = "shared_pref_user_password";
    public static final String SHARED_PREF_IS_USER_LOGIN = "shared_pref_is_user_login";
    public static final String SHARED_PREF_ALL_NOTFICATION = "shared_pref_all_notifications";
    public static final String SHARED_PREF_PHONE = "shared_pref_phone";
    public static final String SHARED_PREF_ALL_MEMBERS = "shared_pref_all_members";
    public static final String SHAREDPREF_PLAYER_ID = "shared_pref_id";

    public static final String DOMAIN_API = "http://52.91.166.193";

    public static final String ADD_MEMBER_API = "/Webservices/ChildTracker/AddMembers.php";
    public static final String ADD_MEMBER_API_MEMBER_NAME = "memberName";
    public static final String ADD_MEMBER_API_AGE = "age";
    public static final String ADD_MEMBER_API_HEIGHT = "height";
    public static final String ADD_MEMBER_API_FATHER_NAME = "fatherName";
    public static final String ADD_MEMBER_API_MOTHER_NAME = "motherName";
    public static final String ADD_MEMBER_API_COMMENTS = "comment";
    public static final String ADD_MEMBER_API_ADDRESS = "address";
    public static final String ADD_MEMBER_API_PHOTO = "photo";
    public static final String ADD_MEMBER_API_USER_ID = "userId";

    public static final String BROADCAST_MESSAGE_API = "/Webservices/ChildTracker/OneSignalToAll.php";
    public static final String BROADCAST_MESSAGE_API_MESSAGE = "message";
    public static final String BROADCAST_MESSAGE_API_MOBILE = "mobileNumber";
    public static final String BROADCAST_MESSAGE_API_MEMBER = "memberId";
    public static final String BROADCAST_MESSAGE_API_LAT = "lat";
    public static final String BROADCAST_MESSAGE_API_LNG = "lng";
    public static final String BROADCAST_MESSAGE_API_TOKEN = "notificationToken";

    public static final String REPLY_TO_USER_API = "/Webservices/ChildTracker/ReplyTheNotification.php";
    public static final String REPLY_TO_USER_API_MEMBER_ID = "memberId";
    public static final String REPLY_TO_USER_API_USER_ID = "userId";
    public static final String REPLY_TO_USER_API_MESAGE = "message";
    public static final String REPLY_TO_USER_API__LAT = "lat";
    public static final String REPLY_TO_USER_API__LNG = "lng";

    public static final String LOGIN_API = "/Webservices/ChildTracker/LoginExistingUser.php";
    public static final String LOGIN_API_MOBILE_NO = "mobileNumber";
    public static final String LOGIN_API_NOTIFICATION_TOKEN = "notificationToken";

    public static final String REGISTER_API = "/Webservices/ChildTracker/LoginNewUser.php";
    public static final String REGISTER_API_NAME = "name";
    public static final String REGISTER_API_MOBILE = "mobile";
    public static final String REGISTER_API_EMAIL = "email";
    public static final String REGISTER_API_PHOTO = "photo";
    public static final String REGISTER_API_PASSWORD = "password";
    public static final String REGISTER_API_NOTIFICATION_TOKEN = "notificationToken";

    public static final String ROTI_MSG_91_OTP_API = "/Webservices/Customer/GenerateOTP.php";
    public static final String ROTI_MSG_91_API_MOBILE = "mobile";
    public static final String ROTI_MSG_91_API_MOBILE_PREFIX = "prefix";
    public static final String ROTI_MSG_91_API_MESSAGE = "message";


    public static final String CREATE_LOGIN_API = "http://54.186.94.218";

    public static final String UPLOAD_API = "/Webservices/ChildTracker/upload_image.php";
    public static final String UPLOAD_API_USER_ID = "userId";
    public static final String UPLOAD_API_MEMBER_ID = "memberId";

    public static final String BROADCAST_MESSAGE_TO_COMMUNITY_API = "http://54.186.94.218";
    public static final String IMAGE_PREFIX_PATH = "http://52.91.166.193/Webservices/ChildTracker/";


}
