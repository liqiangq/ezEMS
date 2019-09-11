package com.thtf.util.ofc;

import com.thtf.util.ColourUtil;

public class JSONUtil_OFC {
	public static String getJSON(ChartsModuleUtil c, String chartType) {
		String json = "";
		if (c != null) {
			if("bar".equals(chartType)){ //柱状图
				json = barJSON(c);
			}else if("pie".equals(chartType)){ //饼状图
				json = pieJSON(c);
			}else if("line".equals(chartType)){ //曲线图
				json = lineJSON(c);
			}
		}
		
		return json;
	}
	private static String pieJSON(ChartsModuleUtil c){
		StringBuffer json = new StringBuffer();
		json.append("{                                                             ");
		json.append("    \"elements\": [                                           ");
		json.append("        {                                                     ");
		json.append("            \"type\": \"pie\",                                ");
		json.append("            \"start-angle\": 35,                              ");
		json.append("            \"animate\": [                                    ");
		json.append("                {                                             ");
		json.append("                    \"type\": \"fade\"                        ");
		json.append("                },                                            ");
		json.append("                {                                             ");
		json.append("                    \"type\": \"bounce\",                     ");
		json.append("                    \"distance\": 4                           ");
		json.append("                }                                             ");
		json.append("            ],                                                ");
		json.append("            \"gradient-fill\": true,                          ");
		json.append("            \"tip\": \"#val# of #total# / #percent# of 100%\",   ");
		json.append("            \"colours\": [                                    ");
		for(int i = 0 ; i<c.getElements().size() ; i++){
			if(i>0){
				json.append(",");
			}
			json.append("\""+ColourUtil.colours[i]+"\"");
		}
		json.append("            ],                                                ");
		json.append("            \"values\": [                                     ");
		for(int i = 0 ; i<c.getElements().size() ; i++){
			ElementsModuleUtil e = c.getElements().get(i);
			if(i>0){
				json.append(",");
			}
			for(int j = 0 ; j < e.getValues().size() ; j++){
				json.append("{");
				json.append("\"value\": "+e.getValues().get(j).getValue()+",");
				json.append("\"label\": \""+e.getText()+"("+e.getValues().get(j).getValue()+")\"");
				json.append("}");
			}
		}
		json.append("            ]                                                 ");
		json.append("        }                                                     ");
		json.append("    ],                                                        ");
		json.append("    \"title\": {                                              ");
		json.append("        \"text\": \""+c.getTitle()+"\"                        ");
		json.append("    }                                                         ");
		json.append("}                                                             ");
		return json.toString();
		
		
		
		/*ItemObject o = null;
		StringBuffer json = new StringBuffer();
		json.append("{                                                             ");
		json.append("    \"elements\": [                                           ");
		json.append("        {                                                     ");
		json.append("            \"type\": \"pie\",                                ");
		json.append("            \"start-angle\": 35,                              ");
		json.append("            \"animate\": [                                    ");
		json.append("                {                                             ");
		json.append("                    \"type\": \"fade\"                        ");
		json.append("                },                                            ");
		json.append("                {                                             ");
		json.append("                    \"type\": \"bounce\",                     ");
		json.append("                    \"distance\": 4                           ");
		json.append("                }                                             ");
		json.append("            ],                                                ");
		json.append("            \"gradient-fill\": true,                          ");
		json.append("            \"tip\": \"#val# of #total##percent# of 100%\",   ");
		json.append("            \"colours\": [                                    ");
		ElementsModuleUtil e = c.getElements().get(0);
		for(int i = 0 ; i<e.getValues().size() ; i++){
			if(i>0){
				json.append(",");
			}
			json.append("\""+ColourUtil.colours[i]+"\"");
		}
		json.append("            ],                                                ");
		json.append("            \"values\": [                                     ");
		for(int j = 0 ; j < e.getValues().size() ; j++){
			o = e.getValues().get(j);
			if(j!=0){
				json.append(",");
			}
			json.append("{ \"value\":"+o.getValue()+", \"label\": \""+o.getKey()+" ("+o.getValue()+")\" }");
		}
		json.append("            ]                                                 ");
		json.append("        }                                                     ");
		json.append("    ],                                                        ");
		json.append("    \"title\": {                                              ");
		json.append("        \"text\": \""+c.getTitle()+"\"                        ");
		json.append("    }                                                         ");
		json.append("}                                                             ");
		return json.toString();*/
		
		
	}
	private static String lineJSON(ChartsModuleUtil c){
		StringBuffer json = new StringBuffer();
		json.append("{");
		// -- title
		json.append("\"title\":{");
		json.append("\"text\":\"" + c.getTitle() + "\"");
//		json.append(",\"style\": \"{font-size: 20px; color:#0000ff; font-family: Verdana; text-align: center;}\"");
		json.append("},");

		// --"y_legend":
		json.append("\"y_legend\":{");
		json.append("\"text\":\"" + c.getYlegend() + "\"");
		json.append(",\"style\": \"{color: #736AFF; font-size: 12px;}\"");
		json.append("},");
		// --"y_axis":

		json.append("\"y_axis\": {");
//		json.append("\"stroke\": 4,");
//		json.append("\"tick_length\": 3,");
//		json.append("\"colour\": \"#d000d0\",");
//		json.append("\"grid_colour\": \"#00ff00\",");
		json.append("\"steps\": "+c.getSteps()+",");
		json.append("\"max\": "+c.getMax()+",");
		json.append("\"min\": "+c.getMin()+"");
		json.append("},");

		// -------"x_axis":
		json.append("\"x_axis\": {");
//		json.append("\"stroke\": 1,");
//		json.append("\"tick_height\": 10,");
//		json.append("\"colour\": \"#d000d0\",");
//		json.append("\"grid_colour\": \"#00ff00\",");
		json.append("\"labels\": {");
		json.append("\"labels\": [");
		for (int i = 0; i < c.getXaxisValues().size(); i++) {
			if (i == 0) {
				json.append("\"" + c.getXaxisValues().get(i) + "\"");
			} else {
				json.append(",\"" + c.getXaxisValues().get(i) + "\"");
			}
		}
		json.append("]");
		json.append("}");
		json.append("},");

		json.append("\"elements\": [");
		
		ItemObject o = null;
		for(int i = 0 ; i<c.getElements().size() ; i++){
			ElementsModuleUtil e = c.getElements().get(i);
			if(i != 0){
				json.append(",");
			}
			json.append("{");
			json.append("\"type\": \"line\",");
//			json.append("\"alpha\": 0.5,");
			json.append("\"colour\": \"#"+ColourUtil.colours[i]+"\",");
			json.append("\"dot-style\": { \"type\": \"solid-dot\", \"dot-size\": 3, \"halo-size\": 1, \"colour\": \""+ColourUtil.colours[i]+"\" },");
			json.append("\"text\": \""+e.getText()+"\",");
			json.append("\"font-size\": 10,");
			json.append("\"values\": [");
			for(int j = 0 ; j < e.getValues().size() ; j++){
				o = e.getValues().get(j);
				if(j!=0){
					json.append(",");
				}
				if("".equals(o.getState())){
					json.append("{\"value\":");
					json.append(o.getValue());
					json.append(",\"colour\": \""+ColourUtil.colours[i]+"\", \"tip\": \"#val#");
					json.append("-设备:"+e.getText());
					json.append("\" }");
				}else{
					json.append("{\"value\":");
					json.append(o.getValue());
					json.append(",\"colour\": \"#D02020\", \"tip\": \"#val#");
					json.append("-设备:"+e.getText());
					json.append("-异常:"+o.getState());
					json.append("\" }");
					
				}
			}
			json.append("]");
			json.append("}");
		
		}
		json.append("]");
		json.append("}");
		return json.toString();
	}
	private static String barJSON(ChartsModuleUtil c){
		ItemObject o = null;
		StringBuffer json = new StringBuffer();
		json.append("{");
		// -- title
		json.append("\"title\":{");
		json.append("\"text\":\"" + c.getTitle() + "\"");
//		json.append(",\"style\": \"{font-size: 20px; color:#0000ff; font-family: Verdana; text-align: center;}\"");
		json.append("},");

		// --"y_legend":
		json.append("\"y_legend\":{");
		json.append("\"text\":\"" + c.getYlegend() + "\"");
		json.append(",\"style\": \"{color: #736AFF; font-size: 12px;}\"");
		json.append("},");
		// --"y_axis":

		json.append("\"y_axis\": {");
//		json.append("\"stroke\": 4,");
//		json.append("\"tick_length\": 3,");
//		json.append("\"colour\": \"#d000d0\",");
//		json.append("\"grid_colour\": \"#00ff00\",");
		json.append("\"steps\": "+c.getSteps()+",");
		json.append("\"max\": "+c.getMax()+",");
		json.append("\"min\": "+c.getMin()+"");
		json.append("},");

		// -------"x_axis":
		json.append("\"x_axis\": {");
//		json.append("\"stroke\": 1,");
//		json.append("\"tick_height\": 10,");
//		json.append("\"colour\": \"#d000d0\",");
//		json.append("\"grid_colour\": \"#00ff00\",");
		json.append("\"labels\": {");
		json.append("\"labels\": [");
		for (int i = 0; i < c.getXaxisValues().size(); i++) {
			if (i == 0) {
				json.append("\"" + c.getXaxisValues().get(i) + "\"");
			} else {
				json.append(",\"" + c.getXaxisValues().get(i) + "\"");
			}
		}
		json.append("]");
		json.append("}");
		json.append("},");

		json.append("\"elements\": [");
		
		for(int i = 0 ; i<c.getElements().size() ; i++){
			ElementsModuleUtil e = c.getElements().get(i);
			if(i != 0){
				json.append(",");
			}
			json.append("{");
			json.append("\"type\": \"bar_glass\",");
			json.append("\"alpha\": 0.8,");
			json.append("\"colour\": \"#"+ColourUtil.colours[i]+"\",");
			json.append("\"text\": \""+e.getText()+"\",");
			json.append("\"font-size\": 10,");
			json.append("\"values\": [");
//			for(int j = 0 ; j < e.getValues().size() ; j++){
//				if(j!=0){
//					json.append(",");
//				}
//				json.append(e.getValues().get(j).getValue());
//			}
			for(int j = 0 ; j < e.getValues().size() ; j++){
				o = e.getValues().get(j);
				if(j!=0){
					json.append(",");
				}
				if("".equals(o.getState())){
					json.append(e.getValues().get(j).getValue());
				}else{
					json.append("{\"top\":");
					json.append(o.getValue());
					json.append(",\"colour\":\"#"+ColourUtil.colours[i]+"\",\"tip\":\"");
					json.append("#val#-异常:"+o.getState());
					json.append("\"}");
				}
			}
			json.append("]");
			json.append("}");
		
		}
		json.append("]");
		json.append("}");
		return json.toString();
	}
}
