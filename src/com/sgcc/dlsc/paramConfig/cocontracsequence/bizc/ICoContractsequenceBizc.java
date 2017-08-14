package com.sgcc.dlsc.paramConfig.cocontracsequence.bizc;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.List;

import com.sgcc.dlsc.paramConfig.cocontracsequence.vo.CoContractSequenceinfoVO;
import com.sgcc.uap.rest.support.DicItems;
import com.sgcc.uap.rest.support.IDRequestObject;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

/**
 * 
 * @author djdeng
 *
 */
public interface ICoContractsequenceBizc {

	QueryResultObject query(RequestCondition params) throws IllegalAccessException, InvocationTargetException, ParseException;

	QueryResultObject queryById(String id,String name);

	String remove(String []strs);

	DicItems getGencodeByType(String type);
	
	public List<DicItems> getGenCodeByType(String type,String fieldName);
	
	public void saveOrUpdate(CoContractSequenceinfoVO vo);
	
	/**
	 * 查询根节点
	 * @param clazz
	 * @param pidName
	 * @return list
	 */
	public List listRoot(Class clazz,String pidName,String marketId);
	/**
	 * 判断是否有子节点
	 * @param pid
	 * @param childClazz
	 * @param pidName
	 * @return boolean
	 */
	public boolean hasChild(String pid,Class childClazz,String pidName,String marketId);
	/**
	 * 查询节点
	 * @param clazz
	 * @param nodeValue
	 * @param pidName
	 * @return list
	 */
	public List listNode(Class clazz,String nodeValue,String pidName,String marketId);
}
