package com.md.drone.repository.impl;

import com.md.drone.domain.Drone;
import com.md.drone.repository.DroneRepository;
import com.md.drone.repository.mapper.DroneMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

/**
 * {@link DroneRepository} 的 MyBatis 实现。
 */
@Repository
public class DroneRepositoryImpl implements DroneRepository {

    private final DroneMapper droneMapper;

    /**
     * @param droneMapper 映射器
     */
    public DroneRepositoryImpl(DroneMapper droneMapper) {
        this.droneMapper = droneMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Drone drone) {
        droneMapper.insert(drone);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean update(Drone drone) {
        return droneMapper.updateById(drone) > 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteById(long id) {
        return droneMapper.deleteById(id) > 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Drone> findById(long id) {
        return Optional.ofNullable(droneMapper.selectById(id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Drone> findByNameOptionally(String nameOrNull) {
        String like = null;
        if (StringUtils.hasText(nameOrNull)) {
            like = "%" + nameOrNull + "%";
        }
        return droneMapper.selectList(like);
    }
}
