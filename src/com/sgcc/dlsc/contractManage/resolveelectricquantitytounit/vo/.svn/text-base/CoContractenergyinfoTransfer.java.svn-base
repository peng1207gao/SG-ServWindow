package com.sgcc.dlsc.contractManage.resolveelectricquantitytounit.vo;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.sgcc.dlsc.entity.po.CoBusigeneratorqty;
import com.sgcc.dlsc.entity.po.CoContractenergyinfo;

public class CoContractenergyinfoTransfer {

	public static ArrayList<CoBusigeneratorqty> energyinfo2BuisGneratorQTY(
			CoBusiGeneratorQtyVO vo, String marketid) {
		ArrayList<CoBusigeneratorqty> result = new ArrayList<CoBusigeneratorqty>();

		// for(int i = 0; i < 5; i++){//循环5次，分别记录 尖峰平谷总电量

		if (vo.getTotalValue() != null && !"".equals(vo.getTotalValue())) {// 总电量
			result.add(getQTYEntity(vo, marketid, new BigDecimal(1),
					new BigDecimal(vo.getTotalValue()),
					new BigDecimal(vo.getTotalValue())));
		}
		if (vo.getPeakValue() != null && !"".equals(vo.getPeakValue())) {// 尖电量
			result.add(getQTYEntity(vo, marketid, new BigDecimal(2),
					new BigDecimal(vo.getPeakValue()),
					new BigDecimal(vo.getPeakValue())));
		}
		if (vo.getTopValue() != null && !"".equals(vo.getTopValue())) {// 峰电量
			result.add(getQTYEntity(vo, marketid, new BigDecimal(3),
					new BigDecimal(vo.getTopValue()),
					new BigDecimal(vo.getTopValue())));
		}
		if (vo.getFlatValue() != null && !"".equals(vo.getFlatValue())) {// 平电量
			result.add(getQTYEntity(vo, marketid, new BigDecimal(4),
					new BigDecimal(vo.getFlatValue()),
					new BigDecimal(vo.getFlatValue())));
		}
		if (vo.getValleyValue() != null && !"".equals(vo.getValleyValue())) {// 谷电量
			result.add(getQTYEntity(vo, marketid, new BigDecimal(5),
					new BigDecimal(vo.getValleyValue()),
					new BigDecimal(vo.getValleyValue())));
		}
		// }
		return result;
	}

	private static CoBusigeneratorqty getQTYEntity(CoBusiGeneratorQtyVO vo,
			String marketid, BigDecimal period, BigDecimal genqty,
			BigDecimal netqty) {
		CoBusigeneratorqty qty = new CoBusigeneratorqty();
		qty.setContractid(vo.getContractid());
		qty.setMarketid(marketid);
		qty.setPurchaseunit(vo.getPurchaseunitid());
		qty.setSellerunit(vo.getSellerunitid());
		qty.setTimeno(vo.getTimeno());
		qty.setStartdate(vo.getStartdate());
		qty.setEnddate(vo.getEnddate());
		qty.setPeriod(period);
		qty.setGenqty(genqty);
		qty.setNetqty(netqty);
		return qty;
	}

	public static CoContractenergyinfo toPO(CoContractenergyinfoVO vo) {
		CoContractenergyinfo coContractenergyinfo = new CoContractenergyinfo();
		if (vo != null) {
			coContractenergyinfo.setContractid(vo.getContractid());
			coContractenergyinfo.setStartdate(vo.getStartdate());
			coContractenergyinfo.setEnddate(vo.getEnddate());
			coContractenergyinfo.setPeriod(vo.getPeriod());
			coContractenergyinfo.setPurchasergen(vo.getPurchasergen());
			coContractenergyinfo.setFee(vo.getFee());
			coContractenergyinfo.setEnergy(vo.getEnergy());
			coContractenergyinfo.setQtytype(vo.getQtytype());
			coContractenergyinfo.setTimeno(vo.getTimeno());
			coContractenergyinfo.setPurchaserenergy(vo.getPurchaserenergy());
			coContractenergyinfo.setPurchaserprice(vo.getPurchaserprice());
			coContractenergyinfo.setPurisincludetax(vo.getPurisincludetax());
			coContractenergyinfo.setSellergen(vo.getSellergen());
			coContractenergyinfo.setSellerenergy(vo.getSellerenergy());
			coContractenergyinfo.setSellerprice(vo.getSellerprice());
			coContractenergyinfo.setSellerisincludetax(vo
					.getSellerisincludetax());
			coContractenergyinfo.setPrice(vo.getPrice());
			coContractenergyinfo.setGuid(vo.getGuid());
			coContractenergyinfo.setPurisincludetp(vo.getPurisincludetp());
			coContractenergyinfo
					.setSellerisincludetp(vo.getSellerisincludetp());
			coContractenergyinfo.setPricetax(vo.getPricetax());
			coContractenergyinfo.setPricetp(vo.getPricetp());
			coContractenergyinfo.setWhereinsert(vo.getWhereinsert());
		}
		return coContractenergyinfo;
	}

	public static CoContractenergyinfoVO toVO(CoContractenergyinfo po) {
		CoContractenergyinfoVO coContractenergyinfolistVO = new CoContractenergyinfoVO();

		coContractenergyinfolistVO.setContractid(po.getContractid());
		coContractenergyinfolistVO.setStartdate(po.getStartdate());
		coContractenergyinfolistVO.setEnddate(po.getEnddate());
		coContractenergyinfolistVO.setPeriod(po.getPeriod());
		coContractenergyinfolistVO.setPurchasergen(po.getPurchasergen());
		coContractenergyinfolistVO.setFee(po.getFee());
		coContractenergyinfolistVO.setEnergy(po.getEnergy());
		coContractenergyinfolistVO.setQtytype(po.getQtytype());
		coContractenergyinfolistVO.setTimeno(po.getTimeno());
		coContractenergyinfolistVO.setPurchaserenergy(po.getPurchaserenergy());
		coContractenergyinfolistVO.setPurchaserprice(po.getPurchaserprice());
		coContractenergyinfolistVO.setPurisincludetax(po.getPurisincludetax());
		coContractenergyinfolistVO.setSellergen(po.getSellergen());
		coContractenergyinfolistVO.setSellerenergy(po.getSellerenergy());
		coContractenergyinfolistVO.setSellerprice(po.getSellerprice());
		coContractenergyinfolistVO.setSellerisincludetax(po
				.getSellerisincludetax());
		coContractenergyinfolistVO.setPrice(po.getPrice());
		coContractenergyinfolistVO.setGuid(po.getGuid());
		coContractenergyinfolistVO.setPurisincludetp(po.getPurisincludetp());
		coContractenergyinfolistVO.setSellerisincludetp(po
				.getSellerisincludetp());
		coContractenergyinfolistVO.setPricetax(po.getPricetax());
		coContractenergyinfolistVO.setPricetp(po.getPricetp());
		coContractenergyinfolistVO.setWhereinsert(po.getWhereinsert());
		return coContractenergyinfolistVO;
	}
}
