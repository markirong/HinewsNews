package com.hinews.bean;

import java.util.List;

public class ChannelBean {
    private int status;
    private String navcode;
    private List<ResultBean> result;

    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getNavcode() {
        return navcode;
    }
    public void setNavcode(String navcode) {
        this.navcode = navcode;
    }
    public List<ResultBean> getResult() {
        return result;
    }
    public void setResult(List<ResultBean> result) {
        this.result = result;
    }
    public static class ResultBean {
        private String section_name;
        private List<SectionDataBean> section_data;

        public String getSection_name() {
            return section_name;
        }
        public void setSection_name(String section_name) {
            this.section_name = section_name;
        }
        public List<SectionDataBean> getSection_data() {
            return section_data;
        }
        public void setSection_data(List<SectionDataBean> section_data) {
            this.section_data = section_data;
        }
        public static class SectionDataBean {
            private String channelname;
            private String cid;
            private String channelselected;
            private String order;
            private String section_index;
            private String ismoved;
            private String url;
            private String listtype;
            public String getChannelname() {
                return channelname;
            }
            public void setChannelname(String channelname) {
                this.channelname = channelname;
            }
            public String getCid() {
                return cid;
            }
            public void setCid(String cid) {
                this.cid = cid;
            }
            public String getChannelselected() {
                return channelselected;
            }
            public void setChannelselected(String channelselected) {
                this.channelselected = channelselected;
            }
            public String getOrder() {
                return order;
            }
            public void setOrder(String order) {
                this.order = order;
            }
            public String getSection_index() {
                return section_index;
            }
            public void setSection_index(String section_index) {
                this.section_index = section_index;
            }
            public String getIsmoved() {
                return ismoved;
            }
            public void setIsmoved(String ismoved) {
                this.ismoved = ismoved;
            }
            public String getUrl() {
                return url;
            }
            public void setUrl(String url) {
                this.url = url;
            }
            public String getListtype() {
                return listtype;
            }
            public void setListtype(String listtype) {
                this.listtype = listtype;
            }
        }
    }
}




