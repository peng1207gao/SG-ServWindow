package com.sgcc.dlsc.contractManage.contracttemplate.bizc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.beans.factory.annotation.Autowired;

import com.sgcc.dlsc.commons.util.DateUtil;
import com.sgcc.dlsc.commons.util.Guid;
import com.sgcc.dlsc.commons.util.ToolsSys;
import com.sgcc.dlsc.entity.po.CoContemplatewithparam;
import com.sgcc.dlsc.entity.po.CoContractaffixinfo;
import com.sgcc.dlsc.entity.po.CoContractbaseinfo;
import com.sgcc.uap.persistence.IHibernateDao;

public class ContractTemplateBizc implements IContractTemplateBizc
{
	@Autowired
	private IHibernateDao hibernateDao;
	/**
	 * 根据合同编号查询合同详细信息
	 * @author xuzihu
	 * @date 2012-9-27 上午09:46:39
	 * @param contractid 合同ID
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List getContractInfoByCode(String contractid){
		StringBuffer sql = new StringBuffer("select c.paperContractCode, c.contractname ,b1.participantname as b1pp, b2.participantname  as b2pp, c.Contractqty, c.Contractstartdate, c.Contractenddate,");
		sql.append(" c.Signeddate, c.contractid, co.price from sgbiz.CO_ContractBaseInfo c left join sgbiz.ba_marketparticipant b1 on c.purchaser = b1.participantid")
		.append(" left join sgbiz.ba_marketparticipant b2 on c.seller = b2.participantid left join sgbiz.CO_ContractEnergyInfo co on c.contractid = co.contractid")
		.append(" where c.contractid=?");
//		Object[] objs = null;
		List<?> list = hibernateDao.executeSqlQuery(sql.toString(),new Object[]{contractid});//@查询信息
//		if (list!=null && list.size()>0) {
//			 objs = (Object[]) list.get(0);
//		}
		return list;
	}
	public void excZip(String zipFileName, String[] fileNames) throws IOException{
		File[] files = new File[fileNames.length];
		for (int i = 0; i < fileNames.length; i++) {
			if (files[i]==null||files[i].equals("")) {
				files[i] = new File(fileNames[i]);
			}
		}
		zipFile(zipFileName, files);
	}
	/**
	 * 根据文件路径压缩指定文件
	 * @author xuzihu
	 * @date 2012-10-9 上午11:28:52
	 * @param out 压缩输出流
	 * @param files 需要压缩的文件数组
	 * @throws IOException
	 */
	public static void zipFile(String zipFileName, File[] files) throws IOException{
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
		for (int i = 0; i < files.length; i++) {
			out.putNextEntry(new ZipEntry(files[i].getName()));
			FileInputStream in = new FileInputStream(files[i]);
			int b;
			System.out.println(files[i].getName());
			while ((b = in.read()) != -1) {
				out.write(b);
			}
			out.setEncoding("gbk");
			out.closeEntry();
			in.close();
		}
		out.close();
	}
	/**
	 * 根据文件路径压缩指定文件
	 * @author xuzihu
	 * @date 2012-10-9 上午11:28:52
	 * @param out 压缩输出流
	 * @param files 需要压缩的文件数组
	 * @throws IOException
	 */
	public CoContemplatewithparam findById(String objId) {
		CoContemplatewithparam po = hibernateDao.getObject(CoContemplatewithparam.class, objId);
		return po;
	}
	public List<String[]> findCoParamByCoModel(String coModelCode) {
		String sql = "SELECT CCP.TEMPPARAMNAME,CCPD.DIC_VALUE FROM CO_CONTRACTPARAMETER CCP LEFT JOIN CO_CONTRACTPARAMDIC CCPD ON CCPD.SHEETID=CCP.DIC_ID WHERE CCP.CONTRACTTEMPLATECODE=?";
		List list = hibernateDao.executeSqlQuery(sql,new Object[]{coModelCode});
		List<String[]> coparamList = new ArrayList<String[]>();
		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);
			//封装名称和sql
			String[] strs = new String[]{ToolsSys.ObjIsNullToStr(objs[0]), ToolsSys.ObjIsNullToStr(objs[1])};//@判断是否为空并初始化
			coparamList.add(strs);
		}
		return coparamList;
	}
	public List<String[]> findConfig(String tbn, String tb, String marketid,
			String objId) {
		String sql = "SELECT CTCF.CONFIG_FIELD_NAME,CTCC.FIELD_ID FROM CO_TEMPCLIENCANFIELDCONFIG CTCC,CO_TEMPCLIENFIELDCONFIG CTCF WHERE CTCC.SHEETID=CTCF.CAN_SHEETID AND (CTCC.TABLE_ID=? OR CTCC.TABLE_ID=? ) AND CTCF.MARKETID=? AND CTCF.CONTRACTTEMPLATECODE=?";
		//@获取配置查询
		List list = hibernateDao.executeSqlQuery(sql, new Object[]{tbn,tb,marketid,objId});
		List<String[]> strslist = new ArrayList<String[]>();
		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);
			String[] strs = new String[]{ToolsSys.ObjIsNullToStr(objs[0]),ToolsSys.ObjIsNullToStr(objs[1])};
			strslist.add(strs);
		}
		//@返回数据
		return strslist;
	}
	public CoContractbaseinfo findByInfoId(String objId) {
		CoContractbaseinfo po = hibernateDao.getObject(CoContractbaseinfo.class, objId);
		return po;
	}
	/**
	 * 查询信息
	 * @description 方法描述
	 * @param coparamList
	 * @param params
	 * @return
	 * @author mengke 
	 * @date 2013-4-16
	 */
	public List<String[]> findValueByParamsql(List<String[]> coparamList, String[][] params){
		List<String[]> newCoParams = new ArrayList<String[]>();
		for (int i = 0; i < coparamList.size(); i++) {
			String[] coparam = coparamList.get(i);
			String sql = coparam[1];
			for (int j = 0; j < params.length; j++) {
				//替换关键字
				sql = sql.replaceAll(params[j][0], params[j][1]);
			}
			String[] cp = new String[2];
			cp[0] = coparam[0];
			List list = hibernateDao.executeSqlQuery(sql);//@查询信息
			if (list != null && list.size() > 0) {
				//把sql查询出来的值再放入参数数组中
				cp[1] = ToolsSys.ObjIsNullToStr(list.get(0));
			}else{
				cp[1] = "";
			}
			newCoParams.add(cp);
		}
		return newCoParams;
	}
	public void saveupLoadFile(File filetemp, String contractid, String fileName,String marketid) {
		CoContractaffixinfo coContractaffixinfo = new CoContractaffixinfo();
		String guid = Guid.create();
		coContractaffixinfo.setContractid(contractid);
//		coContractaffixinfo.setGuid(guid);
		coContractaffixinfo.setMarketid(marketid);
		coContractaffixinfo.setAffixno(new BigDecimal("1"));
		coContractaffixinfo.setAffixname(fileName);
		coContractaffixinfo.setUploadtime(DateUtil.getJavaDate());
		hibernateDao.saveObject(coContractaffixinfo);
		String sql = "INSERT INTO T_MX_ATTACHMENT T (T.ATTACHMENT_ID,T.PK_VAL,T.ATT_NAME,T.ATT_FILE) VALUES(?,?,?,?)";
		hibernateDao.executeSqlUpdate(sql, new Object[]{Guid.create(),coContractaffixinfo.getGuid(),fileName,filetemp});
		System.out.println();
	}
	/**
	 * 查询信息
	 * @description 方法描述
	 * @param findConfig
	 * @param tableId
	 * @param param
	 * @param coparamList
	 * @return
	 * @author xuzihu 
	 * @date 2013-4-16
	 */
	public List<String[]> findValueByClientConf(List<String[]> findConfig,String tableId, String param, List<String[]> coparamList){
		if(findConfig==null || findConfig.size()<=0)
			return coparamList;
		String sql = "SELECT ";
		for (int i = 0; i < findConfig.size(); i++) {
			String[] str = findConfig.get(i);
			if (i>0) {
				sql = sql + ",";
			}
			sql = sql + "tb_."+str[1]+" as co_"+i;
		}
		sql = sql + " FROM " + tableId + " tb_ WHERE tb_."+param;//param参数，用于过滤条件
		System.out.println(sql);
		//查询数据
		List list = hibernateDao.executeSqlQuery(sql);
		for (int i = 0; i < findConfig.size(); i++) {
			String[] str = findConfig.get(i);//对应表字段
			if(findConfig.size()==1){
				Object objs = (Object) list.get(0);//得到数据
				//表字段对应值
				coparamList.add(new String[]{str[0],ToolsSys.ObjIsNullToStr(objs)});
			}else{
				Object[] objs = (Object[]) list.get(0);//得到数据
				//表字段对应值
				coparamList.add(new String[]{str[0],ToolsSys.ObjIsNullToStr(objs[i])});
			}
		}
		return coparamList;
	}
	public String getcontractName(String contractId) {
		String contractname=hibernateDao.executeSqlQuery(" select co.contractname from co_contractbaseinfo co where co.contractid=?",new Object[]{contractId}).get(0).toString();
		return contractname;
	}
	public String getSsaveModelPath(String tempId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT S.att_path||''||S.ATT_NAME ")
		.append("FROM T_MX_ATTACHMENT S WHERE S.PK_VAL = ?");
		List list =hibernateDao.executeSqlQuery(sql.toString(), new Object[]{tempId});
		String path="";
		if(list!=null&&list.size()>0){
			path=list.get(0).toString();
		}
		return path;
	}
	public String isokdown(String guid){
		String finStr = "fail";
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT S.ATTACHMENT_ID,S.ATT_NAME,S.ATT_SIZE,S.att_path ")
		.append("FROM T_MX_ATTACHMENT S WHERE S.PK_VAL = ?");//uap中会将上传的附件插入到表T_MX_ATTACHMENT中，此表的PK_VAL字段与合同范本表的contracttemplatecode对应
		Object[] obj = new Object[]{guid};
		List cxlist = hibernateDao.executeSqlQuery(sql.toString(),obj);//查询附件
		if(cxlist!=null&&cxlist.size()>0){
			if(cxlist!=null&&cxlist.size()>0){
				for (int i = 0; i < cxlist.size(); i++) {
					Object[] ydArr = (Object[]) cxlist.get(0);
					String filename = ydArr[1].toString();//附件名称
					String url = ydArr[3].toString()+ydArr[1].toString();
					File file = new File(url);
					if(file.exists()){
						finStr = "success";
					}
				}
			}
		}
		return finStr;
	}
	/**
	 * 
	 * @description 方法描述
	 * @return
	 * @author xuzihu 
	 * @date 2014-7-8
	 */
	public List<String[]> findCoParamByCoModelall(){
		String sql="select t.dic_name,t.dic_value from co_contractparamdic t";
		List list = hibernateDao.executeSqlQuery(sql);
		List<String[]> coparamList = new ArrayList<String[]>();
		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);
			//封装名称和sql
			String[] strs = new String[]{ToolsSys.ObjIsNullToStr(objs[0]), ToolsSys.ObjIsNullToStr(objs[1])};//@判断是否为空并初始化
			coparamList.add(strs);
		}
		return coparamList;
	}
	
}
