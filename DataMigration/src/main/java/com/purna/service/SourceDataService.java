package com.purna.service;

import com.purna.model.SourceData;
import com.purna.repository.SourceDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

@Service
public class SourceDataService {

    @Autowired
    private SourceDataRepository sourceDataRepository;

    public int save(SourceData sourceData) {
        return sourceDataRepository.save(sourceData);
    }

    public int update(SourceData sourceData) {
        return sourceDataRepository.update(sourceData);
    }

    public SourceData findById(Long id) {
        return sourceDataRepository.findById(id);
    }

    public int deleteById(Long id) {
        return sourceDataRepository.deleteById(id);
    }

    public List<SourceData> findAll() {
        return sourceDataRepository.findAll();
    }
}
