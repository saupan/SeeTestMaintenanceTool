package com.smt.daoImpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.smt.dao.GlobalDAO;
import com.smt.domain.AdminConfigDetails;
import com.smt.domain.UserDetails;
import com.smt.utils.UserPOJO;

@Repository
public class GlobalDAOImpl implements GlobalDAO{

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void saveAdminConfigDetails(AdminConfigDetails adminConfigDetails) {
		Session session = entityManager.unwrap(Session.class);
		try {
			session.update(adminConfigDetails);
		} catch(HibernateException he) {
			he.printStackTrace();
		} finally {
			session.flush();
		}
	}

	@Override
	public AdminConfigDetails getMailConfigData(UserPOJO userPOJO) {
		AdminConfigDetails adminConfigDetails = null;
		Session session = entityManager.unwrap(Session.class);
		StringBuilder hql = new StringBuilder();
		Query query = null;
		try {
			hql.append("from AdminConfigDetails where userId=:userId");
			query = session.createQuery(hql.toString());
			query.setString("userId", userPOJO.getId());
			adminConfigDetails = (AdminConfigDetails)query.uniqueResult();
		} catch(HibernateException 	he) {
			he.printStackTrace();
		} finally {
			session.flush();
			query = null;
			hql = null;
		}
		return adminConfigDetails;
	}
}
