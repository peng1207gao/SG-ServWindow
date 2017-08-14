package com.sgcc.dlsc.contractManage.coContractEnergyInfoChart.bizc;

import java.util.List;

public interface ICoContractEnergyInfoChartBizc {
	
	public List getCOContractEnergyTree(String type);
	
	public List getAllEnergyInfo(String contractid);
	
	public int[] getAvg(String contractid, String sdate, String edate);
	
	Object getChartXml( String contractId, String startDate,
			String endDate);
}
