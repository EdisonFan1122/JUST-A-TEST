package spring.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import spring.domain.TouristRecommend;

import java.util.List;

/**
 * Created by Edison on 2018/5/27.
 */
@Repository
public interface TouristRecommendDAO extends JpaRepository<TouristRecommend, Integer> {


    TouristRecommend findByTouristId(String touristId);


    @Query(value = "select t.* from TOURIST_RECOMMEND t ORDER BY t.L_SCORE+0 DESC,t.D_UPDATE_TIME DESC", nativeQuery = true)
    List<TouristRecommend> findAllOrderByScoreAndUpdateTimeDesc();


}


