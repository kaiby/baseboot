package cn.ctyun.core.base.dao;

import java.io.Serializable;

import org.springframework.dao.DataAccessException;

/**
 * Created by zhouxiaobo on 2017/5/23.
 */

public interface GenericDao<E, PK extends Serializable> {

	E getById(PK var1) throws DataAccessException;

	int deleteById(PK var1) throws DataAccessException;

	//int save(E var1) throws DataAccessException;
	
	int insert(E var1) throws DataAccessException;

	int update(E var1) throws DataAccessException;

}
