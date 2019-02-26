package spring.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.domain.WechatUser;


/**
 * Created by Edison on 2018/5/26.
 */
@Repository
public interface WechatUserDAO extends JpaRepository<WechatUser, Integer> {

    WechatUser findByWeChatId(String weChatId);


}
