// STAR-CCM+ macro: d_SWrapper_FluidFanRad.java
// Written by STAR-CCM+ 14.04.011
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;
import star.resurfacer.*;
import star.prismmesher.*;
import star.base.query.*;
import star.base.neo.*;
import star.base.report.*;
import star.dualmesher.*;
import star.cadmodeler.*;
import star.meshing.*;
import star.surfacewrapper.*;

public class e_WrapperMesh extends StarMacro {

  public void execute() {
    Wrapper();
    Imprint();
    Mesh();
}

private void Wrapper() {

    Simulation simulation_0 = 
      getActiveSimulation();
    
  double Roll = simulation_0.getReportManager().getReport("Roll [deg]").getReportMonitorValue();
  double Yaw = simulation_0.getReportManager().getReport("Yaw [rad]").getReportMonitorValue();
  double CornerRadius = simulation_0.getReportManager().getReport("Corner Radius [m]").getReportMonitorValue();
  double CoolingMode = simulation_0.getReportManager().getReport("Cooling Mode").getReportMonitorValue(); 
    
  Units units_0 = 
      ((Units) simulation_0.getUnitsManager().getObject("m"));
  Units units_1 = 
      ((Units) simulation_0.getUnitsManager().getObject("mm"));

  SolidModelPart solidModelPart_0 = 
      ((SolidModelPart) simulation_0.get(SimulationPartManager.class).getPart("Domain"));
  CompositePart compositePart_0 = 
      ((CompositePart) simulation_0.get(SimulationPartManager.class).getPart("CFD_FULL_ASSEMBLY"));

    SurfaceWrapperAutoMeshOperation surfaceWrapperAutoMeshOperation_0 = 
      (SurfaceWrapperAutoMeshOperation) simulation_0.get(MeshOperationManager.class).createSurfaceWrapperAutoMeshOperation(new NeoObjectVector(new Object[] {solidModelPart_0, compositePart_0}), "Surface Wrapper");
    surfaceWrapperAutoMeshOperation_0.setPresentationName("SW - Fluid");

    MeshOperationPart meshOperationPart_0 = 
      ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("Surface Wrapper"));
    meshOperationPart_0.setPresentationName("Fluid");

    surfaceWrapperAutoMeshOperation_0.getDefaultValues().get(BaseSize.class).setValue(0.2);
    surfaceWrapperAutoMeshOperation_0.getDefaultValues().get(BaseSize.class).setUnits(units_0);

    PartsMinimumSurfaceSize partsMinimumSurfaceSize_0 = 
      surfaceWrapperAutoMeshOperation_0.getDefaultValues().get(PartsMinimumSurfaceSize.class);
    partsMinimumSurfaceSize_0.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsMinimumSurfaceSize_0.getAbsoluteSizeValue()).setValue(0.5);
    ((ScalarPhysicalQuantity) partsMinimumSurfaceSize_0.getAbsoluteSizeValue()).setUnits(units_1);

    SurfaceCurvature surfaceCurvature_10 = 
    surfaceWrapperAutoMeshOperation_0.getDefaultValues().get(SurfaceCurvature.class);
    surfaceCurvature_10.setEnableCurvatureDeviationDist(true);
    surfaceCurvature_10.getCurvatureDeviationDistance().setUnits(units_0);
    surfaceCurvature_10.getCurvatureDeviationDistance().setValue(3.0);
    surfaceCurvature_10.setEnableCurvatureDeviationDist(false);

    // General Refinement //

    SurfaceCustomMeshControl surfaceCustomMeshControl_10 = 
      surfaceWrapperAutoMeshOperation_0.getCustomMeshControls().createSurfaceControl();
    surfaceCustomMeshControl_10.setPresentationName("Car");
    surfaceCustomMeshControl_10.getGeometryObjects().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.DoesNotContain, "DIFF"), new NamePredicate(NameOperator.DoesNotContain, "FW"), new NamePredicate(NameOperator.DoesNotContain, "SU_"), new NamePredicate(NameOperator.DoesNotContain, "SC"), new NamePredicate(NameOperator.DoesNotContain, "TYRE"), new NamePredicate(NameOperator.DoesNotContain, "FAN"), new NamePredicate(NameOperator.DoesNotContain, "RW"), new NamePredicate(NameOperator.DoesNotContain, "RADIATOR"), new RelationshipPredicate(RelationshipOperator.DescendantOf, new IdentityPredicate(IdentityOperator.Equals, Arrays.<ClientServerObject>asList(compositePart_0))), new TypePredicate(TypeOperator.Is, GeometryPart.class))), new NamePredicate(NameOperator.Contains, "RW_S"))), Query.STANDARD_MODIFIERS));
    surfaceCustomMeshControl_10.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);

    PartsTargetSurfaceSize partsTargetSurfaceSize_10 = 
    surfaceCustomMeshControl_10.getCustomValues().get(PartsTargetSurfaceSize.class);
    partsTargetSurfaceSize_10.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_10.getAbsoluteSizeValue()).setUnits(units_1);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_10.getAbsoluteSizeValue()).setValue(5.0);


    // Aero Package //

    SurfaceCustomMeshControl surfaceCustomMeshControl_0 = 
      surfaceWrapperAutoMeshOperation_0.getCustomMeshControls().createSurfaceControl();
    surfaceCustomMeshControl_0.setPresentationName("Aero Package");
    surfaceCustomMeshControl_0.getGeometryObjects().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.DoesNotContain, "SUP"), new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "DIFF"), new NamePredicate(NameOperator.Contains, "RW"), new NamePredicate(NameOperator.Contains, "SC"))))), Query.STANDARD_MODIFIERS));
    surfaceCustomMeshControl_0.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);
    surfaceCustomMeshControl_0.getCustomConditions().get(PartsMinimumSurfaceSizeOption.class).setSelected(PartsMinimumSurfaceSizeOption.Type.CUSTOM);
    surfaceCustomMeshControl_0.getCustomConditions().get(PartsSurfaceCurvatureOption.class).setSelected(PartsSurfaceCurvatureOption.Type.CUSTOM_VALUES);

    PartsTargetSurfaceSize partsTargetSurfaceSize_0 = 
      surfaceCustomMeshControl_0.getCustomValues().get(PartsTargetSurfaceSize.class);
    partsTargetSurfaceSize_0.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_0.getAbsoluteSizeValue()).setValue(2.5);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_0.getAbsoluteSizeValue()).setUnits(units_1);

    PartsMinimumSurfaceSize partsMinimumSurfaceSize_1 = 
      surfaceCustomMeshControl_0.getCustomValues().get(PartsMinimumSurfaceSize.class);
    partsMinimumSurfaceSize_1.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsMinimumSurfaceSize_1.getAbsoluteSizeValue()).setValue(0.75);
    ((ScalarPhysicalQuantity) partsMinimumSurfaceSize_1.getAbsoluteSizeValue()).setUnits(units_1);

    SurfaceCurvature surfaceCurvature_0 = 
      surfaceCustomMeshControl_0.getCustomValues().get(SurfaceCurvature.class);
    surfaceCurvature_0.setEnableCurvatureDeviationDist(true);
    surfaceCurvature_0.setNumPointsAroundCircle(50.0);
    surfaceCurvature_0.getCurvatureDeviationDistance().setValue(1.0);
    surfaceCurvature_0.getCurvatureDeviationDistance().setUnits(units_1);

    // Front Wing //

    SurfaceCustomMeshControl surfaceCustomMeshControl_4 = 
      surfaceWrapperAutoMeshOperation_0.getCustomMeshControls().createSurfaceControl();
    surfaceCustomMeshControl_4.setPresentationName("FW");
    surfaceCustomMeshControl_4.getGeometryObjects().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "FW"), new TypePredicate(TypeOperator.Is, GeometryPart.class))), Query.STANDARD_MODIFIERS));
    surfaceCustomMeshControl_4.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);
    surfaceCustomMeshControl_4.getCustomConditions().get(PartsMinimumSurfaceSizeOption.class).setSelected(PartsMinimumSurfaceSizeOption.Type.CUSTOM);
    PartsTargetSurfaceSize partsTargetSurfaceSize_4 = 
      surfaceCustomMeshControl_4.getCustomValues().get(PartsTargetSurfaceSize.class);
    partsTargetSurfaceSize_4.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_4.getAbsoluteSizeValue()).setValue(2.0);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_4.getAbsoluteSizeValue()).setUnits(units_1);
    PartsMinimumSurfaceSize partsMinimumSurfaceSize_2 = 
      surfaceCustomMeshControl_4.getCustomValues().get(PartsMinimumSurfaceSize.class);
    partsMinimumSurfaceSize_2.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsMinimumSurfaceSize_2.getAbsoluteSizeValue()).setValue(0.3);
    ((ScalarPhysicalQuantity) partsMinimumSurfaceSize_2.getAbsoluteSizeValue()).setUnits(units_1);

    // SU //

    SurfaceCustomMeshControl surfaceCustomMeshControl_6 = 
      surfaceWrapperAutoMeshOperation_0.getCustomMeshControls().createSurfaceControl();
    surfaceCustomMeshControl_6.setPresentationName("SU");
    surfaceCustomMeshControl_6.getGeometryObjects().setQuery(new Query(new NamePredicate(NameOperator.Contains, "SU_"), Query.STANDARD_MODIFIERS));
    surfaceCustomMeshControl_6.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);
    PartsTargetSurfaceSize partsTargetSurfaceSize_7 = 
      surfaceCustomMeshControl_6.getCustomValues().get(PartsTargetSurfaceSize.class);
    partsTargetSurfaceSize_7.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_7.getAbsoluteSizeValue()).setValue(3.5);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_7.getAbsoluteSizeValue()).setUnits(units_1);


    // Tyres //

    SurfaceCustomMeshControl surfaceCustomMeshControl_8 = 
      surfaceWrapperAutoMeshOperation_0.getCustomMeshControls().createSurfaceControl();
    surfaceCustomMeshControl_8.setPresentationName("Tyres");
    surfaceCustomMeshControl_8.getGeometryObjects().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "TYRE"))), Query.STANDARD_MODIFIERS));
    surfaceCustomMeshControl_8.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);
    surfaceCustomMeshControl_8.getCustomConditions().get(PartsMinimumSurfaceSizeOption.class).setSelected(PartsMinimumSurfaceSizeOption.Type.CUSTOM);
    PartsTargetSurfaceSize partsTargetSurfaceSize_9 = 
      surfaceCustomMeshControl_8.getCustomValues().get(PartsTargetSurfaceSize.class);
    partsTargetSurfaceSize_9.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_9.getAbsoluteSizeValue()).setValue(2.0);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_9.getAbsoluteSizeValue()).setUnits(units_1);
    PartsMinimumSurfaceSize partsMinimumSurfaceSize_4 = 
      surfaceCustomMeshControl_8.getCustomValues().get(PartsMinimumSurfaceSize.class);
    partsMinimumSurfaceSize_4.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsMinimumSurfaceSize_4.getAbsoluteSizeValue()).setValue(0.2);
    ((ScalarPhysicalQuantity) partsMinimumSurfaceSize_4.getAbsoluteSizeValue()).setUnits(units_1);

    // Radiator & Fan //

    SurfaceCustomMeshControl surfaceCustomMeshControl_5 = 
      surfaceWrapperAutoMeshOperation_0.getCustomMeshControls().createSurfaceControl();
    surfaceCustomMeshControl_5.setPresentationName("Radiator & Fan");
    surfaceCustomMeshControl_5.getGeometryObjects().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, GeometryPart.class), new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "FAN"), new NamePredicate(NameOperator.Contains, "RADIATOR"))))), Query.STANDARD_MODIFIERS));
    surfaceCustomMeshControl_5.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);
    PartsTargetSurfaceSize partsTargetSurfaceSize_6 = 
      surfaceCustomMeshControl_5.getCustomValues().get(PartsTargetSurfaceSize.class);
    partsTargetSurfaceSize_6.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_6.getAbsoluteSizeValue()).setValue(2.0);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_6.getAbsoluteSizeValue()).setUnits(units_1);

    // Contact Preventions //

    PartsOneGroupContactPreventionSet partsOneGroupContactPreventionSet_0 = 
      surfaceWrapperAutoMeshOperation_0.getContactPreventionSet().createPartsOneGroupContactPreventionSet();
    partsOneGroupContactPreventionSet_0.setPresentationName("Ground");

  // Contact prevention more fine to prevent FW | Diffuser Back | Lateral Diffuser | Mono | Inner wall from getting mesh glued each other
  // uncomment 1st line -> Faster
  // uncomment 2nd line -> Slower with finer contact prevention
  
    partsOneGroupContactPreventionSet_0.getPartSurfaceGroup().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "TYRE"), new NamePredicate(NameOperator.Contains, "Ground"))), Query.STANDARD_MODIFIERS));
    // partsOneGroupContactPreventionSet_0.getPartSurfaceGroup().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "TYRE"), new NamePredicate(NameOperator.Contains, "FW"), new NamePredicate(NameOperator.Contains, "Ground"), new NamePredicate(NameOperator.Contains, "inner"), new NamePredicate(NameOperator.Contains, "underbody"), new NamePredicate(NameOperator.Contains, "vane"), new NamePredicate(NameOperator.Contains, "bck"), new RelationshipPredicate(RelationshipOperator.DescendantOf, new IdentityPredicate(IdentityOperator.Equals, Arrays.<ClientServerObject>asList(cadPart_0))))), Query.STANDARD_MODIFIERS));
    partsOneGroupContactPreventionSet_0.getFloor().setValue(0.5);
    partsOneGroupContactPreventionSet_0.getFloor().setUnits(units_1);

      PartsOneGroupContactPreventionSet partsOneGroupContactPreventionSet_2 = 
    surfaceWrapperAutoMeshOperation_0.getContactPreventionSet().createPartsOneGroupContactPreventionSet();
    partsOneGroupContactPreventionSet_2.setPresentationName("Mono - SU");
    partsOneGroupContactPreventionSet_2.getFloor().setUnits(units_1);
    partsOneGroupContactPreventionSet_2.getFloor().setValue(2.0);
    partsOneGroupContactPreventionSet_2.getPartSurfaceGroup().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, GeometryPart.class), new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "SU_"), new NamePredicate(NameOperator.Contains, "BH"), new NamePredicate(NameOperator.Contains, "MONO"))))), Query.STANDARD_MODIFIERS));


  PartsOneGroupContactPreventionSet partsOneGroupContactPreventionSet_3 = 
      surfaceWrapperAutoMeshOperation_0.getContactPreventionSet().createPartsOneGroupContactPreventionSet();
    partsOneGroupContactPreventionSet_3.setPresentationName("FW-EndP");
    partsOneGroupContactPreventionSet_3.getPartSurfaceGroup().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "FW"), new TypePredicate(TypeOperator.Is, GeometryPart.class), new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "EP"), new NamePredicate(NameOperator.Contains, "MAIN"), new NamePredicate(NameOperator.Contains, "FLAP"))))), Query.STANDARD_MODIFIERS));
    partsOneGroupContactPreventionSet_3.getFloor().setValue(1.0);
    partsOneGroupContactPreventionSet_3.getFloor().setUnits(units_1);

    PartsOneGroupContactPreventionSet partsOneGroupContactPreventionSet_4 = 
      surfaceWrapperAutoMeshOperation_0.getContactPreventionSet().createPartsOneGroupContactPreventionSet();
    partsOneGroupContactPreventionSet_4.setPresentationName("FW-Sup");
    partsOneGroupContactPreventionSet_4.getPartSurfaceGroup().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, GeometryPart.class), new NamePredicate(NameOperator.Contains, "FW"), new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "MAIN"), new NamePredicate(NameOperator.Contains, "MID"), new NamePredicate(NameOperator.Contains, "SUP"))))), Query.STANDARD_MODIFIERS));
    partsOneGroupContactPreventionSet_4.getFloor().setValue(1.0);
    partsOneGroupContactPreventionSet_4.getFloor().setUnits(units_1);

    PartsOneGroupContactPreventionSet partsOneGroupContactPreventionSet_5 = 
    surfaceWrapperAutoMeshOperation_0.getContactPreventionSet().createPartsOneGroupContactPreventionSet();
    partsOneGroupContactPreventionSet_5.setPresentationName("Driver - HR - Mono - MH");
    partsOneGroupContactPreventionSet_5.getPartSurfaceGroup().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "DRIVER"), new NamePredicate(NameOperator.Contains, "HR"), new NamePredicate(NameOperator.Contains, "MONO"), new NamePredicate(NameOperator.Contains, "MAIN_HOOP"))), new TypePredicate(TypeOperator.Is, GeometryPart.class))), Query.STANDARD_MODIFIERS));
    partsOneGroupContactPreventionSet_5.getFloor().setUnits(units_1);
    partsOneGroupContactPreventionSet_5.getFloor().setValue(2.0);

    PartsOneGroupContactPreventionSet partsOneGroupContactPreventionSet_6 = 
    surfaceWrapperAutoMeshOperation_0.getContactPreventionSet().createPartsOneGroupContactPreventionSet();
    partsOneGroupContactPreventionSet_6.setPresentationName("Mono - Wings");
    partsOneGroupContactPreventionSet_6.getPartSurfaceGroup().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "MONO"), new NamePredicate(NameOperator.Contains, "FW"), new NamePredicate(NameOperator.Contains, "SC"))), Query.STANDARD_MODIFIERS));
    partsOneGroupContactPreventionSet_6.getFloor().setUnits(units_1);
    partsOneGroupContactPreventionSet_6.getFloor().setValue(3.0);

    PartsOneGroupContactPreventionSet partsOneGroupContactPreventionSet_7 = 
    surfaceWrapperAutoMeshOperation_0.getContactPreventionSet().createPartsOneGroupContactPreventionSet();
    partsOneGroupContactPreventionSet_7.setPresentationName("Tyres - SU");
    partsOneGroupContactPreventionSet_7.getPartSurfaceGroup().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "SU_"), new NamePredicate(NameOperator.Contains, "TYRE"))), Query.STANDARD_MODIFIERS));
    partsOneGroupContactPreventionSet_7.getFloor().setUnits(units_0);
    partsOneGroupContactPreventionSet_7.getFloor().setValue(5.0);

    PartsOneGroupContactPreventionSet partsOneGroupContactPreventionSet_8 = 
    surfaceWrapperAutoMeshOperation_0.getContactPreventionSet().createPartsOneGroupContactPreventionSet();
    partsOneGroupContactPreventionSet_8.setPresentationName("Mono - UT");
    partsOneGroupContactPreventionSet_8.getFloor().setUnits(units_1);
    partsOneGroupContactPreventionSet_8.getFloor().setValue(1.0);
    partsOneGroupContactPreventionSet_8.getPartSurfaceGroup().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "MONO"), new NamePredicate(NameOperator.Contains, "DIFF"))), Query.STANDARD_MODIFIERS));

    PartsOneGroupContactPreventionSet partsOneGroupContactPreventionSet_9 = 
    surfaceWrapperAutoMeshOperation_0.getContactPreventionSet().createPartsOneGroupContactPreventionSet();
    partsOneGroupContactPreventionSet_9.setPresentationName("RW - RW_Support");
    partsOneGroupContactPreventionSet_9.getPartSurfaceGroup().setQuery(new Query(new NamePredicate(NameOperator.Contains, "RW"), Query.STANDARD_MODIFIERS));
    partsOneGroupContactPreventionSet_9.getFloor().setUnits(units_1);
    partsOneGroupContactPreventionSet_9.getFloor().setValue(2.0);

    // Surface Wrapper Radiator Right //
 
    CompositePart compositePart_1 = 
      ((CompositePart) simulation_0.get(SimulationPartManager.class).getPart("CFD_FULL_ASSEMBLY"));

    CompositePart compositePart_2 = 
      ((CompositePart) compositePart_1.getChildParts().getPart("PT"));

    SolidModelPart solidModelPart_1 = 
      ((SolidModelPart) compositePart_2.getChildParts().getPart("RADIATOR_R"));

    SurfaceWrapperAutoMeshOperation surfaceWrapperAutoMeshOperation_1 = 
      (SurfaceWrapperAutoMeshOperation) simulation_0.get(MeshOperationManager.class).createSurfaceWrapperAutoMeshOperation(new NeoObjectVector(new Object[] {solidModelPart_1}), "Surface Wrapper");
    surfaceWrapperAutoMeshOperation_1.setPresentationName("SW - Radiator_R");

    SurfaceWrapperAutoMesher surfaceWrapperAutoMesher_0 = 
      ((SurfaceWrapperAutoMesher) surfaceWrapperAutoMeshOperation_1.getMeshers().getObject("Surface Wrapper"));
    surfaceWrapperAutoMesher_0.setDoWrapperMeshAlignment(true);

    MeshOperationPart meshOperationPart_1 = 
      ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("Surface Wrapper"));
    meshOperationPart_1.setPresentationName("RADIATOR_R");

    surfaceWrapperAutoMeshOperation_1.getDefaultValues().get(BaseSize.class).setValue(5.0);
    surfaceWrapperAutoMeshOperation_1.getDefaultValues().get(BaseSize.class).setUnits(units_1);

    PartsTargetSurfaceSize partsTargetSurfaceSize_11 = 
      surfaceWrapperAutoMeshOperation_1.getDefaultValues().get(PartsTargetSurfaceSize.class);
    partsTargetSurfaceSize_11.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_11.getAbsoluteSizeValue()).setValue(2.0);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_11.getAbsoluteSizeValue()).setUnits(units_1);

  // Surface Wrapper Radiator Left //
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    simulation_0.get(MeshOperationManager.class).createCopyOfMeshOperation("star.surfacewrapper.SurfaceWrapperAutoMeshOperation", "Copy of SW - Radiator_R");

    SurfaceWrapperAutoMeshOperation surfaceWrapperAutoMeshOperation_2 = 
      ((SurfaceWrapperAutoMeshOperation) simulation_0.get(MeshOperationManager.class).getObject("Copy of SW - Radiator_R"));
    surfaceWrapperAutoMeshOperation_2.copyProperties(surfaceWrapperAutoMeshOperation_1);
    surfaceWrapperAutoMeshOperation_2.setPresentationName("SW - Radiator_L");
    MeshOperationPart meshOperationPart_2 = 
      ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("Copy of SW - Radiator_R"));
    meshOperationPart_2.setPresentationName("RADIATOR_L");
    surfaceWrapperAutoMeshOperation_2.getInputGeometryObjects().setQuery(null);

    SolidModelPart solidModelPart_2 = 
      ((SolidModelPart) compositePart_2.getChildParts().getPart("RADIATOR_L"));
    surfaceWrapperAutoMeshOperation_2.getInputGeometryObjects().setObjects(solidModelPart_2);
  }
  if ( CoolingMode != 0) {

  simulation_0.get(MeshOperationManager.class).createCopyOfMeshOperation("star.surfacewrapper.SurfaceWrapperAutoMeshOperation", "Copy of SW - Radiator_L");

    SurfaceWrapperAutoMeshOperation surfaceWrapperAutoMeshOperation_3 = 
      ((SurfaceWrapperAutoMeshOperation) simulation_0.get(MeshOperationManager.class).getObject("Copy of SW - Radiator_L"));
    surfaceWrapperAutoMeshOperation_3.copyProperties(surfaceWrapperAutoMeshOperation_1);
    surfaceWrapperAutoMeshOperation_3.setPresentationName("SW - Fan_R");
    MeshOperationPart meshOperationPart_3 = 
      ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("Copy of SW - Radiator_L"));
    meshOperationPart_3.setPresentationName("FAN_R");
    surfaceWrapperAutoMeshOperation_3.getInputGeometryObjects().setQuery(null);
  
    SolidModelPart solidModelPart_3 = 
      ((SolidModelPart) compositePart_2.getChildParts().getPart("FAN_R"));
    surfaceWrapperAutoMeshOperation_3.getInputGeometryObjects().setObjects(solidModelPart_3);
  
  // SURFACE WRAPPER FAN LEFT
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {

    simulation_0.get(MeshOperationManager.class).createCopyOfMeshOperation("star.surfacewrapper.SurfaceWrapperAutoMeshOperation", "Copy of SW - Fan_R");

    SurfaceWrapperAutoMeshOperation surfaceWrapperAutoMeshOperation_4 = 
      ((SurfaceWrapperAutoMeshOperation) simulation_0.get(MeshOperationManager.class).getObject("Copy of SW - Fan_R"));
    surfaceWrapperAutoMeshOperation_4.copyProperties(surfaceWrapperAutoMeshOperation_3);
    surfaceWrapperAutoMeshOperation_4.setPresentationName("SW - Fan_L");
    MeshOperationPart meshOperationPart_4 = 
      ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("Copy of SW - Fan_R"));
    meshOperationPart_4.setPresentationName("FAN_L");
    surfaceWrapperAutoMeshOperation_4.getInputGeometryObjects().setQuery(null);

    SolidModelPart solidModelPart_4 = 
      ((SolidModelPart) compositePart_2.getChildParts().getPart("FAN_L"));
    surfaceWrapperAutoMeshOperation_4.getInputGeometryObjects().setObjects(solidModelPart_4); 
  }
  }

  surfaceWrapperAutoMeshOperation_0.execute();
  surfaceWrapperAutoMeshOperation_1.execute();
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
  SurfaceWrapperAutoMeshOperation surfaceWrapperAutoMeshOperation_2 = 
      ((SurfaceWrapperAutoMeshOperation) simulation_0.get(MeshOperationManager.class).getObject("SW - Radiator_L"));
  surfaceWrapperAutoMeshOperation_2.execute();}
  if ( CoolingMode != 0) {
  SurfaceWrapperAutoMeshOperation surfaceWrapperAutoMeshOperation_3 = 
      ((SurfaceWrapperAutoMeshOperation) simulation_0.get(MeshOperationManager.class).getObject("SW - Fan_R"));
  surfaceWrapperAutoMeshOperation_3.execute();
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
  SurfaceWrapperAutoMeshOperation surfaceWrapperAutoMeshOperation_4 = 
      ((SurfaceWrapperAutoMeshOperation) simulation_0.get(MeshOperationManager.class).getObject("SW - Fan_L"));
  surfaceWrapperAutoMeshOperation_4.execute();}}

}

