package com.sgcc.dlsc.paramConfig.cocontracsequence.bizc;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import com.sgcc.dlsc.commons.util.ToolsSys;
import com.sgcc.dlsc.commons.util.UserInfoUtil;
import com.sgcc.dlsc.contractManage.CoContransEnergyInfoV2.vo.CoContransenergyinfoTransfer;
import com.sgcc.dlsc.contractManage.CoContransEnergyInfoV2.vo.CoContransenergyinfoVO;
import com.sgcc.dlsc.entity.po.BaGencode;
import com.sgcc.dlsc.entity.po.CoContracsequenceinfo;
import com.sgcc.dlsc.entity.po.CoContractSequenceinfo;
import com.sgcc.dlsc.entity.po.CoContractenergyinfo;
import com.sgcc.dlsc.entity.po.CoContracttypeinfo;
import com.sgcc.dlsc.entity.po.CoContransenergyinfo;
import com.sgcc.dlsc.entity.po.CoTypesequencerelation;
import com.sgcc.dlsc.paramConfig.cocontracsequence.RequestConditionFilterUtil;
import com.sgcc.dlsc.paramConfig.cocontracsequence.vo.CoContractSequenceinfoTransfer;
import com.sgcc.dlsc.paramConfig.cocontracsequence.vo.CoContractSequenceinfoVO;
import com.sgcc.dlsc.paramConfig.cocontracsequence.vo.CoTypesequencerelationVO;
import com.sgcc.isc.core.orm.identity.User;
import com.sgcc.uap.bizc.sysbizc.datadictionary.IDataDictionaryBizC;
import com.sgcc.uap.integrate.isc.wrapper.factory.AdapterWrapperFactory;
import com.sgcc.uap.persistence.IHibernateDao;
import com.sgcc.uap.persistence.criterion.QueryCriteria;
import com.sgcc.uap.rest.support.DicItems;
import com.sgcc.uap.rest.support.IDRequestObject;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;
import com.sgcc.uap.rest.utils.RestUtils;

/**
 * 
 * @author djdeng
 *
 */
public class CoContractsequenceBizc implements ICoContractsequenceBizc {

	@Autowired
	private IHibernateDao hibernateDao;

	@Autowired
	private IDataDictionaryBizC dataDictionaryBizC;

	/**
	 * @throws ParseException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * 
	 */
	public QueryResultObject query(RequestCondition params) throws IllegalAccessException, InvocationTargetException, ParseException {
		
		//解析前端请求的查询过滤参数
		Map<String, String> filters = RequestConditionFilterUtil.resolveFilterParams(params);
		
		//根据前端设置的搜索条件构建sql语句和参数
		StringBuilder sql = new StringBuilder();
		List<Object> sqlParams = new ArrayList<Object>();
		searchSqlAndParams(filters, sql, sqlParams);
		
		//分页查询
		QueryResultObject result = new QueryResultObject();
		result.setItemCount(count(sql, sqlParams));
		result.setItems(query(params, sql, sqlParams));
		return result;
	}
	
