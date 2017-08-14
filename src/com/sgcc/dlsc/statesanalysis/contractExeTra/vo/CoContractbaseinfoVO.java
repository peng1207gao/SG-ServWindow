package com.sgcc.dlsc.statesanalysis.contractExeTra.vo;
import java.math.BigDecimal;
import java.util.Date;

import java.io.Serializable;
import com.sgcc.uap.rest.annotation.attribute.EditorType;
import com.sgcc.uap.rest.annotation.attribute.AttributeType;
import com.sgcc.uap.rest.annotation.attribute.ViewAttribute;
import com.sgcc.uap.rest.support.ParentVO;

/**
 * CoContractbaseinfo的VO类
 *
 * @author  Administrator  [Tue Apr 22 20:45:47 CST 2014]
 * 
 */
public class CoContractbaseinfoVO extends ParentVO implements Serializable{

	private static final long serialVersionUID = 1L;

    /** 
     * 属性marketid
     */
	@ViewAttribute(name = "marketid", caption="市场ID", length =36, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String marketid;
    /** 
     * 属性contractid
     */
	@ViewAttribute(name = "contractid", caption="合同ID", length =36, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String contractid;
    /** 
     * 属性supercontractid
     */
	@ViewAttribute(name = "supercontractid", caption="上级合同ID", length =36, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String supercontractid;
    /** 
     * 属性contractname
     */
	@ViewAttribute(name = "contractname", caption="合同名称", length =200, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String contractname;
    /** 
     * 属性contracttype
     */
	@ViewAttribute(name = "contracttype", caption="合同类型", length =64, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String contracttype;
    /** 
     * 属性papercontractcode
     */
	@ViewAttribute(name = "papercontractcode", caption="纸质合同编号", length =64, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String papercontractcode;
    /** 
     * 属性papercontractname
     */
	@ViewAttribute(name = "papercontractname", caption="纸质合同名称", length =200, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String papercontractname;
    /** 
     * 属性contracttemplateid
     */
	@ViewAttribute(name = "contracttemplateid", caption="合同范本ID", length =36, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String contracttemplateid;
    /** 
     * 属性signstate
     */
	@ViewAttribute(name = "signstate", caption="签订状态", length =2, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal signstate;
    /** 
     * 属性signeddate
     */
	@ViewAttribute(name = "signeddate", caption="签订时间", length =2, editor=EditorType.DateTimeEditor,  nullable=true, readOnly=false,  type=AttributeType.DATE) 
	private Date signeddate;
    /** 
     * 属性notsignedreason
     */
	@ViewAttribute(name = "notsignedreason", caption="合同未签订原因", length =256, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String notsignedreason;
    /** 
     * 属性signedperson
     */
	@ViewAttribute(name = "signedperson", caption="合同签订人", length =16, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String signedperson;
    /** 
     * 属性signedsite
     */
	@ViewAttribute(name = "signedsite", caption="合同签订地点", length =32, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String signedsite;
    /** 
     * 属性backupperson
     */
	@ViewAttribute(name = "backupperson", caption="合同备案人", length =16, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String backupperson;
    /** 
     * 属性backupdate
     */
	@ViewAttribute(name = "backupdate", caption="合同备案时间", length =16, editor=EditorType.DateTimeEditor,  nullable=true, readOnly=false,  type=AttributeType.DATE) 
	private Date backupdate;
    /** 
     * 属性backupstate
     */
	@ViewAttribute(name = "backupstate", caption="备案状态", length =2, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal backupstate;
    /** 
     * 属性backuporg
     */
	@ViewAttribute(name = "backuporg", caption="合同备案机构", length =64, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String backuporg;
    /** 
     * 属性disbackupreason
     */
	@ViewAttribute(name = "disbackupreason", caption="未备案原因", length =16, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String disbackupreason;
    /** 
     * 属性isend
     */
	@ViewAttribute(name = "isend", caption="是否到期", length =1, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal isend;
    /** 
     * 属性prepcontractflag
     */
	@ViewAttribute(name = "prepcontractflag", caption="预合同标志", length =1, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal prepcontractflag;
    /** 
     * 属性issecrecyflag
     */
	@ViewAttribute(name = "issecrecyflag", caption="是否自定义", length =1, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal issecrecyflag;
    /** 
     * 属性contractcyc
     */
	@ViewAttribute(name = "contractcyc", caption="合同周期 合同类型（）", length =2, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal contractcyc;
    /** 
     * 属性purchaser
     */
	@ViewAttribute(name = "purchaser", caption="购电方", length =36, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String purchaser;
    /** 
     * 属性seller
     */
	@ViewAttribute(name = "seller", caption="售电方", length =36, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String seller;
    /** 
     * 属性sellerunit
     */
	@ViewAttribute(name = "sellerunit", caption="售电单元", length =36, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String sellerunit;
    /** 
     * 属性purchaseunit
     */
	@ViewAttribute(name = "purchaseunit", caption="购电单元", length =36, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String purchaseunit;
    /** 
     * 属性contractqty
     */
	@ViewAttribute(name = "contractqty", caption="合同电力", length =20, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal contractqty;
    /** 
     * 属性qtytype
     */
	@ViewAttribute(name = "qtytype", caption="电量口径", length =2, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal qtytype;
    /** 
     * 属性contractstartdate
     */
	@ViewAttribute(name = "contractstartdate", caption="合同开始时间 1", length =2, editor=EditorType.DateTimeEditor,  nullable=true, readOnly=false,  type=AttributeType.DATE) 
	private Date contractstartdate;
    /** 
     * 属性contractenddate
     */
	@ViewAttribute(name = "contractenddate", caption="合同结束时间", length =2, editor=EditorType.DateTimeEditor,  nullable=true, readOnly=false,  type=AttributeType.DATE) 
	private Date contractenddate;
    /** 
     * 属性transactionid
     */
	@ViewAttribute(name = "transactionid", caption="交易ID", length =36, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String transactionid;
    /** 
     * 属性transactiontype
     */
	@ViewAttribute(name = "transactiontype", caption="交易性质", length =64, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String transactiontype;
    /** 
     * 属性rightsettlementside
     */
	@ViewAttribute(name = "rightsettlementside", caption="发电权结算方", length =2, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal rightsettlementside;
    /** 
     * 属性isrepurchaseflag
     */
	@ViewAttribute(name = "isrepurchaseflag", caption="是否回购交易", length =1, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal isrepurchaseflag;
    /** 
     * 属性replacetype
     */
	@ViewAttribute(name = "replacetype", caption="替代类型", length =16, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String replacetype;
    /** 
     * 属性areatype
     */
	@ViewAttribute(name = "areatype", caption="区域类型", length =2, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal areatype;
    /** 
     * 属性isehvflag
     */
	@ViewAttribute(name = "isehvflag", caption="是特高压交易标志", length =1, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal isehvflag;
    /** 
     * 属性isresaleflag
     */
	@ViewAttribute(name = "isresaleflag", caption="是代购转售交易标志", length =1, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal isresaleflag;
    /** 
     * 属性isopen
     */
	@ViewAttribute(name = "isopen", caption="是否开口合同", length =1, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal isopen;
    /** 
     * 属性purchasegate
     */
	@ViewAttribute(name = "purchasegate", caption="购电关口", length =36, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String purchasegate;
    /** 
     * 属性settlegate
     */
	@ViewAttribute(name = "settlegate", caption="售电关口", length =36, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String settlegate;
    /** 
     * 属性lossundertaker
     */
	@ViewAttribute(name = "lossundertaker", caption="线损承担方", length =2, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal lossundertaker;
    /** 
     * 属性purchaserperson
     */
	@ViewAttribute(name = "purchaserperson", caption="购电方联系人", length =32, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String purchaserperson;
    /** 
     * 属性purchaserphone
     */
	@ViewAttribute(name = "purchaserphone", caption="购电方联系电话", length =32, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String purchaserphone;
    /** 
     * 属性purchaserconftime
     */
	@ViewAttribute(name = "purchaserconftime", caption="购电方确认时间", length =32, editor=EditorType.DateTimeEditor,  nullable=true, readOnly=false,  type=AttributeType.DATE) 
	private Date purchaserconftime;
    /** 
     * 属性sellerperson
     */
	@ViewAttribute(name = "sellerperson", caption="售电方联系人", length =32, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String sellerperson;
    /** 
     * 属性sellerphone
     */
	@ViewAttribute(name = "sellerphone", caption="售电方联系电话", length =32, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String sellerphone;
    /** 
     * 属性sellerconftime
     */
	@ViewAttribute(name = "sellerconftime", caption="售电方确认时间", length =32, editor=EditorType.DateTimeEditor,  nullable=true, readOnly=false,  type=AttributeType.DATE) 
	private Date sellerconftime;
    /** 
     * 属性transperson
     */
	@ViewAttribute(name = "transperson", caption="输电方联系人", length =32, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String transperson;
    /** 
     * 属性transphone
     */
	@ViewAttribute(name = "transphone", caption="输电方联系电话", length =20, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String transphone;
    /** 
     * 属性transconftime
     */
	@ViewAttribute(name = "transconftime", caption="输电方确认时间", length =20, editor=EditorType.DateTimeEditor,  nullable=true, readOnly=false,  type=AttributeType.DATE) 
	private Date transconftime;
    /** 
     * 属性changetimes
     */
	@ViewAttribute(name = "changetimes", caption="变更次数", length =4, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal changetimes;
    /** 
     * 属性description
     */
	@ViewAttribute(name = "description", caption="说明", length =256, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String description;
    /** 
     * 属性isdelete
     */
	@ViewAttribute(name = "isdelete", caption="删除标记", length =1, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal isdelete;
    /** 
     * 属性updatetime
     */
	@ViewAttribute(name = "updatetime", caption="更新日期", length =1, editor=EditorType.DateTimeEditor,  nullable=true, readOnly=false,  type=AttributeType.DATE) 
	private Date updatetime;
    /** 
     * 属性updatepersonid
     */
	@ViewAttribute(name = "updatepersonid", caption="更新人id", length =36, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String updatepersonid;
    /** 
     * 属性versionid
     */
	@ViewAttribute(name = "versionid", caption="版本id", length =36, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String versionid;
    /** 
     * 属性version
     */
	@ViewAttribute(name = "version", caption="版本号", length =16, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String version;
    /** 
     * 属性versiondesc
     */
	@ViewAttribute(name = "versiondesc", caption="版本说明", length =256, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String versiondesc;
    /** 
     * 属性isrelation
     */
	@ViewAttribute(name = "isrelation", caption="关联权限", length =2, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal isrelation;
    /** 
     * 属性exectype
     */
	@ViewAttribute(name = "exectype", caption="合同执行类型", length =2, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal exectype;
    /** 
     * 属性flow
     */
	@ViewAttribute(name = "flow", caption="合同流程", length =2, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal flow;
    /** 
     * 属性settleside
     */
	@ViewAttribute(name = "settleside", caption="合同结算方", length =2, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal settleside;
    /** 
     * 属性businessid
     */
	@ViewAttribute(name = "businessid", caption="业务序列ID", length =36, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String businessid;
    /** 
     * 属性flowflag
     */
	@ViewAttribute(name = "flowflag", caption="流程标志", length =2, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal flowflag;
    /** 
     * 属性sequenceid
     */
	@ViewAttribute(name = "sequenceid", caption="合同序列id", length =36, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String sequenceid;
	
	  /** 
     * 属性wcdl
     */
	@ViewAttribute(name = "wcdl", caption="完成电量", length =36, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String wcdl;
	
	  /** 
     * 属性wcdl
     */
	@ViewAttribute(name = "wcl", caption="完成率", length =36, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String wcl;
    /** 
     * 属性energy
     */
	@ViewAttribute(name = "energy", caption="合同容量", length =12, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal energy;
    /** 
     * 属性contractprice
     */
	@ViewAttribute(name = "contractprice", caption="合同电价", length =12, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal contractprice;
    /** 
     * 属性contractenergy
     */
	@ViewAttribute(name = "contractenergy", caption="合同电量", length =20, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal contractenergy;
    /** 
     * 属性superexecid
     */
	@ViewAttribute(name = "superexecid", caption="所属意向性ID", length =36, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String superexecid;
    /** 
     * 属性ingodown
     */
	@ViewAttribute(name = "ingodown", caption="是否入库", length =2, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal ingodown;
    /** 
     * 属性expend1
     */
	@ViewAttribute(name = "expend1", caption="??1", length =12, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal expend1;
    /** 
     * 属性expend2
     */
	@ViewAttribute(name = "expend2", caption="??2", length =12, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal expend2;
    /** 
     * 属性expend3
     */
	@ViewAttribute(name = "expend3", caption="??3", length =12, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal expend3;
    /** 
     * 属性expend4
     */
	@ViewAttribute(name = "expend4", caption="??4", length =12, editor=EditorType.DateTimeEditor,  nullable=true, readOnly=false,  type=AttributeType.DATE) 
	private Date expend4;
    /** 
     * 属性expend5
     */
	@ViewAttribute(name = "expend5", caption="??5", length =32, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String expend5;
    /** 
     * 属性expend6
     */
	@ViewAttribute(name = "expend6", caption="??6", length =32, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String expend6;
    /** 
     * 属性purchasergen
     */
	@ViewAttribute(name = "purchasergen", caption="购电方发电量", length =20, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal purchasergen;
    /** 
     * 属性purchaserenergy
     */
	@ViewAttribute(name = "purchaserenergy", caption="购电方上网电量", length =20, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal purchaserenergy;
    /** 
     * 属性sellergen
     */
	@ViewAttribute(name = "sellergen", caption="售电方发电量", length =20, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal sellergen;
    /** 
     * 属性sellerenergy
     */
	@ViewAttribute(name = "sellerenergy", caption="售电方上网电量", length =20, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal sellerenergy;
    /** 
     * 属性coVersion
     */
	@ViewAttribute(name = "coVersion", caption="合同版本", length =20, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String coVersion;
    /** 
     * 属性isTax
     */
	@ViewAttribute(name = "isTax", caption="是否含税", length =2, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal isTax;
    /** 
     * 属性vendeeGenRate
     */
	@ViewAttribute(name = "vendeeGenRate", caption="购方厂用电率", length =16, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal vendeeGenRate;
    /** 
     * 属性saleGenRate
     */
	@ViewAttribute(name = "saleGenRate", caption="售方厂用电率", length =16, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal saleGenRate;
    /** 
     * 属性saleLoss
     */
	@ViewAttribute(name = "saleLoss", caption="售方网损", length =16, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal saleLoss;
    /** 
     * 属性vendeeLoss
     */
	@ViewAttribute(name = "vendeeLoss", caption="购方网损", length =16, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal vendeeLoss;
    /** 
     * 属性vendeeBreathPrice
     */
	@ViewAttribute(name = "vendeeBreathPrice", caption="购方违约赔偿电价", length =12, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal vendeeBreathPrice;
    /** 
     * 属性saleBreathPrice
     */
	@ViewAttribute(name = "saleBreathPrice", caption="售方违约赔偿电价", length =12, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal saleBreathPrice;
    /** 
     * 属性approvedTariff
     */
	@ViewAttribute(name = "approvedTariff", caption="批复上网电价", length =12, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal approvedTariff;
    /** 
     * 属性catalogPrice
     */
	@ViewAttribute(name = "catalogPrice", caption="目录电价", length =12, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal catalogPrice;
    /** 
     * 属性transferAllotPrice
     */
	@ViewAttribute(name = "transferAllotPrice", caption="输配电价", length =12, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal transferAllotPrice;
    /** 
     * 属性netDiscountLoss
     */
	@ViewAttribute(name = "netDiscountLoss", caption="网损折价", length =12, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal netDiscountLoss;
    /** 
     * 属性fundsandaddPrice
     */
	@ViewAttribute(name = "fundsandaddPrice", caption="政府基金及附加", length =12, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal fundsandaddPrice;
    /** 
     * 属性tradePriceMargin
     */
	@ViewAttribute(name = "tradePriceMargin", caption="价差成交价格", length =12, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.BIGDECIMAL) 
	private BigDecimal tradePriceMargin;
    /**
     * CoContractbaseinfoVO构造函数
     */
    public CoContractbaseinfoVO() {
        super();
    }  
	
    /**
     * CoContractbaseinfoVO完整的构造函数
     */  
    public CoContractbaseinfoVO(String marketid,String contractid,BigDecimal isdelete,String versionid){
        this.marketid = marketid;
        this.contractid = contractid;
        this.isdelete = isdelete;
        this.versionid = versionid;
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
     * 属性 supercontractid 的get方法
     * @return String
     */
    public String getSupercontractid(){
        return supercontractid;
    }
	
    /**
     * 属性 supercontractid 的set方法
     * @return
     */
    public void setSupercontractid(String supercontractid){
        this.supercontractid = supercontractid;
    } 
	
    /**
     * 属性 contractname 的get方法
     * @return String
     */
    public String getContractname(){
        return contractname;
    }
	
    /**
     * 属性 contractname 的set方法
     * @return
     */
    public void setContractname(String contractname){
        this.contractname = contractname;
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
     * 属性 papercontractcode 的get方法
     * @return String
     */
    public String getPapercontractcode(){
        return papercontractcode;
    }
	
    /**
     * 属性 papercontractcode 的set方法
     * @return
     */
    public void setPapercontractcode(String papercontractcode){
        this.papercontractcode = papercontractcode;
    } 
	
    /**
     * 属性 papercontractname 的get方法
     * @return String
     */
    public String getPapercontractname(){
        return papercontractname;
    }
	
    /**
     * 属性 papercontractname 的set方法
     * @return
     */
    public void setPapercontractname(String papercontractname){
        this.papercontractname = papercontractname;
    } 
	
    /**
     * 属性 contracttemplateid 的get方法
     * @return String
     */
    public String getContracttemplateid(){
        return contracttemplateid;
    }
	
    /**
     * 属性 contracttemplateid 的set方法
     * @return
     */
    public void setContracttemplateid(String contracttemplateid){
        this.contracttemplateid = contracttemplateid;
    } 
	
    /**
     * 属性 signstate 的get方法
     * @return BigDecimal
     */
    public BigDecimal getSignstate(){
        return signstate;
    }
	
    /**
     * 属性 signstate 的set方法
     * @return
     */
    public void setSignstate(BigDecimal signstate){
        this.signstate = signstate;
    } 
	
    /**
     * 属性 signeddate 的get方法
     * @return Date
     */
    public Date getSigneddate(){
        return signeddate;
    }
	
    /**
     * 属性 signeddate 的set方法
     * @return
     */
    public void setSigneddate(Date signeddate){
        this.signeddate = signeddate;
    } 
	
    /**
     * 属性 notsignedreason 的get方法
     * @return String
     */
    public String getNotsignedreason(){
        return notsignedreason;
    }
	
    /**
     * 属性 notsignedreason 的set方法
     * @return
     */
    public void setNotsignedreason(String notsignedreason){
        this.notsignedreason = notsignedreason;
    } 
	
    /**
     * 属性 signedperson 的get方法
     * @return String
     */
    public String getSignedperson(){
        return signedperson;
    }
	
    /**
     * 属性 signedperson 的set方法
     * @return
     */
    public void setSignedperson(String signedperson){
        this.signedperson = signedperson;
    } 
	
    /**
     * 属性 signedsite 的get方法
     * @return String
     */
    public String getSignedsite(){
        return signedsite;
    }
	
    /**
     * 属性 signedsite 的set方法
     * @return
     */
    public void setSignedsite(String signedsite){
        this.signedsite = signedsite;
    } 
	
    /**
     * 属性 backupperson 的get方法
     * @return String
     */
    public String getBackupperson(){
        return backupperson;
    }
	
    /**
     * 属性 backupperson 的set方法
     * @return
     */
    public void setBackupperson(String backupperson){
        this.backupperson = backupperson;
    } 
	
    /**
     * 属性 backupdate 的get方法
     * @return Date
     */
    public Date getBackupdate(){
        return backupdate;
    }
	
    /**
     * 属性 backupdate 的set方法
     * @return
     */
    public void setBackupdate(Date backupdate){
        this.backupdate = backupdate;
    } 
	
    /**
     * 属性 backupstate 的get方法
     * @return BigDecimal
     */
    public BigDecimal getBackupstate(){
        return backupstate;
    }
	
    /**
     * 属性 backupstate 的set方法
     * @return
     */
    public void setBackupstate(BigDecimal backupstate){
        this.backupstate = backupstate;
    } 
	
    /**
     * 属性 backuporg 的get方法
     * @return String
     */
    public String getBackuporg(){
        return backuporg;
    }
	
    /**
     * 属性 backuporg 的set方法
     * @return
     */
    public void setBackuporg(String backuporg){
        this.backuporg = backuporg;
    } 
	
    /**
     * 属性 disbackupreason 的get方法
     * @return String
     */
    public String getDisbackupreason(){
        return disbackupreason;
    }
	
    /**
     * 属性 disbackupreason 的set方法
     * @return
     */
    public void setDisbackupreason(String disbackupreason){
        this.disbackupreason = disbackupreason;
    } 
	
    /**
     * 属性 isend 的get方法
     * @return BigDecimal
     */
    public BigDecimal getIsend(){
        return isend;
    }
	
    /**
     * 属性 isend 的set方法
     * @return
     */
    public void setIsend(BigDecimal isend){
        this.isend = isend;
    } 
	
    /**
     * 属性 prepcontractflag 的get方法
     * @return BigDecimal
     */
    public BigDecimal getPrepcontractflag(){
        return prepcontractflag;
    }
	
    /**
     * 属性 prepcontractflag 的set方法
     * @return
     */
    public void setPrepcontractflag(BigDecimal prepcontractflag){
        this.prepcontractflag = prepcontractflag;
    } 
	
    /**
     * 属性 issecrecyflag 的get方法
     * @return BigDecimal
     */
    public BigDecimal getIssecrecyflag(){
        return issecrecyflag;
    }
	
    /**
     * 属性 issecrecyflag 的set方法
     * @return
     */
    public void setIssecrecyflag(BigDecimal issecrecyflag){
        this.issecrecyflag = issecrecyflag;
    } 
	
    /**
     * 属性 contractcyc 的get方法
     * @return BigDecimal
     */
    public BigDecimal getContractcyc(){
        return contractcyc;
    }
	
    /**
     * 属性 contractcyc 的set方法
     * @return
     */
    public void setContractcyc(BigDecimal contractcyc){
        this.contractcyc = contractcyc;
    } 
	
    /**
     * 属性 purchaser 的get方法
     * @return String
     */
    public String getPurchaser(){
        return purchaser;
    }
	
    /**
     * 属性 purchaser 的set方法
     * @return
     */
    public void setPurchaser(String purchaser){
        this.purchaser = purchaser;
    } 
	
    /**
     * 属性 seller 的get方法
     * @return String
     */
    public String getSeller(){
        return seller;
    }
	
    /**
     * 属性 seller 的set方法
     * @return
     */
    public void setSeller(String seller){
        this.seller = seller;
    } 
	
    /**
     * 属性 sellerunit 的get方法
     * @return String
     */
    public String getSellerunit(){
        return sellerunit;
    }
	
    /**
     * 属性 sellerunit 的set方法
     * @return
     */
    public void setSellerunit(String sellerunit){
        this.sellerunit = sellerunit;
    } 
	
    /**
     * 属性 purchaseunit 的get方法
     * @return String
     */
    public String getPurchaseunit(){
        return purchaseunit;
    }
	
    /**
     * 属性 purchaseunit 的set方法
     * @return
     */
    public void setPurchaseunit(String purchaseunit){
        this.purchaseunit = purchaseunit;
    } 
	
    /**
     * 属性 contractqty 的get方法
     * @return BigDecimal
     */
    public BigDecimal getContractqty(){
        return contractqty;
    }
	
    /**
     * 属性 contractqty 的set方法
     * @return
     */
    public void setContractqty(BigDecimal string){
        this.contractqty = string;
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
     * 属性 contractstartdate 的get方法
     * @return Date
     */
    public Date getContractstartdate(){
        return contractstartdate;
    }
	
    /**
     * 属性 contractstartdate 的set方法
     * @return
     */
    public void setContractstartdate(Date contractstartdate){
        this.contractstartdate = contractstartdate;
    } 
	
    /**
     * 属性 contractenddate 的get方法
     * @return Date
     */
    public Date getContractenddate(){
        return contractenddate;
    }
	
    /**
     * 属性 contractenddate 的set方法
     * @return
     */
    public void setContractenddate(Date contractenddate){
        this.contractenddate = contractenddate;
    } 
	
    /**
     * 属性 transactionid 的get方法
     * @return String
     */
    public String getTransactionid(){
        return transactionid;
    }
	
    /**
     * 属性 transactionid 的set方法
     * @return
     */
    public void setTransactionid(String transactionid){
        this.transactionid = transactionid;
    } 
	
    /**
     * 属性 transactiontype 的get方法
     * @return String
     */
    public String getTransactiontype(){
        return transactiontype;
    }
	
    /**
     * 属性 transactiontype 的set方法
     * @return
     */
    public void setTransactiontype(String transactiontype){
        this.transactiontype = transactiontype;
    } 
	
    /**
     * 属性 rightsettlementside 的get方法
     * @return BigDecimal
     */
    public BigDecimal getRightsettlementside(){
        return rightsettlementside;
    }
	
    /**
     * 属性 rightsettlementside 的set方法
     * @return
     */
    public void setRightsettlementside(BigDecimal rightsettlementside){
        this.rightsettlementside = rightsettlementside;
    } 
	
    /**
     * 属性 isrepurchaseflag 的get方法
     * @return BigDecimal
     */
    public BigDecimal getIsrepurchaseflag(){
        return isrepurchaseflag;
    }
	
    /**
     * 属性 isrepurchaseflag 的set方法
     * @return
     */
    public void setIsrepurchaseflag(BigDecimal isrepurchaseflag){
        this.isrepurchaseflag = isrepurchaseflag;
    } 
	
    /**
     * 属性 replacetype 的get方法
     * @return String
     */
    public String getReplacetype(){
        return replacetype;
    }
	
    /**
     * 属性 replacetype 的set方法
     * @return
     */
    public void setReplacetype(String replacetype){
        this.replacetype = replacetype;
    } 
	
    /**
     * 属性 areatype 的get方法
     * @return BigDecimal
     */
    public BigDecimal getAreatype(){
        return areatype;
    }
	
    /**
     * 属性 areatype 的set方法
     * @return
     */
    public void setAreatype(BigDecimal areatype){
        this.areatype = areatype;
    } 
	
    /**
     * 属性 isehvflag 的get方法
     * @return BigDecimal
     */
    public BigDecimal getIsehvflag(){
        return isehvflag;
    }
	
    /**
     * 属性 isehvflag 的set方法
     * @return
     */
    public void setIsehvflag(BigDecimal isehvflag){
        this.isehvflag = isehvflag;
    } 
	
    /**
     * 属性 isresaleflag 的get方法
     * @return BigDecimal
     */
    public BigDecimal getIsresaleflag(){
        return isresaleflag;
    }
	
    /**
     * 属性 isresaleflag 的set方法
     * @return
     */
    public void setIsresaleflag(BigDecimal isresaleflag){
        this.isresaleflag = isresaleflag;
    } 
	
    /**
     * 属性 isopen 的get方法
     * @return BigDecimal
     */
    public BigDecimal getIsopen(){
        return isopen;
    }
	
    /**
     * 属性 isopen 的set方法
     * @return
     */
    public void setIsopen(BigDecimal isopen){
        this.isopen = isopen;
    } 
	
    /**
     * 属性 purchasegate 的get方法
     * @return String
     */
    public String getPurchasegate(){
        return purchasegate;
    }
	
    /**
     * 属性 purchasegate 的set方法
     * @return
     */
    public void setPurchasegate(String purchasegate){
        this.purchasegate = purchasegate;
    } 
	
    /**
     * 属性 settlegate 的get方法
     * @return String
     */
    public String getSettlegate(){
        return settlegate;
    }
	
    /**
     * 属性 settlegate 的set方法
     * @return
     */
    public void setSettlegate(String settlegate){
        this.settlegate = settlegate;
    } 
	
    /**
     * 属性 lossundertaker 的get方法
     * @return BigDecimal
     */
    public BigDecimal getLossundertaker(){
        return lossundertaker;
    }
	
    /**
     * 属性 lossundertaker 的set方法
     * @return
     */
    public void setLossundertaker(BigDecimal lossundertaker){
        this.lossundertaker = lossundertaker;
    } 
	
    /**
     * 属性 purchaserperson 的get方法
     * @return String
     */
    public String getPurchaserperson(){
        return purchaserperson;
    }
	
    /**
     * 属性 purchaserperson 的set方法
     * @return
     */
    public void setPurchaserperson(String purchaserperson){
        this.purchaserperson = purchaserperson;
    } 
	
    /**
     * 属性 purchaserphone 的get方法
     * @return String
     */
    public String getPurchaserphone(){
        return purchaserphone;
    }
	
    /**
     * 属性 purchaserphone 的set方法
     * @return
     */
    public void setPurchaserphone(String purchaserphone){
        this.purchaserphone = purchaserphone;
    } 
	
    /**
     * 属性 purchaserconftime 的get方法
     * @return Date
     */
    public Date getPurchaserconftime(){
        return purchaserconftime;
    }
	
    /**
     * 属性 purchaserconftime 的set方法
     * @return
     */
    public void setPurchaserconftime(Date purchaserconftime){
        this.purchaserconftime = purchaserconftime;
    } 
	
    /**
     * 属性 sellerperson 的get方法
     * @return String
     */
    public String getSellerperson(){
        return sellerperson;
    }
	
    /**
     * 属性 sellerperson 的set方法
     * @return
     */
    public void setSellerperson(String sellerperson){
        this.sellerperson = sellerperson;
    } 
	
    /**
     * 属性 sellerphone 的get方法
     * @return String
     */
    public String getSellerphone(){
        return sellerphone;
    }
	
    /**
     * 属性 sellerphone 的set方法
     * @return
     */
    public void setSellerphone(String sellerphone){
        this.sellerphone = sellerphone;
    } 
	
    /**
     * 属性 sellerconftime 的get方法
     * @return Date
     */
    public Date getSellerconftime(){
        return sellerconftime;
    }
	
    /**
     * 属性 sellerconftime 的set方法
     * @return
     */
    public void setSellerconftime(Date sellerconftime){
        this.sellerconftime = sellerconftime;
    } 
	
    /**
     * 属性 transperson 的get方法
     * @return String
     */
    public String getTransperson(){
        return transperson;
    }
	
    /**
     * 属性 transperson 的set方法
     * @return
     */
    public void setTransperson(String transperson){
        this.transperson = transperson;
    } 
	
    /**
     * 属性 transphone 的get方法
     * @return String
     */
    public String getTransphone(){
        return transphone;
    }
	
    /**
     * 属性 transphone 的set方法
     * @return
     */
    public void setTransphone(String transphone){
        this.transphone = transphone;
    } 
	
    /**
     * 属性 transconftime 的get方法
     * @return Date
     */
    public Date getTransconftime(){
        return transconftime;
    }
	
    /**
     * 属性 transconftime 的set方法
     * @return
     */
    public void setTransconftime(Date transconftime){
        this.transconftime = transconftime;
    } 
	
    /**
     * 属性 changetimes 的get方法
     * @return BigDecimal
     */
    public BigDecimal getChangetimes(){
        return changetimes;
    }
	
    /**
     * 属性 changetimes 的set方法
     * @return
     */
    public void setChangetimes(BigDecimal changetimes){
        this.changetimes = changetimes;
    } 
	
    /**
     * 属性 description 的get方法
     * @return String
     */
    public String getDescription(){
        return description;
    }
	
    /**
     * 属性 description 的set方法
     * @return
     */
    public void setDescription(String description){
        this.description = description;
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
     * 属性 versionid 的get方法
     * @return String
     */
    public String getVersionid(){
        return versionid;
    }
	
    /**
     * 属性 versionid 的set方法
     * @return
     */
    public void setVersionid(String versionid){
        this.versionid = versionid;
    } 
	
    /**
     * 属性 version 的get方法
     * @return String
     */
    public String getVersion(){
        return version;
    }
	
    /**
     * 属性 version 的set方法
     * @return
     */
    public void setVersion(String version){
        this.version = version;
    } 
	
    /**
     * 属性 versiondesc 的get方法
     * @return String
     */
    public String getVersiondesc(){
        return versiondesc;
    }
	
    /**
     * 属性 versiondesc 的set方法
     * @return
     */
    public void setVersiondesc(String versiondesc){
        this.versiondesc = versiondesc;
    } 
	
    /**
     * 属性 isrelation 的get方法
     * @return BigDecimal
     */
    public BigDecimal getIsrelation(){
        return isrelation;
    }
	
    /**
     * 属性 isrelation 的set方法
     * @return
     */
    public void setIsrelation(BigDecimal isrelation){
        this.isrelation = isrelation;
    } 
	
    /**
     * 属性 exectype 的get方法
     * @return BigDecimal
     */
    public BigDecimal getExectype(){
        return exectype;
    }
	
    /**
     * 属性 exectype 的set方法
     * @return
     */
    public void setExectype(BigDecimal exectype){
        this.exectype = exectype;
    } 
	
    /**
     * 属性 flow 的get方法
     * @return BigDecimal
     */
    public BigDecimal getFlow(){
        return flow;
    }
	
    /**
     * 属性 flow 的set方法
     * @return
     */
    public void setFlow(BigDecimal flow){
        this.flow = flow;
    } 
	
    /**
     * 属性 settleside 的get方法
     * @return BigDecimal
     */
    public BigDecimal getSettleside(){
        return settleside;
    }
	
    /**
     * 属性 settleside 的set方法
     * @return
     */
    public void setSettleside(BigDecimal settleside){
        this.settleside = settleside;
    } 
	
    /**
     * 属性 businessid 的get方法
     * @return String
     */
    public String getBusinessid(){
        return businessid;
    }
	
    /**
     * 属性 businessid 的set方法
     * @return
     */
    public void setBusinessid(String businessid){
        this.businessid = businessid;
    } 
	
    /**
     * 属性 flowflag 的get方法
     * @return BigDecimal
     */
    public BigDecimal getFlowflag(){
        return flowflag;
    }
	
    /**
     * 属性 flowflag 的set方法
     * @return
     */
    public void setFlowflag(BigDecimal flowflag){
        this.flowflag = flowflag;
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
        this.sequenceid = sequenceid;
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
     * 属性 contractprice 的get方法
     * @return BigDecimal
     */
    public BigDecimal getContractprice(){
        return contractprice;
    }
	
    /**
     * 属性 contractprice 的set方法
     * @return
     */
    public void setContractprice(BigDecimal contractprice){
        this.contractprice = contractprice;
    } 
	
    /**
     * 属性 contractenergy 的get方法
     * @return BigDecimal
     */
    public BigDecimal getContractenergy(){
        return contractenergy;
    }
	
    /**
     * 属性 contractenergy 的set方法
     * @return
     */
    public void setContractenergy(BigDecimal contractenergy){
        this.contractenergy = contractenergy;
    } 
	
    /**
     * 属性 superexecid 的get方法
     * @return String
     */
    public String getSuperexecid(){
        return superexecid;
    }
	
    /**
     * 属性 superexecid 的set方法
     * @return
     */
    public void setSuperexecid(String superexecid){
        this.superexecid = superexecid;
    } 
	
    /**
     * 属性 ingodown 的get方法
     * @return BigDecimal
     */
    public BigDecimal getIngodown(){
        return ingodown;
    }
	
    /**
     * 属性 ingodown 的set方法
     * @return
     */
    public void setIngodown(BigDecimal ingodown){
        this.ingodown = ingodown;
    } 
	
    /**
     * 属性 expend1 的get方法
     * @return BigDecimal
     */
    public BigDecimal getExpend1(){
        return expend1;
    }
	
    /**
     * 属性 expend1 的set方法
     * @return
     */
    public void setExpend1(BigDecimal expend1){
        this.expend1 = expend1;
    } 
	
    /**
     * 属性 expend2 的get方法
     * @return BigDecimal
     */
    public BigDecimal getExpend2(){
        return expend2;
    }
	
    /**
     * 属性 expend2 的set方法
     * @return
     */
    public void setExpend2(BigDecimal expend2){
        this.expend2 = expend2;
    } 
	
    /**
     * 属性 expend3 的get方法
     * @return Date
     */
    public BigDecimal getExpend3(){
        return expend3;
    }
	
    /**
     * 属性 expend3 的set方法
     * @return
     */
    public void setExpend3(BigDecimal expend3){
        this.expend3 = expend3;
    } 
	
    /**
     * 属性 expend4 的get方法
     * @return Date
     */
    public Date getExpend4(){
        return expend4;
    }
	
    /**
     * 属性 expend4 的set方法
     * @return
     */
    public void setExpend4(Date expend4){
        this.expend4 = expend4;
    } 
	
    /**
     * 属性 expend5 的get方法
     * @return String
     */
    public String getExpend5(){
        return expend5;
    }
	
    /**
     * 属性 expend5 的set方法
     * @return
     */
    public void setExpend5(String expend5){
        this.expend5 = expend5;
    } 
	
    /**
     * 属性 expend6 的get方法
     * @return String
     */
    public String getExpend6(){
        return expend6;
    }
	
    /**
     * 属性 expend6 的set方法
     * @return
     */
    public void setExpend6(String expend6){
        this.expend6 = expend6;
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
     * 属性 coVersion 的get方法
     * @return String
     */
    public String getCoVersion(){
        return coVersion;
    }
	
    /**
     * 属性 coVersion 的set方法
     * @return
     */
    public void setCoVersion(String coVersion){
        this.coVersion = coVersion;
    } 
	
    /**
     * 属性 isTax 的get方法
     * @return BigDecimal
     */
    public BigDecimal getIsTax(){
        return isTax;
    }
	
    /**
     * 属性 isTax 的set方法
     * @return
     */
    public void setIsTax(BigDecimal isTax){
        this.isTax = isTax;
    } 
	
    /**
     * 属性 vendeeGenRate 的get方法
     * @return BigDecimal
     */
    public BigDecimal getVendeeGenRate(){
        return vendeeGenRate;
    }
	
    /**
     * 属性 vendeeGenRate 的set方法
     * @return
     */
    public void setVendeeGenRate(BigDecimal vendeeGenRate){
        this.vendeeGenRate = vendeeGenRate;
    } 
	
    /**
     * 属性 saleGenRate 的get方法
     * @return BigDecimal
     */
    public BigDecimal getSaleGenRate(){
        return saleGenRate;
    }
	
    /**
     * 属性 saleGenRate 的set方法
     * @return
     */
    public void setSaleGenRate(BigDecimal saleGenRate){
        this.saleGenRate = saleGenRate;
    } 
	
    /**
     * 属性 saleLoss 的get方法
     * @return BigDecimal
     */
    public BigDecimal getSaleLoss(){
        return saleLoss;
    }
	
    /**
     * 属性 saleLoss 的set方法
     * @return
     */
    public void setSaleLoss(BigDecimal saleLoss){
        this.saleLoss = saleLoss;
    } 
	
    /**
     * 属性 vendeeLoss 的get方法
     * @return BigDecimal
     */
    public BigDecimal getVendeeLoss(){
        return vendeeLoss;
    }
	
    /**
     * 属性 vendeeLoss 的set方法
     * @return
     */
    public void setVendeeLoss(BigDecimal vendeeLoss){
        this.vendeeLoss = vendeeLoss;
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
     * 属性 approvedTariff 的get方法
     * @return BigDecimal
     */
    public BigDecimal getApprovedTariff(){
        return approvedTariff;
    }
	
    /**
     * 属性 approvedTariff 的set方法
     * @return
     */
    public void setApprovedTariff(BigDecimal approvedTariff){
        this.approvedTariff = approvedTariff;
    } 
	
    /**
     * 属性 catalogPrice 的get方法
     * @return BigDecimal
     */
    public BigDecimal getCatalogPrice(){
        return catalogPrice;
    }
	
    /**
     * 属性 catalogPrice 的set方法
     * @return
     */
    public void setCatalogPrice(BigDecimal catalogPrice){
        this.catalogPrice = catalogPrice;
    } 
	
    /**
     * 属性 transferAllotPrice 的get方法
     * @return BigDecimal
     */
    public BigDecimal getTransferAllotPrice(){
        return transferAllotPrice;
    }
	
    /**
     * 属性 transferAllotPrice 的set方法
     * @return
     */
    public void setTransferAllotPrice(BigDecimal transferAllotPrice){
        this.transferAllotPrice = transferAllotPrice;
    } 
	
    /**
     * 属性 netDiscountLoss 的get方法
     * @return BigDecimal
     */
    public BigDecimal getNetDiscountLoss(){
        return netDiscountLoss;
    }
	
    /**
     * 属性 netDiscountLoss 的set方法
     * @return
     */
    public void setNetDiscountLoss(BigDecimal netDiscountLoss){
        this.netDiscountLoss = netDiscountLoss;
    } 
	
    /**
     * 属性 fundsandaddPrice 的get方法
     * @return BigDecimal
     */
    public BigDecimal getFundsandaddPrice(){
        return fundsandaddPrice;
    }
	
    /**
     * 属性 fundsandaddPrice 的set方法
     * @return
     */
    public void setFundsandaddPrice(BigDecimal fundsandaddPrice){
        this.fundsandaddPrice = fundsandaddPrice;
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
     * toString方法
     * @return String
     */
	public String toString(){

		  return new StringBuffer()
            .append("marketid"+":"+getMarketid())
            .append("contractid"+":"+getContractid())
            .append("supercontractid"+":"+getSupercontractid())
            .append("contractname"+":"+getContractname())
            .append("contracttype"+":"+getContracttype())
            .append("papercontractcode"+":"+getPapercontractcode())
            .append("papercontractname"+":"+getPapercontractname())
            .append("contracttemplateid"+":"+getContracttemplateid())
            .append("signstate"+":"+getSignstate())
            .append("signeddate"+":"+getSigneddate())
            .append("notsignedreason"+":"+getNotsignedreason())
            .append("signedperson"+":"+getSignedperson())
            .append("signedsite"+":"+getSignedsite())
            .append("backupperson"+":"+getBackupperson())
            .append("backupdate"+":"+getBackupdate())
            .append("backupstate"+":"+getBackupstate())
            .append("backuporg"+":"+getBackuporg())
            .append("disbackupreason"+":"+getDisbackupreason())
            .append("isend"+":"+getIsend())
            .append("prepcontractflag"+":"+getPrepcontractflag())
            .append("issecrecyflag"+":"+getIssecrecyflag())
            .append("contractcyc"+":"+getContractcyc())
            .append("purchaser"+":"+getPurchaser())
            .append("seller"+":"+getSeller())
            .append("sellerunit"+":"+getSellerunit())
            .append("purchaseunit"+":"+getPurchaseunit())
            .append("contractqty"+":"+getContractqty())
            .append("qtytype"+":"+getQtytype())
            .append("contractstartdate"+":"+getContractstartdate())
            .append("contractenddate"+":"+getContractenddate())
            .append("transactionid"+":"+getTransactionid())
            .append("transactiontype"+":"+getTransactiontype())
            .append("rightsettlementside"+":"+getRightsettlementside())
            .append("isrepurchaseflag"+":"+getIsrepurchaseflag())
            .append("replacetype"+":"+getReplacetype())
            .append("areatype"+":"+getAreatype())
            .append("isehvflag"+":"+getIsehvflag())
            .append("isresaleflag"+":"+getIsresaleflag())
            .append("isopen"+":"+getIsopen())
            .append("purchasegate"+":"+getPurchasegate())
            .append("settlegate"+":"+getSettlegate())
            .append("lossundertaker"+":"+getLossundertaker())
            .append("purchaserperson"+":"+getPurchaserperson())
            .append("purchaserphone"+":"+getPurchaserphone())
            .append("purchaserconftime"+":"+getPurchaserconftime())
            .append("sellerperson"+":"+getSellerperson())
            .append("sellerphone"+":"+getSellerphone())
            .append("sellerconftime"+":"+getSellerconftime())
            .append("transperson"+":"+getTransperson())
            .append("transphone"+":"+getTransphone())
            .append("transconftime"+":"+getTransconftime())
            .append("changetimes"+":"+getChangetimes())
            .append("description"+":"+getDescription())
            .append("isdelete"+":"+getIsdelete())
            .append("updatetime"+":"+getUpdatetime())
            .append("updatepersonid"+":"+getUpdatepersonid())
            .append("versionid"+":"+getVersionid())
            .append("version"+":"+getVersion())
            .append("versiondesc"+":"+getVersiondesc())
            .append("isrelation"+":"+getIsrelation())
            .append("exectype"+":"+getExectype())
            .append("flow"+":"+getFlow())
            .append("settleside"+":"+getSettleside())
            .append("businessid"+":"+getBusinessid())
            .append("flowflag"+":"+getFlowflag())
            .append("sequenceid"+":"+getSequenceid())
            .append("energy"+":"+getEnergy())
            .append("contractprice"+":"+getContractprice())
            .append("contractenergy"+":"+getContractenergy())
            .append("superexecid"+":"+getSuperexecid())
            .append("ingodown"+":"+getIngodown())
            .append("expend1"+":"+getExpend1())
            .append("expend2"+":"+getExpend2())
            .append("expend3"+":"+getExpend3())
            .append("expend4"+":"+getExpend4())
            .append("expend5"+":"+getExpend5())
            .append("expend6"+":"+getExpend6())
            .append("purchasergen"+":"+getPurchasergen())
            .append("purchaserenergy"+":"+getPurchaserenergy())
            .append("sellergen"+":"+getSellergen())
            .append("sellerenergy"+":"+getSellerenergy())
            .append("coVersion"+":"+getCoVersion())
            .append("isTax"+":"+getIsTax())
            .append("vendeeGenRate"+":"+getVendeeGenRate())
            .append("saleGenRate"+":"+getSaleGenRate())
            .append("saleLoss"+":"+getSaleLoss())
            .append("vendeeLoss"+":"+getVendeeLoss())
            .append("vendeeBreathPrice"+":"+getVendeeBreathPrice())
            .append("saleBreathPrice"+":"+getSaleBreathPrice())
            .append("approvedTariff"+":"+getApprovedTariff())
            .append("catalogPrice"+":"+getCatalogPrice())
            .append("transferAllotPrice"+":"+getTransferAllotPrice())
            .append("netDiscountLoss"+":"+getNetDiscountLoss())
            .append("fundsandaddPrice"+":"+getFundsandaddPrice())
            .append("tradePriceMargin"+":"+getTradePriceMargin())
            .toString(); 
			
    }

	public String getWcdl() {
		return wcdl;
	}

	public void setWcdl(String wcdl) {
		this.wcdl = wcdl;
	}

	public String getWcl() {
		return wcl;
	}

	public void setWcl(String wcl) {
		this.wcl = wcl;
	} 
   


}