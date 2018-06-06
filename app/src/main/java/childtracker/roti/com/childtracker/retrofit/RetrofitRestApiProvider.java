package childtracker.roti.com.childtracker.retrofit;

import android.app.Activity;
import android.util.Log;

import childtracker.roti.com.childtracker.dto.GenericSuccessResponseDto;
import childtracker.roti.com.childtracker.dto.LoginPojo;
import childtracker.roti.com.childtracker.dto.LoginResponseDto;
import childtracker.roti.com.childtracker.dto.MembersPojo;
import childtracker.roti.com.childtracker.interfaces.IAddMember;
import childtracker.roti.com.childtracker.interfaces.IBroadcastMessageToAll;
import childtracker.roti.com.childtracker.interfaces.ILogin;
import childtracker.roti.com.childtracker.interfaces.IRegisterNewUser;
import childtracker.roti.com.childtracker.utils.ChildTrackerUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitRestApiProvider {

    private static final String TAG = RetrofitRestApiProvider.class.getSimpleName();
    private Activity mContext;
    private final Retrofit mRetrofit;


    public RetrofitRestApiProvider(Activity context, String baseUrl) {
        Log.d(TAG, "inside RetrofitRestApiProvider");
        mContext = context;
        mRetrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void loginUser(Callback callback, String mobile) {
        if (ChildTrackerUtils.checkInternetConnection(mContext) == true) {
            Log.d(TAG, "inside loginUser");
            ILogin iLogin = mRetrofit.create(ILogin.class);
            Call<LoginResponseDto> call = iLogin.loginUser(mobile);
            call.enqueue(callback);
        }
    }

    public void registerUsers(Callback callback, LoginPojo loginPojo) {
        if (ChildTrackerUtils.checkInternetConnection(mContext) == true) {
            Log.d(TAG, "inside registerUsers");
            IRegisterNewUser iRegisterNewUser = mRetrofit.create(IRegisterNewUser.class);
            Call<GenericSuccessResponseDto> call = iRegisterNewUser.registerNewUser(loginPojo.name,
                    loginPojo.mobile, loginPojo.email, loginPojo.photo, loginPojo.password
            );
            call.enqueue(callback);
        }
    }

    public void sendMessageToCommunity(Callback callback, String message) {
        if (ChildTrackerUtils.checkInternetConnection(mContext) == true) {
            Log.d(TAG, "inside registerUsers");
            IBroadcastMessageToAll iBroadcastMessageToAll = mRetrofit.create(IBroadcastMessageToAll.class);
            Call<Void> call = iBroadcastMessageToAll.broadcastMessage(message);
            call.enqueue(callback);
        }
    }

    public void addMembers(Callback callback, MembersPojo membersPojo) {
        if (ChildTrackerUtils.checkInternetConnection(mContext) == true) {
            Log.d(TAG, "inside addMembers");
            IAddMember iAddMember = mRetrofit.create(IAddMember.class);
            Call<GenericSuccessResponseDto> call = iAddMember.registerNewUser(membersPojo.memberName,
                    membersPojo.age,
                    membersPojo.height,
                    membersPojo.fatherName,
                    membersPojo.motherName,
                    membersPojo.comment,
                    membersPojo.address,
                    membersPojo.photo,
                    membersPojo.userId
            );
            call.enqueue(callback);
        }
    }

}