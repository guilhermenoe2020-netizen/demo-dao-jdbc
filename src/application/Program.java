package application;


import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {		
	
	
		SellerDao sellerDao = DaoFactory.createSellerDao();
		
		System.out.println("== test 1: seller findById ==");
		System.out.println("\n== test 2: seller findByDepartment ==");
		Seller seller = sellerDao.findById(3);
		Department department = new Department(1, null);
		List<Seller> list = sellerDao.findByDepartment(department);
		
		for(Seller obj : list) {
			System.out.println(obj);
		}

	}

}
