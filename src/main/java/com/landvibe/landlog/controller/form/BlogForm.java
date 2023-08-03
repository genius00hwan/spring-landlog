package com.landvibe.landlog.controller.form;

public class BlogForm {
	private String title;
	private String contents;

	public BlogForm( String title, String contents) {
		this.title = title;
		this.contents = contents;
	}

	public String getTitle() {
		return title;
	}

	public String getContents() {
		return contents;
	}


}
