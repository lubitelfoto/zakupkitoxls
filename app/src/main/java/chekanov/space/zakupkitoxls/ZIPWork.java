package chekanov.space.zakupkitoxls;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZIPWork {

    private File file;
    private FileOutputStream unzipin;

    //обязательно сделать close для unzipin

    ZIPWork(File file){
        this.file = file;
    }
    public void unZip(){

        try(ZipInputStream zin = new ZipInputStream(new FileInputStream(file)))
        {

            ZipEntry entry;
            String name;
            long size;
            while ((entry = zin.getNextEntry())!= null){
                name = entry.getName();
                size = entry.getSize();
                Log.d(MainActivity.TAG, "Name " + name + " size " + size );
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    unzipin.write(c);
                }
                zin.closeEntry();

            }
        }
    catch(Exception e) {
        e.printStackTrace();
        }
    }
}
