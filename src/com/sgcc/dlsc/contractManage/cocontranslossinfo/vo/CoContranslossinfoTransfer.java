package com.sgcc.dlsc.contractManage.cocontranslossinfo.vo;

import com.sgcc.dlsc.entity.po.CoContranslossinfo;


public class CoContranslossinfoTransfer {

	public static CoContranslossinfo toPO(CoContranslossinfoVO vo) {
		CoContranslossinfo coContranslossinfo = new CoContranslossinfo();
		if(vo != null){
	         coContranslossinfo.setContractid(vo.getContractid());
	         coContranslossinfo.setTranslineno(vo.getTranslineno());
	         coContranslossinfo.setLinestartendgate(vo.getLinestartendgate());
	         coContranslossinfo.setLoss(vo.getLoss());
	         coContranslossinfo.setLinkid(vo.getLinkid());
	         coContranslossinfo.setDirection(vo.getDirection());
	         coContranslossinfo.setGuid(vo.getGuid());
	         coContranslossinfo.setTransmission(vo.getTransmission());
	    }
		return coContranslossinfo;
	}

	public static CoContranslossinfoVO toVO(CoContranslossinfo po) {
		CoContranslossinfoVO coContranslossinfolistVO = new CoContranslossinfoVO();

	    coContranslossinfolistVO.setContractid(po.getContractid());
	    coContranslossinfolistVO.setTranslineno(po.getTranslineno());
	    coContranslossinfolistVO.setLinestartendgate(po.getLinestartendgate());
	    coContranslossinfolistVO.setLoss(po.getLoss());
	    coContranslossinfolistVO.setLinkid(po.getLinkid());
	    coContranslossinfolistVO.setDirection(po.getDirection());
	    coContranslossinfolistVO.setGuid(po.getGuid());
	    coContranslossinfolistVO.setTransmission(po.getTransmission());
		return coContranslossinfolistVO;
	}
}
