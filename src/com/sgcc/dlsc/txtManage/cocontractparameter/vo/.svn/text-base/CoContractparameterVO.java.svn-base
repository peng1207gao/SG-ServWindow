package com.sgcc.dlsc.txtManage.cocontractparameter.vo;
import java.util.Date;
import java.math.BigDecimal;

import java.io.Serializable;
import com.sgcc.uap.rest.annotation.attribute.EditorType;
import com.sgcc.uap.rest.annotation.attribute.AttributeType;
import com.sgcc.uap.rest.annotation.attribute.ViewAttribute;
import com.sgcc.uap.rest.support.ParentVO;

/**
 * CoContractparameter的VO类
 *
 * @author  xiabaike  [Wed May 21 18:06:16 CST 2014]
 * 
 */
public class CoContractparameterVO extends ParentVO implements Serializable{

	private static final long serialVersionUID = 1L;

    /** 
     * 属性marketid
     */
	@ViewAttribute(name = "marketid", caption="市场ID", length =36, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String marketid;
    /** 
     * 属性tempparamid
     */
	@ViewAttribute(name = "tempparamid", caption="参数ID", length =36, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String tempparamid;
    /** 
     * 属性tempparamname  CONTRACTTEMPLATENAME
     */
	@ViewAttribute(name = "tempparamname", caption="参数名称", length =64, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String tempparamname;
	
	/** 
     * 属性contracttemplatename  CONTRACTTEMPLATENAME
     */
	@ViewAttribute(name = "contracttemplatename", caption="合同范本名称", length =64, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING)
	private String contracttemplatename;
	
	/** 
     * 属性dicname
     */
	@ViewAttribute(name = "dicname", caption="标签编码", length =200, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String dicname;
	
	/** 
     * 属性bookmark
     */
	@ViewAttribute(name = "bookmark", caption="书签名称", length =64, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String bookmark;
	
    public String getDicname() {
		return dicname;
	}

	public void setDicname(String dicname) {
		this.dicname = dicname;
	}

	public String getBookmark() {
		return bookmark;
	}

	public void setBookmark(String bookmark) {
		this.bookmark = bookmark;
	}

	public String getContracttemplatename() {
		return contracttemplatename;
	}

	public void setContracttemplatename(String contracttemplatename) {
		this.contracttemplatename = contracttemplatename;
	}

	/** 
     * 属性tempparamex
     */
	@ViewAttribute(name = "tempparamex", caption="参数公式", length =500, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String tempparamex;
    /** 
     * 属性tempparamsql
     */
	@ViewAttribute(name = "tempparamsql", caption="参数SQL", length =500, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String tempparamsql;
    /** 
     * 属性tempparamdesc
     */
	@ViewAttribute(name = "tempparamdesc", caption="参数说明", length =500, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String tempparamdesc;
    /** 
     * 属性contracttype
     */
	@ViewAttribute(name = "contracttype", caption="合同类型", length =64, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String contracttype;
    /** 
     * 属性starteffectivedate
     */
	@ViewAttribute(name = "starteffectivedate", caption="生效时间", length =64, editor=EditorType.DateTimeEditor,  nullable=true, readOnly=false,  type=AttributeType.DATE) 
	private Date starteffectivedate;
    /** 
     * 属性endeffectivedate
     */
	@ViewAttribute(name = "endeffectivedate", caption="失效日期", length =64, editor=EditorType.DateTimeEditor,  nullable=true, readOnly=false,  type=AttributeType.DATE) 
	private Date endeffectivedate;
    /** 
     * 属性isshare
     */
	@ViewAttribute(name = "isshare", caption="是否共享", length =1, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal isshare;
    /** 
     * 属性createdate
     */
	@ViewAttribute(name = "createdate", caption="创建时间", length =1, editor=EditorType.DateTimeEditor,  nullable=true, readOnly=false,  type=AttributeType.DATE) 
	private Date createdate;
    /** 
     * 属性creator
     */
	@ViewAttribute(name = "creator", caption="创建人", length =36, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String creator;
    /** 
     * 属性isdelete
     */
	@ViewAttribute(name = "isdelete", caption="删除标记", length =1, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal isdelete;
    /** 
     * 属性updatetime
     */
	@ViewAttribute(name = "updatetime", caption="信息更新时间", length =1, editor=EditorType.DateTimeEditor,  nullable=true, readOnly=false,  type=AttributeType.DATE) 
	private Date updatetime;
    /** 
     * 属性updatepersonid
     */
	@ViewAttribute(name = "updatepersonid", caption="信息更新人编号", length =36, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String updatepersonid;
    /** 
     * 属性contracttemplatecode
     */
	@ViewAttribute(name = "contracttemplatecode", caption="合同范本编号", length =36, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String contracttemplatecode;
    /** 
     * 属性dicId
     */
	@ViewAttribute(name = "dicId", caption="合同范本参数字典ID", length =36, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String dicId;
    /**
     * CoContractparameterVO构造函数
     */
    public CoContractparameterVO() {
        super();
    }  
	
    /**
     * CoContractparameterVO完整的构造函数
     */  
    public CoContractparameterVO(String marketid,String tempparamid,String tempparamname,BigDecimal isshare,BigDecimal isdelete){
        this.marketid = marketid;
        this.tempparamid = tempparamid;
        this.tempparamname = tempparamname;
        this.isshare = isshare;
        this.isdelete = isdelete;
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
     * 属性 tempparamid 的get方法
     * @return String
     */
    public String getTempparamid(){
        return tempparamid;
    }
	
    /**
     * 属性 tempparamid 的set方法
     * @return
     */
    public void setTempparamid(String tempparamid){
        if(tempparamid != null && tempparamid.trim().length() == 0){
            this.tempparamid = null;
        }else{
            this.tempparamid = tempparamid;
        }
    } 
	
    /**
     * 属性 tempparamname 的get方法
     * @return String
     */
    public String getTempparamname(){
        return tempparamname;
    }
	
    /**
     * 属性 tempparamname 的set方法
     * @return
     */
    public void setTempparamname(String tempparamname){
        this.tempparamname = tempparamname;
    } 
	
    /**
     * 属性 tempparamex 的get方法
     * @return String
     */
    public String getTempparamex(){
        return tempparamex;
    }
	
    /**
     * 属性 tempparamex 的set方法
     * @return
     */
    public void setTempparamex(String tempparamex){
        this.tempparamex = tempparamex;
    } 
	
    /**
     * 属性 tempparamsql 的get方法
     * @return String
     */
    public String getTempparamsql(){
        return tempparamsql;
    }
	
    /**
     * 属性 tempparamsql 的set方法
     * @return
     */
    public void setTempparamsql(String tempparamsql){
        this.tempparamsql = tempparamsql;
    } 
	
    /**
     * 属性 tempparamdesc 的get方法
     * @return String
     */
    public String getTempparamdesc(){
        return tempparamdesc;
    }
	
    /**
     * 属性 tempparamdesc 的set方法
     * @return
     */
    public void setTempparamdesc(String tempparamdesc){
        this.tempparamdesc = tempparamdesc;
    } 
	
    /**
     * 属性 contracttype 的get方法
     * @return String
     */
    public String getContracttype(){
        return contracttype;
    }
	
    /**
     * 属性 contracttype 的set方法
     * @return
     */
    public void setContracttype(String contracttype){
        this.contracttype = contracttype;
    } 
	
    /**
     * 属性 starteffectivedate 的get方法
     * @return Date
     */
    public Date getStarteffectivedate(){
        return starteffectivedate;
    }
	
    /**
     * 属性 starteffectivedate 的set方法
     * @return
     */
    public void setStarteffectivedate(Date starteffectivedate){
        this.starteffectivedate = starteffectivedate;
    } 
	
    /**
     * 属性 endeffectivedate 的get方法
     * @return Date
     */
    public Date getEndeffectivedate(){
        return endeffectivedate;
    }
	
    /**
     * 属性 endeffectivedate 的set方法
     * @return
     */
    public void setEndeffectivedate(Date endeffectivedate){
        this.endeffectivedate = endeffectivedate;
    } 
	
    /**
     * 属性 isshare 的get方法
     * @return BigDecimal
     */
    public BigDecimal getIsshare(){
        return isshare;
    }
	
    /**
     * 属性 isshare 的set方法
     * @return
     */
    public void setIsshare(BigDecimal isshare){
        this.isshare = isshare;
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
	
    /**
     * 属性 creator 的get方法
     * @return String
     */
    public String getCreator(){
        return creator;
    }
	
    /**
     * 属性 creator 的set方法
     * @return
     */
    public void setCreator(String creator){
        this.creator = creator;
    } 
	
    /**
     * 属性 isdelete 的get方法
     * @return BigDecimal
     */
    public BigDecimal getIsdelete(){
        return isdelete;
    }
	
    /**
     * 属性 isdelete 的set方法
     * @return
     */
    public void setIsdelete(BigDecimal isdelete){
        this.isdelete = isdelete;
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
     * 属性 contracttemplatecode 的get方法
     * @return String
     */
    public String getContracttemplatecode(){
        return contracttemplatecode;
    }
	
    /**
     * 属性 contracttemplatecode 的set方法
     * @return
     */
    public void setContracttemplatecode(String contracttemplatecode){
        this.contracttemplatecode = contracttemplatecode;
    } 
	
    /**
     * 属性 dicId 的get方法
     * @return String
     */
    public String getDicId(){
        return dicId;
    }
	
    /**
     * 属性 dicId 的set方法
     * @return
     */
    public void setDicId(String dicId){
        this.dicId = dicId;
    } 
	

    /**
     * toString方法
     * @return String
     */
	public String toString(){

		  return new StringBuffer()
            .append("marketid"+":"+getMarketid())
            .append("tempparamid"+":"+getTempparamid())
            .append("tempparamname"+":"+getTempparamname())
            .append("tempparamex"+":"+getTempparamex())
            .append("tempparamsql"+":"+getTempparamsql())
            .append("tempparamdesc"+":"+getTempparamdesc())
            .append("contracttype"+":"+getContracttype())
            .append("starteffectivedate"+":"+getStarteffectivedate())
            .append("endeffectivedate"+":"+getEndeffectivedate())
            .append("isshare"+":"+getIsshare())
            .append("createdate"+":"+getCreatedate())
            .append("creator"+":"+getCreator())
            .append("isdelete"+":"+getIsdelete())
            .append("updatetime"+":"+getUpdatetime())
            .append("updatepersonid"+":"+getUpdatepersonid())
            .append("contracttemplatecode"+":"+getContracttemplatecode())
            .append("dicId"+":"+getDicId())
            .toString(); 
			
    } 
   


}