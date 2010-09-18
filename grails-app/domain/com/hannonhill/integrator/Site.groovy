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
	String username
	String password
	InternalCmsAssets internalCmsAssets
	
	String toString() {
		"$name"
	}
	
	static constraints = {
		wsId(nullable: true)
		internalCmsAssets(nullable: true)
	}
	
	void setUpWS() {
		this.authorization = authenticationService.getAuthentication(this.username, this.password)
		this.handler = authenticationService.getHandler(this.endpoint)
	}
	
	CascadeSite createRemoteSite() {
		this.setUpWS()
		siteService.createRemoteSite(this)
	}
}
