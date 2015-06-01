package com.fclub.tpd.controller;

import com.fclub.tpd.dataobject.Provider;
import com.fclub.tpd.helper.SessionHelper;

public abstract class BaseController {
	
	protected boolean isAdmin() {
		return getProvider().isAdmin();
	}
	
	protected Provider getProvider() {
    	return SessionHelper.getProvider();
    }
    
    protected Integer getProviderId() {
    	return getProvider().getProviderId();
    }
    
    protected Integer getOperaterId() {
    	Provider provider = getProvider();
    	return provider.isAdmin() ? provider.getAdminId() : provider.getProviderId();
    }
    
}
