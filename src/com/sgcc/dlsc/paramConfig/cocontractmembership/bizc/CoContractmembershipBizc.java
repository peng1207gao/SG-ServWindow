package com.sgcc.dlsc.paramConfig.cocontractmembership.bizc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.sgcc.dlsc.commons.util.ToolsSys;
import com.sgcc.dlsc.entity.po.CoContractmembership;
import com.sgcc.dlsc.paramConfig.cocontractmembership.vo.CoContractmembershipTransfer;
import com.sgcc.dlsc.paramConfig.cocontractmembership.vo.ContractmembershipRequestConditionVO;
import com.sgcc.uap.persistence.IHibernateDao;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.utils.RestUtils;


/**
 * 合同准入成员信息 {@link CoContractmembership} 逻辑执行组件
 * 
 * @author djdeng
 *
 */
public class CoContractmembershipBizc implements ICoContractmembershipBizc {
	
	/**
	 * 数据持久化操作对象
	 */
	@Autowired
	private IHibernateDao hibernateDao;

	
	/**
	 * 合同准入成员信息保存
	 * 
	 * 先根据合同类型，合同角色类型，场景Id 删除旧数据
	 * 
	 * 再保存用户提交的新数据
	 */
	public void saveOrUpdate(List<CoContractmembership> contractmemberships) {
		
		//删除旧数据
		if (contractmemberships != null && !contractmemberships.isEmpty()) {
			deleteOldContractmemberships(contractmemberships.get(0));
		}
		
		//保存用户提交的新数据
		hibernateDao.saveAllObjectWithoutCache(contractmemberships);
		
	}
	
	/**
	 * 删除旧数据
	 * 
	 * @param membership  合同准入成员信息
	 */
	private void deleteOldContractmemberships(CoContractmembership membership) {
		
		String marketId = membership.getMarketid();
		String contractType = membership.getContracttypeid();
		String contractroleType = membership.getDisplaytype().toString();
		
		//
		delOldData(contractType, marketId, contractroleType);
	}

	/**
	 * 根据合同类型和合同角色查询已经关联的市场成员的Id主键集合和名称集合，主要做前端表单的展示用
	 * 
	 * @param contractroleType 合同角色
	 * 
	 * @param contractType 合同类型
	 * 
	 * @param marketId 场景Id
	 * 
	 * @return 返回市场成员的Id主键集合和名称集合封装的Map对象
	 */
	public Map<String, String> getSelectedMemberships(String contractroleType,
			String contractType, String marketId) {
		StringBuffer sql = new StringBuffer();
		List list = new ArrayList();
		sql.append("select c.participantid, c.displaytype, c.guid, b.participantname");
		sql.append(" from co_contractmembership c");
		sql.append(" left join BA_MarketParticipant b");
		sql.append(" on c.participantid = b.participantid");
		sql.append(" where c.contracttypeid =?");
		sql.append(" and c.marketid = ?");
		sql.append(" and c.displaytype = ?");
		if(ToolsSys.isnull(contractroleType)){
			Object[] params = new Object[]{contractType, marketId, Integer.parseInt(contractroleType)};
			list = hibernateDao.executeSqlQuery(sql.toString(), params);
		}
		
		Map<String, String> values = buildSelectedMemberships(list);
		return values;
	}

	/**
	 * 将从数据库中加载出来的市场成员集合封装成一个Map对象，
	 * 
	 * 在该对象中包括两个值，一个是ids 使用逗号隔开的主键集合，另个是names 使用逗号隔开的名称集合。
	 * 
	 * @param list 从数据库中查询出的原始数据
	 * 
	 * @return  封装后的数据对象
	 */
	private Map<String, String> buildSelectedMemberships(List<Object[]> list) {
		StringBuilder participantids = new StringBuilder();
		StringBuilder participantnames = new StringBuilder();
		
		Map<String, String> values = new HashMap<String, String>();
		
		if (list == null || list.isEmpty()) {
			return values;
		}
		
		for (Object[] objs : list) {
			participantids.append(",").append(objs[0]);
			participantnames.append(",").append(objs[3]);
		}
		
		values.put("ids", participantids.toString().substring(1));
		values.put("names", participantnames.toString().substring(1));
		return values;
	}

	/**
	 * 根据条件删除旧的准入成员信息
	 * @description 方法描述
	 * @param marketId
	 * @param displayType
	 * @param coType
	 * @return
	 * @throws SQLException
	 * @author wanshulu
	 * @date 2013-4-27
	 */
	public boolean delOldData(String cotypeId, String marketId ,String displaytype) {
		
		String sql = "DELETE FROM CO_CONTRACTMEMBERSHIP C WHERE C.CONTRACTTYPEID = '"
		+cotypeId+"' AND C.MARKETID = '"+marketId+"' AND C.DISPLAYTYPE = '"+displaytype+"'";

		hibernateDao.executeSqlUpdate(sql);
		return true;
	}

