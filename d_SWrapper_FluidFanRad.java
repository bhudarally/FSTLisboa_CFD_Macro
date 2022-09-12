// STAR-CCM+ macro: d_SWrapper_FluidFanRad.java
// Written by STAR-CCM+ 14.04.011
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;
import star.vis.*;
import star.cadmodeler.*;
import star.base.query.*;
import star.meshing.*;
import star.surfacewrapper.*;

public class d_SWrapper_FluidFanRad extends StarMacro {

  public void execute() {
    execute0();
    execute1();
    execute2();
}

private void execute0() {

    Simulation simulation_0 = 
    getActiveSimulation();

    Scene scene_0 = 
    simulation_0.getSceneManager().getScene("Domain");

    SceneUpdate sceneUpdate_0 = 
    scene_0.getSceneUpdate();

    scene_0.close();

    SimpleBlockPart simpleBlockPart_0 = 
    ((SimpleBlockPart) simulation_0.get(SimulationPartManager.class).getPart("Domain"));

    PartSurface partSurface_0 = 
    ((PartSurface) simpleBlockPart_0.getPartSurfaceManager().getPartSurface("Block Surface"));

    simpleBlockPart_0.getPartSurfaceManager().splitPartSurfacesByAngle(new NeoObjectVector(new Object[] {partSurface_0}), 89.0);

    scene_0.open();

    partSurface_0.setPresentationName("Outlet");

    PartSurface partSurface_1 = 
    ((PartSurface) simpleBlockPart_0.getPartSurfaceManager().getPartSurface("Block Surface 2"));

    partSurface_1.setPresentationName("Wall");

    PartSurface partSurface_2 = 
    ((PartSurface) simpleBlockPart_0.getPartSurfaceManager().getPartSurface("Block Surface 4"));

    partSurface_2.setPresentationName("Top");

    PartSurface partSurface_3 = 
    ((PartSurface) simpleBlockPart_0.getPartSurfaceManager().getPartSurface("Block Surface 3"));

    partSurface_3.setPresentationName("Ground");

    PartSurface partSurface_4 = 
    ((PartSurface) simpleBlockPart_0.getPartSurfaceManager().getPartSurface("Block Surface 5"));

    partSurface_4.setPresentationName("Symmetry");

    PartSurface partSurface_5 = 
    ((PartSurface) simpleBlockPart_0.getPartSurfaceManager().getPartSurface("Block Surface 6"));

    partSurface_5.setPresentationName("Inlet");

    CompositePart compositePart_0 = 
    ((CompositePart) simulation_0.get(SimulationPartManager.class).getPart("Car"));

    SurfaceWrapperAutoMeshOperation surfaceWrapperAutoMeshOperation_0 = 
    (SurfaceWrapperAutoMeshOperation) simulation_0.get(MeshOperationManager.class).createSurfaceWrapperAutoMeshOperation(new NeoObjectVector(new Object[] {compositePart_0, simpleBlockPart_0}), "Surface Wrapper");

    surfaceWrapperAutoMeshOperation_0.setLinkOutputPartName(false);

    MeshOperationPart meshOperationPart_0 = 
    ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("Surface Wrapper"));

    meshOperationPart_0.setPresentationName("Fluid");

    surfaceWrapperAutoMeshOperation_0.getDefaultValues().get(BaseSize.class).setValue(0.2);

    PartsTargetSurfaceSize partsTargetSurfaceSize_0 = 
    surfaceWrapperAutoMeshOperation_0.getDefaultValues().get(PartsTargetSurfaceSize.class);

