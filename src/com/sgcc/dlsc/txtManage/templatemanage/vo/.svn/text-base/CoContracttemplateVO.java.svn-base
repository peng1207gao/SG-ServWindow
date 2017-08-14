package com.sgcc.dlsc.txtManage.templatemanage.vo;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.sgcc.uap.rest.annotation.attribute.AttributeType;
import com.sgcc.uap.rest.annotation.attribute.EditorType;
import com.sgcc.uap.rest.annotation.attribute.ViewAttribute;
import com.sgcc.uap.rest.support.ParentVO;

/**
 * CoContracttemplate的VO类
 *
 * @author  mengke  [Thu May 22 11:23:03 CST 2014]
 * 
 */
public class CoContracttemplateVO extends ParentVO implements Serializable{

	private static final long serialVersionUID = 1L;

    /** 
     * 属性marketid
     */
	@ViewAttribute(name = "marketid", caption="市场ID", length =36, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String marketid;
    /** 
     * 属性contracttype
     */
	@ViewAttribute(name = "contracttype", caption="合同类型", length =64, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String contracttype;
    /** 
     * 属性contracttemplateid
     */
	@ViewAttribute(name = "contracttemplateid", caption="合同范本ID", length =36, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String contracttemplateid;
    /** 
     * 属性contracttemplatecode
     */
	@ViewAttribute(name = "contracttemplatecode", caption="合同范本编号", length =64, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String contracttemplatecode;
    /** 
     * 属性contracttemplatename
     */
	@ViewAttribute(name = "contracttemplatename", caption="合同范本名称", length =64, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String contracttemplatename;
    /** 
     * 属性version
     */
	@ViewAttribute(name = "version", caption="版本号", length =16, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String version;
    /** 
     * 属性issueddate
     */
	@ViewAttribute(name = "issueddate", caption="下发时间", length =16, editor=EditorType.DateTimeEditor,  nullable=true, readOnly=false,  type=AttributeType.DATE) 
	private Date issueddate;
    /** 
     * 属性starteffectivedate
     */
	@ViewAttribute(name = "starteffectivedate", caption="生效时间", length =16, editor=EditorType.DateTimeEditor,  nullable=true, readOnly=false,  type=AttributeType.DATE) 
	private Date starteffectivedate;
    /** 
     * 属性endeffectivedate
     */
	@ViewAttribute(name = "endeffectivedate", caption="失效时间", length =16, editor=EditorType.DateTimeEditor,  nullable=true, readOnly=false,  type=AttributeType.DATE) 
	private Date endeffectivedate;
    /** 
     * 属性effectiveflag
     */
	@ViewAttribute(name = "effectiveflag", caption="失效标志", length =1, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal effectiveflag;
    /** 
     * 属性isdelete
     */
	@ViewAttribute(name = "isdelete", caption="删除标记", length =1, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal isdelete;
    /** 
     * 属性updatetime
     */
	@ViewAttribute(name = "updatetime", caption="信息更新时间", length =1, editor=EditorType.DateTimeEditor,  nullable=true, readOnly=false,  type=AttributeType.DATE) 
	private Date updatetime;
    /** 
     * 属性updatepersonid
     */
	@ViewAttribute(name = "updatepersonid", caption="信息更新人编号", length =36, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String updatepersonid;
    /**
     * CoContracttemplateVO构造函数
     */
    public CoContracttemplateVO() {
        super();
    }  
	
    /**
     * CoContracttemplateVO完整的构造函数
     */  
    public CoContracttemplateVO(String marketid,String contracttemplateid,BigDecimal isdelete){
        this.marketid = marketid;
        this.contracttemplateid = contracttemplateid;
        this.isdelete = isdelete;
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
     * 属性 contracttype 的get方法
     * @return String
     */
    public String getContracttype(){
        return contracttype;
    }
	
    /**
     * 属性 contracttype 的set方法
     * @return
     */
    public void setContracttype(String contracttype){
        this.contracttype = contracttype;
    } 
	
    /**
     * 属性 contracttemplateid 的get方法
     * @return String
     */
    public String getContracttemplateid(){
        return contracttemplateid;
    }
	
    /**
     * 属性 contracttemplateid 的set方法
     * @return
     */
    public void setContracttemplateid(String contracttemplateid){
        if(contracttemplateid != null && contracttemplateid.trim().length() == 0){
            this.contracttemplateid = null;
        }else{
            this.contracttemplateid = contracttemplateid;
        }
    } 
	
    /**
     * 属性 contracttemplatecode 的get方法
     * @return String
     */
    public String getContracttemplatecode(){
        return contracttemplatecode;
    }
	
    /**
     * 属性 contracttemplatecode 的set方法
     * @return
     */
    public void setContracttemplatecode(String contracttemplatecode){
        this.contracttemplatecode = contracttemplatecode;
    } 
	
    /**
     * 属性 contracttemplatename 的get方法
     * @return String
     */
    public String getContracttemplatename(){
        return contracttemplatename;
    }
	
    /**
     * 属性 contracttemplatename 的set方法
     * @return
     */
    public void setContracttemplatename(String contracttemplatename){
        this.contracttemplatename = contracttemplatename;
    } 
	
    /**
     * 属性 version 的get方法
     * @return String
     */
    public String getVersion(){
        return version;
    }
	
    /**
     * 属性 version 的set方法
     * @return
     */
    public void setVersion(String version){
        this.version = version;
    } 
	
    /**
     * 属性 issueddate 的get方法
     * @return Date
     */
    public Date getIssueddate(){
        return issueddate;
    }
	
    /**
     * 属性 issueddate 的set方法
     * @return
     */
    public void setIssueddate(Date issueddate){
        this.issueddate = issueddate;
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
     * 属性 isdelete 的get方法
     * @return BigDecimal
     */
    public BigDecimal getIsdelete(){
        return isdelete;
    }
	
    /**
     * 属性 isdelete 的set方法
     * @return
     */
    public void setIsdelete(BigDecimal isdelete){
        this.isdelete = isdelete;
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
            .append("marketid"+":"+getMarketid())
            .append("contracttype"+":"+getContracttype())
            .append("contracttemplateid"+":"+getContracttemplateid())
            .append("contracttemplatecode"+":"+getContracttemplatecode())
            .append("contracttemplatename"+":"+getContracttemplatename())
            .append("version"+":"+getVersion())
            .append("issueddate"+":"+getIssueddate())
            .append("starteffectivedate"+":"+getStarteffectivedate())
            .append("endeffectivedate"+":"+getEndeffectivedate())
            .append("effectiveflag"+":"+getEffectiveflag())
            .append("isdelete"+":"+getIsdelete())
            .append("updatetime"+":"+getUpdatetime())
            .append("updatepersonid"+":"+getUpdatepersonid())
            .toString(); 
			
    } 
   


}