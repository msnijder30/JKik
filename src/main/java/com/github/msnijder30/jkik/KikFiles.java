package com.github.msnijder30.jkik;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

import lombok.Getter;

public class KikFiles {
	
	@Getter
	private HashMap<UUID, File> files = new HashMap<UUID, File>();
	
	@Getter
	private KikApi api;
	public KikFiles(KikApi api) {
		this.api = api;
	}
	
	public UUID addFile(File file) {
		UUID uuid = UUID.randomUUID();
		while(files.containsKey(uuid)) uuid = UUID.randomUUID();
		files.put(uuid, file);
		return uuid;
	}
	
	public File getFile(UUID uuid) {
		return files.get(uuid);
	}
	
	public void removeFile(UUID uuid) {
		files.remove(uuid);
	}
	
	public String getPublicFileUrl(UUID uuid) {
		return "http://" + Utils.getPublicIP() + ":" + api.getPort() + "/" + uuid.toString();
	}

}
