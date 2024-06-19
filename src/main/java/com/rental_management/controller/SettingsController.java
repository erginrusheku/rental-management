package com.rental_management.controller;


import com.rental_management.service.SettingsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/settings")
public class SettingsController {
    private final SettingsService settingsService;

    public SettingsController(SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    @GetMapping("/all")
    ResponseEntity<List<SettingsDTO>> getAllSettings(){
        List<SettingsDTO> settingsList = settingsService.getAllSettings();
        return new ResponseEntity<>(settingsList, HttpStatus.OK);
    }

    @PostMapping("/create")
    ResponseEntity<SettingsDTO> createSettings(@RequestBody SettingsDTO settingsDTO){
        SettingsDTO settings = settingsService.createSettings(settingsDTO);
        return new ResponseEntity<>(settings, HttpStatus.CREATED);
    }

    @GetMapping("/settingsId/{settingsId}")
    ResponseEntity<SettingsDTO> getById(@PathVariable Long settingsId){
        SettingsDTO settingIds = settingsService.getById(settingsId);
        return new ResponseEntity<>(settingIds, HttpStatus.OK);
    }

    @PutMapping({"/updateSettings/{settingsId}"})
    ResponseEntity<SettingsDTO> updateSettings(@PathVariable Long settingsId, @RequestBody SettingsDTO settingsDTO){
        SettingsDTO settingsAndId = settingsService.updateSettings(settingsId, settingsDTO);
        return new ResponseEntity<>(settingsAndId, HttpStatus.OK);
    }

    @DeleteMapping("/deleteSettingsId/{settingsId}")
    ResponseEntity<Void> deleteSettings(@PathVariable Long settingsId){
        settingsService.deleteSettings(settingsId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
