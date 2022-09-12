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
    ContactPatch();
  }

  private void CoolingCheck() {

    Simulation simulation_0 = 
      getActiveSimulation();

    CompositePart compositePart_0 = 
      ((CompositePart) simulation_0.get(SimulationPartManager.class).getPart("Car"));

    // Cooling Mode (1 Active, 0 Passive)

    try {

    CadPart cadPart_1 = 
      ((CadPart) compositePart_0.getChildParts().getPart("Fan_R"));

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

    CompositePart compositePart_0 = 
      ((CompositePart) simulation_0.get(SimulationPartManager.class).getPart("Car"));

    
      // Fan Left C.System //
    if ( CoolingMode != 0) {
    if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
  CadPart cadPart_0 = 
    ((CadPart) compositePart_0.getChildParts().getPart("Fan_L"));
  simulation_0.get(SolidModelManager.class).createSolidModelForCadParts(new NeoObjectVector(new Object[] {cadPart_0}));

  CadModel cadModel_0 = 
    ((CadModel) simulation_0.get(SolidModelManager.class).getObject("3D-CAD Model 1"));
  cadModel_0.resetSystemOptions();
  cadModel_0.setPresentationName("Fan_L");

  star.cadmodeler.Body cadmodelerBody_0 = 
    ((star.cadmodeler.Body) cadModel_0.getBody("Fan_L"));

  cadmodelerBody_0.setUnNamedFacesDefaultAttributeName("Duct_L");

  Face face_0 = 
    ((Face) cadmodelerBody_0.getFace("Fan_L"));
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
    ((SolidModelPart) compositePart_0.getChildParts().getPart("Fan_L"));
  PartSurface partSurface_0 = 
      ((PartSurface) solidModelPart_0.getPartSurfaceManager().getPartSurface("Faces"));
  solidModelPart_0.deleteMeshPartSurfaces(new NeoObjectVector(new Object[] {partSurface_0}));
  simulation_0.get(SimulationPartManager.class).updateParts(new NeoObjectVector(new Object[] {solidModelPart_0}));
  }

        // Fan Right C.System //
  CadPart cadPart_1 = 
    ((CadPart) compositePart_0.getChildParts().getPart("Fan_R"));
  simulation_0.get(SolidModelManager.class).createSolidModelForCadParts(new NeoObjectVector(new Object[] {cadPart_1}));

  CadModel cadModel_1 = 
    ((CadModel) simulation_0.get(SolidModelManager.class).getObject("3D-CAD Model 1"));
  cadModel_1.resetSystemOptions();
  cadModel_1.setPresentationName("Fan_R");

  star.cadmodeler.Body cadmodelerBody_1 = 
    ((star.cadmodeler.Body) cadModel_1.getBody("Fan_R"));

  cadmodelerBody_1.setUnNamedFacesDefaultAttributeName("Duct_R");

  Face face_1 = 
    ((Face) cadmodelerBody_1.getFace("Fan_R"));
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
    ((SolidModelPart) compositePart_0.getChildParts().getPart("Fan_R"));
    PartSurface partSurface_1 = 
      ((PartSurface) solidModelPart_1.getPartSurfaceManager().getPartSurface("Faces"));
  solidModelPart_1.deleteMeshPartSurfaces(new NeoObjectVector(new Object[] {partSurface_1}));
  simulation_0.get(SimulationPartManager.class).updateParts(new NeoObjectVector(new Object[] {solidModelPart_1}));
}

    // Radiator Left C.System //
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    CadPart cadPart_2 = 
      ((CadPart) compositePart_0.getChildParts().getPart("Radiator_L"));
    simulation_0.get(SolidModelManager.class).createSolidModelForCadParts(new NeoObjectVector(new Object[] {cadPart_2}));
    CadModel cadModel_2 = 
      ((CadModel) simulation_0.get(SolidModelManager.class).getObject("3D-CAD Model 1"));
    cadModel_2.resetSystemOptions();
    cadModel_2.setPresentationName("Radiator_L");

    star.cadmodeler.Body cadmodelerBody_2 = 
      ((star.cadmodeler.Body) cadModel_2.getBody("Radiator_L"));

    cadmodelerBody_2.setUnNamedFacesDefaultAttributeName("Shroud_L");

    Face face_2 = 
      ((Face) cadmodelerBody_2.getFace("Radiator_Inlet_L"));
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
      ((SolidModelPart) compositePart_0.getChildParts().getPart("Radiator_L"));
    PartSurface partSurface_2 = 
      ((PartSurface) solidModelPart_2.getPartSurfaceManager().getPartSurface("Faces"));
    solidModelPart_2.deleteMeshPartSurfaces(new NeoObjectVector(new Object[] {partSurface_2}));
    simulation_0.get(SimulationPartManager.class).updateParts(new NeoObjectVector(new Object[] {solidModelPart_2}));
  }

    // Radiator Right C.System //

    CadPart cadPart_3 = 
      ((CadPart) compositePart_0.getChildParts().getPart("Radiator_R"));
    simulation_0.get(SolidModelManager.class).createSolidModelForCadParts(new NeoObjectVector(new Object[] {cadPart_3}));
    CadModel cadModel_3 = 
      ((CadModel) simulation_0.get(SolidModelManager.class).getObject("3D-CAD Model 1"));
    cadModel_3.resetSystemOptions();
    cadModel_3.setPresentationName("Radiator_R");

    star.cadmodeler.Body cadmodelerBody_3 = 
      ((star.cadmodeler.Body) cadModel_3.getBody("Radiator_R"));

    cadmodelerBody_3.setUnNamedFacesDefaultAttributeName("Shroud_R");

    Face face_3 = 
      ((Face) cadmodelerBody_3.getFace("Radiator_Inlet_R"));
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
      ((SolidModelPart) compositePart_0.getChildParts().getPart("Radiator_R"));
    PartSurface partSurface_3 = 
      ((PartSurface) solidModelPart_3.getPartSurfaceManager().getPartSurface("Faces"));
    solidModelPart_3.deleteMeshPartSurfaces(new NeoObjectVector(new Object[] {partSurface_3}));
    simulation_0.get(SimulationPartManager.class).updateParts(new NeoObjectVector(new Object[] {solidModelPart_3}));
}

  private void WheelsRF() {

    Simulation simulation_0 = 
      getActiveSimulation();

  double Roll = simulation_0.getReportManager().getReport("Roll [deg]").getReportMonitorValue();
  double Yaw = simulation_0.getReportManager().getReport("Yaw [rad]").getReportMonitorValue();
  double CoGX = simulation_0.getReportManager().getReport("CoGX").getReportMonitorValue();
  double CoGZ = simulation_0.getReportManager().getReport("CoGZ").getReportMonitorValue();
  double CornerRadius = simulation_0.getReportManager().getReport("Corner Radius [m]").getReportMonitorValue(); 

  CompositePart compositePart_0 = 
      ((CompositePart) simulation_0.get(SimulationPartManager.class).getPart("Car")); 

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
      ((star.cadmodeler.Body) cadModel_4.getBody("SU_FL"));
    Face face_4 = 
      ((Face) cadmodelerBody_4.getFace("FL_Center"));
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
      ((star.cadmodeler.Body) cadModel_5.getBody("SU_FR"));
    Face face_5 = 
      ((Face) cadmodelerBody_5.getFace("FR_Center"));
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
      ((star.cadmodeler.Body) cadModel_6.getBody("SU_RL"));
    Face face_6 = 
      ((Face) cadmodelerBody_6.getFace("RL_Center"));
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
      ((star.cadmodeler.Body) cadModel_7.getBody("SU_RR"));
    Face face_7 = 
      ((Face) cadmodelerBody_7.getFace("RR_Center"));
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

  private void ContactPatch() {

    Simulation simulation_0 = 
      getActiveSimulation();

    double Roll = simulation_0.getReportManager().getReport("Roll [deg]").getReportMonitorValue();
    double Yaw = simulation_0.getReportManager().getReport("Yaw [rad]").getReportMonitorValue();
    double CoGX = simulation_0.getReportManager().getReport("CoGX").getReportMonitorValue();
    double CoGZ = simulation_0.getReportManager().getReport("CoGZ").getReportMonitorValue();
    double CornerRadius = simulation_0.getReportManager().getReport("Corner Radius [m]").getReportMonitorValue(); 

    CompositePart compositePart_0 = 
      ((CompositePart) simulation_0.get(SimulationPartManager.class).getPart("Car")); 

    LabCoordinateSystem labCoordinateSystem_0 = 
      simulation_0.getCoordinateSystemManager().getLabCoordinateSystem();

    Units units_0 = 
      simulation_0.getUnitsManager().getPreferredUnits(new IntVector(new int[] {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));
    Units units_1 = 
      ((Units) simulation_0.getUnitsManager().getObject("mm"));

    // Fillet Front Left //
    
    if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {

    CadPart cadPart_9 = 
    ((CadPart) compositePart_0.getChildParts().getPart("Tyre_FL"));
    simulation_0.get(SolidModelManager.class).createSolidModelForCadParts(new NeoObjectVector(new Object[] {cadPart_9}));
    CadModel cadModel_9 = 
    ((CadModel) simulation_0.get(SolidModelManager.class).getObject("3D-CAD Model 1"));
    cadModel_9.resetSystemOptions();
    cadModel_9.setPresentationName("Tyre_FL");

    simulation_0.get(SolidModelManager.class).editCadModel(cadModel_9);

    CanonicalSketchPlane canonicalSketchPlane_1 = 
    ((CanonicalSketchPlane) cadModel_9.getFeature("XY"));
    Sketch sketch_1 = 
    cadModel_9.getFeatureManager().createSketch(canonicalSketchPlane_1);
    cadModel_9.getFeatureManager().startSketchEdit(sketch_1);
    sketch_1.createRectangle(new DoubleVector(new double[] {0.40, 0.3}), new DoubleVector(new double[] {-0.40, 0.88}));
    sketch_1.markFeatureForEdit();
    cadModel_9.getFeatureManager().stopSketchEdit(sketch_1, true);
    sketch_1.setIsUptoDate(true);
    cadModel_9.getFeatureManager().rollForwardToEnd();
    ExtrusionMerge extrusionMerge_1 = 
    cadModel_9.getFeatureManager().createExtrusionMerge(sketch_1);
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
    cadModel_9.getFeatureManager().execute(extrusionMerge_1);

    try {
      LineSketchPrimitive lineSketchPrimitive_1 = 
      ((LineSketchPrimitive) sketch_1.getSketchPrimitive("Line 1"));
      Face face_13 = 
      ((Face) extrusionMerge_1.getStartCapFace(lineSketchPrimitive_1));
      Fillet fillet_1 = 
      cadModel_9.getFeatureManager().createFillet(new NeoObjectVector(new Object[] {}), new NeoObjectVector(new Object[] {face_13}));
      fillet_1.setColorVector(new IntVector(new int[] {-16711936}));
      fillet_1.getRadius().setUnits(units_1);
      fillet_1.getRadius().setValue(1.0);
      fillet_1.setEdges(new NeoObjectVector(new Object[] {}));
      fillet_1.setFaces(new NeoObjectVector(new Object[] {face_13}));
      fillet_1.markFeatureForEdit();
      try {
        cadModel_9.getFeatureManager().execute(fillet_1);
      }
      catch (Exception ee){
        fillet_1.getRadius().setValue(0.5);
        cadModel_9.getFeatureManager().execute(fillet_1);
      }
    }
    catch (Exception e){
      star.cadmodeler.Body cadmodelerBody_101 = 
      ((star.cadmodeler.Body) cadModel_9.getBody("Tyre_FL"));
      Face face_13 = 
      ((Face) extrusionMerge_1.getFaceByLocation(cadmodelerBody_101,new DoubleVector(new double[] {-0.25, 0.76, 0.0})));
      Fillet fillet_1 = 
      cadModel_9.getFeatureManager().createFillet(new NeoObjectVector(new Object[] {}), new NeoObjectVector(new Object[] {face_13}));
      fillet_1.setColorVector(new IntVector(new int[] {-16711936}));
      fillet_1.getRadius().setUnits(units_1);
      fillet_1.getRadius().setValue(1.0);
      fillet_1.setEdges(new NeoObjectVector(new Object[] {}));
      fillet_1.setFaces(new NeoObjectVector(new Object[] {face_13}));
      fillet_1.markFeatureForEdit();
      try {
        cadModel_9.getFeatureManager().execute(fillet_1);
      }
      catch (Exception ee){
        fillet_1.getRadius().setValue(0.5);
        cadModel_9.getFeatureManager().execute(fillet_1);
      }
    }

    ExtrusionCut extrusionCut_1 = 
    cadModel_9.getFeatureManager().createExtrusionCut(sketch_1);
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

    cadModel_9.getFeatureManager().execute(extrusionCut_1);
    cadModel_9.update();
    simulation_0.get(SolidModelManager.class).endEditCadModel(cadModel_9);

    SolidModelPart solidModelPart_9 = 
    ((SolidModelPart) compositePart_0.getChildParts().getPart("Tyre_FL"));
    simulation_0.get(SimulationPartManager.class).updateParts(new NeoObjectVector(new Object[] {solidModelPart_9}));
    }

    // Fillet Front Right //
    
    CadPart cadPart_8 = 
    ((CadPart) compositePart_0.getChildParts().getPart("Tyre_FR"));
    simulation_0.get(SolidModelManager.class).createSolidModelForCadParts(new NeoObjectVector(new Object[] {cadPart_8}));
    CadModel cadModel_8 = 
    ((CadModel) simulation_0.get(SolidModelManager.class).getObject("3D-CAD Model 1"));
    cadModel_8.resetSystemOptions();
    cadModel_8.setPresentationName("Tyre_FR");
    
    CanonicalSketchPlane canonicalSketchPlane_0 = 
    ((CanonicalSketchPlane) cadModel_8.getFeature("XY"));
    Sketch sketch_0 = 
    cadModel_8.getFeatureManager().createSketch(canonicalSketchPlane_0);
    cadModel_8.getFeatureManager().startSketchEdit(sketch_0);
    sketch_0.createRectangle(new DoubleVector(new double[] {0.4, -0.3}), new DoubleVector(new double[] {-0.45, -0.9}));
    sketch_0.markFeatureForEdit();
    cadModel_8.getFeatureManager().stopSketchEdit(sketch_0, true);
    sketch_0.setIsUptoDate(true);
    cadModel_8.getFeatureManager().rollForwardToEnd();
    ExtrusionMerge extrusionMerge_0 = 
    cadModel_8.getFeatureManager().createExtrusionMerge(sketch_0);
    extrusionMerge_0.setColorVector(new IntVector(new int[] {-16711681, -65536, -16776961, -16711936, -65536}));
    extrusionMerge_0.setDirectionOption(1);
    extrusionMerge_0.setExtrudedBodyTypeOption(0);
    extrusionMerge_0.getDistance().setValue(0.05);
    extrusionMerge_0.setDistanceOption(0);
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
    BodyNameRefManager bodyNameRefManager_0 = 
    extrusionMerge_0.getInteractionBodies();
    bodyNameRefManager_0.setBodies(new NeoObjectVector(new Object[] {}));
    extrusionMerge_0.setInteractingSelectedBodies(false);
    extrusionMerge_0.markFeatureForEdit();
    cadModel_8.getFeatureManager().execute(extrusionMerge_0);

    try {
      LineSketchPrimitive lineSketchPrimitive_0 = 
      ((LineSketchPrimitive) sketch_0.getSketchPrimitive("Line 1"));
      Face face_12 = 
      ((Face) extrusionMerge_0.getStartCapFace(lineSketchPrimitive_0));
      Fillet fillet_0 = 
      cadModel_8.getFeatureManager().createFillet(new NeoObjectVector(new Object[] {}), new NeoObjectVector(new Object[] {face_12}));
      fillet_0.setColorVector(new IntVector(new int[] {-16711936}));
      fillet_0.getRadius().setUnits(units_1);
      fillet_0.getRadius().setValue(1.0);
      fillet_0.setEdges(new NeoObjectVector(new Object[] {}));
      fillet_0.setFaces(new NeoObjectVector(new Object[] {face_12}));
      fillet_0.markFeatureForEdit();
      try {
        cadModel_8.getFeatureManager().execute(fillet_0);
      }
      catch (Exception ee){
        fillet_0.getRadius().setValue(0.5);
        cadModel_8.getFeatureManager().execute(fillet_0);
      }
    }
    catch (Exception e){
      star.cadmodeler.Body cadmodelerBody_100 = 
      ((star.cadmodeler.Body) cadModel_8.getBody("Tyre_FR"));
      Face face_12 = 
      ((Face) extrusionMerge_0.getFaceByLocation(cadmodelerBody_100,new DoubleVector(new double[] {-0.37, -0.42, 0.0})));
      Fillet fillet_0 = 
      cadModel_8.getFeatureManager().createFillet(new NeoObjectVector(new Object[] {}), new NeoObjectVector(new Object[] {face_12}));
      fillet_0.setColorVector(new IntVector(new int[] {-16711936}));
      fillet_0.getRadius().setUnits(units_1);
      fillet_0.getRadius().setValue(1.0);
      fillet_0.setEdges(new NeoObjectVector(new Object[] {}));
      fillet_0.setFaces(new NeoObjectVector(new Object[] {face_12}));
      fillet_0.markFeatureForEdit();
      cadModel_8.getFeatureManager().execute(fillet_0);
      try {
        cadModel_8.getFeatureManager().execute(fillet_0);
      }
      catch (Exception ee){
        fillet_0.getRadius().setValue(0.5);
        cadModel_8.getFeatureManager().execute(fillet_0);
      }
    }
    ExtrusionCut extrusionCut_0 = 
    cadModel_8.getFeatureManager().createExtrusionCut(sketch_0);
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

    cadModel_8.getFeatureManager().execute(extrusionCut_0);
    cadModel_8.update();
    simulation_0.get(SolidModelManager.class).endEditCadModel(cadModel_8);

    SolidModelPart solidModelPart_8 = 
    ((SolidModelPart) compositePart_0.getChildParts().getPart("Tyre_FR"));
    simulation_0.get(SimulationPartManager.class).updateParts(new NeoObjectVector(new Object[] {solidModelPart_8}));

    // Fillet Rear Left //
    
    if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {

    CadPart cadPart_101 = 
    ((CadPart) compositePart_0.getChildParts().getPart("Tyre_RL"));
    simulation_0.get(SolidModelManager.class).createSolidModelForCadParts(new NeoObjectVector(new Object[] {cadPart_101}));
    CadModel cadModel_11 = 
    ((CadModel) simulation_0.get(SolidModelManager.class).getObject("3D-CAD Model 1"));
    cadModel_11.resetSystemOptions();
    cadModel_11.setPresentationName("Tyre_RL");

    simulation_0.get(SolidModelManager.class).editCadModel(cadModel_11);

    CanonicalSketchPlane canonicalSketchPlane_3 = 
    ((CanonicalSketchPlane) cadModel_11.getFeature("XY"));
    Sketch sketch_3 = 
    cadModel_11.getFeatureManager().createSketch(canonicalSketchPlane_3);
    cadModel_11.getFeatureManager().startSketchEdit(sketch_3);
    sketch_3.createRectangle(new DoubleVector(new double[] {-2.0, 0.32}), new DoubleVector(new double[] {-1.05, 0.9}));
    sketch_3.markFeatureForEdit();
    cadModel_11.getFeatureManager().stopSketchEdit(sketch_3, true);
    sketch_3.setIsUptoDate(true);
    cadModel_11.getFeatureManager().rollForwardToEnd();
    ExtrusionMerge extrusionMerge_3 = 
    cadModel_11.getFeatureManager().createExtrusionMerge(sketch_3);
    extrusionMerge_3.setColorVector(new IntVector(new int[] {-16711681, -65536, -16776961, -16711936, -65536}));
    extrusionMerge_3.setDirectionOption(1);
    extrusionMerge_3.setExtrudedBodyTypeOption(0);
    extrusionMerge_3.getDistance().setValue(0.05);
    extrusionMerge_3.setDistanceOption(0);
    extrusionMerge_3.setCoordinateSystemOption(0);
    extrusionMerge_3.setDraftOption(0);
    extrusionMerge_3.setImportedCoordinateSystem(labCoordinateSystem_0);
    extrusionMerge_3.getDirectionAxis().setCoordinateSystem(labCoordinateSystem_0);
    extrusionMerge_3.getDirectionAxis().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {0.0, 0.0, 1.0}));
    extrusionMerge_3.setFace(null);
    extrusionMerge_3.setBody(null);
    extrusionMerge_3.setSketch(sketch_3);
    extrusionMerge_3.setPostOption(1);
    extrusionMerge_3.setExtrusionOption(0);
    BodyNameRefManager bodyNameRefManager_6 = 
    extrusionMerge_3.getInteractionBodies();
    bodyNameRefManager_6.setBodies(new NeoObjectVector(new Object[] {}));
    extrusionMerge_3.setInteractingSelectedBodies(false);
    extrusionMerge_3.markFeatureForEdit();
    cadModel_11.getFeatureManager().execute(extrusionMerge_3);

    try {
      LineSketchPrimitive lineSketchPrimitive_3 = 
      ((LineSketchPrimitive) sketch_3.getSketchPrimitive("Line 1"));
      Face face_15 = 
      ((Face) extrusionMerge_3.getStartCapFace(lineSketchPrimitive_3));
      Fillet fillet_3 = 
      cadModel_11.getFeatureManager().createFillet(new NeoObjectVector(new Object[] {}), new NeoObjectVector(new Object[] {face_15}));
      fillet_3.setColorVector(new IntVector(new int[] {-16711936}));
      fillet_3.getRadius().setUnits(units_1);
      fillet_3.getRadius().setValue(1.0);
      fillet_3.setEdges(new NeoObjectVector(new Object[] {}));
      fillet_3.setFaces(new NeoObjectVector(new Object[] {face_15}));
      fillet_3.markFeatureForEdit();
      try {
        cadModel_11.getFeatureManager().execute(fillet_3);
      }
      catch (Exception ee){
        fillet_3.getRadius().setValue(0.5);
        cadModel_11.getFeatureManager().execute(fillet_3);
      }
    }
    catch (Exception e){
      star.cadmodeler.Body cadmodelerBody_103 = 
      ((star.cadmodeler.Body) cadModel_11.getBody("Tyre_RL"));
      Face face_15 = 
      ((Face) extrusionMerge_0.getFaceByLocation(cadmodelerBody_103,new DoubleVector(new double[] {-1.86, 0.72, 0.0})));
      Fillet fillet_3 = 
      cadModel_11.getFeatureManager().createFillet(new NeoObjectVector(new Object[] {}), new NeoObjectVector(new Object[] {face_15}));
      fillet_3.setColorVector(new IntVector(new int[] {-16711936}));
      fillet_3.getRadius().setUnits(units_1);
      fillet_3.getRadius().setValue(1.0);
      fillet_3.setEdges(new NeoObjectVector(new Object[] {}));
      fillet_3.setFaces(new NeoObjectVector(new Object[] {face_15}));
      fillet_3.markFeatureForEdit();
      try {
        cadModel_11.getFeatureManager().execute(fillet_3);
      }
      catch (Exception ee){
        fillet_3.getRadius().setValue(0.5);
        cadModel_11.getFeatureManager().execute(fillet_3);
      }
    }

    ExtrusionCut extrusionCut_3 = 
    cadModel_11.getFeatureManager().createExtrusionCut(sketch_3);
    extrusionCut_3.setColorVector(new IntVector(new int[] {-16711681, -65536, -16776961, -16711936, -65536}));
    extrusionCut_3.setDirectionOption(1);
    extrusionCut_3.setExtrudedBodyTypeOption(0);
    extrusionCut_3.getDistance().setValue(0.05);
    extrusionCut_3.setDistanceOption(0);
    extrusionCut_3.setCoordinateSystemOption(0);
    extrusionCut_3.setDraftOption(0);
    extrusionCut_3.setImportedCoordinateSystem(labCoordinateSystem_0);
    extrusionCut_3.getDirectionAxis().setCoordinateSystem(labCoordinateSystem_0);
    extrusionCut_3.getDirectionAxis().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {0.0, 0.0, 1.0}));
    extrusionCut_3.setFace(null);
    extrusionCut_3.setBody(null);
    extrusionCut_3.setSketch(sketch_3);
    extrusionCut_3.setExtrusionOption(0);
    BodyNameRefManager bodyNameRefManager_7 = 
    extrusionCut_3.getCutBodies();
    bodyNameRefManager_7.setBodies(new NeoObjectVector(new Object[] {}));
    extrusionCut_3.setSubtractingSelectedCutBodies(false);
    extrusionCut_3.markFeatureForEdit();

    cadModel_11.getFeatureManager().execute(extrusionCut_3);
    cadModel_11.update();
    simulation_0.get(SolidModelManager.class).endEditCadModel(cadModel_11);

    SolidModelPart solidModelPart_11 = 
    ((SolidModelPart) compositePart_0.getChildParts().getPart("Tyre_RL"));
    simulation_0.get(SimulationPartManager.class).updateParts(new NeoObjectVector(new Object[] {solidModelPart_11}));
  }
    
  // Fillet Rear Right //

    CadPart cadPart_100 = 
    ((CadPart) compositePart_0.getChildParts().getPart("Tyre_RR"));
    simulation_0.get(SolidModelManager.class).createSolidModelForCadParts(new NeoObjectVector(new Object[] {cadPart_100}));
    CadModel cadModel_10 = 
    ((CadModel) simulation_0.get(SolidModelManager.class).getObject("3D-CAD Model 1"));
    cadModel_10.resetSystemOptions();
    cadModel_10.setPresentationName("Tyre_RR");

    simulation_0.get(SolidModelManager.class).editCadModel(cadModel_10);

    CanonicalSketchPlane canonicalSketchPlane_2 = 
    ((CanonicalSketchPlane) cadModel_10.getFeature("XY"));
    Sketch sketch_2 = 
    cadModel_10.getFeatureManager().createSketch(canonicalSketchPlane_2);
    cadModel_10.getFeatureManager().startSketchEdit(sketch_2);
    sketch_2.createRectangle(new DoubleVector(new double[] {-2.0, -0.32}), new DoubleVector(new double[] {-1.05, -0.9}));
    sketch_2.markFeatureForEdit();
    cadModel_10.getFeatureManager().stopSketchEdit(sketch_2, true);
    sketch_2.setIsUptoDate(true);
    cadModel_10.getFeatureManager().rollForwardToEnd();
    ExtrusionMerge extrusionMerge_2 = 
    cadModel_10.getFeatureManager().createExtrusionMerge(sketch_2);
    extrusionMerge_2.setColorVector(new IntVector(new int[] {-16711681, -65536, -16776961, -16711936, -65536}));
    extrusionMerge_2.setDirectionOption(1);
    extrusionMerge_2.setExtrudedBodyTypeOption(0);
    extrusionMerge_2.getDistance().setValue(0.05);
    extrusionMerge_2.setDistanceOption(0);
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
    BodyNameRefManager bodyNameRefManager_4 = 
    extrusionMerge_2.getInteractionBodies();
    bodyNameRefManager_4.setBodies(new NeoObjectVector(new Object[] {}));
    extrusionMerge_2.setInteractingSelectedBodies(false);
    extrusionMerge_2.markFeatureForEdit();
    cadModel_10.getFeatureManager().execute(extrusionMerge_2);
    try {
      LineSketchPrimitive lineSketchPrimitive_2 = 
      ((LineSketchPrimitive) sketch_2.getSketchPrimitive("Line 1"));
      Face face_14 = 
      ((Face) extrusionMerge_2.getStartCapFace(lineSketchPrimitive_2));
      Fillet fillet_2 = 
      cadModel_10.getFeatureManager().createFillet(new NeoObjectVector(new Object[] {}), new NeoObjectVector(new Object[] {face_14}));
      fillet_2.setColorVector(new IntVector(new int[] {-16711936}));
      fillet_2.getRadius().setUnits(units_1);
      fillet_2.getRadius().setValue(1.0);
      fillet_2.setEdges(new NeoObjectVector(new Object[] {}));
      fillet_2.setFaces(new NeoObjectVector(new Object[] {face_14}));
      fillet_2.markFeatureForEdit();
      try {
        cadModel_10.getFeatureManager().execute(fillet_2);
      }
      catch (Exception ee){
        fillet_2.getRadius().setValue(0.5);
        cadModel_10.getFeatureManager().execute(fillet_2);
      }
    }
    catch (Exception e){
      star.cadmodeler.Body cadmodelerBody_8 = 
      ((star.cadmodeler.Body) cadModel_10.getBody("Tyre_RR"));
      Face face_14 = 
      ((Face) extrusionMerge_2.getFaceByLocation(cadmodelerBody_8,new DoubleVector(new double[] {-1.227, -0.576, 0.0})));
      Fillet fillet_2 = 
      cadModel_10.getFeatureManager().createFillet(new NeoObjectVector(new Object[] {}), new NeoObjectVector(new Object[] {face_14}));
      fillet_2.setColorVector(new IntVector(new int[] {-16711936}));
      fillet_2.getRadius().setUnits(units_1);
      fillet_2.getRadius().setValue(1.0);
      fillet_2.setEdges(new NeoObjectVector(new Object[] {}));
      fillet_2.setFaces(new NeoObjectVector(new Object[] {face_14}));
      fillet_2.markFeatureForEdit();
      try {
        cadModel_10.getFeatureManager().execute(fillet_2);
      }
      catch (Exception ee){
        fillet_2.getRadius().setValue(0.5);
        cadModel_10.getFeatureManager().execute(fillet_2);
      }
    }
    
    ExtrusionCut extrusionCut_2 = 
    cadModel_10.getFeatureManager().createExtrusionCut(sketch_2);
    extrusionCut_2.setColorVector(new IntVector(new int[] {-16711681, -65536, -16776961, -16711936, -65536}));
    extrusionCut_2.setDirectionOption(1);
    extrusionCut_2.setExtrudedBodyTypeOption(0);
    extrusionCut_2.getDistance().setValue(0.05);
    extrusionCut_2.setDistanceOption(0);
    extrusionCut_2.setCoordinateSystemOption(0);
    extrusionCut_2.setDraftOption(0);
    extrusionCut_2.setImportedCoordinateSystem(labCoordinateSystem_0);
    extrusionCut_2.getDirectionAxis().setCoordinateSystem(labCoordinateSystem_0);
    extrusionCut_2.getDirectionAxis().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {0.0, 0.0, 1.0}));
    extrusionCut_2.setFace(null);
    extrusionCut_2.setBody(null);
    extrusionCut_2.setSketch(sketch_2);
    extrusionCut_2.setExtrusionOption(0);
    BodyNameRefManager bodyNameRefManager_5 = 
    extrusionCut_2.getCutBodies();
    bodyNameRefManager_5.setBodies(new NeoObjectVector(new Object[] {}));
    extrusionCut_2.setSubtractingSelectedCutBodies(false);
    extrusionCut_2.markFeatureForEdit();

    cadModel_10.getFeatureManager().execute(extrusionCut_2);
    cadModel_10.update();
    simulation_0.get(SolidModelManager.class).endEditCadModel(cadModel_10);

    SolidModelPart solidModelPart_10 = 
    ((SolidModelPart) compositePart_0.getChildParts().getPart("Tyre_RR"));
    simulation_0.get(SimulationPartManager.class).updateParts(new NeoObjectVector(new Object[] {solidModelPart_10}));
  
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
      ((CompositePart) simulation_0.get(SimulationPartManager.class).getPart("Car")); 


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