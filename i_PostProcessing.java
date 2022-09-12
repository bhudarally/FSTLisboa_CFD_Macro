// STAR-CCM+ macro: i_Cp_CoP.java
// Written by STAR-CCM+ 14.04.011
package macro;

import java.util.*;
import star.common.*;
import java.io.*;
import java.nio.*;
import star.base.neo.*;
import star.base.query.*;
import star.base.report.*;
import star.motion.*;
import star.flow.*;
import star.vis.*;

public class i_PostProcessing extends StarMacro {

  BufferedWriter bwout = null;

  public void execute() {
    PrintReports();
    PrintMonitors();
    CP();
    CpTx();
    CpTy();
    CpTz();
    //StaticCpx();
    CfX();
    //CFStream();
    Qcrit();
    Cp2DPlot();
    Cf2DPlot();
  }

  private void PrintReports() {

  try {
  Simulation simulation_0 = getActiveSimulation();
  SimpleAnnotation simpleAnnotation_1 = 
      ((SimpleAnnotation) simulation_0.getAnnotationManager().getObject("Description"));

  SimpleAnnotation simpleAnnotation_2 = 
      ((SimpleAnnotation) simulation_0.getAnnotationManager().getObject("Sim ID"));

  String description = simpleAnnotation_1.getText();
  String sim_id = simpleAnnotation_2.getText();

  bwout = new BufferedWriter(new FileWriter(resolvePath("..\\Post_Processing\\Reports_"+sim_id+".csv")));
  bwout.write(description+" ID ="+sim_id+", \n");            
  bwout.write("Report Name, Value, Unit, \n");

  Collection<Report> reportCollection = simulation_0.getReportManager().getObjects();

// Export AeroCoeffs
for (Report thisReport : reportCollection){

  String fieldLocationName = thisReport.getPresentationName();
  Double fieldValue = thisReport.getReportMonitorValue();
  String fieldUnits = thisReport.getUnits().toString();
  if (fieldLocationName.contains("[Aero]") == true && fieldLocationName.contains("Mean") == true) {
      bwout.write( fieldLocationName + ", " +fieldValue + ", " + fieldUnits +"\n");}}

// Export CoP
for (Report thisReport : reportCollection){

  String fieldLocationName = thisReport.getPresentationName();
  Double fieldValue = thisReport.getReportMonitorValue();
  String fieldUnits = thisReport.getUnits().toString();
  if (fieldLocationName.contains("[Aero] Aero Balance") == true) {
      bwout.write( fieldLocationName + ", " +fieldValue + ", " + fieldUnits +"\n");}}

// Export DownF

for (Report thisReport : reportCollection){
  String fieldLocationName = thisReport.getPresentationName();
  Double fieldValue = thisReport.getReportMonitorValue();
  String fieldUnits = thisReport.getUnits().toString();
  if (fieldLocationName.contains("[DownF]") == true && fieldLocationName.contains("Mean") == true) {
      bwout.write( fieldLocationName + ", " +fieldValue + ", " + fieldUnits +"\n");}}

// Export Drag

for (Report thisReport : reportCollection){
  String fieldLocationName = thisReport.getPresentationName();
  Double fieldValue = thisReport.getReportMonitorValue();
  String fieldUnits = thisReport.getUnits().toString();
  if (fieldLocationName.contains("[Drag]") == true && fieldLocationName.contains("Mean") == true) {
      bwout.write( fieldLocationName + ", " +fieldValue + ", " + fieldUnits +"\n");}}

// Export SideF

for (Report thisReport : reportCollection){
  String fieldLocationName = thisReport.getPresentationName();
  Double fieldValue = thisReport.getReportMonitorValue();
  String fieldUnits = thisReport.getUnits().toString();
  if (fieldLocationName.contains("[SideF]") == true && fieldLocationName.contains("Mean") == true) {
      bwout.write( fieldLocationName + ", " +fieldValue + ", " + fieldUnits +"\n");}}

// Export Cooling

for (Report thisReport : reportCollection){
  String fieldLocationName = thisReport.getPresentationName();
  Double fieldValue = thisReport.getReportMonitorValue();
  String fieldUnits = thisReport.getUnits().toString();
  if (fieldLocationName.contains("[Cooling]") == true && fieldLocationName.contains("Mean") == true) {
      bwout.write( fieldLocationName + ", " +fieldValue + ", " + fieldUnits +"\n");}}

// Export CLoads

for (Report thisReport : reportCollection){
  String fieldLocationName = thisReport.getPresentationName();
  Double fieldValue = thisReport.getReportMonitorValue();
  String fieldUnits = thisReport.getUnits().toString();
  if (fieldLocationName.contains("[CLoad") == true && fieldLocationName.contains("Mean") == true) {
      bwout.write( fieldLocationName + ", " +fieldValue + ", " + fieldUnits +"\n");}}

// Export Moments

for (Report thisReport : reportCollection){
  String fieldLocationName = thisReport.getPresentationName();
  Double fieldValue = thisReport.getReportMonitorValue();
  String fieldUnits = thisReport.getUnits().toString();
  if (fieldLocationName.contains("[M") == true && fieldLocationName.contains("Mean") == true) {
      bwout.write( fieldLocationName + ", " +fieldValue + ", " + fieldUnits +"\n");}}

// Export Extras

for (Report thisReport : reportCollection){
  String fieldLocationName = thisReport.getPresentationName();
  Double fieldValue = thisReport.getReportMonitorValue();
  String fieldUnits = thisReport.getUnits().toString();
  if (fieldLocationName.contains("Time") == true || fieldLocationName.contains("Memory") == true || fieldLocationName.contains("No. cells") == true) {
      bwout.write( fieldLocationName + ", " +fieldValue + ", " + fieldUnits +"\n");}}

  bwout.close();}

    catch (IOException iOException) {}
  }

  private void PrintMonitors() {

  // Export Scene | Image | .CSV //
  Simulation simulation_0 = 
    getActiveSimulation();

  MonitorPlot monitorPlot_0 = 
      ((MonitorPlot) simulation_0.getPlotManager().getPlot("[CLoad]"));
    monitorPlot_0.close();
    monitorPlot_0.exportScene(resolvePath("..\\Post_Processing\\Monitors\\Center of Loads\\Scene_CLoad.sce"), "Downforce", "", false, false);
    monitorPlot_0.encode(resolvePath("..\\Post_Processing\\Monitors\\Center of Loads\\Image_CLoad.jpg"), "png", 1920, 1080);
    monitorPlot_0.export(resolvePath("..\\Post_Processing\\Monitors\\Center of Loads\\Monitor_CLoad.csv"), ",");
  
  MonitorPlot monitorPlot_1 = 
      ((MonitorPlot) simulation_0.getPlotManager().getPlot("[Cooling]"));
    monitorPlot_1.close();
    monitorPlot_1.exportScene(resolvePath("..\\Post_Processing\\Monitors\\Cooling\\Scene_Cooling.sce"), "Downforce", "", false, false);
    monitorPlot_1.encode(resolvePath("..\\Post_Processing\\Monitors\\Cooling\\Image_Cooling.jpg"), "png", 1920, 1080);
    monitorPlot_1.export(resolvePath("..\\Post_Processing\\Monitors\\Cooling\\Monitor_Cooling.csv"), ",");
  
  MonitorPlot monitorPlot_2 = 
      ((MonitorPlot) simulation_0.getPlotManager().getPlot("[DownF]"));
    monitorPlot_2.close();
    monitorPlot_2.exportScene(resolvePath("..\\Post_Processing\\Monitors\\DownF\\Scene_DownF.sce"), "Downforce", "", false, false);
    monitorPlot_2.encode(resolvePath("..\\Post_Processing\\Monitors\\DownF\\Image_DownF.jpg"), "png", 1920, 1080);
    monitorPlot_2.export(resolvePath("..\\Post_Processing\\Monitors\\DownF\\Monitor_DownF.csv"), ",");
  
  MonitorPlot monitorPlot_3 = 
      ((MonitorPlot) simulation_0.getPlotManager().getPlot("[Drag]"));
    monitorPlot_3.close();
    monitorPlot_3.exportScene(resolvePath("..\\Post_Processing\\Monitors\\Drag\\Scene_Drag.sce"), "Downforce", "", false, false);
    monitorPlot_3.encode(resolvePath("..\\Post_Processing\\Monitors\\Drag\\Image_Drag.jpg"), "png", 1920, 1080);
    monitorPlot_3.export(resolvePath("..\\Post_Processing\\Monitors\\Drag\\Monitor_Drag.csv"), ",");
  
  MonitorPlot monitorPlot_5 = 
      ((MonitorPlot) simulation_0.getPlotManager().getPlot("[SideF]"));
    monitorPlot_5.close();
    monitorPlot_5.exportScene(resolvePath("..\\Post_Processing\\Monitors\\SideF\\Scene_SideF.sce"), "Downforce", "", false, false);
    monitorPlot_5.encode(resolvePath("..\\Post_Processing\\Monitors\\SideF\\Image_SideF.jpg"), "png", 1920, 1080);
    monitorPlot_5.export(resolvePath("..\\Post_Processing\\Monitors\\SideF\\Monitor_SideF.csv"), ",");
  
  MonitorPlot monitorPlot_6 = 
      ((MonitorPlot) simulation_0.getPlotManager().getPlot("[Time] Solver Iteration"));
    monitorPlot_6.close();
    monitorPlot_6.exportScene(resolvePath("..\\Post_Processing\\Monitors\\Solver Time\\Scene_Time.sce"), "Downforce", "", false, false);
    monitorPlot_6.encode(resolvePath("..\\Post_Processing\\Monitors\\Solver Time\\Image_Time.jpg"), "png", 1920, 1080);
    monitorPlot_6.export(resolvePath("..\\Post_Processing\\Monitors\\Solver Time\\Monitor_Time.csv"), ",");
  
  MonitorPlot monitorPlot_7 = 
      ((MonitorPlot) simulation_0.getPlotManager().getPlot("Residuals"));
    monitorPlot_7.close();
    monitorPlot_7.exportScene(resolvePath("..\\Post_Processing\\Monitors\\Residuals\\Scene_Residuals.sce"), "Downforce", "", false, false);
    monitorPlot_7.encode(resolvePath("..\\Post_Processing\\Monitors\\Residuals\\Image_Residuals.jpg"), "png", 1920, 1080);
    monitorPlot_7.export(resolvePath("..\\Post_Processing\\Monitors\\Residuals\\Monitor_Residuals.csv"), ",");
  //}
  }

