package com.sgcc.dlsc.statesanalysis.jfcontractstate.vo;
import java.sql.Date;
import java.math.BigDecimal;

import java.io.Serializable;
import com.sgcc.uap.rest.annotation.attribute.EditorType;
import com.sgcc.uap.rest.annotation.attribute.AttributeType;
import com.sgcc.uap.rest.annotation.attribute.ViewAttribute;
import com.sgcc.uap.rest.support.ParentVO;

/**
 * SjjcJfHtlzxx的VO类
 *
 * @author  mengke  [Wed Apr 23 11:44:25 CST 2014]
 * 
 */
public class SjjcJfHtlzxxVO extends ParentVO implements Serializable{

	private static final long serialVersionUID = 1L;

    /** 
     * 属性objId
     */
	@ViewAttribute(name = "objId", caption="记录ID", length =50, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String objId;
    /** 
     * 属性companyname
     */
	@ViewAttribute(name = "companyname", caption="运行单位名称", length =128, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String companyname;
    /** 
     * 属性companyid
     */
	@ViewAttribute(name = "companyid", caption="运行单位编码", length =50, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String companyid;
    /** 
     * 属性contractname
     */
	@ViewAttribute(name = "contractname", caption="合同名称", length =1000, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String contractname;
    /** 
     * 属性contractid
     */
	@ViewAttribute(name = "contractid", caption="合同ID", length =50, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String contractid;
    /** 
     * 属性contractno
     */
	@ViewAttribute(name = "contractno", caption="合同编号", length =512, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String contractno;
    /** 
     * 属性intodate
     */
	@ViewAttribute(name = "intodate", caption="流转时间", length =512, editor=EditorType.DateTimeEditor,  nullable=true, readOnly=false,  type=AttributeType.DATE) 
	private Date intodate;
    /** 
     * 属性contractstate
     */
	@ViewAttribute(name = "contractstate", caption="合同流转信息,1起草2审核流转3审批4编号5签署6备案", length =2, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal contractstate;
    /** 
     * 属性contractsuggestion
     */
	@ViewAttribute(name = "contractsuggestion", caption="合同审批意见", length =3600, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String contractsuggestion;
    /**
     * SjjcJfHtlzxxVO构造函数
     */
    public SjjcJfHtlzxxVO() {
        super();
    }  
	
    /**
     * SjjcJfHtlzxxVO完整的构造函数
     */  
    public SjjcJfHtlzxxVO(String objId,String companyname,String companyid,String contractid){
        this.objId = objId;
        this.companyname = companyname;
        this.companyid = companyid;
        this.contractid = contractid;
    }
 
    /**
     * 属性 objId 的get方法
     * @return String
     */
    public String getObjId(){
        return objId;
    }
	
    /**
     * 属性 objId 的set方法
     * @return
     */
    public void setObjId(String objId){
        if(objId != null && objId.trim().length() == 0){
            this.objId = null;
        }else{
            this.objId = objId;
        }
    } 
	
    /**
     * 属性 companyname 的get方法
     * @return String
     */
    public String getCompanyname(){
        return companyname;
    }
	
    /**
     * 属性 companyname 的set方法
     * @return
     */
    public void setCompanyname(String companyname){
        this.companyname = companyname;
    } 
	
    /**
     * 属性 companyid 的get方法
     * @return String
     */
    public String getCompanyid(){
        return companyid;
    }
	
    /**
     * 属性 companyid 的set方法
     * @return
     */
    public void setCompanyid(String companyid){
        this.companyid = companyid;
    } 
	
    /**
     * 属性 contractname 的get方法
     * @return String
     */
    public String getContractname(){
        return contractname;
    }
	
    /**
     * 属性 contractname 的set方法
     * @return
     */
    public void setContractname(String contractname){
        this.contractname = contractname;
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
     * 属性 contractno 的get方法
     * @return String
     */
    public String getContractno(){
        return contractno;
    }
	
    /**
     * 属性 contractno 的set方法
     * @return
     */
    public void setContractno(String contractno){
        this.contractno = contractno;
    } 
	
    /**
     * 属性 intodate 的get方法
     * @return Date
     */
    public Date getIntodate(){
        return intodate;
    }
	
    /**
     * 属性 intodate 的set方法
     * @return
     */
    public void setIntodate(Date intodate){
        this.intodate = intodate;
    } 
	
    /**
     * 属性 contractstate 的get方法
     * @return BigDecimal
     */
    public BigDecimal getContractstate(){
        return contractstate;
    }
	
    /**
     * 属性 contractstate 的set方法
     * @return
     */
    public void setContractstate(BigDecimal contractstate){
        this.contractstate = contractstate;
    } 
	
    /**
     * 属性 contractsuggestion 的get方法
     * @return String
     */
    public String getContractsuggestion(){
        return contractsuggestion;
    }
	
    /**
     * 属性 contractsuggestion 的set方法
     * @return
     */
    public void setContractsuggestion(String contractsuggestion){
        this.contractsuggestion = contractsuggestion;
    } 
	

    /**
     * toString方法
     * @return String
     */
	public String toString(){

		  return new StringBuffer()
            .append("objId"+":"+getObjId())
            .append("companyname"+":"+getCompanyname())
            .append("companyid"+":"+getCompanyid())
            .append("contractname"+":"+getContractname())
            .append("contractid"+":"+getContractid())
            .append("contractno"+":"+getContractno())
            .append("intodate"+":"+getIntodate())
            .append("contractstate"+":"+getContractstate())
            .append("contractsuggestion"+":"+getContractsuggestion())
            .toString(); 
			
    } 
   


}