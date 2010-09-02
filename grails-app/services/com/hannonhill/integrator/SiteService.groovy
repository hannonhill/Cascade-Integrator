package com.hannonhill.integrator

import com.hannonhill.www.ws.ns.AssetOperationService.CreateResult
import com.hannonhill.www.ws.ns.AssetOperationService.RecycleBinExpiration
import com.hannonhill.www.ws.ns.AssetOperationService.Asset
import com.hannonhill.www.ws.ns.AssetOperationService.Read
import com.hannonhill.www.ws.ns.AssetOperationService.ReadResult
import com.hannonhill.www.ws.ns.AssetOperationService.Identifier
import com.hannonhill.www.ws.ns.AssetOperationService.EntityTypeString
import com.hannonhill.www.ws.ns.AssetOperationService.Site as CascadeSite

class SiteService {
	
	Site localSite
	
	static transactional = true
	
	CascadeSite createRemoteSite(Site localSite) {
		this.localSite = localSite
		CascadeSite site = new CascadeSite()
		site.setUrl(this.localSite.name)
		site.setName(this.localSite.name)
		site.setRecycleBinExpiration(new RecycleBinExpiration().value2)
		
		Asset asset = new Asset()
		asset.setSite(site)
		
		String wsId = this.localSite.handler.create(this.localSite.authorization, asset).getCreatedAssetId()
		
		CascadeSite remoteSite = this.readRemoteSite(wsId)
		
		return remoteSite
	}
	
	CascadeSite readRemoteSite(String id) {
		Identifier toRead = new Identifier();
		toRead.setId(id);
		toRead.setType(EntityTypeString.site);
		
		Read read = new Read();
		read.setIdentifier(toRead);
		
		ReadResult result = this.localSite.handler.read(this.localSite.authorization, toRead);
		Asset a = result.getAsset()
		CascadeSite s = a.getSite()
		
		return s
	}
}
