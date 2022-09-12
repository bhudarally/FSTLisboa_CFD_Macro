// STAR-CCM+ macro: b_CSystem_RadFanRename.java
// Written by STAR-CCM+ 14.04.011
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;
import star.cadmodeler.*;
import star.meshing.*;

public class b_CSystem_RadFanRename extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    CompositePart compositePart_0 = 
      ((CompositePart) simulation_0.get(SimulationPartManager.class).getPart("Car"));

    /*CadPart cadPart_0 = 
      ((CadPart) compositePart_0.getChildParts().getPart("Radiator"));

    simulation_0.get(SolidModelManager.class).createSolidModelForCadParts(new NeoObjectVector(new Object[] {cadPart_0}));

    CadModel cadModel_0 = 
      ((CadModel) simulation_0.get(SolidModelManager.class).getObject("3D-CAD Model 1"));

    ImportCadFileFeature importCadFileFeature_0 = 
      ((ImportCadFileFeature) cadModel_0.getFeature("ImportCad 1"));

    star.cadmodeler.Body cadmodelerBody_0 = 
      ((star.cadmodeler.Body) importCadFileFeature_0.getBodyByIndex(1));
	  
	Face face_0 = 
      ((Face) importCadFileFeature_0.getFaceByLocation(cadmodelerBody_0,new DoubleVector(new double[] {-1.707308320785095, -0.3439658997241918, 0.23046162083275712})));  

    ReferencePointFromEntityCenter referencePointFromEntityCenter_0 = 
      cadModel_0.getFeatureManager().createPointFromEntityCenter(face_0);

    referencePointFromEntityCenter_0.setColorVector(new IntVector(new int[] {-16711936}));

    referencePointFromEntityCenter_0.setEntity(face_0);

    referencePointFromEntityCenter_0.setCenterPointType(0);

    referencePointFromEntityCenter_0.markFeatureForEdit();

    cadModel_0.getFeatureManager().execute(referencePointFromEntityCenter_0);

    ReferenceCoordinateSystemFromPointAndPlane referenceCoordinateSystemFromPointAndPlane_0 = 
      cadModel_0.getFeatureManager().createCoordinateSystemFromPointAndPlane(null, null, null, face_0);

    referenceCoordinateSystemFromPointAndPlane_0.setColorVector(new IntVector(new int[] {-16711936, -16711681}));

    referenceCoordinateSystemFromPointAndPlane_0.setReferencePoint(referencePointFromEntityCenter_0);

    referenceCoordinateSystemFromPointAndPlane_0.setVertex(null);

    referenceCoordinateSystemFromPointAndPlane_0.setSketchPlane(null);

    referenceCoordinateSystemFromPointAndPlane_0.setFace(face_0);

    referenceCoordinateSystemFromPointAndPlane_0.setPlacementType(0);

    referenceCoordinateSystemFromPointAndPlane_0.markFeatureForEdit();

    cadModel_0.getFeatureManager().execute(referenceCoordinateSystemFromPointAndPlane_0);

    ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_0 = 
      (ExportedCartesianCoordinateSystem) referenceCoordinateSystemFromPointAndPlane_0.createExportedCoordinateSystem();

    cadModel_0.setFaceNameAttributes(new NeoObjectVector(new Object[] {face_0}), "Radiator_inlet", 135, 206, 250, 255);

	Face face_1 = 
      ((Face) importCadFileFeature_0.getFaceByLocation(cadmodelerBody_0,new DoubleVector(new double[] {-1.7318978888224252, -0.3268568063079662, 0.23208398522230037})));

    cadModel_0.setFaceNameAttributes(new NeoObjectVector(new Object[] {face_1}), "Radiator_outlet", 135, 206, 250, 255);

    simulation_0.get(SolidModelManager.class).endEditCadModel(cadModel_0);

    SolidModelPart solidModelPart_0 = 
      ((SolidModelPart) compositePart_0.getChildParts().getPart("Radiator"));

    simulation_0.get(SimulationPartManager.class).updateParts(new NeoObjectVector(new Object[] {solidModelPart_0}));

    PartSurface partSurface_0 = 
      ((PartSurface) solidModelPart_0.getPartSurfaceManager().getPartSurface("Faces"));

    partSurface_0.setPresentationName("Radiator_shroud");

    exportedCartesianCoordinateSystem_0.setPresentationName("CS - Radiator");

    CadPart cadPart_1 = 
      ((CadPart) compositePart_0.getChildParts().getPart("Fan"));

    simulation_0.get(SolidModelManager.class).createSolidModelForCadParts(new NeoObjectVector(new Object[] {cadPart_1}));

    CadModel cadModel_1 = 
      ((CadModel) simulation_0.get(SolidModelManager.class).getObject("3D-CAD Model 2"));

    ImportCadFileFeature importCadFileFeature_1 = 
      ((ImportCadFileFeature) cadModel_1.getFeature("ImportCad 1"));

    star.cadmodeler.Body cadmodelerBody_1 = 
      ((star.cadmodeler.Body) cadModel_1.getBody("Fan"));
  
	Face face_2 = 
      ((Face) importCadFileFeature_1.getFaceByLocation(cadmodelerBody_1,new DoubleVector(new double[] {-1.8492614733304014, -0.3467343295483643, 0.23579990045969162})));

	ReferencePointFromEntityCenter referencePointFromEntityCenter_3 = 
      cadModel_1.getFeatureManager().createPointFromEntityCenter(face_2);

    referencePointFromEntityCenter_3.setColorVector(new IntVector(new int[] {-16711936}));

    referencePointFromEntityCenter_3.setEntity(face_2);

    referencePointFromEntityCenter_3.setCenterPointType(0);

    referencePointFromEntityCenter_3.markFeatureForEdit();

    cadModel_1.getFeatureManager().execute(referencePointFromEntityCenter_3);

    ReferenceCoordinateSystemFromPointAndPlane referenceCoordinateSystemFromPointAndPlane_3 = 
      cadModel_1.getFeatureManager().createCoordinateSystemFromPointAndPlane(null, null, null, face_2);

    referenceCoordinateSystemFromPointAndPlane_3.setColorVector(new IntVector(new int[] {-16711936, -16711681}));

    referenceCoordinateSystemFromPointAndPlane_3.setReferencePoint(referencePointFromEntityCenter_3);

    referenceCoordinateSystemFromPointAndPlane_3.setVertex(null);

    referenceCoordinateSystemFromPointAndPlane_3.setSketchPlane(null);

    referenceCoordinateSystemFromPointAndPlane_3.setFace(face_2);

    referenceCoordinateSystemFromPointAndPlane_3.setPlacementType(0);

    referenceCoordinateSystemFromPointAndPlane_3.markFeatureForEdit();

    cadModel_1.getFeatureManager().execute(referenceCoordinateSystemFromPointAndPlane_3);

	ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_3 = 
      (ExportedCartesianCoordinateSystem) referenceCoordinateSystemFromPointAndPlane_3.createExportedCoordinateSystem();

    cadModel_1.setFaceNameAttributes(new NeoObjectVector(new Object[] {face_2}), "Fan", 135, 206, 250, 255);
	
	exportedCartesianCoordinateSystem_3.setPresentationName("CS - Fan");
 
	Face face_3 = 
      ((Face) importCadFileFeature_1.getFaceByLocation(cadmodelerBody_1,new DoubleVector(new double[] {-1.731897888822425, -0.32685680630796593, 0.23208398522230062})));

    cadModel_1.setFaceNameAttributes(new NeoObjectVector(new Object[] {face_3}), "duct_inlet", 135, 206, 250, 255);

    simulation_0.get(SolidModelManager.class).endEditCadModel(cadModel_1);

    SolidModelPart solidModelPart_1 = 
      ((SolidModelPart) compositePart_0.getChildParts().getPart("Fan"));

    simulation_0.get(SimulationPartManager.class).updateParts(new NeoObjectVector(new Object[] {solidModelPart_1}));

    PartSurface partSurface_1 = 
      ((PartSurface) solidModelPart_1.getPartSurfaceManager().getPartSurface("Faces"));

    partSurface_1.setPresentationName("duct_shroud");*/

    CadPart cadPart_2 = 
      ((CadPart) compositePart_0.getChildParts().getPart("SU_Front"));

    simulation_0.get(SolidModelManager.class).createSolidModelForCadParts(new NeoObjectVector(new Object[] {cadPart_2}));

    CadModel cadModel_2 = 
      ((CadModel) simulation_0.get(SolidModelManager.class).getObject("3D-CAD Model 1"));

    ImportCadFileFeature importCadFileFeature_2 = 
      ((ImportCadFileFeature) cadModel_2.getFeature("ImportCad 1"));

    star.cadmodeler.Body cadmodelerBody_2 = 
      ((star.cadmodeler.Body) cadModel_2.getBody("SU_Front"));

    Face face_4 = 
      ((Face) importCadFileFeature_2.getFaceByLocation(cadmodelerBody_2,new DoubleVector(new double[] {-0.003264999923726454, -0.6684729912242166, 0.23081106210451824})));

    ReferencePointFromEntityCenter referencePointFromEntityCenter_1 = 
      cadModel_2.getFeatureManager().createPointFromEntityCenter(face_4);

    referencePointFromEntityCenter_1.setColorVector(new IntVector(new int[] {-16711936}));

    referencePointFromEntityCenter_1.setEntity(face_4);

    referencePointFromEntityCenter_1.setCenterPointType(0);

    referencePointFromEntityCenter_1.markFeatureForEdit();

    cadModel_2.getFeatureManager().execute(referencePointFromEntityCenter_1);

    ReferenceCoordinateSystemFromPointAndPlane referenceCoordinateSystemFromPointAndPlane_1 = 
      cadModel_2.getFeatureManager().createCoordinateSystemFromPointAndPlane(null, null, null, face_4);

    referenceCoordinateSystemFromPointAndPlane_1.setColorVector(new IntVector(new int[] {-16711936, -16711681}));

    referenceCoordinateSystemFromPointAndPlane_1.setReferencePoint(referencePointFromEntityCenter_1);

    referenceCoordinateSystemFromPointAndPlane_1.setVertex(null);

    referenceCoordinateSystemFromPointAndPlane_1.setSketchPlane(null);

    referenceCoordinateSystemFromPointAndPlane_1.setFace(face_4);

    referenceCoordinateSystemFromPointAndPlane_1.setPlacementType(0);

    referenceCoordinateSystemFromPointAndPlane_1.markFeatureForEdit();

    cadModel_2.getFeatureManager().execute(referenceCoordinateSystemFromPointAndPlane_1);

    ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_1 = 
      (ExportedCartesianCoordinateSystem) referenceCoordinateSystemFromPointAndPlane_1.createExportedCoordinateSystem();

    simulation_0.get(SolidModelManager.class).endEditCadModel(cadModel_2);

    SolidModelPart solidModelPart_2 = 
      ((SolidModelPart) compositePart_0.getChildParts().getPart("SU_Front"));

    simulation_0.get(SimulationPartManager.class).updateParts(new NeoObjectVector(new Object[] {solidModelPart_2}));

    exportedCartesianCoordinateSystem_1.setPresentationName("CS - Wheel Front");

    /*CadPart cadPart_3 = 
      ((CadPart) compositePart_0.getChildParts().getPart("SU_Rear"));

    simulation_0.get(SolidModelManager.class).createSolidModelForCadParts(new NeoObjectVector(new Object[] {cadPart_3}));

    CadModel cadModel_3 = 
      ((CadModel) simulation_0.get(SolidModelManager.class).getObject("3D-CAD Model 4"));

    ImportCadFileFeature importCadFileFeature_3 = 
      ((ImportCadFileFeature) cadModel_3.getFeature("ImportCad 1"));

    star.cadmodeler.Body cadmodelerBody_3 = 
      ((star.cadmodeler.Body) cadModel_3.getBody("SU_Rear"));

    Face face_5 = 
      ((Face) importCadFileFeature_3.getFaceByLocation(cadmodelerBody_3,new DoubleVector(new double[] {-1.5415920799737082, -0.6706395716462835, 0.2316913523695682})));

    ReferencePointFromEntityCenter referencePointFromEntityCenter_2 = 
      cadModel_3.getFeatureManager().createPointFromEntityCenter(face_5);

    referencePointFromEntityCenter_2.setColorVector(new IntVector(new int[] {-16711936}));

    referencePointFromEntityCenter_2.setEntity(face_5);

    referencePointFromEntityCenter_2.setCenterPointType(0);

    referencePointFromEntityCenter_2.markFeatureForEdit();

    cadModel_3.getFeatureManager().execute(referencePointFromEntityCenter_2);

    ReferenceCoordinateSystemFromPointAndPlane referenceCoordinateSystemFromPointAndPlane_2 = 
      cadModel_3.getFeatureManager().createCoordinateSystemFromPointAndPlane(null, null, null, face_5);

    referenceCoordinateSystemFromPointAndPlane_2.setColorVector(new IntVector(new int[] {-16711936, -16711681}));

    referenceCoordinateSystemFromPointAndPlane_2.setReferencePoint(referencePointFromEntityCenter_2);

    referenceCoordinateSystemFromPointAndPlane_2.setVertex(null);

    referenceCoordinateSystemFromPointAndPlane_2.setSketchPlane(null);

    referenceCoordinateSystemFromPointAndPlane_2.setFace(face_5);

    referenceCoordinateSystemFromPointAndPlane_2.setPlacementType(0);

    referenceCoordinateSystemFromPointAndPlane_2.markFeatureForEdit();

    cadModel_3.getFeatureManager().execute(referenceCoordinateSystemFromPointAndPlane_2);

    ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_2 = 
      (ExportedCartesianCoordinateSystem) referenceCoordinateSystemFromPointAndPlane_2.createExportedCoordinateSystem();

    simulation_0.get(SolidModelManager.class).endEditCadModel(cadModel_3);

    SolidModelPart solidModelPart_3 = 
      ((SolidModelPart) compositePart_0.getChildParts().getPart("SU_Rear"));

    simulation_0.get(SimulationPartManager.class).updateParts(new NeoObjectVector(new Object[] {solidModelPart_3}));

    exportedCartesianCoordinateSystem_2.setPresentationName("CS - Wheel Rear");*/

