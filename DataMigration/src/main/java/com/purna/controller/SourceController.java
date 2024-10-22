package com.purna.controller;

import com.purna.model.SourceData;
import com.purna.service.SourceDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/source")
public class SourceController {

    @Autowired
    private SourceDataService sourceDataService;

    @PostMapping
    public int createSource(@RequestBody SourceData sourceData) {
        return sourceDataService.save(sourceData);
    }

    @PutMapping("/{id}")
    public int updateSource(@PathVariable Long id, @RequestBody SourceData sourceData) {
        sourceData.setId(id);
        return sourceDataService.update(sourceData);
    }

    @GetMapping("/{id}")
    public SourceData getSourceById(@PathVariable Long id) {
        return sourceDataService.findById(id);
    }

    @DeleteMapping("/{id}")
    public int deleteSource(@PathVariable Long id) {
        return sourceDataService.deleteById(id);
    }

    @GetMapping
    public List<SourceData> getAllSourceData() {
        return sourceDataService.findAll();
    }
}
