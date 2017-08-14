package com.sgcc.dlsc.contractManage.cocontractaccessory.vo;

import com.sgcc.dlsc.entity.po.CoContractaccessory;


public class CoContractaccessoryTransfer {

	public static CoContractaccessory toPO(CoContractaccessoryVO vo) {
		CoContractaccessory coContractaccessory = new CoContractaccessory();
		if(vo != null){
	         coContractaccessory.setGuid(vo.getGuid());
	         coContractaccessory.setContractid(vo.getContractid());
	         coContractaccessory.setContractrole(vo.getContractrole());
	         coContractaccessory.setParticipantid(vo.getParticipantid());
	         coContractaccessory.setUnitid(vo.getUnitid());
	    }
		return coContractaccessory;
	}

	public static CoContractaccessoryVO toVO(CoContractaccessory po) {
		CoContractaccessoryVO coContractaccessorylistVO = new CoContractaccessoryVO();

	    coContractaccessorylistVO.setGuid(po.getGuid());
	    coContractaccessorylistVO.setContractid(po.getContractid());
	    coContractaccessorylistVO.setContractrole(po.getContractrole());
	    coContractaccessorylistVO.setParticipantid(po.getParticipantid());
	    coContractaccessorylistVO.setUnitid(po.getUnitid());
		return coContractaccessorylistVO;
	}
}
