package com.hannonhill.integrator

import com.hannonhill.www.ws.ns.AssetOperationService.Site as CascadeSite
import com.hannonhill.www.ws.ns.AssetOperationService.Folder as CascadeFolder

class ContentType {
	
	def authenticationService
	def contentTypeService
	
	static belongsTo = [site: Site]
	static transients = ["body", "authorization", "handler"]
	
	String name
	
	//transients
	String body
	def authorization
	def handler
	
	String toString() {
		"$name"
	}
	
	void setUpWS() {
		this.authorization = authenticationService.getAuthentication()
		this.handler = authenticationService.getHandler()
	}
	
	static constraints = {
		wsId(nullable: true)
	}
	
	void createRemoteContentType(CascadeSite site, CascadeFolder[] folders) {
		this.setBody()
		this.setUpWS()
		contentTypeService.createRemoteContentType(site, this, folders)
	}
	
	void setBody() {
		this.body = "<html><head></head><body><system-region name=\"DEFAULT\"/></body></html>"
	}
}
