package com.xindong.api.dao.impl;

import java.util.List;


import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.xindong.api.dao.AddressDao;
import com.xindong.api.domain.Address;
import com.xindong.api.domain.query.AddressQuery;
import com.xindong.api.domain.vo.AddressCounty;
@SuppressWarnings("unchecked")
@Repository(value="addressDao")
public class AddressDaoImpl extends SqlMapClientTemplate implements AddressDao {

	@Override
	public Integer insert(Address address) {
		return (Integer) insert("Address.insert", address);
	}

	@Override
	public void modify(Address address) {
		update("Address.updateByPrimaryKey", address);
	}

	@Override
	public Address selectByAddressId(Integer addressId) {
		return (Address) queryForObject("Address.selectByPrimaryKey", addressId);
	}

	@Override
	public Integer countByCondition(AddressQuery addressQuery) {
		return (Integer) queryForObject("Address.countByCondition", addressQuery);
	}

	@Override
	public List<Address> selectByCondition(AddressQuery addressQuery) {
		return (List<Address>)queryForList("Address.selectByCondition",addressQuery);
	}

	@Override
	public List<AddressCounty> selectByAddressCounty(AddressQuery addressQuery) {
		return (List<AddressCounty>)queryForList("Address.selectByAddressCounty",addressQuery);
	}

}
