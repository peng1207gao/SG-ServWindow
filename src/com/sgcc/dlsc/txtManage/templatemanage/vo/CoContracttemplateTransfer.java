package com.sgcc.dlsc.txtManage.templatemanage.vo;

import com.sgcc.dlsc.entity.po.CoContracttemplate;

public class CoContracttemplateTransfer {

	public static CoContracttemplate toPO(CoContracttemplateVO vo) {
		CoContracttemplate coContracttemplate = new CoContracttemplate();
		if(vo != null){
	         coContracttemplate.setMarketid(vo.getMarketid());
	         coContracttemplate.setContracttype(vo.getContracttype());
	         coContracttemplate.setContracttemplateid(vo.getContracttemplateid());
	         coContracttemplate.setContracttemplatecode(vo.getContracttemplatecode());
	         coContracttemplate.setContracttemplatename(vo.getContracttemplatename());
	         coContracttemplate.setVersion(vo.getVersion());
	         coContracttemplate.setIssueddate(vo.getIssueddate());
	         coContracttemplate.setStarteffectivedate(vo.getStarteffectivedate());
	         coContracttemplate.setEndeffectivedate(vo.getEndeffectivedate());
	         coContracttemplate.setEffectiveflag(vo.getEffectiveflag());
	         coContracttemplate.setIsdelete(vo.getIsdelete());
	         coContracttemplate.setUpdatetime(vo.getUpdatetime());
	         coContracttemplate.setUpdatepersonid(vo.getUpdatepersonid());
	    }
		return coContracttemplate;
	}

	public static CoContracttemplateVO toVO(CoContracttemplate po) {
		CoContracttemplateVO coContracttemplatelistVO = new CoContracttemplateVO();

	    coContracttemplatelistVO.setMarketid(po.getMarketid());
	    coContracttemplatelistVO.setContracttype(po.getContracttype());
	    coContracttemplatelistVO.setContracttemplateid(po.getContracttemplateid());
	    coContracttemplatelistVO.setContracttemplatecode(po.getContracttemplatecode());
	    coContracttemplatelistVO.setContracttemplatename(po.getContracttemplatename());
	    coContracttemplatelistVO.setVersion(po.getVersion());
	    coContracttemplatelistVO.setIssueddate(po.getIssueddate());
	    coContracttemplatelistVO.setStarteffectivedate(po.getStarteffectivedate());
	    coContracttemplatelistVO.setEndeffectivedate(po.getEndeffectivedate());
	    coContracttemplatelistVO.setEffectiveflag(po.getEffectiveflag());
	    coContracttemplatelistVO.setIsdelete(po.getIsdelete());
	    coContracttemplatelistVO.setUpdatetime(po.getUpdatetime());
	    coContracttemplatelistVO.setUpdatepersonid(po.getUpdatepersonid());
		return coContracttemplatelistVO;
	}
}
