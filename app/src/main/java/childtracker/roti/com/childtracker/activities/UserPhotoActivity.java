package childtracker.roti.com.childtracker.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import childtracker.roti.com.childtracker.R;
import childtracker.roti.com.childtracker.dto.GenericSuccessResponseDto;
import childtracker.roti.com.childtracker.dto.LoginPojo;
import childtracker.roti.com.childtracker.interfaces.UploadImageInterface;
import childtracker.roti.com.childtracker.retrofit.RetrofitRestApiProvider;
import childtracker.roti.com.childtracker.retrofit.UploadObject;
import childtracker.roti.com.childtracker.utils.CircleTransform;
import childtracker.roti.com.childtracker.utils.Constants;
import childtracker.roti.com.childtracker.utils.CustomSharedPreferance;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserPhotoActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {


    private static final String TAG = UserPhotoActivity.class.getSimpleName();
    private static final int REQUEST_GALLERY_CODE = 200;
    private static final int READ_REQUEST_CODE = 300;
    private Uri uri;
    private String mUserPhoto = "";

    @BindView(R.id.pgProgressBar)
    ProgressBar pgProgressBar;

    @BindView(R.id.ivProfilePic)
    ImageView ivProfilePic;

    @OnClick(R.id.llPhoto)
    public void onProfileClick() {
        Intent openGalleryIntent = new Intent(Intent.ACTION_PICK);
        openGalleryIntent.setType("image/*");
        startActivityForResult(openGalleryIntent, REQUEST_GALLERY_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, UserPhotoActivity.this);
    }

    @BindView(R.id.tvName)
    TextInputEditText mEdName;
    private CustomSharedPreferance mCustomSharedPref;

    @OnClick(R.id.btNext)
    public void onNext() {
        if (false == TextUtils.isEmpty(mEdName.getText().toString())) {
            RetrofitRestApiProvider retrofitRestApiProvider = new RetrofitRestApiProvider(UserPhotoActivity.this, Constants.DOMAIN_API);
            LoginPojo loginPojo = new LoginPojo();
            loginPojo.email = getIntent().getStringExtra(Constants.EXTRA_EMAIL);
            loginPojo.mobile = getIntent().getStringExtra(Constants.EXTRA_MOBILE);
            loginPojo.name = mEdName.getText().toString();
            loginPojo.password = getIntent().getStringExtra(Constants.EXTRA_PASSWORD);
            loginPojo.photo = mUserPhoto;
            loginPojo.notificationToken = mCustomSharedPref.getString(Constants.SHAREDPREF_PLAYER_ID);
            retrofitRestApiProvider.registerUsers(mCallback, loginPojo);
        } else {
            Toast.makeText(UserPhotoActivity.this, R.string.please_enter_valid_name, Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_photo_activity);
        ButterKnife.bind(UserPhotoActivity.this);
        getSupportActionBar().setTitle(Html.fromHtml(getString(R.string.photo_activity_title)));
        mCustomSharedPref = new CustomSharedPreferance(UserPhotoActivity.this);
        EasyPermissions.requestPermissions(this, getString(R.string.read_file), READ_REQUEST_CODE, Manifest.permission.READ_EXTERNAL_STORAGE);
    }


    retrofit2.Callback mCallback = new Callback<GenericSuccessResponseDto>() {
        @Override
        public void onResponse(Call<GenericSuccessResponseDto> call, Response<GenericSuccessResponseDto> response) {
            GenericSuccessResponseDto genericSuccessResponseDto = response.body();
            if (genericSuccessResponseDto != null && genericSuccessResponseDto.getResult() != null) {
                mCustomSharedPref.addString(Constants.SHARED_PREF_USER_ID, genericSuccessResponseDto.getResult());
                mCustomSharedPref.addString(Constants.SHARED_PREF_IS_USER_LOGIN, "true");
                Intent dashbaordActivity = new Intent(UserPhotoActivity
                        .this, DashboardActivity.class);
                dashbaordActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(dashbaordActivity);
                finish();
            }

        }

        @Override
        public void onFailure(Call<GenericSuccessResponseDto> call, Throwable t) {

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        pgProgressBar.setVisibility(View.VISIBLE);
        if (requestCode == REQUEST_GALLERY_CODE && resultCode == Activity.RESULT_OK) {
            uri = data.getData();
            if (EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                String filePath = getRealPathFromURIPath(uri, UserPhotoActivity.this);
                final File file = new File(filePath);

                Log.d(TAG, "Filename " + file.getName());
                //RequestBody mFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
                MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("uploaded_file", file.getName(), mFile);
                RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Constants.DOMAIN_API)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                UploadImageInterface uploadImage = retrofit.create(UploadImageInterface.class);
                Call<UploadObject> fileUpload = uploadImage.uploadFile(fileToUpload, filename);
                fileUpload.enqueue(new Callback<UploadObject>() {
                    @Override
                    public void onResponse(Call<UploadObject> call, Response<UploadObject> response) {
                        UploadObject uploadObject = response.body();
                        if (uploadObject != null && uploadObject.getStatus() != null && uploadObject.getStatus().equals("true")) {
                            Toast.makeText(UserPhotoActivity.this, "Profile pic uploaded", Toast.LENGTH_LONG).show();
                            Picasso.with(UserPhotoActivity.this).load(file).transform(new CircleTransform()).into(ivProfilePic);
                            mUserPhoto = uploadObject.getFile_path();
                        } else {
                            Toast.makeText(UserPhotoActivity.this, "Failed to update profile pic", Toast.LENGTH_LONG).show();

                        }

                        pgProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<UploadObject> call, Throwable t) {
                        Log.d(TAG, "Error " + t.getMessage());
                        pgProgressBar.setVisibility(View.GONE);
                    }
                });
            } else {
                EasyPermissions.requestPermissions(this, getString(R.string.read_file), READ_REQUEST_CODE, Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        } else {
            pgProgressBar.setVisibility(View.GONE);
        }
    }

    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if (uri != null) {
            String filePath = getRealPathFromURIPath(uri, UserPhotoActivity.this);
            File file = new File(filePath);
            RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), mFile);
            RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.DOMAIN_API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            UploadImageInterface uploadImage = retrofit.create(UploadImageInterface.class);
            Call<UploadObject> fileUpload = uploadImage.uploadFile(fileToUpload, filename);
            fileUpload.enqueue(new Callback<UploadObject>() {
                @Override
                public void onResponse(Call<UploadObject> call, Response<UploadObject> response) {
                    Toast.makeText(UserPhotoActivity.this, "Success " + response.message(), Toast.LENGTH_LONG).show();
                    Toast.makeText(UserPhotoActivity.this, "Success " + response.body().toString(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<UploadObject> call, Throwable t) {
                    Log.d(TAG, "Error " + t.getMessage());
                }
            });
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.d(TAG, "Permission has been denied");
    }

}
