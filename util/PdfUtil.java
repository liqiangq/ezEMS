package com.thtf.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;



public class PdfUtil {
	/*
	 * 柱形图 参数（数值数组，名称数组，图的名称，路径，全路径）
	 */
	public byte[] MarkPdf(List<Map<String, String>> dataList) {
		Document doc = new Document();
		ByteArrayOutputStream ba = new ByteArrayOutputStream();

		try {
			if (dataList.size() > 0 && dataList.get(0).size() > 0) {
				PdfWriter writer = PdfWriter.getInstance(doc, ba);
				doc.open();
				String show_type = null;
				BaseFont bfChinese = BaseFont.createFont("STSongStd-Light",
						"UniGB-UCS2-H", false);

				Font fontChinese = new Font(bfChinese);

				if (dataList.get(0).size() == 25) {
					show_type = "点";
				} else if (dataList.get(0).size() == 13) {
					show_type = "月";
				} else {
					show_type = "天";
				}

				String[] headname = new String[dataList.get(0).size()];
				HashMap dateHeadMap = (HashMap) dataList.get(0);
				Iterator iterHead = dateHeadMap.entrySet().iterator();
				while (iterHead.hasNext()) {
					Map.Entry e = (Entry) iterHead.next();
					Object key = e.getKey();
					if ("name".equals(key)) {
						headname[0] = "名称";
					} else {
						int id = Integer.parseInt(key.toString());
						headname[id] = (id-1) + show_type;
					}
				}

				PdfPTable table = new PdfPTable(headname.length);
				for (int i = 0; i < headname.length; i++) {
					PdfPCell c1 = new PdfPCell(new Phrase(headname[i],
							fontChinese));
					c1.setColspan(1);
					table.addCell(c1);
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
						if ("name".equals(key)) {
							dateString[0] = value.toString();
						} else {
							int id = Integer.parseInt(key.toString());
							dateString[id] = value.toString();
						}

					}
					resultlist.add(dateString);
				}
				
				for (int i = 0; i < resultlist.size(); i++) {
					String[] number = (String[]) resultlist.get(i);
					for (int j = 0; j < number.length; j++) {
						PdfPCell c1 = new PdfPCell(new Phrase(number[j],
								fontChinese));
						c1.setColspan(1);
						table.addCell(c1);
					}
				}

				table.setHorizontalAlignment(Element.ALIGN_LEFT);

				doc.add(table);

				BufferedImage bi = MarkCharts.MarkHistogramTu(dataList);
				java.awt.Image image1 = (java.awt.Image) bi;
				Image image = Image.getInstance(image1, Color.RED, false);
				doc.add(image);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			doc.close();
		}
		return ba.toByteArray();
	}

}