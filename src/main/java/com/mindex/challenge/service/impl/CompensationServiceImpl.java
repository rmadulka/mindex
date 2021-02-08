package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CompensationServiceImpl implements CompensationService {

    @Autowired
    private CompensationRepository compensationRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;

    @Override
    public Compensation create(Compensation compensation) {
        compensation.setCid(UUID.randomUUID().toString());
        employeeService.read(compensation.getEmployeeId());
        compensationRepository.insert(compensation);
        return compensation;
    }

    @Override
    public List<Compensation> read(String employeeId){
        return compensationRepository.findByEmployeeId(employeeId);
    }
}
