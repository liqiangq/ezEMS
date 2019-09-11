package com.thtf.util;

import java.awt.Color;
import java.awt.Font;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;

import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.util.Rotation;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/*
 * 生成图形公共类
 * (饼图,曲线,柱图)
 */

public class MarkCharts {
	/*
	 * 饼图 参数（名称数组，数值数组，图的名称，路径，全路径）
	 */
	public static BufferedImage MarkBar(String[] datanames, double[] numbers,
			String title) {

		// 1.获取数据
		DefaultPieDataset data = new DefaultPieDataset();
		for (int i = 0; i < datanames.length; i++) {
			data.setValue(datanames[i], numbers[i]);
		}

		// 2.生成3D饼图数据
		Font font = new Font("黑体", Font.CENTER_BASELINE, 10);
		PiePlot3D plot = new PiePlot3D(data);// 生成一个3D饼图
		plot.setLabelFont(font);
		plot.setForegroundAlpha(0.6f); // 数据区的背景透明度
		// 设置开始角度
		plot.setStartAngle(150D);
		// 设置方向为”顺时针方向“
		plot.setDirection(Rotation.CLOCKWISE);

		// 3.生成chart图形
		JFreeChart chart = new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT,
				plot, true);
		chart.setBackgroundPaint(java.awt.Color.white);// 可选，设置

		// 设置标题文字，并将其字体设置 此处为图片正上方文字
		chart.setTitle(new TextTitle(title, font));

		BufferedImage image = chart.createBufferedImage(300, 200, null);

