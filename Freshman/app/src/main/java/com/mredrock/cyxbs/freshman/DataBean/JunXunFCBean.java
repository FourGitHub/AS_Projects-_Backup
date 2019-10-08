package com.mredrock.cyxbs.freshman.DataBean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by FengHaHa on2018/8/17 0017 3:25
 */
public class JunXunFCBean {

    @SerializedName("video")
    private List<VideoBean> videoList;
    @SerializedName("picture")
    private List<PictureBean> pictureList;

    public List<VideoBean> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<VideoBean> videoList) {
        this.videoList = videoList;
    }

    public List<PictureBean> getPictureList() {
        return pictureList;
    }

    public void setPictureList(List<PictureBean> pictureList) {
        this.pictureList = pictureList;
    }

    public static class VideoBean {
        /**
         * name : 2016重庆邮电大学军训视频2
         * url : https://v.qq.com/x/page/r07539i9p1d.html?
         * video_pic : {"name":"UUID131","url":"/picture/38f98e9a9e8d498ea522252f67382236.png"}
         */

        private String name;
        @SerializedName("url")
        private String videoUrl;
        @SerializedName("video_pic")
        private VideoPicBean videoPicBean;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }

        public VideoPicBean getVideoPicBean() {
            return videoPicBean;
        }

        public void setVideoPicBean(VideoPicBean videoPicBean) {
            this.videoPicBean = videoPicBean;
        }

        public static class VideoPicBean {
            /**
             * name : UUID131
             * url : /picture/38f98e9a9e8d498ea522252f67382236.png
             */

            @SerializedName("name")
            private String videoPicName;
            private String url;

            public String getVideoPicName() {
                return videoPicName;
            }

            public void setVideoPicName(String videoPicName) {
                this.videoPicName = videoPicName;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }

    public static class PictureBean {
        /**
         * name : 汇报表演
         * url : /picture/5243d251d89247da847257cffe14f818.jpg
         */

        private String name;
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
