package com.sgcc.dlsc.contractManage.CoContransEnergyInfo.bizc;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.sgcc.uap.persistence.IHibernateDao;

import com.sgcc.uap.bizc.sysbizc.datadictionary.IDataDictionaryBizC;

import com.sgcc.uap.rest.support.DicItems;
import com.sgcc.uap.rest.support.IDRequestObject;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.utils.CrudUtils;
import com.sgcc.uap.rest.utils.RestUtils;

//引入po,vo,transefer
import com.sgcc.dlsc.contractManage.CoContransEnergyInfo.vo.CoContransenergyinfoTransfer;
import com.sgcc.dlsc.contractManage.CoContransEnergyInfo.vo.CoContransenergyinfoVO;
import com.sgcc.dlsc.entity.po.CoContransenergyinfo;
/**
 * 用户定义逻辑构件
 * 
 * @author xiabaike 
 *       
 */
public class CoContransEnergyInfoBizc implements ICoContransEnergyInfoBizc{

	@Autowired
	private IHibernateDao hibernateDao;
	
	@Autowired
	private IDataDictionaryBizC dataDictionaryBizC;	
	/**
	 * 
	 * @description 判断合同是否有对应的输电方
	 * @return
	 * @author gaoming
	 * @date 2013-1-16
	 */
	public boolean judgeExist(String contractId,String guid,String sdf){
		Boolean xx = true;
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT CCT.GUID,CCT.CONTRACTID,CCT.TRANSMISSION FROM CO_ConTransEnergyInfo CCT ");
		sql.append("  WHERE CCT.CONTRACTID = '").append(contractId).append("' AND CCT.TRANSMISSION = '").append(sdf).append("' ");
		if(guid!=null && !guid.equals("")){
			sql.append("  AND CCT.GUID<>'").append(guid).append("' ");
		}
		List list = hibernateDao.executeSqlQuery(sql.toString());//@查询信息
		if(list!=null && list.size()>0){
			xx = false;
		}
		return xx;
	}
	
	/**
	 * 保存更新
	 * 
	 * @param list
	 */
	 public List<CoContransenergyinfoVO> saveOrUpdate(List<Map> list) {
		 
		List<CoContransenergyinfoVO> voList = new ArrayList<CoContransenergyinfoVO>();
		for (int i = 0; i < list.size(); i++) {
			Map map = list.get(i);
			String poName = CoContransenergyinfo.class.getName();
			if(map.containsKey("guid")){
				String id = (String)map.get("guid");
				CoContransenergyinfoVO vo = update(map,poName,id);
				voList.add(vo);
			}else{
				CoContransenergyinfoVO coContransenergyinfoVO = save(map);
				voList.add(coContransenergyinfoVO);
			}
			System.out.println("--------map:"+map);
		}
		return voList;
	 }
	 
	//保存记录
	public CoContransenergyinfoVO save(Map map) {
		
		CoContransenergyinfoVO coContransenergyinfoVo = new CoContransenergyinfoVO();
		try {
			BeanUtils.populate(coContransenergyinfoVo, map);
		} catch (Exception e) {
		}
		CoContransenergyinfo coContransenergyinfo = CoContransenergyinfoTransfer.toPO(coContransenergyinfoVo);
		hibernateDao.saveOrUpdateObject(coContransenergyinfo);
		coContransenergyinfoVo = CoContransenergyinfoTransfer.toVO(coContransenergyinfo);
		if(map.containsKey("mxVirtualId")){
			coContransenergyinfoVo.setMxVirtualId(String.valueOf(map.get("mxVirtualId")));
		}
		return coContransenergyinfoVo;
	}
	
