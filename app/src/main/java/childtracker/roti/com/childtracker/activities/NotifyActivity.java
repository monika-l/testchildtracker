package childtracker.roti.com.childtracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import childtracker.roti.com.childtracker.R;
import childtracker.roti.com.childtracker.adapter.NotifyAdapter;
import childtracker.roti.com.childtracker.dto.MemberDto;

public class NotifyActivity extends AppCompatActivity {


    private ArrayList<MemberDto.MemberMetadata> memberDtos = new ArrayList<MemberDto.MemberMetadata>();

    @BindView(R.id.rvMembersList)
    RecyclerView rvMembersList;


    @OnClick(R.id.btAddMember)
    public void onMemberAdd() {
        Intent addMemberActivity = new Intent(NotifyActivity.this, AddMemberActivity.class);
        startActivity(addMemberActivity);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notify_activity);
        ButterKnife.bind(NotifyActivity.this);
        getSupportActionBar().setTitle(Html.fromHtml(getString(R.string.notify_activity_title)));
        prepareData();

        NotifyAdapter adapter = new NotifyAdapter(NotifyActivity.this,memberDtos);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvMembersList.setLayoutManager(mLayoutManager);
        rvMembersList.setItemAnimator(new DefaultItemAnimator());
        rvMembersList.setAdapter(adapter);
    }

    private void prepareData()

    {
        MemberDto.MemberMetadata memberMetadata = new MemberDto().new MemberMetadata();
        memberMetadata.setMemberImage("http://google.com");
        memberMetadata.setMemberName("Sachin Shah");
        memberMetadata.setMemberPhoneNo("874554120");
        memberDtos.add(memberMetadata);

        memberMetadata = new MemberDto().new MemberMetadata();
        memberMetadata.setMemberImage("http://google.com");
        memberMetadata.setMemberName("Meena Shah");
        memberMetadata.setMemberPhoneNo("874998545");
        memberDtos.add(memberMetadata);
    }
}
