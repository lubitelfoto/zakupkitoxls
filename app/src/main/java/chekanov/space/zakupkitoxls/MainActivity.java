package chekanov.space.zakupkitoxls;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


import org.apache.commons.net.ftp.FTPFile;


import java.io.File;


public class MainActivity extends AppCompatActivity {

    public static String TAG = "ZAK_log";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final File fileDir = getFilesDir();

        DownLoadThread downLoadThread = new DownLoadThread(fileDir);
        downLoadThread.start();
    }

}