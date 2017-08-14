package com.sgcc.dlsc.contractManage.contResoByMonth;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;

import com.sgcc.dlsc.commons.util.DateUtil;
import com.sgcc.dlsc.commons.util.ToolsSys;
import com.sgcc.dlsc.contractManage.coContractbaseinfoV2Act.vo.CoContractbaseinfoVO;
import com.sgcc.dlsc.contractManage.contResoByMonth.bizc.IContResoByMonthBizc;
import com.sgcc.dlsc.entity.po.CoContractenergyinfo;
import com.sgcc.uap.rest.annotation.ColumnRequestParam;
import com.sgcc.uap.rest.annotation.ColumnResponseBody;
import com.sgcc.uap.rest.annotation.RawResponseBody;
import com.sgcc.uap.rest.annotation.attribute.ViewAttributeData;
import com.sgcc.uap.rest.support.WrappedResult;
import com.sgcc.uap.rest.utils.ViewAttributeUtils;

@Controller
@RequestMapping("/contResoByMonth")
public class ContResoByMonthController {

	@Autowired
	private IContResoByMonthBizc bizc;
	
	String sdate;
	String edate;
	int syear;
	int eyear;
	int smonth;
	int emonth;
	int sday;
	int eday; 
	String StrBeginLastDay ;//合同开始月份最后一天天数
	String beginLastDay ;//合同开始月份最后一天
	String endFirstDay ;//合同截止月份第一天
	String papercontractcode;//纸质合同编号
	String contractname;//合同名称
	
