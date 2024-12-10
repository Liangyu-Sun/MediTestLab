package com.softwaremanage.meditestlab.repository.sample_module;

import com.softwaremanage.meditestlab.pojo.sample_module.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

//    List<Application> findByJcIdAndSaIdAndAState(Integer jcId, Integer saId, String aState);

//    List<Application> findBysId(Integer sId);

    List<Application> findByJcId(Integer uId);
}
