package com.example.hospital.api.db.dao;

import java.util.ArrayList;
import java.util.HashMap;

public interface MedicalDeptDao {
    public ArrayList<HashMap> searchAll();

    public ArrayList<HashMap> searchDeptAndSub();


}




