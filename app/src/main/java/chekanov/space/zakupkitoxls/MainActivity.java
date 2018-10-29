package chekanov.space.zakupkitoxls;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.zip.InflaterInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class MainActivity extends AppCompatActivity {

    public static String TAG = "ZAK_log";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String user = getResources().getString(R.string.ftp_username);
        final String pass = getResources().getString(R.string.ftp_password);
        final String server = getResources().getString(R.string.ftp_server);
        final File fileDir =  this.getFilesDir();


        class NetWorkThread extends Thread {

            public void run(){
        try {
            FTPWork ftpWorker = new FTPWork(server, user, pass);
            File file = new File(fileDir, "notification_Adygeja_Resp_2018101000_2018101100_003.xml.zip");
            ftpWorker.downloadFile("/fcs_regions/Adygeja_Resp/notifications/currMonth/notification_Adygeja_Resp_2018101000_2018101100_003.xml.zip",
                    file);
            Log.d(TAG, String.valueOf(file.exists()));
            if(file.exists()) {
                FileInputStream zipfin = new FileInputStream(file);
                BufferedInputStream zin = new BufferedInputStream(zipfin);

                try (ArchiveInputStream ain = new ArchiveStreamFactory().createArchiveInputStream(zin)){
                    ArchiveEntry entry = null;
                    while ((entry = ain.getNextEntry()) != null){
                        Log.d(TAG, entry.getName());
                    }
                }


            }
                //ZIPWork zipWork = new ZIPWork(file);
                //zipWork.unZip();}
        }
        catch (Exception e){
            //Вписать обработку ошибки
            Log.e(TAG, "Ошибка FTPWork", e);
        }
        }
        }
        NetWorkThread netWorkThread = new NetWorkThread();
        netWorkThread.start();
    }
}
