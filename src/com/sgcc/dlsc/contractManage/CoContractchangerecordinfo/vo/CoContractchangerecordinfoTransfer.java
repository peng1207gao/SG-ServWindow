package com.sgcc.dlsc.contractManage.CoContractchangerecordinfo.vo;


import com.sgcc.dlsc.entity.po.CoContractchangerecordinfo;

public class CoContractchangerecordinfoTransfer {

	public static CoContractchangerecordinfo toPO(CoContractchangerecordinfoVO vo) {
		CoContractchangerecordinfo coContractchangerecordinfo = new CoContractchangerecordinfo();
		if(vo != null){
	         coContractchangerecordinfo.setMarketid(vo.getMarketid());
	         coContractchangerecordinfo.setContractid(vo.getContractid());
	         coContractchangerecordinfo.setSupercontractid(vo.getSupercontractid());
	         coContractchangerecordinfo.setContractname(vo.getContractname());
	         coContractchangerecordinfo.setContracttype(vo.getContracttype());
	         coContractchangerecordinfo.setPapercontractcode(vo.getPapercontractcode());
	         coContractchangerecordinfo.setPapercontractname(vo.getPapercontractname());
	         coContractchangerecordinfo.setContracttemplateid(vo.getContracttemplateid());
	         coContractchangerecordinfo.setSignstate(vo.getSignstate());
	         coContractchangerecordinfo.setSigneddate(vo.getSigneddate());
	         coContractchangerecordinfo.setNotsignedreason(vo.getNotsignedreason());
	         coContractchangerecordinfo.setSignedperson(vo.getSignedperson());
	         coContractchangerecordinfo.setSignedsite(vo.getSignedsite());
	         coContractchangerecordinfo.setBackupstate(vo.getBackupstate());
	         coContractchangerecordinfo.setBackupdate(vo.getBackupdate());
	         coContractchangerecordinfo.setBackupperson(vo.getBackupperson());
	         coContractchangerecordinfo.setBackuporg(vo.getBackuporg());
	         coContractchangerecordinfo.setDisbackupreason(vo.getDisbackupreason());
	         coContractchangerecordinfo.setIsend(vo.getIsend());
	         coContractchangerecordinfo.setPrepcontractflag(vo.getPrepcontractflag());
	         coContractchangerecordinfo.setIssecrecyflag(vo.getIssecrecyflag());
	         coContractchangerecordinfo.setContractcyc(vo.getContractcyc());
	         coContractchangerecordinfo.setPurchaser(vo.getPurchaser());
	         coContractchangerecordinfo.setSeller(vo.getSeller());
	         coContractchangerecordinfo.setSellerunit(vo.getSellerunit());
	         coContractchangerecordinfo.setPurchaseunit(vo.getPurchaseunit());
	         coContractchangerecordinfo.setContractqty(vo.getContractqty());
	         coContractchangerecordinfo.setQtytype(vo.getQtytype());
	         coContractchangerecordinfo.setContractstartdate(vo.getContractstartdate());
	         coContractchangerecordinfo.setContractenddate(vo.getContractenddate());
	         coContractchangerecordinfo.setTransactionid(vo.getTransactionid());
	         coContractchangerecordinfo.setTransactiontype(vo.getTransactiontype());
	         coContractchangerecordinfo.setRightsettlementside(vo.getRightsettlementside());
	         coContractchangerecordinfo.setIsrepurchaseflag(vo.getIsrepurchaseflag());
	         coContractchangerecordinfo.setReplacetype(vo.getReplacetype());
	         coContractchangerecordinfo.setAreatype(vo.getAreatype());
	         coContractchangerecordinfo.setIsehvflag(vo.getIsehvflag());
	         coContractchangerecordinfo.setIsresaleflag(vo.getIsresaleflag());
	         coContractchangerecordinfo.setIsopen(vo.getIsopen());
	         coContractchangerecordinfo.setPurchasegate(vo.getPurchasegate());
	         coContractchangerecordinfo.setSettlegate(vo.getSettlegate());
	         coContractchangerecordinfo.setLossundertaker(vo.getLossundertaker());
	         coContractchangerecordinfo.setPurchaserperson(vo.getPurchaserperson());
	         coContractchangerecordinfo.setPurchaserphone(vo.getPurchaserphone());
	         coContractchangerecordinfo.setPurchaserconftime(vo.getPurchaserconftime());
	         coContractchangerecordinfo.setSellerperson(vo.getSellerperson());
	         coContractchangerecordinfo.setSellerphone(vo.getSellerphone());
	         coContractchangerecordinfo.setSellerconftime(vo.getSellerconftime());
	         coContractchangerecordinfo.setTransperson(vo.getTransperson());
	         coContractchangerecordinfo.setTransphone(vo.getTransphone());
	         coContractchangerecordinfo.setTransconftime(vo.getTransconftime());
	         coContractchangerecordinfo.setChangetimes(vo.getChangetimes());
	         coContractchangerecordinfo.setDescription(vo.getDescription());
	         coContractchangerecordinfo.setIsdelete(vo.getIsdelete());
	         coContractchangerecordinfo.setUpdatetime(vo.getUpdatetime());
	         coContractchangerecordinfo.setUpdatepersonid(vo.getUpdatepersonid());
	         coContractchangerecordinfo.setVersionid(vo.getVersionid());
	         coContractchangerecordinfo.setVersion(vo.getVersion());
	         coContractchangerecordinfo.setVersiondesc(vo.getVersiondesc());
	         coContractchangerecordinfo.setIsrelation(vo.getIsrelation());
	         coContractchangerecordinfo.setExectype(vo.getExectype());
	         coContractchangerecordinfo.setFlow(vo.getFlow());
	         coContractchangerecordinfo.setSettleside(vo.getSettleside());
	         coContractchangerecordinfo.setCmd(vo.getCmd());
	         coContractchangerecordinfo.setOperator(vo.getOperator());
	         coContractchangerecordinfo.setOperatingdescription(vo.getOperatingdescription());
	         coContractchangerecordinfo.setOperatedate(vo.getOperatedate());
	         coContractchangerecordinfo.setOrderno(vo.getOrderno());
	         coContractchangerecordinfo.setSuperexecid(vo.getSuperexecid());
	         coContractchangerecordinfo.setContractenergy(vo.getContractenergy());
	         coContractchangerecordinfo.setContractprice(vo.getContractprice());
	         coContractchangerecordinfo.setEnergy(vo.getEnergy());
	         coContractchangerecordinfo.setSequenceid(vo.getSequenceid());
	         coContractchangerecordinfo.setBusinessid(vo.getBusinessid());
	         coContractchangerecordinfo.setFlowflag(vo.getFlowflag());
	         coContractchangerecordinfo.setCoVersion(vo.getCoVersion());
	         coContractchangerecordinfo.setIsTax(vo.getIsTax());
	         coContractchangerecordinfo.setSellerenergy(vo.getSellerenergy());
	         coContractchangerecordinfo.setIngodown(vo.getIngodown());
	         coContractchangerecordinfo.setExpend1(vo.getExpend1());
	         coContractchangerecordinfo.setExpend2(vo.getExpend2());
	         coContractchangerecordinfo.setExpend3(vo.getExpend3());
	         coContractchangerecordinfo.setExpend4(vo.getExpend4());
	         coContractchangerecordinfo.setExpend5(vo.getExpend5());
	         coContractchangerecordinfo.setExpend6(vo.getExpend6());
	         coContractchangerecordinfo.setPurchasergen(vo.getPurchasergen());
	         coContractchangerecordinfo.setPurchaserenergy(vo.getPurchaserenergy());
	         coContractchangerecordinfo.setSellergen(vo.getSellergen());
	         coContractchangerecordinfo.setVendeeGenRate(vo.getVendeeGenRate());
	         coContractchangerecordinfo.setSaleGenRate(vo.getSaleGenRate());
	         coContractchangerecordinfo.setSaleLoss(vo.getSaleLoss());
	         coContractchangerecordinfo.setVendeeLoss(vo.getVendeeLoss());
	         coContractchangerecordinfo.setVendeeBreathPrice(vo.getVendeeBreathPrice());
	         coContractchangerecordinfo.setSaleBreathPrice(vo.getSaleBreathPrice());
	         coContractchangerecordinfo.setApprovedTariff(vo.getApprovedTariff());
	         coContractchangerecordinfo.setCatalogPrice(vo.getCatalogPrice());
	         coContractchangerecordinfo.setTransferAllotPrice(vo.getTransferAllotPrice());
	         coContractchangerecordinfo.setNetDiscountLoss(vo.getNetDiscountLoss());
	         coContractchangerecordinfo.setFundsandaddPrice(vo.getFundsandaddPrice());
	         coContractchangerecordinfo.setTradePriceMargin(vo.getTradePriceMargin());
	    }
		return coContractchangerecordinfo;
	}

