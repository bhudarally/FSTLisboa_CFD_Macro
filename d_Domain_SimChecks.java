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

public class d_Domain_SimChecks extends StarMacro {

  public void execute() {
    Domain();
    GeoRFChecks();
  }


  private void Domain() {

  Simulation simulation_0 = 
    getActiveSimulation();
   
  double Roll = simulation_0.getReportManager().getReport("Roll [deg]").getReportMonitorValue();
  double Yaw = simulation_0.getReportManager().getReport("Yaw [rad]").getReportMonitorValue();
  double CoGX = simulation_0.getReportManager().getReport("CoGX").getReportMonitorValue();
  double CoGZ = simulation_0.getReportManager().getReport("CoGZ").getReportMonitorValue();
  double CornerRadius = simulation_0.getReportManager().getReport("Corner Radius [m]").getReportMonitorValue();    
  
  Units units_0 = 
      simulation_0.getUnitsManager().getPreferredUnits(new IntVector(new int[] {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));
  Units units_1 = 
      ((Units) simulation_0.getUnitsManager().getObject("deg"));

  // Domain //
    
  CadModel cadModel_0 = 
    simulation_0.get(SolidModelManager.class).createSolidModel();
  cadModel_0.resetSystemOptions();
  cadModel_0.setPresentationName("Domain");

      CanonicalSketchPlane canonicalSketchPlane_0 = 
      ((CanonicalSketchPlane) cadModel_0.getFeature("YZ"));
    Sketch sketch_0 = 
      cadModel_0.getFeatureManager().createSketch(canonicalSketchPlane_0);
    cadModel_0.getFeatureManager().startSketchEdit(sketch_0);
  if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {  
  sketch_0.createRectangle(new DoubleVector(new double[] {-7.0, 0.0}), new DoubleVector(new double[] {0.0, 10.0}));
  } else if ( CornerRadius == 0 && ( Yaw != 0 || Roll != 0) ) { 
  sketch_0.createRectangle(new DoubleVector(new double[] {-7.0, 0.0}), new DoubleVector(new double[] {7.0, 10.0}));
  } else {
    sketch_0.createRectangle(new DoubleVector(new double[] {-7.0, 0.0}), new DoubleVector(new double[] {7.0, 10.0}));

    LineSketchPrimitive lineSketchPrimitive_0 = 
      sketch_0.createLine(new DoubleVector(new double[] {CornerRadius, 0.0}), new DoubleVector(new double[] {CornerRadius, 10.0}));
    sketch_0.createVerticalConstraint(lineSketchPrimitive_0);
    sketch_0.getProjectedSketchPrimitives(new NeoObjectVector(new Object[] {}), new NeoObjectVector(new Object[] {lineSketchPrimitive_0}), new NeoObjectVector(new Object[] {}), new NeoObjectVector(new Object[] {}), new NeoObjectVector(new Object[] {}));
    sketch_0.setConstructionState(new NeoObjectVector(new Object[] {lineSketchPrimitive_0}), true);
  }
  
    sketch_0.setIsUptoDate(true);
    sketch_0.markFeatureForEdit();
    cadModel_0.getFeatureManager().stopSketchEdit(sketch_0, true);
    cadModel_0.getFeatureManager().updateModelAfterFeatureEdited(sketch_0, null);

  LabCoordinateSystem labCoordinateSystem_0 = 
      simulation_0.getCoordinateSystemManager().getLabCoordinateSystem();
    
  CartesianCoordinateSystem cartesianCoordinateSystem_0 = 
      ((CartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("Air Orientation - Car"));

      if ( CornerRadius == 0) {
    
  double DistanceDomainFront = 10.81 ;
  double DistanceDomainBack = 39.19 ; 
    
  ExtrusionMerge extrusionMerge_0 = 
      cadModel_0.getFeatureManager().createExtrusionMerge(sketch_0);
  extrusionMerge_0.setDirectionOption(0);
    extrusionMerge_0.setExtrudedBodyTypeOption(0);
    extrusionMerge_0.getDistance().setValue(DistanceDomainFront);
    extrusionMerge_0.getDistance().setUnits(units_0);
    extrusionMerge_0.getDistanceAsymmetric().setValue(DistanceDomainBack);
    extrusionMerge_0.getDistanceAsymmetric().setUnits(units_0);
    extrusionMerge_0.setDistanceOption(2);
    extrusionMerge_0.setCoordinateSystemOption(0);
    extrusionMerge_0.setDraftOption(0);
    extrusionMerge_0.setImportedCoordinateSystem(labCoordinateSystem_0);
    extrusionMerge_0.getDirectionAxis().setCoordinateSystem(labCoordinateSystem_0);
    extrusionMerge_0.getDirectionAxis().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {0.0, 0.0, 1.0}));
    extrusionMerge_0.setFace(null);
    extrusionMerge_0.setBody(null);
    extrusionMerge_0.setSketch(sketch_0);
    extrusionMerge_0.setPostOption(1);
    extrusionMerge_0.setExtrusionOption(0);
    extrusionMerge_0.setInteractingBodies(new NeoObjectVector(new Object[] {}));
    extrusionMerge_0.setInteractingBodiesBodyGroups(new NeoObjectVector(new Object[] {}));
    extrusionMerge_0.setInteractingBodiesCadFilters(new NeoObjectVector(new Object[] {}));
    extrusionMerge_0.setInteractingSelectedBodies(false);
    cadModel_0.getFeatureManager().markDependentNotUptodate(extrusionMerge_0);
    extrusionMerge_0.markFeatureForEdit();
    cadModel_0.getFeatureManager().execute(extrusionMerge_0);}
  
    //{ // Half car Straight - Symmetric simulation
  
  if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) { 
  
  ExtrusionMerge extrusionMerge_0 = 
      ((ExtrusionMerge) cadModel_0.getFeature("Extrude 1"));
  
  LineSketchPrimitive lineSketchPrimitive_0 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 1"));
    Face face_0 = 
      ((Face) extrusionMerge_0.getSideFace(lineSketchPrimitive_0,"True"));
    cadModel_0.setFaceNameAttributes(new NeoObjectVector(new Object[] {face_0}), "Wall_Outer");

    LineSketchPrimitive lineSketchPrimitive_6 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 2"));
    Face face_1 = 
      ((Face) extrusionMerge_0.getSideFace(lineSketchPrimitive_6,"True"));
    cadModel_0.setFaceNameAttributes(new NeoObjectVector(new Object[] {face_1}), "Wall_Top");

    LineSketchPrimitive lineSketchPrimitive_5 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 3"));
    Face face_2 = 
      ((Face) extrusionMerge_0.getSideFace(lineSketchPrimitive_5,"True"));
    cadModel_0.setFaceNameAttributes(new NeoObjectVector(new Object[] {face_2}), "Wall_Inner");

    LineSketchPrimitive lineSketchPrimitive_7 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 4"));
    Face face_3 = 
      ((Face) extrusionMerge_0.getSideFace(lineSketchPrimitive_7,"True"));
    cadModel_0.setFaceNameAttributes(new NeoObjectVector(new Object[] {face_3}), "Ground");

    Face face_4 = 
      ((Face) extrusionMerge_0.getStartCapFace(lineSketchPrimitive_0));
    cadModel_0.setFaceNameAttributes(new NeoObjectVector(new Object[] {face_4}), "Outlet");

    Face face_5 = 
      ((Face) extrusionMerge_0.getEndCapFace(lineSketchPrimitive_0));
    cadModel_0.setFaceNameAttributes(new NeoObjectVector(new Object[] {face_5}), "Inlet");
  
   star.cadmodeler.Body cadmodelerBody_0 = 
      ((star.cadmodeler.Body) extrusionMerge_0.getBody(lineSketchPrimitive_0));
    cadmodelerBody_0.setPresentationName("Domain");
    cadModel_0.update();
    simulation_0.get(SolidModelManager.class).endEditCadModel(cadModel_0);
    cadModel_0.createParts(new NeoObjectVector(new Object[] {cadmodelerBody_0}), new NeoObjectVector(new Object[] {}), 1, "SharpEdges", 30.0, 3, false, 1.0E-5);
  SolidModelPart solidModelPart_0 = 
      ((SolidModelPart) simulation_0.get(SimulationPartManager.class).getPart("Domain"));
  solidModelPart_0.transposePartsToCsys(new NeoObjectVector(new Object[] {solidModelPart_0}), labCoordinateSystem_0, cartesianCoordinateSystem_0);
  }
  //}
    
    //{ // Full Car Domain Straight simulation
    
  else if ( CornerRadius == 0 && ( Yaw != 0 || Roll != 0) ) { 
   
  ExtrusionMerge extrusionMerge_0 = 
      ((ExtrusionMerge) cadModel_0.getFeature("Extrude 1"));
    
  LineSketchPrimitive lineSketchPrimitive_0 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 1"));
  Face face_0 = 
      ((Face) extrusionMerge_0.getSideFace(lineSketchPrimitive_0,"True"));
    cadModel_0.setFaceNameAttributes(new NeoObjectVector(new Object[] {face_0}), "Wall_Outer");
    
  LineSketchPrimitive lineSketchPrimitive_1 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 2"));
    Face face_1 = 
      ((Face) extrusionMerge_0.getSideFace(lineSketchPrimitive_1,"True"));
    cadModel_0.setFaceNameAttributes(new NeoObjectVector(new Object[] {face_1}), "Wall_Top"); 

  LineSketchPrimitive lineSketchPrimitive_2 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 3"));
    Face face_2 = 
      ((Face) extrusionMerge_0.getSideFace(lineSketchPrimitive_2,"True"));
    cadModel_0.setFaceNameAttributes(new NeoObjectVector(new Object[] {face_2}), "Wall_Inner");
    
  LineSketchPrimitive lineSketchPrimitive_3 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 4"));
    Face face_3 = 
      ((Face) extrusionMerge_0.getSideFace(lineSketchPrimitive_3,"True"));
    cadModel_0.setFaceNameAttributes(new NeoObjectVector(new Object[] {face_3}), "Ground");  
    
  Face face_4 = 
      ((Face) extrusionMerge_0.getStartCapFace(lineSketchPrimitive_0));
    cadModel_0.setFaceNameAttributes(new NeoObjectVector(new Object[] {face_4}), "Outlet"); 
    
    Face face_5 = 
      ((Face) extrusionMerge_0.getEndCapFace(lineSketchPrimitive_0));
    cadModel_0.setFaceNameAttributes(new NeoObjectVector(new Object[] {face_5}), "Inlet");
  
   star.cadmodeler.Body cadmodelerBody_0 = 
      ((star.cadmodeler.Body) extrusionMerge_0.getBody(lineSketchPrimitive_0));
    cadmodelerBody_0.setPresentationName("Domain");
    cadModel_0.update();
    simulation_0.get(SolidModelManager.class).endEditCadModel(cadModel_0);
    cadModel_0.createParts(new NeoObjectVector(new Object[] {cadmodelerBody_0}), new NeoObjectVector(new Object[] {}), 1, "SharpEdges", 30.0, 3, false, 1.0E-5);
  
  SolidModelPart solidModelPart_0 = 
      ((SolidModelPart) simulation_0.get(SimulationPartManager.class).getPart("Domain"));
  solidModelPart_0.transposePartsToCsys(new NeoObjectVector(new Object[] {solidModelPart_0}), labCoordinateSystem_0, cartesianCoordinateSystem_0);
  }
  
  //}
   