  private void CP() {

  Simulation simulation_0 = 
    getActiveSimulation();
    
  double Roll = simulation_0.getReportManager().getReport("Roll [deg]").getReportMonitorValue();
  double Yaw = simulation_0.getReportManager().getReport("Yaw [rad]").getReportMonitorValue();
  double CornerRadius = simulation_0.getReportManager().getReport("Corner Radius [m]").getReportMonitorValue();

  simulation_0.getSceneManager().createEmptyScene("Scene");  
    
  Scene scene_0 = 
      simulation_0.getSceneManager().getScene("Scene 1");
    scene_0.initializeAndWait();  
  SceneUpdate sceneUpdate_0 = 
      scene_0.getSceneUpdate();
  scene_0.setPresentationName("CP");  
  scene_0.setAspectRatio(AspectRatioEnum.RATIO_16_9);
  scene_0.setAxesViewport(new DoubleVector(new double[] {0.88, 0.0, 1.0, 0.14}));
  
  LogoAnnotation logoAnnotation_0 = 
      ((LogoAnnotation) simulation_0.getAnnotationManager().getObject("Logo"));
    scene_0.getAnnotationPropManager().removePropsForAnnotations(logoAnnotation_0);

    ImageAnnotation2D imageAnnotation2D_0 = 
      ((ImageAnnotation2D) simulation_0.getAnnotationManager().getObject("Line"));
    ImageAnnotationProp2D imageAnnotationProp2D_0 = 
      (ImageAnnotationProp2D) scene_0.getAnnotationPropManager().createPropForAnnotation(imageAnnotation2D_0);
    SimpleAnnotation simpleAnnotation_0 = 
      ((SimpleAnnotation) simulation_0.getAnnotationManager().getObject("Setup column 1"));
    SimpleAnnotationProp simpleAnnotationProp_0 = 
      (SimpleAnnotationProp) scene_0.getAnnotationPropManager().createPropForAnnotation(simpleAnnotation_0);
    SimpleAnnotation simpleAnnotation_1 = 
      ((SimpleAnnotation) simulation_0.getAnnotationManager().getObject("Setup column 2"));
    SimpleAnnotationProp simpleAnnotationProp_1 = 
      (SimpleAnnotationProp) scene_0.getAnnotationPropManager().createPropForAnnotation(simpleAnnotation_1);
    SimpleAnnotation simpleAnnotation_2 = 
      ((SimpleAnnotation) simulation_0.getAnnotationManager().getObject("Description"));
    SimpleAnnotationProp simpleAnnotationProp_2 = 
      (SimpleAnnotationProp) scene_0.getAnnotationPropManager().createPropForAnnotation(simpleAnnotation_2);
    ImageAnnotation2D imageAnnotation2D_1 = 
      ((ImageAnnotation2D) simulation_0.getAnnotationManager().getObject("FST Black Icon"));
    ImageAnnotationProp2D imageAnnotationProp2D_1 = 
      (ImageAnnotationProp2D) scene_0.getAnnotationPropManager().createPropForAnnotation(imageAnnotation2D_1);
  scene_0.setBackgroundColorMode(BackgroundColorMode.SOLID);
  scene_0.close();
  
  ScalarDisplayer scalarDisplayer_0 = 
      scene_0.getDisplayerManager().createScalarDisplayer("Scalar");
    scalarDisplayer_0.initialize();
    scalarDisplayer_0.setPresentationName("CP");
  
  Region region_0 = 
      simulation_0.getRegionManager().getRegion("Fluid");
  
  scalarDisplayer_0.getInputParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, InterfaceBoundary.class), new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.DoesNotContain, "Domain"), new NamePredicate(NameOperator.DoesNotContain, "FAN"), new NamePredicate(NameOperator.DoesNotContain, "RADIATOR"))))), Query.STANDARD_MODIFIERS));
  
  if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
      SymmetricRepeat symmetricRepeat_0 = 
      ((SymmetricRepeat) simulation_0.getTransformManager().getObject("Domain.Wall_Inner 1"));
    scalarDisplayer_0.setVisTransform(symmetricRepeat_0);}
  
  try {
    UserFieldFunction userFieldFunction_0 = 
      ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("Cp"));
    scalarDisplayer_0.getScalarDisplayQuantity().setFieldFunction(userFieldFunction_0);
  }
  catch (Exception e){
    UserFieldFunction userFieldFunction_0 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();
    userFieldFunction_0.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);
    userFieldFunction_0.setPresentationName("Cp");
    userFieldFunction_0.setFunctionName("Cp");
  
    if ( CornerRadius == 0) {
    userFieldFunction_0.setDefinition("(${PressureMonitor}-${Pressure Avg. Inlet})/(0.5*1.225*pow(${car_velocity[ms-1]},2))"); 
    } else {
    userFieldFunction_0.setDefinition("(${PressureMonitor}-${Pressure Avg. Inlet})/(0.5*1.225*pow(${Angular_Velocity}*($$Position(@CoordinateSystem(\"Laboratory.Air Orientation - Car.Air Center\"))[0]),2))");} 
  }
  UserFieldFunction userFieldFunction_0 = 
      ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("Cp"));
  scalarDisplayer_0.getScalarDisplayQuantity().setFieldFunction(userFieldFunction_0);
  
  
  //{ // Colorbar
  try {
  UserLookupTable userLookupTable_0 = 
      ((UserLookupTable) simulation_0.get(LookupTableManager.class).getObject("Carreira_CP_-3+1"));
  }
  catch (Exception e){
    
  UserLookupTable userLookupTable_0 = 
      simulation_0.get(LookupTableManager.class).createLookupTable();
    userLookupTable_0.setPresentationName("Carreira_CP_-3+1");  
  userLookupTable_0.setColorMap(new ColorMap(new DoubleVector(new double[] {0.0, 1.0, 0.0, 1.0, 0.19, 0.0, 0.0, 0.9215686274509803, 0.259, 0.2478402969473144, 0.0, 0.892816517288296, 0.436, 0.0, 1.0, 1.0, 0.5, 0.3258965792972184, 0.9454054260487254, 0.9454054260487254, 0.5860655737704918, 0.0, 1.0, 0.0, 0.725, 0.6862745098039216, 0.6862745098039216, 0.6862745098039216, 0.774, 0.6862745098039216, 0.6862745098039216, 0.6862745098039216, 0.8688524590163934, 1.0, 1.0, 0.0, 0.975, 1.0, 0.0, 0.0, 1.0, 0.5019607843137255, 0.0, 0.0}), new DoubleVector(new double[] {0.0, 1.0, 1.0, 1.0}), 0));
  }
  //}
  
  UserLookupTable userLookupTable_0 = 
      ((UserLookupTable) simulation_0.get(LookupTableManager.class).getObject("Carreira_CP_-3+1"));
  
  Legend legend_0 = 
      scalarDisplayer_0.getLegend();
    legend_0.setLookupTable(userLookupTable_0);
  legend_0.setLevels(54);
    legend_0.setWidth(0.21);
    legend_0.setFontString("Arial-Bold");
    legend_0.setShadow(false);
    legend_0.setHeight(0.025);
    legend_0.setTitleHeight(0.03);
    legend_0.setLabelHeight(0.02);
    legend_0.setPositionCoordinate(new DoubleVector(new double[] {0.02, 0.03}));
    legend_0.setLabelFormat("%1.1f");
    legend_0.setRampType(RampType.LINEAR);
    legend_0.setNumberOfLabels(5);
  
  scalarDisplayer_0.setFillMode(ScalarFillMode.NODE_FILLED);
    scalarDisplayer_0.getScalarDisplayQuantity().setRange(new DoubleVector(new double[] {-3, 1}));
    scalarDisplayer_0.getScalarDisplayQuantity().setClip(ClipMode.NONE);
  
    scene_0.export3DSceneFileAndWait(resolvePath("..\\Post_Processing\\CP\\Scene_CP.sce"), "CP", simpleAnnotation_0.getText(), false, true);
  
  CurrentView currentView_0 = 
      scene_0.getCurrentView();
  
  VisView visView_0 = 
      ((VisView) simulation_0.getViewManager().getObject("bottom"));
    currentView_0.setView(visView_0);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\CP\\bottom.jpg"), 1, 1920, 1080, true, false);
  
  VisView visView_1 = 
      ((VisView) simulation_0.getViewManager().getObject("bottom_front_left"));
    currentView_0.setView(visView_1);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\CP\\bottom_front_left.jpg"), 1, 1920, 1080, true, false);
  
  VisView visView_2 = 
      ((VisView) simulation_0.getViewManager().getObject("bottom_front_right"));
    currentView_0.setView(visView_2);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\CP\\bottom_front_right.jpg"), 1, 1920, 1080, true, false);
  
  VisView visView_3 = 
      ((VisView) simulation_0.getViewManager().getObject("bottom_rear_left"));
    currentView_0.setView(visView_3);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\CP\\bottom_rear_left.jpg"), 1, 1920, 1080, true, false);
  
  VisView visView_4 = 
      ((VisView) simulation_0.getViewManager().getObject("bottom_rear_right"));
    currentView_0.setView(visView_4);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\CP\\bottom_rear_right.jpg"), 1, 1920, 1080, true, false);
  
  VisView visView_5 = 
      ((VisView) simulation_0.getViewManager().getObject("front"));
    currentView_0.setView(visView_5);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\CP\\front.jpg"), 1, 1920, 1080, true, false);
  
  VisView visView_6 = 
      ((VisView) simulation_0.getViewManager().getObject("left"));
    currentView_0.setView(visView_6);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\CP\\left.jpg"), 1, 1920, 1080, true, false);
  
  VisView visView_7 = 
      ((VisView) simulation_0.getViewManager().getObject("rear"));
    currentView_0.setView(visView_7);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\CP\\rear.jpg"), 1, 1920, 1080, true, false);
  
  VisView visView_8 = 
      ((VisView) simulation_0.getViewManager().getObject("right"));
    currentView_0.setView(visView_8);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\CP\\right.jpg"), 1, 1920, 1080, true, false);
  
  VisView visView_9 = 
      ((VisView) simulation_0.getViewManager().getObject("top"));
    currentView_0.setView(visView_9);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\CP\\top.jpg"), 1, 1920, 1080, true, false);
  
  VisView visView_10 = 
      ((VisView) simulation_0.getViewManager().getObject("top_front_left"));
    currentView_0.setView(visView_10);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\CP\\top_front_left.jpg"), 1, 1920, 1080, true, false);
  
  VisView visView_11 = 
      ((VisView) simulation_0.getViewManager().getObject("top_front_right"));
    currentView_0.setView(visView_11);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\CP\\top_front_right.jpg"), 1, 1920, 1080, true, false);
  
  VisView visView_12 = 
      ((VisView) simulation_0.getViewManager().getObject("top_rear_left"));
    currentView_0.setView(visView_12);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\CP\\top_rear_left.jpg"), 1, 1920, 1080, true, false);
  
  VisView visView_13 = 
      ((VisView) simulation_0.getViewManager().getObject("top_rear_right"));
    currentView_0.setView(visView_13);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\CP\\top_rear_right.jpg"), 1, 1920, 1080, true, false);

  }

  private void CpTx() {

  Simulation simulation_0 = 
      getActiveSimulation();  
    
  double Roll = simulation_0.getReportManager().getReport("Roll [deg]").getReportMonitorValue();
  double Yaw = simulation_0.getReportManager().getReport("Yaw [rad]").getReportMonitorValue();
  double CornerRadius = simulation_0.getReportManager().getReport("Corner Radius [m]").getReportMonitorValue();

  // Velocity Avg. //

  try{
  UserFieldFunction userFieldFunction_0 = 
    ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("v_avg"));
  }
  catch (Exception e){
  UserFieldFunction userFieldFunction_111 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();
    userFieldFunction_111.getTypeOption().setSelected(FieldFunctionTypeOption.Type.VECTOR);
    userFieldFunction_111.setPresentationName("Velocity Avg.");
    userFieldFunction_111.setFunctionName("v_avg");
    Units units_2 = 
      simulation_0.getUnitsManager().getPreferredUnits(new IntVector(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}));
    userFieldFunction_111.setDefinition("[${V[i]Monitor},${V[j]Monitor},${V[k]Monitor}]");
  }

  Units units_0 = 
      ((Units) simulation_0.getUnitsManager().getObject("m"));
  Units units_1 = 
      ((Units) simulation_0.getUnitsManager().getObject("mm"));

  // Plane X //

  try {
  PlaneSection planeSection_0 = 
      ((PlaneSection) simulation_0.getPartManager().getObject("CPTsectionx"));
  planeSection_0.getOrientationCoordinate().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {1.0, 0.0, 0.0}));
  planeSection_0.getOriginCoordinate().setCoordinate(units_1, units_0, units_0, new DoubleVector(new double[] {1000.0, 0.0, 0.0}));
  }
  catch (Exception e){
  PlaneSection planeSection_0 = 
      (PlaneSection) simulation_0.getPartManager().createImplicitPart(new NeoObjectVector(new Object[] {}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), new DoubleVector(new double[] {0.0, 0.0, 0.0}), 0, 1, new DoubleVector(new double[] {0.0}));
  planeSection_0.setPresentationName("CPTsectionx");
  planeSection_0.getInputParts().setQuery(new Query(new TypePredicate(TypeOperator.Is, Region.class), Query.STANDARD_MODIFIERS));
  planeSection_0.getOrientationCoordinate().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {1.0, 0.0, 0.0}));
  planeSection_0.getOriginCoordinate().setCoordinate(units_1, units_0, units_0, new DoubleVector(new double[] {1000.0, 0.0, 0.0}));
  }

  PlaneSection planeSection_0 = 
      ((PlaneSection) simulation_0.getPartManager().getObject("CPTsectionx"));

    simulation_0.getSceneManager().createEmptyScene("Scene");
  Scene scene_0 = 
      simulation_0.getSceneManager().getScene("Scene 1");
  scene_0.initializeAndWait();  
  SceneUpdate sceneUpdate_0 = 
      scene_0.getSceneUpdate();
  scene_0.setPresentationName("CpTx");  
  scene_0.setAspectRatio(AspectRatioEnum.RATIO_16_9);
  scene_0.setAxesVisible(false);
  
  LogoAnnotation logoAnnotation_0 = 
      ((LogoAnnotation) simulation_0.getAnnotationManager().getObject("Logo"));
    scene_0.getAnnotationPropManager().removePropsForAnnotations(logoAnnotation_0);

    ImageAnnotation2D imageAnnotation2D_0 = 
      ((ImageAnnotation2D) simulation_0.getAnnotationManager().getObject("Line"));
    ImageAnnotationProp2D imageAnnotationProp2D_0 = 
      (ImageAnnotationProp2D) scene_0.getAnnotationPropManager().createPropForAnnotation(imageAnnotation2D_0);
    SimpleAnnotation simpleAnnotation_0 = 
      ((SimpleAnnotation) simulation_0.getAnnotationManager().getObject("Setup column 1"));
    SimpleAnnotationProp simpleAnnotationProp_0 = 
      (SimpleAnnotationProp) scene_0.getAnnotationPropManager().createPropForAnnotation(simpleAnnotation_0);
    SimpleAnnotation simpleAnnotation_1 = 
      ((SimpleAnnotation) simulation_0.getAnnotationManager().getObject("Setup column 2"));
    SimpleAnnotationProp simpleAnnotationProp_1 = 
      (SimpleAnnotationProp) scene_0.getAnnotationPropManager().createPropForAnnotation(simpleAnnotation_1);
    SimpleAnnotation simpleAnnotation_2 = 
      ((SimpleAnnotation) simulation_0.getAnnotationManager().getObject("Description"));
    SimpleAnnotationProp simpleAnnotationProp_2 = 
      (SimpleAnnotationProp) scene_0.getAnnotationPropManager().createPropForAnnotation(simpleAnnotation_2);
    ImageAnnotation2D imageAnnotation2D_10 = 
      ((ImageAnnotation2D) simulation_0.getAnnotationManager().getObject("FST Black Icon"));
    ImageAnnotationProp2D imageAnnotationProp2D_10 = 
      (ImageAnnotationProp2D) scene_0.getAnnotationPropManager().createPropForAnnotation(imageAnnotation2D_10);
    imageAnnotationProp2D_10.setPosition(new DoubleVector(new double[] {0.6, 0.015, 0.0}));
  scene_0.setBackgroundColorMode(BackgroundColorMode.SOLID);
  scene_0.close();
  
  try {
      SimpleAnnotation simpleAnnotation_3 = 
      ((SimpleAnnotation) simulation_0.getAnnotationManager().getObject("CoordinateX"));
      simpleAnnotation_3.setText("X = 1000.0 mm");
      SimpleAnnotationProp simpleAnnotationProp_3 = 
      (SimpleAnnotationProp) scene_0.getAnnotationPropManager().createPropForAnnotation(simpleAnnotation_3);
  }
  catch (Exception e){
    SimpleAnnotation simpleAnnotation_3 = 
      simulation_0.getAnnotationManager().createAnnotation(SimpleAnnotation.class);
    simpleAnnotation_3.setPresentationName("CoordinateX");
    simpleAnnotation_3.setShadow(false);
    simpleAnnotation_3.setText("X = 1000.0 mm");
    simpleAnnotation_3.setFontString("Arial-Bold");
    simpleAnnotation_3.setDefaultHeight(0.025);
    simpleAnnotation_3.setDefaultPosition(new DoubleVector(new double[] {0.9, 0.045, 0.0}));
  SimpleAnnotationProp simpleAnnotationProp_3 = 
      (SimpleAnnotationProp) scene_0.getAnnotationPropManager().createPropForAnnotation(simpleAnnotation_3);
  }

  SimpleAnnotation simpleAnnotation_3 = 
      ((SimpleAnnotation) simulation_0.getAnnotationManager().getObject("CoordinateX"));

  try {
      ImageAnnotation2D imageAnnotation2D_1 = 
      ((ImageAnnotation2D) simulation_0.getAnnotationManager().getObject("Car-Side"));
  }
  catch (Exception e){
    final String folder = resolveWorkPath();
    ImageAnnotation2D imageAnnotation2D_1 = 
        simulation_0.getAnnotationManager().createAnnotation(ImageAnnotation2D.class);
    imageAnnotation2D_1.setFilePath("..\\Modules_Macro\\Library\\BlackSide.png");
    imageAnnotation2D_1.setPresentationName("Car-Side");
    imageAnnotation2D_1.setDefaultWidthStretchFactor(1.0);
    imageAnnotation2D_1.setDefaultHeight(0.225);
    imageAnnotation2D_1.setDefaultPosition(new DoubleVector(new double[] {0.0, 0.125, 0.0}));
  }
  
  try {

    XYPlot xYPlot_0 = 
      ((XYPlot) simulation_0.getPlotManager().getPlot("PlaneX - Car"));

    PlotImage plotImage_0 = 
      ((PlotImage) simulation_0.getAnnotationManager().getObject("Car X Plane"));

    PlotImageAnnotationProp plotImageAnnotationProp_3 = 
      (PlotImageAnnotationProp) scene_0.getAnnotationPropManager().createPropForAnnotation(plotImage_0);
    plotImageAnnotationProp_3.setScaleComponents(false);
    plotImageAnnotationProp_3.setLocation(DisplayLocationMode.BACKGROUND);

  }
  catch (Exception e){
    XYPlot xYPlot_0 = 
      simulation_0.getPlotManager().createPlot(XYPlot.class);
    xYPlot_0.open();
    PlotUpdate plotUpdate_0 = 
      xYPlot_0.getPlotUpdate();
    xYPlot_0.setPresentationName("PlaneX - Car");
  xYPlot_0.close();
  xYPlot_0.getParts().setObjects(planeSection_0);

  Cartesian2DAxisManager cartesian2DAxisManager_0 = 
      ((Cartesian2DAxisManager) xYPlot_0.getAxisManager());
    Cartesian2DAxis cartesian2DAxis_0 = 
      ((Cartesian2DAxis) cartesian2DAxisManager_0.getAxis("Bottom Axis"));
    cartesian2DAxis_0.setPosition(Cartesian2DAxis.Position.Right);

  YAxisType yAxisType_0 = 
      ((YAxisType) xYPlot_0.getYAxes().getAxisType("Y Type 1"));
  
  if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
  cartesian2DAxis_0.setMinimum(-15.0);
    cartesian2DAxis_0.setMaximum(12.5);
    InternalDataSet internalDataSet_0 = 
      ((InternalDataSet) yAxisType_0.getDataSetManager().getDataSet("CPTsectionx"));
  internalDataSet_0.setXScale(0.8);
  } else {
  cartesian2DAxis_0.setMinimum(-40.0);
    cartesian2DAxis_0.setMaximum(50.0);
  InternalDataSet internalDataSet_0 = 
      ((InternalDataSet) yAxisType_0.getDataSetManager().getDataSet("CPTsectionx"));
  internalDataSet_0.setXScale(1.3);
  }
  
    Cartesian2DAxis cartesian2DAxis_1 = 
      ((Cartesian2DAxis) cartesian2DAxisManager_0.getAxis("Left Axis"));
    cartesian2DAxis_1.setPosition(Cartesian2DAxis.Position.Bottom);
  cartesian2DAxis_1.setMinimum(-4.0);
  cartesian2DAxis_1.setMaximum(9.0);

    AxisType axisType_0 = 
      xYPlot_0.getXAxisType();
    axisType_0.getDirectionVector().setComponents(0.0, 1.0, 0.0);
  axisType_0.getDirectionVector().setUnits(units_0);

    FieldFunctionUnits fieldFunctionUnits_0 = 
      yAxisType_0.getScalarFunction();

    PrimitiveFieldFunction primitiveFieldFunction_0 = 
      ((PrimitiveFieldFunction) simulation_0.getFieldFunctionManager().getFunction("Position"));
    VectorComponentFieldFunction vectorComponentFieldFunction_0 = 
      ((VectorComponentFieldFunction) primitiveFieldFunction_0.getComponentFunction(0));
    fieldFunctionUnits_0.setFieldFunction(vectorComponentFieldFunction_0);

    InternalDataSet internalDataSet_0 = 
      ((InternalDataSet) yAxisType_0.getDataSetManager().getDataSet("CPTsectionx"));
    internalDataSet_0.setShowLegendEntry(false);
    LineStyle lineStyle_0 = 
      internalDataSet_0.getLineStyle();
    lineStyle_0.getLinePatternOption().setSelected(LinePatternOption.Type.SOLID);
    SymbolStyleWithSpacing symbolStyleWithSpacing_0 = 
      internalDataSet_0.getSymbolStyle();
    symbolStyleWithSpacing_0.getSymbolShapeOption().setSelected(SymbolShapeOption.Type.NONE);
    lineStyle_0.setWidth(3);
    cartesian2DAxis_1.setVisible(false);
    cartesian2DAxis_0.setVisible(false);
    MultiColLegend multiColLegend_0 = 
      xYPlot_0.getLegend();
    multiColLegend_0.setVisible(false);
    xYPlot_0.setTitle("");
    xYPlot_0.setAspectRatio(AspectRatioEnum.RATIO_16_9);
    ImageAnnotation2D imageAnnotation2D_1 = 
      ((ImageAnnotation2D) simulation_0.getAnnotationManager().getObject("Car-Side"));
    ImageAnnotationProp2D imageAnnotationProp2D_1 = 
      (ImageAnnotationProp2D) xYPlot_0.getAnnotationPropManager().createPropForAnnotation(imageAnnotation2D_1);
  imageAnnotationProp2D_1.setWidthStretchFactor(1.0);
  imageAnnotationProp2D_1.setPosition(new DoubleVector(new double[] {0.1669, 0.3417, 0.0}));
  imageAnnotationProp2D_1.setLocation(DisplayLocationMode.BACKGROUND);
  
  PlotImage plotImage_0 = 
      simulation_0.getAnnotationManager().createAnnotation(PlotImage.class);
    plotImage_0.setPresentationName("Car X Plane");
    plotImage_0.setViewObject(xYPlot_0);
    plotImage_0.setDefaultScaleComponents(false);
    plotImage_0.setDefaultPosition(new DoubleVector(new double[] {0.68, -0.162, 0.0}));

    PlotImageAnnotationProp plotImageAnnotationProp_3 = 
      (PlotImageAnnotationProp) scene_0.getAnnotationPropManager().createPropForAnnotation(plotImage_0);
    plotImageAnnotationProp_3.setScaleComponents(false);
    plotImageAnnotationProp_3.setLocation(DisplayLocationMode.BACKGROUND);

  }

  XYPlot xYPlot_0 = 
      ((XYPlot) simulation_0.getPlotManager().getPlot("PlaneX - Car"));

  scene_0.setBackgroundColorMode(BackgroundColorMode.SOLID);
  scene_0.close();
  
  ScalarDisplayer scalarDisplayer_0 = 
      scene_0.getDisplayerManager().createScalarDisplayer("Scalar");
    scalarDisplayer_0.initialize();
    scalarDisplayer_0.setPresentationName("CpTx");
    scalarDisplayer_0.getInputParts().setObjects(planeSection_0);
  
  ClipPlane clipPlane_0 = 
      ((ClipPlane) scene_0.getPlaneManager().getPlane("Plane 1"));
    ClipPlane clipPlane_1 = 
      ((ClipPlane) scene_0.getPlaneManager().getPlane("Plane 2"));
    ClipPlane clipPlane_2 = 
      ((ClipPlane) scene_0.getPlaneManager().getPlane("Plane 3"));
    clipPlane_0.getOriginCoordinate().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {0.0, 0.0, 1.8}));
    clipPlane_1.getOriginCoordinate().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {0.0, 1.5, 0.0}));
    clipPlane_2.getOriginCoordinate().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {0.0, -1.5, 0.0}));
    clipPlane_0.setNormal(new DoubleVector(new double[] {0.0, 0.0, -1.0}));
    clipPlane_1.setNormal(new DoubleVector(new double[] {0.0, -1.0, 0.0}));
    clipPlane_2.setNormal(new DoubleVector(new double[] {0.0, 1.0, 0.0}));
    clipPlane_0.setEnabled(true);
    clipPlane_1.setEnabled(true);
    clipPlane_2.setEnabled(true);
  
  if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
      SymmetricRepeat symmetricRepeat_0 = 
      ((SymmetricRepeat) simulation_0.getTransformManager().getObject("Domain.Wall_Inner 1"));
    scalarDisplayer_0.setVisTransform(symmetricRepeat_0);}
  
  try {
    UserFieldFunction userFieldFunction_0 = 
      ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("CpT"));
    scalarDisplayer_0.getScalarDisplayQuantity().setFieldFunction(userFieldFunction_0);

  }
  catch (Exception e){
    UserFieldFunction userFieldFunction_0 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();
    userFieldFunction_0.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);
    userFieldFunction_0.setFunctionName("CpT");
    userFieldFunction_0.setPresentationName("CpT");
  
    if ( CornerRadius == 0) {
    userFieldFunction_0.setDefinition("(${TotalPressureMonitor}-${Pressure Avg. Inlet})/(0.5*1.225*pow(${car_velocity[ms-1]},2))"); 
    } else {
    userFieldFunction_0.setDefinition("(${TotalPressureMonitor}-${Pressure Avg. Inlet})/(0.5*1.225*pow(${Angular_Velocity}*($$Position(@CoordinateSystem(\"Laboratory.Air Orientation - Car.Air Center\"))[0]),2))");} 
    }
  UserFieldFunction userFieldFunction_0 = 
      ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("CpT"));
  scalarDisplayer_0.getScalarDisplayQuantity().setFieldFunction(userFieldFunction_0);

  try {
  UserLookupTable userLookupTable_0 = 
      ((UserLookupTable) simulation_0.get(LookupTableManager.class).getObject("Carreira_CPT_-1+1"));
  }
  catch (Exception e){
    
  UserLookupTable userLookupTable_0 = 
      simulation_0.get(LookupTableManager.class).createLookupTable();
    userLookupTable_0.setPresentationName("Carreira_CPT_-1+1"); 
  userLookupTable_0.setColorMap(new ColorMap(new DoubleVector(new double[] {0.0, 0.0, 0.0, 0.0, 0.072, 1.0, 0.4117647058823529, 0.7058823529411765, 0.12, 1.0, 0.4117647058823529, 0.7058823529411765, 0.24, 0.0, 0.0, 1.0, 0.26, 0.0, 0.0, 1.0, 0.49, 0.0, 1.0, 1.0, 0.51, 0.0, 1.0, 1.0, 0.627, 0.0, 1.0, 0.0, 0.75, 1.0, 1.0, 0.0, 0.9640287769784173, 1.0, 0.0, 0.0, 0.9892086330935251, 1.0, 0.0, 0.0}), new DoubleVector(new double[] {0.0, 1.0, 1.0, 1.0}), 0));
  }
  
  /*BlueRedLookupTable blueRedLookupTable_0 = 
      ((BlueRedLookupTable) simulation_0.get(LookupTableManager.class).getObject("blue-red"));*/

  UserLookupTable userLookupTable_0 = 
      ((UserLookupTable) simulation_0.get(LookupTableManager.class).getObject("Carreira_CPT_-1+1"));
  
  Legend legend_0 = 
      scalarDisplayer_0.getLegend();
    legend_0.setLookupTable(userLookupTable_0);
  legend_0.setLevels(54);
    legend_0.setWidth(0.21);
    legend_0.setFontString("Arial-Bold");
    legend_0.setShadow(false);
    legend_0.setHeight(0.025);
    legend_0.setTitleHeight(0.03);
    legend_0.setLabelHeight(0.02);
    legend_0.setPositionCoordinate(new DoubleVector(new double[] {0.02, 0.03}));
    legend_0.setLabelFormat("%1.1f");
    legend_0.setRampType(RampType.LINEAR);
    legend_0.setNumberOfLabels(5);
  
    scalarDisplayer_0.setFillMode(ScalarFillMode.NODE_SMOOTH);
    scalarDisplayer_0.getInterpolationOption().setSelected(ScalarInterpolationOption.Type.BANDED);
    scalarDisplayer_0.getScalarDisplayQuantity().setRange(new DoubleVector(new double[] {-1.0, 1.0}));
    scalarDisplayer_0.getScalarDisplayQuantity().setClip(ClipMode.NONE);
  
  VectorDisplayer vectorDisplayer_0 = 
      scene_0.getDisplayerManager().createVectorDisplayer("Vector");
  vectorDisplayer_0.initialize();
    Legend legend_1 = 
      vectorDisplayer_0.getLegend();
    BlueRedLookupTable blueRedLookupTable_1 = 
      ((BlueRedLookupTable) simulation_0.get(LookupTableManager.class).getObject("blue-red"));
    legend_1.setLookupTable(blueRedLookupTable_1);
    vectorDisplayer_0.setPresentationName("LIC");
  vectorDisplayer_0.setOpacity(0.4);
    vectorDisplayer_0.setDisplayMode(VectorDisplayMode.VECTOR_DISPLAY_MODE_LIC);
    vectorDisplayer_0.setColorMode(VectorColorMode.SCALAR);
    vectorDisplayer_0.getInputParts().setObjects(planeSection_0);
  
  if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
  SymmetricRepeat symmetricRepeat_1 = 
      ((SymmetricRepeat) simulation_0.getTransformManager().getObject("Domain.Wall_Inner 1"));
    vectorDisplayer_0.setVisTransform(symmetricRepeat_1);}
  
    UserFieldFunction userFieldFunction_1 = 
      ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("v_avg"));
    vectorDisplayer_0.getVectorDisplayQuantity().setFieldFunction(userFieldFunction_1); 
    
    vectorDisplayer_0.getColoringScalar().setFieldFunction(userFieldFunction_0);
  LICSettings lICSettings_0 = 
      vectorDisplayer_0.getLICSettings();
    lICSettings_0.setNumberOfSteps(40);
    lICSettings_0.setIntensity(1.0);
    vectorDisplayer_0.getColoringScalar().setRange(new DoubleVector(new double[] {-1.0, 1.0}));
    vectorDisplayer_0.getColoringScalar().setClip(ClipMode.NONE);
  
  legend_1.setVisible(false);

  /*PartDisplayer partDisplayer_10 = 
      scene_0.getDisplayerManager().createPartDisplayer("Outline", -1, 4);
    PlaneSection planeSection_10 = 
      ((PlaneSection) simulation_0.getPartManager().getObject("CPTsectionx"));
      partDisplayer_10.getInputParts().setObjects(planeSection_10);*/
  
  CurrentView currentView_0 = 
      scene_0.getCurrentView();
  
  scene_0.setViewOrientation(new DoubleVector(new double[] {1.0, -0.0, -0.0}), new DoubleVector(new double[] {0.0, 0.0, 1.0}));

  VisView visView_0 = 
      ((VisView) simulation_0.getViewManager().getObject("front"));
    currentView_0.setView(visView_0);
  scene_0.printAndWait(resolvePath("..\\Post_Processing\\CpTx\\0 CpTx1000mm.jpg"), 1, 1920, 1080, true, false);
  
  for (int i = 1; i < 220; i++) {   // CHANGE I BACK TO 220
    planeSection_0.getOriginCoordinate().setCoordinate(units_1, units_0, units_0, new DoubleVector(new double[] {(1000-i*15), 0.0, 0.0}));
  currentView_0.setView(visView_0);
  double coordinate = 1000 - i*15; 
  simpleAnnotation_3.setText("X = "+ coordinate +" mm");
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\CpTx\\"+ i +" CpTx"+ coordinate +"mm.jpg"), 1, 1920, 1080, true, false);
  }

  try {
    ((ClientServerObjectGroup) simulation_0.getPlotManager().getGroupsManager().getObject("Plane Aux")).setPresentationName("Plane Aux");
  }
  catch (Exception e){
  simulation_0.getPlotManager().getGroupsManager().createGroup("Plane Aux");
}
  
  ((ClientServerObjectGroup) simulation_0.getPlotManager().getGroupsManager().getObject("Plane Aux")).getGroupsManager().groupObjects("Plane Aux", new NeoObjectVector(new Object[] {xYPlot_0}), true);
  
  }

  private void CpTy() {

  Simulation simulation_0 = 
      getActiveSimulation();  
    
  double Roll = simulation_0.getReportManager().getReport("Roll [deg]").getReportMonitorValue();
  double Yaw = simulation_0.getReportManager().getReport("Yaw [rad]").getReportMonitorValue();
  double CornerRadius = simulation_0.getReportManager().getReport("Corner Radius [m]").getReportMonitorValue();

  // Velocity Avg. //

  try{
  UserFieldFunction userFieldFunction_0 = 
    ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("v_avg"));
  }
  catch (Exception e){
  UserFieldFunction userFieldFunction_111 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();
    userFieldFunction_111.getTypeOption().setSelected(FieldFunctionTypeOption.Type.VECTOR);
    userFieldFunction_111.setPresentationName("Velocity Avg.");
    userFieldFunction_111.setFunctionName("v_avg");
    Units units_2 = 
      simulation_0.getUnitsManager().getPreferredUnits(new IntVector(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}));
    userFieldFunction_111.setDefinition("[${V[i]Monitor},${V[j]Monitor},${V[k]Monitor}]");
  }

  Units units_0 = 
      ((Units) simulation_0.getUnitsManager().getObject("m"));
  Units units_1 = 
      ((Units) simulation_0.getUnitsManager().getObject("mm"));

  // Plane X //

  try {
  PlaneSection planeSection_0 = 
      ((PlaneSection) simulation_0.getPartManager().getObject("CPTsectiony"));
  if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
  planeSection_0.getOrientationCoordinate().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {0.0, 1.0, 0.0}));
    planeSection_0.getOriginCoordinate().setCoordinate(units_1, units_1, units_1, new DoubleVector(new double[] {0.0, -1.0, 0.0}));
  } else {
    planeSection_0.getOrientationCoordinate().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {0.0, 1.0, 0.0}));
    planeSection_0.getOriginCoordinate().setCoordinate(units_1, units_1, units_1, new DoubleVector(new double[] {0.0, -901.0, 0.0}));
  }
  }
  catch (Exception e){
  PlaneSection planeSection_0 = 
      (PlaneSection) simulation_0.getPartManager().createImplicitPart(new NeoObjectVector(new Object[] {}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), new DoubleVector(new double[] {0.0, 0.0, 0.0}), 0, 1, new DoubleVector(new double[] {0.0}));
  planeSection_0.setPresentationName("CPTsectiony");
  planeSection_0.getInputParts().setQuery(new Query(new TypePredicate(TypeOperator.Is, Region.class), Query.STANDARD_MODIFIERS));
  if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
  planeSection_0.getOrientationCoordinate().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {0.0, 1.0, 0.0}));
    planeSection_0.getOriginCoordinate().setCoordinate(units_1, units_1, units_1, new DoubleVector(new double[] {0.0, -1.0, 0.0}));
  } else {
    planeSection_0.getOrientationCoordinate().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {0.0, 1.0, 0.0}));
    planeSection_0.getOriginCoordinate().setCoordinate(units_1, units_1, units_1, new DoubleVector(new double[] {0.0, -901.0, 0.0}));
  }
  }

  PlaneSection planeSection_0 = 
      ((PlaneSection) simulation_0.getPartManager().getObject("CPTsectiony"));

    simulation_0.getSceneManager().createEmptyScene("Scene");
  Scene scene_0 = 
      simulation_0.getSceneManager().getScene("Scene 1");
  scene_0.initializeAndWait();  
  SceneUpdate sceneUpdate_0 = 
      scene_0.getSceneUpdate();
  scene_0.setPresentationName("CpTy");  
  scene_0.setAspectRatio(AspectRatioEnum.RATIO_16_9);
  scene_0.setAxesVisible(false);
  
  LogoAnnotation logoAnnotation_0 = 
      ((LogoAnnotation) simulation_0.getAnnotationManager().getObject("Logo"));
    scene_0.getAnnotationPropManager().removePropsForAnnotations(logoAnnotation_0);

    ImageAnnotation2D imageAnnotation2D_0 = 
      ((ImageAnnotation2D) simulation_0.getAnnotationManager().getObject("Line"));
    ImageAnnotationProp2D imageAnnotationProp2D_0 = 
      (ImageAnnotationProp2D) scene_0.getAnnotationPropManager().createPropForAnnotation(imageAnnotation2D_0);
    SimpleAnnotation simpleAnnotation_0 = 
      ((SimpleAnnotation) simulation_0.getAnnotationManager().getObject("Setup column 1"));
    SimpleAnnotationProp simpleAnnotationProp_0 = 
      (SimpleAnnotationProp) scene_0.getAnnotationPropManager().createPropForAnnotation(simpleAnnotation_0);
    SimpleAnnotation simpleAnnotation_1 = 
      ((SimpleAnnotation) simulation_0.getAnnotationManager().getObject("Setup column 2"));
    SimpleAnnotationProp simpleAnnotationProp_1 = 
      (SimpleAnnotationProp) scene_0.getAnnotationPropManager().createPropForAnnotation(simpleAnnotation_1);
    SimpleAnnotation simpleAnnotation_2 = 
      ((SimpleAnnotation) simulation_0.getAnnotationManager().getObject("Description"));
    SimpleAnnotationProp simpleAnnotationProp_2 = 
      (SimpleAnnotationProp) scene_0.getAnnotationPropManager().createPropForAnnotation(simpleAnnotation_2);
    ImageAnnotation2D imageAnnotation2D_10 = 
      ((ImageAnnotation2D) simulation_0.getAnnotationManager().getObject("FST Black Icon"));
    ImageAnnotationProp2D imageAnnotationProp2D_10 = 
      (ImageAnnotationProp2D) scene_0.getAnnotationPropManager().createPropForAnnotation(imageAnnotation2D_10);
    imageAnnotationProp2D_10.setPosition(new DoubleVector(new double[] {0.6, 0.015, 0.0}));
  scene_0.setBackgroundColorMode(BackgroundColorMode.SOLID);
  scene_0.close();
  
  try {
      SimpleAnnotation simpleAnnotation_3 = 
      ((SimpleAnnotation) simulation_0.getAnnotationManager().getObject("CoordinateY"));
      if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
    simpleAnnotation_3.setText("Y = -1.0 mm");
  } else {
    simpleAnnotation_3.setText("Y = -901.0 mm");
  }
      SimpleAnnotationProp simpleAnnotationProp_3 = 
      (SimpleAnnotationProp) scene_0.getAnnotationPropManager().createPropForAnnotation(simpleAnnotation_3);
  }
  catch (Exception e){
    SimpleAnnotation simpleAnnotation_3 = 
      simulation_0.getAnnotationManager().createAnnotation(SimpleAnnotation.class);
    simpleAnnotation_3.setPresentationName("CoordinateY");
    simpleAnnotation_3.setShadow(false);
    if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
    simpleAnnotation_3.setText("Y = -1.0 mm");
  } else {
    simpleAnnotation_3.setText("Y = -901.0 mm");
  }
    simpleAnnotation_3.setFontString("Arial-Bold");
    simpleAnnotation_3.setDefaultHeight(0.025);
    simpleAnnotation_3.setDefaultPosition(new DoubleVector(new double[] {0.9, 0.045, 0.0}));
  SimpleAnnotationProp simpleAnnotationProp_3 = 
      (SimpleAnnotationProp) scene_0.getAnnotationPropManager().createPropForAnnotation(simpleAnnotation_3);
  }

  SimpleAnnotation simpleAnnotation_3 = 
      ((SimpleAnnotation) simulation_0.getAnnotationManager().getObject("CoordinateY"));

  try {
      ImageAnnotation2D imageAnnotation2D_1 = 
      ((ImageAnnotation2D) simulation_0.getAnnotationManager().getObject("Car-Top"));
  }
  catch (Exception e){
    final String folder = resolveWorkPath();
    ImageAnnotation2D imageAnnotation2D_1 = 
        simulation_0.getAnnotationManager().createAnnotation(ImageAnnotation2D.class);
    imageAnnotation2D_1.setFilePath("..\\Modules_Macro\\Library\\BlackTop.png");
    imageAnnotation2D_1.setPresentationName("Car-Top");
    imageAnnotation2D_1.setDefaultWidthStretchFactor(1.0);
    imageAnnotation2D_1.setDefaultHeight(0.225);
    imageAnnotation2D_1.setDefaultPosition(new DoubleVector(new double[] {0.0, 0.125, 0.0}));
  }
  
  try {

    XYPlot xYPlot_0 = 
      ((XYPlot) simulation_0.getPlotManager().getPlot("PlaneY - Car"));

    PlotImage plotImage_0 = 
      ((PlotImage) simulation_0.getAnnotationManager().getObject("Car Y Plane"));

    PlotImageAnnotationProp plotImageAnnotationProp_3 = 
      (PlotImageAnnotationProp) scene_0.getAnnotationPropManager().createPropForAnnotation(plotImage_0);
    plotImageAnnotationProp_3.setScaleComponents(false);
    plotImageAnnotationProp_3.setLocation(DisplayLocationMode.BACKGROUND);

  }
  catch (Exception e){
    XYPlot xYPlot_0 = 
      simulation_0.getPlotManager().createPlot(XYPlot.class);
    xYPlot_0.open();
    PlotUpdate plotUpdate_0 = 
      xYPlot_0.getPlotUpdate();
    xYPlot_0.setPresentationName("PlaneY - Car");
  xYPlot_0.close();
  xYPlot_0.getParts().setObjects(planeSection_0);

  Cartesian2DAxisManager cartesian2DAxisManager_0 = 
      ((Cartesian2DAxisManager) xYPlot_0.getAxisManager());
    Cartesian2DAxis cartesian2DAxis_0 = 
      ((Cartesian2DAxis) cartesian2DAxisManager_0.getAxis("Bottom Axis"));

    YAxisType yAxisType_0 = 
      ((YAxisType) xYPlot_0.getYAxes().getAxisType("Y Type 1"));
  
  if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
  cartesian2DAxis_0.setMinimum(-60.0);   //Before was -43.0
    cartesian2DAxis_0.setMaximum(110.0);    //Before was 90.0
    InternalDataSet internalDataSet_0 = 
      ((InternalDataSet) yAxisType_0.getDataSetManager().getDataSet("CPTsectiony"));
  internalDataSet_0.setXScale(1.0);
  } else {
  cartesian2DAxis_0.setMinimum(-30.0);
    cartesian2DAxis_0.setMaximum(80.0);
  InternalDataSet internalDataSet_0 = 
      ((InternalDataSet) yAxisType_0.getDataSetManager().getDataSet("CPTsectiony"));
  internalDataSet_0.setXScale(1.3);}
  
    Cartesian2DAxis cartesian2DAxis_1 = 
      ((Cartesian2DAxis) cartesian2DAxisManager_0.getAxis("Left Axis"));
    cartesian2DAxis_1.setPosition(Cartesian2DAxis.Position.Right);
  if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
  cartesian2DAxis_1.setMinimum(-3.15);
    cartesian2DAxis_1.setMaximum(4.05);
  } else {
  cartesian2DAxis_1.setMinimum(-2.67);
  cartesian2DAxis_1.setMaximum(3.4);}

    AxisType axisType_0 = 
      xYPlot_0.getXAxisType();
    axisType_0.getDirectionVector().setComponents(1.0, 0.0, 0.0);
  axisType_0.getDirectionVector().setUnits(units_0);

    FieldFunctionUnits fieldFunctionUnits_0 = 
      yAxisType_0.getScalarFunction();

    PrimitiveFieldFunction primitiveFieldFunction_0 = 
      ((PrimitiveFieldFunction) simulation_0.getFieldFunctionManager().getFunction("Position"));
    VectorComponentFieldFunction vectorComponentFieldFunction_0 = 
      ((VectorComponentFieldFunction) primitiveFieldFunction_0.getComponentFunction(1));
    fieldFunctionUnits_0.setFieldFunction(vectorComponentFieldFunction_0);

    InternalDataSet internalDataSet_0 = 
      ((InternalDataSet) yAxisType_0.getDataSetManager().getDataSet("CPTsectiony"));
    internalDataSet_0.setShowLegendEntry(false);
    LineStyle lineStyle_0 = 
      internalDataSet_0.getLineStyle();
    lineStyle_0.getLinePatternOption().setSelected(LinePatternOption.Type.SOLID);
    SymbolStyleWithSpacing symbolStyleWithSpacing_0 = 
      internalDataSet_0.getSymbolStyle();
    symbolStyleWithSpacing_0.getSymbolShapeOption().setSelected(SymbolShapeOption.Type.NONE);
    lineStyle_0.setWidth(3);
    cartesian2DAxis_1.setVisible(false);
    cartesian2DAxis_0.setVisible(false);
    MultiColLegend multiColLegend_0 = 
      xYPlot_0.getLegend();
    multiColLegend_0.setVisible(false);
    xYPlot_0.setTitle("");
    xYPlot_0.setAspectRatio(AspectRatioEnum.RATIO_16_9);
    ImageAnnotation2D imageAnnotation2D_1 = 
      ((ImageAnnotation2D) simulation_0.getAnnotationManager().getObject("Car-Top"));
    ImageAnnotationProp2D imageAnnotationProp2D_1 = 
      (ImageAnnotationProp2D) xYPlot_0.getAnnotationPropManager().createPropForAnnotation(imageAnnotation2D_1);
  imageAnnotationProp2D_1.setWidthStretchFactor(1.0);
  imageAnnotationProp2D_1.setPosition(new DoubleVector(new double[] {0.1669, 0.3417, 0.0}));
  imageAnnotationProp2D_1.setLocation(DisplayLocationMode.BACKGROUND);
  
  PlotImage plotImage_0 = 
      simulation_0.getAnnotationManager().createAnnotation(PlotImage.class);
    plotImage_0.setPresentationName("Car Y Plane");
    plotImage_0.setViewObject(xYPlot_0);
    plotImage_0.setDefaultScaleComponents(false);
    plotImage_0.setDefaultPosition(new DoubleVector(new double[] {0.68, -0.165, 0.0}));

    PlotImageAnnotationProp plotImageAnnotationProp_3 = 
      (PlotImageAnnotationProp) scene_0.getAnnotationPropManager().createPropForAnnotation(plotImage_0);
    plotImageAnnotationProp_3.setScaleComponents(false);
    plotImageAnnotationProp_3.setLocation(DisplayLocationMode.BACKGROUND);

  }

  XYPlot xYPlot_0 = 
      ((XYPlot) simulation_0.getPlotManager().getPlot("PlaneY - Car"));

  scene_0.setBackgroundColorMode(BackgroundColorMode.SOLID);
  scene_0.close();
  
  ScalarDisplayer scalarDisplayer_0 = 
      scene_0.getDisplayerManager().createScalarDisplayer("Scalar");
    scalarDisplayer_0.initialize();
    scalarDisplayer_0.setPresentationName("CpTy");
    scalarDisplayer_0.getInputParts().setObjects(planeSection_0);
  
  ClipPlane clipPlane_0 = 
      ((ClipPlane) scene_0.getPlaneManager().getPlane("Plane 1"));
    ClipPlane clipPlane_1 = 
      ((ClipPlane) scene_0.getPlaneManager().getPlane("Plane 2"));
    ClipPlane clipPlane_2 = 
      ((ClipPlane) scene_0.getPlaneManager().getPlane("Plane 3"));
    clipPlane_0.getOriginCoordinate().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {0.0, 0.0, 1.8}));
    clipPlane_1.getOriginCoordinate().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {0.0, 1.5, 0.0}));
    clipPlane_2.getOriginCoordinate().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {0.0, -1.5, 0.0}));
    clipPlane_0.setNormal(new DoubleVector(new double[] {0.0, 0.0, -1.0}));
    clipPlane_1.setNormal(new DoubleVector(new double[] {0.0, -1.0, 0.0}));
    clipPlane_2.setNormal(new DoubleVector(new double[] {0.0, 1.0, 0.0}));
    clipPlane_0.setEnabled(true);
    clipPlane_1.setEnabled(true);
    clipPlane_2.setEnabled(true);
  
  try {
    UserFieldFunction userFieldFunction_0 = 
      ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("CpT"));
    scalarDisplayer_0.getScalarDisplayQuantity().setFieldFunction(userFieldFunction_0);

  }
  catch (Exception e){
    UserFieldFunction userFieldFunction_0 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();
    userFieldFunction_0.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);
    userFieldFunction_0.setFunctionName("CpT");
    userFieldFunction_0.setPresentationName("CpT");
  
    if ( CornerRadius == 0) {
    userFieldFunction_0.setDefinition("(${TotalPressureMonitor}-${Pressure Avg. Inlet})/(0.5*1.225*pow(${car_velocity[ms-1]},2))"); 
    } else {
    userFieldFunction_0.setDefinition("(${TotalPressureMonitor}-${Pressure Avg. Inlet})/(0.5*1.225*pow(${Angular_Velocity}*($$Position(@CoordinateSystem(\"Laboratory.Air Orientation - Car.Air Center\"))[0]),2))");} 
    }
  UserFieldFunction userFieldFunction_0 = 
      ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("CpT"));
  scalarDisplayer_0.getScalarDisplayQuantity().setFieldFunction(userFieldFunction_0);

  try {
  UserLookupTable userLookupTable_0 = 
      ((UserLookupTable) simulation_0.get(LookupTableManager.class).getObject("Carreira_CPT_-1+1"));
  }
  catch (Exception e){
    
  UserLookupTable userLookupTable_0 = 
      simulation_0.get(LookupTableManager.class).createLookupTable();
    userLookupTable_0.setPresentationName("Carreira_CPT_-1+1"); 
  userLookupTable_0.setColorMap(new ColorMap(new DoubleVector(new double[] {0.0, 0.0, 0.0, 0.0, 0.072, 1.0, 0.4117647058823529, 0.7058823529411765, 0.12, 1.0, 0.4117647058823529, 0.7058823529411765, 0.24, 0.0, 0.0, 1.0, 0.26, 0.0, 0.0, 1.0, 0.49, 0.0, 1.0, 1.0, 0.51, 0.0, 1.0, 1.0, 0.627, 0.0, 1.0, 0.0, 0.75, 1.0, 1.0, 0.0, 0.9640287769784173, 1.0, 0.0, 0.0, 0.9892086330935251, 1.0, 0.0, 0.0}), new DoubleVector(new double[] {0.0, 1.0, 1.0, 1.0}), 0));
  }
  
  /*BlueRedLookupTable blueRedLookupTable_0 = 
      ((BlueRedLookupTable) simulation_0.get(LookupTableManager.class).getObject("blue-red"));*/

  UserLookupTable userLookupTable_0 = 
      ((UserLookupTable) simulation_0.get(LookupTableManager.class).getObject("Carreira_CPT_-1+1"));
  
  Legend legend_0 = 
      scalarDisplayer_0.getLegend();
    legend_0.setLookupTable(userLookupTable_0);
    legend_0.setLevels(54);
    legend_0.setWidth(0.21);
    legend_0.setFontString("Arial-Bold");
    legend_0.setShadow(false);
    legend_0.setHeight(0.025);
    legend_0.setTitleHeight(0.03);
    legend_0.setLabelHeight(0.02);
    legend_0.setPositionCoordinate(new DoubleVector(new double[] {0.02, 0.03}));
    legend_0.setLabelFormat("%1.1f");
    legend_0.setRampType(RampType.LINEAR);
    legend_0.setNumberOfLabels(5);
  
    scalarDisplayer_0.setFillMode(ScalarFillMode.NODE_SMOOTH);
    scalarDisplayer_0.getInterpolationOption().setSelected(ScalarInterpolationOption.Type.BANDED);
    scalarDisplayer_0.getScalarDisplayQuantity().setRange(new DoubleVector(new double[] {-1.0, 1.0}));
    scalarDisplayer_0.getScalarDisplayQuantity().setClip(ClipMode.NONE);
  
  VectorDisplayer vectorDisplayer_0 = 
      scene_0.getDisplayerManager().createVectorDisplayer("Vector");
  vectorDisplayer_0.initialize();
    Legend legend_1 = 
      vectorDisplayer_0.getLegend();
    BlueRedLookupTable blueRedLookupTable_1 = 
      ((BlueRedLookupTable) simulation_0.get(LookupTableManager.class).getObject("blue-red"));
    legend_1.setLookupTable(blueRedLookupTable_1);
    vectorDisplayer_0.setPresentationName("LIC");
  vectorDisplayer_0.setOpacity(0.4);
    vectorDisplayer_0.setDisplayMode(VectorDisplayMode.VECTOR_DISPLAY_MODE_LIC);
    vectorDisplayer_0.setColorMode(VectorColorMode.SCALAR);
    vectorDisplayer_0.getInputParts().setObjects(planeSection_0);
  
    UserFieldFunction userFieldFunction_1 = 
      ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("v_avg"));
    vectorDisplayer_0.getVectorDisplayQuantity().setFieldFunction(userFieldFunction_1); 
    
    vectorDisplayer_0.getColoringScalar().setFieldFunction(userFieldFunction_0);
  LICSettings lICSettings_0 = 
      vectorDisplayer_0.getLICSettings();
    lICSettings_0.setNumberOfSteps(40);
    lICSettings_0.setIntensity(1.0);
    vectorDisplayer_0.getColoringScalar().setRange(new DoubleVector(new double[] {-1.0, 1.0}));
    vectorDisplayer_0.getColoringScalar().setClip(ClipMode.NONE);
  
  legend_1.setVisible(false);

  /*PartDisplayer partDisplayer_10 = 
      scene_0.getDisplayerManager().createPartDisplayer("Outline", -1, 4);
    PlaneSection planeSection_10 = 
      ((PlaneSection) simulation_0.getPartManager().getObject("CPTsectiony"));
      partDisplayer_10.getInputParts().setObjects(planeSection_10);*/
  
  CurrentView currentView_0 = 
      scene_0.getCurrentView();
  
  scene_0.setViewOrientation(new DoubleVector(new double[] {0.0, -1.0, 0.0}), new DoubleVector(new double[] {0.0, 0.0, 1.0}));

  if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
  VisView visView_0 = 
      ((VisView) simulation_0.getViewManager().getObject("right_CptY"));
    currentView_0.setView(visView_0);
  scene_0.printAndWait(resolvePath("..\\Post_Processing\\CpTy\\0 CpTy-1mm.jpg"), 1, 1920, 1080, true, false);
  
  for (int i = 1; i < 101; i++) { // CHANGE BACK I TO 101
    planeSection_0.getOriginCoordinate().setCoordinate(units_1, units_1, units_0, new DoubleVector(new double[] {0.0, (-1-i*9), 0.0}));
  currentView_0.setView(visView_0);
  double coordinate = -1-i*9; 
  simpleAnnotation_3.setText("Y = "+ coordinate +" mm");
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\CpTy\\"+ i +" CpTy"+ coordinate +"mm.jpg"), 1, 1920, 1080, true, false);
  }
  } else {

  VisView visView_0 = 
      ((VisView) simulation_0.getViewManager().getObject("right_CptY"));
    currentView_0.setView(visView_0);
  scene_0.printAndWait(resolvePath("..\\Post_Processing\\CpTy\\0 CpTy-901mm.jpg"), 1, 1920, 1080, true, false);
  
  for (int i = 1; i < 202; i++) { // CHANGE BACK I TO 202
    planeSection_0.getOriginCoordinate().setCoordinate(units_1, units_1, units_0, new DoubleVector(new double[] {0.0, (-901+i*9), 0.0}));
  currentView_0.setView(visView_0);
  double coordinate = -901+i*9; 
  simpleAnnotation_3.setText("Y = "+ coordinate +" mm");
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\CpTy\\"+ i +" CpTy"+ coordinate +"mm.jpg"), 1, 1920, 1080, true, false);
  }

  }
  ((ClientServerObjectGroup) simulation_0.getPlotManager().getGroupsManager().getObject("Plane Aux")).getGroupsManager().groupObjects("Plane Aux", new NeoObjectVector(new Object[] {xYPlot_0}), true);
   
}

