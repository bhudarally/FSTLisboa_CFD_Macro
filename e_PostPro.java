// Simcenter STAR-CCM+ macro: e_PostPro.java
// Written by Simcenter STAR-CCM+ 15.06.007
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

public class e_PostPro extends StarMacro {

  public void execute() {
	Reports();
	Monitors();
    CP();
	CpTx();
	CpTy();
	CpTz();
	// L2();
  }

	BufferedWriter bwout = null;

  public void Reports() {	
  
	//{ // Export Reports
	try {
		
	Simulation simulation_0 = getActiveSimulation();
	
	double SIMIDdouble = simulation_0.getReportManager().getReport("Sim ID").getReportMonitorValue();
	int intSIM = (int) SIMIDdouble;  
	
	bwout = new BufferedWriter(new FileWriter(resolvePath("..\\Visualization & Data\\Results\\Reports\\ReportsMC0"+intSIM+".csv")));                   
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
	//}
  }
  
  private void Monitors() {	
  
	//{ // Export Scene | Image | .CSV
	Simulation simulation_0 = 
      getActiveSimulation();
  
	MonitorPlot monitorPlot_0 = 
      ((MonitorPlot) simulation_0.getPlotManager().getPlot("[CLoad]"));
    monitorPlot_0.close();
    monitorPlot_0.exportScene(resolvePath("..\\Visualization & Data\\Results\\Monitors\\Center of Loads\\Scene_CLoad.sce"), "Downforce", "", false, false);
    monitorPlot_0.encode(resolvePath("..\\Visualization & Data\\Results\\Monitors\\Center of Loads\\Image_CLoad.png"), "png", 1920, 1080);
    monitorPlot_0.export(resolvePath("..\\Visualization & Data\\Results\\Monitors\\Center of Loads\\Monitor_CLoad.csv"), ",");
	
	MonitorPlot monitorPlot_1 = 
      ((MonitorPlot) simulation_0.getPlotManager().getPlot("[Cooling]"));
    monitorPlot_1.close();
    monitorPlot_1.exportScene(resolvePath("..\\Visualization & Data\\Results\\Monitors\\Cooling\\Scene_Cooling.sce"), "Downforce", "", false, false);
    monitorPlot_1.encode(resolvePath("..\\Visualization & Data\\Results\\Monitors\\Cooling\\Image_Cooling.png"), "png", 1920, 1080);
    monitorPlot_1.export(resolvePath("..\\Visualization & Data\\Results\\Monitors\\Cooling\\Monitor_Cooling.csv"), ",");
	
	MonitorPlot monitorPlot_2 = 
      ((MonitorPlot) simulation_0.getPlotManager().getPlot("[DownF]"));
    monitorPlot_2.close();
    monitorPlot_2.exportScene(resolvePath("..\\Visualization & Data\\Results\\Monitors\\DownF\\Scene_DownF.sce"), "Downforce", "", false, false);
    monitorPlot_2.encode(resolvePath("..\\Visualization & Data\\Results\\Monitors\\DownF\\Image_DownF.png"), "png", 1920, 1080);
    monitorPlot_2.export(resolvePath("..\\Visualization & Data\\Results\\Monitors\\DownF\\Monitor_DownF.csv"), ",");
	
	MonitorPlot monitorPlot_3 = 
      ((MonitorPlot) simulation_0.getPlotManager().getPlot("[Drag]"));
    monitorPlot_3.close();
    monitorPlot_3.exportScene(resolvePath("..\\Visualization & Data\\Results\\Monitors\\Drag\\Scene_Drag.sce"), "Downforce", "", false, false);
    monitorPlot_3.encode(resolvePath("..\\Visualization & Data\\Results\\Monitors\\Drag\\Image_Drag.png"), "png", 1920, 1080);
    monitorPlot_3.export(resolvePath("..\\Visualization & Data\\Results\\Monitors\\Drag\\Monitor_Drag.csv"), ",");
	
	MonitorPlot monitorPlot_4 = 
      ((MonitorPlot) simulation_0.getPlotManager().getPlot("[Moments]"));
    monitorPlot_4.close();
    monitorPlot_4.exportScene(resolvePath("..\\Visualization & Data\\Results\\Monitors\\Moments\\Scene_Moments.sce"), "Downforce", "", false, false);
    monitorPlot_4.encode(resolvePath("..\\Visualization & Data\\Results\\Monitors\\Moments\\Image_Moments.png"), "png", 1920, 1080);
    monitorPlot_4.export(resolvePath("..\\Visualization & Data\\Results\\Monitors\\Moments\\Monitor_Moments.csv"), ",");
	
	MonitorPlot monitorPlot_5 = 
      ((MonitorPlot) simulation_0.getPlotManager().getPlot("[SideF]"));
    monitorPlot_5.close();
    monitorPlot_5.exportScene(resolvePath("..\\Visualization & Data\\Results\\Monitors\\SideF\\Scene_SideF.sce"), "Downforce", "", false, false);
    monitorPlot_5.encode(resolvePath("..\\Visualization & Data\\Results\\Monitors\\SideF\\Image_SideF.png"), "png", 1920, 1080);
    monitorPlot_5.export(resolvePath("..\\Visualization & Data\\Results\\Monitors\\SideF\\Monitor_SideF.csv"), ",");
	
	MonitorPlot monitorPlot_6 = 
      ((MonitorPlot) simulation_0.getPlotManager().getPlot("[Time] Solver Iteration"));
    monitorPlot_6.close();
    monitorPlot_6.exportScene(resolvePath("..\\Visualization & Data\\Results\\Monitors\\Solver Time\\Scene_Time.sce"), "Downforce", "", false, false);
    monitorPlot_6.encode(resolvePath("..\\Visualization & Data\\Results\\Monitors\\Solver Time\\Image_Time.png"), "png", 1920, 1080);
    monitorPlot_6.export(resolvePath("..\\Visualization & Data\\Results\\Monitors\\Solver Time\\Monitor_Time.csv"), ",");
	
	MonitorPlot monitorPlot_7 = 
      ((MonitorPlot) simulation_0.getPlotManager().getPlot("Residuals"));
    monitorPlot_7.close();
    monitorPlot_7.exportScene(resolvePath("..\\Visualization & Data\\Results\\Monitors\\Residuals\\Scene_Residuals.sce"), "Downforce", "", false, false);
    monitorPlot_7.encode(resolvePath("..\\Visualization & Data\\Results\\Monitors\\Residuals\\Image_Residuals.png"), "png", 1920, 1080);
    monitorPlot_7.export(resolvePath("..\\Visualization & Data\\Results\\Monitors\\Residuals\\Monitor_Residuals.csv"), ",");
	//}
  }
  
  private void CP() {

	//{ // CP Scene
	
	Simulation simulation_0 = 
      getActiveSimulation();
	  
	double Roll = simulation_0.getReportManager().getReport("Roll [deg]").getReportMonitorValue();
	double Yaw = simulation_0.getReportManager().getReport("Yaw [rad]").getReportMonitorValue();
	double CornerRadius = simulation_0.getReportManager().getReport("Corner Radius [m]").getReportMonitorValue(); 
	double SIMIDdouble = simulation_0.getReportManager().getReport("Sim ID").getReportMonitorValue();
	int intSIM = (int) SIMIDdouble;
	  
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
      ((SimpleAnnotation) simulation_0.getAnnotationManager().getObject("Sim ID"));
    SimpleAnnotationProp simpleAnnotationProp_2 = 
      (SimpleAnnotationProp) scene_0.getAnnotationPropManager().createPropForAnnotation(simpleAnnotation_2);
	scene_0.setBackgroundColorMode(BackgroundColorMode.SOLID);
    scene_0.close();
	
	ScalarDisplayer scalarDisplayer_0 = 
      scene_0.getDisplayerManager().createScalarDisplayer("Scalar");
    scalarDisplayer_0.initialize();
    scalarDisplayer_0.setPresentationName("CP");
	
	Region region_0 = 
      simulation_0.getRegionManager().getRegion("Fluid");
	
	scalarDisplayer_0.getInputParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, InterfaceBoundary.class), new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.DoesNotContain, "Domain"), new NamePredicate(NameOperator.DoesNotContain, "Fan"), new NamePredicate(NameOperator.DoesNotContain, "Radiator"))))), Query.STANDARD_MODIFIERS));
	
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
	
    scene_0.export3DSceneFileAndWait(resolvePath("..\\Visualization & Data\\Results\\PostPro\\Cp\\Scene_CP.sce"), "CP", simpleAnnotation_0.getText(), false, true);
	
	CurrentView currentView_0 = 
      scene_0.getCurrentView();
  
	VisView visView_0 = 
      ((VisView) simulation_0.getViewManager().getObject("bottom"));
    currentView_0.setView(visView_0);
    scene_0.printAndWait(resolvePath("..\\Visualization & Data\\Results\\PostPro\\Cp\\bottom.png"), 1, 1920, 1080, true, false);
  
	VisView visView_1 = 
      ((VisView) simulation_0.getViewManager().getObject("bottom_front_left"));
    currentView_0.setView(visView_1);
    scene_0.printAndWait(resolvePath("..\\Visualization & Data\\Results\\PostPro\\Cp\\bottom_front_left.png"), 1, 1920, 1080, true, false);
	
	VisView visView_2 = 
      ((VisView) simulation_0.getViewManager().getObject("bottom_front_right"));
    currentView_0.setView(visView_2);
    scene_0.printAndWait(resolvePath("..\\Visualization & Data\\Results\\PostPro\\Cp\\bottom_front_right.png"), 1, 1920, 1080, true, false);
	
	VisView visView_3 = 
      ((VisView) simulation_0.getViewManager().getObject("bottom_rear_left"));
    currentView_0.setView(visView_3);
    scene_0.printAndWait(resolvePath("..\\Visualization & Data\\Results\\PostPro\\Cp\\bottom_rear_left.png"), 1, 1920, 1080, true, false);
	
	VisView visView_4 = 
      ((VisView) simulation_0.getViewManager().getObject("bottom_rear_right"));
    currentView_0.setView(visView_4);
    scene_0.printAndWait(resolvePath("..\\Visualization & Data\\Results\\PostPro\\Cp\\bottom_rear_right.png"), 1, 1920, 1080, true, false);
	
	VisView visView_5 = 
      ((VisView) simulation_0.getViewManager().getObject("front"));
    currentView_0.setView(visView_5);
    scene_0.printAndWait(resolvePath("..\\Visualization & Data\\Results\\PostPro\\Cp\\front.png"), 1, 1920, 1080, true, false);
	
	VisView visView_6 = 
      ((VisView) simulation_0.getViewManager().getObject("left"));
    currentView_0.setView(visView_6);
    scene_0.printAndWait(resolvePath("..\\Visualization & Data\\Results\\PostPro\\Cp\\left.png"), 1, 1920, 1080, true, false);
	
	VisView visView_7 = 
      ((VisView) simulation_0.getViewManager().getObject("rear"));
    currentView_0.setView(visView_7);
    scene_0.printAndWait(resolvePath("..\\Visualization & Data\\Results\\PostPro\\Cp\\rear.png"), 1, 1920, 1080, true, false);
	
	VisView visView_8 = 
      ((VisView) simulation_0.getViewManager().getObject("right"));
    currentView_0.setView(visView_8);
    scene_0.printAndWait(resolvePath("..\\Visualization & Data\\Results\\PostPro\\Cp\\right.png"), 1, 1920, 1080, true, false);
	
	VisView visView_9 = 
      ((VisView) simulation_0.getViewManager().getObject("top"));
    currentView_0.setView(visView_9);
    scene_0.printAndWait(resolvePath("..\\Visualization & Data\\Results\\PostPro\\Cp\\top.png"), 1, 1920, 1080, true, false);
	
	VisView visView_10 = 
      ((VisView) simulation_0.getViewManager().getObject("top_front_left"));
    currentView_0.setView(visView_10);
    scene_0.printAndWait(resolvePath("..\\Visualization & Data\\Results\\PostPro\\Cp\\top_front_left.png"), 1, 1920, 1080, true, false);
	
	VisView visView_11 = 
      ((VisView) simulation_0.getViewManager().getObject("top_front_right"));
    currentView_0.setView(visView_11);
    scene_0.printAndWait(resolvePath("..\\Visualization & Data\\Results\\PostPro\\Cp\\top_front_right.png"), 1, 1920, 1080, true, false);
  
	VisView visView_12 = 
      ((VisView) simulation_0.getViewManager().getObject("top_rear_left"));
    currentView_0.setView(visView_12);
    scene_0.printAndWait(resolvePath("..\\Visualization & Data\\Results\\PostPro\\Cp\\top_rear_left.png"), 1, 1920, 1080, true, false);
	
	VisView visView_13 = 
      ((VisView) simulation_0.getViewManager().getObject("top_rear_right"));
    currentView_0.setView(visView_13);
    scene_0.printAndWait(resolvePath("..\\Visualization & Data\\Results\\PostPro\\Cp\\top_rear_right.png"), 1, 1920, 1080, true, false);
	
	
	//}
  
	//{ // Velocity Avg.
  UserFieldFunction userFieldFunction_1 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();
    userFieldFunction_1.getTypeOption().setSelected(FieldFunctionTypeOption.Type.VECTOR);
    userFieldFunction_1.setPresentationName("Velocity Avg.");
    userFieldFunction_1.setFunctionName("v_avg");

    Units units_1 = 
      simulation_0.getUnitsManager().getPreferredUnits(new IntVector(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}));
    userFieldFunction_1.setDefinition("[${V[i]Monitor},${V[j]Monitor},${V[k]Monitor}]");
	//}
  }

  private void CpTx() {
	  
	Simulation simulation_0 = 
      getActiveSimulation();  
	  
	double Roll = simulation_0.getReportManager().getReport("Roll [deg]").getReportMonitorValue();
	double Yaw = simulation_0.getReportManager().getReport("Yaw [rad]").getReportMonitorValue();
	double CornerRadius = simulation_0.getReportManager().getReport("Corner Radius [m]").getReportMonitorValue(); 
	double SIMIDdouble = simulation_0.getReportManager().getReport("Sim ID").getReportMonitorValue();
	int intSIM = (int) SIMIDdouble;  
	  
	PlaneSection planeSection_0 = 
      (PlaneSection) simulation_0.getPartManager().createImplicitPart(new NeoObjectVector(new Object[] {}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), new DoubleVector(new double[] {0.0, 0.0, 0.0}), 0, 1, new DoubleVector(new double[] {0.0}));
 
    planeSection_0.setPresentationName("CPTsectionx");
	planeSection_0.getInputParts().setQuery(new Query(new TypePredicate(TypeOperator.Is, Region.class), Query.STANDARD_MODIFIERS));

	Units units_0 = 
      ((Units) simulation_0.getUnitsManager().getObject("m"));
	Units units_1 = 
      ((Units) simulation_0.getUnitsManager().getObject("mm"));

    planeSection_0.getOrientationCoordinate().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {1.0, 0.0, 0.0}));
    planeSection_0.getOriginCoordinate().setCoordinate(units_1, units_0, units_0, new DoubleVector(new double[] {1000.0, 0.0, 0.0}));

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
      ((SimpleAnnotation) simulation_0.getAnnotationManager().getObject("Sim ID"));
    SimpleAnnotationProp simpleAnnotationProp_2 = 
      (SimpleAnnotationProp) scene_0.getAnnotationPropManager().createPropForAnnotation(simpleAnnotation_2);
    simpleAnnotationProp_2.setPosition(new DoubleVector(new double[] {0.59, 0.01, 0.0}));
	
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

	final String folder = resolveWorkPath();
	ImageAnnotation2D imageAnnotation2D_1 = 
      simulation_0.getAnnotationManager().createAnnotation(ImageAnnotation2D.class);
	imageAnnotation2D_1.setFilePath( folder +"/Files/BlackSide.png");
    imageAnnotation2D_1.setPresentationName("Car-Side");
    imageAnnotation2D_1.setDefaultWidthStretchFactor(1.0);
	imageAnnotation2D_1.setDefaultHeight(0.225);
	imageAnnotation2D_1.setDefaultPosition(new DoubleVector(new double[] {0.0, 0.125, 0.0}));
	
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
	
	if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
	cartesian2DAxis_0.setMinimum(-15.0);
    cartesian2DAxis_0.setMaximum(12.0);
	} else {
	cartesian2DAxis_0.setMinimum(-23.0);
    cartesian2DAxis_0.setMaximum(29.0);}
	
    Cartesian2DAxis cartesian2DAxis_1 = 
      ((Cartesian2DAxis) cartesian2DAxisManager_0.getAxis("Left Axis"));
    cartesian2DAxis_1.setPosition(Cartesian2DAxis.Position.Bottom);
	cartesian2DAxis_1.setMinimum(-4.0);
	cartesian2DAxis_1.setMaximum(9.0);

    AxisType axisType_0 = 
      xYPlot_0.getXAxisType();
    axisType_0.getDirectionVector().setComponents(0.0, 1.0, 0.0);
	axisType_0.getDirectionVector().setUnits(units_0);

	YAxisType yAxisType_0 = 
      ((YAxisType) xYPlot_0.getYAxes().getAxisType("Y Type 1"));
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
	userLookupTable_0.setColorMap(new ColorMap(new DoubleVector(new double[] {0.0, 0.0, 0.0, 0.0, 0.0738, 1.0, 0.0, 1.0, 0.12, 1.0, 0.0, 1.0, 0.24, 0.0, 0.0, 1.0, 0.26, 0.0, 0.0, 1.0, 0.49, 0.0, 1.0, 1.0, 0.51, 0.0, 1.0, 1.0, 0.627, 0.0, 1.0, 0.0, 0.75, 1.0, 1.0, 0.0, 0.9447, 1.0, 0.0, 0.0, 0.9631, 1.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0}), new DoubleVector(new double[] {0.0, 1.0, 0.9898, 1.0, 1.0, 0.0}), 0));
	}
	
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
	
	scalarDisplayer_0.setFillMode(ScalarFillMode.NODE_FILLED);
    scalarDisplayer_0.getScalarDisplayQuantity().setRange(new DoubleVector(new double[] {-1, 1}));
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
	vectorDisplayer_0.setOpacity(0.25);
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
    vectorDisplayer_0.getColoringScalar().setRange(new DoubleVector(new double[] {-1.0, 0.98}));
    vectorDisplayer_0.getColoringScalar().setClip(ClipMode.MAX);
	
	legend_1.setVisible(false);
	
	CurrentView currentView_0 = 
      scene_0.getCurrentView();
	
	scene_0.setViewOrientation(new DoubleVector(new double[] {1.0, -0.0, -0.0}), new DoubleVector(new double[] {0.0, 0.0, 1.0}));

	VisView visView_0 = 
      ((VisView) simulation_0.getViewManager().getObject("front"));
    currentView_0.setView(visView_0);
	scene_0.printAndWait(resolvePath("..\\Visualization & Data\\Results\\PostPro\\CpT-x\\0 CpTx1000mm.png"), 1, 1920, 1080, true, false);
	
	for (int i = 1; i < 220; i++) {   // CHANGE I BACK TO 220
    planeSection_0.getOriginCoordinate().setCoordinate(units_1, units_0, units_0, new DoubleVector(new double[] {(1000-i*15), 0.0, 0.0}));
	currentView_0.setView(visView_0);
	double coordinate = 1000 - i*15; 
	simpleAnnotation_3.setText("X = "+ coordinate +" mm");
    scene_0.printAndWait(resolvePath("..\\Visualization & Data\\Results\\PostPro\\CpT-x\\"+ i +" CpTx"+ coordinate +"mm.png"), 1, 1920, 1080, true, false);
	}
	
	simulation_0.getPlotManager().getGroupsManager().createGroup("Plane Aux");
	((ClientServerObjectGroup) simulation_0.getPlotManager().getGroupsManager().getObject("Plane Aux")).getGroupsManager().groupObjects("Plane Aux", new NeoObjectVector(new Object[] {xYPlot_0}), true);
	
  }
  
  private void CpTy() {
	  
	Simulation simulation_0 = 
      getActiveSimulation();  
	  
	double Roll = simulation_0.getReportManager().getReport("Roll [deg]").getReportMonitorValue();
	double Yaw = simulation_0.getReportManager().getReport("Yaw [rad]").getReportMonitorValue();
	double CornerRadius = simulation_0.getReportManager().getReport("Corner Radius [m]").getReportMonitorValue(); 
	double SIMIDdouble = simulation_0.getReportManager().getReport("Sim ID").getReportMonitorValue();
	int intSIM = (int) SIMIDdouble;  
	  
	PlaneSection planeSection_0 = 
      (PlaneSection) simulation_0.getPartManager().createImplicitPart(new NeoObjectVector(new Object[] {}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), new DoubleVector(new double[] {0.0, 0.0, 0.0}), 0, 1, new DoubleVector(new double[] {0.0}));
 
    planeSection_0.setPresentationName("CPTsectiony");
	planeSection_0.getInputParts().setQuery(new Query(new TypePredicate(TypeOperator.Is, Region.class), Query.STANDARD_MODIFIERS));

	Units units_0 = 
      ((Units) simulation_0.getUnitsManager().getObject("m"));
	Units units_1 = 
      ((Units) simulation_0.getUnitsManager().getObject("mm"));
	  
	if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
	planeSection_0.getOrientationCoordinate().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {0.0, 1.0, 0.0}));
    planeSection_0.getOriginCoordinate().setCoordinate(units_1, units_0, units_0, new DoubleVector(new double[] {0.0, -1.0, 0.0}));
	} else {
    planeSection_0.getOrientationCoordinate().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {0.0, 1.0, 0.0}));
    planeSection_0.getOriginCoordinate().setCoordinate(units_1, units_0, units_0, new DoubleVector(new double[] {0.0, -901.0, 0.0}));
	}
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
      ((SimpleAnnotation) simulation_0.getAnnotationManager().getObject("Sim ID"));
    SimpleAnnotationProp simpleAnnotationProp_2 = 
      (SimpleAnnotationProp) scene_0.getAnnotationPropManager().createPropForAnnotation(simpleAnnotation_2);
    simpleAnnotationProp_2.setPosition(new DoubleVector(new double[] {0.59, 0.01, 0.0}));
	
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

	final String folder = resolveWorkPath();
	ImageAnnotation2D imageAnnotation2D_1 = 
      simulation_0.getAnnotationManager().createAnnotation(ImageAnnotation2D.class);
	imageAnnotation2D_1.setFilePath( folder +"/Files/BlackTop.png");
    imageAnnotation2D_1.setPresentationName("Car-Top");
    imageAnnotation2D_1.setDefaultWidthStretchFactor(1.0);
	imageAnnotation2D_1.setDefaultHeight(0.225);
	imageAnnotation2D_1.setDefaultPosition(new DoubleVector(new double[] {0.0, 0.125, 0.0}));
	
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
	if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
	cartesian2DAxis_0.setMinimum(-45.0);   //Before was -43.0
    cartesian2DAxis_0.setMaximum(90.0);	   //Before was 90.0
	} else {
	cartesian2DAxis_0.setMinimum(-40.0);
    cartesian2DAxis_0.setMaximum(84.0);}

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

	YAxisType yAxisType_0 = 
      ((YAxisType) xYPlot_0.getYAxes().getAxisType("Y Type 1"));
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
	userLookupTable_0.setColorMap(new ColorMap(new DoubleVector(new double[] {0.0, 0.0, 0.0, 0.0, 0.0738, 1.0, 0.0, 1.0, 0.12, 1.0, 0.0, 1.0, 0.24, 0.0, 0.0, 1.0, 0.26, 0.0, 0.0, 1.0, 0.49, 0.0, 1.0, 1.0, 0.51, 0.0, 1.0, 1.0, 0.627, 0.0, 1.0, 0.0, 0.75, 1.0, 1.0, 0.0, 0.9447, 1.0, 0.0, 0.0, 0.9631, 1.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0}), new DoubleVector(new double[] {0.0, 1.0, 0.9898, 1.0, 1.0, 0.0}), 0));
	}
	
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
	
	scalarDisplayer_0.setFillMode(ScalarFillMode.NODE_FILLED);
    scalarDisplayer_0.getScalarDisplayQuantity().setRange(new DoubleVector(new double[] {-1, 1}));
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
	vectorDisplayer_0.setOpacity(0.25);
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
    vectorDisplayer_0.getColoringScalar().setRange(new DoubleVector(new double[] {-1.0, 0.98}));
    vectorDisplayer_0.getColoringScalar().setClip(ClipMode.MAX);
	legend_1.setVisible(false);
	
	CurrentView currentView_0 = 
      scene_0.getCurrentView();
	
	scene_0.setViewOrientation(new DoubleVector(new double[] {0.0, -1.0, 0.0}), new DoubleVector(new double[] {0.0, 0.0, 1.0}));
	
	if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
	
	VisView visView_0 = 
      ((VisView) simulation_0.getViewManager().getObject("right"));
    currentView_0.setView(visView_0);
	scene_0.printAndWait(resolvePath("..\\Visualization & Data\\Results\\PostPro\\CpT-y\\0 CpTy-1mm.png"), 1, 1920, 1080, true, false);
	
	for (int i = 1; i < 101; i++) { // CHANGE BACK I TO 101
    planeSection_0.getOriginCoordinate().setCoordinate(units_1, units_1, units_0, new DoubleVector(new double[] {0.0, (-1-i*9), 0.0}));
	currentView_0.setView(visView_0);
	double coordinate = -1-i*9; 
	simpleAnnotation_3.setText("Y = "+ coordinate +" mm");
    scene_0.printAndWait(resolvePath("..\\Visualization & Data\\Results\\PostPro\\CpT-y\\"+ i +" CpTy"+ coordinate +"mm.png"), 1, 1920, 1080, true, false);
	}
	} else {

	VisView visView_0 = 
      ((VisView) simulation_0.getViewManager().getObject("right"));
    currentView_0.setView(visView_0);
	scene_0.printAndWait(resolvePath("..\\Visualization & Data\\Results\\PostPro\\CpT-y\\0 CpTy-901mm.png"), 1, 1920, 1080, true, false);
	
	for (int i = 1; i < 202; i++) { // CHANGE BACK I TO 202
    planeSection_0.getOriginCoordinate().setCoordinate(units_1, units_1, units_0, new DoubleVector(new double[] {0.0, (-901+i*9), 0.0}));
	currentView_0.setView(visView_0);
	double coordinate = -901+i*9; 
	simpleAnnotation_3.setText("Y = "+ coordinate +" mm");
    scene_0.printAndWait(resolvePath("..\\Visualization & Data\\Results\\PostPro\\CpT-y\\"+ i +" CpTy"+ coordinate +"mm.png"), 1, 1920, 1080, true, false);
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
	double SIMIDdouble = simulation_0.getReportManager().getReport("Sim ID").getReportMonitorValue();
	int intSIM = (int) SIMIDdouble;  
	  
	PlaneSection planeSection_0 = 
      (PlaneSection) simulation_0.getPartManager().createImplicitPart(new NeoObjectVector(new Object[] {}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), new DoubleVector(new double[] {0.0, 0.0, 0.0}), 0, 1, new DoubleVector(new double[] {0.0}));
 
    planeSection_0.setPresentationName("CPTsectionz");
	planeSection_0.getInputParts().setQuery(new Query(new TypePredicate(TypeOperator.Is, Region.class), Query.STANDARD_MODIFIERS));

	Units units_0 = 
      ((Units) simulation_0.getUnitsManager().getObject("m"));
	Units units_1 = 
      ((Units) simulation_0.getUnitsManager().getObject("mm"));

    planeSection_0.getOrientationCoordinate().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {0.0, 0.0, 1.0}));
    planeSection_0.getOriginCoordinate().setCoordinate(units_1, units_0, units_1, new DoubleVector(new double[] {0.0, 0.0, 5.0}));

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
      ((SimpleAnnotation) simulation_0.getAnnotationManager().getObject("Sim ID"));
    SimpleAnnotationProp simpleAnnotationProp_2 = 
      (SimpleAnnotationProp) scene_0.getAnnotationPropManager().createPropForAnnotation(simpleAnnotation_2);
    simpleAnnotationProp_2.setPosition(new DoubleVector(new double[] {0.59, 0.01, 0.0}));
	
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
	
	ImageAnnotation2D imageAnnotation2D_1 = 
      ((ImageAnnotation2D) simulation_0.getAnnotationManager().getObject("Car-Side"));
	
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
	
	if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
	cartesian2DAxis_0.setMinimum(-8.5);
    cartesian2DAxis_0.setMaximum(13.5);
	} else {
	cartesian2DAxis_0.setMinimum(-23.0);
    cartesian2DAxis_0.setMaximum(29.0);}
	
    Cartesian2DAxis cartesian2DAxis_1 = 
      ((Cartesian2DAxis) cartesian2DAxisManager_0.getAxis("Left Axis"));
    cartesian2DAxis_1.setPosition(Cartesian2DAxis.Position.Right);
	cartesian2DAxis_1.setMinimum(-2.5);
	cartesian2DAxis_1.setMaximum(4.5);

    AxisType axisType_0 = 
      xYPlot_0.getXAxisType();
    axisType_0.getDirectionVector().setComponents(0.0, 1.0, 0.0);
	axisType_0.getDirectionVector().setUnits(units_0);

	YAxisType yAxisType_0 = 
      ((YAxisType) xYPlot_0.getYAxes().getAxisType("Y Type 1"));

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

	scene_0.setBackgroundColorMode(BackgroundColorMode.SOLID);
	scene_0.close();
	
	ScalarDisplayer scalarDisplayer_0 = 
      scene_0.getDisplayerManager().createScalarDisplayer("Scalar");
    scalarDisplayer_0.initialize();
    scalarDisplayer_0.setPresentationName("CpTz");
    scalarDisplayer_0.getInputParts().setObjects(planeSection_0);
	
	ClipPlane clipPlane_0 = 
      ((ClipPlane) scene_0.getPlaneManager().getPlane("Plane 1"));
    ClipPlane clipPlane_1 = 
      ((ClipPlane) scene_0.getPlaneManager().getPlane("Plane 2"));
    ClipPlane clipPlane_2 = 
      ((ClipPlane) scene_0.getPlaneManager().getPlane("Plane 3"));
	ClipPlane clipPlane_3 = 
      ((ClipPlane) scene_0.getPlaneManager().getPlane("Plane 4"));
    clipPlane_0.getOriginCoordinate().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {-2.9, 0.0, 0.0}));
    clipPlane_1.getOriginCoordinate().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {1.2, 0.0, 0.0}));
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
	userLookupTable_0.setColorMap(new ColorMap(new DoubleVector(new double[] {0.0, 0.0, 0.0, 0.0, 0.0738, 1.0, 0.0, 1.0, 0.12, 1.0, 0.0, 1.0, 0.24, 0.0, 0.0, 1.0, 0.26, 0.0, 0.0, 1.0, 0.49, 0.0, 1.0, 1.0, 0.51, 0.0, 1.0, 1.0, 0.627, 0.0, 1.0, 0.0, 0.75, 1.0, 1.0, 0.0, 0.9447, 1.0, 0.0, 0.0, 0.9631, 1.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0}), new DoubleVector(new double[] {0.0, 1.0, 0.9898, 1.0, 1.0, 0.0}), 0));
	}
	
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
	
	scalarDisplayer_0.setFillMode(ScalarFillMode.NODE_FILLED);
    scalarDisplayer_0.getScalarDisplayQuantity().setRange(new DoubleVector(new double[] {-1, 1}));
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
	vectorDisplayer_0.setOpacity(0.25);
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
    vectorDisplayer_0.getColoringScalar().setRange(new DoubleVector(new double[] {-1.0, 0.98}));
    vectorDisplayer_0.getColoringScalar().setClip(ClipMode.MAX);
	
	legend_1.setVisible(false);
	
	CurrentView currentView_0 = 
      scene_0.getCurrentView();
	
	scene_0.setViewOrientation(new DoubleVector(new double[] {0.0, 0.0, 1.0}), new DoubleVector(new double[] {0.0, 0.0, 1.0}));

	VisView visView_0 = 
      ((VisView) simulation_0.getViewManager().getObject("top_CptZ"));
    currentView_0.setView(visView_0);
	
	// ParallelScale parallelScale_0 = 
      // currentView_0.getParallelScale();
    // parallelScale_0.setValue(1.4);
	// currentView_0.getFocalPointCoordinate().setCoordinate(units_1, units_1, units_1, new DoubleVector(new double[] {-510.0, -200.0, 0.0}));
	scene_0.printAndWait(resolvePath("..\\Visualization & Data\\Results\\PostPro\\CpT-z\\0 CpTz5mm.png"), 1, 1920, 1080, true, false);
	
	for (int i = 1; i < 130; i++) {  
    planeSection_0.getOriginCoordinate().setCoordinate(units_1, units_0, units_1, new DoubleVector(new double[] {0.0, 0.0, (5+i*10)}));
	currentView_0.setView(visView_0);
	// parallelScale_0.setValue(1.4);
	// currentView_0.getFocalPointCoordinate().setCoordinate(units_1, units_1, units_1, new DoubleVector(new double[] {-510.0, -200.0, 0.0}));
	double coordinate = 5+i*10;
	simpleAnnotation_3.setText("Z = "+ coordinate +" mm");
    scene_0.printAndWait(resolvePath("..\\Visualization & Data\\Results\\PostPro\\CpT-z\\"+ i +" CpTz"+ coordinate +"mm.png"), 1, 1920, 1080, true, false);
	}

	((ClientServerObjectGroup) simulation_0.getPlotManager().getGroupsManager().getObject("Plane Aux")).getGroupsManager().groupObjects("Plane Aux", new NeoObjectVector(new Object[] {xYPlot_0}), true);	
	simulation_0.saveState(resolvePath("..\\Simulation\\MC0"+intSIM+".sim"));
	
  }
  
  private void L2() {
  }

}
