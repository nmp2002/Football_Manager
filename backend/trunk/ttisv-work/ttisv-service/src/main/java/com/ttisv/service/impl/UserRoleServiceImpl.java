package com.ttisv.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ttisv.bean.UserRole;
import com.ttisv.dao.UserRoleDao;
import com.ttisv.service.UserRoleService;
@Service
@Transactional
public class UserRoleServiceImpl implements UserRoleService{
	@Autowired
	UserRoleDao userRoleDao;
	@Override
	public void saveOrUpdate(UserRole o) {
		// TODO Auto-generated method stub
		userRoleDao.saveOrUpdate(o);
	}

}
