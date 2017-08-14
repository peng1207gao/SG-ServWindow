package com.sgcc.dlsc.txtManage.CoContractparamdic.vo;
import java.util.Date;
import java.math.BigDecimal;

import java.io.Serializable;
import com.sgcc.uap.rest.annotation.attribute.EditorType;
import com.sgcc.uap.rest.annotation.attribute.AttributeType;
import com.sgcc.uap.rest.annotation.attribute.ViewAttribute;
import com.sgcc.uap.rest.support.ParentVO;

/**
 * CoContractparamdic的VO类
 *
 * @author  xiabaike  [Mon May 26 10:17:16 CST 2014]
 * 
 */
public class CoContractparamdicVO extends ParentVO implements Serializable{

	private static final long serialVersionUID = 1L;

    /** 
     * 属性sheetid
     */
	@ViewAttribute(name = "sheetid", caption="主键", length =36, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String sheetid;
    /** 
     * 属性dicName
     */
	@ViewAttribute(name = "dicName", caption="标签名称", length =200, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String dicName;
    /** 
     * 属性dicValue
     */
	@ViewAttribute(name = "dicValue", caption="标签值", length =500, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String dicValue;
    /** 
     * 属性dicContent
     */
	@ViewAttribute(name = "dicContent", caption="说明", length =500, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String dicContent;
    /** 
     * 属性operateUser
     */
	@ViewAttribute(name = "operateUser", caption="维护人", length =36, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String operateUser;
    /** 
     * 属性operateDate
     */
	@ViewAttribute(name = "operateDate", caption="维护时间", length =36, editor=EditorType.DateTimeEditor,  nullable=true, readOnly=false,  type=AttributeType.DATE) 
	private Date operateDate;
    /** 
     * 属性updateUser
     */
	@ViewAttribute(name = "updateUser", caption="更新人", length =36, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String updateUser;
    /** 
     * 属性updateDate
     */
	@ViewAttribute(name = "updateDate", caption="更新时间", length =36, editor=EditorType.DateTimeEditor,  nullable=true, readOnly=false,  type=AttributeType.DATE) 
	private Date updateDate;
    /** 
     * 属性orderNo
     */
	@ViewAttribute(name = "orderNo", caption="是否显示", length =22, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal orderNo;
    /**
     * CoContractparamdicVO构造函数
     */
    public CoContractparamdicVO() {
        super();
    }  
	
    /**
     * CoContractparamdicVO完整的构造函数
     */  
    public CoContractparamdicVO(String sheetid){
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
     * 属性 dicName 的get方法
     * @return String
     */
    public String getDicName(){
        return dicName;
    }
	
    /**
     * 属性 dicName 的set方法
     * @return
     */
    public void setDicName(String dicName){
        this.dicName = dicName;
    } 
	
    /**
     * 属性 dicValue 的get方法
     * @return String
     */
    public String getDicValue(){
        return dicValue;
    }
	
    /**
     * 属性 dicValue 的set方法
     * @return
     */
    public void setDicValue(String dicValue){
        this.dicValue = dicValue;
    } 
	
    /**
     * 属性 dicContent 的get方法
     * @return String
     */
    public String getDicContent(){
        return dicContent;
    }
	
    /**
     * 属性 dicContent 的set方法
     * @return
     */
    public void setDicContent(String dicContent){
        this.dicContent = dicContent;
    } 
	
    /**
     * 属性 operateUser 的get方法
     * @return String
     */
    public String getOperateUser(){
        return operateUser;
    }
	
    /**
     * 属性 operateUser 的set方法
     * @return
     */
    public void setOperateUser(String operateUser){
        this.operateUser = operateUser;
    } 
	
    /**
     * 属性 operateDate 的get方法
     * @return Date
     */
    public Date getOperateDate(){
        return operateDate;
    }
	
    /**
     * 属性 operateDate 的set方法
     * @return
     */
    public void setOperateDate(Date operateDate){
        this.operateDate = operateDate;
    } 
	
    /**
     * 属性 updateUser 的get方法
     * @return String
     */
    public String getUpdateUser(){
        return updateUser;
    }
	
    /**
     * 属性 updateUser 的set方法
     * @return
     */
    public void setUpdateUser(String updateUser){
        this.updateUser = updateUser;
    } 
	
    /**
     * 属性 updateDate 的get方法
     * @return Date
     */
    public Date getUpdateDate(){
        return updateDate;
    }
	
    /**
     * 属性 updateDate 的set方法
     * @return
     */
    public void setUpdateDate(Date updateDate){
        this.updateDate = updateDate;
    } 
	
    /**
     * 属性 orderNo 的get方法
     * @return BigDecimal
     */
    public BigDecimal getOrderNo(){
        return orderNo;
    }
	
    /**
     * 属性 orderNo 的set方法
     * @return
     */
    public void setOrderNo(BigDecimal orderNo){
        this.orderNo = orderNo;
    } 
	

    /**
     * toString方法
     * @return String
     */
	public String toString(){

		  return new StringBuffer()
            .append("sheetid"+":"+getSheetid())
            .append("dicName"+":"+getDicName())
            .append("dicValue"+":"+getDicValue())
            .append("dicContent"+":"+getDicContent())
            .append("operateUser"+":"+getOperateUser())
            .append("operateDate"+":"+getOperateDate())
            .append("updateUser"+":"+getUpdateUser())
            .append("updateDate"+":"+getUpdateDate())
            .append("orderNo"+":"+getOrderNo())
            .toString(); 
			
    } 
   


}