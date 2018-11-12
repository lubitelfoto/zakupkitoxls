package chekanov.space.zakupkitoxls;

import android.util.Log;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.utils.IOUtils;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static chekanov.space.zakupkitoxls.MainActivity.TAG;

public class UnzipThread extends Thread {

    private InputStream zis;
    private List<ZakupkiItem> zakupkiItems;

    public UnzipThread(InputStream zis, List<ZakupkiItem> zakupkiItems){
        this.zis = zis;
        this.zakupkiItems = zakupkiItems;
    }

    @Override
    public void run() {
        BufferedInputStream zin = new BufferedInputStream(zis);
        try (ArchiveInputStream ain = new ArchiveStreamFactory().createArchiveInputStream(zin)) {
            ArchiveEntry entry = null;
            while ((entry = ain.getNextEntry()) != null) {
                String entryName = entry.getName();
                //Log.d(TAG, String.valueOf(entryName));
                Pattern pat = Pattern.compile("xml", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pat.matcher(entryName);
                boolean isXML = matcher.find();
                //Log.d(TAG, String.valueOf(isXML));
                if (isXML) {
                    byte[] buf = new byte[(int) entry.getSize()];
                    int readed = IOUtils.readFully(ain, buf);
                    if (readed != buf.length) {
                        throw new RuntimeException("Read bytes count and entry size differ");
                    }
                    XMLWorkParser parser = new XMLWorkParser();
                    InputStream pis = new ByteArrayInputStream(buf);
                    ZakupkiItem zakupkiItem = parser.parse(pis);
                    synchronized (zakupkiItems){
                        zakupkiItems.add(zakupkiItem);
                    }
                }
            }
            zin.close();
            zis.close();
        } catch (Exception e) {
            //Вписать обработку ошибки
            Log.e(TAG, "Archive Error", e);
        }
    }
}
