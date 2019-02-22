package com.zhsq.biz.vo;

public class EntityRelationElement {
	private String relationName, rtypeName, recordCode;

	public EntityRelationElement(String relationName, String rtypeName,
			String recordCode) {
		this.relationName = relationName;
		this.rtypeName = rtypeName;
		this.recordCode = recordCode;
	}

	public String getRelationName() {
		return relationName;
	}

	public String getRtypeName() {
		return rtypeName;
	}

	public String getRecordCode() {
		return recordCode;
	}

}