	/**
	 * 查询市场成员集合
	 * 
	 * 根据参数 "type"的值执行不同的查询，
	 * 
	 * 1. type=0  查询未被选中的市场成员
	 * 
	 * 2. type=1 查询已被选中的市场成员
	 */
	public QueryResultObject queryMembership(Map<String, String>  condition) {
		
		//查询未被选中的市场成员
		String type = condition.get("type");
		if ("0".equals(type)) {
			return getNoSelectedMembership(condition);
			
		//查询已被选中的市场成员
		} else if ("1".equals(type)) {
			return getSelectedMembership(condition);
		}
		
		return null;
	}

	/**
	 * 根据合同类型，合同角色，场景Id查询与合同类型关联的市场成员集合
	 * 
	 * @param condition 查询条件
	 * 
	 * @return 未与合同类型关联的市场成员集合
	 */
	private QueryResultObject getSelectedMembership(
			Map<String, String> condition) {
		//
		String marketId = condition.get("marketId");
		String coType = condition.get("contractType");
		String displayType = condition.get("contractroleType");
		
		StringBuffer sql = new StringBuffer();
		
		sql.append(" select distinct c.participantid,m.participantname " +
				"from CO_CONTRACTMEMBERSHIP c " +
				"left join BA_MarketParticipant m " +
				"on c.participantid=m.participantid " +
				"where c.contracttypeid='").append(coType)
				.append("' and c.marketid='").append(marketId).
				append("' and c.displaytype='").append(displayType).append("' ");
		
		//执行数据库查询，封装对象，并返回
		List list = hibernateDao.executeSqlQuery(sql.toString());
		return RestUtils.wrappQueryResult(buildMemberships(list));
	}
	