private void CpTz() {

  Simulation simulation_0 = 
      getActiveSimulation();  
    
  double Roll = simulation_0.getReportManager().getReport("Roll [deg]").getReportMonitorValue();
  double Yaw = simulation_0.getReportManager().getReport("Yaw [rad]").getReportMonitorValue();
  double CornerRadius = simulation_0.getReportManager().getReport("Corner Radius [m]").getReportMonitorValue();

  // Velocity Avg. //

  try{
  UserFieldFunction userFieldFunction_0 = 
    ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("v_avg"));
  }
  catch (Exception e){
  UserFieldFunction userFieldFunction_111 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();
    userFieldFunction_111.getTypeOption().setSelected(FieldFunctionTypeOption.Type.VECTOR);
    userFieldFunction_111.setPresentationName("Velocity Avg.");
    userFieldFunction_111.setFunctionName("v_avg");
    Units units_2 = 
      simulation_0.getUnitsManager().getPreferredUnits(new IntVector(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}));
    userFieldFunction_111.setDefinition("[${V[i]Monitor},${V[j]Monitor},${V[k]Monitor}]");
  }

  Units units_0 = 
      ((Units) simulation_0.getUnitsManager().getObject("m"));
  Units units_1 = 
      ((Units) simulation_0.getUnitsManager().getObject("mm"));

  // Plane Z //

  try {
  PlaneSection planeSection_0 = 
      ((PlaneSection) simulation_0.getPartManager().getObject("CPTsectionz"));
  planeSection_0.getOrientationCoordinate().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {0.0, 0.0, 1.0}));
    planeSection_0.getOriginCoordinate().setCoordinate(units_1, units_0, units_1, new DoubleVector(new double[] {0.0, 0.0, 5.0}));
  }
  catch (Exception e){
  PlaneSection planeSection_0 = 
      (PlaneSection) simulation_0.getPartManager().createImplicitPart(new NeoObjectVector(new Object[] {}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), new DoubleVector(new double[] {0.0, 0.0, 0.0}), 0, 1, new DoubleVector(new double[] {0.0}));
  planeSection_0.setPresentationName("CPTsectionz");
  planeSection_0.getInputParts().setQuery(new Query(new TypePredicate(TypeOperator.Is, Region.class), Query.STANDARD_MODIFIERS));
  planeSection_0.getOrientationCoordinate().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {0.0, 0.0, 1.0}));
    planeSection_0.getOriginCoordinate().setCoordinate(units_1, units_0, units_1, new DoubleVector(new double[] {0.0, 0.0, 5.0}));
  }

  PlaneSection planeSection_0 = 
      ((PlaneSection) simulation_0.getPartManager().getObject("CPTsectionz"));

    simulation_0.getSceneManager().createEmptyScene("Scene");
  Scene scene_0 = 
      simulation_0.getSceneManager().getScene("Scene 1");
  scene_0.initializeAndWait();  
  SceneUpdate sceneUpdate_0 = 
      scene_0.getSceneUpdate();
  scene_0.setPresentationName("CpTz");  
  scene_0.setAspectRatio(AspectRatioEnum.RATIO_16_9);
  scene_0.setAxesVisible(false);
  
  LogoAnnotation logoAnnotation_0 = 
      ((LogoAnnotation) simulation_0.getAnnotationManager().getObject("Logo"));
    scene_0.getAnnotationPropManager().removePropsForAnnotations(logoAnnotation_0);

    ImageAnnotation2D imageAnnotation2D_0 = 
      ((ImageAnnotation2D) simulation_0.getAnnotationManager().getObject("Line"));
    ImageAnnotationProp2D imageAnnotationProp2D_0 = 
      (ImageAnnotationProp2D) scene_0.getAnnotationPropManager().createPropForAnnotation(imageAnnotation2D_0);
    SimpleAnnotation simpleAnnotation_0 = 
      ((SimpleAnnotation) simulation_0.getAnnotationManager().getObject("Setup column 1"));
    SimpleAnnotationProp simpleAnnotationProp_0 = 
      (SimpleAnnotationProp) scene_0.getAnnotationPropManager().createPropForAnnotation(simpleAnnotation_0);
    SimpleAnnotation simpleAnnotation_1 = 
      ((SimpleAnnotation) simulation_0.getAnnotationManager().getObject("Setup column 2"));
    SimpleAnnotationProp simpleAnnotationProp_1 = 
      (SimpleAnnotationProp) scene_0.getAnnotationPropManager().createPropForAnnotation(simpleAnnotation_1);
    SimpleAnnotation simpleAnnotation_2 = 
      ((SimpleAnnotation) simulation_0.getAnnotationManager().getObject("Description"));
    SimpleAnnotationProp simpleAnnotationProp_2 = 
      (SimpleAnnotationProp) scene_0.getAnnotationPropManager().createPropForAnnotation(simpleAnnotation_2);
    ImageAnnotation2D imageAnnotation2D_10 = 
      ((ImageAnnotation2D) simulation_0.getAnnotationManager().getObject("FST Black Icon"));
    ImageAnnotationProp2D imageAnnotationProp2D_10 = 
      (ImageAnnotationProp2D) scene_0.getAnnotationPropManager().createPropForAnnotation(imageAnnotation2D_10);
    imageAnnotationProp2D_10.setPosition(new DoubleVector(new double[] {0.6, 0.015, 0.0}));
  scene_0.setBackgroundColorMode(BackgroundColorMode.SOLID);
  scene_0.close();
  
  try {
      SimpleAnnotation simpleAnnotation_3 = 
      ((SimpleAnnotation) simulation_0.getAnnotationManager().getObject("CoordinateZ"));
      simpleAnnotation_3.setText("Z = 5.0 mm");
      SimpleAnnotationProp simpleAnnotationProp_3 = 
      (SimpleAnnotationProp) scene_0.getAnnotationPropManager().createPropForAnnotation(simpleAnnotation_3);
  }
  catch (Exception e){
    SimpleAnnotation simpleAnnotation_3 = 
      simulation_0.getAnnotationManager().createAnnotation(SimpleAnnotation.class);
    simpleAnnotation_3.setPresentationName("CoordinateZ");
    simpleAnnotation_3.setShadow(false);
    simpleAnnotation_3.setText("Z = 5.0 mm");
    simpleAnnotation_3.setFontString("Arial-Bold");
    simpleAnnotation_3.setDefaultHeight(0.025);
    simpleAnnotation_3.setDefaultPosition(new DoubleVector(new double[] {0.9, 0.045, 0.0}));
  SimpleAnnotationProp simpleAnnotationProp_3 = 
      (SimpleAnnotationProp) scene_0.getAnnotationPropManager().createPropForAnnotation(simpleAnnotation_3);
  }

  SimpleAnnotation simpleAnnotation_3 = 
      ((SimpleAnnotation) simulation_0.getAnnotationManager().getObject("CoordinateZ"));

  try {
      ImageAnnotation2D imageAnnotation2D_1 = 
      ((ImageAnnotation2D) simulation_0.getAnnotationManager().getObject("Car-Side"));
  }
  catch (Exception e){
    final String folder = resolveWorkPath();
    ImageAnnotation2D imageAnnotation2D_1 = 
        simulation_0.getAnnotationManager().createAnnotation(ImageAnnotation2D.class);
    imageAnnotation2D_1.setFilePath("..\\Modules_Macro\\Library\\BlackSide.png");
    imageAnnotation2D_1.setPresentationName("Car-Side");
    imageAnnotation2D_1.setDefaultWidthStretchFactor(1.0);
    imageAnnotation2D_1.setDefaultHeight(0.225);
    imageAnnotation2D_1.setDefaultPosition(new DoubleVector(new double[] {0.0, 0.125, 0.0}));
  }
  
  try {

    XYPlot xYPlot_0 = 
      ((XYPlot) simulation_0.getPlotManager().getPlot("PlaneZ - Car"));

    PlotImage plotImage_0 = 
      ((PlotImage) simulation_0.getAnnotationManager().getObject("Car Z Plane"));

    PlotImageAnnotationProp plotImageAnnotationProp_3 = 
      (PlotImageAnnotationProp) scene_0.getAnnotationPropManager().createPropForAnnotation(plotImage_0);
    plotImageAnnotationProp_3.setScaleComponents(false);
    plotImageAnnotationProp_3.setLocation(DisplayLocationMode.BACKGROUND);

  }
  catch (Exception e){
    XYPlot xYPlot_0 = 
      simulation_0.getPlotManager().createPlot(XYPlot.class);
    xYPlot_0.open();
    PlotUpdate plotUpdate_0 = 
      xYPlot_0.getPlotUpdate();
    xYPlot_0.setPresentationName("PlaneZ - Car");
  xYPlot_0.close();
  xYPlot_0.getParts().setObjects(planeSection_0);
  
  Cartesian2DAxisManager cartesian2DAxisManager_0 = 
      ((Cartesian2DAxisManager) xYPlot_0.getAxisManager());
    Cartesian2DAxis cartesian2DAxis_0 = 
      ((Cartesian2DAxis) cartesian2DAxisManager_0.getAxis("Bottom Axis"));

  YAxisType yAxisType_0 = 
      ((YAxisType) xYPlot_0.getYAxes().getAxisType("Y Type 1"));
  
  if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
  cartesian2DAxis_0.setMinimum(-19.0);
    cartesian2DAxis_0.setMaximum(29.0);
    InternalDataSet internalDataSet_0 = 
      ((InternalDataSet) yAxisType_0.getDataSetManager().getDataSet("CPTsectionz"));
    internalDataSet_0.setXScale(1.8);
  } else {
  cartesian2DAxis_0.setMinimum(-15.0);
    cartesian2DAxis_0.setMaximum(70.0);
    InternalDataSet internalDataSet_0 = 
      ((InternalDataSet) yAxisType_0.getDataSetManager().getDataSet("CPTsectionz"));
    internalDataSet_0.setXScale(0.75);
  }
  
    Cartesian2DAxis cartesian2DAxis_1 = 
      ((Cartesian2DAxis) cartesian2DAxisManager_0.getAxis("Left Axis"));
    cartesian2DAxis_1.setPosition(Cartesian2DAxis.Position.Right);
  cartesian2DAxis_1.setMinimum(-2.5);
  cartesian2DAxis_1.setMaximum(4.5);

    AxisType axisType_0 = 
      xYPlot_0.getXAxisType();
    axisType_0.getDirectionVector().setComponents(0.0, 1.0, 0.0);
  axisType_0.getDirectionVector().setUnits(units_0);

    FieldFunctionUnits fieldFunctionUnits_0 = 
      yAxisType_0.getScalarFunction();

    PrimitiveFieldFunction primitiveFieldFunction_0 = 
      ((PrimitiveFieldFunction) simulation_0.getFieldFunctionManager().getFunction("Position"));
    VectorComponentFieldFunction vectorComponentFieldFunction_0 = 
      ((VectorComponentFieldFunction) primitiveFieldFunction_0.getComponentFunction(2));
    fieldFunctionUnits_0.setFieldFunction(vectorComponentFieldFunction_0);

    InternalDataSet internalDataSet_0 = 
      ((InternalDataSet) yAxisType_0.getDataSetManager().getDataSet("CPTsectionz"));
    internalDataSet_0.setShowLegendEntry(false);
    LineStyle lineStyle_0 = 
      internalDataSet_0.getLineStyle();
    lineStyle_0.getLinePatternOption().setSelected(LinePatternOption.Type.SOLID);
    SymbolStyleWithSpacing symbolStyleWithSpacing_0 = 
      internalDataSet_0.getSymbolStyle();
    symbolStyleWithSpacing_0.getSymbolShapeOption().setSelected(SymbolShapeOption.Type.NONE);
    lineStyle_0.setWidth(3);
    cartesian2DAxis_1.setVisible(false);
    cartesian2DAxis_0.setVisible(false);
    MultiColLegend multiColLegend_0 = 
      xYPlot_0.getLegend();
    multiColLegend_0.setVisible(false);
    xYPlot_0.setTitle("");
    xYPlot_0.setAspectRatio(AspectRatioEnum.RATIO_16_9);
    ImageAnnotation2D imageAnnotation2D_1 = 
      ((ImageAnnotation2D) simulation_0.getAnnotationManager().getObject("Car-Side"));
    ImageAnnotationProp2D imageAnnotationProp2D_1 = 
      (ImageAnnotationProp2D) xYPlot_0.getAnnotationPropManager().createPropForAnnotation(imageAnnotation2D_1);
  imageAnnotationProp2D_1.setWidthStretchFactor(1.0);
  imageAnnotationProp2D_1.setPosition(new DoubleVector(new double[] {0.1669, 0.3417, 0.0}));
  imageAnnotationProp2D_1.setLocation(DisplayLocationMode.BACKGROUND);
  
  PlotImage plotImage_0 = 
      simulation_0.getAnnotationManager().createAnnotation(PlotImage.class);
    plotImage_0.setPresentationName("Car Z Plane");
    plotImage_0.setViewObject(xYPlot_0);
    plotImage_0.setDefaultScaleComponents(false);
    plotImage_0.setDefaultPosition(new DoubleVector(new double[] {0.68, -0.162, 0.0}));

    PlotImageAnnotationProp plotImageAnnotationProp_3 = 
      (PlotImageAnnotationProp) scene_0.getAnnotationPropManager().createPropForAnnotation(plotImage_0);
    plotImageAnnotationProp_3.setScaleComponents(false);
    plotImageAnnotationProp_3.setLocation(DisplayLocationMode.BACKGROUND);

  }

  XYPlot xYPlot_0 = 
      ((XYPlot) simulation_0.getPlotManager().getPlot("PlaneZ - Car"));

  scene_0.setBackgroundColorMode(BackgroundColorMode.SOLID);
  scene_0.close();
  
  ScalarDisplayer scalarDisplayer_0 = 
      scene_0.getDisplayerManager().createScalarDisplayer("Scalar");
    scalarDisplayer_0.initialize();
    scalarDisplayer_0.setPresentationName("CpTx");
    scalarDisplayer_0.getInputParts().setObjects(planeSection_0);
  
  ClipPlane clipPlane_0 = 
      ((ClipPlane) scene_0.getPlaneManager().getPlane("Plane 1"));
    ClipPlane clipPlane_1 = 
      ((ClipPlane) scene_0.getPlaneManager().getPlane("Plane 2"));
    ClipPlane clipPlane_2 = 
      ((ClipPlane) scene_0.getPlaneManager().getPlane("Plane 3"));
  ClipPlane clipPlane_3 = 
      ((ClipPlane) scene_0.getPlaneManager().getPlane("Plane 4"));
    clipPlane_0.getOriginCoordinate().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {-3.4, 0.0, 0.0}));
    clipPlane_1.getOriginCoordinate().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {1.65, 0.0, 0.0}));
    clipPlane_2.getOriginCoordinate().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {0.0, 1.2, 0.0}));
    clipPlane_3.getOriginCoordinate().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {0.0, -1.2, 0.0}));
  clipPlane_0.setNormal(new DoubleVector(new double[] {1.0, 0.0, 0.0}));
    clipPlane_1.setNormal(new DoubleVector(new double[] {-1.0, 0.0, 0.0}));
    clipPlane_2.setNormal(new DoubleVector(new double[] {0.0, -1.0, 0.0}));
    clipPlane_3.setNormal(new DoubleVector(new double[] {0.0, 1.0, 0.0}));
    clipPlane_0.setEnabled(true);
    clipPlane_1.setEnabled(true);
    clipPlane_2.setEnabled(true);
    clipPlane_3.setEnabled(true);
  
  if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
      SymmetricRepeat symmetricRepeat_0 = 
      ((SymmetricRepeat) simulation_0.getTransformManager().getObject("Domain.Wall_Inner 1"));
    scalarDisplayer_0.setVisTransform(symmetricRepeat_0);}
  
  try {
    UserFieldFunction userFieldFunction_0 = 
      ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("CpT"));
    scalarDisplayer_0.getScalarDisplayQuantity().setFieldFunction(userFieldFunction_0);

  }
  catch (Exception e){
    UserFieldFunction userFieldFunction_0 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();
    userFieldFunction_0.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);
    userFieldFunction_0.setFunctionName("CpT");
    userFieldFunction_0.setPresentationName("CpT");
  
    if ( CornerRadius == 0) {
    userFieldFunction_0.setDefinition("(${TotalPressureMonitor}-${Pressure Avg. Inlet})/(0.5*1.225*pow(${car_velocity[ms-1]},2))"); 
    } else {
    userFieldFunction_0.setDefinition("(${TotalPressureMonitor}-${Pressure Avg. Inlet})/(0.5*1.225*pow(${Angular_Velocity}*($$Position(@CoordinateSystem(\"Laboratory.Air Orientation - Car.Air Center\"))[0]),2))");} 
    }
  UserFieldFunction userFieldFunction_0 = 
      ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("CpT"));
  scalarDisplayer_0.getScalarDisplayQuantity().setFieldFunction(userFieldFunction_0);

  try {
  UserLookupTable userLookupTable_0 = 
      ((UserLookupTable) simulation_0.get(LookupTableManager.class).getObject("Carreira_CPT_-1+1"));
  }
  catch (Exception e){
    
  UserLookupTable userLookupTable_0 = 
      simulation_0.get(LookupTableManager.class).createLookupTable();
    userLookupTable_0.setPresentationName("Carreira_CPT_-1+1"); 
  userLookupTable_0.setColorMap(new ColorMap(new DoubleVector(new double[] {0.0, 0.0, 0.0, 0.0, 0.072, 1.0, 0.4117647058823529, 0.7058823529411765, 0.12, 1.0, 0.4117647058823529, 0.7058823529411765, 0.24, 0.0, 0.0, 1.0, 0.26, 0.0, 0.0, 1.0, 0.49, 0.0, 1.0, 1.0, 0.51, 0.0, 1.0, 1.0, 0.627, 0.0, 1.0, 0.0, 0.75, 1.0, 1.0, 0.0, 0.9640287769784173, 1.0, 0.0, 0.0, 0.9892086330935251, 1.0, 0.0, 0.0}), new DoubleVector(new double[] {0.0, 1.0, 1.0, 1.0}), 0));
  }
  
  /*BlueRedLookupTable blueRedLookupTable_0 = 
      ((BlueRedLookupTable) simulation_0.get(LookupTableManager.class).getObject("blue-red"));*/

  UserLookupTable userLookupTable_0 = 
      ((UserLookupTable) simulation_0.get(LookupTableManager.class).getObject("Carreira_CPT_-1+1"));
  
  Legend legend_0 = 
      scalarDisplayer_0.getLegend();
    legend_0.setLookupTable(userLookupTable_0);
  legend_0.setLevels(54);
    legend_0.setWidth(0.21);
    legend_0.setFontString("Arial-Bold");
    legend_0.setShadow(false);
    legend_0.setHeight(0.025);
    legend_0.setTitleHeight(0.03);
    legend_0.setLabelHeight(0.02);
    legend_0.setPositionCoordinate(new DoubleVector(new double[] {0.02, 0.03}));
    legend_0.setLabelFormat("%1.1f");
    legend_0.setRampType(RampType.LINEAR);
    legend_0.setNumberOfLabels(5);
  
    scalarDisplayer_0.setFillMode(ScalarFillMode.NODE_SMOOTH);
    scalarDisplayer_0.getInterpolationOption().setSelected(ScalarInterpolationOption.Type.BANDED);
    scalarDisplayer_0.getScalarDisplayQuantity().setRange(new DoubleVector(new double[] {-1.0, 1.0}));
    scalarDisplayer_0.getScalarDisplayQuantity().setClip(ClipMode.NONE);
  
  VectorDisplayer vectorDisplayer_0 = 
      scene_0.getDisplayerManager().createVectorDisplayer("Vector");
  vectorDisplayer_0.initialize();
    Legend legend_1 = 
      vectorDisplayer_0.getLegend();
    BlueRedLookupTable blueRedLookupTable_1 = 
      ((BlueRedLookupTable) simulation_0.get(LookupTableManager.class).getObject("blue-red"));
    legend_1.setLookupTable(blueRedLookupTable_1);
    vectorDisplayer_0.setPresentationName("LIC");
  vectorDisplayer_0.setOpacity(0.4);
    vectorDisplayer_0.setDisplayMode(VectorDisplayMode.VECTOR_DISPLAY_MODE_LIC);
    vectorDisplayer_0.setColorMode(VectorColorMode.SCALAR);
    vectorDisplayer_0.getInputParts().setObjects(planeSection_0);
  
  if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
  SymmetricRepeat symmetricRepeat_1 = 
      ((SymmetricRepeat) simulation_0.getTransformManager().getObject("Domain.Wall_Inner 1"));
    vectorDisplayer_0.setVisTransform(symmetricRepeat_1);}
  
    UserFieldFunction userFieldFunction_1 = 
      ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("v_avg"));
    vectorDisplayer_0.getVectorDisplayQuantity().setFieldFunction(userFieldFunction_1); 
    
    vectorDisplayer_0.getColoringScalar().setFieldFunction(userFieldFunction_0);
  LICSettings lICSettings_0 = 
      vectorDisplayer_0.getLICSettings();
    lICSettings_0.setNumberOfSteps(40);
    lICSettings_0.setIntensity(1.0);
    vectorDisplayer_0.getColoringScalar().setRange(new DoubleVector(new double[] {-1.0, 1.0}));
    vectorDisplayer_0.getColoringScalar().setClip(ClipMode.NONE);
  
  legend_1.setVisible(false);

  /*PartDisplayer partDisplayer_10 = 
      scene_0.getDisplayerManager().createPartDisplayer("Outline", -1, 4);
    PlaneSection planeSection_10 = 
      ((PlaneSection) simulation_0.getPartManager().getObject("CPTsectionz"));
      partDisplayer_10.getInputParts().setObjects(planeSection_10);*/
  
  CurrentView currentView_0 = 
      scene_0.getCurrentView();
  
  scene_0.setViewOrientation(new DoubleVector(new double[] {0.0, 0.0, 1.0}), new DoubleVector(new double[] {0.0, 0.0, 1.0}));

  VisView visView_0 = 
      ((VisView) simulation_0.getViewManager().getObject("top_CptZ"));
    currentView_0.setView(visView_0);
  
  for (int i = 1; i < 130; i++) {  
    planeSection_0.getOriginCoordinate().setCoordinate(units_1, units_0, units_1, new DoubleVector(new double[] {0.0, 0.0, (5+i*10)}));
  currentView_0.setView(visView_0);
  double coordinate = 5+i*10;
  simpleAnnotation_3.setText("Z = "+ coordinate +" mm");
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\CpTz\\"+ i +" CpTz"+ coordinate +"mm.png"), 1, 1920, 1080, true, false);
  }

  ((ClientServerObjectGroup) simulation_0.getPlotManager().getGroupsManager().getObject("Plane Aux")).getGroupsManager().groupObjects("Plane Aux", new NeoObjectVector(new Object[] {xYPlot_0}), true); 
  simulation_0.saveState(resolvePath("..\\Simulation\\FST11CFD.sim"));
  
  }

  private void StaticCpx() {

  Simulation simulation_0 = 
      getActiveSimulation();  
    
  double Roll = simulation_0.getReportManager().getReport("Roll [deg]").getReportMonitorValue();
  double Yaw = simulation_0.getReportManager().getReport("Yaw [rad]").getReportMonitorValue();
  double CornerRadius = simulation_0.getReportManager().getReport("Corner Radius [m]").getReportMonitorValue();

  // Velocity Avg. //

  try{
  UserFieldFunction userFieldFunction_0 = 
    ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("v_avg"));
  }
  catch (Exception e){
  UserFieldFunction userFieldFunction_111 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();
    userFieldFunction_111.getTypeOption().setSelected(FieldFunctionTypeOption.Type.VECTOR);
    userFieldFunction_111.setPresentationName("Velocity Avg.");
    userFieldFunction_111.setFunctionName("v_avg");
    Units units_2 = 
      simulation_0.getUnitsManager().getPreferredUnits(new IntVector(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}));
    userFieldFunction_111.setDefinition("[${V[i]Monitor},${V[j]Monitor},${V[k]Monitor}]");
  }

  Units units_0 = 
      ((Units) simulation_0.getUnitsManager().getObject("m"));
  Units units_1 = 
      ((Units) simulation_0.getUnitsManager().getObject("mm"));

  // Plane X //

  try {
  PlaneSection planeSection_0 = 
      ((PlaneSection) simulation_0.getPartManager().getObject("StaticCPsectionx"));
  planeSection_0.getOrientationCoordinate().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {1.0, 0.0, 0.0}));
  planeSection_0.getOriginCoordinate().setCoordinate(units_1, units_0, units_0, new DoubleVector(new double[] {1000.0, 0.0, 0.0}));
  }
  catch (Exception e){
  PlaneSection planeSection_0 = 
      (PlaneSection) simulation_0.getPartManager().createImplicitPart(new NeoObjectVector(new Object[] {}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), new DoubleVector(new double[] {0.0, 0.0, 0.0}), 0, 1, new DoubleVector(new double[] {0.0}));
  planeSection_0.setPresentationName("StaticCPsectionx");
  planeSection_0.getInputParts().setQuery(new Query(new TypePredicate(TypeOperator.Is, Region.class), Query.STANDARD_MODIFIERS));
  planeSection_0.getOrientationCoordinate().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {1.0, 0.0, 0.0}));
  planeSection_0.getOriginCoordinate().setCoordinate(units_1, units_0, units_0, new DoubleVector(new double[] {1000.0, 0.0, 0.0}));
  }

  PlaneSection planeSection_0 = 
      ((PlaneSection) simulation_0.getPartManager().getObject("StaticCPsectionx"));

    simulation_0.getSceneManager().createEmptyScene("Scene");
  Scene scene_0 = 
      simulation_0.getSceneManager().getScene("Scene 1");
  scene_0.initializeAndWait();  
  SceneUpdate sceneUpdate_0 = 
      scene_0.getSceneUpdate();
  scene_0.setPresentationName("StaticCPx");  
  scene_0.setAspectRatio(AspectRatioEnum.RATIO_16_9);
  scene_0.setAxesVisible(false);
  
  LogoAnnotation logoAnnotation_0 = 
      ((LogoAnnotation) simulation_0.getAnnotationManager().getObject("Logo"));
    scene_0.getAnnotationPropManager().removePropsForAnnotations(logoAnnotation_0);

    ImageAnnotation2D imageAnnotation2D_0 = 
      ((ImageAnnotation2D) simulation_0.getAnnotationManager().getObject("Line"));
    ImageAnnotationProp2D imageAnnotationProp2D_0 = 
      (ImageAnnotationProp2D) scene_0.getAnnotationPropManager().createPropForAnnotation(imageAnnotation2D_0);
    SimpleAnnotation simpleAnnotation_0 = 
      ((SimpleAnnotation) simulation_0.getAnnotationManager().getObject("Setup column 1"));
    SimpleAnnotationProp simpleAnnotationProp_0 = 
      (SimpleAnnotationProp) scene_0.getAnnotationPropManager().createPropForAnnotation(simpleAnnotation_0);
    SimpleAnnotation simpleAnnotation_1 = 
      ((SimpleAnnotation) simulation_0.getAnnotationManager().getObject("Setup column 2"));
    SimpleAnnotationProp simpleAnnotationProp_1 = 
      (SimpleAnnotationProp) scene_0.getAnnotationPropManager().createPropForAnnotation(simpleAnnotation_1);
    SimpleAnnotation simpleAnnotation_2 = 
      ((SimpleAnnotation) simulation_0.getAnnotationManager().getObject("Description"));
    SimpleAnnotationProp simpleAnnotationProp_2 = 
      (SimpleAnnotationProp) scene_0.getAnnotationPropManager().createPropForAnnotation(simpleAnnotation_2);
    ImageAnnotation2D imageAnnotation2D_10 = 
      ((ImageAnnotation2D) simulation_0.getAnnotationManager().getObject("FST Black Icon"));
    ImageAnnotationProp2D imageAnnotationProp2D_10 = 
      (ImageAnnotationProp2D) scene_0.getAnnotationPropManager().createPropForAnnotation(imageAnnotation2D_10);
    imageAnnotationProp2D_10.setPosition(new DoubleVector(new double[] {0.6, 0.015, 0.0}));
  scene_0.setBackgroundColorMode(BackgroundColorMode.SOLID);
  scene_0.close();
  
  try {
      SimpleAnnotation simpleAnnotation_3 = 
      ((SimpleAnnotation) simulation_0.getAnnotationManager().getObject("SCoordinateX"));
      simpleAnnotation_3.setText("X = 1000.0 mm");
      SimpleAnnotationProp simpleAnnotationProp_3 = 
      (SimpleAnnotationProp) scene_0.getAnnotationPropManager().createPropForAnnotation(simpleAnnotation_3);
  }
  catch (Exception e){
    SimpleAnnotation simpleAnnotation_3 = 
      simulation_0.getAnnotationManager().createAnnotation(SimpleAnnotation.class);
    simpleAnnotation_3.setPresentationName("SCoordinateX");
    simpleAnnotation_3.setShadow(false);
    simpleAnnotation_3.setText("X = 1000.0 mm");
    simpleAnnotation_3.setFontString("Arial-Bold");
    simpleAnnotation_3.setDefaultHeight(0.025);
    simpleAnnotation_3.setDefaultPosition(new DoubleVector(new double[] {0.9, 0.045, 0.0}));
  SimpleAnnotationProp simpleAnnotationProp_3 = 
      (SimpleAnnotationProp) scene_0.getAnnotationPropManager().createPropForAnnotation(simpleAnnotation_3);
  }

  SimpleAnnotation simpleAnnotation_3 = 
      ((SimpleAnnotation) simulation_0.getAnnotationManager().getObject("SCoordinateX"));

  try {
      ImageAnnotation2D imageAnnotation2D_1 = 
      ((ImageAnnotation2D) simulation_0.getAnnotationManager().getObject("Car-Side"));
  }
  catch (Exception e){
    final String folder = resolveWorkPath();
    ImageAnnotation2D imageAnnotation2D_1 = 
        simulation_0.getAnnotationManager().createAnnotation(ImageAnnotation2D.class);
    imageAnnotation2D_1.setFilePath("..\\Modules_Macro\\Library\\BlackSide.png");
    imageAnnotation2D_1.setPresentationName("Car-Side");
    imageAnnotation2D_1.setDefaultWidthStretchFactor(1.0);
    imageAnnotation2D_1.setDefaultHeight(0.225);
    imageAnnotation2D_1.setDefaultPosition(new DoubleVector(new double[] {0.0, 0.125, 0.0}));
  }
  
  try {

    XYPlot xYPlot_0 = 
      ((XYPlot) simulation_0.getPlotManager().getPlot("PlaneSX - Car"));

    PlotImage plotImage_0 = 
      ((PlotImage) simulation_0.getAnnotationManager().getObject("Car SX Plane"));

    PlotImageAnnotationProp plotImageAnnotationProp_3 = 
      (PlotImageAnnotationProp) scene_0.getAnnotationPropManager().createPropForAnnotation(plotImage_0);
    plotImageAnnotationProp_3.setScaleComponents(false);
    plotImageAnnotationProp_3.setLocation(DisplayLocationMode.BACKGROUND);

  }
  catch (Exception e){
    XYPlot xYPlot_0 = 
      simulation_0.getPlotManager().createPlot(XYPlot.class);
    xYPlot_0.open();
    PlotUpdate plotUpdate_0 = 
      xYPlot_0.getPlotUpdate();
    xYPlot_0.setPresentationName("PlaneSX - Car");
  xYPlot_0.close();
  xYPlot_0.getParts().setObjects(planeSection_0);

  Cartesian2DAxisManager cartesian2DAxisManager_0 = 
      ((Cartesian2DAxisManager) xYPlot_0.getAxisManager());
    Cartesian2DAxis cartesian2DAxis_0 = 
      ((Cartesian2DAxis) cartesian2DAxisManager_0.getAxis("Bottom Axis"));
    cartesian2DAxis_0.setPosition(Cartesian2DAxis.Position.Right);

  YAxisType yAxisType_0 = 
      ((YAxisType) xYPlot_0.getYAxes().getAxisType("Y Type 1"));
  
  if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
  cartesian2DAxis_0.setMinimum(-15.0);
    cartesian2DAxis_0.setMaximum(12.5);
    InternalDataSet internalDataSet_0 = 
      ((InternalDataSet) yAxisType_0.getDataSetManager().getDataSet("StaticCPsectionx"));
  internalDataSet_0.setXScale(0.8);
  } else {
  cartesian2DAxis_0.setMinimum(-40.0);
    cartesian2DAxis_0.setMaximum(50.0);
  InternalDataSet internalDataSet_0 = 
      ((InternalDataSet) yAxisType_0.getDataSetManager().getDataSet("StaticCPsectionx"));
  internalDataSet_0.setXScale(1.3);
  }
  
    Cartesian2DAxis cartesian2DAxis_1 = 
      ((Cartesian2DAxis) cartesian2DAxisManager_0.getAxis("Left Axis"));
    cartesian2DAxis_1.setPosition(Cartesian2DAxis.Position.Bottom);
  cartesian2DAxis_1.setMinimum(-4.0);
  cartesian2DAxis_1.setMaximum(9.0);

    AxisType axisType_0 = 
      xYPlot_0.getXAxisType();
    axisType_0.getDirectionVector().setComponents(0.0, 1.0, 0.0);
  axisType_0.getDirectionVector().setUnits(units_0);

    FieldFunctionUnits fieldFunctionUnits_0 = 
      yAxisType_0.getScalarFunction();

    PrimitiveFieldFunction primitiveFieldFunction_0 = 
      ((PrimitiveFieldFunction) simulation_0.getFieldFunctionManager().getFunction("Position"));
    VectorComponentFieldFunction vectorComponentFieldFunction_0 = 
      ((VectorComponentFieldFunction) primitiveFieldFunction_0.getComponentFunction(0));
    fieldFunctionUnits_0.setFieldFunction(vectorComponentFieldFunction_0);

    InternalDataSet internalDataSet_0 = 
      ((InternalDataSet) yAxisType_0.getDataSetManager().getDataSet("StaticCPsectionx"));
    internalDataSet_0.setShowLegendEntry(false);
    LineStyle lineStyle_0 = 
      internalDataSet_0.getLineStyle();
    lineStyle_0.getLinePatternOption().setSelected(LinePatternOption.Type.SOLID);
    SymbolStyleWithSpacing symbolStyleWithSpacing_0 = 
      internalDataSet_0.getSymbolStyle();
    symbolStyleWithSpacing_0.getSymbolShapeOption().setSelected(SymbolShapeOption.Type.NONE);
    lineStyle_0.setWidth(3);
    cartesian2DAxis_1.setVisible(false);
    cartesian2DAxis_0.setVisible(false);
    MultiColLegend multiColLegend_0 = 
      xYPlot_0.getLegend();
    multiColLegend_0.setVisible(false);
    xYPlot_0.setTitle("");
    xYPlot_0.setAspectRatio(AspectRatioEnum.RATIO_16_9);
    ImageAnnotation2D imageAnnotation2D_1 = 
      ((ImageAnnotation2D) simulation_0.getAnnotationManager().getObject("Car-Side"));
    ImageAnnotationProp2D imageAnnotationProp2D_1 = 
      (ImageAnnotationProp2D) xYPlot_0.getAnnotationPropManager().createPropForAnnotation(imageAnnotation2D_1);
  imageAnnotationProp2D_1.setWidthStretchFactor(1.0);
  imageAnnotationProp2D_1.setPosition(new DoubleVector(new double[] {0.1669, 0.3417, 0.0}));
  imageAnnotationProp2D_1.setLocation(DisplayLocationMode.BACKGROUND);
  
  PlotImage plotImage_0 = 
      simulation_0.getAnnotationManager().createAnnotation(PlotImage.class);
    plotImage_0.setPresentationName("Car SX Plane");
    plotImage_0.setViewObject(xYPlot_0);
    plotImage_0.setDefaultScaleComponents(false);
    plotImage_0.setDefaultPosition(new DoubleVector(new double[] {0.68, -0.162, 0.0}));

    PlotImageAnnotationProp plotImageAnnotationProp_3 = 
      (PlotImageAnnotationProp) scene_0.getAnnotationPropManager().createPropForAnnotation(plotImage_0);
    plotImageAnnotationProp_3.setScaleComponents(false);
    plotImageAnnotationProp_3.setLocation(DisplayLocationMode.BACKGROUND);

  }

  XYPlot xYPlot_0 = 
      ((XYPlot) simulation_0.getPlotManager().getPlot("PlaneSX - Car"));

  scene_0.setBackgroundColorMode(BackgroundColorMode.SOLID);
  scene_0.close();
  
  ScalarDisplayer scalarDisplayer_0 = 
      scene_0.getDisplayerManager().createScalarDisplayer("Scalar");
    scalarDisplayer_0.initialize();
    scalarDisplayer_0.setPresentationName("StaticCPx");
    scalarDisplayer_0.getInputParts().setObjects(planeSection_0);
  
  ClipPlane clipPlane_0 = 
      ((ClipPlane) scene_0.getPlaneManager().getPlane("Plane 1"));
    ClipPlane clipPlane_1 = 
      ((ClipPlane) scene_0.getPlaneManager().getPlane("Plane 2"));
    ClipPlane clipPlane_2 = 
      ((ClipPlane) scene_0.getPlaneManager().getPlane("Plane 3"));
    clipPlane_0.getOriginCoordinate().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {0.0, 0.0, 1.8}));
    clipPlane_1.getOriginCoordinate().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {0.0, 1.5, 0.0}));
    clipPlane_2.getOriginCoordinate().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {0.0, -1.5, 0.0}));
    clipPlane_0.setNormal(new DoubleVector(new double[] {0.0, 0.0, -1.0}));
    clipPlane_1.setNormal(new DoubleVector(new double[] {0.0, -1.0, 0.0}));
    clipPlane_2.setNormal(new DoubleVector(new double[] {0.0, 1.0, 0.0}));
    clipPlane_0.setEnabled(true);
    clipPlane_1.setEnabled(true);
    clipPlane_2.setEnabled(true);
  
  if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
      SymmetricRepeat symmetricRepeat_0 = 
      ((SymmetricRepeat) simulation_0.getTransformManager().getObject("Domain.Wall_Inner 1"));
    scalarDisplayer_0.setVisTransform(symmetricRepeat_0);}
  
  try {
    UserFieldFunction userFieldFunction_0 = 
      ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("StaticCp"));
    scalarDisplayer_0.getScalarDisplayQuantity().setFieldFunction(userFieldFunction_0);

  }
  catch (Exception e){
    UserFieldFunction userFieldFunction_0 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();
    userFieldFunction_0.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);
    userFieldFunction_0.setFunctionName("StaticCp");
    userFieldFunction_0.setPresentationName("StaticCp");
  
    if ( CornerRadius == 0) {
    userFieldFunction_0.setDefinition("(${TotalPressureMonitor}-${Pressure Avg. Inlet}-(0.5*1.225*pow(${car_velocity[ms-1]},2)))/(0.5*1.225*pow(${car_velocity[ms-1]},2))"); 
    } else {
    userFieldFunction_0.setDefinition("(${TotalPressureMonitor}-${Pressure Avg. Inlet}-(0.5*1.225*pow(${Angular_Velocity}*($$Position(@CoordinateSystem(\"Laboratory.Air Orientation - Car.Air Center\"))[0]),2)))/(0.5*1.225*pow(${Angular_Velocity}*($$Position(@CoordinateSystem(\"Laboratory.Air Orientation - Car.Air Center\"))[0]),2))");} 
    }
  UserFieldFunction userFieldFunction_0 = 
      ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("StaticCp"));
  scalarDisplayer_0.getScalarDisplayQuantity().setFieldFunction(userFieldFunction_0);

  try {
  UserLookupTable userLookupTable_0 = 
      ((UserLookupTable) simulation_0.get(LookupTableManager.class).getObject("Carreira_StaticCP_-2+1"));
  }
  catch (Exception e){
    
  UserLookupTable userLookupTable_0 = 
      simulation_0.get(LookupTableManager.class).createLookupTable();
    userLookupTable_0.setPresentationName("Carreira_StaticCP_-2+1");  
  userLookupTable_0.setColorMap(new ColorMap(new DoubleVector(new double[] {0.0, 1.0, 0.0, 1.0, 0.15, 0.0, 0.0, 0.9215686274509803, 0.21, 0.2478402969473144, 0.0, 0.892816517288296, 0.34, 0.0, 1.0, 1.0, 0.4, 0.3258965792972184, 0.9454054260487254, 0.9454054260487254, 0.51, 0.0, 1.0, 0.0, 0.62, 0.6862745098039216, 0.6862745098039216, 0.6862745098039216, 0.69, 0.6862745098039216, 0.6862745098039216, 0.6862745098039216, 0.8688524590163934, 1.0, 1.0, 0.0, 0.975, 1.0, 0.0, 0.0, 1.0, 0.5019607843137255, 0.0, 0.0}), new DoubleVector(new double[] {0.0, 1.0, 1.0, 1.0}), 0));
  }
  //}
  
  UserLookupTable userLookupTable_0 = 
      ((UserLookupTable) simulation_0.get(LookupTableManager.class).getObject("Carreira_StaticCP_-2+1"));
  
  Legend legend_0 = 
      scalarDisplayer_0.getLegend();
    legend_0.setLookupTable(userLookupTable_0);
  legend_0.setLevels(54);
    legend_0.setWidth(0.21);
    legend_0.setFontString("Arial-Bold");
    legend_0.setShadow(false);
    legend_0.setHeight(0.025);
    legend_0.setTitleHeight(0.03);
    legend_0.setLabelHeight(0.02);
    legend_0.setPositionCoordinate(new DoubleVector(new double[] {0.02, 0.03}));
    legend_0.setLabelFormat("%1.1f");
    legend_0.setRampType(RampType.LINEAR);
    legend_0.setNumberOfLabels(5);
  
    scalarDisplayer_0.setFillMode(ScalarFillMode.NODE_SMOOTH);
    scalarDisplayer_0.getInterpolationOption().setSelected(ScalarInterpolationOption.Type.BANDED);
    scalarDisplayer_0.getScalarDisplayQuantity().setRange(new DoubleVector(new double[] {-2.0, 1.0}));
    scalarDisplayer_0.getScalarDisplayQuantity().setClip(ClipMode.NONE);
  
  VectorDisplayer vectorDisplayer_0 = 
      scene_0.getDisplayerManager().createVectorDisplayer("Vector");
  vectorDisplayer_0.initialize();
    Legend legend_1 = 
      vectorDisplayer_0.getLegend();
    BlueRedLookupTable blueRedLookupTable_1 = 
      ((BlueRedLookupTable) simulation_0.get(LookupTableManager.class).getObject("blue-red"));
    legend_1.setLookupTable(blueRedLookupTable_1);
    vectorDisplayer_0.setPresentationName("LIC");
  vectorDisplayer_0.setOpacity(0.4);
    vectorDisplayer_0.setDisplayMode(VectorDisplayMode.VECTOR_DISPLAY_MODE_LIC);
    vectorDisplayer_0.setColorMode(VectorColorMode.SCALAR);
    vectorDisplayer_0.getInputParts().setObjects(planeSection_0);
  
  if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
  SymmetricRepeat symmetricRepeat_1 = 
      ((SymmetricRepeat) simulation_0.getTransformManager().getObject("Domain.Wall_Inner 1"));
    vectorDisplayer_0.setVisTransform(symmetricRepeat_1);}
  
    UserFieldFunction userFieldFunction_1 = 
      ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("v_avg"));
    vectorDisplayer_0.getVectorDisplayQuantity().setFieldFunction(userFieldFunction_1); 
    
    vectorDisplayer_0.getColoringScalar().setFieldFunction(userFieldFunction_0);
  LICSettings lICSettings_0 = 
      vectorDisplayer_0.getLICSettings();
    lICSettings_0.setNumberOfSteps(40);
    lICSettings_0.setIntensity(1.0);
    vectorDisplayer_0.getColoringScalar().setRange(new DoubleVector(new double[] {-1.0, 1.0}));
    vectorDisplayer_0.getColoringScalar().setClip(ClipMode.NONE);
  
  legend_1.setVisible(false);

  /*PartDisplayer partDisplayer_10 = 
      scene_0.getDisplayerManager().createPartDisplayer("Outline", -1, 4);
    PlaneSection planeSection_10 = 
      ((PlaneSection) simulation_0.getPartManager().getObject("CPTsectionx"));
      partDisplayer_10.getInputParts().setObjects(planeSection_10);*/
  
  CurrentView currentView_0 = 
      scene_0.getCurrentView();
  
  scene_0.setViewOrientation(new DoubleVector(new double[] {1.0, -0.0, -0.0}), new DoubleVector(new double[] {0.0, 0.0, 1.0}));

  VisView visView_0 = 
      ((VisView) simulation_0.getViewManager().getObject("front"));
    currentView_0.setView(visView_0);
  scene_0.printAndWait(resolvePath("..\\Post_Processing\\StaticCpx\\0 StaticCpx1000mm.jpg"), 1, 1920, 1080, true, false);
  
  for (int i = 1; i < 220; i++) {   // CHANGE I BACK TO 220
    planeSection_0.getOriginCoordinate().setCoordinate(units_1, units_0, units_0, new DoubleVector(new double[] {(1000-i*15), 0.0, 0.0}));
  currentView_0.setView(visView_0);
  double coordinate = 1000 - i*15; 
  simpleAnnotation_3.setText("X = "+ coordinate +" mm");
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\StaticCpx\\"+ i +" StaticCpx"+ coordinate +"mm.jpg"), 1, 1920, 1080, true, false);
  }

  try {
    ((ClientServerObjectGroup) simulation_0.getPlotManager().getGroupsManager().getObject("Plane Aux")).setPresentationName("Plane Aux");
  }
  catch (Exception e){
  simulation_0.getPlotManager().getGroupsManager().createGroup("Plane Aux");
}
  
  ((ClientServerObjectGroup) simulation_0.getPlotManager().getGroupsManager().getObject("Plane Aux")).getGroupsManager().groupObjects("Plane Aux", new NeoObjectVector(new Object[] {xYPlot_0}), true);
  
  }

  private void CfX() {

  Simulation simulation_0 = 
    getActiveSimulation();
    
  double Roll = simulation_0.getReportManager().getReport("Roll [deg]").getReportMonitorValue();
  double Yaw = simulation_0.getReportManager().getReport("Yaw [rad]").getReportMonitorValue();
  double CornerRadius = simulation_0.getReportManager().getReport("Corner Radius [m]").getReportMonitorValue();

  simulation_0.getSceneManager().createEmptyScene("Scene");  
    
  Scene scene_0 = 
      simulation_0.getSceneManager().getScene("Scene 1");
    scene_0.initializeAndWait();  
  SceneUpdate sceneUpdate_0 = 
      scene_0.getSceneUpdate();
  scene_0.setPresentationName("CFx");  
  scene_0.setAspectRatio(AspectRatioEnum.RATIO_16_9);
  scene_0.setAxesViewport(new DoubleVector(new double[] {0.88, 0.0, 1.0, 0.14}));
  
  LogoAnnotation logoAnnotation_0 = 
      ((LogoAnnotation) simulation_0.getAnnotationManager().getObject("Logo"));
    scene_0.getAnnotationPropManager().removePropsForAnnotations(logoAnnotation_0);

    ImageAnnotation2D imageAnnotation2D_0 = 
      ((ImageAnnotation2D) simulation_0.getAnnotationManager().getObject("Line"));
    ImageAnnotationProp2D imageAnnotationProp2D_0 = 
      (ImageAnnotationProp2D) scene_0.getAnnotationPropManager().createPropForAnnotation(imageAnnotation2D_0);
    SimpleAnnotation simpleAnnotation_0 = 
      ((SimpleAnnotation) simulation_0.getAnnotationManager().getObject("Setup column 1"));
    SimpleAnnotationProp simpleAnnotationProp_0 = 
      (SimpleAnnotationProp) scene_0.getAnnotationPropManager().createPropForAnnotation(simpleAnnotation_0);
    SimpleAnnotation simpleAnnotation_1 = 
      ((SimpleAnnotation) simulation_0.getAnnotationManager().getObject("Setup column 2"));
    SimpleAnnotationProp simpleAnnotationProp_1 = 
      (SimpleAnnotationProp) scene_0.getAnnotationPropManager().createPropForAnnotation(simpleAnnotation_1);
    SimpleAnnotation simpleAnnotation_2 = 
      ((SimpleAnnotation) simulation_0.getAnnotationManager().getObject("Description"));
    SimpleAnnotationProp simpleAnnotationProp_2 = 
      (SimpleAnnotationProp) scene_0.getAnnotationPropManager().createPropForAnnotation(simpleAnnotation_2);
    ImageAnnotation2D imageAnnotation2D_1 = 
      ((ImageAnnotation2D) simulation_0.getAnnotationManager().getObject("FST Black Icon"));
    ImageAnnotationProp2D imageAnnotationProp2D_1 = 
      (ImageAnnotationProp2D) scene_0.getAnnotationPropManager().createPropForAnnotation(imageAnnotation2D_1);
  scene_0.setBackgroundColorMode(BackgroundColorMode.SOLID);
  scene_0.close();
  
  ScalarDisplayer scalarDisplayer_0 = 
      scene_0.getDisplayerManager().createScalarDisplayer("Scalar");
    scalarDisplayer_0.initialize();
    scalarDisplayer_0.setPresentationName("CFx");
  
  Region region_0 = 
      simulation_0.getRegionManager().getRegion("Fluid");
  
  scalarDisplayer_0.getInputParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, InterfaceBoundary.class), new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.DoesNotContain, "Domain"), new NamePredicate(NameOperator.DoesNotContain, "FAN"), new NamePredicate(NameOperator.DoesNotContain, "RADIATOR"))))), Query.STANDARD_MODIFIERS));
  
  if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
      SymmetricRepeat symmetricRepeat_0 = 
      ((SymmetricRepeat) simulation_0.getTransformManager().getObject("Domain.Wall_Inner 1"));
    scalarDisplayer_0.setVisTransform(symmetricRepeat_0);}
  
  try {
    UserFieldFunction userFieldFunction_0 = 
      ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("CFx"));
    scalarDisplayer_0.getScalarDisplayQuantity().setFieldFunction(userFieldFunction_0);
  }
  catch (Exception e){
    UserFieldFunction userFieldFunction_0 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();
    userFieldFunction_0.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);
    userFieldFunction_0.setPresentationName("CFx");
    userFieldFunction_0.setFunctionName("CFx");
  
    if ( CornerRadius == 0) {
    userFieldFunction_0.setDefinition("${Wss[i]Monitor}*-1/(0.5*1.225*pow(${car_velocity[ms-1]},2))");
    scalarDisplayer_0.getScalarDisplayQuantity().setFieldFunction(userFieldFunction_0);
    } else {
    userFieldFunction_0.setDefinition("${Wss[i]Monitor}*-1/(0.5*1.225*pow(${Angular_Velocity}*($$Position(@CoordinateSystem(\"Laboratory.Air Orientation - Car.Air Center\"))[0]),2))");
    scalarDisplayer_0.getScalarDisplayQuantity().setFieldFunction(userFieldFunction_0);
  }
}
  
  
  //{ // Colorbar
  BlueRedLookupTable blueRedLookupTable_0 = 
      ((BlueRedLookupTable) simulation_0.get(LookupTableManager.class).getObject("blue-red"));
  //}
  
  Legend legend_0 = 
      scalarDisplayer_0.getLegend();
    legend_0.setLookupTable(blueRedLookupTable_0);
    legend_0.setWidth(0.21);
    legend_0.setFontString("Arial-Bold");
    legend_0.setShadow(false);
    legend_0.setHeight(0.025);
    legend_0.setTitleHeight(0.03);
    legend_0.setLabelHeight(0.02);
    legend_0.setPositionCoordinate(new DoubleVector(new double[] {0.02, 0.03}));
    legend_0.setLabelFormat("%1.1f");
    legend_0.setRampType(RampType.LINEAR);
    legend_0.setLevels(2);
    legend_0.setNumberOfLabels(3);
  
  
    scalarDisplayer_0.setFillMode(ScalarFillMode.NODE_FILLED);
    scalarDisplayer_0.getScalarDisplayQuantity().setRange(new DoubleVector(new double[] {-0.02, 0.02}));
    scalarDisplayer_0.getScalarDisplayQuantity().setClip(ClipMode.MIN);
  
    scene_0.export3DSceneFileAndWait(resolvePath("..\\Post_Processing\\CFx\\Scene_CFx.sce"), "CFx", simpleAnnotation_0.getText(), false, true);
  
  CurrentView currentView_0 = 
      scene_0.getCurrentView();
  
  VisView visView_0 = 
      ((VisView) simulation_0.getViewManager().getObject("bottom"));
    currentView_0.setView(visView_0);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\CFx\\bottom.jpg"), 1, 1920, 1080, true, false);
  
  VisView visView_1 = 
      ((VisView) simulation_0.getViewManager().getObject("bottom_front_left"));
    currentView_0.setView(visView_1);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\CFx\\bottom_front_left.jpg"), 1, 1920, 1080, true, false);
  
  VisView visView_2 = 
      ((VisView) simulation_0.getViewManager().getObject("bottom_front_right"));
    currentView_0.setView(visView_2);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\CFx\\bottom_front_right.jpg"), 1, 1920, 1080, true, false);
  
  VisView visView_3 = 
      ((VisView) simulation_0.getViewManager().getObject("bottom_rear_left"));
    currentView_0.setView(visView_3);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\CFx\\bottom_rear_left.jpg"), 1, 1920, 1080, true, false);
  
  VisView visView_4 = 
      ((VisView) simulation_0.getViewManager().getObject("bottom_rear_right"));
    currentView_0.setView(visView_4);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\CFx\\bottom_rear_right.jpg"), 1, 1920, 1080, true, false);
  
  VisView visView_5 = 
      ((VisView) simulation_0.getViewManager().getObject("front"));
    currentView_0.setView(visView_5);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\CFx\\front.jpg"), 1, 1920, 1080, true, false);
  
  VisView visView_6 = 
      ((VisView) simulation_0.getViewManager().getObject("left"));
    currentView_0.setView(visView_6);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\CFx\\left.jpg"), 1, 1920, 1080, true, false);
  
  VisView visView_7 = 
      ((VisView) simulation_0.getViewManager().getObject("rear"));
    currentView_0.setView(visView_7);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\CFx\\rear.jpg"), 1, 1920, 1080, true, false);
  
  VisView visView_8 = 
      ((VisView) simulation_0.getViewManager().getObject("right"));
    currentView_0.setView(visView_8);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\CFx\\right.jpg"), 1, 1920, 1080, true, false);
  
  VisView visView_9 = 
      ((VisView) simulation_0.getViewManager().getObject("top"));
    currentView_0.setView(visView_9);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\CFx\\top.jpg"), 1, 1920, 1080, true, false);
  
  VisView visView_10 = 
      ((VisView) simulation_0.getViewManager().getObject("top_front_left"));
    currentView_0.setView(visView_10);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\CFx\\top_front_left.jpg"), 1, 1920, 1080, true, false);
  
  VisView visView_11 = 
      ((VisView) simulation_0.getViewManager().getObject("top_front_right"));
    currentView_0.setView(visView_11);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\CFx\\top_front_right.jpg"), 1, 1920, 1080, true, false);
  
  VisView visView_12 = 
      ((VisView) simulation_0.getViewManager().getObject("top_rear_left"));
    currentView_0.setView(visView_12);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\CFx\\top_rear_left.jpg"), 1, 1920, 1080, true, false);
  
  VisView visView_13 = 
      ((VisView) simulation_0.getViewManager().getObject("top_rear_right"));
    currentView_0.setView(visView_13);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\CFx\\top_rear_right.jpg"), 1, 1920, 1080, true, false);


  }
  

  private void CFStream() {
  
  /*CurrentView currentView_0 = 
      scene_0.getCurrentView();
  
  VisView visView_0 = 
      ((VisView) simulation_0.getViewManager().getObject("bottom"));
    currentView_0.setView(visView_0);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\CF-X\\bottom.jpg"), 1, 1920, 1080, true, false);
  
  VisView visView_1 = 
      ((VisView) simulation_0.getViewManager().getObject("bottom_front_left"));
    currentView_0.setView(visView_1);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\CF-X\\bottom_front_left.jpg"), 1, 1920, 1080, true, false);
  
  VisView visView_2 = 
      ((VisView) simulation_0.getViewManager().getObject("bottom_front_right"));
    currentView_0.setView(visView_2);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\CF-X\\bottom_front_right.jpg"), 1, 1920, 1080, true, false);
  
  VisView visView_3 = 
      ((VisView) simulation_0.getViewManager().getObject("bottom_rear_left"));
    currentView_0.setView(visView_3);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\CF-X\\bottom_rear_left.jpg"), 1, 1920, 1080, true, false);
  
  VisView visView_4 = 
      ((VisView) simulation_0.getViewManager().getObject("bottom_rear_right"));
    currentView_0.setView(visView_4);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\CF-X\\bottom_rear_right.jpg"), 1, 1920, 1080, true, false);
  
  VisView visView_5 = 
      ((VisView) simulation_0.getViewManager().getObject("front"));
    currentView_0.setView(visView_5);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\CF-X\\front.jpg"), 1, 1920, 1080, true, false);
  
  VisView visView_6 = 
      ((VisView) simulation_0.getViewManager().getObject("left"));
    currentView_0.setView(visView_6);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\CF-X\\left.jpg"), 1, 1920, 1080, true, false);
  
  VisView visView_7 = 
      ((VisView) simulation_0.getViewManager().getObject("rear"));
    currentView_0.setView(visView_7);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\CF-X\\rear.jpg"), 1, 1920, 1080, true, false);
  
  VisView visView_8 = 
      ((VisView) simulation_0.getViewManager().getObject("right"));
    currentView_0.setView(visView_8);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\CF-X\\right.jpg"), 1, 1920, 1080, true, false);
  
  VisView visView_9 = 
      ((VisView) simulation_0.getViewManager().getObject("top"));
    currentView_0.setView(visView_9);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\CF-X\\top.jpg"), 1, 1920, 1080, true, false);
  
  VisView visView_10 = 
      ((VisView) simulation_0.getViewManager().getObject("top_front_left"));
    currentView_0.setView(visView_10);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\CF-X\\top_front_left.jpg"), 1, 1920, 1080, true, false);
  
  VisView visView_11 = 
      ((VisView) simulation_0.getViewManager().getObject("top_front_right"));
    currentView_0.setView(visView_11);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\CF-X\\top_front_right.jpg"), 1, 1920, 1080, true, false);
  
  VisView visView_12 = 
      ((VisView) simulation_0.getViewManager().getObject("top_rear_left"));
    currentView_0.setView(visView_12);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\CF-X\\top_rear_left.jpg"), 1, 1920, 1080, true, false);
  
  VisView visView_13 = 
      ((VisView) simulation_0.getViewManager().getObject("top_rear_right"));
    currentView_0.setView(visView_13);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\CF-X\\top_rear_right.jpg"), 1, 1920, 1080, true, false);*/

}

  private void Qcrit() {

  Simulation simulation_0 = 
    getActiveSimulation();
    
  double Roll = simulation_0.getReportManager().getReport("Roll [deg]").getReportMonitorValue();
  double Yaw = simulation_0.getReportManager().getReport("Yaw [rad]").getReportMonitorValue();
  double CornerRadius = simulation_0.getReportManager().getReport("Corner Radius [m]").getReportMonitorValue();

  simulation_0.getSceneManager().createEmptyScene("Scene");  
    
  Scene scene_0 = 
      simulation_0.getSceneManager().getScene("Scene 1");
    scene_0.initializeAndWait();  
  SceneUpdate sceneUpdate_0 = 
      scene_0.getSceneUpdate();
  scene_0.setPresentationName("Qcrit");  
  scene_0.setAspectRatio(AspectRatioEnum.RATIO_16_9);
  scene_0.setAxesViewport(new DoubleVector(new double[] {0.88, 0.0, 1.0, 0.14}));
  
  LogoAnnotation logoAnnotation_0 = 
      ((LogoAnnotation) simulation_0.getAnnotationManager().getObject("Logo"));
    scene_0.getAnnotationPropManager().removePropsForAnnotations(logoAnnotation_0);

    ImageAnnotation2D imageAnnotation2D_0 = 
      ((ImageAnnotation2D) simulation_0.getAnnotationManager().getObject("Line"));
    ImageAnnotationProp2D imageAnnotationProp2D_0 = 
      (ImageAnnotationProp2D) scene_0.getAnnotationPropManager().createPropForAnnotation(imageAnnotation2D_0);
    SimpleAnnotation simpleAnnotation_0 = 
      ((SimpleAnnotation) simulation_0.getAnnotationManager().getObject("Setup column 1"));
    SimpleAnnotationProp simpleAnnotationProp_0 = 
      (SimpleAnnotationProp) scene_0.getAnnotationPropManager().createPropForAnnotation(simpleAnnotation_0);
    SimpleAnnotation simpleAnnotation_1 = 
      ((SimpleAnnotation) simulation_0.getAnnotationManager().getObject("Setup column 2"));
    SimpleAnnotationProp simpleAnnotationProp_1 = 
      (SimpleAnnotationProp) scene_0.getAnnotationPropManager().createPropForAnnotation(simpleAnnotation_1);
    SimpleAnnotation simpleAnnotation_2 = 
      ((SimpleAnnotation) simulation_0.getAnnotationManager().getObject("Description"));
    SimpleAnnotationProp simpleAnnotationProp_2 = 
      (SimpleAnnotationProp) scene_0.getAnnotationPropManager().createPropForAnnotation(simpleAnnotation_2);
    ImageAnnotation2D imageAnnotation2D_1 = 
      ((ImageAnnotation2D) simulation_0.getAnnotationManager().getObject("FST Black Icon"));
    ImageAnnotationProp2D imageAnnotationProp2D_1 = 
      (ImageAnnotationProp2D) scene_0.getAnnotationPropManager().createPropForAnnotation(imageAnnotation2D_1);
  scene_0.setBackgroundColorMode(BackgroundColorMode.SOLID);
  scene_0.close();
  
  ScalarDisplayer scalarDisplayer_0 = 
      scene_0.getDisplayerManager().createScalarDisplayer("Scalar");
    scalarDisplayer_0.initialize();
    scalarDisplayer_0.setPresentationName("Qcrit");

    Units units_2 = 
      simulation_0.getUnitsManager().getPreferredUnits(new IntVector(new int[] {0, 0, -2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));
    Region region_0 = 
      simulation_0.getRegionManager().getRegion("Fluid");
    PrimitiveFieldFunction primitiveFieldFunction_0 = 
      ((PrimitiveFieldFunction) simulation_0.getFieldFunctionManager().getFunction("QcritMonitor"));
    IsoPart isoPart_0 = 
    simulation_0.getPartManager().createIsoPart(new NeoObjectVector(new Object[] {region_0}), primitiveFieldFunction_0);
    isoPart_0.setMode(IsoMode.ISOVALUE_SINGLE);
    SingleIsoValue singleIsoValue_0 = 
      isoPart_0.getSingleIsoValue();
    singleIsoValue_0.getValueQuantity().setValue(10000.0);
    singleIsoValue_0.getValueQuantity().setUnits(units_2);

  PartDisplayer partDisplayer_0 = 
    scene_0.getDisplayerManager().createPartDisplayer("Geometry", -1, 4);
  partDisplayer_0.getInputParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, InterfaceBoundary.class), new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.DoesNotContain, "Domain"), new NamePredicate(NameOperator.DoesNotContain, "FAN"), new NamePredicate(NameOperator.DoesNotContain, "RADIATOR"))))), Query.STANDARD_MODIFIERS));
  scalarDisplayer_0.getInputParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, InterfaceBoundary.class), new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.DoesNotContain, "Domain"), new NamePredicate(NameOperator.DoesNotContain, "FAN"), new NamePredicate(NameOperator.DoesNotContain, "RADIATOR"))))), Query.STANDARD_MODIFIERS));
  isoPart_0.setPresentationName("Q_crit");
  scalarDisplayer_0.getInputParts().setObjects(isoPart_0);
  partDisplayer_0.setOutline(true);
  partDisplayer_0.setFeatures(true);
  partDisplayer_0.setSurface(true);

  try {
    UserFieldFunction userFieldFunction_0 = 
      ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("CpT"));
    scalarDisplayer_0.getScalarDisplayQuantity().setFieldFunction(userFieldFunction_0);

  }
  catch (Exception e){
    UserFieldFunction userFieldFunction_0 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();
    userFieldFunction_0.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);
    userFieldFunction_0.setFunctionName("CpT");
    userFieldFunction_0.setPresentationName("CpT");
  
    if ( CornerRadius == 0) {
    userFieldFunction_0.setDefinition("(${TotalPressureMonitor}-${Pressure Avg. Inlet})/(0.5*1.225*pow(${car_velocity[ms-1]},2))"); 
    } else {
    userFieldFunction_0.setDefinition("(${TotalPressureMonitor}-${Pressure Avg. Inlet})/(0.5*1.225*pow(${Angular_Velocity}*($$Position(@CoordinateSystem(\"Laboratory.Air Orientation - Car.Air Center\"))[0]),2))");} 
    }

    UserFieldFunction userFieldFunction_0 = 
      ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("CpT"));
  scalarDisplayer_0.getScalarDisplayQuantity().setFieldFunction(userFieldFunction_0);

    if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
      SymmetricRepeat symmetricRepeat_0 = 
      ((SymmetricRepeat) simulation_0.getTransformManager().getObject("Domain.Wall_Inner 1"));
    scalarDisplayer_0.setVisTransform(symmetricRepeat_0);
    partDisplayer_0.setVisTransform(symmetricRepeat_0);} 
  
  //{ // Colorbar
  BlueRedLookupTable blueRedLookupTable_0 = 
      ((BlueRedLookupTable) simulation_0.get(LookupTableManager.class).getObject("blue-red"));
  //}
  
  Legend legend_0 = 
      scalarDisplayer_0.getLegend();
    legend_0.setLookupTable(blueRedLookupTable_0);
    legend_0.setLevels(54);
    legend_0.setWidth(0.21);
    legend_0.setFontString("Arial-Bold");
    legend_0.setShadow(false);
    legend_0.setHeight(0.025);
    legend_0.setTitleHeight(0.03);
    legend_0.setLabelHeight(0.02);
    legend_0.setPositionCoordinate(new DoubleVector(new double[] {0.02, 0.03}));
    legend_0.setLabelFormat("%1.1f");
    legend_0.setRampType(RampType.LINEAR);
    legend_0.setNumberOfLabels(5);
  
  
    scalarDisplayer_0.setFillMode(ScalarFillMode.NODE_FILLED);
    scalarDisplayer_0.getScalarDisplayQuantity().setRange(new DoubleVector(new double[] {-0.5, 1}));
    scalarDisplayer_0.getScalarDisplayQuantity().setClip(ClipMode.NONE);
  
    scene_0.export3DSceneFileAndWait(resolvePath("..\\Post_Processing\\Qcrit\\Scene_Qcrit.sce"), "Qcrit", simpleAnnotation_0.getText(), false, true);
  
  CurrentView currentView_0 = 
      scene_0.getCurrentView();
  
  VisView visView_0 = 
      ((VisView) simulation_0.getViewManager().getObject("bottom"));
    currentView_0.setView(visView_0);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\Qcrit\\bottom.jpg"), 1, 1920, 1080, true, false);
  
  VisView visView_1 = 
      ((VisView) simulation_0.getViewManager().getObject("bottom_front_left"));
    currentView_0.setView(visView_1);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\Qcrit\\bottom_front_left.jpg"), 1, 1920, 1080, true, false);
  
  VisView visView_2 = 
      ((VisView) simulation_0.getViewManager().getObject("bottom_front_right"));
    currentView_0.setView(visView_2);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\Qcrit\\bottom_front_right.jpg"), 1, 1920, 1080, true, false);
  
  VisView visView_3 = 
      ((VisView) simulation_0.getViewManager().getObject("bottom_rear_left"));
    currentView_0.setView(visView_3);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\Qcrit\\bottom_rear_left.jpg"), 1, 1920, 1080, true, false);
  
  VisView visView_4 = 
      ((VisView) simulation_0.getViewManager().getObject("bottom_rear_right"));
    currentView_0.setView(visView_4);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\Qcrit\\bottom_rear_right.jpg"), 1, 1920, 1080, true, false);
  
  VisView visView_5 = 
      ((VisView) simulation_0.getViewManager().getObject("front"));
    currentView_0.setView(visView_5);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\Qcrit\\front.jpg"), 1, 1920, 1080, true, false);
  
  VisView visView_6 = 
      ((VisView) simulation_0.getViewManager().getObject("left"));
    currentView_0.setView(visView_6);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\Qcrit\\left.jpg"), 1, 1920, 1080, true, false);
  
  VisView visView_7 = 
      ((VisView) simulation_0.getViewManager().getObject("rear"));
    currentView_0.setView(visView_7);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\Qcrit\\rear.jpg"), 1, 1920, 1080, true, false);
  
  VisView visView_8 = 
      ((VisView) simulation_0.getViewManager().getObject("right"));
    currentView_0.setView(visView_8);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\Qcrit\\right.jpg"), 1, 1920, 1080, true, false);
  
  VisView visView_9 = 
      ((VisView) simulation_0.getViewManager().getObject("top"));
    currentView_0.setView(visView_9);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\Qcrit\\top.jpg"), 1, 1920, 1080, true, false);
  
  VisView visView_10 = 
      ((VisView) simulation_0.getViewManager().getObject("top_front_left"));
    currentView_0.setView(visView_10);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\Qcrit\\top_front_left.jpg"), 1, 1920, 1080, true, false);
  
  VisView visView_11 = 
      ((VisView) simulation_0.getViewManager().getObject("top_front_right"));
    currentView_0.setView(visView_11);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\Qcrit\\top_front_right.jpg"), 1, 1920, 1080, true, false);
  
  VisView visView_12 = 
      ((VisView) simulation_0.getViewManager().getObject("top_rear_left"));
    currentView_0.setView(visView_12);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\Qcrit\\top_rear_left.jpg"), 1, 1920, 1080, true, false);
  
  VisView visView_13 = 
      ((VisView) simulation_0.getViewManager().getObject("top_rear_right"));
    currentView_0.setView(visView_13);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\Qcrit\\top_rear_right.jpg"), 1, 1920, 1080, true, false);

  }

  private void Cp2DPlot() {

    Simulation simulation_0 = 
    getActiveSimulation();
    
  double Roll = simulation_0.getReportManager().getReport("Roll [deg]").getReportMonitorValue();
  double Yaw = simulation_0.getReportManager().getReport("Yaw [rad]").getReportMonitorValue();
  double CornerRadius = simulation_0.getReportManager().getReport("Corner Radius [m]").getReportMonitorValue();
  
  try {
    UserFieldFunction userFieldFunction_0 = 
      ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("Cp"));
  }
  catch (Exception e){
    UserFieldFunction userFieldFunction_0 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();
    userFieldFunction_0.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);
    userFieldFunction_0.setPresentationName("Cp");
    userFieldFunction_0.setFunctionName("Cp");
  
    if ( CornerRadius == 0) {
    userFieldFunction_0.setDefinition("(${PressureMonitor}-${Pressure Avg. Inlet})/(0.5*1.225*pow(${car_velocity[ms-1]},2))"); 
    } else {
    userFieldFunction_0.setDefinition("(${PressureMonitor}-${Pressure Avg. Inlet})/(0.5*1.225*pow(${Angular_Velocity}*($$Position(@CoordinateSystem(\"Laboratory.Air Orientation - Car.Air Center\"))[0]),2))");} 
  }

  UserFieldFunction userFieldFunction_0 = 
      ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("Cp"));

  Units units_0 = 
      simulation_0.getUnitsManager().getPreferredUnits(new IntVector(new int[] {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));
  Units units_1 = 
      ((Units) simulation_0.getUnitsManager().getObject("mm"));
  LabCoordinateSystem labCoordinateSystem_0 = 
      simulation_0.getCoordinateSystemManager().getLabCoordinateSystem();
  Region region_0 = 
      simulation_0.getRegionManager().getRegion("Fluid");

  // FW //

    PlaneSection planeSection_0 = 
      (PlaneSection) simulation_0.getPartManager().createImplicitPart(new NeoObjectVector(new Object[] {}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), new DoubleVector(new double[] {0.0, 0.0, 0.0}), 0, 1, new DoubleVector(new double[] {0.0}));
    planeSection_0.setCoordinateSystem(labCoordinateSystem_0);
    planeSection_0.getInputParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new RelationshipPredicate(RelationshipOperator.DescendantOf, new IdentityPredicate(IdentityOperator.Equals, Arrays.<ClientServerObject>asList(region_0))), new NamePredicate(NameOperator.Contains, "FW"))), Query.STANDARD_MODIFIERS));
    
    if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
      planeSection_0.getOriginCoordinate().setCoordinate(units_0, units_1, units_0, new DoubleVector(new double[] {0.0, -1.0, 0.0}));
    }
    else{
      planeSection_0.getOriginCoordinate().setCoordinate(units_0, units_1, units_0, new DoubleVector(new double[] {0.0, -730.0, 0.0}));
    }
    
    planeSection_0.getOriginCoordinate().setCoordinateSystem(labCoordinateSystem_0);
    planeSection_0.getOrientationCoordinate().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {0.0, 1.0, 0.0}));
    planeSection_0.setValueMode(ValueMode.SINGLE);
    planeSection_0.setPresentationName("CP_FW");

    XYPlot xYPlot_0 = 
      simulation_0.getPlotManager().createPlot(XYPlot.class);
    xYPlot_0.setPresentationName("CP_FW");
    xYPlot_0.getParts().setObjects(planeSection_0);
    YAxisType yAxisType_1 = 
      ((YAxisType) xYPlot_0.getYAxes().getAxisType("Y Type 1"));
   MultiColLegend multiColLegend_0 = 
      xYPlot_0.getLegend();
    multiColLegend_0.setVisible(false);
    Cartesian2DAxisManager cartesian2DAxisManager_0 = 
      ((Cartesian2DAxisManager) xYPlot_0.getAxisManager());
    Cartesian2DAxis cartesian2DAxis_0 = 
      ((Cartesian2DAxis) cartesian2DAxisManager_0.getAxis("Bottom Axis"));
    cartesian2DAxis_0.setReverse(true);
    FieldFunctionUnits fieldFunctionUnits_1 = 
      yAxisType_1.getScalarFunction();
    fieldFunctionUnits_1.setFieldFunction(userFieldFunction_0);

    PlotUpdate plotUpdate_0 = 
      xYPlot_0.getPlotUpdate();

  new File(resolvePath("..\\Post_Processing\\CP\\FW\\Scenes")).mkdirs();

  try {
    ((ClientServerObjectGroup) simulation_0.getPlotManager().getGroupsManager().getObject("2D CP Plots")).setPresentationName("2D CP Plots");
  }
  catch (Exception e){
  simulation_0.getPlotManager().getGroupsManager().createGroup("2D CP Plots");
  simulation_0.getPartManager().getGroupsManager().createGroup("CP Planes");
}

