package vip.zhonghui.edu.ui.fragment03.detail;

public class ImageBean {
    int imageResId;
    String info;

    public ImageBean(int imageResId, String info) {
        this.imageResId = imageResId;
        this.info = info;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
