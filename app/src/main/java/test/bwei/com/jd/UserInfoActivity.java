package test.bwei.com.jd;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import test.bwei.com.jd.Bean.User;

public class UserInfoActivity extends AppCompatActivity {

    private ImageView head;
    private TextView username;
    private TextView nickname;
    private SharedPreferences sp;
    private RelativeLayout touxiang;
    private String nicknames;
    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    private static final int CROP_SMALL_PICTURE = 2;
    private static final int UPDATANICK= 3;
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/*");
    protected static Uri tempUri;
    private ByteArrayOutputStream byteArrayOutputStream;
    private File file;
    private RelativeLayout nick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        getSupportActionBar().hide();
        sp = getSharedPreferences("user_uid", MODE_PRIVATE);
        initView();
        initData();
    }

    private void initData() {
        String uid = sp.getString("uid", null);
        OkHttpClient ok = new OkHttpClient();
        FormBody.Builder bulider = new FormBody.Builder();
        bulider.add("uid", uid);
        FormBody formBody = bulider.build();
        Request request = new Request.Builder().post(formBody).url(Api.USERINFO).build();
        ok.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                Gson gson = new Gson();
                final User user = gson.fromJson(s, User.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        username.setText(user.data.username);
                        nickname.setText(user.data.nickname);
                        nicknames = user.data.nickname;
                        String s = (String) user.data.icon;
                        Glide.with(UserInfoActivity.this).load(s).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true) .dontAnimate().into(head);
                    }
                });

            }
        });
    }

    private void initView() {
        head = (ImageView) findViewById(R.id.imageView);
        username = (TextView) findViewById(R.id.username);
        nickname = (TextView) findViewById(R.id.nickname);
        touxiang = (RelativeLayout) findViewById(R.id.touxiang);
        nick = (RelativeLayout) findViewById(R.id.nick);
        touxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload();
            }
        });
        nick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserInfoActivity.this, NickActivity.class);
                intent.putExtra("nick", nicknames);
                startActivityForResult(intent, 3);
            }
        });
    }

    private void upload() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("设置头像");
        String[] items = {"选择本地照片", "拍照"};
        builder.setNegativeButton("取消", null);
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case CHOOSE_PICTURE: // 选择本地照片
                        Intent openAlbumIntent = new Intent(
                                Intent.ACTION_GET_CONTENT);
                        openAlbumIntent.setType("image/*");
                        startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
                        break;
                    case TAKE_PICTURE: // 拍照
                        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                            Intent openCameraIntent = new Intent(
                                    MediaStore.ACTION_IMAGE_CAPTURE);
                            tempUri = Uri.fromFile(new File(Environment
                                    .getExternalStorageDirectory(), "image.jpg"));
                            // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
                            openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                            startActivityForResult(openCameraIntent, TAKE_PICTURE);
                        }

                        break;
                }
            }
        });
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) { // 如果返回码是可以用的
            switch (requestCode) {
                case TAKE_PICTURE:
                    startPhotoZoom(tempUri); // 开始对图片进行裁剪处理
                    break;
                case CHOOSE_PICTURE:
                    startPhotoZoom(data.getData()); // 开始对图片进行裁剪处理
                    break;
                case CROP_SMALL_PICTURE:
                    if (data != null) {
                        setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
                    }
                    break;


        }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!TextUtils.isEmpty(sp.getString("uid", null))) {
            String uid = sp.getString("uid", null);
            OkHttpClient ok = new OkHttpClient();
            FormBody.Builder bulider = new FormBody.Builder();
            bulider.add("uid", uid);
            FormBody formBody = bulider.build();
            Request request = new Request.Builder().post(formBody).url(Api.USERINFO).build();
            ok.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String s = response.body().string();
                    Gson gson = new Gson();
                    final User user = gson.fromJson(s, User.class);

                  runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                nickname.setText(user.data.nickname);
                                System.out.println(user.data.icon);
                                String s = (String) user.data.icon;
                                Glide.with(UserInfoActivity.this).load(s).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true) .dontAnimate().into(head);
                            }
                        });




                }
            });
        }


    }

    protected void startPhotoZoom(Uri uri) {
        if (uri == null) {

        }
        tempUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 340);
        intent.putExtra("outputY", 340);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }

    protected void setImageToView(Intent data) {
        Bundle extras = data.getExtras();

        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            head.setImageBitmap(photo);
            try {
                String uid = sp.getString("uid", null);
                byteArrayOutputStream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] bytes=byteArrayOutputStream.toByteArray();
                FileOutputStream fos=new FileOutputStream(new File(Environment.getExternalStorageDirectory()+"/head.png"));
                fos.write(bytes);
                OkHttpClient okHttpClient = new OkHttpClient();
                MultipartBody.Builder mult = new MultipartBody.Builder().setType(MultipartBody.FORM);
                mult.addFormDataPart("uid", uid);
                        mult.addFormDataPart("file", "head.png", RequestBody.create(MEDIA_TYPE_PNG, new File(Environment.getExternalStorageDirectory()+"/head.png")));
                MultipartBody body = mult.build();
                Request request = new Request.Builder()
                        .url(Api.UPLOAD)
                        .post(body).build();
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(UserInfoActivity.this, "上传成功", Toast.LENGTH_SHORT).show();

                            }
                        });

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            } finally {

            }
        }
    }
}
