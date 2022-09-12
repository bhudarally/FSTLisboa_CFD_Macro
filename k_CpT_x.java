// STAR-CCM+ macro: k_CpT_x.java
// Written by STAR-CCM+ 14.04.011
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;
import star.flow.*;
import star.base.query.*;
import star.vis.*;

public class k_CpT_x extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();
	
    PlaneSection planeSection_0 = 
      (PlaneSection) simulation_0.getPartManager().createImplicitPart(new NeoObjectVector(new Object[] {}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), new DoubleVector(new double[] {0.0, 0.0, 0.0}), 0, 1, new DoubleVector(new double[] {0.0}));
    planeSection_0.setPresentationName("CPTsection");

     planeSection_0.getInputParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Region.class))), Query.STANDARD_MODIFIERS));

    Units units_0 = 
      ((Units) simulation_0.getUnitsManager().getObject("m"));

    planeSection_0.getOrientationCoordinate().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {1.0, 0.0, 0.0}));

    Units units_1 = 
      ((Units) simulation_0.getUnitsManager().getObject("mm"));

    planeSection_0.getOriginCoordinate().setCoordinate(units_1, units_0, units_0, new DoubleVector(new double[] {1000.0, 0.0, 0.0}));

    simulation_0.getSceneManager().createEmptyScene("Scene");

    Scene scene_0 = 
      simulation_0.getSceneManager().getScene("Scene 1");

    scene_0.initializeAndWait();

    SceneUpdate sceneUpdate_0 = 
      scene_0.getSceneUpdate();

    scene_0.resetCamera();
	
	scene_0.setPresentationName("CpT_loop_X");

  scene_0.close();

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

   VectorDisplayer vectorDisplayer_0 = 
      scene_0.getDisplayerManager().createVectorDisplayer("Vector");

    vectorDisplayer_0.initialize();
	
	try {
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

  try {

    UserFieldFunction userFieldFunction_0 = 
      ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("CpT"));

    vectorDisplayer_0.getColoringScalar().setFieldFunction(userFieldFunction_0);

  }
  catch (Exception e){

    UserFieldFunction userFieldFunction_0 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();

    userFieldFunction_0.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);

    userFieldFunction_0.setFunctionName("CpT");

    userFieldFunction_0.setPresentationName("CpT");

    userFieldFunction_0.setDefinition("(${TotalPressureMonitor}-${Pressure Avg. Inlet})/(0.5*1.225*pow(${Car-Velocity},2))");

    vectorDisplayer_0.getColoringScalar().setFieldFunction(userFieldFunction_0);
    
  }

    UserFieldFunction userFieldFunction_0 = 
      ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("CpT"));

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


  UserLookupTable userLookupTable_0 = 
      ((UserLookupTable) simulation_0.get(LookupTableManager.class).getObject("Carreira_CPT_-1+1"));
	  
	 ScalarDisplayer scalarDisplayer_0 = 
      scene_0.getDisplayerManager().createScalarDisplayer("Scalar");
    scalarDisplayer_0.initialize();
    scalarDisplayer_0.setPresentationName("CpTx");
    scalarDisplayer_0.getInputParts().setObjects(planeSection_0);
	scalarDisplayer_0.getScalarDisplayQuantity().setFieldFunction(userFieldFunction_0);
	Legend legend_0 = 
      scalarDisplayer_0.getLegend();
	  
	scalarDisplayer_0.setFillMode(ScalarFillMode.NODE_SMOOTH);
    scalarDisplayer_0.getInterpolationOption().setSelected(ScalarInterpolationOption.Type.BANDED);
    scalarDisplayer_0.getScalarDisplayQuantity().setRange(new DoubleVector(new double[] {-1.0, 1.0}));
    scalarDisplayer_0.getScalarDisplayQuantity().setClip(ClipMode.NONE);

    legend_0.setLookupTable(userLookupTable_0);
	
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
	legend_1.setVisible(false);
    
    vectorDisplayer_0.getColoringScalar().setFieldFunction(userFieldFunction_0);
  LICSettings lICSettings_0 = 
      vectorDisplayer_0.getLICSettings();
    lICSettings_0.setNumberOfSteps(40);
    lICSettings_0.setIntensity(1.0);
    vectorDisplayer_0.getColoringScalar().setRange(new DoubleVector(new double[] {-1.0, 1.0}));
    vectorDisplayer_0.getColoringScalar().setClip(ClipMode.NONE);

    SymmetricRepeat symmetricRepeat_0 = 
      ((SymmetricRepeat) simulation_0.getTransformManager().getObject("Domain.Symmetry 1"));
	vectorDisplayer_0.setVisTransform(symmetricRepeat_0);
	scalarDisplayer_0.setVisTransform(symmetricRepeat_0);

    vectorDisplayer_0.getColoringScalar().setRange(new DoubleVector(new double[] {-1.0, 1.0}));

    vectorDisplayer_0.getColoringScalar().setClip(ClipMode.NONE);  
	
	legend_0.setShadow(true);
	
	legend_0.setLabelFormat("%-+1.1f");
	
	legend_0.setNumberOfLabels(4);
	
	legend_0.setTextColor(new DoubleVector(new double[] {1.0, 1.0, 1.0}));
	
	legend_0.setWidth(0.02);  
	
	legend_0.setFontString("Lucida Sans-Bold");
	
	legend_0.setTextPosition(LegendTextPosition.LEGEND_POSITION_FOLLOWING);
	
	legend_0.setHeight(0.3);
	
	legend_0.setTitleHeight(0.032);
	
	legend_0.setLabelHeight(0.025);
	
	legend_0.updateLayout(new DoubleVector(new double[] {0.96, 0.52}), 0.02, 0.35, 1);
	 
	ImageAnnotation2D imageAnnotation2D_0 = 
      ((ImageAnnotation2D) simulation_0.getAnnotationManager().getObject("fst_white")); 
	  
	LogoAnnotation logoAnnotation_0 = 
      ((LogoAnnotation) simulation_0.getAnnotationManager().getObject("Logo"));

    scene_0.getAnnotationPropManager().removePropsForAnnotations(logoAnnotation_0);
	 
	 FixedAspectAnnotationProp fixedAspectAnnotationProp_0 = 
      (FixedAspectAnnotationProp) scene_0.getAnnotationPropManager().createPropForAnnotation(imageAnnotation2D_0);
	  
	fixedAspectAnnotationProp_0.setHeight(0.075);
	
	fixedAspectAnnotationProp_0.setPosition(new DoubleVector(new double[] {0.905, 0.03, 0.0}));
	
	SimpleAnnotation simpleAnnotation_0 = 
      simulation_0.getAnnotationManager().createSimpleAnnotation();
	  
	simpleAnnotation_0.setPresentationName("Plane Coordinate");
	
	FixedAspectAnnotationProp fixedAspectAnnotationProp_1 = 
      (FixedAspectAnnotationProp) scene_0.getAnnotationPropManager().createPropForAnnotation(simpleAnnotation_0);
	
	simpleAnnotation_0.setFontString("Dialog-Plain");
	
	simpleAnnotation_0.setColor(new DoubleVector(new double[] {1.0, 1.0, 1.0}));
	  
	simpleAnnotation_0.setText("x = 1000 mm");

	fixedAspectAnnotationProp_1.setHeight(0.035);
	
	fixedAspectAnnotationProp_1.setPosition(new DoubleVector(new double[] {0.01, 0.01, 0.0}));

      SimpleAnnotation simpleAnnotation_4 = 
      ((SimpleAnnotation) simulation_0.getAnnotationManager().getObject("Description"));

    FixedAspectAnnotationProp fixedAspectAnnotationProp_4 = 
      (FixedAspectAnnotationProp) scene_0.getAnnotationPropManager().createPropForAnnotation(simpleAnnotation_4);

    fixedAspectAnnotationProp_4.setHeight(0.016);

    fixedAspectAnnotationProp_4.setPosition(new DoubleVector(new double[] {0.0075, 0.97, 0.0}));

    SimpleAnnotation simpleAnnotation_3 = 
      ((SimpleAnnotation) simulation_0.getAnnotationManager().getObject("Sim_Type"));

    FixedAspectAnnotationProp fixedAspectAnnotationProp_3 = 
      (FixedAspectAnnotationProp) scene_0.getAnnotationPropManager().createPropForAnnotation(simpleAnnotation_3);

    fixedAspectAnnotationProp_3.setHeight(0.016);

    fixedAspectAnnotationProp_3.setPosition(new DoubleVector(new double[] {0.0075, 0.95, 0.0}));

      SimpleAnnotation simpleAnnotation_11 = 
      ((SimpleAnnotation) simulation_0.getAnnotationManager().getObject("Velocity"));

    FixedAspectAnnotationProp fixedAspectAnnotationProp_11 = 
      (FixedAspectAnnotationProp) scene_0.getAnnotationPropManager().createPropForAnnotation(simpleAnnotation_11);

    fixedAspectAnnotationProp_11.setHeight(0.016);

    fixedAspectAnnotationProp_11.setPosition(new DoubleVector(new double[] {0.0075, 0.93, 0.0}));

  SimpleAnnotation simpleAnnotation_10 = 
      ((SimpleAnnotation) simulation_0.getAnnotationManager().getObject("FRH"));

    FixedAspectAnnotationProp fixedAspectAnnotationProp_10 = 
      (FixedAspectAnnotationProp) scene_0.getAnnotationPropManager().createPropForAnnotation(simpleAnnotation_10);

    fixedAspectAnnotationProp_10.setHeight(0.016);

    fixedAspectAnnotationProp_10.setPosition(new DoubleVector(new double[] {0.0075, 0.91, 0.0}));

    SimpleAnnotation simpleAnnotation_2 = 
      ((SimpleAnnotation) simulation_0.getAnnotationManager().getObject("RRH"));

    FixedAspectAnnotationProp fixedAspectAnnotationProp_2 = 
      (FixedAspectAnnotationProp) scene_0.getAnnotationPropManager().createPropForAnnotation(simpleAnnotation_2);

    fixedAspectAnnotationProp_2.setHeight(0.016);

    fixedAspectAnnotationProp_2.setPosition(new DoubleVector(new double[] {0.0075, 0.89, 0.0}));

    CurrentView currentView_0 = 
      scene_0.getCurrentView();
    
    scene_0.setViewOrientation(new DoubleVector(new double[] {1.0, -0.0, -0.0}), new DoubleVector(new double[] {0.0, 0.0, 1.0}));

    scene_0.setBackgroundColorMode(BackgroundColorMode.SOLID);

    SolidBackgroundColor solidBackgroundColor_0 = 
      scene_0.getSolidBackgroundColor();

    solidBackgroundColor_0.setColor(new DoubleVector(new double[] {0.800000011920929, 0.800000011920929, 0.800000011920929}));
	
	currentView_0.setInput(new DoubleVector(new double[] {0.7661203875055023, -0.0022834004916742834, 0.729558522941893}), new DoubleVector(new double[] {30.00007268903694, -0.0022834004916742834, 0.729558522941893}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), 0.73, 1, 30.0);
	
	ParallelScale parallelScale_0 = 
      currentView_0.getParallelScale();

    parallelScale_0.setValue(0.73);

    scene_0.printAndWait(resolvePath("..\\Post_Processing\\CpTx\\cpt0 - x = 1000 mm.jpg"), 1, 1920, 1080, true, false);
	
	for (int i = 1; i < 100; i++) { 

    planeSection_0.getOriginCoordinate().setCoordinate(units_1, units_0, units_0, new DoubleVector(new double[] {(1000-i*15), 0.0, 0.0}));
	
	currentView_0.setInput(new DoubleVector(new double[] {0.7661203875055023, -0.0022834004916742834, 0.729558522941893}), new DoubleVector(new double[] {30.00007268903694, -0.0022834004916742834, 0.729558522941893}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), 0.73, 1, 30.0);
	
	double coordinate = 1000 - i*15; 
	
	simpleAnnotation_0.setText("x = "+ coordinate +" mm");

    scene_0.printAndWait(resolvePath("..\\Post_Processing\\CpTx\\cpt"+ i +" - x = "+ coordinate +" mm.jpg"), 1, 1920, 1080, true, false);
	}
  }
}
