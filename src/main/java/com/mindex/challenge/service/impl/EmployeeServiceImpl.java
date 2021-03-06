package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee create(Employee employee) {
        LOG.debug("Creating employee [{}]", employee);

        employee.setEmployeeId(UUID.randomUUID().toString());
        employeeRepository.insert(employee);

        return employee;
    }

    @Override
    public Employee read(String id) {
        LOG.debug("Finding employee with id [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        return employee;
    }

    @Override
    public Employee update(Employee employee) {
        LOG.debug("Updating employee [{}]", employee);

        return employeeRepository.save(employee);
    }

    @Override
    public ReportingStructure getReportingStructure(String employeeId) {
        LOG.debug("Retrieving reporting structure for employeeId [{}]", employeeId);

        Employee rootEmployee = employeeRepository.findByEmployeeId(employeeId);

        //Subtract 1 to exclude the root employee from being counted in the number of reports
        return new ReportingStructure(rootEmployee, traverseReportTree(rootEmployee) - 1);
    }

    /**
     * Traverses the Employee report structure (tree) and returns total number of employees given a root employee
     * Assumes that the reporting structure is a tree (no node may report to two parents)
     * @param employee Root employee
     * @return total number of employees including the root employee
     */
    private int traverseReportTree(Employee employee){
        if (employee.getDirectReports() == null) {
            return 1;
        }
        int sum = 0;
        List<Employee> directReports = employee.getDirectReports();
        for (int i = 0; i < directReports.size(); i++) {

            //Because an employee's direct reports only hold employeeIds, we need to read the DB
            //for the complete Employee object. We also need to wire the direct reports to the Employee
            //object in order to return the fully filled out report
            Employee childEmployee = read(directReports.get(i).getEmployeeId());
            directReports.set(i, childEmployee);
            sum += traverseReportTree(childEmployee);
        }
        return sum + 1;
    }
}
