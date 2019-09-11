package com.thtf.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	public static String removeOrders(String hql) {
		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(hql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}
	public static boolean isNull(String s){
		if("".equals(s)||s==null){
			return true;
		}
		return false;
	}
}
