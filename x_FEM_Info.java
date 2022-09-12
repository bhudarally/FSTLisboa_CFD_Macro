// STAR-CCM+ macro: nbvwio.java
// Written by STAR-CCM+ 14.04.011
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;
import java.io.*;
import star.vis.*;
import star.meshing.*;
import star.base.report.*;
import star.flow.*;

public class x_FEM_Info extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    new File(resolvePath("..\\Post_Processing\\FEM")).mkdirs();

    Simulation simulation_0 = 
      getActiveSimulation();

    Region region_0 = 
      simulation_0.getRegionManager().getRegion("Fluid");

    XyzInternalTable xyzInternalTable_1 = 
      simulation_0.getTableManager().createTable(XyzInternalTable.class);

    xyzInternalTable_1.setPresentationName("Pressure Distrib - Diff_Lateral_LID");

    PrimitiveFieldFunction primitiveFieldFunction_0 = 
      ((PrimitiveFieldFunction) simulation_0.getFieldFunctionManager().getFunction("Pressure"));

    xyzInternalTable_1.setFieldFunctions(new NeoObjectVector(new Object[] {primitiveFieldFunction_0}));

    xyzInternalTable_1.getParts().setQuery(null);

    Boundary boundary_1 = 
      region_0.getBoundaryManager().getBoundary("Car.Diff_Lateral_LID.Faces");

    xyzInternalTable_1.getParts().setObjects(boundary_1);

    xyzInternalTable_1.extract();

    xyzInternalTable_1.export(resolvePath("..\\Post_Processing\\FEM\\Pressure Distrib. - Diff_Lateral_LID.csv"), ",");

    //

    XyzInternalTable xyzInternalTable_2 = 
      simulation_0.getTableManager().createTable(XyzInternalTable.class);

    xyzInternalTable_2.copyProperties(xyzInternalTable_1);

    xyzInternalTable_2.setPresentationName("Pressure Distrib - Diff_Lateral");

    xyzInternalTable_2.getParts().setQuery(null);

    Boundary boundary_2 = 
      region_0.getBoundaryManager().getBoundary("Car.Diff_Lateral.Faces");

    xyzInternalTable_2.getParts().setObjects(boundary_2);

    xyzInternalTable_2.extract();

    xyzInternalTable_2.export(resolvePath("..\\Post_Processing\\FEM\\Pressure Distrib. - Diff_Lateral.csv"), ",");

    //

    XyzInternalTable xyzInternalTable_3 = 
      simulation_0.getTableManager().createTable(XyzInternalTable.class);

    xyzInternalTable_3.copyProperties(xyzInternalTable_1);

    xyzInternalTable_3.setPresentationName("Pressure Distrib - FW_EP");

    xyzInternalTable_3.getParts().setQuery(null);

    Boundary boundary_3 = 
      region_0.getBoundaryManager().getBoundary("Car.FW_EP.Faces");

    xyzInternalTable_3.getParts().setObjects(boundary_3);

    xyzInternalTable_3.extract();

    xyzInternalTable_3.export(resolvePath("..\\Post_Processing\\FEM\\Pressure Distrib. - FW_EP.csv"), ",");

    //

    XyzInternalTable xyzInternalTable_4 = 
      simulation_0.getTableManager().createTable(XyzInternalTable.class);

    xyzInternalTable_4.copyProperties(xyzInternalTable_1);

    xyzInternalTable_4.setPresentationName("Pressure Distrib - FW_Main");

    xyzInternalTable_4.getParts().setQuery(null);

    Boundary boundary_4 = 
      region_0.getBoundaryManager().getBoundary("Car.FW_Main.Faces");

    xyzInternalTable_4.getParts().setObjects(boundary_4);

    xyzInternalTable_4.extract();

    xyzInternalTable_4.export(resolvePath("..\\Post_Processing\\FEM\\Pressure Distrib. - FW_Main.csv"), ",");

    //

    XyzInternalTable xyzInternalTable_5 = 
      simulation_0.getTableManager().createTable(XyzInternalTable.class);

    xyzInternalTable_5.copyProperties(xyzInternalTable_1);

    xyzInternalTable_5.setPresentationName("Pressure Distrib - FW_Flap");

    xyzInternalTable_5.getParts().setQuery(null);

    Boundary boundary_5 = 
      region_0.getBoundaryManager().getBoundary("Car.FW_Flap.Faces");

    xyzInternalTable_5.getParts().setObjects(boundary_5);

    xyzInternalTable_5.extract();

    xyzInternalTable_5.export(resolvePath("..\\Post_Processing\\FEM\\Pressure Distrib. - FW_Flap.csv"), ",");

    //

    XyzInternalTable xyzInternalTable_6 = 
      simulation_0.getTableManager().createTable(XyzInternalTable.class);

    xyzInternalTable_6.copyProperties(xyzInternalTable_1);

    xyzInternalTable_6.setPresentationName("Pressure Distrib - FW_Mid");

    xyzInternalTable_6.getParts().setQuery(null);

    Boundary boundary_6 = 
      region_0.getBoundaryManager().getBoundary("Car.FW_Mid.Faces");

    xyzInternalTable_6.getParts().setObjects(boundary_6);

    xyzInternalTable_6.extract();

    xyzInternalTable_6.export(resolvePath("..\\Post_Processing\\FEM\\Pressure Distrib. - FW_Mid.csv"), ",");

    //

    XyzInternalTable xyzInternalTable_7 = 
      simulation_0.getTableManager().createTable(XyzInternalTable.class);

    xyzInternalTable_7.copyProperties(xyzInternalTable_1);

    xyzInternalTable_7.setPresentationName("Pressure Distrib - FW_Sup");

    xyzInternalTable_7.getParts().setQuery(null);

    Boundary boundary_7 = 
      region_0.getBoundaryManager().getBoundary("Car.FW_Sup.Faces");

    xyzInternalTable_7.getParts().setObjects(boundary_7);

    xyzInternalTable_7.extract();

    xyzInternalTable_7.export(resolvePath("..\\Post_Processing\\FEM\\Pressure Distrib. - FW_Sup.csv"), ",");

    //

    XyzInternalTable xyzInternalTable_8 = 
      simulation_0.getTableManager().createTable(XyzInternalTable.class);

    xyzInternalTable_8.copyProperties(xyzInternalTable_1);

    xyzInternalTable_8.setPresentationName("Pressure Distrib - SC0");

    xyzInternalTable_8.getParts().setQuery(null);

    Boundary boundary_8 = 
      region_0.getBoundaryManager().getBoundary("Car.SC0.Faces");

    xyzInternalTable_8.getParts().setObjects(boundary_8);

    xyzInternalTable_8.extract();

    xyzInternalTable_8.export(resolvePath("..\\Post_Processing\\FEM\\Pressure Distrib. - SC0.csv"), ",");

    //

    XyzInternalTable xyzInternalTable_9 = 
      simulation_0.getTableManager().createTable(XyzInternalTable.class);

    xyzInternalTable_9.copyProperties(xyzInternalTable_1);

    xyzInternalTable_9.setPresentationName("Pressure Distrib - SC1");

    xyzInternalTable_9.getParts().setQuery(null);

    Boundary boundary_9 = 
      region_0.getBoundaryManager().getBoundary("Car.SC1.Faces");

    xyzInternalTable_9.getParts().setObjects(boundary_9);

    xyzInternalTable_9.extract();

    xyzInternalTable_9.export(resolvePath("..\\Post_Processing\\FEM\\Pressure Distrib. - SC1.csv"), ",");

    //

    XyzInternalTable xyzInternalTable_10 = 
      simulation_0.getTableManager().createTable(XyzInternalTable.class);

    xyzInternalTable_10.copyProperties(xyzInternalTable_1);

    xyzInternalTable_10.setPresentationName("Pressure Distrib - SC2");

    xyzInternalTable_10.getParts().setQuery(null);

    Boundary boundary_10 = 
      region_0.getBoundaryManager().getBoundary("Car.SC2.Faces");

    xyzInternalTable_10.getParts().setObjects(boundary_10);

    xyzInternalTable_10.extract();

    xyzInternalTable_10.export(resolvePath("..\\Post_Processing\\FEM\\Pressure Distrib. - SC2.csv"), ",");

    //

    XyzInternalTable xyzInternalTable_11 = 
      simulation_0.getTableManager().createTable(XyzInternalTable.class);

    xyzInternalTable_11.copyProperties(xyzInternalTable_1);

    xyzInternalTable_11.setPresentationName("Pressure Distrib - SC3");

    xyzInternalTable_11.getParts().setQuery(null);

    Boundary boundary_11 = 
      region_0.getBoundaryManager().getBoundary("Car.SC3.Faces");

    xyzInternalTable_11.getParts().setObjects(boundary_11);

    xyzInternalTable_11.extract();

    xyzInternalTable_11.export(resolvePath("..\\Post_Processing\\FEM\\Pressure Distrib. - SC3.csv"), ",");

    //

    XyzInternalTable xyzInternalTable_12 = 
      simulation_0.getTableManager().createTable(XyzInternalTable.class);

    xyzInternalTable_12.copyProperties(xyzInternalTable_1);

    xyzInternalTable_12.setPresentationName("Pressure Distrib - SC_EP");

    xyzInternalTable_12.getParts().setQuery(null);

    Boundary boundary_12 = 
      region_0.getBoundaryManager().getBoundary("Car.SC_EP.Faces");

    xyzInternalTable_12.getParts().setObjects(boundary_12);

    xyzInternalTable_12.extract();

    xyzInternalTable_12.export(resolvePath("..\\Post_Processing\\FEM\\Pressure Distrib. - SC_EP.csv"), ",");

    //

    XyzInternalTable xyzInternalTable_13 = 
      simulation_0.getTableManager().createTable(XyzInternalTable.class);

    xyzInternalTable_13.copyProperties(xyzInternalTable_1);

    xyzInternalTable_13.setPresentationName("Pressure Distrib - RW_EP");

    xyzInternalTable_13.getParts().setQuery(null);

    Boundary boundary_13 = 
      region_0.getBoundaryManager().getBoundary("Car.RW_EP.Faces");

    xyzInternalTable_13.getParts().setObjects(boundary_13);

    xyzInternalTable_13.extract();

    xyzInternalTable_13.export(resolvePath("..\\Post_Processing\\FEM\\Pressure Distrib. - RW_EP.csv"), ",");

    //

    XyzInternalTable xyzInternalTable_14 = 
      simulation_0.getTableManager().createTable(XyzInternalTable.class);

    xyzInternalTable_14.copyProperties(xyzInternalTable_1);

    xyzInternalTable_14.setPresentationName("Pressure Distrib - RW1");

    xyzInternalTable_14.getParts().setQuery(null);

    Boundary boundary_14 = 
      region_0.getBoundaryManager().getBoundary("Car.RW1.Faces");

    xyzInternalTable_14.getParts().setObjects(boundary_14);

    xyzInternalTable_14.extract();

    xyzInternalTable_14.export(resolvePath("..\\Post_Processing\\FEM\\Pressure Distrib. - RW1.csv"), ",");

    //

    XyzInternalTable xyzInternalTable_15 = 
      simulation_0.getTableManager().createTable(XyzInternalTable.class);

    xyzInternalTable_15.copyProperties(xyzInternalTable_1);

    xyzInternalTable_15.setPresentationName("Pressure Distrib - RW2");

    xyzInternalTable_15.getParts().setQuery(null);

    Boundary boundary_15 = 
      region_0.getBoundaryManager().getBoundary("Car.RW2.Faces");

    xyzInternalTable_15.getParts().setObjects(boundary_15);

    xyzInternalTable_15.extract();

    xyzInternalTable_15.export(resolvePath("..\\Post_Processing\\FEM\\Pressure Distrib. - RW2.csv"), ",");

    //

    XyzInternalTable xyzInternalTable_16 = 
      simulation_0.getTableManager().createTable(XyzInternalTable.class);

    xyzInternalTable_16.copyProperties(xyzInternalTable_1);

    xyzInternalTable_16.setPresentationName("Pressure Distrib - RW3");

    xyzInternalTable_16.getParts().setQuery(null);

    Boundary boundary_16 = 
      region_0.getBoundaryManager().getBoundary("Car.RW3.Faces");

    xyzInternalTable_16.getParts().setObjects(boundary_16);

    xyzInternalTable_16.extract();

    xyzInternalTable_16.export(resolvePath("..\\Post_Processing\\FEM\\Pressure Distrib. - RW3.csv"), ",");

    //

    XyzInternalTable xyzInternalTable_17 = 
      simulation_0.getTableManager().createTable(XyzInternalTable.class);

    xyzInternalTable_17.copyProperties(xyzInternalTable_1);

    xyzInternalTable_17.setPresentationName("Pressure Distrib - RW_Support");

    xyzInternalTable_17.getParts().setQuery(null);

    Boundary boundary_17 = 
      region_0.getBoundaryManager().getBoundary("Car.RW_Support.Faces");

    xyzInternalTable_17.getParts().setObjects(boundary_17);

    xyzInternalTable_17.extract();

    xyzInternalTable_17.export(resolvePath("..\\Post_Processing\\FEM\\Pressure Distrib. - RW_Support.csv"), ",");

    //

    XyzInternalTable xyzInternalTable_18 = 
      simulation_0.getTableManager().createTable(XyzInternalTable.class);

    xyzInternalTable_18.copyProperties(xyzInternalTable_1);

    xyzInternalTable_18.setPresentationName("Pressure Distrib - Diff_Back");

    xyzInternalTable_18.getParts().setQuery(null);

    Boundary boundary_18 = 
      region_0.getBoundaryManager().getBoundary("Car.Diff_Back.Faces");

    xyzInternalTable_18.getParts().setObjects(boundary_18);

    xyzInternalTable_18.extract();

    xyzInternalTable_18.export(resolvePath("..\\Post_Processing\\FEM\\Pressure Distrib. - Diff_Back.csv"), ",");

      XyzInternalTable xyzInternalTable_19 = 
      simulation_0.getTableManager().createTable(XyzInternalTable.class);

    xyzInternalTable_19.copyProperties(xyzInternalTable_1);

    xyzInternalTable_19.setPresentationName("Pressure Distrib - BH");

    xyzInternalTable_19.getParts().setQuery(null);

    Boundary boundary_19 = 
      region_0.getBoundaryManager().getBoundary("Car.BH.Faces");

    xyzInternalTable_19.getParts().setObjects(boundary_19);

    xyzInternalTable_19.extract();

    xyzInternalTable_19.export(resolvePath("..\\Post_Processing\\FEM\\Pressure Distrib. - BH.csv"), ",");

    //

    ForceReport forceReport_1 = 
      simulation_0.getReportManager().createReport(ForceReport.class);

    forceReport_1.getDirection().setComponents(0.0, -1.0, 0.0);

    forceReport_1.setPresentationName("SideF_Diff_Back");

    forceReport_1.getParts().setQuery(null);

    forceReport_1.getParts().setObjects(boundary_18);

    ForceReport forceReport_2 = 
      simulation_0.getReportManager().createReport(ForceReport.class);

    forceReport_2.copyProperties(forceReport_1);
  
  forceReport_2.setPresentationName("SideF_Diff_Lat");

  forceReport_2.getParts().setQuery(null);

    forceReport_2.getParts().setObjects(boundary_2);
  
  ForceReport forceReport_21 = 
      simulation_0.getReportManager().createReport(ForceReport.class);

    forceReport_21.copyProperties(forceReport_1);
  
  forceReport_21.setPresentationName("SideF_Diff_Lat_LID");

  forceReport_21.getParts().setQuery(null);

    forceReport_21.getParts().setObjects(boundary_1);

    ForceReport forceReport_410 = 
      simulation_0.getReportManager().createReport(ForceReport.class);

    forceReport_410.copyProperties(forceReport_1);

    forceReport_410.setPresentationName("SideF_FW_Main");

    forceReport_410.getParts().setQuery(null);

    forceReport_410.getParts().setObjects(boundary_4);

    ForceReport forceReport_430 = 
      simulation_0.getReportManager().createReport(ForceReport.class);

    forceReport_430.copyProperties(forceReport_1);

    forceReport_430.setPresentationName("SideF_FW_Flap");

    forceReport_430.getParts().setQuery(null);

    forceReport_430.getParts().setObjects(boundary_5);

    ForceReport forceReport_450 = 
      simulation_0.getReportManager().createReport(ForceReport.class);

    forceReport_450.copyProperties(forceReport_1);

    forceReport_450.setPresentationName("SideF_FW_EP");

    forceReport_450.getParts().setQuery(null);

    forceReport_450.getParts().setObjects(boundary_3);

    ForceReport forceReport_470 = 
      simulation_0.getReportManager().createReport(ForceReport.class);

    forceReport_470.copyProperties(forceReport_1);

    forceReport_470.setPresentationName("SideF_FW_Mid");

    forceReport_470.getParts().setQuery(null);

    forceReport_470.getParts().setObjects(boundary_6);

    ForceReport forceReport_490 = 
      simulation_0.getReportManager().createReport(ForceReport.class);

    forceReport_490.copyProperties(forceReport_1);

    forceReport_490.setPresentationName("SideF_FW_Sup");

    forceReport_490.getParts().setQuery(null);

    forceReport_490.getParts().setObjects(boundary_7);

    ForceReport forceReport_61 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
      
    forceReport_61.copyProperties(forceReport_1);

    forceReport_61.setPresentationName("SideF_RW1");

    forceReport_61.getParts().setQuery(null);

    forceReport_61.getParts().setObjects(boundary_14);

    ForceReport forceReport_62 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
      
    forceReport_62.copyProperties(forceReport_1);

    forceReport_62.setPresentationName("SideF_RW2");

    forceReport_62.getParts().setQuery(null);

    forceReport_62.getParts().setObjects(boundary_15);

    ForceReport forceReport_63 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
      
    forceReport_63.copyProperties(forceReport_1);

    forceReport_63.setPresentationName("SideF_RW3");

    forceReport_63.getParts().setQuery(null);

    forceReport_63.getParts().setObjects(boundary_16);
    
    ForceReport forceReport_65 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
      
    forceReport_65.copyProperties(forceReport_1);

    forceReport_65.setPresentationName("SideF_RW_EP");

    forceReport_65.getParts().setQuery(null);

    forceReport_65.getParts().setObjects(boundary_13);
    
  ForceReport forceReport_7 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    
  forceReport_7.copyProperties(forceReport_1);

    forceReport_7.setPresentationName("SideF_SC0");

    forceReport_7.getParts().setQuery(null);

    forceReport_7.getParts().setObjects(boundary_8);
  
  ForceReport forceReport_9 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    
  forceReport_9.copyProperties(forceReport_1);

    forceReport_9.setPresentationName("SideF_SC1");

    forceReport_9.getParts().setQuery(null);

    forceReport_9.getParts().setObjects(boundary_9);
  
  ForceReport forceReport_11 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    
  forceReport_11.copyProperties(forceReport_1);

    forceReport_11.setPresentationName("SideF_SC2");

    forceReport_11.getParts().setQuery(null);

    forceReport_11.getParts().setObjects(boundary_10);
  
  ForceReport forceReport_13 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    
  forceReport_13.copyProperties(forceReport_1);

    forceReport_13.setPresentationName("SideF_SC3");

    forceReport_13.getParts().setQuery(null);

    forceReport_13.getParts().setObjects(boundary_11);

  ForceReport forceReport_15 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    
  forceReport_15.copyProperties(forceReport_1);

    forceReport_15.setPresentationName("SideF_SC_EP");

    forceReport_15.getParts().setQuery(null);

    forceReport_15.getParts().setObjects(boundary_12);

    ForceReport forceReport_201 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
      
    forceReport_201.copyProperties(forceReport_1);

    forceReport_201.setPresentationName("SideF_BH");

    forceReport_201.getParts().setQuery(null);

    forceReport_201.getParts().setObjects(boundary_19);

    ForceReport forceReport_203 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
      
    forceReport_203.copyProperties(forceReport_1);

    forceReport_203.setPresentationName("SideF_RW_Support");

    forceReport_203.getParts().setQuery(null);

    forceReport_203.getParts().setObjects(boundary_17);

    BufferedWriter bwout = null;

try {

    bwout = new BufferedWriter(new FileWriter(resolvePath("..\\Post_Processing\\ReportsFEM_Temp.csv")));                   
    bwout.write("Report Name, Value, Unit, \n");

    Collection<Report> reportCollection = simulation_0.getReportManager().getObjects();

for (Report thisReport : reportCollection){

    String fieldLocationName = thisReport.getPresentationName();
    Double fieldValue = thisReport.getReportMonitorValue();
    String fieldUnits = thisReport.getUnits().toString();

    simulation_0.println("Field Location :" + fieldLocationName);
    simulation_0.println(" Field Value :" + fieldValue);
    simulation_0.println(" Field Units :" + fieldUnits);
    simulation_0.println("");

    bwout.write( fieldLocationName + ", " +fieldValue + ", " + fieldUnits +"\n");

}
    bwout.close();

} catch (IOException iOException) {
}
    
  }
}
