package com.sgcc.dlsc.contractManage.contractmonthmanage.vo;

import com.sgcc.dlsc.entity.po.CoContractmonthqty;


public class CoContractmonthqtyTransfer {

	public static CoContractmonthqty toPO(CoContractmonthqtyVO vo) {
		CoContractmonthqty coContractmonthqty = new CoContractmonthqty();
		if(vo != null){
	         coContractmonthqty.setContractid(vo.getContractid());
	         coContractmonthqty.setJanqty(vo.getJanqty());
	         coContractmonthqty.setFebqty(vo.getFebqty());
	         coContractmonthqty.setMarqty(vo.getMarqty());
	         coContractmonthqty.setAprilqty(vo.getAprilqty());
	         coContractmonthqty.setMayqty(vo.getMayqty());
	         coContractmonthqty.setJunqty(vo.getJunqty());
	         coContractmonthqty.setJulyqty(vo.getJulyqty());
	         coContractmonthqty.setAugqty(vo.getAugqty());
	         coContractmonthqty.setSepqty(vo.getSepqty());
	         coContractmonthqty.setOctqty(vo.getOctqty());
	         coContractmonthqty.setNovqty(vo.getNovqty());
	         coContractmonthqty.setDecqty(vo.getDecqty());
	         coContractmonthqty.setAddpersonid(vo.getAddpersonid());
	         coContractmonthqty.setAdddate(vo.getAdddate());
	         coContractmonthqty.setUpdatepersonid(vo.getUpdatepersonid());
	         coContractmonthqty.setUpdatedate(vo.getUpdatedate());
	         coContractmonthqty.setMarketid(vo.getMarketid());
	         coContractmonthqty.setMonthqtytype(vo.getMonthqtytype());
	         coContractmonthqty.setGuid(vo.getGuid());
	    }
		return coContractmonthqty;
	}

	public static CoContractmonthqtyVO toVO(CoContractmonthqty po) {
		CoContractmonthqtyVO coContractmonthqtylistVO = new CoContractmonthqtyVO();

	    coContractmonthqtylistVO.setContractid(po.getContractid());
	    coContractmonthqtylistVO.setJanqty(po.getJanqty());
	    coContractmonthqtylistVO.setFebqty(po.getFebqty());
	    coContractmonthqtylistVO.setMarqty(po.getMarqty());
	    coContractmonthqtylistVO.setAprilqty(po.getAprilqty());
	    coContractmonthqtylistVO.setMayqty(po.getMayqty());
	    coContractmonthqtylistVO.setJunqty(po.getJunqty());
	    coContractmonthqtylistVO.setJulyqty(po.getJulyqty());
	    coContractmonthqtylistVO.setAugqty(po.getAugqty());
	    coContractmonthqtylistVO.setSepqty(po.getSepqty());
	    coContractmonthqtylistVO.setOctqty(po.getOctqty());
	    coContractmonthqtylistVO.setNovqty(po.getNovqty());
	    coContractmonthqtylistVO.setDecqty(po.getDecqty());
	    coContractmonthqtylistVO.setAddpersonid(po.getAddpersonid());
	    coContractmonthqtylistVO.setAdddate(po.getAdddate());
	    coContractmonthqtylistVO.setUpdatepersonid(po.getUpdatepersonid());
	    coContractmonthqtylistVO.setUpdatedate(po.getUpdatedate());
	    coContractmonthqtylistVO.setMarketid(po.getMarketid());
	    coContractmonthqtylistVO.setMonthqtytype(po.getMonthqtytype());
	    coContractmonthqtylistVO.setGuid(po.getGuid());
		return coContractmonthqtylistVO;
	}
}
