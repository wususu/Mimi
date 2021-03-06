package com.spittr.user.dao;

import java.util.*;

import com.spittr.dao.BaseDao;
import com.spittr.user.model.*;

public interface UserRelationshipDao extends BaseDao<UserRelationship>{

	UserRelationship get(long id);
	
	UserRelationship get(User mainUser, User objectUser);
	
	List<UserRelationship> getObjectUserRelationships(User objectUser, Date bfTime, int limit);
	
	List<UserRelationship> getMainUserRelationships(User mainUser, Date bfTime, int limit);
	
	List<UserRelationship> getMainUserRelationships(User mainUser);
}
