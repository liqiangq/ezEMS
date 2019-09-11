package com.thtf.util.ofc;

public class ItemObject {
	private String key; //
	private String value;
	private String state;//状态  Unknow-未知  Offline-离线  Timeout-超时  Right-正确
	
	public ItemObject(String key, String value,String state) {
		this.key = key;
		this.value = value;
		this.state = state;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getState() {
		String s = "";
		if("Right".equals(state)){
			
		}else if("Unknow".equals(state)){
			s = "未知";
		}else if("Offline".equals(state)){
			s = "离线";
		}else if("Timeout".equals(state)){
			s = "超时";
		}else{
			if(state!=null){
				if(state.indexOf("Unknow")>-1){
					s+="未知";
				}
				if(state.indexOf("Offline")>-1){
					if(!"".equals(s)){
						s+=",";
					}
					s+="离线";
				}
				if(state.indexOf("Timeout")>-1){
					if(!"".equals(s)){
						s+=",";
					}
					s+="超时";
				}
			}
		}
		return s;
	}
	public void setState(String state) {
		this.state = state;
	}
	
}
