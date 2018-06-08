package childtracker.roti.com.childtracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import childtracker.roti.com.childtracker.R;
import childtracker.roti.com.childtracker.dto.LoginResponseDto;
import childtracker.roti.com.childtracker.retrofit.RetrofitRestApiProvider;
import childtracker.roti.com.childtracker.utils.ChildTrackerUtils;
import childtracker.roti.com.childtracker.utils.Constants;
import childtracker.roti.com.childtracker.utils.CustomSharedPreferance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RetrofitRestApiProvider mRetrofitRestApiProvider;
    private CustomSharedPreferance mCustomSharedPref;
    private static final int READ_REQUEST_CODE = 300;

    @BindView(R.id.phone_number_edt)
    AppCompatEditText mEdPhone;
    @BindView(R.id.pgProgressBar)
    ProgressBar pgProgressBar;

    @OnClick(R.id.btNext)
    public void onNext() {
        String phoneInput = mEdPhone.getText().toString();
        if (false == TextUtils.isEmpty(phoneInput) && phoneInput.length() == 10) {
            pgProgressBar.setVisibility(View.VISIBLE);
            mRetrofitRestApiProvider.loginUser(mCallback, mEdPhone.getText().toString(), mCustomSharedPref.getString(Constants.SHAREDPREF_PLAYER_ID));
        } else {
            Toast.makeText(MainActivity.this, R.string.please_enter_10_digit_no, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(MainActivity.this);
        getSupportActionBar().setTitle(Html.fromHtml(getString(R.string.phone_number_title)));
        mCustomSharedPref = new CustomSharedPreferance(MainActivity.this);
        mRetrofitRestApiProvider = new RetrofitRestApiProvider(MainActivity.this
                , Constants.DOMAIN_API);


    }


    @Override
    protected void onResume() {
        super.onResume();

        if (mCustomSharedPref.getString(Constants.SHARED_PREF_IS_USER_LOGIN) != null) {
            Intent siginActivity = new Intent(MainActivity.this, DashboardActivity.class);
            startActivity(siginActivity);
            finish();
        }

    }


    retrofit2.Callback mCallback = new Callback<LoginResponseDto>() {
        @Override
        public void onResponse(Call<LoginResponseDto> call, Response<LoginResponseDto> response) {
            pgProgressBar.setVisibility(View.GONE);
            LoginResponseDto loginResponseDto = response.body();
            mCustomSharedPref.addString(Constants.SHARED_PREF_PHONE, mEdPhone.getText().toString());
            if (loginResponseDto != null && loginResponseDto.getUserId() != null && Integer.parseInt(loginResponseDto.getUserId()) > 0) {
                // user is existing user take to dashbaord
                Intent siginActivity = new Intent(MainActivity.this, UserPasswordActivity.class);
                siginActivity.putExtra(Constants.EXTRA_PASSWORD, loginResponseDto.getPassword());
                mCustomSharedPref.addString(Constants.SHARED_PREF_USER_ID, loginResponseDto.getUserId());
                mCustomSharedPref.addString(Constants.SHARED_PREF_USER_PASSWORD, loginResponseDto.getPassword());
                mCustomSharedPref.addString(Constants.SHARED_PREF_ALL_MEMBERS, ChildTrackerUtils.convertObjectToJson(loginResponseDto.getMembers()));
                startActivity(siginActivity);
            } else {
                // user is new user redirect for futher information
                Intent siginActivity = new Intent(MainActivity.this, UserPasswordActivity.class);
                siginActivity.putExtra(Constants.EXTRA_MOBILE, mEdPhone.getText().toString());
                startActivity(siginActivity);
            }

        }

        @Override
        public void onFailure(Call<LoginResponseDto> call, Throwable t) {
            pgProgressBar.setVisibility(View.GONE);
        }
    };
}