	private void searchSqlAndParams(Map<String, String> filters, StringBuilder sql, List<Object> sqlParams) {
		
		//查询参数
		String sequenceType = filters.get("sequencetype");
		String sequenceCircle = filters.get("sequencecircle");
		String stime = filters.get("createdatestart");
		String etime = filters.get("createdateend");
		String sequenceName = filters.get("sequencename");
		String marketcode = filters.get("marketid");
		
		sql.append("SELECT SEQ.SEQUENCEID, SEQ.SEQUENCENAME,TO_CHAR(SEQ.CREATEDATE, 'YYYY-MM-DD HH24:MI:SS'),");
		sql.append(" BG.NAME, SEQ.UPDATEPERSONID, TO_CHAR(SEQ.UPDATETIME, 'YYYY-MM-DD HH24:MI:SS'),");
		sql.append(" BGE.NAME AS CIRCLE,TO_CHAR(LISTAGG(cc.Typename,',') WITHIN GROUP(ORDER BY cc.Typename))");
		sql.append(" FROM CO_ContracSequenceInfo SEQ");
		sql.append(" LEFT JOIN BA_GENCODE BG ON ");
		sql.append(" SEQ.SEQUENCETYPE = BG.VALUE AND BG.TYPE = '65'");
		sql.append(" LEFT JOIN BA_GENCODE BGE ON");
		sql.append(" SEQ.SEQUENCECIRCLE = BGE.VALUE AND BGE.TYPE = '23' ");
		sql.append(" left join co_typesequencerelation ct on seq.sequenceid = ct.sequenceid");
		sql.append(" left join co_contracttypeinfo cc on cc.contracttypeid = ct.contracttypeid WHERE 1=1");
		
		if (StringUtils.isNotBlank(sequenceType) && !sequenceType.equals("0")) {
			sql.append(" AND SEQ.SEQUENCETYPE=?");
			sqlParams.add(sequenceType);
		}
		if (StringUtils.isNotBlank(sequenceCircle) && !sequenceCircle.equals("0")) {
			sql.append(" AND BGE.VALUE = ?");
			sqlParams.add(sequenceCircle);
		}
		if (StringUtils.isNotBlank(stime) && StringUtils.isNotBlank(etime)) {
			sql.append(" AND TO_CHAR(SEQ.CREATEDATE, 'YYYY-MM-DD HH24:MI:SS') BETWEEN ? AND ?");
			sqlParams.add(stime);
			sqlParams.add(etime);
		}else if(StringUtils.isNotBlank(stime) && !StringUtils.isNotBlank(etime)){
			sql.append(" AND TO_CHAR(SEQ.CREATEDATE, 'YYYY-MM-DD HH24:MI:SS') >= ?");
			sqlParams.add(stime);
		}else if(!StringUtils.isNotBlank(stime) && StringUtils.isNotBlank(etime)){
			sql.append(" AND TO_CHAR(SEQ.CREATEDATE, 'YYYY-MM-DD HH24:MI:SS') <= ?");
			sqlParams.add(etime);
		}
		if (StringUtils.isNotBlank(sequenceName)) {
			sql.append(" AND SEQ.SEQUENCENAME like ?");
			sqlParams.add("%" + sequenceName + "%");
		}
		if (StringUtils.isNotBlank(marketcode)) {
			sql.append(" AND SEQ.MARKETID = ?");
			sqlParams.add(marketcode);
		}
		
		sql.append(" group by SEQ.SEQUENCEID, SEQ.SEQUENCENAME, SEQ.CREATEDATE, BG.NAME, SEQ.UPDATEPERSONID, SEQ.UPDATETIME, BGE.NAME");
		sql.append(" ORDER BY SEQ.CREATEDATE DESC");
	}
	
	private int count(StringBuilder sql, List<Object> sqlParams) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT COUNT(1) FROM (").append(sql.toString()).append(")");
		//Object[] sqlParamsArray = ObjectUtils.toObjectArray(sqlParams);
		List list = hibernateDao.executeSqlQuery(buffer.toString(), sqlParams.toArray());
		
