package com.sgcc.dlsc.contractManage.cocontractaccessory.vo;
import java.math.BigDecimal;

import java.io.Serializable;
import com.sgcc.uap.rest.annotation.attribute.EditorType;
import com.sgcc.uap.rest.annotation.attribute.AttributeType;
import com.sgcc.uap.rest.annotation.attribute.ViewAttribute;
import com.sgcc.uap.rest.support.ParentVO;

/**
 * CoContractaccessory的VO类
 *
 * @author  djdeng  [Thu Feb 13 10:18:31 CST 2014]
 * 
 */
public class CoContractaccessoryVO extends ParentVO implements Serializable{

	private static final long serialVersionUID = 1L;

    /** 
     * 属性guid
     */
	@ViewAttribute(name = "guid", caption="GUID", length =36, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String guid;
    /** 
     * 属性contractid
     */
	@ViewAttribute(name = "contractid", caption="CONTRACTID", length =36, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING, visible=false) 
	private String contractid;
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
     * 属性participantid
     */
	@ViewAttribute(name = "participantid", caption="机组名称", length =36, editor=EditorType.DropDownEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String participantid;
    /** 
     * 属性unitid
     */
	@ViewAttribute(name = "unitid", caption="UNITID", length =36, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String unitid;
	/** 
     * 属性ecounitid
     */
	@ViewAttribute(name = "ecounitid", caption="经济机组id", length =36, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String ecounitid;
	/** 
     * 属性ecounitname
     */
	@ViewAttribute(name = "ecounitname", caption="机组名称", length =36, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String ecounitname;
	/** 
     * 属性ecounitcapacity
     */
	@ViewAttribute(name = "ecounitcapacity", caption="权益容量", length =36, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String ecounitcapacity;
	/** 
     * 属性generatorid
     */
	@ViewAttribute(name = "generatorid", caption="物理机组id", length =36, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String generatorid;
	/** 
     * 属性unitid
     */
	@ViewAttribute(name = "sequencecircle", caption="sequencecircle", length =36, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String sequencecircle;
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
     * CoContractaccessoryVO构造函数
     */
    public CoContractaccessoryVO() {
        super();
    }  
	
    /**
     * CoContractaccessoryVO完整的构造函数
     */  
    public CoContractaccessoryVO(String guid,String contractid,BigDecimal contractrole){
        this.guid = guid;
        this.contractid = contractid;
        this.contractrole = contractrole;
    }
    public String getEcounitid() {
		return ecounitid;
	}

	public void setEcounitid(String ecounitid) {
		this.ecounitid = ecounitid;
	}

	public String getEcounitname() {
		return ecounitname;
	}

	public void setEcounitname(String ecounitname) {
		this.ecounitname = ecounitname;
	}

	public String getEcounitcapacity() {
		return ecounitcapacity;
	}

	public void setEcounitcapacity(String ecounitcapacity) {
		this.ecounitcapacity = ecounitcapacity;
	}

	public String getGeneratorid() {
		return generatorid;
	}

	public void setGeneratorid(String generatorid) {
		this.generatorid = generatorid;
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
	
    /**
     * 属性 participantid 的get方法
     * @return String
     */
    public String getParticipantid(){
        return participantid;
    }
	
    /**
     * 属性 participantid 的set方法
     * @return
     */
    public void setParticipantid(String participantid){
        this.participantid = participantid;
    } 
	
    /**
     * 属性 unitid 的get方法
     * @return String
     */
    public String getUnitid(){
        return unitid;
    }
	
    /**
     * 属性 unitid 的set方法
     * @return
     */
    public void setUnitid(String unitid){
        this.unitid = unitid;
    } 
	

    /**
     * toString方法
     * @return String
     */
	public String toString(){

		  return new StringBuffer()
            .append("guid"+":"+getGuid())
            .append("contractid"+":"+getContractid())
            .append("contractrole"+":"+getContractrole())
            .append("participantid"+":"+getParticipantid())
            .append("unitid"+":"+getUnitid())
            .toString(); 
			
    }

	public String getSequencecircle() {
		return sequencecircle;
	}

	public void setSequencecircle(String sequencecircle) {
		this.sequencecircle = sequencecircle;
	} 
   


}