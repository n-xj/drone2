package com.md.drone.service.impl;

import com.md.drone.domain.dto.DroneRequest;
import com.md.drone.domain.Drone;
import com.md.drone.repository.DroneRepository;
import com.md.drone.service.DroneAiAttributeService;
import com.md.drone.service.DroneService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 无人机用例的默认实现：编排参数校验、AI 与持久化。
 */
@Service
public class DroneServiceImpl implements DroneService {

    private final DroneRepository droneRepository;
    private final DroneAiAttributeService droneAiAttributeService;

    /**
     * @param droneRepository        数据访问
     * @param droneAiAttributeService AI 属性
     */
    public DroneServiceImpl(DroneRepository droneRepository, DroneAiAttributeService droneAiAttributeService) {
        this.droneRepository = droneRepository;
        this.droneAiAttributeService = droneAiAttributeService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Drone> listByName(String nameOrNull) {
        return droneRepository.findByNameOptionally(nameOrNull);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Drone getByIdOrThrow(long id) {
        return droneRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("无人机不存在: id=" + id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Drone create(DroneRequest request) {
        Drone d = new Drone();
        d.setName(request.getName());
        d.setModel(request.getModel());
        d.setSerialNo(request.getSerialNo());
        d.setRemark(request.getRemark());
        LocalDateTime now = LocalDateTime.now();
        d.setCreatedAt(now);
        d.setUpdatedAt(now);
        d.setAttributesJson(droneAiAttributeService.generateJsonAttributes(d));
        droneRepository.save(d);
        return d;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Drone update(long id, DroneRequest request) {
        Drone existing = getByIdOrThrow(id);
        existing.setName(request.getName());
        existing.setModel(request.getModel());
        existing.setSerialNo(request.getSerialNo());
        existing.setRemark(request.getRemark());
        if (Boolean.TRUE.equals(request.getRegenerateAttributes())) {
            Drone forAi = new Drone();
            forAi.setName(request.getName());
            forAi.setModel(request.getModel());
            forAi.setSerialNo(request.getSerialNo());
            forAi.setRemark(request.getRemark());
            existing.setAttributesJson(droneAiAttributeService.generateJsonAttributes(forAi));
        }
        existing.setUpdatedAt(LocalDateTime.now());
        if (!droneRepository.update(existing)) {
            throw new IllegalArgumentException("更新失败: id=" + id);
        }
        return getByIdOrThrow(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void delete(long id) {
        if (!droneRepository.deleteById(id)) {
            throw new IllegalArgumentException("无人机不存在: id=" + id);
        }
    }
}
