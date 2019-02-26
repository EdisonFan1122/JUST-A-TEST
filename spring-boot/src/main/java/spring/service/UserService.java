package spring.service;

import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.dao.TouristRecommendDAO;
import spring.dao.TouristUserDAO;
import spring.dao.WechatUserDAO;
import spring.domain.TouristRecommend;
import spring.domain.TouristUser;
import spring.domain.WechatUser;

import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by Edison on 2018/5/26.
 */
@Service
public class UserService {

    @Autowired
    private WechatUserDAO wechatUserDAO;

    @Autowired
    private TouristRecommendDAO touristRecommendDAO;

    @Autowired
    private TouristUserDAO touristUserDAO;

    @Autowired
    private TouristUserService touristUserService;


    /*
     *  每次进入小程序后，检查用户是否已存在
     *  用户不存在，则返回false，进入景点倾向选择页面,提交之后，发起新增用户请求
     *  用户已存在，则返回true,直接进入推荐列表
     */
    public boolean checkWechatUser(String weChatId) throws Exception {
        if (wechatUserDAO.findByWeChatId(weChatId) == null) {
            return false;
        } else {
            return true;
        }
    }

    /*
     *  用于用户首次进入小程序，选择景点倾向后，保存用户的ID，以及对人文，自然，娱乐三大类景点的兴趣度
     */
    public boolean addWechatUser(String weChatId, String strRw, String strZr, String strYl) throws Exception {
        boolean flag = true;
        List<Double> list = this.getUserCharact(strRw, strZr, strYl);
        double scenicRw = list.get(0);
        double scenicZr = list.get(1);
        double scenicYl = list.get(2);
        try {
            if (wechatUserDAO.findByWeChatId(weChatId) == null) {
                WechatUser wechatUser = new WechatUser();
                wechatUser.setWeChatId(weChatId);
                wechatUser.setScenicRw(scenicRw);
                wechatUser.setScenicZr(scenicZr);
                wechatUser.setScenicYl(scenicYl);
                wechatUserDAO.save(wechatUser);
            }
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }
        return flag;
    }

    /*
     * 查询：用户进入主页后，显示被推荐的景区列表信息
     * 推荐算法实现
     */
    public List<TouristRecommend> getRecommendTourist(String weChatId) throws Exception {
        WechatUser wechatUser = wechatUserDAO.findByWeChatId(weChatId);
        //用户兴趣分类,,,,,,,
        double scenicRw = wechatUser.getScenicRw();
        double scenicZr = wechatUser.getScenicZr();
        double scenicYl = wechatUser.getScenicYl();

        //依据景点热度，以及更新时间来倒排序
        List<TouristRecommend> list = touristRecommendDAO.findAllOrderByScoreAndUpdateTimeDesc();
        List<TouristRecommend> reList = new ArrayList<>();
        //景区特点分类，,,,,,,,
        double rw = 0;
        double zr = 0;
        double yl = 0;

        //吻合度
        double kindA = 0;
        double kindB = 0;
        double kindC = 0;

        int lSize = list.size();
        if (lSize > 0) {
            TouristRecommend touristRecommend = null;
            List<Double> tourCharact = null;
            for (int i = 0; i < lSize; i++) {
                touristRecommend = list.get(i);
                tourCharact = this.getTourCharact(touristRecommend.getTouristId());
                rw = tourCharact.get(0);
                zr = tourCharact.get(1);
                yl = tourCharact.get(2);
                //求吻合度百分比
                if (!(rw < 1.0E-8 && scenicRw < 1.0E-8)) {
                    kindA = rw < scenicRw ? (rw / scenicRw) : (scenicRw / rw);
                }

                if (!(zr < 1.0E-8 && scenicZr < 1.0E-8)) {
                    kindB = zr < scenicZr ? (zr / scenicZr) : (scenicZr / zr);
                }

                if (!(yl < 1.0E-8 && scenicYl < 1.0E-8)) {
                    kindC = yl < scenicYl ? (yl / scenicYl) : (scenicYl / yl);
                }

                //要求吻合度超过0.8且景点在这一大类超过一半的分数才推荐
                boolean A = kindA >= 0.7 && rw >= 50;
                boolean B = kindB >= 0.7 && zr >= 50;
                boolean C = kindC >= 0.7 && yl >= 50;
//                boolean D = reList.size() < 10;
                if (A || B || C) {
                    reList.add(touristRecommend);
                }
            }
        }
        //如果所有景点都不符合推荐吻合度，则按时间和热度倒序推荐
        if (reList.size() == 0) {
            reList = touristRecommendDAO.findAllOrderByScoreAndUpdateTimeDesc();
        }
        return reList;
    }

