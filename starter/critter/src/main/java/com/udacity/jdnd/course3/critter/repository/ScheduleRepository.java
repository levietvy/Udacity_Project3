package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query(value = "select s from Schedule s where :petId MEMBER OF s.petList")
    List<Schedule> getSchedulesByPetId(@Param("petId")Long petId);

    @Query(value = "select s from Schedule s where :employeeId MEMBER OF s.employees")
    List<Schedule> getSchedulesByEmployeeId(@Param("employeeId")Long employeeId);
}
