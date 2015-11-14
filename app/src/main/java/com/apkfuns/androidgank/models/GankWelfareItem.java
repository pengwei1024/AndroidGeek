package com.apkfuns.androidgank.models;

import java.util.List;

/**
 * Created by pengwei08 on 15/11/14.
 */
public class GankWelfareItem {

    /**
     * error : false
     * results : [{"who":"张涵宇","publishedAt":"2015-11-13T04:03:12.613Z","desc":"11.13","type":"福利","url":"http://ww3.sinaimg.cn/large/7a8aed7bgw1exz7lm0ow0j20qo0hrjud.jpg","used":true,"objectId":"56455fb500b0d1dbac0668bd","createdAt":"2015-11-13T03:57:41.206Z","updatedAt":"2015-11-13T04:03:13.180Z"},{"who":"张涵宇","publishedAt":"2015-11-12T13:46:23.466Z","desc":"11.12","type":"福利","url":"http://ww2.sinaimg.cn/large/7a8aed7bjw1exy13si92lj20v218g10h.jpg","used":true,"objectId":"5644071360b294bc60929319","createdAt":"2015-11-12T03:27:15.775Z","updatedAt":"2015-11-12T13:46:25.023Z"},{"who":"张涵宇","publishedAt":"2015-11-11T03:47:41.135Z","desc":"11.11","type":"福利","url":"http://ww4.sinaimg.cn/large/7a8aed7bjw1exwto3cm5xj20nm0kq7a3.jpg","used":true,"objectId":"5642a6e160b2298f08cf2f09","createdAt":"2015-11-11T02:24:33.366Z","updatedAt":"2015-11-11T03:47:41.977Z"},{"who":"张涵宇","publishedAt":"2015-11-10T05:37:51.792Z","desc":"11.10","type":"福利","url":"http://ww1.sinaimg.cn/large/7a8aed7bjw1exvmxmy36wj20ru114gqq.jpg","used":true,"objectId":"56414c5700b01b7839349a38","createdAt":"2015-11-10T01:45:59.392Z","updatedAt":"2015-11-10T05:37:52.814Z"},{"who":"张涵宇","publishedAt":"2015-11-09T04:20:34.123Z","desc":"11.9","type":"福利","url":"http://ww3.sinaimg.cn/large/a3bcec5fjw1exukiyu2zoj20hs0qo0w9.jpg","used":true,"objectId":"564014e000b0023c3aaae702","createdAt":"2015-11-09T03:37:04.087Z","updatedAt":"2015-11-09T04:20:35.558Z"},{"who":"张涵宇","publishedAt":"2015-11-06T04:11:25.973Z","desc":"11.6","type":"福利","url":"http://ww4.sinaimg.cn/large/7a8aed7bjw1exr0p4r0h3j20oy15445o.jpg","used":true,"objectId":"563c084900b0bf378b386d6d","createdAt":"2015-11-06T01:54:17.536Z","updatedAt":"2015-11-06T04:11:26.776Z"},{"who":"张涵宇","publishedAt":"2015-11-05T04:02:52.968Z","desc":"11.5","type":"福利","url":"http://ww4.sinaimg.cn/large/7a8aed7bjw1exp4h479xfj20hs0qoq6t.jpg","used":true,"objectId":"5639df0e00b0d1dbbfe619a4","createdAt":"2015-11-04T10:33:50.564Z","updatedAt":"2015-11-05T04:02:54.977Z"},{"who":"张涵宇","publishedAt":"2015-11-04T04:01:55.601Z","desc":"11.4","type":"福利","url":"http://ww4.sinaimg.cn/large/7a8aed7bgw1exory1k01ej20go0gnjv8.jpg","used":true,"objectId":"5639797460b215d66ddff940","createdAt":"2015-11-04T03:20:20.050Z","updatedAt":"2015-11-04T04:01:56.605Z"},{"who":"张涵宇","publishedAt":"2015-11-03T06:04:59.454Z","desc":"11.3","type":"福利","url":"http://ww2.sinaimg.cn/large/7a8aed7bjw1exng5dd728j20go0m877n.jpg","used":true,"objectId":"5637f5dc60b294e7f60f6e65","createdAt":"2015-11-02T23:46:36.247Z","updatedAt":"2015-11-03T06:05:00.399Z"},{"who":"张涵宇","publishedAt":"2015-11-02T04:16:06.443Z","desc":"11.2","type":"福利","url":"http://ww2.sinaimg.cn/large/7a8aed7bgw1exmhnx76z9j20go0dcabp.jpg","used":true,"objectId":"5636de3560b28045696cb16b","createdAt":"2015-11-02T03:53:25.557Z","updatedAt":"2015-11-02T04:16:13.945Z"}]
     */

    private boolean error;
    /**
     * who : 张涵宇
     * publishedAt : 2015-11-13T04:03:12.613Z
     * desc : 11.13
     * type : 福利
     * url : http://ww3.sinaimg.cn/large/7a8aed7bgw1exz7lm0ow0j20qo0hrjud.jpg
     * used : true
     * objectId : 56455fb500b0d1dbac0668bd
     * createdAt : 2015-11-13T03:57:41.206Z
     * updatedAt : 2015-11-13T04:03:13.180Z
     */

    private List<ResultsEntity> results;

    public void setError(boolean error) {
        this.error = error;
    }

    public void setResults(List<ResultsEntity> results) {
        this.results = results;
    }

    public boolean isError() {
        return error;
    }

    public List<ResultsEntity> getResults() {
        return results;
    }

    public static class ResultsEntity {
        private String who;
        private String publishedAt;
        private String desc;
        private String type;
        private String url;
        private boolean used;
        private String objectId;
        private String createdAt;
        private String updatedAt;

        public void setWho(String who) {
            this.who = who;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setUsed(boolean used) {
            this.used = used;
        }

        public void setObjectId(String objectId) {
            this.objectId = objectId;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getWho() {
            return who;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public String getDesc() {
            return desc;
        }

        public String getType() {
            return type;
        }

        public String getUrl() {
            return url;
        }

        public boolean isUsed() {
            return used;
        }

        public String getObjectId() {
            return objectId;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }
    }
}
