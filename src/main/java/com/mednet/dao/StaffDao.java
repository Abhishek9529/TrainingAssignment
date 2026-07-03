package com.mednet.dao;
import org.springframework.stereotype.Repository;
import com.mednet.config.DBConnection;
import com.mednet.entity.Staff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
@Repository
public class StaffDao {

    public List<Staff> getAllStaff() {

        List<Staff> staffList = new ArrayList<>();

        String sql = "SELECT * FROM staff ORDER BY id";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Staff staff = new Staff();

                staff.setId(rs.getInt("id"));
                staff.setName(rs.getString("name"));
                staff.setCode(rs.getString("code"));
                staff.setUserType(rs.getString("user_type"));
                staff.setPhone(rs.getString("phone"));
                staff.setDepartment(rs.getString("department"));
                staff.setStatus(rs.getString("status"));
                staff.setJoiningDate(rs.getString("joining_date"));

                staffList.add(staff);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return staffList;
    }

}