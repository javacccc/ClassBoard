package com.bjw.bean;

/**
 * 创建人：wxdn
 * 创建时间：2018/3/12
 * 功能描述：
 */

public class ImageBean {
    int LabID;
    int image1;
    int image2;
    int image3;
    int image4;

    public ImageBean() {
    }

    public ImageBean(int labID, int image1, int image2, int image3, int image4) {
        LabID = labID;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.image4 = image4;
    }

    public int getLabID() {
        return LabID;
    }

    public void setLabID(int labID) {
        LabID = labID;
    }

    public int getImage1() {
        return image1;
    }

    public void setImage1(int image1) {
        this.image1 = image1;
    }

    public int getImage2() {
        return image2;
    }

    public void setImage2(int image2) {
        this.image2 = image2;
    }

    public int getImage3() {
        return image3;
    }

    public void setImage3(int image3) {
        this.image3 = image3;
    }

    public int getImage4() {
        return image4;
    }

    public void setImage4(int image4) {
        this.image4 = image4;
    }
}
