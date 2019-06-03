package util;

import java.io.IOException;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries.XYSeriesRenderStyle;
import org.knowm.xchart.style.markers.SeriesMarkers;

public class Plot2D {
	private XYChart chart;
	private double[] xData;
	private double[] yData;
	private String title;
	
    public Plot2D(String title, String xAxis, String yAxis) { //plot with respect to index
    	chart = new XYChartBuilder().width(1200).height(600).title(title).xAxisTitle(xAxis).yAxisTitle(yAxis).build();
    	chart.getStyler().setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Line);
    	this.title = title;
    }
    
    public Plot2D(String title, String xAxis, String yAxis, int width, int height) { //plot with respect to index
    	chart = new XYChartBuilder().width(width).height(height).title(title).xAxisTitle(xAxis).yAxisTitle(yAxis).build();
    	chart.getStyler().setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Line);
    	this.title = title;
    }
    
    public void addData(String label, double[] data) {
    	yData = data;
    	xData = new double[yData.length];
    	for (int i = 0; i < yData.length; i++) xData[i] = i;
    	
    	chart.addSeries(label, xData, yData).setMarker(SeriesMarkers.NONE);
    }
    
    public void addData(String label, double[] xData, double[] yData) {
    	chart.addSeries(label, xData, yData).setMarker(SeriesMarkers.NONE);
    }
    
    public void translateY(int compensation) {
    	for (int i = 0; i < yData.length; i++) {
    		yData[i] = yData[i] + compensation;
    		if (yData[i] < 0) yData[i] = 0;
    	}
    }
    
    public void showPlot() throws IOException {
    	
    	System.out.printf("Plotting %s...\n", title);
    	BitmapEncoder.saveBitmapWithDPI(chart, title, BitmapFormat.BMP, 300);
    	// Show it
    	new SwingWrapper(chart).displayChart();
    }
    
    public static double[] toDoubleArray(byte[] byteArray) {
    	double[] doubleArray = new double[byteArray.length];
    	
    	for (int i = 0; i < byteArray.length; i++) {
    		doubleArray[i] = (double)byteArray[i];
    	}
    	
    	return doubleArray;
    }
    
    public static double[] toDoubleArray(int[] intArray) {
    	double[] doubleArray = new double[intArray.length];
    	
    	for (int i = 0; i < intArray.length; i++) {
    		doubleArray[i] = (double)intArray[i];
    	}
    	
    	return doubleArray;
    }
}