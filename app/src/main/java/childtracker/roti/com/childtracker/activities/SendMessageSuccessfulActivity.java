package childtracker.roti.com.childtracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;

import butterknife.ButterKnife;
import butterknife.OnClick;
import childtracker.roti.com.childtracker.R;

public class SendMessageSuccessfulActivity extends AppCompatActivity {

    @OnClick(R.id.btOk)
    public void onNext() {
        Intent activity = new Intent(SendMessageSuccessfulActivity.this, DashboardActivity.class);
        startActivity(activity);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_message_to_community_successful_activity);
        ButterKnife.bind(SendMessageSuccessfulActivity.this);
        getSupportActionBar().setTitle(Html.fromHtml(getString(R.string.message_success)));

    }

}
