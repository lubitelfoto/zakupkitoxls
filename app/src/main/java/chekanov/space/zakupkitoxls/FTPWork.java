package chekanov.space.zakupkitoxls;


import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class FTPWork {

    FTPClient ftp = null;

    public FTPWork(String host, String user, String pass) throws Exception{

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

    //заменить второй аргумент

    public void downloadFile(String remoteFilePath, File file) {


        try (FileOutputStream fos = new FileOutputStream(file)) {
            if(this.ftp.retrieveFile(remoteFilePath, fos)) {
                Log.d(MainActivity.TAG, "Download is OK");
            }
            else {
                Log.d(MainActivity.TAG, "Download is Fail");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        /*
        try {
            InputStream stream = this.ftp.retrieveFileStream(remoteFilePath);
           BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
            String line;
            while( (line = reader.readLine()) != null ){
                xmlBuf.add( line );
            }

            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

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

