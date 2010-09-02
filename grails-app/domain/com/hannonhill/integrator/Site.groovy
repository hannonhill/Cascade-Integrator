package com.hannonhill.integrator

import com.hannonhill.www.ws.ns.AssetOperationService.Site as CascadeSite

class Site {
	
	def authenticationService
	def siteService
	
	def authorization
	def handler
	
	static hasMany = [contentTypes: ContentType]
	
	String name
	String wsId
	
	String toString() {
		"$name"
	}
	
	static constraints = {
		wsId(nullable: true)
	}
	
	void setUpWS() {
		this.authorization = authenticationService.getAuthentication()
		this.handler = authenticationService.getHandler()
	}
	
	CascadeSite createRemoteSite() {
		this.setUpWS()
		siteService.createRemoteSite(this)
	}
}
