package com.rental_management.service;

import com.rental_management.dto.ResponseBody;
import com.rental_management.dto.SettingsDTO;

import java.util.List;

public interface SettingsService {
    List<SettingsDTO> getAllSettings();
    SettingsDTO createSettings(SettingsDTO settingsDTO);
    SettingsDTO getById(Long settingsId);
    SettingsDTO updateSettings(Long settingsId, SettingsDTO settingsDTO);
    void deleteSettings(Long settingsId);
}
