package childtracker.roti.com.childtracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.text.Html;
import android.view.View;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import childtracker.roti.com.childtracker.R;
import childtracker.roti.com.childtracker.dto.LoginResponseDto;
import childtracker.roti.com.childtracker.retrofit.RetrofitRestApiProvider;
import childtracker.roti.com.childtracker.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RetrofitRestApiProvider mRetrofitRestApiProvider;
    @BindView(R.id.phone_number_edt)
    AppCompatEditText mEdPhone;
    @BindView(R.id.pgProgressBar)
    ProgressBar pgProgressBar;

    @OnClick(R.id.btNext)
    public void onNext() {
        pgProgressBar.setVisibility(View.VISIBLE);
        mRetrofitRestApiProvider.loginUser(mCallback, mEdPhone.getText().toString());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(MainActivity.this);
        getSupportActionBar().setTitle(Html.fromHtml(getString(R.string.phone_number_title)));
        mRetrofitRestApiProvider = new RetrofitRestApiProvider(MainActivity.this
                , Constants.DOMAIN_API);
    }


    retrofit2.Callback mCallback = new Callback<LoginResponseDto>() {
        @Override
        public void onResponse(Call<LoginResponseDto> call, Response<LoginResponseDto> response) {
            LoginResponseDto loginResponseDto = response.body();
            if (loginResponseDto != null && loginResponseDto.getUserId() != null && Integer.parseInt(loginResponseDto.getUserId()) > 0) {
                // user is existing user take to dashbaord
                Intent siginActivity = new Intent(MainActivity.this, DashboardActivity.class);
                startActivity(siginActivity);
                finish();
            } else {
                // user is new user redirect for futher information
                Intent siginActivity = new Intent(MainActivity.this, UserPasswordActivity.class);
                siginActivity.putExtra(Constants.EXTRA_MOBILE,mEdPhone.getText().toString());
                startActivity(siginActivity);
            }
            pgProgressBar.setVisibility(View.GONE);
        }

        @Override
        public void onFailure(Call<LoginResponseDto> call, Throwable t) {
            pgProgressBar.setVisibility(View.GONE);
        }
    };
}
