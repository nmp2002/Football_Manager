package com.ttisv.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.ttisv.bean.Role;
import com.ttisv.dao.RoleDao;

@Repository
public class RoleDaoImpl extends BaseDaoImpl<Role> implements RoleDao {

	@SuppressWarnings("unchecked")
	@Override
	public Role findByName(String name) {
		Role rs = null;
		Session session = this.getCurrentSession();
		try {
			Query query = session.createQuery("SELECT fg FROM Role fg WHERE name =:name", Role.class);
			query.setParameter("name", name);
			List<Role> lst = query.getResultList();
			if (lst != null && lst.size() > 0) {
				return lst.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
}
