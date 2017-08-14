package com.sgcc.dlsc.contractManage.resolveelectricquantitytounit.vo;
import java.util.Date;
import java.math.BigDecimal;

import java.io.Serializable;
import com.sgcc.uap.rest.annotation.attribute.EditorType;
import com.sgcc.uap.rest.annotation.attribute.AttributeType;
import com.sgcc.uap.rest.annotation.attribute.ViewAttribute;
import com.sgcc.uap.rest.support.ParentVO;

/**
 * CoContractenergyinfo的VO类
 *
 * @author  Jingbo.Fu  [Sat Feb 15 10:54:51 CST 2014]
 * 
 */
public class CoBusiGeneratorQtyVO extends ParentVO implements Serializable{

	private static final long serialVersionUID = 1L;

    /** 
     * 属性contractid
     */
	@ViewAttribute(name = "contractid", caption="合同ID", length =36, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String contractid;
    /** 
     * 属性startdate
     */
	@ViewAttribute(name = "startdate", caption="开始时间", length =36, editor=EditorType.DateTimeEditor,  nullable=true, readOnly=false,  type=AttributeType.DATE) 
	private Date startdate;
    /** 
     * 属性enddate
     */
	@ViewAttribute(name = "enddate", caption="结束时间", length =36, editor=EditorType.DateTimeEditor,  nullable=true, readOnly=false,  type=AttributeType.DATE) 
	private Date enddate;
    /** 
     * 属性period
     */
	@ViewAttribute(name = "period", caption="时段 ", length =2, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal period;
    /** 
     * 属性purchasergen
     */
	@ViewAttribute(name = "purchasergen", caption="购电方发电量", length =12, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal purchasergen;
    /** 
     * 属性timeno
     */
	@ViewAttribute(name = "timeno", caption="时间段序号", length =12, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal timeno;
    /** 
     * 属性purchaserenergy
     */
	@ViewAttribute(name = "purchaserenergy", caption="购电方上网电量 ", length =12, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal purchaserenergy;
    /** 
     * 属性purchaserprice
     */
	@ViewAttribute(name = "purchaserprice", caption="购电方电价", length =12, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal purchaserprice;
    /** 
     * 属性sellerenergy
     */
	@ViewAttribute(name = "sellerenergy", caption="售电方上网电量", length =12, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal sellerenergy;
    /** 
     * 属性price
     */
	@ViewAttribute(name = "price", caption="成交电价 ", length =12, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal price;
    /** 
     * 属性guid
     */
	@ViewAttribute(name = "guid", caption="GUID", length =36, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String guid;
	/** 
     * 属性totalValue
     */
	@ViewAttribute(name = "totalValue", caption="总电量", length =36, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String totalValue;
	/** 
     * 属性peakValue
     */
	@ViewAttribute(name = "peakValue", caption="尖电量", length =36, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String peakValue;
	/** 
     * 属性topValue
     */
	@ViewAttribute(name = "topValue", caption="峰电量", length =36, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String topValue;
	/** 
     * 属性flatValue
     */
	@ViewAttribute(name = "flatValue", caption="平电量", length =36, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String flatValue;
	/** 
     * 属性valleyValue
     */
	@ViewAttribute(name = "valleyValue", caption="谷电量", length =36, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String valleyValue;

	/** 
     * 属性contractname
     */
	@ViewAttribute(name = "contractname", caption="合同名称", length =10, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String contractname;
	 /** 
     * 属性contractrole
     */
	@ViewAttribute(name = "contractrole", caption="合同角色", length =1, editor=EditorType.DropDownEditor,  nullable=false, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal contractrole;
    /** 
     * 属性purchaseunit
     */
	@ViewAttribute(name = "purchaseunit", caption="购方经济机组", length =1, editor=EditorType.DropDownEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String purchaseunit;

	/** 
     * 属性sellerunit
     */
	@ViewAttribute(name = "sellerunit", caption="售方经济机组", length =1, editor=EditorType.DropDownEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String sellerunit;
	
	 /** 
     * 属性purchaseunitid
     */
	@ViewAttribute(name = "purchaseunitid", caption="购方经济机组id", length =1, editor=EditorType.DropDownEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String purchaseunitid;

	/** 
     * 属性sellerunitid
     */
	@ViewAttribute(name = "sellerunitid", caption="售方经济机组id", length =1, editor=EditorType.DropDownEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String sellerunitid;
	
	/** 
     * 属性ecounitid
     */
	@ViewAttribute(name = "ecounitid", caption="经济机组id", length =1, editor=EditorType.DropDownEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String ecounitid;
	
	/** 
     * 属性ecounitcapacity
     */
	@ViewAttribute(name = "ecounitcapacity", caption="经济机组权益容量", length =1, editor=EditorType.DropDownEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String ecounitcapacity;
	
	/** 
     * 属性participantid
     */
	@ViewAttribute(name = "participantid", caption="机组id", length =1, editor=EditorType.DropDownEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String participantid;
	
	/** 
     * 属性unit
     */
	@ViewAttribute(name = "unit", caption="物理机组", length =1, editor=EditorType.DropDownEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String unit;
	
	/** 
     * 属性ecounit
     */
	@ViewAttribute(name = "ecounit", caption="经济机组", length =1, editor=EditorType.DropDownEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String ecounit;
	
	/** 
     * 属性unitid
     */
	@ViewAttribute(name = "unitid", caption="物理机组id", length =1, editor=EditorType.DropDownEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String unitid;
	

	public String getUnitid() {
		return unitid;
	}

	public void setUnitid(String unitid) {
		this.unitid = unitid;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getEcounit() {
		return ecounit;
	}

	public void setEcounit(String ecounit) {
		this.ecounit = ecounit;
	}

	public String getParticipantid() {
		return participantid;
	}

	public void setParticipantid(String participantid) {
		this.participantid = participantid;
	}

	public String getEcounitid() {
		return ecounitid;
	}

	public void setEcounitid(String ecounitid) {
		this.ecounitid = ecounitid;
	}

	public String getEcounitcapacity() {
		return ecounitcapacity;
	}

	public void setEcounitcapacity(String ecounitcapacity) {
		this.ecounitcapacity = ecounitcapacity;
	}
	
	public String getPurchaseunit() {
		return purchaseunit;
	}

	public void setPurchaseunit(String purchaseunit) {
		this.purchaseunit = purchaseunit;
	}

	public String getSellerunit() {
		return sellerunit;
	}

	public void setSellerunit(String sellerunit) {
		this.sellerunit = sellerunit;
	}

	public String getPurchaseunitid() {
		return purchaseunitid;
	}

	public void setPurchaseunitid(String purchaseunitid) {
		this.purchaseunitid = purchaseunitid;
	}

	public String getSellerunitid() {
		return sellerunitid;
	}

	public void setSellerunitid(String sellerunitid) {
		this.sellerunitid = sellerunitid;
	}

	/**
     * 属性 contractrole 的get方法
     * @return BigDecimal
     */
    public BigDecimal getContractrole(){
        return contractrole;
    }
	
    /**
     * 属性 contractrole 的set方法
     * @return
     */
    public void setContractrole(BigDecimal contractrole){
        this.contractrole = contractrole;
    } 
	
	public String getContractname() {
		return contractname;
	}

	public void setContractname(String contractname) {
		this.contractname = contractname;
	}
	

	public String getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(String totalValue) {
		this.totalValue = totalValue;
	}

	public String getPeakValue() {
		return peakValue;
	}

	public void setPeakValue(String peakValue) {
		this.peakValue = peakValue;
	}

	public String getTopValue() {
		return topValue;
	}

	public void setTopValue(String topValue) {
		this.topValue = topValue;
	}

	public String getFlatValue() {
		return flatValue;
	}

	public void setFlatValue(String flatValue) {
		this.flatValue = flatValue;
	}

	public String getValleyValue() {
		return valleyValue;
	}

	public void setValleyValue(String valleyValue) {
		this.valleyValue = valleyValue;
	}
    /**
     * CoContractenergyinfoVO构造函数
     */
    public CoBusiGeneratorQtyVO() {
        super();
    }  
	
    /**
     * CoContractenergyinfoVO完整的构造函数
     */  
    public CoBusiGeneratorQtyVO(String guid){
        this.guid = guid;
    }
 
    /**
     * 属性 contractid 的get方法
     * @return String
     */
    public String getContractid(){
        return contractid;
    }
	
    /**
     * 属性 contractid 的set方法
     * @return
     */
    public void setContractid(String contractid){
        this.contractid = contractid;
    } 
	
    /**
     * 属性 startdate 的get方法
     * @return Date
     */
    public Date getStartdate(){
        return startdate;
    }
	
    /**
     * 属性 startdate 的set方法
     * @return
     */
    public void setStartdate(Date startdate){
        this.startdate = startdate;
    } 
	
    /**
     * 属性 enddate 的get方法
     * @return Date
     */
    public Date getEnddate(){
        return enddate;
    }
	
    /**
     * 属性 enddate 的set方法
     * @return
     */
    public void setEnddate(Date enddate){
        this.enddate = enddate;
    } 
	
    /**
     * 属性 period 的get方法
     * @return BigDecimal
     */
    public BigDecimal getPeriod(){
        return period;
    }
	
    /**
     * 属性 period 的set方法
     * @return
     */
    public void setPeriod(BigDecimal period){
        this.period = period;
    } 
	
    /**
     * 属性 purchasergen 的get方法
     * @return BigDecimal
     */
    public BigDecimal getPurchasergen(){
        return purchasergen;
    }
	
    /**
     * 属性 purchasergen 的set方法
     * @return
     */
    public void setPurchasergen(BigDecimal purchasergen){
        this.purchasergen = purchasergen;
    } 
	
    /**
     * 属性 timeno 的get方法
     * @return BigDecimal
     */
    public BigDecimal getTimeno(){
        return timeno;
    }
	
    /**
     * 属性 timeno 的set方法
     * @return
     */
    public void setTimeno(BigDecimal timeno){
        this.timeno = timeno;
    } 
	
    /**
     * 属性 purchaserenergy 的get方法
     * @return BigDecimal
     */
    public BigDecimal getPurchaserenergy(){
        return purchaserenergy;
    }
	
    /**
     * 属性 purchaserenergy 的set方法
     * @return
     */
    public void setPurchaserenergy(BigDecimal purchaserenergy){
        this.purchaserenergy = purchaserenergy;
    } 
	
    /**
     * 属性 purchaserprice 的get方法
     * @return BigDecimal
     */
    public BigDecimal getPurchaserprice(){
        return purchaserprice;
    }
	
    /**
     * 属性 purchaserprice 的set方法
     * @return
     */
    public void setPurchaserprice(BigDecimal purchaserprice){
        this.purchaserprice = purchaserprice;
    } 
	
    /**
     * 属性 sellerenergy 的get方法
     * @return BigDecimal
     */
    public BigDecimal getSellerenergy(){
        return sellerenergy;
    }
	
    /**
     * 属性 sellerenergy 的set方法
     * @return
     */
    public void setSellerenergy(BigDecimal sellerenergy){
        this.sellerenergy = sellerenergy;
    } 
	
    /**
     * 属性 price 的get方法
     * @return BigDecimal
     */
    public BigDecimal getPrice(){
        return price;
    }
	
    /**
     * 属性 price 的set方法
     * @return
     */
    public void setPrice(BigDecimal price){
        this.price = price;
    } 
	
    /**
     * 属性 guid 的get方法
     * @return String
     */
    public String getGuid(){
        return guid;
    }
	
    /**
     * 属性 guid 的set方法
     * @return
     */
    public void setGuid(String guid){
        if(guid != null && guid.trim().length() == 0){
            this.guid = null;
        }else{
            this.guid = guid;
        }
    } 

    /**
     * toString方法
     * @return String
     */
	public String toString(){

		  return new StringBuffer()
            .append("contractid"+":"+getContractid())
            .append("contractName"+ ":" + getContractname())
            .append("startdate"+":"+getStartdate())
            .append("enddate"+":"+getEnddate())
            .append("period"+":"+getPeriod())
            .append("purchasergen"+":"+getPurchasergen())
            .append("timeno"+":"+getTimeno())
            .append("purchaserenergy"+":"+getPurchaserenergy())
            .append("purchaserprice"+":"+getPurchaserprice())
            .append("price"+":"+getPrice())
            .append("guid"+":"+getGuid())
            .toString(); 
    } 
   


}