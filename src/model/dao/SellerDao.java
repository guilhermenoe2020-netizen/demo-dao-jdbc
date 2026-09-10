package model.dao;

import java.util.List;

import model.entities.Seller;

public interface SellerDao {

	void insert(Seller obj);
	void update(Seller obj);
	void deleById(Integer id);
	Seller findAll(Integer id);
	List<Seller> findAll();
}
