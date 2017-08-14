package com.sgcc.dlsc.contractManage.coContractEnergyInfoChart.bizc;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.sgcc.dlsc.commons.bean.FCcategories;
import com.sgcc.dlsc.commons.bean.FCcategory;
import com.sgcc.dlsc.commons.bean.FCchart;
import com.sgcc.dlsc.commons.bean.FCdataset;
import com.sgcc.dlsc.commons.bean.FCset;
import com.sgcc.dlsc.commons.util.ToolsSys;
import com.sgcc.uap.bizc.sysbizc.datadictionary.IDataDictionaryBizC;
import com.sgcc.uap.persistence.IHibernateDao;

public class CoContractEnergyInfoChartBizc implements ICoContractEnergyInfoChartBizc{

	@Autowired
	private IHibernateDao dao;
	
	@Autowired
	private IDataDictionaryBizC dataDictionaryBizC;

	public List getCOContractEnergyTree(String type) {
		StringBuffer sql = new StringBuffer();
		sql.append("select cc.contractid, TO_CHAR(cc.startdate, 'YYYY-MM-DD'),  TO_CHAR(cc.enddate, 'YYYY-MM-DD'),  cc.timeno from CO_ContractEnergyInfo cc where cc.contractid = ? group by cc.contractid, cc.startdate, cc.enddate, cc.timeno order by cc.startdate");
		
		List list = dao.executeSqlQuery(sql.toString(),new Object[]{type});
		return list;
	}

	public List getAllEnergyInfo(String contractid) {
		
		return null;
	}

	public int[] getAvg(String contractid, String sdate, String edate){
				String sql = "select sum(cc.power)/4 as a from co_conpowerinfo cc where 1=1";
				if(contractid != null && !"".equals(contractid)){
					sql += " and cc.contractid = '"+contractid+"'";
				}
				if(sdate!= null && !"".equals(sdate)){
					sql += " and TO_CHAR(cc.STARTDATE, 'yyyy-MM-dd') >= '"+sdate+"'";
				}
				if(edate!= null && !"".equals(edate)){
					sql += " and TO_CHAR(cc.enddate, 'yyyy-MM-dd') <= '"+edate+"'";
				}
				List list = dao.executeSqlQuery(sql);
				Object reValue = new Object();
				if ((Object) list.get(0) != null && list.size() > 0) {
					reValue =  (Object) list.get(0);
				}else{
					reValue = 0;
				}
				int temp = Integer.parseInt(reValue.toString());//@转换为int类型
				return new int[]{temp};
	}

	public String getChartXml(String contractId, String startDate,
			String endDate) {
		List datalist;//数据库查询数据list
		String sql = "SELECT * FROM";
		sql += " CO_ConPowerInfo CO WHERE CO.CONTRACTID='"+contractId+"'";
		sql += " AND TO_CHAR(CO.STARTDATE, 'yyyy-MM-dd') = '"+startDate+"'";
		sql += " AND TO_CHAR(CO.ENDDATE, 'yyyy-MM-dd') = '"+endDate+"' ORDER BY CO.TIMEPOINT";
		List<Object[]> list =dao.executeSqlQuery(sql);//查询数据
		List<Object[]> returnList = new ArrayList();//最终遍历的结果集
		
		FCchart chart = new FCchart();
		chart.setCaption("");				//设置图表的标题
		chart.setShowValues("0");						//设置数据值与数据图形不一同显示
		chart.setShowLegend("1");
		chart.setLegendPosition("RIGHT");
		chart.setYAxisName("");
		chart.setPlotBorderDashed("true");
		chart.setCanvasBorderThickness("2");
		String xx="";
		FCcategories categories = new FCcategories();
		FCdataset dataset1 = new FCdataset();
		List<FCcategory> fclist = new ArrayList<FCcategory>();
		List<FCset> fflist = new ArrayList<FCset>();
		/*
		 * 补点：把数据库没有的数据进行补全，默认值为上一点的数据值。
		 */
		double lastVale = 0; //上一个记录点的值
		if (list != null && list.size() != 0) {
			if (ToolsSys.ObjIsNullToStr(list.get(0)[5]).equals("")) {//如果第一个点""
				Object []obj = {1, 0};//{number, value}
				returnList.add(obj);//@添加数据
			}else{//非“”
				double value = Double.parseDouble(list.get(0)[5].toString());
				Object []obj = {1, value};
				returnList.add(obj);//@添加数据
			}
			
			//从第二条开始读取
			for (int i = 1; i < list.size(); i++) {
				if (ToolsSys.ObjIsNullToStr(list.get(i)[5]).equals("")) {
					Object []obj = {(i+1), 0};//于鹤告诉修改为0
					returnList.add(obj);//@添加数据
				}else{
					double value = Double.parseDouble(list.get(i)[5].toString());
					Object []obj = {(i+1), value};
					lastVale = value;
					returnList.add(obj);//@添加数据
				}
			}
			
			
			for(int i=0;i<returnList.size();){
				Object[] obj1 = (Object[]) returnList.get(i);
				String month = obj1[0]==null?"":obj1[2].toString();
				FCcategory fc = new FCcategory();
				fc.setLabel(month);
				fclist.add(fc);
				i=i+3;
			}
			categories.setList(fclist);
			
			dataset1.setSeriesName("");					//设置集合名称
			for(int i=0;i<returnList.size();){
				Object[] obj1 = (Object[]) returnList.get(i);
				String feizhinum = obj1[1]==null?"":obj1[1].toString();
				FCset ff = new FCset();
				ff.setValue(feizhinum);
				fflist.add(ff);
				i=i+3;
			}
			dataset1.setList(fflist);
		}else{
			FCcategory fc = new FCcategory();
			fc.setLabel("");
			fclist.add(fc);
			categories.setList(fclist);
			FCset ff = new FCset();
			ff.setValue("");
			fflist.add(ff);
			dataset1.setList(fflist);
		}
			xx =categories.toXml()+dataset1.toXml();
		return chart.toXml(xx);
	}
}
