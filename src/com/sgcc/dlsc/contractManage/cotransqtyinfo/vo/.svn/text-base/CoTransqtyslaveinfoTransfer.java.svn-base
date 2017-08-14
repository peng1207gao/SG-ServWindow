package com.sgcc.dlsc.contractManage.cotransqtyinfo.vo;

import java.math.BigDecimal;

import com.sgcc.dlsc.entity.po.CoTransqtyslaveinfo;

public class CoTransqtyslaveinfoTransfer {

	public static CoTransqtyslaveinfo toPO(CoTransqtyslaveinfoVO vo) {
		CoTransqtyslaveinfo coTransqtyslaveinfo = new CoTransqtyslaveinfo();
		if(vo != null){
	         coTransqtyslaveinfo.setGuid(vo.getGuid());
	         coTransqtyslaveinfo.setTransinfoid(vo.getTransinfoid());
	         coTransqtyslaveinfo.setStarttime(vo.getStarttime());
	         coTransqtyslaveinfo.setEndtime(vo.getEndtime());
	         coTransqtyslaveinfo.setPower(vo.getPower());
	         coTransqtyslaveinfo.setQtytype(new BigDecimal(vo.getQtytype()));
	         coTransqtyslaveinfo.setExplanation(vo.getExplanation());
	         coTransqtyslaveinfo.setBight(vo.getBight());
	         coTransqtyslaveinfo.setStartdate(vo.getStartdate());
	         coTransqtyslaveinfo.setEnddate(vo.getEnddate());
	    }
		return coTransqtyslaveinfo;
	}

	public static CoTransqtyslaveinfoVO toVO(CoTransqtyslaveinfo po) {
		CoTransqtyslaveinfoVO coTransqtyslaveinfolistVO = new CoTransqtyslaveinfoVO();

	    coTransqtyslaveinfolistVO.setGuid(po.getGuid());
	    coTransqtyslaveinfolistVO.setTransinfoid(po.getTransinfoid());
	    coTransqtyslaveinfolistVO.setStarttime(po.getStarttime());
	    coTransqtyslaveinfolistVO.setEndtime(po.getEndtime());
	    coTransqtyslaveinfolistVO.setPower(po.getPower());
	    coTransqtyslaveinfolistVO.setQtytype(po.getQtytype().toString());
	    coTransqtyslaveinfolistVO.setExplanation(po.getExplanation());
	    coTransqtyslaveinfolistVO.setBight(po.getBight());
	    coTransqtyslaveinfolistVO.setStartdate(po.getStartdate());
	    coTransqtyslaveinfolistVO.setEnddate(po.getEnddate());
		return coTransqtyslaveinfolistVO;
	}
}
