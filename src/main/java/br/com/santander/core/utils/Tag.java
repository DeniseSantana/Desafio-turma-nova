package br.com.santander.core.utils;

import java.util.Arrays;
import java.util.List;

public class Tag {

	private List<String> listTags;
	
	public Tag(String[] tags) {
		listTags = Arrays.asList(tags);
	}
	
	public List<String> getListTags() {
		return listTags;
	}
	
	public String getLastTag() {
		return listTags.get(listTags.size()-1);
	}

	public String convertTagToSimpleName(String tag) {
		return tag.replace("@", "");
	}
}
