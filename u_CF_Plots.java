// STAR-CCM+ macro: nbvwio.java
// Written by STAR-CCM+ 14.04.011
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;
import java.io.*;
import java.nio.*;
import star.vis.*;
import star.meshing.*;
import star.base.report.*;
import star.base.query.*;
import star.flow.*;

public class u_CF_Plots extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    Units units_0 = 
      simulation_0.getUnitsManager().getPreferredUnits(new IntVector(new int[] {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));

    PlaneSection planeSection_0 = 
      (PlaneSection) simulation_0.getPartManager().createImplicitPart(new NeoObjectVector(new Object[] {}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), new DoubleVector(new double[] {0.0, 0.0, 0.0}), 0, 1, new DoubleVector(new double[] {0.0}));

    LabCoordinateSystem labCoordinateSystem_0 = 
      simulation_0.getCoordinateSystemManager().getLabCoordinateSystem();

    planeSection_0.setCoordinateSystem(labCoordinateSystem_0);

    planeSection_0.getInputParts().setQuery(null);

    Region region_0 = 
      simulation_0.getRegionManager().getRegion("Fluid");

    Units units_1 = 
      ((Units) simulation_0.getUnitsManager().getObject("mm"));

    planeSection_0.getInputParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new RelationshipPredicate(RelationshipOperator.DescendantOf, new IdentityPredicate(IdentityOperator.Equals, Arrays.<ClientServerObject>asList(region_0))), new NamePredicate(NameOperator.Contains, "FW"))), Query.STANDARD_MODIFIERS));

    planeSection_0.getOriginCoordinate().setCoordinate(units_0, units_1, units_0, new DoubleVector(new double[] {0.0, -1.0, 0.0}));

    planeSection_0.getOriginCoordinate().setCoordinateSystem(labCoordinateSystem_0);

    planeSection_0.getOrientationCoordinate().setUnits0(units_0);

    planeSection_0.getOrientationCoordinate().setUnits1(units_0);

    planeSection_0.getOrientationCoordinate().setUnits2(units_0);

    planeSection_0.getOrientationCoordinate().setDefinition("");

    planeSection_0.getOrientationCoordinate().setValue(new DoubleVector(new double[] {0.0, 1.0, 0.0}));

    planeSection_0.getOrientationCoordinate().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {0.0, 1.0, 0.0}));

    planeSection_0.setValueMode(ValueMode.SINGLE);

    planeSection_0.setPresentationName("CF_FW");

    XYPlot xYPlot_0 = 
      simulation_0.getPlotManager().createPlot(XYPlot.class);

    xYPlot_0.setPresentationName("CF_FW");

    xYPlot_0.getParts().setObjects(planeSection_0);

    YAxisType yAxisType_1 = 
      ((YAxisType) xYPlot_0.getYAxes().getAxisType("Y Type 1"));

    try {

      FieldFunctionUnits fieldFunctionUnits_1 = 
      yAxisType_1.getScalarFunction();

    UserFieldFunction userFieldFunction_0 = 
      ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("Cf"));

    fieldFunctionUnits_1.setFieldFunction(userFieldFunction_0);

  }
  catch (Exception e){

    UserFieldFunction userFieldFunction_0 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();

    userFieldFunction_0.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);

    userFieldFunction_0.setPresentationName("Cf");

    userFieldFunction_0.setFunctionName("Cf");

    userFieldFunction_0.setDefinition("mag($${WallShearStress})/(0.5*1.225*pow(${Car-Velocity},2))");    

    FieldFunctionUnits fieldFunctionUnits_1 = 
      yAxisType_1.getScalarFunction();

    fieldFunctionUnits_1.setFieldFunction(userFieldFunction_0);
  }
    
    MultiColLegend multiColLegend_0 = 
      xYPlot_0.getLegend();

    multiColLegend_0.setVisible(false);

    Cartesian2DAxisManager cartesian2DAxisManager_0 = 
      ((Cartesian2DAxisManager) xYPlot_0.getAxisManager());

    Cartesian2DAxis cartesian2DAxis_0 = 
      ((Cartesian2DAxis) cartesian2DAxisManager_0.getAxis("Bottom Axis"));

    cartesian2DAxis_0.setReverse(true);

    PlotUpdate plotUpdate_0 = 
      xYPlot_0.getPlotUpdate();

    xYPlot_0.setTitle("CF_FW - y = 1 mm");

    new File(resolvePath("..\\Post_Processing\\CF\\FW\\Monitors\\Scenes")).mkdirs();

    xYPlot_0.encode(resolvePath("..\\Post_Processing\\CF\\FW\\Monitors\\Images\\CF_FW - 0 - y = 1 mm.png"), "png", 1920, 1080);

    xYPlot_0.exportScene(resolvePath("..\\Post_Processing\\CF\\FW\\Monitors\\Scenes\\CF_FW - 0 - y = 1 mm.sce"), "CF_FW", "", false, false);

  xYPlot_0.export(resolvePath("..\\Post_Processing\\CF\\FW\\Monitors\\Files\\CF_FW - 0 - y = 1 mm.csv"), ",");

    for (int i = 1; i < 81; i++) { 

    planeSection_0.getOriginCoordinate().setCoordinate(units_0, units_1, units_0, new DoubleVector(new double[] {0.0, (-1-i*9), 0.0}));

  double coordinate = 1+i*9; 

  xYPlot_0.setTitle("CF_FW - y = "+ coordinate +" mm");
  
  xYPlot_0.encode(resolvePath("..\\Post_Processing\\CF\\FW\\Monitors\\Images\\CF_FW - "+ i +" - y = "+ coordinate +" mm.png"), "png", 1920, 1080);

  xYPlot_0.exportScene(resolvePath("..\\Post_Processing\\CF\\FW\\Monitors\\Scenes\\CF_FW - "+ i +" - y = "+ coordinate +" mm.sce"), "CF_FW", "", false, false);

  xYPlot_0.export(resolvePath("..\\Post_Processing\\CF\\FW\\Monitors\\Files\\CF_FW - "+ i +" - y = "+ coordinate +" mm.csv"), ",");
}

  PlaneSection planeSection_1 = 
      (PlaneSection) simulation_0.getPartManager().createImplicitPart(new NeoObjectVector(new Object[] {}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), new DoubleVector(new double[] {0.0, 0.0, 0.0}), 0, 1, new DoubleVector(new double[] {0.0}));

    planeSection_1.setPresentationName("CF_SC");

    planeSection_1.copyProperties(planeSection_0);

    planeSection_1.getOriginCoordinate().setCoordinate(units_0, units_1, units_0, new DoubleVector(new double[] {0.0, -255.0, 0.0}));

    planeSection_1.getInputParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "SC"), new TypePredicate(TypeOperator.Is, Boundary.class))), Query.STANDARD_MODIFIERS));
  
    XYPlot xYPlot_1 = 
      simulation_0.getPlotManager().createPlot(XYPlot.class);

    xYPlot_1.setPresentationName("CF_SC");

    xYPlot_1.copyProperties(xYPlot_0);

    xYPlot_1.getParts().setObjects(planeSection_1);

    xYPlot_1.setTitle("CF_SC - y = 1 mm");

    new File(resolvePath("..\\Post_Processing\\CF\\Side_Elements\\Monitors\\Scenes")).mkdirs();

    xYPlot_1.encode(resolvePath("..\\Post_Processing\\CF\\Side_Elements\\Monitors\\Images\\CF_SC - 0 - y = 255 mm.png"), "png", 1920, 1080);

    xYPlot_1.exportScene(resolvePath("..\\Post_Processing\\CF\\Side_Elements\\Monitors\\Scenes\\CF_SC - 0 - y = 255 mm.sce"), "CF_SC", "", false, false);

  xYPlot_1.export(resolvePath("..\\Post_Processing\\CF\\Side_Elements\\Monitors\\Files\\CF_SC - 0 - y = 255 mm.csv"), ",");

    for (int i = 1; i < 44; i++) { 

    planeSection_1.getOriginCoordinate().setCoordinate(units_0, units_1, units_0, new DoubleVector(new double[] {0.0, (-255-i*10), 0.0}));

  double coordinate = 255+i*10; 

  xYPlot_1.setTitle("CF_SC - y = "+ coordinate +" mm");
  
  xYPlot_1.encode(resolvePath("..\\Post_Processing\\CF\\Side_Elements\\Monitors\\Images\\CF_SC - "+ i +" - y = "+ coordinate +" mm.png"), "png", 1920, 1080);

  xYPlot_1.exportScene(resolvePath("..\\Post_Processing\\CF\\Side_Elements\\Monitors\\Scenes\\CF_SC - "+ i +" - y = "+ coordinate +" mm.sce"), "CF_SC", "", false, false);

  xYPlot_1.export(resolvePath("..\\Post_Processing\\CF\\Side_Elements\\Monitors\\Files\\CF_SC - "+ i +" - y = "+ coordinate +" mm.csv"), ",");
}

