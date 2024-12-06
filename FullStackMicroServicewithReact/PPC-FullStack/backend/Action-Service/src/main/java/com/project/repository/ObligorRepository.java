package com.project.repository;

import com.project.entity.Obligor;
import com.project.entity.ObligorDocument;

public interface ObligorRepository {

    void saveObligor(Obligor obligor);
    Obligor updateObligor(Obligor obligor);
    void deleteobligor(String obligorId);

    void saveObligorDocument(ObligorDocument obligorDocument);
    void deleteObligorDocument(String obligorDocId);
}
