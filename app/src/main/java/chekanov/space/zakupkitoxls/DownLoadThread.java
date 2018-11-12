package chekanov.space.zakupkitoxls;

import android.util.Log;

import org.apache.commons.net.ftp.FTPFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static chekanov.space.zakupkitoxls.MainActivity.TAG;

public class DownLoadThread extends Thread {

    private String user = Constant.user;
    private String pass = Constant.pass;
    private String server = Constant.server;
    private String ftpPath = Constant.ftpPath;
    private String remotePath = Constant.remotePath;

    private File fileDir;

    public DownLoadThread(File filedir){
        this.fileDir = filedir;
    }

    @Override
    public void run() {
        try {
            FTPWork ftpWorker = new FTPWork(server, user, pass);

            FTPFile[] nameDirs = ftpWorker.getNameDirs(Constant.ftpPath);
            for (FTPFile dir : nameDirs) {
                List<ZakupkiItem> zakupkiItems = new ArrayList<>();
                String[] nameFiles = ftpWorker.getNameFiles(ftpPath + dir.getName() + remotePath);
                while (nameFiles == null) {
                    ftpWorker.disconnect();
                    ftpWorker.reconnect();
                    nameFiles = ftpWorker.getNameFiles(ftpPath + dir.getName() + remotePath);
                }
                for (String fileName : nameFiles) {
                    InputStream zis = ftpWorker.downloadStream(fileName);
                    if (zis != null) {
                        UnzipThread unzipThread = new UnzipThread(zis, zakupkiItems);
                        unzipThread.start();
                    } else {
                        ftpWorker.reconnect();
                    }
                }

                try {
                    XLSWork.writeXlsxFile(zakupkiItems, fileDir, dir.getName());
                } catch (IOException e) {
                    Log.e(TAG, "XLSWork exception", e);
                }
            }
        }
        catch (Exception e){
                Log.e(TAG, "DownloadThread exception", e);
            }
    }
}
