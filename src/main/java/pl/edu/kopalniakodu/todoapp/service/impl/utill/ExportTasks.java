package pl.edu.kopalniakodu.todoapp.service.impl.utill;


import org.apache.commons.math3.util.Pair;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.kopalniakodu.todoapp.domain.Task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ExportTasks {

    private static final Logger logger = LoggerFactory.getLogger(ExportTasks.class);

    private Workbook wb;
    private List<Sheet> sheetList;

    public ExportTasks() {
        this.wb = new HSSFWorkbook();

    }

    public Pair<String, Workbook> exportFile(List<Task> taskList) {
        generateExcelWorkBook(taskList);
        Pair<String, Workbook> pair = new Pair<>(generateFileName(), wb);
        return pair;
    }


    private void generateExcelWorkBook(List<Task> taskList) {
        generateSheets();
        generateTableHead();
        generateBody(taskList);
    }

    private void generateTableHead() {

        List<String> tableHead = Arrays.asList("Title", "Description", "Task weight", "Creation date", "Last modified date");

        CellStyle tableHeadCellStyle = wb.createCellStyle();

        //align
        tableHeadCellStyle.setAlignment(HorizontalAlignment.CENTER);
        tableHeadCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        // colors
        tableHeadCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
        tableHeadCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);


        //border style
        tableHeadCellStyle.setBorderBottom(BorderStyle.MEDIUM);
        tableHeadCellStyle.setBorderTop(BorderStyle.MEDIUM);
        tableHeadCellStyle.setBorderLeft(BorderStyle.MEDIUM);
        tableHeadCellStyle.setBorderRight(BorderStyle.MEDIUM);

        //text-wraping
        tableHeadCellStyle.setWrapText(true);


        for (Sheet sheet : this.sheetList) {
            Row row = sheet.createRow(0);
            row.setHeightInPoints(50);
            for (int i = 0; i < tableHead.size(); i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(tableHead.get(i));
                cell.setCellStyle(tableHeadCellStyle);
                //sheet.autoSizeColumn(i);
                if (tableHead.get(i).equalsIgnoreCase("Description")) {
                    sheet.setColumnWidth(i, 15000);
                } else {
                    sheet.setColumnWidth(i, 5000);
                }
            }
        }
    }

    private void generateBody(List<Task> taskList) {

        Sheet activeTaskSheet = wb.getSheet("Active tasks");
        Sheet historyTaskSheet = wb.getSheet("Tasks history");

        CellStyle tableBodyCellStyle = wb.createCellStyle();

        //align
        tableBodyCellStyle.setAlignment(HorizontalAlignment.CENTER);
        tableBodyCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);


        //border style
        tableBodyCellStyle.setBorderBottom(BorderStyle.THIN);
        tableBodyCellStyle.setBorderTop(BorderStyle.THIN);
        tableBodyCellStyle.setBorderLeft(BorderStyle.THIN);
        tableBodyCellStyle.setBorderRight(BorderStyle.THIN);

        //text-wraping
        tableBodyCellStyle.setWrapText(true);

        int activeTaskRowCounter = 1;
        int historyTaskRowCounter = 1;

        String[] propertiesToSave = {"Title", "Description", "TaskWeight", "CreationDate", "LastModifiedDate"};

        for (Task task : taskList) {
            if (task.getActive()) {
                Row row = activeTaskSheet.createRow(activeTaskRowCounter);
                row.setHeightInPoints(50);

                for (int i = 0; i < propertiesToSave.length; i++) {
                    Cell cell = row.createCell(i);
                    cell.setCellStyle(tableBodyCellStyle);

                    switch (propertiesToSave[i]) {
                        case "Title":
                            cell.setCellValue(task.getTitle());
                            break;
                        case "Description":
                            cell.setCellValue(task.getDescription());
                            break;
                        case "TaskWeight":
                            cell.setCellValue(task.getTaskWeight().name());
                            break;
                        case "CreationDate":
                            cell.setCellValue(task.getCreationDate().toString());
                            break;
                        case "LastModifiedDate":
                            cell.setCellValue(task.getLastModifiedDate().toString());
                            break;
                        default:
                            cell.setCellValue("");
                            break;
                    }
                }
                activeTaskRowCounter = activeTaskRowCounter + 1;
            } else {
                Row row = historyTaskSheet.createRow(historyTaskRowCounter);
                row.setHeightInPoints(50);

                for (int i = 0; i < propertiesToSave.length; i++) {
                    Cell cell = row.createCell(i);
                    cell.setCellStyle(tableBodyCellStyle);

                    switch (propertiesToSave[i]) {
                        case "Title":
                            cell.setCellValue(task.getTitle());
                            break;
                        case "Description":
                            cell.setCellValue(task.getDescription());
                            break;
                        case "TaskWeight":
                            cell.setCellValue(task.getTaskWeight().name());
                            break;
                        case "CreationDate":
                            cell.setCellValue(task.getCreationDate().toString());
                            break;
                        case "LastModifiedDate":
                            cell.setCellValue(task.getLastModifiedDate().toString());
                            break;
                        default:
                            cell.setCellValue("");
                            break;
                    }
                }
                historyTaskRowCounter = historyTaskRowCounter + 1;
            }
        }
    }


    private String generateFileName() {
        String fileName = LocalDate.now().toString() + "_" + LocalDateTime.now().getHour() + "_" + LocalDateTime.now().getMinute() + ".xls";
        return fileName;
    }

    private void generateSheets() {
        Sheet activeTaskSheet = wb.createSheet("Active tasks");
        Sheet historyTaskSheet = wb.createSheet("Tasks history");
        activeTaskSheet.autoSizeColumn(15);

        List<Sheet> sheetList = new ArrayList<>();
        sheetList.add(activeTaskSheet);
        sheetList.add(historyTaskSheet);

        this.sheetList = sheetList;
    }


}