PlaneSection planeSection_2 = 
      (PlaneSection) simulation_0.getPartManager().createImplicitPart(new NeoObjectVector(new Object[] {}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), new DoubleVector(new double[] {0.0, 0.0, 0.0}), 0, 1, new DoubleVector(new double[] {0.0}));

    planeSection_2.setPresentationName("CF_Diff");

    planeSection_2.copyProperties(planeSection_0);

    planeSection_2.getOriginCoordinate().setCoordinate(units_0, units_1, units_0, new DoubleVector(new double[] {0.0, -1.0, 0.0}));

    planeSection_2.getInputParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "Diff"), new TypePredicate(TypeOperator.Is, Boundary.class))), Query.STANDARD_MODIFIERS));
  
    XYPlot xYPlot_2 = 
      simulation_0.getPlotManager().createPlot(XYPlot.class);

    xYPlot_2.setPresentationName("CF_Diff");

    xYPlot_2.copyProperties(xYPlot_0);

    xYPlot_2.getParts().setObjects(planeSection_2);

    xYPlot_2.setTitle("CF_Diff - y = 1 mm");

    new File(resolvePath("..\\Post_Processing\\CF\\Diffusers\\Monitors\\Scenes")).mkdirs();

    xYPlot_2.encode(resolvePath("..\\Post_Processing\\CF\\Diffusers\\Monitors\\Images\\CF_Diff - 0 - y = 1 mm.png"), "png", 1920, 1080);

    xYPlot_2.exportScene(resolvePath("..\\Post_Processing\\CF\\Diffusers\\Monitors\\Scenes\\CF_Diff - 0 - y = 1 mm.sce"), "CF_Diff", "", false, false);

  xYPlot_2.export(resolvePath("..\\Post_Processing\\CF\\Diffusers\\Monitors\\Files\\CF_Diff - 0 - y = 1 mm.csv"), ",");

    for (int i = 1; i < 77; i++) { 

    planeSection_2.getOriginCoordinate().setCoordinate(units_0, units_1, units_0, new DoubleVector(new double[] {0.0, (-1-i*9), 0.0}));

  double coordinate = 1+i*9; 

  xYPlot_2.setTitle("CF_Diff - y = "+ coordinate +" mm");
  
  xYPlot_2.encode(resolvePath("..\\Post_Processing\\CF\\Diffusers\\Monitors\\Images\\CF_Diff - "+ i +" - y = "+ coordinate +" mm.png"), "png", 1920, 1080);

  xYPlot_2.exportScene(resolvePath("..\\Post_Processing\\CF\\Diffusers\\Monitors\\Scenes\\CF_Diff - "+ i +" - y = "+ coordinate +" mm.sce"), "CF_Diff", "", false, false);

  xYPlot_2.export(resolvePath("..\\Post_Processing\\CF\\Diffusers\\Monitors\\Files\\CF_Diff - "+ i +" - y = "+ coordinate +" mm.csv"), ",");
}