if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {

    xYPlot_0.setTitle("CP_FW - y = 1 mm");

    xYPlot_0.encode(resolvePath("..\\Post_Processing\\CP\\FW\\Images\\CP_FW - 0 - y = 1 mm.jpg"), "png", 1920, 1080);
    xYPlot_0.exportScene(resolvePath("..\\Post_Processing\\CP\\FW\\Scenes\\CP_FW - 0 - y = 1 mm.sce"), "CP_FW", "", false, false);
    xYPlot_0.export(resolvePath("..\\Post_Processing\\CP\\FW\\Files\\CP_FW - 0 - y = 1 mm.csv"), ",");

    for (int i = 1; i < 81; i++) { 

    planeSection_0.getOriginCoordinate().setCoordinate(units_0, units_1, units_0, new DoubleVector(new double[] {0.0, (-1-i*9), 0.0}));

    double coordinate = 1+i*9; 

  xYPlot_0.setTitle("CP_FW - y = "+ coordinate +" mm");
  xYPlot_0.encode(resolvePath("..\\Post_Processing\\CP\\FW\\Images\\CP_FW - "+ i +" - y = "+ coordinate +" mm.jpg"), "png", 1920, 1080);
  xYPlot_0.exportScene(resolvePath("..\\Post_Processing\\CP\\FW\\Scenes\\CP_FW - "+ i +" - y = "+ coordinate +" mm.sce"), "CP_FW", "", false, false);
  xYPlot_0.export(resolvePath("..\\Post_Processing\\CP\\FW\\Files\\CP_FW - "+ i +" - y = "+ coordinate +" mm.csv"), ",");
  }
  } else {

    xYPlot_0.setTitle("CP_FW - y = -730 mm");

    xYPlot_0.encode(resolvePath("..\\Post_Processing\\CP\\FW\\Images\\CP_FW - 0 - y = -730 mm.jpg"), "png", 1920, 1080);
    xYPlot_0.exportScene(resolvePath("..\\Post_Processing\\CP\\FW\\Scenes\\CP_FW - 0 - y = -730 mm.sce"), "CP_FW", "", false, false);
    xYPlot_0.export(resolvePath("..\\Post_Processing\\CP\\FW\\Files\\CP_FW - 0 - y = -730 mm.csv"), ",");

    for (int i = 1; i < 163; i++) { 

    planeSection_0.getOriginCoordinate().setCoordinate(units_0, units_1, units_0, new DoubleVector(new double[] {0.0, (-730+i*9), 0.0}));

    double coordinate = -730+i*9; 

    xYPlot_0.setTitle("CP_FW - y = "+ coordinate +" mm");
    xYPlot_0.encode(resolvePath("..\\Post_Processing\\CP\\FW\\Monitors\\Images\\CP_FW - "+ i +" - y = "+ coordinate +" mm.jpg"), "png", 1920, 1080);
    xYPlot_0.exportScene(resolvePath("..\\Post_Processing\\CP\\FW\\Monitors\\Scenes\\CP_FW - "+ i +" - y = "+ coordinate +" mm.sce"), "CP_FW", "", false, false);
    xYPlot_0.export(resolvePath("..\\Post_Processing\\CP\\FW\\Files\\CP_FW - "+ i +" - y = "+ coordinate +" mm.csv"), ",");
    }

  
  }

  ((ClientServerObjectGroup) simulation_0.getPlotManager().getGroupsManager().getObject("2D CP Plots")).getGroupsManager().groupObjects("2D CP Plots", new NeoObjectVector(new Object[] {xYPlot_0}), true);
  ((ClientServerObjectGroup) simulation_0.getPartManager().getGroupsManager().getObject("CP Planes")).getGroupsManager().groupObjects("CP Planes", new NeoObjectVector(new Object[] {planeSection_0}), true);
   
  // Diff //

  PlaneSection planeSection_1 = 
      (PlaneSection) simulation_0.getPartManager().createImplicitPart(new NeoObjectVector(new Object[] {}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), new DoubleVector(new double[] {0.0, 0.0, 0.0}), 0, 1, new DoubleVector(new double[] {0.0}));
    planeSection_1.setCoordinateSystem(labCoordinateSystem_0);
    planeSection_1.getInputParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new RelationshipPredicate(RelationshipOperator.DescendantOf, new IdentityPredicate(IdentityOperator.Equals, Arrays.<ClientServerObject>asList(region_0))), new NamePredicate(NameOperator.Contains, "DIFF"))), Query.STANDARD_MODIFIERS));
    
    if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
      planeSection_1.getOriginCoordinate().setCoordinate(units_0, units_1, units_0, new DoubleVector(new double[] {0.0, -1.0, 0.0}));
    }
    else{
      planeSection_1.getOriginCoordinate().setCoordinate(units_0, units_1, units_0, new DoubleVector(new double[] {0.0, -694.0, 0.0}));
    }
    
    planeSection_1.getOriginCoordinate().setCoordinateSystem(labCoordinateSystem_0);
    planeSection_1.getOrientationCoordinate().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {0.0, 1.0, 0.0}));
    planeSection_1.setValueMode(ValueMode.SINGLE);
    planeSection_1.setPresentationName("CP_Diffuser");

    XYPlot xYPlot_1 = 
      simulation_0.getPlotManager().createPlot(XYPlot.class);
    xYPlot_1.setPresentationName("CP_Diffuser");
    xYPlot_1.getParts().setObjects(planeSection_1);
    YAxisType yAxisType_2 = 
      ((YAxisType) xYPlot_1.getYAxes().getAxisType("Y Type 1"));
   MultiColLegend multiColLegend_1 = 
      xYPlot_1.getLegend();
    multiColLegend_1.setVisible(false);
    Cartesian2DAxisManager cartesian2DAxisManager_1 = 
      ((Cartesian2DAxisManager) xYPlot_1.getAxisManager());
    Cartesian2DAxis cartesian2DAxis_1 = 
      ((Cartesian2DAxis) cartesian2DAxisManager_1.getAxis("Bottom Axis"));
    cartesian2DAxis_1.setReverse(true);
    FieldFunctionUnits fieldFunctionUnits_2 = 
      yAxisType_2.getScalarFunction();
    fieldFunctionUnits_2.setFieldFunction(userFieldFunction_0);

    PlotUpdate plotUpdate_1 = 
      xYPlot_1.getPlotUpdate();

  new File(resolvePath("..\\Post_Processing\\CP\\Diffuser\\Scenes")).mkdirs();