private void Imprint() {

  //{ 1st Imprint try

  double tolerance = 5.0E-4;

  Simulation simulation_0 = 
    getActiveSimulation();
  
  double Roll = simulation_0.getReportManager().getReport("Roll [deg]").getReportMonitorValue();
  double Yaw = simulation_0.getReportManager().getReport("Yaw [rad]").getReportMonitorValue();
  double CornerRadius = simulation_0.getReportManager().getReport("Corner Radius [m]").getReportMonitorValue();
  double CoolingMode = simulation_0.getReportManager().getReport("Cooling Mode").getReportMonitorValue(); 
   
  MeshOperationPart meshOperationPart_2 = 
    ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("Fluid"));
  MeshOperationPart meshOperationPart_3 = 
    ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("RADIATOR_R"));
  
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {

  MeshOperationPart meshOperationPart_4 = 
    ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("RADIATOR_L"));

  if (CoolingMode != 0) {
  MeshOperationPart meshOperationPart_0 = 
    ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("FAN_R"));  
  MeshOperationPart meshOperationPart_1 = 
      ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("FAN_L"));
    
  ImprintPartsOperation imprintPartsOperation_0 = 
    (ImprintPartsOperation) simulation_0.get(MeshOperationManager.class).createImprintPartsOperation(new NeoObjectVector(new Object[] {meshOperationPart_0, meshOperationPart_1, meshOperationPart_2, meshOperationPart_3, meshOperationPart_4}));
  }
  else{
    ImprintPartsOperation imprintPartsOperation_0 = 
    (ImprintPartsOperation) simulation_0.get(MeshOperationManager.class).createImprintPartsOperation(new NeoObjectVector(new Object[] {meshOperationPart_2, meshOperationPart_3, meshOperationPart_4}));
  }
  }
  
  if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
    if (CoolingMode != 0) {
  MeshOperationPart meshOperationPart_0 = 
    ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("FAN_R"));
  ImprintPartsOperation imprintPartsOperation_0 = 
    (ImprintPartsOperation) simulation_0.get(MeshOperationManager.class).createImprintPartsOperation(new NeoObjectVector(new Object[] {meshOperationPart_0, meshOperationPart_2, meshOperationPart_3}));
  }
  else {
    ImprintPartsOperation imprintPartsOperation_0 = 
    (ImprintPartsOperation) simulation_0.get(MeshOperationManager.class).createImprintPartsOperation(new NeoObjectVector(new Object[] {meshOperationPart_2, meshOperationPart_3}));
  }
  }
  
  ImprintPartsOperation imprintPartsOperation_0 = 
      ((ImprintPartsOperation) simulation_0.get(MeshOperationManager.class).getObject("Imprint"));
    
  imprintPartsOperation_0.setLinkOutputPartName(false);
  imprintPartsOperation_0.getTolerance().setValue(tolerance);
  imprintPartsOperation_0.execute();

  if (CoolingMode != 0) {
  MeshOperationPart meshOperationPart_0 = 
    ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("FAN_R"));
  PartContact partContact_0 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_2,meshOperationPart_0);
  PartContact partContact_2 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_2,meshOperationPart_3);
  PartContact partContact_4 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_0,meshOperationPart_3);
  
  
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
  MeshOperationPart meshOperationPart_1 = 
      ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("FAN_L"));
  MeshOperationPart meshOperationPart_4 = 
    ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("RADIATOR_L"));
  
  PartContact partContact_1 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_2,meshOperationPart_1);
  PartContact partContact_3 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_2,meshOperationPart_4);
  PartContact partContact_5 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_1,meshOperationPart_4); 
  
  PartSurface partSurface_0 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.FAN_L.FAN_L"));
  PartSurface partSurface_1 = 
  ((PartSurface) meshOperationPart_1.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.FAN_L.FAN_L"));
  PartSurface partSurface_2 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.FAN_L.DUCT_L"));
  PartSurface partSurface_3 = 
  ((PartSurface) meshOperationPart_1.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.FAN_L.DUCT_L"));
  PartSurface partSurface_4 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.SHROUD_L"));
  PartSurface partSurface_5 = 
  ((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.SHROUD_L"));
  PartSurface partSurface_6 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.RADIATOR_INLET_L"));
  PartSurface partSurface_7 = 
  ((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.RADIATOR_INLET_L"));
  PartSurface partSurface_16 = 
  ((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.RADIATOR_OUTLET_L"));
  PartSurface partSurface_17 = 
  ((PartSurface) meshOperationPart_1.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.FAN_L.DUCT_INLET_L"));
  try {
  InPlacePartSurfaceContact inPlacePartSurfaceContact_1 = 
  ((InPlacePartSurfaceContact) partContact_1.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_4,partSurface_3));
  partContact_1.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_1); 
  }
  catch (Exception e){}
  try {
  InPlacePartSurfaceContact inPlacePartSurfaceContact_3 = 
  ((InPlacePartSurfaceContact) partContact_3.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_2,partSurface_5));
  partContact_3.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_3);  
  }
  catch (Exception e){}
  try {
  InPlacePartSurfaceContact inPlacePartSurfaceContact_4 = 
  ((InPlacePartSurfaceContact) partContact_1.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_1,partSurface_2));
  partContact_1.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_4);  
  }
  catch (Exception e){}
  }
  
  PartSurface partSurface_8 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.FAN_R.FAN_R"));
  PartSurface partSurface_9 = 
  ((PartSurface) meshOperationPart_0.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.FAN_R.FAN_R"));
  PartSurface partSurface_10 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.FAN_R.DUCT_R"));
  PartSurface partSurface_11 = 
  ((PartSurface) meshOperationPart_0.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.FAN_R.DUCT_R"));
  PartSurface partSurface_12 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_R.SHROUD_R"));
  PartSurface partSurface_13 = 
  ((PartSurface) meshOperationPart_3.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_R.SHROUD_R"));
  PartSurface partSurface_14 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_R.RADIATOR_INLET_R"));
  PartSurface partSurface_15 = 
  ((PartSurface) meshOperationPart_3.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_R.RADIATOR_INLET_R"));
  PartSurface partSurface_18 = 
  ((PartSurface) meshOperationPart_3.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_R.RADIATOR_OUTLET_R"));
  PartSurface partSurface_19 = 
  ((PartSurface) meshOperationPart_0.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.FAN_R.DUCT_INLET_R"));

  try {
  InPlacePartSurfaceContact inPlacePartSurfaceContact_0 = 
  ((InPlacePartSurfaceContact) partContact_0.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_11,partSurface_12));
  partContact_0.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_0); 
  }
  catch(Exception e){}

  try {
  InPlacePartSurfaceContact inPlacePartSurfaceContact_2 = 
  ((InPlacePartSurfaceContact) partContact_2.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_10,partSurface_13));
  partContact_2.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_2);  
  }
  catch (Exception e){}

  try {
  InPlacePartSurfaceContact inPlacePartSurfaceContact_5 = 
  ((InPlacePartSurfaceContact) partContact_0.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_10,partSurface_9));
  partContact_0.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_5);  
  }
  catch (Exception e){}

  try {
  
    
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
  MeshOperationPart meshOperationPart_1 = 
      ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("FAN_L"));
  MeshOperationPart meshOperationPart_4 = 
    ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("RADIATOR_L"));
    
  PartContact partContact_10 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_2,meshOperationPart_1); 
  PartContact partContact_11 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_4,meshOperationPart_2);
  PartContact partContact_14 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_4,meshOperationPart_1);
  
  PartSurface partSurface_0 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.FAN_L.FAN_L"));
  PartSurface partSurface_1 = 
  ((PartSurface) meshOperationPart_1.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.FAN_L.FAN_L"));
  PartSurface partSurface_2 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.FAN_L.DUCT_L"));
  PartSurface partSurface_3 = 
  ((PartSurface) meshOperationPart_1.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.FAN_L.DUCT_L"));
  PartSurface partSurface_4 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.SHROUD_L"));
  PartSurface partSurface_5 = 
  ((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.SHROUD_L"));
  PartSurface partSurface_6 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.RADIATOR_INLET_L"));
  PartSurface partSurface_7 = 
  ((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.RADIATOR_INLET_L"));
  PartSurface partSurface_16 = 
  ((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.RADIATOR_OUTLET_L"));
  PartSurface partSurface_17 = 
  ((PartSurface) meshOperationPart_1.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.FAN_L.DUCT_INLET_L"));  
    
  // Fan (L)
  InPlacePartSurfaceContact inPlacePartSurfaceContact_10 = 
  ((InPlacePartSurfaceContact) partContact_10.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_0,partSurface_1));
  MetaData metaData_10 = 
  inPlacePartSurfaceContact_10.getMetaData();

  // Duct (L)
  InPlacePartSurfaceContact inPlacePartSurfaceContact_11 = 
  ((InPlacePartSurfaceContact) partContact_10.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_2,partSurface_3));
  MetaData metaData_11 = 
  inPlacePartSurfaceContact_11.getMetaData();

  // Rad_Shroud (L)
  InPlacePartSurfaceContact inPlacePartSurfaceContact_12 = 
  ((InPlacePartSurfaceContact) partContact_11.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_4,partSurface_5));
  MetaData metaData_12 = 
  inPlacePartSurfaceContact_12.getMetaData();

  // Rad_Inlet (L)
  InPlacePartSurfaceContact inPlacePartSurfaceContact_13 = 
  ((InPlacePartSurfaceContact) partContact_11.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_6,partSurface_7));
  MetaData metaData_13 = 
  inPlacePartSurfaceContact_13.getMetaData();
  
  // Rad_outlet/Fan_inlet (L)
  InPlacePartSurfaceContact inPlacePartSurfaceContact_18 = 
  ((InPlacePartSurfaceContact) partContact_14.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_16,partSurface_17));
  MetaData metaData_18 = 
  inPlacePartSurfaceContact_18.getMetaData();
    
  }
    
  PartContact partContact_12 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_0,meshOperationPart_2);
  PartContact partContact_13 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_2,meshOperationPart_3);
  PartContact partContact_15 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_0,meshOperationPart_3);

  // Fan (R)
  InPlacePartSurfaceContact inPlacePartSurfaceContact_14 = 
  ((InPlacePartSurfaceContact) partContact_12.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_8,partSurface_9));
  MetaData metaData_14 = 
  inPlacePartSurfaceContact_14.getMetaData();

  // Duct (R)
  InPlacePartSurfaceContact inPlacePartSurfaceContact_15 = 
  ((InPlacePartSurfaceContact) partContact_12.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_10,partSurface_11));
  MetaData metaData_15 = 
  inPlacePartSurfaceContact_15.getMetaData();

  // Rad_Shroud (R)
  InPlacePartSurfaceContact inPlacePartSurfaceContact_16 = 
  ((InPlacePartSurfaceContact) partContact_13.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_12,partSurface_13));
  MetaData metaData_16 = 
  inPlacePartSurfaceContact_16.getMetaData();

  // Rad_Inlet (R)
  InPlacePartSurfaceContact inPlacePartSurfaceContact_17 = 
  ((InPlacePartSurfaceContact) partContact_13.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_14,partSurface_15));
  MetaData metaData_17 = 
  inPlacePartSurfaceContact_17.getMetaData();

  // Rad_outlet/Fan_inlet (R)
  InPlacePartSurfaceContact inPlacePartSurfaceContact_19 = 
  ((InPlacePartSurfaceContact) partContact_15.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_18,partSurface_19));
  MetaData metaData_19 = 
  inPlacePartSurfaceContact_19.getMetaData();

  }
  //}
  
  //{ 2nd | 3rd | 4th | 5th Imprint try
  
  catch (Exception ef){

  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
  MeshOperationPart meshOperationPart_1 = 
      ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("FAN_L"));
  MeshOperationPart meshOperationPart_4 = 
    ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("RADIATOR_L"));
    
  PartContact partContact_10 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_2,meshOperationPart_1);
  PartContact partContact_11 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_4,meshOperationPart_2);
  PartContact partContact_14 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_4,meshOperationPart_1);
  simulation_0.get(PartContactManager.class).removeObjects(partContact_10, partContact_11, partContact_14);
  
  }
  
  PartContact partContact_12 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_0,meshOperationPart_2);
  PartContact partContact_13 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_2,meshOperationPart_3);
  PartContact partContact_15 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_0,meshOperationPart_3);
  simulation_0.get(PartContactManager.class).removeObjects(partContact_12, partContact_13, partContact_15);

  tolerance = 1.0E-3;
  imprintPartsOperation_0.getTolerance().setValue(tolerance);
  imprintPartsOperation_0.execute();
  
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
  MeshOperationPart meshOperationPart_1 = 
      ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("FAN_L"));
  MeshOperationPart meshOperationPart_4 = 
    ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("RADIATOR_L"));

  PartContact partContact_100 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_2,meshOperationPart_1);
  PartContact partContact_101 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_4,meshOperationPart_2);
  PartContact partContact_104 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_4,meshOperationPart_1);
  
  PartSurface partSurface_0 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.FAN_L.FAN_L"));
  PartSurface partSurface_1 = 
  ((PartSurface) meshOperationPart_1.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.FAN_L.FAN_L"));
  PartSurface partSurface_2 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.FAN_L.DUCT_L"));
  PartSurface partSurface_3 = 
  ((PartSurface) meshOperationPart_1.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.FAN_L.DUCT_L"));
  PartSurface partSurface_4 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.SHROUD_L"));
  PartSurface partSurface_5 = 
  ((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.SHROUD_L"));
  PartSurface partSurface_6 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.RADIATOR_INLET_L"));
  PartSurface partSurface_7 = 
  ((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.RADIATOR_INLET_L"));
  PartSurface partSurface_16 = 
  ((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.RADIATOR_OUTLET_L"));
  PartSurface partSurface_17 = 
  ((PartSurface) meshOperationPart_1.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.FAN_L.DUCT_INLET_L")); 
  
  try {
  InPlacePartSurfaceContact inPlacePartSurfaceContact_1 = 
  ((InPlacePartSurfaceContact) partContact_100.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_4,partSurface_3));
  partContact_100.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_1); 
  }
  catch (Exception eeee){}
  
  try {
  InPlacePartSurfaceContact inPlacePartSurfaceContact_3 = 
  ((InPlacePartSurfaceContact) partContact_101.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_2,partSurface_5));
  partContact_101.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_3);  
  }
  catch (Exception eeeeee){}
  
  try {
  InPlacePartSurfaceContact inPlacePartSurfaceContact_4 = 
  ((InPlacePartSurfaceContact) partContact_100.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_1,partSurface_2));
  partContact_100.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_4);  
  }
  catch (Exception eeeeeee){}
  }
  
  PartContact partContact_102 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_0,meshOperationPart_2);
  PartContact partContact_103 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_2,meshOperationPart_3);
  PartContact partContact_105 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_0,meshOperationPart_3);

  try {
  InPlacePartSurfaceContact inPlacePartSurfaceContact_0 = 
  ((InPlacePartSurfaceContact) partContact_102.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_11,partSurface_12));
  partContact_102.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_0); 
  }
  catch (Exception eee){}

  try {
  InPlacePartSurfaceContact inPlacePartSurfaceContact_2 = 
  ((InPlacePartSurfaceContact) partContact_103.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_10,partSurface_13));
  partContact_103.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_2);  
  }
  catch (Exception eeeee){}

  try {
  InPlacePartSurfaceContact inPlacePartSurfaceContact_5 = 
  ((InPlacePartSurfaceContact) partContact_102.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_10,partSurface_9));
  partContact_102.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_5);  
  }
  catch (Exception eeeeeeee){}

  try {
    
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
  MeshOperationPart meshOperationPart_1 = 
      ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("FAN_L"));
  MeshOperationPart meshOperationPart_4 = 
    ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("RADIATOR_L"));

  PartContact partContact_100 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_2,meshOperationPart_1);
  PartContact partContact_101 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_4,meshOperationPart_2);
  PartContact partContact_104 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_4,meshOperationPart_1);
  
  PartSurface partSurface_0 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.FAN_L.FAN_L"));
  PartSurface partSurface_1 = 
  ((PartSurface) meshOperationPart_1.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.FAN_L.FAN_L"));
  PartSurface partSurface_2 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.FAN_L.DUCT_L"));
  PartSurface partSurface_3 = 
  ((PartSurface) meshOperationPart_1.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.FAN_L.DUCT_L"));
  PartSurface partSurface_4 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.SHROUD_L"));
  PartSurface partSurface_5 = 
  ((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.SHROUD_L"));
  PartSurface partSurface_6 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.RADIATOR_INLET_L"));
  PartSurface partSurface_7 = 
  ((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.RADIATOR_INLET_L"));
  PartSurface partSurface_16 = 
  ((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.RADIATOR_OUTLET_L"));
  PartSurface partSurface_17 = 
  ((PartSurface) meshOperationPart_1.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.FAN_L.DUCT_INLET_L"));
  
   
  // Fan (L)
  InPlacePartSurfaceContact inPlacePartSurfaceContact_10 = 
  ((InPlacePartSurfaceContact) partContact_100.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_0,partSurface_1));
  MetaData metaData_10 = 
  inPlacePartSurfaceContact_10.getMetaData();

  // Duct (L)
  InPlacePartSurfaceContact inPlacePartSurfaceContact_11 = 
  ((InPlacePartSurfaceContact) partContact_100.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_2,partSurface_3));
  MetaData metaData_11 = 
  inPlacePartSurfaceContact_11.getMetaData();

  // Rad_Shroud (L)
  InPlacePartSurfaceContact inPlacePartSurfaceContact_12 = 
  ((InPlacePartSurfaceContact) partContact_101.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_4,partSurface_5));
  MetaData metaData_12 = 
  inPlacePartSurfaceContact_12.getMetaData();

  // Rad_Inlet (L)
  InPlacePartSurfaceContact inPlacePartSurfaceContact_13 = 
  ((InPlacePartSurfaceContact) partContact_101.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_6,partSurface_7));
  MetaData metaData_13 = 
  inPlacePartSurfaceContact_13.getMetaData();
  
  // Rad_outlet/Fan_inlet (L)
  InPlacePartSurfaceContact inPlacePartSurfaceContact_18 = 
  ((InPlacePartSurfaceContact) partContact_104.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_16,partSurface_17));
  MetaData metaData_18 = 
  inPlacePartSurfaceContact_18.getMetaData();
  }

  // Fan (R)
  InPlacePartSurfaceContact inPlacePartSurfaceContact_14 = 
  ((InPlacePartSurfaceContact) partContact_102.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_8,partSurface_9));
  MetaData metaData_14 = 
  inPlacePartSurfaceContact_14.getMetaData();

  // Duct (R)
  InPlacePartSurfaceContact inPlacePartSurfaceContact_15 = 
  ((InPlacePartSurfaceContact) partContact_102.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_10,partSurface_11));
  MetaData metaData_15 = 
  inPlacePartSurfaceContact_15.getMetaData();

  // Rad_Shroud (R)
  InPlacePartSurfaceContact inPlacePartSurfaceContact_16 = 
  ((InPlacePartSurfaceContact) partContact_103.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_12,partSurface_13));
  MetaData metaData_16 = 
  inPlacePartSurfaceContact_16.getMetaData();

  // Rad_Inlet (R)
  InPlacePartSurfaceContact inPlacePartSurfaceContact_17 = 
  ((InPlacePartSurfaceContact) partContact_103.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_14,partSurface_15));
  MetaData metaData_17 = 
  inPlacePartSurfaceContact_17.getMetaData();

  // Rad_outlet/Fan_inlet (R)
  InPlacePartSurfaceContact inPlacePartSurfaceContact_19 = 
  ((InPlacePartSurfaceContact) partContact_105.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_18,partSurface_19));
  MetaData metaData_19 = 
  inPlacePartSurfaceContact_19.getMetaData();

  }
  
  //}
  
  //{ 3rd | 4th | 5th Imprint try
  
  catch (Exception eff){
    
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
  MeshOperationPart meshOperationPart_1 = 
      ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("FAN_L"));
  MeshOperationPart meshOperationPart_4 = 
    ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("RADIATOR_L"));
    
  PartContact partContact_100 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_2,meshOperationPart_1);
  PartContact partContact_101 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_4,meshOperationPart_2);
  PartContact partContact_104 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_4,meshOperationPart_1);
  simulation_0.get(PartContactManager.class).removeObjects(partContact_100, partContact_101, partContact_104);

  } 

  simulation_0.get(PartContactManager.class).removeObjects(partContact_102, partContact_103, partContact_105);

  tolerance = 1.0E-4;
  imprintPartsOperation_0.getTolerance().setValue(tolerance);
  imprintPartsOperation_0.execute();
  
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
  MeshOperationPart meshOperationPart_1 = 
      ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("FAN_L"));
  MeshOperationPart meshOperationPart_4 = 
    ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("RADIATOR_L"));


  PartContact partContact_1000 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_2,meshOperationPart_1);
  PartContact partContact_1001 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_4,meshOperationPart_2);
  PartContact partContact_1004 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_4,meshOperationPart_1);
  
  PartSurface partSurface_0 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.FAN_L.FAN_L"));
  PartSurface partSurface_1 = 
  ((PartSurface) meshOperationPart_1.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.FAN_L.FAN_L"));
  PartSurface partSurface_2 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.FAN_L.DUCT_L"));
  PartSurface partSurface_3 = 
  ((PartSurface) meshOperationPart_1.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.FAN_L.DUCT_L"));
  PartSurface partSurface_4 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.SHROUD_L"));
  PartSurface partSurface_5 = 
  ((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.SHROUD_L"));
  PartSurface partSurface_6 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.RADIATOR_INLET_L"));
  PartSurface partSurface_7 = 
  ((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.RADIATOR_INLET_L"));
  PartSurface partSurface_16 = 
  ((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.RADIATOR_OUTLET_L"));
  PartSurface partSurface_17 = 
  ((PartSurface) meshOperationPart_1.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.FAN_L.DUCT_INLET_L"));
  
  try {
  InPlacePartSurfaceContact inPlacePartSurfaceContact_1 = 
  ((InPlacePartSurfaceContact) partContact_1000.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_4,partSurface_3));
  partContact_1000.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_1); 
  }
  catch (Exception eeee){}
  
  try {
  InPlacePartSurfaceContact inPlacePartSurfaceContact_3 = 
  ((InPlacePartSurfaceContact) partContact_1001.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_2,partSurface_5));
  partContact_1001.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_3);  
  }
  catch (Exception eeeeee){}
  
  try {
  InPlacePartSurfaceContact inPlacePartSurfaceContact_4 = 
  ((InPlacePartSurfaceContact) partContact_1000.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_1,partSurface_2));
  partContact_1000.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_4);  
  }
  catch (Exception eeeeeee){}
  
  }
  
  PartContact partContact_1002 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_0,meshOperationPart_2);
  PartContact partContact_1003 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_2,meshOperationPart_3);
  PartContact partContact_1005 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_0,meshOperationPart_3);

  try {
  InPlacePartSurfaceContact inPlacePartSurfaceContact_0 = 
  ((InPlacePartSurfaceContact) partContact_1002.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_11,partSurface_12));
  partContact_1002.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_0); 
  }
  catch (Exception eee){}

  try {
  InPlacePartSurfaceContact inPlacePartSurfaceContact_2 = 
  ((InPlacePartSurfaceContact) partContact_1003.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_10,partSurface_13));
  partContact_1003.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_2);  
  }
  catch (Exception eeeee){}

  try {
  InPlacePartSurfaceContact inPlacePartSurfaceContact_5 = 
  ((InPlacePartSurfaceContact) partContact_1002.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_10,partSurface_9));
  partContact_1002.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_5);  
  }
  catch (Exception eeeeeeeeeeeeee){}

  try {
    
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
  MeshOperationPart meshOperationPart_1 = 
      ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("FAN_L"));
  MeshOperationPart meshOperationPart_4 = 
    ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("RADIATOR_L"));
    
  PartContact partContact_1000 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_2,meshOperationPart_1);
  PartContact partContact_1001 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_4,meshOperationPart_2);
  PartContact partContact_1004 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_4,meshOperationPart_1);
  
  PartSurface partSurface_0 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.FAN_L.FAN_L"));
  PartSurface partSurface_1 = 
  ((PartSurface) meshOperationPart_1.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.FAN_L.FAN_L"));
  PartSurface partSurface_2 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.FAN_L.DUCT_L"));
  PartSurface partSurface_3 = 
  ((PartSurface) meshOperationPart_1.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.FAN_L.DUCT_L"));
  PartSurface partSurface_4 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.SHROUD_L"));
  PartSurface partSurface_5 = 
  ((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.SHROUD_L"));
  PartSurface partSurface_6 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.RADIATOR_INLET_L"));
  PartSurface partSurface_7 = 
  ((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.RADIATOR_INLET_L"));
  PartSurface partSurface_16 = 
  ((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.RADIATOR_OUTLET_L"));
  PartSurface partSurface_17 = 
  ((PartSurface) meshOperationPart_1.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.FAN_L.DUCT_INLET_L"));

  // Fan (L)
  InPlacePartSurfaceContact inPlacePartSurfaceContact_10 = 
  ((InPlacePartSurfaceContact) partContact_1000.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_0,partSurface_1));
  MetaData metaData_10 = 
  inPlacePartSurfaceContact_10.getMetaData();

  // Duct (L)
  InPlacePartSurfaceContact inPlacePartSurfaceContact_11 = 
  ((InPlacePartSurfaceContact) partContact_1000.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_2,partSurface_3));
  MetaData metaData_11 = 
  inPlacePartSurfaceContact_11.getMetaData();

  // Rad_Shroud (L)
  InPlacePartSurfaceContact inPlacePartSurfaceContact_12 = 
  ((InPlacePartSurfaceContact) partContact_1001.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_4,partSurface_5));
  MetaData metaData_12 = 
  inPlacePartSurfaceContact_12.getMetaData();

  // Rad_Inlet (L)
  InPlacePartSurfaceContact inPlacePartSurfaceContact_13 = 
  ((InPlacePartSurfaceContact) partContact_1001.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_6,partSurface_7));
  MetaData metaData_13 = 
  inPlacePartSurfaceContact_13.getMetaData();

  // Rad_outlet/Fan_inlet (L)
  InPlacePartSurfaceContact inPlacePartSurfaceContact_18 = 
  ((InPlacePartSurfaceContact) partContact_1004.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_16,partSurface_17));
  MetaData metaData_18 = 
  inPlacePartSurfaceContact_18.getMetaData();
  }

  // Fan (R)
  InPlacePartSurfaceContact inPlacePartSurfaceContact_14 = 
  ((InPlacePartSurfaceContact) partContact_1002.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_8,partSurface_9));
  MetaData metaData_14 = 
  inPlacePartSurfaceContact_14.getMetaData();

  // Duct (R)
  InPlacePartSurfaceContact inPlacePartSurfaceContact_15 = 
  ((InPlacePartSurfaceContact) partContact_1002.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_10,partSurface_11));
  MetaData metaData_15 = 
  inPlacePartSurfaceContact_15.getMetaData();

  // Rad_Shroud (R)
  InPlacePartSurfaceContact inPlacePartSurfaceContact_16 = 
  ((InPlacePartSurfaceContact) partContact_1003.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_12,partSurface_13));
  MetaData metaData_16 = 
  inPlacePartSurfaceContact_16.getMetaData();

  // Rad_Inlet (R)
  InPlacePartSurfaceContact inPlacePartSurfaceContact_17 = 
  ((InPlacePartSurfaceContact) partContact_1003.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_14,partSurface_15));
  MetaData metaData_17 = 
  inPlacePartSurfaceContact_17.getMetaData();

  // Rad_outlet/Fan_inlet (R)
  InPlacePartSurfaceContact inPlacePartSurfaceContact_19 = 
  ((InPlacePartSurfaceContact) partContact_1005.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_18,partSurface_19));
  MetaData metaData_19 = 
  inPlacePartSurfaceContact_19.getMetaData();

  }
  //}
  
  //{ 4th | 5th Imprint try
  
  catch (Exception efff){
    
    
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
  MeshOperationPart meshOperationPart_1 = 
      ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("FAN_L"));
  MeshOperationPart meshOperationPart_4 = 
    ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("RADIATOR_L"));
    
  PartContact partContact_1000 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_2,meshOperationPart_1);
  PartContact partContact_1001 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_4,meshOperationPart_2);
  PartContact partContact_1004 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_4,meshOperationPart_1);
  simulation_0.get(PartContactManager.class).removeObjects(partContact_1000, partContact_1001, partContact_1004);

  } 

  simulation_0.get(PartContactManager.class).removeObjects(partContact_1002, partContact_1003, partContact_1005);   

  tolerance = 5.0E-5;
  imprintPartsOperation_0.getTolerance().setValue(tolerance);
  imprintPartsOperation_0.execute();
  
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
  MeshOperationPart meshOperationPart_1 = 
      ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("FAN_L"));
  MeshOperationPart meshOperationPart_4 = 
    ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("RADIATOR_L"));
  
  PartContact partContact_10000 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_2,meshOperationPart_1);
  PartContact partContact_10001 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_4,meshOperationPart_2);
  PartContact partContact_10004 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_4,meshOperationPart_1);
  
  PartSurface partSurface_0 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.FAN_L.FAN_L"));
  PartSurface partSurface_1 = 
  ((PartSurface) meshOperationPart_1.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.FAN_L.FAN_L"));
  PartSurface partSurface_2 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.FAN_L.DUCT_L"));
  PartSurface partSurface_3 = 
  ((PartSurface) meshOperationPart_1.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.FAN_L.DUCT_L"));
  PartSurface partSurface_4 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.SHROUD_L"));
  PartSurface partSurface_5 = 
  ((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.SHROUD_L"));
  PartSurface partSurface_6 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.RADIATOR_INLET_L"));
  PartSurface partSurface_7 = 
  ((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.RADIATOR_INLET_L"));
  PartSurface partSurface_16 = 
  ((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.RADIATOR_OUTLET_L"));
  PartSurface partSurface_17 = 
  ((PartSurface) meshOperationPart_1.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.FAN_L.DUCT_INLET_L")); 
  
  try {
  InPlacePartSurfaceContact inPlacePartSurfaceContact_1 = 
  ((InPlacePartSurfaceContact) partContact_10000.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_4,partSurface_3));
  partContact_10000.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_1); 
  }
  catch (Exception eeee){}
  
  try {
  InPlacePartSurfaceContact inPlacePartSurfaceContact_3 = 
  ((InPlacePartSurfaceContact) partContact_10001.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_2,partSurface_5));
  partContact_10001.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_3);  
  }
  catch (Exception eeeeee){}
  
  try {
  InPlacePartSurfaceContact inPlacePartSurfaceContact_4 = 
  ((InPlacePartSurfaceContact) partContact_10000.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_1,partSurface_2));
  partContact_10000.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_4);  
  }
  catch (Exception eeeeeee){}
  
  }
  
  PartContact partContact_10002 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_0,meshOperationPart_2);
  PartContact partContact_10003 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_2,meshOperationPart_3);
  PartContact partContact_10005 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_0,meshOperationPart_3);

  try {
  InPlacePartSurfaceContact inPlacePartSurfaceContact_0 = 
  ((InPlacePartSurfaceContact) partContact_10002.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_11,partSurface_12));
  partContact_10002.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_0); 
  }
  catch (Exception eee){}

  try {
  InPlacePartSurfaceContact inPlacePartSurfaceContact_2 = 
  ((InPlacePartSurfaceContact) partContact_10003.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_10,partSurface_13));
  partContact_10003.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_2);  
  }
  catch (Exception eeeee){}

  try {
  InPlacePartSurfaceContact inPlacePartSurfaceContact_5 = 
  ((InPlacePartSurfaceContact) partContact_10002.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_10,partSurface_9));
  partContact_10002.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_5);  
  }
  catch (Exception eeeeeeeeeeeeeeeeeeee){}

  try {
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
  MeshOperationPart meshOperationPart_1 = 
      ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("FAN_L"));
  MeshOperationPart meshOperationPart_4 = 
    ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("RADIATOR_L"));  
    
  PartContact partContact_10000 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_2,meshOperationPart_1);
  PartContact partContact_10001 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_4,meshOperationPart_2);
  PartContact partContact_10004 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_4,meshOperationPart_1); 
  
  PartSurface partSurface_0 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.FAN_L.FAN_L"));
  PartSurface partSurface_1 = 
  ((PartSurface) meshOperationPart_1.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.FAN_L.FAN_L"));
  PartSurface partSurface_2 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.FAN_L.DUCT_L"));
  PartSurface partSurface_3 = 
  ((PartSurface) meshOperationPart_1.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.FAN_L.DUCT_L"));
  PartSurface partSurface_4 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.SHROUD_L"));
  PartSurface partSurface_5 = 
  ((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.SHROUD_L"));
  PartSurface partSurface_6 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.RADIATOR_INLET_L"));
  PartSurface partSurface_7 = 
  ((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.RADIATOR_INLET_L"));
  PartSurface partSurface_16 = 
  ((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.RADIATOR_OUTLET_L"));
  PartSurface partSurface_17 = 
  ((PartSurface) meshOperationPart_1.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.FAN_L.DUCT_INLET_L "));

  // Fan (L)
  InPlacePartSurfaceContact inPlacePartSurfaceContact_10 = 
  ((InPlacePartSurfaceContact) partContact_10000.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_0,partSurface_1));
  MetaData metaData_10 = 
  inPlacePartSurfaceContact_10.getMetaData();

  // Duct (L)
  InPlacePartSurfaceContact inPlacePartSurfaceContact_11 = 
  ((InPlacePartSurfaceContact) partContact_10000.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_2,partSurface_3));
  MetaData metaData_11 = 
  inPlacePartSurfaceContact_11.getMetaData();

  // Rad_Shroud (L)
  InPlacePartSurfaceContact inPlacePartSurfaceContact_12 = 
  ((InPlacePartSurfaceContact) partContact_10001.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_4,partSurface_5));
  MetaData metaData_12 = 
  inPlacePartSurfaceContact_12.getMetaData();

  // Rad_Inlet (L)
  InPlacePartSurfaceContact inPlacePartSurfaceContact_13 = 
  ((InPlacePartSurfaceContact) partContact_10001.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_6,partSurface_7));
  MetaData metaData_13 = 
  inPlacePartSurfaceContact_13.getMetaData();
  
  // Rad_outlet/Fan_inlet (L)
  InPlacePartSurfaceContact inPlacePartSurfaceContact_18 = 
  ((InPlacePartSurfaceContact) partContact_10004.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_16,partSurface_17));
  MetaData metaData_18 = 
  inPlacePartSurfaceContact_18.getMetaData();

  }
  
  // Fan (R)
  InPlacePartSurfaceContact inPlacePartSurfaceContact_14 = 
  ((InPlacePartSurfaceContact) partContact_10002.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_8,partSurface_9));
  MetaData metaData_14 = 
  inPlacePartSurfaceContact_14.getMetaData();

  // Duct (R)
  InPlacePartSurfaceContact inPlacePartSurfaceContact_15 = 
  ((InPlacePartSurfaceContact) partContact_10002.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_10,partSurface_11));
  MetaData metaData_15 = 
  inPlacePartSurfaceContact_15.getMetaData();

  // Rad_Shroud (R)
  InPlacePartSurfaceContact inPlacePartSurfaceContact_16 = 
  ((InPlacePartSurfaceContact) partContact_10003.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_12,partSurface_13));
  MetaData metaData_16 = 
  inPlacePartSurfaceContact_16.getMetaData();

  // Rad_Inlet (R)
  InPlacePartSurfaceContact inPlacePartSurfaceContact_17 = 
  ((InPlacePartSurfaceContact) partContact_10003.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_14,partSurface_15));
  MetaData metaData_17 = 
  inPlacePartSurfaceContact_17.getMetaData();

  // Rad_outlet/Fan_inlet (R)
  InPlacePartSurfaceContact inPlacePartSurfaceContact_19 = 
  ((InPlacePartSurfaceContact) partContact_10005.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_18,partSurface_19));
  MetaData metaData_19 = 
  inPlacePartSurfaceContact_19.getMetaData();
  }
  //}
  
  //{ 5th Imprint try
  
  catch (Exception effffff){
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
  MeshOperationPart meshOperationPart_1 = 
      ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("FAN_L"));
  MeshOperationPart meshOperationPart_4 = 
    ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("RADIATOR_L"));
    
  PartContact partContact_10000 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_2,meshOperationPart_1);
  PartContact partContact_10001 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_4,meshOperationPart_2);
  PartContact partContact_10004 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_4,meshOperationPart_1);
  simulation_0.get(PartContactManager.class).removeObjects(partContact_10000, partContact_10001, partContact_10004);
  }

  simulation_0.get(PartContactManager.class).removeObjects(partContact_10002, partContact_10003, partContact_10005);

  tolerance = 1.0E-5;
  imprintPartsOperation_0.getTolerance().setValue(tolerance);
  imprintPartsOperation_0.execute();
  
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
  MeshOperationPart meshOperationPart_1 = 
      ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("FAN_L"));
  MeshOperationPart meshOperationPart_4 = 
    ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("RADIATOR_L"));
  
  PartContact partContact_100000 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_2,meshOperationPart_1);
  PartContact partContact_100001 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_4,meshOperationPart_2);
  PartContact partContact_100004 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_4,meshOperationPart_1);
  
  PartSurface partSurface_0 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.FAN_L.FAN_L"));
  PartSurface partSurface_1 = 
  ((PartSurface) meshOperationPart_1.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.FAN_L.FAN_L"));
  PartSurface partSurface_2 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.FAN_L.DUCT_L"));
  PartSurface partSurface_3 = 
  ((PartSurface) meshOperationPart_1.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.FAN_L.DUCT_L"));
  PartSurface partSurface_4 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.SHROUD_L"));
  PartSurface partSurface_5 = 
  ((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.SHROUD_L"));
  PartSurface partSurface_6 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.RADIATOR_INLET_L"));
  PartSurface partSurface_7 = 
  ((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.RADIATOR_INLET_L"));
  PartSurface partSurface_16 = 
  ((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.RADIATOR_OUTLET_L"));
  PartSurface partSurface_17 = 
  ((PartSurface) meshOperationPart_1.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.FAN_L.DUCT_INLET_L")); 
  
  try {
  InPlacePartSurfaceContact inPlacePartSurfaceContact_1 = 
  ((InPlacePartSurfaceContact) partContact_100000.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_4,partSurface_3));
  partContact_100000.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_1); 
  }
  catch (Exception eeee){}
  
  try {
  InPlacePartSurfaceContact inPlacePartSurfaceContact_3 = 
  ((InPlacePartSurfaceContact) partContact_100001.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_2,partSurface_5));
  partContact_100001.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_3);  
  }
  catch (Exception eeeeee){}
  
  try {
  InPlacePartSurfaceContact inPlacePartSurfaceContact_4 = 
  ((InPlacePartSurfaceContact) partContact_100000.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_1,partSurface_2));
  partContact_100000.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_4);  
  }
  catch (Exception eeeeeee){}
  
  }
  
  PartContact partContact_100002 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_0,meshOperationPart_2);
  PartContact partContact_100003 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_2,meshOperationPart_3);
  PartContact partContact_100005 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_0,meshOperationPart_3);

  try {
  InPlacePartSurfaceContact inPlacePartSurfaceContact_0 = 
  ((InPlacePartSurfaceContact) partContact_100002.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_11,partSurface_12));
  partContact_100002.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_0); 
  }
  catch (Exception eee){}

  try {
  InPlacePartSurfaceContact inPlacePartSurfaceContact_2 = 
  ((InPlacePartSurfaceContact) partContact_100003.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_10,partSurface_13));
  partContact_100003.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_2);  
  }
  catch (Exception eeeee){}

  try {
  InPlacePartSurfaceContact inPlacePartSurfaceContact_5 = 
  ((InPlacePartSurfaceContact) partContact_100002.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_10,partSurface_9));
  partContact_100002.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_5);  
  }
  catch (Exception eeeeeeeeeeeeeeeeeeeeeeeeee){}
  //}
  }
  }
  }
}
}
else{
  PartContact partContact_3 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_2,meshOperationPart_3);
  
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
  MeshOperationPart meshOperationPart_4 = 
    ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("RADIATOR_L"));
  
  PartContact partContact_2 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_2,meshOperationPart_4);
  
  PartSurface partSurface_4 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.SHROUD_L"));
  PartSurface partSurface_5 = 
  ((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.SHROUD_L"));
  PartSurface partSurface_6 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.RADIATOR_INLET_L"));
  PartSurface partSurface_7 = 
  ((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.RADIATOR_INLET_L"));
    PartSurface partSurface_0 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.RADIATOR_OUTLET_L"));
  PartSurface partSurface_16 = 
  ((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.RADIATOR_OUTLET_L"));

  try {
  InPlacePartSurfaceContact inPlacePartSurfaceContact_1 = 
  ((InPlacePartSurfaceContact) partContact_2.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_5,partSurface_6));
  partContact_2.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_1); 
  }
  catch (Exception e){}

  try {
  InPlacePartSurfaceContact inPlacePartSurfaceContact_3 = 
  ((InPlacePartSurfaceContact) partContact_2.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_5,partSurface_0));
  partContact_2.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_3);  
  }
  catch (Exception e){}

}
  
  PartSurface partSurface_12 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_R.SHROUD_R"));
  PartSurface partSurface_13 = 
  ((PartSurface) meshOperationPart_3.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_R.SHROUD_R"));
  PartSurface partSurface_14 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_R.RADIATOR_INLET_R"));
  PartSurface partSurface_15 = 
  ((PartSurface) meshOperationPart_3.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_R.RADIATOR_INLET_R"));
  PartSurface partSurface_18 = 
  ((PartSurface) meshOperationPart_3.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_R.RADIATOR_OUTLET_R"));
    PartSurface partSurface_19 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_R.RADIATOR_OUTLET_R"));

  try {
  InPlacePartSurfaceContact inPlacePartSurfaceContact_0 = 
  ((InPlacePartSurfaceContact) partContact_3.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_13,partSurface_14));
  partContact_3.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_0); 
  }
  catch(Exception e){}

  try {
  InPlacePartSurfaceContact inPlacePartSurfaceContact_5 = 
  ((InPlacePartSurfaceContact) partContact_3.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_13,partSurface_19));
  partContact_3.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_5);  
  }
  catch (Exception e){}

  try {
  
    
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
  MeshOperationPart meshOperationPart_4 = 
    ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("RADIATOR_L"));
    
  PartContact partContact_11 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_4,meshOperationPart_2);

  PartSurface partSurface_4 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.SHROUD_L"));
  PartSurface partSurface_5 = 
  ((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.SHROUD_L"));
  PartSurface partSurface_6 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.RADIATOR_INLET_L"));
  PartSurface partSurface_7 = 
  ((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.RADIATOR_INLET_L"));
  PartSurface partSurface_16 = 
  ((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.RADIATOR_OUTLET_L"));
    PartSurface partSurface_0 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.RADIATOR_OUTLET_L"));

  // Rad_Shroud (L)
  InPlacePartSurfaceContact inPlacePartSurfaceContact_12 = 
  ((InPlacePartSurfaceContact) partContact_11.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_4,partSurface_5));
  MetaData metaData_12 = 
  inPlacePartSurfaceContact_12.getMetaData();

  // Rad_Inlet (L)
  InPlacePartSurfaceContact inPlacePartSurfaceContact_13 = 
  ((InPlacePartSurfaceContact) partContact_11.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_6,partSurface_7));
  MetaData metaData_13 = 
  inPlacePartSurfaceContact_13.getMetaData();
  
  // Rad_outlet (L)
  InPlacePartSurfaceContact inPlacePartSurfaceContact_18 = 
  ((InPlacePartSurfaceContact) partContact_11.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_16,partSurface_0));
  MetaData metaData_18 = 
  inPlacePartSurfaceContact_18.getMetaData();
    
  }
    
  PartContact partContact_13 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_2,meshOperationPart_3);

  // Rad_Shroud (R)
  InPlacePartSurfaceContact inPlacePartSurfaceContact_16 = 
  ((InPlacePartSurfaceContact) partContact_13.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_12,partSurface_13));
  MetaData metaData_16 = 
  inPlacePartSurfaceContact_16.getMetaData();

  // Rad_Inlet (R)
  InPlacePartSurfaceContact inPlacePartSurfaceContact_17 = 
  ((InPlacePartSurfaceContact) partContact_13.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_14,partSurface_15));
  MetaData metaData_17 = 
  inPlacePartSurfaceContact_17.getMetaData();

  // Rad_outlet (R)
  InPlacePartSurfaceContact inPlacePartSurfaceContact_19 = 
  ((InPlacePartSurfaceContact) partContact_13.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_18,partSurface_19));
  MetaData metaData_19 = 
  inPlacePartSurfaceContact_19.getMetaData();

  }
  //}
  
  //{ 2nd | 3rd | 4th | 5th Imprint try
  
  catch (Exception ef){

  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
  MeshOperationPart meshOperationPart_4 = 
    ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("RADIATOR_L"));

  PartContact partContact_11 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_4,meshOperationPart_2);
  simulation_0.get(PartContactManager.class).removeObjects(partContact_11);
  
  }
  
  PartContact partContact_13 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_2,meshOperationPart_3);
  simulation_0.get(PartContactManager.class).removeObjects(partContact_13);

  tolerance = 1.0E-3;
  imprintPartsOperation_0.getTolerance().setValue(tolerance);
  imprintPartsOperation_0.execute();
  
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
  MeshOperationPart meshOperationPart_4 = 
    ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("RADIATOR_L"));
  
  PartContact partContact_2 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_2,meshOperationPart_4);
  
  PartSurface partSurface_4 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.SHROUD_L"));
  PartSurface partSurface_5 = 
  ((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.SHROUD_L"));
  PartSurface partSurface_6 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.RADIATOR_INLET_L"));
  PartSurface partSurface_7 = 
  ((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.RADIATOR_INLET_L"));
    PartSurface partSurface_0 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.RADIATOR_OUTLET_L"));
  PartSurface partSurface_16 = 
  ((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.RADIATOR_OUTLET_L"));

  try {
  InPlacePartSurfaceContact inPlacePartSurfaceContact_1 = 
  ((InPlacePartSurfaceContact) partContact_2.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_5,partSurface_6));
  partContact_2.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_1); 
  }
  catch (Exception e){}

  try {
  InPlacePartSurfaceContact inPlacePartSurfaceContact_3 = 
  ((InPlacePartSurfaceContact) partContact_2.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_5,partSurface_0));
  partContact_2.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_3);  
  }
  catch (Exception e){}
}
  
  /*PartSurface partSurface_12 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_R.SHROUD_R"));
  PartSurface partSurface_13 = 
  ((PartSurface) meshOperationPart_3.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_R.SHROUD_R"));
  PartSurface partSurface_14 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_R.RADIATOR_INLET_R"));
  PartSurface partSurface_15 = 
  ((PartSurface) meshOperationPart_3.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_R.RADIATOR_INLET_R"));
  PartSurface partSurface_18 = 
  ((PartSurface) meshOperationPart_3.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_R.RADIATOR_OUTLET_R"));
    PartSurface partSurface_19 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_R.RADIATOR_OUTLET_R"));*/

  try {
  InPlacePartSurfaceContact inPlacePartSurfaceContact_0 = 
  ((InPlacePartSurfaceContact) partContact_3.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_13,partSurface_14));
  partContact_3.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_0); 
  }
  catch(Exception e){}

  try {
  InPlacePartSurfaceContact inPlacePartSurfaceContact_5 = 
  ((InPlacePartSurfaceContact) partContact_3.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_13,partSurface_19));
  partContact_3.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_5);  
  }
  catch (Exception e){}

  try {
  
    
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
  MeshOperationPart meshOperationPart_4 = 
    ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("RADIATOR_L"));
    
  PartContact partContact_11 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_4,meshOperationPart_2);

  PartSurface partSurface_4 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.SHROUD_L"));
  PartSurface partSurface_5 = 
  ((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.SHROUD_L"));
  PartSurface partSurface_6 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.RADIATOR_INLET_L"));
  PartSurface partSurface_7 = 
  ((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.RADIATOR_INLET_L"));
  PartSurface partSurface_16 = 
  ((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.RADIATOR_OUTLET_L"));
    PartSurface partSurface_0 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.RADIATOR_OUTLET_L"));

  // Rad_Shroud (L)
  InPlacePartSurfaceContact inPlacePartSurfaceContact_12 = 
  ((InPlacePartSurfaceContact) partContact_11.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_4,partSurface_5));
  MetaData metaData_12 = 
  inPlacePartSurfaceContact_12.getMetaData();

  // Rad_Inlet (L)
  InPlacePartSurfaceContact inPlacePartSurfaceContact_13 = 
  ((InPlacePartSurfaceContact) partContact_11.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_6,partSurface_7));
  MetaData metaData_13 = 
  inPlacePartSurfaceContact_13.getMetaData();
  
  // Rad_outlet (L)
  InPlacePartSurfaceContact inPlacePartSurfaceContact_18 = 
  ((InPlacePartSurfaceContact) partContact_11.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_16,partSurface_0));
  MetaData metaData_18 = 
  inPlacePartSurfaceContact_18.getMetaData();
    
  }

  // Rad_Shroud (R)
  InPlacePartSurfaceContact inPlacePartSurfaceContact_16 = 
  ((InPlacePartSurfaceContact) partContact_13.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_12,partSurface_13));
  MetaData metaData_16 = 
  inPlacePartSurfaceContact_16.getMetaData();

  // Rad_Inlet (R)
  InPlacePartSurfaceContact inPlacePartSurfaceContact_17 = 
  ((InPlacePartSurfaceContact) partContact_13.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_14,partSurface_15));
  MetaData metaData_17 = 
  inPlacePartSurfaceContact_17.getMetaData();

  // Rad_outlet (R)
  InPlacePartSurfaceContact inPlacePartSurfaceContact_19 = 
  ((InPlacePartSurfaceContact) partContact_13.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_18,partSurface_19));
  MetaData metaData_19 = 
  inPlacePartSurfaceContact_19.getMetaData();

  }
  
  //}
  
  //{ 3rd | 4th | 5th Imprint try
  
  catch (Exception eff){
    
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
  MeshOperationPart meshOperationPart_4 = 
    ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("RADIATOR_L"));

  PartContact partContact_11 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_4,meshOperationPart_2);
  simulation_0.get(PartContactManager.class).removeObjects(partContact_11);
  
  }
  
  simulation_0.get(PartContactManager.class).removeObjects(partContact_13);

  tolerance = 1.0E-4;
  imprintPartsOperation_0.getTolerance().setValue(tolerance);
  imprintPartsOperation_0.execute();
  
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
  MeshOperationPart meshOperationPart_4 = 
    ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("RADIATOR_L"));
  
  PartContact partContact_2 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_2,meshOperationPart_4);
  
  PartSurface partSurface_4 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.SHROUD_L"));
  PartSurface partSurface_5 = 
  ((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.SHROUD_L"));
  PartSurface partSurface_6 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.RADIATOR_INLET_L"));
  PartSurface partSurface_7 = 
  ((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.RADIATOR_INLET_L"));
    PartSurface partSurface_0 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.RADIATOR_OUTLET_L"));
  PartSurface partSurface_16 = 
  ((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.RADIATOR_OUTLET_L"));

  try {
  InPlacePartSurfaceContact inPlacePartSurfaceContact_1 = 
  ((InPlacePartSurfaceContact) partContact_2.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_5,partSurface_6));
  partContact_2.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_1); 
  }
  catch (Exception e){}

  try {
  InPlacePartSurfaceContact inPlacePartSurfaceContact_3 = 
  ((InPlacePartSurfaceContact) partContact_2.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_5,partSurface_0));
  partContact_2.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_3);  
  }
  catch (Exception e){}

}
  
  /*PartSurface partSurface_12 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_R.SHROUD_R"));
  PartSurface partSurface_13 = 
  ((PartSurface) meshOperationPart_3.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_R.SHROUD_R"));
  PartSurface partSurface_14 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_R.RADIATOR_INLET_R"));
  PartSurface partSurface_15 = 
  ((PartSurface) meshOperationPart_3.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_R.RADIATOR_INLET_R"));
  PartSurface partSurface_18 = 
  ((PartSurface) meshOperationPart_3.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_R.RADIATOR_OUTLET_R"));
    PartSurface partSurface_19 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_R.RADIATOR_OUTLET_R"));*/

  try {
  InPlacePartSurfaceContact inPlacePartSurfaceContact_0 = 
  ((InPlacePartSurfaceContact) partContact_3.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_13,partSurface_14));
  partContact_3.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_0); 
  }
  catch(Exception e){}

  try {
  InPlacePartSurfaceContact inPlacePartSurfaceContact_5 = 
  ((InPlacePartSurfaceContact) partContact_3.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_13,partSurface_19));
  partContact_3.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_5);  
  }
  catch (Exception e){}

  try {
  
    
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
  MeshOperationPart meshOperationPart_4 = 
    ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("RADIATOR_L"));
    
  PartContact partContact_11 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_4,meshOperationPart_2);

  PartSurface partSurface_4 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.SHROUD_L"));
  PartSurface partSurface_5 = 
  ((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.SHROUD_L"));
  PartSurface partSurface_6 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.RADIATOR_INLET_L"));
  PartSurface partSurface_7 = 
  ((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.RADIATOR_INLET_L"));
  PartSurface partSurface_16 = 
  ((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.RADIATOR_OUTLET_L"));
    PartSurface partSurface_0 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.RADIATOR_OUTLET_L"));

  // Rad_Shroud (L)
  InPlacePartSurfaceContact inPlacePartSurfaceContact_12 = 
  ((InPlacePartSurfaceContact) partContact_11.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_4,partSurface_5));
  MetaData metaData_12 = 
  inPlacePartSurfaceContact_12.getMetaData();

  // Rad_Inlet (L)
  InPlacePartSurfaceContact inPlacePartSurfaceContact_13 = 
  ((InPlacePartSurfaceContact) partContact_11.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_6,partSurface_7));
  MetaData metaData_13 = 
  inPlacePartSurfaceContact_13.getMetaData();
  
  // Rad_outlet (L)
  InPlacePartSurfaceContact inPlacePartSurfaceContact_18 = 
  ((InPlacePartSurfaceContact) partContact_11.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_16,partSurface_0));
  MetaData metaData_18 = 
  inPlacePartSurfaceContact_18.getMetaData();
    
  }
    
  /*PartContact partContact_13 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_2,meshOperationPart_3);*/

  // Rad_Shroud (R)
  InPlacePartSurfaceContact inPlacePartSurfaceContact_16 = 
  ((InPlacePartSurfaceContact) partContact_13.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_12,partSurface_13));
  MetaData metaData_16 = 
  inPlacePartSurfaceContact_16.getMetaData();

  // Rad_Inlet (R)
  InPlacePartSurfaceContact inPlacePartSurfaceContact_17 = 
  ((InPlacePartSurfaceContact) partContact_13.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_14,partSurface_15));
  MetaData metaData_17 = 
  inPlacePartSurfaceContact_17.getMetaData();

  // Rad_outlet (R)
  InPlacePartSurfaceContact inPlacePartSurfaceContact_19 = 
  ((InPlacePartSurfaceContact) partContact_13.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_18,partSurface_19));
  MetaData metaData_19 = 
  inPlacePartSurfaceContact_19.getMetaData();

  }
  //}
  
  //{ 4th | 5th Imprint try
  
  catch (Exception efff){
    
    
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
  MeshOperationPart meshOperationPart_4 = 
    ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("RADIATOR_L"));

  PartContact partContact_11 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_4,meshOperationPart_2);
  simulation_0.get(PartContactManager.class).removeObjects(partContact_11);
  
  }
  
  /*PartContact partContact_13 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_2,meshOperationPart_3);*/
  simulation_0.get(PartContactManager.class).removeObjects(partContact_13);

  tolerance = 5.0E-5;
  imprintPartsOperation_0.getTolerance().setValue(tolerance);
  imprintPartsOperation_0.execute();
  
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
  MeshOperationPart meshOperationPart_4 = 
    ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("RADIATOR_L"));
  
  PartContact partContact_2 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_2,meshOperationPart_4);
  
  PartSurface partSurface_4 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.SHROUD_L"));
  PartSurface partSurface_5 = 
  ((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.SHROUD_L"));
  PartSurface partSurface_6 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.RADIATOR_INLET_L"));
  PartSurface partSurface_7 = 
  ((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.RADIATOR_INLET_L"));
    PartSurface partSurface_0 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.RADIATOR_OUTLET_L"));
  PartSurface partSurface_16 = 
  ((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.RADIATOR_OUTLET_L"));

  try {
  InPlacePartSurfaceContact inPlacePartSurfaceContact_1 = 
  ((InPlacePartSurfaceContact) partContact_2.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_5,partSurface_6));
  partContact_2.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_1); 
  }
  catch (Exception e){}

  try {
  InPlacePartSurfaceContact inPlacePartSurfaceContact_3 = 
  ((InPlacePartSurfaceContact) partContact_2.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_5,partSurface_0));
  partContact_2.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_3);  
  }
  catch (Exception e){}

}
  
  /*PartSurface partSurface_12 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_R.SHROUD_R"));
  PartSurface partSurface_13 = 
  ((PartSurface) meshOperationPart_3.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_R.SHROUD_R"));
  PartSurface partSurface_14 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_R.RADIATOR_INLET_R"));
  PartSurface partSurface_15 = 
  ((PartSurface) meshOperationPart_3.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_R.RADIATOR_INLET_R"));
  PartSurface partSurface_18 = 
  ((PartSurface) meshOperationPart_3.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_R.RADIATOR_OUTLET_R"));
    PartSurface partSurface_19 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_R.RADIATOR_OUTLET_R"));*/

  try {
  InPlacePartSurfaceContact inPlacePartSurfaceContact_0 = 
  ((InPlacePartSurfaceContact) partContact_3.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_13,partSurface_14));
  partContact_3.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_0); 
  }
  catch(Exception e){}

  try {
  InPlacePartSurfaceContact inPlacePartSurfaceContact_5 = 
  ((InPlacePartSurfaceContact) partContact_3.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_13,partSurface_19));
  partContact_3.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_5);  
  }
  catch (Exception e){}

  try {
  
    
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
  MeshOperationPart meshOperationPart_4 = 
    ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("RADIATOR_L"));
    
  PartContact partContact_11 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_4,meshOperationPart_2);

  PartSurface partSurface_4 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.SHROUD_L"));
  PartSurface partSurface_5 = 
  ((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.SHROUD_L"));
  PartSurface partSurface_6 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.RADIATOR_INLET_L"));
  PartSurface partSurface_7 = 
  ((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.RADIATOR_INLET_L"));
  PartSurface partSurface_16 = 
  ((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.RADIATOR_OUTLET_L"));
    PartSurface partSurface_0 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.RADIATOR_OUTLET_L"));

  // Rad_Shroud (L)
  InPlacePartSurfaceContact inPlacePartSurfaceContact_12 = 
  ((InPlacePartSurfaceContact) partContact_11.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_4,partSurface_5));
  MetaData metaData_12 = 
  inPlacePartSurfaceContact_12.getMetaData();

  // Rad_Inlet (L)
  InPlacePartSurfaceContact inPlacePartSurfaceContact_13 = 
  ((InPlacePartSurfaceContact) partContact_11.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_6,partSurface_7));
  MetaData metaData_13 = 
  inPlacePartSurfaceContact_13.getMetaData();
  
  // Rad_outlet (L)
  InPlacePartSurfaceContact inPlacePartSurfaceContact_18 = 
  ((InPlacePartSurfaceContact) partContact_11.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_16,partSurface_0));
  MetaData metaData_18 = 
  inPlacePartSurfaceContact_18.getMetaData();
    
  }
    
  /*PartContact partContact_13 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_2,meshOperationPart_3);*/

  // Rad_Shroud (R)
  InPlacePartSurfaceContact inPlacePartSurfaceContact_16 = 
  ((InPlacePartSurfaceContact) partContact_13.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_12,partSurface_13));
  MetaData metaData_16 = 
  inPlacePartSurfaceContact_16.getMetaData();

  // Rad_Inlet (R)
  InPlacePartSurfaceContact inPlacePartSurfaceContact_17 = 
  ((InPlacePartSurfaceContact) partContact_13.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_14,partSurface_15));
  MetaData metaData_17 = 
  inPlacePartSurfaceContact_17.getMetaData();

  // Rad_outlet (R)
  InPlacePartSurfaceContact inPlacePartSurfaceContact_19 = 
  ((InPlacePartSurfaceContact) partContact_13.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_18,partSurface_19));
  MetaData metaData_19 = 
  inPlacePartSurfaceContact_19.getMetaData();
  }
  //}
  
  //{ 5th Imprint try

  catch (Exception effffff){
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
  MeshOperationPart meshOperationPart_4 = 
    ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("RADIATOR_L"));

  PartContact partContact_11 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_4,meshOperationPart_2);
  simulation_0.get(PartContactManager.class).removeObjects(partContact_11);
  
  }
  
  /*PartContact partContact_13 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_2,meshOperationPart_3);
  simulation_0.get(PartContactManager.class).removeObjects(partContact_13);*/

  tolerance = 1.0E-3;
  imprintPartsOperation_0.getTolerance().setValue(tolerance);
  imprintPartsOperation_0.execute();
  
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
  MeshOperationPart meshOperationPart_4 = 
    ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("RADIATOR_L"));
  
  PartContact partContact_2 = 
  simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_2,meshOperationPart_4);
  
  PartSurface partSurface_4 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.SHROUD_L"));
  PartSurface partSurface_5 = 
  ((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.SHROUD_L"));
  PartSurface partSurface_6 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.RADIATOR_INLET_L"));
  PartSurface partSurface_7 = 
  ((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.RADIATOR_INLET_L"));
    PartSurface partSurface_0 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.RADIATOR_OUTLET_L"));
  PartSurface partSurface_16 = 
  ((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_L.RADIATOR_OUTLET_L"));

  try {
  InPlacePartSurfaceContact inPlacePartSurfaceContact_1 = 
  ((InPlacePartSurfaceContact) partContact_2.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_5,partSurface_6));
  partContact_2.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_1); 
  }
  catch (Exception e){}

  try {
  InPlacePartSurfaceContact inPlacePartSurfaceContact_3 = 
  ((InPlacePartSurfaceContact) partContact_2.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_5,partSurface_0));
  partContact_2.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_3);  
  }
  catch (Exception e){}

}
  
  /*PartSurface partSurface_12 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_R.SHROUD_R"));
  PartSurface partSurface_13 = 
  ((PartSurface) meshOperationPart_3.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_R.SHROUD_R"));
  PartSurface partSurface_14 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_R.RADIATOR_INLET_R"));
  PartSurface partSurface_15 = 
  ((PartSurface) meshOperationPart_3.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_R.RADIATOR_INLET_R"));
  PartSurface partSurface_18 = 
  ((PartSurface) meshOperationPart_3.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_R.RADIATOR_OUTLET_R"));
    PartSurface partSurface_19 = 
  ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("CFD_FULL_ASSEMBLY.PT.RADIATOR_R.RADIATOR_OUTLET_R"));*/

  try {
  InPlacePartSurfaceContact inPlacePartSurfaceContact_0 = 
  ((InPlacePartSurfaceContact) partContact_3.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_13,partSurface_14));
  partContact_3.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_0); 
  }
  catch(Exception e){}

  try {
  InPlacePartSurfaceContact inPlacePartSurfaceContact_5 = 
  ((InPlacePartSurfaceContact) partContact_3.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_13,partSurface_19));
  partContact_3.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_5);  
  }
  catch (Exception e){}
}
  //}
  }
  }
  }
}

}

