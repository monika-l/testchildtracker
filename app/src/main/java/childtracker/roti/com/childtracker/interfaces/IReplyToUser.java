package childtracker.roti.com.childtracker.interfaces;

import childtracker.roti.com.childtracker.utils.Constants;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface IReplyToUser {
    @POST(Constants.REPLY_TO_USER_API)
    @FormUrlEncoded
    Call<Void> replyToUser(
            @Field(Constants.REPLY_TO_USER_API_MEMBER_ID) String memberId,
            @Field(Constants.REPLY_TO_USER_API_MESAGE) String message,
            @Field(Constants.REPLY_TO_USER_API_USER_ID) String userId,
            @Field(Constants.REPLY_TO_USER_API__LAT) String lat,
            @Field(Constants.REPLY_TO_USER_API__LNG) String lng


    );
}
