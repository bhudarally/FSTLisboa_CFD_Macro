// STAR-CCM+ macro: b_CSystem_RadFanRename.java
// Written by STAR-CCM+ 14.04.011
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;
import star.cadmodeler.*;
import star.meshing.*;
import star.base.report.*;
import java.io.*;
import java.nio.*;

public class c_GeometryPreparation extends StarMacro {

  public void execute() {
    CoolingCheck();
    CoolingRF();
    WheelsRF();
    CoGAndAirRF();
  }

  private void CoolingCheck() {

    Simulation simulation_0 = 
      getActiveSimulation();

    CompositePart compositePart_1 = 
      ((CompositePart) simulation_0.get(SimulationPartManager.class).getPart("CFD_FULL_ASSEMBLY"));

    CompositePart compositePart_0 = 
      ((CompositePart) compositePart_1.getChildParts().getPart("PT"));

    // Cooling Mode (1 Active, 0 Passive)

    try {

    CadPart cadPart_0 = 
      ((CadPart) compositePart_0.getChildParts().getPart("FAN_R"));

    UserFieldFunction userFieldFunction_0 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();
    userFieldFunction_0.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);
    userFieldFunction_0.setPresentationName("Cooling Mode");
    userFieldFunction_0.setFunctionName("cooling_mode");
    userFieldFunction_0.setDefinition("1");

    ExpressionReport expressionReport_0 = 
      simulation_0.getReportManager().createReport(ExpressionReport.class);
    expressionReport_0.setPresentationName("Cooling Mode");
    expressionReport_0.setDefinition("${cooling_mode}");

    try {
      ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Cooling]")).setPresentationName("[Cooling]");;
    }
    catch (Exception e)
    {
      simulation_0.getReportManager().getGroupsManager().createGroup("[Cooling]");
    }
    ((ClientServerObjectGroup) (ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Cooling]")).getGroupsManager().groupObjects("[Cooling]", new NeoObjectVector(new Object[] {expressionReport_0}), true);

    
    try {
      ((ClientServerObjectGroup) simulation_0.getFieldFunctionManager().getGroupsManager().getObject("[Cooling]")).setPresentationName("[Cooling]");;
    }
    catch (Exception eee)
    {
      simulation_0.getFieldFunctionManager().getGroupsManager().createGroup("[Cooling]");
    }
    ((ClientServerObjectGroup) simulation_0.getFieldFunctionManager().getGroupsManager().getObject("[Cooling]")).getGroupsManager().groupObjects("[Cooling]", new NeoObjectVector(new Object[] {userFieldFunction_0}), true);

  }
  catch (Exception e){

    UserFieldFunction userFieldFunction_0 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();
    userFieldFunction_0.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);
    userFieldFunction_0.setPresentationName("Cooling Mode");
    userFieldFunction_0.setFunctionName("cooling_mode");
    userFieldFunction_0.setDefinition("0");

    ExpressionReport expressionReport_0 = 
      simulation_0.getReportManager().createReport(ExpressionReport.class);
    expressionReport_0.setPresentationName("Cooling Mode");
    expressionReport_0.setDefinition("${cooling_mode}");

