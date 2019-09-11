package com.thtf.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;

/**
 * 
 * 功能说明: poi向excel中写入数据
 * 
 * @param sheetName
 * @param list
 * @param file
 * @throws IOException
 * 
 */
public class ExcelUtil {

	public byte[] writeExcel(List<Map<String, String>> dataList) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {

			if (dataList.size() > 0 && dataList.get(0).size() > 0) {
				HSSFWorkbook wb = new HSSFWorkbook();
				HSSFSheet sheet = wb.createSheet();

				HSSFFont font = wb.createFont();
				font.setFontName("黑体");
				
				String show_type=null;

				// 设置单元格类型
				HSSFCellStyle style = wb.createCellStyle();
				style.setFont(font);
				style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平布局：居中
				style.setWrapText(true);
				style.setBorderBottom(HSSFCellStyle.BORDER_THIN);// 下边框
				style.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
				style.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
				style.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框

				if(dataList.get(0).size()==25){
					show_type="点";
				}else if(dataList.get(0).size()==13){
					show_type="月";
				}else{
					show_type="天";
				}

                String[] headname=new String[dataList.get(0).size()];
                HashMap dateHeadMap = (HashMap) dataList.get(0);
                Iterator iterHead = dateHeadMap.entrySet().iterator();
				while (iterHead.hasNext()) {
					Map.Entry e = (Entry) iterHead.next();
					Object key = e.getKey();
					if("name".equals(key)){
						headname[0]="名称";
					}else{
						int id=Integer.parseInt(key.toString());
						headname[id]=(id-1)+show_type;
					}
				}
                
				// 设置标题
				HSSFRow row0 = sheet.createRow((short) 0);
				for (int j = 0; j < headname.length; j++) {
					HSSFCell cellTemp = row0.createCell((short) j);
					cellTemp.setCellValue(headname[j]);
					cellTemp.setCellStyle(style);
					sheet.autoSizeColumn((short) j);// 自动调列宽
				}
				
				List<String[]> resultlist = new ArrayList<String[]>();

				for (int i = 0; i < dataList.size(); i++) {
					HashMap dateMap = (HashMap) dataList.get(i);
					String[] dateString = new String[dataList.get(0).size()];
					Iterator iter = dateMap.entrySet().iterator();
					while (iter.hasNext()) {
						Map.Entry e = (Entry) iter.next();
						Object key = e.getKey();
						Object value = e.getValue();
						if("name".equals(key)){
							dateString[0]=value.toString();
						}else{
							int id=Integer.parseInt(key.toString());
							dateString[id]=value.toString();
						}
						
					}
					resultlist.add(dateString);
				}

				for (int i = 0; i < resultlist.size(); i++) {
					HSSFRow rowTemp = sheet.createRow(i + 1);
					String[] number = (String[]) resultlist.get(i);
					for (int j = 0; j < number.length; j++) {
						HSSFCell cellTemp = rowTemp.createCell((short) j);
						cellTemp.setCellValue(number[j]);
						cellTemp.setCellStyle(style);
						sheet.autoSizeColumn((short) j);// 自动调列宽
					}
				}
				wb.write(out);
			}
		} catch (IOException io) {
			io.printStackTrace();
			System.out.println("io erorr :  " + io.getMessage());
		}
		return out.toByteArray();
	}
}
