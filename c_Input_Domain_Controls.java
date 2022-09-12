// STAR-CCM+ macro: c_Input_Domain_Controls.java
// Written by STAR-CCM+ 14.04.011
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;
import star.vis.*;
import star.base.query.*;
import star.cadmodeler.*;
import star.meshing.*;

public class c_Input_Domain_Controls extends StarMacro {

  public void execute() {
    execute1();
    execute2();
    execute3();
    execute0();
    execute10();
  }
  /// CAR CONFIGURATION (MUST BE CHANGED)
                           																	 						   //Units
	double V = 15.0;									//Car Velocity  														m.s-1

	double Vi = V*0.8;								//Initial Velocity  													m.s-1
	
	double CoGZ = 0.302;							//Height of Center of Gravity (for Pressure Distribution)				meters

  int Front_Ride_Height = 40;

  int Rear_Ride_Height = 60;

  String Description = "Simplified Macro Random Text";

  String Type = "Straight";

  private void execute10() {

    Simulation simulation_0 = 
      getActiveSimulation();

  SimpleAnnotation simpleAnnotation_0 = 
      simulation_0.getAnnotationManager().createSimpleAnnotation();

    simpleAnnotation_0.setPresentationName("FRH");

    simpleAnnotation_0.setColor(new DoubleVector(new double[] {1.0, 1.0, 1.0}));

    simpleAnnotation_0.setText("FRH = "+Front_Ride_Height+" mm");

    simpleAnnotation_0.setFontString("Dialog-Bold");

    SimpleAnnotation simpleAnnotation_1 = 
      simulation_0.getAnnotationManager().createSimpleAnnotation();

    simpleAnnotation_1.copyProperties(simpleAnnotation_0);

    simpleAnnotation_1.setPresentationName("RRH");

    simpleAnnotation_1.setText("RRH = "+Rear_Ride_Height+" mm");

    simpleAnnotation_1.setFontString("Dialog-Bold");

    SimpleAnnotation simpleAnnotation_2 = 
      simulation_0.getAnnotationManager().createSimpleAnnotation();

    simpleAnnotation_2.copyProperties(simpleAnnotation_0);

    simpleAnnotation_2.setPresentationName("Description");

    simpleAnnotation_2.setText(Description);

    simpleAnnotation_2.setFontString("Dialog-Bold");

    SimpleAnnotation simpleAnnotation_3 = 
      simulation_0.getAnnotationManager().createSimpleAnnotation();

    simpleAnnotation_3.copyProperties(simpleAnnotation_0);

    simpleAnnotation_3.setPresentationName("Sim_Type");

    simpleAnnotation_3.setText("Sim. Type - "+Type);

    simpleAnnotation_3.setFontString("Dialog-Bold");

    SimpleAnnotation simpleAnnotation_4 = 
      simulation_0.getAnnotationManager().createSimpleAnnotation();

    simpleAnnotation_4.copyProperties(simpleAnnotation_0);

    simpleAnnotation_4.setPresentationName("Velocity");

    simpleAnnotation_4.setText("Car Velocity = "+V+" m/s");

    simpleAnnotation_4.setFontString("Dialog-Bold");

}
  private void execute1() {

    Simulation simulation_0 = 
      getActiveSimulation();

    CadModel cadModel_20 = 
      simulation_0.get(SolidModelManager.class).createSolidModel();

    CanonicalSketchPlane canonicalSketchPlane_200 = 
      ((CanonicalSketchPlane) cadModel_20.getFeature("ZX"));

    Sketch sketch_1 = 
      cadModel_20.getFeatureManager().createSketch(canonicalSketchPlane_200);

    cadModel_20.getFeatureManager().startSketchEdit(sketch_1);

    Units units_0 = 
      simulation_0.getUnitsManager().getPreferredUnits(new IntVector(new int[] {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));

    LineSketchPrimitive lineSketchPrimitive_0 = 
      sketch_1.createLine(new DoubleVector(new double[] {2.5, 1.9}), new DoubleVector(new double[] {1.0, 1.9}));

    PointSketchPrimitive pointSketchPrimitive_0 = 
      ((PointSketchPrimitive) sketch_1.getSketchPrimitive("Point 2"));

    LineSketchPrimitive lineSketchPrimitive_1 = 
      sketch_1.createLine(pointSketchPrimitive_0, new DoubleVector(new double[] {1, -0.25}));

    PointSketchPrimitive pointSketchPrimitive_1 = 
      ((PointSketchPrimitive) sketch_1.getSketchPrimitive("Point 3"));

    LineSketchPrimitive lineSketchPrimitive_2 = 
      sketch_1.createLine(pointSketchPrimitive_1, new DoubleVector(new double[] {2.5, -2.6}));

    PointSketchPrimitive pointSketchPrimitive_2 = 
      ((PointSketchPrimitive) sketch_1.getSketchPrimitive("Point 4"));

    PointSketchPrimitive pointSketchPrimitive_3 = 
      ((PointSketchPrimitive) sketch_1.getSketchPrimitive("Point 1"));

    LineSketchPrimitive lineSketchPrimitive_3 = 
      sketch_1.createLine(pointSketchPrimitive_2, pointSketchPrimitive_3);

    sketch_1.markFeatureForEdit();

    cadModel_20.getFeatureManager().stopSketchEdit(sketch_1, true);

    sketch_1.setIsUptoDate(true);

    cadModel_20.getFeatureManager().rollForwardToEnd();

    ExtrusionMerge extrusionMerge_0 = 
      cadModel_20.getFeatureManager().createExtrusionMerge(sketch_1);

    extrusionMerge_0.setColorVector(new IntVector(new int[] {-16711681, -65536, -16776961, -16711936, -65536}));

    extrusionMerge_0.setDirectionOption(0);

    extrusionMerge_0.setExtrudedBodyTypeOption(0);

    extrusionMerge_0.getDistance().setValue(-1.5);

    extrusionMerge_0.getDistanceAsymmetric().setValue(-0.1);

    extrusionMerge_0.setDistanceOption(2);

    extrusionMerge_0.setCoordinateSystemOption(0);

    extrusionMerge_0.setDraftOption(0);

    LabCoordinateSystem labCoordinateSystem_0 = 
      simulation_0.getCoordinateSystemManager().getLabCoordinateSystem();

    extrusionMerge_0.setImportedCoordinateSystem(labCoordinateSystem_0);

    extrusionMerge_0.getDirectionAxis().setCoordinateSystem(labCoordinateSystem_0);

    extrusionMerge_0.getDirectionAxis().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {0.0, 0.0, 1.0}));

