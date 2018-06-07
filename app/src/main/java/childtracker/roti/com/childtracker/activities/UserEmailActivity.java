package childtracker.roti.com.childtracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import childtracker.roti.com.childtracker.R;
import childtracker.roti.com.childtracker.utils.Constants;

public class UserEmailActivity extends AppCompatActivity {

    @BindView(R.id.tvEmail)
    TextInputEditText mEdEmail;

    @OnClick(R.id.btNext)
    public void onNext() {
        if( false == TextUtils.isEmpty(mEdEmail.getText().toString())) {
            Intent dashbaordActivity = new Intent(UserEmailActivity
                    .this, UserPhotoActivity.class);
            dashbaordActivity.putExtras(getIntent());
            dashbaordActivity.putExtra(Constants.EXTRA_EMAIL,mEdEmail.getText().toString());
            startActivity(dashbaordActivity);
        }else{
            Toast.makeText(UserEmailActivity.this, R.string.please_enter_valid_email_di,Toast.LENGTH_LONG).show();
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_email_activity);
        ButterKnife.bind(UserEmailActivity.this);
        getSupportActionBar().setTitle(Html.fromHtml(getString(R.string.email_activity_title)));

    }
}
