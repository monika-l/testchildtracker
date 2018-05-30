package childtracker.roti.com.childtracker.adapter;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import childtracker.roti.com.childtracker.R;
import childtracker.roti.com.childtracker.dto.MemberDto;

public class NotifyAdapter extends RecyclerView.Adapter<NotifyAdapter.MyViewHolder> {

    private List<MemberDto.MemberMetadata> mMemberMetaData;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvMobile;
        Button btNotifiMessage;

        public MyViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tvMemberName);
            tvMobile = (TextView) view.findViewById(R.id.tvMemberPhone);
            btNotifiMessage = (Button) view.findViewById(R.id.notifyMemberMessage);
        }
    }


    public NotifyAdapter( Context context , List<MemberDto.MemberMetadata> memberMetadata) {
        mContext = context;
        this.mMemberMetaData = memberMetadata;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notify_component, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MemberDto.MemberMetadata member = mMemberMetaData.get(position);
        holder.tvName.setText(member.getMemberName());
        holder.tvMobile.setText(member.getMemberPhoneNo());
        holder.btNotifiMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.AppCompatAlertDialogStyle);
                LayoutInflater li = LayoutInflater.from(mContext);
                View dialogView = li.inflate(R.layout.message_dialog_component, null);
                builder.setView(dialogView);
                EditText mEdFinalDeliveryAddress = (EditText) dialogView
                        .findViewById(R.id.et_input);
//                mEdFinalDeliveryAddress.setText("Please Enter Message");
                mEdFinalDeliveryAddress.setTextColor(Color.WHITE);
                mEdFinalDeliveryAddress.getBackground().setColorFilter(mContext.getResources().getColor(R.color.blue),
                        PorterDuff.Mode.SRC_ATOP);
                mEdFinalDeliveryAddress.setInputType(InputType.TYPE_CLASS_TEXT);
                mEdFinalDeliveryAddress.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                mEdFinalDeliveryAddress.setLines(3);
                builder.setTitle("Notify message");
                builder.setPositiveButton("GO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMemberMetaData.size();
    }
}