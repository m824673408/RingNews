package com.dark.xiaom.ringnews.domain;

import java.util.List;

/**
 * Created by xiaom on 2017/6/7.
 */

public class WeiXinArticleJson {
    public String status;
    public String msg;
    public WeiXinResult result;

    public static class WeiXinResult {
        public String channel;
        public String channelid;
        public String total;
        public String num;
        public String start;
        public List<WeiXinContent> list;

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public String getChannelid() {
            return channelid;
        }

        public void setChannelid(String channelid) {
            this.channelid = channelid;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getStart() {
            return start;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public List<WeiXinContent> getList() {
            return list;
        }

        public void setList(List<WeiXinContent> list) {
            this.list = list;
        }
    }

    public static class WeiXinContent {
        public String title;
        public String time;
        public String weixinname;
        public String weixinaccount;
        public String weixinsummary;
        public String channelid;
        public String pic;
        public String content;
        public String url;
        public String readnum;
        public String likenum;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getWeixinname() {
            return weixinname;
        }

        public void setWeixinname(String weixinname) {
            this.weixinname = weixinname;
        }

        public String getWeixinaccount() {
            return weixinaccount;
        }

        public void setWeixinaccount(String weixinaccount) {
            this.weixinaccount = weixinaccount;
        }

        public String getWeixinsummary() {
            return weixinsummary;
        }

        public void setWeixinsummary(String weixinsummary) {
            this.weixinsummary = weixinsummary;
        }

        public String getChannelid() {
            return channelid;
        }

        public void setChannelid(String channelid) {
            this.channelid = channelid;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getReadnum() {
            return readnum;
        }

        public void setReadnum(String readnum) {
            this.readnum = readnum;
        }

        public String getLikenum() {
            return likenum;
        }

        public void setLikenum(String likenum) {
            this.likenum = likenum;
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public WeiXinResult getResult() {
        return result;
    }

    public void setResult(WeiXinResult esult) {
        this.result = esult;
    }
}
