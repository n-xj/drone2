package com.md.drone.service.impl;

import com.md.drone.domain.dto.DroneRequest;
import com.md.drone.domain.Drone;
import com.md.drone.repository.DroneRepository;
import com.md.drone.service.DroneAiAttributeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.mockito.ArgumentCaptor;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * 服务层在仓储与 AI 已隔离 mock 时行为符合预期。
 */
@ExtendWith(MockitoExtension.class)
class DroneServiceTest {

    @Mock
    private DroneRepository droneRepository;
    @Mock
    private DroneAiAttributeService ai;

    private DroneServiceImpl droneService;

    /**
     * 注入 mock 依赖，构建被测服务。
     */
    @BeforeEach
    void setUp() {
        droneService = new DroneServiceImpl(droneRepository, ai);
    }

    @Test
    void should_return_list_when_listByName() {
        when(droneRepository.findByNameOptionally("q")).thenReturn(Collections.emptyList());
        assertEquals(0, droneService.listByName("q").size());
    }

    @Test
    void should_throw_when_id_missing() {
        when(droneRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> droneService.getByIdOrThrow(1L));
    }

    @Test
    void should_create_and_fill_attributes() {
        when(ai.generateJsonAttributes(any(Drone.class))).thenReturn("{\"a\":1}");
        DroneRequest f = new DroneRequest();
        f.setName("n1");
        ArgumentCaptor<Drone> cap = ArgumentCaptor.forClass(Drone.class);
        droneService.create(f);
        verify(droneRepository).save(cap.capture());
        assertEquals("{\"a\":1}", cap.getValue().getAttributesJson());
    }

    @Test
    void should_delete_when_exists() {
        when(droneRepository.deleteById(1L)).thenReturn(true);
        droneService.delete(1L);
        verify(droneRepository).deleteById(1L);
    }

    @Test
    void should_update_without_regenerating_attributes() {
        Drone existing = new Drone();
        existing.setId(1L);
        when(droneRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(droneRepository.update(any(Drone.class))).thenReturn(true);
        DroneRequest f = new DroneRequest();
        f.setName("n2");
        f.setModel("m2");
        f.setRegenerateAttributes(false);
        Drone out = droneService.update(1L, f);
        assertEquals("n2", out.getName());
    }

    @Test
    void should_regenerate_json_when_flag_true() {
        Drone existing = new Drone();
        existing.setId(1L);
        when(droneRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(droneRepository.update(any(Drone.class))).thenReturn(true);
        when(ai.generateJsonAttributes(any(Drone.class))).thenReturn("{\"g\":1}");
        DroneRequest f = new DroneRequest();
        f.setName("n");
        f.setRegenerateAttributes(Boolean.TRUE);
        droneService.update(1L, f);
        verify(ai).generateJsonAttributes(any(Drone.class));
    }

    @Test
    void should_throw_when_update_repository_returns_false() {
        Drone existing = new Drone();
        existing.setId(1L);
        when(droneRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(droneRepository.update(any(Drone.class))).thenReturn(false);
        DroneRequest f = new DroneRequest();
        f.setName("n");
        assertThrows(IllegalArgumentException.class, () -> droneService.update(1L, f));
    }

    @Test
    void should_throw_when_delete_by_id_fails() {
        when(droneRepository.deleteById(9L)).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () -> droneService.delete(9L));
    }
}
