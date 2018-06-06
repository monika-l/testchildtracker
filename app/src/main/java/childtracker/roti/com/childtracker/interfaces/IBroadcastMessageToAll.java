package childtracker.roti.com.childtracker.interfaces;

import childtracker.roti.com.childtracker.utils.Constants;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface IBroadcastMessageToAll {
    @POST(Constants.BROADCAST_MESSAGE_API)
    @FormUrlEncoded
    Call<Void> broadcastMessage(
            @Field(Constants.BROADCAST_MESSAGE_API_MESSAGE) String message


    );
}
