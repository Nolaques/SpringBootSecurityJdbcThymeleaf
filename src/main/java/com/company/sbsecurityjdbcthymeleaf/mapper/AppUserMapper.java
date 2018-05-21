package com.company.sbsecurityjdbcthymeleaf.mapper;

import com.company.sbsecurityjdbcthymeleaf.model.AppUser;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AppUserMapper implements RowMapper<AppUser>{

    public static final String BASE_SQL
            = "Select u.User_Id, u.User_Name, u.Encrypted_Password from App_User u";



    @Override
    public AppUser mapRow(ResultSet rs, int rowNum) throws SQLException {

        Long userId = rs.getLong("User_Id");
        String userName = rs.getString("User_Name");
        String encryptedPassword = rs.getString("Encrypted_Password");

        return new AppUser(userId, userName, encryptedPassword);
    }
}
