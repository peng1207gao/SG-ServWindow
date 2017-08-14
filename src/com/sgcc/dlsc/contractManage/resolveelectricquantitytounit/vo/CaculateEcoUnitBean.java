package com.sgcc.dlsc.contractManage.resolveelectricquantitytounit.vo;

import java.util.ArrayList;

public class CaculateEcoUnitBean {
	
	private ArrayList<CoBusiGeneratorQtyVO> purchaserList = new ArrayList<CoBusiGeneratorQtyVO>();
	private ArrayList<CoBusiGeneratorQtyVO> sellerList = new ArrayList<CoBusiGeneratorQtyVO>();
	double purchaserCapacitTotal = 0;
	double sellerCapacityTotal = 0;
	CoContractenergyinfoVO energyInfo = null;
	private boolean purchaserIsBigUser = false;
	private boolean sellerIsBigUser = false;
	
	public boolean isPurchaserIsBigUser() {
		return purchaserIsBigUser;
	}
	public void setPurchaserIsBigUser(boolean purchaserIsBigUser) {
		this.purchaserIsBigUser = purchaserIsBigUser;
	}
	public boolean isSellerIsBigUser() {
		return sellerIsBigUser;
	}
	public void setSellerIsBigUser(boolean sellerIsBigUser) {
		this.sellerIsBigUser = sellerIsBigUser;
	}
	public ArrayList<CoBusiGeneratorQtyVO> getPurchaserList() {
		return purchaserList;
	}
	public void setPurchaserList(ArrayList<CoBusiGeneratorQtyVO> purchaserList) {
		this.purchaserList = purchaserList;
	}
	public ArrayList<CoBusiGeneratorQtyVO> getSellerList() {
		return sellerList;
	}
	public void setSellerList(ArrayList<CoBusiGeneratorQtyVO> sellerList) {
		this.sellerList = sellerList;
	}
	public double getPurchaserCapacitTotal() {
		return purchaserCapacitTotal;
	}
	public void setPurchaserCapacitTotal(double purchaserCapacitTotal) {
		this.purchaserCapacitTotal = purchaserCapacitTotal;
	}
	public double getSellerCapacityTotal() {
		return sellerCapacityTotal;
	}
	public void setSellerCapacityTotal(double sellerCapacityTotal) {
		this.sellerCapacityTotal = sellerCapacityTotal;
	}
	public CoContractenergyinfoVO getEnergyInfo() {
		return energyInfo;
	}
	public void setEnergyInfo(CoContractenergyinfoVO energyInfo) {
		this.energyInfo = energyInfo;
	}
}	