	//更新记录
	public CoContransenergyinfoVO update(Map<String, ?> map,String poName,String id) {
		
		CoContransenergyinfoVO coContransenergyinfoVo = new CoContransenergyinfoVO();
		//更新操作
		try {
			BeanUtils.populate(coContransenergyinfoVo, map);
		} catch (Exception e) {
		}
		Object[] objArray = CrudUtils.generateHql(CoContransenergyinfo.class, map, "guid");
		hibernateDao.update((String) objArray[0], (Object[]) objArray[1]);
		return coContransenergyinfoVo;
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
			String sql = "delete from CO_ConTransEnergyInfo where guid = '"+ id +"'";
			System.out.println("-----sql:"+sql);
			hibernateDao.update("delete from " + CoContransenergyinfo.class.getName() + " where guid = ?" ,new Object[]{id});
		}
	}
	
	/**
	 * 根据输入条件查询记录
	 * 
	 * @param queryCondition
	 * @return
	 */
	public QueryResultObject query(String contractId,int pageIndex,int pageSize) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT CCE.GUID,CCE.CONTRACTID,CCE.TRANSMISSION,BM.PARTICIPANTNAME,BM.LICENCECODE,BM.TAXCODE,BM.LINKMAN,BM.TELEPHONE ");
		sql.append(" FROM CO_ConTransEnergyInfo CCE LEFT JOIN BA_MARKETPARTICIPANT BM ");
		sql.append(" ON CCE.TRANSMISSION = BM.PARTICIPANTID AND BM.PARTICIPANTTYPE = '1' WHERE CCE.CONTRACTID = '").append(contractId).append("' ");
		sql.append(" order by NLSSORT(BM.PARTICIPANTNAME,'NLS_SORT=SCHINESE_PINYIN_M')");
		QueryResultObject result = new QueryResultObject();
		int size = 0;	//定义数据条数
		String sql2 = "SELECT COUNT(*) C FROM ( "+ sql.toString() + " ) T ";		//拼接查询数据条数的sql语句
		List list = hibernateDao.executeSqlQuery(sql2.toString());
		if(list.size() > 0){	//如果有查询结果，给数据条数变量赋值
			size = Integer.parseInt(list.get(0).toString());
		}
		result.setItemCount(size);	//给返回对象赋数据的数量
		list = hibernateDao.executeSqlQuery(sql.toString(),pageIndex,pageSize);	//查询分页的数据
		List<CoContransenergyinfoVO> voList = new ArrayList<CoContransenergyinfoVO>();		//返回的数据结果集
 		for(int i = 0;i < list.size();i++){	//遍历要展示的数据
 			Object[] objs = (Object[])list.get(i);	//得到第i条数据
 			CoContransenergyinfoVO vo = new CoContransenergyinfoVO();	//数据结果集中的一条数据
 			//给每个变量赋值
 			vo.setGuid(objs[0] == null ? "" : objs[0].toString());
 			vo.setContractId(objs[1] == null ? "" : objs[1].toString());
 			vo.setParticipantName(objs[3] == null ? "" : objs[3].toString());
 			vo.setLicenceCode(objs[4] == null ? "" : objs[4].toString());
 			vo.setTaxCode(objs[5] == null ? "" : objs[5].toString());
 			vo.setLinkMan(objs[6] == null ? "" : objs[6].toString());
 			vo.setTelephone(objs[7] == null ? "" : objs[7].toString());
 			voList.add(vo);	//把数据添加到结果集中
 		}
		result.setItems(voList);	//给返回对象赋分页查询数据
		return result;	//返回返回值
	}
	
	/**
	 * 查询单条记录
	 * @param id
	 * @return QueryResultObject
	 */
	public QueryResultObject queryById(String id, String marketId) {
		CoContransenergyinfo coContransenergyinfo = (CoContransenergyinfo) hibernateDao.getObject(CoContransenergyinfo.class,id);
		CoContransenergyinfoVO vo = null;
		if (coContransenergyinfo != null) {
			vo = CoContransenergyinfoTransfer.toVO(coContransenergyinfo);
		}
		return RestUtils.wrappQueryResult(vo).addDicItems(wrapDictListNew(marketId));
	}
	
	private List<DicItems> wrapDictListNew(String marketId) {// 将字典对象封装为list
		List<DicItems> dicts = new ArrayList<DicItems>();
	    dicts.add(translateFromFile("transmission",marketId));
	    return dicts;
	}
	// 从属性文件中查询字典
	private DicItems translateFromFile(String fieldName,String marketId) {
				List<Map<String, String>> list = new ArrayList();
				Object[] obj = {};
				StringBuffer sql = new StringBuffer();
				obj = new Object[]{"1",marketId};
				sql.append("SELECT T.PARTICIPANTID,T.PARTICIPANTNAME FROM BA_MARKETPARTICIPANT T WHERE T.PARTICIPANTTYPE=? AND T.MARKETID=?");
				
				List tlist = hibernateDao.executeSqlQuery(sql.toString(),obj);
				for(int i=0;i<tlist.size();i++){
					Map<String, String> map = new HashMap();
					Object[] robj = (Object[])tlist.get(i);
					String value = robj[0]==null?"":robj[0].toString();
					String name = robj[1]==null?"":robj[1].toString();
					map.put("value", value);
					map.put("text", name);
//					map.put(value, name);
					list.add(map);
				}
				DicItems dict = new DicItems();
				dict.setName(fieldName);
				dict.setValues(list);
				return dict;
			}

	public void setHibernateDao(IHibernateDao hibernateDao) {
		this.hibernateDao = hibernateDao;
	}

}
