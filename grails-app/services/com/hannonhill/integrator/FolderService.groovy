package com.hannonhill.integrator

import com.hannonhill.www.ws.ns.AssetOperationService.Asset
import com.hannonhill.www.ws.ns.AssetOperationService.AssetFactoryContainer
import com.hannonhill.www.ws.ns.AssetOperationService.EntityTypeString
import com.hannonhill.www.ws.ns.AssetOperationService.Read
import com.hannonhill.www.ws.ns.AssetOperationService.ReadResult
import com.hannonhill.www.ws.ns.AssetOperationService.Identifier
import com.hannonhill.www.ws.ns.AssetOperationService.Folder as CascadeFolder
import com.hannonhill.www.ws.ns.AssetOperationService.Site as CascadeSite

class FolderService {
	
	static transactional = true
	
	Folder folder
	
	CascadeFolder createRemoteFolder(CascadeSite site, Folder folder, String parentFolderId) {
		this.folder = folder
		CascadeFolder csFolder = new CascadeFolder()
		csFolder.setSiteId(site.getId())
		csFolder.setName(this.folder.name)
		csFolder.setParentFolderId(parentFolderId)
		
		Asset asset = new Asset()
		asset.setFolder(csFolder)
		
		String wsId = this.folder.handler.create(this.folder.authorization, asset).getCreatedAssetId()
		
		CascadeFolder remoteFolder = this.readRemoteFolder(wsId)
		
		return remoteFolder
	}
	
	private CascadeFolder readRemoteFolder(String id) {
		
		Asset a = this.readRemoteAsset(id, EntityTypeString.folder)
		
		CascadeFolder f = a.getFolder()
		
		return f
	}
	
	private Asset readRemoteAsset(String id, EntityTypeString type) {
		Identifier toRead = new Identifier();
		toRead.setId(id);
		toRead.setType(type);
		
		Read read = new Read();
		read.setIdentifier(toRead);
		
		ReadResult result = this.folder.handler.read(this.folder.authorization, toRead);
		Asset a = result.getAsset()
		
		return a
	}

}