PlaneSection planeSection_3 = 
      (PlaneSection) simulation_0.getPartManager().createImplicitPart(new NeoObjectVector(new Object[] {}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), new DoubleVector(new double[] {0.0, 0.0, 0.0}), 0, 1, new DoubleVector(new double[] {0.0}));

    planeSection_3.setPresentationName("CF_RW");

    planeSection_3.copyProperties(planeSection_0);

    planeSection_3.getOriginCoordinate().setCoordinate(units_0, units_1, units_0, new DoubleVector(new double[] {0.0, -1.0, 0.0}));

    planeSection_3.getInputParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "RW"), new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.DoesNotContain, "Sup"))), Query.STANDARD_MODIFIERS));
  
    XYPlot xYPlot_3 = 
      simulation_0.getPlotManager().createPlot(XYPlot.class);

    xYPlot_3.setPresentationName("CF_RW");

    xYPlot_3.copyProperties(xYPlot_0);

    xYPlot_3.getParts().setObjects(planeSection_3);

    xYPlot_3.setTitle("CF_RW - y = 1 mm");

    new File(resolvePath("..\\Post_Processing\\CF\\RW\\Monitors\\Scenes")).mkdirs();

    xYPlot_3.encode(resolvePath("..\\Post_Processing\\CF\\RW\\Monitors\\Images\\CF_RW - 0 - y = 1 mm.png"), "png", 1920, 1080);

    xYPlot_3.exportScene(resolvePath("..\\Post_Processing\\CF\\RW\\Monitors\\Scenes\\CF_RW - 0 - y = 1 mm.sce"), "CF_RW", "", false, false);

  xYPlot_3.export(resolvePath("..\\Post_Processing\\CF\\RW\\Monitors\\Files\\CF_RW - 0 - y = 1 mm.csv"), ",");

    for (int i = 1; i < 50; i++) { 

    planeSection_3.getOriginCoordinate().setCoordinate(units_0, units_1, units_0, new DoubleVector(new double[] {0.0, (-1-i*9), 0.0}));

  double coordinate = 1+i*9; 

  xYPlot_3.setTitle("CF_RW - y = "+ coordinate +" mm");
  
  xYPlot_3.encode(resolvePath("..\\Post_Processing\\CF\\RW\\Monitors\\Images\\CF_RW - "+ i +" - y = "+ coordinate +" mm.png"), "png", 1920, 1080);

  xYPlot_3.exportScene(resolvePath("..\\Post_Processing\\CF\\RW\\Monitors\\Scenes\\CF_RW - "+ i +" - y = "+ coordinate +" mm.sce"), "CF_RW", "", false, false);

  xYPlot_3.export(resolvePath("..\\Post_Processing\\CF\\RW\\Monitors\\Files\\CF_RW - "+ i +" - y = "+ coordinate +" mm.csv"), ",");
}

