package com.sgcc.dlsc.paramConfig.cocontracsequence.vo;
import java.math.BigDecimal;
import java.util.Date;

import java.io.Serializable;
import com.sgcc.uap.rest.annotation.attribute.EditorType;
import com.sgcc.uap.rest.annotation.attribute.AttributeType;
import com.sgcc.uap.rest.annotation.attribute.ViewAttribute;
import com.sgcc.uap.rest.support.ParentVO;

/**
 * CoContractSequenceinfo的VO类
 *
 * @author  djdeng  [Tue Feb 18 09:19:33 CST 2014]
 * 
 */
public class CoContractSequenceinfoVO extends ParentVO implements Serializable{

	private static final long serialVersionUID = 1L;

    /** 
     * 属性sequenceid
     */
	@ViewAttribute(name = "maintenancePer", caption="维护人", length =36, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String maintenancePer;
	
	
	@ViewAttribute(name = "sequenceid", caption="合同序列id", length =36, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String sequenceid;
    /** 
     * 属性sequencename
     */
	@ViewAttribute(name = "sequencename", caption="合同序列名称", length =64, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String sequencename;
    /** 
     * 属性sequencetype
     */
	@ViewAttribute(name = "sequencetype", caption="合同序列类型", length =2, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal sequencetype;
	
	/** 
     * 属性sequencetype
     */
	@ViewAttribute(name = "sequencetypeStr", caption="合同序列类型", length =2, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String sequencetypeStr;
    /** 
     * 属性marketid
     */
	@ViewAttribute(name = "marketid", caption="市场ID", length =64, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String marketid;
    /** 
     * 属性updatetime
     */
	@ViewAttribute(name = "updatetime", caption="信息更新时间", length =64, editor=EditorType.DateTimeEditor,  nullable=true, readOnly=false,  type=AttributeType.DATE) 
	private Date updatetime;
    /** 
     * 属性updatepersonid
     */
	@ViewAttribute(name = "updatepersonid", caption="信息更新人编号", length =36, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String updatepersonid;
    /** 
     * 属性sequencecircle
     */
	@ViewAttribute(name = "sequencecircle", caption="合同序列周期", length =2, editor=EditorType.DropDownEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal sequencecircle;
	
	 /** 
     * 属性sequencecircle
     */
	@ViewAttribute(name = "sequencecircleStr", caption="合同序列周期", length =2, editor=EditorType.DropDownEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String sequencecircleStr;
	
    /** 
     * 属性createdate
     */
	@ViewAttribute(name = "createdate", caption="创建时间", length =2, editor=EditorType.DateTimeEditor,  nullable=true, readOnly=false,  type=AttributeType.DATE) 
	private Date createdate;
	
	/** 
     * 属性createdate
     */
	@ViewAttribute(name = "contractType", caption="合同类型", length =56, editor=EditorType.DropDownTreeEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String contractType;
	
	/** 
     * 属性createdate
     */
	@ViewAttribute(name = "tenId", caption="临时id", length =56, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String tenId;
	
	/** 
     * 属性contractTypeName
     */
	@ViewAttribute(name = "contractTypeName", caption="合同类型名称", editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String contractTypeName;
	
    /**
     * CoContractSequenceinfoVO构造函数
     */
    public CoContractSequenceinfoVO() {
        super();
    }  
	
    /**
     * CoContractSequenceinfoVO完整的构造函数
     */  
    public CoContractSequenceinfoVO(String sequenceid){
        this.sequenceid = sequenceid;
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
        if(sequenceid != null && sequenceid.trim().length() == 0){
            this.sequenceid = null;
        }else{
            this.sequenceid = sequenceid;
        }
    } 
	
    /**
     * 属性 sequencename 的get方法
     * @return String
     */
    public String getSequencename(){
        return sequencename;
    }
	
    /**
     * 属性 sequencename 的set方法
     * @return
     */
    public void setSequencename(String sequencename){
        this.sequencename = sequencename;
    } 
	
    /**
     * 属性 sequencetype 的get方法
     * @return BigDecimal
     */
    public BigDecimal getSequencetype(){
        return sequencetype;
    }
	
    /**
     * 属性 sequencetype 的set方法
     * @return
     */
    public void setSequencetype(BigDecimal sequencetype){
        this.sequencetype = sequencetype;
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
     * 属性 sequencecircle 的get方法
     * @return BigDecimal
     */
    public BigDecimal getSequencecircle(){
        return sequencecircle;
    }
	
    /**
     * 属性 sequencecircle 的set方法
     * @return
     */
    public void setSequencecircle(BigDecimal sequencecircle){
        this.sequencecircle = sequencecircle;
    } 
	
    /**
     * 属性 createdate 的get方法
     * @return Date
     */
    public Date getCreatedate(){
        return createdate;
    }
	
    /**
     * 属性 createdate 的set方法
     * @return
     */
    public void setCreatedate(Date createdate){
        this.createdate = createdate;
    } 

    public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	/**
     * toString方法
     * @return String
     */
	public String toString(){

		  return new StringBuffer()
            .append("sequenceid"+":"+getSequenceid())
            .append("sequencename"+":"+getSequencename())
            .append("sequencetype"+":"+getSequencetype())
            .append("marketid"+":"+getMarketid())
            .append("updatetime"+":"+getUpdatetime())
            .append("updatepersonid"+":"+getUpdatepersonid())
            .append("sequencecircle"+":"+getSequencecircle())
            .append("createdate"+":"+getCreatedate())
            .toString(); 
			
    }

	public String getSequencetypeStr() {
		return sequencetypeStr;
	}

	public void setSequencetypeStr(String sequencetypeStr) {
		this.sequencetypeStr = sequencetypeStr;
	}

	public String getSequencecircleStr() {
		return sequencecircleStr;
	}

	public void setSequencecircleStr(String sequencecircleStr) {
		this.sequencecircleStr = sequencecircleStr;
	}

	public String getMaintenancePer() {
		return maintenancePer;
	}

	public void setMaintenancePer(String maintenancePer) {
		this.maintenancePer = maintenancePer;
	}

	public String getTenId() {
		return tenId;
	}

	public void setTenId(String tenId) {
		this.tenId = tenId;
	}

	public String getContractTypeName() {
		return contractTypeName;
	}

	public void setContractTypeName(String contractTypeName) {
		this.contractTypeName = contractTypeName;
	} 
   



}