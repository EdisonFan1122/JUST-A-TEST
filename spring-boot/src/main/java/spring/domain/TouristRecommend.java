package spring.domain;

import javax.persistence.Column;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Edison on 2018/5/27.
 */
@Entity
@Table(name = "TOURIST_RECOMMEND")
public class TouristRecommend {

    //景区编号(主键)
    @Id
    @Column(name = "C_TOURIST_ID")
    private String touristId;

    //景区名称
    @Column(name = "C_TOURIST_NAME")
    private String touristName;

    //景区级别     1. 5A  2. 4A  3. 3A  4. 2A 5.  1A  6. 无
    @Column(name = "C_TOURIST_LEVEL")
    private String touristLevel;

    //详细地址
    @Column(name = "C_ADDRESS")
    private String address;

    //经度
    @Column(name="C_LNG")
    private String lng;

    //纬度
    @Column(name="C_LAT")
    private String lat;


    //是否免费景区
    @Column(name = "C_ISFREE")
    private String isFree;

    //景区详情
    @Column(name = "C_TOURIST_DECRIP")
    private String touristDecrip;

    //热度
    @Column(name = "L_SCORE")
    private int score;

    //推荐级别      1：优先推荐（新录入）2：正常 3：黑名单（有黑历史）
    @Column(name = "C_RECOMMEN_LEVEL")
    private String recommenLevel;

    //录入人
    @Column(name = "C_ENTER_USER")
    private String enterUser;

    //录入时间
    @Column(name = "D_ENTER_TIME")
    private Date enterTime;

    //修改人
    @Column(name = "C_UPDATE_USER")
    private String updateUser;

    //修改时间
    @Column(name = "D_UPDATE_TIME")
    private Date updateTime;

    //人文景观
    @Column(name = "C_SCENIC_RW")
    private String scenicRw;

    //自然景观
    @Column(name = "C_SCENIC_ZR")
    private String scenicZr;

    //娱乐游玩景观
    @Column(name = "C_SCENIC_YL")
    private String scenicYl;

    public void setTouristId(String touristId) {
        this.touristId = touristId;
    }

    public String getTouristName() {
        return touristName;
    }

    public void setTouristName(String touristName) {
        this.touristName = touristName;
    }

    public String getTouristLevel() {
        return touristLevel;
    }

    public void setTouristLevel(String touristLevel) {
        this.touristLevel = touristLevel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getIsFree() {
        return isFree;
    }

    public void setIsFree(String isFree) {
        this.isFree = isFree;
    }

    public String getTouristDecrip() {
        return touristDecrip;
    }

    public void setTouristDecrip(String touristDecrip) {
        this.touristDecrip = touristDecrip;
    }

    public String getRecommenLevel() {
        return recommenLevel;
    }

    public void setRecommenLevel(String recommenLevel) {
        this.recommenLevel = recommenLevel;
    }

    public String getEnterUser() {
        return enterUser;
    }

    public void setEnterUser(String enterUser) {
        this.enterUser = enterUser;
    }

    public Date getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(Date enterTime) {
        this.enterTime = enterTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setScenicRw(String scenicRw) {
        this.scenicRw = scenicRw;
    }

    public void setScenicZr(String scenicZr) {
        this.scenicZr = scenicZr;
    }

    public void setScenicYl(String scenicYl) {
        this.scenicYl = scenicYl;
    }

    public String getTouristId() {

        return touristId;
    }

    public String getScenicRw() {
        return scenicRw;
    }

    public String getScenicZr() {
        return scenicZr;
    }

    public String getScenicYl() {
        return scenicYl;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
