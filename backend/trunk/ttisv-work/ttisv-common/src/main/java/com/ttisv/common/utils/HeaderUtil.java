package com.ttisv.common.utils;

import org.springframework.http.HttpHeaders;

public class HeaderUtil {
	private static final String APPLICATION_NAME = "TTISV";

	private HeaderUtil() {
	}

	public static HttpHeaders createAlert(String message, String param) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-ttisv-alert", message);
		headers.add("X-ttisv-params", param);
		return headers;
	}

	public static HttpHeaders createEntityCreationAlert(String entityName, String param) {
		return createAlert(APPLICATION_NAME + "." + entityName + ".created", param);
	}

	public static HttpHeaders createEntityUpdateAlert(String entityName, String param) {
		return createAlert(APPLICATION_NAME + "." + entityName + ".updated", param);
	}

	public static HttpHeaders createEntityDeletionAlert(String entityName, String param) {
		return createAlert(APPLICATION_NAME + "." + entityName + ".deleted", param);
	}

	public static HttpHeaders createFailureAlert(String entityName, String errorKey, String defaultMessage) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-ttisv-error", "error." + errorKey);
		headers.add("X-ttisv--params", entityName);
		return headers;
	}
}
