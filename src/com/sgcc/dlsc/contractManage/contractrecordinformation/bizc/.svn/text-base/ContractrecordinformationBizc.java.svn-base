package com.sgcc.dlsc.contractManage.contractrecordinformation.bizc;

import java.math.BigDecimal;
import java.util.Date;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.sgcc.uap.persistence.IHibernateDao;
import com.sgcc.uap.persistence.criterion.QueryCriteria;

import com.sgcc.uap.bizc.sysbizc.datadictionary.IDataDictionaryBizC;
import com.sgcc.uap.rest.support.IDRequestObject;
import com.sgcc.uap.rest.support.QueryFilter;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;
import com.sgcc.uap.rest.utils.CrudUtils;
import com.sgcc.uap.rest.utils.RestUtils;

import com.sgcc.dlsc.commons.util.ComUtils;
import com.sgcc.dlsc.contractManage.coContractbackupInfo.vo.CoContractbackupinfoTransfer;
import com.sgcc.dlsc.contractManage.coContractbackupInfo.vo.CoContractbackupinfoVO;
import com.sgcc.dlsc.contractManage.contractrecordinformation.vo.ContractRecordInformationTransfer;
import com.sgcc.dlsc.contractManage.contractrecordinformation.vo.ContractRecordInformationVO;
import com.sgcc.dlsc.entity.po.CoContractbackupinfo;
import com.sgcc.dlsc.entity.po.CoContractbaseinfo;
import com.sgcc.dlsc.entity.po.ContractRecordInformation;
/**
 * 用户定义逻辑构件
 * 
 * @author Jingbo.Fu 
 * 
 */
public class ContractrecordinformationBizc implements IContractrecordinformationBizc{

	@Autowired
	private IHibernateDao hibernateDao;
	
	@Autowired
	private IDataDictionaryBizC dataDictionaryBizC;
	
