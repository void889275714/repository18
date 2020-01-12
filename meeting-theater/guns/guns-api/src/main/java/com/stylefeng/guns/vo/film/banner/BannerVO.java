package com.stylefeng.guns.vo.film.banner;

import lombok.Data;

import java.io.Serializable;

@Data
public class BannerVO implements Serializable {
    private static final long serialVersionUID = -5627242466743056753L;

    /*
    Idea默认没有开启serializable,去settings设置在alt+enter开启
    */

    private String bannerId;
    private String bannerAddress;
    private String bannerUrl;

}
