package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Compensation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {

    private static String EMPLOYEE_ID = "16a596ae-edd3-4847-99fe-c4518e82c86f";

    private String compensationUrl;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Before
    public void setup() {
        compensationUrl = "http://localhost:" + port + "/compensation/{id}";
    }

    @Test
    public void testCreateRead() {
        //Test compensation
        Compensation testCompensation = new Compensation();
        testCompensation.setSalary(100);
        testCompensation.setEffectiveDate(new Date(0));

        // Create checks
        Compensation createdCompensation = restTemplate.postForEntity(compensationUrl, testCompensation, Compensation.class, EMPLOYEE_ID).getBody();

        assertNotNull(createdCompensation);
        assertEquals(EMPLOYEE_ID, createdCompensation.getEmployee().getEmployeeId());
        assertCompensationEquivalence(testCompensation, createdCompensation);


        // Read checks
        Compensation[] readCompensations = restTemplate.getForEntity(compensationUrl, Compensation[].class, EMPLOYEE_ID).getBody();

        assertNotNull(readCompensations);
        assertEquals(1, readCompensations.length);
        assertEquals(createdCompensation.getEmployee().getEmployeeId(), readCompensations[0].getEmployee().getEmployeeId());
        assertCompensationEquivalence(readCompensations[0], createdCompensation);

    }

    private static void assertCompensationEquivalence(Compensation expected, Compensation actual) {
        assertEquals(expected.getEffectiveDate(), actual.getEffectiveDate());
        assertEquals(expected.getSalary(), actual.getSalary());
    }
}