		return image;
	}

	/*
	 * 曲图 参数（名称数组，数值数组，图的名称，路径，全路径）
	 */
	public static BufferedImage MarkCurveTu(String[] datanames,
			double[] numbers, String title) {

		// 1.获取数据
		DefaultPieDataset data = new DefaultPieDataset();
		for (int i = 0; i < datanames.length; i++) {
			data.setValue(datanames[i], numbers[i]);
		}

		// 2.生成3D饼图数据
		PiePlot3D plot = new PiePlot3D(data);// 生成一个3D饼图
		plot.setForegroundAlpha(0.6f); // 数据区的背景透明度

		// 3.生成chart图形
		
		JFreeChart chart = new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT,
				plot, true);
		chart.setBackgroundPaint(java.awt.Color.white);// 可选，设置图片背景色
		chart.setTitle(title);// 可选，设置图片标题

		BufferedImage image = chart.createBufferedImage(600, 400, null);

		return image;
	}

	/*
	 * 柱形图 参数（数值数组，名称数组，图的名称，路径，全路径）
	 */
	public static BufferedImage MarkHistogramTu(
			List<Map<String, String>> dataList) {
		// // 1.CategoryDataset数据集对象

		String show_type = null;
		BufferedImage image = null;
		if (dataList.size() > 0 && dataList.get(0).size() > 0) {
			if (dataList.get(0).size() == 25) {
				show_type = "点";
			} else if (dataList.get(0).size() == 13) {
				show_type = "月";
			} else {
				show_type = "天";
			}

			String[] columnKeys = new String[dataList.get(0).size()-1];
			String[] rowKeys = new String[dataList.size()];
			HashMap dateHeadMap = (HashMap) dataList.get(0);
			Iterator iterHead = dateHeadMap.entrySet().iterator();
			while (iterHead.hasNext()) {
				Map.Entry e = (Entry) iterHead.next();
				Object key = e.getKey();
				Object value = e.getValue();
				if ("name".equals(key)) {

				} else {
					int id = Integer.parseInt(key.toString());
					columnKeys[id-1] = (id-1) + show_type;
				}
			}
			List<double[]> resultlist = new ArrayList<double[]>();

			for (int i = 0; i < dataList.size(); i++) {
				HashMap dateMap = (HashMap) dataList.get(i);
				double[] dateString = new double[dataList.get(0).size()];
				Iterator iter = dateMap.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry e = (Entry) iter.next();
					Object key = e.getKey();
					Object value = e.getValue();
					if ("name".equals(key)) {
						rowKeys[i] = value.toString();
					} else {
						int id = Integer.parseInt(key.toString());
						dateString[id] = Double.parseDouble(value.toString());
					}

				}
				resultlist.add(dateString);
			}

			double[][] data = new double[resultlist.size()][resultlist.get(0).length-1];
			for (int i = 0; i < resultlist.size(); i++) {
				double[] dataOld= resultlist.get(i);
				for(int j=0;j<dataOld.length-1;j++){
					data[i][j]=dataOld[j+1];
				}
				
			}
			
			CategoryDataset dataset = DatasetUtilities.createCategoryDataset(
					rowKeys, columnKeys, data);
			// 2）JFreeChart创建图形对象。
			JFreeChart chart = ChartFactory.createBarChart3D(null, null, null,
					dataset, PlotOrientation.HORIZONTAL, true, false, false);
			chart.setBackgroundPaint(Color.WHITE);

			// 3)CategoryPlot图表区域对象
			CategoryPlot plot = chart.getCategoryPlot();
			CategoryAxis domainAxis = plot.getDomainAxis();
			plot.setDomainAxis(domainAxis);
			plot.setDatasetRenderingOrder(DatasetRenderingOrder.REVERSE);

			// 4）设置图形显示的属性（横纵轴的设置）
			// 设置Y轴显示整数
			NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
			rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

			// 设置最高的一个 Item 与图片顶端的距离
			rangeAxis.setUpperMargin(0.15);
			// 设置最低的一个 Item 与图片底端的距离
			rangeAxis.setLowerMargin(0.15);
			plot.setRangeAxis(rangeAxis);

			// 5）设置图表对象的属性
			BarRenderer3D renderer = new BarRenderer3D();
			renderer.setBaseOutlinePaint(Color.BLACK);
			renderer
					.setLabelGenerator(new org.jfree.chart.labels.StandardCategoryLabelGenerator());
			renderer.setItemLabelFont(new Font("黑体", Font.PLAIN, 20));
			renderer.setItemLabelsVisible(true);

			renderer.setBaseItemLabelsVisible(true);
			renderer.setItemLabelPaint(Color.decode("#799AE1"));
			renderer.setMaxBarWidth(0.03); // 设置柱状图的width
			// 设置柱状的颜色

			// 设置每个地区所包含的平行柱的之间距离
			renderer.setItemMargin(0.1);

			// 
			// 默认的数字显示在柱子中，通过如下两句可调整数字的显示
			// 注意：此句很关键，若无此句，那数字的显示会被覆盖，给人数字没有显示出来的问题
			// renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(
			// ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));
			renderer.setItemLabelAnchorOffset(10D);
			// 设置每个地区所包含的平行柱的之间距离
			// renderer.setItemMargin(0.3);
			plot.setRenderer(renderer);
			// 设置地区、销量的显示位置
			// 将下方的“”放到上方
			plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
			// 将默认放在左边的“”放到右方
			plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
			plot.setWeight(100);
			// 设置柱的透明度
			plot.setForegroundAlpha(0.6f);
			plot.setRenderer(renderer);

			// 设置图表数据的数值

			// 对图片的美观方面的设置
			// 设置标题的文字
//			TextTitle textTitle = chart.getTitle();
//			textTitle.setFont(new Font("黑体", Font.PLAIN, 14));
			// 设置X轴坐标上的文字
			domainAxis.setTickLabelFont(new Font("宋体", Font.PLAIN, 12));
			// 设置X轴的标题文字
			domainAxis.setLabelFont(new Font("宋体", Font.PLAIN, 12));
			// 设置Y轴坐标上的文字
			rangeAxis.setTickLabelFont(new Font("宋体", Font.PLAIN, 12));
			// 设置Y轴的标题文字
			rangeAxis.setLabelFont(new Font("黑体", Font.PLAIN, 12));

			// 设置网格背景颜色
			plot.setBackgroundPaint(Color.white);
			// 设置网格竖线颜色
			plot.setDomainGridlinePaint(Color.decode("#799AE1"));
			// 设置网格横线颜色
			plot.setRangeGridlinePaint(Color.decode("#799AE1"));
			plot.setBackgroundAlpha(0.9f);
			image = chart.createBufferedImage(560, 800, null);
		}
		return image;
	}
}
