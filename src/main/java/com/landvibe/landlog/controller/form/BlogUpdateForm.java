package com.landvibe.landlog.controller.form;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
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
}