    extrusionMerge_0.setFace(null);

    extrusionMerge_0.setBody(null);

    extrusionMerge_0.setSketch(sketch_1);

    extrusionMerge_0.setPostOption(1);

    extrusionMerge_0.setExtrusionOption(0);

    BodyNameRefManager bodyNameRefManager_0 = 
      extrusionMerge_0.getInteractionBodies();

    bodyNameRefManager_0.setBodies(new NeoObjectVector(new Object[] {}));

    extrusionMerge_0.setInteractingSelectedBodies(false);

    extrusionMerge_0.markFeatureForEdit();

    cadModel_20.getFeatureManager().execute(extrusionMerge_0);

    simulation_0.get(SolidModelManager.class).endEditCadModel(cadModel_20);

    ExtrusionMerge extrusionMerge_3 = 
      ((ExtrusionMerge) cadModel_20.getFeature("Extrude 1"));

    Sketch sketch_4 = 
      ((Sketch) cadModel_20.getFeature("Sketch 1"));

    LineSketchPrimitive lineSketchPrimitive_6 = 
      ((LineSketchPrimitive) sketch_4.getSketchPrimitive("Line 1"));

    star.cadmodeler.Body cadmodelerBody_2 = 
      ((star.cadmodeler.Body) extrusionMerge_3.getBody(lineSketchPrimitive_6));

    cadModel_20.createParts(new NeoObjectVector(new Object[] {cadmodelerBody_2}), new NeoObjectVector(new Object[] {}), 1, "SharpEdges", 30.0, 2, true, 1.0E-5);

    SolidModelPart solidModelPart_2 = 
      ((SolidModelPart) simulation_0.get(SimulationPartManager.class).getPart("Body 1"));