if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {

    xYPlot_1.setTitle("CP_Diffuser - y = 1 mm");

    xYPlot_1.encode(resolvePath("..\\Post_Processing\\CP\\Diffuser\\Images\\CP_Diffuser - 0 - y = 1 mm.jpg"), "png", 1920, 1080);
    xYPlot_1.exportScene(resolvePath("..\\Post_Processing\\CP\\Diffuser\\Scenes\\CP_Diffuser - 0 - y = 1 mm.sce"), "CP_Diffuser", "", false, false);
    xYPlot_1.export(resolvePath("..\\Post_Processing\\CP\\Diffuser\\Files\\CP_Diffuser - 0 - y = 1 mm.csv"), ",");

    for (int i = 1; i < 50; i++) { 

    planeSection_1.getOriginCoordinate().setCoordinate(units_0, units_1, units_0, new DoubleVector(new double[] {0.0, (-1-i*9), 0.0}));

  double coordinate = 1+i*9;

  xYPlot_1.setTitle("CP_Diffuser - y = "+ coordinate +" mm");
  xYPlot_1.encode(resolvePath("..\\Post_Processing\\CP\\Diffuser\\Images\\CP_Diffuser - "+ i +" - y = "+ coordinate +" mm.jpg"), "png", 1920, 1080);
  xYPlot_1.exportScene(resolvePath("..\\Post_Processing\\CP\\Diffuser\\Scenes\\CP_Diffuser - "+ i +" - y = "+ coordinate +" mm.sce"), "CP_Diffuser", "", false, false);
  xYPlot_1.export(resolvePath("..\\Post_Processing\\CP\\Diffuser\\Files\\CP_Diffuser - "+ i +" - y = "+ coordinate +" mm.csv"), ",");
  }
  } else {

    xYPlot_1.setTitle("CP_Diffuser - y = -451 mm");

    xYPlot_1.encode(resolvePath("..\\Post_Processing\\CP\\Diffuser\\Images\\CP_Diffuser - 0 - y = -694 mm.jpg"), "png", 1920, 1080);
    xYPlot_1.exportScene(resolvePath("..\\Post_Processing\\CP\\Diffuser\\Scenes\\CP_Diffuser - 0 - y = -694 mm.sce"), "CP_Diffuser", "", false, false);
    xYPlot_1.export(resolvePath("..\\Post_Processing\\CP\\Diffuser\\CP_Diffuser - 0 - y = -694 mm.csv"), ",");

    for (int i = 1; i < 101; i++) { 

    planeSection_1.getOriginCoordinate().setCoordinate(units_0, units_1, units_0, new DoubleVector(new double[] {0.0, (-451+i*9), 0.0}));

  double coordinate = -451+i*9; 

    xYPlot_1.setTitle("CP_Diffuser - y = "+ coordinate +" mm");
    xYPlot_1.encode(resolvePath("..\\Post_Processing\\CP\\Diffuser\\Images\\CP_Diffuser - "+ i +" - y = "+ coordinate +" mm.jpg"), "png", 1920, 1080);
    xYPlot_1.exportScene(resolvePath("..\\Post_Processing\\CP\\Diffuser\\Scenes\\CP_Diffuser - "+ i +" - y = "+ coordinate +" mm.sce"), "CP_Diffuser", "", false, false);
    xYPlot_1.export(resolvePath("..\\Post_Processing\\CP\\Diffuser\\Files\\CP_Diffuser - "+ i +" - y = "+ coordinate +" mm.csv"), ",");
    }

  
  }

  ((ClientServerObjectGroup) simulation_0.getPlotManager().getGroupsManager().getObject("2D CP Plots")).getGroupsManager().groupObjects("2D CP Plots", new NeoObjectVector(new Object[] {xYPlot_1}), true);
  ((ClientServerObjectGroup) simulation_0.getPartManager().getGroupsManager().getObject("CP Planes")).getGroupsManager().groupObjects("CP Planes", new NeoObjectVector(new Object[] {planeSection_1}), true);
   

  // RW //

  PlaneSection planeSection_2 = 
      (PlaneSection) simulation_0.getPartManager().createImplicitPart(new NeoObjectVector(new Object[] {}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), new DoubleVector(new double[] {0.0, 0.0, 0.0}), 0, 1, new DoubleVector(new double[] {0.0}));
    planeSection_2.setCoordinateSystem(labCoordinateSystem_0);
    planeSection_2.getInputParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new RelationshipPredicate(RelationshipOperator.DescendantOf, new IdentityPredicate(IdentityOperator.Equals, Arrays.<ClientServerObject>asList(region_0))), new NamePredicate(NameOperator.Contains, "RW"))), Query.STANDARD_MODIFIERS));
    
    if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
      planeSection_2.getOriginCoordinate().setCoordinate(units_0, units_1, units_0, new DoubleVector(new double[] {0.0, -1.0, 0.0}));
    }
    else{
      planeSection_2.getOriginCoordinate().setCoordinate(units_0, units_1, units_0, new DoubleVector(new double[] {0.0, -451.0, 0.0}));
    }
    
    planeSection_2.getOriginCoordinate().setCoordinateSystem(labCoordinateSystem_0);
    planeSection_2.getOrientationCoordinate().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {0.0, 1.0, 0.0}));
    planeSection_2.setValueMode(ValueMode.SINGLE);
    planeSection_2.setPresentationName("CP_RW");

    XYPlot xYPlot_2 = 
      simulation_0.getPlotManager().createPlot(XYPlot.class);
    xYPlot_2.setPresentationName("CP_RW");
    xYPlot_2.getParts().setObjects(planeSection_2);
    YAxisType yAxisType_3 = 
      ((YAxisType) xYPlot_2.getYAxes().getAxisType("Y Type 1"));
   MultiColLegend multiColLegend_2 = 
      xYPlot_2.getLegend();
    multiColLegend_2.setVisible(false);
    Cartesian2DAxisManager cartesian2DAxisManager_2 = 
      ((Cartesian2DAxisManager) xYPlot_2.getAxisManager());
    Cartesian2DAxis cartesian2DAxis_2 = 
      ((Cartesian2DAxis) cartesian2DAxisManager_2.getAxis("Bottom Axis"));
    cartesian2DAxis_2.setReverse(true);
    FieldFunctionUnits fieldFunctionUnits_3 = 
      yAxisType_3.getScalarFunction();
    fieldFunctionUnits_3.setFieldFunction(userFieldFunction_0);

    PlotUpdate plotUpdate_2 = 
      xYPlot_2.getPlotUpdate();

  new File(resolvePath("..\\Post_Processing\\CP\\RW\\Scenes")).mkdirs();

