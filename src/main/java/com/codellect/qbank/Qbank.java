/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codellect.qbank;

import com.codellect.util.ExcelReader;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author Ritesh
 */
public class Qbank {

    public static void main(String[] args) throws IOException {
        String fileName = args.length>0?args[0]:"data.xlsx";
        ExcelReader e = loadExcelData(fileName);
        int level = 0;
        Scanner sc = new Scanner(System.in);
        printWelcomeMsg();
        while (true) {
            if(level>=0){
                int index = (int) Math.abs(Math.random() * e.getQuestionByLevel(level).size());
                System.out.println("");
                QbankBean q = e.getQuestionByLevel(level).get(index);
                System.out.println(q);
                System.out.println("\n1. Continue Level");
                System.out.println("2. Next Level");
                System.out.println("3. Skip next level");
                System.out.println("4. Reset");
                System.out.println("5. Previous level");
                System.out.println("6. Exit");
                System.out.print("Enter selection (1/2/3/4/5/6) :");
            }
            int choice = sc.nextInt();
            if (choice == 6) break;
            level = reviseLevel(choice, level, e.getLevelCount());
        }
    }

    private static ExcelReader loadExcelData(String fileName) {
        File f = new File(fileName);
        ExcelReader e = new ExcelReader(f);
        if(e.getAllQuestions().isEmpty()) System.exit(0);
        return e;
    }

    private static int reviseLevel(int choice, int level, int levelCount) {
        switch(choice){
            case 1: break;
            case 2: {level++;break;}
            case 3: {level++;level++;break;}
            case 4: {level = 0;break;}
            case 5: {level--;break;}
            case 6: break;
            default : {JOptionPane.showMessageDialog(null, "Please enter a valid selection");return -1;}
        }
        if (level >= levelCount) return levelCount - 1;
        if(level < 0) return 0;
        return level;
    }

    private static void printWelcomeMsg() {
        System.out.println("*******************************************");
        System.out.println("*      Welcome to UCA selection Test      *");
        System.out.println("*******************************************");
    }

}
