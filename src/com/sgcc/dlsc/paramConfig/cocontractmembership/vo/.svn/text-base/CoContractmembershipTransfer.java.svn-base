package com.sgcc.dlsc.paramConfig.cocontractmembership.vo;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.sgcc.dlsc.entity.po.CoContractmembership;


public class CoContractmembershipTransfer {

	public static CoContractmembership toPO(CoContractmembershipVO vo) {
		CoContractmembership coContractmembership = new CoContractmembership();
		if(vo != null){
	         coContractmembership.setContracttypeid(vo.getContracttypeid());
	         coContractmembership.setMarketid(vo.getMarketid());
	         coContractmembership.setParticipantid(vo.getParticipantid());
	         coContractmembership.setDisplaytype(vo.getDisplaytype());
	         coContractmembership.setGuid(vo.getGuid());
	         coContractmembership.setUpdatetime(vo.getUpdatetime());
	         coContractmembership.setUpdatepersonid(vo.getUpdatepersonid());
	    }
		return coContractmembership;
	}

	public static CoContractmembershipVO toVO(CoContractmembership po) {
		CoContractmembershipVO coContractmembershiplistVO = new CoContractmembershipVO();

	    coContractmembershiplistVO.setContracttypeid(po.getContracttypeid());
	    coContractmembershiplistVO.setMarketid(po.getMarketid());
	    coContractmembershiplistVO.setParticipantid(po.getParticipantid());
	    coContractmembershiplistVO.setDisplaytype(po.getDisplaytype());
	    coContractmembershiplistVO.setGuid(po.getGuid());
	    coContractmembershiplistVO.setUpdatetime(po.getUpdatetime());
	    coContractmembershiplistVO.setUpdatepersonid(po.getUpdatepersonid());
		return coContractmembershiplistVO;
	}
	
	
	/**
	 * 将前台提交的数据转换成合同准入成员信息 
	 * 
	 * @param params  前台请求提交的数据，包括合同角色，合同类型，市场成员ID集合
	 * 
	 * @param userName   用户信息
	 * 
	 * @param marketId   场景信息
	 * 
	 * @return 合同准入成员信息集合  {@link CoContractmembership}
	 * 
	 */
	public static List<CoContractmembership> buildContractmemberships(
			Map<String, String> params, String marketId, String userName) {
		
		String contractType = params.get("contractType");
		String contractroleType = params.get("contractroleType");
		String participantIDs = params.get("participantIDs");
		Date nowDate = new Date(System.currentTimeMillis());
		
		List<CoContractmembership> values = new ArrayList<CoContractmembership>();
		String[] ids = participantIDs.split(",");
		
		//清除重复的Id，null，""这些不正确的ID
		Set<String> setIds = uniquedMemberships(ids);
		
		for (String id : setIds) {
			if (StringUtils.isNotBlank(id)) {
				CoContractmembership cms = new CoContractmembership
						(contractType, marketId, id, new BigDecimal(contractroleType), null);
				
				cms.setUpdatepersonid(userName);
				cms.setUpdatetime(nowDate);  //当前时间
				
				values.add(cms);
			}
		}
		return values;
	}
	
	/**
	 * 清除重复的Id，null，""这些不正确的ID
	 * 
	 * @param ids 前台传递的ID集合
	 * 
	 * @return 唯一的ID集合
	 */
	private static Set<String> uniquedMemberships(String[] ids) {
		Set<String> setIds = new HashSet<String>();
		if (ids != null && ids.length > 0) {
			for (String id : ids) {
				setIds.add(id);
			}
		}
		return setIds;
	}
	
	public static ContractmembershipRequestConditionVO buildRequestConditionVO(Map<String, String> condition) {
		ContractmembershipRequestConditionVO vo = new ContractmembershipRequestConditionVO();
//		String marketId = condition.get("marketId");
//		String coType = condition.get("contractType");
//		String displayType = condition.get("contractroleType");
//		String participantType = condition.get("participantType");
//		String participantName = condition.get("participantName");
//		String shareMarket = condition.get("shareMarket");
		
		vo.setContractroleType(condition.get("contractroleType"));
		vo.setContractType(condition.get("contractType"));
		vo.setMarketId(condition.get("marketId"));
		
		vo.setParticipantName(condition.get("participantName"));
		vo.setParticipantType(condition.get("participantType"));
		vo.setShareMarket(condition.get("shareMarket"));
		
		vo.setCommercialType(condition.get("commercialType"));
		vo.setGeneratorClass(condition.get("generatorClass"));
		return vo;
	}
}
