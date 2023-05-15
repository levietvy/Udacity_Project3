package com.udacity.jdnd.course3.critter.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class EmployeeServiceId implements Serializable {
    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "service_id")
    private Long serviceId;

}
