package com.sgcc.dlsc.contractManage.NetProContConcMess.bizc;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.sgcc.dlsc.commons.bean.FCcategories;
import com.sgcc.dlsc.commons.bean.FCcategory;
import com.sgcc.dlsc.commons.bean.FCchart;
import com.sgcc.dlsc.commons.bean.FCdataset;
import com.sgcc.dlsc.commons.bean.FCset;
import com.sgcc.uap.bizc.sysbizc.datadictionary.IDataDictionaryBizC;
import com.sgcc.uap.persistence.IHibernateDao;

public class NetProContConcMessBizc implements INetProContConcMessBizc{
	
	@Autowired
	private IHibernateDao dao;
	
	@Autowired
	private IDataDictionaryBizC dataDictionaryBizC;

	public String getBar(String marketId) {
		FCchart fcchart=new FCchart();
		fcchart.setNumberSuffix("个");
		fcchart.setDecimals("0");
		fcchart.setNumDivLines("4");
		fcchart.setyAxisMaxValue("5");
		fcchart.setDivLineAlpha("80");
		fcchart.setDivLineColor("999999");
		List list1 = getMarket(marketId);
		FCcategories fccategories=new FCcategories();
		List<FCcategory> fccategorys=new ArrayList<FCcategory>();
		FCcategory tenFcc=new FCcategory();
		if(list1!=null&&list1.size()>0){
			Object[] obj =  (Object[]) list1.get(0);
			String marketname = (String) obj[0];
			tenFcc.setName(marketname);
			fccategorys.add(tenFcc);
			fccategories.setList(fccategorys);
		}
		FCdataset dataset1 = new FCdataset();
		dataset1.setColor("FF0080");
//		dataset1.setSeriesName("预合同");
		if(list1!=null && list1.size()>0){
			Object[] obj1 =  (Object[]) list1.get(0);
			List list2 = getCol(marketId,1);
			int count = 0;
			Object obj2 = list2.get(0);
			if(obj2!=null){
				count = Integer.valueOf(obj2.toString());
				dataset1.setSeriesName(list2.get(1).toString());
			}
			FCset fcset=new FCset();
			fcset.setValue(count+"");
			List<FCset> fclist=new ArrayList<FCset>();
			fclist.add(fcset);
			dataset1.setList(fclist);
		}
		FCdataset dataset2 = new FCdataset();
		dataset2.setColor("930093");
//		dataset2.setSeriesName("流转中");
		if(list1!=null&&list1.size()>0){
			Object[] obj1 =  (Object[]) list1.get(0);
			List list2 =getCol(marketId,2);
			int count = 0;
			Object obj2 = list2.get(0);
			if(obj2!=null){
				count = Integer.valueOf(obj2.toString());
				dataset2.setSeriesName(list2.get(1).toString());
			}
			
			FCset fcset=new FCset();
			fcset.setValue(count+"");
			List<FCset> fclist=new ArrayList<FCset>();
			fclist.add(fcset);
			dataset2.setList(fclist);
		}
		FCdataset dataset3 = new FCdataset();
		dataset3.setColor("6F00D2");
//		dataset3.setSeriesName("未签订");	
		if(list1!=null&&list1.size()>0){
			Object[] obj1 =  (Object[]) list1.get(0);
			List list2 = getCol(marketId,3);
			int count = 0;
			Object obj2 = list2.get(0);
			if(obj2!=null){
				count = Integer.valueOf(obj2.toString());
				dataset3.setSeriesName(list2.get(1).toString());
			}
			FCset fcset=new FCset();
			fcset.setValue(count+"");
			List<FCset> fclist=new ArrayList<FCset>();
			fclist.add(fcset);
			dataset3.setList(fclist);
		}
		FCdataset dataset4 = new FCdataset();
		dataset4.setColor("0000C6");
//		dataset4.setSeriesName("已签订");			
		if(list1!=null&&list1.size()>0){
			Object[] obj1 =  (Object[]) list1.get(0);
			List list2 = getCol(marketId,4);
				int count = 0;
				Object obj2=list2.get(0);
				if(obj2!=null){
					count = Integer.valueOf(obj2.toString());
					dataset4.setSeriesName(list2.get(1).toString());
				}
				FCset fcset=new FCset();
				fcset.setValue(count+"");
				List<FCset> fclist=new ArrayList<FCset>();
				fclist.add(fcset);
				dataset4.setList(fclist);
		}
		FCdataset dataset5 = new FCdataset();
		dataset5.setColor("000000");
//		dataset5.setSeriesName("未备案");
		if(list1!=null&&list1.size()>0){
			Object[] obj1 =  (Object[]) list1.get(0);
		    List list2 =getCol(marketId,5);
			int count = 0;
			Object obj2 =list2.get(0);
			if(obj2!=null){
				count = Integer.valueOf(obj2.toString());
				dataset5.setSeriesName(list2.get(1).toString());
			}
			FCset fcset=new FCset();
			fcset.setValue(count+"");
			List<FCset> fclist=new ArrayList<FCset>();
			fclist.add(fcset);
			dataset5.setList(fclist);
		}
//		FCdataset dataset6 = new FCdataset();
//		dataset6.setColor("00FFFF");
//		dataset6.setSeriesName("已备案");
//		if(list1!=null&&list1.size()>0){
//			Object[] obj1 =  (Object[]) list1.get(0);
//			List list2 = getCo6(marketId);
//			int count = 0;
//			if(list2.size()!=0){
//				Object obj2 = (Object)list2.get(0);
//				if(obj2!=null){
//					count = Integer.valueOf(obj2.toString());
//				}else{
//					count = 0;
//				}
//			}else{
//				count = 0;
//			}
//			FCset fcset=new FCset();
//			fcset.setValue(count+"");
//			List<FCset> fclist=new ArrayList<FCset>();
//			fclist.add(fcset);
//			dataset6.setList(fclist);
//		}
		return fcchart.toXml(fccategories.toXml()+dataset1.toXml()+dataset2.toXml()+dataset3.toXml()+dataset4.toXml()
				+dataset5.toXml());
	}
	public List getMarket(String marketId){
		String sql = "select bm.marketname,bm.marketid from ba_market bm where bm.marketid = '"+marketId+"'";
		return dao.executeSqlQuery(sql);
	}
	public List getCol(String marketid,int i) {  //预合同
		String sql = "select count(*) from co_contractbaseinfo cc where cc.marketid = '"+marketid+"' and cc.flowflag='"+i+"' and cc.isdelete = '0'";
		List list= dao.executeSqlQuery(sql);
		String sql1 = "select name from ba_gencode bb where bb.value='"+i+"' and bb.type='67'";
		List list1=dao.executeSqlQuery(sql1);//@执行sql
		if(list1.size()!=0){
			list.add(list1.get(0));
		}
		return list;
	}
//	public List getCo6(String marketid) {
//		String sql = "select count(*) from co_contractbaseinfo cc where cc.marketid = '"+marketid+"' and cc.backupState = '1'";
//		return dao.executeSqlQuery(sql);//@执行sql
//	}
}