if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {

    xYPlot_2.setTitle("CP_RW - y = 1 mm");

    xYPlot_2.encode(resolvePath("..\\Post_Processing\\CP\\RW\\Images\\CP_RW - 0 - y = 1 mm.jpg"), "png", 1920, 1080);
    xYPlot_2.exportScene(resolvePath("..\\Post_Processing\\CP\\RW\\Scenes\\CP_RW - 0 - y = 1 mm.sce"), "CP_RW", "", false, false);
    xYPlot_2.export(resolvePath("..\\Post_Processing\\CP\\RW\\Files\\CP_RW - 0 - y = 1 mm.csv"), ",");

    for (int i = 1; i < 77; i++) { 

    planeSection_2.getOriginCoordinate().setCoordinate(units_0, units_1, units_0, new DoubleVector(new double[] {0.0, (-1-i*9), 0.0}));

  double coordinate = 1+i*9;  

  xYPlot_2.setTitle("CP_RW - y = "+ coordinate +" mm");
  xYPlot_2.encode(resolvePath("..\\Post_Processing\\CP\\RW\\Images\\CP_RW - "+ i +" - y = "+ coordinate +" mm.jpg"), "png", 1920, 1080);
  xYPlot_2.exportScene(resolvePath("..\\Post_Processing\\CP\\RW\\Scenes\\CP_RW - "+ i +" - y = "+ coordinate +" mm.sce"), "CP_RW", "", false, false);
  xYPlot_2.export(resolvePath("..\\Post_Processing\\CP\\RW\\Files\\CP_RW - "+ i +" - y = "+ coordinate +" mm.csv"), ",");
  }
  } else {

    xYPlot_2.setTitle("CP_RW - y = -451 mm");

    xYPlot_2.encode(resolvePath("..\\Post_Processing\\CP\\RW\\Images\\CP_RW - 0 - y = -451 mm.jpg"), "png", 1920, 1080);
    xYPlot_2.exportScene(resolvePath("..\\Post_Processing\\CP\\RW\\Scenes\\CP_RW - 0 - y = -451 mm.sce"), "CP_RW", "", false, false);
    xYPlot_2.export(resolvePath("..\\Post_Processing\\CP\\RW\\RW\\CP_RW - 0 - y = -451 mm.csv"), ",");

    for (int i = 1; i < 155; i++) { 

    planeSection_2.getOriginCoordinate().setCoordinate(units_0, units_1, units_0, new DoubleVector(new double[] {0.0, (-694+i*9), 0.0}));

  double coordinate = -694+i*9;

    xYPlot_2.setTitle("CP_RW - y = "+ coordinate +" mm");
    xYPlot_2.encode(resolvePath("..\\Post_Processing\\CP\\RW\\Images\\CP_RW - "+ i +" - y = "+ coordinate +" mm.jpg"), "png", 1920, 1080);
    xYPlot_2.exportScene(resolvePath("..\\Post_Processing\\CP\\RW\\Scenes\\CP_RW - "+ i +" - y = "+ coordinate +" mm.sce"), "CP_RW", "", false, false);
    xYPlot_2.export(resolvePath("..\\Post_Processing\\CP\\RW\\Files\\CP_RW - "+ i +" - y = "+ coordinate +" mm.csv"), ",");
    }

  
  }

  ((ClientServerObjectGroup) simulation_0.getPlotManager().getGroupsManager().getObject("2D CP Plots")).getGroupsManager().groupObjects("2D CP Plots", new NeoObjectVector(new Object[] {xYPlot_2}), true);
  ((ClientServerObjectGroup) simulation_0.getPartManager().getGroupsManager().getObject("CP Planes")).getGroupsManager().groupObjects("CP Planes", new NeoObjectVector(new Object[] {planeSection_2}), true);
   
   // SC //

  PlaneSection planeSection_3 = 
      (PlaneSection) simulation_0.getPartManager().createImplicitPart(new NeoObjectVector(new Object[] {}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), new DoubleVector(new double[] {0.0, 0.0, 0.0}), 0, 1, new DoubleVector(new double[] {0.0}));
    planeSection_3.setCoordinateSystem(labCoordinateSystem_0);
    planeSection_3.getInputParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new RelationshipPredicate(RelationshipOperator.DescendantOf, new IdentityPredicate(IdentityOperator.Equals, Arrays.<ClientServerObject>asList(region_0))), new NamePredicate(NameOperator.Contains, "SC"))), Query.STANDARD_MODIFIERS));
    
    if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
      planeSection_3.getOriginCoordinate().setCoordinate(units_0, units_1, units_0, new DoubleVector(new double[] {0.0, -1.0, 0.0}));
    }
    else{
      planeSection_3.getOriginCoordinate().setCoordinate(units_0, units_1, units_0, new DoubleVector(new double[] {0.0, -451.0, 0.0}));
    }
    
    planeSection_3.getOriginCoordinate().setCoordinateSystem(labCoordinateSystem_0);
    planeSection_3.getOrientationCoordinate().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {0.0, 1.0, 0.0}));
    planeSection_3.setValueMode(ValueMode.SINGLE);
    planeSection_3.setPresentationName("CP_SC");

    XYPlot xYPlot_3 = 
      simulation_0.getPlotManager().createPlot(XYPlot.class);
    xYPlot_3.setPresentationName("CP_SC");
    xYPlot_3.getParts().setObjects(planeSection_2);
    YAxisType yAxisType_4 = 
      ((YAxisType) xYPlot_3.getYAxes().getAxisType("Y Type 1"));
   MultiColLegend multiColLegend_3 = 
      xYPlot_3.getLegend();
    multiColLegend_3.setVisible(false);
    Cartesian2DAxisManager cartesian2DAxisManager_3 = 
      ((Cartesian2DAxisManager) xYPlot_3.getAxisManager());
    Cartesian2DAxis cartesian2DAxis_3 = 
      ((Cartesian2DAxis) cartesian2DAxisManager_3.getAxis("Bottom Axis"));
    cartesian2DAxis_3.setReverse(true);
    FieldFunctionUnits fieldFunctionUnits_4 = 
      yAxisType_4.getScalarFunction();
    fieldFunctionUnits_4.setFieldFunction(userFieldFunction_0);

    PlotUpdate plotUpdate_3 = 
      xYPlot_3.getPlotUpdate();

  new File(resolvePath("..\\Post_Processing\\CP\\SC\\Scenes")).mkdirs();

