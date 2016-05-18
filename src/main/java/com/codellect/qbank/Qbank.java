/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codellect.qbank;

import com.codellect.util.ExcelReader;
import java.io.File;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Ritesh
 */
public class Qbank {

    public static void main(String[] args) {
        File f = new File("data.xlsx");
        ExcelReader e = new ExcelReader(f);
        int level = 0;
        Scanner sc = new Scanner(System.in);
        while (true) {
            QbankBean q = getRandom(e.getQuestionByLevel(level));
            System.out.println(q);
            System.out.println("1. Continue Level");
            System.out.println("2. Next Level");
            System.out.println("3. Skip next level");
            System.out.println("4. Reset");
            System.out.println("5. Previous level");
            System.out.println("6. Exit");
            System.out.print("Enter selection (1/2/3/4/5/6) :");
            int choice = sc.nextInt();
            System.out.println("------------------------------------------------");
            level = reviseLevel(choice, level, e.getLevelCount());
            if (choice == 6) {
                break;
            }
        }
    }

    private static int reviseLevel(int choice, int level, int levelCount) {
        switch(choice){
            case 2: {level++;break;}
            case 3: {level++;level++;break;}
            case 4: {level = 0;break;}
            case 5: {level--;break;}
        }
        if (level >= levelCount) return levelCount - 1;
        if(level < 0) return 0;
        return level;
    }

    private static QbankBean getRandom(List<QbankBean> questions) {
        int index = (int) Math.abs(Math.random() * questions.size());
        return questions.get(index);
    }

}
