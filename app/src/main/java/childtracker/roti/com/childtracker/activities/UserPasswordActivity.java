package childtracker.roti.com.childtracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import childtracker.roti.com.childtracker.R;
import childtracker.roti.com.childtracker.utils.Constants;

public class UserPasswordActivity extends AppCompatActivity {


    @BindView(R.id.tvPassword)
    TextInputEditText mEdPassword;

    @OnClick(R.id.btNext)
    public void onSignup() {
        Intent signup = new Intent(UserPasswordActivity.this, UserEmailActivity.class);
        startActivity(signup);
    }

    @OnClick(R.id.btNext)
    public void onNext() {
        Intent dashbaordActivity = new Intent(UserPasswordActivity
                .this, UserEmailActivity.class);
        dashbaordActivity.putExtras(getIntent());
        dashbaordActivity.putExtra(Constants.EXTRA_PASSWORD,mEdPassword.getText().toString());
        startActivity(dashbaordActivity);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_password_activity);
        ButterKnife.bind(UserPasswordActivity.this);
        getSupportActionBar().setTitle(Html.fromHtml(getString(R.string.password_activity_title)));
    }
}
