package childtracker.roti.com.childtracker.interfaces;


import childtracker.roti.com.childtracker.dto.GenerateOtpDto;
import childtracker.roti.com.childtracker.utils.Constants;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface IGenerateSMS {
    @GET(Constants.ROTI_MSG_91_OTP_API)
    Call<GenerateOtpDto> generateSMS(

            @Query(Constants.ROTI_MSG_91_API_MOBILE) String mobile,
            @Query(Constants.ROTI_MSG_91_API_MESSAGE) String message,
            @Query(Constants.ROTI_MSG_91_API_MOBILE_PREFIX) String prefix

    );
}