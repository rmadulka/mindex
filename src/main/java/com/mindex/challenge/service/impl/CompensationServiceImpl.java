package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CompensationServiceImpl implements CompensationService {

    @Autowired
    private CompensationRepository compensationRepository;

    @Override
    public Compensation create(Compensation compensation, Employee employee) {
        compensation.setCid(UUID.randomUUID().toString());
        compensation.setEmployee(employee);
        compensationRepository.insert(compensation);
        return compensation;
    }

    @Override
    public List<Compensation> read(String employeeId){
        return compensationRepository.findByEmployeeId(employeeId);
    }
}