    //{ // Corner DOMAIN
   else { 
  double AngleDomainFront = 60 * 9.125 / CornerRadius ;
  double AngleDomainBack = 185 * 9.125 / CornerRadius ; 
  
  LineSketchPrimitive lineSketchPrimitive_0 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 5"));
  
    RevolveMerge revolveMerge_0 = 
      (RevolveMerge) cadModel_0.getFeatureManager().createRevolveMerge(sketch_0);
    revolveMerge_0.setCoordinateSystemSourceOption(0);
    revolveMerge_0.setImportedCoordinateSystem(labCoordinateSystem_0);
    revolveMerge_0.getSpecifiedAxisDirection().setCoordinateSystem(labCoordinateSystem_0);
    revolveMerge_0.getSpecifiedAxisPosition().setCoordinateSystem(labCoordinateSystem_0);
    revolveMerge_0.getAngle().setValue(AngleDomainFront);
    revolveMerge_0.getAngle().setUnits(units_1);
    revolveMerge_0.getAngleAsymmetric().setValue(AngleDomainBack);
    revolveMerge_0.getAngleAsymmetric().setUnits(units_1);
    revolveMerge_0.setAngleOption(2);
    revolveMerge_0.setRevolvedBodyTypeOption(0);
    revolveMerge_0.setDirectionOption(0);
    revolveMerge_0.setRotationAxisOption(1);
    revolveMerge_0.setCoordinateSystemSourceOption(1);
    revolveMerge_0.setAxis(lineSketchPrimitive_0);
    revolveMerge_0.setSketch(sketch_0);
    revolveMerge_0.setPostOption(1);
    revolveMerge_0.setInteractingBodies(new NeoObjectVector(new Object[] {}));
    revolveMerge_0.setInteractingBodiesBodyGroups(new NeoObjectVector(new Object[] {}));
    revolveMerge_0.setInteractingBodiesCadFilters(new NeoObjectVector(new Object[] {}));
    revolveMerge_0.setInteractingSelectedBodies(false);
    cadModel_0.getFeatureManager().markDependentNotUptodate(revolveMerge_0);
    revolveMerge_0.markFeatureForEdit();
    cadModel_0.getFeatureManager().execute(revolveMerge_0);
  
  // DOMAIN Corner FACES NAME ATTRIBUTION - INLET | OUTLET | GROUND | WALL_INNER | WALL_OUTER | WALL_TOP
  
  Sketch sketch_1 = 
      ((Sketch) cadModel_0.getFeature("Sketch 1"));
  LineSketchPrimitive lineSketchPrimitive_1 = 
      ((LineSketchPrimitive) sketch_1.getSketchPrimitive("Line 1"));
  Face face_0 = 
      ((Face) revolveMerge_0.getSideFace(lineSketchPrimitive_1,"True"));
  cadModel_0.setFaceNameAttributes(new NeoObjectVector(new Object[] {face_0}), "Wall_Outer", 135, 206, 250, 255);

  LineSketchPrimitive lineSketchPrimitive_2 = 
      ((LineSketchPrimitive) sketch_1.getSketchPrimitive("Line 2"));
  Face face_1 = 
      ((Face) revolveMerge_0.getSideFace(lineSketchPrimitive_2,"True"));
  cadModel_0.setFaceNameAttributes(new NeoObjectVector(new Object[] {face_1}), "Wall_Top", 135, 206, 250, 255);

  LineSketchPrimitive lineSketchPrimitive_3 = 
      ((LineSketchPrimitive) sketch_1.getSketchPrimitive("Line 3"));
  Face face_2 = 
      ((Face) revolveMerge_0.getSideFace(lineSketchPrimitive_3,"True"));
  cadModel_0.setFaceNameAttributes(new NeoObjectVector(new Object[] {face_2}), "Wall_Inner", 135, 206, 250, 255);
  
  LineSketchPrimitive lineSketchPrimitive_4 = 
      ((LineSketchPrimitive) sketch_1.getSketchPrimitive("Line 4"));
  Face face_3 = 
      ((Face) revolveMerge_0.getSideFace(lineSketchPrimitive_4,"True"));
  cadModel_0.setFaceNameAttributes(new NeoObjectVector(new Object[] {face_3}), "Ground", 135, 206, 250, 255);
  
  Face face_4 = 
      ((Face) revolveMerge_0.getStartCapFace(lineSketchPrimitive_1));
    cadModel_0.setFaceNameAttributes(new NeoObjectVector(new Object[] {face_4}), "Outlet", 135, 206, 250, 255);

    Face face_5 = 
      ((Face) revolveMerge_0.getEndCapFace(lineSketchPrimitive_1));
    cadModel_0.setFaceNameAttributes(new NeoObjectVector(new Object[] {face_5}), "Inlet", 135, 206, 250, 255);

    star.cadmodeler.Body cadmodelerBody_0 = 
      ((star.cadmodeler.Body) revolveMerge_0.getBody(lineSketchPrimitive_0));
    cadmodelerBody_0.setPresentationName("Domain");
    cadModel_0.update();
    simulation_0.get(SolidModelManager.class).endEditCadModel(cadModel_0);
    cadModel_0.createParts(new NeoObjectVector(new Object[] {cadmodelerBody_0}), new NeoObjectVector(new Object[] {}), 1, "SharpEdges", 30.0, 3, false, 1.0E-5);
  
  SolidModelPart solidModelPart_0 = 
      ((SolidModelPart) simulation_0.get(SimulationPartManager.class).getPart("Domain"));
  solidModelPart_0.transposePartsToCsys(new NeoObjectVector(new Object[] {solidModelPart_0}), labCoordinateSystem_0, cartesianCoordinateSystem_0);
}

  //{ // DB 2 WAKE
    
    CadModel cadModel_1 = 
      simulation_0.get(SolidModelManager.class).createSolidModel();
    cadModel_1.resetSystemOptions();
    cadModel_1.setPresentationName("DB_2_Wake");

    CanonicalSketchPlane canonicalSketchPlane_1 = 
      ((CanonicalSketchPlane) cadModel_1.getFeature("YZ"));
    Sketch sketch_2 = 
      cadModel_1.getFeatureManager().createSketch(canonicalSketchPlane_1);
    cadModel_1.getFeatureManager().startSketchEdit(sketch_2);
  
