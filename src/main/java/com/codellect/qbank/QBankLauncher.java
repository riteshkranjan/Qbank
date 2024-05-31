package com.codellect.qbank;

import com.codellect.util.CommonUtils;
import com.codellect.util.ExcelReader;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QBankLauncher extends JFrame {
    private final JPanel contentPanel;
    private final ExcelReader e = CommonUtils.loadExcelData("data.xlsx");

    private final JTextArea questionTextArea;
    private final JTextArea infoTextArea;

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try{
                    QBankLauncher qBankLauncher = new QBankLauncher();
                    qBankLauncher.setVisible(true);
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
        });

    }

    public QBankLauncher(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100,100,700,700);
        contentPanel = new JPanel();
        contentPanel.setBorder(new EmptyBorder(5,5,5,5));
        contentPanel.setLayout(new BorderLayout(0,0));
        setContentPane(contentPanel);
        setName("UCA Question Bank");
        setTitle("UCA Question Bank");
        contentPanel.setLayout(null);

        questionTextArea = new JTextArea();
        questionTextArea.setEditable(false);

        questionTextArea.setFont(new Font("SansSerif", Font.PLAIN, 16));
        questionTextArea.setBorder(new LineBorder(new Color(0, 0, 0), 2));
        questionTextArea.setBounds(80, 130, 500, 286);
        contentPanel.add(questionTextArea);

        infoTextArea = new JTextArea();
        infoTextArea.setEditable(false);
        infoTextArea.setEnabled(true);

        infoTextArea.setFont(new Font("SansSerif", Font.BOLD, 16));
        infoTextArea.setBorder(new LineBorder(new Color(0, 0, 0), 2));
        infoTextArea.setBounds(80, 96, 500, 30);
        contentPanel.add(infoTextArea);

        int x = 80;
        int space  = 150;
        addGetQuestionButton("Level 0", 0, x);
        x += space;
        addGetQuestionButton("Level 1", 1, x);
        x += space;
        addGetQuestionButton("Level 2", 2, x);
        x += space;
        addGetQuestionButton("Level 3", 3, x);

        JButton close = new JButton("Close");
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        close.setOpaque(true);
        close.setBackground(Color.GRAY);
        close.setBounds(290, 425, 90, 20);
        contentPanel.add(close);
    }

    private void addGetQuestionButton(String text, final int level, int x) {
        JButton button1 = new JButton(text);
        button1.setOpaque(true);
        button1.setBackground(Color.PINK);
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                questionTextArea.setText("");
                infoTextArea.setText("");
                QBankBean q = getQuestionByLevel(level);
                questionTextArea.setBackground(Color.WHITE);
                questionTextArea.setLineWrap(true);
                questionTextArea.setWrapStyleWord(true);
                questionTextArea.setText(buildQuestionTextArea(q));

                infoTextArea.setBackground(Color.WHITE);
                infoTextArea.setLineWrap(true);
                infoTextArea.setWrapStyleWord(true);
                infoTextArea.setText(buildInfoTextArea(q));
            }
        });
        button1.setBounds(x, 23, 90, 20);
        contentPanel.add(button1);
    }

    public QBankBean getQuestionByLevel(int level) {
        int index = (int) Math.abs(Math.random() * e.getQuestionByLevel(level).size());
        return  e.getQuestionByLevel(level).get(index);
    }

    public String buildInfoTextArea(QBankBean q){
        return "Id : " + q.getId() +
                "\t Level: " + q.getLevel() +
                "\t Tag: " + q.getTag();
    }

    public String buildQuestionTextArea(QBankBean q){
        return q.getQuestion();
    }

}
