package com.ars.serviceImpl;

import javax.persistence.PersistenceException;

import org.modelmapper.ModelMapper;

import com.ars.dao.AdminDao;
import com.ars.daoImpl.AdminDaoImpl;
import com.ars.entity.Admin;
import com.ars.exception.GlobalException;
import com.ars.model.AdminDTO;
import com.ars.service.AdminService;

public class AdminServiceImpl implements AdminService{
	AdminDao aDao=new AdminDaoImpl();
	@Override
	public void registerAdmin(Admin admin)  // for register admin
	{
		aDao.registerAdmin(admin);
		
	}

	@Override
	public boolean loginAdmin(String userName, String password) // for admin login
	{
		
		return aDao.loginAdmin(userName, password);
	}
	
			//method for get admin  in service layer
			@Override
			public AdminDTO getAdminById(int id) {
				Admin admin	=aDao.getAdminById(id);
				if(admin!=null)
				{
					
					return new ModelMapper().map(admin, AdminDTO.class); //converting entity to DTO
				}
					throw new GlobalException("Admin details does not exist!!");
			}
			//method for delete admin  in service layer
			@Override
			public void deleteAdmin(int id) throws PersistenceException {
				
				aDao.deleteAdmin(id);
				
			}
			//method for update admin  in service layer
			@Override
			public AdminDTO updateAdmin(int id, Admin admin) {
				Admin a=aDao.updateAdmin(id, admin);
				
				return new ModelMapper().map(a, AdminDTO.class); //converting entity to DTO
			}

}
