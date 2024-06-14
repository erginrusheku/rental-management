package com.rental_management.service;

import com.rental_management.dto.SettingsDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SettingsServiceImpl implements SettingsService{
    @Override
    public List<SettingsDTO> getAllSettings() {
        return List.of();
    }

    @Override
    public SettingsDTO createSettings(SettingsDTO settingsDTO) {
        return null;
    }

    @Override
    public SettingsDTO getById(Long settingsId) {
        return null;
    }

    @Override
    public SettingsDTO updateSettings(Long settingsId, SettingsDTO settingsDTO) {
        return null;
    }

    @Override
    public void deleteSettings(Long settingsId) {

    }
}