    partsTargetSurfaceSize_0.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);

    partsTargetSurfaceSize_0.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.RELATIVE);

    PartsMinimumSurfaceSize partsMinimumSurfaceSize_0 = 
    surfaceWrapperAutoMeshOperation_0.getDefaultValues().get(PartsMinimumSurfaceSize.class);

    partsMinimumSurfaceSize_0.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);

    Units units_0 = 
    ((Units) simulation_0.getUnitsManager().getObject("mm"));

    ((ScalarPhysicalQuantity) partsMinimumSurfaceSize_0.getAbsoluteSizeValue()).setUnits(units_0);

    ((ScalarPhysicalQuantity) partsMinimumSurfaceSize_0.getAbsoluteSizeValue()).setValue(1.0);

    SurfaceCurvature surfaceCurvature_0 = 
    surfaceWrapperAutoMeshOperation_0.getDefaultValues().get(SurfaceCurvature.class);

    surfaceCurvature_0.setEnableCurvatureDeviationDist(true);

    surfaceCurvature_0.getCurvatureDeviationDistance().setUnits(units_0);

    surfaceCurvature_0.getCurvatureDeviationDistance().setValue(3.0);

    surfaceCurvature_0.setEnableCurvatureDeviationDist(false);

    SurfaceCustomMeshControl surfaceCustomMeshControl_0 = 
    surfaceWrapperAutoMeshOperation_0.getCustomMeshControls().createSurfaceControl();

    surfaceCustomMeshControl_0.setPresentationName("Aero Package");

    surfaceCustomMeshControl_0.getGeometryObjects().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.DoesNotContain, "Sup"), new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "Diff"), new NamePredicate(NameOperator.Contains, "RW"))))), Query.STANDARD_MODIFIERS));

    surfaceCustomMeshControl_0.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);

    PartsTargetSurfaceSize partsTargetSurfaceSize_1 = 
    surfaceCustomMeshControl_0.getCustomValues().get(PartsTargetSurfaceSize.class);

    partsTargetSurfaceSize_1.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);

    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_1.getAbsoluteSizeValue()).setValue(3.0);

    SurfaceCustomMeshControl surfaceCustomMeshControl_1 = 
    surfaceWrapperAutoMeshOperation_0.getCustomMeshControls().createSurfaceControl();

    surfaceCustomMeshControl_1.setPresentationName("Car");

    surfaceCustomMeshControl_1.getGeometryObjects().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.DoesNotContain, "Diff"), new NamePredicate(NameOperator.DoesNotContain, "FW"), new NamePredicate(NameOperator.DoesNotContain, "SU_"), new NamePredicate(NameOperator.DoesNotContain, "SC"), new NamePredicate(NameOperator.DoesNotContain, "Tyre"), new NamePredicate(NameOperator.DoesNotContain, "Fan"), new NamePredicate(NameOperator.DoesNotContain, "RW"), new NamePredicate(NameOperator.DoesNotContain, "Radiator"), new RelationshipPredicate(RelationshipOperator.DescendantOf, new IdentityPredicate(IdentityOperator.Equals, Arrays.<ClientServerObject>asList(compositePart_0))), new TypePredicate(TypeOperator.Is, GeometryPart.class))), new NamePredicate(NameOperator.Contains, "RW_S"))), Query.STANDARD_MODIFIERS));
  
    surfaceCustomMeshControl_1.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);

    PartsTargetSurfaceSize partsTargetSurfaceSize_2 = 
    surfaceCustomMeshControl_1.getCustomValues().get(PartsTargetSurfaceSize.class);

    partsTargetSurfaceSize_2.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);

    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_2.getAbsoluteSizeValue()).setUnits(units_0);

    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_2.getAbsoluteSizeValue()).setValue(7.0);

    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_1.getAbsoluteSizeValue()).setUnits(units_0);

    VolumeCustomMeshControl volumeCustomMeshControl_0 = 
    surfaceWrapperAutoMeshOperation_0.getCustomMeshControls().createVolumeControl();

    CadPart cadPart_101 = 
    ((CadPart) simulation_0.get(SimulationPartManager.class).getPart("DB_Car_New"));

    volumeCustomMeshControl_0.getGeometryObjects().setObjects(cadPart_101);

    volumeCustomMeshControl_0.setPresentationName("DB_Car");

    VolumeControlSurfaceWrapperSizeOption volumeControlSurfaceWrapperSizeOption_0 = 
    volumeCustomMeshControl_0.getCustomConditions().get(VolumeControlSurfaceWrapperSizeOption.class);

    volumeControlSurfaceWrapperSizeOption_0.setVolumeControlBaseSizeOption(true);

    VolumeControlSize volumeControlSize_0 = 
    volumeCustomMeshControl_0.getCustomValues().get(VolumeControlSize.class);

    volumeControlSize_0.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);

    ((ScalarPhysicalQuantity) volumeControlSize_0.getAbsoluteSizeValue()).setUnits(units_0);

    ((ScalarPhysicalQuantity) volumeControlSize_0.getAbsoluteSizeValue()).setValue(25.0);

    VolumeCustomMeshControl volumeCustomMeshControl_1 = 
    surfaceWrapperAutoMeshOperation_0.getCustomMeshControls().createVolumeControl();

    SimpleBlockPart simpleBlockPart_2 = 
    ((SimpleBlockPart) simulation_0.get(SimulationPartManager.class).getPart("DB_Wake"));

    volumeCustomMeshControl_1.getGeometryObjects().setObjects(simpleBlockPart_2);

    volumeCustomMeshControl_1.setPresentationName("DB_Wake");

    VolumeControlSurfaceWrapperSizeOption volumeControlSurfaceWrapperSizeOption_1 = 
    volumeCustomMeshControl_1.getCustomConditions().get(VolumeControlSurfaceWrapperSizeOption.class);

    volumeControlSurfaceWrapperSizeOption_1.setVolumeControlBaseSizeOption(true);

    VolumeControlSize volumeControlSize_1 = 
    volumeCustomMeshControl_1.getCustomValues().get(VolumeControlSize.class);

    volumeControlSize_1.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);

    ((ScalarPhysicalQuantity) volumeControlSize_1.getAbsoluteSizeValue()).setUnits(units_0);

    ((ScalarPhysicalQuantity) volumeControlSize_1.getAbsoluteSizeValue()).setValue(100.0);

    VolumeCustomMeshControl volumeCustomMeshControl_3 = 
    surfaceWrapperAutoMeshOperation_0.getCustomMeshControls().createVolumeControl();

    CadPart cadPart_102 = 
    ((CadPart) simulation_0.get(SimulationPartManager.class).getPart("DB_Close_New"));

    volumeCustomMeshControl_3.getGeometryObjects().setObjects(cadPart_102);

    volumeCustomMeshControl_3.setPresentationName("DB_Close");

    VolumeControlSurfaceWrapperSizeOption volumeControlSurfaceWrapperSizeOption_3 = 
    volumeCustomMeshControl_3.getCustomConditions().get(VolumeControlSurfaceWrapperSizeOption.class);

    volumeControlSurfaceWrapperSizeOption_3.setVolumeControlBaseSizeOption(true);

    VolumeControlSize volumeControlSize_3 = 
    volumeCustomMeshControl_3.getCustomValues().get(VolumeControlSize.class);

    volumeControlSize_3.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);

    ((ScalarPhysicalQuantity) volumeControlSize_3.getAbsoluteSizeValue()).setUnits(units_0);

    ((ScalarPhysicalQuantity) volumeControlSize_3.getAbsoluteSizeValue()).setValue(50.0);

    VolumeCustomMeshControl volumeCustomMeshControl_4 = 
    surfaceWrapperAutoMeshOperation_0.getCustomMeshControls().createVolumeControl();

    SolidModelPart solidModelPart_101 = 
    ((SolidModelPart) simulation_0.get(SimulationPartManager.class).getPart("DB_Diff"));

    volumeCustomMeshControl_4.getGeometryObjects().setObjects(solidModelPart_101);

    volumeCustomMeshControl_4.setPresentationName("DB_Diff");

    VolumeControlSurfaceWrapperSizeOption volumeControlSurfaceWrapperSizeOption_4 = 
    volumeCustomMeshControl_4.getCustomConditions().get(VolumeControlSurfaceWrapperSizeOption.class);

    volumeControlSurfaceWrapperSizeOption_4.setVolumeControlBaseSizeOption(true);

    VolumeControlSize volumeControlSize_4 = 
    volumeCustomMeshControl_4.getCustomValues().get(VolumeControlSize.class);

    volumeControlSize_4.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);

    ((ScalarPhysicalQuantity) volumeControlSize_4.getAbsoluteSizeValue()).setUnits(units_0);

    ((ScalarPhysicalQuantity) volumeControlSize_4.getAbsoluteSizeValue()).setValue(12.5);

    SurfaceCustomMeshControl surfaceCustomMeshControl_2 = 
    surfaceWrapperAutoMeshOperation_0.getCustomMeshControls().createSurfaceControl();

    surfaceCustomMeshControl_2.setPresentationName("FW");

    surfaceCustomMeshControl_2.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);

    PartsTargetSurfaceSize partsTargetSurfaceSize_3 = 
    surfaceCustomMeshControl_2.getCustomValues().get(PartsTargetSurfaceSize.class);

    partsTargetSurfaceSize_3.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);

    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_3.getAbsoluteSizeValue()).setUnits(units_0);

    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_3.getAbsoluteSizeValue()).setValue(2.0);

    surfaceCustomMeshControl_2.getGeometryObjects().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "FW"), new NamePredicate(NameOperator.Contains, "SC"))), Query.STANDARD_MODIFIERS));
  
    CurveCustomMeshControl curveCustomMeshControl_0 = 
    surfaceWrapperAutoMeshOperation_0.getCustomMeshControls().createCurveControl();

    curveCustomMeshControl_0.setPresentationName("RW");

    curveCustomMeshControl_0.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);

    PartsTargetSurfaceSize partsTargetSurfaceSize_4 = 
    curveCustomMeshControl_0.getCustomValues().get(PartsTargetSurfaceSize.class);

    partsTargetSurfaceSize_4.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);

    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_4.getAbsoluteSizeValue()).setUnits(units_0);

    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_4.getAbsoluteSizeValue()).setValue(1.0);

    curveCustomMeshControl_0.getGeometryObjects().setQuery(new Query(new NamePredicate(NameOperator.Contains, "RW"), Query.STANDARD_MODIFIERS));

    SurfaceCustomMeshControl surfaceCustomMeshControl_3 = 
    surfaceWrapperAutoMeshOperation_0.getCustomMeshControls().createSurfaceControl();

    surfaceCustomMeshControl_3.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);

    PartsTargetSurfaceSize partsTargetSurfaceSize_5 = 
    surfaceCustomMeshControl_3.getCustomValues().get(PartsTargetSurfaceSize.class);

    partsTargetSurfaceSize_5.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);

    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_5.getAbsoluteSizeValue()).setUnits(units_0);

    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_5.getAbsoluteSizeValue()).setValue(4.0);

    surfaceCustomMeshControl_3.setPresentationName("SU");

    surfaceCustomMeshControl_3.getGeometryObjects().setQuery(new Query(new NamePredicate(NameOperator.Contains, "SU_"), Query.STANDARD_MODIFIERS));

    CurveCustomMeshControl curveCustomMeshControl_1 = 
    surfaceWrapperAutoMeshOperation_0.getCustomMeshControls().createCurveControl();

    curveCustomMeshControl_1.setPresentationName("Underbody");

    curveCustomMeshControl_1.getGeometryObjects().setQuery(new Query(new NamePredicate(NameOperator.Contains, "Diff"), Query.STANDARD_MODIFIERS));
  
    curveCustomMeshControl_1.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);

    PartsTargetSurfaceSize partsTargetSurfaceSize_6 = 
    curveCustomMeshControl_1.getCustomValues().get(PartsTargetSurfaceSize.class);

    partsTargetSurfaceSize_6.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);

    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_6.getAbsoluteSizeValue()).setUnits(units_0);

    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_6.getAbsoluteSizeValue()).setValue(3.0);

    SurfaceCustomMeshControl surfaceCustomMeshControl_4 = 
    surfaceWrapperAutoMeshOperation_0.getCustomMeshControls().createSurfaceControl();

    surfaceCustomMeshControl_4.setPresentationName("Tyres");

    surfaceCustomMeshControl_4.getGeometryObjects().setQuery(new Query(new NamePredicate(NameOperator.Contains, "Tyre"), Query.STANDARD_MODIFIERS));

    surfaceCustomMeshControl_4.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);

    PartsTargetSurfaceSize partsTargetSurfaceSize_7 = 
    surfaceCustomMeshControl_4.getCustomValues().get(PartsTargetSurfaceSize.class);

    partsTargetSurfaceSize_7.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);

    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_7.getAbsoluteSizeValue()).setUnits(units_0);

    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_7.getAbsoluteSizeValue()).setValue(7.0);

    CurveCustomMeshControl curveCustomMeshControl_2 = 
    surfaceWrapperAutoMeshOperation_0.getCustomMeshControls().createCurveControl();

    curveCustomMeshControl_2.setPresentationName("Tyres");

    curveCustomMeshControl_2.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);

    PartsTargetSurfaceSize partsTargetSurfaceSize_8 = 
    curveCustomMeshControl_2.getCustomValues().get(PartsTargetSurfaceSize.class);

    partsTargetSurfaceSize_8.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);

    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_8.getAbsoluteSizeValue()).setUnits(units_0);

    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_8.getAbsoluteSizeValue()).setValue(7.0);

    curveCustomMeshControl_2.getGeometryObjects().setQuery(new Query(new NamePredicate(NameOperator.Contains, "Tyre"), Query.STANDARD_MODIFIERS));

    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_8.getAbsoluteSizeValue()).setValue(3.0);

    PartsOneGroupContactPreventionSet partsOneGroupContactPreventionSet_0 = 
    surfaceWrapperAutoMeshOperation_0.getContactPreventionSet().createPartsOneGroupContactPreventionSet();

    partsOneGroupContactPreventionSet_0.setPresentationName("Driver - HR");

    partsOneGroupContactPreventionSet_0.getPartSurfaceGroup().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "Driver"), new NamePredicate(NameOperator.Contains, "HR"))), new TypePredicate(TypeOperator.Is, GeometryPart.class))), Query.STANDARD_MODIFIERS));

    partsOneGroupContactPreventionSet_0.getFloor().setUnits(units_0);

    partsOneGroupContactPreventionSet_0.getFloor().setValue(2.0);

    PartsOneGroupContactPreventionSet partsOneGroupContactPreventionSet_1 = 
    surfaceWrapperAutoMeshOperation_0.getContactPreventionSet().createPartsOneGroupContactPreventionSet();

    partsOneGroupContactPreventionSet_1.setPresentationName("Ground - Tyres");

    partsOneGroupContactPreventionSet_1.getFloor().setValue(0.5);

    partsOneGroupContactPreventionSet_1.getPartSurfaceGroup().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "Tyre"), new NamePredicate(NameOperator.Contains, "Ground"))), Query.STANDARD_MODIFIERS));

    PartsOneGroupContactPreventionSet partsOneGroupContactPreventionSet_2 = 
    surfaceWrapperAutoMeshOperation_0.getContactPreventionSet().createPartsOneGroupContactPreventionSet();

    partsOneGroupContactPreventionSet_2.setPresentationName("Mono - Wings");

    partsOneGroupContactPreventionSet_2.getPartSurfaceGroup().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "Mono"), new NamePredicate(NameOperator.Contains, "FW"), new NamePredicate(NameOperator.Contains, "SC"))), Query.STANDARD_MODIFIERS));

    partsOneGroupContactPreventionSet_2.getFloor().setUnits(units_0);

    partsOneGroupContactPreventionSet_2.getFloor().setValue(3.0);

    partsOneGroupContactPreventionSet_1.getFloor().setUnits(units_0);

    PartsOneGroupContactPreventionSet partsOneGroupContactPreventionSet_3 = 
    surfaceWrapperAutoMeshOperation_0.getContactPreventionSet().createPartsOneGroupContactPreventionSet();

    partsOneGroupContactPreventionSet_3.setPresentationName("Mono - SU");

    partsOneGroupContactPreventionSet_3.getFloor().setUnits(units_0);

    partsOneGroupContactPreventionSet_3.getFloor().setValue(2.0);

    partsOneGroupContactPreventionSet_3.getPartSurfaceGroup().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, GeometryPart.class), new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "SU_"), new NamePredicate(NameOperator.Contains, "BH"), new NamePredicate(NameOperator.Contains, "Mono"))))), Query.STANDARD_MODIFIERS));

    PartsOneGroupContactPreventionSet partsOneGroupContactPreventionSet_4 = 
    surfaceWrapperAutoMeshOperation_0.getContactPreventionSet().createPartsOneGroupContactPreventionSet();

    partsOneGroupContactPreventionSet_4.setPresentationName("Mono - UT");

    partsOneGroupContactPreventionSet_4.getFloor().setUnits(units_0);

    partsOneGroupContactPreventionSet_4.getFloor().setValue(1.0);

    partsOneGroupContactPreventionSet_4.getPartSurfaceGroup().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "Mono"), new NamePredicate(NameOperator.Contains, "Diff"))), Query.STANDARD_MODIFIERS));

    PartsOneGroupContactPreventionSet partsOneGroupContactPreventionSet_6 = 
    surfaceWrapperAutoMeshOperation_0.getContactPreventionSet().createPartsOneGroupContactPreventionSet();

    partsOneGroupContactPreventionSet_6.setPresentationName("RW");

    partsOneGroupContactPreventionSet_6.getPartSurfaceGroup().setQuery(new Query(new NamePredicate(NameOperator.Contains, "RW"), Query.STANDARD_MODIFIERS));

    partsOneGroupContactPreventionSet_6.getFloor().setUnits(units_0);

    partsOneGroupContactPreventionSet_6.getFloor().setValue(0.1);
}

