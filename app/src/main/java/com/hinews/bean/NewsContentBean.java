package com.hinews.bean;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;
public class NewsContentBean {
    private String page;
    private List<ContentBeans> content;
    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public List<ContentBeans> getContent() {
        return content;
    }

    public void setContent(List<ContentBeans> content) {
        this.content = content;
    }

    public static class ContentBeans implements MultiItemEntity {
        private String title;
        private String newsid;
        private String type;
        private String url;
        private String typeid;
        private String icon;
        private String date;
        private String cid;
        private String listtype;
        private String video_src;
        private int plno;
        private int newsType;
        private List<ItemsBean> items;
        private List<String> img;
        public String getTitle() {
            return title;
        }
        public void setTitle(String title) {
            this.title = title;
        }
        public String getNewsid() {
            return newsid;
        }
        public void setNewsid(String newsid) {
            this.newsid = newsid;
        }
        public String getType() {
            return type;
        }
        public void setType(String type) {
            this.type = type;
        }
        public String getUrl() {
            return url;
        }
        public void setUrl(String url) {
            this.url = url;
        }
        public String getTypeid() {
            return typeid;
        }
        public void setTypeid(String typeid) {
            this.typeid = typeid;
        }
        public String getIcon() {
            return icon;
        }
        public void setIcon(String icon) {
            this.icon = icon;
        }
        public String getDate() {
            return date;
        }
        public void setDate(String date) {
            this.date = date;
        }
        public String getCid() {
            return cid;
        }
        public void setCid(String cid) {
            this.cid = cid;
        }
        public String getListtype() {
            return listtype;
        }
        public void setListtype(String listtype) {
            this.listtype = listtype;
        }
        public String getVideo_src() {
            return video_src;
        }
        public void setVideo_src(String video_src) {
            this.video_src = video_src;
        }
        public int getPlno() {
            return plno;
        }
        public void setPlno(int plno) {
            this.plno = plno;
        }
        public int getNewsType() {
            return newsType;
        }
        public void setNewsType(int newsType) {
            this.newsType = newsType;
        }
        public List<ItemsBean> getItems() {
            return items;
        }
        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }
        public List<String> getImg() {
            return img;
        }
        public void setImg(List<String> img) {
            this.img = img;
        }
        @Override
        public int getItemType() {
            return Integer.parseInt(typeid);
        }

        public static class ItemsBean {
            private String title;
            private String listtype;
            private String newsid;
            private String url;
            private String icon;
            private String date;
            private String type;
            private String video_src;
            private int plno;
            private int newsType;
            private String typeid;
            private List<String> img;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getListtype() {
                return listtype;
            }

            public void setListtype(String listtype) {
                this.listtype = listtype;
            }

            public String getNewsid() {
                return newsid;
            }

            public void setNewsid(String newsid) {
                this.newsid = newsid;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getVideo_src() {
                return video_src;
            }

            public void setVideo_src(String video_src) {
                this.video_src = video_src;
            }

            public int getPlno() {
                return plno;
            }

            public void setPlno(int plno) {
                this.plno = plno;
            }

            public int getNewsType() {
                return newsType;
            }

            public void setNewsType(int newsType) {
                this.newsType = newsType;
            }

            public String getTypeid() {
                return typeid;
            }

            public void setTypeid(String typeid) {
                this.typeid = typeid;
            }

            public List<String> getImg() {
                return img;
            }

            public void setImg(List<String> img) {
                this.img = img;
            }
        }
    }
}