	public static CoContractchangerecordinfoVO toVO(CoContractchangerecordinfo po) {
		CoContractchangerecordinfoVO coContractchangerecordinfolistVO = new CoContractchangerecordinfoVO();

	    coContractchangerecordinfolistVO.setMarketid(po.getMarketid());
	    coContractchangerecordinfolistVO.setContractid(po.getContractid());
	    coContractchangerecordinfolistVO.setSupercontractid(po.getSupercontractid());
	    coContractchangerecordinfolistVO.setContractname(po.getContractname());
	    coContractchangerecordinfolistVO.setContracttype(po.getContracttype());
	    coContractchangerecordinfolistVO.setPapercontractcode(po.getPapercontractcode());
	    coContractchangerecordinfolistVO.setPapercontractname(po.getPapercontractname());
	    coContractchangerecordinfolistVO.setContracttemplateid(po.getContracttemplateid());
	    coContractchangerecordinfolistVO.setSignstate(po.getSignstate());
	    coContractchangerecordinfolistVO.setSigneddate(po.getSigneddate());
	    coContractchangerecordinfolistVO.setNotsignedreason(po.getNotsignedreason());
	    coContractchangerecordinfolistVO.setSignedperson(po.getSignedperson());
	    coContractchangerecordinfolistVO.setSignedsite(po.getSignedsite());
	    coContractchangerecordinfolistVO.setBackupstate(po.getBackupstate());
	    coContractchangerecordinfolistVO.setBackupdate(po.getBackupdate());
	    coContractchangerecordinfolistVO.setBackupperson(po.getBackupperson());
	    coContractchangerecordinfolistVO.setBackuporg(po.getBackuporg());
	    coContractchangerecordinfolistVO.setDisbackupreason(po.getDisbackupreason());
	    coContractchangerecordinfolistVO.setIsend(po.getIsend());
	    coContractchangerecordinfolistVO.setPrepcontractflag(po.getPrepcontractflag());
	    coContractchangerecordinfolistVO.setIssecrecyflag(po.getIssecrecyflag());
	    coContractchangerecordinfolistVO.setContractcyc(po.getContractcyc());
	    coContractchangerecordinfolistVO.setPurchaser(po.getPurchaser());
	    coContractchangerecordinfolistVO.setSeller(po.getSeller());
	    coContractchangerecordinfolistVO.setSellerunit(po.getSellerunit());
	    coContractchangerecordinfolistVO.setPurchaseunit(po.getPurchaseunit());
	    coContractchangerecordinfolistVO.setContractqty(po.getContractqty());
	    coContractchangerecordinfolistVO.setQtytype(po.getQtytype());
	    coContractchangerecordinfolistVO.setContractstartdate(po.getContractstartdate());
	    coContractchangerecordinfolistVO.setContractenddate(po.getContractenddate());
	    coContractchangerecordinfolistVO.setTransactionid(po.getTransactionid());
	    coContractchangerecordinfolistVO.setTransactiontype(po.getTransactiontype());
	    coContractchangerecordinfolistVO.setRightsettlementside(po.getRightsettlementside());
	    coContractchangerecordinfolistVO.setIsrepurchaseflag(po.getIsrepurchaseflag());
	    coContractchangerecordinfolistVO.setReplacetype(po.getReplacetype());
	    coContractchangerecordinfolistVO.setAreatype(po.getAreatype());
	    coContractchangerecordinfolistVO.setIsehvflag(po.getIsehvflag());
	    coContractchangerecordinfolistVO.setIsresaleflag(po.getIsresaleflag());
	    coContractchangerecordinfolistVO.setIsopen(po.getIsopen());
	    coContractchangerecordinfolistVO.setPurchasegate(po.getPurchasegate());
	    coContractchangerecordinfolistVO.setSettlegate(po.getSettlegate());
	    coContractchangerecordinfolistVO.setLossundertaker(po.getLossundertaker());
	    coContractchangerecordinfolistVO.setPurchaserperson(po.getPurchaserperson());
	    coContractchangerecordinfolistVO.setPurchaserphone(po.getPurchaserphone());
	    coContractchangerecordinfolistVO.setPurchaserconftime(po.getPurchaserconftime());
	    coContractchangerecordinfolistVO.setSellerperson(po.getSellerperson());
	    coContractchangerecordinfolistVO.setSellerphone(po.getSellerphone());
	    coContractchangerecordinfolistVO.setSellerconftime(po.getSellerconftime());
	    coContractchangerecordinfolistVO.setTransperson(po.getTransperson());
	    coContractchangerecordinfolistVO.setTransphone(po.getTransphone());
	    coContractchangerecordinfolistVO.setTransconftime(po.getTransconftime());
	    coContractchangerecordinfolistVO.setChangetimes(po.getChangetimes());
	    coContractchangerecordinfolistVO.setDescription(po.getDescription());
	    coContractchangerecordinfolistVO.setIsdelete(po.getIsdelete());
	    coContractchangerecordinfolistVO.setUpdatetime(po.getUpdatetime());
	    coContractchangerecordinfolistVO.setUpdatepersonid(po.getUpdatepersonid());
	    coContractchangerecordinfolistVO.setVersionid(po.getVersionid());
	    coContractchangerecordinfolistVO.setVersion(po.getVersion());
	    coContractchangerecordinfolistVO.setVersiondesc(po.getVersiondesc());
	    coContractchangerecordinfolistVO.setIsrelation(po.getIsrelation());
	    coContractchangerecordinfolistVO.setExectype(po.getExectype());
	    coContractchangerecordinfolistVO.setFlow(po.getFlow());
	    coContractchangerecordinfolistVO.setSettleside(po.getSettleside());
	    coContractchangerecordinfolistVO.setCmd(po.getCmd());
	    coContractchangerecordinfolistVO.setOperator(po.getOperator());
	    coContractchangerecordinfolistVO.setOperatingdescription(po.getOperatingdescription());
	    coContractchangerecordinfolistVO.setOperatedate(po.getOperatedate());
	    coContractchangerecordinfolistVO.setOrderno(po.getOrderno());
	    coContractchangerecordinfolistVO.setSuperexecid(po.getSuperexecid());
	    coContractchangerecordinfolistVO.setContractenergy(po.getContractenergy());
	    coContractchangerecordinfolistVO.setContractprice(po.getContractprice());
	    coContractchangerecordinfolistVO.setEnergy(po.getEnergy());
	    coContractchangerecordinfolistVO.setSequenceid(po.getSequenceid());
	    coContractchangerecordinfolistVO.setBusinessid(po.getBusinessid());
	    coContractchangerecordinfolistVO.setFlowflag(po.getFlowflag());
	    coContractchangerecordinfolistVO.setCoVersion(po.getCoVersion());
	    coContractchangerecordinfolistVO.setIsTax(po.getIsTax());
	    coContractchangerecordinfolistVO.setSellerenergy(po.getSellerenergy());
	    coContractchangerecordinfolistVO.setIngodown(po.getIngodown());
	    coContractchangerecordinfolistVO.setExpend1(po.getExpend1());
	    coContractchangerecordinfolistVO.setExpend2(po.getExpend2());
	    coContractchangerecordinfolistVO.setExpend3(po.getExpend3());
	    coContractchangerecordinfolistVO.setExpend4(po.getExpend4());
	    coContractchangerecordinfolistVO.setExpend5(po.getExpend5());
	    coContractchangerecordinfolistVO.setExpend6(po.getExpend6());
	    coContractchangerecordinfolistVO.setPurchasergen(po.getPurchasergen());
	    coContractchangerecordinfolistVO.setPurchaserenergy(po.getPurchaserenergy());
	    coContractchangerecordinfolistVO.setSellergen(po.getSellergen());
	    coContractchangerecordinfolistVO.setVendeeGenRate(po.getVendeeGenRate());
	    coContractchangerecordinfolistVO.setSaleGenRate(po.getSaleGenRate());
	    coContractchangerecordinfolistVO.setSaleLoss(po.getSaleLoss());
	    coContractchangerecordinfolistVO.setVendeeLoss(po.getVendeeLoss());
	    coContractchangerecordinfolistVO.setVendeeBreathPrice(po.getVendeeBreathPrice());
	    coContractchangerecordinfolistVO.setSaleBreathPrice(po.getSaleBreathPrice());
	    coContractchangerecordinfolistVO.setApprovedTariff(po.getApprovedTariff());
	    coContractchangerecordinfolistVO.setCatalogPrice(po.getCatalogPrice());
	    coContractchangerecordinfolistVO.setTransferAllotPrice(po.getTransferAllotPrice());
	    coContractchangerecordinfolistVO.setNetDiscountLoss(po.getNetDiscountLoss());
	    coContractchangerecordinfolistVO.setFundsandaddPrice(po.getFundsandaddPrice());
	    coContractchangerecordinfolistVO.setTradePriceMargin(po.getTradePriceMargin());
		return coContractchangerecordinfolistVO;
	}
}
