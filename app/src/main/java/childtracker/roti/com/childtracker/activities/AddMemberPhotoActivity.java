package childtracker.roti.com.childtracker.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import childtracker.roti.com.childtracker.R;
import childtracker.roti.com.childtracker.dto.GenericSuccessResponseDto;
import childtracker.roti.com.childtracker.dto.LoginResponseDto;
import childtracker.roti.com.childtracker.dto.MembersPojo;
import childtracker.roti.com.childtracker.interfaces.UploadImageInterface;
import childtracker.roti.com.childtracker.retrofit.RetrofitRestApiProvider;
import childtracker.roti.com.childtracker.retrofit.UploadObject;
import childtracker.roti.com.childtracker.utils.ChildTrackerUtils;
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

public class AddMemberPhotoActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {


    private static final String TAG = AddMemberPhotoActivity.class.getSimpleName();
    private ArrayList<String> addMoreImages = new ArrayList<String>();
    private static final int REQUEST_GALLERY_CODE = 200;
    private static final int REQUEST_ADDMORE_GALLERY_CODE = 400;
    private static final int READ_REQUEST_CODE = 300;
    private Uri uri;
    private String mUserPhoto = "";

    @BindView(R.id.ivProfilePic)
    ImageView ivProfilePic;

    @BindView(R.id.llAddMorePhotos)
    LinearLayout llAddMorePhotos;

    @BindView(R.id.pgProgressBar)
    ProgressBar pgProgressBar;

    @OnClick(R.id.tvAddMore)
    public void onAddMoreClick() {

        Intent openGalleryIntent = new Intent(Intent.ACTION_PICK);
        openGalleryIntent.setType("image/*");
        startActivityForResult(openGalleryIntent, REQUEST_ADDMORE_GALLERY_CODE);
    }


    @OnClick(R.id.llPhoto)
    public void onProfileClick() {
        Intent openGalleryIntent = new Intent(Intent.ACTION_PICK);
        openGalleryIntent.setType("image/*");
        startActivityForResult(openGalleryIntent, REQUEST_GALLERY_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, AddMemberPhotoActivity.this);
    }


    private CustomSharedPreferance mCustomSharedPref;

