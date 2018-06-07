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

public class AddMemberMotherNameActivity extends AppCompatActivity {

    @BindView(R.id.tvMotherName)
    TextInputEditText tvMotherName;


    @OnClick(R.id.btNext)
    public void onNext() {
        if (false == TextUtils.isEmpty(tvMotherName.getText().toString())) {
            Intent activity = new Intent(AddMemberMotherNameActivity.this, AddMemberAddressActivity.class);
            activity.putExtras(getIntent());
            activity.putExtra(Constants.EXTRA_MOTHER_NAME, tvMotherName.getText().toString());
            startActivity(activity);
        } else {
            Toast.makeText(AddMemberMotherNameActivity.this, "Please enter valid mother name", Toast.LENGTH_LONG).show();
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_member_mothers_activity);
        ButterKnife.bind(AddMemberMotherNameActivity.this);
        getSupportActionBar().setTitle(Html.fromHtml(getString(R.string.add_memeber_mothers_name)));

    }

}
