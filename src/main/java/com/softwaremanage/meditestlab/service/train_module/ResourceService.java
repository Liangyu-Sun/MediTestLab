package com.softwaremanage.meditestlab.service.train_module;


import com.softwaremanage.meditestlab.pojo.train_module.Resource;
import com.softwaremanage.meditestlab.repository.train_module.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    public void save(Resource resource) {
        resourceRepository.save(resource);
    }
    public List<Resource> findByProjectIdAndResourceType(Integer pId, String resourceType) {
        return resourceRepository.findAllByProjectIdAndResourceType(pId, resourceType);
    }
}