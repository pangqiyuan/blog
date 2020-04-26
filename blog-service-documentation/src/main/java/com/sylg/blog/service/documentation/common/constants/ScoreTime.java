package com.sylg.blog.service.documentation.common.constants;

/**
 *
 * 时间枚举
 * 创建时间	2020年4月16日
 *
 * @author 忆地球往事
 */
public enum ScoreTime {
    //week代表七天，mid代表15天，mouth代表30天
    Week(7),Mid(15),Mouth(30);

    private Integer day;

    ScoreTime(int day) {
        this.day = day;
    }

    public Integer getDay() {
        return day;
    }
}
