package com.gg.habittrain.data;

/**
 * Created by Administrator on 2017/7/1 0001.
 */

public class HabitData {
    private String habitId;
    private String time;
    private String title;
    private String content;
    private String isComplete;
    public HabitData(String habitId,String time,String title,String content){
        this.habitId=habitId;
        this.time=time;
        this.title=title;
        this.content=content;
    }

    public String getHabitId() {
        return habitId;
    }

    public void setHabitId(String habitId) {
        this.habitId = habitId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(String isComplete) {
        this.isComplete = isComplete;
    }
}