if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {

    xYPlot_3.setTitle("CP_SC - y = 1 mm");

    xYPlot_3.encode(resolvePath("..\\Post_Processing\\CP\\SC\\Images\\CP_SC - 0 - y = 1 mm.jpg"), "png", 1920, 1080);
    xYPlot_3.exportScene(resolvePath("..\\Post_Processing\\CP\\SC\\Scenes\\CP_SC - 0 - y = 1 mm.sce"), "CP_SC", "", false, false);
    xYPlot_3.export(resolvePath("..\\Post_Processing\\CP\\SC\\Files\\CP_SC - 0 - y = 1 mm.csv"), ",");

    for (int i = 1; i < 44; i++) { 

    planeSection_3.getOriginCoordinate().setCoordinate(units_0, units_1, units_0, new DoubleVector(new double[] {0.0, (-250-i*10), 0.0}));

  double coordinate = 250+i*10;

  xYPlot_3.setTitle("CP_SC - y = "+ coordinate +" mm");
  xYPlot_3.encode(resolvePath("..\\Post_Processing\\CP\\SC\\Images\\CP_SC - "+ i +" - y = "+ coordinate +" mm.jpg"), "png", 1920, 1080);
  xYPlot_3.exportScene(resolvePath("..\\Post_Processing\\CP\\SC\\Scenes\\CP_SC - "+ i +" - y = "+ coordinate +" mm.sce"), "CP_SC", "", false, false);
  xYPlot_3.export(resolvePath("..\\Post_Processing\\CP\\SC\\Files\\CP_SC - "+ i +" - y = "+ coordinate +" mm.csv"), ",");
  }
  } else {

    xYPlot_2.setTitle("CP_SC - y = -695 mm");

    xYPlot_3.encode(resolvePath("..\\Post_Processing\\CP\\SC\\Images\\CP_SC - 0 - y = -695 mm.jpg"), "png", 1920, 1080);
    xYPlot_3.exportScene(resolvePath("..\\Post_Processing\\CP\\SC\\Scenes\\CP_SC - 0 - y = -695 mm.sce"), "CP_SC", "", false, false);
    xYPlot_3.export(resolvePath("..\\Post_Processing\\CP\\SC\\SC\\CP_SC - 0 - y = -695 mm.csv"), ",");

    for (int i = 1; i < 44; i++) { 

    planeSection_3.getOriginCoordinate().setCoordinate(units_0, units_1, units_0, new DoubleVector(new double[] {0.0, (-255-i*10), 0.0}));

  double coordinate = -250-i*10; 

    xYPlot_3.setTitle("CP_SC - y = "+ coordinate +" mm");
    xYPlot_3.encode(resolvePath("..\\Post_Processing\\CP\\SC\\Images\\CP_SC - "+ i +" - y = "+ coordinate +" mm.jpg"), "png", 1920, 1080);
    xYPlot_3.exportScene(resolvePath("..\\Post_Processing\\CP\\SC\\Scenes\\CP_SC - "+ i +" - y = "+ coordinate +" mm.sce"), "CP_SC", "", false, false);
    xYPlot_3.export(resolvePath("..\\Post_Processing\\CP\\SC\\Files\\CP_SC - "+ i +" - y = "+ coordinate +" mm.csv"), ",");
    }

    for (int i = 1; i < 44; i++) { 

    planeSection_3.getOriginCoordinate().setCoordinate(units_0, units_1, units_0, new DoubleVector(new double[] {0.0, (255+i*10), 0.0}));

  double coordinate = 250+i*10; 

    xYPlot_3.setTitle("CP_SC - y = "+ coordinate +" mm");
    xYPlot_3.encode(resolvePath("..\\Post_Processing\\CP\\SC\\Images\\CP_SC - "+ i +" - y = "+ coordinate +" mm.jpg"), "png", 1920, 1080);
    xYPlot_3.exportScene(resolvePath("..\\Post_Processing\\CP\\SC\\Scenes\\CP_SC - "+ i +" - y = "+ coordinate +" mm.sce"), "CP_SC", "", false, false);
    xYPlot_3.export(resolvePath("..\\Post_Processing\\CP\\SC\\Files\\CP_SC - "+ i +" - y = "+ coordinate +" mm.csv"), ",");
    }

  
  }

  ((ClientServerObjectGroup) simulation_0.getPlotManager().getGroupsManager().getObject("2D CP Plots")).getGroupsManager().groupObjects("2D CP Plots", new NeoObjectVector(new Object[] {xYPlot_3}), true);
  ((ClientServerObjectGroup) simulation_0.getPartManager().getGroupsManager().getObject("CP Planes")).getGroupsManager().groupObjects("CP Planes", new NeoObjectVector(new Object[] {planeSection_3}), true);
   

  }

  private void Cf2DPlot() {

    Simulation simulation_0 = 
    getActiveSimulation();
    
  double Roll = simulation_0.getReportManager().getReport("Roll [deg]").getReportMonitorValue();
  double Yaw = simulation_0.getReportManager().getReport("Yaw [rad]").getReportMonitorValue();
  double CornerRadius = simulation_0.getReportManager().getReport("Corner Radius [m]").getReportMonitorValue();
  
  try {
    UserFieldFunction userFieldFunction_0 = 
      ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("Cf"));
  }
  catch (Exception e){
    UserFieldFunction userFieldFunction_0 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();
    userFieldFunction_0.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);
    userFieldFunction_0.setPresentationName("Cf");
    userFieldFunction_0.setFunctionName("Cf");
  
    if ( CornerRadius == 0) {
    userFieldFunction_0.setDefinition("(mag($${WallShearStress}))/(0.5*1.225*pow(${car_velocity[ms-1]},2))"); 
    } else {
    userFieldFunction_0.setDefinition("(mag($${WallShearStress}))/(0.5*1.225*pow(${Angular_Velocity}*($$Position(@CoordinateSystem(\"Laboratory.Air Orientation - Car.Air Center\"))[0]),2))");} 
  }

  UserFieldFunction userFieldFunction_0 = 
      ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("Cf"));

  Units units_0 = 
      simulation_0.getUnitsManager().getPreferredUnits(new IntVector(new int[] {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));
  Units units_1 = 
      ((Units) simulation_0.getUnitsManager().getObject("mm"));
  LabCoordinateSystem labCoordinateSystem_0 = 
      simulation_0.getCoordinateSystemManager().getLabCoordinateSystem();
  Region region_0 = 
      simulation_0.getRegionManager().getRegion("Fluid");

  // FW //

    PlaneSection planeSection_0 = 
      (PlaneSection) simulation_0.getPartManager().createImplicitPart(new NeoObjectVector(new Object[] {}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), new DoubleVector(new double[] {0.0, 0.0, 0.0}), 0, 1, new DoubleVector(new double[] {0.0}));
    planeSection_0.setCoordinateSystem(labCoordinateSystem_0);
    planeSection_0.getInputParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new RelationshipPredicate(RelationshipOperator.DescendantOf, new IdentityPredicate(IdentityOperator.Equals, Arrays.<ClientServerObject>asList(region_0))), new NamePredicate(NameOperator.Contains, "FW"))), Query.STANDARD_MODIFIERS));
    
    if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
      planeSection_0.getOriginCoordinate().setCoordinate(units_0, units_1, units_0, new DoubleVector(new double[] {0.0, -1.0, 0.0}));
    }
    else{
      planeSection_0.getOriginCoordinate().setCoordinate(units_0, units_1, units_0, new DoubleVector(new double[] {0.0, -730.0, 0.0}));
    }
    
    planeSection_0.getOriginCoordinate().setCoordinateSystem(labCoordinateSystem_0);
    planeSection_0.getOrientationCoordinate().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {0.0, 1.0, 0.0}));
    planeSection_0.setValueMode(ValueMode.SINGLE);
    planeSection_0.setPresentationName("CF_FW");

    XYPlot xYPlot_0 = 
      simulation_0.getPlotManager().createPlot(XYPlot.class);
    xYPlot_0.setPresentationName("CF_FW");
    xYPlot_0.getParts().setObjects(planeSection_0);
    YAxisType yAxisType_1 = 
      ((YAxisType) xYPlot_0.getYAxes().getAxisType("Y Type 1"));
   MultiColLegend multiColLegend_0 = 
      xYPlot_0.getLegend();
    multiColLegend_0.setVisible(false);
    Cartesian2DAxisManager cartesian2DAxisManager_0 = 
      ((Cartesian2DAxisManager) xYPlot_0.getAxisManager());
    Cartesian2DAxis cartesian2DAxis_0 = 
      ((Cartesian2DAxis) cartesian2DAxisManager_0.getAxis("Bottom Axis"));
    cartesian2DAxis_0.setReverse(true);
    FieldFunctionUnits fieldFunctionUnits_1 = 
      yAxisType_1.getScalarFunction();
    fieldFunctionUnits_1.setFieldFunction(userFieldFunction_0);

    PlotUpdate plotUpdate_0 = 
      xYPlot_0.getPlotUpdate();

  new File(resolvePath("..\\Post_Processing\\CF\\FW\\Scenes")).mkdirs();
  try {
    ((ClientServerObjectGroup) simulation_0.getPlotManager().getGroupsManager().getObject("2D CF Plots")).setPresentationName("2D CF Plots");
  }
  catch (Exception e){
  simulation_0.getPlotManager().getGroupsManager().createGroup("2D CF Plots");
  simulation_0.getPartManager().getGroupsManager().createGroup("CF Planes");
}

