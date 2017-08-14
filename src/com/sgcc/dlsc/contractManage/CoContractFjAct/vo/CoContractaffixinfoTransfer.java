package com.sgcc.dlsc.contractManage.CoContractFjAct.vo;

import com.sgcc.dlsc.entity.po.CoContractaffixinfo;

public class CoContractaffixinfoTransfer {

	public static CoContractaffixinfo toPO(CoContractaffixinfoVO vo) {
		CoContractaffixinfo coContractaffixinfo = new CoContractaffixinfo();
		if(vo != null){
	         coContractaffixinfo.setMarketid(vo.getMarketid());
	         coContractaffixinfo.setContractid(vo.getContractid());
	         coContractaffixinfo.setAffixno(vo.getAffixno());
	         coContractaffixinfo.setAffixname(vo.getAffixname());
	         coContractaffixinfo.setPapercontractfile(vo.getPapercontractfile());
	         coContractaffixinfo.setUploadperson(vo.getUploadperson());
	         coContractaffixinfo.setUploadtime(vo.getUploadtime());
	         coContractaffixinfo.setDescription(vo.getDescription());
	         coContractaffixinfo.setGuid(vo.getGuid());
	         coContractaffixinfo.setAffixtype(vo.getAffixtype());
	    }
		return coContractaffixinfo;
	}

	public static CoContractaffixinfoVO toVO(CoContractaffixinfo po) {
		CoContractaffixinfoVO coContractaffixinfolistVO = new CoContractaffixinfoVO();

	    coContractaffixinfolistVO.setMarketid(po.getMarketid());
	    coContractaffixinfolistVO.setContractid(po.getContractid());
	    coContractaffixinfolistVO.setAffixno(po.getAffixno());
	    coContractaffixinfolistVO.setAffixname(po.getAffixname());
	    coContractaffixinfolistVO.setPapercontractfile(po.getPapercontractfile());
	    coContractaffixinfolistVO.setUploadperson(po.getUploadperson());
	    coContractaffixinfolistVO.setUploadtime(po.getUploadtime());
	    coContractaffixinfolistVO.setDescription(po.getDescription());
	    coContractaffixinfolistVO.setGuid(po.getGuid());
	    coContractaffixinfolistVO.setAffixtype(po.getAffixtype());
		return coContractaffixinfolistVO;
	}
}
