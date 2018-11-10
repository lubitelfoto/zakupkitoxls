package chekanov.space.zakupkitoxls;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


public abstract class XLSWork {
    private static final String FILE_NAME = "zakupki.xls";

    public static void writeXlsxFile(List<ZakupkiItem> zakupkiItems) throws IOException {
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
        book.write(new FileOutputStream(FILE_NAME));
    }
}
