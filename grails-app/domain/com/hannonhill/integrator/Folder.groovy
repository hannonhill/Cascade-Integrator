package com.hannonhill.integrator

import com.hannonhill.www.ws.ns.AssetOperationService.Site as CascadeSite
import com.hannonhill.www.ws.ns.AssetOperationService.Folder as CascadeFolder

class Folder {
	
	def authenticationService
	def folderService
	
	static belongsTo = [site: Site]
	static transients = ["authorization", "handler"]
	
	String name
	
	//transients
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
	
	protected CascadeFolder[] createRemoteFolder(CascadeSite site, String parentFolder) {
		this.setUpWS()
		Folder folder = folderService.createRemoteFolder(site, this, parentFolder)
		return folder
	}
}
