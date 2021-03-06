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

public class AddMemberFathersNameActivity extends AppCompatActivity {

    @BindView(R.id.tvFatherName)
    TextInputEditText tvFatherName;


    @OnClick(R.id.btNext)
    public void onNext() {
        if (false == TextUtils.isEmpty(tvFatherName.getText().toString())) {
            Intent activity = new Intent(AddMemberFathersNameActivity.this, AddMemberMotherNameActivity.class);
            activity.putExtras(getIntent());
            activity.putExtra(Constants.EXTRA_FATHER_NAME, tvFatherName.getText().toString());
            startActivity(activity);
        } else {
            Toast.makeText(AddMemberFathersNameActivity.this, "Please enter valid father name", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_member_fathers_activity);
        ButterKnife.bind(AddMemberFathersNameActivity.this);
        getSupportActionBar().setTitle(Html.fromHtml(getString(R.string.add_memeber_fathers_name)));

    }

}
