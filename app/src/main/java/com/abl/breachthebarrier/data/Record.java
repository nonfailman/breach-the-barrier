package com.abl.breachthebarrier.data;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.PropertyName;

public class Record {

    @DocumentId
    private String userName;

    @PropertyName("user_score")
    private Integer score;

    @PropertyName("words_learned")
    private Integer wordsLearned;

    @PropertyName("courses_complete")
    private Integer coursesComplete;

    public Record(){

    }

    @DocumentId
    public String getUserName() {
        return userName;
    }

    @DocumentId
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @PropertyName("user_score")
    public Integer getScore() {
        return score;
    }

    @PropertyName("user_score")
    public void setScore(Integer score) {
        this.score = score;
    }

    @PropertyName("words_learned")
    public Integer getWordsLearned() {
        return wordsLearned;
    }

    @PropertyName("words_learned")
    public void setWordsLearned(Integer wordsLearned) {
        this.wordsLearned = wordsLearned;
    }

    @PropertyName("courses_complete")
    public Integer getCoursesComplete() {
        return coursesComplete;
    }

    @PropertyName("courses_complete")
    public void setCoursesComplete(Integer coursesComplete) {
        this.coursesComplete = coursesComplete;
    }

    @Override
    public String toString() {
        return "Record{" +
                "userName='" + userName + '\'' +
                ", coursesComplete=" + coursesComplete +
                ", wordsLearned=" + wordsLearned +
                ", score=" + score +
                '}';
    }
}
