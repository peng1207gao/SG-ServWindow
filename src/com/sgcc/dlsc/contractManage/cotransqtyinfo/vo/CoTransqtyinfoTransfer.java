package com.sgcc.dlsc.contractManage.cotransqtyinfo.vo;

import com.sgcc.dlsc.entity.po.CoTransqtyinfo;

public class CoTransqtyinfoTransfer {

	public static CoTransqtyinfo toPO(CoTransqtyinfoVO vo) {
		CoTransqtyinfo coTransqtyinfo = new CoTransqtyinfo();
		if(vo != null){
	         coTransqtyinfo.setTransinfoid(vo.getTransinfoid());
	         coTransqtyinfo.setContractid(vo.getContractid());
	         coTransqtyinfo.setTransmission(vo.getTransmission());
	         coTransqtyinfo.setLinkid(vo.getLinkid());
	         coTransqtyinfo.setLinkno(vo.getLinkno());
	         coTransqtyinfo.setPassagewayno(vo.getPassagewayno());
	         coTransqtyinfo.setPassagewayname(vo.getPassagewayname());
	         coTransqtyinfo.setStartgateid(vo.getStartgateid());
	         coTransqtyinfo.setEndgateid(vo.getEndgateid());
	         coTransqtyinfo.setStartprice(vo.getStartprice());
	         coTransqtyinfo.setEndprice(vo.getEndprice());
	         coTransqtyinfo.setTransprice(vo.getTransprice());
	         coTransqtyinfo.setLossrate(vo.getLossrate());
	         coTransqtyinfo.setTransqty(vo.getTransqty());
	         coTransqtyinfo.setCapfee(vo.getCapfee());
	         coTransqtyinfo.setSeprate(vo.getSeprate());
	         coTransqtyinfo.setPeakregurate(vo.getPeakregurate());
	         coTransqtyinfo.setIsincludetax(vo.getIsincludetax());
	         coTransqtyinfo.setPower(vo.getPower());
	         coTransqtyinfo.setExplanation(vo.getExplanation());
	         coTransqtyinfo.setSpare1(vo.getSpare1());
	         coTransqtyinfo.setSpare2(vo.getSpare2());
	         coTransqtyinfo.setSpare3(vo.getSpare3());
	         coTransqtyinfo.setStarttime(vo.getStarttime());
	         coTransqtyinfo.setEndtime(vo.getEndtime());
	         coTransqtyinfo.setTranscap(vo.getTranscap());
	         coTransqtyinfo.setCapprice(vo.getCapprice());
	    }
		return coTransqtyinfo;
	}

	public static CoTransqtyinfoVO toVO(CoTransqtyinfo po) {
		CoTransqtyinfoVO coTransqtyinfolistVO = new CoTransqtyinfoVO();

	    coTransqtyinfolistVO.setTransinfoid(po.getTransinfoid());
	    coTransqtyinfolistVO.setContractid(po.getContractid());
	    coTransqtyinfolistVO.setTransmission(po.getTransmission());
	    coTransqtyinfolistVO.setLinkid(po.getLinkid());
	    coTransqtyinfolistVO.setLinkno(po.getLinkno());
	    coTransqtyinfolistVO.setPassagewayno(po.getPassagewayno());
	    coTransqtyinfolistVO.setPassagewayname(po.getPassagewayname());
	    coTransqtyinfolistVO.setStartgateid(po.getStartgateid());
	    coTransqtyinfolistVO.setEndgateid(po.getEndgateid());
	    coTransqtyinfolistVO.setStartprice(po.getStartprice());
	    coTransqtyinfolistVO.setEndprice(po.getEndprice());
	    coTransqtyinfolistVO.setTransprice(po.getTransprice());
	    coTransqtyinfolistVO.setLossrate(po.getLossrate());
	    coTransqtyinfolistVO.setTransqty(po.getTransqty());
	    coTransqtyinfolistVO.setCapfee(po.getCapfee());
	    coTransqtyinfolistVO.setSeprate(po.getSeprate());
	    coTransqtyinfolistVO.setPeakregurate(po.getPeakregurate());
	    coTransqtyinfolistVO.setIsincludetax(po.getIsincludetax());
	    coTransqtyinfolistVO.setPower(po.getPower());
	    coTransqtyinfolistVO.setExplanation(po.getExplanation());
	    coTransqtyinfolistVO.setSpare1(po.getSpare1());
	    coTransqtyinfolistVO.setSpare2(po.getSpare2());
	    coTransqtyinfolistVO.setSpare3(po.getSpare3());
	    coTransqtyinfolistVO.setStarttime(po.getStarttime());
	    coTransqtyinfolistVO.setEndtime(po.getEndtime());
	    coTransqtyinfolistVO.setTranscap(po.getTranscap());
	    coTransqtyinfolistVO.setCapprice(po.getCapprice());
		return coTransqtyinfolistVO;
	}
}
