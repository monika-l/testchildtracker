package childtracker.roti.com.childtracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;

import butterknife.ButterKnife;
import childtracker.roti.com.childtracker.R;

public class SendMessageToCommunityActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_message_to_community_activity);
        ButterKnife.bind(SendMessageToCommunityActivity.this);
        getSupportActionBar().setTitle(Html.fromHtml(getString(R.string.sending_message)));
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent activity = new Intent(SendMessageToCommunityActivity.this, SendMessageSuccessfulActivity.class);
                startActivity(activity);
            }
        }, 2500);
    }

}
