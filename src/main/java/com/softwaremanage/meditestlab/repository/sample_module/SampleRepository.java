package com.softwaremanage.meditestlab.repository.sample_module;

import com.softwaremanage.meditestlab.pojo.sample_module.Sample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SampleRepository extends JpaRepository<Sample, Integer> {

    List<Sample> findAllBysId(Integer sId);


}
