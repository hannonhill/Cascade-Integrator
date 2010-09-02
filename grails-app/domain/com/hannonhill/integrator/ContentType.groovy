package com.hannonhill.integrator

class ContentType {
	
	def authenticationService
	def contentTypeService
	
	static belongsTo = [site: Site]
	static transients = ["body", "authorization", "handler"]
	
	String name
	String wsId
	
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
	
	void createRemoteContentType(com.hannonhill.www.ws.ns.AssetOperationService.Site site) {
		this.setBody()
		this.setUpWS()
		contentTypeService.createRemoteContentType(site, this)
	}
	
	void setBody() {
		this.body = "<html><head></head><body><system-region name=\"DEFAULT\"/></body></html>"
	}
}
