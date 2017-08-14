package com.sgcc.dlsc.txtManage.tempfieldconfig.vo;
import java.util.Date;

import java.io.Serializable;
import com.sgcc.uap.rest.annotation.attribute.EditorType;
import com.sgcc.uap.rest.annotation.attribute.AttributeType;
import com.sgcc.uap.rest.annotation.attribute.ViewAttribute;
import com.sgcc.uap.rest.support.ParentVO;

/**
 * CoTempclienfieldconfig的VO类
 *
 * @author  mengke  [Mon May 26 14:14:20 CST 2014]
 * 
 */
public class CoTempclienfieldconfigVO extends ParentVO implements Serializable{

	private static final long serialVersionUID = 1L;

    /** 
     * 属性sheetid
     */
	@ViewAttribute(name = "sheetid", caption="主键", length =36, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String sheetid;
    /** 
     * 属性canSheetid
     */
	@ViewAttribute(name = "canSheetid", caption="范本中显示名称", length =36, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String canSheetid;
    /** 
     * 属性configFieldName
     */
	@ViewAttribute(name = "configFieldName", caption="CONFIG_FIELD_NAME", length =64, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String configFieldName;
    /** 
     * 属性personid
     */
	@ViewAttribute(name = "personid", caption="维护人ID", length =36, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String personid;
    /** 
     * 属性marketid
     */
	@ViewAttribute(name = "marketid", caption="维护人市场", length =36, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String marketid;
    /** 
     * 属性updatedate
     */
	@ViewAttribute(name = "updatedate", caption="维护时间", length =36, editor=EditorType.DateTimeEditor,  nullable=true, readOnly=false,  type=AttributeType.DATE) 
	private Date updatedate;
    /** 
     * 属性contracttemplatecode
     */
	@ViewAttribute(name = "contracttemplatecode", caption="合同范本编号", length =36, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String contracttemplatecode;
    /**
     * CoTempclienfieldconfigVO构造函数
     */
    public CoTempclienfieldconfigVO() {
        super();
    }  
	
    /**
     * CoTempclienfieldconfigVO完整的构造函数
     */  
    public CoTempclienfieldconfigVO(String sheetid){
        this.sheetid = sheetid;
    }
 
    /**
     * 属性 sheetid 的get方法
     * @return String
     */
    public String getSheetid(){
        return sheetid;
    }
	
    /**
     * 属性 sheetid 的set方法
     * @return
     */
    public void setSheetid(String sheetid){
        if(sheetid != null && sheetid.trim().length() == 0){
            this.sheetid = null;
        }else{
            this.sheetid = sheetid;
        }
    } 
	
    /**
     * 属性 canSheetid 的get方法
     * @return String
     */
    public String getCanSheetid(){
        return canSheetid;
    }
	
    /**
     * 属性 canSheetid 的set方法
     * @return
     */
    public void setCanSheetid(String canSheetid){
        this.canSheetid = canSheetid;
    } 
	
    /**
     * 属性 configFieldName 的get方法
     * @return String
     */
    public String getConfigFieldName(){
        return configFieldName;
    }
	
    /**
     * 属性 configFieldName 的set方法
     * @return
     */
    public void setConfigFieldName(String configFieldName){
        this.configFieldName = configFieldName;
    } 
	
    /**
     * 属性 personid 的get方法
     * @return String
     */
    public String getPersonid(){
        return personid;
    }
	
    /**
     * 属性 personid 的set方法
     * @return
     */
    public void setPersonid(String personid){
        this.personid = personid;
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
     * 属性 updatedate 的get方法
     * @return Date
     */
    public Date getUpdatedate(){
        return updatedate;
    }
	
    /**
     * 属性 updatedate 的set方法
     * @return
     */
    public void setUpdatedate(Date updatedate){
        this.updatedate = updatedate;
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
     * toString方法
     * @return String
     */
	public String toString(){

		  return new StringBuffer()
            .append("sheetid"+":"+getSheetid())
            .append("canSheetid"+":"+getCanSheetid())
            .append("configFieldName"+":"+getConfigFieldName())
            .append("personid"+":"+getPersonid())
            .append("marketid"+":"+getMarketid())
            .append("updatedate"+":"+getUpdatedate())
            .append("contracttemplatecode"+":"+getContracttemplatecode())
            .toString(); 
			
    } 
   


}