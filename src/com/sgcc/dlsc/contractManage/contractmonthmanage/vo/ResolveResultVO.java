package com.sgcc.dlsc.contractManage.contractmonthmanage.vo;
import java.io.Serializable;

import com.sgcc.uap.rest.annotation.attribute.AttributeType;
import com.sgcc.uap.rest.annotation.attribute.EditorType;
import com.sgcc.uap.rest.annotation.attribute.ViewAttribute;
import com.sgcc.uap.rest.support.ParentVO;

/**
 * CoContractmonthqty的VO类
 *
 * @author  Administrator  [Fri Jun 27 15:17:18 CST 2014]
 * 
 */
public class ResolveResultVO extends ParentVO implements Serializable{

	private static final long serialVersionUID = 1L;

    /** 
     * 属性contractid
     */
	@ViewAttribute(name = "contractid", caption="合同ID", length =36, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String contractid;
	
	/** 
     * 属性contractname
     */
	@ViewAttribute(name = "contractname", caption="合同名称", length =36, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String contractname;
	/** 
     * 属性purchaserunitname
     */
	@ViewAttribute(name = "purchaserunitname", caption="购方经济机组", length =36, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String purchaserunitname;

	/** 
     * 属性sellerunitname
     */
	@ViewAttribute(name = "sellerunitname", caption="售方经济机组", length =36, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String sellerunitname;
    /** 
     * 属性janqty
     */
	@ViewAttribute(name = "janqty", caption="一月电量", length =20, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String janqty;
    /** 
     * 属性febqty
     */
	@ViewAttribute(name = "febqty", caption="二月电量", length =20, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String febqty;
    /** 
     * 属性marqty
     */
	@ViewAttribute(name = "marqty", caption="三月电量", length =20, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String marqty;
    /** 
     * 属性aprilqty
     */
	@ViewAttribute(name = "aprilqty", caption="四月电量", length =20, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String aprilqty;
    /** 
     * 属性mayqty
     */
	@ViewAttribute(name = "mayqty", caption="五月电量", length =20, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String mayqty;
    /** 
     * 属性junqty
     */
	@ViewAttribute(name = "junqty", caption="六月电量", length =20, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String junqty;
    /** 
     * 属性julyqty
     */
	@ViewAttribute(name = "julyqty", caption="七月电量", length =20, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String julyqty;
    /** 
     * 属性augqty
     */
	@ViewAttribute(name = "augqty", caption="八月电量", length =20, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String augqty;
    /** 
     * 属性sepqty
     */
	@ViewAttribute(name = "sepqty", caption="九月电量", length =20, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String sepqty;
    /** 
     * 属性octqty
     */
	@ViewAttribute(name = "octqty", caption="十月电量", length =20, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String octqty;
    /** 
     * 属性novqty
     */
	@ViewAttribute(name = "novqty", caption="十一月电量", length =20, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String novqty;
    /** 
     * 属性decqty
     */
	@ViewAttribute(name = "decqty", caption="十二月电量", length =20, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String decqty;
   
	
	@ViewAttribute(name = "guid", caption="GUID", length =36, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String guid;
    /**
     * CoContractmonthqtyVO构造函数
     */
    public ResolveResultVO() {
        super();
    }  
	
    /**
     * CoContractmonthqtyVO完整的构造函数
     */  
    public ResolveResultVO(String contractid,String guid){
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
     * @return String
     */
    public String getJanqty(){
        return janqty;
    }
	
    /**
     * 属性 janqty 的set方法
     * @return
     */
    public void setJanqty(String janqty){
        this.janqty = janqty;
    } 
	
    /**
     * 属性 febqty 的get方法
     * @return String
     */
    public String getFebqty(){
        return febqty;
    }
	
    /**
     * 属性 febqty 的set方法
     * @return
     */
    public void setFebqty(String febqty){
        this.febqty = febqty;
    } 
	
    /**
     * 属性 marqty 的get方法
     * @return String
     */
    public String getMarqty(){
        return marqty;
    }
	
    /**
     * 属性 marqty 的set方法
     * @return
     */
    public void setMarqty(String marqty){
        this.marqty = marqty;
    } 
	
    /**
     * 属性 aprilqty 的get方法
     * @return String
     */
    public String getAprilqty(){
        return aprilqty;
    }
	
    /**
     * 属性 aprilqty 的set方法
     * @return
     */
    public void setAprilqty(String aprilqty){
        this.aprilqty = aprilqty;
    } 
	
    /**
     * 属性 mayqty 的get方法
     * @return String
     */
    public String getMayqty(){
        return mayqty;
    }
	
    /**
     * 属性 mayqty 的set方法
     * @return
     */
    public void setMayqty(String mayqty){
        this.mayqty = mayqty;
    } 
	
    /**
     * 属性 junqty 的get方法
     * @return String
     */
    public String getJunqty(){
        return junqty;
    }
	
    /**
     * 属性 junqty 的set方法
     * @return
     */
    public void setJunqty(String junqty){
        this.junqty = junqty;
    } 
	
    /**
     * 属性 julyqty 的get方法
     * @return String
     */
    public String getJulyqty(){
        return julyqty;
    }
	
    /**
     * 属性 julyqty 的set方法
     * @return
     */
    public void setJulyqty(String julyqty){
        this.julyqty = julyqty;
    } 
	
    /**
     * 属性 augqty 的get方法
     * @return String
     */
    public String getAugqty(){
        return augqty;
    }
	
    /**
     * 属性 augqty 的set方法
     * @return
     */
    public void setAugqty(String augqty){
        this.augqty = augqty;
    } 
	
    /**
     * 属性 sepqty 的get方法
     * @return String
     */
    public String getSepqty(){
        return sepqty;
    }
	
    /**
     * 属性 sepqty 的set方法
     * @return
     */
    public void setSepqty(String sepqty){
        this.sepqty = sepqty;
    } 
	
    /**
     * 属性 octqty 的get方法
     * @return String
     */
    public String getOctqty(){
        return octqty;
    }
	
    /**
     * 属性 octqty 的set方法
     * @return
     */
    public void setOctqty(String octqty){
        this.octqty = octqty;
    } 
	
    /**
     * 属性 novqty 的get方法
     * @return String
     */
    public String getNovqty(){
        return novqty;
    }
	
    /**
     * 属性 novqty 的set方法
     * @return
     */
    public void setNovqty(String novqty){
        this.novqty = novqty;
    } 
	
    /**
     * 属性 decqty 的get方法
     * @return String
     */
    public String getDecqty(){
        return decqty;
    }
	
    /**
     * 属性 decqty 的set方法
     * @return
     */
    public void setDecqty(String decqty){
        this.decqty = decqty;
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
	
    public String getContractname() {
		return contractname;
	}

	public void setContractname(String contractname) {
		this.contractname = contractname;
	}

	public String getPurchaserunitname() {
		return purchaserunitname;
	}

	public void setPurchaserunitname(String purchaserunitname) {
		this.purchaserunitname = purchaserunitname;
	}

	public String getSellerunitname() {
		return sellerunitname;
	}

	public void setSellerunitname(String sellerunitname) {
		this.sellerunitname = sellerunitname;
	}
    /**
     * toString方法
     * @return String
     */
	public String toString(){

		  return new StringBuffer()
		  	.append("contractname"+":"+getContractname())
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
            .append("guid"+":"+getGuid())
            .toString(); 
    } 
}