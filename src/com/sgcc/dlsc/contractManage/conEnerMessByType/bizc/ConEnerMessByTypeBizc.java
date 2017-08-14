package com.sgcc.dlsc.contractManage.conEnerMessByType.bizc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.sgcc.dlsc.commons.bean.FCchart;
import com.sgcc.dlsc.commons.bean.FCset;
import com.sgcc.uap.bizc.sysbizc.datadictionary.IDataDictionaryBizC;
import com.sgcc.uap.persistence.IHibernateDao;

public class ConEnerMessByTypeBizc implements IConEnerMessByTypeBizc{

	@Autowired
	private IHibernateDao dao;
	
	@Autowired
	private IDataDictionaryBizC dataDictionaryBizC;

	public String getPie(String marketId) {
			FCchart chart = new FCchart();
//			chart.setCaption("购电费结构分析");				//设置图表的标题
			chart.setDecimals("0");							//功能属性类-------------设置四舍五入的小数点精度。范围0-10
//			chart.setStartingAngle("70");					//起始角度
			chart.setShowLegend("1");						//显示图例
//			String chartsXML = "<graph decimalPrecision='0' showNames='1' numberSuffix='MWh'>";
			chart.setNumberSuffix("MKH");
			StringBuffer sql =  new StringBuffer("");
			sql.append("select cc.typename,cc.contracttypeid from");
			sql.append(" CO_CONTRACTTYPEINFO cc left join co_contractbaseinfo c on c.contracttype = cc.contracttypeid");
			sql.append(" where cc.starteffectivedate <= sysdate and cc.effectiveflag = 0 and (cc.marketid='"+marketId+"' or cc.marketid='91812')");// or cc.contracttypeid in");
//			sql.append( " (select bp.dataid from  co_contracttypeinfo_share bp where");
//			sql.append( "  bp.marketid ='"+marketId+"') ");
//			sql.append(")");
			sql.append( " and c.contractqty is not null group by  cc.typename, cc.contracttypeid");
			List list1 = dao.executeSqlQuery(sql.toString());
			for(int i=0;i<list1.size();i++){
				Object[] obj = (Object[]) list1.get(i); 
				String name = obj[0]==null?"其他类型":obj[0].toString();
				String cotype = obj[1]==null?"":obj[1].toString();
				List list2 = getCount(marketId,cotype);
				String capa = "";
				if(list2.size()!=0){
					Object obj2 = (Object)list2.get(0);
					capa = obj2==null?"0":obj2.toString();
//					Float value =  Float.valueOf(capa);
					
					FCset fs = new FCset();
					fs.setLabel(name); //设置SET的label值
					fs.setValue(capa);		//设置SET的value值
					fs.setDisplayValue(name+",  "+capa);
					fs.setIsSliced("1");
					chart.addSet(fs);
				}
			}
			return chart.toXml();//@返回
	}
	public List getCount(String marketid, String cotype) {
		// TODO Auto-generated method stub
		String sql = "select sum(contractqty) from co_contractbaseinfo cc where 1=1";
		if(cotype!=null&&!"".equals(cotype)){
			sql += " and cc.contracttype = '"+cotype+"'";
		}else{
			sql += " and cc.contracttype is null";
		}
		sql += " and cc.marketid = '"+marketid+"'";
		sql += "and cc.isdelete = '0'";
		return dao.executeSqlQuery(sql);
	}
	public List getCocapa(String marketid) {
		String sql = "select sum(contractqty), cct.typename,cct.contracttypeid,count(*) from co_contractbaseinfo cc left join co_contracttypeinfo cct on cc.contracttype = cct.contracttypeid";
		sql += " where cc.contractqty is not null AND TO_CHAR(cc.CONTRACTSTARTDATE, 'YYYY') = TO_CHAR(sysdate, 'YYYY') AND TO_CHAR(cc.CONTRACTENDDATE, 'YYYY') = TO_CHAR(sysdate, 'YYYY') and cc.marketid = '"+marketid+"' group by cct.typename,cct.contracttypeid order by sum(contractqty)";
		return dao.executeSqlQuery(sql);
	}
}
