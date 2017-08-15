package com.spittr.message.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spittr.dao.BaseDaoHibernate5;
import com.spittr.message.model.Message;
import com.spittr.tools.page.Page;


@Repository
public class MessageDaoImpl extends BaseDaoHibernate5<Message> implements MessageDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<Message> get(Page page){
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Message.class)
				.setMaxResults(page.getItemPerPage())
				.setFirstResult((int)page.getFirst())
				.add(Restrictions.eq("isDelete", false))
				.addOrder(Order.desc("tmCreated"));
		@SuppressWarnings("unchecked")
		List<Message> messageLIst = (List<Message>)criteria.list();
		return messageLIst;
	}
	
	@Override
	public Long count(){
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Message.class)
				.add(Restrictions.eq("isDelete", false))
				.setProjection(Projections.rowCount());
		Long count = (Long)criteria.uniqueResult();
		return count;
	}

	@Override
	public List<Message> getByLid(Page page, Long lid) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Message.class)
				.add(Restrictions.eq("lid", lid))
				.add(Restrictions.eq("isDelete", false))
				.addOrder(Order.desc("tmCreated"));
		@SuppressWarnings("unchecked")
		List<Message> list = criteria.list();
		return list;
	}

	@Override
	public Long coutByLid(Long lid) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Message.class)
				.add(Restrictions.eq("lid", lid))
				.add(Restrictions.eq("isDelete", false))
				.setProjection(Projections.rowCount());
		Long count = (long)criteria.uniqueResult();
		return count;
	}
	
}
	
