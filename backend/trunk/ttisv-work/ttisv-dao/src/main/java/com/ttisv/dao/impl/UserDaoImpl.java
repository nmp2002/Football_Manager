package com.ttisv.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;

import org.springframework.stereotype.Repository;

import com.ttisv.bean.User;
import com.ttisv.dao.UserDao;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

	@Override
	public User findByUsername(String username) {
		User rs = null;
		Session session = this.getCurrentSession();
		try {
			Query query = session.createQuery("SELECT fg FROM User fg WHERE username =:username", User.class);
			query.setParameter("username", username);

			List<User> lst = query.getResultList();
			if (lst != null && lst.size() > 0) {
				return lst.get(0);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	@Override
	public Boolean existsByUsername(String username) {
		int count = 0;
		try {
			Session session = this.getCurrentSession(); 

			String sql = "select count (*) FROM User fg WHERE username =:username";

			Query q = session.createQuery(sql).setParameter("username", username);
			count = ((Long) q.getSingleResult()).intValue();
		} catch (Exception e) {
			e.printStackTrace();

		}

		return count > 0 ? true : false;
	}

	@Override
	public Boolean existsByEmail(String email) {
		int count = 0;
		try {
			Session session = this.getCurrentSession();

			String sql = "select count (*) FROM User fg WHERE email =:email";

			Query q = session.createQuery(sql).setParameter("email", email);
			count = ((Long) q.getSingleResult()).intValue();
		} catch (Exception e) {
			e.printStackTrace();

		}

		return count > 0 ? true : false;
	}

}
