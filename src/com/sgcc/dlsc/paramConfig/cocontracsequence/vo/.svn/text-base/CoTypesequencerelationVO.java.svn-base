package com.sgcc.dlsc.paramConfig.cocontracsequence.vo;

import java.io.Serializable;
import com.sgcc.uap.rest.annotation.attribute.EditorType;
import com.sgcc.uap.rest.annotation.attribute.AttributeType;
import com.sgcc.uap.rest.annotation.attribute.ViewAttribute;
import com.sgcc.uap.rest.support.ParentVO;

/**
 * CoTypesequencerelation的VO类
 *
 * @author  djdeng  [Tue Feb 18 09:22:07 CST 2014]
 * 
 */
public class CoTypesequencerelationVO extends ParentVO implements Serializable{

	private static final long serialVersionUID = 1L;

    /** 
     * 属性guid
     */
	@ViewAttribute(name = "guid", caption="??id", length =36, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String guid;
    /** 
     * 属性contracttypeid
     */
	@ViewAttribute(name = "contracttypeid", caption="????id", length =500, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String contracttypeid;
    /** 
     * 属性sequenceid
     */
	@ViewAttribute(name = "sequenceid", caption="????id", length =36, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String sequenceid;
    /**
     * CoTypesequencerelationVO构造函数
     */
    public CoTypesequencerelationVO() {
        super();
    }  
	
    /**
     * CoTypesequencerelationVO完整的构造函数
     */  
    public CoTypesequencerelationVO(String guid){
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
     * 属性 sequenceid 的get方法
     * @return String
     */
    public String getSequenceid(){
        return sequenceid;
    }
	
    /**
     * 属性 sequenceid 的set方法
     * @return
     */
    public void setSequenceid(String sequenceid){
        this.sequenceid = sequenceid;
    } 
	

    /**
     * toString方法
     * @return String
     */
	public String toString(){

		  return new StringBuffer()
            .append("guid"+":"+getGuid())
            .append("contracttypeid"+":"+getContracttypeid())
            .append("sequenceid"+":"+getSequenceid())
            .toString(); 
			
    } 
   


}