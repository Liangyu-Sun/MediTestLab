package com.softwaremanage.meditestlab.repository.train_module;

import com.softwaremanage.meditestlab.pojo.train_module.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrainRepository extends JpaRepository<Train, Integer> {
    //List<Train> findAllBytId(Integer trainingId) ;
    Train  findByTrainingId(Integer trainingId);
}
