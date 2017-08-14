package com.sgcc.dlsc.contractManage.cocontractaccessory.vo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.sgcc.dlsc.entity.po.BaGenerator;

public class CoBaGeneratorTransfer {

	public static CoBaGeneratorVO toVO(BaGenerator po) {
		CoBaGeneratorVO vo = new CoBaGeneratorVO();
		vo.setGeneratorid(po.getGeneratorid());
		vo.setGeneratorname(po.getGeneratorname());
		return vo;
	}

	public static List<CoBaGeneratorVO> toVO(List<Object[]> objs, 
			String contractrole, String contractid) {
		
		List<CoBaGeneratorVO> vos = new ArrayList<CoBaGeneratorVO>();
		if (objs == null || objs.isEmpty()) {
			return vos;
		}
		for (Object[] obj : objs) {
			CoBaGeneratorVO vo = new CoBaGeneratorVO();
			vo.setContractid(contractid);
			vo.setContractrole(new BigDecimal(contractrole));
			vo.setGeneratorid((String) obj[1]);
			vo.setGeneratorname((String) obj[0]);
			vos.add(vo);
		}
		return vos;
	}

}
