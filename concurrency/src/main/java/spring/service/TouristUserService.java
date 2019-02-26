package spring.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.dao.TouristRecommendDAO;
import spring.dao.TouristUserDAO;
import spring.domain.TouristRecommend;
import spring.domain.TouristUser;

/**
 * Created by Edison on 2018/5/27.
 */
@Service
public class TouristUserService {

    @Autowired
    private TouristUserDAO touristUserDAO;

    @Autowired
    private TouristRecommendDAO touristRecommendDAO;

    @Autowired
    private UserService userService;


    /*
     *  @Param requestMode="collect"或"unCollect"
     *  景点详情页面点击收藏，点击取消收藏，
     *  修改status值实现
     */
    public void updateCollect(String requestMode, String weChatId, String touristId) throws Exception {
        //获取当前系统时间
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date = df.parse(df.format(date));
        TouristUser touristUser = touristUserDAO.findByWeChatIdAndTouristId(weChatId, touristId);
        int status = 0;
        if ("collect".equals(requestMode)) {
            status = 1;
        } else {
            status = 0;
        }
        //1代表收藏,0代表取消收藏
        touristUser.setStatus(status);
        touristUser.setHistory(date);
        touristUserDAO.save(touristUser);
        //根据requestMode修改其用户对应的倾向值
        userService.updateWeChatUserAndTourScore(requestMode, weChatId, touristId);
    }


    /*
     * 返回收藏的景点列表信息
     */
    public List<TouristRecommend> getTouristCollect(String weChatId) throws Exception {
        List<String> list = touristUserDAO.findByWeChatId(weChatId);
        List<TouristRecommend> trList = new ArrayList<>();
        int length = list.size();
        String touristId = "";
        for (int i = 0; i < length; i++) {
            touristId = list.get(i);
            if (touristRecommendDAO.findByTouristId(touristId) != null) {
                trList.add(touristRecommendDAO.findByTouristId(touristId));
            }
        }
        return trList;
    }


    /*
     * 返回浏览历史的景点列表信息
     */
    public List<TouristRecommend> getTouristHistory(String weChatId) throws Exception {
        List<String> list = touristUserDAO.findByHisWeChatId(weChatId);
        List<TouristRecommend> hisList = new ArrayList<>();
        int length = list.size();
        String touristId = "";
        for (int i = 0; i < length; i++) {
            touristId = list.get(i);
            if (touristRecommendDAO.findByTouristId(touristId) != null) {
                hisList.add(touristRecommendDAO.findByTouristId(touristId));
            }
        }
        return hisList;
    }

    /*
     *   @Param requestMode="remove"
     *   列表页面，移除，用户对应倾向-5
     */
    public void getTourRemove(String requestMode, String weChatId, String touristId) throws Exception {
        userService.updateWeChatUserAndTourScore(requestMode, weChatId, touristId);
    }


    /*
     *   @Param requestMode="share"
     *   详情页面，点击分享，用户对应倾向+3
     */
    public void getTourShare(String requestMode, String weChatId, String touristId) throws Exception {
        userService.updateWeChatUserAndTourScore(requestMode, weChatId, touristId);
    }


    /*
     *   景点热度修改
     */
    public void updateTourScore(String touristId, int add) throws Exception {
        TouristRecommend touristRecommend = touristRecommendDAO.findByTouristId(touristId);
        if (touristRecommend != null) {
            touristRecommend.setScore(touristRecommend.getScore() + add);
        }
    }


    /*
     *   @Param weChatId touristId
     *  详情页面，根据weChatId touristId获取收藏状态
     */
    public TouristUser getCollectStatus(String weChatId,String touristId) throws Exception {
        return touristUserDAO.findByWeChatIdAndTouristId(weChatId,touristId);
    }


}