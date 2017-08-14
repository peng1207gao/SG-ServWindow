package com.sgcc.dlsc.statesanalysis.thermalpower.vo;

import com.sgcc.dlsc.entity.po.SeTielineResultN;

public class SeTielineResultNTransfer {

	public static SeTielineResultN toPO(SeTielineResultNVO vo) {
		SeTielineResultN seTielineResultN = new SeTielineResultN();
		if(vo != null){
	         seTielineResultN.setGuid(vo.getGuid());
	         seTielineResultN.setMarketId(vo.getMarketId());
	         seTielineResultN.setMktYear(vo.getMktYear());
	         seTielineResultN.setTielineId(vo.getTielineId());
	         seTielineResultN.setTielineIdN(vo.getTielineIdN());
	         seTielineResultN.setSaleParticipantid(vo.getSaleParticipantid());
	         seTielineResultN.setVendeeParticipantid(vo.getVendeeParticipantid());
	         seTielineResultN.setRecipientParticipantid(vo.getRecipientParticipantid());
	         seTielineResultN.setPayerParticipantid(vo.getPayerParticipantid());
	         seTielineResultN.setContractId(vo.getContractId());
	         seTielineResultN.setSbstypeId(vo.getSbstypeId());
	         seTielineResultN.setEnergyT(vo.getEnergyT());
	         seTielineResultN.setPriceT(vo.getPriceT());
	         seTielineResultN.setFeeT(vo.getFeeT());
	         seTielineResultN.setEnergyS(vo.getEnergyS());
	         seTielineResultN.setPriceS(vo.getPriceS());
	         seTielineResultN.setFeeS(vo.getFeeS());
	         seTielineResultN.setEnergyP(vo.getEnergyP());
	         seTielineResultN.setPriceP(vo.getPriceP());
	         seTielineResultN.setFeeP(vo.getFeeP());
	         seTielineResultN.setEnergyF(vo.getEnergyF());
	         seTielineResultN.setPriceF(vo.getPriceF());
	         seTielineResultN.setFeeF(vo.getFeeF());
	         seTielineResultN.setEnergyB(vo.getEnergyB());
	         seTielineResultN.setPriceB(vo.getPriceB());
	         seTielineResultN.setFeeB(vo.getFeeB());
	    }
		return seTielineResultN;
	}

	public static SeTielineResultNVO toVO(SeTielineResultN po) {
		SeTielineResultNVO seTielineResultNlistVO = new SeTielineResultNVO();

	    seTielineResultNlistVO.setGuid(po.getGuid());
	    seTielineResultNlistVO.setMarketId(po.getMarketId());
	    seTielineResultNlistVO.setMktYear(po.getMktYear());
	    seTielineResultNlistVO.setTielineId(po.getTielineId());
	    seTielineResultNlistVO.setTielineIdN(po.getTielineIdN());
	    seTielineResultNlistVO.setSaleParticipantid(po.getSaleParticipantid());
	    seTielineResultNlistVO.setVendeeParticipantid(po.getVendeeParticipantid());
	    seTielineResultNlistVO.setRecipientParticipantid(po.getRecipientParticipantid());
	    seTielineResultNlistVO.setPayerParticipantid(po.getPayerParticipantid());
	    seTielineResultNlistVO.setContractId(po.getContractId());
	    seTielineResultNlistVO.setSbstypeId(po.getSbstypeId());
	    seTielineResultNlistVO.setEnergyT(po.getEnergyT());
	    seTielineResultNlistVO.setPriceT(po.getPriceT());
	    seTielineResultNlistVO.setFeeT(po.getFeeT());
	    seTielineResultNlistVO.setEnergyS(po.getEnergyS());
	    seTielineResultNlistVO.setPriceS(po.getPriceS());
	    seTielineResultNlistVO.setFeeS(po.getFeeS());
	    seTielineResultNlistVO.setEnergyP(po.getEnergyP());
	    seTielineResultNlistVO.setPriceP(po.getPriceP());
	    seTielineResultNlistVO.setFeeP(po.getFeeP());
	    seTielineResultNlistVO.setEnergyF(po.getEnergyF());
	    seTielineResultNlistVO.setPriceF(po.getPriceF());
	    seTielineResultNlistVO.setFeeF(po.getFeeF());
	    seTielineResultNlistVO.setEnergyB(po.getEnergyB());
	    seTielineResultNlistVO.setPriceB(po.getPriceB());
	    seTielineResultNlistVO.setFeeB(po.getFeeB());
		return seTielineResultNlistVO;
	}
}
