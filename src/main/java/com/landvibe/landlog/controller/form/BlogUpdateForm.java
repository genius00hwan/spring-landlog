package com.landvibe.landlog.controller.form;

public class BlogUpdateForm {
	Long id;
	Long creatorId;
	String title;
	String contents;

	public BlogUpdateForm(Long creatorId, Long id, String title, String contents) {
		this.creatorId = creatorId;
		this.id = id;
		this.title = title;
		this.contents = contents;
	}

	public Long getId() {
		return id;
	}

	public Long getCreatorId() {
		return creatorId;
	}

	public String getTitle() {
		return title;
	}

	public String getContents() {
		return contents;
	}
}