    solidModelPart_2.setPresentationName("DB_Car_Minus");
}
    //
    private void execute2() {

    Simulation simulation_0 = 
      getActiveSimulation();

    CadModel cadModel_30 = 
      simulation_0.get(SolidModelManager.class).createSolidModel();

    CanonicalSketchPlane canonicalSketchPlane_20 = 
      ((CanonicalSketchPlane) cadModel_30.getFeature("ZX"));

    Sketch sketch_1 = 
      cadModel_30.getFeatureManager().createSketch(canonicalSketchPlane_20);

    cadModel_30.getFeatureManager().startSketchEdit(sketch_1);

    Units units_0 = 
      simulation_0.getUnitsManager().getPreferredUnits(new IntVector(new int[] {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));

    LineSketchPrimitive lineSketchPrimitive_0 = 
      sketch_1.createLine(new DoubleVector(new double[] {3.6, 2.6}), new DoubleVector(new double[] {1.3, 2.6}));

    PointSketchPrimitive pointSketchPrimitive_0 = 
      ((PointSketchPrimitive) sketch_1.getSketchPrimitive("Point 2"));

    LineSketchPrimitive lineSketchPrimitive_1 = 
      sketch_1.createLine(pointSketchPrimitive_0, new DoubleVector(new double[] {1.3, -0.04}));

    PointSketchPrimitive pointSketchPrimitive_1 = 
      ((PointSketchPrimitive) sketch_1.getSketchPrimitive("Point 3"));

    LineSketchPrimitive lineSketchPrimitive_2 = 
      sketch_1.createLine(pointSketchPrimitive_1, new DoubleVector(new double[] {3.6, -3.6}));

    PointSketchPrimitive pointSketchPrimitive_2 = 
      ((PointSketchPrimitive) sketch_1.getSketchPrimitive("Point 4"));

    PointSketchPrimitive pointSketchPrimitive_3 = 
      ((PointSketchPrimitive) sketch_1.getSketchPrimitive("Point 1"));

    LineSketchPrimitive lineSketchPrimitive_3 = 
      sketch_1.createLine(pointSketchPrimitive_2, pointSketchPrimitive_3);

    sketch_1.markFeatureForEdit();

    cadModel_30.getFeatureManager().stopSketchEdit(sketch_1, true);

    sketch_1.setIsUptoDate(true);

    cadModel_30.getFeatureManager().rollForwardToEnd();

    ExtrusionMerge extrusionMerge_0 = 
      cadModel_30.getFeatureManager().createExtrusionMerge(sketch_1);

    extrusionMerge_0.setColorVector(new IntVector(new int[] {-16711681, -65536, -16776961, -16711936, -65536}));

    extrusionMerge_0.setDirectionOption(0);

    extrusionMerge_0.setExtrudedBodyTypeOption(0);

    extrusionMerge_0.getDistance().setValue(-2.0);

    extrusionMerge_0.getDistanceAsymmetric().setValue(-0.1);

    extrusionMerge_0.setDistanceOption(2);

    extrusionMerge_0.setCoordinateSystemOption(0);

    extrusionMerge_0.setDraftOption(0);

    LabCoordinateSystem labCoordinateSystem_0 = 
      simulation_0.getCoordinateSystemManager().getLabCoordinateSystem();

    extrusionMerge_0.setImportedCoordinateSystem(labCoordinateSystem_0);

    extrusionMerge_0.getDirectionAxis().setCoordinateSystem(labCoordinateSystem_0);

    extrusionMerge_0.getDirectionAxis().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {0.0, 0.0, 1.0}));

    extrusionMerge_0.setFace(null);

    extrusionMerge_0.setBody(null);

    extrusionMerge_0.setSketch(sketch_1);

    extrusionMerge_0.setPostOption(1);

    extrusionMerge_0.setExtrusionOption(0);

    BodyNameRefManager bodyNameRefManager_0 = 
      extrusionMerge_0.getInteractionBodies();

    bodyNameRefManager_0.setBodies(new NeoObjectVector(new Object[] {}));

    extrusionMerge_0.setInteractingSelectedBodies(false);

    extrusionMerge_0.markFeatureForEdit();

    cadModel_30.getFeatureManager().execute(extrusionMerge_0);

    simulation_0.get(SolidModelManager.class).endEditCadModel(cadModel_30);

    ExtrusionMerge extrusionMerge_3 = 
      ((ExtrusionMerge) cadModel_30.getFeature("Extrude 1"));

    Sketch sketch_4 = 
      ((Sketch) cadModel_30.getFeature("Sketch 1"));

    LineSketchPrimitive lineSketchPrimitive_6 = 
      ((LineSketchPrimitive) sketch_4.getSketchPrimitive("Line 1"));

    star.cadmodeler.Body cadmodelerBody_2 = 
      ((star.cadmodeler.Body) extrusionMerge_3.getBody(lineSketchPrimitive_6));

    cadModel_30.createParts(new NeoObjectVector(new Object[] {cadmodelerBody_2}), new NeoObjectVector(new Object[] {}), 1, "SharpEdges", 30.0, 2, true, 1.0E-5);

    SolidModelPart solidModelPart_2 = 
      ((SolidModelPart) simulation_0.get(SimulationPartManager.class).getPart("Body 1"));

    solidModelPart_2.setPresentationName("DB_Close_Minus");
}

    private void execute3() {

    Simulation simulation_0 = 
      getActiveSimulation();

    CadModel cadModel_2 = 
      simulation_0.get(SolidModelManager.class).createSolidModel();

    CanonicalSketchPlane canonicalSketchPlane_0 = 
      ((CanonicalSketchPlane) cadModel_2.getFeature("XY"));

    Sketch sketch_0 = 
      cadModel_2.getFeatureManager().createSketch(canonicalSketchPlane_0);

    cadModel_2.getFeatureManager().startSketchEdit(sketch_0);

    Units units_0 = 
      simulation_0.getUnitsManager().getPreferredUnits(new IntVector(new int[] {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));

    LineSketchPrimitive lineSketchPrimitive_0 = 
      sketch_0.createLine(new DoubleVector(new double[] {-1.05, 0.1}), new DoubleVector(new double[] {-0.2, 0.1}));

    PointSketchPrimitive pointSketchPrimitive_0 = 
      ((PointSketchPrimitive) sketch_0.getSketchPrimitive("Point 2"));

    LineSketchPrimitive lineSketchPrimitive_1 = 
      sketch_0.createLine(pointSketchPrimitive_0, new DoubleVector(new double[] {-0.2, -0.475}));

    PointSketchPrimitive pointSketchPrimitive_1 = 
      ((PointSketchPrimitive) sketch_0.getSketchPrimitive("Point 3"));

    LineSketchPrimitive lineSketchPrimitive_2 = 
      sketch_0.createLine(pointSketchPrimitive_1, new DoubleVector(new double[] {-0.3, -0.475}));

    PointSketchPrimitive pointSketchPrimitive_2 = 
      ((PointSketchPrimitive) sketch_0.getSketchPrimitive("Point 4"));

    LineSketchPrimitive lineSketchPrimitive_3 = 
      sketch_0.createLine(pointSketchPrimitive_2, new DoubleVector(new double[] {-0.3, -0.75}));

    PointSketchPrimitive pointSketchPrimitive_3 = 
      ((PointSketchPrimitive) sketch_0.getSketchPrimitive("Point 5"));

    LineSketchPrimitive lineSketchPrimitive_4 = 
      sketch_0.createLine(pointSketchPrimitive_3, new DoubleVector(new double[] {-1.05, -0.75}));

    PointSketchPrimitive pointSketchPrimitive_4 = 
      ((PointSketchPrimitive) sketch_0.getSketchPrimitive("Point 6"));

    PointSketchPrimitive pointSketchPrimitive_5 = 
      ((PointSketchPrimitive) sketch_0.getSketchPrimitive("Point 1"));

    LineSketchPrimitive lineSketchPrimitive_5 = 
      sketch_0.createLine(pointSketchPrimitive_4, pointSketchPrimitive_5);

    sketch_0.markFeatureForEdit();

    cadModel_2.getFeatureManager().stopSketchEdit(sketch_0, true);

    sketch_0.setIsUptoDate(true);

    cadModel_2.getFeatureManager().rollForwardToEnd();

    Sketch sketch_1 = 
      cadModel_2.getFeatureManager().createSketch(canonicalSketchPlane_0);

    cadModel_2.getFeatureManager().startSketchEdit(sketch_1);

    sketch_1.createRectangle(new DoubleVector(new double[] {-0.2, 0.1}), new DoubleVector(new double[] {0.24, -0.475}));

    sketch_1.markFeatureForEdit();

    cadModel_2.getFeatureManager().stopSketchEdit(sketch_1, true);

    sketch_1.setIsUptoDate(true);

    cadModel_2.getFeatureManager().rollForwardToEnd();

    Sketch sketch_2 = 
      cadModel_2.getFeatureManager().createSketch(canonicalSketchPlane_0);

    cadModel_2.getFeatureManager().startSketchEdit(sketch_2);

    LineSketchPrimitive lineSketchPrimitive_6 = 
      sketch_2.createLine(new DoubleVector(new double[] {-1.55, 0.1}), new DoubleVector(new double[] {-1.05, 0.1}));

    PointSketchPrimitive pointSketchPrimitive_6 = 
      ((PointSketchPrimitive) sketch_2.getSketchPrimitive("Point 2"));

    LineSketchPrimitive lineSketchPrimitive_7 = 
      sketch_2.createLine(pointSketchPrimitive_6, new DoubleVector(new double[] {-1.05, -0.75}));

    PointSketchPrimitive pointSketchPrimitive_7 = 
      ((PointSketchPrimitive) sketch_2.getSketchPrimitive("Point 3"));

    LineSketchPrimitive lineSketchPrimitive_8 = 
      sketch_2.createLine(pointSketchPrimitive_7, new DoubleVector(new double[] {-1.275, -0.75}));

    PointSketchPrimitive pointSketchPrimitive_8 = 
      ((PointSketchPrimitive) sketch_2.getSketchPrimitive("Point 4"));

    LineSketchPrimitive lineSketchPrimitive_9 = 
      sketch_2.createLine(pointSketchPrimitive_8, new DoubleVector(new double[] {-1.275, -0.475}));

    PointSketchPrimitive pointSketchPrimitive_9 = 
      ((PointSketchPrimitive) sketch_2.getSketchPrimitive("Point 5"));

    LineSketchPrimitive lineSketchPrimitive_10 = 
      sketch_2.createLine(pointSketchPrimitive_9, new DoubleVector(new double[] {-1.55, -0.475}));

    PointSketchPrimitive pointSketchPrimitive_10 = 
      ((PointSketchPrimitive) sketch_2.getSketchPrimitive("Point 6"));

    PointSketchPrimitive pointSketchPrimitive_11 = 
      ((PointSketchPrimitive) sketch_2.getSketchPrimitive("Point 1"));

    LineSketchPrimitive lineSketchPrimitive_11 = 
      sketch_2.createLine(pointSketchPrimitive_10, pointSketchPrimitive_11);

    sketch_2.markFeatureForEdit();

    cadModel_2.getFeatureManager().stopSketchEdit(sketch_2, true);

    sketch_2.setIsUptoDate(true);

    cadModel_2.getFeatureManager().rollForwardToEnd();

    ExtrusionMerge extrusionMerge_0 = 
      cadModel_2.getFeatureManager().createExtrusionMerge(sketch_0);

    extrusionMerge_0.setColorVector(new IntVector(new int[] {-16711681, -65536, -16776961, -16711936, -65536}));

    extrusionMerge_0.setDirectionOption(0);

    extrusionMerge_0.setExtrudedBodyTypeOption(0);

    extrusionMerge_0.getDistance().setValue(0.325);

    extrusionMerge_0.setDistanceOption(2);

    extrusionMerge_0.setCoordinateSystemOption(0);

    extrusionMerge_0.setDraftOption(0);

    LabCoordinateSystem labCoordinateSystem_0 = 
      simulation_0.getCoordinateSystemManager().getLabCoordinateSystem();

    extrusionMerge_0.setImportedCoordinateSystem(labCoordinateSystem_0);

    extrusionMerge_0.getDirectionAxis().setCoordinateSystem(labCoordinateSystem_0);

    extrusionMerge_0.getDirectionAxis().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {0.0, 0.0, 1.0}));

    extrusionMerge_0.setFace(null);

    extrusionMerge_0.setBody(null);

    extrusionMerge_0.setSketch(sketch_0);

    extrusionMerge_0.setPostOption(1);

    extrusionMerge_0.setExtrusionOption(0);

    BodyNameRefManager bodyNameRefManager_0 = 
      extrusionMerge_0.getInteractionBodies();

    bodyNameRefManager_0.setBodies(new NeoObjectVector(new Object[] {}));

    extrusionMerge_0.setInteractingSelectedBodies(false);

    extrusionMerge_0.markFeatureForEdit();

    cadModel_2.getFeatureManager().execute(extrusionMerge_0);

    ExtrusionMerge extrusionMerge_1 = 
      cadModel_2.getFeatureManager().createExtrusionMerge(sketch_1);

    extrusionMerge_1.setColorVector(new IntVector(new int[] {-16711681, -65536, -16776961, -16711936, -65536}));

    extrusionMerge_1.setDirectionOption(0);

    extrusionMerge_1.setExtrudedBodyTypeOption(0);

    extrusionMerge_1.getDistance().setValue(0.43);

    extrusionMerge_1.setDistanceOption(2);

    extrusionMerge_1.setCoordinateSystemOption(0);

    extrusionMerge_1.setDraftOption(0);

    extrusionMerge_1.setImportedCoordinateSystem(labCoordinateSystem_0);

    extrusionMerge_1.getDirectionAxis().setCoordinateSystem(labCoordinateSystem_0);

    extrusionMerge_1.getDirectionAxis().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {0.0, 0.0, 1.0}));

    extrusionMerge_1.setFace(null);

    extrusionMerge_1.setBody(null);

    extrusionMerge_1.setSketch(sketch_1);

    extrusionMerge_1.setPostOption(1);

    extrusionMerge_1.setExtrusionOption(0);

    BodyNameRefManager bodyNameRefManager_1 = 
      extrusionMerge_1.getInteractionBodies();

    bodyNameRefManager_1.setBodies(new NeoObjectVector(new Object[] {}));

    extrusionMerge_1.setInteractingSelectedBodies(false);

    extrusionMerge_1.markFeatureForEdit();

    cadModel_2.getFeatureManager().execute(extrusionMerge_1);

    ExtrusionMerge extrusionMerge_2 = 
      cadModel_2.getFeatureManager().createExtrusionMerge(sketch_2);

    extrusionMerge_2.setColorVector(new IntVector(new int[] {-16711681, -65536, -16776961, -16711936, -65536}));

    extrusionMerge_2.setDirectionOption(0);

    extrusionMerge_2.setExtrudedBodyTypeOption(0);

    extrusionMerge_2.getDistance().setValue(0.5);

    extrusionMerge_2.setDistanceOption(2);

    extrusionMerge_2.setCoordinateSystemOption(0);

    extrusionMerge_2.setDraftOption(0);

    extrusionMerge_2.setImportedCoordinateSystem(labCoordinateSystem_0);

    extrusionMerge_2.getDirectionAxis().setCoordinateSystem(labCoordinateSystem_0);

    extrusionMerge_2.getDirectionAxis().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {0.0, 0.0, 1.0}));

    extrusionMerge_2.setFace(null);

    extrusionMerge_2.setBody(null);

    extrusionMerge_2.setSketch(sketch_2);

    extrusionMerge_2.setPostOption(1);

    extrusionMerge_2.setExtrusionOption(0);

    BodyNameRefManager bodyNameRefManager_2 = 
      extrusionMerge_2.getInteractionBodies();

    bodyNameRefManager_2.setBodies(new NeoObjectVector(new Object[] {}));

    extrusionMerge_2.setInteractingSelectedBodies(false);

    extrusionMerge_2.markFeatureForEdit();

    cadModel_2.getFeatureManager().rollBack(sketch_0);

    cadModel_2.getFeatureManager().startSketchEdit(sketch_0);

    cadModel_2.getFeatureManager().stopSketchEdit(sketch_0, false);

    cadModel_2.getFeatureManager().markDependentNotUptodate(sketch_0);

    cadModel_2.getFeatureManager().rollForwardToEnd();

    ExtrusionMerge extrusionMerge_20 = 
      ((ExtrusionMerge) cadModel_2.getFeature("Extrude 3"));

    star.cadmodeler.Body cadmodelerBody_0 = 
      ((star.cadmodeler.Body) extrusionMerge_20.getBodyByLocation(new DoubleVector(new double[] {-1.05, -0.26246295, 0.399624725})));

    simulation_0.get(SolidModelManager.class).endEditCadModel(cadModel_2);

    cadModel_2.createParts(new NeoObjectVector(new Object[] {cadmodelerBody_0}), new NeoObjectVector(new Object[] {}), 1, "SharpEdges", 30.0, 2, true, 1.0E-5);

    SolidModelPart solidModelPart_1 = 
      ((SolidModelPart) simulation_0.get(SimulationPartManager.class).getPart("Body 3"));

    solidModelPart_1.setPresentationName("DB_Diff");
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();
	  
	UserFieldFunction userFieldFunction_0 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();

    userFieldFunction_0.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);

    userFieldFunction_0.setPresentationName("Car-Velocity");

    userFieldFunction_0.setFunctionName("Car-Velocity");

    userFieldFunction_0.setDefinition(Double.toString(V));

    UserFieldFunction userFieldFunction_2 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();

    userFieldFunction_2.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);

    userFieldFunction_2.setPresentationName("Initial-Velocity");

    userFieldFunction_2.setFunctionName("Initial-Velocity");

    userFieldFunction_2.setDefinition(Double.toString(Vi));
	
	UserFieldFunction userFieldFunction_1 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();

    userFieldFunction_1.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);

    userFieldFunction_1.setPresentationName("CoGZ");

    userFieldFunction_1.setFunctionName("CoGZ");

    userFieldFunction_1.setDefinition(Double.toString(CoGZ));

    Units units_0 = 
      simulation_0.getUnitsManager().getPreferredUnits(new IntVector(new int[] {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));

    MeshPartFactory meshPartFactory_0 = 
      simulation_0.get(MeshPartFactory.class);

    SimpleBlockPart simpleBlockPart_0 = 
      meshPartFactory_0.createNewBlockPart(simulation_0.get(SimulationPartManager.class));

    simpleBlockPart_0.setDoNotRetessellate(true);

    LabCoordinateSystem labCoordinateSystem_0 = 
      simulation_0.getCoordinateSystemManager().getLabCoordinateSystem();

    simpleBlockPart_0.setCoordinateSystem(labCoordinateSystem_0);

    simpleBlockPart_0.getCorner1().setCoordinateSystem(labCoordinateSystem_0);

    simpleBlockPart_0.getCorner1().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {-1.1, -1.1, 0.0}));

    simpleBlockPart_0.getCorner2().setCoordinateSystem(labCoordinateSystem_0);

    simpleBlockPart_0.getCorner2().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {1.2, 0.0, 1.1}));

    simpleBlockPart_0.rebuildSimpleShapePart();

    simpleBlockPart_0.setDoNotRetessellate(false);

    SimpleBlockPart simpleBlockPart_1 = 
      meshPartFactory_0.createNewBlockPart(simulation_0.get(SimulationPartManager.class));

    simpleBlockPart_1.setDoNotRetessellate(true);

    simpleBlockPart_1.setCoordinateSystem(labCoordinateSystem_0);

    simpleBlockPart_1.getCorner1().setCoordinateSystem(labCoordinateSystem_0);

    simpleBlockPart_1.getCorner1().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {-13.0, -2.0, 0.0}));

    simpleBlockPart_1.getCorner2().setCoordinateSystem(labCoordinateSystem_0);

    simpleBlockPart_1.getCorner2().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {2.2, 0.0, 2.0}));

    simpleBlockPart_1.rebuildSimpleShapePart();

    simpleBlockPart_1.setDoNotRetessellate(false);

    SimpleBlockPart simpleBlockPart_2 = 
      meshPartFactory_0.createNewBlockPart(simulation_0.get(SimulationPartManager.class));

    simpleBlockPart_2.setDoNotRetessellate(true);

    simpleBlockPart_2.setCoordinateSystem(labCoordinateSystem_0);

    simpleBlockPart_2.getCorner1().setCoordinateSystem(labCoordinateSystem_0);

    simpleBlockPart_2.getCorner1().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {-13, -5.0, 0.0}));

    simpleBlockPart_2.getCorner2().setCoordinateSystem(labCoordinateSystem_0);

    simpleBlockPart_2.getCorner2().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {5.0, 0.0, 5.0}));

    simpleBlockPart_2.rebuildSimpleShapePart();

    simpleBlockPart_2.setDoNotRetessellate(false);

    SimpleBlockPart simpleBlockPart_3 = 
      meshPartFactory_0.createNewBlockPart(simulation_0.get(SimulationPartManager.class));

    simpleBlockPart_3.setDoNotRetessellate(true);

    simpleBlockPart_3.setCoordinateSystem(labCoordinateSystem_0);

    simpleBlockPart_3.getCorner1().setCoordinateSystem(labCoordinateSystem_0);

    simpleBlockPart_3.getCorner1().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {-1.2, -1.2, 0.0}));

    simpleBlockPart_3.getCorner2().setCoordinateSystem(labCoordinateSystem_0);

    simpleBlockPart_3.getCorner2().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {1.3, 0.0, 1.2}));

    simpleBlockPart_3.rebuildSimpleShapePart();

    simpleBlockPart_3.setDoNotRetessellate(false);

    SimpleBlockPart simpleBlockPart_4 = 
      meshPartFactory_0.createNewBlockPart(simulation_0.get(SimulationPartManager.class));

    simpleBlockPart_4.setDoNotRetessellate(true);

    simpleBlockPart_4.setCoordinateSystem(labCoordinateSystem_0);

    simpleBlockPart_4.getCorner1().setCoordinateSystem(labCoordinateSystem_0);

    simpleBlockPart_4.getCorner1().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {0.24, -0.8, -0.1}));

    simpleBlockPart_4.getCorner2().setCoordinateSystem(labCoordinateSystem_0);

    simpleBlockPart_4.getCorner2().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {1.0, 0.1, 0.43}));

    simpleBlockPart_4.rebuildSimpleShapePart();

    simpleBlockPart_4.setDoNotRetessellate(false);

    simpleBlockPart_0.setPresentationName("DB_Car");

    simpleBlockPart_1.setPresentationName("DB_Wake");

    simpleBlockPart_2.setPresentationName("Domain");

    simpleBlockPart_3.setPresentationName("DB_Close");

    simpleBlockPart_4.setPresentationName("DB_FW");

    MeshActionManager meshActionManager_0 = 
      simulation_0.get(MeshActionManager.class);

    SolidModelPart solidModelPart_100 = 
      ((SolidModelPart) simulation_0.get(SimulationPartManager.class).getPart("DB_Car_Minus"));

    CadPart cadPart_100 = 
      (CadPart) meshActionManager_0.subtractParts(new NeoObjectVector(new Object[] {simpleBlockPart_0, solidModelPart_100}), simpleBlockPart_0, "CAD");

    cadPart_100.setPresentationName("DB_Car_New");

    SolidModelPart solidModelPart_101 = 
      ((SolidModelPart) simulation_0.get(SimulationPartManager.class).getPart("DB_Close_Minus"));

    CadPart cadPart_101 = 
      (CadPart) meshActionManager_0.subtractParts(new NeoObjectVector(new Object[] {simpleBlockPart_3, solidModelPart_101}), simpleBlockPart_3, "CAD");

    cadPart_101.setPresentationName("DB_Close_New");

    Scene scene_0 = 
      simulation_0.getSceneManager().getScene("Geometry Scene 1");

    scene_0.initializeAndWait();

    PartDisplayer partDisplayer_0 = 
      ((PartDisplayer) scene_0.getDisplayerManager().getDisplayer("Outline 1"));

    partDisplayer_0.initialize();

    PartDisplayer partDisplayer_1 = 
      ((PartDisplayer) scene_0.getDisplayerManager().getDisplayer("Geometry 1"));

    partDisplayer_1.initialize();

    SceneUpdate sceneUpdate_0 = 
      scene_0.getSceneUpdate();

    HardcopyProperties hardcopyProperties_0 = 
      sceneUpdate_0.getHardcopyProperties();

    hardcopyProperties_0.setCurrentResolutionWidth(924);

    hardcopyProperties_0.setCurrentResolutionHeight(538);

    scene_0.resetCamera();

    scene_0.setPresentationName("Domain");

    scene_0.getDisplayerManager().deleteDisplayers(new NeoObjectVector(new Object[] {partDisplayer_0}));

    scene_0.close();

    CurrentView currentView_0 = 
      scene_0.getCurrentView();

    currentView_0.setInput(new DoubleVector(new double[] {-3.998094923133972, -2.3856210246701437, 2.6847117861289793}), new DoubleVector(new double[] {-3.998094923133972, -42.414330875645675, 2.6847117861289793}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), 15.79645792773036, 0, 30.0);

    partDisplayer_1.setOpacity(0.5);

    currentView_0.setInput(new DoubleVector(new double[] {-3.998094923133972, -2.3856210246701437, 2.6847117861289793}), new DoubleVector(new double[] {-3.998094923133972, -42.414330875645675, 2.6847117861289793}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), 15.79645792773036, 0, 30.0);

    partDisplayer_1.setSurface(false);

    partDisplayer_1.setSurface(true);

    partDisplayer_1.setOutline(false);

    PartDisplayer partDisplayer_2 = 
      scene_0.getDisplayerManager().createPartDisplayer("Geometry", -1, 4);

    partDisplayer_2.initialize();

    CompositePart compositePart_0 = 
      ((CompositePart) simulation_0.get(SimulationPartManager.class).getPart("Car"));

        SolidModelPart solidModelPart_1 = 
      ((SolidModelPart) simulation_0.get(SimulationPartManager.class).getPart("DB_Diff"));

    partDisplayer_2.getInputParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new RelationshipPredicate(RelationshipOperator.DescendantOf, new IdentityPredicate(IdentityOperator.Equals, Arrays.<ClientServerObject>asList(compositePart_0))))))), Query.STANDARD_MODIFIERS));

    partDisplayer_1.getInputParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, PartSurface.class), new TypePredicate(TypeOperator.Is, PartCurve.class))), new RelationshipPredicate(RelationshipOperator.DescendantOf, new IdentityPredicate(IdentityOperator.Equals, Arrays.<ClientServerObject>asList(cadPart_100, cadPart_101, solidModelPart_1, simpleBlockPart_4, simpleBlockPart_1, simpleBlockPart_2))))), Query.STANDARD_MODIFIERS));

    partDisplayer_2.setOutline(false);

    partDisplayer_2.setSurface(true);

    currentView_0.setInput(new DoubleVector(new double[] {-13.9678481833325, 0.04383818142672169, 3.34428755434272}), new DoubleVector(new double[] {-13.9678481833325, -116.57030326275205, 3.34428755434272}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), 15.79645792773036, 0, 13.0);
  
    partDisplayer_1.setPresentationName("Domain");

    partDisplayer_2.setPresentationName("Car");

    partDisplayer_1.setOpacity(0.5);

    scene_0.close();

  }
}
