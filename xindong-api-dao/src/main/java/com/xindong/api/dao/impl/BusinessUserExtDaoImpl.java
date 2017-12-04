package com.xindong.api.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.xindong.api.dao.BusinessUserExtDao;
import com.xindong.api.domain.BusinessUserExt;
import com.xindong.api.domain.query.BusinessUserExtQuery;
@SuppressWarnings("unchecked")
@Repository(value="businessUserExtDao")
public class BusinessUserExtDaoImpl extends SqlMapClientTemplate implements BusinessUserExtDao {

	/** 添加商家信息  */
	@Override
	public Integer insert(BusinessUserExt businessUserExt) {
		return (Integer) insert("BusinessUserExt.insert", businessUserExt);
	}

	/** 依据用户ID修改商家扩展信息 */
	@Override
	public void modifyByUserId(BusinessUserExt businessUserExt) {
		update("BusinessUserExt.updateByUserId", businessUserExt);
	}

	/** 依据用户ID查询信息  */
	@Override
	public BusinessUserExt selectByUserId(int userId) {
		return (BusinessUserExt) queryForObject("BusinessUserExt.selectByUserId",userId);
	}
	
	/** 依据自增ID查询信息  */
	@Override
	public BusinessUserExt selectById(int id) {
		return (BusinessUserExt) queryForObject("BusinessUserExt.selectById",id);
	}

	/** 使用模糊查询，查询出商户数量  */
	@Override
	public int countByCondition(BusinessUserExtQuery businessUserExtQuery) {
		return (Integer)queryForObject("BusinessUserExt.countByCondition", businessUserExtQuery);
	}

	/** 查询全部商户列表  */
	@Override
	public List<BusinessUserExt> selectByCondition(
			BusinessUserExtQuery businessUserExtQuery) {
		return queryForList("BusinessUserExt.selectByCondition",businessUserExtQuery);
	}
	
	/** 查询全部商户列表  */
	@Override
	public List<BusinessUserExt> selectByConditionForPage(
			BusinessUserExtQuery businessUserExtQuery) {
		return queryForList("BusinessUserExt.selectByConditionForPage",businessUserExtQuery);
	}

}
