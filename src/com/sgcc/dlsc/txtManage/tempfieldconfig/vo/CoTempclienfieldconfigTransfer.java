package com.sgcc.dlsc.txtManage.tempfieldconfig.vo;

import com.sgcc.dlsc.entity.po.CoTempclienfieldconfig;

public class CoTempclienfieldconfigTransfer {

	public static CoTempclienfieldconfig toPO(CoTempclienfieldconfigVO vo) {
		CoTempclienfieldconfig coTempclienfieldconfig = new CoTempclienfieldconfig();
		if(vo != null){
	         coTempclienfieldconfig.setSheetid(vo.getSheetid());
	         coTempclienfieldconfig.setCanSheetid(vo.getCanSheetid());
	         coTempclienfieldconfig.setConfigFieldName(vo.getConfigFieldName());
	         coTempclienfieldconfig.setPersonid(vo.getPersonid());
	         coTempclienfieldconfig.setMarketid(vo.getMarketid());
	         coTempclienfieldconfig.setUpdatedate(vo.getUpdatedate());
	         coTempclienfieldconfig.setContracttemplatecode(vo.getContracttemplatecode());
	    }
		return coTempclienfieldconfig;
	}

	public static CoTempclienfieldconfigVO toVO(CoTempclienfieldconfig po) {
		CoTempclienfieldconfigVO coTempclienfieldconfiglistVO = new CoTempclienfieldconfigVO();

	    coTempclienfieldconfiglistVO.setSheetid(po.getSheetid());
	    coTempclienfieldconfiglistVO.setCanSheetid(po.getCanSheetid());
	    coTempclienfieldconfiglistVO.setConfigFieldName(po.getConfigFieldName());
	    coTempclienfieldconfiglistVO.setPersonid(po.getPersonid());
	    coTempclienfieldconfiglistVO.setMarketid(po.getMarketid());
	    coTempclienfieldconfiglistVO.setUpdatedate(po.getUpdatedate());
	    coTempclienfieldconfiglistVO.setContracttemplatecode(po.getContracttemplatecode());
		return coTempclienfieldconfiglistVO;
	}
}
