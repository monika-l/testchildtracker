package childtracker.roti.com.childtracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;

import butterknife.ButterKnife;
import butterknife.OnClick;
import childtracker.roti.com.childtracker.R;

public class UserPhotoActivity extends AppCompatActivity {


    @OnClick(R.id.btNext)
    public void onNext() {
        Intent dashbaordActivity = new Intent(UserPhotoActivity
                .this, DashboardActivity.class);
        startActivity(dashbaordActivity);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_photo_activity);
        ButterKnife.bind(UserPhotoActivity.this);
        getSupportActionBar().setTitle(Html.fromHtml(getString(R.string.photo_activity_title)));

    }
}
