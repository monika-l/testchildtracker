package childtracker.roti.com.childtracker.activities;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import childtracker.roti.com.childtracker.R;
import childtracker.roti.com.childtracker.utils.CircleTransform;
import childtracker.roti.com.childtracker.utils.Constants;

public class ZoomPhotoActivity extends AppCompatActivity {

    private int position = 0;
    @BindView(R.id.ivUserImage)
    ImageView mIvImage;

    @OnClick(R.id.btPrevious)
    public void onPrevious() {
        if (position > 0) {
            position--;
            Picasso.with(ZoomPhotoActivity.this).load(Constants.IMAGE_PREFIX_PATH + mAllImages.get(position)).transform(new CircleTransform()).into(mIvImage);
        }
    }

    @OnClick(R.id.btNext)
    public void onNext() {
        if (position <= mAllImages.size() - 2) {
    position++;
            Picasso.with(ZoomPhotoActivity.this).load(Constants.IMAGE_PREFIX_PATH + mAllImages.get(position)).transform(new CircleTransform()).into(mIvImage);
        }
    }


    ArrayList<String> mAllImages = new ArrayList<String>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zoom_photo_activity);
        ButterKnife.bind(ZoomPhotoActivity.this);
        String allImages = getIntent().getStringExtra(Constants.EXTRA_PHOTO);
        if (allImages != null && TextUtils.isEmpty(allImages) == false) {
            String[] images = allImages.split(",");
            mAllImages = new ArrayList(Arrays.asList(images));
            Picasso.with(ZoomPhotoActivity.this).load(Constants.IMAGE_PREFIX_PATH + mAllImages.get(0)).transform(new CircleTransform()).into(mIvImage);
        }
    }
}
