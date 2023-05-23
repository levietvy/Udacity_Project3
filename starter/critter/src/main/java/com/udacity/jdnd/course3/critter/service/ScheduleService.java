package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ScheduleService {
    
    @Autowired
    ScheduleRepository scheduleRepository;
    
    public ScheduleDTO addSchedule(ScheduleDTO scheduleDTO){
        Schedule schedule = scheduleRepository.save(convertDtoToEntity(scheduleDTO));
        return convertEntityToDto(schedule);
    }

    public Schedule convertDtoToEntity(ScheduleDTO scheduleDto){
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDto, schedule);
        return schedule;
    }

    public ScheduleDTO convertEntityToDto(Schedule schedule){
        ScheduleDTO scheduleDto = new ScheduleDTO();
        BeanUtils.copyProperties(schedule,scheduleDto);
        return scheduleDto;
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
