package com.sgcc.dlsc.statesanalysis.thermalpower.vo;
import java.io.Serializable;

import com.sgcc.uap.rest.annotation.attribute.AttributeType;
import com.sgcc.uap.rest.annotation.attribute.EditorType;
import com.sgcc.uap.rest.annotation.attribute.ViewAttribute;
import com.sgcc.uap.rest.support.ParentVO;

/**
 * SeTielineResultN的VO类
 *
 * @author  DELL  [Wed Apr 23 15:32:25 CST 2014]
 * 
 */
public class PowerFinishPercentageVO extends ParentVO implements Serializable{

	private static final long serialVersionUID = 1L;

    /** 
     * 属性sellerid
     */
	@ViewAttribute(name = "sellerid", caption="售点方id", length =36, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String sellerid;

	/** 
     * 属性sellername
     */
	@ViewAttribute(name = "sellername", caption="售点方名称", length =36, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String sellername;
    /** 
     * 属性contractqty
     */
	@ViewAttribute(name = "contractqty", caption="合同电量", length =36, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String contractqty;
    /** 
     * 属性energy_t
     */
	@ViewAttribute(name = "energy_t", caption="完成电量", length =36, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String energy_t;
    /** 
     * 属性percentage
     */
	@ViewAttribute(name = "percentage", caption="完成百分比", length =36, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String percentage;
    /** 
     * 属性deviation
     */
	@ViewAttribute(name = "deviation", caption="相对平均进度偏差", length =36, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String deviation;
	
    /** 
     * 属性deviationvalue
     */
	@ViewAttribute(name = "deviationvalue", caption="偏差值", length =36, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String deviationvalue;
	
   
	public String getDeviationvalue() {
		return deviationvalue;
	}

	public void setDeviationvalue(String deviationvalue) {
		this.deviationvalue = deviationvalue;
	}

	public String getDeviation() {
		return deviation;
	}

	public void setDeviation(String deviation) {
		this.deviation = deviation;
	}

	public String getSellerid() {
		return sellerid;
	}

	public void setSellerid(String sellerid) {
		this.sellerid = sellerid;
	}

	public String getSellername() {
		return sellername;
	}

	public void setSellername(String sellername) {
		this.sellername = sellername;
	}

	public String getContractqty() {
		return contractqty;
	}

	public void setContractqty(String contractqty) {
		this.contractqty = contractqty;
	}

	public String getEnergy_t() {
		return energy_t;
	}

	public void setEnergy_t(String energy_t) {
		this.energy_t = energy_t;
	}

	public String getPercentage() {
		return percentage;
	}

	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}

    /**
     * toString方法
     * @return String
     */
	public String toString(){

		  return new StringBuffer()
            .append("sellername"+":"+getSellername())
            .append("contractqty"+":"+getContractqty())
            .append("energy_t"+":"+getEnergy_t())
            .append("percentage"+":"+getPercentage())
            .toString(); 
    } 
   


}