	/**
	 * 复制合同
	 * 
	 * @param 分解方式
	 *         
	 *           
	 * @return queryResult
	 * @throws Exception 
	 */
	@RequestMapping("/queryFjfsSelect")
	public @RawResponseBody Object queryFjfsSelect(){
		try {
			List<Map<String, String>> namelist = new ArrayList<Map<String, String>>();
			String type = "95";
			List list = bizc.queryFjfsSelect(type);
			for (int i = 0; i < list.size(); i++) {
				Map<String, String> map = new HashMap<String, String>();
				Object[] obj = (Object[]) list.get(i);
				if(!"5".equals(obj[0].toString())&&!"6".equals(obj[0].toString())){
					map.put("name", obj[1] == null ? "" : obj[1].toString());//封装下拉列表数据
					map.put("value", obj[0] == null ? "" : obj[0].toString());
					namelist.add(map);
				}
				
			}
			return WrappedResult.successWrapedResult(namelist);
		} catch (Exception e) {
			throw new RestClientException("程序异常");
		}
	}
	/**
	 * 复制合同
	 * @param 分解方式
	 * @return queryResult
	 * @throws Exception 
	 */
	@RequestMapping("/energyFJ")
	public @RawResponseBody Object energyFJ(@RequestParam("contractid") String contractid,@RequestParam("energyFS") String energyFS,
			@RequestParam("accuracyJs") String accuracyJs,@RequestParam("flag") String flag){
		try {
			Integer accuracy = Integer.parseInt(accuracyJs);
			List<Map<String, String>> namelist = new ArrayList<Map<String, String>>();
			Map<String, String> map = new LinkedHashMap<String, String>();
			Map<String, String> map1 = new LinkedHashMap<String, String>();
			Map<String, String> map2 = new LinkedHashMap<String, String>();
			Map<String, String> map3 = new LinkedHashMap<String, String>();
			Map<String, String> map4 = new LinkedHashMap<String, String>();
			Object[] obj = bizc.findContractById(contractid);
			sdate = ToolsSys.ObjIsNullToStr(obj[1]);//开始日期		
			edate = ToolsSys.ObjIsNullToStr(obj[2]);//结束日期
			Integer isdate = Integer.parseInt(sdate.replace("-", ""));//开始日期int
			Integer iedate = Integer.parseInt(edate.replace("-", ""));//结束日期int
			syear = Integer.parseInt(sdate.substring(0, 4));//开始时间年份
			smonth = Integer.parseInt(sdate.substring(5, 7));//开始时间月份
			sday = Integer.parseInt(sdate.substring(8, 10));//开始时间日期
			eyear = Integer.parseInt(edate.substring(0, 4));//结束时间年份
			emonth = Integer.parseInt(edate.substring(5, 7));//结束时间月份
			eday = Integer.parseInt(edate.substring(8, 10));//结束时间日期
			String StrBeginLastDay = String
					.valueOf(yearMonthDays(syear, smonth));//合同开始月份最后一天天数
			String beginLastDay = getNewDate(sdate.substring(0, 4),
					sdate.substring(5, 7), StrBeginLastDay);//合同开始月份最后一天
			String endFirstDay = getNewDate(edate.substring(0, 4),
					edate.substring(5, 7), "1");//合同截止月份第一天
			BigDecimal beginMonthDay = new BigDecimal(getworktime(sdate,
					beginLastDay) + 1);
			BigDecimal contractDay = new BigDecimal(
					getworktime(sdate, edate) + 1);
			int contractrole;//合同角色
			float checkday = 0;//合同期内的检修天数
			float day = 0;//合同期内不检修的天数
			Map daymap = new HashMap();
			if (energyFS.equals("5")) {
				contractrole = 2;
			} else {
				contractrole = 1;
			}
			List list = bizc.getSellergenCalendarList(contractid, contractrole);//@根据合同id，合同角色查询信息
			int unitNum = bizc.findUnitNum(contractid, contractrole);//@根据合同id，合同角色查询信息
			if (unitNum < 2) {
				unitNum = 1;
			}
			/*
			 * 计算合同期内的所有机组检修天数总和
			 */
			for (int i = 0; i < list.size(); i++) {
				Object[] obj1 = (Object[]) list.get(i);
				String checkStratDate = (String) obj1[2];//检修开始日期
				String checkEndDate = (String) obj1[3];//检修结束日期
				int checkStratDate1 = Integer.parseInt(checkStratDate.replace(
						"-", ""));
				int checkEndDate1 = Integer.parseInt(checkEndDate.replace("-",
						""));
				int sdate1 = Integer.parseInt(sdate.replace("-", ""));
				int edate1 = Integer.parseInt(edate.replace("-", ""));
				if (checkEndDate1 < sdate1) {//检修结束日期小于合同开始日期
					checkday += 0;
				} else if (checkEndDate1 >= sdate1 && checkStratDate1 < sdate1) {//
					checkday = Float
							.valueOf(getworktime(sdate, checkEndDate) + 1);
				} else if (checkEndDate1 <= edate1 && checkStratDate1 >= sdate1) {
					checkday += Float.valueOf(getworktime(checkStratDate,
							checkEndDate) + 1);
				} else if (checkEndDate1 >= edate1 && checkStratDate1 <= sdate1) {
					checkday += 0;
				} else if (checkEndDate1 > edate1 && checkStratDate1 <= edate1) {
					checkday += Float
							.valueOf(getworktime(checkStratDate, edate) + 1);
				} else if (checkEndDate1 > edate1) {
					checkday += 0;
				}
			}
			day = Float.valueOf(getworktime(sdate, edate) + 1) - checkday;
			/**
			 * 计算合同期内的每个月里的所有机组检修天数总和
			 */
			//开始月份的
			String fmsdate = sdate;//月份的合同开始时间
			String fmedate = beginLastDay;//月份的合同结束时间
			int fmsdate1 = Integer.parseInt(fmsdate.replace("-", ""));
			int fmedate1 = Integer.parseInt(fmedate.replace("-", ""));
			float checkday1 = 0;//合同期内的检修日期
			float day1 = 0;//合同期内的检修日期
			for (int i = 0; i < list.size(); i++) {
				Object[] obj1 = (Object[]) list.get(i);
				String checkStratDate = (String) obj1[2];//检修开始日期
				String checkEndDate = (String) obj1[3];//检修结束日期
				int checkStratDate1 = Integer.parseInt(checkStratDate.replace(
						"-", ""));
				int checkEndDate1 = Integer.parseInt(checkEndDate.replace("-",
						""));
				if (checkEndDate1 < fmsdate1) {//检修结束日期小于合同开始日期
					checkday1 += 0;
				} else if (checkEndDate1 >= fmsdate1
						&& checkStratDate1 < fmsdate1) {//
					checkday1 = checkEndDate1 - fmsdate1 + 1;
				} else if (checkEndDate1 <= fmedate1
						&& checkStratDate1 >= fmsdate1) {
					checkday1 += checkEndDate1 - checkStratDate1 + 1;
				} else if (checkEndDate1 >= fmedate1
						&& checkStratDate1 <= fmsdate1) {
					checkday1 += 0;
				} else if (checkEndDate1 > fmedate1
						&& checkStratDate1 <= fmedate1) {
					checkday1 += fmedate1 - checkStratDate1 + 1;
				} else if (checkEndDate1 > fmedate1) {
					checkday1 += 0;
				}
			}
			day1 = Float.valueOf(getworktime(sdate, beginLastDay) + 1)
					- checkday1;
			daymap.put(smonth, day1);
			BigDecimal b_day1 = new BigDecimal(day1);
			BigDecimal b_day3 = new BigDecimal(0);
			BigDecimal b_day = new BigDecimal(day);
			BigDecimal b_unitNum = new BigDecimal(unitNum);
			for (int j = smonth + 1; j < emonth; j++) {
				String mmsdate = syear + "-" + j + "-1";
				String mmedate = syear + "-" + j + "-"
						+ yearMonthDays(syear, j);
				int mmsdate1 = syear * 1000 + j * 100 + 1;
				int mmedate1 = syear * 1000 + j * 100 + yearMonthDays(syear, j);
				float checkday3 = 0;//合同期内的检修日期
				float day3 = 0;
				for (int i = 0; i < list.size(); i++) {
					Object[] obj1 = (Object[]) list.get(i);
					String checkStratDate = (String) obj1[2];//检修开始日期
					String checkEndDate = (String) obj1[3];//检修结束日期
					int checkStratDate1 = Integer.parseInt(checkStratDate
							.replace("-", ""));
					int checkEndDate1 = Integer.parseInt(checkEndDate.replace(
							"-", ""));
					if (checkEndDate1 < fmsdate1) {//检修结束日期小于合同开始日期
						checkday3 += 0;
					} else if (checkEndDate1 >= fmsdate1
							&& checkStratDate1 < fmsdate1) {//
						checkday3 = checkEndDate1 - fmsdate1 + 1;
					} else if (checkEndDate1 <= fmedate1
							&& checkStratDate1 >= fmsdate1) {
						checkday3 += checkEndDate1 - checkStratDate1 + 1;
					} else if (checkEndDate1 >= fmedate1
							&& checkStratDate1 <= fmsdate1) {
						checkday3 += 0;
					} else if (checkEndDate1 > fmedate1
							&& checkStratDate1 <= fmedate1) {
						checkday3 += fmedate1 - checkStratDate1 + 1;
					} else if (checkEndDate1 > fmedate1) {
						checkday3 += 0;
					}
				}
				day3 = Float.valueOf(yearMonthDays(syear, j)) - checkday3;
				daymap.put(j, day3);
				b_day3 = new BigDecimal(day3);
			}
			//结束月份
			String lmsdate = endFirstDay;
			String lmedate = edate;
			int lmsdate1 = Integer.parseInt(endFirstDay.replace("-", ""));
			int lmedate1 = Integer.parseInt(edate.replace("-", ""));
			float checkday2 = 0;//合同期内的检修日期
			float day2 = 0;
			for (int i = 0; i < list.size(); i++) {
				Object[] obj1 = (Object[]) list.get(i);
				String checkStratDate = (String) obj1[2];//检修开始日期
				String checkEndDate = (String) obj1[3];//检修结束日期
				int checkStratDate1 = Integer.parseInt(checkStratDate.replace(
						"-", ""));
				int checkEndDate1 = Integer.parseInt(checkEndDate.replace("-",
						""));
				if (checkEndDate1 < fmsdate1) {//检修结束日期小于合同开始日期
					checkday2 += 0;
				} else if (checkEndDate1 >= fmsdate1
						&& checkStratDate1 < fmsdate1) {//
					checkday2 = checkEndDate1 - fmsdate1 + 1;
				} else if (checkEndDate1 <= fmedate1
						&& checkStratDate1 >= fmsdate1) {
					checkday2 += checkEndDate1 - checkStratDate1 + 1;
				} else if (checkEndDate1 >= fmedate1
						&& checkStratDate1 <= fmsdate1) {
					checkday2 += 0;
				} else if (checkEndDate1 > fmedate1
						&& checkStratDate1 <= fmedate1) {
					checkday2 += fmedate1 - checkStratDate1 + 1;
				} else if (checkEndDate1 > fmedate1) {
					checkday2 += 0;
				}
			}
			day2 = Float.valueOf(getworktime(endFirstDay, edate) + 1)
					- checkday2;
			daymap.put(emonth, day2);
			BigDecimal b_day2 = new BigDecimal(day2);
			//如果是空就不分解
			if (obj[3] == null) { //合同电量
				for (int i = smonth; i <= emonth; i++) {
					map4.put(i + "", "");
				}
			} else {
				Float energy = Float
						.parseFloat(ToolsSys.ObjIsNullToStr(obj[3]));//合同电量
				BigDecimal b_energy = new BigDecimal(energy);
				if (energyFS.equals("2")) {//按天数分解
					//开始月份
					BigDecimal firstMonthenergy;
					firstMonthenergy = b_energy.multiply(beginMonthDay.divide(
							contractDay, 10, BigDecimal.ROUND_HALF_UP));
					map4.put(
							String.valueOf(smonth),
							String.valueOf(firstMonthenergy.setScale(
									-1 * accuracy, 5).floatValue()));

					/*中间月份： 合同分月电量=合同总电量*分解月份自然天数/(合同截止日期-合同开始日期+1)*/
					for (int i = smonth + 1; i < emonth; i++) {
						BigDecimal middleMonthDay = new BigDecimal(
								yearMonthDays(syear, i));
						BigDecimal middleMonthenergy;
						middleMonthenergy = b_energy.multiply(middleMonthDay
								.divide(contractDay, 10,
										BigDecimal.ROUND_HALF_UP));
						map4.put(
								String.valueOf(i),
								String.valueOf(middleMonthenergy.setScale(
										-1 * accuracy, 5).floatValue()));
					}

					//合同截止月份：合同分月电量=合同总电量*(合同截止日期-合同截止月份第一天+1)/(合同截止日期-合同开始日期+1)	
					BigDecimal sum4 = new BigDecimal(0);
					for (int i = smonth; i < emonth; i++) {
						sum4 = sum4.add(new BigDecimal((String) map4
								.get(i + "")));
					}
					BigDecimal lastMonthenergy_f = b_energy.subtract(sum4);
					map4.put(
							String.valueOf(emonth),
							String.valueOf(lastMonthenergy_f.setScale(
									-1 * accuracy, 5).floatValue()));
				} else if (energyFS.equals("5") || energyFS.equals("6")) {//按转出方/购入方检修日历分解

					map4.put(String.valueOf(smonth), String.valueOf((b_energy
							.multiply(b_day1.divide(b_day.multiply(b_unitNum),
									10, BigDecimal.ROUND_HALF_UP))).setScale(
							-1 * accuracy, 5).floatValue()));
					for (int j = smonth + 1; j < emonth; j++) {
						map4.put(String.valueOf(j), String.valueOf((b_energy
								.multiply(b_day3.divide(
										b_day.multiply(b_unitNum), 10,
										BigDecimal.ROUND_HALF_UP))).setScale(
								-1 * accuracy, 5).floatValue()));
					}
					BigDecimal sum4 = new BigDecimal(0);
					for (int i = smonth; i < emonth; i++) {
						sum4 = sum4.add(new BigDecimal((String) map4
								.get(i + "")));
					}
					BigDecimal lastMonthenergy_f4 = b_energy.subtract(sum4);
					map4.put(String.valueOf(emonth),
							String.valueOf(lastMonthenergy_f4.floatValue()));
				}

			}
			if (obj[4] == null) { //售电方发电量
				for (int i = smonth; i <= emonth; i++) {
					map.put(i + "", "");
				}
			} else {
				Float sellergen = Float.parseFloat(ToolsSys
						.ObjIsNullToStr(obj[4]));//合同电量
				BigDecimal b_sellergen = new BigDecimal(sellergen);
				if (energyFS.equals("2")) {//按天数分解
					//开始月份
					BigDecimal firstMonthsellergen;
					firstMonthsellergen = b_sellergen.multiply(beginMonthDay
							.divide(contractDay, 10, BigDecimal.ROUND_HALF_UP));
					map.put(String.valueOf(smonth),
							String.valueOf(firstMonthsellergen.setScale(
									-1 * accuracy, 5).floatValue()));

					/*中间月份： 合同分月电量=合同总电量*分解月份自然天数/(合同截止日期-合同开始日期+1)*/
					for (int i = smonth + 1; i < emonth; i++) {
						BigDecimal middleMonthDay = new BigDecimal(
								yearMonthDays(syear, i));
						BigDecimal middleMonthsellergen;
						middleMonthsellergen = b_sellergen
								.multiply(middleMonthDay.divide(contractDay,
										10, BigDecimal.ROUND_HALF_UP));
						map.put(String.valueOf(i),
								String.valueOf(middleMonthsellergen.setScale(
										-1 * accuracy, 5).floatValue()));
					}

					//合同截止月份：合同分月电量=合同总电量*(合同截止日期-合同截止月份第一天+1)/(合同截止日期-合同开始日期+1)	
					BigDecimal sum = new BigDecimal(0);
					for (int i = smonth; i < emonth; i++) {
						sum = sum.add(new BigDecimal((String) map.get(i + "")));
					}
					BigDecimal lastMonthsellergen_f = b_sellergen.subtract(sum);
					map.put(String.valueOf(emonth),
							String.valueOf(lastMonthsellergen_f));

				} else if (energyFS.equals("5") || energyFS.equals("6")) {//按转出方/购入方检修日历分解
					map.put(String.valueOf(smonth), String.valueOf((b_sellergen
							.multiply(b_day1.divide(b_day.multiply(b_unitNum),
									10, BigDecimal.ROUND_HALF_UP))).setScale(
							-1 * accuracy, 5).floatValue()));
					for (int j = smonth + 1; j < emonth; j++) {
						map.put(String.valueOf(j), String.valueOf((b_sellergen
								.multiply(b_day3.divide(
										b_day.multiply(b_unitNum), 10,
										BigDecimal.ROUND_HALF_UP))).setScale(
								-1 * accuracy, 5).floatValue()));
					}
					BigDecimal sum = new BigDecimal(0);
					for (int i = smonth; i < emonth; i++) {
						sum = sum.add(new BigDecimal((String) map.get(i + "")));
					}
					BigDecimal lastMonthsellergen_f = b_sellergen.subtract(sum);
					map.put(String.valueOf(emonth),
							String.valueOf(lastMonthsellergen_f.floatValue()));
				}

			}
			if (obj[5] == null) { //售电方上网电量
				for (int i = smonth; i <= emonth; i++) {
					map1.put(i + "", "");
				}
			} else {
				Float sellerenergy = Float.parseFloat(ToolsSys
						.ObjIsNullToStr(obj[5]));//合同电量
				BigDecimal b_sellerenergy = new BigDecimal(sellerenergy);
				if (energyFS.equals("2")) {//按天数分解
					//开始月份
					BigDecimal firstMonthsellerenergy;
					firstMonthsellerenergy = b_sellerenergy
							.multiply(beginMonthDay.divide(contractDay, 10,
									BigDecimal.ROUND_HALF_UP));
					map1.put(
							String.valueOf(smonth),
							String.valueOf(firstMonthsellerenergy.setScale(
									-1 * accuracy, 5).floatValue()));

					/*中间月份： 合同分月电量=合同总电量*分解月份自然天数/(合同截止日期-合同开始日期+1)*/
					for (int i = smonth + 1; i < emonth; i++) {
						BigDecimal middleMonthDay = new BigDecimal(
								yearMonthDays(syear, i));
						BigDecimal middleMonthsellerenergy;
						middleMonthsellerenergy = b_sellerenergy
								.multiply(middleMonthDay.divide(contractDay,
										10, BigDecimal.ROUND_HALF_UP));
						map1.put(String.valueOf(i), String
								.valueOf(middleMonthsellerenergy.setScale(
										-1 * accuracy, 5).floatValue()));
					}

					//合同截止月份：合同分月电量=合同总电量*(合同截止日期-合同截止月份第一天+1)/(合同截止日期-合同开始日期+1)	
					BigDecimal sum1 = new BigDecimal(0);
					for (int i = smonth; i < emonth; i++) {
						sum1 = sum1.add(new BigDecimal((String) map1
								.get(i + "")));
					}
					BigDecimal lastMonthsellerenergy_f = b_sellerenergy
							.subtract(sum1);
					map1.put(String.valueOf(emonth),
							String.valueOf(lastMonthsellerenergy_f));
				} else if (energyFS.equals("5") || energyFS.equals("6")) {//按转出方/购入方检修日历分解
					map1.put(String.valueOf(smonth), String
							.valueOf((b_sellerenergy.multiply(b_day1.divide(
									b_day.multiply(b_unitNum), 10,
									BigDecimal.ROUND_HALF_UP))).setScale(
									-1 * accuracy, 5).floatValue()));
					for (int j = smonth + 1; j < emonth; j++) {
						map1.put(String.valueOf(j), String
								.valueOf((b_sellerenergy.multiply(b_day3
										.divide(b_day.multiply(b_unitNum), 10,
												BigDecimal.ROUND_HALF_UP)))
										.setScale(-1 * accuracy, 5)
										.floatValue()));
					}
					BigDecimal sum1 = new BigDecimal(0);
					for (int i = smonth; i < emonth; i++) {
						sum1 = sum1.add(new BigDecimal((String) map1
								.get(i + "")));
					}
					BigDecimal lastMonthsellerenergy_f = b_sellerenergy
							.subtract(sum1);
					map1.put(String.valueOf(emonth), String
							.valueOf(lastMonthsellerenergy_f.floatValue()));
				}

			}
			if (obj[6] == null) { //被替代方发电量
				for (int i = smonth; i <= emonth; i++) {
					map2.put(i + "", "");
				}
			} else {
				Float purchasergen = Float.parseFloat(ToolsSys
						.ObjIsNullToStr(obj[6]));//合同电量
				BigDecimal b_purchasergen = new BigDecimal(purchasergen);
				if (energyFS.equals("2")) {//按天数分解
					//开始月份
					BigDecimal firstMonthpurchasergen;
					firstMonthpurchasergen = b_purchasergen
							.multiply(beginMonthDay.divide(contractDay, 10,
									BigDecimal.ROUND_HALF_UP));
					map2.put(
							String.valueOf(smonth),
							String.valueOf(firstMonthpurchasergen.setScale(
									-1 * accuracy, 5).floatValue()));

					/*中间月份： 合同分月电量=合同总电量*分解月份自然天数/(合同截止日期-合同开始日期+1)*/
					for (int i = smonth + 1; i < emonth; i++) {
						BigDecimal middleMonthDay = new BigDecimal(
								yearMonthDays(syear, i));
						BigDecimal middleMonthpurchasergen;
						middleMonthpurchasergen = b_purchasergen
								.multiply(middleMonthDay.divide(contractDay,
										10, BigDecimal.ROUND_HALF_UP));
						map2.put(String.valueOf(i), String
								.valueOf(middleMonthpurchasergen.setScale(
										-1 * accuracy, 5).floatValue()));
					}

					//合同截止月份：合同分月电量=合同总电量*(合同截止日期-合同截止月份第一天+1)/(合同截止日期-合同开始日期+1)	
					BigDecimal sum2 = new BigDecimal(0);
					for (int i = smonth; i < emonth; i++) {
						sum2 = sum2.add(new BigDecimal((String) map2
								.get(i + "")));
					}
					BigDecimal lastMonthpurchasergen_f = b_purchasergen
							.subtract(sum2);
					map2.put(String.valueOf(emonth),
							String.valueOf(lastMonthpurchasergen_f));
				} else if (energyFS.equals("5") || energyFS.equals("6")) {//按转出方/购入方检修日历分解
					map2.put(String.valueOf(smonth), String
							.valueOf((b_purchasergen.multiply(b_day1.divide(
									b_day.multiply(b_unitNum), 10,
									BigDecimal.ROUND_HALF_UP))).setScale(
									-1 * accuracy, 5).floatValue()));
					for (int j = smonth + 1; j < emonth; j++) {
						map2.put(String.valueOf(j), String
								.valueOf((b_purchasergen.multiply(b_day3
										.divide(b_day.multiply(b_unitNum), 10,
												BigDecimal.ROUND_HALF_UP)))
										.setScale(-1 * accuracy, 5)
										.floatValue()));
					}
					BigDecimal sum2 = new BigDecimal(0);
					for (int i = smonth; i < emonth; i++) {
						sum2 = sum2.add(new BigDecimal((String) map2
								.get(i + "")));
					}
					BigDecimal lastMonthpurchasergen_f = b_purchasergen
							.subtract(sum2);
					map2.put(String.valueOf(emonth), String
							.valueOf(lastMonthpurchasergen_f.floatValue()));
				}

			}
			if (obj[7] == null) { //被替代方案上网电量
				for (int i = smonth; i <= emonth; i++) {
					map3.put(i + "", "");
				}
			} else {
				Float purchaserenergy = Float.parseFloat(ToolsSys
						.ObjIsNullToStr(obj[7]));//合同电量
				BigDecimal b_purchaserenergy = new BigDecimal(purchaserenergy);
				if (energyFS.equals("2")) {//按天数分解
					//开始月份
					BigDecimal firstMonthpurchaserenergy;
					firstMonthpurchaserenergy = b_purchaserenergy
							.multiply(beginMonthDay.divide(contractDay, 10,
									BigDecimal.ROUND_HALF_UP));
					map3.put(
							String.valueOf(smonth),
							String.valueOf(firstMonthpurchaserenergy.setScale(
									-1 * accuracy, 5).floatValue()));

					/*中间月份： 合同分月电量=合同总电量*分解月份自然天数/(合同截止日期-合同开始日期+1)*/
					for (int i = smonth + 1; i < emonth; i++) {
						BigDecimal middleMonthDay = new BigDecimal(
								yearMonthDays(syear, i));
						BigDecimal middleMonthpurchaserenergy;
						middleMonthpurchaserenergy = b_purchaserenergy
								.multiply(middleMonthDay.divide(contractDay,
										10, BigDecimal.ROUND_HALF_UP));
						map3.put(String.valueOf(i), String
								.valueOf(middleMonthpurchaserenergy.setScale(
										-1 * accuracy, 5).floatValue()));
					}

					//合同截止月份：合同分月电量=合同总电量*(合同截止日期-合同截止月份第一天+1)/(合同截止日期-合同开始日期+1)	
					BigDecimal sum3 = new BigDecimal(0);
					for (int i = smonth; i < emonth; i++) {
						sum3 = sum3.add(new BigDecimal((String) map3
								.get(i + "")));
					}
					BigDecimal lastMonthpurchaserenergy_f = b_purchaserenergy
							.subtract(sum3);
					map3.put(String.valueOf(emonth),
							String.valueOf(lastMonthpurchaserenergy_f));
				} else if (energyFS.equals("5") || energyFS.equals("6")) {//按转出方/购入方检修日历分解
					map3.put(String.valueOf(smonth), String
							.valueOf((b_purchaserenergy.multiply(b_day1.divide(
									b_day.multiply(b_unitNum), 10,
									BigDecimal.ROUND_HALF_UP))).setScale(
									-1 * accuracy, 5).floatValue()));
					for (int j = smonth + 1; j < emonth; j++) {
						map3.put(String.valueOf(j), String
								.valueOf((b_purchaserenergy.multiply(b_day3
										.divide(b_day.multiply(b_unitNum), 10,
												BigDecimal.ROUND_HALF_UP)))
										.setScale(-1 * accuracy, 5)
										.floatValue()));
					}
					BigDecimal sum3 = new BigDecimal(0);
					for (int i = smonth; i < emonth; i++) {
						sum3 = sum3.add(new BigDecimal((String) map3
								.get(i + "")));
					}
					BigDecimal lastMonthpurchaserenergy_f = b_purchaserenergy
							.subtract(sum3);
					map3.put(String.valueOf(emonth), String
							.valueOf(lastMonthpurchaserenergy_f.floatValue()));
				}

			}
			if (flag.equals("1")) {
				String data = "[";
				for (int i = smonth; i <= emonth; i++) {
					data += "{\"data_1\":\"" + map.get(i + "")
							+ "\",\"data_2\":\"" + map1.get(i + "")
							+ "\",\"data_3\":\"" + map2.get(i + "")
							+ "\",\"data_4\":\"" + map3.get(i + "")
							+ "\",\"data_5\":\"" + map4.get(i + "") + "\"}";
					if (i < emonth) {
						data += ",";
					}
				}
				data += "]";
				return WrappedResult.successWrapedResult(data);
			}
			namelist.add(map);
			namelist.add(map1);
			namelist.add(map2);
			namelist.add(map3);
			namelist.add(map4);
			return WrappedResult.successWrapedResult(namelist);
		} catch (Exception e) {
			throw new RestClientException("程序异常！");
		}
	}
	
	
	
