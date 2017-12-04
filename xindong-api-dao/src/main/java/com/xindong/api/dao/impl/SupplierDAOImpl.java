package com.xindong.api.dao.impl;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.xindong.api.dao.SupplierDAO;
import com.xindong.api.domain.Supplier;
@Repository(value="supplierDao")
public class SupplierDAOImpl extends SqlMapClientTemplate implements SupplierDAO {

    public SupplierDAOImpl() {
        super();
    }

    public Integer insert(Supplier record) {
    	return (Integer) insert("supplier.insert", record);
    }

    public int updateByPrimaryKey(Supplier record) {
        int rows = update("supplier.updateByPrimaryKey", record);
        return rows;
    }

    public Supplier selectByPrimaryKey(Integer supplierId) {
        Supplier key = new Supplier();
        key.setSupplierId(supplierId);
        Supplier record = (Supplier) queryForObject("supplier.selectByPrimaryKey", key);
        return record;
    }

    public int deleteByPrimaryKey(Integer supplierId) {
        Supplier key = new Supplier();
        key.setSupplierId(supplierId);
        int rows = delete("supplier.deleteByPrimaryKey", key);
        return rows;
    }

}