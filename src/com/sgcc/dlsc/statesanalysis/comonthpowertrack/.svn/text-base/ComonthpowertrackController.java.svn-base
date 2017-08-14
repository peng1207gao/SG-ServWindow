package com.sgcc.dlsc.statesanalysis.comonthpowertrack;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sgcc.dlsc.commons.util.UserInfoUtil;
import com.sgcc.dlsc.statesanalysis.comonthpowertrack.bizc.IComonthpowertrackBizc;
import com.sgcc.dlsc.util.ReportUtils;
import com.sgcc.uap.rest.annotation.ItemResponseBody;
import com.sgcc.uap.rest.annotation.RawResponseBody;
import com.sgcc.uap.rest.utils.JsonUtils;

@Controller
@RequestMapping("/comonthpowertrack") //根据po类名生成
public class ComonthpowertrackController {
	
    @Autowired
    private IComonthpowertrackBizc comonthpowertrackBizc;

    
    /**
	 * 
	 * @description 方法描述
	 * @param params
	 * @param request
	 * @return
	 * @throws Exception
	 * @author xuzihu
	 * @date 2014-4-2
	 */
	@RequestMapping(value="/getReportId",method=RequestMethod.GET)
	public @RawResponseBody Object getReportId(@RequestParam("params") String params)throws Exception{
		Map map = JsonUtils.getObjectMapper().readValue(params, Map.class);
		String key = (String) map.get("reportName");
		return ReportUtils.getTemplateId(key);
	}
    
	/**
	 * 注入逻辑构件
	 * @param comonthpowertrackBizc
	 */
	public void setComonthpowertrackBizc(IComonthpowertrackBizc comonthpowertrackBizc) {
		this.comonthpowertrackBizc = comonthpowertrackBizc;
	}
}
