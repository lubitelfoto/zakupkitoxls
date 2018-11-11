package chekanov.space.zakupkitoxls;

import android.util.Log;
import android.view.View;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


public abstract class XLSWork {

    public static void writeXlsxFile(List<ZakupkiItem> zakupkiItems, File fileDir) throws IOException {
        HSSFWorkbook book = new HSSFWorkbook();
        Sheet sheet = book.createSheet("Закупки");
        long lengthMap = zakupkiItems.size();
        for(int i = 0; i < lengthMap; i++) {
            Row row = sheet.createRow(i);
            Cell numberCell = row.createCell(0);
            ZakupkiItem zakupkiItem = zakupkiItems.get(i);
            numberCell.setCellValue(zakupkiItem.getNumber());
            Cell publishDateCell = row.createCell(1);
            HSSFDataFormat format = book.createDataFormat();
            CellStyle dateStyle = book.createCellStyle();
            dateStyle.setDataFormat(format.getFormat("dd.mm.yyyy"));
            publishDateCell.setCellStyle(dateStyle);
            publishDateCell.setCellValue(zakupkiItem.getPublishDate());
        }
        File file = new File(fileDir.getAbsolutePath() + "zakupki.xls");
        try {
            book.write(new FileOutputStream(file));
            Log.d(MainActivity.TAG, "File create is " + file.exists());
        }
        catch (Exception e){
            Log.e(MainActivity.TAG, "File create exception", e);
        }
    }
}
