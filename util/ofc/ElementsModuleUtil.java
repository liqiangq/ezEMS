package com.thtf.util.ofc;

import java.util.List;

public class ElementsModuleUtil {
	private String type;
	private List<ItemObject> values;
	private String text;
	private String style;

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<ItemObject> getValues() {
		return values;
	}

	public void setValues(List<ItemObject> values) {
		this.values = values;
	}

}
