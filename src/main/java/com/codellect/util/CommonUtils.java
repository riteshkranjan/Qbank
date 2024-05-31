/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codellect.util;

import java.io.File;
import javax.swing.JOptionPane;

/**
 *
 * @author Ritesh
 */
public class CommonUtils {
    public static int parseUserSelection(String ch) {
        try {
            return Integer.parseInt(ch);
        } catch (NumberFormatException ex) {
            return -1;
        }
    }

    public static ExcelReader loadExcelData(String fileName) {
        File f = new File(fileName);
        return new ExcelReader(f);
    }

    public static int reviseLevel(int choice, int level, int levelCount) {
        if(choice < 0) {
            JOptionPane.showMessageDialog(null, "Please enters a valid selection");
            System.out.print("Enter selection (1/2/3/4/5/6) : ");
            return -1;
        }
        switch(choice){
            case 1:
            case 6:
                break;
            case 2: {level++;break;}
            case 3: {level++;level++;break;}
            case 4: {level = 0;break;}
            case 5: {level--;break;}
            default : {
                    JOptionPane.showMessageDialog(null, choice+" not a valid selection");
                    System.out.print("Enter selection (1/2/3/4/5/6) : ");
                    return -1;
                }
            }
        if (level >= levelCount) return levelCount - 1;
        return Math.max(level, 0);
    }
}
