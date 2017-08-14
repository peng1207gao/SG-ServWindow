package com.sgcc.dlsc.statesanalysis.comonthpowertrack.bizc;

import org.springframework.beans.factory.annotation.Autowired;

import com.sgcc.uap.persistence.IHibernateDao;

//引入po,vo,transefer
/**
 * 用户定义逻辑构件
 * 
 * @author lenovo 
 * 
 */
public class ComonthpowertrackBizc implements IComonthpowertrackBizc{

	@Autowired
	private IHibernateDao hibernateDao;
	

	

	public void setHibernateDao(IHibernateDao hibernateDao) {
		this.hibernateDao = hibernateDao;
	}
}
