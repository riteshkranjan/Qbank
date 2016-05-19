/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codellect.util;

import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Ritesh
 */
public class ExcelReaderTest {
    private static ExcelReader e;
    public ExcelReaderTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        File f = new File(".\\src\\test\\resource\\test_data.xlsx");
        e = new ExcelReader(f);
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getLevelCount method, of class ExcelReader.
     */
    @Test
    public void testGetLevelCount() {
        System.out.println("getLevelCount");
        int result = e.getLevelCount();
        assertEquals(4, result);
    }

    /**
     * Test of getQuestionByLevel method, of class ExcelReader.
     */
    @Test
    public void testGetQuestionByLevel() {
        System.out.println("getQuestionByLevel");
        assertEquals(15, e.getQuestionByLevel(0).size());
        assertEquals(1, e.getQuestionByLevel(0).get(0).getId());
        assertEquals(0, e.getQuestionByLevel(0).get(0).getLevel());
        assertEquals("C/C++", e.getQuestionByLevel(0).get(0).getTag());
        assertEquals("Explain Pointers in C", e.getQuestionByLevel(0).get(0).getQuestion());
        
        assertEquals(11, e.getQuestionByLevel(1).size());
        assertEquals(6, e.getQuestionByLevel(2).size());
        assertEquals(4, e.getQuestionByLevel(3).size());
    }

    /**
     * Test of getAllQuestions method, of class ExcelReader.
     */
    @Test
    public void testGetAllQuestions() {
        System.out.println("getAllQuestions");
        assertEquals(4, e.getAllQuestions().size());
        assertEquals(15, e.getAllQuestions().get(0).size());
        assertEquals(11, e.getAllQuestions().get(1).size());
        assertEquals(6, e.getAllQuestions().get(2).size());
        assertEquals(4, e.getAllQuestions().get(3).size());
    }
}
