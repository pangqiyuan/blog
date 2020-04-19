package com.sylg.blog.service.documentation.common.constants;


public enum ScoreTime {
    Week(7),Mid(15),Mouth(30);
    private Integer day;

    ScoreTime(int day) {
        this.day = day;
    }

    public Integer getDay() {
        return day;
    }
}
