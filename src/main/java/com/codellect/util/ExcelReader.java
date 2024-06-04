/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codellect.util;

import com.codellect.qbank.QBankBean;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ritesh
 */
public class ExcelReader {

    private final File excel;
    List<QBankBean> questions = new ArrayList<>();

    public ExcelReader(File excel) {
        this.excel = excel;
        read();
    }

    private void read() {
        try (FileInputStream inputStream = new FileInputStream(excel); Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet firstSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = firstSheet.iterator();
            Row nextRow = iterator.next();
            System.out.println("Row " + nextRow.getRowNum() + " skipped");
            while (iterator.hasNext()) {
                nextRow = iterator.next();
                short count = nextRow.getLastCellNum();
                if (count >= 4 & notEmpty(nextRow.getCell(ColumnType.STATEMENT.value()))) {
                    QBankBean qbankBean = new QBankBean();
                    qbankBean.setId((int) nextRow.getCell(ColumnType.ID.value()).getNumericCellValue());
                    qbankBean.setLevel((int) nextRow.getCell(ColumnType.LEVEL.value()).getNumericCellValue());
                    qbankBean.setTag(nextRow.getCell(ColumnType.TAG.value()).getStringCellValue());
                    qbankBean.setQuestion(nextRow.getCell(ColumnType.STATEMENT.value()).getStringCellValue());
                    if (count > 4) {
                        qbankBean.setHint(nextRow.getCell(ColumnType.HINT.value()).getStringCellValue());
                    }
                    push(qbankBean);
                }
            }
        } catch (IOException ex) {
            System.out.println("\033[0;1m" + ex.getMessage());
            System.out.println("Please place the data.xlsx file here or provide path of data file as in command line argument" + "\033[0;0m");
        }
    }

    private void push(QBankBean qbankBean) {
        if (qbankBean.getQuestion() == null) {
            return;
        }
        questions.add(qbankBean);
    }

    public int getLevelCount() {
        return questions.stream()
                .collect(Collectors.groupingBy(QBankBean::getLevel))
                .size();
    }

    public List<String> getAllTags() {
        return new ArrayList<>(questions.stream()
                .collect(Collectors.groupingBy(QBankBean::getTag))
                .keySet());
    }

    public List<QBankBean> getQuestionByLevel(int level) {
        return questions.stream()
                .filter(x -> x.getLevel() == level)
                .collect(Collectors.toList());
    }

    public List<QBankBean> getQuestionByLevelAndTags(int level, String tag) {
        return getQuestionByLevel(level).stream()
                .filter(x -> x.getTag().equalsIgnoreCase(tag))
                .collect(Collectors.toList());
    }

    public List<QBankBean> getAllQuestions() {
        return questions;
    }

    private boolean notEmpty(Cell cell) {
        return cell != null && !cell.getStringCellValue().isEmpty();
    }

    private enum ColumnType {

        ID(0),
        LEVEL(1),
        TAG(2),
        STATEMENT(3),

        HINT(4);
        private final int key;

        ColumnType(int key) {
            this.key = key;
        }

        public int value() {
            return this.key;
        }

    }
}
