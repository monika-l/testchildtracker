package childtracker.roti.com.childtracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;

import butterknife.ButterKnife;
import butterknife.OnClick;
import childtracker.roti.com.childtracker.R;

public class AddMemberAddressActivity extends AppCompatActivity {

    @OnClick(R.id.btNext)
    public void onNext() {
        Intent activity = new Intent(AddMemberAddressActivity.this, AddMemberCommentActivity.class);
        startActivity(activity);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_member_addresss_activity);
        ButterKnife.bind(AddMemberAddressActivity.this);
        getSupportActionBar().setTitle(Html.fromHtml(getString(R.string.add_memeber_address)));

    }

}
