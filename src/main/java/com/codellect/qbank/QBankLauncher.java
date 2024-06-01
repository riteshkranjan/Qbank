package com.codellect.qbank;

import com.codellect.util.CommonUtils;
import com.codellect.util.ExcelReader;
import static com.codellect.util.AppConstants.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;

public class QBankLauncher extends JFrame {

    private final JPanel contentPanel;
    private final ExcelReader e = CommonUtils.loadExcelData("data.xlsx");
    private final JTextArea questionTextArea;
    private final JTextPane infoTextArea;

    private final ButtonGroup buttonGroup = new ButtonGroup();

    private final ButtonGroup levelButtonGroup = new ButtonGroup();

    public QBankLauncher() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(APP_X, APP_Y, APP_WIDTH, APP_HEIGHT);
        try {
            ImageIcon img = new ImageIcon(new URL("https://www.developmentaid.org/files/organizationLogos/chitkara-university-47016.jpg"));
            setIconImage(img.getImage());
        } catch (MalformedURLException ex) {
            System.out.println("Icon image not found");
        }

        contentPanel = new JPanel();
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPanel);
        setName(APP_NAME);
        setTitle(APP_NAME);
        contentPanel.setLayout(null);

        infoTextArea = new JTextPane();
        questionTextArea = new JTextArea();

        questionTextArea.setEditable(false);
        questionTextArea.setFont(TEXT_FONT);
        questionTextArea.setBorder(new LineBorder(new Color(0, 0, 0), 2));
        questionTextArea.setBounds(80, 96, 500, 286);
        contentPanel.add(questionTextArea);



        if (e.getAllQuestions().isEmpty()) {
            questionTextArea.setText("No questions loaded. Please place correct excel sheet file at root.");

        } else {

            infoTextArea.setEditable(false);
            infoTextArea.setOpaque(false);

            infoTextArea.setFont(INBFO_TEXT_FONT);
            infoTextArea.setBounds(80, 400, 500, 30);
            contentPanel.add(infoTextArea);

            buildLevelButtons();
            buildTagRadioButtons();
        }
        addCloseButton();
    }

    private void addCloseButton() {
        JButton close = new JButton("Close");
        close.addActionListener(e -> System.exit(0));
        close.setOpaque(true);
        close.setBackground(Color.GRAY);
        close.setBounds(290, 450, 90, 20);
        contentPanel.add(close);
    }

    private void buildTagRadioButtons() {
        int x;
        int y;
        x = 80;
        y = 72;
        List<String> tags = e.getAllTags();
        addRadioButton(tags, x, y);
    }

    private void buildLevelButtons() {
        int x = 80;
        int y = 23;
        int space = 150;
        for (int i = 0; i < e.getLevelCount(); i++) {
            if (x + 90 > 700) {
                x = 80;
                y += 23;
            }
            addGetQuestionButton(i, x, y);
            x += space;
        }
    }


    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            try {
                QBankLauncher qBankLauncher = new QBankLauncher();
                qBankLauncher.setVisible(true);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });

    }

    private void addRadioButton(List<String> tags, int x, int y) {
        for (String tag : tags) {
            JRadioButton jRadioButton = new JRadioButton(tag);
            jRadioButton.setText(tag);
            jRadioButton.setBounds(x, y, 100, 20);
            x += 100;
            if (x > 700) {
                x = 80;
                y += 23;
            }
            buttonGroup.add(jRadioButton);
            contentPanel.add(jRadioButton);
        }
        JRadioButton jRadioButton = new JRadioButton(ALL);
        jRadioButton.setText(ALL);
        jRadioButton.setBounds(x, y, 100, 20);
        jRadioButton.setSelected(true);
        buttonGroup.add(jRadioButton);
        contentPanel.add(jRadioButton);
    }


    private void addGetQuestionButton(final int level, int x, int y) {
        JToggleButton toggleButton = new JToggleButton("Level " + level);
        toggleButton.setOpaque(true);
        toggleButton.setBackground(Color.PINK);
        toggleButton.addActionListener(e -> {
            questionTextArea.setText("");
            infoTextArea.setText("");
            String tag = getSelectedTag();
            java.util.List<QBankBean> questions = getQuestionsByLevelAndTag(level, tag);
            QBankBean q = questions.isEmpty() ? null : questions.get((int) Math.abs(Math.random() * questions.size()));
            questionTextArea.setBackground(Color.WHITE);
            questionTextArea.setLineWrap(true);
            questionTextArea.setWrapStyleWord(true);
            questionTextArea.setText(buildQuestionTextArea(q));
            infoTextArea.setText(buildInfoTextArea(questions.size()));
        });
        toggleButton.setBounds(x, y, 90, 20);
        contentPanel.add(toggleButton);
        levelButtonGroup.add(toggleButton);
    }

    private String getSelectedTag() {
        Enumeration<AbstractButton> radioButtons = buttonGroup.getElements();
        while (radioButtons.hasMoreElements()) {
            JRadioButton button = (JRadioButton) radioButtons.nextElement();
            if (button.isSelected()) {
                return button.getText();
            }
        }
        return ALL;
    }

    public java.util.List<QBankBean> getQuestionsByLevelAndTag(int level, String tag) {
        List<QBankBean> response;
        if (tag.equalsIgnoreCase(ALL)) {
            response = e.getQuestionByLevel(level);
        } else {
            response = e.getQuestionByLevelAndTags(level, tag);
        }
        return response;
    }

    public String buildInfoTextArea(int size) {
        return "\tTotal " + size + " question(s) found";
    }

    public String buildQuestionTextArea(QBankBean q) {
        return q == null ? "No Questions Found!! Please try with different tag/level" : q.getQuestion() + "\n\n\nTag : " + q.getTag();
    }

}
