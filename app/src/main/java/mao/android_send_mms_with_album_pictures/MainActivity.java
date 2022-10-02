package mao.android_send_mms_with_album_pictures;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity
{

    /**
     * 标签
     */
    private static final String TAG = "MainActivity";

    /**
     * 文件的路径对象
     */
    private Uri uri;
    /**
     * 选择照片的请求码
     */
    private final int CHOOSE_CODE = 1238;


    private ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        super.onActivityResult(requestCode, resultCode, intent);

        if (resultCode == RESULT_OK && requestCode == CHOOSE_CODE)
        {
            //从相册选择一张照片
            if (intent.getData() != null)
            {
                //数据非空，表示选中了某张照片
                //获得选中照片的路径对象
                uri = intent.getData();
                //设置图像视图的路径对象
                imageButton.setImageURI(uri);

                Log.d(TAG, "uri.getPath=" + uri.getPath() + ",uri.toString=" + uri.toString());
            }
        }
    }
}