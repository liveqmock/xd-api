package com.xindong.api.dao;

import com.xindong.api.domain.Supplier;

public interface SupplierDAO {
	Integer insert(Supplier record);

    int updateByPrimaryKey(Supplier record);

    Supplier selectByPrimaryKey(Integer supplierId);

    int deleteByPrimaryKey(Integer supplierId);

}