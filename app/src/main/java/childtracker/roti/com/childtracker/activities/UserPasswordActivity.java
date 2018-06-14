package childtracker.roti.com.childtracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import childtracker.roti.com.childtracker.R;
import childtracker.roti.com.childtracker.utils.ChildTrackerUtils;
import childtracker.roti.com.childtracker.utils.Constants;
import childtracker.roti.com.childtracker.utils.CustomSharedPreferance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserPasswordActivity extends AppCompatActivity {


    @BindView(R.id.tvPassword)
    TextInputEditText mEdPassword;

    @BindView(R.id.tvFrogotPassword)
    TextView mForgotPassword;

    @OnClick(R.id.tvFrogotPassword)
    public void onForgotPassword() {
        ChildTrackerUtils.sendPasswordToMobile(UserPasswordActivity.this, mCallback, mMobile, "Your password with childtracker is : " + mPassword);
        Toast.makeText(UserPasswordActivity.this, "Password has been send to your mobile", Toast.LENGTH_LONG).show();
    }

    private String mPassword;
    private String mMobile;


    @OnClick(R.id.btNext)
    public void onSignup() {
        if (mPassword == null) {
            Intent dashbaordActivity = new Intent(UserPasswordActivity
                    .this, UserEmailActivity.class);
            dashbaordActivity.putExtras(getIntent());
            dashbaordActivity.putExtra(Constants.EXTRA_PASSWORD, mEdPassword.getText().toString());
            startActivity(dashbaordActivity);
        } else {
            if (mPassword.equals(mEdPassword.getText().toString()) == true) {
                CustomSharedPreferance customSharedPreferance = new CustomSharedPreferance(UserPasswordActivity.this);
                customSharedPreferance.addString(Constants.SHARED_PREF_IS_USER_LOGIN, "true");
                Intent signup = new Intent(UserPasswordActivity.this, DashboardActivity.class);
                signup.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(signup);
                finish();
            } else {
                Toast.makeText(UserPasswordActivity.this, R.string.please_enter_correct_password, Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_password_activity);
        ButterKnife.bind(UserPasswordActivity.this);
        getSupportActionBar().setTitle(Html.fromHtml(getString(R.string.password_activity_title)));
        checkifNewUserOrExisting();
    }

    private void checkifNewUserOrExisting() {
        mPassword = getIntent().getStringExtra(Constants.EXTRA_PASSWORD);
        mMobile = getIntent().getStringExtra(Constants.EXTRA_MOBILE);
        if (mPassword == null) {
            mEdPassword.setHint(R.string.please_create_password);
            mForgotPassword.setVisibility(View.GONE);
        } else {
            mEdPassword.setHint(R.string.please_enter_password);
            mForgotPassword.setVisibility(View.VISIBLE);

        }
    }


    retrofit2.Callback mCallback = new Callback() {
        @Override
        public void onResponse(Call call, Response response) {

        }

        @Override
        public void onFailure(Call call, Throwable t) {

        }
    };
}
