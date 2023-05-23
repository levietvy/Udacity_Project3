package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.Enum.EmployeeSkill;
import com.udacity.jdnd.course3.critter.dto.EmployeeDTO;
import com.udacity.jdnd.course3.critter.dto.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;
    
    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO){
        Employee tempEmployee = employeeRepository.save(convertDtoToEntity(employeeDTO));
        return convertEntityToDto(tempEmployee);
    }

    public Employee getEmployeeById(Long id){
        return employeeRepository.getById(id);
    }

    public List<Employee> getEmployeesByIDs(List<Long> ids){
        List<Employee> employeeList = new ArrayList<>();
        for (long id : ids){
            employeeList.add(employeeRepository.getById(id));
        }
        return employeeList;
    }

    public List<EmployeeDTO> findEmployeeForService(EmployeeRequestDTO employeeRequestDTO){
        DayOfWeek dayOfWeek = employeeRequestDTO.getDate().getDayOfWeek();
        List<Employee> employeeList = employeeRepository.findEmployeeByDaysAvailable(dayOfWeek);
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
        int skillCount;
        for (Employee employee : employeeList) {
            skillCount = 0;
            for (EmployeeSkill skill : employeeRequestDTO.getSkills()){
                if (employee.getSkills().contains(skill)) {
                    skillCount++;
                }
            }
            if (skillCount == employeeRequestDTO.getSkills().size()) {
                employeeDTOList.add(convertEntityToDto(employee));
            }
        }
        return employeeDTOList;
    }

    public Employee convertDtoToEntity(EmployeeDTO employeeDto){
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDto, employee);
        return employee;
    }

    public EmployeeDTO convertEntityToDto(Employee employee){
        EmployeeDTO employeeDto = new EmployeeDTO();
        BeanUtils.copyProperties(employee,employeeDto);
        return employeeDto;
    }
}
