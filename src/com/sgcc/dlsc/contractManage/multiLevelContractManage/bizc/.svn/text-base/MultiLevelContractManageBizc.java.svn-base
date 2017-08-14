package com.sgcc.dlsc.contractManage.multiLevelContractManage.bizc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.sgcc.dlsc.commons.bean.VIParamBean;
import com.sgcc.dlsc.commons.bean.VIReturnBean;
import com.sgcc.dlsc.commons.bizc.IComm_VerticalIntegration_Bizc;
import com.sgcc.dlsc.commons.util.DateUtil;
import com.sgcc.dlsc.commons.util.UserInfoUtil;
import com.sgcc.dlsc.constants.DataConstants;
import com.sgcc.dlsc.contractManage.IdeaConManage.vo.CoContractbaseinfoTransfer;
import com.sgcc.dlsc.contractManage.IdeaConManage.vo.CoContractbaseinfoVO;
import com.sgcc.dlsc.entity.po.CoContractbaseinfo;
import com.sgcc.isc.core.orm.identity.User;
import com.sgcc.uap.bizc.sysbizc.datadictionary.IDataDictionaryBizC;
import com.sgcc.uap.persistence.IHibernateDao;
import com.sgcc.uap.rest.support.IDRequestObject;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.utils.CrudUtils;
import com.sgcc.uap.rest.utils.JsonUtils;
import com.sgcc.uap.rest.utils.RestUtils;
/**
 * 用户定义逻辑构件
 * 
 * @author DELL 
 * 
 */
public class MultiLevelContractManageBizc implements IMultiLevelContractManageBizc{

	@Autowired
	private IHibernateDao hibernateDao;
	
