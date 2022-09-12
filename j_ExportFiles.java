// STAR-CCM+ macro: j_ExportFiles.java
package macro;

import java.util.*;
import java.io.*;
import java.nio.*;
import star.common.*;
import star.base.neo.*;
import star.base.report.*;
import star.base.query.*;
import star.flow.*;

public class j_ExportFiles extends StarMacro {

BufferedWriter bwout = null;

public void execute() {

try {

	Simulation simulation_0 = getActiveSimulation();

	bwout = new BufferedWriter(new FileWriter(resolvePath("..\\Post_Processing\\Reports.csv")));                   
	bwout.write("Report Name, Value, Unit, \n");

	Collection<Report> reportCollection = simulation_0.getReportManager().getObjects();

for (Report thisReport : reportCollection){

	String fieldLocationName = thisReport.getPresentationName();
	Double fieldValue = thisReport.getReportMonitorValue();
	String fieldUnits = thisReport.getUnits().toString();

	if (fieldLocationName.contains("_Mean") == true || fieldLocationName.contains("(VD Method)") == true) {
      bwout.write( fieldLocationName + ", " +fieldValue + ", " + fieldUnits +"\n");
}
}
	bwout.close();

} catch (IOException iOException) {
}
	
	Simulation simulation_0 = 
      getActiveSimulation();
	
	MonitorPlot monitorPlot_0 = 
      ((MonitorPlot) simulation_0.getPlotManager().getPlot("Downforce"));

    monitorPlot_0.close();

    monitorPlot_0.exportScene(resolvePath("..\\Post_Processing\\Monitors\\Scenes\\Downforce.sce"), "Downforce", "", false, false);

    monitorPlot_0.encode(resolvePath("..\\Post_Processing\\Monitors\\Images\\Downforce.png"), "png", 1920, 1080);

    monitorPlot_0.export(resolvePath("..\\Post_Processing\\Monitors\\Files\\Downforce_Monitor.csv"), ",");
	
	MonitorPlot monitorPlot_1 = 
      ((MonitorPlot) simulation_0.getPlotManager().getPlot("Drag"));

    monitorPlot_1.close();

    monitorPlot_1.exportScene(resolvePath("..\\Post_Processing\\Monitors\\Scenes\\Drag.sce"), "Drag", "", false, false);

    monitorPlot_1.encode(resolvePath("..\\Post_Processing\\Monitors\\Images\\Drag.png"), "png", 1920, 1080);

    monitorPlot_1.export(resolvePath("..\\Post_Processing\\Monitors\\Files\\Drag_Monitor.csv"), ",");
	
	MonitorPlot monitorPlot_2 = 
      ((MonitorPlot) simulation_0.getPlotManager().getPlot("Residuals"));

    monitorPlot_2.close();

    monitorPlot_2.exportScene(resolvePath("..\\Post_Processing\\Monitors\\Scenes\\Residuals.sce"), "Residuals", "", false, false);

    monitorPlot_2.encode(resolvePath("..\\Post_Processing\\Monitors\\Images\\Residuals.png"), "png", 1920, 1080);

    monitorPlot_2.export(resolvePath("..\\Post_Processing\\Monitors\\Files\\Residuals_Monitor.csv"), ",");
	
	/*MonitorPlot monitorPlot_3 = 
      ((MonitorPlot) simulation_0.getPlotManager().getPlot("Mass Flow"));

    monitorPlot_3.close();

    monitorPlot_3.exportScene(resolvePath("..\\Post_Processing\\Monitors\\Scenes\\MassFlow.sce"), "Mass Flow", "", false, false);

    monitorPlot_3.encode(resolvePath("..\\Post_Processing\\Monitors\\Images\\MassFlow.png"), "png", 1920, 1080);

    monitorPlot_3.export(resolvePath("..\\Post_Processing\\Monitors\\Files\\MassFlow_Monitor.csv"), ",");
	
	MonitorPlot monitorPlot_4 = 
      ((MonitorPlot) simulation_0.getPlotManager().getPlot("Pressure Drop"));

    monitorPlot_4.close();

    monitorPlot_4.exportScene(resolvePath("..\\Post_Processing\\Monitors\\Scenes\\PressureDrop.sce"), "Pressure Drop", "", false, false);

    monitorPlot_4.encode(resolvePath("..\\Post_Processing\\Monitors\\Images\\PressureDrop.png"), "png", 1920, 1080);

    monitorPlot_4.export(resolvePath("..\\Post_Processing\\Monitors\\Files\\PressureDrop_Monitor.csv"), ",");*/
	
	MonitorPlot monitorPlot_5 = 
      ((MonitorPlot) simulation_0.getPlotManager().getPlot("Solver Iteration Elapsed Time"));

    monitorPlot_5.close();

    monitorPlot_5.exportScene(resolvePath("..\\Post_Processing\\Monitors\\Scenes\\SolverIterationElapsedTime.sce"), "Solver Iteration Elapsed Time", "", false, false);

    monitorPlot_5.encode(resolvePath("..\\Post_Processing\\Monitors\\Images\\SolverIterationElapsedTime.png"), "png", 1920, 1080);

    monitorPlot_5.export(resolvePath("..\\Post_Processing\\Monitors\\Files\\SolverIterationElapsedTime_Monitor.csv"), ",");

    try {

    XYPlot xYPlot_0 = 
      ((XYPlot) simulation_0.getPlotManager().getPlot("Accumulated Downforce"));

    XYPlot xYPlot_1 = 
      ((XYPlot) simulation_0.getPlotManager().getPlot("Accumulated Drag"));

    simulation_0.getPlotManager().deletePlots(new NeoObjectVector(new Object[] {xYPlot_0, xYPlot_1}));

    AccumulatedForceTable accumulatedForceTable_0 = 
      ((AccumulatedForceTable) simulation_0.getTableManager().getTable("Accumulated Downforce"));

    simulation_0.getTableManager().remove(accumulatedForceTable_0);

    AccumulatedForceTable accumulatedForceTable_1 = 
      ((AccumulatedForceTable) simulation_0.getTableManager().getTable("Accumulated Drag"));

    simulation_0.getTableManager().remove(accumulatedForceTable_1);

  }
  catch (Exception e){
    
  }

    AccumulatedForceTable accumulatedForceTable_0 = 
      simulation_0.getTableManager().createTable(AccumulatedForceTable.class);

    accumulatedForceTable_0.setPresentationName("Accumulated Drag");

    AccumulatedForceHistogram accumulatedForceHistogram_0 = 
      ((AccumulatedForceHistogram) accumulatedForceTable_0.getHistogram());

    accumulatedForceHistogram_0.setNumberOfBin(25000);

    accumulatedForceHistogram_0.getBinDirection().setComponents(-1.0, 0.0, 0.0);

    accumulatedForceHistogram_0.getForceDirection().setComponents(-1.0, 0.0, 0.0);

    Region region_1 = 
      simulation_0.getRegionManager().getRegion("Fluid");

    accumulatedForceTable_0.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new RelationshipPredicate(RelationshipOperator.DescendantOf, new IdentityPredicate(IdentityOperator.Equals, Arrays.<ClientServerObject>asList(region_1))), new NamePredicate(NameOperator.DoesNotContain, "Domain"))), Query.STANDARD_MODIFIERS));
  
    accumulatedForceTable_0.extract();

    XYPlot xYPlot_0 = 
      simulation_0.getPlotManager().createPlot(XYPlot.class);

    xYPlot_0.setPresentationName("Accumulated Drag");

    xYPlot_0.getDataSetManager().addDataProviders(new NeoObjectVector(new Object[] {accumulatedForceTable_0}));

    ExternalDataSet externalDataSet_6 = 
      ((ExternalDataSet) xYPlot_0.getDataSetManager().getDataSet("Accumulated Drag"));

    externalDataSet_6.setAccumulateYValues(true);

    LineStyle lineStyle_3 = 
      externalDataSet_6.getLineStyle();

    lineStyle_3.getLinePatternOption().setSelected(LinePatternOption.Type.SOLID);

    lineStyle_3.setWidth(4);

    SymbolStyleWithSpacing symbolStyleWithSpacing_2 = 
      externalDataSet_6.getSymbolStyle();

    symbolStyleWithSpacing_2.getSymbolShapeOption().setSelected(SymbolShapeOption.Type.NONE);

    MultiColLegend multiColLegend_1 = 
      xYPlot_0.getLegend();

    multiColLegend_1.setVisible(false);

    xYPlot_0.setTitle("Accumulated Drag");

    xYPlot_0.encode(resolvePath("..\\Post_Processing\\Monitors\\Images\\Accumulated_Drag.png"), "png", 1920, 1080);

    xYPlot_0.exportScene(resolvePath("..\\Post_Processing\\Monitors\\Scenes\\Accumulated_Drag.sce"), "Accumulated_Drag", "", false, false);

  xYPlot_0.export(resolvePath("..\\Post_Processing\\Monitors\\Files\\Accumulated_Drag.csv"), ",");

  AccumulatedForceTable accumulatedForceTable_1 = 
      simulation_0.getTableManager().createTable(AccumulatedForceTable.class);

    accumulatedForceTable_1.setPresentationName("Accumulated Downforce");

    accumulatedForceTable_1.copyProperties(accumulatedForceTable_0);

    AccumulatedForceHistogram accumulatedForceHistogram_3 = 
      ((AccumulatedForceHistogram) accumulatedForceTable_1.getHistogram());

    accumulatedForceHistogram_3.getForceDirection().setComponents(0.0, 0.0, -1.0);

    XYPlot xYPlot_1 = 
      simulation_0.getPlotManager().createPlot(XYPlot.class);

    xYPlot_1.setPresentationName("Accumulated Downforce");

    xYPlot_1.copyProperties(xYPlot_0);

    ExternalDataSet externalDataSet_1 = 
      ((ExternalDataSet) xYPlot_1.getDataSetManager().getDataSet("Accumulated Drag"));

    xYPlot_1.getDataSetManager().remove(externalDataSet_1);

    xYPlot_1.getDataSetManager().addDataProviders(new NeoObjectVector(new Object[] {accumulatedForceTable_1}));

    ExternalDataSet externalDataSet_2 = 
      ((ExternalDataSet) xYPlot_1.getDataSetManager().getDataSet("Accumulated Downforce"));

    LineStyle lineStyle_1 = 
      externalDataSet_2.getLineStyle();

    lineStyle_1.getLinePatternOption().setSelected(LinePatternOption.Type.SOLID);

    lineStyle_1.setWidth(4);

    SymbolStyleWithSpacing symbolStyleWithSpacing_1 = 
      externalDataSet_2.getSymbolStyle();

    symbolStyleWithSpacing_1.getSymbolShapeOption().setSelected(SymbolShapeOption.Type.NONE);

    accumulatedForceTable_1.extract();

    xYPlot_1.setTitle("Accumulated Downforce");

    externalDataSet_2.setXValuesName("Position");

    externalDataSet_2.setYValuesName("Accumulated Force");

    xYPlot_1.encode(resolvePath("..\\Post_Processing\\Monitors\\Images\\Accumulated_Downforce.png"), "png", 1920, 1080);

    xYPlot_1.exportScene(resolvePath("..\\Post_Processing\\Monitors\\Scenes\\Accumulated_Downforce.sce"), "Accumulated_Downforce", "", false, false);

    xYPlot_1.export(resolvePath("..\\Post_Processing\\Monitors\\Files\\Accumulated_Downforce.csv"), ",");
    }
}
