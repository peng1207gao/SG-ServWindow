package com.sgcc.dlsc.contractManage.resolveelectricquantitytounit.vo;

import java.util.ArrayList;

public class ContractUnitBean {
	private String contractid;
	private ArrayList<String> purchaserList = new ArrayList<String>();
	private ArrayList<String> sellerList = new ArrayList<String>();
	public String getContractid() {
		return contractid;
	}
	public void setContractid(String contractid) {
		this.contractid = contractid;
	}
	public ArrayList<String> getPurchaserList() {
		return purchaserList;
	}
	public void setPurchaserList(ArrayList<String> purchaserList) {
		this.purchaserList = purchaserList;
	}
	public ArrayList<String> getSellerList() {
		return sellerList;
	}
	public void setSellerList(ArrayList<String> sellerList) {
		this.sellerList = sellerList;
	}
	
}
 