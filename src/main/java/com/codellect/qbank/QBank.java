/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codellect.qbank;

import com.codellect.util.CommonUtils;
import com.codellect.util.ExcelReader;

import java.util.List;
import java.util.Scanner;

/**
 * @author Ritesh
 */
public class QBank {

    public static void main(String[] args) {
        String fileName = args.length > 0 ? args[0] : "data.xlsx";
        ExcelReader e = CommonUtils.loadExcelData(fileName);
        Scanner sc = new Scanner(System.in);
        if (e.getAllQuestions().isEmpty()) {
            System.out.println("Press any key followed by ENTER to exit...");
            sc.next();
        } else {
            int level = 0;

            printWelcomeMsg();
            while (true) {
                if (level >= 0) {
                    List<QBankBean> response = e.getQuestionByLevel(level);
                    int index = (int) Math.abs(Math.random() * response.size());
                    System.out.println();
                    QBankBean q = response.get(index);
                    System.out.println(q);
                    System.out.println("\n1. Continue Level");
                    System.out.println("2. Next Level");
                    System.out.println("3. Skip next level");
                    System.out.println("4. Reset");
                    System.out.println("5. Previous level");
                    System.out.println("6. Exit");
                    System.out.print("Enter selection (1/2/3/4/5/6) : ");
                }
                String ch = sc.next();
                int choice = CommonUtils.parseUserSelection(ch);
                if (choice == 6) break;
                level = CommonUtils.reviseLevel(choice, level, e.getLevelCount());
            }
        }
    }

    private static void printWelcomeMsg() {
        System.out.println("*******************************************");
        System.out.println("*      Welcome to UCA selection Test      *");
        System.out.println("*******************************************");
    }

}