    try {
      ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Cooling]")).setPresentationName("[Cooling]");;
    }
    catch (Exception ee)
    {
      simulation_0.getReportManager().getGroupsManager().createGroup("[Cooling]");
    }
    ((ClientServerObjectGroup) (ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Cooling]")).getGroupsManager().groupObjects("[Cooling]", new NeoObjectVector(new Object[] {expressionReport_0}), true);

    try {
      ((ClientServerObjectGroup) simulation_0.getFieldFunctionManager().getGroupsManager().getObject("[Cooling]")).setPresentationName("[Cooling]");;
    }
    catch (Exception eeee)
    {
      simulation_0.getFieldFunctionManager().getGroupsManager().createGroup("[Cooling]");
    }
    ((ClientServerObjectGroup) simulation_0.getFieldFunctionManager().getGroupsManager().getObject("[Cooling]")).getGroupsManager().groupObjects("[Cooling]", new NeoObjectVector(new Object[] {userFieldFunction_0}), true);

  }

  }

  private void CoolingRF() {

    Simulation simulation_0 = 
      getActiveSimulation();
    
  double Roll = simulation_0.getReportManager().getReport("Roll [deg]").getReportMonitorValue();
  double Yaw = simulation_0.getReportManager().getReport("Yaw [rad]").getReportMonitorValue();
  double CoGX = simulation_0.getReportManager().getReport("CoGX").getReportMonitorValue();
  double CoGZ = simulation_0.getReportManager().getReport("CoGZ").getReportMonitorValue();
  double CornerRadius = simulation_0.getReportManager().getReport("Corner Radius [m]").getReportMonitorValue(); 
  double CoolingMode = simulation_0.getReportManager().getReport("Cooling Mode").getReportMonitorValue(); 

    CompositePart compositePart_1 = 
      ((CompositePart) simulation_0.get(SimulationPartManager.class).getPart("CFD_FULL_ASSEMBLY"));

    CompositePart compositePart_0 = 
      ((CompositePart) compositePart_1.getChildParts().getPart("PT"));

    
      // Fan Left C.System //
    if ( CoolingMode != 0) {
    if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
  CadPart cadPart_1 = 
    ((CadPart) compositePart_0.getChildParts().getPart("FAN_L"));
  simulation_0.get(SolidModelManager.class).createSolidModelForCadParts(new NeoObjectVector(new Object[] {cadPart_1}));

  CadModel cadModel_0 = 
    ((CadModel) simulation_0.get(SolidModelManager.class).getObject("3D-CAD Model 1"));
  cadModel_0.resetSystemOptions();
  cadModel_0.setPresentationName("FAN_L");

  star.cadmodeler.Body cadmodelerBody_0 = 
    ((star.cadmodeler.Body) cadModel_0.getBody("FAN_L"));

  NamedFaces namedFaces_0 = 
    ((NamedFaces) cadmodelerBody_0.getNamedFacesManager().getObject("ColoredFace1"));

  namedFaces_0.setPresentationName("DUCT_L");


  Face face_0 = 
    ((Face) cadmodelerBody_0.getFace("FAN_L"));
  ReferencePointFromEntityCenter referencePointFromEntityCenter_0 = 
    cadModel_0.getFeatureManager().createPointFromEntityCenter(face_0);
  referencePointFromEntityCenter_0.setEntity(face_0);
  referencePointFromEntityCenter_0.setCenterPointType(0);
  referencePointFromEntityCenter_0.markFeatureForEdit();
  cadModel_0.getFeatureManager().execute(referencePointFromEntityCenter_0);

  ReferenceCoordinateSystemFromPointAndPlane referenceCoordinateSystemFromPointAndPlane_0 = 
    cadModel_0.getFeatureManager().createCoordinateSystemFromPointAndPlane(referencePointFromEntityCenter_0, null, null, null);
  referenceCoordinateSystemFromPointAndPlane_0.setReferencePoint(referencePointFromEntityCenter_0);
  referenceCoordinateSystemFromPointAndPlane_0.setVertex(null);
  referenceCoordinateSystemFromPointAndPlane_0.setSketchPlane(null);
  referenceCoordinateSystemFromPointAndPlane_0.setFace(face_0);
  referenceCoordinateSystemFromPointAndPlane_0.setPlacementType(0);
  referenceCoordinateSystemFromPointAndPlane_0.markFeatureForEdit();
  cadModel_0.getFeatureManager().execute(referenceCoordinateSystemFromPointAndPlane_0);
  referenceCoordinateSystemFromPointAndPlane_0.setPresentationName("Fan (L) - Axis");

  ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_0 = 
    (ExportedCartesianCoordinateSystem) referenceCoordinateSystemFromPointAndPlane_0.createExportedCoordinateSystem();
  cadModel_0.update();
  simulation_0.get(SolidModelManager.class).endEditCadModel(cadModel_0);

  SolidModelPart solidModelPart_0 = 
    ((SolidModelPart) compositePart_0.getChildParts().getPart("FAN_L"));
  PartSurface partSurface_0 = 
      ((PartSurface) solidModelPart_0.getPartSurfaceManager().getPartSurface("ColoredFace1"));
  solidModelPart_0.deleteMeshPartSurfaces(new NeoObjectVector(new Object[] {partSurface_0}));
  simulation_0.get(SimulationPartManager.class).updateParts(new NeoObjectVector(new Object[] {solidModelPart_0}));
  }

        // Fan Right C.System //
  CadPart cadPart_0 = 
    ((CadPart) compositePart_0.getChildParts().getPart("FAN_R"));
  simulation_0.get(SolidModelManager.class).createSolidModelForCadParts(new NeoObjectVector(new Object[] {cadPart_0}));

  CadModel cadModel_1 = 
    ((CadModel) simulation_0.get(SolidModelManager.class).getObject("3D-CAD Model 1"));
  cadModel_1.resetSystemOptions();
  cadModel_1.setPresentationName("FAN_R");

  star.cadmodeler.Body cadmodelerBody_1 = 
    ((star.cadmodeler.Body) cadModel_1.getBody("FAN_R"));

  NamedFaces namedFaces_1 = 
    ((NamedFaces) cadmodelerBody_1.getNamedFacesManager().getObject("ColoredFace1"));

  namedFaces_1.setPresentationName("DUCT_R");

  Face face_1 = 
    ((Face) cadmodelerBody_1.getFace("FAN_R"));
  ReferencePointFromEntityCenter referencePointFromEntityCenter_1 = 
    cadModel_1.getFeatureManager().createPointFromEntityCenter(face_1);
  referencePointFromEntityCenter_1.setEntity(face_1);
  referencePointFromEntityCenter_1.setCenterPointType(0);
  referencePointFromEntityCenter_1.markFeatureForEdit();
  cadModel_1.getFeatureManager().execute(referencePointFromEntityCenter_1);

  ReferenceCoordinateSystemFromPointAndPlane referenceCoordinateSystemFromPointAndPlane_1 = 
    cadModel_1.getFeatureManager().createCoordinateSystemFromPointAndPlane(referencePointFromEntityCenter_1, null, null, null);
  referenceCoordinateSystemFromPointAndPlane_1.setReferencePoint(referencePointFromEntityCenter_1);
  referenceCoordinateSystemFromPointAndPlane_1.setVertex(null);
  referenceCoordinateSystemFromPointAndPlane_1.setSketchPlane(null);
  referenceCoordinateSystemFromPointAndPlane_1.setFace(face_1);
  referenceCoordinateSystemFromPointAndPlane_1.setPlacementType(0);
  referenceCoordinateSystemFromPointAndPlane_1.markFeatureForEdit();
  cadModel_1.getFeatureManager().execute(referenceCoordinateSystemFromPointAndPlane_1);
  referenceCoordinateSystemFromPointAndPlane_1.setPresentationName("Fan (R) - Axis");

  ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_1 = 
    (ExportedCartesianCoordinateSystem) referenceCoordinateSystemFromPointAndPlane_1.createExportedCoordinateSystem();
  cadModel_1.update();
  simulation_0.get(SolidModelManager.class).endEditCadModel(cadModel_1);

  SolidModelPart solidModelPart_1 = 
    ((SolidModelPart) compositePart_0.getChildParts().getPart("FAN_R"));
    PartSurface partSurface_1 = 
      ((PartSurface) solidModelPart_1.getPartSurfaceManager().getPartSurface("ColoredFace1"));
  solidModelPart_1.deleteMeshPartSurfaces(new NeoObjectVector(new Object[] {partSurface_1}));
  simulation_0.get(SimulationPartManager.class).updateParts(new NeoObjectVector(new Object[] {solidModelPart_1}));
}

    // Radiator Left C.System //
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    CadPart cadPart_2 = 
      ((CadPart) compositePart_0.getChildParts().getPart("RADIATOR_L"));
    simulation_0.get(SolidModelManager.class).createSolidModelForCadParts(new NeoObjectVector(new Object[] {cadPart_2}));
    CadModel cadModel_2 = 
      ((CadModel) simulation_0.get(SolidModelManager.class).getObject("3D-CAD Model 1"));
    cadModel_2.resetSystemOptions();
    cadModel_2.setPresentationName("RADIATOR_L");

    star.cadmodeler.Body cadmodelerBody_2 = 
      ((star.cadmodeler.Body) cadModel_2.getBody("RADIATOR_L"));

    NamedFaces namedFaces_2 = 
    ((NamedFaces) cadmodelerBody_2.getNamedFacesManager().getObject("ColoredFace1"));

    namedFaces_2.setPresentationName("SHROUD_L");

    Face face_2 = 
      ((Face) cadmodelerBody_2.getFace("RADIATOR_INLET_L"));
    ReferencePointFromEntityCenter referencePointFromEntityCenter_2 = 
      cadModel_2.getFeatureManager().createPointFromEntityCenter(face_2);
    referencePointFromEntityCenter_2.setEntity(face_2);
    referencePointFromEntityCenter_2.setCenterPointType(0);
    referencePointFromEntityCenter_2.markFeatureForEdit();
    cadModel_2.getFeatureManager().execute(referencePointFromEntityCenter_2);

      ReferenceCoordinateSystemFromPointAndPlane referenceCoordinateSystemFromPointAndPlane_2 = 
    cadModel_2.getFeatureManager().createCoordinateSystemFromPointAndPlane(referencePointFromEntityCenter_2, null, null, null);
  referenceCoordinateSystemFromPointAndPlane_2.setReferencePoint(referencePointFromEntityCenter_2);
  referenceCoordinateSystemFromPointAndPlane_2.setVertex(null);
  referenceCoordinateSystemFromPointAndPlane_2.setSketchPlane(null);
  referenceCoordinateSystemFromPointAndPlane_2.setFace(face_2);
  referenceCoordinateSystemFromPointAndPlane_2.setPlacementType(0);
  referenceCoordinateSystemFromPointAndPlane_2.markFeatureForEdit();
  cadModel_2.getFeatureManager().execute(referenceCoordinateSystemFromPointAndPlane_2);
  referenceCoordinateSystemFromPointAndPlane_2.setPresentationName("Radiator (L) - CSys");

    ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_2 = 
      (ExportedCartesianCoordinateSystem) referenceCoordinateSystemFromPointAndPlane_2.createExportedCoordinateSystem();
    cadModel_2.update();
    simulation_0.get(SolidModelManager.class).endEditCadModel(cadModel_2);


    SolidModelPart solidModelPart_2 = 
      ((SolidModelPart) compositePart_0.getChildParts().getPart("RADIATOR_L"));
    PartSurface partSurface_2 = 
      ((PartSurface) solidModelPart_2.getPartSurfaceManager().getPartSurface("ColoredFace1"));
    solidModelPart_2.deleteMeshPartSurfaces(new NeoObjectVector(new Object[] {partSurface_2}));
    simulation_0.get(SimulationPartManager.class).updateParts(new NeoObjectVector(new Object[] {solidModelPart_2}));
  }

    // Radiator Right C.System //

    CadPart cadPart_3 = 
      ((CadPart) compositePart_0.getChildParts().getPart("RADIATOR_R"));
    simulation_0.get(SolidModelManager.class).createSolidModelForCadParts(new NeoObjectVector(new Object[] {cadPart_3}));
    CadModel cadModel_3 = 
      ((CadModel) simulation_0.get(SolidModelManager.class).getObject("3D-CAD Model 1"));
    cadModel_3.resetSystemOptions();
    cadModel_3.setPresentationName("RADIATOR_R");

    star.cadmodeler.Body cadmodelerBody_3 = 
      ((star.cadmodeler.Body) cadModel_3.getBody("RADIATOR_R"));

    NamedFaces namedFaces_3 = 
    ((NamedFaces) cadmodelerBody_3.getNamedFacesManager().getObject("ColoredFace1"));

    namedFaces_3.setPresentationName("SHROUD_R");

    Face face_3 = 
      ((Face) cadmodelerBody_3.getFace("RADIATOR_INLET_R"));
    ReferencePointFromEntityCenter referencePointFromEntityCenter_3 = 
      cadModel_3.getFeatureManager().createPointFromEntityCenter(face_3);
    referencePointFromEntityCenter_3.setEntity(face_3);
    referencePointFromEntityCenter_3.setCenterPointType(0);
    referencePointFromEntityCenter_3.markFeatureForEdit();
    cadModel_3.getFeatureManager().execute(referencePointFromEntityCenter_3);

      ReferenceCoordinateSystemFromPointAndPlane referenceCoordinateSystemFromPointAndPlane_3 = 
    cadModel_3.getFeatureManager().createCoordinateSystemFromPointAndPlane(referencePointFromEntityCenter_3, null, null, null);
  referenceCoordinateSystemFromPointAndPlane_3.setReferencePoint(referencePointFromEntityCenter_3);
  referenceCoordinateSystemFromPointAndPlane_3.setVertex(null);
  referenceCoordinateSystemFromPointAndPlane_3.setSketchPlane(null);
  referenceCoordinateSystemFromPointAndPlane_3.setFace(face_3);
  referenceCoordinateSystemFromPointAndPlane_3.setPlacementType(0);
  referenceCoordinateSystemFromPointAndPlane_3.markFeatureForEdit();
  cadModel_3.getFeatureManager().execute(referenceCoordinateSystemFromPointAndPlane_3);
  referenceCoordinateSystemFromPointAndPlane_3.setPresentationName("Radiator (R) - CSys");

    ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_3 = 
      (ExportedCartesianCoordinateSystem) referenceCoordinateSystemFromPointAndPlane_3.createExportedCoordinateSystem();
    cadModel_3.update();
    simulation_0.get(SolidModelManager.class).endEditCadModel(cadModel_3);

    SolidModelPart solidModelPart_3 = 
      ((SolidModelPart) compositePart_0.getChildParts().getPart("RADIATOR_R"));
    PartSurface partSurface_3 = 
      ((PartSurface) solidModelPart_3.getPartSurfaceManager().getPartSurface("ColoredFace1"));
    solidModelPart_3.deleteMeshPartSurfaces(new NeoObjectVector(new Object[] {partSurface_3}));
    simulation_0.get(SimulationPartManager.class).updateParts(new NeoObjectVector(new Object[] {solidModelPart_3}));
}
 
 // Creates Reference Frames of the Wheels //
 
  private void WheelsRF() {

    Simulation simulation_0 = 
      getActiveSimulation();

  double Roll = simulation_0.getReportManager().getReport("Roll [deg]").getReportMonitorValue();
  double Yaw = simulation_0.getReportManager().getReport("Yaw [rad]").getReportMonitorValue();
  double CoGX = simulation_0.getReportManager().getReport("CoGX").getReportMonitorValue();
  double CoGZ = simulation_0.getReportManager().getReport("CoGZ").getReportMonitorValue();
  double CornerRadius = simulation_0.getReportManager().getReport("Corner Radius [m]").getReportMonitorValue(); 

  CompositePart compositePart_2 = 
      ((CompositePart) simulation_0.get(SimulationPartManager.class).getPart("CFD_FULL_ASSEMBLY"));

  CompositePart compositePart_1 = 
      ((CompositePart) compositePart_2.getChildParts().getPart("SU"));

  CompositePart compositePart_0 = 
      ((CompositePart) compositePart_1.getChildParts().getPart("SU_NO_TYRES")); 

    // Front Left Wheel C.System //

    if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
  CadPart cadPart_4 = 
      ((CadPart) compositePart_0.getChildParts().getPart("SU_FL"));
    simulation_0.get(SolidModelManager.class).createSolidModelForCadParts(new NeoObjectVector(new Object[] {cadPart_4}));
    CadModel cadModel_4 = 
      ((CadModel) simulation_0.get(SolidModelManager.class).getObject("3D-CAD Model 1"));
    cadModel_4.resetSystemOptions();
    cadModel_4.setPresentationName("SU_FL");

    star.cadmodeler.Body cadmodelerBody_4 = 
      ((star.cadmodeler.Body) cadModel_4.getBody("UPR_FL"));
    Face face_4 = 
      ((Face) cadmodelerBody_4.getFace("FL_CENTER"));
    ReferencePointFromEntityCenter referencePointFromEntityCenter_4 = 
      cadModel_4.getFeatureManager().createPointFromEntityCenter(face_4);
    referencePointFromEntityCenter_4.setEntity(face_4);
    referencePointFromEntityCenter_4.setCenterPointType(0);
    cadModel_4.getFeatureManager().execute(referencePointFromEntityCenter_4);

    ReferenceCoordinateSystemFromPointAndPlane referenceCoordinateSystemFromPointAndPlane_4 = 
      cadModel_4.getFeatureManager().createCoordinateSystemFromPointAndPlane(referencePointFromEntityCenter_4, null, null, null);
    referenceCoordinateSystemFromPointAndPlane_4.setReferencePoint(referencePointFromEntityCenter_4);
    referenceCoordinateSystemFromPointAndPlane_4.setVertex(null);
    referenceCoordinateSystemFromPointAndPlane_4.setSketchPlane(null);
    referenceCoordinateSystemFromPointAndPlane_4.setFace(face_4);
    referenceCoordinateSystemFromPointAndPlane_4.setPlacementType(0);
    cadModel_4.getFeatureManager().execute(referenceCoordinateSystemFromPointAndPlane_4);
    referenceCoordinateSystemFromPointAndPlane_4.setPresentationName("CSys - FL");

    ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_4 = 
      (ExportedCartesianCoordinateSystem) referenceCoordinateSystemFromPointAndPlane_4.createExportedCoordinateSystem();
    cadModel_4.update();
    simulation_0.get(SolidModelManager.class).endEditCadModel(cadModel_4);
    SolidModelPart solidModelPart_4 = 
      ((SolidModelPart) compositePart_0.getChildParts().getPart("SU_FL"));
    simulation_0.get(SimulationPartManager.class).updateParts(new NeoObjectVector(new Object[] {solidModelPart_4}));
  }

  // Front Right Wheel C.System //

  CadPart cadPart_5 = 
      ((CadPart) compositePart_0.getChildParts().getPart("SU_FR"));
    simulation_0.get(SolidModelManager.class).createSolidModelForCadParts(new NeoObjectVector(new Object[] {cadPart_5}));
    CadModel cadModel_5 = 
      ((CadModel) simulation_0.get(SolidModelManager.class).getObject("3D-CAD Model 1"));
    cadModel_5.resetSystemOptions();
    cadModel_5.setPresentationName("SU_FR");

    star.cadmodeler.Body cadmodelerBody_5 = 
      ((star.cadmodeler.Body) cadModel_5.getBody("UPR_FR"));
    Face face_5 = 
      ((Face) cadmodelerBody_5.getFace("FR_CENTER"));
    ReferencePointFromEntityCenter referencePointFromEntityCenter_5 = 
      cadModel_5.getFeatureManager().createPointFromEntityCenter(face_5);
    referencePointFromEntityCenter_5.setEntity(face_5);
    referencePointFromEntityCenter_5.setCenterPointType(0);
    cadModel_5.getFeatureManager().execute(referencePointFromEntityCenter_5);

    ReferenceCoordinateSystemFromPointAndPlane referenceCoordinateSystemFromPointAndPlane_5 = 
      cadModel_5.getFeatureManager().createCoordinateSystemFromPointAndPlane(referencePointFromEntityCenter_5, null, null, null);
    referenceCoordinateSystemFromPointAndPlane_5.setReferencePoint(referencePointFromEntityCenter_5);
    referenceCoordinateSystemFromPointAndPlane_5.setVertex(null);
    referenceCoordinateSystemFromPointAndPlane_5.setSketchPlane(null);
    referenceCoordinateSystemFromPointAndPlane_5.setFace(face_5);
    referenceCoordinateSystemFromPointAndPlane_5.setPlacementType(0);
    cadModel_5.getFeatureManager().execute(referenceCoordinateSystemFromPointAndPlane_5);
    referenceCoordinateSystemFromPointAndPlane_5.setPresentationName("CSys - FR");

    ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_5 = 
      (ExportedCartesianCoordinateSystem) referenceCoordinateSystemFromPointAndPlane_5.createExportedCoordinateSystem();
    cadModel_5.update();
    simulation_0.get(SolidModelManager.class).endEditCadModel(cadModel_5);
    SolidModelPart solidModelPart_5 = 
      ((SolidModelPart) compositePart_0.getChildParts().getPart("SU_FR"));
    simulation_0.get(SimulationPartManager.class).updateParts(new NeoObjectVector(new Object[] {solidModelPart_5}));

    // Rear Left Wheel C.System //

    if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
  CadPart cadPart_6 = 
      ((CadPart) compositePart_0.getChildParts().getPart("SU_RL"));
    simulation_0.get(SolidModelManager.class).createSolidModelForCadParts(new NeoObjectVector(new Object[] {cadPart_6}));
    CadModel cadModel_6 = 
      ((CadModel) simulation_0.get(SolidModelManager.class).getObject("3D-CAD Model 1"));
    cadModel_6.resetSystemOptions();
    cadModel_6.setPresentationName("SU_RL");

    star.cadmodeler.Body cadmodelerBody_6 = 
      ((star.cadmodeler.Body) cadModel_6.getBody("UPR_RL"));
    Face face_6 = 
      ((Face) cadmodelerBody_6.getFace("RL_CENTER"));
    ReferencePointFromEntityCenter referencePointFromEntityCenter_6 = 
      cadModel_6.getFeatureManager().createPointFromEntityCenter(face_6);
    referencePointFromEntityCenter_6.setEntity(face_6);
    referencePointFromEntityCenter_6.setCenterPointType(0);
    cadModel_6.getFeatureManager().execute(referencePointFromEntityCenter_6);

    ReferenceCoordinateSystemFromPointAndPlane referenceCoordinateSystemFromPointAndPlane_6 = 
      cadModel_6.getFeatureManager().createCoordinateSystemFromPointAndPlane(referencePointFromEntityCenter_6, null, null, null);
    referenceCoordinateSystemFromPointAndPlane_6.setReferencePoint(referencePointFromEntityCenter_6);
    referenceCoordinateSystemFromPointAndPlane_6.setVertex(null);
    referenceCoordinateSystemFromPointAndPlane_6.setSketchPlane(null);
    referenceCoordinateSystemFromPointAndPlane_6.setFace(face_6);
    referenceCoordinateSystemFromPointAndPlane_6.setPlacementType(0);
    cadModel_6.getFeatureManager().execute(referenceCoordinateSystemFromPointAndPlane_6);
    referenceCoordinateSystemFromPointAndPlane_6.setPresentationName("CSys - RL");

    ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_6 = 
      (ExportedCartesianCoordinateSystem) referenceCoordinateSystemFromPointAndPlane_6.createExportedCoordinateSystem();
    cadModel_6.update();
    simulation_0.get(SolidModelManager.class).endEditCadModel(cadModel_6);
    SolidModelPart solidModelPart_6 = 
      ((SolidModelPart) compositePart_0.getChildParts().getPart("SU_RL"));
    simulation_0.get(SimulationPartManager.class).updateParts(new NeoObjectVector(new Object[] {solidModelPart_6}));
  }

  // Rear Right Wheel C.System //

  CadPart cadPart_7 = 
      ((CadPart) compositePart_0.getChildParts().getPart("SU_RR"));
    simulation_0.get(SolidModelManager.class).createSolidModelForCadParts(new NeoObjectVector(new Object[] {cadPart_7}));
    CadModel cadModel_7 = 
      ((CadModel) simulation_0.get(SolidModelManager.class).getObject("3D-CAD Model 1"));
    cadModel_7.resetSystemOptions();
    cadModel_7.setPresentationName("SU_RR");

    star.cadmodeler.Body cadmodelerBody_7 = 
      ((star.cadmodeler.Body) cadModel_7.getBody("UPR_RR"));
    Face face_7 = 
      ((Face) cadmodelerBody_7.getFace("RR_CENTER"));
    ReferencePointFromEntityCenter referencePointFromEntityCenter_7 = 
      cadModel_7.getFeatureManager().createPointFromEntityCenter(face_7);
    referencePointFromEntityCenter_7.setEntity(face_7);
    referencePointFromEntityCenter_7.setCenterPointType(0);
    cadModel_7.getFeatureManager().execute(referencePointFromEntityCenter_7);

    ReferenceCoordinateSystemFromPointAndPlane referenceCoordinateSystemFromPointAndPlane_7 = 
      cadModel_7.getFeatureManager().createCoordinateSystemFromPointAndPlane(referencePointFromEntityCenter_7, null, null, null);
    referenceCoordinateSystemFromPointAndPlane_7.setReferencePoint(referencePointFromEntityCenter_7);
    referenceCoordinateSystemFromPointAndPlane_7.setVertex(null);
    referenceCoordinateSystemFromPointAndPlane_7.setSketchPlane(null);
    referenceCoordinateSystemFromPointAndPlane_7.setFace(face_7);
    referenceCoordinateSystemFromPointAndPlane_7.setPlacementType(0);
    cadModel_7.getFeatureManager().execute(referenceCoordinateSystemFromPointAndPlane_7);
    referenceCoordinateSystemFromPointAndPlane_7.setPresentationName("CSys - RR");

    ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_7 = 
      (ExportedCartesianCoordinateSystem) referenceCoordinateSystemFromPointAndPlane_7.createExportedCoordinateSystem();
    cadModel_7.update();
    simulation_0.get(SolidModelManager.class).endEditCadModel(cadModel_7);
    SolidModelPart solidModelPart_7 = 
      ((SolidModelPart) compositePart_0.getChildParts().getPart("SU_RR"));
    simulation_0.get(SimulationPartManager.class).updateParts(new NeoObjectVector(new Object[] {solidModelPart_7}));

}

  private void CoGAndAirRF() {

  Simulation simulation_0 = 
      getActiveSimulation();

  double Roll = simulation_0.getReportManager().getReport("Roll [deg]").getReportMonitorValue();
  double Yaw = simulation_0.getReportManager().getReport("Yaw [rad]").getReportMonitorValue();
  double CoGX = simulation_0.getReportManager().getReport("CoGX").getReportMonitorValue();
  double CoGZ = simulation_0.getReportManager().getReport("CoGZ").getReportMonitorValue();
  double CornerRadius = simulation_0.getReportManager().getReport("Corner Radius [m]").getReportMonitorValue(); 

  CompositePart compositePart_0 = 
      ((CompositePart) simulation_0.get(SimulationPartManager.class).getPart("CFD_FULL_ASSEMBLY")); 


   // CoG & Air CS //
  
  Units units_0 = 
      ((Units) simulation_0.getUnitsManager().getObject("m"));
    
  Units units_1 = 
      simulation_0.getUnitsManager().getPreferredUnits(new IntVector(new int[] {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));

    LabCoordinateSystem labCoordinateSystem_0 = 
      simulation_0.getCoordinateSystemManager().getLabCoordinateSystem();
  
  CartesianCoordinateSystem cartesianCoordinateSystem_0 = 
      labCoordinateSystem_0.getLocalCoordinateSystemManager().createLocalCoordinateSystem(CartesianCoordinateSystem.class, "Cartesian");
    cartesianCoordinateSystem_0.setPresentationName("CoG");
    cartesianCoordinateSystem_0.getOrigin().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {CoGX, 0.0, CoGZ})); 
  
  CartesianCoordinateSystem cartesianCoordinateSystem_1 = 
      labCoordinateSystem_0.getLocalCoordinateSystemManager().createLocalCoordinateSystem(CartesianCoordinateSystem.class, "Cartesian");
    cartesianCoordinateSystem_1.setPresentationName("Air Orientation - Car");
    cartesianCoordinateSystem_1.getOrigin().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {CoGX, 0.0, 0.0}));
    try {
  cartesianCoordinateSystem_1.getLocalCoordinateSystemManager().rotateLocalCoordinateSystems(new NeoObjectVector(new Object[] {cartesianCoordinateSystem_1}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), new NeoObjectVector(new Object[] {units_0, units_0, units_0}), Yaw, cartesianCoordinateSystem_1);
  } catch (Exception e){}
  
  //Reference Frame Coordinate System
  if ( CornerRadius != 0 ) {
  CylindricalCoordinateSystem cylindricalCoordinateSystem_0 = 
      cartesianCoordinateSystem_1.getLocalCoordinateSystemManager().createLocalCoordinateSystem(CylindricalCoordinateSystem.class, "Cylindrical");
  cylindricalCoordinateSystem_0.getOrigin().setUnits1(units_0);
  cylindricalCoordinateSystem_0.setBasis0(new DoubleVector(new double[] {0.0, -1.0, 0.0}));
  cylindricalCoordinateSystem_0.setBasis1(new DoubleVector(new double[] {1.0, 0.0, 0.0}));
  cylindricalCoordinateSystem_0.getOrigin().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {0.0, CornerRadius , 0.0}));
  cylindricalCoordinateSystem_0.setPresentationName("Air Center");
  }
  //} 
  }

}