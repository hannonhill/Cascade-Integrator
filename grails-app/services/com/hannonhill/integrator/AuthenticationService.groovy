package com.hannonhill.integrator

import com.hannonhill.www.ws.ns.AssetOperationService.Authentication
import com.hannonhill.www.ws.ns.AssetOperationService.AssetOperationHandler
import com.hannonhill.www.ws.ns.AssetOperationService.AssetOperationHandlerServiceLocator

class AuthenticationService {
	
	static transactional = true
	
	Authentication authentication
	AssetOperationHandler handler
	
	Authentication getAuthentication(user, password) { //decided to remove the username and password parameters in favor of a single hardcoded pair
		if(this.authentication == null) {
			Authentication authentication = new Authentication()
			authentication.setUsername(user)
			authentication.setPassword(password)
			this.authentication = authentication
		}
		
		return this.authentication
	}
	
	AssetOperationHandler getHandler(endpoint) {
		if(this.handler == null) {
			AssetOperationHandlerServiceLocator serviceLocator = new AssetOperationHandlerServiceLocator()
			endpoint = endpoint + "/ws/services/AssetOperationService"
			serviceLocator.setAssetOperationServiceEndpointAddress(endpoint)
			AssetOperationHandler handler = serviceLocator.getAssetOperationService()
			this.handler = handler
		}		
		
		return this.handler
	}
}
