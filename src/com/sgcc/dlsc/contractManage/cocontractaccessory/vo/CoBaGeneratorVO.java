package com.sgcc.dlsc.contractManage.cocontractaccessory.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.sgcc.uap.rest.annotation.attribute.AttributeType;
import com.sgcc.uap.rest.annotation.attribute.EditorType;
import com.sgcc.uap.rest.annotation.attribute.ViewAttribute;
import com.sgcc.uap.rest.support.ParentVO;

public class CoBaGeneratorVO  extends ParentVO implements Serializable {

	 /** 
     * 属性participantid
     */
	@ViewAttribute(name = "generatorid", caption="机组Id", length =36, editor=EditorType.TextEditor,  
			nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String generatorid;
	
	 /** 
     * 属性contractid
     */
	@ViewAttribute(name = "generatorname", caption="机组名称", length =36, editor=EditorType.TextEditor,  
			nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String generatorname;
	
	 /** 
     * 属性contractid
     */
	@ViewAttribute(name = "contractid", caption="CONTRACTID", length =36, editor=EditorType.TextEditor,  
			nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String contractid;
	
    /** 
     * 属性contractrole
     */
	@ViewAttribute(name = "contractrole", caption="合同角色", length =1, editor=EditorType.TextEditor,  
			nullable=false, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal contractrole;

	public String getGeneratorid() {
		return generatorid;
	}

	public void setGeneratorid(String generatorid) {
		this.generatorid = generatorid;
	}

	public String getGeneratorname() {
		return generatorname;
	}

	public void setGeneratorname(String generatorname) {
		this.generatorname = generatorname;
	}

	public String getContractid() {
		return contractid;
	}

	public void setContractid(String contractid) {
		this.contractid = contractid;
	}

	public BigDecimal getContractrole() {
		return contractrole;
	}

	public void setContractrole(BigDecimal contractrole) {
		this.contractrole = contractrole;
	}
	
}
