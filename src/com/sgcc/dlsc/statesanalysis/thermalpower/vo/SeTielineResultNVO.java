package com.sgcc.dlsc.statesanalysis.thermalpower.vo;
import java.sql.Date;
import java.math.BigDecimal;

import java.io.Serializable;
import com.sgcc.uap.rest.annotation.attribute.EditorType;
import com.sgcc.uap.rest.annotation.attribute.AttributeType;
import com.sgcc.uap.rest.annotation.attribute.ViewAttribute;
import com.sgcc.uap.rest.support.ParentVO;

/**
 * SeTielineResultN的VO类
 *
 * @author  DELL  [Wed Apr 23 15:32:25 CST 2014]
 * 
 */
public class SeTielineResultNVO extends ParentVO implements Serializable{

	private static final long serialVersionUID = 1L;

    /** 
     * 属性guid
     */
	@ViewAttribute(name = "guid", caption="???", length =36, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String guid;
    /** 
     * 属性marketId
     */
	@ViewAttribute(name = "marketId", caption="??????ID", length =32, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String marketId;
    /** 
     * 属性mktYear
     */
	@ViewAttribute(name = "mktYear", caption="??", length =32, editor=EditorType.DateTimeEditor,  nullable=false, readOnly=false,  type=AttributeType.DATE) 
	private Date mktYear;
    /** 
     * 属性tielineId
     */
	@ViewAttribute(name = "tielineId", caption="???ID", length =32, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String tielineId;
    /** 
     * 属性tielineIdN
     */
	@ViewAttribute(name = "tielineIdN", caption="???ID", length =20, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal tielineIdN;
    /** 
     * 属性saleParticipantid
     */
	@ViewAttribute(name = "saleParticipantid", caption="???????ID", length =32, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String saleParticipantid;
    /** 
     * 属性vendeeParticipantid
     */
	@ViewAttribute(name = "vendeeParticipantid", caption="???????ID", length =32, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String vendeeParticipantid;
    /** 
     * 属性recipientParticipantid
     */
	@ViewAttribute(name = "recipientParticipantid", caption="???????ID", length =32, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String recipientParticipantid;
    /** 
     * 属性payerParticipantid
     */
	@ViewAttribute(name = "payerParticipantid", caption="???????ID", length =32, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String payerParticipantid;
    /** 
     * 属性contractId
     */
	@ViewAttribute(name = "contractId", caption="??ID", length =32, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String contractId;
    /** 
     * 属性sbstypeId
     */
	@ViewAttribute(name = "sbstypeId", caption="??????ID", length =32, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String sbstypeId;
    /** 
     * 属性energyT
     */
	@ViewAttribute(name = "energyT", caption="???", length =20, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal energyT;
    /** 
     * 属性priceT
     */
	@ViewAttribute(name = "priceT", caption="???", length =12, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal priceT;
    /** 
     * 属性feeT
     */
	@ViewAttribute(name = "feeT", caption="???", length =20, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal feeT;
    /** 
     * 属性energyS
     */
	@ViewAttribute(name = "energyS", caption="???", length =20, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal energyS;
    /** 
     * 属性priceS
     */
	@ViewAttribute(name = "priceS", caption="???", length =12, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal priceS;
    /** 
     * 属性feeS
     */
	@ViewAttribute(name = "feeS", caption="???", length =20, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal feeS;
    /** 
     * 属性energyP
     */
	@ViewAttribute(name = "energyP", caption="???", length =20, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal energyP;
    /** 
     * 属性priceP
     */
	@ViewAttribute(name = "priceP", caption="???", length =12, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal priceP;
    /** 
     * 属性feeP
     */
	@ViewAttribute(name = "feeP", caption="???", length =20, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal feeP;
    /** 
     * 属性energyF
     */
	@ViewAttribute(name = "energyF", caption="???", length =20, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal energyF;
    /** 
     * 属性priceF
     */
	@ViewAttribute(name = "priceF", caption="???", length =12, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal priceF;
    /** 
     * 属性feeF
     */
	@ViewAttribute(name = "feeF", caption="???", length =20, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal feeF;
    /** 
     * 属性energyB
     */
	@ViewAttribute(name = "energyB", caption="???", length =20, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal energyB;
    /** 
     * 属性priceB
     */
	@ViewAttribute(name = "priceB", caption="???", length =12, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal priceB;
    /** 
     * 属性feeB
     */
	@ViewAttribute(name = "feeB", caption="???", length =20, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal feeB;
    /**
     * SeTielineResultNVO构造函数
     */
    public SeTielineResultNVO() {
        super();
    }  
	
    /**
     * SeTielineResultNVO完整的构造函数
     */  
    public SeTielineResultNVO(String guid,String marketId,Date mktYear,String tielineId,String saleParticipantid,String vendeeParticipantid,String recipientParticipantid,String payerParticipantid,String contractId,String sbstypeId){
        this.guid = guid;
        this.marketId = marketId;
        this.mktYear = mktYear;
        this.tielineId = tielineId;
        this.saleParticipantid = saleParticipantid;
        this.vendeeParticipantid = vendeeParticipantid;
        this.recipientParticipantid = recipientParticipantid;
        this.payerParticipantid = payerParticipantid;
        this.contractId = contractId;
        this.sbstypeId = sbstypeId;
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
     * 属性 marketId 的get方法
     * @return String
     */
    public String getMarketId(){
        return marketId;
    }
	
    /**
     * 属性 marketId 的set方法
     * @return
     */
    public void setMarketId(String marketId){
        this.marketId = marketId;
    } 
	
    /**
     * 属性 mktYear 的get方法
     * @return Date
     */
    public Date getMktYear(){
        return mktYear;
    }
	
    /**
     * 属性 mktYear 的set方法
     * @return
     */
    public void setMktYear(Date mktYear){
        this.mktYear = mktYear;
    } 
	
    /**
     * 属性 tielineId 的get方法
     * @return String
     */
    public String getTielineId(){
        return tielineId;
    }
	
    /**
     * 属性 tielineId 的set方法
     * @return
     */
    public void setTielineId(String tielineId){
        this.tielineId = tielineId;
    } 
	
    /**
     * 属性 tielineIdN 的get方法
     * @return BigDecimal
     */
    public BigDecimal getTielineIdN(){
        return tielineIdN;
    }
	
    /**
     * 属性 tielineIdN 的set方法
     * @return
     */
    public void setTielineIdN(BigDecimal tielineIdN){
        this.tielineIdN = tielineIdN;
    } 
	
    /**
     * 属性 saleParticipantid 的get方法
     * @return String
     */
    public String getSaleParticipantid(){
        return saleParticipantid;
    }
	
    /**
     * 属性 saleParticipantid 的set方法
     * @return
     */
    public void setSaleParticipantid(String saleParticipantid){
        this.saleParticipantid = saleParticipantid;
    } 
	
    /**
     * 属性 vendeeParticipantid 的get方法
     * @return String
     */
    public String getVendeeParticipantid(){
        return vendeeParticipantid;
    }
	
    /**
     * 属性 vendeeParticipantid 的set方法
     * @return
     */
    public void setVendeeParticipantid(String vendeeParticipantid){
        this.vendeeParticipantid = vendeeParticipantid;
    } 
	
    /**
     * 属性 recipientParticipantid 的get方法
     * @return String
     */
    public String getRecipientParticipantid(){
        return recipientParticipantid;
    }
	
    /**
     * 属性 recipientParticipantid 的set方法
     * @return
     */
    public void setRecipientParticipantid(String recipientParticipantid){
        this.recipientParticipantid = recipientParticipantid;
    } 
	
    /**
     * 属性 payerParticipantid 的get方法
     * @return String
     */
    public String getPayerParticipantid(){
        return payerParticipantid;
    }
	
    /**
     * 属性 payerParticipantid 的set方法
     * @return
     */
    public void setPayerParticipantid(String payerParticipantid){
        this.payerParticipantid = payerParticipantid;
    } 
	
    /**
     * 属性 contractId 的get方法
     * @return String
     */
    public String getContractId(){
        return contractId;
    }
	
    /**
     * 属性 contractId 的set方法
     * @return
     */
    public void setContractId(String contractId){
        this.contractId = contractId;
    } 
	
    /**
     * 属性 sbstypeId 的get方法
     * @return String
     */
    public String getSbstypeId(){
        return sbstypeId;
    }
	
    /**
     * 属性 sbstypeId 的set方法
     * @return
     */
    public void setSbstypeId(String sbstypeId){
        this.sbstypeId = sbstypeId;
    } 
	
    /**
     * 属性 energyT 的get方法
     * @return BigDecimal
     */
    public BigDecimal getEnergyT(){
        return energyT;
    }
	
    /**
     * 属性 energyT 的set方法
     * @return
     */
    public void setEnergyT(BigDecimal energyT){
        this.energyT = energyT;
    } 
	
    /**
     * 属性 priceT 的get方法
     * @return BigDecimal
     */
    public BigDecimal getPriceT(){
        return priceT;
    }
	
    /**
     * 属性 priceT 的set方法
     * @return
     */
    public void setPriceT(BigDecimal priceT){
        this.priceT = priceT;
    } 
	
    /**
     * 属性 feeT 的get方法
     * @return BigDecimal
     */
    public BigDecimal getFeeT(){
        return feeT;
    }
	
    /**
     * 属性 feeT 的set方法
     * @return
     */
    public void setFeeT(BigDecimal feeT){
        this.feeT = feeT;
    } 
	
    /**
     * 属性 energyS 的get方法
     * @return BigDecimal
     */
    public BigDecimal getEnergyS(){
        return energyS;
    }
	
    /**
     * 属性 energyS 的set方法
     * @return
     */
    public void setEnergyS(BigDecimal energyS){
        this.energyS = energyS;
    } 
	
    /**
     * 属性 priceS 的get方法
     * @return BigDecimal
     */
    public BigDecimal getPriceS(){
        return priceS;
    }
	
    /**
     * 属性 priceS 的set方法
     * @return
     */
    public void setPriceS(BigDecimal priceS){
        this.priceS = priceS;
    } 
	
    /**
     * 属性 feeS 的get方法
     * @return BigDecimal
     */
    public BigDecimal getFeeS(){
        return feeS;
    }
	
    /**
     * 属性 feeS 的set方法
     * @return
     */
    public void setFeeS(BigDecimal feeS){
        this.feeS = feeS;
    } 
	
    /**
     * 属性 energyP 的get方法
     * @return BigDecimal
     */
    public BigDecimal getEnergyP(){
        return energyP;
    }
	
    /**
     * 属性 energyP 的set方法
     * @return
     */
    public void setEnergyP(BigDecimal energyP){
        this.energyP = energyP;
    } 
	
    /**
     * 属性 priceP 的get方法
     * @return BigDecimal
     */
    public BigDecimal getPriceP(){
        return priceP;
    }
	
    /**
     * 属性 priceP 的set方法
     * @return
     */
    public void setPriceP(BigDecimal priceP){
        this.priceP = priceP;
    } 
	
    /**
     * 属性 feeP 的get方法
     * @return BigDecimal
     */
    public BigDecimal getFeeP(){
        return feeP;
    }
	
    /**
     * 属性 feeP 的set方法
     * @return
     */
    public void setFeeP(BigDecimal feeP){
        this.feeP = feeP;
    } 
	
    /**
     * 属性 energyF 的get方法
     * @return BigDecimal
     */
    public BigDecimal getEnergyF(){
        return energyF;
    }
	
    /**
     * 属性 energyF 的set方法
     * @return
     */
    public void setEnergyF(BigDecimal energyF){
        this.energyF = energyF;
    } 
	
    /**
     * 属性 priceF 的get方法
     * @return BigDecimal
     */
    public BigDecimal getPriceF(){
        return priceF;
    }
	
    /**
     * 属性 priceF 的set方法
     * @return
     */
    public void setPriceF(BigDecimal priceF){
        this.priceF = priceF;
    } 
	
    /**
     * 属性 feeF 的get方法
     * @return BigDecimal
     */
    public BigDecimal getFeeF(){
        return feeF;
    }
	
    /**
     * 属性 feeF 的set方法
     * @return
     */
    public void setFeeF(BigDecimal feeF){
        this.feeF = feeF;
    } 
	
    /**
     * 属性 energyB 的get方法
     * @return BigDecimal
     */
    public BigDecimal getEnergyB(){
        return energyB;
    }
	
    /**
     * 属性 energyB 的set方法
     * @return
     */
    public void setEnergyB(BigDecimal energyB){
        this.energyB = energyB;
    } 
	
    /**
     * 属性 priceB 的get方法
     * @return BigDecimal
     */
    public BigDecimal getPriceB(){
        return priceB;
    }
	
    /**
     * 属性 priceB 的set方法
     * @return
     */
    public void setPriceB(BigDecimal priceB){
        this.priceB = priceB;
    } 
	
    /**
     * 属性 feeB 的get方法
     * @return BigDecimal
     */
    public BigDecimal getFeeB(){
        return feeB;
    }
	
    /**
     * 属性 feeB 的set方法
     * @return
     */
    public void setFeeB(BigDecimal feeB){
        this.feeB = feeB;
    } 
	

    /**
     * toString方法
     * @return String
     */
	public String toString(){

		  return new StringBuffer()
            .append("guid"+":"+getGuid())
            .append("marketId"+":"+getMarketId())
            .append("mktYear"+":"+getMktYear())
            .append("tielineId"+":"+getTielineId())
            .append("tielineIdN"+":"+getTielineIdN())
            .append("saleParticipantid"+":"+getSaleParticipantid())
            .append("vendeeParticipantid"+":"+getVendeeParticipantid())
            .append("recipientParticipantid"+":"+getRecipientParticipantid())
            .append("payerParticipantid"+":"+getPayerParticipantid())
            .append("contractId"+":"+getContractId())
            .append("sbstypeId"+":"+getSbstypeId())
            .append("energyT"+":"+getEnergyT())
            .append("priceT"+":"+getPriceT())
            .append("feeT"+":"+getFeeT())
            .append("energyS"+":"+getEnergyS())
            .append("priceS"+":"+getPriceS())
            .append("feeS"+":"+getFeeS())
            .append("energyP"+":"+getEnergyP())
            .append("priceP"+":"+getPriceP())
            .append("feeP"+":"+getFeeP())
            .append("energyF"+":"+getEnergyF())
            .append("priceF"+":"+getPriceF())
            .append("feeF"+":"+getFeeF())
            .append("energyB"+":"+getEnergyB())
            .append("priceB"+":"+getPriceB())
            .append("feeB"+":"+getFeeB())
            .toString(); 
			
    } 
   


}