/*try {

      try {

        MinReport minReport_0 = 
          ((MinReport) simulation_0.getReportManager().getReport("min_Misc"));

        MaxReport maxReport_0 = 
          ((MaxReport) simulation_0.getReportManager().getReport("max_Misc"));

      }

      catch (Exception e){

    Boundary boundary_1000 = 
      region_0.getBoundaryManager().getBoundary("Car.Misc.Faces");

    MinReport minReport_0 = 
      simulation_0.getReportManager().createReport(MinReport.class);

    PrimitiveFieldFunction primitiveFieldFunction_0 = 
      ((PrimitiveFieldFunction) simulation_0.getFieldFunctionManager().getFunction("Position"));

    VectorComponentFieldFunction vectorComponentFieldFunction_0 = 
      ((VectorComponentFieldFunction) primitiveFieldFunction_0.getComponentFunction(1));

    minReport_0.setFieldFunction(vectorComponentFieldFunction_0);

    minReport_0.getParts().setObjects(boundary_1000);

    minReport_0.setUnits(units_1);

    minReport_0.setPresentationName("min_Misc");

    MaxReport maxReport_0 = 
      simulation_0.getReportManager().createReport(MaxReport.class);

    maxReport_0.setPresentationName("max_Misc");

    maxReport_0.setFieldFunction(vectorComponentFieldFunction_0);

    maxReport_0.getParts().setQuery(null);

    maxReport_0.setUnits(units_1);

    maxReport_0.getParts().setObjects(boundary_1000);
  }

  MinReport minReport_0 = 
    ((MinReport) simulation_0.getReportManager().getReport("min_Misc"));

  MaxReport maxReport_0 = 
    ((MaxReport) simulation_0.getReportManager().getReport("max_Misc"));

  Boundary boundary_1000 = 
      region_0.getBoundaryManager().getBoundary("Car.Misc.Faces");

      try {

        try {

        MinReport minReport_1 = 
          ((MinReport) simulation_0.getReportManager().getReport("min_Misc2"));

        MaxReport maxReport_1 = 
          ((MaxReport) simulation_0.getReportManager().getReport("max_Misc2"));

      }

      catch (Exception e){

    Boundary boundary_1001 = 
      region_0.getBoundaryManager().getBoundary("Car.Misc2.Faces");

      MinReport minReport_1 = 
      simulation_0.getReportManager().createReport(MinReport.class);

    minReport_1.copyProperties(minReport_0);

    minReport_1.setPresentationName("min_Misc2");

    minReport_1.getParts().setQuery(null);

    minReport_1.setUnits(units_1);

    minReport_1.getParts().setObjects(boundary_1001);

    MaxReport maxReport_1 = 
      simulation_0.getReportManager().createReport(MaxReport.class);

    maxReport_1.copyProperties(maxReport_0);

    maxReport_1.setPresentationName("max_Misc2");

    maxReport_1.getParts().setQuery(null);

    maxReport_1.setUnits(units_1);

    maxReport_1.getParts().setObjects(boundary_1001);
  }

  MinReport minReport_1 = 
    ((MinReport) simulation_0.getReportManager().getReport("min_Misc2"));

  MaxReport maxReport_1 = 
    ((MaxReport) simulation_0.getReportManager().getReport("max_Misc2"));

  Boundary boundary_1001 = 
      region_0.getBoundaryManager().getBoundary("Car.Misc2.Faces");

    double min_Misc = simulation_0.getReportManager().getReport("min_Misc").getReportMonitorValue();
    double min_Misc2 = simulation_0.getReportManager().getReport("min_Misc2").getReportMonitorValue();
    double max_Misc = simulation_0.getReportManager().getReport("max_Misc").getReportMonitorValue();
    double max_Misc2 = simulation_0.getReportManager().getReport("max_Misc2").getReportMonitorValue();

    PlaneSection planeSection_4 = 
      (PlaneSection) simulation_0.getPartManager().createImplicitPart(new NeoObjectVector(new Object[] {}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), new DoubleVector(new double[] {0.0, 0.0, 0.0}), 0, 1, new DoubleVector(new double[] {0.0}));

    planeSection_4.setPresentationName("CF_Misc");

    planeSection_4.copyProperties(planeSection_0);

    planeSection_4.getOriginCoordinate().setCoordinate(units_0, units_1, units_0, new DoubleVector(new double[] {0.0, max_Misc, 0.0}));

    planeSection_4.getInputParts().setObjects(boundary_1000);

    XYPlot xYPlot_4 = 
      simulation_0.getPlotManager().createPlot(XYPlot.class);

    xYPlot_4.setPresentationName("CF_Misc");

    xYPlot_4.copyProperties(xYPlot_0);

    xYPlot_4.getParts().setObjects(planeSection_4);

    xYPlot_4.setTitle("CF_Misc - y = "+ -max_Misc +" mm");

    new File(resolvePath("..\\Post_Processing\\CF\\Misc\\Monitors\\Scenes")).mkdirs();

    xYPlot_4.encode(resolvePath("..\\Post_Processing\\CF\\Misc\\Monitors\\Images\\CF_Misc - 0 - y = "+ -max_Misc +" mm.png"), "png", 1920, 1080);

    xYPlot_4.exportScene(resolvePath("..\\Post_Processing\\CF\\Misc\\Monitors\\Scenes\\CF_Misc - 0 - y = "+ -max_Misc +" mm.sce"), "CF_Misc", "", false, false);

    xYPlot_4.export(resolvePath("..\\Post_Processing\\CF\\Misc\\Monitors\\Files\\CF_Misc - 0 - y = "+ -max_Misc +" mm mm.csv"), ",");

    double misc_it = (max_Misc - min_Misc)/9;

  for (double i = 1; i < misc_it; i++) {

  planeSection_4.getOriginCoordinate().setCoordinate(units_0, units_1, units_0, new DoubleVector(new double[] {0.0, (max_Misc-i*9), 0.0}));

  double coordinate = -max_Misc+i*9; 

  xYPlot_4.setTitle("CF_Misc - y = "+ coordinate +" mm");
  
  xYPlot_4.encode(resolvePath("..\\Post_Processing\\CF\\Misc\\Monitors\\Images\\CF_Misc - "+ i +" - y = "+ coordinate +" mm.png"), "png", 1920, 1080);

  xYPlot_4.exportScene(resolvePath("..\\Post_Processing\\CF\\Misc\\Monitors\\Scenes\\CF_Misc - "+ i +" - y = "+ coordinate +" mm.sce"), "CF_Misc", "", false, false);

  xYPlot_4.export(resolvePath("..\\Post_Processing\\CF\\Misc\\Monitors\\Files\\CF_Misc - "+ i +" - y = "+ coordinate +" mm.csv"), ",");
}

  PlaneSection planeSection_5 = 
      (PlaneSection) simulation_0.getPartManager().createImplicitPart(new NeoObjectVector(new Object[] {}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), new DoubleVector(new double[] {0.0, 0.0, 0.0}), 0, 1, new DoubleVector(new double[] {0.0}));

    planeSection_5.setPresentationName("CF_Misc2");

    planeSection_5.copyProperties(planeSection_0);

    planeSection_5.getOriginCoordinate().setCoordinate(units_0, units_1, units_0, new DoubleVector(new double[] {0.0, max_Misc2, 0.0}));

    planeSection_5.getInputParts().setObjects(boundary_1001);

    XYPlot xYPlot_5 = 
      simulation_0.getPlotManager().createPlot(XYPlot.class);

    xYPlot_5.setPresentationName("CF_Misc2");

    xYPlot_5.copyProperties(xYPlot_0);

    xYPlot_5.getParts().setObjects(planeSection_5);

    xYPlot_5.setTitle("CF_Misc - y = "+ -max_Misc2 +" mm");

    new File(resolvePath("..\\Post_Processing\\CF\\Misc2\\Monitors\\Scenes")).mkdirs();

    xYPlot_5.encode(resolvePath("..\\Post_Processing\\CF\\Misc2\\Monitors\\Images\\CF_Misc2 - 0 - y = "+ -max_Misc2 +" mm.png"), "png", 1920, 1080);

    xYPlot_5.exportScene(resolvePath("..\\Post_Processing\\CF\\Misc2\\Monitors\\Scenes\\CF_Misc2 - 0 - y = "+ -max_Misc2 +" mm.sce"), "CF_Misc2", "", false, false);

    xYPlot_5.export(resolvePath("..\\Post_Processing\\CF\\Misc2\\Monitors\\Files\\CF_Misc2 - 0 - y = "+ -max_Misc2 +" mm.csv"), ",");

  double misc2_it = (max_Misc2 - min_Misc2)/9;

  for (double i = 1; i < misc2_it; i++) { 

    planeSection_5.getOriginCoordinate().setCoordinate(units_0, units_1, units_0, new DoubleVector(new double[] {0.0, (max_Misc2-i*9), 0.0}));

  double coordinate = -max_Misc2+i*9; 

  xYPlot_5.setTitle("CF_Misc - y = "+ coordinate +" mm");
  
  xYPlot_5.encode(resolvePath("..\\Post_Processing\\CF\\Misc2\\Monitors\\Images\\CF_Misc2 - "+ i +" - y = "+ coordinate +" mm.png"), "png", 1920, 1080);

  xYPlot_5.exportScene(resolvePath("..\\Post_Processing\\CF\\Misc2\\Monitors\\Scenes\\CF_Misc2 - "+ i +" - y = "+ coordinate +" mm.sce"), "CF_Misc2", "", false, false);

  xYPlot_5.export(resolvePath("..\\Post_Processing\\CF\\Misc2\\Monitors\\Files\\CF_Misc2 - "+ i +" - y = "+ coordinate +" mm.csv"), ",");
  }
}
      catch (Exception e){

    double min_Misc = simulation_0.getReportManager().getReport("min_Misc").getReportMonitorValue();
    double max_Misc = simulation_0.getReportManager().getReport("max_Misc").getReportMonitorValue();

    PlaneSection planeSection_4 = 
      (PlaneSection) simulation_0.getPartManager().createImplicitPart(new NeoObjectVector(new Object[] {}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), new DoubleVector(new double[] {0.0, 0.0, 0.0}), 0, 1, new DoubleVector(new double[] {0.0}));

    planeSection_4.setPresentationName("CF_Misc");

    planeSection_4.copyProperties(planeSection_0);

    planeSection_4.getOriginCoordinate().setCoordinate(units_0, units_1, units_0, new DoubleVector(new double[] {0.0, max_Misc, 0.0}));

    planeSection_4.getInputParts().setObjects(boundary_1000);

    XYPlot xYPlot_4 = 
      simulation_0.getPlotManager().createPlot(XYPlot.class);

    xYPlot_4.setPresentationName("CF_Misc");

    xYPlot_4.copyProperties(xYPlot_0);

    xYPlot_4.getParts().setObjects(planeSection_4);

    xYPlot_4.setTitle("CF_Misc - y = "+ -max_Misc +" mm");

    new File(resolvePath("..\\Post_Processing\\CF\\Misc\\Monitors\\Scenes")).mkdirs();

    xYPlot_4.encode(resolvePath("..\\Post_Processing\\CF\\Misc\\Monitors\\Images\\CF_Misc - 0 - y = "+ -max_Misc +" mm.png"), "png", 1920, 1080);

    xYPlot_4.exportScene(resolvePath("..\\Post_Processing\\CF\\Misc\\Monitors\\Scenes\\CF_Misc - 0 - y = "+ -max_Misc +" mm.sce"), "CF_Misc", "", false, false);

    xYPlot_4.export(resolvePath("..\\Post_Processing\\CF\\Misc\\Monitors\\Files\\CF_Misc - 0 - y = "+ -max_Misc +" mm mm.csv"), ",");
  
    double misc_it = (max_Misc - min_Misc) / 9;

  for (double i = 1; i < misc_it; i++) { 

    planeSection_4.getOriginCoordinate().setCoordinate(units_0, units_1, units_0, new DoubleVector(new double[] {0.0, (max_Misc-i*9), 0.0}));

  double coordinate = -max_Misc+i*9; 

  xYPlot_4.setTitle("CF_Misc - y = "+ coordinate +" mm");
  
  xYPlot_4.encode(resolvePath("..\\Post_Processing\\CF\\Misc\\Monitors\\Images\\CF_Misc - "+ i +" - y = "+ coordinate +" mm.png"), "png", 1920, 1080);

  xYPlot_4.exportScene(resolvePath("..\\Post_Processing\\CF\\Misc\\Monitors\\Scenes\\CF_Misc - "+ i +" - y = "+ coordinate +" mm.sce"), "CF_Misc", "", false, false);

  xYPlot_4.export(resolvePath("..\\Post_Processing\\CF\\Misc\\Monitors\\Files\\CF_Misc - "+ i +" - y = "+ coordinate +" mm.csv"), ",");

     }
  }
}

  catch (Exception e){
  }*/

}
}




