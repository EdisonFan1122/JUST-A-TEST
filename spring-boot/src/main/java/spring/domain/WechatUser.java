package spring.domain;

import javax.persistence.*;
import java.io.Serializable;
/**
 * Created by Edison on 2018/5/26.
 * 微信用户信息实体类
 */
@Entity
@Table(name="WECHAT_USER")
public class WechatUser implements Serializable {

    private static final long serialVersionUID = -2420979951576787924L;

    //微信ID

    @Id
    @Column(name = "C_WECHAT_ID")
    private String weChatId;

    //人文景观
    @Column(name = "C_SCENIC_RW")
    private double scenicRw;

    //自然景观
    @Column(name = "C_SCENIC_ZR")
    private double scenicZr;

    //娱乐游玩景观
    @Column(name = "C_SCENIC_YL")
    private double scenicYl;

    public String getWeChatId() {
        return weChatId;
    }

    public void setWeChatId(String weChatId) {
        this.weChatId = weChatId;
    }

    public double getScenicRw() {
        return scenicRw;
    }

    public void setScenicRw(double scenicRw) {
        this.scenicRw = scenicRw;
    }

    public double getScenicZr() {
        return scenicZr;
    }

    public void setScenicZr(double scenicZr) {
        this.scenicZr = scenicZr;
    }

    public double getScenicYl() {
        return scenicYl;
    }

    public void setScenicYl(double scenicYl) {
        this.scenicYl = scenicYl;
    }

}



