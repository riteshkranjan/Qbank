/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codellect.qbank;

/**
 * @author Ritesh
 */
public class QBankBean {

    private int id;
    private int level;
    private String tag;
    private String question;

    private String hint;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    @Override
    public String toString() {
        return "-----------------------------------------\n" + "| " + "id=" + id + ", level=" + level + ", tag=" + tag + "\n" +
                "-----------------------------------------\n" +
                "\033[0;1m Statement : " + question + "\033[0;0m";
    }
}
