package mao.android_send_mms_with_album_pictures;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

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
    private EditText editText1;
    private EditText editText2;
    private EditText editText3;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText1 = findViewById(R.id.EditText1);
        editText2 = findViewById(R.id.EditText2);
        editText3 = findViewById(R.id.EditText3);
        imageButton = findViewById(R.id.ImageButton);
        imageButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent albumIntent = new Intent(Intent.ACTION_GET_CONTENT);
                //设置内容类型为图像
                albumIntent.setType("image/*");
                //打开系统相册，并等待图片选择结果
                startActivityForResult(albumIntent, CHOOSE_CODE);
            }
        });
        findViewById(R.id.Button).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String phone = editText1.getText().toString();
                if (phone.equals(""))
                {
                    toastShow("请输入电话号码");
                    return;
                }
                String title = editText2.getText().toString();
                if (title.equals(""))
                {
                    toastShow("请输入标题");
                    return;
                }
                String message = editText3.getText().toString();
                if (message.equals(""))
                {
                    toastShow("请输入内容");
                    return;
                }
                if (uri == null)
                {
                    toastShow("请选择一张图片");
                    return;
                }

                sendMms(phone, title, message);

            }
        });

    }


    /**
     * 发送彩信
     *
     * @param phone   电话
     * @param title   标题
     * @param message 消息
     */
    private void sendMms(String phone, String title, String message)
    {
        // 创建一个发送动作的意图
        Intent intent = new Intent(Intent.ACTION_SEND);
        // 另外开启新页面
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // 需要读权限
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        // 彩信发送的目标号码
        intent.putExtra("address", phone);
        // 彩信的标题
        intent.putExtra("subject", title);
        // 彩信的内容
        intent.putExtra("sms_body", message);
        // mUri为彩信的图片附件
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        // 彩信的附件为图片
        intent.setType("image/*");
        // 部分手机无法直接跳到彩信发送页面，故而需要用户手动选择彩信应用
        //intent.setClassName("com.android.mms","com.android.mms.ui.ComposeMessageActivity");
        //因为未指定要打开哪个页面，所以系统会在底部弹出选择窗口
        startActivity(intent);
        toastShow("请在弹窗中选择短信或者信息应用");
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
            else
            {
                uri = null;
                //imageButton.setImageURI(null);
            }
        }
    }

    /**
     * 显示消息
     *
     * @param message 消息
     */
    private void toastShow(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}