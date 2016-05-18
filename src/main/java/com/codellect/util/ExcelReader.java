/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codellect.util;

import com.codellect.qbank.QbankBean;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Ritesh
 */
public class ExcelReader {

    private final Logger log = Logger.getLogger(ExcelReader.class);
    private final File excel;
    List<List<QbankBean>> questions = new ArrayList<>();

    public ExcelReader(File excel) {
        this.excel = excel;
        read();
    }

    private void read() {
        try (FileInputStream inputStream = new FileInputStream(excel); Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet firstSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = firstSheet.iterator();
            Row nextRow = iterator.next();
            while (iterator.hasNext()) {
                nextRow = iterator.next();
                short count = nextRow.getLastCellNum();
                if (count == 4) {
                    QbankBean qbankBean = new QbankBean();
                    qbankBean.setId((int) nextRow.getCell(0).getNumericCellValue());
                    qbankBean.setLevel((int) nextRow.getCell(1).getNumericCellValue());
                    qbankBean.setTag(nextRow.getCell(2).getStringCellValue());
                    qbankBean.setQuestion(nextRow.getCell(3).getStringCellValue());
                    push(qbankBean);
                }
            }
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
    }

    private void push(QbankBean qbankBean) {
        if (qbankBean.getQuestion() == null) {
            return;
        }
        int level = qbankBean.getLevel();
        while (getLevelCount() <= level) {
            List<QbankBean> q = new ArrayList<>();
            questions.add(q);
        }
        questions.get(level).add(qbankBean);
    }

    public int getLevelCount() {
        return questions.size();
    }

    public List<QbankBean> getQuestionByLevel(int level) {
        return questions.get(level);
    }

    public List<List<QbankBean>> getAllQuestions() {
        return questions;
    }
}
