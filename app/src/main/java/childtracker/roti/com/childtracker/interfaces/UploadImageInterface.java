package childtracker.roti.com.childtracker.interfaces;

import childtracker.roti.com.childtracker.retrofit.UploadObject;
import childtracker.roti.com.childtracker.utils.Constants;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface UploadImageInterface {
    @Multipart
    @POST(Constants.UPLOAD_API)
    Call<UploadObject> uploadFile(
            @Part MultipartBody.Part file,
            @Part("name") RequestBody name);

}