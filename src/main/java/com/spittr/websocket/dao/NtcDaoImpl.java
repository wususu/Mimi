package com.spittr.websocket.dao;

import java.util.List;

import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spittr.dao.BaseDaoHibernate5;

import com.spittr.websocket.model.*;

@Repository
public class NtcDaoImpl extends BaseDaoHibernate5<NtcBody> implements NtcDao{

	@Autowired
	protected SessionFactory sessionFactory;

	@Override
	public NtcMLikee getNtcMLikee(long id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(NtcMLikee.class)
				.add(Restrictions.eq("id", id));
		Object object = criteria.uniqueResult();
		
		return object==null?null:(NtcMLikee)object;
	}	
	
	@Override
	public NtcCLikee getNtcCLikee(long id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(NtcCLikee.class)
				.add(Restrictions.eq("id", id));
		Object object = criteria.uniqueResult();
		
		return object==null?null:(NtcCLikee)object;
	}	
	
	@Override
	public NtcCmmnt getNtcCmmnt(long id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(NtcCmmnt.class)
				.add(Restrictions.eq("id", id));
		Object object = criteria.uniqueResult();
		
		return object==null?null:(NtcCmmnt)object;
	}	
	

	@Override
	public NtcRCmmnt getNtcRCmmnt(long id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(NtcRCmmnt.class)
				.add(Restrictions.eq("id", id));
		Object object = criteria.uniqueResult();
		
		return object==null?null:(NtcRCmmnt)object;
	}

	
	@Override
	public NtcMLikee getNtcMLikee(Long mid, Long lkUid) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(NtcMLikee.class)
				.add(Restrictions.eq("mid", mid))
				.add(Restrictions.eq("lkUid", lkUid));
		Object object = criteria.uniqueResult();
		
		return object ==null?null:(NtcMLikee)object;
	}
	
	@Override
	public NtcCLikee getNtcCLikee(Long cid, Long lkUid) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(NtcCLikee.class)
				.add(Restrictions.eq("cid", cid))
				.add(Restrictions.eq("lkUid", lkUid));
		Object object = criteria.uniqueResult();
		
		return object ==null?null:(NtcCLikee)object;
	}
	
	@Override
	public NtcCmmnt getNtcCmmnt(Long mid, Long cid) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(NtcCmmnt.class)
				.add(Restrictions.eq("cid", cid))
				.add(Restrictions.eq("mid", mid));
		Object object = criteria.uniqueResult();
		
		return object ==null?null:(NtcCmmnt)object;
	}

	@Override
	public NtcRCmmnt getNtcRCmmnt(Long cid, Long rcid) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(NtcRCmmnt.class)
				.add(Restrictions.eq("cid", cid))
				.add(Restrictions.eq("rcid", rcid));
		Object object = criteria.uniqueResult();
		
		return object ==null?null:(NtcRCmmnt)object;
	}

	@Override
	public List<NtcMLikee> getNtcMLikeeNotRcv(long mUid) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(NtcMLikee.class)
				.add(Restrictions.eq("mUid", mUid))
				.add(Restrictions.eq("isRecived", false));
		@SuppressWarnings("unchecked")
		List<NtcMLikee> ntcMLikees = (List<NtcMLikee>)criteria.list();	
		return ntcMLikees;
	}

	@Override
	public List<NtcCLikee> getNtcCLikeeNotRcv(long cUid) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(NtcCLikee.class)
				.add(Restrictions.eq("cUid", cUid))
				.add(Restrictions.eq("isRecived", false));
		@SuppressWarnings("unchecked")
		List<NtcCLikee> ntcCLikees = (List<NtcCLikee>)criteria.list();
		return ntcCLikees;
	}

	@Override
	public List<NtcCmmnt> getNtcCmmntNotRcv(long mUid) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(NtcCmmnt.class)
				.add(Restrictions.eq("mUid", mUid))
				.add(Restrictions.eq("isRecived", false));	
	@SuppressWarnings("unchecked")
	List<NtcCmmnt> ntcCmmnts = (List<NtcCmmnt>)criteria.list();
		return ntcCmmnts;
	}

	@Override
	public List<NtcRCmmnt> getNtcRCmmntNotRcv(long rcUid) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(NtcRCmmnt.class)
				.add(Restrictions.eq("rcUid", rcUid))
				.add(Restrictions.eq("isRecived", false));
		@SuppressWarnings("unchecked")
		List<NtcRCmmnt> ntcRCmmnts = (List<NtcRCmmnt>)criteria.list();
		return ntcRCmmnts;
	}
	
}
