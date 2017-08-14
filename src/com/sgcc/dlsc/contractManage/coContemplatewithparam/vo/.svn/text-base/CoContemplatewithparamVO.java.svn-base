package com.sgcc.dlsc.contractManage.coContemplatewithparam.vo;
import java.util.Date;
import java.math.BigDecimal;

import java.io.Serializable;
import com.sgcc.uap.rest.annotation.attribute.EditorType;
import com.sgcc.uap.rest.annotation.attribute.AttributeType;
import com.sgcc.uap.rest.annotation.attribute.ViewAttribute;
import com.sgcc.uap.rest.support.ParentVO;

/**
 * CoContemplatewithparam的VO类
 *
 * @author  thinpad  [Wed Feb 19 10:21:42 CST 2014]
 * 
 */
public class CoContemplatewithparamVO extends ParentVO implements Serializable{

	private static final long serialVersionUID = 1L;

    /** 
     * 属性marketid
     */
	@ViewAttribute(name = "marketid", caption="??ID", length =36, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String marketid;
    /** 
     * 属性contracttype
     */
	@ViewAttribute(name = "contracttype", caption="????", length =64, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String contracttype;
    /** 
     * 属性contracttemplatecode
     */
	@ViewAttribute(name = "contracttemplatecode", caption="??????", length =36, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String contracttemplatecode;
    /** 
     * 属性contracttemplatename
     */
	@ViewAttribute(name = "contracttemplatename", caption="??????", length =100, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String contracttemplatename;
    /** 
     * 属性contracttemplatefile
     */
	@ViewAttribute(name = "contracttemplatefile", caption="??????", length =100, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BYTEARRAY) 
	private byte[] contracttemplatefile;
    /** 
     * 属性version
     */
	@ViewAttribute(name = "version", caption="???", length =16, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String version;
    /** 
     * 属性issueddate
     */
	@ViewAttribute(name = "issueddate", caption="????", length =16, editor=EditorType.DateTimeEditor,  nullable=true, readOnly=false,  type=AttributeType.DATE) 
	private Date issueddate;
    /** 
     * 属性starteffectivedate
     */
	@ViewAttribute(name = "starteffectivedate", caption="????", length =16, editor=EditorType.DateTimeEditor,  nullable=true, readOnly=false,  type=AttributeType.DATE) 
	private Date starteffectivedate;
    /** 
     * 属性endeffectivedate
     */
	@ViewAttribute(name = "endeffectivedate", caption="????", length =16, editor=EditorType.DateTimeEditor,  nullable=true, readOnly=false,  type=AttributeType.DATE) 
	private Date endeffectivedate;
    /** 
     * 属性effectiveflag
     */
	@ViewAttribute(name = "effectiveflag", caption="????", length =1, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal effectiveflag;
    /** 
     * 属性isshare
     */
	@ViewAttribute(name = "isshare", caption="ISSHARE", length =1, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal isshare;
    /** 
     * 属性descreption
     */
	@ViewAttribute(name = "descreption", caption="DESCREPTION", length =256, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String descreption;
    /**
     * CoContemplatewithparamVO构造函数
     */
    public CoContemplatewithparamVO() {
        super();
    }  
	
    /**
     * CoContemplatewithparamVO完整的构造函数
     */  
    public CoContemplatewithparamVO(String marketid,String contracttemplatecode){
        this.marketid = marketid;
        this.contracttemplatecode = contracttemplatecode;
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
        if(contracttemplatecode != null && contracttemplatecode.trim().length() == 0){
            this.contracttemplatecode = null;
        }else{
            this.contracttemplatecode = contracttemplatecode;
        }
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
     * 属性 contracttemplatefile 的get方法
     * @return byte[]
     */
    public byte[] getContracttemplatefile(){
        return contracttemplatefile;
    }
	
    /**
     * 属性 contracttemplatefile 的set方法
     * @return
     */
    public void setContracttemplatefile(byte[] contracttemplatefile){
        this.contracttemplatefile = contracttemplatefile;
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
     * 属性 isshare 的get方法
     * @return BigDecimal
     */
    public BigDecimal getIsshare(){
        return isshare;
    }
	
    /**
     * 属性 isshare 的set方法
     * @return
     */
    public void setIsshare(BigDecimal isshare){
        this.isshare = isshare;
    } 
	
    /**
     * 属性 descreption 的get方法
     * @return String
     */
    public String getDescreption(){
        return descreption;
    }
	
    /**
     * 属性 descreption 的set方法
     * @return
     */
    public void setDescreption(String descreption){
        this.descreption = descreption;
    } 
	

    /**
     * toString方法
     * @return String
     */
	public String toString(){

		  return new StringBuffer()
            .append("marketid"+":"+getMarketid())
            .append("contracttype"+":"+getContracttype())
            .append("contracttemplatecode"+":"+getContracttemplatecode())
            .append("contracttemplatename"+":"+getContracttemplatename())
            .append("contracttemplatefile"+":"+getContracttemplatefile())
            .append("version"+":"+getVersion())
            .append("issueddate"+":"+getIssueddate())
            .append("starteffectivedate"+":"+getStarteffectivedate())
            .append("endeffectivedate"+":"+getEndeffectivedate())
            .append("effectiveflag"+":"+getEffectiveflag())
            .append("isshare"+":"+getIsshare())
            .append("descreption"+":"+getDescreption())
            .toString(); 
			
    } 
   


}