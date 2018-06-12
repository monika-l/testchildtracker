package childtracker.roti.com.childtracker.adapter;


import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import childtracker.roti.com.childtracker.R;
import childtracker.roti.com.childtracker.activities.ZoomPhotoActivity;
import childtracker.roti.com.childtracker.dto.NotificationDisplayDto;
import childtracker.roti.com.childtracker.retrofit.RetrofitRestApiProvider;
import childtracker.roti.com.childtracker.utils.CircleTransform;
import childtracker.roti.com.childtracker.utils.Constants;
import childtracker.roti.com.childtracker.utils.CustomSharedPreferance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<NotificationDisplayDto> mMemberMetaData;
    private CustomSharedPreferance customSharedPreferance;
//    private List<NotificationsDto.NotificationsMetaData> mMemberMetaData;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvMessage, tvReply, tvWhatsappUser;
        public ImageView ivUserImage;

        public MyViewHolder(View view) {
            super(view);
            tvMessage = (TextView) view.findViewById(R.id.tvMessage);
            tvWhatsappUser = (TextView) view.findViewById(R.id.tvWhatsappUser);
            tvReply = (TextView) view.findViewById(R.id.tvReply);
            ivUserImage = (ImageView) view.findViewById(R.id.ivUserImage);
        }
    }


    public NotificationAdapter(Context context, ArrayList<NotificationDisplayDto> memberMetadata) {
        this.mMemberMetaData = memberMetadata;
        mContext = context;
        customSharedPreferance = new CustomSharedPreferance(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notifications_component, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        if (mMemberMetaData.get(position).getUserMobile() != null) {
            String message = mMemberMetaData.get(position).getMessage();

            String displayMessage = "You have received reply from " + mMemberMetaData.get(position).getUserName() +
                    " for " + mMemberMetaData.get(position).getChildName() + " : " + "\n\n" + message;
            holder.tvMessage.setText(displayMessage);
            if(mMemberMetaData.get(position).getUserPhoto()!=null && TextUtils.isEmpty(mMemberMetaData.get(position).getUserPhoto()) ==false){
                Picasso.with(mContext).load("http://52.91.166.193/Webservices/ChildTracker/" + mMemberMetaData.get(position).getUserPhoto()).transform(new CircleTransform()).into(holder.ivUserImage);
            }
            holder.tvWhatsappUser.setText(mMemberMetaData.get(position).getUserMobile());
            holder.tvWhatsappUser.setVisibility(View.VISIBLE);

            holder.tvWhatsappUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openWhatsApp(mMemberMetaData.get(position).getUserMobile());
                }
            });
        } else {

            holder.tvMessage.setText(mMemberMetaData.get(position).getMessage());
            holder.tvWhatsappUser.setVisibility(View.GONE);
        }


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
                        retrofitRestApiProvider.sendReplyToUser(mCallback, mEdFinalDeliveryAddress.getText().toString(), mMemberMetaData.get(position).getMemberId(), customSharedPreferance.getString(Constants.SHARED_PREF_USER_ID));
                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.show();

            }
        });

        if (mMemberMetaData.get(position).getImages() != null && mMemberMetaData.get(position).getImages().length() > 0) {
            String[] allImages = mMemberMetaData.get(position).getImages().split(",");
            Picasso.with(mContext).load("http://52.91.166.193/Webservices/ChildTracker/" + allImages[0]).transform(new CircleTransform()).into(holder.ivUserImage);
            holder.ivUserImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent zoomPhotoActivity = new Intent(mContext, ZoomPhotoActivity.class);
                    zoomPhotoActivity.putExtra(Constants.EXTRA_PHOTO, mMemberMetaData.get(position).getImages());
                    mContext.startActivity(zoomPhotoActivity);
                }
            });

        }
    }

    private void openWhatsApp(String number) {
        try {
            number = number.replace(" ", "").replace("+", "");

            Intent sendIntent = new Intent("android.intent.action.MAIN");
            sendIntent.setComponent(new ComponentName("com.whatsapp","com.whatsapp.Conversation"));
            sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(number)+"@s.whatsapp.net");
            mContext.startActivity(sendIntent);

        } catch(Exception e) {
            Log.e("", "ERROR_OPEN_MESSANGER"+e.toString());
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