package chekanov.space.zakupkitoxls;


import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class FTPWork {

    private FTPClient ftp = null;
    private String host;
    private String user;
    private String pass;

    public FTPWork(String host, String user, String pass) throws Exception{

        this.host = host;
        this.user = user;
        this.pass = pass;

        ftp = new FTPClient();
        int reply;
        ftp.connect(host);
        reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)){
        ftp.disconnect();
        throw new Exception("Exception in connecting to FTP Server");
        }
        boolean sucess = ftp.login(user, pass);
        if (!sucess){
            ftp.disconnect();
            throw new Exception("Could not login to the server");
        }

        ftp.setFileType(FTP.BINARY_FILE_TYPE);
        ftp.enterLocalPassiveMode();

    }

    public void reconnect() throws Exception{
        int reply;
        ftp.connect(host);
        reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)){
            ftp.disconnect();
            throw new Exception("Exception in connecting to FTP Server");
        }
        boolean sucess = ftp.login(user, pass);
        if (!sucess){
            ftp.disconnect();
            throw new Exception("Could not login to the server");
        }

        ftp.setFileType(FTP.BINARY_FILE_TYPE);
        ftp.enterLocalPassiveMode();
    }



    //заменить второй аргумент

    public void downloadFile(String remoteFilePath, File file) {


        try (FileOutputStream fos = new FileOutputStream(file)) {

            this.ftp.retrieveFile(remoteFilePath, fos);

        } catch (IOException e) {
            Log.e(MainActivity.TAG, "Error download file", e);
        }
    }
        public InputStream downloadStream (String remoteFilePath){
            InputStream os = null;
            try{
                os = this.ftp.retrieveFileStream(remoteFilePath);
            }
            catch (Exception e){
                Log.e(MainActivity.TAG, "Error download stream", e);
            }
            return os;
       }


    public String [] getNameFiles(String remotePath){
        String [] nameFiles = null;
        try {
            nameFiles = this.ftp.listNames(remotePath);
        }
        catch (Exception e){
            Log.e(MainActivity.TAG, "Error get name files", e);
        }
        return nameFiles;
    }

    public FTPFile [] getNameDirs(String remotePath){
        FTPFile[] nameDirs = null;
        try {
            nameDirs = this.ftp.listDirectories(remotePath);
            Log.d(MainActivity.TAG, "Number of directories" + String.valueOf(nameDirs.length));
        }
        catch (Exception e){
            Log.e(MainActivity.TAG, "Error get names dir", e);
        }
        return nameDirs;
    }

        public FTPFile [] getListFiles(String remotePath){
            FTPFile [] listFiles = null;
            try {
                listFiles = this.ftp.listFiles(remotePath);
            }
            catch (Exception e){
                Log.e(MainActivity.TAG, "Error list files", e);
            }
            return listFiles;
        }

    public void disconnect() {
        if (this.ftp.isConnected()) {
            try {
                this.ftp.logout();
                this.ftp.disconnect();
            } catch (IOException e) {
                Log.e(MainActivity.TAG, "Error disconnect", e);
                // do nothing as file is already downloaded from FTP server
            }
        }
    }

}