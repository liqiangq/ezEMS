package com.thtf.util.ofc;

import java.util.List;

public class ChartsModuleUtil {
	private String title;
	private String ylegend;
	private String xaxis;
	private List<String> xaxisValues;
	
	private String yaxis;
	private String max;
	private String min;
	private String steps;
	
	private List<ElementsModuleUtil> elements;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getYlegend() {
		return ylegend;
	}

	public void setYlegend(String ylegend) {
		this.ylegend = ylegend;
	}

	public String getXaxis() {
		return xaxis;
	}

	public void setXaxis(String xaxis) {
		this.xaxis = xaxis;
	}

	public List<String> getXaxisValues() {
		return xaxisValues;
	}

	public void setXaxisValues(List<String> xaxisValues) {
		this.xaxisValues = xaxisValues;
	}

	public String getYaxis() {
		return yaxis;
	}

	public void setYaxis(String yaxis) {
		this.yaxis = yaxis;
	}

	public String getMax() {
		return max;
	}

	public void setMax(String max) {
		this.max = max;
	}

	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

	public String getSteps() {
		return steps;
	}

	public void setSteps(String steps) {
		this.steps = steps;
	}

	public List<ElementsModuleUtil> getElements() {
		return elements;
	}

	public void setElements(List<ElementsModuleUtil> elements) {
		this.elements = elements;
	}
	
	
}
