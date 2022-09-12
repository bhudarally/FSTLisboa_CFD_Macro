// STAR-CCM+ macro: o_vz.java
// Written by STAR-CCM+ 14.04.011
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;
import star.base.query.*;
import star.vis.*;

public class o_vz extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    PlaneSection planeSection_0 = 
      (PlaneSection) simulation_0.getPartManager().createImplicitPart(new NeoObjectVector(new Object[] {}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), new DoubleVector(new double[] {0.0, 0.0, 0.0}), 0, 1, new DoubleVector(new double[] {0.0}));

    planeSection_0.setPresentationName("Velocitysectionz");

    Units units_0 = 
      ((Units) simulation_0.getUnitsManager().getObject("m"));

    planeSection_0.getOriginCoordinate().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {0.0, 0.0, 0.005}));

    planeSection_0.getInputParts().setQuery(null);

    planeSection_0.getInputParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Region.class))), Query.STANDARD_MODIFIERS));
   
    simulation_0.getSceneManager().createEmptyScene("Scene");

    Scene scene_0 = 
      simulation_0.getSceneManager().getScene("Scene 1");

    scene_0.initializeAndWait();

    SceneUpdate sceneUpdate_0 = 
      scene_0.getSceneUpdate();

    scene_0.resetCamera();

    scene_0.close();
	
	scene_0.setPresentationName("velocity_z");

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

    VectorDisplayer vectorDisplayer_0 = 
      scene_0.getDisplayerManager().createVectorDisplayer("Vector");

    vectorDisplayer_0.initialize();

    Legend legend_0 = 
      vectorDisplayer_0.getLegend();

    BlueRedLookupTable blueRedLookupTable_0 = 
      ((BlueRedLookupTable) simulation_0.get(LookupTableManager.class).getObject("blue-red"));

    legend_0.setLookupTable(blueRedLookupTable_0);

    vectorDisplayer_0.setDisplayMode(VectorDisplayMode.VECTOR_DISPLAY_MODE_LIC);

    vectorDisplayer_0.getInputParts().setQuery(null);

    vectorDisplayer_0.getInputParts().setObjects(planeSection_0);

    SymmetricRepeat symmetricRepeat_0 = 
      ((SymmetricRepeat) simulation_0.getTransformManager().getObject("Domain.Symmetry 1"));

    vectorDisplayer_0.setVisTransform(symmetricRepeat_0);

    CurrentView currentView_0 = 
      scene_0.getCurrentView();

    currentView_0.setInput(new DoubleVector(new double[] {-5.0, -3.5, 0.001}), new DoubleVector(new double[] {-5.0, -3.5, 59.51331987061893}), new DoubleVector(new double[] {0.0, 1.0, 0.0}), 15.535833005787202, 1, 30.0);

    vectorDisplayer_0.getVectorDisplayQuantity().setRange(new DoubleVector(new double[] {0.0, 30.0}));

  PrimitiveFieldFunction primitiveFieldFunction_0 = 
      ((PrimitiveFieldFunction) simulation_0.getFieldFunctionManager().getFunction("Velocity"));

  vectorDisplayer_0.getVectorDisplayQuantity().setFieldFunction(primitiveFieldFunction_0);
	
	legend_0.setLabelFormat("%-2.2g"); 
	
	LICSettings lICSettings_0 = 
      vectorDisplayer_0.getLICSettings();
	  
	 lICSettings_0.setNumberOfSteps(31);
	  
	 lICSettings_0.setStepSize(0.6); 

	legend_0.setHeight(0.3);
	
	legend_0.setNumberOfLabels(3);
	
	legend_0.updateLayout(new DoubleVector(new double[] {0.8644417568427749, 0.17730351437699726}), 0.029999999999999805, 0.5999999999999999, 1);
	
	legend_0.setTextColor(new DoubleVector(new double[] {1.0, 1.0, 1.0}));
	
	legend_0.setWidth(0.02);
	
	legend_0.setShadow(true);
	
	legend_0.setWidth(0.02);
	
	legend_0.setFontString("Lucida Sans-Bold");
	
	legend_0.setTextPosition(LegendTextPosition.LEGEND_POSITION_FOLLOWING);
	
	legend_0.setTitleHeight(0.023);
	
	legend_0.setLabelHeight(0.018);
	
	legend_0.updateLayout(new DoubleVector(new double[] {0.96, 0.52}), 0.02, 0.35, 1);
	
	vectorDisplayer_0.getVectorDisplayQuantity().setClip(ClipMode.NONE);
	
	ImageAnnotation2D imageAnnotation2D_0 = 
      ((ImageAnnotation2D) simulation_0.getAnnotationManager().getObject("fst_black")); 
	  
	LogoAnnotation logoAnnotation_0 = 
      ((LogoAnnotation) simulation_0.getAnnotationManager().getObject("Logo"));

    scene_0.getAnnotationPropManager().removePropsForAnnotations(logoAnnotation_0);
	 
	 FixedAspectAnnotationProp fixedAspectAnnotationProp_0 = 
      (FixedAspectAnnotationProp) scene_0.getAnnotationPropManager().createPropForAnnotation(imageAnnotation2D_0);
	  
	fixedAspectAnnotationProp_0.setHeight(0.075);
	
	fixedAspectAnnotationProp_0.setPosition(new DoubleVector(new double[] {0.905, 0.03, 0.0}));

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
	
	SimpleAnnotation simpleAnnotation_0 = 
      ((SimpleAnnotation) simulation_0.getAnnotationManager().getObject("Plane Coordinate"));
	  
	FixedAspectAnnotationProp fixedAspectAnnotationProp_1 = 
      (FixedAspectAnnotationProp) scene_0.getAnnotationPropManager().createPropForAnnotation(simpleAnnotation_0);
	  
	simpleAnnotation_0.setText("z = 5 mm");

  fixedAspectAnnotationProp_1.setHeight(0.035);
	
	fixedAspectAnnotationProp_1.setPosition(new DoubleVector(new double[] {0.01, 0.01, 0.0}));

    currentView_0.setInput(new DoubleVector(new double[] {-0.8212206650667956, -0.006731317869260345, 0.05473061000198243}), new DoubleVector(new double[] {-0.6097271417765704, -0.4641713416308355, 64.04338352580567}), new DoubleVector(new double[] {0, 1, 0}), 1.1363084114937156, 1, 30.0);																																						
	
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\Vz\\velocity0 - z = 5 mm.jpg"), 1, 1920, 1080, true, false);
	
	for (int i = 1; i < 65; i++) { 

    planeSection_0.getOriginCoordinate().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {0.0, 0.0, 0.005+i*0.010}));
	
	vectorDisplayer_0.getVectorDisplayQuantity().setRange(new DoubleVector(new double[] {0.0, 30.0}));
	
	double coordinate = 5+i*10; 
	
	simpleAnnotation_0.setText("z = "+ coordinate +" mm");

	currentView_0.setInput(new DoubleVector(new double[] {-0.8212206650667956, -0.006731317869260345, 0.05473061000198243}), new DoubleVector(new double[] {-0.6097271417765704, -0.4641713416308355, 64.04338352580567}), new DoubleVector(new double[] {1.1812536908442732E-5, 0.9999744506258504, 0.007148283429787951}), 1.1363084114937156, 1, 30.0);
	
	scene_0.printAndWait(resolvePath("..\\Post_Processing\\Vz\\velocity"+ i +" - z = "+ coordinate +" mm.jpg"), 1, 1920, 1080, true, false);
	
	}

    
  }
}
