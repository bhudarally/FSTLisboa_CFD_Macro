// STAR-CCM+ macro: f_AutoMesh.java
// Written by STAR-CCM+ 14.04.011
package macro;

import java.util.*;

import star.common.*;
import star.base.report.*;
import star.cadmodeler.*;
import star.base.query.*;
import star.base.neo.*;
import star.flow.*;
import star.prismmesher.*;
import star.vis.*;
import star.trimmer.*;
import star.meshing.*;
import star.surfacewrapper.*;
import star.resurfacer.*;
import star.dualmesher.*;

public class f_AutoMesh extends StarMacro {

  public void execute() {
    execute0();
  }                       

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    MeshOperationPart meshOperationPart_0 = 
      ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("Fluid"));
	  
	/*MeshOperationPart meshOperationPart_1 = 
      ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("Radiator"));
	  
	MeshOperationPart meshOperationPart_2 = 
      ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("Fan"));*/

    AutoMeshOperation autoMeshOperation_0 = 
      simulation_0.get(MeshOperationManager.class).createAutoMeshOperation(new StringVector(new String[] {"star.dualmesher.DualAutoMesher", "star.resurfacer.ResurfacerAutoMesher", "star.resurfacer.AutomaticSurfaceRepairAutoMesher", "star.prismmesher.PrismAutoMesher"}), new NeoObjectVector(new Object[] {}));

    autoMeshOperation_0.setLinkOutputPartName(false);
	
	autoMeshOperation_0.setMeshPartByPart(true);

	autoMeshOperation_0.getInputGeometryObjects().setObjects(meshOperationPart_0);

  Units units_0 = 
      ((Units) simulation_0.getUnitsManager().getObject("mm"));

    autoMeshOperation_0.getDefaultValues().get(BaseSize.class).setDefinition("0.3/(pow(${Ri},1/3))");

    PartsMinimumSurfaceSize partsMinimumSurfaceSize_0 = 
      autoMeshOperation_0.getDefaultValues().get(PartsMinimumSurfaceSize.class);

  partsMinimumSurfaceSize_0.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    
  ((ScalarPhysicalQuantity) partsMinimumSurfaceSize_0.getAbsoluteSizeValue()).setDefinition("0.001/(pow(${Ri},1/3))");

    NumPrismLayers numPrismLayers_0 = 
      autoMeshOperation_0.getDefaultValues().get(NumPrismLayers.class);

    IntegerValue integerValue_0 = 
      numPrismLayers_0.getNumLayersValue();

    integerValue_0.getQuantity().setDefinition("12*(pow(${Ri},1/3))");

    PrismAutoMesher prismAutoMesher_0 = 
      ((PrismAutoMesher) autoMeshOperation_0.getMeshers().getObject("Prism Layer Mesher"));

    prismAutoMesher_0.getPrismStretchingOption().setSelected(PrismStretchingOption.Type.WALL_THICKNESS);

    PrismThickness prismThickness_0 = 
      autoMeshOperation_0.getDefaultValues().get(PrismThickness.class);

    prismThickness_0.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);

    ((ScalarPhysicalQuantity) prismThickness_0.getAbsoluteSizeValue()).setUnits(units_0);

    ((ScalarPhysicalQuantity) prismThickness_0.getAbsoluteSizeValue()).setValue(6.0);

    autoMeshOperation_0.getDefaultValues().get(PrismWallThickness.class).setDefinition("0.00002/(pow(${Ri},1/3))");

    PartsPostMeshOptimizerBase partsPostMeshOptimizerBase_0 = 
      autoMeshOperation_0.getDefaultValues().get(PartsPostMeshOptimizerBase.class);

    partsPostMeshOptimizerBase_0.setOptimizeTopology(true);

    partsPostMeshOptimizerBase_0.setOptimizeBoundary(true);

    SurfaceCustomMeshControl surfaceCustomMeshControl_5 = 
      autoMeshOperation_0.getCustomMeshControls().createSurfaceControl();

    surfaceCustomMeshControl_5.setPresentationName("Aero Package");

    surfaceCustomMeshControl_5.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);

    surfaceCustomMeshControl_5.getCustomConditions().get(PartsSurfaceCurvatureOption.class).setSelected(PartsSurfaceCurvatureOption.Type.CUSTOM_VALUES);
    SurfaceCurvature surfaceCurvature_5 = 
      surfaceCustomMeshControl_5.getCustomValues().get(SurfaceCurvature.class);
    surfaceCurvature_5.setEnableCurvatureDeviationDist(true);
    surfaceCurvature_5.setNumPointsAroundCircle(50.0);
    surfaceCurvature_5.getCurvatureDeviationDistance().setValue(1.0);
    surfaceCurvature_5.getCurvatureDeviationDistance().setUnits(units_0);

    PartsTargetSurfaceSize partsTargetSurfaceSize_190 = 
      surfaceCustomMeshControl_5.getCustomValues().get(PartsTargetSurfaceSize.class);

    partsTargetSurfaceSize_190.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);

    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_190.getAbsoluteSizeValue()).setDefinition("0.0045/(pow(${Ri},1/3))");

    CompositePart compositePart_0 = 
      ((CompositePart) simulation_0.get(SimulationPartManager.class).getPart("Car"));

    /*CadPart cadPart_1 = 
      ((CadPart) compositePart_0.getChildParts().getPart("Mono"));*/

    surfaceCustomMeshControl_5.getGeometryObjects().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.DoesNotContain, "Diff"), new NamePredicate(NameOperator.DoesNotContain, "FW"), new NamePredicate(NameOperator.DoesNotContain, "SU_"), new NamePredicate(NameOperator.DoesNotContain, "SC"), new NamePredicate(NameOperator.DoesNotContain, "Tyre"), new NamePredicate(NameOperator.DoesNotContain, "Fan"), new NamePredicate(NameOperator.DoesNotContain, "RW"), new NamePredicate(NameOperator.DoesNotContain, "Radiator"), new NamePredicate(NameOperator.DoesNotContain, "Domain"), new TypePredicate(TypeOperator.Is, PartSurface.class))), new NamePredicate(NameOperator.Contains, "RW_S"))), Query.STANDARD_MODIFIERS));
  
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

    integerValue_1.getQuantity().setDefinition("20*(pow(${Ri},1/3))");

    PrismThickness prismThickness_1 = 
      surfaceCustomMeshControl_5.getCustomValues().get(CustomPrismValuesManager.class).get(PrismThickness.class);

    prismThickness_1.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);

    ((ScalarPhysicalQuantity) prismThickness_1.getAbsoluteSizeValue()).setUnits(units_0);

    ((ScalarPhysicalQuantity) prismThickness_1.getAbsoluteSizeValue()).setValue(6.0);

    SurfaceCustomMeshControl surfaceCustomMeshControl_6 = 
      autoMeshOperation_0.getCustomMeshControls().createSurfaceControl();

    surfaceCustomMeshControl_6.setPresentationName("Car");

    surfaceCustomMeshControl_6.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);

    surfaceCustomMeshControl_6.getGeometryObjects().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "BH"), new NamePredicate(NameOperator.Contains, "Main_H"), new NamePredicate(NameOperator.Contains, "Driver"), new NamePredicate(NameOperator.Contains, "Mono"), new NamePredicate(NameOperator.Contains, "RW_S"))), Query.STANDARD_MODIFIERS));
  
    PartsTargetSurfaceSize partsTargetSurfaceSize_100 = 
      surfaceCustomMeshControl_6.getCustomValues().get(PartsTargetSurfaceSize.class);

    partsTargetSurfaceSize_100.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);

    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_100.getAbsoluteSizeValue()).setDefinition("0.0055/(pow(${Ri},1/3))");

    VolumeCustomMeshControl volumeCustomMeshControl_0 = 
      autoMeshOperation_0.getCustomMeshControls().createVolumeControl();

    SimpleBlockPart simpleBlockPart_0 = 
      ((SimpleBlockPart) simulation_0.get(SimulationPartManager.class).getPart("DB_Wake"));

    volumeCustomMeshControl_0.setPresentationName("DB_Wake");

    volumeCustomMeshControl_0.getGeometryObjects().setObjects(simpleBlockPart_0);

    VolumeControlResurfacerSizeOption volumeControlResurfacerSizeOption_0 = 
      volumeCustomMeshControl_0.getCustomConditions().get(VolumeControlResurfacerSizeOption.class);

    volumeControlResurfacerSizeOption_0.setVolumeControlBaseSizeOption(true);

    VolumeControlDualMesherSizeOption volumeControlDualMesherSizeOption_0 = 
      volumeCustomMeshControl_0.getCustomConditions().get(VolumeControlDualMesherSizeOption.class);

    volumeControlDualMesherSizeOption_0.setVolumeControlBaseSizeOption(true);

    VolumeControlSize volumeControlSize_0 = 
      volumeCustomMeshControl_0.getCustomValues().get(VolumeControlSize.class);

    volumeControlSize_0.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);

    ((ScalarPhysicalQuantity) volumeControlSize_0.getAbsoluteSizeValue()).setDefinition("0.135/(pow(${Ri},1/3))");

    ////

    /*VolumeCustomMeshControl volumeCustomMeshControl_1 = 
      autoMeshOperation_0.getCustomMeshControls().createVolumeControl();

    volumeCustomMeshControl_1.getGeometryObjects().setQuery(null);

    SolidModelPart solidModelPart_101 = 
      ((SolidModelPart) simulation_0.get(SimulationPartManager.class).getPart("DB_Diff"));

    volumeCustomMeshControl_1.setPresentationName("DB_Diff");

    volumeCustomMeshControl_1.getGeometryObjects().setObjects(solidModelPart_101);

    VolumeControlResurfacerSizeOption volumeControlResurfacerSizeOption_1 = 
      volumeCustomMeshControl_1.getCustomConditions().get(VolumeControlResurfacerSizeOption.class);

    volumeControlResurfacerSizeOption_1.setVolumeControlBaseSizeOption(true);

    VolumeControlDualMesherSizeOption volumeControlDualMesherSizeOption_1 = 
      volumeCustomMeshControl_1.getCustomConditions().get(VolumeControlDualMesherSizeOption.class);

    volumeControlDualMesherSizeOption_1.setVolumeControlBaseSizeOption(true);

    VolumeControlSize volumeControlSize_1 = 
      volumeCustomMeshControl_1.getCustomValues().get(VolumeControlSize.class);

    volumeControlSize_1.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);

    ((ScalarPhysicalQuantity) volumeControlSize_1.getAbsoluteSizeValue()).setDefinition("0.005/(pow(${Ri},1/3))");*/

    ////

    VolumeCustomMeshControl volumeCustomMeshControl_10 = 
      autoMeshOperation_0.getCustomMeshControls().createVolumeControl();

    volumeCustomMeshControl_10.getGeometryObjects().setQuery(null);

    SimpleBlockPart simpleBlockPart_40 = 
      ((SimpleBlockPart) simulation_0.get(SimulationPartManager.class).getPart("DB_FW"));

    volumeCustomMeshControl_10.setPresentationName("DB_FW");

    volumeCustomMeshControl_10.getGeometryObjects().setObjects(simpleBlockPart_40);

    VolumeControlResurfacerSizeOption volumeControlResurfacerSizeOption_10 = 
      volumeCustomMeshControl_10.getCustomConditions().get(VolumeControlResurfacerSizeOption.class);

    volumeControlResurfacerSizeOption_10.setVolumeControlBaseSizeOption(true);

    VolumeControlDualMesherSizeOption volumeControlDualMesherSizeOption_10 = 
      volumeCustomMeshControl_10.getCustomConditions().get(VolumeControlDualMesherSizeOption.class);

    volumeControlDualMesherSizeOption_10.setVolumeControlBaseSizeOption(true);

    VolumeControlSize volumeControlSize_10 = 
      volumeCustomMeshControl_10.getCustomValues().get(VolumeControlSize.class);

    volumeControlSize_10.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);

    ((ScalarPhysicalQuantity) volumeControlSize_10.getAbsoluteSizeValue()).setDefinition("0.004/(pow(${Ri},1/3))");

    ///

    VolumeCustomMeshControl volumeCustomMeshControl_2 = 
      autoMeshOperation_0.getCustomMeshControls().createVolumeControl();

    volumeCustomMeshControl_2.getGeometryObjects().setQuery(null);

    CadPart cadPart_102 = 
      ((CadPart) simulation_0.get(SimulationPartManager.class).getPart("DB_Close"));

    volumeCustomMeshControl_2.setPresentationName("DB_Close");

    volumeCustomMeshControl_2.getGeometryObjects().setObjects(cadPart_102);

    VolumeControlResurfacerSizeOption volumeControlResurfacerSizeOption_2 = 
      volumeCustomMeshControl_2.getCustomConditions().get(VolumeControlResurfacerSizeOption.class);

    volumeControlResurfacerSizeOption_2.setVolumeControlBaseSizeOption(true);

    VolumeControlDualMesherSizeOption volumeControlDualMesherSizeOption_2 = 
      volumeCustomMeshControl_2.getCustomConditions().get(VolumeControlDualMesherSizeOption.class);

    volumeControlDualMesherSizeOption_2.setVolumeControlBaseSizeOption(true);

    VolumeControlSize volumeControlSize_2 = 
      volumeCustomMeshControl_2.getCustomValues().get(VolumeControlSize.class);

    volumeControlSize_2.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);

    ((ScalarPhysicalQuantity) volumeControlSize_2.getAbsoluteSizeValue()).setDefinition("0.05/(pow(${Ri},1/3))");

    ///

    VolumeCustomMeshControl volumeCustomMeshControl_3 = 
      autoMeshOperation_0.getCustomMeshControls().createVolumeControl();

    volumeCustomMeshControl_3.getGeometryObjects().setQuery(null);

    CadPart cadPart_101 = 
      ((CadPart) simulation_0.get(SimulationPartManager.class).getPart("DB_Car"));

    volumeCustomMeshControl_3.setPresentationName("DB_Car");

    volumeCustomMeshControl_3.getGeometryObjects().setObjects(cadPart_101);

    VolumeControlResurfacerSizeOption volumeControlResurfacerSizeOption_3 = 
      volumeCustomMeshControl_3.getCustomConditions().get(VolumeControlResurfacerSizeOption.class);

    volumeControlResurfacerSizeOption_3.setVolumeControlBaseSizeOption(true);

    VolumeControlDualMesherSizeOption volumeControlDualMesherSizeOption_3 = 
      volumeCustomMeshControl_3.getCustomConditions().get(VolumeControlDualMesherSizeOption.class);

    volumeControlDualMesherSizeOption_3.setVolumeControlBaseSizeOption(true);

    VolumeControlSize volumeControlSize_3 = 
      volumeCustomMeshControl_3.getCustomValues().get(VolumeControlSize.class);

    volumeControlSize_3.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);

    ((ScalarPhysicalQuantity) volumeControlSize_3.getAbsoluteSizeValue()).setDefinition("0.012/(pow(${Ri},1/3))");

    SurfaceCustomMeshControl surfaceCustomMeshControl_7 = 
      autoMeshOperation_0.getCustomMeshControls().createSurfaceControl();

    surfaceCustomMeshControl_7.setPresentationName("Domain");

    surfaceCustomMeshControl_7.getGeometryObjects().setQuery(new Query(new NamePredicate(NameOperator.Contains, "Domain"), Query.STANDARD_MODIFIERS));

    PartsCustomizePrismMesh partsCustomizePrismMesh_1 = 
      surfaceCustomMeshControl_7.getCustomConditions().get(PartsCustomizePrismMesh.class);

    partsCustomizePrismMesh_1.getCustomPrismOptions().setSelected(PartsCustomPrismsOption.Type.DISABLE);

    surfaceCustomMeshControl_7.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);

    PartsTargetSurfaceSize partsTargetSurfaceSize_9 = 
      surfaceCustomMeshControl_7.getCustomValues().get(PartsTargetSurfaceSize.class);

    partsTargetSurfaceSize_9.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);

    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_9.getAbsoluteSizeValue()).setDefinition("0.3/(pow(${Ri},1/3))");

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

    surfaceCustomMeshControl_8.getCustomConditions().get(PartsSurfaceCurvatureOption.class).setSelected(PartsSurfaceCurvatureOption.Type.CUSTOM_VALUES);
    SurfaceCurvature surfaceCurvature_8 = 
      surfaceCustomMeshControl_8.getCustomValues().get(SurfaceCurvature.class);
    surfaceCurvature_8.setEnableCurvatureDeviationDist(true);
    surfaceCurvature_8.setNumPointsAroundCircle(50.0);
    surfaceCurvature_8.getCurvatureDeviationDistance().setValue(1.0);
    surfaceCurvature_8.getCurvatureDeviationDistance().setUnits(units_0);

    NumPrismLayers numPrismLayers_2 = 
      surfaceCustomMeshControl_8.getCustomValues().get(CustomPrismValuesManager.class).get(NumPrismLayers.class);

    IntegerValue integerValue_2 = 
      numPrismLayers_2.getNumLayersValue();

    integerValue_2.getQuantity().setDefinition("18*(pow(${Ri},1/3))");

    PrismThickness prismThickness_2 = 
      surfaceCustomMeshControl_8.getCustomValues().get(CustomPrismValuesManager.class).get(PrismThickness.class);

    prismThickness_2.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);

    ((ScalarPhysicalQuantity) prismThickness_2.getAbsoluteSizeValue()).setUnits(units_0);

    ((ScalarPhysicalQuantity) prismThickness_2.getAbsoluteSizeValue()).setValue(4.0);

    PartsTargetSurfaceSize partsTargetSurfaceSize_10 = 
      surfaceCustomMeshControl_8.getCustomValues().get(PartsTargetSurfaceSize.class);

    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_10.getAbsoluteSizeValue()).setDefinition("0.004/(pow(${Ri},1/3))");

    SurfaceCustomMeshControl surfaceCustomMeshControl_9 = 
      autoMeshOperation_0.getCustomMeshControls().createSurfaceControl();

    surfaceCustomMeshControl_9.setPresentationName("Prisms Disable");

    surfaceCustomMeshControl_9.getGeometryObjects().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "Main_H"), new NamePredicate(NameOperator.Contains, "Cockpit"), new NamePredicate(NameOperator.Contains, "RW_S"))), Query.STANDARD_MODIFIERS));

    PartsCustomizePrismMesh partsCustomizePrismMesh_3 = 
      surfaceCustomMeshControl_9.getCustomConditions().get(PartsCustomizePrismMesh.class);

    partsCustomizePrismMesh_3.getCustomPrismOptions().setSelected(PartsCustomPrismsOption.Type.DISABLE);

    SurfaceCustomMeshControl surfaceCustomMeshControl_10 = 
      autoMeshOperation_0.getCustomMeshControls().createSurfaceControl();

    surfaceCustomMeshControl_10.setPresentationName("SU");

    surfaceCustomMeshControl_10.getGeometryObjects().setQuery(new Query(new NamePredicate(NameOperator.Contains, "SU_"), Query.STANDARD_MODIFIERS));

    surfaceCustomMeshControl_10.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);

    PartsTargetSurfaceSize partsTargetSurfaceSize_43 = 
      surfaceCustomMeshControl_10.getCustomValues().get(PartsTargetSurfaceSize.class);

    partsTargetSurfaceSize_43.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);

    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_43.getAbsoluteSizeValue()).setDefinition("0.008/(pow(${Ri},1/3))");

    PartsCustomizePrismMesh partsCustomizePrismMesh_4 = 
      surfaceCustomMeshControl_10.getCustomConditions().get(PartsCustomizePrismMesh.class);

    partsCustomizePrismMesh_4.getCustomPrismOptions().setSelected(PartsCustomPrismsOption.Type.DISABLE);

    SurfaceCustomMeshControl surfaceCustomMeshControl_11 = 
      autoMeshOperation_0.getCustomMeshControls().createSurfaceControl();

    surfaceCustomMeshControl_11.setPresentationName("Tyres");

    surfaceCustomMeshControl_11.getGeometryObjects().setQuery(new Query(new NamePredicate(NameOperator.Contains, "Tyre"), Query.STANDARD_MODIFIERS));

    surfaceCustomMeshControl_11.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);

    PartsTargetSurfaceSize partsTargetSurfaceSize_63 = 
      surfaceCustomMeshControl_11.getCustomValues().get(PartsTargetSurfaceSize.class);

    partsTargetSurfaceSize_63.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);

    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_63.getAbsoluteSizeValue()).setDefinition("0.008/(pow(${Ri},1/3))");
    PartsCustomizePrismMesh partsCustomizePrismMesh_5 = 
      surfaceCustomMeshControl_11.getCustomConditions().get(PartsCustomizePrismMesh.class);

    partsCustomizePrismMesh_5.getCustomPrismOptions().setSelected(PartsCustomPrismsOption.Type.DISABLE);
	
	SurfaceCustomMeshControl surfaceCustomMeshControl_12 = 
      autoMeshOperation_0.getCustomMeshControls().createSurfaceControl();

    surfaceCustomMeshControl_12.setPresentationName("Radiator - Fan");

    surfaceCustomMeshControl_12.getGeometryObjects().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "Fan"), new NamePredicate(NameOperator.Contains, "Radiator"))), Query.STANDARD_MODIFIERS));
  
    surfaceCustomMeshControl_12.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);

    PartsTargetSurfaceSize partsTargetSurfaceSize_1912 = 
      surfaceCustomMeshControl_12.getCustomValues().get(PartsTargetSurfaceSize.class);

    partsTargetSurfaceSize_1912.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
	
	PartsCustomizePrismMesh partsCustomizePrismMesh_6 = 
      surfaceCustomMeshControl_12.getCustomConditions().get(PartsCustomizePrismMesh.class);

    partsCustomizePrismMesh_6.getCustomPrismOptions().setSelected(PartsCustomPrismsOption.Type.DISABLE);

  PartsTargetSurfaceSize partsTargetSurfaceSize_4 = 
      surfaceCustomMeshControl_12.getCustomValues().get(PartsTargetSurfaceSize.class);
    
  ((ScalarPhysicalQuantity) partsTargetSurfaceSize_4.getAbsoluteSizeValue()).setDefinition("0.0025/(pow(${Ri},1/3))");

  SurfaceCustomMeshControl surfaceCustomMeshControl_14 = 
      autoMeshOperation_0.getCustomMeshControls().createSurfaceControl();

    surfaceCustomMeshControl_14.setPresentationName("Mono_SU");

  surfaceCustomMeshControl_14.getGeometryObjects().setQuery(new Query(new NamePredicate(NameOperator.Contains, "Mono_SU"), Query.STANDARD_MODIFIERS));

  PartsCustomizePrismMesh partsCustomizePrismMesh_10 = 
      surfaceCustomMeshControl_14.getCustomConditions().get(PartsCustomizePrismMesh.class);

    partsCustomizePrismMesh_10.getCustomPrismOptions().setSelected(PartsCustomPrismsOption.Type.CUSTOMIZE);

  PartsCustomizePrismMeshControls partsCustomizePrismMeshControls_10 = 
    partsCustomizePrismMesh_10.getCustomPrismControls();

    partsCustomizePrismMeshControls_10.setCustomizeNumLayers(true);

  NumPrismLayers numPrismLayers_10 = 
    surfaceCustomMeshControl_14.getCustomValues().get(CustomPrismValuesManager.class).get(NumPrismLayers.class);

  IntegerValue integerValue_10 = 
    numPrismLayers_10.getNumLayersValue();

    integerValue_10.getQuantity().setDefinition("10*(pow(${Ri},1/3))");

  SurfaceCustomMeshControl surfaceCustomMeshControl_15 = 
      autoMeshOperation_0.getCustomMeshControls().createSurfaceControl();

    surfaceCustomMeshControl_15.setPresentationName("RW");

    surfaceCustomMeshControl_15.getGeometryObjects().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "RW"), new NamePredicate(NameOperator.DoesNotContain, "RW_S"))), Query.STANDARD_MODIFIERS));

    surfaceCustomMeshControl_15.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);

    PartsCustomizePrismMesh partsCustomizePrismMesh_90 = 
      surfaceCustomMeshControl_15.getCustomConditions().get(PartsCustomizePrismMesh.class);

    partsCustomizePrismMesh_90.getCustomPrismOptions().setSelected(PartsCustomPrismsOption.Type.CUSTOMIZE);

    PartsCustomizePrismMeshControls partsCustomizePrismMeshControls_90 = 
      partsCustomizePrismMesh_90.getCustomPrismControls();

    partsCustomizePrismMeshControls_90.setCustomizeNumLayers(true);

    PartsTargetSurfaceSize partsTargetSurfaceSize_25 = 
      surfaceCustomMeshControl_15.getCustomValues().get(PartsTargetSurfaceSize.class);

    partsTargetSurfaceSize_25.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);

    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_25.getAbsoluteSizeValue()).setDefinition("0.0045/(pow(${Ri},1/3))");

    NumPrismLayers numPrismLayers_25 = 
      surfaceCustomMeshControl_15.getCustomValues().get(CustomPrismValuesManager.class).get(NumPrismLayers.class);

    IntegerValue integerValue_25 = 
      numPrismLayers_25.getNumLayersValue();

    integerValue_25.getQuantity().setDefinition("12*(pow(${Ri},1/3))");

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

    ((ScalarPhysicalQuantity) prismThickness_18.getAbsoluteSizeValue()).setUnits(units_0);

    ((ScalarPhysicalQuantity) prismThickness_18.getAbsoluteSizeValue()).setValue(3.0);

    PartsTargetSurfaceSize partsTargetSurfaceSize_18 = 
      surfaceCustomMeshControl_18.getCustomValues().get(PartsTargetSurfaceSize.class);

    partsTargetSurfaceSize_18.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);

    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_18.getAbsoluteSizeValue()).setDefinition("0.0045/(pow(${Ri},1/3))");

    SurfaceCustomMeshControl surfaceCustomMeshControl_19 = 
      autoMeshOperation_0.getCustomMeshControls().createSurfaceControl();

    surfaceCustomMeshControl_19.setPresentationName("Side Elements");

    surfaceCustomMeshControl_19.getGeometryObjects().setQuery(new Query(new NamePredicate(NameOperator.Contains, "SC"), Query.STANDARD_MODIFIERS));

    surfaceCustomMeshControl_19.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);

    PartsCustomizePrismMesh partsCustomizePrismMesh_19 = 
      surfaceCustomMeshControl_19.getCustomConditions().get(PartsCustomizePrismMesh.class);

    partsCustomizePrismMesh_19.getCustomPrismOptions().setSelected(PartsCustomPrismsOption.Type.CUSTOMIZE);

    PartsCustomizePrismMeshControls partsCustomizePrismMeshControls_19 = 
      partsCustomizePrismMesh_19.getCustomPrismControls();

    partsCustomizePrismMeshControls_19.setCustomizeNumLayers(true);

    partsCustomizePrismMeshControls_19.setCustomizeTotalThickness(true);

    NumPrismLayers numPrismLayers_19 = 
      surfaceCustomMeshControl_19.getCustomValues().get(CustomPrismValuesManager.class).get(NumPrismLayers.class);

    IntegerValue integerValue_19 = 
      numPrismLayers_19.getNumLayersValue();

    integerValue_19.getQuantity().setDefinition("10*(pow(${Ri},1/3))");

    PrismThickness prismThickness_19 = 
      surfaceCustomMeshControl_19.getCustomValues().get(CustomPrismValuesManager.class).get(PrismThickness.class);

    prismThickness_19.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);

    ((ScalarPhysicalQuantity) prismThickness_19.getAbsoluteSizeValue()).setUnits(units_0);

    ((ScalarPhysicalQuantity) prismThickness_19.getAbsoluteSizeValue()).setValue(5.0);

    PartsTargetSurfaceSize partsTargetSurfaceSize_19 = 
      surfaceCustomMeshControl_19.getCustomValues().get(PartsTargetSurfaceSize.class);

    partsTargetSurfaceSize_19.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);

    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_19.getAbsoluteSizeValue()).setDefinition("0.0045/(pow(${Ri},1/3))");

	autoMeshOperation_0.getMesherParallelModeOption().setSelected(MesherParallelModeOption.Type.PARALLEL);
	
	autoMeshOperation_0.execute();
	
	SumReport sumReport_0 = 
      ((SumReport) simulation_0.getReportManager().getReport("CSFront - origin [X]"));
	  
	SumReport sumReport_1 = 
      ((SumReport) simulation_0.getReportManager().getReport("CSFront - origin [Y]"));
	  
	SumReport sumReport_2 = 
      ((SumReport) simulation_0.getReportManager().getReport("CSFront - origin [Z]"));
	  
	SumReport sumReport_3 = 
      ((SumReport) simulation_0.getReportManager().getReport("CSFront - aux [X]"));
	  
	SumReport sumReport_4 = 
      ((SumReport) simulation_0.getReportManager().getReport("CSFront - aux [Y]"));
	  
	SumReport sumReport_5 = 
      ((SumReport) simulation_0.getReportManager().getReport("CSFront - aux [Z]"));
	  
	/*SumReport sumReport_6 = 
      ((SumReport) simulation_0.getReportManager().getReport("CSRear - origin [X]"));
	
	SumReport sumReport_7 = 
      ((SumReport) simulation_0.getReportManager().getReport("CSRear - origin [Y]"));
	  
	SumReport sumReport_8 = 
      ((SumReport) simulation_0.getReportManager().getReport("CSRear - origin [Z]"));
	  
	SumReport sumReport_9 = 
      ((SumReport) simulation_0.getReportManager().getReport("CSRear - aux [X]"));
	  
	SumReport sumReport_10 = 
      ((SumReport) simulation_0.getReportManager().getReport("CSRear - aux [Y]"));
	  
	SumReport sumReport_11 = 
      ((SumReport) simulation_0.getReportManager().getReport("CSRear - aux [Z]"));*/
	  
	FvRepresentation fvRepresentation_0 = 
      ((FvRepresentation) simulation_0.getRepresentationManager().getObject("Volume Mesh"));
	  
	sumReport_0.setRepresentation(fvRepresentation_0);
	
	sumReport_1.setRepresentation(fvRepresentation_0);
	
	sumReport_2.setRepresentation(fvRepresentation_0);
	
	sumReport_3.setRepresentation(fvRepresentation_0);
	
	sumReport_4.setRepresentation(fvRepresentation_0);
	
	sumReport_5.setRepresentation(fvRepresentation_0);
	
	/*sumReport_6.setRepresentation(fvRepresentation_0);
	
	sumReport_7.setRepresentation(fvRepresentation_0);
	
	sumReport_8.setRepresentation(fvRepresentation_0);
	
	sumReport_9.setRepresentation(fvRepresentation_0);
	
	sumReport_10.setRepresentation(fvRepresentation_0);
	
	sumReport_11.setRepresentation(fvRepresentation_0);*/
	
	Region region_0 = 
      simulation_0.getRegionManager().getRegion("Fluid");
	
	Boundary boundary_0 = 
      region_0.getBoundaryManager().getBoundary("Car.Tyre_Front.Faces");
	
	WallRelativeRotationProfile wallRelativeRotationProfile_0 = 
      boundary_0.getValues().get(WallRelativeRotationProfile.class);
	
	PrimitiveFieldFunction primitiveFieldFunction_0 = 
      ((PrimitiveFieldFunction) simulation_0.getFieldFunctionManager().getFunction("AngularVelocity-FrontReport"));

    wallRelativeRotationProfile_0.getMethod(FunctionScalarProfileMethod.class).setFieldFunction(primitiveFieldFunction_0);
	
	/*Boundary boundary_1 = 
      region_0.getBoundaryManager().getBoundary("Car.Tyre_Rear.Faces");
	  
	WallRelativeRotationProfile wallRelativeRotationProfile_1 = 
      boundary_1.getValues().get(WallRelativeRotationProfile.class);
	  
	PrimitiveFieldFunction primitiveFieldFunction_1 = 
      ((PrimitiveFieldFunction) simulation_0.getFieldFunctionManager().getFunction("AngularVelocity-RearReport"));

    wallRelativeRotationProfile_1.getMethod(FunctionScalarProfileMethod.class).setFieldFunction(primitiveFieldFunction_1);*/
	  
	  
  }
}
