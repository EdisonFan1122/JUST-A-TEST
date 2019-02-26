package spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import spring.domain.TouristRecommend;
import spring.domain.TouristUser;
import spring.domain.WechatUser;
import spring.service.TouristUserService;
import spring.service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;


/**
 * Created by Edison on 2018/5/26.
 */
@Controller
@RequestMapping("/recommend")
public class RecommendAction {

    @Autowired
    private UserService userService;

    @Autowired
    private TouristUserService touristUserService;

    @RequestMapping("ping")
    @ResponseBody
    public String ping() {
        return "pong";
    }



    /**
     * 获取微信小程序openid
     *
     * @author fyf
     * @param code 调用微信登陆返回的Code
     * @return openId
     */
    @RequestMapping(value = "getOpenId", method = RequestMethod.GET)
    @ResponseBody
    public  String getOpenId(String code){
        //微信端登录code值
        String requestUrl = "https://api.weixin.qq.com/sns/jscode2session";
        Map<String,String> requestUrlParam = new HashMap<>();
        requestUrlParam.put("appid", "wx1653305a5e9111a3");
        requestUrlParam.put("secret", "618e409c45c46f99173e6b0904c3cca0");
        requestUrlParam.put("js_code", code);
        requestUrlParam.put("grant_type", "authorization_code");
        //发送post请求读取调用微信 https://api.weixin.qq.com/sns/jscode2session 接口获取openid用户唯一标识
        return this.sendPost(requestUrl, requestUrlParam);
    }


    /**
     * 向指定 URL 发送POST方法的请求
     * @author fyf
     * @param url 发送请求的 URL
     * @param
     * @return 所代表远程资源的响应结果
     */
    public  String sendPost(String url, Map<String, ?> paramMap) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";

        String param = "";
        Iterator<String> it = paramMap.keySet().iterator();

        while(it.hasNext()) {
            String key = it.next();
            param += key + "=" + paramMap.get(key) + "&";
        }

        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性

            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
           e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }


    /*
     * 每次进入小程序后，检查用户是否已存在
     * 用户不存在，则返回false，进入景点倾向选择页面,提交之后，发起新增用户请求
     * 用户已存在，则返回true,直接进入推荐列表
     */
    @RequestMapping(value = "user/checkUser", method = RequestMethod.GET)
    @ResponseBody
    public boolean checkWechatUser(String weChatId) throws Exception {
        return userService.checkWechatUser(weChatId);
    }


    /*
     * 新增一个用户
     * 新增成功，则返回true，进入推荐列表页面
     * 则返回false,新增失败
     */
    @RequestMapping(value = "user/addUser", method = RequestMethod.GET)
    @ResponseBody
    public boolean addWechatUser(String weChatId, String strRw, String strZr, String strYl) throws Exception {
        return userService.addWechatUser(weChatId, strRw, strZr, strYl);
    }


    /*
     *  进入主页面，获得推荐,展示被推荐景点列表
     */
    @RequestMapping(value = "user/getTourRecommendList", method = RequestMethod.GET)
    @ResponseBody
    public List<TouristRecommend> getTourRecommendList(String weChatId) throws Exception {
        return userService.getRecommendTourist(weChatId);
    }

    /*
     *   @Param requestMode="browse"
     *  点击主页面列表，收藏页面列表，浏览历史页面列表，获得景点详情，进入景点详情页面
     *  1.若已存在该浏览数据，则更新浏览时间：修改TOURIST_USER表中对应数据的浏览时间：D_HISTORY=nowdate
     *  2.若不存在该浏览数据，则新增一条数据touristUser:D_HISTORY=nowdate,C_STATUS=‘0’
     *  3.用户和景点对应的吻合度超过0.8的倾向分类，其用户在对应的分类上，值+1
     *  4.对应景点热度+1
     */
    @RequestMapping(value = "user/getTouristInfo", method = RequestMethod.GET)
    @ResponseBody
    public TouristRecommend getTouristInfo(String requestMode, String weChatId, String touristId) throws Exception {
        return userService.getTouristInfo(requestMode, weChatId, touristId);
    }

    /*
     *  @Param requestMode="collect"或"unCollect"
     * 详情页面点击收藏，点击取消收藏
     * 1.点击“收藏”按钮，修改TOURIST_USER表中对应数据的收藏状态：C_STATUS=‘1’
     * 2.点击“取消收藏”按钮，修改TOURIST_USER表中对应数据的收藏状态：C_STATUS=‘0’
     * 3.更新一条已存在的对应的浏览记录的历史时间:D_HISTORY=nowdate
     * 4.点击“收藏”，用户对应的兴趣方向+2，热度加2
     * 5.点击“取消收藏”，用户对应的兴趣方向-1，热度-1
     */
    @RequestMapping(value = "user/updateCollect", method = RequestMethod.GET)
    @ResponseBody
    public boolean updateCollect(String requestMode, String weChatId, String touristId) throws Exception {
        boolean flag = true;
        try {
            touristUserService.updateCollect(requestMode, weChatId, touristId);
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }
        return flag;
    }


    /*
     * “我的”页面，点击收藏，进入收藏页面列表，页面样式同推荐页面样式
     */
    @RequestMapping(value = "user/getCollectList", method = RequestMethod.GET)
    @ResponseBody
    public List<TouristRecommend> getTouristCollect(String weChatId) throws Exception {
        return touristUserService.getTouristCollect(weChatId);
    }


    /*
     * “我的”页面，点击"浏览历史"，进入"浏览历史"页面列表，页面样式同推荐页面样式
     */
    @RequestMapping(value = "user/getHistoryList", method = RequestMethod.GET)
    @ResponseBody
    public List<TouristRecommend> getTouristHistory(String weChatId) throws Exception {
        return touristUserService.getTouristHistory(weChatId);
    }


    /*
     *   @Param requestMode="remove"
     *  列表页面，点击“移除”，对应倾向值-5，热度-5
     */
    @RequestMapping(value = "user/getTourRemove", method = RequestMethod.GET)
    @ResponseBody
    public boolean getTourRemove(String requestMode, String weChatId, String touristId) throws Exception {
        boolean flag = true;
        try {
            touristUserService.getTourRemove(requestMode, weChatId, touristId);
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }
        return flag;
    }


    /*
     *   @Param requestMode="share"
     *  详情页面，点击“转发”，对应倾向值+3，热度+3
     */
    @RequestMapping(value = "user/getTourShare", method = RequestMethod.GET)
    @ResponseBody
    public boolean getTourShare(String requestMode, String weChatId, String touristId) throws Exception {
        boolean flag = true;
        try {
            touristUserService.getTourShare(requestMode, weChatId, touristId);
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }
        return flag;
    }

    /*
     *   @Param weChatId touristId
     *  详情页面，根据weChatId touristId获取收藏状态
     */
    @RequestMapping(value = "user/collectStatu", method = RequestMethod.GET)
    @ResponseBody
    public TouristUser getCollectStatus(String weChatId,String touristId) throws Exception {
        TouristUser touristUser =null;
        try {
            touristUser = touristUserService.getCollectStatus(weChatId,touristId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return touristUser;
    }




}