	/**
	 * 查询合同备案信息
	 * @param queryCondition
	 * @author Jingbo.Fu
	 * @date 2014-02-11
	 * @return
	 */
	public QueryResultObject queryContractRecordInfo(RequestCondition queryCondition,String contractId) {

		QueryCriteria qc = new QueryCriteria();
		List<ContractRecordInformation> result = new ArrayList();
		int PageIndex  = Integer.valueOf(queryCondition.getPageIndex());
		int PageSize  = Integer.valueOf(queryCondition.getPageSize());
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT BK.CONTRACTID,BK.GUID,BGE.NAME AS STATUS,BK.BACKUPORG,BG.NAME AS CTYPE , ");
		sql.append(" BK.BACKUPPERSON,BK.BACKUPDATE,BK.CONTRACTNUM FROM CO_ContractBackupInfo BK");
		sql.append(" LEFT JOIN BA_MARKET BM ON BK.BACKUPORG = BM.MARKETCODE");
		sql.append(" LEFT JOIN BA_GENCODE BG ON BK.BACKUPTYPE = BG.VALUE AND BG.TYPE = '71'");
		sql.append(" LEFT JOIN BA_GENCODE BGE ON BK.BACKUPSTATUS = BGE.VALUE AND BGE.TYPE = '26'");
		if(contractId!= null&&!contractId.equals(""))
		{
			sql.append(" WHERE BK.CONTRACTID ='"+contractId+"'");
		}
		List list = hibernateDao.executeSqlQuery(sql.toString(), PageIndex, PageSize);
		int count = 0;
		if(list!=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				Object[] obj = (Object[]) list.get(i);
				ContractRecordInformation vo = new ContractRecordInformation(); 
				vo.setContracrid(obj[0]==null?"":obj[0].toString());
				vo.setGuid(obj[1]==null?"":obj[1].toString());
				vo.setStatus(obj[2]==null?"":obj[2].toString());
				vo.setBackuporg(obj[3]==null?"":obj[3].toString());
				vo.setCtype(obj[4]==null?"":obj[4].toString());
				vo.setBackupperson(obj[5]==null?"":obj[5].toString());
				vo.setBackupdate(obj[6]==null?"":obj[6].toString());
				vo.setContractnum(obj[7]==null?"":obj[7].toString());
				result.add(vo);
			}
			String cont = "SELECT COUNT(*) FROM ("+sql.toString()+") con ";
			List list2=hibernateDao.executeSqlQuery(cont);
			if(list2!=null ){
				count = Integer.parseInt(list2.get(0).toString());
			}
		}
		return RestUtils.wrappQueryResult(result, count);
		
		
	}
	/**
	 * 签订合同信息
	 * @param contractId
	 * @return
	 * @author fujingbo
	 */
	public Map<String, List> getContractSignInfo(String contractId)
	{	
		StringBuffer sql = new StringBuffer();
		sql.append("select co.contractname, co.signState, co.signeddate, co.signedperson ");
		sql.append(" from Co_Contractbaseinfo co ");
		sql.append(" LEFT JOIN Ba_Gencode bg ON BG.VALUE = CO.SIGNSTATE  AND BG.TYPE = '24'");
		//sql.append(" LEFT JOIN SGSOTOWER.PUB_USERS U ON CO.SIGNEDPERSON = U.USER_ID");
		sql.append(" WHERE CO.CONTRACTID = ?");
		String sql3 = "select to_char(sysdate,'yyyy-mm-dd')  from  dual";
		List dateList = hibernateDao.executeSqlQuery(sql3);
		List list = hibernateDao.executeSqlQuery(sql.toString(),new Object[]{contractId});
		list.add(dateList.get(0));
		Map map = new HashMap();
		map.put("contract", list);
		StringBuffer sql2 = new StringBuffer();
		sql2.append(" SELECT BG.VALUE,BG.NAME FROM BA_GENCODE BG WHERE BG.TYPE='24' ORDER BY BG.VALUE ASC");
		List list2 = hibernateDao.executeSqlQuery(sql2.toString());
		map.put("dict", list2);
		return map;
		
	}
	/**
	 * 获取签订状态
	 * @param type
	 * @return
	 * @author fujingbo
	 */
	public List getTypeList() {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT BG.VALUE,BG.NAME FROM BA_GENCODE BG WHERE BG.TYPE='24' ORDER BY BG.VALUE ASC");
		List list = hibernateDao.executeSqlQuery(sql.toString());
		return list;
	}
	/**
	 * 保存合同签订信息
	 * @param contractId
	 * @param signPer
	 * @param signDate
	 * @param signStatus
	 * @author fujingbo
	 * @return
	 */
	public int saveSignContract(String contractId,String signPer,String signDate,int signStatus)
	{
		String sql = "update "+CoContractbaseinfo.class.getName()+" set signstate = ?,signeddate = ?,signedperson = ? where contractid =?";
		//ComUtils.getUtilDate()
		java.util.Date datenewDate = ComUtils.getUtilDate(signDate,"yyyy-MM-dd");
		hibernateDao.update(sql,new Object[]{new BigDecimal(signStatus) ,datenewDate,signPer,contractId});
		return 1;
	}
	/**
	 * 保存更新
	 * 
	 * @param list
	 */
	 public List<ContractRecordInformationVO> saveOrUpdate(List<Map> list) {
		 
		List<ContractRecordInformationVO> voList = new ArrayList<ContractRecordInformationVO>();
		List<CoContractbackupinfoVO> voList2 = new ArrayList<CoContractbackupinfoVO>();
		for (int i = 0; i < list.size(); i++) {
			Map map = list.get(i);
			map.remove("status");
			map.remove("ctype");
			String poName = CoContractbackupinfo.class.getName();
			if(map.containsKey("guid")){
				String id = (String)map.get("guid");
				ContractRecordInformationVO vo = update(map,poName,id);
				voList.add(vo);
			}else{
				CoContractbackupinfoVO tjfxMaplineidVO = save2(map);//CoContractbackupinfoVO
				voList2.add(tjfxMaplineidVO);
			}
		}
		return voList;
	 }
	 private CoContractbackupinfoVO save2(Map map) {
			
			CoContractbackupinfoVO coContractbackupinfoVo = new CoContractbackupinfoVO();
			try {
				Object backupDate = map.get("backupdate");
				Date datenewDate = ComUtils.getUtilDate(backupDate.toString(),"yyyy-MM-dd");
				map.remove("backupdate");
				map.put("backupdate", datenewDate);
				BeanUtils.populate(coContractbackupinfoVo, map);
			} catch (Exception e) {
			}
			CoContractbackupinfo coContractbackupinfo = CoContractbackupinfoTransfer.toPO(coContractbackupinfoVo);
			hibernateDao.saveOrUpdateObject(coContractbackupinfo);
			coContractbackupinfoVo = CoContractbackupinfoTransfer.toVO(coContractbackupinfo);
			if(map.containsKey("mxVirtualId")){
				coContractbackupinfoVo.setMxVirtualId(String.valueOf(map.get("mxVirtualId")));
			}
			return coContractbackupinfoVo;
		}
	//保存记录
	private ContractRecordInformationVO save(Map map) {
		
		ContractRecordInformationVO tjfxMaplineidVo = new ContractRecordInformationVO();
		try {
			BeanUtils.populate(tjfxMaplineidVo, map);
		} catch (Exception e) {
		}
		ContractRecordInformation tjfxMaplineid = ContractRecordInformationTransfer.toPO(tjfxMaplineidVo);
		List list = new ArrayList();
		String sql = "insert into com.sgcc.dlsc.entity.po.CoContractbackupinfo (CONTRACTID, BACKUPDATE, BACKUPPERSON," +
	             " BACKUPORG, BACKUPTYPE, CONTRACTNUM, BACKUPSTATUS, DESCRIPTION, GUID)" +
	             " values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		list.add(sql);
		list.add(map);
		
		Object[] objArray = CrudUtils.generateHql(CoContractbackupinfo.class, map, "guid");
		hibernateDao.update((String) objArray[0], (Object[]) objArray[1]);
		//		hibernateDao.saveOrUpdateObject(tjfxMaplineid);
//		tjfxMaplineidVo = ContractRecordInformationTransfer.toVO(tjfxMaplineid);
		if(map.containsKey("mxVirtualId")){
			tjfxMaplineidVo.setMxVirtualId(String.valueOf(map.get("mxVirtualId")));
		}
		return tjfxMaplineidVo;
	}
	
	//更新记录
	private ContractRecordInformationVO update(Map<String, ?> map,String poName,String id) {
		
		ContractRecordInformationVO tjfxMaplineidVo = new ContractRecordInformationVO();
		//更新操作
		try {
			BeanUtils.populate(tjfxMaplineidVo, map);
		} catch (Exception e) {
		}
		Object[] objArray = CrudUtils.generateHql(CoContractbackupinfo.class, map, "guid");
		hibernateDao.update((String) objArray[0], (Object[]) objArray[1]);
		return tjfxMaplineidVo;
	}
	
	/**
	 * 删除
	 * 
	 * @param idObject
	 */
	public void remove(IDRequestObject idObject) {
		String[] ids = idObject.getIds();
		for (int i = 0; i < ids.length; i++) {
			String id = ids[i];
			hibernateDao.update("delete from " + CoContractbackupinfo.class.getName() + " where guid = ?" ,new Object[]{id});
		}
	}
	
	/**
	 * 根据输入条件查询记录
	 * 
	 * @param queryCondition
	 * @return
	 */
	public QueryResultObject query(RequestCondition queryCondition) {

		QueryCriteria qc = new QueryCriteria();
		List<ContractRecordInformation> result = null;
		int count = 0;
		qc.addFrom(CoContractbackupinfo.class);
		if (queryCondition != null) {
			qc = wrapQuery(queryCondition, qc);
			count = getRecordCount(qc);
			qc = wrapPage(queryCondition, qc);
			result = hibernateDao.findAllByCriteria(qc);

		} else {
			result = hibernateDao.findAllByCriteria(qc);
			count = getRecordCount(qc);
		}
		return RestUtils.wrappQueryResult(result, count);
		
		
	}
	private QueryCriteria wrapQuery(RequestCondition queryCondition,
			QueryCriteria qc) {
		List<QueryFilter> wheres = queryCondition.getQueryFilter(ContractRecordInformationVO.class);
		if (wheres != null && wheres.size() > 0) {
			CrudUtils.addQCWhere(qc, wheres, CoContractbackupinfo.class.getName());

		}

		String orders = queryCondition.getSorter();
		if (orders != null) {
			qc.addOrder(orders.replaceAll("&", ","));
		}
		return qc;
	}
	
	private QueryCriteria wrapPage(RequestCondition queryCondition,
			QueryCriteria qc) {
		int pageIndex = 1, pageSize = 1;
		if (queryCondition.getPageIndex() != null
				&& queryCondition.getPageSize() != null) {
			pageIndex = Integer.valueOf(queryCondition.getPageIndex());
			pageSize = Integer.valueOf(queryCondition.getPageSize());
			qc.addPage(pageIndex, pageSize);
		}
		return qc;
	}
	
	/**
	 * 查询单条记录
	 * @param id
	 * @return QueryResultObject
	 */
	public QueryResultObject queryById(String id) {
		CoContractbackupinfo guid = (CoContractbackupinfo) hibernateDao.getObject(CoContractbackupinfo.class,id);
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT BK.CONTRACTID,BK.GUID,BGE.NAME AS STATUS,BK.BACKUPORG,BG.NAME AS CTYPE , ");
		sql.append(" BK.BACKUPPERSON,BK.BACKUPDATE,BK.CONTRACTNUM ,BK.backuptype,BK.backupstatus, BK.DESCRIPTION  FROM CO_ContractBackupInfo BK");
		sql.append(" LEFT JOIN BA_MARKET BM ON BK.BACKUPORG = BM.MARKETCODE");
		sql.append(" LEFT JOIN BA_GENCODE BG ON BK.BACKUPTYPE = BG.VALUE AND BG.TYPE = '71'");
		sql.append(" LEFT JOIN BA_GENCODE BGE ON BK.BACKUPSTATUS = BGE.VALUE AND BGE.TYPE = '26'");
		sql.append(" WHERE BK.guid ='"+id+"'");
		System.out.println(sql.toString());
		List list = hibernateDao.executeSqlQuery(sql.toString());
		ContractRecordInformation vo = new ContractRecordInformation(); 
		if(list!=null && list.size()>0)
		{
			Object[] obj = (Object[]) list.get(0);
			vo.setContracrid(obj[0]==null?"":obj[0].toString());
			vo.setGuid(obj[1]==null?"":obj[1].toString());
			vo.setStatus(obj[2]==null?"":obj[2].toString());
			vo.setBackuporg(obj[3]==null?"":obj[3].toString());
			vo.setCtype(obj[4]==null?"":obj[4].toString());
			vo.setBackupperson(obj[5]==null?"":obj[5].toString());
			vo.setBackupdate(obj[6]==null?"":obj[6].toString());
			vo.setContractnum(obj[7]==null?"":obj[7].toString());
			if(obj[8]!=null)
			{
				vo.setBackuptype(new BigDecimal(obj[8].toString()));
			}
			if(obj[9]!=null)
			{
				vo.setBackupstatus(new BigDecimal(obj[9]==null?"":obj[9].toString()));
			}
			vo.setDescription(obj[10]==null?"":obj[10].toString());
		}
		List<ContractRecordInformation> result = new ArrayList();
		result.add(vo);
		//ContractRecordInformationVO vo = (ContractRecordInformationVO)hibernateDao.executeSqlQuery(sql.toString()).get(0);
		//		ContractRecordInformationVO vo = null;
//		if (guid != null) {
//			vo = ContractRecordInformationTransfer.toVO(guid);
//		}
		
		return RestUtils.wrappQueryResult(result);
	}

	
	// 获取总记录数
	private int getRecordCount(QueryCriteria qc) {
		int count = 0;
		count = hibernateDao.findAllByCriteriaPageCount(qc,1);
		return count;
	}

	public void setHibernateDao(IHibernateDao hibernateDao) {
		this.hibernateDao = hibernateDao;
	}
}
