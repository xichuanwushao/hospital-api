package com.example.hospital.api.db.dao;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public interface DoctorWorkPlanDao {
    public ArrayList<HashMap> searchWorkPlanInRange(Map param);
}




