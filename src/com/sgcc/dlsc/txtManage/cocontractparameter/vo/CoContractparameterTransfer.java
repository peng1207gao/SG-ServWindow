package com.sgcc.dlsc.txtManage.cocontractparameter.vo;

import com.sgcc.dlsc.entity.po.CoContractparameter;

public class CoContractparameterTransfer {

	public static CoContractparameter toPO(CoContractparameterVO vo) {
		CoContractparameter coContractparameter = new CoContractparameter();
		if(vo != null){
	         coContractparameter.setMarketid(vo.getMarketid());
	         coContractparameter.setTempparamid(vo.getTempparamid());
	         coContractparameter.setTempparamname(vo.getTempparamname());
	         coContractparameter.setTempparamex(vo.getTempparamex());
	         coContractparameter.setTempparamsql(vo.getTempparamsql());
	         coContractparameter.setTempparamdesc(vo.getTempparamdesc());
	         coContractparameter.setContracttype(vo.getContracttype());
	         coContractparameter.setStarteffectivedate(vo.getStarteffectivedate());
	         coContractparameter.setEndeffectivedate(vo.getEndeffectivedate());
	         coContractparameter.setIsshare(vo.getIsshare());
	         coContractparameter.setCreatedate(vo.getCreatedate());
	         coContractparameter.setCreator(vo.getCreator());
	         coContractparameter.setIsdelete(vo.getIsdelete());
	         coContractparameter.setUpdatetime(vo.getUpdatetime());
	         coContractparameter.setUpdatepersonid(vo.getUpdatepersonid());
	         coContractparameter.setContracttemplatecode(vo.getContracttemplatecode());
	         coContractparameter.setDicId(vo.getDicId());
	         coContractparameter.setBookmark(vo.getBookmark());
	    }
		return coContractparameter;
	}

	public static CoContractparameterVO toVO(CoContractparameter po) {
		CoContractparameterVO coContractparameterlistVO = new CoContractparameterVO();

	    coContractparameterlistVO.setMarketid(po.getMarketid());
	    coContractparameterlistVO.setTempparamid(po.getTempparamid());
	    coContractparameterlistVO.setTempparamname(po.getTempparamname());
	    coContractparameterlistVO.setTempparamex(po.getTempparamex());
	    coContractparameterlistVO.setTempparamsql(po.getTempparamsql());
	    coContractparameterlistVO.setTempparamdesc(po.getTempparamdesc());
	    coContractparameterlistVO.setContracttype(po.getContracttype());
	    coContractparameterlistVO.setStarteffectivedate(po.getStarteffectivedate());
	    coContractparameterlistVO.setEndeffectivedate(po.getEndeffectivedate());
	    coContractparameterlistVO.setIsshare(po.getIsshare());
	    coContractparameterlistVO.setCreatedate(po.getCreatedate());
	    coContractparameterlistVO.setCreator(po.getCreator());
	    coContractparameterlistVO.setIsdelete(po.getIsdelete());
	    coContractparameterlistVO.setUpdatetime(po.getUpdatetime());
	    coContractparameterlistVO.setUpdatepersonid(po.getUpdatepersonid());
	    coContractparameterlistVO.setContracttemplatecode(po.getContracttemplatecode());
	    coContractparameterlistVO.setDicId(po.getDicId());
	    coContractparameterlistVO.setBookmark(po.getBookmark());
		return coContractparameterlistVO;
	}
}
