package com.company.sbsecurityjdbcthymeleaf.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;

@Repository
@Transactional
public class AppRoleDAO extends JdbcDaoSupport{

    @Autowired
    public AppRoleDAO(@Qualifier("dataSource") DataSource dataSource){
        this.setDataSource(dataSource);
    }

    public List<String> getRoleNames(Long userId){
        String sql = "Select r.Role_Name "
                + " from User_Role ur, App_Role r"
                + " where ur.Role_id = r.Role_Id and ur.User_Id = ? ";

        Object[] params = new Object[]{userId};

        List<String> roles = this.getJdbcTemplate().queryForList(sql, params, String.class);
        return roles;
    }
}