    @OnClick(R.id.btNext)
    public void onNext() {

        ArrayList<String> allChildImages = addMoreImages;
        if (false != TextUtils.isEmpty(mUserPhoto)) {
            allChildImages.add(mUserPhoto);
        }

        RetrofitRestApiProvider retrofitRestApiProvider = new RetrofitRestApiProvider(AddMemberPhotoActivity.this, Constants.DOMAIN_API);
        MembersPojo membersPojo = new MembersPojo();
        membersPojo.address = getIntent().getStringExtra(Constants.EXTRA_ADDRESS);
        membersPojo.age = getIntent().getStringExtra(Constants.EXTRA_AGE);
        membersPojo.comment = getIntent().getStringExtra(Constants.EXTRA_COMMENT);
        membersPojo.fatherName = getIntent().getStringExtra(Constants.EXTRA_FATHER_NAME);
        membersPojo.height = getIntent().getStringExtra(Constants.EXTRA_HEIGHT);
        membersPojo.memberName = getIntent().getStringExtra(Constants.EXTRA_NAME);
        membersPojo.motherName = getIntent().getStringExtra(Constants.EXTRA_MOTHER_NAME);
        membersPojo.userId = mCustomSharedPref.getString(Constants.SHARED_PREF_USER_ID);
        membersPojo.photo = allChildImages.size() > 0 ? TextUtils.join(",", allChildImages) : mUserPhoto;

        retrofitRestApiProvider.addMembers(mCallback, membersPojo);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_member_photo_activity);
        ButterKnife.bind(AddMemberPhotoActivity.this);
        getSupportActionBar().setTitle(Html.fromHtml(getString(R.string.photo_activity_title)));
        mCustomSharedPref = new CustomSharedPreferance(AddMemberPhotoActivity.this);
        EasyPermissions.requestPermissions(this, getString(R.string.read_file), READ_REQUEST_CODE, Manifest.permission.READ_EXTERNAL_STORAGE);
    }


    retrofit2.Callback mCallback = new Callback<GenericSuccessResponseDto>() {
        @Override
        public void onResponse(Call<GenericSuccessResponseDto> call, Response<GenericSuccessResponseDto> response) {
            GenericSuccessResponseDto genericSuccessResponseDto = response.body();
            if (genericSuccessResponseDto != null && genericSuccessResponseDto.getResult() != null) {

                RetrofitRestApiProvider mRetrofitRestApiProvider = new RetrofitRestApiProvider(AddMemberPhotoActivity.this, Constants.DOMAIN_API);
                mRetrofitRestApiProvider.loginUser(mFecthMemberCallback, mCustomSharedPref.getString(Constants.SHARED_PREF_PHONE), mCustomSharedPref.getString(Constants.SHAREDPREF_PLAYER_ID));


            }

        }

        @Override
        public void onFailure(Call<GenericSuccessResponseDto> call, Throwable t) {

        }
    };

    retrofit2.Callback mFecthMemberCallback = new Callback<LoginResponseDto>() {
        @Override
        public void onResponse(Call<LoginResponseDto> call, Response<LoginResponseDto> response) {
            pgProgressBar.setVisibility(View.GONE);
            LoginResponseDto loginResponseDto = response.body();
            if (loginResponseDto != null && loginResponseDto.getUserId() != null && Integer.parseInt(loginResponseDto.getUserId()) > 0) {
                // user is existing user take to dashbaord
                mCustomSharedPref.addString(Constants.SHARED_PREF_USER_ID, loginResponseDto.getUserId());
                mCustomSharedPref.addString(Constants.SHARED_PREF_ALL_MEMBERS, ChildTrackerUtils.convertObjectToJson(loginResponseDto.getMembers()));
                Intent siginActivity = new Intent(AddMemberPhotoActivity.this, DashboardActivity.class);
                siginActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(siginActivity);
                finish();
            }

        }

        @Override
        public void onFailure(Call<LoginResponseDto> call, Throwable t) {
            pgProgressBar.setVisibility(View.GONE);
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_GALLERY_CODE && resultCode == Activity.RESULT_OK) {
            uri = data.getData();
            if (EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                String filePath = getRealPathFromURIPath(uri, AddMemberPhotoActivity.this);
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
                pgProgressBar.setVisibility(View.VISIBLE);
                fileUpload.enqueue(new Callback<UploadObject>() {
                    @Override
                    public void onResponse(Call<UploadObject> call, Response<UploadObject> response) {
                        UploadObject uploadObject = response.body();
                        if (uploadObject != null && uploadObject.getStatus() != null && uploadObject.getStatus().equals("true")) {
                            Toast.makeText(AddMemberPhotoActivity.this, "Profile pic uploaded", Toast.LENGTH_LONG).show();
                            Picasso.with(AddMemberPhotoActivity.this).load(file).transform(new CircleTransform()).into(ivProfilePic);
                            mUserPhoto = uploadObject.getFile_path();
                        } else {
                            Toast.makeText(AddMemberPhotoActivity.this, "Failed to update profile pic", Toast.LENGTH_LONG).show();

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
        }
        if (requestCode == REQUEST_ADDMORE_GALLERY_CODE && resultCode == Activity.RESULT_OK) {
            uri = data.getData();
            if (EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                String filePath = getRealPathFromURIPath(uri, AddMemberPhotoActivity.this);
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
                pgProgressBar.setVisibility(View.VISIBLE);
                fileUpload.enqueue(new Callback<UploadObject>() {
                    @Override
                    public void onResponse(Call<UploadObject> call, Response<UploadObject> response) {
                        UploadObject uploadObject = response.body();
                        if (uploadObject != null && uploadObject.getStatus() != null && uploadObject.getStatus().equals("true")) {
                            Toast.makeText(AddMemberPhotoActivity.this, "Profile pic uploaded", Toast.LENGTH_LONG).show();
                            LayoutInflater inflater = (LayoutInflater) AddMemberPhotoActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);
                            View childLayout = inflater.inflate(
                                    R.layout.user_member_selection_component, null);
                            TextView userName = (TextView) childLayout
                                    .findViewById(R.id.tvUserName);
                            final ImageView userImage = (ImageView) childLayout
                                    .findViewById(R.id.ivUserImage);
                            Picasso.with(AddMemberPhotoActivity.this).load(file).transform(new CircleTransform()).into(userImage);
                            addMoreImages.add(uploadObject.getFile_path());
                            llAddMorePhotos.addView(childLayout);

//                            Picasso.with(AddMemberPhotoActivity.this).load(file).transform(new CircleTransform()).into(ivProfilePic);
//                            mUserPhoto = uploadObject.getFile_path();
                        } else {
                            Toast.makeText(AddMemberPhotoActivity.this, "Failed to update profile pic", Toast.LENGTH_LONG).show();
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
            String filePath = getRealPathFromURIPath(uri, AddMemberPhotoActivity.this);
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
                    Toast.makeText(AddMemberPhotoActivity.this, "Success " + response.message(), Toast.LENGTH_LONG).show();
                    Toast.makeText(AddMemberPhotoActivity.this, "Success " + response.body().toString(), Toast.LENGTH_LONG).show();
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
