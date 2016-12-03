package com.smt.dao;

import com.smt.domain.AdminConfigDetails;
import com.smt.utils.UserPOJO;



public interface GlobalDAO {

	void saveAdminConfigDetails(AdminConfigDetails adminConfigDetails);

	AdminConfigDetails getMailConfigData(UserPOJO userPOJO);

	
}