// Contact Patch Fillet 1mm Front Tyre
  
  CadPart cadPart_4 = 
      ((CadPart) compositePart_0.getChildParts().getPart("Tyre_Front"));

    simulation_0.get(SolidModelManager.class).createSolidModelForCadParts(new NeoObjectVector(new Object[] {cadPart_4}));

    CadModel cadModel_4 = 
      ((CadModel) simulation_0.get(SolidModelManager.class).getObject("3D-CAD Model 2"));

    CanonicalSketchPlane canonicalSketchPlane_0 = 
      ((CanonicalSketchPlane) cadModel_4.getFeature("XY"));

    Sketch sketch_0 = 
      cadModel_4.getFeatureManager().createSketch(canonicalSketchPlane_0);

    cadModel_4.getFeatureManager().startSketchEdit(sketch_0);

    Units units_0 = 
      simulation_0.getUnitsManager().getPreferredUnits(new IntVector(new int[] {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));

    sketch_0.createRectangle(new DoubleVector(new double[] {-0.5, -1.0}), new DoubleVector(new double[] {0.5, -0.2}));

    sketch_0.markFeatureForEdit();

    cadModel_4.getFeatureManager().stopSketchEdit(sketch_0, true);

    sketch_0.setIsUptoDate(true);

    cadModel_4.getFeatureManager().rollForwardToEnd();

    ExtrusionMerge extrusionMerge_0 = 
      cadModel_4.getFeatureManager().createExtrusionMerge(sketch_0);

    extrusionMerge_0.setColorVector(new IntVector(new int[] {-16711681, -65536, -16776961, -16711936, -65536}));

    extrusionMerge_0.setDirectionOption(1);

    extrusionMerge_0.setExtrudedBodyTypeOption(0);

    extrusionMerge_0.getDistance().setValue(0.05);

    extrusionMerge_0.setDistanceOption(0);

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

    cadModel_4.getFeatureManager().execute(extrusionMerge_0);

    LineSketchPrimitive lineSketchPrimitive_0 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 1"));

    Face face_6 = 
      ((Face) extrusionMerge_0.getStartCapFace(lineSketchPrimitive_0));
    
  Fillet fillet_0 = 
      cadModel_4.getFeatureManager().createFillet(new NeoObjectVector(new Object[] {}), new NeoObjectVector(new Object[] {face_6}));

  fillet_0.setColorVector(new IntVector(new int[] {-16711936})); 

    Units units_1 = 
      ((Units) simulation_0.getUnitsManager().getObject("mm"));

    fillet_0.getRadius().setUnits(units_1);

    fillet_0.getRadius().setValue(1.0);
  
  fillet_0.setEdges(new NeoObjectVector(new Object[] {}));

    fillet_0.setFaces(new NeoObjectVector(new Object[] {face_6}));

    fillet_0.markFeatureForEdit();

    cadModel_4.getFeatureManager().execute(fillet_0);

    ExtrusionCut extrusionCut_0 = 
      cadModel_4.getFeatureManager().createExtrusionCut(sketch_0);

    extrusionCut_0.setColorVector(new IntVector(new int[] {-16711681, -65536, -16776961, -16711936, -65536}));

    extrusionCut_0.setDirectionOption(1);

    extrusionCut_0.setExtrudedBodyTypeOption(0);

    extrusionCut_0.getDistance().setValue(0.05);

    extrusionCut_0.setDistanceOption(0);

    extrusionCut_0.setCoordinateSystemOption(0);

    extrusionCut_0.setDraftOption(0);

    extrusionCut_0.setImportedCoordinateSystem(labCoordinateSystem_0);

    extrusionCut_0.getDirectionAxis().setCoordinateSystem(labCoordinateSystem_0);

    extrusionCut_0.getDirectionAxis().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {0.0, 0.0, 1.0}));

    extrusionCut_0.setFace(null);

    extrusionCut_0.setBody(null);

    extrusionCut_0.setSketch(sketch_0);

    extrusionCut_0.setExtrusionOption(0);

    BodyNameRefManager bodyNameRefManager_1 = 
      extrusionCut_0.getCutBodies();

    bodyNameRefManager_1.setBodies(new NeoObjectVector(new Object[] {}));

    extrusionCut_0.setSubtractingSelectedCutBodies(false);

    extrusionCut_0.markFeatureForEdit();

    cadModel_4.getFeatureManager().execute(extrusionCut_0);
  
  cadModel_4.update();

    simulation_0.get(SolidModelManager.class).endEditCadModel(cadModel_4);

    SolidModelPart solidModelPart_4 = 
      ((SolidModelPart) compositePart_0.getChildParts().getPart("Tyre_Front"));

    simulation_0.get(SimulationPartManager.class).updateParts(new NeoObjectVector(new Object[] {solidModelPart_4}));
 
  // Contact Patch Fillet 1mm Rear Tyre
  
     /*CadPart cadPart_5 = 
      ((CadPart) compositePart_0.getChildParts().getPart("Tyre_Rear"));

    simulation_0.get(SolidModelManager.class).createSolidModelForCadParts(new NeoObjectVector(new Object[] {cadPart_5}));

    CadModel cadModel_5 = 
      ((CadModel) simulation_0.get(SolidModelManager.class).getObject("3D-CAD Model 6"));

    CanonicalSketchPlane canonicalSketchPlane_1 = 
      ((CanonicalSketchPlane) cadModel_5.getFeature("XY"));

    Sketch sketch_1 = 
      cadModel_5.getFeatureManager().createSketch(canonicalSketchPlane_1);

    cadModel_5.getFeatureManager().startSketchEdit(sketch_1);

    sketch_1.createRectangle(new DoubleVector(new double[] {-2.0, -0.32}), new DoubleVector(new double[] {-1.05, -0.9}));

    sketch_1.markFeatureForEdit();

    cadModel_5.getFeatureManager().stopSketchEdit(sketch_1, true);

    sketch_1.setIsUptoDate(true);

    cadModel_5.getFeatureManager().rollForwardToEnd();

    ExtrusionMerge extrusionMerge_1 = 
      cadModel_5.getFeatureManager().createExtrusionMerge(sketch_1);

    extrusionMerge_1.setColorVector(new IntVector(new int[] {-16711681, -65536, -16776961, -16711936, -65536}));

    extrusionMerge_1.setDirectionOption(1);

    extrusionMerge_1.setExtrudedBodyTypeOption(0);

    extrusionMerge_1.getDistance().setValue(0.05);

    extrusionMerge_1.setDistanceOption(0);

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

    BodyNameRefManager bodyNameRefManager_2 = 
      extrusionMerge_1.getInteractionBodies();

    bodyNameRefManager_2.setBodies(new NeoObjectVector(new Object[] {}));

    extrusionMerge_1.setInteractingSelectedBodies(false);

    extrusionMerge_1.markFeatureForEdit();

    cadModel_5.getFeatureManager().execute(extrusionMerge_1);

    LineSketchPrimitive lineSketchPrimitive_1 = 
      ((LineSketchPrimitive) sketch_1.getSketchPrimitive("Line 1"));

    Face face_7 = 
      ((Face) extrusionMerge_1.getStartCapFace(lineSketchPrimitive_1));

    Fillet fillet_1 = 
      cadModel_5.getFeatureManager().createFillet(new NeoObjectVector(new Object[] {}), new NeoObjectVector(new Object[] {face_7}));

    fillet_1.setColorVector(new IntVector(new int[] {-16711936}));

    fillet_1.getRadius().setUnits(units_1);

    fillet_1.getRadius().setValue(1.0);

    fillet_1.setEdges(new NeoObjectVector(new Object[] {}));

    fillet_1.setFaces(new NeoObjectVector(new Object[] {face_7}));

    fillet_1.markFeatureForEdit();

    cadModel_5.getFeatureManager().execute(fillet_1);

    ExtrusionCut extrusionCut_1 = 
      cadModel_5.getFeatureManager().createExtrusionCut(sketch_1);

    extrusionCut_1.setColorVector(new IntVector(new int[] {-16711681, -65536, -16776961, -16711936, -65536}));

    extrusionCut_1.setDirectionOption(1);

    extrusionCut_1.setExtrudedBodyTypeOption(0);

    extrusionCut_1.getDistance().setValue(0.05);

    extrusionCut_1.setDistanceOption(0);

    extrusionCut_1.setCoordinateSystemOption(0);

    extrusionCut_1.setDraftOption(0);

    extrusionCut_1.setImportedCoordinateSystem(labCoordinateSystem_0);

    extrusionCut_1.getDirectionAxis().setCoordinateSystem(labCoordinateSystem_0);

    extrusionCut_1.getDirectionAxis().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {0.0, 0.0, 1.0}));

    extrusionCut_1.setFace(null);

    extrusionCut_1.setBody(null);

    extrusionCut_1.setSketch(sketch_1);

    extrusionCut_1.setExtrusionOption(0);

    BodyNameRefManager bodyNameRefManager_3 = 
      extrusionCut_1.getCutBodies();

    bodyNameRefManager_3.setBodies(new NeoObjectVector(new Object[] {}));

    extrusionCut_1.setSubtractingSelectedCutBodies(false);

    extrusionCut_1.markFeatureForEdit();

    cadModel_5.getFeatureManager().execute(extrusionCut_1);

    cadModel_5.update();

    simulation_0.get(SolidModelManager.class).endEditCadModel(cadModel_5);

    SolidModelPart solidModelPart_5 = 
      ((SolidModelPart) compositePart_0.getChildParts().getPart("Tyre_Rear"));

    simulation_0.get(SimulationPartManager.class).updateParts(new NeoObjectVector(new Object[] {solidModelPart_5}));*/
  }
}