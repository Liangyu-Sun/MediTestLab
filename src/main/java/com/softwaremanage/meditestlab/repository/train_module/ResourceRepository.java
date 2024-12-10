package com.softwaremanage.meditestlab.repository.train_module;

import com.softwaremanage.meditestlab.pojo.train_module.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Integer> {
    //List<Resource> findByPIdAndResourceType(Integer pId, String resourceType);
    List<Resource> findAllByProjectIdAndResourceType(Integer pId, String resourceType);
}
