package com.dark.xiaom.ringnews.domain;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xiaom on 2017/6/17.
 */

public class SplashImageJson implements Serializable {
    public int showapi_res_code;
    public String showapi_res_error;
    public ShowAPIRESBODY showapi_res_body;

    public static class ShowAPIRESBODY {
        public int ret_code;
        public PAGEBEAN pagebean;

        public static class PAGEBEAN {

            public int allPages;
            public List<Content> contentlist;
            public int currentPage;
            public int allNum;
            public int maxResult;

            public static class Content {
                public String typeName;
                public String title;
                public List<PhotoContent> list;
                public int type;
                public String itemId;
                public String ct;

                public static class PhotoContent {
                    public String big;
                    public String small;
                    public String middle;

                }
            }
        }
    }
}