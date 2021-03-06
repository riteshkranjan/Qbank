/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codellect.qbank;

/**
 *
 * @author Ritesh
 */
public class QbankBean {

    private int id;
    private int level;
    private String tag;
    private String question;

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("-----------------------------------------\n");
        sb.append("| ").append("id=").append(id).append(", level=").append(level).append(", tag=").append(tag).append("\n");
        sb.append("-----------------------------------------\n");
        sb.append("\033[0;1m Statement : ").append(question).append("\033[0;0m");
        return sb.toString();
    }
}
