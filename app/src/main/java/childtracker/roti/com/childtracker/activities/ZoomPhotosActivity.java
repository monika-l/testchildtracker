package childtracker.roti.com.childtracker.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import childtracker.roti.com.childtracker.R;
import childtracker.roti.com.childtracker.utils.Constants;

public class ZoomPhotosActivity extends AppCompatActivity {


    List<String> allImages = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zoom_photo_activity);
        ButterKnife.bind(ZoomPhotosActivity.this);
        String allImagesString = getIntent().getStringExtra(Constants.EXTRA_PHOTO);

        if (allImagesString != null && false == TextUtils.isEmpty(allImagesString)) {
            String[] imagesArray = allImagesString.split(",");
            allImages = Arrays.asList(imagesArray);
        }


    }

}