if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {

    xYPlot_0.setTitle("CF_FW - y = 1 mm");

    xYPlot_0.encode(resolvePath("..\\Post_Processing\\CF\\FW\\Images\\CF_FW - 0 - y = 1 mm.jpg"), "png", 1920, 1080);
    xYPlot_0.exportScene(resolvePath("..\\Post_Processing\\CF\\FW\\Scenes\\CF_FW - 0 - y = 1 mm.sce"), "CF_FW", "", false, false);
    xYPlot_0.export(resolvePath("..\\Post_Processing\\CF\\FW\\Files\\CF_FW - 0 - y = 1 mm.csv"), ",");

    for (int i = 1; i < 81; i++) { 

    planeSection_0.getOriginCoordinate().setCoordinate(units_0, units_1, units_0, new DoubleVector(new double[] {0.0, (-1-i*9), 0.0}));

    double coordinate = 1+i*9; 

  xYPlot_0.setTitle("CF_FW - y = "+ coordinate +" mm");
  xYPlot_0.encode(resolvePath("..\\Post_Processing\\CF\\FW\\Images\\CF_FW - "+ i +" - y = "+ coordinate +" mm.jpg"), "png", 1920, 1080);
  xYPlot_0.exportScene(resolvePath("..\\Post_Processing\\CF\\FW\\Scenes\\CF_FW - "+ i +" - y = "+ coordinate +" mm.sce"), "CF_FW", "", false, false);
  xYPlot_0.export(resolvePath("..\\Post_Processing\\CF\\FW\\Files\\CF_FW - "+ i +" - y = "+ coordinate +" mm.csv"), ",");
  }
  } else {

    xYPlot_0.setTitle("CF_FW - y = -730 mm");

    xYPlot_0.encode(resolvePath("..\\Post_Processing\\CF\\FW\\Images\\CF_FW - 0 - y = -730 mm.jpg"), "png", 1920, 1080);
    xYPlot_0.exportScene(resolvePath("..\\Post_Processing\\CF\\FW\\Scenes\\CF_FW - 0 - y = -730 mm.sce"), "CF_FW", "", false, false);
    xYPlot_0.export(resolvePath("..\\Post_Processing\\CF\\FW\\Files\\CF_FW - 0 - y = -730 mm.csv"), ",");

    for (int i = 1; i < 163; i++) { 

    planeSection_0.getOriginCoordinate().setCoordinate(units_0, units_1, units_0, new DoubleVector(new double[] {0.0, (-730+i*9), 0.0}));

    double coordinate = -730+i*9; 

    xYPlot_0.setTitle("CF_FW - y = "+ coordinate +" mm");
    xYPlot_0.encode(resolvePath("..\\Post_Processing\\CF\\FW\\Monitors\\Images\\CF_FW - "+ i +" - y = "+ coordinate +" mm.jpg"), "png", 1920, 1080);
    xYPlot_0.exportScene(resolvePath("..\\Post_Processing\\CF\\FW\\Monitors\\Scenes\\CF_FW - "+ i +" - y = "+ coordinate +" mm.sce"), "CF_FW", "", false, false);
    xYPlot_0.export(resolvePath("..\\Post_Processing\\CF\\FW\\Files\\CF_FW - "+ i +" - y = "+ coordinate +" mm.csv"), ",");
    }

  
  }

  ((ClientServerObjectGroup) simulation_0.getPlotManager().getGroupsManager().getObject("2D CF Plots")).getGroupsManager().groupObjects("2D CF Plots", new NeoObjectVector(new Object[] {xYPlot_0}), true);
  ((ClientServerObjectGroup) simulation_0.getPartManager().getGroupsManager().getObject("CF Planes")).getGroupsManager().groupObjects("CF Planes", new NeoObjectVector(new Object[] {planeSection_0}), true);
   
  // Diff //

  PlaneSection planeSection_1 = 
      (PlaneSection) simulation_0.getPartManager().createImplicitPart(new NeoObjectVector(new Object[] {}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), new DoubleVector(new double[] {0.0, 0.0, 0.0}), 0, 1, new DoubleVector(new double[] {0.0}));
    planeSection_1.setCoordinateSystem(labCoordinateSystem_0);
    planeSection_1.getInputParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new RelationshipPredicate(RelationshipOperator.DescendantOf, new IdentityPredicate(IdentityOperator.Equals, Arrays.<ClientServerObject>asList(region_0))), new NamePredicate(NameOperator.Contains, "DIFF"))), Query.STANDARD_MODIFIERS));
    
    if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
      planeSection_1.getOriginCoordinate().setCoordinate(units_0, units_1, units_0, new DoubleVector(new double[] {0.0, -1.0, 0.0}));
    }
    else{
      planeSection_1.getOriginCoordinate().setCoordinate(units_0, units_1, units_0, new DoubleVector(new double[] {0.0, -694.0, 0.0}));
    }
    
    planeSection_1.getOriginCoordinate().setCoordinateSystem(labCoordinateSystem_0);
    planeSection_1.getOrientationCoordinate().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {0.0, 1.0, 0.0}));
    planeSection_1.setValueMode(ValueMode.SINGLE);
    planeSection_1.setPresentationName("CF_Diffuser");

    XYPlot xYPlot_1 = 
      simulation_0.getPlotManager().createPlot(XYPlot.class);
    xYPlot_1.setPresentationName("CF_Diffuser");
    xYPlot_1.getParts().setObjects(planeSection_1);
    YAxisType yAxisType_2 = 
      ((YAxisType) xYPlot_1.getYAxes().getAxisType("Y Type 1"));
   MultiColLegend multiColLegend_1 = 
      xYPlot_1.getLegend();
    multiColLegend_1.setVisible(false);
    Cartesian2DAxisManager cartesian2DAxisManager_1 = 
      ((Cartesian2DAxisManager) xYPlot_1.getAxisManager());
    Cartesian2DAxis cartesian2DAxis_1 = 
      ((Cartesian2DAxis) cartesian2DAxisManager_1.getAxis("Bottom Axis"));
    cartesian2DAxis_1.setReverse(true);
    FieldFunctionUnits fieldFunctionUnits_2 = 
      yAxisType_2.getScalarFunction();
    fieldFunctionUnits_2.setFieldFunction(userFieldFunction_0);

    PlotUpdate plotUpdate_1 = 
      xYPlot_1.getPlotUpdate();

  new File(resolvePath("..\\Post_Processing\\CF\\RW\\Scenes")).mkdirs();

if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {

    xYPlot_1.setTitle("CF_Diffuser - y = 1 mm");

    xYPlot_1.encode(resolvePath("..\\Post_Processing\\CF\\Diffuser\\Images\\CF_Diffuser - 0 - y = 1 mm.jpg"), "png", 1920, 1080);
    xYPlot_1.exportScene(resolvePath("..\\Post_Processing\\CF\\Diffuser\\Scenes\\CF_Diffuser - 0 - y = 1 mm.sce"), "CF_Diffuser", "", false, false);
    xYPlot_1.export(resolvePath("..\\Post_Processing\\CF\\Diffuser\\Files\\CF_Diffuser - 0 - y = 1 mm.csv"), ",");

    for (int i = 1; i < 50; i++) { 

    planeSection_1.getOriginCoordinate().setCoordinate(units_0, units_1, units_0, new DoubleVector(new double[] {0.0, (-1-i*9), 0.0}));

  double coordinate = 1+i*9;  

  xYPlot_1.setTitle("CF_Diffuser - y = "+ coordinate +" mm");
  xYPlot_1.encode(resolvePath("..\\Post_Processing\\CF\\Diffuser\\Images\\CF_Diffuser - "+ i +" - y = "+ coordinate +" mm.jpg"), "png", 1920, 1080);
  xYPlot_1.exportScene(resolvePath("..\\Post_Processing\\CF\\Diffuser\\Scenes\\CF_Diffuser - "+ i +" - y = "+ coordinate +" mm.sce"), "CF_Diffuser", "", false, false);
  xYPlot_1.export(resolvePath("..\\Post_Processing\\CF\\Diffuser\\Files\\CF_Diffuser - "+ i +" - y = "+ coordinate +" mm.csv"), ",");
  }
  } else {

    xYPlot_1.setTitle("CF_Diffuser - y = -451 mm");

    xYPlot_1.encode(resolvePath("..\\Post_Processing\\CF\\Diffuser\\Images\\CF_Diffuser - 0 - y = -694 mm.jpg"), "png", 1920, 1080);
    xYPlot_1.exportScene(resolvePath("..\\Post_Processing\\CF\\Diffuser\\Scenes\\CF_Diffuser - 0 - y = -694 mm.sce"), "CF_Diffuser", "", false, false);
    xYPlot_1.export(resolvePath("..\\Post_Processing\\CF\\Diffuser\\CF_Diffuser - 0 - y = -694 mm.csv"), ",");

    for (int i = 1; i < 101; i++) { 

    planeSection_1.getOriginCoordinate().setCoordinate(units_0, units_1, units_0, new DoubleVector(new double[] {0.0, (-451+i*9), 0.0}));

  double coordinate = -451+i*9; 

    xYPlot_1.setTitle("CF_Diffuser - y = "+ coordinate +" mm");
    xYPlot_1.encode(resolvePath("..\\Post_Processing\\CF\\Diffuser\\Images\\CF_Diffuser - "+ i +" - y = "+ coordinate +" mm.jpg"), "png", 1920, 1080);
    xYPlot_1.exportScene(resolvePath("..\\Post_Processing\\CF\\Diffuser\\Scenes\\CF_Diffuser - "+ i +" - y = "+ coordinate +" mm.sce"), "CF_Diffuser", "", false, false);
    xYPlot_1.export(resolvePath("..\\Post_Processing\\CF\\Diffuser\\Files\\CF_Diffuser - "+ i +" - y = "+ coordinate +" mm.csv"), ",");
    }

  
  }

  ((ClientServerObjectGroup) simulation_0.getPlotManager().getGroupsManager().getObject("2D CF Plots")).getGroupsManager().groupObjects("2D CF Plots", new NeoObjectVector(new Object[] {xYPlot_1}), true);
  ((ClientServerObjectGroup) simulation_0.getPartManager().getGroupsManager().getObject("CF Planes")).getGroupsManager().groupObjects("CF Planes", new NeoObjectVector(new Object[] {planeSection_1}), true);
   

  // RW //

  PlaneSection planeSection_2 = 
      (PlaneSection) simulation_0.getPartManager().createImplicitPart(new NeoObjectVector(new Object[] {}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), new DoubleVector(new double[] {0.0, 0.0, 0.0}), 0, 1, new DoubleVector(new double[] {0.0}));
    planeSection_2.setCoordinateSystem(labCoordinateSystem_0);
    planeSection_2.getInputParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new RelationshipPredicate(RelationshipOperator.DescendantOf, new IdentityPredicate(IdentityOperator.Equals, Arrays.<ClientServerObject>asList(region_0))), new NamePredicate(NameOperator.Contains, "RW"))), Query.STANDARD_MODIFIERS));
    
    if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
      planeSection_2.getOriginCoordinate().setCoordinate(units_0, units_1, units_0, new DoubleVector(new double[] {0.0, -1.0, 0.0}));
    }
    else{
      planeSection_2.getOriginCoordinate().setCoordinate(units_0, units_1, units_0, new DoubleVector(new double[] {0.0, -451.0, 0.0}));
    }
    
    planeSection_2.getOriginCoordinate().setCoordinateSystem(labCoordinateSystem_0);
    planeSection_2.getOrientationCoordinate().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {0.0, 1.0, 0.0}));
    planeSection_2.setValueMode(ValueMode.SINGLE);
    planeSection_2.setPresentationName("CF_RW");

    XYPlot xYPlot_2 = 
      simulation_0.getPlotManager().createPlot(XYPlot.class);
    xYPlot_2.setPresentationName("CF_RW");
    xYPlot_2.getParts().setObjects(planeSection_2);
    YAxisType yAxisType_3 = 
      ((YAxisType) xYPlot_2.getYAxes().getAxisType("Y Type 1"));
   MultiColLegend multiColLegend_2 = 
      xYPlot_2.getLegend();
    multiColLegend_2.setVisible(false);
    Cartesian2DAxisManager cartesian2DAxisManager_2 = 
      ((Cartesian2DAxisManager) xYPlot_2.getAxisManager());
    Cartesian2DAxis cartesian2DAxis_2 = 
      ((Cartesian2DAxis) cartesian2DAxisManager_2.getAxis("Bottom Axis"));
    cartesian2DAxis_2.setReverse(true);
    FieldFunctionUnits fieldFunctionUnits_3 = 
      yAxisType_3.getScalarFunction();
    fieldFunctionUnits_3.setFieldFunction(userFieldFunction_0);

    PlotUpdate plotUpdate_2 = 
      xYPlot_2.getPlotUpdate();

  new File(resolvePath("..\\Post_Processing\\CF\\RW\\Scenes")).mkdirs();

if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {

    xYPlot_2.setTitle("CF_RW - y = 1 mm");

    xYPlot_2.encode(resolvePath("..\\Post_Processing\\CF\\RW\\Images\\CF_RW - 0 - y = 1 mm.jpg"), "png", 1920, 1080);
    xYPlot_2.exportScene(resolvePath("..\\Post_Processing\\CF\\RW\\Scenes\\CF_RW - 0 - y = 1 mm.sce"), "CF_RW", "", false, false);
    xYPlot_2.export(resolvePath("..\\Post_Processing\\CF\\RW\\Files\\CF_RW - 0 - y = 1 mm.csv"), ",");

    for (int i = 1; i < 77; i++) { 

    planeSection_2.getOriginCoordinate().setCoordinate(units_0, units_1, units_0, new DoubleVector(new double[] {0.0, (-1-i*9), 0.0}));

  double coordinate = 1+i*9;  

  xYPlot_2.setTitle("CF_RW - y = "+ coordinate +" mm");
  xYPlot_2.encode(resolvePath("..\\Post_Processing\\CF\\RW\\Images\\CF_RW - "+ i +" - y = "+ coordinate +" mm.jpg"), "png", 1920, 1080);
  xYPlot_2.exportScene(resolvePath("..\\Post_Processing\\CF\\RW\\Scenes\\CF_RW - "+ i +" - y = "+ coordinate +" mm.sce"), "CF_RW", "", false, false);
  xYPlot_2.export(resolvePath("..\\Post_Processing\\CF\\RW\\Files\\CF_RW - "+ i +" - y = "+ coordinate +" mm.csv"), ",");
  }
  } else {

    xYPlot_2.setTitle("CF_RW - y = -451 mm");

    xYPlot_2.encode(resolvePath("..\\Post_Processing\\CF\\RW\\Images\\CF_RW - 0 - y = -451 mm.jpg"), "png", 1920, 1080);
    xYPlot_2.exportScene(resolvePath("..\\Post_Processing\\CF\\RW\\Scenes\\CF_RW - 0 - y = -451 mm.sce"), "CF_RW", "", false, false);
    xYPlot_2.export(resolvePath("..\\Post_Processing\\CF\\RW\\CF_RW - 0 - y = -451 mm.csv"), ",");

    for (int i = 1; i < 155; i++) { 

    planeSection_2.getOriginCoordinate().setCoordinate(units_0, units_1, units_0, new DoubleVector(new double[] {0.0, (-694+i*9), 0.0}));

  double coordinate = -694+i*9;

    xYPlot_2.setTitle("CF_RW - y = "+ coordinate +" mm");
    xYPlot_2.encode(resolvePath("..\\Post_Processing\\CF\\RW\\Images\\CF_RW - "+ i +" - y = "+ coordinate +" mm.jpg"), "png", 1920, 1080);
    xYPlot_2.exportScene(resolvePath("..\\Post_Processing\\CF\\RW\\Scenes\\CF_RW - "+ i +" - y = "+ coordinate +" mm.sce"), "CF_RW", "", false, false);
    xYPlot_2.export(resolvePath("..\\Post_Processing\\CF\\RW\\Files\\CF_RW - "+ i +" - y = "+ coordinate +" mm.csv"), ",");
    }

  
  }

  ((ClientServerObjectGroup) simulation_0.getPlotManager().getGroupsManager().getObject("2D CF Plots")).getGroupsManager().groupObjects("2D CF Plots", new NeoObjectVector(new Object[] {xYPlot_2}), true);
  ((ClientServerObjectGroup) simulation_0.getPartManager().getGroupsManager().getObject("CF Planes")).getGroupsManager().groupObjects("CF Planes", new NeoObjectVector(new Object[] {planeSection_2}), true);
   
   // SC //

  PlaneSection planeSection_3 = 
      (PlaneSection) simulation_0.getPartManager().createImplicitPart(new NeoObjectVector(new Object[] {}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), new DoubleVector(new double[] {0.0, 0.0, 0.0}), 0, 1, new DoubleVector(new double[] {0.0}));
    planeSection_3.setCoordinateSystem(labCoordinateSystem_0);
    planeSection_3.getInputParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new RelationshipPredicate(RelationshipOperator.DescendantOf, new IdentityPredicate(IdentityOperator.Equals, Arrays.<ClientServerObject>asList(region_0))), new NamePredicate(NameOperator.Contains, "SC"))), Query.STANDARD_MODIFIERS));
    
    if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
      planeSection_3.getOriginCoordinate().setCoordinate(units_0, units_1, units_0, new DoubleVector(new double[] {0.0, -1.0, 0.0}));
    }
    else{
      planeSection_3.getOriginCoordinate().setCoordinate(units_0, units_1, units_0, new DoubleVector(new double[] {0.0, -451.0, 0.0}));
    }
    
    planeSection_3.getOriginCoordinate().setCoordinateSystem(labCoordinateSystem_0);
    planeSection_3.getOrientationCoordinate().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {0.0, 1.0, 0.0}));
    planeSection_3.setValueMode(ValueMode.SINGLE);
    planeSection_3.setPresentationName("CF_SC");

    XYPlot xYPlot_3 = 
      simulation_0.getPlotManager().createPlot(XYPlot.class);
    xYPlot_3.setPresentationName("CF_SC");
    xYPlot_3.getParts().setObjects(planeSection_2);
    YAxisType yAxisType_4 = 
      ((YAxisType) xYPlot_3.getYAxes().getAxisType("Y Type 1"));
   MultiColLegend multiColLegend_3 = 
      xYPlot_3.getLegend();
    multiColLegend_3.setVisible(false);
    Cartesian2DAxisManager cartesian2DAxisManager_3 = 
      ((Cartesian2DAxisManager) xYPlot_3.getAxisManager());
    Cartesian2DAxis cartesian2DAxis_3 = 
      ((Cartesian2DAxis) cartesian2DAxisManager_3.getAxis("Bottom Axis"));
    cartesian2DAxis_3.setReverse(true);
    FieldFunctionUnits fieldFunctionUnits_4 = 
      yAxisType_4.getScalarFunction();
    fieldFunctionUnits_4.setFieldFunction(userFieldFunction_0);

    PlotUpdate plotUpdate_3 = 
      xYPlot_3.getPlotUpdate();

  new File(resolvePath("..\\Post_Processing\\CF\\SC\\Scenes")).mkdirs();

if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {

    xYPlot_3.setTitle("CF_SC - y = 1 mm");

    xYPlot_3.encode(resolvePath("..\\Post_Processing\\CF\\SC\\Images\\CF_SC - 0 - y = 1 mm.jpg"), "png", 1920, 1080);
    xYPlot_3.exportScene(resolvePath("..\\Post_Processing\\CF\\SC\\Scenes\\CF_SC - 0 - y = 1 mm.sce"), "CF_SC", "", false, false);
    xYPlot_3.export(resolvePath("..\\Post_Processing\\CF\\SC\\Files\\CF_SC - 0 - y = 1 mm.csv"), ",");

    for (int i = 1; i < 44; i++) { 

    planeSection_3.getOriginCoordinate().setCoordinate(units_0, units_1, units_0, new DoubleVector(new double[] {0.0, (-250-i*10), 0.0}));

  double coordinate = 250+i*10;

  xYPlot_3.setTitle("CF_SC - y = "+ coordinate +" mm");
  xYPlot_3.encode(resolvePath("..\\Post_Processing\\CF\\SC\\Images\\CF_SC - "+ i +" - y = "+ coordinate +" mm.jpg"), "png", 1920, 1080);
  xYPlot_3.exportScene(resolvePath("..\\Post_Processing\\CF\\SC\\Scenes\\CF_SC - "+ i +" - y = "+ coordinate +" mm.sce"), "CF_SC", "", false, false);
  xYPlot_3.export(resolvePath("..\\Post_Processing\\CF\\SC\\Files\\CF_SC - "+ i +" - y = "+ coordinate +" mm.csv"), ",");
  }
  } else {

    xYPlot_2.setTitle("CF_SC - y = -695 mm");

    xYPlot_3.encode(resolvePath("..\\Post_Processing\\CF\\SC\\Images\\CF_SC - 0 - y = -695 mm.jpg"), "png", 1920, 1080);
    xYPlot_3.exportScene(resolvePath("..\\Post_Processing\\CF\\SC\\Scenes\\CF_SC - 0 - y = -695 mm.sce"), "CF_SC", "", false, false);
    xYPlot_3.export(resolvePath("..\\Post_Processing\\CF\\SC\\CF_SC - 0 - y = -695 mm.csv"), ",");

    for (int i = 1; i < 44; i++) { 

    planeSection_3.getOriginCoordinate().setCoordinate(units_0, units_1, units_0, new DoubleVector(new double[] {0.0, (-255-i*10), 0.0}));

  double coordinate = -250-i*10; 

    xYPlot_3.setTitle("CF_SC - y = "+ coordinate +" mm");
    xYPlot_3.encode(resolvePath("..\\Post_Processing\\CF\\SC\\Images\\CF_SC - "+ i +" - y = "+ coordinate +" mm.jpg"), "png", 1920, 1080);
    xYPlot_3.exportScene(resolvePath("..\\Post_Processing\\CF\\SC\\Scenes\\CF_SC - "+ i +" - y = "+ coordinate +" mm.sce"), "CF_SC", "", false, false);
    xYPlot_3.export(resolvePath("..\\Post_Processing\\CF\\SC\\Files\\CF_SC - "+ i +" - y = "+ coordinate +" mm.csv"), ",");
    }

    for (int i = 1; i < 44; i++) { 

    planeSection_3.getOriginCoordinate().setCoordinate(units_0, units_1, units_0, new DoubleVector(new double[] {0.0, (255+i*10), 0.0}));

  double coordinate = 250+i*10;
  double fixedindex = i+44;

    xYPlot_3.setTitle("CF_SC - y = "+ coordinate +" mm");
    xYPlot_3.encode(resolvePath("..\\Post_Processing\\CF\\SC\\Images\\CF_SC - "+ fixedindex +" - y = "+ coordinate +" mm.jpg"), "png", 1920, 1080);
    xYPlot_3.exportScene(resolvePath("..\\Post_Processing\\CF\\SC\\Scenes\\CF_SC - "+ fixedindex +" - y = "+ coordinate +" mm.sce"), "CF_SC", "", false, false);
    xYPlot_3.export(resolvePath("..\\Post_Processing\\CF\\SC\\Files\\CF_SC - "+ fixedindex +" - y = "+ coordinate +" mm.csv"), ",");
    }

  
  }

  ((ClientServerObjectGroup) simulation_0.getPlotManager().getGroupsManager().getObject("2D CF Plots")).getGroupsManager().groupObjects("2D CF Plots", new NeoObjectVector(new Object[] {xYPlot_3}), true);
  ((ClientServerObjectGroup) simulation_0.getPartManager().getGroupsManager().getObject("CF Planes")).getGroupsManager().groupObjects("CF Planes", new NeoObjectVector(new Object[] {planeSection_3}), true);
   

  }
    
}
