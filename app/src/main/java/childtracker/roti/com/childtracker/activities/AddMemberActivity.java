package childtracker.roti.com.childtracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import childtracker.roti.com.childtracker.R;
import childtracker.roti.com.childtracker.utils.Constants;

public class AddMemberActivity extends AppCompatActivity {

    @BindView(R.id.tvName)
    TextInputEditText tvName;



    @OnClick(R.id.btNext)
    public void onNext() {
        Intent activity = new Intent(AddMemberActivity.this, AddMemberAgeActivity.class);
        activity.putExtra(Constants.EXTRA_NAME,tvName.getText().toString());
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
