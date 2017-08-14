package com.sgcc.dlsc.statesanalysis.jfcontractstate.vo;

import com.sgcc.dlsc.entity.po.SjjcJfHtlzxx;

public class SjjcJfHtlzxxTransfer {

	public static SjjcJfHtlzxx toPO(SjjcJfHtlzxxVO vo) {
		SjjcJfHtlzxx sjjcJfHtlzxx = new SjjcJfHtlzxx();
		if(vo != null){
	         sjjcJfHtlzxx.setObjId(vo.getObjId());
	         sjjcJfHtlzxx.setCompanyname(vo.getCompanyname());
	         sjjcJfHtlzxx.setCompanyid(vo.getCompanyid());
	         sjjcJfHtlzxx.setContractname(vo.getContractname());
	         sjjcJfHtlzxx.setContractid(vo.getContractid());
	         sjjcJfHtlzxx.setContractno(vo.getContractno());
	         sjjcJfHtlzxx.setIntodate(vo.getIntodate());
	         sjjcJfHtlzxx.setContractstate(vo.getContractstate());
	         sjjcJfHtlzxx.setContractsuggestion(vo.getContractsuggestion());
	    }
		return sjjcJfHtlzxx;
	}

	public static SjjcJfHtlzxxVO toVO(SjjcJfHtlzxx po) {
		SjjcJfHtlzxxVO sjjcJfHtlzxxlistVO = new SjjcJfHtlzxxVO();

	    sjjcJfHtlzxxlistVO.setObjId(po.getObjId());
	    sjjcJfHtlzxxlistVO.setCompanyname(po.getCompanyname());
	    sjjcJfHtlzxxlistVO.setCompanyid(po.getCompanyid());
	    sjjcJfHtlzxxlistVO.setContractname(po.getContractname());
	    sjjcJfHtlzxxlistVO.setContractid(po.getContractid());
	    sjjcJfHtlzxxlistVO.setContractno(po.getContractno());
	    sjjcJfHtlzxxlistVO.setIntodate(po.getIntodate());
	    sjjcJfHtlzxxlistVO.setContractstate(po.getContractstate());
	    sjjcJfHtlzxxlistVO.setContractsuggestion(po.getContractsuggestion());
		return sjjcJfHtlzxxlistVO;
	}
}
