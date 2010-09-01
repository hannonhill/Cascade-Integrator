package com.hannonhill.integrator

import com.hannonhill.www.ws.ns.AssetOperationService.CreateResult
import com.hannonhill.www.ws.ns.AssetOperationService.RecycleBinExpiration
import com.hannonhill.www.ws.ns.AssetOperationService.Asset
import com.hannonhill.www.ws.ns.AssetOperationService.Read
import com.hannonhill.www.ws.ns.AssetOperationService.ReadResult
import com.hannonhill.www.ws.ns.AssetOperationService.Identifier
import com.hannonhill.www.ws.ns.AssetOperationService.EntityTypeString

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
		this.wsId = handler.create(authorization, asset).getCreatedAssetId()
		
		com.hannonhill.www.ws.ns.AssetOperationService.Site remoteSite = this.readRemoteSite(this.wsId)
		
		return remoteSite
	}
	
	com.hannonhill.www.ws.ns.AssetOperationService.Site readRemoteSite(String id) {
		Identifier toRead = new Identifier();
		toRead.setId(id);
		toRead.setType(EntityTypeString.site);

		Read read = new Read();
		read.setIdentifier(toRead);

		def authorization = authenticationService.getAuthentication()
		def handler = authenticationService.getHandler()
		ReadResult result = handler.read(authorization, toRead);
		Asset a = result.getAsset()
		com.hannonhill.www.ws.ns.AssetOperationService.Site s = a.getSite()
		
		return s
	}
	
}
