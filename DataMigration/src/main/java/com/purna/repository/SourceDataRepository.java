package com.purna.repository;

import com.purna.model.SourceData;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SourceDataRepository {
    int save(SourceData sourceData);
    int update(SourceData sourceData);
    SourceData findById(Long id);
    int deleteById(Long id);
    List<SourceData> findAll();

}