private void execute1() {

    Simulation simulation_0 = 
    getActiveSimulation();

    CompositePart compositePart_0 = 
    ((CompositePart) simulation_0.get(SimulationPartManager.class).getPart("Car"));

    SurfaceWrapperAutoMeshOperation surfaceWrapperAutoMeshOperation_0 = 
    ((SurfaceWrapperAutoMeshOperation) simulation_0.get(MeshOperationManager.class).getObject("Surface Wrapper"));

    PartsOneGroupContactPreventionSet partsOneGroupContactPreventionSet_6 = 
    surfaceWrapperAutoMeshOperation_0.getContactPreventionSet().createPartsOneGroupContactPreventionSet();

    partsOneGroupContactPreventionSet_6.setPresentationName("Symmetry - Aero Package");

    Units units_0 = 
    ((Units) simulation_0.getUnitsManager().getObject("mm"));

    partsOneGroupContactPreventionSet_6.getFloor().setUnits(units_0);

    partsOneGroupContactPreventionSet_6.getFloor().setValue(5.0);

    partsOneGroupContactPreventionSet_6.getPartSurfaceGroup().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "Sym"), new NamePredicate(NameOperator.Contains, "FW"), new NamePredicate(NameOperator.Contains, "Diff"), new NamePredicate(NameOperator.Contains, "RW"))), Query.STANDARD_MODIFIERS));

    partsOneGroupContactPreventionSet_6.getFloor().setValue(3.0);

    PartsOneGroupContactPreventionSet partsOneGroupContactPreventionSet_7 = 
    surfaceWrapperAutoMeshOperation_0.getContactPreventionSet().createPartsOneGroupContactPreventionSet();

    partsOneGroupContactPreventionSet_7.setPresentationName("Symmetry - Car");

    partsOneGroupContactPreventionSet_7.getFloor().setUnits(units_0);

    partsOneGroupContactPreventionSet_7.getFloor().setValue(3.0);

    partsOneGroupContactPreventionSet_7.getPartSurfaceGroup().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "Sym"), new NamePredicate(NameOperator.Contains, "Mono"))), Query.STANDARD_MODIFIERS));

    PartsOneGroupContactPreventionSet partsOneGroupContactPreventionSet_8 = 
    surfaceWrapperAutoMeshOperation_0.getContactPreventionSet().createPartsOneGroupContactPreventionSet();

    partsOneGroupContactPreventionSet_8.setPresentationName("Tyres - SU");

    partsOneGroupContactPreventionSet_8.getPartSurfaceGroup().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "SU_"), new NamePredicate(NameOperator.Contains, "Tyre"))), Query.STANDARD_MODIFIERS));
  
    partsOneGroupContactPreventionSet_8.getFloor().setUnits(units_0);

    partsOneGroupContactPreventionSet_8.getFloor().setValue(5.0);

    Scene scene_0 = 
    simulation_0.getSceneManager().getScene("Domain");

    CurrentView currentView_0 = 
    scene_0.getCurrentView();

    currentView_0.setInput(new DoubleVector(new double[] {-1.2453064535917202, 0.03070811853435096, 1.420799189174049}), new DoubleVector(new double[] {-1.2453064535917202, -14.881143509306906, 1.420799189174049}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), 15.79645792773036, 0, 13.0);

    SurfaceCustomMeshControl surfaceCustomMeshControl_1 = 
    ((SurfaceCustomMeshControl) surfaceWrapperAutoMeshOperation_0.getCustomMeshControls().getObject("Car"));

    PartsTargetSurfaceSize partsTargetSurfaceSize_2 = 
    surfaceCustomMeshControl_1.getCustomValues().get(PartsTargetSurfaceSize.class);

    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_2.getAbsoluteSizeValue()).setValue(5.0);

    /*SurfaceCustomMeshControl surfaceCustomMeshControl_5 = 
    surfaceWrapperAutoMeshOperation_0.getCustomMeshControls().createSurfaceControl();

    surfaceCustomMeshControl_5.setPresentationName("Radiator - Fan");

    surfaceCustomMeshControl_5.getGeometryObjects().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, GeometryPart.class), new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "Fan"), new NamePredicate(NameOperator.Contains, "Radiator"))))), Query.STANDARD_MODIFIERS));
  
    surfaceCustomMeshControl_5.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);

    PartsTargetSurfaceSize partsTargetSurfaceSize_9 = 
    surfaceCustomMeshControl_5.getCustomValues().get(PartsTargetSurfaceSize.class);

    partsTargetSurfaceSize_9.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);

    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_9.getAbsoluteSizeValue()).setUnits(units_0);

    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_9.getAbsoluteSizeValue()).setValue(2.0);*/

  SurfaceWrapperAutoMesher surfaceWrapperAutoMesher_0 = 
  ((SurfaceWrapperAutoMesher) surfaceWrapperAutoMeshOperation_0.getMeshers().getObject("Surface Wrapper"));

  surfaceWrapperAutoMesher_0.setDoWrapperMeshAlignment(true);

  surfaceWrapperAutoMeshOperation_0.setPresentationName("SW - Fluid");

  SurfaceWrapperAutoMeshOperation surfaceWrapperAutoMeshOperation_1 = 
  ((SurfaceWrapperAutoMeshOperation) simulation_0.get(MeshOperationManager.class).getObject("SW - Fluid"));

  surfaceWrapperAutoMeshOperation_1.execute();

  /*SolidModelPart solidModelPart_0 = 
  ((SolidModelPart) compositePart_0.getChildParts().getPart("Radiator"));

  SurfaceWrapperAutoMeshOperation surfaceWrapperAutoMeshOperation_2 = 
  (SurfaceWrapperAutoMeshOperation) simulation_0.get(MeshOperationManager.class).createSurfaceWrapperAutoMeshOperation(new NeoObjectVector(new Object[] {solidModelPart_0}), "Surface Wrapper");

  surfaceWrapperAutoMeshOperation_2.setLinkOutputPartName(false);

  surfaceWrapperAutoMeshOperation_2.setPresentationName("SW - Radiator");

  SurfaceWrapperAutoMesher surfaceWrapperAutoMesher_1 = 
  ((SurfaceWrapperAutoMesher) surfaceWrapperAutoMeshOperation_2.getMeshers().getObject("Surface Wrapper"));

  surfaceWrapperAutoMesher_1.setDoWrapperMeshAlignment(true);

  PartsTargetSurfaceSize partsTargetSurfaceSize_10 = 
  surfaceWrapperAutoMeshOperation_2.getDefaultValues().get(PartsTargetSurfaceSize.class);

  partsTargetSurfaceSize_10.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);

  ((ScalarPhysicalQuantity) partsTargetSurfaceSize_10.getAbsoluteSizeValue()).setUnits(units_0);

  ((ScalarPhysicalQuantity) partsTargetSurfaceSize_10.getAbsoluteSizeValue()).setValue(2.0);

  surfaceWrapperAutoMeshOperation_2.getDefaultValues().get(BaseSize.class).setUnits(units_0);

  surfaceWrapperAutoMeshOperation_2.getDefaultValues().get(BaseSize.class).setValue(5.0);

  simulation_0.get(MeshOperationManager.class).createCopyOfMeshOperation("star.surfacewrapper.SurfaceWrapperAutoMeshOperation", "Copy of SW - Radiator");

  SurfaceWrapperAutoMeshOperation surfaceWrapperAutoMeshOperation_3 = 
  ((SurfaceWrapperAutoMeshOperation) simulation_0.get(MeshOperationManager.class).getObject("Copy of SW - Radiator"));

  surfaceWrapperAutoMeshOperation_3.copyProperties(surfaceWrapperAutoMeshOperation_2);

  surfaceWrapperAutoMeshOperation_3.setPresentationName("SW - Fan");

  SolidModelPart solidModelPart_1 = 
  ((SolidModelPart) compositePart_0.getChildParts().getPart("Fan"));

  surfaceWrapperAutoMeshOperation_3.getInputGeometryObjects().setObjects(solidModelPart_1);

  MeshOperationPart meshOperationPart_1 = 
  ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("Surface Wrapper"));

  meshOperationPart_1.setPresentationName("Radiator");

  MeshOperationPart meshOperationPart_2 = 
  ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("Copy of SW - Radiator"));

  meshOperationPart_2.setPresentationName("Fan");

  surfaceWrapperAutoMeshOperation_2.execute();

  surfaceWrapperAutoMeshOperation_3.execute();*/

}

