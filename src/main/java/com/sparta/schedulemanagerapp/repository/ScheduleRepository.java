package com.sparta.schedulemanagerapp.repository;

import com.sparta.schedulemanagerapp.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ScheduleRepository {
    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createSchedule(Schedule schedule) {
        String sql = "INSERT INTO schedules (name, task, password, created_date, updated_date) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, schedule.getName(), schedule.getTask(), schedule.getPassword(),
                schedule.getCreatedDate(), schedule.getUpdatedDate());
    }

    public List<Schedule> findAll() {
        String sql = "SELECT * FROM schedules ORDER BY updated_date DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Schedule(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("task"),
                rs.getString("password"),
                rs.getTimestamp("created_date").toLocalDateTime(),
                rs.getTimestamp("updated_date").toLocalDateTime()
        ));
    }

    public Schedule findById(Long id) {
        String sql = "SELECT * FROM schedules WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> new Schedule(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("task"),
                rs.getString("password"),
                rs.getTimestamp("created_date").toLocalDateTime(),
                rs.getTimestamp("updated_date").toLocalDateTime()
        ));
    }

    public void updateSchedule(Schedule schedule) {
        String sql = "UPDATE schedules SET task = ?, updated_date = ? WHERE id = ?";
        jdbcTemplate.update(sql, schedule.getTask(), LocalDateTime.now(), schedule.getId());
    }

    public void deleteSchedule(Long id) {
        String sql = "DELETE FROM schedules WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<Schedule> findByName(String name) {
        String sql = "SELECT * FROM schedules WHERE name = ?";
        return jdbcTemplate.query(sql, new Object[]{name}, (rs, rowNum) -> new Schedule(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("task"),
                rs.getString("password"),
                rs.getTimestamp("created_date").toLocalDateTime(),
                rs.getTimestamp("updated_date").toLocalDateTime()
        ));
    }
}
