package childtracker.roti.com.childtracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;

import butterknife.ButterKnife;
import butterknife.OnClick;
import childtracker.roti.com.childtracker.R;

public class MainActivity extends AppCompatActivity {


    @OnClick(R.id.btNext)
    public void onNext() {
        Intent siginActivity = new Intent(MainActivity.this, UserPasswordActivity.class);
        startActivity(siginActivity);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(MainActivity.this);
        getSupportActionBar().setTitle(Html.fromHtml(getString(R.string.phone_number_title)));

    }
}