private void execute2() {

    /*double tol = 1.0E-4;

    Simulation simulation_0 = 
    getActiveSimulation();

    MeshOperationPart meshOperationPart_0 = 
    ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("Fan"));

    MeshOperationPart meshOperationPart_1 = 
    ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("Fluid"));

    MeshOperationPart meshOperationPart_2 = 
    ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("Radiator"));

    ImprintPartsOperation imprintPartsOperation_0 = 
    (ImprintPartsOperation) simulation_0.get(MeshOperationManager.class).createImprintPartsOperation(new NeoObjectVector(new Object[] {meshOperationPart_0, meshOperationPart_1, meshOperationPart_2}));

    imprintPartsOperation_0.setLinkOutputPartName(false);

    imprintPartsOperation_0.getTolerance().setValue(tol);

    imprintPartsOperation_0.execute();

    PartContact partContact_0 = 
    simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_1,meshOperationPart_0);

    PartContact partContact_1 = 
    simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_1,meshOperationPart_2);

    PartContact partContact_2 = 
    simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_0,meshOperationPart_2);

    PartSurface partSurface_10 = 
    ((PartSurface) meshOperationPart_1.getPartSurfaceManager().getPartSurface("Car.Radiator.Radiator_shroud"));

    PartSurface partSurface_11 = 
    ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("Car.Radiator.Radiator_shroud"));

    PartSurface partSurface_12 = 
    ((PartSurface) meshOperationPart_1.getPartSurfaceManager().getPartSurface("Car.Radiator.Radiator_inlet"));

    PartSurface partSurface_13 = 
    ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("Car.Radiator.Radiator_inlet"));

    PartSurface partSurface_14 = 
    ((PartSurface) meshOperationPart_0.getPartSurfaceManager().getPartSurface("Car.Fan.Fan"));

    PartSurface partSurface_15 = 
    ((PartSurface) meshOperationPart_1.getPartSurfaceManager().getPartSurface("Car.Fan.Fan"));

    PartSurface partSurface_16 = 
    ((PartSurface) meshOperationPart_0.getPartSurfaceManager().getPartSurface("Car.Fan.duct_shroud"));

    PartSurface partSurface_17 = 
    ((PartSurface) meshOperationPart_1.getPartSurfaceManager().getPartSurface("Car.Fan.duct_shroud"));

    PartSurface partSurface_18 = 
    ((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("Car.Radiator.Radiator_outlet"));

    PartSurface partSurface_19 = 
    ((PartSurface) meshOperationPart_0.getPartSurfaceManager().getPartSurface("Car.Fan.duct_inlet"));

    try {

        InPlacePartSurfaceContact inPlacePartSurfaceContact_0 = 
        ((InPlacePartSurfaceContact) partContact_0.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_10,partSurface_16));

        partContact_0.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_0); 
    }

    catch (Exception efffffff){

    }

    try {

        InPlacePartSurfaceContact inPlacePartSurfaceContact_1 = 
        ((InPlacePartSurfaceContact) partContact_1.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_17,partSurface_11));

        partContact_1.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_1);  

    }

    catch (Exception effffff){

    }

    try {

        PartContact partContact_10 = 
        simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_2,meshOperationPart_1);

        PartContact partContact_11 = 
        simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_0,meshOperationPart_1);

         PartContact partContact_12 = 
        simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_0,meshOperationPart_2);

      // Rad shroud
        InPlacePartSurfaceContact inPlacePartSurfaceContact_10 = 
        ((InPlacePartSurfaceContact) partContact_10.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_10,partSurface_11));

        MetaData metaData_10 = 
      inPlacePartSurfaceContact_10.getMetaData();

      //Rad inlet
        InPlacePartSurfaceContact inPlacePartSurfaceContact_11 = 
        ((InPlacePartSurfaceContact) partContact_10.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_12,partSurface_13));

        MetaData metaData_11 = 
      inPlacePartSurfaceContact_11.getMetaData();

      //Fan
        InPlacePartSurfaceContact inPlacePartSurfaceContact_12 = 
        ((InPlacePartSurfaceContact) partContact_11.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_14,partSurface_15));

        MetaData metaData_12 = 
      inPlacePartSurfaceContact_12.getMetaData();

      // Fan shroud
        InPlacePartSurfaceContact inPlacePartSurfaceContact_13 = 
        ((InPlacePartSurfaceContact) partContact_11.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_16,partSurface_17));

        MetaData metaData_13 = 
      inPlacePartSurfaceContact_13.getMetaData();
       

      // rad outlet/fan inlet
        InPlacePartSurfaceContact inPlacePartSurfaceContact_14 = 
        ((InPlacePartSurfaceContact) partContact_12.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_18,partSurface_19));

        MetaData metaData_14 = 
      inPlacePartSurfaceContact_14.getMetaData();

    }

    catch (Exception efffff){

        PartContact partContact_10 = 
        simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_2,meshOperationPart_1);

        PartContact partContact_11 = 
        simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_0,meshOperationPart_1);

         PartContact partContact_12 = 
        simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_0,meshOperationPart_2);

        simulation_0.get(PartContactManager.class).removeObjects(partContact_10, partContact_11, partContact_12);

        tol = 5.0E-5;

        imprintPartsOperation_0.getTolerance().setValue(tol);

        imprintPartsOperation_0.execute();

        PartContact partContact_100 = 
        simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_2,meshOperationPart_1);

        PartContact partContact_101 = 
        simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_0,meshOperationPart_1);

         PartContact partContact_102 = 
        simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_0,meshOperationPart_2);

        try {

            InPlacePartSurfaceContact inPlacePartSurfaceContact_0 = 
            ((InPlacePartSurfaceContact) partContact_100.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_10,partSurface_16));

            partContact_100.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_0); 
        }

        catch (Exception effff){

        }

        try {

            InPlacePartSurfaceContact inPlacePartSurfaceContact_1 = 
            ((InPlacePartSurfaceContact) partContact_101.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_17,partSurface_11));

            partContact_101.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_1);  

        }

        catch (Exception efff){

        }

        try {


      // Rad shroud
            InPlacePartSurfaceContact inPlacePartSurfaceContact_10 = 
            ((InPlacePartSurfaceContact) partContact_100.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_10,partSurface_11));

            MetaData metaData_10 = 
      inPlacePartSurfaceContact_10.getMetaData();

      //Rad inlet
            InPlacePartSurfaceContact inPlacePartSurfaceContact_11 = 
            ((InPlacePartSurfaceContact) partContact_100.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_12,partSurface_13));

            MetaData metaData_11 = 
      inPlacePartSurfaceContact_11.getMetaData();

      //Fan
            InPlacePartSurfaceContact inPlacePartSurfaceContact_12 = 
            ((InPlacePartSurfaceContact) partContact_101.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_14,partSurface_15));

            MetaData metaData_12 = 
      inPlacePartSurfaceContact_12.getMetaData();

      // Fan shroud
            InPlacePartSurfaceContact inPlacePartSurfaceContact_13 = 
            ((InPlacePartSurfaceContact) partContact_101.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_16,partSurface_17));

            MetaData metaData_13 = 
      inPlacePartSurfaceContact_13.getMetaData();

      // rad outlet/fan inlet
            InPlacePartSurfaceContact inPlacePartSurfaceContact_14 = 
            ((InPlacePartSurfaceContact) partContact_102.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_18,partSurface_19));

            MetaData metaData_14 = 
      inPlacePartSurfaceContact_14.getMetaData();

        }

        catch (Exception eff){

            simulation_0.get(PartContactManager.class).removeObjects(partContact_100, partContact_101, partContact_102);

            tol = 1.0E-5;

            imprintPartsOperation_0.getTolerance().setValue(tol);

            imprintPartsOperation_0.execute();

            PartContact partContact_1000 = 
        simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_2,meshOperationPart_1);

        PartContact partContact_1001 = 
        simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_0,meshOperationPart_1);

         PartContact partContact_1002 = 
        simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_0,meshOperationPart_2);

            try {

                InPlacePartSurfaceContact inPlacePartSurfaceContact_0 = 
                ((InPlacePartSurfaceContact) partContact_1000.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_10,partSurface_16));

                partContact_1000.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_0); 
            }

            catch (Exception ef){

            }

            try {

                InPlacePartSurfaceContact inPlacePartSurfaceContact_1 = 
                ((InPlacePartSurfaceContact) partContact_1001.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_17,partSurface_11));

                partContact_1001.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_1);  

            }

            catch (Exception k){

            }

        }
        
    }*/

}
}
