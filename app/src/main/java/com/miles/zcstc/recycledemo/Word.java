package com.miles.zcstc.recycledemo;

public class Word {
    private String initial;//此单词的首字母
    private String english;//单词英文
    private String chinese;//单词中文

    public Word(String english, String chinese) {
        this.english = english;
        this.chinese = chinese;
        this.initial = english.substring(0,1).toUpperCase();  //首字母获取
    }

    public String getInitial() {
        return initial;
    }

    public void setInitial(String initial) {
        this.initial = initial;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getChinese() {
        return chinese;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese;
    }
}

