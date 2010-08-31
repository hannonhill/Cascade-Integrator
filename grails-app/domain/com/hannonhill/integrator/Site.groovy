package com.hannonhill.integrator

import com.hannonhill.www.ws.ns.AssetOperationService.CreateResult
import com.hannonhill.www.ws.ns.AssetOperationService.RecycleBinExpiration
import com.hannonhill.www.ws.ns.AssetOperationService.Asset

class Site {
	
	def authenticationService
	
	static hasMany = [templates: Template]
	
	String name
	String wsId
	
	String toString() {
		"$name"
	}
	
	static constraints = {
		wsId(nullable: true)
	}
	
	com.hannonhill.www.ws.ns.AssetOperationService.Site createRemoteSite() {
		com.hannonhill.www.ws.ns.AssetOperationService.Site site = new com.hannonhill.www.ws.ns.AssetOperationService.Site()
		site.setUrl(this.name)
		site.setName(this.name)
		site.setRecycleBinExpiration(new RecycleBinExpiration().value2)
		
		Asset asset = new Asset()
		asset.setSite(site)
		
		def authorization = authenticationService.getAuthentication()
		def handler = authenticationService.getHandler()
		def siteId = handler.create(authorization, asset)
		
		this.wsId = siteId
		
		return site
	}
	
}