private void Mesh() {

  // AssigningSWToRegions //
  
  Simulation simulation_0 = 
      getActiveSimulation();
    
  double Roll = simulation_0.getReportManager().getReport("Roll [deg]").getReportMonitorValue();
  double Yaw = simulation_0.getReportManager().getReport("Yaw [rad]").getReportMonitorValue();
  double CoGX = simulation_0.getReportManager().getReport("CoGX").getReportMonitorValue();
  double CoGZ = simulation_0.getReportManager().getReport("CoGZ").getReportMonitorValue();
  double CornerRadius = simulation_0.getReportManager().getReport("Corner Radius [m]").getReportMonitorValue();
  double CoolingMode = simulation_0.getReportManager().getReport("Cooling Mode").getReportMonitorValue(); 

    MeshOperationPart meshOperationPart_2 = 
      ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("Fluid"));
  
    MeshOperationPart meshOperationPart_4 = 
      ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("RADIATOR_R"));

  if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
  if ( CoolingMode != 0) {

  MeshOperationPart meshOperationPart_1 = 
      ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("FAN_R"));
  simulation_0.getRegionManager().newRegionsFromParts(new NeoObjectVector(new Object[] {meshOperationPart_1, meshOperationPart_2, meshOperationPart_4}), "OneRegionPerPart", null, "OneBoundaryPerPartSurface", null, "OneFeatureCurve", null, RegionManager.CreateInterfaceMode.BOUNDARY); 
  AutoMeshOperation autoMeshOperation_0 = 
      simulation_0.get(MeshOperationManager.class).createAutoMeshOperation(new StringVector(new String[] {"star.resurfacer.ResurfacerAutoMesher", "star.resurfacer.AutomaticSurfaceRepairAutoMesher", "star.dualmesher.DualAutoMesher", "star.prismmesher.PrismAutoMesher"}), new NeoObjectVector(new Object[] {meshOperationPart_1, meshOperationPart_2, meshOperationPart_4}));
  
  }
  else {
    simulation_0.getRegionManager().newRegionsFromParts(new NeoObjectVector(new Object[] {meshOperationPart_2, meshOperationPart_4}), "OneRegionPerPart", null, "OneBoundaryPerPartSurface", null, "OneFeatureCurve", null, RegionManager.CreateInterfaceMode.BOUNDARY); 
  AutoMeshOperation autoMeshOperation_0 = 
      simulation_0.get(MeshOperationManager.class).createAutoMeshOperation(new StringVector(new String[] {"star.resurfacer.ResurfacerAutoMesher", "star.resurfacer.AutomaticSurfaceRepairAutoMesher", "star.dualmesher.DualAutoMesher", "star.prismmesher.PrismAutoMesher"}), new NeoObjectVector(new Object[] {meshOperationPart_2, meshOperationPart_4}));
  }

  } else {
    
  MeshOperationPart meshOperationPart_3 = 
      ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("RADIATOR_L"));

   if ( CoolingMode != 0) {
    MeshOperationPart meshOperationPart_0 = 
      ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("FAN_L"));
    MeshOperationPart meshOperationPart_1 = 
      ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("FAN_R"));
    simulation_0.getRegionManager().newRegionsFromParts(new NeoObjectVector(new Object[] {meshOperationPart_0, meshOperationPart_1, meshOperationPart_2, meshOperationPart_3, meshOperationPart_4}), "OneRegionPerPart", null, "OneBoundaryPerPartSurface", null, "OneFeatureCurve", null, RegionManager.CreateInterfaceMode.BOUNDARY);
    AutoMeshOperation autoMeshOperation_0 = 
      simulation_0.get(MeshOperationManager.class).createAutoMeshOperation(new StringVector(new String[] {"star.resurfacer.ResurfacerAutoMesher", "star.resurfacer.AutomaticSurfaceRepairAutoMesher", "star.dualmesher.DualAutoMesher", "star.prismmesher.PrismAutoMesher"}), new NeoObjectVector(new Object[] {meshOperationPart_0,meshOperationPart_1,meshOperationPart_2, meshOperationPart_3, meshOperationPart_4}));
    }

  else {
    simulation_0.getRegionManager().newRegionsFromParts(new NeoObjectVector(new Object[] {meshOperationPart_2, meshOperationPart_3, meshOperationPart_4}), "OneRegionPerPart", null, "OneBoundaryPerPartSurface", null, "OneFeatureCurve", null, RegionManager.CreateInterfaceMode.BOUNDARY);
  AutoMeshOperation autoMeshOperation_0 = 
      simulation_0.get(MeshOperationManager.class).createAutoMeshOperation(new StringVector(new String[] {"star.resurfacer.ResurfacerAutoMesher", "star.resurfacer.AutomaticSurfaceRepairAutoMesher", "star.dualmesher.DualAutoMesher", "star.prismmesher.PrismAutoMesher"}), new NeoObjectVector(new Object[] {meshOperationPart_2, meshOperationPart_3, meshOperationPart_4}));
  }
    }

  AutoMeshOperation autoMeshOperation_0 = 
      ((AutoMeshOperation) simulation_0.get(MeshOperationManager.class).getObject("Automated Mesh"));
  
    autoMeshOperation_0.setMeshPartByPart(true);
    autoMeshOperation_0.getMesherParallelModeOption().setSelected(MesherParallelModeOption.Type.PARALLEL);
    autoMeshOperation_0.setPresentationName("Mesh");

    CompositePart compositePart_1 = 
      ((CompositePart) simulation_0.get(SimulationPartManager.class).getPart("CFD_FULL_ASSEMBLY"));

    CompositePart compositePart_0 = 
      ((CompositePart) compositePart_1.getChildParts().getPart("CH"));

    CadPart cadPart_1 = 
      ((CadPart) compositePart_0.getChildParts().getPart("HR"));

      // Default values Automated Mesh //
    
  Units units_0 = 
      simulation_0.getUnitsManager().getPreferredUnits(new IntVector(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));
    Units units_1 = 
      simulation_0.getUnitsManager().getInternalUnits(new IntVector(new int[] {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));
  Units units_2 = 
      ((Units) simulation_0.getUnitsManager().getObject("mm"));

  autoMeshOperation_0.getDefaultValues().get(BaseSize.class).setDefinition("0.35*(pow(${Ri},1/3))");

  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
  PartsMinimumSurfaceSize partsMinimumSurfaceSize_0 = 
      autoMeshOperation_0.getDefaultValues().get(PartsMinimumSurfaceSize.class);
    partsMinimumSurfaceSize_0.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsMinimumSurfaceSize_0.getAbsoluteSizeValue()).setDefinition("0.0013*(pow(${Ri},1/3))");
  }
  else {
    PartsMinimumSurfaceSize partsMinimumSurfaceSize_0 = 
      autoMeshOperation_0.getDefaultValues().get(PartsMinimumSurfaceSize.class);
    partsMinimumSurfaceSize_0.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsMinimumSurfaceSize_0.getAbsoluteSizeValue()).setDefinition("0.0013*(pow(${Ri},1/3))");
  }

    SurfaceGrowthRate surfaceGrowthRate_0 = 
      autoMeshOperation_0.getDefaultValues().get(SurfaceGrowthRate.class);
    surfaceGrowthRate_0.setGrowthRateOption(SurfaceGrowthRate.GrowthRateOption.USER_SPECIFIED);
    surfaceGrowthRate_0.getGrowthRateScalar().setValue(1.35);
    surfaceGrowthRate_0.getGrowthRateScalar().setUnits(units_0);

    PartsTetPolyGrowthRate partsTetPolyGrowthRate_0 = 
      autoMeshOperation_0.getDefaultValues().get(PartsTetPolyGrowthRate.class);
    partsTetPolyGrowthRate_0.setGrowthRate(1.25);

  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
  NumPrismLayers numPrismLayers_0 = 
      autoMeshOperation_0.getDefaultValues().get(NumPrismLayers.class);
    IntegerValue integerValue_0 = 
      numPrismLayers_0.getNumLayersValue();
    integerValue_0.getQuantity().setDefinition("11/(pow(${Ri},1/3))");
  }
  else {
    NumPrismLayers numPrismLayers_0 = 
      autoMeshOperation_0.getDefaultValues().get(NumPrismLayers.class);
    IntegerValue integerValue_0 = 
      numPrismLayers_0.getNumLayersValue();
    integerValue_0.getQuantity().setDefinition("11/(pow(${Ri},1/3))");
  }

    PrismAutoMesher prismAutoMesher_0 = 
      ((PrismAutoMesher) autoMeshOperation_0.getMeshers().getObject("Prism Layer Mesher"));
    prismAutoMesher_0.getPrismStretchingOption().setSelected(PrismStretchingOption.Type.WALL_THICKNESS);
    prismAutoMesher_0.setGapFillPercentage(49.0);
    prismAutoMesher_0.setLayerChoppingPercentage(75.0);
    PrismThickness prismThickness_0 = 
      autoMeshOperation_0.getDefaultValues().get(PrismThickness.class);
    prismThickness_0.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) prismThickness_0.getAbsoluteSizeValue()).setValue(6.0);
    ((ScalarPhysicalQuantity) prismThickness_0.getAbsoluteSizeValue()).setUnits(units_2);
    autoMeshOperation_0.getDefaultValues().get(PrismWallThickness.class).setDefinition("2.0E-5/(pow(${Ri},1/3))");

    PartsPostMeshOptimizerBase partsPostMeshOptimizerBase_0 = 
      autoMeshOperation_0.getDefaultValues().get(PartsPostMeshOptimizerBase.class);
    partsPostMeshOptimizerBase_0.setOptimizeTopology(true);
    partsPostMeshOptimizerBase_0.setOptimizeBoundary(true);

  // General Refinement //

    if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    SurfaceCustomMeshControl surfaceCustomMeshControl_6 = 
    autoMeshOperation_0.getCustomMeshControls().createSurfaceControl();
    surfaceCustomMeshControl_6.setPresentationName("Car");
    surfaceCustomMeshControl_6.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);
    surfaceCustomMeshControl_6.getGeometryObjects().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "BH"), new InheritedPartPredicate(InheritedPartOperator.Inherits, Arrays.<ClientServerObject>asList(cadPart_1)), new NamePredicate(NameOperator.Contains, "Main_H"), new NamePredicate(NameOperator.Contains, "DRIVER"), new NamePredicate(NameOperator.Contains, "MONO"), new NamePredicate(NameOperator.Contains, "RW_S"))), Query.STANDARD_MODIFIERS));
    PartsTargetSurfaceSize partsTargetSurfaceSize_100 = 
      surfaceCustomMeshControl_6.getCustomValues().get(PartsTargetSurfaceSize.class);
    partsTargetSurfaceSize_100.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_100.getAbsoluteSizeValue()).setDefinition("0.01*(pow(${Ri},1/3))");
  }
  else {
    SurfaceCustomMeshControl surfaceCustomMeshControl_6 = 
    autoMeshOperation_0.getCustomMeshControls().createSurfaceControl();
    surfaceCustomMeshControl_6.setPresentationName("Car");
    surfaceCustomMeshControl_6.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);
    surfaceCustomMeshControl_6.getGeometryObjects().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "BH"), new InheritedPartPredicate(InheritedPartOperator.Inherits, Arrays.<ClientServerObject>asList(cadPart_1)), new NamePredicate(NameOperator.Contains, "Main_H"), new NamePredicate(NameOperator.Contains, "DRIVER"), new NamePredicate(NameOperator.Contains, "MONO"), new NamePredicate(NameOperator.Contains, "RW_S"))), Query.STANDARD_MODIFIERS));
    PartsTargetSurfaceSize partsTargetSurfaceSize_100 = 
      surfaceCustomMeshControl_6.getCustomValues().get(PartsTargetSurfaceSize.class);
    partsTargetSurfaceSize_100.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_100.getAbsoluteSizeValue()).setDefinition("0.01*(pow(${Ri},1/3))");
  }

  // Aero Package //

  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
  SurfaceCustomMeshControl surfaceCustomMeshControl_5 = 
      autoMeshOperation_0.getCustomMeshControls().createSurfaceControl();
    surfaceCustomMeshControl_5.setPresentationName("Aero Package");
    surfaceCustomMeshControl_5.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);
    PartsTargetSurfaceSize partsTargetSurfaceSize_190 = 
      surfaceCustomMeshControl_5.getCustomValues().get(PartsTargetSurfaceSize.class);
    partsTargetSurfaceSize_190.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_190.getAbsoluteSizeValue()).setDefinition("0.005*(pow(${Ri},1/3))");
    surfaceCustomMeshControl_5.getGeometryObjects().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.DoesNotContain, "MONO"), new NamePredicate(NameOperator.DoesNotContain, "MAIN_HOOP"), new NamePredicate(NameOperator.DoesNotContain, "DRIVER"), new NamePredicate(NameOperator.DoesNotContain, "BH"), new NamePredicate(NameOperator.DoesNotContain, "HR"), new NamePredicate(NameOperator.DoesNotContain, "FW"), new NamePredicate(NameOperator.DoesNotContain, "SU_"), new NamePredicate(NameOperator.DoesNotContain, "SC"), new NamePredicate(NameOperator.DoesNotContain, "TYRE"), new NamePredicate(NameOperator.DoesNotContain, "FAN"), new NamePredicate(NameOperator.DoesNotContain, "RW"), new NamePredicate(NameOperator.DoesNotContain, "RADIATOR"), new NamePredicate(NameOperator.DoesNotContain, "Domain"), new TypePredicate(TypeOperator.Is, PartSurface.class))), new NamePredicate(NameOperator.Contains, "DIFF"), new NamePredicate(NameOperator.Contains, "Misc"))), Query.STANDARD_MODIFIERS));
    PartsCustomizePrismMesh partsCustomizePrismMesh_0 = 
      surfaceCustomMeshControl_5.getCustomConditions().get(PartsCustomizePrismMesh.class);
    partsCustomizePrismMesh_0.getCustomPrismOptions().setSelected(PartsCustomPrismsOption.Type.CUSTOMIZE);
    PartsCustomizePrismMeshControls partsCustomizePrismMeshControls_0 = 
      partsCustomizePrismMesh_0.getCustomPrismControls();
    partsCustomizePrismMeshControls_0.setCustomizeNumLayers(true);
    partsCustomizePrismMeshControls_0.setCustomizeTotalThickness(true);
    NumPrismLayers numPrismLayers_1 = 
      surfaceCustomMeshControl_5.getCustomValues().get(CustomPrismValuesManager.class).get(NumPrismLayers.class);
    IntegerValue integerValue_1 = 
      numPrismLayers_1.getNumLayersValue();
    integerValue_1.getQuantity().setDefinition("18/(pow(${Ri},1/3))");
    PrismThickness prismThickness_1 = 
      surfaceCustomMeshControl_5.getCustomValues().get(CustomPrismValuesManager.class).get(PrismThickness.class);
    prismThickness_1.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) prismThickness_1.getAbsoluteSizeValue()).setUnits(units_2);
    ((ScalarPhysicalQuantity) prismThickness_1.getAbsoluteSizeValue()).setValue(6.0);
    surfaceCustomMeshControl_5.getCustomConditions().get(PartsSurfaceCurvatureOption.class).setSelected(PartsSurfaceCurvatureOption.Type.CUSTOM_VALUES);
    SurfaceCurvature surfaceCurvature_5 = 
      surfaceCustomMeshControl_5.getCustomValues().get(SurfaceCurvature.class);
    surfaceCurvature_5.setEnableCurvatureDeviationDist(true);
    surfaceCurvature_5.setNumPointsAroundCircle(50.0);
    surfaceCurvature_5.getCurvatureDeviationDistance().setValue(1.0);
    surfaceCurvature_5.getCurvatureDeviationDistance().setUnits(units_2);
  }
  else {
    SurfaceCustomMeshControl surfaceCustomMeshControl_5 = 
      autoMeshOperation_0.getCustomMeshControls().createSurfaceControl();
    surfaceCustomMeshControl_5.setPresentationName("Aero Package");
    surfaceCustomMeshControl_5.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);
    PartsTargetSurfaceSize partsTargetSurfaceSize_190 = 
      surfaceCustomMeshControl_5.getCustomValues().get(PartsTargetSurfaceSize.class);
    partsTargetSurfaceSize_190.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_190.getAbsoluteSizeValue()).setDefinition("0.005*(pow(${Ri},1/3))");
    surfaceCustomMeshControl_5.getGeometryObjects().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.DoesNotContain, "MONO"), new NamePredicate(NameOperator.DoesNotContain, "MAIN_HOOP"), new NamePredicate(NameOperator.DoesNotContain, "DRIVER"), new NamePredicate(NameOperator.DoesNotContain, "BH"), new NamePredicate(NameOperator.DoesNotContain, "HR"), new NamePredicate(NameOperator.DoesNotContain, "FW"), new NamePredicate(NameOperator.DoesNotContain, "SU_"), new NamePredicate(NameOperator.DoesNotContain, "SC"), new NamePredicate(NameOperator.DoesNotContain, "TYRE"), new NamePredicate(NameOperator.DoesNotContain, "FAN"), new NamePredicate(NameOperator.DoesNotContain, "RW"), new NamePredicate(NameOperator.DoesNotContain, "RADIATOR"), new NamePredicate(NameOperator.DoesNotContain, "Domain"), new TypePredicate(TypeOperator.Is, PartSurface.class))), new NamePredicate(NameOperator.Contains, "DIFF"), new NamePredicate(NameOperator.Contains, "Misc"))), Query.STANDARD_MODIFIERS));
    PartsCustomizePrismMesh partsCustomizePrismMesh_0 = 
      surfaceCustomMeshControl_5.getCustomConditions().get(PartsCustomizePrismMesh.class);
    partsCustomizePrismMesh_0.getCustomPrismOptions().setSelected(PartsCustomPrismsOption.Type.CUSTOMIZE);
    PartsCustomizePrismMeshControls partsCustomizePrismMeshControls_0 = 
      partsCustomizePrismMesh_0.getCustomPrismControls();
    partsCustomizePrismMeshControls_0.setCustomizeNumLayers(true);
    partsCustomizePrismMeshControls_0.setCustomizeTotalThickness(true);
    NumPrismLayers numPrismLayers_1 = 
      surfaceCustomMeshControl_5.getCustomValues().get(CustomPrismValuesManager.class).get(NumPrismLayers.class);
    IntegerValue integerValue_1 = 
      numPrismLayers_1.getNumLayersValue();
    integerValue_1.getQuantity().setDefinition("18/(pow(${Ri},1/3))");
    PrismThickness prismThickness_1 = 
      surfaceCustomMeshControl_5.getCustomValues().get(CustomPrismValuesManager.class).get(PrismThickness.class);
    prismThickness_1.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) prismThickness_1.getAbsoluteSizeValue()).setUnits(units_2);
    ((ScalarPhysicalQuantity) prismThickness_1.getAbsoluteSizeValue()).setValue(6.0);
    surfaceCustomMeshControl_5.getCustomConditions().get(PartsSurfaceCurvatureOption.class).setSelected(PartsSurfaceCurvatureOption.Type.CUSTOM_VALUES);
    SurfaceCurvature surfaceCurvature_5 = 
      surfaceCustomMeshControl_5.getCustomValues().get(SurfaceCurvature.class);
    surfaceCurvature_5.setEnableCurvatureDeviationDist(true);
    surfaceCurvature_5.setNumPointsAroundCircle(50.0);
    surfaceCurvature_5.getCurvatureDeviationDistance().setValue(1.0);
    surfaceCurvature_5.getCurvatureDeviationDistance().setUnits(units_2);
  }

  // FW //

    if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    SurfaceCustomMeshControl surfaceCustomMeshControl_8 = 
      autoMeshOperation_0.getCustomMeshControls().createSurfaceControl();
    surfaceCustomMeshControl_8.setPresentationName("FW");
    surfaceCustomMeshControl_8.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);
    PartsTargetSurfaceSize partsTargetSurfaceSize_1900 = 
      surfaceCustomMeshControl_8.getCustomValues().get(PartsTargetSurfaceSize.class);
    partsTargetSurfaceSize_1900.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    surfaceCustomMeshControl_8.getGeometryObjects().setQuery(new Query(new NamePredicate(NameOperator.Contains, "FW"), Query.STANDARD_MODIFIERS));
    PartsCustomizePrismMesh partsCustomizePrismMesh_2 = 
      surfaceCustomMeshControl_8.getCustomConditions().get(PartsCustomizePrismMesh.class);
    partsCustomizePrismMesh_2.getCustomPrismOptions().setSelected(PartsCustomPrismsOption.Type.CUSTOMIZE);
    PartsCustomizePrismMeshControls partsCustomizePrismMeshControls_1 = 
      partsCustomizePrismMesh_2.getCustomPrismControls();
    partsCustomizePrismMeshControls_1.setCustomizeTotalThickness(true);
    partsCustomizePrismMeshControls_1.setCustomizeNumLayers(true);
    NumPrismLayers numPrismLayers_2 = 
      surfaceCustomMeshControl_8.getCustomValues().get(CustomPrismValuesManager.class).get(NumPrismLayers.class);
    IntegerValue integerValue_2 = 
      numPrismLayers_2.getNumLayersValue();
    integerValue_2.getQuantity().setDefinition("18/(pow(${Ri},1/3))");
    PrismThickness prismThickness_2 = 
      surfaceCustomMeshControl_8.getCustomValues().get(CustomPrismValuesManager.class).get(PrismThickness.class);
    prismThickness_2.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) prismThickness_2.getAbsoluteSizeValue()).setUnits(units_2);
    ((ScalarPhysicalQuantity) prismThickness_2.getAbsoluteSizeValue()).setValue(4.0);
    PartsTargetSurfaceSize partsTargetSurfaceSize_10 = 
      surfaceCustomMeshControl_8.getCustomValues().get(PartsTargetSurfaceSize.class);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_10.getAbsoluteSizeValue()).setDefinition("0.0045*(pow(${Ri},1/3))");
    surfaceCustomMeshControl_8.getCustomConditions().get(PartsSurfaceCurvatureOption.class).setSelected(PartsSurfaceCurvatureOption.Type.CUSTOM_VALUES);
	surfaceCustomMeshControl_8.getCustomConditions().get(PartsMinimumSurfaceSizeOption.class).setSelected(PartsMinimumSurfaceSizeOption.Type.CUSTOM);
    SurfaceCurvature surfaceCurvature_8 = 
      surfaceCustomMeshControl_8.getCustomValues().get(SurfaceCurvature.class);
    surfaceCurvature_8.setEnableCurvatureDeviationDist(true);
    surfaceCurvature_8.setNumPointsAroundCircle(50.0);
    surfaceCurvature_8.getCurvatureDeviationDistance().setValue(1.0);
    surfaceCurvature_8.getCurvatureDeviationDistance().setUnits(units_2);

    PartsMinimumSurfaceSize partsMinimumSurfaceSize_2 = 
      surfaceCustomMeshControl_8.getCustomValues().get(PartsMinimumSurfaceSize.class);
    partsMinimumSurfaceSize_2.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsMinimumSurfaceSize_2.getAbsoluteSizeValue()).setDefinition("7.5E-4*(pow(${Ri},1/3))");
  }
  else {
    SurfaceCustomMeshControl surfaceCustomMeshControl_8 = 
      autoMeshOperation_0.getCustomMeshControls().createSurfaceControl();
    surfaceCustomMeshControl_8.setPresentationName("FW");
    surfaceCustomMeshControl_8.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);
    PartsTargetSurfaceSize partsTargetSurfaceSize_1900 = 
      surfaceCustomMeshControl_8.getCustomValues().get(PartsTargetSurfaceSize.class);
    partsTargetSurfaceSize_1900.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    surfaceCustomMeshControl_8.getGeometryObjects().setQuery(new Query(new NamePredicate(NameOperator.Contains, "FW"), Query.STANDARD_MODIFIERS));
    PartsCustomizePrismMesh partsCustomizePrismMesh_2 = 
      surfaceCustomMeshControl_8.getCustomConditions().get(PartsCustomizePrismMesh.class);
    partsCustomizePrismMesh_2.getCustomPrismOptions().setSelected(PartsCustomPrismsOption.Type.CUSTOMIZE);
    PartsCustomizePrismMeshControls partsCustomizePrismMeshControls_1 = 
      partsCustomizePrismMesh_2.getCustomPrismControls();
    partsCustomizePrismMeshControls_1.setCustomizeTotalThickness(true);
    partsCustomizePrismMeshControls_1.setCustomizeNumLayers(true);
    NumPrismLayers numPrismLayers_2 = 
      surfaceCustomMeshControl_8.getCustomValues().get(CustomPrismValuesManager.class).get(NumPrismLayers.class);
    IntegerValue integerValue_2 = 
      numPrismLayers_2.getNumLayersValue();
    integerValue_2.getQuantity().setDefinition("18*(pow(${Ri},1/3))");
    PrismThickness prismThickness_2 = 
      surfaceCustomMeshControl_8.getCustomValues().get(CustomPrismValuesManager.class).get(PrismThickness.class);
    prismThickness_2.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) prismThickness_2.getAbsoluteSizeValue()).setUnits(units_2);
    ((ScalarPhysicalQuantity) prismThickness_2.getAbsoluteSizeValue()).setValue(4.0);
    PartsTargetSurfaceSize partsTargetSurfaceSize_10 = 
      surfaceCustomMeshControl_8.getCustomValues().get(PartsTargetSurfaceSize.class);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_10.getAbsoluteSizeValue()).setDefinition("0.0045*(pow(${Ri},1/3))");
    surfaceCustomMeshControl_8.getCustomConditions().get(PartsSurfaceCurvatureOption.class).setSelected(PartsSurfaceCurvatureOption.Type.CUSTOM_VALUES);
	surfaceCustomMeshControl_8.getCustomConditions().get(PartsMinimumSurfaceSizeOption.class).setSelected(PartsMinimumSurfaceSizeOption.Type.CUSTOM);
    SurfaceCurvature surfaceCurvature_8 = 
      surfaceCustomMeshControl_8.getCustomValues().get(SurfaceCurvature.class);
    surfaceCurvature_8.setEnableCurvatureDeviationDist(true);
    surfaceCurvature_8.setNumPointsAroundCircle(50.0);
    surfaceCurvature_8.getCurvatureDeviationDistance().setValue(1.0);
    surfaceCurvature_8.getCurvatureDeviationDistance().setUnits(units_2);

    PartsMinimumSurfaceSize partsMinimumSurfaceSize_2 = 
      surfaceCustomMeshControl_8.getCustomValues().get(PartsMinimumSurfaceSize.class);
    partsMinimumSurfaceSize_2.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsMinimumSurfaceSize_2.getAbsoluteSizeValue()).setDefinition("7.5E-4*(pow(${Ri},1/3))");
  }


  // SU //

        if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
      SurfaceCustomMeshControl surfaceCustomMeshControl_10 = 
      autoMeshOperation_0.getCustomMeshControls().createSurfaceControl();
    surfaceCustomMeshControl_10.setPresentationName("SU");
    surfaceCustomMeshControl_10.getGeometryObjects().setQuery(new Query(new NamePredicate(NameOperator.Contains, "SU_"), Query.STANDARD_MODIFIERS));
    surfaceCustomMeshControl_10.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);
    PartsTargetSurfaceSize partsTargetSurfaceSize_10 = 
      surfaceCustomMeshControl_10.getCustomValues().get(PartsTargetSurfaceSize.class);
    partsTargetSurfaceSize_10.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_10.getAbsoluteSizeValue()).setDefinition("0.01*(pow(${Ri},1/3))");
    PartsCustomizePrismMesh partsCustomizePrismMesh_10 = 
      surfaceCustomMeshControl_10.getCustomConditions().get(PartsCustomizePrismMesh.class);
    partsCustomizePrismMesh_10.getCustomPrismOptions().setSelected(PartsCustomPrismsOption.Type.DISABLE);
  }
  else {
      SurfaceCustomMeshControl surfaceCustomMeshControl_10 = 
      autoMeshOperation_0.getCustomMeshControls().createSurfaceControl();
    surfaceCustomMeshControl_10.setPresentationName("SU");
    surfaceCustomMeshControl_10.getGeometryObjects().setQuery(new Query(new NamePredicate(NameOperator.Contains, "SU_"), Query.STANDARD_MODIFIERS));
    surfaceCustomMeshControl_10.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);
    PartsTargetSurfaceSize partsTargetSurfaceSize_10 = 
      surfaceCustomMeshControl_10.getCustomValues().get(PartsTargetSurfaceSize.class);
    partsTargetSurfaceSize_10.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_10.getAbsoluteSizeValue()).setDefinition("0.01*(pow(${Ri},1/3))");
    PartsCustomizePrismMesh partsCustomizePrismMesh_10 = 
      surfaceCustomMeshControl_10.getCustomConditions().get(PartsCustomizePrismMesh.class);
    partsCustomizePrismMesh_10.getCustomPrismOptions().setSelected(PartsCustomPrismsOption.Type.DISABLE);
  }

    // Tyres //

        if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
      SurfaceCustomMeshControl surfaceCustomMeshControl_11 = 
      autoMeshOperation_0.getCustomMeshControls().createSurfaceControl();
    surfaceCustomMeshControl_11.setPresentationName("Tyres");
    surfaceCustomMeshControl_11.getGeometryObjects().setQuery(new Query(new NamePredicate(NameOperator.Contains, "TYRE"), Query.STANDARD_MODIFIERS));
    surfaceCustomMeshControl_11.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);
	surfaceCustomMeshControl_11.getCustomConditions().get(PartsMinimumSurfaceSizeOption.class).setSelected(PartsMinimumSurfaceSizeOption.Type.CUSTOM);
    PartsTargetSurfaceSize partsTargetSurfaceSize_11 = 
      surfaceCustomMeshControl_11.getCustomValues().get(PartsTargetSurfaceSize.class);
    partsTargetSurfaceSize_11.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_11.getAbsoluteSizeValue()).setDefinition("0.007*(pow(${Ri},1/3))");
     PartsCustomizePrismMesh partsCustomizePrismMesh_11 = 
      surfaceCustomMeshControl_11.getCustomConditions().get(PartsCustomizePrismMesh.class);
    partsCustomizePrismMesh_11.getCustomPrismOptions().setSelected(PartsCustomPrismsOption.Type.DISABLE);

    PartsMinimumSurfaceSize partsMinimumSurfaceSize_8 = 
      surfaceCustomMeshControl_11.getCustomValues().get(PartsMinimumSurfaceSize.class);
      partsMinimumSurfaceSize_8.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsMinimumSurfaceSize_8.getAbsoluteSizeValue()).setDefinition("0.0015*(pow(${Ri},1/3))");
  }
  else {
      SurfaceCustomMeshControl surfaceCustomMeshControl_11 = 
      autoMeshOperation_0.getCustomMeshControls().createSurfaceControl();
    surfaceCustomMeshControl_11.setPresentationName("Tyres");
    surfaceCustomMeshControl_11.getGeometryObjects().setQuery(new Query(new NamePredicate(NameOperator.Contains, "TYRE"), Query.STANDARD_MODIFIERS));
    surfaceCustomMeshControl_11.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);
	surfaceCustomMeshControl_11.getCustomConditions().get(PartsMinimumSurfaceSizeOption.class).setSelected(PartsMinimumSurfaceSizeOption.Type.CUSTOM);
    PartsTargetSurfaceSize partsTargetSurfaceSize_11 = 
      surfaceCustomMeshControl_11.getCustomValues().get(PartsTargetSurfaceSize.class);
    partsTargetSurfaceSize_11.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_11.getAbsoluteSizeValue()).setDefinition("0.007*(pow(${Ri},1/3))");
    PartsCustomizePrismMesh partsCustomizePrismMesh_11 = 
      surfaceCustomMeshControl_11.getCustomConditions().get(PartsCustomizePrismMesh.class);
    partsCustomizePrismMesh_11.getCustomPrismOptions().setSelected(PartsCustomPrismsOption.Type.DISABLE);

    PartsMinimumSurfaceSize partsMinimumSurfaceSize_8 = 
      surfaceCustomMeshControl_11.getCustomValues().get(PartsMinimumSurfaceSize.class);
    partsMinimumSurfaceSize_8.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsMinimumSurfaceSize_8.getAbsoluteSizeValue()).setDefinition("0.0015*(pow(${Ri},1/3))");
  }

      // Radiator & Fan //

        if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
      SurfaceCustomMeshControl surfaceCustomMeshControl_12 = 
      autoMeshOperation_0.getCustomMeshControls().createSurfaceControl();
    surfaceCustomMeshControl_12.setPresentationName("Radiator - Fan");
    surfaceCustomMeshControl_12.getGeometryObjects().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "FAN"), new NamePredicate(NameOperator.Contains, "RADIATOR"))), Query.STANDARD_MODIFIERS));
    surfaceCustomMeshControl_12.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);
    PartsTargetSurfaceSize partsTargetSurfaceSize_12 = 
      surfaceCustomMeshControl_12.getCustomValues().get(PartsTargetSurfaceSize.class);
    partsTargetSurfaceSize_12.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
  PartsCustomizePrismMesh partsCustomizePrismMesh_12 = 
      surfaceCustomMeshControl_12.getCustomConditions().get(PartsCustomizePrismMesh.class);
    partsCustomizePrismMesh_12.getCustomPrismOptions().setSelected(PartsCustomPrismsOption.Type.DISABLE);
  ((ScalarPhysicalQuantity) partsTargetSurfaceSize_12.getAbsoluteSizeValue()).setDefinition("0.005*(pow(${Ri},1/3))");
  }
  else {
      SurfaceCustomMeshControl surfaceCustomMeshControl_12 = 
      autoMeshOperation_0.getCustomMeshControls().createSurfaceControl();
    surfaceCustomMeshControl_12.setPresentationName("Radiator - Fan");
    surfaceCustomMeshControl_12.getGeometryObjects().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "FAN"), new NamePredicate(NameOperator.Contains, "RADIATOR"))), Query.STANDARD_MODIFIERS));
    surfaceCustomMeshControl_12.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);
    PartsTargetSurfaceSize partsTargetSurfaceSize_12 = 
      surfaceCustomMeshControl_12.getCustomValues().get(PartsTargetSurfaceSize.class);
    partsTargetSurfaceSize_12.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
  PartsCustomizePrismMesh partsCustomizePrismMesh_12 = 
      surfaceCustomMeshControl_12.getCustomConditions().get(PartsCustomizePrismMesh.class);
    partsCustomizePrismMesh_12.getCustomPrismOptions().setSelected(PartsCustomPrismsOption.Type.DISABLE);
  ((ScalarPhysicalQuantity) partsTargetSurfaceSize_12.getAbsoluteSizeValue()).setDefinition("0.005*(pow(${Ri},1/3))");
  }

  // RW //

    if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    SurfaceCustomMeshControl surfaceCustomMeshControl_13 = 
      autoMeshOperation_0.getCustomMeshControls().createSurfaceControl();
    surfaceCustomMeshControl_13.setPresentationName("RW");
    surfaceCustomMeshControl_13.getGeometryObjects().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "RW"), new NamePredicate(NameOperator.DoesNotContain, "RW_S"))), Query.STANDARD_MODIFIERS));
    surfaceCustomMeshControl_13.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);
    PartsCustomizePrismMesh partsCustomizePrismMesh_13 = 
      surfaceCustomMeshControl_13.getCustomConditions().get(PartsCustomizePrismMesh.class);
    partsCustomizePrismMesh_13.getCustomPrismOptions().setSelected(PartsCustomPrismsOption.Type.CUSTOMIZE);
    PartsCustomizePrismMeshControls partsCustomizePrismMeshControls_13 = 
      partsCustomizePrismMesh_13.getCustomPrismControls();
    partsCustomizePrismMeshControls_13.setCustomizeNumLayers(true);
    PartsTargetSurfaceSize partsTargetSurfaceSize_13 = 
      surfaceCustomMeshControl_13.getCustomValues().get(PartsTargetSurfaceSize.class);
    partsTargetSurfaceSize_13.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_13.getAbsoluteSizeValue()).setDefinition("0.005*(pow(${Ri},1/3))");
    NumPrismLayers numPrismLayers_13 = 
      surfaceCustomMeshControl_13.getCustomValues().get(CustomPrismValuesManager.class).get(NumPrismLayers.class);
    IntegerValue integerValue_13 = 
      numPrismLayers_13.getNumLayersValue();
    integerValue_13.getQuantity().setDefinition("12/(pow(${Ri},1/3))");
  }
  else {
    SurfaceCustomMeshControl surfaceCustomMeshControl_13 = 
      autoMeshOperation_0.getCustomMeshControls().createSurfaceControl();
    surfaceCustomMeshControl_13.setPresentationName("RW");
    surfaceCustomMeshControl_13.getGeometryObjects().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "RW"), new NamePredicate(NameOperator.DoesNotContain, "RW_S"))), Query.STANDARD_MODIFIERS));
    surfaceCustomMeshControl_13.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);
    PartsCustomizePrismMesh partsCustomizePrismMesh_13 = 
      surfaceCustomMeshControl_13.getCustomConditions().get(PartsCustomizePrismMesh.class);
    partsCustomizePrismMesh_13.getCustomPrismOptions().setSelected(PartsCustomPrismsOption.Type.CUSTOMIZE);
    PartsCustomizePrismMeshControls partsCustomizePrismMeshControls_13 = 
      partsCustomizePrismMesh_13.getCustomPrismControls();
    partsCustomizePrismMeshControls_13.setCustomizeNumLayers(true);
    PartsTargetSurfaceSize partsTargetSurfaceSize_13 = 
      surfaceCustomMeshControl_13.getCustomValues().get(PartsTargetSurfaceSize.class);
    partsTargetSurfaceSize_13.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_13.getAbsoluteSizeValue()).setDefinition("0.005*(pow(${Ri},1/3))");
    NumPrismLayers numPrismLayers_13 = 
      surfaceCustomMeshControl_13.getCustomValues().get(CustomPrismValuesManager.class).get(NumPrismLayers.class);
    IntegerValue integerValue_13 = 
      numPrismLayers_13.getNumLayersValue();
    integerValue_13.getQuantity().setDefinition("12/(pow(${Ri},1/3))");
  }

  // Side Elements //

    if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    SurfaceCustomMeshControl surfaceCustomMeshControl_14 = 
      autoMeshOperation_0.getCustomMeshControls().createSurfaceControl();
    surfaceCustomMeshControl_14.setPresentationName("Side Elements");
    surfaceCustomMeshControl_14.getGeometryObjects().setQuery(new Query(new NamePredicate(NameOperator.Contains, "SC"), Query.STANDARD_MODIFIERS));
    surfaceCustomMeshControl_14.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);
    PartsCustomizePrismMesh partsCustomizePrismMesh_14 = 
      surfaceCustomMeshControl_14.getCustomConditions().get(PartsCustomizePrismMesh.class);
    partsCustomizePrismMesh_14.getCustomPrismOptions().setSelected(PartsCustomPrismsOption.Type.CUSTOMIZE);
    PartsCustomizePrismMeshControls partsCustomizePrismMeshControls_14 = 
      partsCustomizePrismMesh_14.getCustomPrismControls();
    partsCustomizePrismMeshControls_14.setCustomizeNumLayers(true);
    partsCustomizePrismMeshControls_14.setCustomizeTotalThickness(true);
    NumPrismLayers numPrismLayers_14 = 
      surfaceCustomMeshControl_14.getCustomValues().get(CustomPrismValuesManager.class).get(NumPrismLayers.class);
    IntegerValue integerValue_14 = 
      numPrismLayers_14.getNumLayersValue();
    integerValue_14.getQuantity().setDefinition("8/(pow(${Ri},1/3))");
    PrismThickness prismThickness_14 = 
      surfaceCustomMeshControl_14.getCustomValues().get(CustomPrismValuesManager.class).get(PrismThickness.class);
    prismThickness_14.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) prismThickness_14.getAbsoluteSizeValue()).setUnits(units_2);
    ((ScalarPhysicalQuantity) prismThickness_14.getAbsoluteSizeValue()).setValue(5.0);
    PartsTargetSurfaceSize partsTargetSurfaceSize_14 = 
      surfaceCustomMeshControl_14.getCustomValues().get(PartsTargetSurfaceSize.class);
    partsTargetSurfaceSize_14.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_14.getAbsoluteSizeValue()).setDefinition("0.005*(pow(${Ri},1/3))");
  }
  else {
    SurfaceCustomMeshControl surfaceCustomMeshControl_14 = 
      autoMeshOperation_0.getCustomMeshControls().createSurfaceControl();
    surfaceCustomMeshControl_14.setPresentationName("Side Elements");
    surfaceCustomMeshControl_14.getGeometryObjects().setQuery(new Query(new NamePredicate(NameOperator.Contains, "SC"), Query.STANDARD_MODIFIERS));
    surfaceCustomMeshControl_14.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);
    PartsCustomizePrismMesh partsCustomizePrismMesh_14 = 
      surfaceCustomMeshControl_14.getCustomConditions().get(PartsCustomizePrismMesh.class);
    partsCustomizePrismMesh_14.getCustomPrismOptions().setSelected(PartsCustomPrismsOption.Type.CUSTOMIZE);
    PartsCustomizePrismMeshControls partsCustomizePrismMeshControls_14 = 
      partsCustomizePrismMesh_14.getCustomPrismControls();
    partsCustomizePrismMeshControls_14.setCustomizeNumLayers(true);
    partsCustomizePrismMeshControls_14.setCustomizeTotalThickness(true);
    NumPrismLayers numPrismLayers_14 = 
      surfaceCustomMeshControl_14.getCustomValues().get(CustomPrismValuesManager.class).get(NumPrismLayers.class);
    IntegerValue integerValue_14 = 
      numPrismLayers_14.getNumLayersValue();
    integerValue_14.getQuantity().setDefinition("8/(pow(${Ri},1/3))");
    PrismThickness prismThickness_14 = 
      surfaceCustomMeshControl_14.getCustomValues().get(CustomPrismValuesManager.class).get(PrismThickness.class);
    prismThickness_14.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) prismThickness_14.getAbsoluteSizeValue()).setUnits(units_2);
    ((ScalarPhysicalQuantity) prismThickness_14.getAbsoluteSizeValue()).setValue(5.0);
    PartsTargetSurfaceSize partsTargetSurfaceSize_14 = 
      surfaceCustomMeshControl_14.getCustomValues().get(PartsTargetSurfaceSize.class);
    partsTargetSurfaceSize_14.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_14.getAbsoluteSizeValue()).setDefinition("0.005*(pow(${Ri},1/3))");
  }

  // Prism Disable //

      SurfaceCustomMeshControl surfaceCustomMeshControl_9 = 
      autoMeshOperation_0.getCustomMeshControls().createSurfaceControl();
    surfaceCustomMeshControl_9.setPresentationName("Prisms Disable");
    surfaceCustomMeshControl_9.getGeometryObjects().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "MAIN_H"), new NamePredicate(NameOperator.Contains, "COCKPIT"), new InheritedPartPredicate(InheritedPartOperator.Inherits, Arrays.<ClientServerObject>asList(cadPart_1)), new NamePredicate(NameOperator.Contains, "RW_S"))), Query.STANDARD_MODIFIERS));
    PartsCustomizePrismMesh partsCustomizePrismMesh_3 = 
      surfaceCustomMeshControl_9.getCustomConditions().get(PartsCustomizePrismMesh.class);
    partsCustomizePrismMesh_3.getCustomPrismOptions().setSelected(PartsCustomPrismsOption.Type.DISABLE);

  // Mono_SU //

      SurfaceCustomMeshControl surfaceCustomMeshControl_15 = 
      autoMeshOperation_0.getCustomMeshControls().createSurfaceControl();
    surfaceCustomMeshControl_15.setPresentationName("Mono_SU");
  surfaceCustomMeshControl_15.getGeometryObjects().setQuery(new Query(new NamePredicate(NameOperator.Contains, "Mono_SU"), Query.STANDARD_MODIFIERS));
  PartsCustomizePrismMesh partsCustomizePrismMesh_15 = 
      surfaceCustomMeshControl_15.getCustomConditions().get(PartsCustomizePrismMesh.class);
    partsCustomizePrismMesh_15.getCustomPrismOptions().setSelected(PartsCustomPrismsOption.Type.CUSTOMIZE);
  PartsCustomizePrismMeshControls partsCustomizePrismMeshControls_15 = 
    partsCustomizePrismMesh_15.getCustomPrismControls();
    partsCustomizePrismMeshControls_15.setCustomizeNumLayers(true);
  NumPrismLayers numPrismLayers_15 = 
    surfaceCustomMeshControl_15.getCustomValues().get(CustomPrismValuesManager.class).get(NumPrismLayers.class);
      IntegerValue integerValue_15 = 
    numPrismLayers_15.getNumLayersValue();
    integerValue_15.getQuantity().setDefinition("10/(pow(${Ri},1/3))");

  // RW_EP //

    SurfaceCustomMeshControl surfaceCustomMeshControl_18 = 
      autoMeshOperation_0.getCustomMeshControls().createSurfaceControl();
    surfaceCustomMeshControl_18.setPresentationName("RW_EP");
    surfaceCustomMeshControl_18.getGeometryObjects().setQuery(new Query(new NamePredicate(NameOperator.Contains, "RW_EP"), Query.STANDARD_MODIFIERS));
    surfaceCustomMeshControl_18.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);
    PartsCustomizePrismMesh partsCustomizePrismMesh_18 = 
      surfaceCustomMeshControl_18.getCustomConditions().get(PartsCustomizePrismMesh.class);
    partsCustomizePrismMesh_18.getCustomPrismOptions().setSelected(PartsCustomPrismsOption.Type.CUSTOMIZE);
    PartsCustomizePrismMeshControls partsCustomizePrismMeshControls_18 = 
      partsCustomizePrismMesh_18.getCustomPrismControls();
    partsCustomizePrismMeshControls_18.setCustomizeNumLayers(true);
    partsCustomizePrismMeshControls_18.setCustomizeTotalThickness(true);
    NumPrismLayers numPrismLayers_18 = 
      surfaceCustomMeshControl_18.getCustomValues().get(CustomPrismValuesManager.class).get(NumPrismLayers.class);
    IntegerValue integerValue_18 = 
      numPrismLayers_18.getNumLayersValue();
    integerValue_18.getQuantity().setDefinition("6*(pow(${Ri},1/3))");
    PrismThickness prismThickness_18 = 
      surfaceCustomMeshControl_18.getCustomValues().get(CustomPrismValuesManager.class).get(PrismThickness.class);
    prismThickness_18.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) prismThickness_18.getAbsoluteSizeValue()).setUnits(units_2);
    ((ScalarPhysicalQuantity) prismThickness_18.getAbsoluteSizeValue()).setValue(3.0);
    PartsTargetSurfaceSize partsTargetSurfaceSize_18 = 
      surfaceCustomMeshControl_18.getCustomValues().get(PartsTargetSurfaceSize.class);
    partsTargetSurfaceSize_18.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_18.getAbsoluteSizeValue()).setDefinition("0.007*(pow(${Ri},1/3))");

  // Domain //

     SurfaceCustomMeshControl surfaceCustomMeshControl_16 = 
      autoMeshOperation_0.getCustomMeshControls().createSurfaceControl();
    surfaceCustomMeshControl_16.setPresentationName("Domain");
    surfaceCustomMeshControl_16.getGeometryObjects().setQuery(new Query(new NamePredicate(NameOperator.Contains, "Domain"), Query.STANDARD_MODIFIERS));
    surfaceCustomMeshControl_16.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);

    PartsCustomizePrismMesh partsCustomizePrismMesh_7 = 
      surfaceCustomMeshControl_16.getCustomConditions().get(PartsCustomizePrismMesh.class);
    partsCustomizePrismMesh_7.getCustomPrismOptions().setSelected(PartsCustomPrismsOption.Type.DISABLE);

    PartsTargetSurfaceSize partsTargetSurfaceSize_14 = 
      surfaceCustomMeshControl_16.getCustomValues().get(PartsTargetSurfaceSize.class);
    partsTargetSurfaceSize_14.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_14.getAbsoluteSizeValue()).setDefinition("0.35*(pow(${Ri},1/3))");

  // DB_0_Car //
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    VolumeCustomMeshControl volumeCustomMeshControl_0 = 
      autoMeshOperation_0.getCustomMeshControls().createVolumeControl();
    volumeCustomMeshControl_0.setPresentationName("DB_0_Car");
    volumeCustomMeshControl_0.getGeometryObjects().setQuery(new Query(new NamePredicate(NameOperator.Contains, "DB_0_Car"), Query.STANDARD_MODIFIERS));

    VolumeControlResurfacerSizeOption volumeControlResurfacerSizeOption_0 = 
      volumeCustomMeshControl_0.getCustomConditions().get(VolumeControlResurfacerSizeOption.class);
    volumeControlResurfacerSizeOption_0.setVolumeControlBaseSizeOption(true);

    VolumeControlDualMesherSizeOption volumeControlDualMesherSizeOption_0 = 
      volumeCustomMeshControl_0.getCustomConditions().get(VolumeControlDualMesherSizeOption.class);
    volumeControlDualMesherSizeOption_0.setVolumeControlBaseSizeOption(true);

    VolumeControlSize volumeControlSize_0 = 
      volumeCustomMeshControl_0.getCustomValues().get(VolumeControlSize.class);
    volumeControlSize_0.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) volumeControlSize_0.getAbsoluteSizeValue()).setDefinition("0.016*(pow(${Ri},1/3))");
  }
  else {
    VolumeCustomMeshControl volumeCustomMeshControl_0 = 
      autoMeshOperation_0.getCustomMeshControls().createVolumeControl();
    volumeCustomMeshControl_0.setPresentationName("DB_0_Car");
    volumeCustomMeshControl_0.getGeometryObjects().setQuery(new Query(new NamePredicate(NameOperator.Contains, "DB_0_Car"), Query.STANDARD_MODIFIERS));

    VolumeControlResurfacerSizeOption volumeControlResurfacerSizeOption_0 = 
      volumeCustomMeshControl_0.getCustomConditions().get(VolumeControlResurfacerSizeOption.class);
    volumeControlResurfacerSizeOption_0.setVolumeControlBaseSizeOption(true);

    VolumeControlDualMesherSizeOption volumeControlDualMesherSizeOption_0 = 
      volumeCustomMeshControl_0.getCustomConditions().get(VolumeControlDualMesherSizeOption.class);
    volumeControlDualMesherSizeOption_0.setVolumeControlBaseSizeOption(true);

    VolumeControlSize volumeControlSize_0 = 
      volumeCustomMeshControl_0.getCustomValues().get(VolumeControlSize.class);
    volumeControlSize_0.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) volumeControlSize_0.getAbsoluteSizeValue()).setDefinition("0.016*(pow(${Ri},1/3))");
  }

  // DB_1_Close //
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    VolumeCustomMeshControl volumeCustomMeshControl_1 = 
      autoMeshOperation_0.getCustomMeshControls().createVolumeControl();
    VolumeCustomMeshControl volumeCustomMeshControl_0 = 
      ((VolumeCustomMeshControl) autoMeshOperation_0.getCustomMeshControls().getObject("DB_0_Car"));
    volumeCustomMeshControl_1.copyProperties(volumeCustomMeshControl_0);
    volumeCustomMeshControl_1.setPresentationName("DB_1_Close");
    volumeCustomMeshControl_1.getGeometryObjects().setQuery(new Query(new NamePredicate(NameOperator.Contains, "DB_1_Close"), Query.STANDARD_MODIFIERS));

    VolumeControlSize volumeControlSize_1 = 
      volumeCustomMeshControl_1.getCustomValues().get(VolumeControlSize.class);
    ((ScalarPhysicalQuantity) volumeControlSize_1.getAbsoluteSizeValue()).setDefinition("0.06*(pow(${Ri},1/3))");
  }
  else {
    VolumeCustomMeshControl volumeCustomMeshControl_1 = 
      autoMeshOperation_0.getCustomMeshControls().createVolumeControl();
    VolumeCustomMeshControl volumeCustomMeshControl_0 = 
      ((VolumeCustomMeshControl) autoMeshOperation_0.getCustomMeshControls().getObject("DB_0_Car"));
    volumeCustomMeshControl_1.copyProperties(volumeCustomMeshControl_0);
    volumeCustomMeshControl_1.setPresentationName("DB_1_Close");
    volumeCustomMeshControl_1.getGeometryObjects().setQuery(new Query(new NamePredicate(NameOperator.Contains, "DB_1_Close"), Query.STANDARD_MODIFIERS));

    VolumeControlSize volumeControlSize_1 = 
      volumeCustomMeshControl_1.getCustomValues().get(VolumeControlSize.class);
    ((ScalarPhysicalQuantity) volumeControlSize_1.getAbsoluteSizeValue()).setDefinition("0.06*(pow(${Ri},1/3))");
  }

  // DB_2_Wake //
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    VolumeCustomMeshControl volumeCustomMeshControl_2 = 
      autoMeshOperation_0.getCustomMeshControls().createVolumeControl();
    VolumeCustomMeshControl volumeCustomMeshControl_1 = 
      ((VolumeCustomMeshControl) autoMeshOperation_0.getCustomMeshControls().getObject("DB_1_Close"));
    volumeCustomMeshControl_2.copyProperties(volumeCustomMeshControl_1);
    volumeCustomMeshControl_2.setPresentationName("DB_2_Wake");
    volumeCustomMeshControl_2.getGeometryObjects().setQuery(new Query(new NamePredicate(NameOperator.Contains, "DB_2"), Query.STANDARD_MODIFIERS));

    VolumeControlSize volumeControlSize_2 = 
      volumeCustomMeshControl_2.getCustomValues().get(VolumeControlSize.class);
    ((ScalarPhysicalQuantity) volumeControlSize_2.getAbsoluteSizeValue()).setDefinition("0.15*(pow(${Ri},1/3))");
  }
  else {
    VolumeCustomMeshControl volumeCustomMeshControl_2 = 
      autoMeshOperation_0.getCustomMeshControls().createVolumeControl();
    VolumeCustomMeshControl volumeCustomMeshControl_1 = 
      ((VolumeCustomMeshControl) autoMeshOperation_0.getCustomMeshControls().getObject("DB_1_Close"));
    volumeCustomMeshControl_2.copyProperties(volumeCustomMeshControl_1);
    volumeCustomMeshControl_2.setPresentationName("DB_2_Wake");
    volumeCustomMeshControl_2.getGeometryObjects().setQuery(new Query(new NamePredicate(NameOperator.Contains, "DB_2"), Query.STANDARD_MODIFIERS));

    VolumeControlSize volumeControlSize_2 = 
      volumeCustomMeshControl_2.getCustomValues().get(VolumeControlSize.class);
    ((ScalarPhysicalQuantity) volumeControlSize_2.getAbsoluteSizeValue()).setDefinition("0.15*(pow(${Ri},1/3))");
  }

  // DB_Diff //
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    VolumeCustomMeshControl volumeCustomMeshControl_3 = 
      autoMeshOperation_0.getCustomMeshControls().createVolumeControl();
    VolumeCustomMeshControl volumeCustomMeshControl_2 = 
      ((VolumeCustomMeshControl) autoMeshOperation_0.getCustomMeshControls().getObject("DB_2_Wake"));
    volumeCustomMeshControl_3.copyProperties(volumeCustomMeshControl_2);
    volumeCustomMeshControl_3.setPresentationName("DB_Diff");
  volumeCustomMeshControl_3.getGeometryObjects().setQuery(new Query(new NamePredicate(NameOperator.Contains, "DB_Diff"), Query.STANDARD_MODIFIERS));

    VolumeControlSize volumeControlSize_3 = 
      volumeCustomMeshControl_3.getCustomValues().get(VolumeControlSize.class);
    ((ScalarPhysicalQuantity) volumeControlSize_3.getAbsoluteSizeValue()).setDefinition("0.005*(pow(${Ri},1/3))");
  }
  else {
    VolumeCustomMeshControl volumeCustomMeshControl_3 = 
      autoMeshOperation_0.getCustomMeshControls().createVolumeControl();
    VolumeCustomMeshControl volumeCustomMeshControl_2 = 
      ((VolumeCustomMeshControl) autoMeshOperation_0.getCustomMeshControls().getObject("DB_2_Wake"));
    volumeCustomMeshControl_3.copyProperties(volumeCustomMeshControl_2);
    volumeCustomMeshControl_3.setPresentationName("DB_Diff");
  volumeCustomMeshControl_3.getGeometryObjects().setQuery(new Query(new NamePredicate(NameOperator.Contains, "DB_Diff"), Query.STANDARD_MODIFIERS));

    VolumeControlSize volumeControlSize_3 = 
      volumeCustomMeshControl_3.getCustomValues().get(VolumeControlSize.class);
    ((ScalarPhysicalQuantity) volumeControlSize_3.getAbsoluteSizeValue()).setDefinition("0.005*(pow(${Ri},1/3))");
  }

  // DB_FW //
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    VolumeCustomMeshControl volumeCustomMeshControl_4 = 
      autoMeshOperation_0.getCustomMeshControls().createVolumeControl();
    VolumeCustomMeshControl volumeCustomMeshControl_3 = 
      ((VolumeCustomMeshControl) autoMeshOperation_0.getCustomMeshControls().getObject("DB_Diff"));
    volumeCustomMeshControl_4.copyProperties(volumeCustomMeshControl_3);
    volumeCustomMeshControl_4.setPresentationName("DB_FW");
  volumeCustomMeshControl_4.getGeometryObjects().setQuery(new Query(new NamePredicate(NameOperator.Contains, "DB_FW"), Query.STANDARD_MODIFIERS));

    VolumeControlSize volumeControlSize_4 = 
      volumeCustomMeshControl_4.getCustomValues().get(VolumeControlSize.class);
    ((ScalarPhysicalQuantity) volumeControlSize_4.getAbsoluteSizeValue()).setDefinition("0.0046*(pow(${Ri},1/3))");
  }
  else {
    VolumeCustomMeshControl volumeCustomMeshControl_4 = 
      autoMeshOperation_0.getCustomMeshControls().createVolumeControl();
    VolumeCustomMeshControl volumeCustomMeshControl_3 = 
      ((VolumeCustomMeshControl) autoMeshOperation_0.getCustomMeshControls().getObject("DB_Diff"));
    volumeCustomMeshControl_4.copyProperties(volumeCustomMeshControl_3);
    volumeCustomMeshControl_4.setPresentationName("DB_FW");
  volumeCustomMeshControl_4.getGeometryObjects().setQuery(new Query(new NamePredicate(NameOperator.Contains, "DB_FW"), Query.STANDARD_MODIFIERS));

    VolumeControlSize volumeControlSize_4 = 
      volumeCustomMeshControl_4.getCustomValues().get(VolumeControlSize.class);
    ((ScalarPhysicalQuantity) volumeControlSize_4.getAbsoluteSizeValue()).setDefinition("0.0046*(pow(${Ri},1/3))");
  }

  simulation_0.saveState(resolvePath("..\\Simulation\\FST12CFD.sim"));

}
}

  
