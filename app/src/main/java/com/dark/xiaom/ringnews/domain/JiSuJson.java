package com.dark.xiaom.ringnews.domain;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xiaom on 2017/5/23.
 */

public class JiSuJson implements Serializable {
    public String status;
    public String msg;
    public Result result;
    public static class Result{
        public String channel;
        public String num;
        public List<JiSuJson.NewContent> list;

        public String getChannel() {
            return channel;
        }

        public String getNum() {
            return num;
        }

        public List<NewContent> getData() {
            return list;
        }
    }

    public static class NewContent{
        public String title;
        public String time;
        public String src;
        public String category;
        public String pic;
        public String content;
        public String url;
        public String weburl;

        public String getTitle() {
            return title;
        }

        public String getTime() {
            return time;
        }

        public String getSrc() {
            return src;
        }

        public String getCategory() {
            return category;
        }

        public String getPic() {
            return pic;
        }

        public String getContent() {
            return content;
        }

        public String getUrl() {
            return url;
        }

        public String getWeburl() {
            return weburl;
        }
    }

    public String getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public Result getResult() {
        return result;
    }
}
