package com.sgcc.dlsc.contractManage.coContractbackupInfo.vo;

import java.util.Date;
import java.math.BigDecimal;

import java.io.Serializable;
import com.sgcc.uap.rest.annotation.attribute.EditorType;
import com.sgcc.uap.rest.annotation.attribute.AttributeType;
import com.sgcc.uap.rest.annotation.attribute.ViewAttribute;
import com.sgcc.uap.rest.support.ParentVO;

/**
 * CoContractbackupinfo的VO类
 *
 * @author  xiabaike  [Thu Jan 23 09:08:17 CST 2014]
 * 
 */
public class CoContractbackupinfoVO extends ParentVO implements Serializable{

	private static final long serialVersionUID = 1L;

    /** 
     * 属性contractid
     */
	@ViewAttribute(name = "contractid", caption="CONTRACTID", length =36, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String contractid;
    /** 
     * 属性backupdate
     */
	@ViewAttribute(name = "backupdate", caption="BACKUPDATE", length =36, editor=EditorType.DateTimeEditor,  nullable=true, readOnly=false,  type=AttributeType.DATE) 
	private Date backupdate;
    /** 
     * 属性backupperson
     */
	@ViewAttribute(name = "backupperson", caption="BACKUPPERSON", length =50, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String backupperson;
    /** 
     * 属性backuporg
     */
	@ViewAttribute(name = "backuporg", caption="BACKUPORG", length =64, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String backuporg;
    /** 
     * 属性backuptype
     */
	@ViewAttribute(name = "backuptype", caption="1??????2??????3?? ", length =2, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal backuptype;
    /** 
     * 属性contractnum
     */
	@ViewAttribute(name = "contractnum", caption="CONTRACTNUM", length =3, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal contractnum;
    /** 
     * 属性backupstatus
     */
	@ViewAttribute(name = "backupstatus", caption="1?????0???? ", length =2, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal backupstatus;
    /** 
     * 属性description
     */
	@ViewAttribute(name = "description", caption="DESCRIPTION", length =256, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String description;
    /** 
     * 属性guid
     */
	@ViewAttribute(name = "guid", caption="GUID", length =36, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String guid;
    /**
     * CoContractbackupinfoVO构造函数
     */
    public CoContractbackupinfoVO() {
        super();
    }  
	
    /**
     * CoContractbackupinfoVO完整的构造函数
     */  
    public CoContractbackupinfoVO(String contractid,String backuporg,String guid){
        this.contractid = contractid;
        this.backuporg = backuporg;
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
     * 属性 backupdate 的get方法
     * @return Date
     */
    public Date getBackupdate(){
        return backupdate;
    }
	
    /**
     * 属性 backupdate 的set方法
     * @return
     */
    public void setBackupdate(Date backupdate){
        this.backupdate = backupdate;
    } 
	
    /**
     * 属性 backupperson 的get方法
     * @return String
     */
    public String getBackupperson(){
        return backupperson;
    }
	
    /**
     * 属性 backupperson 的set方法
     * @return
     */
    public void setBackupperson(String backupperson){
        this.backupperson = backupperson;
    } 
	
    /**
     * 属性 backuporg 的get方法
     * @return String
     */
    public String getBackuporg(){
        return backuporg;
    }
	
    /**
     * 属性 backuporg 的set方法
     * @return
     */
    public void setBackuporg(String backuporg){
        this.backuporg = backuporg;
    } 
	
    /**
     * 属性 backuptype 的get方法
     * @return BigDecimal
     */
    public BigDecimal getBackuptype(){
        return backuptype;
    }
	
    /**
     * 属性 backuptype 的set方法
     * @return
     */
    public void setBackuptype(BigDecimal backuptype){
        this.backuptype = backuptype;
    } 
	
    /**
     * 属性 contractnum 的get方法
     * @return BigDecimal
     */
    public BigDecimal getContractnum(){
        return contractnum;
    }
	
    /**
     * 属性 contractnum 的set方法
     * @return
     */
    public void setContractnum(BigDecimal contractnum){
        this.contractnum = contractnum;
    } 
	
    /**
     * 属性 backupstatus 的get方法
     * @return BigDecimal
     */
    public BigDecimal getBackupstatus(){
        return backupstatus;
    }
	
    /**
     * 属性 backupstatus 的set方法
     * @return
     */
    public void setBackupstatus(BigDecimal backupstatus){
        this.backupstatus = backupstatus;
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
            .append("backupdate"+":"+getBackupdate())
            .append("backupperson"+":"+getBackupperson())
            .append("backuporg"+":"+getBackuporg())
            .append("backuptype"+":"+getBackuptype())
            .append("contractnum"+":"+getContractnum())
            .append("backupstatus"+":"+getBackupstatus())
            .append("description"+":"+getDescription())
            .append("guid"+":"+getGuid())
            .toString(); 
			
    } 
   


}