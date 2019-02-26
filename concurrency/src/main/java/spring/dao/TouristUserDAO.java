package spring.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import spring.domain.TouristUser;

import java.util.List;

/**
 * Created by Edison on 2018/5/27.
 */
public interface TouristUserDAO extends JpaRepository<TouristUser, Integer> {

//    @Query("select t from TouristUser t where t.weChatId=:weChatId and t.status= 1")
//    List<TouristUser> findByWeChatId(@Param("weChatId") String weChatId, Sort sort);

    @Query(value = "select t.C_TOURIST_ID from TOURIST_USER t where t.L_STATUS = '1'and t.C_WECHAT_ID=?1 ORDER BY t.D_HISTORY DESC ", nativeQuery = true)
    List<String> findByWeChatId(String weChatId);


    @Query(value = "select t.C_TOURIST_ID from TOURIST_USER t where t.C_WECHAT_ID=?1 ORDER BY t.D_HISTORY DESC ", nativeQuery = true)
    List<String> findByHisWeChatId(String weChatId);


    TouristUser findByWeChatIdAndTouristId(String weChatId, String touristId);




}
