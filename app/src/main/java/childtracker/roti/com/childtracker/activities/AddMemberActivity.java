package childtracker.roti.com.childtracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;

import butterknife.ButterKnife;
import butterknife.OnClick;
import childtracker.roti.com.childtracker.R;

public class AddMemberActivity extends AppCompatActivity {

    @OnClick(R.id.btNext)
    public void onNext() {
        Intent activity = new Intent(AddMemberActivity.this, AddMemberAgeActivity.class);
        startActivity(activity);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_member_activity);
        ButterKnife.bind(AddMemberActivity.this);
        getSupportActionBar().setTitle(Html.fromHtml(getString(R.string.add_memeber__child_name)));

    }

}