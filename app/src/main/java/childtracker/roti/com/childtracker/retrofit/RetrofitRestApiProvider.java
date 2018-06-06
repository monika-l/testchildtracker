package childtracker.roti.com.childtracker.retrofit;

import android.app.Activity;
import android.util.Log;

import childtracker.roti.com.childtracker.dto.GenericSuccessResponseDto;
import childtracker.roti.com.childtracker.dto.LoginResponseDto;
import childtracker.roti.com.childtracker.dto.MembersPojo;
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
            ChildTrackerUtils.enableWindowProcessing(mContext);
            Log.d(TAG, "inside loginUser");
            ILogin iLogin = mRetrofit.create(ILogin.class);
            Call<LoginResponseDto> call = iLogin.loginUser(mobile);
            call.enqueue(callback);
        }
    }

    public void registerUsers(Callback callback, MembersPojo member) {
        if (ChildTrackerUtils.checkInternetConnection(mContext) == true) {
            ChildTrackerUtils.enableWindowProcessing(mContext);
            Log.d(TAG, "inside registerUsers");
            IRegisterNewUser iRegisterNewUser = mRetrofit.create(IRegisterNewUser.class);
            Call<GenericSuccessResponseDto> call = iRegisterNewUser.registerNewUser(member.memberName,
                    member.age,member.height,member.fatherName,member.motherName,member.comment,
                    member.address
            );
            call.enqueue(callback);
        }
    }

}