package com.sgcc.dlsc.contractManage.CoContransEnergyInfo.vo;


import com.sgcc.dlsc.entity.po.CoContransenergyinfo;

public class CoContransenergyinfoTransfer {

	public static CoContransenergyinfo toPO(CoContransenergyinfoVO vo) {
		CoContransenergyinfo coContransenergyinfo = new CoContransenergyinfo();
		if(vo != null){
	         coContransenergyinfo.setContractid(vo.getContractid());
	         coContransenergyinfo.setTransno(vo.getTransno());
	         coContransenergyinfo.setTransmission(vo.getTransmission());
	         coContransenergyinfo.setTransperson(vo.getTransperson());
	         coContransenergyinfo.setTransphone(vo.getTransphone());
	         coContransenergyinfo.setTransconftime(vo.getTransconftime());
	         coContransenergyinfo.setTransenergy(vo.getTransenergy());
	         coContransenergyinfo.setCost(vo.getCost());
	         coContransenergyinfo.setLossattribute(vo.getLossattribute());
	         coContransenergyinfo.setPrice(vo.getPrice());
	         coContransenergyinfo.setSettleside(vo.getSettleside());
	         coContransenergyinfo.setSettletype(vo.getSettletype());
	         coContransenergyinfo.setIsincludetax(vo.getIsincludetax());
	         coContransenergyinfo.setTaxrate(vo.getTaxrate());
	         coContransenergyinfo.setLoss(vo.getLoss());
	         coContransenergyinfo.setEnergypercent(vo.getEnergypercent());
	         coContransenergyinfo.setStartdate(vo.getStartdate());
	         coContransenergyinfo.setEnddate(vo.getEnddate());
	         coContransenergyinfo.setGuid(vo.getGuid());
	    }
		return coContransenergyinfo;
	}

	public static CoContransenergyinfoVO toVO(CoContransenergyinfo po) {
		CoContransenergyinfoVO coContransenergyinfolistVO = new CoContransenergyinfoVO();

	    coContransenergyinfolistVO.setContractid(po.getContractid());
	    coContransenergyinfolistVO.setTransno(po.getTransno());
	    coContransenergyinfolistVO.setTransmission(po.getTransmission());
	    coContransenergyinfolistVO.setTransperson(po.getTransperson());
	    coContransenergyinfolistVO.setTransphone(po.getTransphone());
	    coContransenergyinfolistVO.setTransconftime(po.getTransconftime());
	    coContransenergyinfolistVO.setTransenergy(po.getTransenergy());
	    coContransenergyinfolistVO.setCost(po.getCost());
	    coContransenergyinfolistVO.setLossattribute(po.getLossattribute());
	    coContransenergyinfolistVO.setPrice(po.getPrice());
	    coContransenergyinfolistVO.setSettleside(po.getSettleside());
	    coContransenergyinfolistVO.setSettletype(po.getSettletype());
	    coContransenergyinfolistVO.setIsincludetax(po.getIsincludetax());
	    coContransenergyinfolistVO.setTaxrate(po.getTaxrate());
	    coContransenergyinfolistVO.setLoss(po.getLoss());
	    coContransenergyinfolistVO.setEnergypercent(po.getEnergypercent());
	    coContransenergyinfolistVO.setStartdate(po.getStartdate());
	    coContransenergyinfolistVO.setEnddate(po.getEnddate());
	    coContransenergyinfolistVO.setGuid(po.getGuid());
		return coContransenergyinfolistVO;
	}
}
