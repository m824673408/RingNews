package com.dark.xiaom.ringnews.domain;

/**
 * Created by com.example.com.example.newclient.activities.activities on 2016/7/12.
 */

import java.io.Serializable;
import java.util.List;

public class JsonDetail implements Serializable {
    public String reason;
    public int error_code;
    public result result;
    public static class result{
        public String stat;
        public List<NewContent> data;
        public String getStat() {
            return stat;
        }
        public void setStat(String stat) {
            this.stat = stat;
        }
        public List<NewContent> getData() {
            return data;
        }
        public void setData(List<NewContent> data) {
            this.data = data;
        }


    }

    public static class NewContent{
        public String title;
        public String date;
        public String category;
        public String author_name;
        public String thumbnail_pic_s;
        public String thumbnail_pic_s02;
        public String thumbnail_pic_s03;
        public String url;
        public String uniquekey;
        public String type;
        public String realtype;
        public String getTitle() {
            return title;
        }
        public void setTitle(String title) {
            this.title = title;
        }
        public String getDate() {
            return date;
        }
        public void setDate(String date) {
            this.date = date;
        }
        public String getAuthor_name() {
            return author_name;
        }
        public void setAuthor_name(String author_name) {
            this.author_name = author_name;
        }
        public String getThumbnail_pic_s() {
            return thumbnail_pic_s;
        }
        public void setThumbnail_pic_s(String thumbnail_pic_s) {
            this.thumbnail_pic_s = thumbnail_pic_s;
        }
        public String getThumbnail_pic_s02() {
            return thumbnail_pic_s02;
        }
        public void setThumbnail_pic_s02(String thumbnail_pic_s02) {
            this.thumbnail_pic_s02 = thumbnail_pic_s02;
        }
        public String getThumbnail_pic_s03() {
            return thumbnail_pic_s03;
        }
        public void setThumbnail_pic_s03(String thumbnail_pic_s03) {
            this.thumbnail_pic_s03 = thumbnail_pic_s03;
        }
        public String getUrl() {
            return url;
        }
        public void setUrl(String url) {
            this.url = url;
        }
        public String getUniquekey() {
            return uniquekey;
        }
        public void setUniquekey(String uniquekey) {
            this.uniquekey = uniquekey;
        }
        public String getType() {
            return type;
        }
        public void setType(String type) {
            this.type = type;
        }
        public String getRealtype() {
            return realtype;
        }
        public void setRealtype(String realtype) {
            this.realtype = realtype;
        }


    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public result getResult() {
        return result;
    }

    public void setResult(result result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "JsonDetail{" +
                "reason='" + reason + '\'' +
                ", error_code=" + error_code +
                ", result=" + result +
                '}';
    }


}

