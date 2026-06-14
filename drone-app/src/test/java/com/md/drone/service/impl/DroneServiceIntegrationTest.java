package com.md.drone.service.impl;

import com.md.drone.domain.dto.DroneRequest;
import com.md.drone.domain.Drone;
import com.md.drone.service.DroneService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 全链路（SQLite 内存、MyBatis 真实 insert）的创建与列表验证。
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
class DroneServiceIntegrationTest {

    @Autowired
    private DroneService droneService;

    @Test
    void should_persist_and_list_when_create() {
        DroneRequest f = new DroneRequest();
        f.setName("it-drone");
        f.setModel("m1");
        Drone saved = droneService.create(f);
        assertNotNull(saved.getId());
        assertTrue(droneService.listByName("it") != null
                && droneService.listByName("it").size() > 0);
    }

    @Test
    void should_delete_when_exists() {
        DroneRequest f = new DroneRequest();
        f.setName("to-del");
        Drone saved = droneService.create(f);
        long id = saved.getId();
        droneService.delete(id);
        assertFalse(droneService.listByName(null).stream().anyMatch(d -> d.getId() == id));
    }

    @Test
    void should_update_name_when_persisted() {
        DroneRequest create = new DroneRequest();
        create.setName("u1");
        create.setModel("m1");
        Drone saved = droneService.create(create);
        long id = saved.getId();
        DroneRequest upd = new DroneRequest();
        upd.setName("u2");
        upd.setModel("m2");
        upd.setRegenerateAttributes(Boolean.FALSE);
        Drone out = droneService.update(id, upd);
        assertTrue(out.getName().contains("u2"));
    }
}
