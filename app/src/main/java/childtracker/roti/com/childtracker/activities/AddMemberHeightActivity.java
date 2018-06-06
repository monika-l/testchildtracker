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

public class AddMemberHeightActivity extends AppCompatActivity {

    @BindView(R.id.tvHeight)
    TextInputEditText tvHeight;


    @OnClick(R.id.btNext)
    public void onNext() {
        Intent activity = new Intent(AddMemberHeightActivity.this, AddMemberFathersNameActivity.class);
        activity.putExtras(getIntent());
        activity.putExtra(Constants.EXTRA_HEIGHT, tvHeight.getText().toString());
        startActivity(activity);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_member_height_activity);
        ButterKnife.bind(AddMemberHeightActivity.this);
        getSupportActionBar().setTitle(Html.fromHtml(getString(R.string.add_memeber_height)));

    }

}
