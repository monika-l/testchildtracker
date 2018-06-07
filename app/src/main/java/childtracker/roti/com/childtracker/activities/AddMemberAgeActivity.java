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

public class AddMemberAgeActivity extends AppCompatActivity {


    @BindView(R.id.tvAge)
    TextInputEditText tvAge;

    @OnClick(R.id.btNext)
    public void onNext() {
        if(false == TextUtils.isEmpty(tvAge.getText().toString())){
            Intent activity = new Intent(AddMemberAgeActivity.this, AddMemberHeightActivity.class);
            activity.putExtra(Constants.EXTRA_AGE, tvAge.getText().toString());
            activity.putExtras(getIntent());
            startActivity(activity);
        }else {
            Toast.makeText(AddMemberAgeActivity.this, "Please enter valid name", Toast.LENGTH_LONG).show();
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_member_age_activity);
        ButterKnife.bind(AddMemberAgeActivity.this);
        getSupportActionBar().setTitle(Html.fromHtml(getString(R.string.add_memeber_age)));
    }

}
