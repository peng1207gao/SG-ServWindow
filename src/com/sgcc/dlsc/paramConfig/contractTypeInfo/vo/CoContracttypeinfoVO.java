package com.sgcc.dlsc.paramConfig.contractTypeInfo.vo;
import java.math.BigDecimal;
import java.sql.Date;

import java.io.Serializable;
import com.sgcc.uap.rest.annotation.attribute.EditorType;
import com.sgcc.uap.rest.annotation.attribute.AttributeType;
import com.sgcc.uap.rest.annotation.attribute.ViewAttribute;
import com.sgcc.uap.rest.support.ParentVO;

/**
 * CoContracttypeinfo的VO类
 *
 * @author  mengke  [Mon Feb 17 11:33:41 CST 2014]
 * 
 */
public class CoContracttypeinfoVO extends ParentVO implements Serializable{

	private static final long serialVersionUID = 1L;

    /** 
     * 属性contracttypeid
     */
	@ViewAttribute(name = "contracttypeid", caption="CONTRACTTYPEID", length =36, maxLength=36, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String contracttypeid;
    /** 
     * 属性supertypeid
     */
	@ViewAttribute(name = "supertypeid", caption="SUPERTYPEID", length =36, maxLength=36, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String supertypeid;
    /** 
     * 属性typename
     */
	@ViewAttribute(name = "typename", caption="TYPENAME", length =64, maxLength=64, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String typename;
    /** 
     * 属性description
     */
	@ViewAttribute(name = "description", caption="DESCRIPTION", length =256, maxLength=256, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String description;
    /** 
     * 属性isdefine
     */
	@ViewAttribute(name = "isdefine", caption="ISDEFINE", length =1, maxLength=1, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal isdefine;
    /** 
     * 属性marketid
     */
	@ViewAttribute(name = "marketid", caption="MARKETID", length =36, maxLength=36, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String marketid;
    /** 
     * 属性starteffectivedate
     */
	@ViewAttribute(name = "starteffectivedate", caption="STARTEFFECTIVEDATE", length =36, maxLength=36, editor=EditorType.DateTimeEditor,  nullable=true, readOnly=false,  type=AttributeType.DATE) 
	private Date starteffectivedate;
    /** 
     * 属性endeffectivedate
     */
	@ViewAttribute(name = "endeffectivedate", caption="ENDEFFECTIVEDATE", length =36, maxLength=36, editor=EditorType.DateTimeEditor,  nullable=true, readOnly=false,  type=AttributeType.DATE) 
	private Date endeffectivedate;
    /** 
     * 属性effectiveflag
     */
	@ViewAttribute(name = "effectiveflag", caption="EFFECTIVEFLAG", length =1, maxLength=1, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal effectiveflag;
    /** 
     * 属性updatetime
     */
	@ViewAttribute(name = "updatetime", caption="UPDATETIME", length =1, maxLength=36, editor=EditorType.DateTimeEditor,  nullable=true, readOnly=false,  type=AttributeType.DATE) 
	private Date updatetime;
    /** 
     * 属性updatepersonid
     */
	@ViewAttribute(name = "updatepersonid", caption="UPDATEPERSONID", length =36, maxLength=36, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String updatepersonid;
    /**
     * CoContracttypeinfoVO构造函数
     */
    public CoContracttypeinfoVO() {
        super();
    }  
	
    /**
     * CoContracttypeinfoVO完整的构造函数
     */  
    public CoContracttypeinfoVO(String contracttypeid,String marketid){
        this.contracttypeid = contracttypeid;
        this.marketid = marketid;
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
        if(contracttypeid != null && contracttypeid.trim().length() == 0){
            this.contracttypeid = null;
        }else{
            this.contracttypeid = contracttypeid;
        }
    } 
	
    /**
     * 属性 supertypeid 的get方法
     * @return String
     */
    public String getSupertypeid(){
        return supertypeid;
    }
	
    /**
     * 属性 supertypeid 的set方法
     * @return
     */
    public void setSupertypeid(String supertypeid){
        this.supertypeid = supertypeid;
    } 
	
    /**
     * 属性 typename 的get方法
     * @return String
     */
    public String getTypename(){
        return typename;
    }
	
    /**
     * 属性 typename 的set方法
     * @return
     */
    public void setTypename(String typename){
        this.typename = typename;
    } 
	
    /**
     * 属性 description 的get方法
     * @return String
     */
    public String getDescription(){
        return description;
    }
	
    /**
     * 属性 description 的set方法
     * @return
     */
    public void setDescription(String description){
        this.description = description;
    } 
	
    /**
     * 属性 isdefine 的get方法
     * @return BigDecimal
     */
    public BigDecimal getIsdefine(){
        return isdefine;
    }
	
    /**
     * 属性 isdefine 的set方法
     * @return
     */
    public void setIsdefine(BigDecimal isdefine){
        this.isdefine = isdefine;
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
     * 属性 starteffectivedate 的get方法
     * @return Date
     */
    public Date getStarteffectivedate(){
        return starteffectivedate;
    }
	
    /**
     * 属性 starteffectivedate 的set方法
     * @return
     */
    public void setStarteffectivedate(Date starteffectivedate){
        this.starteffectivedate = starteffectivedate;
    } 
	
    /**
     * 属性 endeffectivedate 的get方法
     * @return Date
     */
    public Date getEndeffectivedate(){
        return endeffectivedate;
    }
	
    /**
     * 属性 endeffectivedate 的set方法
     * @return
     */
    public void setEndeffectivedate(Date endeffectivedate){
        this.endeffectivedate = endeffectivedate;
    } 
	
    /**
     * 属性 effectiveflag 的get方法
     * @return BigDecimal
     */
    public BigDecimal getEffectiveflag(){
        return effectiveflag;
    }
	
    /**
     * 属性 effectiveflag 的set方法
     * @return
     */
    public void setEffectiveflag(BigDecimal effectiveflag){
        this.effectiveflag = effectiveflag;
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
            .append("supertypeid"+":"+getSupertypeid())
            .append("typename"+":"+getTypename())
            .append("description"+":"+getDescription())
            .append("isdefine"+":"+getIsdefine())
            .append("marketid"+":"+getMarketid())
            .append("starteffectivedate"+":"+getStarteffectivedate())
            .append("endeffectivedate"+":"+getEndeffectivedate())
            .append("effectiveflag"+":"+getEffectiveflag())
            .append("updatetime"+":"+getUpdatetime())
            .append("updatepersonid"+":"+getUpdatepersonid())
            .toString(); 
			
    } 
   


}