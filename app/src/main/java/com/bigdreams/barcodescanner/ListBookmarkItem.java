package com.bigdreams.barcodescanner;

public class ListBookmarkItem {

	private String title,highDetail;

	public ListBookmarkItem() {
	}

	public ListBookmarkItem(String title,String highDetail) {
		this.title = title;
		this.highDetail=highDetail;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getHighDetail() {
		return this.highDetail;
	}

	public void setHighDetail(String highDetail) {
		this.highDetail=highDetail;
	}

}