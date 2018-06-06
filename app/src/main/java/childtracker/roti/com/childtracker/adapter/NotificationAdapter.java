package childtracker.roti.com.childtracker.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import childtracker.roti.com.childtracker.R;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {

    private List<String> mMemberMetaData;
//    private List<NotificationsDto.NotificationsMetaData> mMemberMetaData;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvMessage, tvMobile, tvAddress;

        public MyViewHolder(View view) {
            super(view);
            tvMessage = (TextView) view.findViewById(R.id.tvMessage);

        }
    }


    public NotificationAdapter(List<String> memberMetadata) {
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
//        NotificationsDto.NotificationsMetaData member = mMemberMetaData.get(position);
//        holder.tvName.setText(member.getMemberName());
//        holder.tvMobile.setText(member.getMemberPhoneNo());
//        holder.tvAddress.setText(member.getMemberMessage());
        holder.tvMessage.setText(mMemberMetaData.get(position));
    }

    @Override
    public int getItemCount() {
        return mMemberMetaData.size();
    }
}