package childtracker.roti.com.childtracker.adapter;


import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import childtracker.roti.com.childtracker.R;
import childtracker.roti.com.childtracker.dto.NotificationDisplayDto;
import childtracker.roti.com.childtracker.retrofit.RetrofitRestApiProvider;
import childtracker.roti.com.childtracker.utils.CircleTransform;
import childtracker.roti.com.childtracker.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<NotificationDisplayDto> mMemberMetaData;
//    private List<NotificationsDto.NotificationsMetaData> mMemberMetaData;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvMessage, tvReply, tvAddress;
public ImageView ivUserImage;
        public MyViewHolder(View view) {
            super(view);
            tvMessage = (TextView) view.findViewById(R.id.tvMessage);
            tvReply = (TextView) view.findViewById(R.id.tvReply);
            ivUserImage = (ImageView) view.findViewById(R.id.ivUserImage);
        }
    }


    public NotificationAdapter(Context context, ArrayList<NotificationDisplayDto> memberMetadata) {
        this.mMemberMetaData = memberMetadata;
        mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notifications_component, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.tvMessage.setText(mMemberMetaData.get(position).getMessage());

        if (mMemberMetaData.get(position).getMessage().contains("Message from community")) {
            holder.tvReply.setVisibility(View.VISIBLE);
        } else {
            holder.tvReply.setVisibility(View.GONE);
        }

        holder.tvReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.AppCompatAlertDialogStyle);
                LayoutInflater li = LayoutInflater.from(mContext);
                View dialogView = li.inflate(R.layout.edit_stock_layout, null);
                builder.setView(dialogView);
                final EditText mEdFinalDeliveryAddress = (EditText) dialogView
                        .findViewById(R.id.et_input);
                builder.setTitle("Enter mesaage");
                builder.setMessage("Enter your message");
                builder.setPositiveButton("GO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        RetrofitRestApiProvider retrofitRestApiProvider = new RetrofitRestApiProvider(mContext, Constants.DOMAIN_API);
                        retrofitRestApiProvider.sendReplyToUser(mCallback, mEdFinalDeliveryAddress.getText().toString(), mMemberMetaData.get(position).getMemberId());
                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.show();

            }
        });

        if(mMemberMetaData.get(position).getImages()!=null && mMemberMetaData.get(position).getImages().length()>0){
            String[] allImages = mMemberMetaData.get(position).getImages().split(",");
            Picasso.with(mContext).load("http://52.91.166.193/Webservices/ChildTracker/"+allImages[0]).transform(new CircleTransform()).into(holder.ivUserImage);
        }
    }

    retrofit2.Callback mCallback = new Callback() {
        @Override
        public void onResponse(Call call, Response response) {

        }

        @Override
        public void onFailure(Call call, Throwable t) {

        }
    };


    @Override
    public int getItemCount() {
        return mMemberMetaData.size();
    }
}