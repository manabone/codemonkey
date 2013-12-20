package com.codemonkey.ibatis.service;

import java.util.List;
import java.util.Map;

/**
 * 类描述：Ibatis业务类
 */
public interface IbatisService {

	/**
	  * 方法描述：查询
	  * @param: sqlId sql标识
	  * @param: queryAndSort 条件排序
	  * @return: 查询结果
	  * @author: wy
	  */
	public List<Map<String, Object>> query(String sqlId, Object queryAndSort);

	public long count(String sqlId, Object queryInfo);

	
	/**
	  * 方法描述：追加
	  * @param: sqlId sql标识
	  * @param: obj 具体对象
	  * @return: void
	  * @author: wy
	  */
	public void insert(String sqlId, Object obj);

	/**
	  * 方法描述：更新
	  * @param: sqlId sql标识
	  * @param: obj 具体对象
	  * @return: void
	  * @author: wy
	  */
	public void update(String sqlId, Object obj);

	/**
	  * 方法描述：删除
	  * @param: sqlId sql标识
	  * @param: obj 具体对象
	  * @return: void
	  * @author: wy
	  */
	public void delete(String sqlId, Object obj);

	/**
	  * 方法描述：查询
	  * @param: sqlId sql标识
	  * @param: obj 具体对象
	  * @return: 查询结果
	  * @author: wy
	  */
	public List<Map<String, Object>> querybylist(String sqlId, Object obj);

	/**
	  * 方法描述：查询
	  * @param: sqlId sql标识
	  * @param: obj 具体对象
	  * @return: 单一结果
	  * @author: wy
	  */
	public Map<String, Object> querybyobject(String sqlId, Object obj);
	
	/**
	  * 方法描述：查询
	  * @param: sqlId sql标识
	  * @return: 查询结果
	  * @author: wy
	  */
	public List<Map<String, Object>> querybylist(String sqlId);
	
	
	/**
	  * 方法描述：查询
	  * @param: sqlId sql标识
	  * @return: 单一结果
	  * @author: wy
	  */
	public Map<String, Object> querybyobject(String sqlId);

}