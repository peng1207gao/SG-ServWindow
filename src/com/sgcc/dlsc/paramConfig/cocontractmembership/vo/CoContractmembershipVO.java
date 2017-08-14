package com.sgcc.dlsc.paramConfig.cocontractmembership.vo;
import java.math.BigDecimal;
import java.sql.Date;

import java.io.Serializable;
import com.sgcc.uap.rest.annotation.attribute.EditorType;
import com.sgcc.uap.rest.annotation.attribute.AttributeType;
import com.sgcc.uap.rest.annotation.attribute.ViewAttribute;
import com.sgcc.uap.rest.support.ParentVO;

/**
 * CoContractmembership的VO类
 *
 * @author  djdeng  [Wed Feb 19 16:12:13 CST 2014]
 * 
 */
public class CoContractmembershipVO extends ParentVO implements Serializable{

	private static final long serialVersionUID = 1L;

    /** 
     * 属性contracttypeid
     */
	@ViewAttribute(name = "contracttypeid", caption="????id", length =36, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String contracttypeid;
    /** 
     * 属性marketid
     */
	@ViewAttribute(name = "marketid", caption="??ID", length =36, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String marketid;
    /** 
     * 属性participantid
     */
	@ViewAttribute(name = "participantid", caption="????ID", length =36, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String participantid;
    /** 
     * 属性displaytype
     */
	@ViewAttribute(name = "displaytype", caption="????,1????2????3???", length =2, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal displaytype;
    /** 
     * 属性guid
     */
	@ViewAttribute(name = "guid", caption="GUID", length =36, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String guid;
    /** 
     * 属性updatetime
     */
	@ViewAttribute(name = "updatetime", caption="??????", length =36, editor=EditorType.DateTimeEditor,  nullable=true, readOnly=false,  type=AttributeType.DATE) 
	private Date updatetime;
    /** 
     * 属性updatepersonid
     */
	@ViewAttribute(name = "updatepersonid", caption="???????", length =36, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String updatepersonid;
    /**
     * CoContractmembershipVO构造函数
     */
    public CoContractmembershipVO() {
        super();
    }  
	
    /**
     * CoContractmembershipVO完整的构造函数
     */  
    public CoContractmembershipVO(String contracttypeid,String marketid,String participantid,BigDecimal displaytype,String guid){
        this.contracttypeid = contracttypeid;
        this.marketid = marketid;
        this.participantid = participantid;
        this.displaytype = displaytype;
        this.guid = guid;
    }
 
    /**
     * 属性 contracttypeid 的get方法
     * @return String
     */
    public String getContracttypeid(){
        return contracttypeid;
    }
	
    /**
     * 属性 contracttypeid 的set方法
     * @return
     */
    public void setContracttypeid(String contracttypeid){
        this.contracttypeid = contracttypeid;
    } 
	
    /**
     * 属性 marketid 的get方法
     * @return String
     */
    public String getMarketid(){
        return marketid;
    }
	
    /**
     * 属性 marketid 的set方法
     * @return
     */
    public void setMarketid(String marketid){
        this.marketid = marketid;
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
     * 属性 displaytype 的get方法
     * @return BigDecimal
     */
    public BigDecimal getDisplaytype(){
        return displaytype;
    }
	
    /**
     * 属性 displaytype 的set方法
     * @return
     */
    public void setDisplaytype(BigDecimal displaytype){
        this.displaytype = displaytype;
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
     * 属性 updatetime 的get方法
     * @return Date
     */
    public Date getUpdatetime(){
        return updatetime;
    }
	
    /**
     * 属性 updatetime 的set方法
     * @return
     */
    public void setUpdatetime(Date updatetime){
        this.updatetime = updatetime;
    } 
	
    /**
     * 属性 updatepersonid 的get方法
     * @return String
     */
    public String getUpdatepersonid(){
        return updatepersonid;
    }
	
    /**
     * 属性 updatepersonid 的set方法
     * @return
     */
    public void setUpdatepersonid(String updatepersonid){
        this.updatepersonid = updatepersonid;
    } 
	

    /**
     * toString方法
     * @return String
     */
	public String toString(){

		  return new StringBuffer()
            .append("contracttypeid"+":"+getContracttypeid())
            .append("marketid"+":"+getMarketid())
            .append("participantid"+":"+getParticipantid())
            .append("displaytype"+":"+getDisplaytype())
            .append("guid"+":"+getGuid())
            .append("updatetime"+":"+getUpdatetime())
            .append("updatepersonid"+":"+getUpdatepersonid())
            .toString(); 
			
    } 
   


}