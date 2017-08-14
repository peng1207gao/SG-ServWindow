package com.sgcc.dlsc.contractManage.conEnerMessByType;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;

import com.sgcc.dlsc.commons.util.ToolsSys;
import com.sgcc.dlsc.commons.util.UserInfoUtil;
import com.sgcc.dlsc.contractManage.coContractCopy.bizc.ICoContractCopyBizc;
import com.sgcc.dlsc.contractManage.conEnerMessByType.bizc.IConEnerMessByTypeBizc;
import com.sgcc.uap.rest.annotation.ItemResponseBody;
import com.sgcc.uap.rest.utils.JsonUtils;

@Controller
@RequestMapping("/conEnerMessByType") //根据po类名生成
public class ConEnerMessByTypeController {
	
	@Autowired
	private IConEnerMessByTypeBizc bizc;
	@RequestMapping(value = "/getChartPie",method = RequestMethod.GET)
	public @ItemResponseBody Object getChartPie(@RequestParam String params,HttpServletRequest request) throws Exception{	
		try {
			Map map = JsonUtils.getObjectMapper().readValue(params, Map.class);
			String marketId="";
			if (!ToolsSys.isnull(map.get("marketId").toString())) {
				marketId = UserInfoUtil.getLoginUserMarket(request);
			}else{
				marketId=map.get("marketId").toString();
			}
			String chartsXML = bizc.getPie(marketId);//@根据业务场景id查相应的图表的数据
			
			List sqllist = bizc.getCocapa(marketId);//@获取到list
			//contextPvd.setRequestAttr("sqllist", sqllist);
			if(sqllist.size()==0){
				return "0";
			}else{
				return chartsXML;
			}
		} catch (Exception e) {
			throw new RestClientException("程序异常");
		}
	}
}
