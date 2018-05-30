package childtracker.roti.com.childtracker.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import childtracker.roti.com.childtracker.R;
import childtracker.roti.com.childtracker.dto.NotificationsDto;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {

    private List<NotificationsDto.NotificationsMetaData> mMemberMetaData;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvMobile, tvAddress;

        public MyViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tvMemberName);
            tvMobile = (TextView) view.findViewById(R.id.tvMemberMobile);
            tvAddress = (TextView) view.findViewById(R.id.tvMessage);
        }
    }


    public NotificationAdapter(List<NotificationsDto.NotificationsMetaData> memberMetadata) {
        this.mMemberMetaData = memberMetadata;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notifications_component, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NotificationsDto.NotificationsMetaData member = mMemberMetaData.get(position);
        holder.tvName.setText(member.getMemberName());
        holder.tvMobile.setText(member.getMemberPhoneNo());
        holder.tvAddress.setText(member.getMemberMessage());
    }

    @Override
    public int getItemCount() {
        return mMemberMetaData.size();
    }
}