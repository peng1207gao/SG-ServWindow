package com.sgcc.dlsc.paramConfig.contractTypeInfo.vo;

import com.sgcc.dlsc.entity.po.CoContracttypeinfo;

public class CoContracttypeinfoTransfer {

	public static CoContracttypeinfo toPO(CoContracttypeinfoVO vo) {
		CoContracttypeinfo coContracttypeinfo = new CoContracttypeinfo();
		if(vo != null){
	         coContracttypeinfo.setContracttypeid(vo.getContracttypeid());
	         coContracttypeinfo.setSupertypeid(vo.getSupertypeid());
	         coContracttypeinfo.setTypename(vo.getTypename());
	         coContracttypeinfo.setDescription(vo.getDescription());
	         coContracttypeinfo.setIsdefine(vo.getIsdefine());
	         coContracttypeinfo.setMarketid(vo.getMarketid());
	         coContracttypeinfo.setStarteffectivedate(vo.getStarteffectivedate());
	         coContracttypeinfo.setEndeffectivedate(vo.getEndeffectivedate());
	         coContracttypeinfo.setEffectiveflag(vo.getEffectiveflag());
	         coContracttypeinfo.setUpdatetime(vo.getUpdatetime());
	         coContracttypeinfo.setUpdatepersonid(vo.getUpdatepersonid());
	    }
		return coContracttypeinfo;
	}

	public static CoContracttypeinfoVO toVO(CoContracttypeinfo po) {
		CoContracttypeinfoVO coContracttypeinfolistVO = new CoContracttypeinfoVO();

	    coContracttypeinfolistVO.setContracttypeid(po.getContracttypeid());
	    coContracttypeinfolistVO.setSupertypeid(po.getSupertypeid());
	    coContracttypeinfolistVO.setTypename(po.getTypename());
	    coContracttypeinfolistVO.setDescription(po.getDescription());
	    coContracttypeinfolistVO.setIsdefine(po.getIsdefine());
	    coContracttypeinfolistVO.setMarketid(po.getMarketid());
	    coContracttypeinfolistVO.setStarteffectivedate(po.getStarteffectivedate());
	    coContracttypeinfolistVO.setEndeffectivedate(po.getEndeffectivedate());
	    coContracttypeinfolistVO.setEffectiveflag(po.getEffectiveflag());
	    coContracttypeinfolistVO.setUpdatetime(po.getUpdatetime());
	    coContracttypeinfolistVO.setUpdatepersonid(po.getUpdatepersonid());
		return coContracttypeinfolistVO;
	}
}
