package spring.domain;

import javax.annotation.sql.DataSourceDefinition;
import javax.persistence.*;
import java.util.Date;

/**
 * Created by Edison on 2018/5/27.
 */

@Entity
@Table(name = "TOURIST_USER")
public class TouristUser {

    //自增主键
    @Id
    @GeneratedValue
    @Column(name = "L_SERIALNO")
    private long serialno;

    //微信ID
    @Column(name = "C_WECHAT_ID")
    private String weChatId;

    //景点ID
    @Column(name = "C_TOURIST_ID")
    private String touristId;

    //是否收藏，默认为0：未收藏，1为收藏
    @Column(name = "L_STATUS")
    private int status;

    //浏览时间，请求点击详情时保存系统时间
    @Column(name = "D_HISTORY")
    private Date history;

    public long getSerialno() {
        return serialno;
    }

    public void setSerialno(long serialno) {
        this.serialno = serialno;
    }

    public String getWeChatId() {
        return weChatId;
    }

    public void setWeChatId(String weChatId) {
        this.weChatId = weChatId;
    }

    public String getTouristId() {
        return touristId;
    }

    public void setTouristId(String touristId) {
        this.touristId = touristId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getHistory() {
        return history;
    }

    public void setHistory(Date history) {
        this.history = history;
    }
}






