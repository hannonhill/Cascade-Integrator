package com.hannonhill.integrator

import com.hannonhill.www.ws.ns.AssetOperationService.Site as CascadeSite

class Site {
	
	def authenticationService
	def siteService
	
	def authorization
	def handler
	
	static hasMany = [contentTypes: ContentType, folders: Folder]
	
	String name
	String wsId
	String endpoint
	InternalCmsAssets internalCmsAssets
	
	String toString() {
		"$name"
	}
	
	static constraints = {
		wsId(nullable: true)
		internalCmsAssets(nullable: true)
	}
	
	void setUpWS(endpoint) {
		this.authorization = authenticationService.getAuthentication()
		this.handler = authenticationService.getHandler(endpoint)
	}
	
	CascadeSite createRemoteSite() {
		this.setUpWS(endpoint)
		siteService.createRemoteSite(this)
	}
}
