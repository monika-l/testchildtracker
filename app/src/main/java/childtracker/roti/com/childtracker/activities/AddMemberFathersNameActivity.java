package childtracker.roti.com.childtracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;

import butterknife.ButterKnife;
import butterknife.OnClick;
import childtracker.roti.com.childtracker.R;

public class AddMemberFathersNameActivity extends AppCompatActivity {

    @OnClick(R.id.btNext)
    public void onNext() {
        Intent activity = new Intent(AddMemberFathersNameActivity.this, AddMemberMotherNameActivity.class);
        startActivity(activity);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_member_fathers_activity);
        ButterKnife.bind(AddMemberFathersNameActivity.this);
        getSupportActionBar().setTitle(Html.fromHtml(getString(R.string.add_memeber_fathers_name)));

    }

}
