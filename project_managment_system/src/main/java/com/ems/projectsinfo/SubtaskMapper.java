package com.ems.projectsinfo;

/**
 * Created by Martynas on 5/6/2017.
 */

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Martynas on 5/6/2017.
 */
public class SubtaskMapper implements RowMapper<Subtask> {
    public Subtask mapRow(ResultSet rs, int rowNum) throws SQLException {
        Subtask subtask = new Subtask();
        subtask.setSubtask_id(null);
        subtask.setDescription(rs.getString("description"));
        subtask.setStatus(rs.getInt("status"));
        subtask.setFk_Task(rs.getInt("fk_Task"));
        return subtask;
    }
}