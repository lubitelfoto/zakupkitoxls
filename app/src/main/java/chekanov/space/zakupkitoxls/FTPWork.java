package chekanov.space.zakupkitoxls;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class FTPWork {

    FTPClient ftp = null;
    String host;
    String user;
    String pass;

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
            e.printStackTrace();
        }
    }
        public InputStream downloadStream (String remoteFilePath){
            InputStream os = null;
            try{
                os = this.ftp.retrieveFileStream(remoteFilePath);
            }
            catch (Exception e){
                e.printStackTrace();
            }
            return os;
       }


    public String [] getNameFiles(String remotePath){
        String [] nameFiles = null;
        try {
            nameFiles = this.ftp.listNames(remotePath);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return nameFiles;
    }

        public FTPFile [] getListFiles(String remotePath){
            FTPFile [] listFiles = null;
            try {
                listFiles = this.ftp.listFiles(remotePath);
            }
            catch (Exception e){
                e.printStackTrace();
            }
            return listFiles;
        }

    public void disconnect() {
        if (this.ftp.isConnected()) {
            try {
                this.ftp.logout();
                this.ftp.disconnect();
            } catch (IOException f) {
                // do nothing as file is already downloaded from FTP server
            }
        }
    }

}