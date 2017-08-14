package com.sgcc.dlsc.paramConfig.cocontracsequence.vo;

import com.sgcc.dlsc.entity.po.CoTypesequencerelation;


public class CoTypesequencerelationTransfer {

	public static CoTypesequencerelation toPO(CoTypesequencerelationVO vo) {
		CoTypesequencerelation coTypesequencerelation = new CoTypesequencerelation();
		if(vo != null){
	         coTypesequencerelation.setGuid(vo.getGuid());
	         coTypesequencerelation.setContracttypeid(vo.getContracttypeid());
	         coTypesequencerelation.setSequenceid(vo.getSequenceid());
	    }
		return coTypesequencerelation;
	}

	public static CoTypesequencerelationVO toVO(CoTypesequencerelation po) {
		CoTypesequencerelationVO coTypesequencerelationlistVO = new CoTypesequencerelationVO();

	    coTypesequencerelationlistVO.setGuid(po.getGuid());
	    coTypesequencerelationlistVO.setContracttypeid(po.getContracttypeid());
	    coTypesequencerelationlistVO.setSequenceid(po.getSequenceid());
		return coTypesequencerelationlistVO;
	}
}
