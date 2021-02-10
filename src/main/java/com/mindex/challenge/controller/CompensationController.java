package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CompensationController {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);

    @Autowired
    private CompensationService compensationService;

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/compensation/{employeeId}")
    public Compensation create(@RequestBody Compensation compensation, @PathVariable String employeeId) {
        LOG.debug("Received compensation create request for employee id [{}]", employeeId);

        Employee requestedEmployee = employeeService.read(employeeId);

        return compensationService.create(compensation, requestedEmployee);
    }

    @GetMapping("/compensation/{employeeId}")
    public List<Compensation> read(@PathVariable String employeeId) {
        LOG.debug("Received compensation read request for employee id [{}]", employeeId);

        return compensationService.read(employeeId);
    }

}
