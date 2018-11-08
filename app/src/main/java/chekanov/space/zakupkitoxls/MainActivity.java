package chekanov.space.zakupkitoxls;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.utils.IOUtils;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;


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

            public void run() {
                try {
                    String remotePath = "/fcs_regions/Adygeja_Resp/notifications/currMonth/";

                    FTPWork ftpWorker = new FTPWork(server, user, pass);
                    String[] nameFiles = ftpWorker.getNameFiles(remotePath);
                    for (final String fileName : nameFiles) {
                        InputStream zis = ftpWorker.downloadStream(fileName);
                        if (zis != null)
                        {
                            BufferedInputStream zin = new BufferedInputStream(zis);
                            try (ArchiveInputStream ain = new ArchiveStreamFactory().createArchiveInputStream(zin)) {
                                ArchiveEntry entry = null;
                                while ((entry = ain.getNextEntry()) != null) {

                                    Log.d(TAG, String.valueOf(entry.getName()));
                                    byte [] buf = new byte[(int)entry.getSize()];
                                    int readed = IOUtils.readFully(ain, buf);
                                    if(readed != buf.length) {
                                        throw new RuntimeException("Read bytes count and entry size differ");
                                    }
                                    XMLWorkParser parser = new XMLWorkParser();
                                    InputStream pis = new ByteArrayInputStream(buf);
                                    parser.parse(pis);
                                }
                            } catch (Exception e) {
                                //Вписать обработку ошибки
                                Log.e(TAG, "Ошибка FTPWork", e);
                            }
                        }
                        else {
                            ftpWorker.reconnect();
                        }
                       /* if (zis != null) {
                            zis.close();
                        }*/
                    }
                } catch (Exception e) {
                    //Вписать обработку ошибки
                    Log.e(TAG, "Ошибка FTPWork", e);
                }
            }
        }
        NetWorkThread netWorkThread = new NetWorkThread();
        netWorkThread.start();
    }

}