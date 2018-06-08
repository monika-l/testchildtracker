package childtracker.roti.com.childtracker.interfaces;

import childtracker.roti.com.childtracker.dto.GenericSuccessResponseDto;
import childtracker.roti.com.childtracker.utils.Constants;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface IRegisterNewUser {
    @POST(Constants.REGISTER_API)
    @FormUrlEncoded
    Call<GenericSuccessResponseDto> registerNewUser(
            @Field(Constants.REGISTER_API_NAME) String name,
                     @Field(Constants.REGISTER_API_MOBILE) String mobile,
                    @Field(Constants.REGISTER_API_EMAIL) String email,
                    @Field(Constants.REGISTER_API_PHOTO) String photo,
                    @Field(Constants.REGISTER_API_PASSWORD) String password,
                    @Field(Constants.REGISTER_API_NOTIFICATION_TOKEN) String notificationToken

    );
}
