package com.hannonhill.integrator

import com.hannonhill.www.ws.ns.AssetOperationService.Site as CascadeSite
import com.hannonhill.www.ws.ns.AssetOperationService.Folder as CascadeFolder
import com.hannonhill.www.ws.ns.AssetOperationService.ContentType as CascadeContentType
import com.hannonhill.www.ws.ns.AssetOperationService.Page

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
	
	CascadeContentType createRemoteContentType(CascadeSite site, CascadeFolder[] folders) {
		this.setBody()
		this.setUpWS()
		CascadeContentType cct = contentTypeService.createRemoteContentType(site, this, folders)
		return cct
	}
	
	Page createRemotePage(CascadeContentType cct, String name, String parentFolderId) {
		Page p = contentTypeService.createRemotePage(cct, name, parentFolderId)
		return p
	}
	
	void setBody() {
		this.body = "<html><head></head><body><system-region name=\"DEFAULT\"/></body></html>"
	}
}
