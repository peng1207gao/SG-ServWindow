package com.sgcc.dlsc.contractManage.COContractEnergyInfo.vo;
import java.util.Date;
import java.math.BigDecimal;

import java.io.Serializable;
import com.sgcc.uap.rest.annotation.attribute.EditorType;
import com.sgcc.uap.rest.annotation.attribute.AttributeType;
import com.sgcc.uap.rest.annotation.attribute.ViewAttribute;
import com.sgcc.uap.rest.support.ParentVO;

/**
 * CoContractenergyinfo的VO类
 *
 * @author  thinpad  [Wed Feb 12 10:35:27 CST 2014]
 * 
 */
public class CoContractenergyinfoVO extends ParentVO implements Serializable{

	private static final long serialVersionUID = 1L;

    /** 
     * 属性contractid
     */
	@ViewAttribute(name = "contractid", caption="??ID", length =36, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String contractid;
    /** 
     * 属性startdate
     */
	@ViewAttribute(name = "startdate", caption="????", length =36, editor=EditorType.DateTimeEditor,  nullable=true, readOnly=false,  type=AttributeType.DATE) 
	private Date startdate;
    /** 
     * 属性enddate
     */
	@ViewAttribute(name = "enddate", caption="????", length =36, editor=EditorType.DateTimeEditor,  nullable=true, readOnly=false,  type=AttributeType.DATE) 
	private Date enddate;
    /** 
     * 属性period
     */
	@ViewAttribute(name = "period", caption="??,1???2?3?4?5? ", length =2, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal period;
    /** 
     * 属性purchasergen
     */
	@ViewAttribute(name = "purchasergen", caption="??????", length =12, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal purchasergen;
    /** 
     * 属性fee
     */
	@ViewAttribute(name = "fee", caption="??", length =20, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal fee;
    /** 
     * 属性energy
     */
	@ViewAttribute(name = "energy",maxLength=13, caption="??", length =12, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal energy;
    /** 
     * 属性qtytype
     */
	@ViewAttribute(name = "qtytype", caption="????", length =2, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal qtytype;
    /** 
     * 属性timeno
     */
	@ViewAttribute(name = "timeno", caption="?????", length =12, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal timeno;
    /** 
     * 属性purchaserenergy
     */
	@ViewAttribute(name = "purchaserenergy", caption="???????,?????????? ", length =12, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal purchaserenergy;
    /** 
     * 属性purchaserprice
     */
	@ViewAttribute(name = "purchaserprice", caption="?????", length =12, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL,maxLength=13) 
	private BigDecimal purchaserprice;
    /** 
     * 属性purisincludetax
     */
	@ViewAttribute(name = "purisincludetax", caption="????", length =2, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal purisincludetax;
    /** 
     * 属性sellergen
     */
	@ViewAttribute(name = "sellergen", caption="??????", length =12, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal sellergen;
    /** 
     * 属性sellerenergy
     */
	@ViewAttribute(name = "sellerenergy", caption="???????", length =12, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal sellerenergy;
    /** 
     * 属性sellerprice
     */
	@ViewAttribute(name = "sellerprice", caption="?????", length =12, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL,maxLength=13) 
	private BigDecimal sellerprice;
    /** 
     * 属性sellerisincludetax
     */
	@ViewAttribute(name = "sellerisincludetax", caption="??????", length =2, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal sellerisincludetax;
    /** 
     * 属性price
     */
	@ViewAttribute(name = "price", caption="????,?????????? ", length =12, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL,maxLength=13) 
	private BigDecimal price;
    /** 
     * 属性guid
     */
	@ViewAttribute(name = "guid", caption="GUID", length =36, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String guid;
    /** 
     * 属性purisincludetp
     */
	@ViewAttribute(name = "purisincludetp", caption="?????", length =12, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal purisincludetp;
    /** 
     * 属性sellerisincludetp
     */
	@ViewAttribute(name = "sellerisincludetp", caption="?????", length =20, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal sellerisincludetp;
    /** 
     * 属性pricetax
     */
	@ViewAttribute(name = "pricetax", caption="??????", length =2, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal pricetax;
    /** 
     * 属性pricetp
     */
	@ViewAttribute(name = "pricetp", caption="????", length =12, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal pricetp;
    /** 
     * 属性whereinsert
     */
	@ViewAttribute(name = "whereinsert", caption="?????0????? 1?????", length =10, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String whereinsert;
	
	/** 
     * 属性tradePriceMargin
     */
	@ViewAttribute(name = "tradePriceMargin", caption="价差成交电价", length =12, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL,maxLength=13) 
	private BigDecimal tradePriceMargin;
    /** 
     * 属性vendeeBreathPrice
     */
	@ViewAttribute(name = "vendeeBreathPrice", caption="购方违约赔偿电价", length =12, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL,maxLength=13) 
	private BigDecimal vendeeBreathPrice;
    /** 
     * 属性saleBreathPrice
     */
	@ViewAttribute(name = "saleBreathPrice", caption="售方违约赔偿电价", length =12, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL,maxLength=13) 
	private BigDecimal saleBreathPrice;
	/** 
     * 属性buyprice
     */
	@ViewAttribute(name = "buyprice", caption="收购电价", length =12, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal buyprice;
    /** 
     * 属性usersideqty
     */
	@ViewAttribute(name = "usersideqty", caption="用户侧电量", length =12, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal usersideqty;
    /** 
     * 属性lossRatio
     */
	@ViewAttribute(name = "lossRatio", caption="网损率", length =12, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal lossRatio;
    /** 
     * 属性allowanceprice
     */
	@ViewAttribute(name = "allowanceprice", caption="政府补贴电价", length =12, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal allowanceprice;
	
    /**
     * CoContractenergyinfoVO构造函数
     */
    public CoContractenergyinfoVO() {
        super();
    }  
	
    /**
     * CoContractenergyinfoVO完整的构造函数
     */  
    public CoContractenergyinfoVO(String guid){
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
     * 属性 startdate 的get方法
     * @return Date
     */
    public Date getStartdate(){
        return startdate;
    }
	
    /**
     * 属性 startdate 的set方法
     * @return
     */
    public void setStartdate(Date startdate){
        this.startdate = startdate;
    } 
	
    /**
     * 属性 enddate 的get方法
     * @return Date
     */
    public Date getEnddate(){
        return enddate;
    }
	
    /**
     * 属性 enddate 的set方法
     * @return
     */
    public void setEnddate(Date enddate){
        this.enddate = enddate;
    } 
	
    /**
     * 属性 period 的get方法
     * @return BigDecimal
     */
    public BigDecimal getPeriod(){
        return period;
    }
	
    /**
     * 属性 period 的set方法
     * @return
     */
    public void setPeriod(BigDecimal period){
        this.period = period;
    } 
	
    /**
     * 属性 purchasergen 的get方法
     * @return BigDecimal
     */
    public BigDecimal getPurchasergen(){
        return purchasergen;
    }
	
    /**
     * 属性 purchasergen 的set方法
     * @return
     */
    public void setPurchasergen(BigDecimal purchasergen){
        this.purchasergen = purchasergen;
    } 
	
    /**
     * 属性 fee 的get方法
     * @return BigDecimal
     */
    public BigDecimal getFee(){
        return fee;
    }
	
    /**
     * 属性 fee 的set方法
     * @return
     */
    public void setFee(BigDecimal fee){
        this.fee = fee;
    } 
	
    /**
     * 属性 energy 的get方法
     * @return BigDecimal
     */
    public BigDecimal getEnergy(){
        return energy;
    }
	
    /**
     * 属性 energy 的set方法
     * @return
     */
    public void setEnergy(BigDecimal energy){
        this.energy = energy;
    } 
	
    /**
     * 属性 qtytype 的get方法
     * @return BigDecimal
     */
    public BigDecimal getQtytype(){
        return qtytype;
    }
	
    /**
     * 属性 qtytype 的set方法
     * @return
     */
    public void setQtytype(BigDecimal qtytype){
        this.qtytype = qtytype;
    } 
	
    /**
     * 属性 timeno 的get方法
     * @return BigDecimal
     */
    public BigDecimal getTimeno(){
        return timeno;
    }
	
    /**
     * 属性 timeno 的set方法
     * @return
     */
    public void setTimeno(BigDecimal timeno){
        this.timeno = timeno;
    } 
	
    /**
     * 属性 purchaserenergy 的get方法
     * @return BigDecimal
     */
    public BigDecimal getPurchaserenergy(){
        return purchaserenergy;
    }
	
    /**
     * 属性 purchaserenergy 的set方法
     * @return
     */
    public void setPurchaserenergy(BigDecimal purchaserenergy){
        this.purchaserenergy = purchaserenergy;
    } 
	
    /**
     * 属性 purchaserprice 的get方法
     * @return BigDecimal
     */
    public BigDecimal getPurchaserprice(){
        return purchaserprice;
    }
	
    /**
     * 属性 purchaserprice 的set方法
     * @return
     */
    public void setPurchaserprice(BigDecimal purchaserprice){
        this.purchaserprice = purchaserprice;
    } 
	
    /**
     * 属性 purisincludetax 的get方法
     * @return BigDecimal
     */
    public BigDecimal getPurisincludetax(){
        return purisincludetax;
    }
	
    /**
     * 属性 purisincludetax 的set方法
     * @return
     */
    public void setPurisincludetax(BigDecimal purisincludetax){
        this.purisincludetax = purisincludetax;
    } 
	
    /**
     * 属性 sellergen 的get方法
     * @return BigDecimal
     */
    public BigDecimal getSellergen(){
        return sellergen;
    }
	
    /**
     * 属性 sellergen 的set方法
     * @return
     */
    public void setSellergen(BigDecimal sellergen){
        this.sellergen = sellergen;
    } 
	
    /**
     * 属性 sellerenergy 的get方法
     * @return BigDecimal
     */
    public BigDecimal getSellerenergy(){
        return sellerenergy;
    }
	
    /**
     * 属性 sellerenergy 的set方法
     * @return
     */
    public void setSellerenergy(BigDecimal sellerenergy){
        this.sellerenergy = sellerenergy;
    } 
	
    /**
     * 属性 sellerprice 的get方法
     * @return BigDecimal
     */
    public BigDecimal getSellerprice(){
        return sellerprice;
    }
	
    /**
     * 属性 sellerprice 的set方法
     * @return
     */
    public void setSellerprice(BigDecimal sellerprice){
        this.sellerprice = sellerprice;
    } 
	
    /**
     * 属性 sellerisincludetax 的get方法
     * @return BigDecimal
     */
    public BigDecimal getSellerisincludetax(){
        return sellerisincludetax;
    }
	
    /**
     * 属性 sellerisincludetax 的set方法
     * @return
     */
    public void setSellerisincludetax(BigDecimal sellerisincludetax){
        this.sellerisincludetax = sellerisincludetax;
    } 
	
    /**
     * 属性 price 的get方法
     * @return BigDecimal
     */
    public BigDecimal getPrice(){
        return price;
    }
	
    /**
     * 属性 price 的set方法
     * @return
     */
    public void setPrice(BigDecimal price){
        this.price = price;
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
     * 属性 purisincludetp 的get方法
     * @return BigDecimal
     */
    public BigDecimal getPurisincludetp(){
        return purisincludetp;
    }
	
    /**
     * 属性 purisincludetp 的set方法
     * @return
     */
    public void setPurisincludetp(BigDecimal purisincludetp){
        this.purisincludetp = purisincludetp;
    } 
	
    /**
     * 属性 sellerisincludetp 的get方法
     * @return BigDecimal
     */
    public BigDecimal getSellerisincludetp(){
        return sellerisincludetp;
    }
	
    /**
     * 属性 sellerisincludetp 的set方法
     * @return
     */
    public void setSellerisincludetp(BigDecimal sellerisincludetp){
        this.sellerisincludetp = sellerisincludetp;
    } 
	
    /**
     * 属性 pricetax 的get方法
     * @return BigDecimal
     */
    public BigDecimal getPricetax(){
        return pricetax;
    }
	
    /**
     * 属性 pricetax 的set方法
     * @return
     */
    public void setPricetax(BigDecimal pricetax){
        this.pricetax = pricetax;
    } 
	
    /**
     * 属性 pricetp 的get方法
     * @return BigDecimal
     */
    public BigDecimal getPricetp(){
        return pricetp;
    }
	
    /**
     * 属性 pricetp 的set方法
     * @return
     */
    public void setPricetp(BigDecimal pricetp){
        this.pricetp = pricetp;
    } 
	
    /**
     * 属性 whereinsert 的get方法
     * @return String
     */
    public String getWhereinsert(){
        return whereinsert;
    }
	
    /**
     * 属性 whereinsert 的set方法
     * @return
     */
    public void setWhereinsert(String whereinsert){
        this.whereinsert = whereinsert;
    } 
	
    /**
     * 属性 tradePriceMargin 的get方法
     * @return BigDecimal
     */
    public BigDecimal getTradePriceMargin(){
        return tradePriceMargin;
    }
	
    /**
     * 属性 tradePriceMargin 的set方法
     * @return
     */
    public void setTradePriceMargin(BigDecimal tradePriceMargin){
        this.tradePriceMargin = tradePriceMargin;
    } 
	
    /**
     * 属性 vendeeBreathPrice 的get方法
     * @return BigDecimal
     */
    public BigDecimal getVendeeBreathPrice(){
        return vendeeBreathPrice;
    }
	
    /**
     * 属性 vendeeBreathPrice 的set方法
     * @return
     */
    public void setVendeeBreathPrice(BigDecimal vendeeBreathPrice){
        this.vendeeBreathPrice = vendeeBreathPrice;
    } 
	
    /**
     * 属性 saleBreathPrice 的get方法
     * @return BigDecimal
     */
    public BigDecimal getSaleBreathPrice(){
        return saleBreathPrice;
    }
	
    /**
     * 属性 saleBreathPrice 的set方法
     * @return
     */
    public void setSaleBreathPrice(BigDecimal saleBreathPrice){
        this.saleBreathPrice = saleBreathPrice;
    }
    
    /**
     * 属性 buyprice 的get方法
     * @return BigDecimal
     */
    public BigDecimal getBuyprice(){
        return buyprice;
    }
	
    /**
     * 属性 buyprice 的set方法
     * @return
     */
    public void setBuyprice(BigDecimal buyprice){
        this.buyprice = buyprice;
    } 
	
    /**
     * 属性 usersideqty 的get方法
     * @return BigDecimal
     */
    public BigDecimal getUsersideqty(){
        return usersideqty;
    }
	
    /**
     * 属性 usersideqty 的set方法
     * @return
     */
    public void setUsersideqty(BigDecimal usersideqty){
        this.usersideqty = usersideqty;
    } 
	
    /**
     * 属性 lossRatio 的get方法
     * @return BigDecimal
     */
    public BigDecimal getLossRatio(){
        return lossRatio;
    }
	
    /**
     * 属性 lossRatio 的set方法
     * @return
     */
    public void setLossRatio(BigDecimal lossRatio){
        this.lossRatio = lossRatio;
    } 
	
    /**
     * 属性 allowanceprice 的get方法
     * @return BigDecimal
     */
    public BigDecimal getAllowanceprice(){
        return allowanceprice;
    }
	
    /**
     * 属性 allowanceprice 的set方法
     * @return
     */
    public void setAllowanceprice(BigDecimal allowanceprice){
        this.allowanceprice = allowanceprice;
    }

    /**
     * toString方法
     * @return String
     */
	public String toString(){

		  return new StringBuffer()
            .append("contractid"+":"+getContractid())
            .append("startdate"+":"+getStartdate())
            .append("enddate"+":"+getEnddate())
            .append("period"+":"+getPeriod())
            .append("purchasergen"+":"+getPurchasergen())
            .append("fee"+":"+getFee())
            .append("energy"+":"+getEnergy())
            .append("qtytype"+":"+getQtytype())
            .append("timeno"+":"+getTimeno())
            .append("purchaserenergy"+":"+getPurchaserenergy())
            .append("purchaserprice"+":"+getPurchaserprice())
            .append("purisincludetax"+":"+getPurisincludetax())
            .append("sellergen"+":"+getSellergen())
            .append("sellerenergy"+":"+getSellerenergy())
            .append("sellerprice"+":"+getSellerprice())
            .append("sellerisincludetax"+":"+getSellerisincludetax())
            .append("price"+":"+getPrice())
            .append("guid"+":"+getGuid())
            .append("purisincludetp"+":"+getPurisincludetp())
            .append("sellerisincludetp"+":"+getSellerisincludetp())
            .append("pricetax"+":"+getPricetax())
            .append("pricetp"+":"+getPricetp())
            .append("whereinsert"+":"+getWhereinsert())
            .append("tradePriceMargin"+":"+getTradePriceMargin())
            .append("vendeeBreathPrice"+":"+getVendeeBreathPrice())
            .append("saleBreathPrice"+":"+getSaleBreathPrice())
            .append("buyprice"+":"+getBuyprice())
            .append("usersideqty"+":"+getUsersideqty())
            .append("lossRatio"+":"+getLossRatio())
            .append("allowanceprice"+":"+getAllowanceprice())
            .toString(); 
			
    } 
   


}