	@Autowired
	private IComm_VerticalIntegration_Bizc commVerticalIntegrationBizc;
	
	
	/**
	 * 判断将要关联的两个合同是否已经存在上下级关系
	 * 如果存在则不能设置为上级合同
	 * @return
	 */
	private Boolean isContractInRelation(String fatherId, String contractId){
		StringBuffer str = new StringBuffer();
		str.append("SELECT cc.contractid");
		str.append(" FROM co_contractbaseinfo cc");
		str.append(" START WITH cc.CONTRACTID = '" + fatherId + "'");
		str.append(" CONNECT BY PRIOR cc.supercontractid = cc.CONTRACTID");
		str.append(" union ");
		str.append("SELECT cc.contractid");
		str.append(" FROM co_contractbaseinfo cc");
		str.append(" START WITH cc.supercontractid = '" + fatherId + "'");
		str.append(" CONNECT BY PRIOR cc.CONTRACTID = cc.supercontractid");
		
		List list = hibernateDao.executeSqlQuery(str.toString());
		for(int i = 0; i < list.size(); i++){
			Object obj = list.get(i);
			if(contractId.equalsIgnoreCase(obj.toString())){
				return true;
			}
		}
		return false;
	}
	/**
	 * 设置上级合同
	 * @description 方法描述
	 * @return
	 * @author juguanghui
	 * @date 2013-4-16
	 */
	public String setUp(String params, HttpServletRequest request){
		Map map = null;
		try {
			map = JsonUtils.getObjectMapper().readValue(params, Map.class);
			String contractid = map.get("contractid")==null?"":map.get("contractid").toString();//@获取合同id
			String fatherid = map.get("fatherid")==null?"":map.get("fatherid").toString();//@获取父合同的合同id
			
			if(isContractInRelation(fatherid, contractid)){
				return "inRelation";//两个合同已经存在关系树，不能再进行【设为上级合同】操作
			}
			
			CoContractbaseinfo coContractbaseinfo = (CoContractbaseinfo) hibernateDao.getObject(CoContractbaseinfo.class,contractid);//@根据合同id查询合同基本信息
			coContractbaseinfo.setSupercontractid(fatherid);
			hibernateDao.updateObject(coContractbaseinfo);//@更新合同信息
			verticalIntegration(coContractbaseinfo, request);//纵向传输
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
		return "success";
	}
	/**
	 * 修改合同【关联权限】
	 */
	public QueryResultObject updateContractInfo(String contractid, String isrelation, HttpServletRequest request){
		CoContractbaseinfo coContractbaseinfo = (CoContractbaseinfo) hibernateDao.getObject(CoContractbaseinfo.class, contractid);
		
		if(isrelation != null){
			coContractbaseinfo.setIsrelation(new BigDecimal(isrelation));
		}
		
		hibernateDao.updateObject(coContractbaseinfo);
		verticalIntegration(coContractbaseinfo, request);
		CoContractbaseinfoVO vo = null;
		if (coContractbaseinfo != null) {
			vo = CoContractbaseinfoTransfer.toVO(coContractbaseinfo);
		}
		return RestUtils.wrappQueryResult(vo);
	}
	
	/**
	 * 纵向传输
	 * @param coContractbaseinfo
	 * @param request
	 */
	private void verticalIntegration(CoContractbaseinfo coContractbaseinfo, HttpServletRequest request){
		String contractid = coContractbaseinfo.getContractid();
		//纵向传输
		String marketid = null;
		try {
			marketid = UserInfoUtil.getLoginUserMarket(request);
		} catch (Exception e1) {
			e1.printStackTrace();
		}//从数据库中获取当前登录用户
		VIParamBean vip = new VIParamBean();
		if(marketid != null){
			vip.setMarketid_source(marketid);
		}
		vip.setMarketid_target(DataConstants.marketid_target);//目的场景
		vip.setTableName("co_contractbaseinfo");//表名
		vip.setWhereCondtion("contractid='"+contractid+"'");
		User user;
		try {
			user = UserInfoUtil.getLoginUser(request);
			vip.setUserId(user.getId());
			vip.setUserName(user.getUserName());//拼音
		} catch (Exception e) {
			e.printStackTrace();
		}
		vip.setDate(DateUtil.getNowDate("yyyy-MM-dd"));
		VIReturnBean vir = commVerticalIntegrationBizc.executeVerticalIntegrationHSXByLogObjId(vip);
		System.out.println(vir.isSuc());
	}
	
	/**
	 * 保存更新
	 * 
	 * @param list
	 */
	 public List<CoContractbaseinfoVO> saveOrUpdate(List<Map> list) {
		 
		List<CoContractbaseinfoVO> voList = new ArrayList<CoContractbaseinfoVO>();
		for (int i = 0; i < list.size(); i++) {
			Map map = list.get(i);
			String poName = CoContractbaseinfo.class.getName();
			if(map.containsKey("contractid")){
				String id = (String)map.get("contractid");
				CoContractbaseinfoVO vo = update(map,poName,id);
				voList.add(vo);
			}else{
				CoContractbaseinfoVO coContractbaseinfoVO = save(map);
				voList.add(coContractbaseinfoVO);
			}
		}
		return voList;
	 }
	 
	//保存记录
	private CoContractbaseinfoVO save(Map map) {
		
		CoContractbaseinfoVO coContractbaseinfoVo = new CoContractbaseinfoVO();
		try {
			BeanUtils.populate(coContractbaseinfoVo, map);
		} catch (Exception e) {
		}
		CoContractbaseinfo coContractbaseinfo = CoContractbaseinfoTransfer.toPO(coContractbaseinfoVo);
		hibernateDao.saveOrUpdateObject(coContractbaseinfo);
		coContractbaseinfoVo = CoContractbaseinfoTransfer.toVO(coContractbaseinfo);
		if(map.containsKey("mxVirtualId")){
			coContractbaseinfoVo.setMxVirtualId(String.valueOf(map.get("mxVirtualId")));
		}
		return coContractbaseinfoVo;
	}
	
	//更新记录
	private CoContractbaseinfoVO update(Map<String, ?> map,String poName,String id) {
		
		CoContractbaseinfoVO coContractbaseinfoVo = new CoContractbaseinfoVO();
		//更新操作
		try {
			BeanUtils.populate(coContractbaseinfoVo, map);
		} catch (Exception e) {
		}
		Object[] objArray = CrudUtils.generateHql(CoContractbaseinfo.class, map, "contractid");
		hibernateDao.update((String) objArray[0], (Object[]) objArray[1]);
		return coContractbaseinfoVo;
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
			hibernateDao.update("delete from " + CoContractbaseinfo.class.getName() + " where contractid = ?" ,new Object[]{id});
		}
	}
	
	/**
	 * 查询单条记录
	 * @param id
	 * @return QueryResultObject
	 */
	public QueryResultObject queryById(String id) {
		CoContractbaseinfo coContractbaseinfo = (CoContractbaseinfo) hibernateDao.getObject(CoContractbaseinfo.class,id);
		CoContractbaseinfoVO vo = null;
		if (coContractbaseinfo != null) {
			vo = CoContractbaseinfoTransfer.toVO(coContractbaseinfo);
		}
		return RestUtils.wrappQueryResult(vo);
	}

	public void setHibernateDao(IHibernateDao hibernateDao) {
		this.hibernateDao = hibernateDao;
	}

}
