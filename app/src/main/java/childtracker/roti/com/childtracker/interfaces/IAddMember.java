package childtracker.roti.com.childtracker.interfaces;

import childtracker.roti.com.childtracker.dto.GenericSuccessResponseDto;
import childtracker.roti.com.childtracker.utils.Constants;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface IAddMember {
    @POST(Constants.ADD_MEMBER_API)
    @FormUrlEncoded
    Call<GenericSuccessResponseDto> registerNewUser(
            @Field(Constants.ADD_MEMBER_API_MEMBER_NAME) String name,
            @Field(Constants.ADD_MEMBER_API_AGE) String age,
            @Field(Constants.ADD_MEMBER_API_HEIGHT) String height,
            @Field(Constants.ADD_MEMBER_API_FATHER_NAME) String fathername,
            @Field(Constants.ADD_MEMBER_API_MOTHER_NAME) String mothername,
            @Field(Constants.ADD_MEMBER_API_COMMENTS) String comments,
            @Field(Constants.ADD_MEMBER_API_ADDRESS) String address,
            @Field(Constants.ADD_MEMBER_API_PHOTO) String photo,
            @Field(Constants.ADD_MEMBER_API_USER_ID) String userId

    );
}
