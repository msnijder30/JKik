package com.github.msnijder30.jkik;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lombok.Getter;

public class KikFiles {

	private static final Logger logger = LogManager.getLogger(KikFiles.class);

	@Getter
	private HashMap<UUID, File> files = new HashMap<>();

	@Getter
	private KikApi api;

	public KikFiles(KikApi api) {
		this.api = api;
	}

	public UUID addFile(File file) {
		UUID uuid = UUID.randomUUID();
		while (files.containsKey(uuid)) {
			uuid = UUID.randomUUID();
		}
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
		// FIXME this should throw an error, not return null...
		try {
			return "http://" + Utils.getPublicIP() + ":" + api.getPort() + "/" + uuid.toString();
		} catch (IOException e) {
			logger.error("Failed to get public file url", e);
			return null;
		}
	}

}
