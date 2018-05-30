package childtracker.roti.com.childtracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;

import butterknife.ButterKnife;
import butterknife.OnClick;
import childtracker.roti.com.childtracker.R;

public class UserEmailActivity extends AppCompatActivity {


    @OnClick(R.id.btNext)
    public void onNext() {
        Intent dashbaordActivity = new Intent(UserEmailActivity
                .this, UserPhotoActivity.class);
        startActivity(dashbaordActivity);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_email_activity);
        ButterKnife.bind(UserEmailActivity.this);
        getSupportActionBar().setTitle(Html.fromHtml(getString(R.string.email_activity_title)));

    }
}
