package com.hannonhill.integrator

import com.hannonhill.www.ws.ns.AssetOperationService.Authentication
import com.hannonhill.www.ws.ns.AssetOperationService.AssetOperationHandler
import com.hannonhill.www.ws.ns.AssetOperationService.AssetOperationHandlerServiceLocator

class AuthenticationService {

    static transactional = true
	
    Authentication getAuthentication() { //decided to remove the username and password parameters in favor of a single hardcoded pair
		Authentication authentication = new Authentication()
		authentication.setUsername("admin")
		authentication.setPassword("admin")

		return authentication
	}

	AssetOperationHandler getHandler() {
		AssetOperationHandlerServiceLocator serviceLocator = new AssetOperationHandlerServiceLocator()
		AssetOperationHandler handler = serviceLocator.getAssetOperationService()

		return handler
	}
	
	
}
