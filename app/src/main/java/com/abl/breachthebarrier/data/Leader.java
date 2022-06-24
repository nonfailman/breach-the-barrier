package com.abl.breachthebarrier.data;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.PropertyName;

public class Leader implements Comparable<Leader> {

    @DocumentId
    private String user;

    @PropertyName("user_score")
    private Integer score;

    @DocumentId
    public String getUser() {
        return user;
    }

    @DocumentId
    public void setUser(String user) {
        this.user = user;
    }

    @PropertyName("user_score")
    public Integer getScore() {
        return score;
    }

    @PropertyName("user_score")
    public void setScore(Integer score) {
        this.score = score;
    }

    @Override
    public int compareTo(Leader leader) {
        return leader.score - this.score;
    }
}
