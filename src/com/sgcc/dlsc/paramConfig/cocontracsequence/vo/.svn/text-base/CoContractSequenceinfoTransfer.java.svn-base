package com.sgcc.dlsc.paramConfig.cocontracsequence.vo;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.sgcc.dlsc.commons.util.DateUtil;
import com.sgcc.dlsc.entity.po.CoContractSequenceinfo;


public class CoContractSequenceinfoTransfer {

	public static CoContractSequenceinfo toPO(CoContractSequenceinfoVO vo) {
		CoContractSequenceinfo coContractSequenceinfo = new CoContractSequenceinfo();
		if(vo != null){
	         coContractSequenceinfo.setSequenceid(vo.getSequenceid());
	         coContractSequenceinfo.setSequencename(vo.getSequencename());
	         coContractSequenceinfo.setSequencetypeStr(vo.getSequencetypeStr());
	         if(vo.getSequencetypeStr()!=null&&!"".equals(vo.getSequencetypeStr())){
	        	 coContractSequenceinfo.setSequencetype(new BigDecimal(vo.getSequencetypeStr()));
	         }
	         coContractSequenceinfo.setMarketid(vo.getMarketid());
	         coContractSequenceinfo.setUpdatetime(vo.getUpdatetime());
	         coContractSequenceinfo.setUpdatepersonid(vo.getUpdatepersonid());
	         coContractSequenceinfo.setSequencecircleStr(vo.getSequencecircleStr());
	         coContractSequenceinfo.setMaintenancePer(vo.getMaintenancePer());
	         coContractSequenceinfo.setContractType(vo.getContractType());
	         coContractSequenceinfo.setUpdatepersonid(vo.getUpdatepersonid());
	         if(vo.getSequencecircleStr()!=null&&!"".equals(vo.getSequencecircleStr())){
	        	 coContractSequenceinfo.setSequencecircle(new BigDecimal(vo.getSequencecircleStr()));
	         }
	         coContractSequenceinfo.setCreatedate(vo.getCreatedate());
	    }
		return coContractSequenceinfo;
	}

	public static CoContractSequenceinfoVO toVO(CoContractSequenceinfo po) {
		CoContractSequenceinfoVO coContractSequenceinfolistVO = new CoContractSequenceinfoVO();

	    coContractSequenceinfolistVO.setSequenceid(po.getSequenceid());
	    coContractSequenceinfolistVO.setSequencename(po.getSequencename());
	    coContractSequenceinfolistVO.setSequencetype(po.getSequencetype());
	    coContractSequenceinfolistVO.setSequencetypeStr(po.getSequencetype()+"");
	    coContractSequenceinfolistVO.setMarketid(po.getMarketid());
	    coContractSequenceinfolistVO.setUpdatetime(po.getUpdatetime());
	    coContractSequenceinfolistVO.setUpdatepersonid(po.getUpdatepersonid());
	    coContractSequenceinfolistVO.setSequencecircle(po.getSequencecircle());
	    coContractSequenceinfolistVO.setSequencecircleStr(po.getSequencecircle()+"");
	    coContractSequenceinfolistVO.setCreatedate(po.getCreatedate());
	    coContractSequenceinfolistVO.setMaintenancePer(po.getMaintenancePer());
	    coContractSequenceinfolistVO.setContractType(po.getContractType());
	    coContractSequenceinfolistVO.setUpdatepersonid(po.getUpdatepersonid());
	    coContractSequenceinfolistVO.setContractTypeName(po.getContractTypeName());
		return coContractSequenceinfolistVO;
	}
	
	public static List<CoContractSequenceinfoVO> toVO(List<Object[]> pos) throws IllegalAccessException, InvocationTargetException, ParseException {
		List<CoContractSequenceinfoVO> vos = new ArrayList<CoContractSequenceinfoVO>();
		for(int i=0;i<pos.size();i++){
			vos.add(toVO(pos.get(i)));
		}
		return vos;
	}
	
	public static CoContractSequenceinfoVO toVO(Object[] po) throws ParseException, IllegalAccessException, InvocationTargetException {
		if (po == null) {
			return null;
		}else{
			CoContractSequenceinfoVO  cv=new CoContractSequenceinfoVO();
			cv.setSequenceid(po[0]==null?"":po[0].toString());	//合同序列编号
			cv.setSequencename(po[1]==null?"":po[1].toString());  //合同序列名称
			SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if( po[2]!=null&&!"".equals(po[2])){
				Date parse = sd.parse(po[2].toString());  //合同序列创建时间
				cv.setCreatedate(parse);	
			}
			if(po[3]!=null&&!"".equals(po[3].toString())){
//				cv.setSequencetype(new BigDecimal(po[3].toString()));
			}
			cv.setSequencetypeStr(po[3]==null?"":po[3].toString());  //合同序列类型
			cv.setUpdatepersonid(po[4]==null?"":po[4].toString());  //维护人
			if( po[5]!=null&&!"".equals(po[5])){
				Date parse1 = sd.parse(po[5]==null?"":po[5].toString()); //维护时间
				cv.setUpdatetime(parse1);	
			}
			
			if(po[5]!=null&&!"".equals(po[5].toString())){
//				cv.setSequencecircle(new BigDecimal(po[5].toString()));
			}
			cv.setSequencecircleStr(po[6]==null?"":po[6].toString());  //合同序列周期
			cv.setContractType(po[7]==null?"":po[7].toString());  //合同类型
			return cv;
		}
	}
}
