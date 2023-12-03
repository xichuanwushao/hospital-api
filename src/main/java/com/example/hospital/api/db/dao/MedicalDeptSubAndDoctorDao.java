package com.example.hospital.api.db.dao;

import com.example.hospital.api.db.pojo.MedicalDeptSubAndDoctorEntity;
import org.springframework.transaction.annotation.Transactional;

public interface MedicalDeptSubAndDoctorDao {
    @Transactional
    public void insert(MedicalDeptSubAndDoctorEntity entity);

}




