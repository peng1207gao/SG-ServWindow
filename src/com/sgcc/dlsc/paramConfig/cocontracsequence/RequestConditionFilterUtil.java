package com.sgcc.dlsc.paramConfig.cocontracsequence;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.sgcc.uap.rest.support.RequestCondition;

/**
 * 工具类
 * 
 * 解析对象 {@link RequestCondition}
 * 
 * @author djdeng
 *
 */
public class RequestConditionFilterUtil {

	/**
	 * 
	 * @param requestCondition
	 * @return
	 */
	public static Map<String, String> resolveFilterParams(RequestCondition requestCondition) {
		Map<String, String> paramMap = new HashMap<String, String>();
		String filtersStr = requestCondition.getFilter().toString();
		
		if (StringUtils.isNotBlank(filtersStr)) {
			String[] filtersArray = filtersStr.split("&");
			
			if (filtersArray != null && filtersArray.length > 0) {
				for (String filterStr : filtersArray) {
					String[] filterArray = filterStr.split("=");
					
					paramMap.put(filterArray[0], filterArray[1]);
				}
			}
		}
		return paramMap;
	}
	
}
