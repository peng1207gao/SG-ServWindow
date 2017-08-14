package com.sgcc.dlsc.contractManage.coContractgateinfo.vo;

import com.sgcc.dlsc.entity.po.CoContractgateinfo;

public class CoContractgateinfoTransfer {

	public static CoContractgateinfo toPO(CoContractgateinfoVO vo) {
		CoContractgateinfo coContractgateinfo = new CoContractgateinfo();
		if(vo != null){
	         coContractgateinfo.setContractid(vo.getContractid());
	         coContractgateinfo.setMarketid(vo.getMarketid());
	         coContractgateinfo.setGateid(vo.getGateid());
	         coContractgateinfo.setGatename(vo.getGatename());
	         coContractgateinfo.setDisplaytype(vo.getDisplaytype());
	         coContractgateinfo.setParticipantid(vo.getParticipantid());
	         coContractgateinfo.setBusiunitid(vo.getBusiunitid());
	         coContractgateinfo.setUpdatetime(vo.getUpdatetime());
	         coContractgateinfo.setUpdatepersonid(vo.getUpdatepersonid());
	         coContractgateinfo.setGuid(vo.getGuid());
	    }
		return coContractgateinfo;
	}

	public static CoContractgateinfoVO toVO(CoContractgateinfo po) {
		CoContractgateinfoVO coContractgateinfolistVO = new CoContractgateinfoVO();

	    coContractgateinfolistVO.setContractid(po.getContractid());
	    coContractgateinfolistVO.setMarketid(po.getMarketid());
	    coContractgateinfolistVO.setGateid(po.getGateid());
	    coContractgateinfolistVO.setGatename(po.getGatename());
	    coContractgateinfolistVO.setDisplaytype(po.getDisplaytype());
	    coContractgateinfolistVO.setParticipantid(po.getParticipantid());
	    coContractgateinfolistVO.setBusiunitid(po.getBusiunitid());
	    coContractgateinfolistVO.setUpdatetime(po.getUpdatetime());
	    coContractgateinfolistVO.setUpdatepersonid(po.getUpdatepersonid());
	    coContractgateinfolistVO.setGuid(po.getGuid());
		return coContractgateinfolistVO;
	}
}