	/**
	 * 复制合同
	 * @param 分解方式
	 * @return queryResult
	 * @throws Exception 
	 */
	@RequestMapping("/energyMonthFJ")
	public @RawResponseBody Object energyMonthFJ(@RequestParam("contractid") String contractid,@RequestParam("energyFS") String energyFS,
			@RequestParam("accuracyJs") String accuracyJs,@RequestParam("flag") String flag){
		try {
			Integer accuracy = Integer.parseInt(accuracyJs);
			List<Map<String, String>> namelist = new ArrayList<Map<String, String>>();
			Map<String, String> map = new LinkedHashMap<String, String>();
			Map<String, String> map1 = new LinkedHashMap<String, String>();
			Map<String, String> map2 = new LinkedHashMap<String, String>();
			Map<String, String> map3 = new LinkedHashMap<String, String>();
			Map<String, String> map4 = new LinkedHashMap<String, String>();
			Object[] obj = bizc.findContractById(contractid);
			sdate = ToolsSys.ObjIsNullToStr(obj[1]);//开始日期		
			edate = ToolsSys.ObjIsNullToStr(obj[2]);//结束日期
			Integer isdate = Integer.parseInt(sdate.replace("-", ""));//开始日期int
			Integer iedate = Integer.parseInt(edate.replace("-", ""));//结束日期int
			syear = Integer.parseInt(sdate.substring(0, 4));//开始时间年份
			smonth = Integer.parseInt(sdate.substring(5, 7));//开始时间月份
			sday = Integer.parseInt(sdate.substring(8, 10));//开始时间日期
			eyear = Integer.parseInt(edate.substring(0, 4));//结束时间年份
			emonth = Integer.parseInt(edate.substring(5, 7));//结束时间月份
			eday = Integer.parseInt(edate.substring(8, 10));//结束时间日期
			if (obj[3] == null) { //合同电量
				for (int i = smonth; i <= emonth; i++) {
					map4.put(i + "", "");
				}
			} else {
				Float energy = Float.parseFloat(ToolsSys.ObjIsNullToStr(obj[3]));//合同电量
				BigDecimal monthEnergy = new BigDecimal(energy/(emonth-smonth+1));
				Float monthEnergyFloat = monthEnergy.setScale(-1 * accuracy, 5).floatValue();
				BigDecimal lastmonthEnergyBig = new BigDecimal(energy.floatValue()-monthEnergyFloat*(emonth-smonth));
				Float lastmonthEnergyFloat = lastmonthEnergyBig.setScale(-1 * accuracy, 5).floatValue();
				for (int i = smonth; i <= emonth; i++) {
					if(i==emonth){
						map4.put(String.valueOf(i),String.valueOf(lastmonthEnergyFloat));
					}else{
						map4.put(String.valueOf(i),String.valueOf(monthEnergyFloat));
					}
					
				}
		    }
			
			if (obj[4] == null) { //售电方发电量
				for (int i = smonth; i <= emonth; i++) {
					map.put(i + "", "");
				}
			} else {
				Float sellergen = Float.parseFloat(ToolsSys.ObjIsNullToStr(obj[4]));//售电方发电量
				BigDecimal monthSellergen = new BigDecimal(sellergen/(emonth-smonth+1));
				Float monthSellergenFloat = monthSellergen.setScale(-1 * accuracy, 5).floatValue();
				BigDecimal lastmonthSellergenFloatBig = new BigDecimal(sellergen.floatValue()-monthSellergenFloat*(emonth-smonth));
				Float lastmonthSellergenFloat = lastmonthSellergenFloatBig.setScale(-1 * accuracy, 5).floatValue();
				for (int i = smonth; i <= emonth; i++) {
					if(i==emonth){
						map.put(String.valueOf(i),String.valueOf(lastmonthSellergenFloat));
					}else{
						map.put(String.valueOf(i),String.valueOf(monthSellergenFloat));
					}
				}
		    }
			
			if (obj[5] == null) { //售电方上网电量
				for (int i = smonth; i <= emonth; i++) {
					map1.put(i + "", "");
				}
			} else {
				Float sellerenergy = Float.parseFloat(ToolsSys.ObjIsNullToStr(obj[5]));//售电方上网电量
				BigDecimal monthSellerenergy = new BigDecimal(sellerenergy/(emonth-smonth+1));
				Float monthSellerenergyFloat = monthSellerenergy.setScale(-1 * accuracy, 5).floatValue();
				BigDecimal lastmonthSellerenergyBig = new BigDecimal(sellerenergy.floatValue()-monthSellerenergyFloat*(emonth-smonth));
				Float lastmonthSellerenergyFloat = lastmonthSellerenergyBig.setScale(-1 * accuracy, 5).floatValue();
				for (int i = smonth; i <= emonth; i++) {
					if(i==emonth){
						map1.put(String.valueOf(i),String.valueOf(lastmonthSellerenergyFloat));
					}else{
						map1.put(String.valueOf(i),String.valueOf(monthSellerenergyFloat));
					}
				}
		    }
			
			if (obj[6] == null) { //被替代方发电量
				for (int i = smonth; i <= emonth; i++) {
					map2.put(i + "", "");
				}
			} else {
				Float purchasergen = Float.parseFloat(ToolsSys.ObjIsNullToStr(obj[6]));//被替代方发电量
				BigDecimal monthPurchasergen = new BigDecimal(purchasergen/(emonth-smonth+1));
				Float monthPurchasergenFloat = monthPurchasergen.setScale(-1 * accuracy, 5).floatValue();
				BigDecimal lastmonthPurchasergenBig = new BigDecimal(purchasergen.floatValue()-monthPurchasergenFloat*(emonth-smonth));
				Float lastmonthPurchasergenFloat = lastmonthPurchasergenBig.setScale(-1 * accuracy, 5).floatValue();
				for (int i = smonth; i <= emonth; i++) {
					if(i==emonth){
						map2.put(String.valueOf(i),String.valueOf(lastmonthPurchasergenFloat));
					}else{
						map2.put(String.valueOf(i),String.valueOf(monthPurchasergenFloat));
					}
					
				}
		    }
			
			if (obj[7] == null) { //被替代方案上网电量
				for (int i = smonth; i <= emonth; i++) {
					map3.put(i + "", "");
				}
			} else {
				Float purchaserenergy = Float.parseFloat(ToolsSys.ObjIsNullToStr(obj[7]));//被替代方案上网电量
				BigDecimal monthPurchaserenergy = new BigDecimal(purchaserenergy/(emonth-smonth+1));
				Float monthPurchaserenergyFloat = monthPurchaserenergy.setScale(-1 * accuracy, 5).floatValue();
				BigDecimal lastmonthPurchaserenergyBig = new BigDecimal(purchaserenergy.floatValue()-monthPurchaserenergyFloat*(emonth-smonth));
				Float lastmonthPurchaserenergyFloat = lastmonthPurchaserenergyBig.setScale(-1 * accuracy, 5).floatValue();
				for (int i = smonth; i <= emonth; i++) {
					if(i==emonth){
						map3.put(String.valueOf(i),String.valueOf(lastmonthPurchaserenergyFloat));
					}else{
						map3.put(String.valueOf(i),String.valueOf(monthPurchaserenergyFloat));
					}
					
				}
		    }
			
			if (flag.equals("1")) {
				String data = "[";
				for (int i = smonth; i <= emonth; i++) {
					data += "{\"data_1\":\"" + map.get(i + "")
							+ "\",\"data_2\":\"" + map1.get(i + "")
							+ "\",\"data_3\":\"" + map2.get(i + "")
							+ "\",\"data_4\":\"" + map3.get(i + "")
							+ "\",\"data_5\":\"" + map4.get(i + "") + "\"}";
					if (i < emonth) {
						data += ",";
					}
				}
				data += "]";
				return WrappedResult.successWrapedResult(data);
			}
			namelist.add(map);
			namelist.add(map1);
			namelist.add(map2);
			namelist.add(map3);
			namelist.add(map4);
			return WrappedResult.successWrapedResult(namelist);
			
		} catch (Exception e) {
			throw new RestClientException("程序异常！");
		}
	}
	
	
	/**
	 * 计算天数差的函数，通用  
	 * @description 方法描述
	 * @return
	 * @author zhounan
	 * @date 2013-1-18
	 */
	public static int getworktime(String startdate, String enddate){ 
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//@初始化日期格式
			Date startTime = null;
			Date endTime = null;
			try {
				endTime = dateFormat.parse(enddate);//@设置结束日期
				startTime = dateFormat.parse(startdate);//@设置开始时间
			} catch (ParseException e) {
				e.printStackTrace();
			}
			int days = (int) ((endTime.getTime() - startTime.getTime()) / (1000 * 60 * 60 * 24));
			return days;
		} catch (Exception e) {
			throw new RestClientException("程序异常！");
		}
	}
	/**
	 * 复制合同
	 * 
	 * @param 分解方式
	 *         
	 *           
	 * @return queryResult
	 * @throws Exception 
	 */
	@RequestMapping("/initHtdl")
	public @RawResponseBody Object initHtdl(@RequestParam("contractid") String contractid){
		try {
			List errorlist = new ArrayList();
			List<Map<String, String>> namelist = new ArrayList<Map<String, String>>();
			Map<String, String> map = new LinkedHashMap<String, String>();
			Map<String, String> map1 = new LinkedHashMap<String, String>();
			Map<String, String> map2 = new LinkedHashMap<String, String>();
			Map<String, String> map3 = new LinkedHashMap<String, String>();
			Map<String, String> map4 = new LinkedHashMap<String, String>();
			map.put("name", "sellergen");
			map1.put("name", "sellerenergy");
			map2.put("name", "purchasergen");
			map3.put("name", "purchaserenergy");
			map4.put("name", "energy");
			if (ToolsSys.isnull(contractid)) {
				Object[] obj = bizc.findContractById(contractid);
				sdate = ToolsSys.ObjIsNullToStr(obj[1]);//开始时间		
				edate = ToolsSys.ObjIsNullToStr(obj[2]);//结束时间
				papercontractcode = ToolsSys.ObjIsNullToStr(obj[9]);//纸质合同编号
				contractname = ToolsSys.ObjIsNullToStr(obj[10]);//合同名称
				if (!ToolsSys.isnull(sdate) || !ToolsSys.isnull(edate)) {
					String anErrorMessage = "合同开始时间、结束时间不能为空，否则无法进行分解！";
					errorlist.add("error1");
					errorlist.add(anErrorMessage);
					return WrappedResult.successWrapedResult(errorlist);
				} else if (obj[3] == null && obj[4] == null && obj[5] == null
						&& obj[6] == null && obj[7] == null) {
					String anErrorMessage = "合同电量、替代方发电量、替代方上网电量、被替代方发电量、被替代方上网电量不能全部为空，否则无法进行分解！";
					errorlist.add("error2");
					errorlist.add(anErrorMessage);
					return WrappedResult.successWrapedResult(errorlist);
				}
				syear = Integer.parseInt(sdate.substring(0, 4));//开始时间年份
				smonth = Integer.parseInt(sdate.substring(5, 7));//开始时间月份
				sday = Integer.parseInt(sdate.substring(8, 10));//开始时间日期

				eyear = Integer.parseInt(edate.substring(0, 4));//结束时间年份
				emonth = Integer.parseInt(edate.substring(5, 7));//结束时间月份
				eday = Integer.parseInt(edate.substring(8, 10));//结束时间日期	

				StrBeginLastDay = String.valueOf(yearMonthDays(syear, smonth));//合同开始月份最后一天天数
				beginLastDay = getNewDate(sdate.substring(0, 4),
						sdate.substring(5, 7), StrBeginLastDay);//合同开始月份最后一天
				endFirstDay = getNewDate(edate.substring(0, 4),
						edate.substring(5, 7), "1");//合同截止月份第一天

				boolean isok = bizc.isOnlyOper(contractid);
				if (isok) {
					//拆分时间到具体每个月的开始时间，结束时间
					//开始月份的
					String startdate1 = sdate;
					String enddate1 = beginLastDay;
					List list1 = bizc.getDataList(contractid, startdate1,
							enddate1);//@根据合同id，开始日期，结束日期查询数据
					if (list1.size() != 0) {
						Object[] obj1 = (Object[]) list1.get(0);
						map.put("month" + smonth, obj1[3] == null ? ""
								: obj1[3].toString());
						map1.put("month" + smonth, obj1[4] == null ? ""
								: obj1[4].toString());
						map2.put("month" + smonth, obj1[5] == null ? ""
								: obj1[5].toString());
						map3.put("month" + smonth, obj1[6] == null ? ""
								: obj1[6].toString());
						map4.put("month" + smonth, obj1[7] == null ? ""
								: obj1[7].toString());
					}
					//中间月份的
					for (int i = smonth + 1; i < emonth; i++) {
						String startdate2 = syear + "-" + i + "-1";
						String enddate2 = syear + "-" + i + "-"
								+ yearMonthDays(syear, i);
						List list2 = bizc.getDataList(contractid, startdate2,
								enddate2);//@根据合同id，开始日期，结束日期查询数据
						if (list2.size() != 0) {
							Object[] obj2 = (Object[]) list2.get(0);
							map.put("month" + i,
									obj2[3] == null ? "" : obj2[3].toString());
							map1.put("month" + i, obj2[4] == null ? ""
									: obj2[4].toString());
							map2.put("month" + i, obj2[5] == null ? ""
									: obj2[5].toString());
							map3.put("month" + i, obj2[6] == null ? ""
									: obj2[6].toString());
							map4.put("month" + i, obj2[7] == null ? ""
									: obj2[7].toString());
						}
					}
					//最后一个月的
					String startdate3 = endFirstDay;
					String enddate3 = edate;
					List list3 = bizc.getDataList(contractid, startdate3,
							enddate3);//@根据合同id，开始日期，结束日期查询数据
					if (list3.size() != 0) {
						Object[] obj3 = (Object[]) list3.get(0);
						map.put("month" + emonth, obj3[3] == null ? ""
								: obj3[3].toString());
						map1.put("month" + emonth, obj3[4] == null ? ""
								: obj3[4].toString());
						map2.put("month" + emonth, obj3[5] == null ? ""
								: obj3[5].toString());
						map3.put("month" + emonth, obj3[6] == null ? ""
								: obj3[6].toString());
						map4.put("month" + emonth, obj3[7] == null ? ""
								: obj3[7].toString());
					}
					namelist.add(map);
					namelist.add(map1);
					namelist.add(map2);
					namelist.add(map3);
					namelist.add(map4);
				} else {
					errorlist.add("canOper");//@给request的Attribute属性赋值
					return WrappedResult.successWrapedResult(errorlist);
				}

				if (sdate.equals("") || edate.equals("")) {
					String error3 = "必须选择开始时间和结束时间都存在的数据！";
					errorlist.add("error3");//@给request的Attribute属性赋值
					errorlist.add(error3);
					return WrappedResult.successWrapedResult(errorlist);
				}
				int isdate = Integer.parseInt(sdate.replace("-", ""));
				int iedate = Integer.parseInt(edate.replace("-", ""));
				if (isdate > iedate) {
					String error4 = "开始时间必须早于结束时间！";
					errorlist.add("error4");//@给request的Attribute属性赋值
					errorlist.add(error4);
					return WrappedResult.successWrapedResult(errorlist);
				}
				Map<String, String> map5 = new LinkedHashMap<String, String>();
				map5.put("papercontractcode", papercontractcode + "");//@给request的Attribute属性赋值
				map5.put("contractname", contractname + "");//@给request的Attribute属性赋值
				map5.put("syear", syear + "");//@给request的Attribute属性赋值
				map5.put("smonth", smonth + "");//@给request的Attribute属性赋值
				map5.put("sdate", sdate + "");//@给request的Attribute属性赋值
				map5.put("edate", edate + "");//@给request的Attribute属性赋值
				map5.put("sday", sday + "");//@给request的Attribute属性赋值
				map5.put("eyear", eyear + "");//@给request的Attribute属性赋值
				map5.put("emonth", emonth + "");//@给request的Attribute属性赋值
				map5.put("eday", eday + "");//@给request的Attribute属性赋值
				map5.put("sellergen", obj[4] == null ? "" : obj[4] + "");
				map5.put("sellerenergy", obj[5] == null ? "" : obj[5] + "");
				map5.put("purchasergen", obj[6] == null ? "" : obj[6] + "");
				map5.put("purchaserenergy", obj[7] == null ? "" : obj[7] + "");
				map5.put("energy", obj[3] == null ? "" : obj[3] + "");
				namelist.add(map5);
				//			map5.put("obj", obj);//@给request的Attribute属性赋值
			}
			return WrappedResult.successWrapedResult(namelist);
		} catch (Exception e) {
			
			throw new RestClientException("程序异常！");
		}
	}
	
	/**
	 * 复制合同
	 * 
	 * @param 分解方式
	 *         
	 *           
	 * @return queryResult
	 * @throws Exception 
	 */
	@RequestMapping("/delHtdl")
	public @RawResponseBody Object delHtdl(@RequestParam("contractid") String contractid){
		try {
			List<String> namelist = new ArrayList<String>();
			boolean tenbool = bizc.delHtdl(contractid);
			if (tenbool) {
				namelist.add("删除成功");
			} else {
				namelist.add("删除失败");
			}
			return WrappedResult.successWrapedResult(namelist);
		} catch (Exception e) {
			throw new RestClientException("程序异常！");
		}
	}
	
	/**
	 * 检验合同类型
	 * 
	 * @param 
	 *         
	 *           
	 * @return queryResult
	 * @throws Exception 
	 */
	@RequestMapping("/checkCoType")
	public @RawResponseBody Object checkCoType(@RequestParam("coType") String coType){
		try {
			List<String> namelist = new ArrayList<String>();
			String str =bizc.checkCoType(coType);
			namelist.add(str);
			return WrappedResult.successWrapedResult(namelist);
		} catch (Exception e) {
			throw new RestClientException("程序异常！");
		}
	}
	
	
	/**
	 * 根据合同id检验合同类型
	 * 
	 * @param 
	 *         
	 *           
	 * @return queryResult
	 * @throws Exception 
	 */
	@RequestMapping("/checkCoTypeTwo")
	public @RawResponseBody Object checkCoTypeTwo(@RequestParam("contractid") String contractid){
		try {
			List<String> namelist = new ArrayList<String>();
			String str =bizc.checkCoTypeTwo(contractid);
			namelist.add(str);
			return WrappedResult.successWrapedResult(namelist);
		} catch (Exception e) {
			throw new RestClientException("程序异常！");
		}
	}
	
	/**
	 * 复制合同
	 * 
	 * @param 检验合同分解
	 *         
	 *           
	 * @return queryResult
	 * @throws Exception 
	 */
	@RequestMapping("/check")
	public @RawResponseBody Object check(@RequestParam("contractid") String contractid,@RequestParam("sdate") String sdate,@RequestParam("edate") String edate){
		try {
			String startdate;
			String enddate;
			syear = Integer.parseInt(sdate.substring(0, 4));//开始时间年份
			smonth = Integer.parseInt(sdate.substring(5, 7));//开始时间月份
			sday = Integer.parseInt(sdate.substring(8, 10));//开始时间日期
			int canned = 0;
			String data = "";
			for (int i = smonth; i <= emonth; i++) {
				if (i == smonth) {
					startdate = sdate;//@设置开始时间
					enddate = syear + "-" + i + "-"
							+ yearMonthDays(syear, smonth);//@设置结束时间
				} else if (i == emonth) {
					startdate = syear + "-" + emonth + "-1";//@设置开始时间
					enddate = edate;//@设置结束时间
				} else {
					startdate = syear + "-" + i + "-1";//@设置开始时间
					enddate = syear + "-" + i + "-" + yearMonthDays(syear, i);//@设置结束时间
				}
				List list = bizc.findOldInfo(contractid, startdate, enddate);//校验是否已存在数据
				if (list != null && list.size() > 0) {
					canned++;
				}
			}
			if (canned > 0) {
				data = "[{\"data\":\"" + canned + "\"}]";
			} else {
				data = "[]";
			}
			return WrappedResult.successWrapedResult(data);
		} catch (Exception e) {
			throw new RestClientException("程序异常！");
		}
	}
	
	/**
	 * 复制合同
	 * 
	 * @param 分解方式
	 *         
	 *           
	 * @return queryResult
	 * @throws SQLException 
	 * @throws Exception 
	 */
	@RequestMapping("/save")
	public @RawResponseBody Object save(@RequestParam("energys") String energys,@RequestParam("sellergens") String sellergens,
			@RequestParam("sellerenergys") String sellerenergys,@RequestParam("purchasergens") String purchasergens,@RequestParam("purchaserenergys") String purchaserenergys
			,@RequestParam("contractid") String contractid,@RequestParam("sdate") String sdate,@RequestParam("edate") String edate,HttpServletRequest request) {
		try {
			String startdate;
			String enddate;
			syear = Integer.parseInt(sdate.substring(0, 4));//开始时间年份
			smonth = Integer.parseInt(sdate.substring(5, 7));//开始时间月份
			sday = Integer.parseInt(sdate.substring(8, 10));//开始时间日期
			String[] purchaserGen1;
			String[] purchaserEnergy1;
			String[] sellerGen1;
			String[] sellerEnergy1;
			String[] energy1;
			List<String> updateList = new ArrayList<String>();
			List<CoContractenergyinfo> insertList = new ArrayList<CoContractenergyinfo>();
			List<CoContractenergyinfo> insertList2 = new ArrayList<CoContractenergyinfo>();
			purchasergens += ", zb";
			purchaserenergys += ", zb";
			sellergens += ", zb";
			sellerenergys += ", zb";
			energys += ", zb";
			purchaserGen1 = purchasergens.split(",");
			purchaserEnergy1 = purchaserenergys.split(",");
			sellerGen1 = sellergens.split(",");
			sellerEnergy1 = sellerenergys.split(",");
			energy1 = energys.split(",");
			int canned = 0;
			for (int i = smonth; i <= emonth; i++) {
				if (i == smonth) {
					startdate = sdate;//@设置开始时间
					enddate = syear + "-" + i + "-"
							+ yearMonthDays(syear, smonth);//@设置结束时间
				} else if (i == emonth) {
					startdate = eyear + "-" + emonth + "-1";//@设置开始时间
					enddate = edate;//@设置结束时间
				} else {
					startdate = syear + "-" + i + "-1";//@设置开始时间
					enddate = syear + "-" + i + "-" + yearMonthDays(syear, i);//@设置结束时间
				}
				List list = bizc.findOldInfo(contractid, startdate, enddate);//校验是否已存在数据
				if (list != null && list.size() > 0) {
					canned++;
					String sql = "update co_contractenergyinfo set period=1, energy='"
							+ energy1[i - 1] + "',purchasergen='"
							+ purchaserGen1[i - 1] + "',purchaserenergy='"
							+ purchaserEnergy1[i - 1] + "', sellergen='"
							+ sellerGen1[i - 1] + "', sellerenergy='"
							+ sellerEnergy1[i - 1] + "' where contractid='"
							+ contractid + "' and startdate=to_date('"
							+ startdate
							+ "','YYYY-MM-DD') and enddate=to_date('" + enddate
							+ "','YYYY-MM-DD') and whereinsert='0'";
					updateList.add(sql);//@更新列表
					insertList2.add(toCoCoContractenergyinfo(energy1[i - 1],
							sellerGen1[i - 1], sellerEnergy1[i - 1],
							purchaserGen1[i - 1], purchaserEnergy1[i - 1],
							contractid, startdate, enddate));
				} else {
					//				if(!ToolsSys.isnull(energy1[i-1]) && !ToolsSys.isnull(purchaserGen1[i-1]) && !ToolsSys.isnull(purchaserEnergy1[i-1]) && !ToolsSys.isnull(sellerGen1[i-1]) && !ToolsSys.isnull(sellerEnergy1[i-1]) ){
					//					
					//				}else{
					insertList.add(toCoCoContractenergyinfo(energy1[i - 1],
							sellerGen1[i - 1], sellerEnergy1[i - 1],
							purchaserGen1[i - 1], purchaserEnergy1[i - 1],
							contractid, startdate, enddate));
					insertList2.add(toCoCoContractenergyinfo(energy1[i - 1],
							sellerGen1[i - 1], sellerEnergy1[i - 1],
							purchaserGen1[i - 1], purchaserEnergy1[i - 1],
							contractid, startdate, enddate));
					//					String sql = "insert into co_contractenergyinfo(guid,contractid,startdate,enddate,purchasergen,purchaserenergy, sellergen, sellerenergy,energy,whereinsert) values((select sys_guid() from dual),'"+contractid+"',to_date('"+startdate+"','yyyy-mm-dd'),to_date('"+enddate+"','yyyy-mm-dd'),'"+purchaserGen1[i-1]+"','"+purchaserEnergy1[i-1]+"','"+sellerGen1[i-1]+"','"+sellerEnergy1[i-1]+"','"+ energy1[i-1] +"',0)";
					//					insertList.add(sql);//@插入列表
					//				}
				}
			}
			int num = bizc.updateBySqlList(updateList, insertList, insertList2);//@更新信息
			if (num == (emonth - smonth + 1)) {
				bizc.zxcs(request, contractid);
				return WrappedResult.successWrapedResult("保存成功");
			} else {
				return WrappedResult.successWrapedResult("保存失败");
			}
		} catch (Exception e) {
			throw new RestClientException("程序异常！");
		}
	}
	
	public CoContractenergyinfo toCoCoContractenergyinfo(String energys,String sellergens,
			String sellerenergys,String purchasergens,String purchaserenergys,
			String contractid,String sdate,String edate){
			CoContractenergyinfo c=new CoContractenergyinfo();
			c.setContractid(contractid);
			c.setStartdate(DateUtil.getUtilDate(sdate,"yyyy-MM-dd"));
			c.setEnddate(DateUtil.getUtilDate(edate,"yyyy-MM-dd"));
			c.setPeriod(new BigDecimal(1));
			if(!"".equals(energys)){
				c.setEnergy(new BigDecimal(energys));
			}
			if(!"".equals(sellergens)){
				c.setSellergen(new BigDecimal(sellergens));
			}
			if(!"".equals(sellerenergys)){
				c.setSellerenergy(new BigDecimal(sellerenergys));
			}
			if(!"".equals(purchasergens)){
				c.setPurchasergen(new BigDecimal(purchasergens));
			}
			if(!"".equals(purchaserenergys)){
				c.setPurchaserenergy(new BigDecimal(purchaserenergys));
			}
			//别删
			c.setWhereinsert("0");
		return c;
	}
	/**
	 * 某年某月有多少天
	 * @description 方法描述
	 * @param year
	 * @param month
	 * @return
	 * @author zhangyan
	 * @date 2014-02-08
	 */
	public static int yearMonthDays(int year,int month){
		int yearMonthDays=0;
		switch(month){
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			yearMonthDays=31;
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			yearMonthDays=30;
			break;
		case 2:
			if(isLeapYear(year)){
				yearMonthDays=29;
			}else{
				yearMonthDays=28;
			}
			break;
		}
		return yearMonthDays;
	}
	/**
	 * 某年是否为润年
	 * @description 方法描述
	 * @param year
	 * @author zhangyan
	 * @date 2014-02-08
	 */
	static boolean isLeapYear(int year){
		return (year%4==0&&year%100!=0||year%400==0);
	}
	/**
	 * 根据给定的年月日返回DATE类型的日期
	 * @description 方法描述
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 * @author zhangyan
	 * @date 2014-02-08
	 */
	public static String getNewDate(String year, String month, String day){
		String StrNewDate = year + "-" + month + "-" + day;
		return StrNewDate;
	}
	/**
	 * 注入逻辑构件
	 * @param bizc
	 */
	public void setContResoByMonthBizc(IContResoByMonthBizc bizc){
		this.bizc=bizc;
	}
	
	/**
	 * 从vo中获取页面展示元数据信息
	 * 
	 * @param columns
	 *            将请求参数{columns:["id","name"]}封装为字符串数组
	 * @return datas
	 */
	@RequestMapping("/meta")
	public @ColumnResponseBody
	List<ViewAttributeData> getMetaData(
			@ColumnRequestParam("params") String[] columns) {
		List<ViewAttributeData> datas = null;
		datas = ViewAttributeUtils.getViewAttributes(columns, CoContractbaseinfoVO.class);
		return datas;
	}
}
