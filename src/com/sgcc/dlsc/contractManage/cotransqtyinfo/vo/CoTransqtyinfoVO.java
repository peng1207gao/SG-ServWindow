package com.sgcc.dlsc.contractManage.cotransqtyinfo.vo;
import java.math.BigDecimal;
import java.util.Date;

import java.io.Serializable;
import com.sgcc.uap.rest.annotation.attribute.EditorType;
import com.sgcc.uap.rest.annotation.attribute.AttributeType;
import com.sgcc.uap.rest.annotation.attribute.ViewAttribute;
import com.sgcc.uap.rest.support.ParentVO;

/**
 * CoTransqtyinfo的VO类
 *
 * @author  mengke  [Sun Feb 09 16:03:05 CST 2014]
 * 
 */
public class CoTransqtyinfoVO extends ParentVO implements Serializable{

	private static final long serialVersionUID = 1L;

    /** 
     * 属性transinfoid
     */
	@ViewAttribute(name = "transinfoid", caption="???ID", length =36, maxLength=36, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String transinfoid;
    /** 
     * 属性contractid
     */
	@ViewAttribute(name = "contractid", caption="??ID", length =36, maxLength=36, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String contractid;
    /** 
     * 属性transmission
     */
	@ViewAttribute(name = "transmission", caption="???", length =36, maxLength=60, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String transmission;
    /** 
     * 属性linkid
     */
	@ViewAttribute(name = "linkid", caption="???ID", length =36, maxLength=36, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String linkid;
    /** 
     * 属性linkno
     */
	@ViewAttribute(name = "linkno", caption="?????", length =12, maxLength=36, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal linkno;
    /** 
     * 属性passagewayno
     */
	@ViewAttribute(name = "passagewayno", caption="??????", length =12, maxLength=36, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal passagewayno;
    /** 
     * 属性passagewayname
     */
	@ViewAttribute(name = "passagewayname", caption="??????", length =128, maxLength=256, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String passagewayname;
    /** 
     * 属性startgateid
     */
	@ViewAttribute(name = "startgateid", caption="????ID", length =36, maxLength=150, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String startgateid;
    /** 
     * 属性endgateid
     */
	@ViewAttribute(name = "endgateid", caption="????ID", length =36, maxLength=150, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String endgateid;
    /** 
     * 属性startprice
     */
	@ViewAttribute(name = "startprice", caption="????", length =12, maxLength=13, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal startprice;
    /** 
     * 属性endprice
     */
	@ViewAttribute(name = "endprice", caption="????", length =12, maxLength=13, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal endprice;
    /** 
     * 属性transprice
     */
	@ViewAttribute(name = "transprice", caption="????", length =12, maxLength=13, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal transprice;
    /** 
     * 属性lossrate
     */
	@ViewAttribute(name = "lossrate", caption="???", length =12, maxLength=13, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal lossrate;
    /** 
     * 属性transqty
     */
	@ViewAttribute(name = "transqty", caption="????", length =16, maxLength=17, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal transqty;
    /** 
     * 属性capfee
     */
	@ViewAttribute(name = "capfee", caption="????", length =16, maxLength=17, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal capfee;
    /** 
     * 属性seprate
     */
	@ViewAttribute(name = "seprate", caption="???????%", length =12, maxLength=13, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal seprate;
    /** 
     * 属性peakregurate
     */
	@ViewAttribute(name = "peakregurate", caption="????%", length =12, maxLength=13, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal peakregurate;
    /** 
     * 属性isincludetax
     */
	@ViewAttribute(name = "isincludetax", caption="????", length =2, maxLength=2, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal isincludetax;
    /** 
     * 属性power
     */
	@ViewAttribute(name = "power", caption="????", length =16, maxLength=17, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal power;
    /** 
     * 属性explanation
     */
	@ViewAttribute(name = "explanation", caption="??", length =1024, maxLength=1024, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String explanation;
    /** 
     * 属性spare1
     */
	@ViewAttribute(name = "spare1", caption="??1", length =128, maxLength=256, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String spare1;
    /** 
     * 属性spare2
     */
	@ViewAttribute(name = "spare2", caption="??2", length =128, maxLength=256, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String spare2;
    /** 
     * 属性spare3
     */
	@ViewAttribute(name = "spare3", caption="??3", length =128, maxLength=256, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String spare3;
    /** 
     * 属性starttime
     */
	@ViewAttribute(name = "starttime", caption="????", length =128, maxLength=128, editor=EditorType.DateTimeEditor,  nullable=true, readOnly=false,  type=AttributeType.DATE) 
	private Date starttime;
    /** 
     * 属性endtime
     */
	@ViewAttribute(name = "endtime", caption="????", length =128, maxLength=128, editor=EditorType.DateTimeEditor,  nullable=true, readOnly=false,  type=AttributeType.DATE) 
	private Date endtime;
    /** 
     * 属性transcap
     */
	@ViewAttribute(name = "transcap", caption="????", length =16, maxLength=17, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal transcap;
    /** 
     * 属性capprice
     */
	@ViewAttribute(name = "capprice", caption="????", length =16, maxLength=17, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal capprice;
    /**
     * CoTransqtyinfoVO构造函数
     */
    public CoTransqtyinfoVO() {
        super();
    }  
	
    /**
     * CoTransqtyinfoVO完整的构造函数
     */  
    public CoTransqtyinfoVO(String transinfoid){
        this.transinfoid = transinfoid;
    }
 
    /**
     * 属性 transinfoid 的get方法
     * @return String
     */
    public String getTransinfoid(){
        return transinfoid;
    }
	
    /**
     * 属性 transinfoid 的set方法
     * @return
     */
    public void setTransinfoid(String transinfoid){
        if(transinfoid != null && transinfoid.trim().length() == 0){
            this.transinfoid = null;
        }else{
            this.transinfoid = transinfoid;
        }
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
     * 属性 transmission 的get方法
     * @return String
     */
    public String getTransmission(){
        return transmission;
    }
	
    /**
     * 属性 transmission 的set方法
     * @return
     */
    public void setTransmission(String transmission){
        this.transmission = transmission;
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
     * 属性 linkno 的get方法
     * @return BigDecimal
     */
    public BigDecimal getLinkno(){
        return linkno;
    }
	
    /**
     * 属性 linkno 的set方法
     * @return
     */
    public void setLinkno(BigDecimal linkno){
        this.linkno = linkno;
    } 
	
    /**
     * 属性 passagewayno 的get方法
     * @return BigDecimal
     */
    public BigDecimal getPassagewayno(){
        return passagewayno;
    }
	
    /**
     * 属性 passagewayno 的set方法
     * @return
     */
    public void setPassagewayno(BigDecimal passagewayno){
        this.passagewayno = passagewayno;
    } 
	
    /**
     * 属性 passagewayname 的get方法
     * @return String
     */
    public String getPassagewayname(){
        return passagewayname;
    }
	
    /**
     * 属性 passagewayname 的set方法
     * @return
     */
    public void setPassagewayname(String passagewayname){
        this.passagewayname = passagewayname;
    } 
	
    /**
     * 属性 startgateid 的get方法
     * @return String
     */
    public String getStartgateid(){
        return startgateid;
    }
	
    /**
     * 属性 startgateid 的set方法
     * @return
     */
    public void setStartgateid(String startgateid){
        this.startgateid = startgateid;
    } 
	
    /**
     * 属性 endgateid 的get方法
     * @return String
     */
    public String getEndgateid(){
        return endgateid;
    }
	
    /**
     * 属性 endgateid 的set方法
     * @return
     */
    public void setEndgateid(String endgateid){
        this.endgateid = endgateid;
    } 
	
    /**
     * 属性 startprice 的get方法
     * @return BigDecimal
     */
    public BigDecimal getStartprice(){
        return startprice;
    }
	
    /**
     * 属性 startprice 的set方法
     * @return
     */
    public void setStartprice(BigDecimal startprice){
        this.startprice = startprice;
    } 
	
    /**
     * 属性 endprice 的get方法
     * @return BigDecimal
     */
    public BigDecimal getEndprice(){
        return endprice;
    }
	
    /**
     * 属性 endprice 的set方法
     * @return
     */
    public void setEndprice(BigDecimal endprice){
        this.endprice = endprice;
    } 
	
    /**
     * 属性 transprice 的get方法
     * @return BigDecimal
     */
    public BigDecimal getTransprice(){
        return transprice;
    }
	
    /**
     * 属性 transprice 的set方法
     * @return
     */
    public void setTransprice(BigDecimal transprice){
        this.transprice = transprice;
    } 
	
    /**
     * 属性 lossrate 的get方法
     * @return BigDecimal
     */
    public BigDecimal getLossrate(){
        return lossrate;
    }
	
    /**
     * 属性 lossrate 的set方法
     * @return
     */
    public void setLossrate(BigDecimal lossrate){
        this.lossrate = lossrate;
    } 
	
    /**
     * 属性 transqty 的get方法
     * @return BigDecimal
     */
    public BigDecimal getTransqty(){
        return transqty;
    }
	
    /**
     * 属性 transqty 的set方法
     * @return
     */
    public void setTransqty(BigDecimal transqty){
        this.transqty = transqty;
    } 
	
    /**
     * 属性 capfee 的get方法
     * @return BigDecimal
     */
    public BigDecimal getCapfee(){
        return capfee;
    }
	
    /**
     * 属性 capfee 的set方法
     * @return
     */
    public void setCapfee(BigDecimal capfee){
        this.capfee = capfee;
    } 
	
    /**
     * 属性 seprate 的get方法
     * @return BigDecimal
     */
    public BigDecimal getSeprate(){
        return seprate;
    }
	
    /**
     * 属性 seprate 的set方法
     * @return
     */
    public void setSeprate(BigDecimal seprate){
        this.seprate = seprate;
    } 
	
    /**
     * 属性 peakregurate 的get方法
     * @return BigDecimal
     */
    public BigDecimal getPeakregurate(){
        return peakregurate;
    }
	
    /**
     * 属性 peakregurate 的set方法
     * @return
     */
    public void setPeakregurate(BigDecimal peakregurate){
        this.peakregurate = peakregurate;
    } 
	
    /**
     * 属性 isincludetax 的get方法
     * @return BigDecimal
     */
    public BigDecimal getIsincludetax(){
        return isincludetax;
    }
	
    /**
     * 属性 isincludetax 的set方法
     * @return
     */
    public void setIsincludetax(BigDecimal isincludetax){
        this.isincludetax = isincludetax;
    } 
	
    /**
     * 属性 power 的get方法
     * @return BigDecimal
     */
    public BigDecimal getPower(){
        return power;
    }
	
    /**
     * 属性 power 的set方法
     * @return
     */
    public void setPower(BigDecimal power){
        this.power = power;
    } 
	
    /**
     * 属性 explanation 的get方法
     * @return String
     */
    public String getExplanation(){
        return explanation;
    }
	
    /**
     * 属性 explanation 的set方法
     * @return
     */
    public void setExplanation(String explanation){
        this.explanation = explanation;
    } 
	
    /**
     * 属性 spare1 的get方法
     * @return String
     */
    public String getSpare1(){
        return spare1;
    }
	
    /**
     * 属性 spare1 的set方法
     * @return
     */
    public void setSpare1(String spare1){
        this.spare1 = spare1;
    } 
	
    /**
     * 属性 spare2 的get方法
     * @return String
     */
    public String getSpare2(){
        return spare2;
    }
	
    /**
     * 属性 spare2 的set方法
     * @return
     */
    public void setSpare2(String spare2){
        this.spare2 = spare2;
    } 
	
    /**
     * 属性 spare3 的get方法
     * @return String
     */
    public String getSpare3(){
        return spare3;
    }
	
    /**
     * 属性 spare3 的set方法
     * @return
     */
    public void setSpare3(String spare3){
        this.spare3 = spare3;
    } 
	
    /**
     * 属性 starttime 的get方法
     * @return Date
     */
    public Date getStarttime(){
        return starttime;
    }
	
    /**
     * 属性 starttime 的set方法
     * @return
     */
    public void setStarttime(Date starttime){
        this.starttime = starttime;
    } 
	
    /**
     * 属性 endtime 的get方法
     * @return Date
     */
    public Date getEndtime(){
        return endtime;
    }
	
    /**
     * 属性 endtime 的set方法
     * @return
     */
    public void setEndtime(Date endtime){
        this.endtime = endtime;
    } 
	
    /**
     * 属性 transcap 的get方法
     * @return BigDecimal
     */
    public BigDecimal getTranscap(){
        return transcap;
    }
	
    /**
     * 属性 transcap 的set方法
     * @return
     */
    public void setTranscap(BigDecimal transcap){
        this.transcap = transcap;
    } 
	
    /**
     * 属性 capprice 的get方法
     * @return BigDecimal
     */
    public BigDecimal getCapprice(){
        return capprice;
    }
	
    /**
     * 属性 capprice 的set方法
     * @return
     */
    public void setCapprice(BigDecimal capprice){
        this.capprice = capprice;
    } 
	

    /**
     * toString方法
     * @return String
     */
	public String toString(){

		  return new StringBuffer()
            .append("transinfoid"+":"+getTransinfoid())
            .append("contractid"+":"+getContractid())
            .append("transmission"+":"+getTransmission())
            .append("linkid"+":"+getLinkid())
            .append("linkno"+":"+getLinkno())
            .append("passagewayno"+":"+getPassagewayno())
            .append("passagewayname"+":"+getPassagewayname())
            .append("startgateid"+":"+getStartgateid())
            .append("endgateid"+":"+getEndgateid())
            .append("startprice"+":"+getStartprice())
            .append("endprice"+":"+getEndprice())
            .append("transprice"+":"+getTransprice())
            .append("lossrate"+":"+getLossrate())
            .append("transqty"+":"+getTransqty())
            .append("capfee"+":"+getCapfee())
            .append("seprate"+":"+getSeprate())
            .append("peakregurate"+":"+getPeakregurate())
            .append("isincludetax"+":"+getIsincludetax())
            .append("power"+":"+getPower())
            .append("explanation"+":"+getExplanation())
            .append("spare1"+":"+getSpare1())
            .append("spare2"+":"+getSpare2())
            .append("spare3"+":"+getSpare3())
            .append("starttime"+":"+getStarttime())
            .append("endtime"+":"+getEndtime())
            .append("transcap"+":"+getTranscap())
            .append("capprice"+":"+getCapprice())
            .toString(); 
			
    } 
   


}