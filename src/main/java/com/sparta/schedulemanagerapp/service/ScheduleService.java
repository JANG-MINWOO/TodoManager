package com.sparta.schedulemanagerapp.service;

import com.sparta.schedulemanagerapp.dto.ScheduleRequestDto;
import com.sparta.schedulemanagerapp.dto.ScheduleResponseDto;
import com.sparta.schedulemanagerapp.entity.Schedule;
import com.sparta.schedulemanagerapp.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public void createSchedule(ScheduleRequestDto scheduleRequestDto) {
        Schedule schedule = new Schedule();
        schedule.setName(scheduleRequestDto.getName());
        schedule.setTask(scheduleRequestDto.getTask());
        schedule.setPassword(scheduleRequestDto.getPassword());
        schedule.setCreatedDate(LocalDateTime.now());
        schedule.setUpdatedDate(LocalDateTime.now());
        scheduleRepository.createSchedule(schedule);
    }

    public List<ScheduleResponseDto> getAllSchedules() {
        return scheduleRepository.findAll().stream()
                .map(schedule -> new ScheduleResponseDto(schedule.getId(), schedule.getName(),
                        schedule.getTask(), schedule.getCreatedDate(),
                        schedule.getUpdatedDate()))
                .collect(Collectors.toList());
    }

    public ScheduleResponseDto getScheduleById(Long id) {
        Schedule schedule = scheduleRepository.findById(id);
        return new ScheduleResponseDto(schedule.getId(), schedule.getName(),
                schedule.getTask(), schedule.getCreatedDate(),
                schedule.getUpdatedDate());
    }

    public void updateSchedule(Long id, ScheduleRequestDto scheduleRequestDto) {
        Schedule schedule = new Schedule();
        schedule.setId(id);
        schedule.setTask(scheduleRequestDto.getTask());
        schedule.setUpdatedDate(LocalDateTime.now());
        scheduleRepository.updateSchedule(schedule);
    }

    public void deleteSchedule(Long id) {
        scheduleRepository.deleteSchedule(id);
    }

    public List<ScheduleResponseDto> searchByName(String name) {
        return scheduleRepository.findByName(name).stream()
                .map(schedule -> new ScheduleResponseDto(schedule.getId(), schedule.getName(),
                        schedule.getTask(), schedule.getCreatedDate(),
                        schedule.getUpdatedDate()))
                .collect(Collectors.toList());
    }
}
