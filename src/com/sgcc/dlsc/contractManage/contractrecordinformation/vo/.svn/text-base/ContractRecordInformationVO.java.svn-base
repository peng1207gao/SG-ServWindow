package com.sgcc.dlsc.contractManage.contractrecordinformation.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.sgcc.uap.rest.annotation.attribute.EditorType;
import com.sgcc.uap.rest.annotation.attribute.AttributeType;
import com.sgcc.uap.rest.annotation.attribute.ViewAttribute;
import com.sgcc.uap.rest.support.ParentVO;

/**
 * TjfxMaplineid的VO类
 *
 * @author  Jingbo.Fu  [Wed Feb 12 11:27:25 CST 2014]
 * 
 */
public class ContractRecordInformationVO extends ParentVO implements Serializable{

	private static final long serialVersionUID = 1L;

	@ViewAttribute(name = "contracrid", caption="CONTRACRID", length =32, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String contracrid;
	
	@ViewAttribute(name = "guidd", caption="GUIDD", length =32, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String guidd;
	@ViewAttribute(name = "description", caption="description", length =32, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String description;
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	@ViewAttribute(name = "status", caption="STATUS", length =32, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String status;
	
	@ViewAttribute(name = "backuporg", caption="BACKUPORG", length =32, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String backuporg;
	
	@ViewAttribute(name = "ctype", caption="ctype", length =32, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String ctype;
	
	@ViewAttribute(name = "backupperson", caption="BACKUPPERSON", length =32, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String backupperson;
	
	@ViewAttribute(name = "backupdate", caption="BACKUPDATE", length =32, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String backupdate;
	
	@ViewAttribute(name = "contractnum", caption="CONTRACTNUM", length =32, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String contractnum;
	
	@ViewAttribute(name = "backuptype", caption="backuptype", length =32, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private BigDecimal backuptype; 
	
	@ViewAttribute(name = "backupstatus", caption="backupstatus", length =32, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private BigDecimal backupstatus;
	
	
	
    public BigDecimal getBackuptype() {
		return backuptype;
	}

	public void setBackuptype(BigDecimal backuptype) {
		this.backuptype = backuptype;
	}

	public BigDecimal getBackupstatus() {
		return backupstatus;
	}

	public void setBackupstatus(BigDecimal backupstatus) {
		this.backupstatus = backupstatus;
	}

	public String getContracrid() {
		return contracrid;
	}

	public void setContracrid(String contracrid) {
		this.contracrid = contracrid;
	}

	public String getGuidd() {
		return guidd;
	}

	public void setGuidd(String guidd) {
		this.guidd = guidd;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBackuporg() {
		return backuporg;
	}

	public void setBackuporg(String backuporg) {
		this.backuporg = backuporg;
	}

	public String getCtype() {
		return ctype;
	}

	public void setCtype(String ctype) {
		this.ctype = ctype;
	}

	public String getBackupperson() {
		return backupperson;
	}

	public void setBackupperson(String backupperson) {
		this.backupperson = backupperson;
	}

	public String getBackupdate() {
		return backupdate;
	}

	public void setBackupdate(String backupdate) {
		this.backupdate = backupdate;
	}

	public String getContractnum() {
		return contractnum;
	}

	public void setContractnum(String contractnum) {
		this.contractnum = contractnum;
	}


	/** 
     * 属性guid
     */
	@ViewAttribute(name = "guid", caption="GUID", length =32, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String guid;
    /** 
     * 属性maplineid
     */
	@ViewAttribute(name = "maplineid", caption="MAPLINEID", length =32, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String maplineid;
    /** 
     * 属性maplinename
     */
	@ViewAttribute(name = "maplinename", caption="MAPLINENAME", length =64, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String maplinename;
    /** 
     * 属性linkid
     */
	@ViewAttribute(name = "linkid", caption="LINKID", length =32, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String linkid;
    /** 
     * 属性tradetype
     */
	@ViewAttribute(name = "tradetype", caption="TRADETYPE", length =64, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String tradetype;
    /**
     * TjfxMaplineidVO构造函数
     */
    public ContractRecordInformationVO() {
        super();
    }  
	
    /**
     * TjfxMaplineidVO完整的构造函数
     */  
    public ContractRecordInformationVO(String guid,String maplineid,String maplinename,String linkid,String tradetype){
        this.guid = guid;
        this.maplineid = maplineid;
        this.maplinename = maplinename;
        this.linkid = linkid;
        this.tradetype = tradetype;
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
     * 属性 maplineid 的get方法
     * @return String
     */
    public String getMaplineid(){
        return maplineid;
    }
	
    /**
     * 属性 maplineid 的set方法
     * @return
     */
    public void setMaplineid(String maplineid){
        this.maplineid = maplineid;
    } 
	
    /**
     * 属性 maplinename 的get方法
     * @return String
     */
    public String getMaplinename(){
        return maplinename;
    }
	
    /**
     * 属性 maplinename 的set方法
     * @return
     */
    public void setMaplinename(String maplinename){
        this.maplinename = maplinename;
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
     * 属性 tradetype 的get方法
     * @return String
     */
    public String getTradetype(){
        return tradetype;
    }
	
    /**
     * 属性 tradetype 的set方法
     * @return
     */
    public void setTradetype(String tradetype){
        this.tradetype = tradetype;
    } 
	

    /**
     * toString方法
     * @return String
     */
	public String toString(){

		  return new StringBuffer()
            .append("guid"+":"+getGuid())
            .append("maplineid"+":"+getMaplineid())
            .append("maplinename"+":"+getMaplinename())
            .append("linkid"+":"+getLinkid())
            .append("tradetype"+":"+getTradetype())
            .append("contracrid"+":"+getContracrid())
            .append("guidd"+":"+getGuidd())
            .append("status"+":"+getStatus())
            .append("backuporg"+":"+getBackuporg())
            .append("ctype"+":"+getCtype())
            .append("backupperson"+":"+getBackupperson())
            .append("backupdate"+":"+getBackupdate())
            .append("contractnum"+":"+getContractnum())//description
            .append("description"+":"+getDescription())
            .toString(); 
			
    } 
   


}