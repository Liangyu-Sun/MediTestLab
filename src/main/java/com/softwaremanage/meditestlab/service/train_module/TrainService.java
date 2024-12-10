package com.softwaremanage.meditestlab.service.train_module;

import com.softwaremanage.meditestlab.pojo.train_module.Train;
import com.softwaremanage.meditestlab.repository.train_module.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainService {

    @Autowired
    private TrainRepository trainRepository;

    public Train saveTrain(Train train) {
        return trainRepository.save(train);
    }
}