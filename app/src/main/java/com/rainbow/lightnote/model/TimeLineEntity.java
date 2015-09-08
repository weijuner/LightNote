package com.rainbow.lightnote.model;

import java.util.List;

/**
 * 一级Item实体类
 * 
 * @author zihao
 * 
 */
public class TimeLineEntity {
	private String time;
	/** 二级Item数据列表 **/
	private List<Note> notes;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public List<Note> getNoteList() {
		return notes;
	}

	public void setNoteList(List<Note> notes) {
		this.notes = notes;
	}

}