package com.dark.xiaom.ringnews.domain;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

public class Comment {

    public Comment() {

    }

    public int commentid;
    public String username;
    public String comment;
    public String time;
    public String newsUrl;
    public String portrait;

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public int getCommentid() {
        return commentid;
    }
    public void setCommentid(int commentid) {
        this.commentid = commentid;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getNewsUrl() {
        return newsUrl;
    }
    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    @Override
    public String toString() {
        return "CommentActivity{" +
                "commentid=" + commentid +
                ", username='" + username + '\'' +
                ", comment='" + comment + '\'' +
                ", time='" + time + '\'' +
                ", newsUrl='" + newsUrl + '\'' +
                '}';
    }
}
