package com.thtf.util;

import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



import com.thtf.service.ReportService;

@Controller  
@Scope("singleton")  
public class ExcelAction  {

	@Autowired
	private ReportService reportService;

	@RequestMapping(value="/ezEPC/excel.action")   
	public String test(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("UTF-8");  
		String fileName = request.getParameter("fileName")+".xls";  
	    String dateType = request.getParameter("dateType"); 
		String date = request.getParameter("date");  
	    String itemType = request.getParameter("itemType"); 
	    String showType = request.getParameter("showType"); 
	    String idstr = request.getParameter("ids"); 
	    String unitId = request.getParameter("unitId");
	    
	    List<String> ids = new ArrayList<String>();
		if (idstr != null) {
			String[] ls = idstr.split(",");
			if (ls != null)
				for (String s : ls) {
					if(s!=null&&!"".equals(s))
						ids.add(s);
				}
		}	
	
	    List<Map<String, String>> dateList=reportService.report(date,dateType,itemType,showType, ids,Long.parseLong(unitId));

	    response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));
	    DataOutputStream output = new DataOutputStream(response
				.getOutputStream());
	    ExcelUtil excelUtil=new  ExcelUtil();
		byte[] bytes = excelUtil.writeExcel(dateList);
		response.setContentLength(bytes.length);
		for (int j = 0; j < bytes.length; j++) {
			output.writeByte(bytes[j]);
		}
		output.flush();


		return null;
	}

      


}
