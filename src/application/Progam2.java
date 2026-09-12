package application;

import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;


public class Progam2 {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
	
		
		System.out.println("\n== test 1: findById ==");
		Department department = departmentDao.findById(1);
		System.out.println(department);
		
		
		System.out.println("\n== test 2: findAll ==");
		List<Department> list = departmentDao.findAll();
		for(Department dep : list) {
			System.out.println(dep);
		}
		
		
		System.out.println("\n== test 3: insert ==");
		Department newDepartment = new Department(null, "computers");
		departmentDao.insert(newDepartment);
		System.out.println("Inserted! New id = " + newDepartment.getId());
		
		
		System.out.println("\n== test 4: update ==");
		department = departmentDao.findById(1);
		department.setName("pc");
		departmentDao.update(department);
		System.out.println("Update completed");
		
		
		System.out.println("\n== test 5: delete ==");
		System.out.println("Enter id for delete test");
		int id = sc.nextInt();
		departmentDao.deleteById(id);
		System.out.println("delet completed");
		
		sc.close();
	}
}