	/**
	 * 将从数据库中查询出的市场成员的主键Id和名称封装成一个Map对象
	 * 
	 * @param list 从数据库中查询出的市场成员集合
	 * 
	 * @return 封装成Map对象的市场成员集合
	 */
	private List<Map<String, Object>> buildMemberships(List<Object[]> list) {
		List<Map<String, Object>> values = new ArrayList<Map<String,Object>>();
		if (list != null && !list.isEmpty()) {
			for (Object[] obj : list) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("participantid", obj[0]);
				map.put("participantname", obj[1]);
				values.add(map);
			}
		}
		return values;
	}

	/**
	 * 这个sql是一期的代码，我也不怎么看得明白，被偶重构了
	 * 
	 * 根据当前登录用户的场景ID和用户在界面中选择的共享市场ID，与其他一些查询条件，执行不同的sql查询
	 * 
	 * 具体的sql查询逻辑请查看具体方法说明，3q。
	 * 
	 * <p>
	 * 存在三种可能的情况：
	 * 
	 * 1. 共享市场ID为空，用户没有指定该查询过滤条件
	 * 
	 * 2. 当前登录用户的场景ID和共享市场ID不一样
	 * 
	 * 3. 当前登录用户的场景ID和共享市场ID一样的
	 * </p>
	 * 
	 * @param condition 查询条件，其中包括很多的参数，
	 * 					具体过滤参数可以参考 {@link ContractmembershipRequestConditionVO}
	 * 					和方法 {@link CoContractmembershipTransfer#buildRequestConditionVO(Map)}
	 * 
	 * @return 未与合同类型关联的市场成员集合
	 */
	private QueryResultObject getNoSelectedMembership(Map<String, String> condition) {
		
		//
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		ContractmembershipRequestConditionVO conditionVO = 
				CoContractmembershipTransfer.buildRequestConditionVO(condition);
		
		String marketId = conditionVO.getMarketId();
		String shareMarket = conditionVO.getShareMarket();
		
		/*
		 * 根据场景ID和共享市场ID之间的暧昧关系，构建sql查询语句
		 */
		if(StringUtils.isBlank(shareMarket)) { 	//共享市场为空
			buildMembershipSqlNoShareMarket(sql, params, conditionVO);
			
//		} else if(!marketId.equals(shareMarket)) {	//共享市场非空非本市场
//			buildMembershipSqlNoEqualShareMarket(sql, params, conditionVO);
			
		} else if(marketId.equals(shareMarket)) {	//共享市场为本市场
			buildMembershipSqlEqualShareMarket(sql, params, conditionVO);
		}

		//执行数据库查询，封装对象，并返回
		List list = hibernateDao.executeSqlQuery(sql.toString(), params.toArray());
		return RestUtils.wrappQueryResult(buildMemberships(list));
	}

	/**
	 * 构建sql查询语句
	 * 
	 * 查询市场成员，并排除已经与合同关联的市场成员，最后的再根据类型和名称过滤市场成员
	 * 
	 * 当然了，最后的查询结果中还包括了共享市场成员
	 * 
	 * @param sql	查询sql语句
	 * @param params	查询参数
	 * @param conditionVO	用户设置的过滤条件
	 */
	private void buildMembershipSqlNoShareMarket(StringBuffer sql, List<Object> params, 
			ContractmembershipRequestConditionVO conditionVO) {
		
		//查询市场成员，并排除已经与合同关联的市场成员，最后的再根据类型和名称过滤市场成员
		sql.append(" select m.participantid, m.participantname");
		sql.append(" from BA_MarketParticipant m");
		sql.append(" where m.marketid = ?");
		sql.append(" and m.participantid not in (");
		sql.append("   select distinct c.participantid from CO_CONTRACTMEMBERSHIP c");
		sql.append("   where c.contracttypeid = ? and c.marketid = ? and c.displaytype = ?");
		sql.append(" )");
		
		params.add(conditionVO.getMarketId());
		params.add(conditionVO.getContractType());
		params.add(conditionVO.getMarketId());
		params.add(conditionVO.getContractroleType());
		
		//
		if(StringUtils.isNotBlank(conditionVO.getParticipantType())) {
			sql.append(" and m.participantType = ?");
			params.add(conditionVO.getParticipantType());
		}
		if(StringUtils.isNotBlank(conditionVO.getParticipantName())) {
			sql.append(" and m.participantName like ?");
			params.add("%" + conditionVO.getParticipantName() + "%");
		}
		
		//？？共享市场成员
//		sql.append(" union all");
//		
//		sql.append(" select m.participantid, m.participantname");
//		sql.append(" from BA_MarketParticipant m");
//		sql.append(" where m.participantid in (");
//		sql.append("   select s.dataid from Ba_Marketparticipant_Share s");
//		sql.append("   where s.marketid = ? and s.smarketid <> ?");
//		sql.append(" )");
//		sql.append(" and  m.participantid not in (");
//		sql.append("   select distinct c.participantid from CO_CONTRACTMEMBERSHIP c");
//		sql.append("   where c.contracttypeid = ? and c.marketid = ? and c.displaytype = ?");
//		sql.append(" )");
		
		params.add(conditionVO.getMarketId());
		params.add(conditionVO.getMarketId());
		params.add(conditionVO.getContractType());
		params.add(conditionVO.getMarketId());
		params.add(conditionVO.getContractroleType());
		
		//
		if(StringUtils.isNotBlank(conditionVO.getParticipantType())) {
			sql.append(" and m.participantType = ?");
			params.add(conditionVO.getParticipantType());
		}
		if(StringUtils.isNotBlank(conditionVO.getParticipantName())) {
			sql.append(" and m.participantName like ?");
			params.add("%" + conditionVO.getParticipantName() + "%");
		}
		
		//
		if (StringUtils.isNotBlank(conditionVO.getCommercialType()) ||
				StringUtils.isNotBlank(conditionVO.getGeneratorClass())) {
			
			String tempSql = sql.toString();
			sql.delete(0, sql.length());
			sql.append(" select distinct c.participantid, c.participantname");
			sql.append(" from (" + tempSql + ") c");
			sql.append(" join BA_GENERATOR g on c.participantid = g.plantid");
			sql.append(" where 1=1");
			
			if (StringUtils.isNotBlank(conditionVO.getCommercialType())) {
				sql.append(" and g.commercialType = ?");
				params.add(conditionVO.getCommercialType());
			}
			if (StringUtils.isNotBlank(conditionVO.getGeneratorClass())) {
				sql.append(" and g.generatorClass = ?");
				params.add(conditionVO.getGeneratorClass());
			}
		}
	}
	
	/**
	 * 构建sql查询语句
	 * 
	 * 查询市场成员，并排除已经与合同关联的市场成员，最后的再根据类型和名称过滤市场成员
	 * 
	 * 场景ID与共享市场ID不想等
	 * 
	 * @param sql	查询sql语句
	 * @param params	查询参数
	 * @param conditionVO	用户设置的过滤条件
	 */
	private void buildMembershipSqlNoEqualShareMarket(StringBuffer sql, List<Object> params, 
			ContractmembershipRequestConditionVO conditionVO) {
		
		sql.append(" select m.participantid, m.participantname");
		sql.append(" from BA_MarketParticipant m");
		sql.append(" where m.participantid in (");
		sql.append("   select s.dataid from Ba_Marketparticipant_Share s");
		sql.append("   where s.marketid = ? and s.smarketid = ? and m.participantid not in (");
		sql.append("     select distinct c.participantid from CO_CONTRACTMEMBERSHIP c");
		sql.append("     where c.contracttypeid = ? and c.marketid = ? and c.displaytype = ?");
		sql.append("   )");
		sql.append(" )");
		
		params.add(conditionVO.getMarketId());
		params.add(conditionVO.getShareMarket());
		params.add(conditionVO.getContractType());
		params.add(conditionVO.getMarketId());
		params.add(conditionVO.getContractroleType());
		
		//
		if(StringUtils.isNotBlank(conditionVO.getParticipantType())) {
			sql.append(" and m.participantType = ?");
			params.add(conditionVO.getParticipantType());
		}
		if(StringUtils.isNotBlank(conditionVO.getParticipantName())) {
			sql.append(" and m.participantName like ?");
			params.add("%" + conditionVO.getParticipantName() + "%");
		}
		
		//
		if (StringUtils.isNotBlank(conditionVO.getCommercialType()) ||
				StringUtils.isNotBlank(conditionVO.getGeneratorClass())) {
			
			String tempSql = sql.toString();
			sql.delete(0, sql.length());
			sql.append(" select distinct c.participantid, c.participantname");
			sql.append(" from (" + tempSql + ") c");
			sql.append(" join BA_GENERATOR g on c.participantid = g.plantid");
			sql.append(" where 1=1");
			
			if (StringUtils.isNotBlank(conditionVO.getCommercialType())) {
				sql.append(" and g.commercialType = ?");
				params.add(conditionVO.getCommercialType());
			}
			if (StringUtils.isNotBlank(conditionVO.getGeneratorClass())) {
				sql.append(" and g.generatorClass = ?");
				params.add(conditionVO.getGeneratorClass());
			}
		}
	}
	
	/**
	 * 构建sql查询语句
	 * 
	 * 查询市场成员，并排除已经与合同关联的市场成员，最后的再根据类型和名称过滤市场成员
	 * 
	 * 场景ID与共享市场ID相恋了，唉真是的
	 * 
	 * @param sql	查询sql语句
	 * @param params	查询参数
	 * @param conditionVO	用户设置的过滤条件
	 */
	private void buildMembershipSqlEqualShareMarket(StringBuffer sql, List<Object> params, 
			ContractmembershipRequestConditionVO conditionVO) {
		
		sql.append(" select m.participantid, m.participantname");
		sql.append(" from BA_MarketParticipant m");
		sql.append(" where m.marketid = ? and m.participantid not in (");
		sql.append("   select distinct c.participantid from CO_CONTRACTMEMBERSHIP c");
		sql.append("   where c.contracttypeid = ? and c.marketid = ? and c.displaytype = ?");
		sql.append(" )");
		
		params.add(conditionVO.getMarketId());
		params.add(conditionVO.getContractType());
		params.add(conditionVO.getMarketId());
		params.add(conditionVO.getContractroleType());
		
		//
		if(StringUtils.isNotBlank(conditionVO.getParticipantType())) {
			sql.append(" and m.participantType = ?");
			params.add(conditionVO.getParticipantType());
		}
		if(StringUtils.isNotBlank(conditionVO.getParticipantName())) {
			sql.append(" and m.participantName like ?");
			params.add("%" + conditionVO.getParticipantName() + "%");
		}
		
		//
		if (StringUtils.isNotBlank(conditionVO.getCommercialType()) ||
				StringUtils.isNotBlank(conditionVO.getGeneratorClass())) {
			
			String tempSql = sql.toString();
			sql.delete(0, sql.length());
			
			sql.append(" select distinct c.participantid, c.participantname");
			sql.append(" from (" + tempSql + ") c");
			sql.append(" join BA_GENERATOR g on c.participantid = g.plantid");
			sql.append(" where 1=1");
			
			//商业类型：公用/自备
			if (StringUtils.isNotBlank(conditionVO.getCommercialType())) {
				sql.append(" and g.commercialType = ?");
				params.add(conditionVO.getCommercialType());
			}
			
			//机组容量等级
			if (StringUtils.isNotBlank(conditionVO.getGeneratorClass())) {
				sql.append(" and g.generatorClass = ?");
				params.add(conditionVO.getGeneratorClass());
			}
		}
	}
}