    /*
     *   @Param requestMode="browse"
     *   1.点击列表某一条数据，获取景点详情信息........用于推荐页面列表，收藏页面列表，历史浏览页面列表
     *   2.用户和景点对应的吻合度超过0.8的倾向分类，其用户在对应的分类上，值+1
     *   3.浏览详情，对应景区热度+1
     */
    public TouristRecommend getTouristInfo(String requestMode, String weChatId, String touristId) throws Exception {
        //获取当前系统时间
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date = df.parse(df.format(date));
        TouristUser touristUser = new TouristUser();
        //表中无此条浏览数据，则新增一条
        if (touristUserDAO.findByWeChatIdAndTouristId(weChatId, touristId) == null) {
            touristUser.setWeChatId(weChatId);
            touristUser.setTouristId(touristId);
            //"0"代表收藏
            touristUser.setStatus(0);
            touristUser.setHistory(date);
        } else {
            //表中有此条浏览数据，则修改浏览时间，更新浏览历史
            touristUser = touristUserDAO.findByWeChatIdAndTouristId(weChatId, touristId);
            touristUser.setHistory(date);
        }
        touristUserDAO.save(touristUser);

        //通过touristId获取景点详情
        TouristRecommend touristRecommend = touristRecommendDAO.findByTouristId(touristId);

        if (touristRecommend != null) {
            //浏览详情：修改用户倾向值：+1,景点浏览热度+1
            this.updateWeChatUserAndTourScore(requestMode, weChatId, touristId);
        }

        return touristRecommend;
    }


    /*
     *  获取某一用户爱好倾向，用于用户首次进入小程序
     */
    public List<Double> getUserCharact(String strRw, String strZr, String strYl) throws Exception {
        //用户兴趣分类，,,,,,,,
        double rw = 0;
        double zr = 0;
        double yl = 0;
        List<Double> charactList = new ArrayList<>();
        if (!StringUtils.isNullOrEmpty(strRw)) {
            int count = this.getCount(strRw);
            rw = count * 12.5;
        }

        if (!StringUtils.isNullOrEmpty(strZr)) {
            int count = this.getCount(strZr);
            zr = count * 16.7;
        }

        if (!StringUtils.isNullOrEmpty(strYl)) {
            int count = this.getCount(strYl);
            yl = count * 8.4;
        }
        charactList.add(rw);
        charactList.add(zr);
        charactList.add(yl);
        return charactList;
    }

    /*
     *  获取某一景点特点倾向
     */
    public List<Double> getTourCharact(String touristId) throws Exception {
        TouristRecommend touristRecommend = touristRecommendDAO.findByTouristId(touristId);
        //景区特点分类，,,,,,,,
        double rw = 0;
        double zr = 0;
        double yl = 0;
        List<Double> charactList = new ArrayList<>();
        if (!StringUtils.isNullOrEmpty(touristRecommend.getScenicRw())) {
            int count = this.getCount(touristRecommend.getScenicRw());
            rw = count * 12.5;
        }

        if (!StringUtils.isNullOrEmpty(touristRecommend.getScenicZr())) {
            int count = this.getCount(touristRecommend.getScenicZr());
            zr = count * 16.7;
        }

        if (!StringUtils.isNullOrEmpty(touristRecommend.getScenicYl())) {
            int count = this.getCount(touristRecommend.getScenicYl());
            yl = count * 8.4;
        }

        charactList.add(rw);
        charactList.add(zr);
        charactList.add(yl);
        return charactList;
    }


