package com.sgcc.dlsc.txtManage.templatemanage.vo;

import com.sgcc.dlsc.entity.po.CoContemplatewithparam;

public class CoContemplatewithparamTransfer {

	public static CoContemplatewithparam toPO(CoContemplatewithparamVO vo) {
		CoContemplatewithparam coContemplatewithparam = new CoContemplatewithparam();
		if(vo != null){
	         coContemplatewithparam.setMarketid(vo.getMarketid());
	         coContemplatewithparam.setContracttype(vo.getContracttype());
	         coContemplatewithparam.setContracttemplatecode(vo.getContracttemplatecode());
	         coContemplatewithparam.setContracttemplatename(vo.getContracttemplatename());
	         coContemplatewithparam.setContracttemplatefile(vo.getContracttemplatefile().getBytes());
	         coContemplatewithparam.setVersion(vo.getVersion());
	         coContemplatewithparam.setIssueddate(vo.getIssueddate());
	         coContemplatewithparam.setStarteffectivedate(vo.getStarteffectivedate());
	         coContemplatewithparam.setEndeffectivedate(vo.getEndeffectivedate());
	         coContemplatewithparam.setEffectiveflag(vo.getEffectiveflag());
	         coContemplatewithparam.setIsshare(vo.getIsshare());
	         coContemplatewithparam.setDescreption(vo.getDescreption());
	    }
		return coContemplatewithparam;
	}

	public static CoContemplatewithparamVO toVO(CoContemplatewithparam po) {
		CoContemplatewithparamVO coContemplatewithparamlistVO = new CoContemplatewithparamVO();

	    coContemplatewithparamlistVO.setMarketid(po.getMarketid());
	    coContemplatewithparamlistVO.setContracttype(po.getContracttype());
	    coContemplatewithparamlistVO.setContracttemplatecode(po.getContracttemplatecode());
	    coContemplatewithparamlistVO.setContracttemplatename(po.getContracttemplatename());
	    coContemplatewithparamlistVO.setContracttemplatefile(po.getContracttemplatefile().toString());
	    coContemplatewithparamlistVO.setVersion(po.getVersion());
	    coContemplatewithparamlistVO.setIssueddate(po.getIssueddate());
	    coContemplatewithparamlistVO.setStarteffectivedate(po.getStarteffectivedate());
	    coContemplatewithparamlistVO.setEndeffectivedate(po.getEndeffectivedate());
	    coContemplatewithparamlistVO.setEffectiveflag(po.getEffectiveflag());
	    coContemplatewithparamlistVO.setIsshare(po.getIsshare());
	    coContemplatewithparamlistVO.setDescreption(po.getDescreption());
		return coContemplatewithparamlistVO;
	}
}