  if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) { 
  sketch_2.createRectangle(new DoubleVector(new double[] {-2.5, 0.0}), new DoubleVector(new double[] {0.0, 5.5}));
  } else if ( CornerRadius == 0 && ( Yaw != 0 || Roll != 0) ) { 
  sketch_2.createRectangle(new DoubleVector(new double[] {-2.5, 0.0}), new DoubleVector(new double[] {2.5, 5.5}));
  } else {
    sketch_2.createRectangle(new DoubleVector(new double[] {-2.5, 0.0}), new DoubleVector(new double[] {2.5, 5.5}));

    LineSketchPrimitive lineSketchPrimitive_5 = 
      sketch_2.createLine(new DoubleVector(new double[] {CornerRadius, 0.0}), new DoubleVector(new double[] {CornerRadius, 5.5}));
    sketch_2.createVerticalConstraint(lineSketchPrimitive_5);
    sketch_2.getProjectedSketchPrimitives(new NeoObjectVector(new Object[] {}), new NeoObjectVector(new Object[] {lineSketchPrimitive_5}), new NeoObjectVector(new Object[] {}), new NeoObjectVector(new Object[] {}), new NeoObjectVector(new Object[] {}));
    sketch_2.setConstructionState(new NeoObjectVector(new Object[] {lineSketchPrimitive_5}), true);
  }
  
    sketch_2.setIsUptoDate(true);
    sketch_2.markFeatureForEdit();
    cadModel_1.getFeatureManager().stopSketchEdit(sketch_2, true);
    cadModel_1.getFeatureManager().updateModelAfterFeatureEdited(sketch_2, null);
  
  if ( CornerRadius == 0) {
    
  double DistanceDB2Front = 2.81 ;
  double DistanceDB2Back = 39.19 ;  
    
  ExtrusionMerge extrusionMerge_1 = 
      cadModel_1.getFeatureManager().createExtrusionMerge(sketch_2);
  extrusionMerge_1.setDirectionOption(0);
    extrusionMerge_1.setExtrudedBodyTypeOption(0);
    extrusionMerge_1.getDistance().setValue(DistanceDB2Front);
    extrusionMerge_1.getDistance().setUnits(units_0);
    extrusionMerge_1.getDistanceAsymmetric().setValue(DistanceDB2Back);
    extrusionMerge_1.getDistanceAsymmetric().setUnits(units_0);
    extrusionMerge_1.setDistanceOption(2);
    extrusionMerge_1.setCoordinateSystemOption(0);
    extrusionMerge_1.setDraftOption(0);
    extrusionMerge_1.setImportedCoordinateSystem(labCoordinateSystem_0);
    extrusionMerge_1.getDirectionAxis().setCoordinateSystem(labCoordinateSystem_0);
    extrusionMerge_1.getDirectionAxis().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {0.0, 0.0, 1.0}));
    extrusionMerge_1.setFace(null);
    extrusionMerge_1.setBody(null);
    extrusionMerge_1.setSketch(sketch_2);
    extrusionMerge_1.setPostOption(1);
    extrusionMerge_1.setExtrusionOption(0);
    extrusionMerge_1.setInteractingBodies(new NeoObjectVector(new Object[] {}));
    extrusionMerge_1.setInteractingBodiesBodyGroups(new NeoObjectVector(new Object[] {}));
    extrusionMerge_1.setInteractingBodiesCadFilters(new NeoObjectVector(new Object[] {}));
    extrusionMerge_1.setInteractingSelectedBodies(false);
    cadModel_1.getFeatureManager().markDependentNotUptodate(extrusionMerge_1);
    extrusionMerge_1.markFeatureForEdit();
    cadModel_1.getFeatureManager().execute(extrusionMerge_1);
  
  LineSketchPrimitive lineSketchPrimitive_5 = 
      ((LineSketchPrimitive) sketch_2.getSketchPrimitive("Line 1"));
    
  star.cadmodeler.Body cadmodelerBody_1 = 
      ((star.cadmodeler.Body) extrusionMerge_1.getBody(lineSketchPrimitive_5));
    cadmodelerBody_1.setPresentationName("DB_2_Wake");
  cadModel_1.update();
    simulation_0.get(SolidModelManager.class).endEditCadModel(cadModel_1);
    cadModel_1.createParts(new NeoObjectVector(new Object[] {cadmodelerBody_1}), new NeoObjectVector(new Object[] {}), 1, "SharpEdges", 30.0, 3, false, 1.0E-5);
  
  SolidModelPart solidModelPart_1 = 
      ((SolidModelPart) simulation_0.get(SimulationPartManager.class).getPart("DB_2_Wake"));
  solidModelPart_1.transposePartsToCsys(new NeoObjectVector(new Object[] {solidModelPart_1}), labCoordinateSystem_0, cartesianCoordinateSystem_0);

  } else {  
  
  double AngleDB2Front = 30 * 9.125 / CornerRadius ;
  double AngleDB2Back = 185 * 9.125 / CornerRadius ;
  
  LineSketchPrimitive lineSketchPrimitive_5 = 
      ((LineSketchPrimitive) sketch_2.getSketchPrimitive("Line 5"));

    RevolveMerge revolveMerge_1 = 
      (RevolveMerge) cadModel_1.getFeatureManager().createRevolveMerge(sketch_2);
    revolveMerge_1.setCoordinateSystemSourceOption(0);
    revolveMerge_1.setImportedCoordinateSystem(labCoordinateSystem_0);
    revolveMerge_1.getSpecifiedAxisDirection().setCoordinateSystem(labCoordinateSystem_0);
    revolveMerge_1.getSpecifiedAxisPosition().setCoordinateSystem(labCoordinateSystem_0);
    revolveMerge_1.getAngle().setValue(AngleDB2Front);
    revolveMerge_1.getAngle().setUnits(units_1);
    revolveMerge_1.getAngleAsymmetric().setValue(AngleDB2Back);
    revolveMerge_1.getAngleAsymmetric().setUnits(units_1);
    revolveMerge_1.setAngleOption(2);
    revolveMerge_1.setRevolvedBodyTypeOption(0);
    revolveMerge_1.setDirectionOption(0);
    revolveMerge_1.setRotationAxisOption(1);
    revolveMerge_1.setCoordinateSystemSourceOption(1);
    revolveMerge_1.setAxis(lineSketchPrimitive_5);
    revolveMerge_1.setSketch(sketch_2);
    revolveMerge_1.setPostOption(1);
    revolveMerge_1.setInteractingBodies(new NeoObjectVector(new Object[] {}));
    revolveMerge_1.setInteractingBodiesBodyGroups(new NeoObjectVector(new Object[] {}));
    revolveMerge_1.setInteractingBodiesCadFilters(new NeoObjectVector(new Object[] {}));
    revolveMerge_1.setInteractingSelectedBodies(false);
    cadModel_1.getFeatureManager().markDependentNotUptodate(revolveMerge_1);
    revolveMerge_1.markFeatureForEdit();
    cadModel_1.getFeatureManager().execute(revolveMerge_1);

    star.cadmodeler.Body cadmodelerBody_1 = 
      ((star.cadmodeler.Body) revolveMerge_1.getBody(lineSketchPrimitive_5));
    cadmodelerBody_1.setPresentationName("DB_2_WAKE");
    cadModel_1.update();
    simulation_0.get(SolidModelManager.class).endEditCadModel(cadModel_1);
    cadModel_1.createParts(new NeoObjectVector(new Object[] {cadmodelerBody_1}), new NeoObjectVector(new Object[] {}), 1, "SharpEdges", 30.0, 3, false, 1.0E-5);
  
  SolidModelPart solidModelPart_1 = 
      ((SolidModelPart) simulation_0.get(SimulationPartManager.class).getPart("DB_2_WAKE"));
  solidModelPart_1.transposePartsToCsys(new NeoObjectVector(new Object[] {solidModelPart_1}), labCoordinateSystem_0, cartesianCoordinateSystem_0);
  }
  
  //}

  // DB 1 SUB //

     CadModel cadModel_30 = 
      simulation_0.get(SolidModelManager.class).createSolidModel();
    cadModel_30.resetSystemOptions();
    cadModel_30.setPresentationName("DB_1_Sub");

    CanonicalSketchPlane canonicalSketchPlane_30 = 
      ((CanonicalSketchPlane) cadModel_30.getFeature("ZX"));
    Sketch sketch_40 = 
      cadModel_30.getFeatureManager().createSketch(canonicalSketchPlane_30);
    cadModel_30.getFeatureManager().startSketchEdit(sketch_40);

    LineSketchPrimitive lineSketchPrimitive_70 = 
      sketch_40.createLine(new DoubleVector(new double[] {3.6, 2.6}), new DoubleVector(new double[] {0.9, 2.6}));
    PointSketchPrimitive pointSketchPrimitive_100 = 
      ((PointSketchPrimitive) sketch_40.getSketchPrimitive("Point 2"));
    LineSketchPrimitive lineSketchPrimitive_80 = 
      sketch_40.createLine(pointSketchPrimitive_100, new DoubleVector(new double[] {1.0, -0.04}));
    PointSketchPrimitive pointSketchPrimitive_10 = 
      ((PointSketchPrimitive) sketch_40.getSketchPrimitive("Point 3"));
    LineSketchPrimitive lineSketchPrimitive_90 = 
      sketch_40.createLine(pointSketchPrimitive_10, new DoubleVector(new double[] {3.6, -6.0}));
    PointSketchPrimitive pointSketchPrimitive_20 = 
      ((PointSketchPrimitive) sketch_40.getSketchPrimitive("Point 4"));
    PointSketchPrimitive pointSketchPrimitive_30 = 
      ((PointSketchPrimitive) sketch_40.getSketchPrimitive("Point 1"));
    LineSketchPrimitive lineSketchPrimitive_100 = 
      sketch_40.createLine(pointSketchPrimitive_20, pointSketchPrimitive_30);
    sketch_40.setIsUptoDate(true);
    sketch_40.markFeatureForEdit();
    cadModel_30.getFeatureManager().stopSketchEdit(sketch_40, true);
    cadModel_30.getFeatureManager().updateModelAfterFeatureEdited(sketch_40, null);

    ExtrusionMerge extrusionMerge_100 = 
      cadModel_30.getFeatureManager().createExtrusionMerge(sketch_40);
    extrusionMerge_100.setDirectionOption(0);
    extrusionMerge_100.setExtrudedBodyTypeOption(0);
    extrusionMerge_100.getDistance().setValue(2.0);
    extrusionMerge_100.getDistance().setUnits(units_0);
    extrusionMerge_100.setDistanceOption(1);
    extrusionMerge_100.setCoordinateSystemOption(0);
    extrusionMerge_100.setDraftOption(0);
    extrusionMerge_100.setImportedCoordinateSystem(labCoordinateSystem_0);
    extrusionMerge_100.getDirectionAxis().setCoordinateSystem(labCoordinateSystem_0);
    extrusionMerge_100.getDirectionAxis().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {0.0, 0.0, 1.0}));
    extrusionMerge_100.setFace(null);
    extrusionMerge_100.setBody(null);
    extrusionMerge_100.setSketch(sketch_40);
    extrusionMerge_100.setPostOption(1);
    extrusionMerge_100.setExtrusionOption(0);
    extrusionMerge_100.setInteractingBodies(new NeoObjectVector(new Object[] {}));
    extrusionMerge_100.setInteractingBodiesBodyGroups(new NeoObjectVector(new Object[] {}));
    extrusionMerge_100.setInteractingBodiesCadFilters(new NeoObjectVector(new Object[] {}));
    extrusionMerge_100.setInteractingSelectedBodies(false);
    cadModel_30.getFeatureManager().markDependentNotUptodate(extrusionMerge_100);
    extrusionMerge_100.markFeatureForEdit();
    cadModel_30.getFeatureManager().execute(extrusionMerge_100);

    star.cadmodeler.Body cadmodelerBody_30 = 
      ((star.cadmodeler.Body) extrusionMerge_100.getBody(lineSketchPrimitive_70));
    cadmodelerBody_30.setPresentationName("DB_1_Sub");
    cadModel_30.update();
    simulation_0.get(SolidModelManager.class).endEditCadModel(cadModel_30);
    cadModel_30.createParts(new NeoObjectVector(new Object[] {cadmodelerBody_30}), new NeoObjectVector(new Object[] {}), 1, "SharpEdges", 30.0, 3, false, 1.0E-5);

    //{ // DB 1 CLOSE
  
  CadModel cadModel_2 = 
      simulation_0.get(SolidModelManager.class).createSolidModel();
    cadModel_2.resetSystemOptions();
    cadModel_2.setPresentationName("DB_1_Close");

    CanonicalSketchPlane canonicalSketchPlane_2 = 
      ((CanonicalSketchPlane) cadModel_2.getFeature("YZ"));
    Sketch sketch_3 = 
      cadModel_2.getFeatureManager().createSketch(canonicalSketchPlane_2);
    cadModel_2.getFeatureManager().startSketchEdit(sketch_3);
  
  if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) { 
  sketch_3.createRectangle(new DoubleVector(new double[] {-1.5, 0.0}), new DoubleVector(new double[] {0, 3.5}));
  } else if ( CornerRadius == 0 && ( Yaw != 0 || Roll != 0) ) { 
  sketch_3.createRectangle(new DoubleVector(new double[] {-1.5, 0.0}), new DoubleVector(new double[] {1.5, 3.5}));
  } else {
    sketch_3.createRectangle(new DoubleVector(new double[] {-1.5, 0.0}), new DoubleVector(new double[] {1.5, 3.5}));

    LineSketchPrimitive lineSketchPrimitive_6 = 
      sketch_3.createLine(new DoubleVector(new double[] {CornerRadius, 0.0}), new DoubleVector(new double[] {CornerRadius, 3.5}));
    sketch_3.createVerticalConstraint(lineSketchPrimitive_6);
    sketch_3.getProjectedSketchPrimitives(new NeoObjectVector(new Object[] {}), new NeoObjectVector(new Object[] {lineSketchPrimitive_6}), new NeoObjectVector(new Object[] {}), new NeoObjectVector(new Object[] {}), new NeoObjectVector(new Object[] {}));
    sketch_3.setConstructionState(new NeoObjectVector(new Object[] {lineSketchPrimitive_6}), true);
  }
  
    sketch_3.setIsUptoDate(true);
    sketch_3.markFeatureForEdit();
    cadModel_2.getFeatureManager().stopSketchEdit(sketch_3, true);
    cadModel_2.getFeatureManager().updateModelAfterFeatureEdited(sketch_3, null);
  
  if ( CornerRadius == 0) {
    
  double DistanceDB1Front = 2.31 ;
  double DistanceDB1Back = 4.69 ; 
  
  ExtrusionMerge extrusionMerge_2 = 
      cadModel_2.getFeatureManager().createExtrusionMerge(sketch_3);
  extrusionMerge_2.setDirectionOption(0);
    extrusionMerge_2.setExtrudedBodyTypeOption(0);
    extrusionMerge_2.getDistance().setValue(DistanceDB1Front);
    extrusionMerge_2.getDistance().setUnits(units_0);
    extrusionMerge_2.getDistanceAsymmetric().setValue(DistanceDB1Back);
    extrusionMerge_2.getDistanceAsymmetric().setUnits(units_0);
    extrusionMerge_2.setDistanceOption(2);
    extrusionMerge_2.setCoordinateSystemOption(0);
    extrusionMerge_2.setDraftOption(0);
    extrusionMerge_2.setImportedCoordinateSystem(labCoordinateSystem_0);
    extrusionMerge_2.getDirectionAxis().setCoordinateSystem(labCoordinateSystem_0);
    extrusionMerge_2.getDirectionAxis().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {0.0, 0.0, 1.0}));
    extrusionMerge_2.setFace(null);
    extrusionMerge_2.setBody(null);
    extrusionMerge_2.setSketch(sketch_3);
    extrusionMerge_2.setPostOption(1);
    extrusionMerge_2.setExtrusionOption(0);
    extrusionMerge_2.setInteractingBodies(new NeoObjectVector(new Object[] {}));
    extrusionMerge_2.setInteractingBodiesBodyGroups(new NeoObjectVector(new Object[] {}));
    extrusionMerge_2.setInteractingBodiesCadFilters(new NeoObjectVector(new Object[] {}));
    extrusionMerge_2.setInteractingSelectedBodies(false);
    cadModel_2.getFeatureManager().markDependentNotUptodate(extrusionMerge_2);
    extrusionMerge_2.markFeatureForEdit();
    cadModel_2.getFeatureManager().execute(extrusionMerge_2);
  
  LineSketchPrimitive lineSketchPrimitive_6 = 
      ((LineSketchPrimitive) sketch_3.getSketchPrimitive("Line 1"));
  
  star.cadmodeler.Body cadmodelerBody_2 = 
      ((star.cadmodeler.Body) extrusionMerge_2.getBody(lineSketchPrimitive_6));
    cadmodelerBody_2.setPresentationName("DB_1_Box");
    cadModel_2.update();
    simulation_0.get(SolidModelManager.class).endEditCadModel(cadModel_2);
    cadModel_2.createParts(new NeoObjectVector(new Object[] {cadmodelerBody_2}), new NeoObjectVector(new Object[] {}), 1, "SharpEdges", 30.0, 3, false, 1.0E-5);
  
  SolidModelPart solidModelPart_2 = 
      ((SolidModelPart) simulation_0.get(SimulationPartManager.class).getPart("DB_1_Box"));
  solidModelPart_2.transposePartsToCsys(new NeoObjectVector(new Object[] {solidModelPart_2}), labCoordinateSystem_0, cartesianCoordinateSystem_0);
    
  } else {  
  
  double AngleDB1Front = 25 * 9.125 / CornerRadius ;
  double AngleDB1Back = 60 * 9.125 / CornerRadius ;
  
  LineSketchPrimitive lineSketchPrimitive_6 = 
      ((LineSketchPrimitive) sketch_3.getSketchPrimitive("Line 5"));

    RevolveMerge revolveMerge_2 = 
      (RevolveMerge) cadModel_2.getFeatureManager().createRevolveMerge(sketch_3);
    revolveMerge_2.setCoordinateSystemSourceOption(0);
    revolveMerge_2.setImportedCoordinateSystem(labCoordinateSystem_0);
    revolveMerge_2.getSpecifiedAxisDirection().setCoordinateSystem(labCoordinateSystem_0);
    revolveMerge_2.getSpecifiedAxisPosition().setCoordinateSystem(labCoordinateSystem_0);
    revolveMerge_2.getAngle().setValue(AngleDB1Front);
    revolveMerge_2.getAngle().setUnits(units_1);
    revolveMerge_2.getAngleAsymmetric().setValue(AngleDB1Back);
    revolveMerge_2.getAngleAsymmetric().setUnits(units_1);
    revolveMerge_2.setAngleOption(2);
    revolveMerge_2.setRevolvedBodyTypeOption(0);
    revolveMerge_2.setDirectionOption(0);
    revolveMerge_2.setRotationAxisOption(1);
    revolveMerge_2.setCoordinateSystemSourceOption(1);
    revolveMerge_2.setAxis(lineSketchPrimitive_6);
    revolveMerge_2.setRotationAxisOption(1);
    revolveMerge_2.setSketch(sketch_3);
    revolveMerge_2.setPostOption(1);
    revolveMerge_2.setInteractingBodies(new NeoObjectVector(new Object[] {}));
    revolveMerge_2.setInteractingBodiesBodyGroups(new NeoObjectVector(new Object[] {}));
    revolveMerge_2.setInteractingBodiesCadFilters(new NeoObjectVector(new Object[] {}));
    revolveMerge_2.setInteractingSelectedBodies(false);
    cadModel_2.getFeatureManager().markDependentNotUptodate(revolveMerge_2);
    revolveMerge_2.markFeatureForEdit();
    cadModel_2.getFeatureManager().execute(revolveMerge_2);

    star.cadmodeler.Body cadmodelerBody_2 = 
      ((star.cadmodeler.Body) revolveMerge_2.getBody(lineSketchPrimitive_6));
    cadmodelerBody_2.setPresentationName("DB_1_Close");
    cadModel_2.update();
    simulation_0.get(SolidModelManager.class).endEditCadModel(cadModel_2);
    cadModel_2.createParts(new NeoObjectVector(new Object[] {cadmodelerBody_2}), new NeoObjectVector(new Object[] {}), 1, "SharpEdges", 30.0, 3, false, 1.0E-5);
  
  SolidModelPart solidModelPart_2 = 
      ((SolidModelPart) simulation_0.get(SimulationPartManager.class).getPart("DB_1_Close"));
  solidModelPart_2.transposePartsToCsys(new NeoObjectVector(new Object[] {solidModelPart_2}), labCoordinateSystem_0, cartesianCoordinateSystem_0);
  }
  //}

   //{ // DB 1 Close Subtract

    if ( CornerRadius == 0) {  
  SolidModelPart solidModelPart_3 = 
      ((SolidModelPart) simulation_0.get(SimulationPartManager.class).getPart("DB_1_Sub"));
  SolidModelPart solidModelPart_30 = 
      ((SolidModelPart) simulation_0.get(SimulationPartManager.class).getPart("DB_1_Box"));
    MeshActionManager meshActionManager_10 = 
      simulation_0.get(MeshActionManager.class);
    CadPart cadPart_200 = 
      (CadPart) meshActionManager_10.subtractParts(new NeoObjectVector(new Object[] {solidModelPart_30, solidModelPart_3}), solidModelPart_30, "CAD");
    cadPart_200.setPresentationName("DB_1_Close");
  }
  //}
  
  //{ // DB 0 SUB
  
    CadModel cadModel_3 = 
      simulation_0.get(SolidModelManager.class).createSolidModel();
    cadModel_3.resetSystemOptions();
    cadModel_3.setPresentationName("DB_0_Sub");

    CanonicalSketchPlane canonicalSketchPlane_3 = 
      ((CanonicalSketchPlane) cadModel_3.getFeature("ZX"));
    Sketch sketch_4 = 
      cadModel_3.getFeatureManager().createSketch(canonicalSketchPlane_3);
    cadModel_3.getFeatureManager().startSketchEdit(sketch_4);

    LineSketchPrimitive lineSketchPrimitive_7 = 
      sketch_4.createLine(new DoubleVector(new double[] {0.75, -0.5}), new DoubleVector(new double[] {0.75, 1.9}));
    sketch_4.createVerticalConstraint(lineSketchPrimitive_7);
    PointSketchPrimitive pointSketchPrimitive_0 = 
      ((PointSketchPrimitive) sketch_4.getSketchPrimitive("Point 2"));
    LineSketchPrimitive lineSketchPrimitive_8 = 
      sketch_4.createLine(pointSketchPrimitive_0, new DoubleVector(new double[] {2.5, 1.9}));
    sketch_4.createHorizontalConstraint(lineSketchPrimitive_8);
    PointSketchPrimitive pointSketchPrimitive_1 = 
      ((PointSketchPrimitive) sketch_4.getSketchPrimitive("Point 3"));
    LineSketchPrimitive lineSketchPrimitive_9 = 
      sketch_4.createLine(pointSketchPrimitive_1, new DoubleVector(new double[] {2.5, -2.9}));
    sketch_4.createVerticalConstraint(lineSketchPrimitive_9);
    PointSketchPrimitive pointSketchPrimitive_2 = 
      ((PointSketchPrimitive) sketch_4.getSketchPrimitive("Point 4"));
    PointSketchPrimitive pointSketchPrimitive_3 = 
      ((PointSketchPrimitive) sketch_4.getSketchPrimitive("Point 1"));
    LineSketchPrimitive lineSketchPrimitive_10 = 
      sketch_4.createLine(pointSketchPrimitive_2, pointSketchPrimitive_3);
    sketch_4.setIsUptoDate(true);
    sketch_4.markFeatureForEdit();
    cadModel_3.getFeatureManager().stopSketchEdit(sketch_4, true);
    cadModel_3.getFeatureManager().updateModelAfterFeatureEdited(sketch_4, null);

    ExtrusionMerge extrusionMerge_0 = 
      cadModel_3.getFeatureManager().createExtrusionMerge(sketch_4);
    extrusionMerge_0.setDirectionOption(0);
    extrusionMerge_0.setExtrudedBodyTypeOption(0);
    extrusionMerge_0.getDistance().setValue(1.5);
    extrusionMerge_0.getDistance().setUnits(units_0);
    extrusionMerge_0.setDistanceOption(1);
    extrusionMerge_0.setCoordinateSystemOption(0);
    extrusionMerge_0.setDraftOption(0);
    extrusionMerge_0.setImportedCoordinateSystem(labCoordinateSystem_0);
    extrusionMerge_0.getDirectionAxis().setCoordinateSystem(labCoordinateSystem_0);
    extrusionMerge_0.getDirectionAxis().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {0.0, 0.0, 1.0}));
    extrusionMerge_0.setFace(null);
    extrusionMerge_0.setBody(null);
    extrusionMerge_0.setSketch(sketch_4);
    extrusionMerge_0.setPostOption(1);
    extrusionMerge_0.setExtrusionOption(0);
    extrusionMerge_0.setInteractingBodies(new NeoObjectVector(new Object[] {}));
    extrusionMerge_0.setInteractingBodiesBodyGroups(new NeoObjectVector(new Object[] {}));
    extrusionMerge_0.setInteractingBodiesCadFilters(new NeoObjectVector(new Object[] {}));
    extrusionMerge_0.setInteractingSelectedBodies(false);
    cadModel_3.getFeatureManager().markDependentNotUptodate(extrusionMerge_0);
    extrusionMerge_0.markFeatureForEdit();
    cadModel_3.getFeatureManager().execute(extrusionMerge_0);

    star.cadmodeler.Body cadmodelerBody_3 = 
      ((star.cadmodeler.Body) extrusionMerge_0.getBody(lineSketchPrimitive_7));
    cadmodelerBody_3.setPresentationName("DB_0_Sub");
    cadModel_3.update();
    simulation_0.get(SolidModelManager.class).endEditCadModel(cadModel_3);
    cadModel_3.createParts(new NeoObjectVector(new Object[] {cadmodelerBody_3}), new NeoObjectVector(new Object[] {}), 1, "SharpEdges", 30.0, 3, false, 1.0E-5);
  //}

  //{ // DB Diff

    CadModel cadModel_4 = 
      simulation_0.get(SolidModelManager.class).createSolidModel();
    cadModel_4.resetSystemOptions();
    cadModel_4.setPresentationName("DB_Diff");

    CanonicalSketchPlane canonicalSketchPlane_4 = 
      ((CanonicalSketchPlane) cadModel_4.getFeature("XY"));
    Sketch sketch_5 = 
      cadModel_4.getFeatureManager().createSketch(canonicalSketchPlane_4);
    cadModel_4.getFeatureManager().startSketchEdit(sketch_5);
    sketch_5.createRectangle(new DoubleVector(new double[] {-0.975, -0.75}), new DoubleVector(new double[] {-0.275, 0.75}));
    sketch_5.createRectangle(new DoubleVector(new double[] {-0.275, -0.45}), new DoubleVector(new double[] {-0.2, 0.45}));
    PointSketchPrimitive pointSketchPrimitive_4 = 
      ((PointSketchPrimitive) sketch_5.getSketchPrimitive("Point 5"));
    LineSketchPrimitive lineSketchPrimitive_11 = 
      ((LineSketchPrimitive) sketch_5.getSketchPrimitive("Line 3"));
    sketch_5.createIncidenceConstraint(pointSketchPrimitive_4, lineSketchPrimitive_11);
    sketch_5.setIsUptoDate(true);
    sketch_5.markFeatureForEdit();
    sketch_5.startSketchQuickTrim();
    sketch_5.deleteTrimmedEntities(new StringVector(new String[] {"Line 3 2", "Line 5"}));
    sketch_5.stopSketchQuickTrim();
    cadModel_4.getFeatureManager().stopSketchEdit(sketch_5, true);
    cadModel_4.getFeatureManager().updateModelAfterFeatureEdited(sketch_5, null);
  
    ExtrusionMerge extrusionMerge_3 = 
      cadModel_4.getFeatureManager().createExtrusionMerge(sketch_5);
    extrusionMerge_3.setDirectionOption(0);
    extrusionMerge_3.setExtrudedBodyTypeOption(0);
    extrusionMerge_3.getDistance().setValue(0.325);
    extrusionMerge_3.getDistance().setUnits(units_0);
    extrusionMerge_3.setDistanceOption(0);
    extrusionMerge_3.setCoordinateSystemOption(0);
    extrusionMerge_3.setDraftOption(0);
    extrusionMerge_3.setImportedCoordinateSystem(labCoordinateSystem_0);
    extrusionMerge_3.getDirectionAxis().setCoordinateSystem(labCoordinateSystem_0);
    extrusionMerge_3.getDirectionAxis().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {0.0, 0.0, 1.0}));
    extrusionMerge_3.setFace(null);
    extrusionMerge_3.setBody(null);
    extrusionMerge_3.setSketch(sketch_5);
    extrusionMerge_3.setPostOption(1);
    extrusionMerge_3.setExtrusionOption(0);
    extrusionMerge_3.setInteractingBodies(new NeoObjectVector(new Object[] {}));
    extrusionMerge_3.setInteractingBodiesBodyGroups(new NeoObjectVector(new Object[] {}));
    extrusionMerge_3.setInteractingBodiesCadFilters(new NeoObjectVector(new Object[] {}));
    extrusionMerge_3.setInteractingSelectedBodies(false);
    cadModel_4.getFeatureManager().markDependentNotUptodate(extrusionMerge_3);
    extrusionMerge_3.markFeatureForEdit();
    cadModel_4.getFeatureManager().execute(extrusionMerge_3);

    Sketch sketch_6 = 
      cadModel_4.getFeatureManager().createSketch(canonicalSketchPlane_4);
    cadModel_4.getFeatureManager().startSketchEdit(sketch_6);
    sketch_6.createRectangle(new DoubleVector(new double[] {-0.975, 0.75}), new DoubleVector(new double[] {-1.25, -0.75}));
    sketch_6.createRectangle(new DoubleVector(new double[] {-1.25, 0.45}), new DoubleVector(new double[] {-1.55, -0.45}));
    sketch_6.setIsUptoDate(true);
    sketch_6.markFeatureForEdit();
    sketch_6.startSketchQuickTrim();
    sketch_6.deleteTrimmedEntities(new StringVector(new String[] {"Line 3 2", "Line 5"}));
    sketch_6.stopSketchQuickTrim();
    cadModel_4.getFeatureManager().stopSketchEdit(sketch_6, true);
    cadModel_4.getFeatureManager().updateModelAfterFeatureEdited(sketch_6, null);

    ExtrusionMerge extrusionMerge_4 = 
      cadModel_4.getFeatureManager().createExtrusionMerge(sketch_6);
    extrusionMerge_4.setDirectionOption(0);
    extrusionMerge_4.setExtrudedBodyTypeOption(0);
    extrusionMerge_4.getDistance().setValue(0.5);
    extrusionMerge_4.getDistance().setUnits(units_0);
    extrusionMerge_4.setDistanceOption(0);
    extrusionMerge_4.setCoordinateSystemOption(0);
    extrusionMerge_4.setDraftOption(0);
    extrusionMerge_4.setImportedCoordinateSystem(labCoordinateSystem_0);
    extrusionMerge_4.getDirectionAxis().setCoordinateSystem(labCoordinateSystem_0);
    extrusionMerge_4.getDirectionAxis().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {0.0, 0.0, 1.0}));
    extrusionMerge_4.setFace(null);
    extrusionMerge_4.setBody(null);
    extrusionMerge_4.setSketch(sketch_6);
    extrusionMerge_4.setPostOption(1);
    extrusionMerge_4.setExtrusionOption(0);
    extrusionMerge_4.setInteractingBodies(new NeoObjectVector(new Object[] {}));
    extrusionMerge_4.setInteractingBodiesBodyGroups(new NeoObjectVector(new Object[] {}));
    extrusionMerge_4.setInteractingBodiesCadFilters(new NeoObjectVector(new Object[] {}));
    extrusionMerge_4.setInteractingSelectedBodies(false);
    cadModel_4.getFeatureManager().markDependentNotUptodate(extrusionMerge_4);
    extrusionMerge_4.markFeatureForEdit();
    cadModel_4.getFeatureManager().execute(extrusionMerge_4);

    Sketch sketch_60 = 
      cadModel_4.getFeatureManager().createSketch(canonicalSketchPlane_4);
    cadModel_4.getFeatureManager().startSketchEdit(sketch_60);
    sketch_60.createRectangle(new DoubleVector(new double[] {-0.2, 0.45}), new DoubleVector(new double[] {0.24, -0.45}));
    sketch_60.setIsUptoDate(true);
    sketch_60.markFeatureForEdit();
    cadModel_4.getFeatureManager().stopSketchEdit(sketch_60, true);
    cadModel_4.getFeatureManager().updateModelAfterFeatureEdited(sketch_60, null);

    ExtrusionMerge extrusionMerge_40 = 
      cadModel_4.getFeatureManager().createExtrusionMerge(sketch_60);
    extrusionMerge_40.setDirectionOption(0);
    extrusionMerge_40.setExtrudedBodyTypeOption(0);
    extrusionMerge_40.getDistance().setValue(0.43);
    extrusionMerge_40.getDistance().setUnits(units_0);
    extrusionMerge_40.setDistanceOption(0);
    extrusionMerge_40.setCoordinateSystemOption(0);
    extrusionMerge_40.setDraftOption(0);
    extrusionMerge_40.setImportedCoordinateSystem(labCoordinateSystem_0);
    extrusionMerge_40.getDirectionAxis().setCoordinateSystem(labCoordinateSystem_0);
    extrusionMerge_40.getDirectionAxis().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {0.0, 0.0, 1.0}));
    extrusionMerge_40.setFace(null);
    extrusionMerge_40.setBody(null);
    extrusionMerge_40.setSketch(sketch_60);
    extrusionMerge_40.setPostOption(1);
    extrusionMerge_40.setExtrusionOption(0);
    extrusionMerge_40.setInteractingBodies(new NeoObjectVector(new Object[] {}));
    extrusionMerge_40.setInteractingBodiesBodyGroups(new NeoObjectVector(new Object[] {}));
    extrusionMerge_40.setInteractingBodiesCadFilters(new NeoObjectVector(new Object[] {}));
    extrusionMerge_40.setInteractingSelectedBodies(false);
    cadModel_4.getFeatureManager().markDependentNotUptodate(extrusionMerge_40);
    extrusionMerge_40.markFeatureForEdit();
    cadModel_4.getFeatureManager().execute(extrusionMerge_40);

    star.cadmodeler.Body cadmodelerBody_4 = 
      ((star.cadmodeler.Body) extrusionMerge_40.getBodyByLocation(new DoubleVector(new double[] {-0.2, 0.06, 0.36})));
    cadmodelerBody_4.setPresentationName("DB_Diff");
    cadModel_4.update();
    simulation_0.get(SolidModelManager.class).endEditCadModel(cadModel_4);
    cadModel_4.createParts(new NeoObjectVector(new Object[] {cadmodelerBody_4}), new NeoObjectVector(new Object[] {}), 1, "SharpEdges", 30.0, 3, false, 1.0E-5);
  
  //}

  //{ // DB 0 BOX
  
    MeshPartFactory meshPartFactory_0 = 
      simulation_0.get(MeshPartFactory.class);
    
    SimpleBlockPart simpleBlockPart_0 = 
      meshPartFactory_0.createNewBlockPart(simulation_0.get(SimulationPartManager.class));
    simpleBlockPart_0.setDoNotRetessellate(true);
    simpleBlockPart_0.setCoordinateSystem(labCoordinateSystem_0);
    simpleBlockPart_0.getCorner1().setCoordinateSystem(labCoordinateSystem_0);
    simpleBlockPart_0.getCorner1().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {-3.5, -1.1, 0.0}));
    simpleBlockPart_0.getCorner2().setCoordinateSystem(labCoordinateSystem_0);
    simpleBlockPart_0.getCorner2().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {1.3, 1.1, 2.2}));
    simpleBlockPart_0.rebuildSimpleShapePart();
    simpleBlockPart_0.setDoNotRetessellate(false);
    simpleBlockPart_0.setPresentationName("DB_0_Box");
  
  SolidModelPart solidModelPart_3 = 
      ((SolidModelPart) simulation_0.get(SimulationPartManager.class).getPart("DB_0_Sub"));
    MeshActionManager meshActionManager_0 = 
      simulation_0.get(MeshActionManager.class);
    CadPart cadPart_0 = 
      (CadPart) meshActionManager_0.subtractParts(new NeoObjectVector(new Object[] {simpleBlockPart_0, solidModelPart_3}), simpleBlockPart_0, "CAD");
    cadPart_0.setPresentationName("DB_0_Car");
  //}
  
  //{ // DB FW

    SimpleBlockPart simpleBlockPart_1 = 
      meshPartFactory_0.createNewBlockPart(simulation_0.get(SimulationPartManager.class));
    simpleBlockPart_1.setDoNotRetessellate(true);
    simpleBlockPart_1.setCoordinateSystem(labCoordinateSystem_0);
    simpleBlockPart_1.getCorner1().setCoordinateSystem(labCoordinateSystem_0);
    simpleBlockPart_1.getCorner1().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {0.24, -0.8, -0.05}));
    simpleBlockPart_1.getCorner2().setCoordinateSystem(labCoordinateSystem_0);
    simpleBlockPart_1.getCorner2().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {1.0, 0.8, 0.4}));
    simpleBlockPart_1.rebuildSimpleShapePart();
    simpleBlockPart_1.setDoNotRetessellate(false);
    simpleBlockPart_1.setPresentationName("DB_FW");
  //}

  }

  private void GeoRFChecks() {

  Simulation simulation_0 = 
    getActiveSimulation();

  double CoolingMode = simulation_0.getReportManager().getReport("Cooling Mode").getReportMonitorValue(); 

  //{ // Geometry Pre check
    
  simulation_0.getSceneManager().createEmptyScene("Scene");  
    
  Scene scene_0 = 
      simulation_0.getSceneManager().getScene("Scene 1");
    scene_0.initializeAndWait();  
  SceneUpdate sceneUpdate_0 = 
      scene_0.getSceneUpdate();
  scene_0.setPresentationName("Geometry");  
  scene_0.setAspectRatio(AspectRatioEnum.RATIO_16_9);
  scene_0.setAxesViewport(new DoubleVector(new double[] {0.88, 0.0, 1.0, 0.14}));

  LogoAnnotation logoAnnotation_0 = 
      ((LogoAnnotation) simulation_0.getAnnotationManager().getObject("Logo"));
    scene_0.getAnnotationPropManager().removePropsForAnnotations(logoAnnotation_0);

    ImageAnnotation2D imageAnnotation2D_0 = 
      ((ImageAnnotation2D) simulation_0.getAnnotationManager().getObject("Line"));
    ImageAnnotationProp2D imageAnnotationProp2D_0 = 
      (ImageAnnotationProp2D) scene_0.getAnnotationPropManager().createPropForAnnotation(imageAnnotation2D_0);

    ImageAnnotation2D imageAnnotation2D_1 = 
      ((ImageAnnotation2D) simulation_0.getAnnotationManager().getObject("FST Black Icon"));
    ImageAnnotationProp2D imageAnnotationProp2D_1 = 
      (ImageAnnotationProp2D) scene_0.getAnnotationPropManager().createPropForAnnotation(imageAnnotation2D_1);

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
  
  SimpleAnnotation simpleAnnotation_3 = 
      simulation_0.getAnnotationManager().createAnnotation(SimpleAnnotation.class);
  simpleAnnotation_3.copyProperties(simpleAnnotation_1);
    simpleAnnotation_3.setPresentationName("Geometry");
    simpleAnnotation_3.setText("Geometry");
  simpleAnnotation_3.setDefaultHeight(0.07);
    simpleAnnotation_3.setDefaultPosition(new DoubleVector(new double[] {0.04, 0.02, 0.0}));
  
  SimpleAnnotationProp simpleAnnotationProp_3 = 
      (SimpleAnnotationProp) scene_0.getAnnotationPropManager().createPropForAnnotation(simpleAnnotation_3);
  
  scene_0.setBackgroundColorMode(BackgroundColorMode.SOLID);
    scene_0.close();
  
  CompositePart compositePart_0 = 
      ((CompositePart) simulation_0.get(SimulationPartManager.class).getPart("Car"));
  
  PartDisplayer partDisplayer_0 = 
      scene_0.getDisplayerManager().createPartDisplayer("Surface", -1, 1);
  partDisplayer_0.initialize();
  
  PartRepresentation partRepresentation_0 = 
      ((PartRepresentation) simulation_0.getRepresentationManager().getObject("Geometry"));
    partDisplayer_0.setRepresentation(partRepresentation_0);
  partDisplayer_0.getInputParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new RelationshipPredicate(RelationshipOperator.DescendantOf, new IdentityPredicate(IdentityOperator.Equals, Arrays.<ClientServerObject>asList(compositePart_0))), new TypePredicate(TypeOperator.Is, PartSurface.class))), Query.STANDARD_MODIFIERS));
  partDisplayer_0.setColorMode(PartColorMode.CONSTANT);
  partDisplayer_0.setOutline(false);
    partDisplayer_0.setDisplayerColor(new DoubleVector(new double[] {0.5921568870544434, 0.5921568870544434, 0.5921568870544434}));
  
  CurrentView currentView_0 = 
      scene_0.getCurrentView();
  
  VisView visView_0 = 
      ((VisView) simulation_0.getViewManager().getObject("bottom"));
    currentView_0.setView(visView_0);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\Pre Checks\\Geometry\\bottom.png"), 1, 1920, 1080, true, false);
    
  VisView visView_1 = 
      ((VisView) simulation_0.getViewManager().getObject("bottom_front_left"));
    currentView_0.setView(visView_1);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\Pre Checks\\Geometry\\bottom_front_left.png"), 1, 1920, 1080, true, false);
  
  VisView visView_2 = 
      ((VisView) simulation_0.getViewManager().getObject("bottom_front_right"));
    currentView_0.setView(visView_2);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\Pre Checks\\Geometry\\bottom_front_right.png"), 1, 1920, 1080, true, false);
  
  VisView visView_3 = 
      ((VisView) simulation_0.getViewManager().getObject("bottom_rear_left"));
    currentView_0.setView(visView_3);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\Pre Checks\\Geometry\\bottom_rear_left.png"), 1, 1920, 1080, true, false);
  
  VisView visView_4 = 
      ((VisView) simulation_0.getViewManager().getObject("bottom_rear_right"));
    currentView_0.setView(visView_4);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\Pre Checks\\Geometry\\bottom_rear_right.png"), 1, 1920, 1080, true, false);
  
  VisView visView_5 = 
      ((VisView) simulation_0.getViewManager().getObject("front"));
    currentView_0.setView(visView_5);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\Pre Checks\\Geometry\\front.png"), 1, 1920, 1080, true, false);
  
  VisView visView_6 = 
      ((VisView) simulation_0.getViewManager().getObject("left"));
    currentView_0.setView(visView_6);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\Pre Checks\\Geometry\\left.png"), 1, 1920, 1080, true, false);
  
  VisView visView_7 = 
      ((VisView) simulation_0.getViewManager().getObject("rear"));
    currentView_0.setView(visView_7);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\Pre Checks\\Geometry\\rear.png"), 1, 1920, 1080, true, false);
  
  VisView visView_8 = 
      ((VisView) simulation_0.getViewManager().getObject("right"));
    currentView_0.setView(visView_8);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\Pre Checks\\Geometry\\right.png"), 1, 1920, 1080, true, false);
  
  VisView visView_9 = 
      ((VisView) simulation_0.getViewManager().getObject("top"));
    currentView_0.setView(visView_9);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\Pre Checks\\Geometry\\top.png"), 1, 1920, 1080, true, false);
  
  VisView visView_10 = 
      ((VisView) simulation_0.getViewManager().getObject("top_front_left"));
    currentView_0.setView(visView_10);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\Pre Checks\\Geometry\\top_front_left.png"), 1, 1920, 1080, true, false);
  
  VisView visView_11 = 
      ((VisView) simulation_0.getViewManager().getObject("top_front_right"));
    currentView_0.setView(visView_11);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\Pre Checks\\Geometry\\top_front_right.png"), 1, 1920, 1080, true, false);
  
  VisView visView_12 = 
      ((VisView) simulation_0.getViewManager().getObject("top_rear_left"));
    currentView_0.setView(visView_12);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\Pre Checks\\Geometry\\top_rear_left.png"), 1, 1920, 1080, true, false);
  
  VisView visView_13 = 
      ((VisView) simulation_0.getViewManager().getObject("top_rear_right"));
    currentView_0.setView(visView_13);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\Pre Checks\\Geometry\\top_rear_right.png"), 1, 1920, 1080, true, false);
  //}
  
  //{ // Coordinate Systems Pre check
  
  double Roll = simulation_0.getReportManager().getReport("Roll [deg]").getReportMonitorValue();
  double Yaw = simulation_0.getReportManager().getReport("Yaw [rad]").getReportMonitorValue();
  double CornerRadius = simulation_0.getReportManager().getReport("Corner Radius [m]").getReportMonitorValue(); 
  
  Scene scene_1 = 
      simulation_0.getSceneManager().createScene("Geometry");
  scene_1.copyProperties(scene_0);
    scene_1.setPresentationName("Pre Checks");
  scene_1.initializeAndWait();
  scene_1.open();

  scene_1.getAnnotationPropManager().removePropsForAnnotations(simpleAnnotation_3);
    scene_1.close();
  
  SimpleAnnotation simpleAnnotation_4 = 
      simulation_0.getAnnotationManager().createAnnotation(SimpleAnnotation.class);
  simpleAnnotation_4.copyProperties(simpleAnnotation_1);
  simpleAnnotation_4.setPresentationName("Coordinate System");
    simpleAnnotation_4.setText("Coordinate \n   Systems");
  simpleAnnotation_4.setDefaultHeight(0.1);
  simpleAnnotation_4.setDefaultPosition(new DoubleVector(new double[] {0.05, 0.015, 0.0}));
  
    SimpleAnnotationProp simpleAnnotationProp_4 = 
      (SimpleAnnotationProp) scene_1.getAnnotationPropManager().createPropForAnnotation(simpleAnnotation_4);
  
  PartDisplayer partDisplayer_1 = 
      ((PartDisplayer) scene_1.getDisplayerManager().getDisplayer("Surface 1"));
    partDisplayer_1.initialize();
  partDisplayer_1.setPresentationName("Car");
  partDisplayer_1.getInputParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new RelationshipPredicate(RelationshipOperator.DescendantOf, new IdentityPredicate(IdentityOperator.Equals, Arrays.<ClientServerObject>asList(compositePart_0))), new TypePredicate(TypeOperator.Is, PartSurface.class))), Query.STANDARD_MODIFIERS));
  CurrentView currentView_1 = 
      scene_1.getCurrentView();
    
  LabCoordinateSystem labCoordinateSystem_0 = 
      simulation_0.getCoordinateSystemManager().getLabCoordinateSystem();

    CartesianCoordinateSystem cartesianCoordinateSystem_0 = 
      ((CartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("Air Orientation - Car"));
    CartesianCoordinateSystem cartesianCoordinateSystem_1 = 
      ((CartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("CoG"));
    ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_1 = 
      ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("CSys - FR"));
    ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_3 = 
      ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("CSys - RR"));
    ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_7 = 
      ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("Radiator (R) - CSys"));
    
  if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {  
  
  if ( CoolingMode != 0) {
      ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_5 = 
      ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("Fan (R) - Axis"));
      scene_1.getCoordinateSystemGroup().setObjects(labCoordinateSystem_0, cartesianCoordinateSystem_0, cartesianCoordinateSystem_1, exportedCartesianCoordinateSystem_1, exportedCartesianCoordinateSystem_3, exportedCartesianCoordinateSystem_5, exportedCartesianCoordinateSystem_7);
    }
  else {
      scene_1.getCoordinateSystemGroup().setObjects(labCoordinateSystem_0, cartesianCoordinateSystem_0, cartesianCoordinateSystem_1, exportedCartesianCoordinateSystem_1, exportedCartesianCoordinateSystem_3, exportedCartesianCoordinateSystem_7);
    }

  

  } else {
    
  ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_0 = 
      ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("CSys - FL"));
  ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_2 = 
      ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("CSys - RL"));
    ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_6 = 
      ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("Radiator (L) - CSys"));

  if ( CoolingMode != 0) {
  ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_5 = 
    ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("Fan (R) - Axis"));
  ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_4 = 
      ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("Fan (L) - Axis"));
  scene_1.getCoordinateSystemGroup().setObjects(labCoordinateSystem_0, cartesianCoordinateSystem_0, cartesianCoordinateSystem_1, exportedCartesianCoordinateSystem_0, exportedCartesianCoordinateSystem_1, exportedCartesianCoordinateSystem_2, exportedCartesianCoordinateSystem_3, exportedCartesianCoordinateSystem_4, exportedCartesianCoordinateSystem_5, exportedCartesianCoordinateSystem_6, exportedCartesianCoordinateSystem_7);
  }
  else {
  scene_1.getCoordinateSystemGroup().setObjects(labCoordinateSystem_0, cartesianCoordinateSystem_0, cartesianCoordinateSystem_1, exportedCartesianCoordinateSystem_0, exportedCartesianCoordinateSystem_1, exportedCartesianCoordinateSystem_2, exportedCartesianCoordinateSystem_3, exportedCartesianCoordinateSystem_6, exportedCartesianCoordinateSystem_7);
  }

  }

  VisView visView_14 = 
      ((VisView) simulation_0.getViewManager().getObject("bottom"));
    currentView_1.setView(visView_14);
    scene_1.printAndWait(resolvePath("..\\Post_Processing\\Pre Checks\\Coordinate Systems\\bottom.png"), 1, 1920, 1080, true, false);
 
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
  ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_0 = 
      ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("CSys - FL"));
  ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_2 = 
      ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("CSys - RL"));
    ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_6 = 
      ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("Radiator (L) - CSys"));
  if ( CoolingMode != 0) {
  ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_5 = 
    ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("Fan (R) - Axis"));
  ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_4 = 
      ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("Fan (L) - Axis"));
  scene_1.getCoordinateSystemGroup().setObjects(labCoordinateSystem_0, cartesianCoordinateSystem_0, cartesianCoordinateSystem_1, exportedCartesianCoordinateSystem_0, exportedCartesianCoordinateSystem_2, exportedCartesianCoordinateSystem_4, exportedCartesianCoordinateSystem_6);
  }
  else {
    scene_1.getCoordinateSystemGroup().setObjects(labCoordinateSystem_0, cartesianCoordinateSystem_0, cartesianCoordinateSystem_1, exportedCartesianCoordinateSystem_0, exportedCartesianCoordinateSystem_2, exportedCartesianCoordinateSystem_6);
  }
  VisView visView_15 = 
      ((VisView) simulation_0.getViewManager().getObject("bottom_front_left"));
    currentView_1.setView(visView_15);
    scene_1.printAndWait(resolvePath("..\\Post_Processing\\Pre Checks\\Coordinate Systems\\bottom_front_left.png"), 1, 1920, 1080, true, false);}
  if ( CoolingMode != 0) {
    ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_5 = 
    ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("Fan (R) - Axis"));
  scene_1.getCoordinateSystemGroup().setObjects(labCoordinateSystem_0, cartesianCoordinateSystem_0, cartesianCoordinateSystem_1, exportedCartesianCoordinateSystem_1, exportedCartesianCoordinateSystem_3, exportedCartesianCoordinateSystem_5, exportedCartesianCoordinateSystem_7);
  }
  else{
  scene_1.getCoordinateSystemGroup().setObjects(labCoordinateSystem_0, cartesianCoordinateSystem_0, cartesianCoordinateSystem_1, exportedCartesianCoordinateSystem_1, exportedCartesianCoordinateSystem_3, exportedCartesianCoordinateSystem_7);
  }
  VisView visView_16 = 
      ((VisView) simulation_0.getViewManager().getObject("bottom_front_right"));
    currentView_1.setView(visView_16);
    scene_1.printAndWait(resolvePath("..\\Post_Processing\\Pre Checks\\Coordinate Systems\\bottom_front_right.png"), 1, 1920, 1080, true, false);
  
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
  ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_0 = 
      ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("CSys - FL"));
  ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_2 = 
      ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("CSys - RL"));
    ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_6 = 
      ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("Radiator (L) - CSys"));
  if ( CoolingMode != 0) {
  ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_4 = 
      ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("Fan (L) - Axis"));
  scene_1.getCoordinateSystemGroup().setObjects(labCoordinateSystem_0, cartesianCoordinateSystem_0, cartesianCoordinateSystem_1, exportedCartesianCoordinateSystem_0, exportedCartesianCoordinateSystem_2, exportedCartesianCoordinateSystem_4, exportedCartesianCoordinateSystem_6);
  }
  else {
    scene_1.getCoordinateSystemGroup().setObjects(labCoordinateSystem_0, cartesianCoordinateSystem_0, cartesianCoordinateSystem_1, exportedCartesianCoordinateSystem_0, exportedCartesianCoordinateSystem_2, exportedCartesianCoordinateSystem_6);
  }
  VisView visView_17 = 
      ((VisView) simulation_0.getViewManager().getObject("bottom_rear_left"));
    currentView_1.setView(visView_17);
    scene_1.printAndWait(resolvePath("..\\Post_Processing\\Pre Checks\\Coordinate Systems\\bottom_rear_left.png"), 1, 1920, 1080, true, false);}
  
  if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
  if ( CoolingMode != 0) {
    ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_5 = 
    ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("Fan (R) - Axis"));
  scene_1.getCoordinateSystemGroup().setObjects(labCoordinateSystem_0, cartesianCoordinateSystem_0, cartesianCoordinateSystem_1, exportedCartesianCoordinateSystem_1, exportedCartesianCoordinateSystem_3, exportedCartesianCoordinateSystem_5, exportedCartesianCoordinateSystem_7);
  }
  else{
  scene_1.getCoordinateSystemGroup().setObjects(labCoordinateSystem_0, cartesianCoordinateSystem_0, cartesianCoordinateSystem_1, exportedCartesianCoordinateSystem_1, exportedCartesianCoordinateSystem_3, exportedCartesianCoordinateSystem_7);  
  }
  } else {
    ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_0 = 
      ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("CSys - FL"));
  ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_2 = 
      ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("CSys - RL"));
    ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_6 = 
      ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("Radiator (L) - CSys"));
  if ( CoolingMode != 0) {
  ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_4 = 
      ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("Fan (L) - Axis"));
  scene_1.getCoordinateSystemGroup().setObjects(labCoordinateSystem_0, cartesianCoordinateSystem_0, cartesianCoordinateSystem_1, exportedCartesianCoordinateSystem_0, exportedCartesianCoordinateSystem_2, exportedCartesianCoordinateSystem_4, exportedCartesianCoordinateSystem_6);
  }
  else {
    scene_1.getCoordinateSystemGroup().setObjects(labCoordinateSystem_0, cartesianCoordinateSystem_0, cartesianCoordinateSystem_1, exportedCartesianCoordinateSystem_0, exportedCartesianCoordinateSystem_2, exportedCartesianCoordinateSystem_6);
  }
  }
  VisView visView_18 = 
      ((VisView) simulation_0.getViewManager().getObject("bottom_rear_right"));
    currentView_1.setView(visView_18);
    scene_1.printAndWait(resolvePath("..\\Post_Processing\\Pre Checks\\Coordinate Systems\\bottom_rear_right.png"), 1, 1920, 1080, true, false);
  
  if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
  scene_1.getCoordinateSystemGroup().setObjects(labCoordinateSystem_0, cartesianCoordinateSystem_0, cartesianCoordinateSystem_1, exportedCartesianCoordinateSystem_1, exportedCartesianCoordinateSystem_3, exportedCartesianCoordinateSystem_7);
  } else {
  ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_0 = 
      ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("CSys - FL"));
  ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_2 = 
      ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("CSys - RL"));
    ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_6 = 
      ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("Radiator (L) - CSys"));
  scene_1.getCoordinateSystemGroup().setObjects(labCoordinateSystem_0, cartesianCoordinateSystem_0, cartesianCoordinateSystem_1, exportedCartesianCoordinateSystem_0, exportedCartesianCoordinateSystem_1, exportedCartesianCoordinateSystem_2, exportedCartesianCoordinateSystem_3, exportedCartesianCoordinateSystem_6, exportedCartesianCoordinateSystem_7);
  }
  VisView visView_19 = 
      ((VisView) simulation_0.getViewManager().getObject("front"));
    currentView_1.setView(visView_19);
    scene_1.printAndWait(resolvePath("..\\Post_Processing\\Pre Checks\\Coordinate Systems\\front.png"), 1, 1920, 1080, true, false);
  
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
  ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_0 = 
      ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("CSys - FL"));
  ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_2 = 
      ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("CSys - RL"));
    ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_6 = 
      ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("Radiator (L) - CSys"));
  if ( CoolingMode != 0) {
  ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_4 = 
      ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("Fan (L) - Axis"));
  scene_1.getCoordinateSystemGroup().setObjects(labCoordinateSystem_0, cartesianCoordinateSystem_0, cartesianCoordinateSystem_1, exportedCartesianCoordinateSystem_0, exportedCartesianCoordinateSystem_2, exportedCartesianCoordinateSystem_4, exportedCartesianCoordinateSystem_6);
  }
  else{
  scene_1.getCoordinateSystemGroup().setObjects(labCoordinateSystem_0, cartesianCoordinateSystem_0, cartesianCoordinateSystem_1, exportedCartesianCoordinateSystem_0, exportedCartesianCoordinateSystem_2, exportedCartesianCoordinateSystem_6);
  }
  VisView visView_20 = 
      ((VisView) simulation_0.getViewManager().getObject("left"));
    currentView_1.setView(visView_20);
    scene_1.printAndWait(resolvePath("..\\Post_Processing\\Pre Checks\\Coordinate Systems\\left.png"), 1, 1920, 1080, true, false);}
  
  
  if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
  if ( CoolingMode != 0) {
  ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_5 = 
    ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("Fan (R) - Axis"));
  scene_1.getCoordinateSystemGroup().setObjects(labCoordinateSystem_0, cartesianCoordinateSystem_0, cartesianCoordinateSystem_1, exportedCartesianCoordinateSystem_1, exportedCartesianCoordinateSystem_3, exportedCartesianCoordinateSystem_5);
  }
  else{
  scene_1.getCoordinateSystemGroup().setObjects(labCoordinateSystem_0, cartesianCoordinateSystem_0, cartesianCoordinateSystem_1, exportedCartesianCoordinateSystem_1, exportedCartesianCoordinateSystem_3);
  }
  } else {
  ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_0 = 
      ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("CSys - FL"));
  ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_2 = 
      ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("CSys - RL"));
  if ( CoolingMode != 0) {
    ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_5 = 
    ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("Fan (R) - Axis"));
  ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_4 = 
      ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("Fan (L) - Axis"));
  scene_1.getCoordinateSystemGroup().setObjects(labCoordinateSystem_0, cartesianCoordinateSystem_0, cartesianCoordinateSystem_1, exportedCartesianCoordinateSystem_0, exportedCartesianCoordinateSystem_1, exportedCartesianCoordinateSystem_2, exportedCartesianCoordinateSystem_3, exportedCartesianCoordinateSystem_4, exportedCartesianCoordinateSystem_5);
  }
  else{
  scene_1.getCoordinateSystemGroup().setObjects(labCoordinateSystem_0, cartesianCoordinateSystem_0, cartesianCoordinateSystem_1, exportedCartesianCoordinateSystem_0, exportedCartesianCoordinateSystem_1, exportedCartesianCoordinateSystem_2, exportedCartesianCoordinateSystem_3);
  }
  }
  VisView visView_21 = 
      ((VisView) simulation_0.getViewManager().getObject("rear"));
    currentView_1.setView(visView_21);
    scene_1.printAndWait(resolvePath("..\\Post_Processing\\Pre Checks\\Coordinate Systems\\rear.png"), 1, 1920, 1080, true, false);
  
  if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
  if ( CoolingMode != 0) {
    ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_5 = 
    ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("Fan (R) - Axis"));
  scene_1.getCoordinateSystemGroup().setObjects(labCoordinateSystem_0, cartesianCoordinateSystem_0, cartesianCoordinateSystem_1, exportedCartesianCoordinateSystem_1, exportedCartesianCoordinateSystem_3, exportedCartesianCoordinateSystem_5, exportedCartesianCoordinateSystem_7);
  }
  else{
  scene_1.getCoordinateSystemGroup().setObjects(labCoordinateSystem_0, cartesianCoordinateSystem_0, cartesianCoordinateSystem_1, exportedCartesianCoordinateSystem_1, exportedCartesianCoordinateSystem_3, exportedCartesianCoordinateSystem_7);
  }
  } else {
    ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_6 = 
      ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("Radiator (L) - CSys"));
  if ( CoolingMode != 0) {
    ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_5 = 
    ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("Fan (R) - Axis"));
  ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_4 = 
      ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("Fan (L) - Axis"));
  scene_1.getCoordinateSystemGroup().setObjects(labCoordinateSystem_0, cartesianCoordinateSystem_0, cartesianCoordinateSystem_1, exportedCartesianCoordinateSystem_1, exportedCartesianCoordinateSystem_3, exportedCartesianCoordinateSystem_4, exportedCartesianCoordinateSystem_5, exportedCartesianCoordinateSystem_6, exportedCartesianCoordinateSystem_7);
  }
  else{
  scene_1.getCoordinateSystemGroup().setObjects(labCoordinateSystem_0, cartesianCoordinateSystem_0, cartesianCoordinateSystem_1, exportedCartesianCoordinateSystem_1, exportedCartesianCoordinateSystem_3, exportedCartesianCoordinateSystem_6, exportedCartesianCoordinateSystem_7);
  }
  }
  VisView visView_22 = 
      ((VisView) simulation_0.getViewManager().getObject("right"));
    currentView_1.setView(visView_22);
    scene_1.printAndWait(resolvePath("..\\Post_Processing\\Pre Checks\\Coordinate Systems\\right.png"), 1, 1920, 1080, true, false);
  
  
  if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
  if ( CoolingMode != 0) {
    ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_5 = 
    ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("Fan (R) - Axis"));
  scene_1.getCoordinateSystemGroup().setObjects(labCoordinateSystem_0, cartesianCoordinateSystem_0, cartesianCoordinateSystem_1, exportedCartesianCoordinateSystem_1, exportedCartesianCoordinateSystem_3, exportedCartesianCoordinateSystem_5, exportedCartesianCoordinateSystem_7);
  }
  else{
  scene_1.getCoordinateSystemGroup().setObjects(labCoordinateSystem_0, cartesianCoordinateSystem_0, cartesianCoordinateSystem_1, exportedCartesianCoordinateSystem_1, exportedCartesianCoordinateSystem_3, exportedCartesianCoordinateSystem_7);
  }
  } else {
  ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_0 = 
      ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("CSys - FL"));
  ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_2 = 
      ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("CSys - RL"));
    ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_6 = 
      ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("Radiator (L) - CSys"));
  if ( CoolingMode != 0) {
    ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_5 = 
    ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("Fan (R) - Axis"));
  ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_4 = 
      ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("Fan (L) - Axis"));
  scene_1.getCoordinateSystemGroup().setObjects(labCoordinateSystem_0, cartesianCoordinateSystem_0, cartesianCoordinateSystem_1, exportedCartesianCoordinateSystem_0, exportedCartesianCoordinateSystem_1, exportedCartesianCoordinateSystem_2, exportedCartesianCoordinateSystem_3, exportedCartesianCoordinateSystem_4, exportedCartesianCoordinateSystem_5, exportedCartesianCoordinateSystem_6, exportedCartesianCoordinateSystem_7);
  }
  else{
  scene_1.getCoordinateSystemGroup().setObjects(labCoordinateSystem_0, cartesianCoordinateSystem_0, cartesianCoordinateSystem_1, exportedCartesianCoordinateSystem_0, exportedCartesianCoordinateSystem_1, exportedCartesianCoordinateSystem_2, exportedCartesianCoordinateSystem_3, exportedCartesianCoordinateSystem_6, exportedCartesianCoordinateSystem_7);
  }
  }
  VisView visView_23 = 
      ((VisView) simulation_0.getViewManager().getObject("top"));
    currentView_1.setView(visView_23);
    scene_1.printAndWait(resolvePath("..\\Post_Processing\\Pre Checks\\Coordinate Systems\\top.png"), 1, 1920, 1080, true, false);
  
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
  ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_0 = 
      ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("CSys - FL"));
  ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_2 = 
      ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("CSys - RL"));
    ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_6 = 
      ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("Radiator (L) - CSys")); 
  VisView visView_24 = 
      ((VisView) simulation_0.getViewManager().getObject("top_front_left"));
    currentView_1.setView(visView_24);
    scene_1.printAndWait(resolvePath("..\\Post_Processing\\Pre Checks\\Coordinate Systems\\top_front_left.png"), 1, 1920, 1080, true, false);}
  
  VisView visView_25 = 
      ((VisView) simulation_0.getViewManager().getObject("top_front_right"));
    currentView_1.setView(visView_25);
    scene_1.printAndWait(resolvePath("..\\Post_Processing\\Pre Checks\\Coordinate Systems\\top_front_right.png"), 1, 1920, 1080, true, false);
  
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
  ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_0 = 
      ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("CSys - FL"));
  ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_2 = 
      ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("CSys - RL"));
    ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_6 = 
      ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("Radiator (L) - CSys"));
  if ( CoolingMode != 0) {
    ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_5 = 
    ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("Fan (R) - Axis"));
  ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_4 = 
      ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("Fan (L) - Axis"));
  scene_1.getCoordinateSystemGroup().setObjects(labCoordinateSystem_0, cartesianCoordinateSystem_0, cartesianCoordinateSystem_1, exportedCartesianCoordinateSystem_0, exportedCartesianCoordinateSystem_2, exportedCartesianCoordinateSystem_4, exportedCartesianCoordinateSystem_5, exportedCartesianCoordinateSystem_6, exportedCartesianCoordinateSystem_7);
  }
  else{
  scene_1.getCoordinateSystemGroup().setObjects(labCoordinateSystem_0, cartesianCoordinateSystem_0, cartesianCoordinateSystem_1, exportedCartesianCoordinateSystem_0, exportedCartesianCoordinateSystem_2, exportedCartesianCoordinateSystem_6, exportedCartesianCoordinateSystem_7);
  }
  VisView visView_26 = 
      ((VisView) simulation_0.getViewManager().getObject("top_rear_left"));
    currentView_1.setView(visView_26);
    scene_1.printAndWait(resolvePath("..\\Post_Processing\\Pre Checks\\Coordinate Systems\\top_rear_left.png"), 1, 1920, 1080, true, false);}
  
  
  if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
  if ( CoolingMode != 0) {
    ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_5 = 
    ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("Fan (R) - Axis"));
  scene_1.getCoordinateSystemGroup().setObjects(labCoordinateSystem_0, cartesianCoordinateSystem_0, cartesianCoordinateSystem_1, exportedCartesianCoordinateSystem_1, exportedCartesianCoordinateSystem_3, exportedCartesianCoordinateSystem_5, exportedCartesianCoordinateSystem_7); 
  }
  else{
  scene_1.getCoordinateSystemGroup().setObjects(labCoordinateSystem_0, cartesianCoordinateSystem_0, cartesianCoordinateSystem_1, exportedCartesianCoordinateSystem_1, exportedCartesianCoordinateSystem_3, exportedCartesianCoordinateSystem_7);  
  }
  } else {
    ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_6 = 
      ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("Radiator (L) - CSys")); 
  if ( CoolingMode != 0) {
    ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_5 = 
    ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("Fan (R) - Axis"));
  ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_4 = 
      ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("Fan (L) - Axis"));
  scene_1.getCoordinateSystemGroup().setObjects(labCoordinateSystem_0, cartesianCoordinateSystem_0, cartesianCoordinateSystem_1, exportedCartesianCoordinateSystem_1, exportedCartesianCoordinateSystem_3, exportedCartesianCoordinateSystem_4, exportedCartesianCoordinateSystem_5, exportedCartesianCoordinateSystem_6, exportedCartesianCoordinateSystem_7);
  }
  else{
  scene_1.getCoordinateSystemGroup().setObjects(labCoordinateSystem_0, cartesianCoordinateSystem_0, cartesianCoordinateSystem_1, exportedCartesianCoordinateSystem_1, exportedCartesianCoordinateSystem_3, exportedCartesianCoordinateSystem_6, exportedCartesianCoordinateSystem_7); 
  }
  }
  VisView visView_27 = 
      ((VisView) simulation_0.getViewManager().getObject("top_rear_right"));
    currentView_1.setView(visView_27);
    scene_1.printAndWait(resolvePath("..\\Post_Processing\\Pre Checks\\Coordinate Systems\\top_rear_right.png"), 1, 1920, 1080, true, false);
  
  //}
  
  //{ // Domain & Boxes Pre check
  
  scene_1.getAnnotationPropManager().removePropsForAnnotations(simpleAnnotation_4);
  
    SimpleAnnotation simpleAnnotation_5 = 
      simulation_0.getAnnotationManager().createAnnotation(SimpleAnnotation.class);
  simpleAnnotation_5.copyProperties(simpleAnnotation_1);
  simpleAnnotation_5.setPresentationName("Domain & Boxes");
    simpleAnnotation_5.setText("Domain & Boxes");
  simpleAnnotation_5.setDefaultHeight(0.055);
  simpleAnnotation_5.setDefaultPosition(new DoubleVector(new double[] {0.02, 0.03, 0.0}));
  
    SimpleAnnotationProp simpleAnnotationProp_5 = 
      (SimpleAnnotationProp) scene_1.getAnnotationPropManager().createPropForAnnotation(simpleAnnotation_5);
  
  PartDisplayer partDisplayer_2 = 
      scene_1.getDisplayerManager().createPartDisplayer("Dummy", -1, 0);
  partDisplayer_2.initialize();
  partDisplayer_2.setPresentationName("Domain & Boxes");
  
  partDisplayer_1.getInputParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new RelationshipPredicate(RelationshipOperator.DescendantOf, new IdentityPredicate(IdentityOperator.Equals, Arrays.<ClientServerObject>asList(compositePart_0))), new TypePredicate(TypeOperator.Is, PartSurface.class))), Query.STANDARD_MODIFIERS));
  scene_1.getCoordinateSystemGroup().setQuery(null);
  scene_1.getCoordinateSystemGroup().setObjects();
    partDisplayer_2.getInputParts().setQuery(null);
    partDisplayer_2.setSurface(true);
  
  SolidModelPart solidModelPart_0 = 
      ((SolidModelPart) simulation_0.get(SimulationPartManager.class).getPart("DB_0_Sub"));
    SimpleBlockPart simpleBlockPart_0 = 
      ((SimpleBlockPart) simulation_0.get(SimulationPartManager.class).getPart("DB_0_Box"));
  
    partDisplayer_2.setOpacity(0.5);
  partDisplayer_2.getInputParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new RelationshipPredicate(RelationshipOperator.NotDescendantOf, new IdentityPredicate(IdentityOperator.Equals, Arrays.<ClientServerObject>asList(compositePart_0))), new TypePredicate(TypeOperator.Is, PartSurface.class), new RelationshipPredicate(RelationshipOperator.NotDescendantOf, new IdentityPredicate(IdentityOperator.Equals, Arrays.<ClientServerObject>asList(solidModelPart_0))), new RelationshipPredicate(RelationshipOperator.NotDescendantOf, new IdentityPredicate(IdentityOperator.Equals, Arrays.<ClientServerObject>asList(simpleBlockPart_0))))), Query.STANDARD_MODIFIERS));
  
  currentView_1.setInput(new DoubleVector(new double[] {-10.0, 0.0, 4.0}), new DoubleVector(new double[] {-10.0, -60.0, 4.0}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), 20.0, 1, 30.0);
  scene_1.printAndWait(resolvePath("..\\Post_Processing\\Pre Checks\\Domain & Boxes\\side.png"), 1, 1920, 1080, true, false);
  
  currentView_1.setInput(new DoubleVector(new double[] {-10.0, 0.0, 0.0}), new DoubleVector(new double[] {-10.0, 0.0, 60.0}), new DoubleVector(new double[] {0.0, 1.0, 0.0}), 20.0, 1, 30.0);
  scene_1.printAndWait(resolvePath("..\\Post_Processing\\Pre Checks\\Domain & Boxes\\top.png"), 1, 1920, 1080, true, false);
  
  currentView_1.setInput(new DoubleVector(new double[] {-10.0, 0.0, 4.0}), new DoubleVector(new double[] {60.0, 0.0, 4.0}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), 6.0, 1, 30.0);
  scene_1.printAndWait(resolvePath("..\\Post_Processing\\Pre Checks\\Domain & Boxes\\front.png"), 1, 1920, 1080, true, false);
  
  //}
  }
}
