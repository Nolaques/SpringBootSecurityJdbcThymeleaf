package com.company.sbsecurityjdbcthymeleaf.dao;


import com.company.sbsecurityjdbcthymeleaf.mapper.AppUserMapper;
import com.company.sbsecurityjdbcthymeleaf.model.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@Repository
@Transactional
public class AppUserDAO extends JdbcDaoSupport{

    @Autowired
    public AppUserDAO(@Qualifier("dataSource") DataSource dataSource){
        this.setDataSource(dataSource);
    }

    public AppUser findUserAccount(String userName){
        //select ... from App_user u where u.User_Name = ?
        String sql = AppUserMapper.BASE_SQL + " where u.User_Name = ? ";

        Object[] params = new Object[]{userName};
        AppUserMapper mapper = new AppUserMapper();

        try {
            AppUser userInfo = this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return userInfo;
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }
}
