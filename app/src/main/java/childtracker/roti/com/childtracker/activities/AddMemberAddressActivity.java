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

public class AddMemberAddressActivity extends AppCompatActivity {

    @BindView(R.id.tvAddress)
    TextInputEditText tvAddress;


    @OnClick(R.id.btNext)
    public void onNext() {
        if (false == TextUtils.isEmpty(tvAddress.getText().toString())) {
            Intent activity = new Intent(AddMemberAddressActivity.this, AddMemberCommentActivity.class);
            activity.putExtra(Constants.EXTRA_ADDRESS, tvAddress.getText().toString());
            activity.putExtras(getIntent());
            startActivity(activity);
        } else {
            Toast.makeText(AddMemberAddressActivity.this, "Please enter valid address", Toast.LENGTH_LONG).show();
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_member_addresss_activity);
        ButterKnife.bind(AddMemberAddressActivity.this);
        getSupportActionBar().setTitle(Html.fromHtml(getString(R.string.add_memeber_address)));

    }

}
