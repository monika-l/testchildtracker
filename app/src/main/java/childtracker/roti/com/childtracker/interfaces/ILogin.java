package childtracker.roti.com.childtracker.interfaces;

import childtracker.roti.com.childtracker.dto.LoginResponseDto;
import childtracker.roti.com.childtracker.utils.Constants;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;



public interface ILogin {
    @POST(Constants.LOGIN_API)
    @FormUrlEncoded
    Call<LoginResponseDto> loginUser(
            @Field(Constants.LOGIN_API_MOBILE_NO) String mobileNo,
            @Field(Constants.LOGIN_API_NOTIFICATION_TOKEN) String notificationToken
    );
}
