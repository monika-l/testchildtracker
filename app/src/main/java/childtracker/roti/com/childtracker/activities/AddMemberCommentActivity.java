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

public class AddMemberCommentActivity extends AppCompatActivity {

    @BindView(R.id.tvComments)
    TextInputEditText tvComment;


    @OnClick(R.id.btNext)
    public void onNext() {
        if (false == TextUtils.isEmpty(tvComment.getText().toString())) {
            Intent activity = new Intent(AddMemberCommentActivity.this, AddMemberPhotoActivity.class);
            activity.putExtras(getIntent());
            activity.putExtra(Constants.EXTRA_COMMENT, tvComment.getText().toString());
            startActivity(activity);
        } else {
            Toast.makeText(AddMemberCommentActivity.this, "Please enter valid comment", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_member_coment_activity);
        ButterKnife.bind(AddMemberCommentActivity.this);
        getSupportActionBar().setTitle(Html.fromHtml(getString(R.string.add_memeber_comment)));
    }

}