		if (list == null || list.isEmpty()) {
			return 0;
		}
		Object obj = list.get(0);
		int totalCount = Integer.parseInt(obj.toString());
		return totalCount;
	}
	
	private List<CoContractSequenceinfoVO> query(RequestCondition params, 
			StringBuilder sql, List<Object> sqlParams) throws IllegalAccessException, InvocationTargetException, ParseException {
		
//		Object[] sqlParamsArray = ObjectUtils.toObjectArray(sqlParams);
		int pageIndex = Integer.parseInt(params.getPageIndex());
		int pageSize = Integer.parseInt(params.getPageSize());
		List<Object[]> list = hibernateDao.executeSqlQuery(sql.toString(), sqlParams.toArray(), pageIndex, pageSize);
		
		//数据映射
		List<CoContractSequenceinfoVO> vos = CoContractSequenceinfoTransfer.toVO(list);
		for(int i=0;i<vos.size();i++){
			if(ToolsSys.isnull(vos.get(i).getUpdatepersonid())){
				try {
					List listUser = AdapterWrapperFactory.getIdentityService().getUsersByLoginCode(vos.get(i).getUpdatepersonid());//.getUsersByLoginCode(contracttype.getUpdatepersonid());
					if(listUser != null && listUser.size() > 0){
						User user = (User) listUser.get(0);
						vos.get(i).setUpdatepersonid(user.getName()==null?"":user.getName());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return vos;
	}
	//checkId 是序列id
	public QueryResultObject queryById(String checkId,String name) {
		CoContractSequenceinfo coContransenergyinfo  = hibernateDao.getObject(CoContractSequenceinfo.class,checkId);
		String[] keys = checkId.split(",");//拆分
		CoContractSequenceinfoVO vo = null;
		if (coContransenergyinfo != null) {
			String sequenceid = coContransenergyinfo.getSequenceid();
			String guid="";
			StringBuffer sql=new StringBuffer("select guid from Co_Typesequencerelation where sequenceid=?");
			List<Object> list=hibernateDao.executeSqlQuery(sql.toString(),new Object[]{sequenceid});
			CoTypesequencerelation cotypesequencerelation=null;
			for(int i=0;i<list.size();i++){
				Object obj =list.get(i);
				if (i>=1) {
					guid = guid + ",";
				}
				String id = obj==null?"":obj.toString();//@判断是否为空并赋值
				guid = guid + id;
			}
			if (ToolsSys.isnull(guid)) {
				String id = guid.replaceAll(", ", "");
				String[] coid = id.split(",");//@拆分
				String cotypeid = "";
				String cotypename="";
				for(int i=0;i<coid.length;i++){
					String coGuid = coid[i]==null?"":coid[i].toString();
//					cotypesequencerelation = (CoTypesequencerelation) hibernateDao.getObject(CoTypesequencerelation.class,coGuid);
					String tenSql="select c.sequenceid,a.contracttypeid,a.typename from co_typesequencerelation c,co_contracttypeinfo a" +
							" where a.contracttypeid=c.contracttypeid and c.guid=?";
					List<Object[]>tenList=new ArrayList<Object[]>();
					tenList=hibernateDao.executeSqlQuery(tenSql,new Object[]{coGuid});
					String typeid = tenList.get(0)[1].toString();
					String typename = tenList.get(0)[2].toString();
					if(i>=1){
						cotypeid = cotypeid + ",";
						cotypeid += typeid;
						cotypename+=",";
						cotypename+= typename;
					}else{
						cotypeid=typeid;
						cotypename=typename;
					}
						
				}
				String contracttypeid = cotypeid.replace(", ", ",");
				coContransenergyinfo.setContractTypeName(cotypename);
				coContransenergyinfo.setContractType(contracttypeid);
			}
			String userName =name;//获取登陆人;
			vo = CoContractSequenceinfoTransfer.toVO(coContransenergyinfo);
		}
		return RestUtils.wrappQueryResult(vo).addDicItems(getGenCodeByType("23","sequencecircleStr"));
	}
	
	// 将字典对象封装为list
	private List<DicItems> wrapDictList() {
		List<DicItems> dicts = new ArrayList<DicItems>();
        dicts.add(this.translateFromDB("supertypeid",CoContracttypeinfo.class.getName(),"contracttypeid","typename"));
        dicts.add(getGenCodeByTypea("23","sequencecircleStr"));
		return dicts;
	}
	
	//从数据库中查找数据字典 
	private DicItems translateFromDB(String fieldName, String poName,String keyField, String valueField) {
		List<Map<String, String>> list = dataDictionaryBizC.translateFromDB
			(poName, "value", "text", keyField, valueField,"1=1");
		DicItems dict = new DicItems();
		dict.setName(fieldName);
		dict.setValues(list);
		return dict;
	}
	
	public String remove(String []strst) {
		String[] objIds = strst;
		List<CoContracsequenceinfo> tosList;//@定义对象list
		List conlist = new ArrayList();//创建list
		List<String>objIds1 = new ArrayList<String>();//
		List msglist = new ArrayList();//创建list
		String msg = "";
		//@分解有无关联的合同的合同序列
		for(int i=0;i<objIds.length;i++){
//			String[] ids = objIds[i].split(",");
//			conlist = coContracsequenceinfoMng.findBySequenceID(objIds[i]);//@根据sequenceid查询
			String sql ="select contractID from CO_ContractBaseInfo ";
			if(ToolsSys.isnull(objIds[i])){
				sql+=" where sequenceID='"+objIds[i]+"'";
			}
			conlist=hibernateDao.executeSqlQuery(sql);
			int j = 0;
			if (conlist.size()!=0) {//@有关联合同
				msglist.add(objIds[i]);//添加到信息list
			}else{//@没有关联
				objIds1.add(objIds[i]);//添加到数组中
				j++;
			}
		}
		//查找有关联合同的合同序列
		if (msglist.size()!= 0) {
			for (int k = 0; k < msglist.size(); k++) {
				msg += msglist.get(k).toString();
				if(k< msglist.size()-1){
					msg+=",";
				}
			}
//			msg="序号"+msg;
//			msg +="的合同序列有关联合同,不能删除！";
		}
		int tenNum=0;
		if (objIds1.size()!=0) {
			String tenStr="";
			for(int i=0;i<objIds1.size();i++){
				int n=hibernateDao.update("delete from " + CoContracsequenceinfo.class.getName() + " where sequenceID = ?" ,new Object[]{objIds1.get(i)});
				tenNum+=n;
				if(i!=objIds1.size()-1){
					tenStr+=objIds1.get(i)+",";
				}else{
					tenStr+=objIds1.get(i);
				}
			}
			if(tenNum==objIds1.size()){ //1删除成功
				msg+="_1_"+tenStr;
			}else if(tenNum==0){ //0删除失败
				msg+="_0_"+tenStr;
			}else if(tenNum<=objIds1.size()){//部分合同删除成功
				msg+="_2";
			}
		}
		return msg;
	}

	public DicItems getGencodeByType(String type) {
		DicItems dict = translateFromDB("direction", BaGencode.class.getName(), 
        		"value", "name", "type = '"+type+"'");
		return dict;
	}
	
	// 从数据库中查询字典
	private DicItems translateFromDB(String fieldName, String poName,
			String keyField, String valueField, String filter) {
		
		List<Map<String, String>> list = dataDictionaryBizC.translateFromDB(
				poName, "value", "text", keyField, valueField, filter);
		DicItems dict = new DicItems();
		dict.setName(fieldName);
		dict.setValues(list);
		return dict;
	}

	public  DicItems getGenCodeByTypea(String type,String fieldName) {
		if (!ToolsSys.isnull(type)) {
			return null;
		}
		List<Map<String, String>> list = new ArrayList();
		StringBuffer sql = new StringBuffer("select value,name from Ba_Gencode bgc where bgc.type=? order by bgc.value");
		List tlist= hibernateDao.executeSqlQuery(sql.toString(), new Object[]{type});
		for(int i=0;i<tlist.size();i++){
			Map<String, String> map = new HashMap();
			Object[] robj = (Object[])tlist.get(i);
			String value = robj[0]==null?"":robj[0].toString();
			String name = robj[1]==null?"":robj[1].toString();
			map.put("value", value);
			map.put("text", name);
			list.add(map);
		}
		DicItems dict = new DicItems();
		dict.setName(fieldName);
		dict.setValues(list);
//		List<DicItems> dicts = new ArrayList<DicItems>();
//		dicts.add(dict);
		return dict;
	}

	public void saveOrUpdate(CoContractSequenceinfoVO vo) {
		CoContractSequenceinfo coContractSequenceinfo=new CoContractSequenceinfo();
		coContractSequenceinfo=CoContractSequenceinfoTransfer.toPO(vo);
//		hibernateDao.saveOrUpdateObject(coContractSequenceinfo);
		Date nowDate = new Date();//当前时间
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置时间格式	
		try {
			nowDate = dateFormat.parse(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));//设置时间格式
		} catch (ParseException e) {
			 e.printStackTrace();
		}	
		if(vo.getTenId()==null){
			coContractSequenceinfo.setUpdatetime(nowDate);//@设置更新时间
			String str="update co_contracsequenceinfo set sequencename=?, sequencetype=?, marketid=?, updatetime=?, updatepersonid=?, sequencecircle=? where sequenceid=?";
			hibernateDao.executeSqlUpdate(str, new Object[]{coContractSequenceinfo.getSequencename(),coContractSequenceinfo.getSequencetype(),coContractSequenceinfo.getMarketid(),
					coContractSequenceinfo.getUpdatetime(),coContractSequenceinfo.getUpdatepersonid(),coContractSequenceinfo.getSequencecircle(),coContractSequenceinfo.getSequenceid()});
		}else{
			coContractSequenceinfo.setUpdatetime(nowDate);//@设置更新时间
			coContractSequenceinfo.setCreatedate(nowDate);//@设置创建时间
			String str="insert into co_contracsequenceinfo values(?,?,?,?,?,?,?,?)";
			hibernateDao.executeSqlUpdate(str, new Object[]{coContractSequenceinfo.getSequenceid(),coContractSequenceinfo.getSequencename(),coContractSequenceinfo.getSequencetype(),
					coContractSequenceinfo.getMarketid(),coContractSequenceinfo.getUpdatetime(),coContractSequenceinfo.getUpdatepersonid(),coContractSequenceinfo.getSequencecircleStr(),
					coContractSequenceinfo.getCreatedate()});
		}
		CoTypesequencerelation cotypesequencerelation = new CoTypesequencerelation();//创建对象
		String type = vo.getContractType().replaceAll(", ", ",");
		//将原来的  关系表 内的关系 全部 删除 在更新
		hibernateDao.update("delete from " + CoTypesequencerelation.class.getName() + " where sequenceID = ?" ,new Object[]{vo.getSequenceid()});
		String[] typeid = type.split(",");//拆解
		for(int i=0;i<typeid.length;i++){
			String cotype = typeid[i]==null?"":typeid[i].toString();//
			cotypesequencerelation.setGuid(null);
			cotypesequencerelation.setContracttypeid(cotype);//@设置contracttypeid 
			cotypesequencerelation.setSequenceid(vo.getSequenceid());//@设置Sequenceid
			hibernateDao.saveOrUpdateObject(cotypesequencerelation);//保存对象
		}
	}
	
	/**
	 * 查询根节点
	 * @param clazz
	 * @param pidName
	 * @return list
	 */
	public List listRoot(Class clazz,String pidName,String marketId){
		return listNode(clazz, null, pidName, marketId);
	}
	/**
	 * 查询节点
	 * @return list
	 */
	public List listNode(Class clazz,String nodeValue,String pidName,String marketId){
		QueryCriteria qc = new QueryCriteria();
		qc.addFrom(clazz.getName());
    	List listcode = null;
    	List list = new ArrayList();
    	Object[] param = {};
    	StringBuffer buf = new StringBuffer();//拼接sql
		if (pidName != null && !pidName.equals("")) {
    		if (nodeValue == null) {
    			buf.append("SELECT T.CONTRACTTYPEID,T.SUPERTYPEID,T.TYPENAME FROM CO_CONTRACTTYPEINFO T WHERE T.EFFECTIVEFLAG='0' AND T.MARKETID = '91812'");
    			buf.append(" AND T.SUPERTYPEID IS NULL ");
    			param = new Object[]{};
    			//qc.addWhere("",pidName, "is", null);
    		} else if (!nodeValue.equals("")) {
    			buf.append("SELECT T.CONTRACTTYPEID,T.SUPERTYPEID,T.TYPENAME FROM CO_CONTRACTTYPEINFO T WHERE T.EFFECTIVEFLAG='0' AND T.MARKETID = ?");
    			buf.append(" AND T.SUPERTYPEID = '");
    			buf.append(nodeValue);
    			buf.append("'");
    			param = new Object[]{marketId};
    			//qc.addWhere("", pidName, "=", nodeValue);
    		}
		}
		listcode = hibernateDao.executeSqlQuery(buf.toString(),param);
		if(listcode != null && listcode.size() > 0){
			for(int i=0;i<listcode.size();i++){
				CoContracttypeinfo po = new CoContracttypeinfo();
				Object[] obj = (Object[]) listcode.get(i);
				po.setContracttypeid(obj[0]==null?"":obj[0].toString());
				po.setSupertypeid(obj[1]==null?"":obj[1].toString());
				po.setTypename(obj[2]==null?"":obj[2].toString());
				list.add(po);
			}
		}
		return list;
	}
	/**
	 * 判断是否有子节点
	 */
	public boolean hasChild(String pid, Class childClazz, String pidName,String marketId) {
		QueryCriteria qc = new QueryCriteria();
		qc.addFrom(childClazz.getName());
		if(pidName != null && !pidName.equals("")) {
			qc.addWhere(pidName + "= '"+ pid +"'");
			qc.addWhere("effectiveflag='0'");
			qc.addWhere("marketid='"+marketId+"'");
		}
		qc.addPage(1,1,1);
		List list = hibernateDao.findAllByCriteria(qc);
		
		return (list != null && list.size() > 0);
	}

	public List<DicItems> getGenCodeByType(String type, String fieldName) {
		if (!ToolsSys.isnull(type)) {
			return null;
		}
		List<Map<String, String>> list = new ArrayList();
		StringBuffer sql = new StringBuffer("select value,name from Ba_Gencode bgc where bgc.type=? order by bgc.value");
		List tlist= hibernateDao.executeSqlQuery(sql.toString(), new Object[]{type});
		for(int i=0;i<tlist.size();i++){
			Map<String, String> map = new HashMap();
			Object[] robj = (Object[])tlist.get(i);
			String value = robj[0]==null?"":robj[0].toString();
			String name = robj[1]==null?"":robj[1].toString();
			map.put("value", value);
			map.put("text", name);
			list.add(map);
		}
		DicItems dict = new DicItems();
		dict.setName(fieldName);
		dict.setValues(list);
		List<DicItems> dicts = new ArrayList<DicItems>();
		dicts.add(dict);
		return dicts;
	}
}
