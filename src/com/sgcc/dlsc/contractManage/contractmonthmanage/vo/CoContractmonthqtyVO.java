package com.sgcc.dlsc.contractManage.contractmonthmanage.vo;
import java.math.BigDecimal;
import java.sql.Date;

import java.io.Serializable;
import com.sgcc.uap.rest.annotation.attribute.EditorType;
import com.sgcc.uap.rest.annotation.attribute.AttributeType;
import com.sgcc.uap.rest.annotation.attribute.ViewAttribute;
import com.sgcc.uap.rest.support.ParentVO;

/**
 * CoContractmonthqty的VO类
 *
 * @author  Administrator  [Fri Jun 27 15:17:18 CST 2014]
 * 
 */
public class CoContractmonthqtyVO extends ParentVO implements Serializable{

	private static final long serialVersionUID = 1L;

    /** 
     * 属性contractid
     */
	@ViewAttribute(name = "contractid", caption="合同ID", length =36, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String contractid;
    /** 
     * 属性janqty
     */
	@ViewAttribute(name = "janqty", caption="一月电量", length =20, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal janqty;
    /** 
     * 属性febqty
     */
	@ViewAttribute(name = "febqty", caption="二月电量", length =20, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal febqty;
    /** 
     * 属性marqty
     */
	@ViewAttribute(name = "marqty", caption="三月电量", length =20, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal marqty;
    /** 
     * 属性aprilqty
     */
	@ViewAttribute(name = "aprilqty", caption="四月电量", length =20, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal aprilqty;
    /** 
     * 属性mayqty
     */
	@ViewAttribute(name = "mayqty", caption="五月电量", length =20, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal mayqty;
    /** 
     * 属性junqty
     */
	@ViewAttribute(name = "junqty", caption="六月电量", length =20, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal junqty;
    /** 
     * 属性julyqty
     */
	@ViewAttribute(name = "julyqty", caption="七月电量", length =20, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal julyqty;
    /** 
     * 属性augqty
     */
	@ViewAttribute(name = "augqty", caption="八月电量", length =20, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal augqty;
    /** 
     * 属性sepqty
     */
	@ViewAttribute(name = "sepqty", caption="九月电量", length =20, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal sepqty;
    /** 
     * 属性octqty
     */
	@ViewAttribute(name = "octqty", caption="十月电量", length =20, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal octqty;
    /** 
     * 属性novqty
     */
	@ViewAttribute(name = "novqty", caption="十一月电量", length =20, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal novqty;
    /** 
     * 属性decqty
     */
	@ViewAttribute(name = "decqty", caption="十二月电量", length =20, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal decqty;
    /** 
     * 属性addpersonid
     */
	@ViewAttribute(name = "addpersonid", caption="新增人", length =36, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String addpersonid;
    /** 
     * 属性adddate
     */
	@ViewAttribute(name = "adddate", caption="新增时间", length =36, editor=EditorType.DateTimeEditor,  nullable=true, readOnly=false,  type=AttributeType.DATE) 
	private Date adddate;
    /** 
     * 属性updatepersonid
     */
	@ViewAttribute(name = "updatepersonid", caption="更新人", length =36, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String updatepersonid;
    /** 
     * 属性updatedate
     */
	@ViewAttribute(name = "updatedate", caption="更新时间", length =36, editor=EditorType.DateTimeEditor,  nullable=true, readOnly=false,  type=AttributeType.DATE) 
	private Date updatedate;
    /** 
     * 属性marketid
     */
	@ViewAttribute(name = "marketid", caption="市场ID", length =36, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String marketid;
    /** 
     * 属性monthqtytype
     */
	@ViewAttribute(name = "monthqtytype", caption="电量类型1,总电量2,替代方发电量3,替代方上网电量4,被替代方发电量5,被替代方上网电量", length =2, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal monthqtytype;
    /** 
     * 属性guid
     */
	@ViewAttribute(name = "guid", caption="GUID", length =36, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String guid;
    /**
     * CoContractmonthqtyVO构造函数
     */
    public CoContractmonthqtyVO() {
        super();
    }  
	
    /**
     * CoContractmonthqtyVO完整的构造函数
     */  
    public CoContractmonthqtyVO(String contractid,String guid){
        this.contractid = contractid;
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
        if(contractid != null && contractid.trim().length() == 0){
            this.contractid = null;
        }else{
            this.contractid = contractid;
        }
    } 
	
    /**
     * 属性 janqty 的get方法
     * @return BigDecimal
     */
    public BigDecimal getJanqty(){
        return janqty;
    }
	
    /**
     * 属性 janqty 的set方法
     * @return
     */
    public void setJanqty(BigDecimal janqty){
        this.janqty = janqty;
    } 
	
    /**
     * 属性 febqty 的get方法
     * @return BigDecimal
     */
    public BigDecimal getFebqty(){
        return febqty;
    }
	
    /**
     * 属性 febqty 的set方法
     * @return
     */
    public void setFebqty(BigDecimal febqty){
        this.febqty = febqty;
    } 
	
    /**
     * 属性 marqty 的get方法
     * @return BigDecimal
     */
    public BigDecimal getMarqty(){
        return marqty;
    }
	
    /**
     * 属性 marqty 的set方法
     * @return
     */
    public void setMarqty(BigDecimal marqty){
        this.marqty = marqty;
    } 
	
    /**
     * 属性 aprilqty 的get方法
     * @return BigDecimal
     */
    public BigDecimal getAprilqty(){
        return aprilqty;
    }
	
    /**
     * 属性 aprilqty 的set方法
     * @return
     */
    public void setAprilqty(BigDecimal aprilqty){
        this.aprilqty = aprilqty;
    } 
	
    /**
     * 属性 mayqty 的get方法
     * @return BigDecimal
     */
    public BigDecimal getMayqty(){
        return mayqty;
    }
	
    /**
     * 属性 mayqty 的set方法
     * @return
     */
    public void setMayqty(BigDecimal mayqty){
        this.mayqty = mayqty;
    } 
	
    /**
     * 属性 junqty 的get方法
     * @return BigDecimal
     */
    public BigDecimal getJunqty(){
        return junqty;
    }
	
    /**
     * 属性 junqty 的set方法
     * @return
     */
    public void setJunqty(BigDecimal junqty){
        this.junqty = junqty;
    } 
	
    /**
     * 属性 julyqty 的get方法
     * @return BigDecimal
     */
    public BigDecimal getJulyqty(){
        return julyqty;
    }
	
    /**
     * 属性 julyqty 的set方法
     * @return
     */
    public void setJulyqty(BigDecimal julyqty){
        this.julyqty = julyqty;
    } 
	
    /**
     * 属性 augqty 的get方法
     * @return BigDecimal
     */
    public BigDecimal getAugqty(){
        return augqty;
    }
	
    /**
     * 属性 augqty 的set方法
     * @return
     */
    public void setAugqty(BigDecimal augqty){
        this.augqty = augqty;
    } 
	
    /**
     * 属性 sepqty 的get方法
     * @return BigDecimal
     */
    public BigDecimal getSepqty(){
        return sepqty;
    }
	
    /**
     * 属性 sepqty 的set方法
     * @return
     */
    public void setSepqty(BigDecimal sepqty){
        this.sepqty = sepqty;
    } 
	
    /**
     * 属性 octqty 的get方法
     * @return BigDecimal
     */
    public BigDecimal getOctqty(){
        return octqty;
    }
	
    /**
     * 属性 octqty 的set方法
     * @return
     */
    public void setOctqty(BigDecimal octqty){
        this.octqty = octqty;
    } 
	
    /**
     * 属性 novqty 的get方法
     * @return BigDecimal
     */
    public BigDecimal getNovqty(){
        return novqty;
    }
	
    /**
     * 属性 novqty 的set方法
     * @return
     */
    public void setNovqty(BigDecimal novqty){
        this.novqty = novqty;
    } 
	
    /**
     * 属性 decqty 的get方法
     * @return BigDecimal
     */
    public BigDecimal getDecqty(){
        return decqty;
    }
	
    /**
     * 属性 decqty 的set方法
     * @return
     */
    public void setDecqty(BigDecimal decqty){
        this.decqty = decqty;
    } 
	
    /**
     * 属性 addpersonid 的get方法
     * @return String
     */
    public String getAddpersonid(){
        return addpersonid;
    }
	
    /**
     * 属性 addpersonid 的set方法
     * @return
     */
    public void setAddpersonid(String addpersonid){
        this.addpersonid = addpersonid;
    } 
	
    /**
     * 属性 adddate 的get方法
     * @return Date
     */
    public Date getAdddate(){
        return adddate;
    }
	
    /**
     * 属性 adddate 的set方法
     * @return
     */
    public void setAdddate(Date adddate){
        this.adddate = adddate;
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
     * 属性 monthqtytype 的get方法
     * @return BigDecimal
     */
    public BigDecimal getMonthqtytype(){
        return monthqtytype;
    }
	
    /**
     * 属性 monthqtytype 的set方法
     * @return
     */
    public void setMonthqtytype(BigDecimal monthqtytype){
        this.monthqtytype = monthqtytype;
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
        this.guid = guid;
    } 
	

    /**
     * toString方法
     * @return String
     */
	public String toString(){

		  return new StringBuffer()
            .append("contractid"+":"+getContractid())
            .append("janqty"+":"+getJanqty())
            .append("febqty"+":"+getFebqty())
            .append("marqty"+":"+getMarqty())
            .append("aprilqty"+":"+getAprilqty())
            .append("mayqty"+":"+getMayqty())
            .append("junqty"+":"+getJunqty())
            .append("julyqty"+":"+getJulyqty())
            .append("augqty"+":"+getAugqty())
            .append("sepqty"+":"+getSepqty())
            .append("octqty"+":"+getOctqty())
            .append("novqty"+":"+getNovqty())
            .append("decqty"+":"+getDecqty())
            .append("addpersonid"+":"+getAddpersonid())
            .append("adddate"+":"+getAdddate())
            .append("updatepersonid"+":"+getUpdatepersonid())
            .append("updatedate"+":"+getUpdatedate())
            .append("marketid"+":"+getMarketid())
            .append("monthqtytype"+":"+getMonthqtytype())
            .append("guid"+":"+getGuid())
            .toString(); 
			
    } 
   


}