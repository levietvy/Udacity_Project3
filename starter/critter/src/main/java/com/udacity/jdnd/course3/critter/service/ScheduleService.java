package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ScheduleService {
    
    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    PetService petService;

    public ScheduleDTO addSchedule(ScheduleDTO scheduleDTO){
        Schedule schedule = scheduleRepository.save(convertDtoToEntity(scheduleDTO));
        return convertEntityToDto(schedule);
    }

    public List<ScheduleDTO> getAllSchedule(){
        List<Schedule> scheduleList = scheduleRepository.findAll();
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();
        for (Schedule schedule : scheduleList) {
            scheduleDTOList.add(convertEntityToDto(schedule));
        }
        return scheduleDTOList;
    }

    public Schedule convertDtoToEntity(ScheduleDTO scheduleDto){
        Schedule schedule = new Schedule();

        BeanUtils.copyProperties(scheduleDto, schedule);

        schedule.setDate(scheduleDto.getDate());
        schedule.setSetEmployeeSkill(scheduleDto.getActivities());
        schedule.setEmployees(employeeService.getEmployeesByIDs(scheduleDto.getEmployeeIds()));
        schedule.setPetList(petService.getPetsByIds(scheduleDto.getPetIds()));

        return schedule;
    }

    public ScheduleDTO convertEntityToDto(Schedule schedule){
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);

        List<Long> listOfEmployeeIDs = schedule.getEmployees()
                .stream()
                .map(Employee::getId)
                .collect(Collectors.toList());

        List<Long> listOfPetIDs = schedule.getPetList()
                .stream()
                .map(Pet::getId)
                .collect(Collectors.toList());

        scheduleDTO.setDate(schedule.getDate());
        scheduleDTO.setActivities(schedule.getSetEmployeeSkill());
        scheduleDTO.setEmployeeIds(listOfEmployeeIDs);
        scheduleDTO.setPetIds(listOfPetIDs);

        return scheduleDTO;
    }

    public List<ScheduleDTO> getScheduleByPetId(Long petId){
        List<Schedule> scheduleList = scheduleRepository.getSchedulesByPetId(petId);
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();
        for (Schedule schedule : scheduleList) {
            scheduleDTOList.add(convertEntityToDto(schedule));
        }
        return scheduleDTOList;
    }

    public List<ScheduleDTO> getScheduleByEmployeeId(Long employeeId){
        List<Schedule> scheduleList = scheduleRepository.getSchedulesByEmployeeId(employeeId);
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();
        for (Schedule schedule : scheduleList) {
            scheduleDTOList.add(convertEntityToDto(schedule));
        }
        return scheduleDTOList;
    }
}
