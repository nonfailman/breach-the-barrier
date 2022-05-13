package com.abl.breachthebarrier.data;

import com.google.firebase.firestore.PropertyName;

public class Question {

    @PropertyName("id")
    private int id;

    @PropertyName("question")
    private String question;

    @PropertyName("image")
    private String image;

    @PropertyName("answer1")
    private String answer1;

    @PropertyName("answer2")
    private String answer2;

    @PropertyName("answer3")
    private String answer3;

    @PropertyName("answer4")
    private String answer4;

    public Question(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", answer1='" + answer1 + '\'' +
                ", answer2='" + answer2 + '\'' +
                ", answer3='" + answer3 + '\'' +
                ", answer4='" + answer4 + '\'' +
                ", question='" + question + '\'' +
                '}';
    }
}