    //计算字符串中“,”数量，并+1
    public int getCount(String str) {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ',') {
                count++;
            }
        }
        return count + 1;
    }


    /*
     *  根据用户倾向以及景点特点倾向来计算吻合度
     *  主要用于得出被推荐的那一类
     */
    public Map<String, Double> getClassResult(String weChatId, String touristId) throws Exception {
        WechatUser wechatUser = wechatUserDAO.findByWeChatId(weChatId);
        //用户兴趣分类,,,,,,,
        double scenicRw = wechatUser.getScenicRw();
        double scenicZr = wechatUser.getScenicZr();
        double scenicYl = wechatUser.getScenicYl();

        List<Double> charactList = this.getTourCharact(touristId);
        //景区特点分类，,,,,,,,
        double rw = charactList.get(0);
        double zr = charactList.get(1);
        double yl = charactList.get(2);

        //吻合度
        double kindA = 0;
        double kindB = 0;
        double kindC = 0;

        //求吻合度百分比
        if (rw < 1.0E-8 && scenicRw < 1.0E-8) {
        } else {
            kindA = rw < scenicRw ? (rw / scenicRw) : (scenicRw / rw);
        }

        if (zr < 1.0E-8 && scenicZr < 1.0E-8) {
        } else {
            kindB = zr < scenicZr ? (zr / scenicZr) : (scenicZr / zr);
        }

        if (yl < 1.0E-8 && scenicYl < 1.0E-8) {
        } else {
            kindC = yl < scenicYl ? (yl / scenicYl) : (scenicYl / yl);
        }

        Map<String, Double> map = new HashMap<>();

        //要求吻合度超过0.5且景点在这一大类超过一半的分数才推荐
        if (kindA >= 0.5 && rw >= 50) {
            map.put("scenicRw", scenicRw);
        }

        if (kindB >= 0.5 && zr >= 50) {
            map.put("scenicZr", scenicZr);
        }

        if (kindC >= 0.5 && yl >= 50) {
            map.put("scenicYl", scenicYl);
        }

        return map;
    }


    /*
     *  修改用户景点倾向
     */
    public void updateWeChatUser(String weChatId, String touristId, int add) throws Exception {
        Map<String, Double> map = this.getClassResult(weChatId, touristId);
        WechatUser wechatUser = wechatUserDAO.findByWeChatId(weChatId);
        boolean overTop = false;
        //map中的value为用户对于这一特征的兴趣度
        if (!map.isEmpty()) {
            if (map.containsKey("scenicRw")) {
                //分值小于98.000000....兴趣倾向最多为100,仅针对加分时
                overTop = map.get("scenicRw") < (100 - add) && map.get("scenicRw") > Math.abs(add);
                if (overTop) {
                    wechatUser.setScenicRw(map.get("scenicRw") + add);
                }
            }
            if (map.containsKey("scenicZr")) {
                overTop = map.get("scenicZr") < (100 - add) && map.get("scenicZr") > Math.abs(add);
                if (overTop) {
                    wechatUser.setScenicZr(map.get("scenicZr") + add);
                }
            }
            if (map.containsKey("scenicYl")) {
                overTop = map.get("scenicYl") < (100 - add) && map.get("scenicYl") > Math.abs(add);
                if (overTop) {
                    wechatUser.setScenicYl(map.get("scenicYl") + add);
                }
            }
            //如果map为空，则说明此用户的对于此景点三类特征都不吻合，此景点为根据热度排名所推荐景点，因此给改用户所有特征方向同时加兴趣度
        } else {
            overTop = wechatUser.getScenicRw() < (100 - add) && wechatUser.getScenicRw() > Math.abs(add);
            if (overTop) {
                wechatUser.setScenicRw(wechatUser.getScenicRw() + add);
            }

            overTop = wechatUser.getScenicZr() < (100 - add) && wechatUser.getScenicZr() > Math.abs(add);
            if (overTop) {
                wechatUser.setScenicZr(wechatUser.getScenicZr() + add);
            }

            overTop = wechatUser.getScenicYl() < (100 - add) && wechatUser.getScenicYl() > Math.abs(add);
            if (overTop) {
                wechatUser.setScenicYl(wechatUser.getScenicYl() + add);
            }
        }
        wechatUserDAO.save(wechatUser);
    }


    /*
     *   修改用户倾向值
     *   同时修改景点热度值
     */
    public void updateWeChatUserAndTourScore(String requestMode, String weChatId, String touristId) throws Exception {
        int add = 0;
        //根据请求参数确定修改的分值
        switch (requestMode) {
            //浏览详情
            case "browse":
                add = 1;
                break;
            //添加收藏
            case "collect":
                add = 2;
                break;
            //取消收藏
            case "unCollect":
                add = -1;
                break;
            //移除
            case "remove":
                add = -5;
                break;
            //分享
            case "share":
                add = 3;
                break;
            default:
                add = 0;
                break;
        }

        //修改景区热度部分
        touristUserService.updateTourScore(touristId, add);

        //修改用户倾向部分
        this.updateWeChatUser(weChatId, touristId, add);


    }


}
