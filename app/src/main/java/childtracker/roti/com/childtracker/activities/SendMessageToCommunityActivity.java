package childtracker.roti.com.childtracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;

import butterknife.ButterKnife;
import childtracker.roti.com.childtracker.R;
import childtracker.roti.com.childtracker.dto.LoginResponseDto;
import childtracker.roti.com.childtracker.retrofit.RetrofitRestApiProvider;
import childtracker.roti.com.childtracker.utils.ChildTrackerUtils;
import childtracker.roti.com.childtracker.utils.Constants;
import childtracker.roti.com.childtracker.utils.CustomSharedPreferance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendMessageToCommunityActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_message_to_community_activity);
        ButterKnife.bind(SendMessageToCommunityActivity.this);
        getSupportActionBar().setTitle(Html.fromHtml(getString(R.string.sending_message)));
        LoginResponseDto.UserMembers memberInfo = (LoginResponseDto.UserMembers) ChildTrackerUtils.convertJsonToObject(getIntent().getStringExtra(Constants.EXTRA_MEMBER_DETAILS), LoginResponseDto.UserMembers.class);
        CustomSharedPreferance customSharedPreferance = new CustomSharedPreferance(SendMessageToCommunityActivity.this);
        String message = "Message from community : " + memberInfo.getComment() + "\n\n" +
                "Childname : " + memberInfo.getChildName() + "\n" +
                "Age : " + memberInfo.getAge() + "\n" +
                "Height : " + memberInfo.getHeight() + "\n" +
                "FathersName : " + memberInfo.getFatherName() + "\n" +
                "MothersName : " + memberInfo.getMotherName() + "\n" +
                "Address : " + memberInfo.getAddress() + "\n";

        RetrofitRestApiProvider retrofitRestApiProvider = new RetrofitRestApiProvider(SendMessageToCommunityActivity.this, Constants.DOMAIN_API);
        retrofitRestApiProvider.sendMessageToCommunity(mCallback, message,customSharedPreferance.getString(Constants.SHARED_PREF_PHONE),customSharedPreferance.getString(Constants.SHAREDPREF_PLAYER_ID),String.valueOf(memberInfo.getId()));


    }

    retrofit2.Callback mCallback = new Callback() {
        @Override
        public void onResponse(Call call, Response response) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent activity = new Intent(SendMessageToCommunityActivity.this, SendMessageSuccessfulActivity.class);
                    startActivity(activity);
                    finish();
                }
            }, 2500);
        }

        @Override
        public void onFailure(Call call, Throwable t) {

        }
    };

}
