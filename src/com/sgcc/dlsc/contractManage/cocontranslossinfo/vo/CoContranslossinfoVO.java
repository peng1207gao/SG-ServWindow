package com.sgcc.dlsc.contractManage.cocontranslossinfo.vo;
import java.math.BigDecimal;

import java.io.Serializable;
import com.sgcc.uap.rest.annotation.attribute.EditorType;
import com.sgcc.uap.rest.annotation.attribute.AttributeType;
import com.sgcc.uap.rest.annotation.attribute.ViewAttribute;
import com.sgcc.uap.rest.support.ParentVO;

/**
 * CoContranslossinfo的VO类
 *
 * @author  djdeng  [Wed Feb 12 13:31:28 CST 2014]
 * 
 */
public class CoContranslossinfoVO extends ParentVO implements Serializable{

	private static final long serialVersionUID = 1L;

    /** 
     * 属性contractid
     */
	@ViewAttribute(name = "contractid", caption="合同ID", length =36, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING, visible=false) 
	private String contractid;
	
	@ViewAttribute(name = "contractname", caption="合同名称", length =60, editor=EditorType.TextEditor,  nullable=true, readOnly=true,  type=AttributeType.STRING) 
	private String contractname;
	
    /** 
     * 属性translineno
     */
	@ViewAttribute(name = "translineno", caption="输电线路序号", length =12, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal translineno;
    /** 
     * 属性linestartendgate
     */
	@ViewAttribute(name = "linestartendgate", caption="线路起止信息", length =64, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String linestartendgate;
    /** 
     * 属性loss
     */
	@ViewAttribute(name = "loss", caption="输电网损", length =12, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal loss;
    /** 
     * 属性linkid
     */
	@ViewAttribute(name = "linkid", caption="联络线ID", length =36, editor=EditorType.DropDownEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String linkid;
    /** 
     * 属性direction
     */
	@ViewAttribute(name = "direction", caption="方向", length =1, editor=EditorType.DropDownEditor,  nullable=false, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal direction;
    /** 
     * 属性guid
     */
	@ViewAttribute(name = "guid", caption="GUID", length =36, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String guid;
    /** 
     * 属性transmission
     */
	@ViewAttribute(name = "transmission", caption="输电方", length =36, editor=EditorType.DropDownEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String transmission;
    /**
     * CoContranslossinfoVO构造函数
     */
    public CoContranslossinfoVO() {
        super();
    }  
	
    /**
     * CoContranslossinfoVO完整的构造函数
     */  
    public CoContranslossinfoVO(String contractid,BigDecimal translineno,String linestartendgate,BigDecimal direction,String guid){
        this.contractid = contractid;
        this.translineno = translineno;
        this.linestartendgate = linestartendgate;
        this.direction = direction;
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
     * 属性 translineno 的get方法
     * @return BigDecimal
     */
    public BigDecimal getTranslineno(){
        return translineno;
    }
	
    /**
     * 属性 translineno 的set方法
     * @return
     */
    public void setTranslineno(BigDecimal translineno){
        this.translineno = translineno;
    } 
	
    /**
     * 属性 linestartendgate 的get方法
     * @return String
     */
    public String getLinestartendgate(){
        return linestartendgate;
    }
	
    /**
     * 属性 linestartendgate 的set方法
     * @return
     */
    public void setLinestartendgate(String linestartendgate){
        this.linestartendgate = linestartendgate;
    } 
	
    /**
     * 属性 loss 的get方法
     * @return BigDecimal
     */
    public BigDecimal getLoss(){
        return loss;
    }
	
    /**
     * 属性 loss 的set方法
     * @return
     */
    public void setLoss(BigDecimal loss){
        this.loss = loss;
    } 
	
    /**
     * 属性 linkid 的get方法
     * @return String
     */
    public String getLinkid(){
        return linkid;
    }
	
    /**
     * 属性 linkid 的set方法
     * @return
     */
    public void setLinkid(String linkid){
        this.linkid = linkid;
    } 
	
    /**
     * 属性 direction 的get方法
     * @return BigDecimal
     */
    public BigDecimal getDirection(){
        return direction;
    }
	
    /**
     * 属性 direction 的set方法
     * @return
     */
    public void setDirection(BigDecimal direction){
        this.direction = direction;
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
     * 属性 transmission 的get方法
     * @return String
     */
    public String getTransmission(){
        return transmission;
    }
	
    /**
     * 属性 transmission 的set方法
     * @return
     */
    public void setTransmission(String transmission){
        this.transmission = transmission;
    } 

    public String getContractname() {
		return contractname;
	}

	public void setContractname(String contractname) {
		this.contractname = contractname;
	}

	/**
     * toString方法
     * @return String
     */
	public String toString(){

		  return new StringBuffer()
            .append("contractid"+":"+getContractid())
            .append("translineno"+":"+getTranslineno())
            .append("linestartendgate"+":"+getLinestartendgate())
            .append("loss"+":"+getLoss())
            .append("linkid"+":"+getLinkid())
            .append("direction"+":"+getDirection())
            .append("guid"+":"+getGuid())
            .append("transmission"+":"+getTransmission())
            .toString(); 
			
    } 
   


}