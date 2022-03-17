package com.yf.blog.entity;

import lombok.Data;

/**
 * @Description: 照片墙实体类
 */
@Data
public class Picture {

    private Long id;
    private String picturename;
    private String picturetime;
    private String pictureaddress;
    private String picturedescription;

    public Picture() {
    }


}