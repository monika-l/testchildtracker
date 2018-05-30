package childtracker.roti.com.childtracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;

import butterknife.ButterKnife;
import butterknife.OnClick;
import childtracker.roti.com.childtracker.R;

public class AddMemberPhotoActivity extends AppCompatActivity {

    @OnClick(R.id.btNext)
    public void onNext() {
        Intent activity = new Intent(AddMemberPhotoActivity.this, DashboardActivity.class);
        startActivity(activity);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_member_photo_activity);
        ButterKnife.bind(AddMemberPhotoActivity.this);
        getSupportActionBar().setTitle(Html.fromHtml(getString(R.string.photo_activity_title)));

    }

}
