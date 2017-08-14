package com.sgcc.dlsc.contractManage.cotransqtyinfo.vo;
import java.math.BigDecimal;
import java.util.Date;

import java.io.Serializable;
import com.sgcc.uap.rest.annotation.attribute.EditorType;
import com.sgcc.uap.rest.annotation.attribute.AttributeType;
import com.sgcc.uap.rest.annotation.attribute.ViewAttribute;
import com.sgcc.uap.rest.support.ParentVO;

/**
 * CoTransqtyslaveinfo的VO类
 *
 * @author  mengke  [Tue Feb 11 10:04:10 CST 2014]
 * 
 */
public class CoTransqtyslaveinfoVO extends ParentVO implements Serializable{

	private static final long serialVersionUID = 1L;

    /** 
     * 属性guid
     */
	@ViewAttribute(name = "guid", caption="GUID", length =36, maxLength=36, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String guid;
    /** 
     * 属性transinfoid
     */
	@ViewAttribute(name = "transinfoid", caption="????ID", length =36, maxLength=36, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String transinfoid;
    /** 
     * 属性starttime
     */
	@ViewAttribute(name = "starttime", caption="????", length =36, maxLength=36, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String starttime;
    /** 
     * 属性endtime
     */
	@ViewAttribute(name = "endtime", caption="????", length =36, maxLength=36, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String endtime;
    /** 
     * 属性power
     */
	@ViewAttribute(name = "power", caption="???", length =16, maxLength=17, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal power;
    /** 
     * 属性qtytype
     */
	@ViewAttribute(name = "qtytype", caption="????", length =2, maxLength=2, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String qtytype;
    /** 
     * 属性explanation
     */
	@ViewAttribute(name = "explanation", caption="??", length =1024, maxLength=1024, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String explanation;
	/** 
     * 属性explanation
     */
	@ViewAttribute(name = "bight", caption="??", length =256, maxLength=256, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String bight;
	/** 
     * 属性explanation
     */
	@ViewAttribute(name = "startdate", caption="??", length =1024, maxLength=1024, editor=EditorType.DateTimeEditor,  nullable=true, readOnly=false,  type=AttributeType.DATE) 
	private Date startdate;
	/** 
     * 属性explanation
     */
	@ViewAttribute(name = "enddate", caption="??", length =1024, maxLength=1024, editor=EditorType.DateTimeEditor,  nullable=true, readOnly=false,  type=AttributeType.DATE) 
	private Date enddate;
    /**
     * CoTransqtyslaveinfoVO构造函数
     */
    public CoTransqtyslaveinfoVO() {
        super();
    }  
	
    /**
     * CoTransqtyslaveinfoVO完整的构造函数
     */  
    public CoTransqtyslaveinfoVO(String guid){
        this.guid = guid;
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
     * 属性 transinfoid 的get方法
     * @return String
     */
    public String getTransinfoid(){
        return transinfoid;
    }
	
    /**
     * 属性 transinfoid 的set方法
     * @return
     */
    public void setTransinfoid(String transinfoid){
        this.transinfoid = transinfoid;
    } 
	
    /**
     * 属性 starttime 的get方法
     * @return String
     */
    public String getStarttime(){
        return starttime;
    }
	
    /**
     * 属性 starttime 的set方法
     * @return
     */
    public void setStarttime(String starttime){
        this.starttime = starttime;
    } 
	
    /**
     * 属性 endtime 的get方法
     * @return String
     */
    public String getEndtime(){
        return endtime;
    }
	
    /**
     * 属性 endtime 的set方法
     * @return
     */
    public void setEndtime(String endtime){
        this.endtime = endtime;
    } 
	
    /**
     * 属性 power 的get方法
     * @return BigDecimal
     */
    public BigDecimal getPower(){
        return power;
    }
	
    /**
     * 属性 power 的set方法
     * @return
     */
    public void setPower(BigDecimal power){
        this.power = power;
    } 
	
    /**
     * 属性 qtytype 的get方法
     * @return BigDecimal
     */
    public String getQtytype(){
        return qtytype;
    }
	
    /**
     * 属性 qtytype 的set方法
     * @return
     */
    public void setQtytype(String qtytype){
        this.qtytype = qtytype;
    } 
	
    /**
     * 属性 explanation 的get方法
     * @return String
     */
    public String getExplanation(){
        return explanation;
    }
	
    /**
     * 属性 explanation 的set方法
     * @return
     */
    public void setExplanation(String explanation){
        this.explanation = explanation;
    } 
	
    
    
    public String getBight() {
		return bight;
	}

	public void setBight(String bight) {
		this.bight = bight;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	/**
     * toString方法
     * @return String
     */
	public String toString(){

		  return new StringBuffer()
            .append("guid"+":"+getGuid())
            .append("transinfoid"+":"+getTransinfoid())
            .append("starttime"+":"+getStarttime())
            .append("endtime"+":"+getEndtime())
            .append("power"+":"+getPower())
            .append("qtytype"+":"+getQtytype())
            .append("explanation"+":"+getExplanation())
            .append("bight"+":"+getBight())
            .append("startdate"+":"+getStartdate())
            .append("enddate"+":"+getEnddate())
            .toString(); 
			
    } 
   


}