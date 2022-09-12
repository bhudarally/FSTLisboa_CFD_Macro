// STAR-CCM+ macro: e_Models_Regions_Interfaces.java
// Written by STAR-CCM+ 14.04.011
package macro;

import java.util.*;

import star.common.*;
import star.segregatedflow.*;
import star.base.neo.*;
import star.base.report.*;
import star.vis.*;
import star.cadmodeler.*;
import star.material.*;
import star.turbulence.*;
import star.flow.*;
import star.kwturb.*;
import star.metrics.*;
import star.meshing.*;

public class e_Models_Regions_Interfaces extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    PhysicsContinuum physicsContinuum_1 = 
      simulation_0.getContinuumManager().createContinuum(PhysicsContinuum.class);

    physicsContinuum_1.enable(SteadyModel.class);

    physicsContinuum_1.enable(ThreeDimensionalModel.class);

    physicsContinuum_1.enable(SingleComponentGasModel.class);

    physicsContinuum_1.enable(SegregatedFlowModel.class);

    physicsContinuum_1.enable(ConstantDensityModel.class);

    physicsContinuum_1.enable(TurbulentModel.class);

    physicsContinuum_1.enable(RansTurbulenceModel.class);

    physicsContinuum_1.enable(KOmegaTurbulence.class);

    physicsContinuum_1.enable(SstKwTurbModel.class);

    physicsContinuum_1.enable(KwAllYplusWallTreatment.class);
	
	physicsContinuum_1.enable(GammaTransitionModel.class);

    physicsContinuum_1.enable(CellQualityRemediationModel.class);

    SingleComponentGasModel singleComponentGasModel_0 = 
      physicsContinuum_1.getModelManager().getModel(SingleComponentGasModel.class);

    Gas gas_0 = 
      ((Gas) singleComponentGasModel_0.getMaterial());

    ConstantMaterialPropertyMethod constantMaterialPropertyMethod_0 = 
      ((ConstantMaterialPropertyMethod) gas_0.getMaterialProperties().getMaterialProperty(ConstantDensityProperty.class).getMethod());

    constantMaterialPropertyMethod_0.getQuantity().setValue(1.225);

    ConstantMaterialPropertyMethod constantMaterialPropertyMethod_1 = 
      ((ConstantMaterialPropertyMethod) gas_0.getMaterialProperties().getMaterialProperty(DynamicViscosityProperty.class).getMethod());

    constantMaterialPropertyMethod_1.getQuantity().setValue(1.8E-5);

    MeshOperationPart meshOperationPart_0 = 
      ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("Fluid"));
	  
	/*MeshOperationPart meshOperationPart_1 = 
      ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("Radiator"));
	  
	MeshOperationPart meshOperationPart_2 = 
      ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("Fan"));*/

    simulation_0.getRegionManager().newRegionsFromParts(new NeoObjectVector(new Object[] {meshOperationPart_0}), "OneRegionPerPart", null, "OneBoundaryPerPartSurface", null, "OneFeatureCurve", null, RegionManager.CreateInterfaceMode.BOUNDARY);

    Region region_0 = 
      simulation_0.getRegionManager().getRegion("Fluid");
	  
    Boundary boundary_0 = 
      region_0.getBoundaryManager().getBoundary("Domain.Wall");

    SymmetryBoundary symmetryBoundary_0 = 
      ((SymmetryBoundary) simulation_0.get(ConditionTypeManager.class).get(SymmetryBoundary.class));

    boundary_0.setBoundaryType(symmetryBoundary_0);

    Boundary boundary_1 = 
      region_0.getBoundaryManager().getBoundary("Domain.Top");

    boundary_1.setBoundaryType(symmetryBoundary_0);

    Boundary boundary_2 = 
      region_0.getBoundaryManager().getBoundary("Domain.Symmetry");

    boundary_2.setBoundaryType(symmetryBoundary_0);

    Boundary boundary_3 = 
      region_0.getBoundaryManager().getBoundary("Domain.Outlet");

    PressureBoundary pressureBoundary_0 = 
      ((PressureBoundary) simulation_0.get(ConditionTypeManager.class).get(PressureBoundary.class));

    boundary_3.setBoundaryType(pressureBoundary_0);

    Boundary boundary_4 = 
      region_0.getBoundaryManager().getBoundary("Domain.Inlet");

    InletBoundary inletBoundary_0 = 
      ((InletBoundary) simulation_0.get(ConditionTypeManager.class).get(InletBoundary.class));

    boundary_4.setBoundaryType(inletBoundary_0);

    VelocityMagnitudeProfile velocityMagnitudeProfile_0 = 
      boundary_4.getValues().get(VelocityMagnitudeProfile.class);

	velocityMagnitudeProfile_0.getMethod(ConstantScalarProfileMethod.class).getQuantity().setDefinition("${Car-Velocity}");

    Boundary boundary_5 = 
      region_0.getBoundaryManager().getBoundary("Domain.Ground");

    boundary_5.getConditions().get(WallSlidingOption.class).setSelected(WallSlidingOption.Type.VECTOR);

    WallRelativeVelocityProfile wallRelativeVelocityProfile_0 = 
      boundary_5.getValues().get(WallRelativeVelocityProfile.class);

	wallRelativeVelocityProfile_0.getMethod(ConstantVectorProfileMethod.class).getQuantity().setDefinition("[${Car-Velocity}*-1, 0.0, 0.0]");

    /*Boundary boundary_6 = 
      region_0.getBoundaryManager().getBoundary("Car.Tyre_Rear.Faces");

    boundary_6.getConditions().get(WallSlidingOption.class).setSelected(WallSlidingOption.Type.LOCAL_ROTATION_RATE);

    ReferenceFrame referenceFrame_0 = 
      boundary_6.getValues().get(ReferenceFrame.class);
	  
	LabCoordinateSystem labCoordinateSystem_0 = 
      simulation_0.getCoordinateSystemManager().getLabCoordinateSystem();

    ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_0 = 
      ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("CS - Wheel Rear"));

    referenceFrame_0.setCoordinateSystem(exportedCartesianCoordinateSystem_0);*/

    Units units_0 = 
      ((Units) simulation_0.getUnitsManager().getObject("mm"));

    /*referenceFrame_0.getOriginVector().setComponents(0.0, 0.0, 0.0);

    referenceFrame_0.getAxisVector().setComponents(0.0, 0.0, -1.0);*/
	
	LabCoordinateSystem labCoordinateSystem_1 = 
      simulation_0.getCoordinateSystemManager().getLabCoordinateSystem();

    ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_1 = 
      ((ExportedCartesianCoordinateSystem) labCoordinateSystem_1.getLocalCoordinateSystemManager().getObject("CS - Wheel Front"));
	
	PointPart pointPart_0 = 
      simulation_0.getPartManager().createPointPart(new NeoObjectVector(new Object[] {}), new DoubleVector(new double[] {0.0, 0.0, 0.0}));

	Boundary boundary_8 = 
      region_0.getBoundaryManager().getBoundary("Car.SU_Front.Faces");
	
	pointPart_0.getInputParts().setObjects(boundary_8);
	
	pointPart_0.setPresentationName("CSFront - origin");
	
	pointPart_0.setCoordinateSystem(exportedCartesianCoordinateSystem_1);
	
	PointPart pointPart_1 = 
      simulation_0.getPartManager().createPointPart(new NeoObjectVector(new Object[] {}), new DoubleVector(new double[] {0.0, 0.0, 0.0}));

    pointPart_1.copyProperties(pointPart_0);

    pointPart_1.setPresentationName("CSFront - aux");
	
	pointPart_1.getPointCoordinate().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {300, 0.0, 0.0}));

	/*PointPart pointPart_2 = 
      simulation_0.getPartManager().createPointPart(new NeoObjectVector(new Object[] {}), new DoubleVector(new double[] {0.0, 0.0, 0.0}));

    pointPart_2.copyProperties(pointPart_0);

    pointPart_2.setPresentationName("CSRear - origin");
	
	pointPart_2.setCoordinateSystem(exportedCartesianCoordinateSystem_0);
	
	Boundary boundary_9 = 
      region_0.getBoundaryManager().getBoundary("Car.SU_Rear.Faces");

    pointPart_2.getInputParts().setObjects(boundary_9);
	
	PointPart pointPart_3 = 
      simulation_0.getPartManager().createPointPart(new NeoObjectVector(new Object[] {}), new DoubleVector(new double[] {0.0, 0.0, 0.0}));

    pointPart_3.copyProperties(pointPart_2);

    pointPart_3.setPresentationName("CSRear - aux");
	
	pointPart_3.getPointCoordinate().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {300, 0.0, 0.0}));*/

	SumReport sumReport_0 = 
      simulation_0.getReportManager().createReport(SumReport.class);

    sumReport_0.setPresentationName("CSFront - origin [X]");
	
	 PrimitiveFieldFunction primitiveFieldFunction_0 = 
      ((PrimitiveFieldFunction) simulation_0.getFieldFunctionManager().getFunction("Position"));

    VectorComponentFieldFunction vectorComponentFieldFunction_0 = 
      ((VectorComponentFieldFunction) primitiveFieldFunction_0.getComponentFunction(0));

    sumReport_0.setFieldFunction(vectorComponentFieldFunction_0);
	
	sumReport_0.getParts().setObjects(pointPart_0);
	
	SumReport sumReport_1 = 
      simulation_0.getReportManager().createReport(SumReport.class);

	sumReport_1.copyProperties(sumReport_0);
	
	sumReport_1.setPresentationName("CSFront - origin [Y]");
	
	VectorComponentFieldFunction vectorComponentFieldFunction_1 = 
      ((VectorComponentFieldFunction) primitiveFieldFunction_0.getComponentFunction(1));

    sumReport_1.setFieldFunction(vectorComponentFieldFunction_1);
	
	SumReport sumReport_2 = 
      simulation_0.getReportManager().createReport(SumReport.class);
	  
	sumReport_2.copyProperties(sumReport_1);
	
	sumReport_2.setPresentationName("CSFront - origin [Z]");
	
	VectorComponentFieldFunction vectorComponentFieldFunction_2 = 
      ((VectorComponentFieldFunction) primitiveFieldFunction_0.getComponentFunction(2));

    sumReport_2.setFieldFunction(vectorComponentFieldFunction_2);
	
	SumReport sumReport_3 = 
      simulation_0.getReportManager().createReport(SumReport.class);
	
	sumReport_3.copyProperties(sumReport_0);
	
	sumReport_3.setPresentationName("CSFront - aux [X]");
	
	sumReport_3.getParts().setObjects(pointPart_1);

	SumReport sumReport_4 = 
      simulation_0.getReportManager().createReport(SumReport.class);
	
	sumReport_4.copyProperties(sumReport_3);

    sumReport_4.setPresentationName("CSFront - aux [Y]");
	
	sumReport_4.setFieldFunction(vectorComponentFieldFunction_1);
	
	SumReport sumReport_5 = 
      simulation_0.getReportManager().createReport(SumReport.class);
	
	sumReport_5.copyProperties(sumReport_4);

    sumReport_5.setPresentationName("CSFront - aux [Z]");
	
	sumReport_5.setFieldFunction(vectorComponentFieldFunction_2);
	
	/*SumReport sumReport_6 = 
      simulation_0.getReportManager().createReport(SumReport.class);
	
	sumReport_6.copyProperties(sumReport_0);

    sumReport_6.setPresentationName("CSRear - origin [X]");
	
	sumReport_6.getParts().setObjects(pointPart_2);
	
	SumReport sumReport_7 = 
      simulation_0.getReportManager().createReport(SumReport.class);
	
	sumReport_7.copyProperties(sumReport_6);

    sumReport_7.setPresentationName("CSRear - origin [Y]");
	
	sumReport_7.setFieldFunction(vectorComponentFieldFunction_1);
	
	SumReport sumReport_8 = 
      simulation_0.getReportManager().createReport(SumReport.class);
	
	sumReport_8.copyProperties(sumReport_7);

    sumReport_8.setPresentationName("CSRear - origin [Z]");

    sumReport_8.setFieldFunction(vectorComponentFieldFunction_2);
	
	SumReport sumReport_9 = 
      simulation_0.getReportManager().createReport(SumReport.class);

	sumReport_9.copyProperties(sumReport_6);

    sumReport_9.setPresentationName("CSRear - aux [X]");
	
	sumReport_9.getParts().setObjects(pointPart_3);
	
	SumReport sumReport_10 = 
      simulation_0.getReportManager().createReport(SumReport.class);
	
	sumReport_10.copyProperties(sumReport_9);

    sumReport_10.setPresentationName("CSRear - aux [Y]");

    sumReport_10.setFieldFunction(vectorComponentFieldFunction_1);
	
	SumReport sumReport_11 = 
      simulation_0.getReportManager().createReport(SumReport.class);
	
	sumReport_11.copyProperties(sumReport_10);

    sumReport_11.setPresentationName("CSRear - aux [Z]");

    sumReport_11.setFieldFunction(vectorComponentFieldFunction_2);*/
	
	ExpressionReport expressionReport_0 = 
      simulation_0.getReportManager().createReport(ExpressionReport.class);

    expressionReport_0.setPresentationName("AngularVelocity - Front");

    expressionReport_0.setDefinition("${Car-Velocity} / (sqrt( pow((${CSFront - aux [X]}-${CSFront - origin [X]})*((-1*${CSFront - origin [Z]})/(${CSFront - aux [Z]}-${CSFront - origin [Z]})) ,2)+ pow((${CSFront - aux [Y]}-${CSFront - origin [Y]})*((-1*${CSFront - origin [Z]})/(${CSFront - aux [Z]}-${CSFront - origin [Z]})) ,2) + pow(${CSFront - origin [Z]} ,2)))");

	/*ExpressionReport expressionReport_1 = 
      simulation_0.getReportManager().createReport(ExpressionReport.class);

    expressionReport_1.setPresentationName("AngularVelocity - Rear");

    expressionReport_1.setDefinition("${Car-Velocity} / (sqrt( pow((${CSRear - aux [X]}-${CSRear - origin [X]})*((-1*${CSRear - origin [Z]})/(${CSRear - aux [Z]}-${CSRear - origin [Z]})) ,2)+ pow((${CSRear - aux [Y]}-${CSRear - origin [Y]})*((-1*${CSRear - origin [Z]})/(${CSRear - aux [Z]}-${CSRear - origin [Z]})) ,2) + pow(${CSRear - origin [Z]} ,2)))");

    WallRelativeRotationProfile wallRelativeRotationProfile_0 = 
      boundary_6.getValues().get(WallRelativeRotationProfile.class);

    wallRelativeRotationProfile_0.setMethod(FunctionScalarProfileMethod.class);*/
  
    Boundary boundary_7 = 
      region_0.getBoundaryManager().getBoundary("Car.Tyre_Front.Faces");

    boundary_7.getConditions().get(WallSlidingOption.class).setSelected(WallSlidingOption.Type.LOCAL_ROTATION_RATE);

    WallRelativeRotationProfile wallRelativeRotationProfile_1 = 
      boundary_7.getValues().get(WallRelativeRotationProfile.class);

    wallRelativeRotationProfile_1.setMethod(FunctionScalarProfileMethod.class);
  
    ReferenceFrame referenceFrame_1 = 
      boundary_7.getValues().get(ReferenceFrame.class);
	
    referenceFrame_1.setCoordinateSystem(exportedCartesianCoordinateSystem_1);

    referenceFrame_1.getOriginVector().setUnits(units_0);

    referenceFrame_1.getOriginVector().setComponents(0.0, 0.0, 0.0);

    referenceFrame_1.getAxisVector().setComponents(0.0, 0.0, -1.0);
	
	/*Region region_1 = 
      simulation_0.getRegionManager().getRegion("Radiator");

    PorousRegion porousRegion_0 = 
      ((PorousRegion) simulation_0.get(ConditionTypeManager.class).get(PorousRegion.class));

    region_1.setRegionType(porousRegion_0);
	
	FileTable fileTable_0 = 
      (FileTable) simulation_0.getTableManager().createFromFile(resolvePath("..\\Modules_Macro\\Library\\Fan12038VA.csv"));

    BoundaryInterface boundaryInterface_0 = 
      ((BoundaryInterface) simulation_0.getInterfaceManager().getInterface("Fan/Fluid 2"));

    boundaryInterface_0.setPresentationName("Duct_shroud");

    BaffleInterface baffleInterface_0 = 
      ((BaffleInterface) simulation_0.get(ConditionTypeManager.class).get(BaffleInterface.class));

    boundaryInterface_0.setInterfaceType(baffleInterface_0);

    BoundaryInterface boundaryInterface_1 = 
      ((BoundaryInterface) simulation_0.getInterfaceManager().getInterface("Fan/Fluid"));

    boundaryInterface_1.setPresentationName("Fan");

    FanInterface fanInterface_0 = 
      ((FanInterface) simulation_0.get(ConditionTypeManager.class).get(FanInterface.class));

    boundaryInterface_1.setInterfaceType(fanInterface_0);

    boundaryInterface_1.swapBoundaries();

    BoundaryInterface boundaryInterface_2 = 
      ((BoundaryInterface) simulation_0.getInterfaceManager().getInterface("Fan/Radiator"));

    boundaryInterface_2.setPresentationName("Radiator_outlet/Duct_inlet");

    BoundaryInterface boundaryInterface_3 = 
      ((BoundaryInterface) simulation_0.getInterfaceManager().getInterface("Fluid/Radiator"));

    boundaryInterface_3.setPresentationName("Radiator_shroud");
	
	boundaryInterface_3.setInterfaceType(baffleInterface_0);

    BoundaryInterface boundaryInterface_4 = 
      ((BoundaryInterface) simulation_0.getInterfaceManager().getInterface("Fluid/Radiator 2"));

    boundaryInterface_4.setPresentationName("Radiator_inlet");

    boundaryInterface_1.getConditions().get(AxialFanSwirlOption.class).setSelected(FanSwirlOption.Type.CALCULATED);
	
	FanCurveTable fanCurveTable_0 = 
      boundaryInterface_1.getValues().get(FanCurveTable.class);
	
	FanCurveTableLeaf fanCurveTableLeaf_0 = 
      fanCurveTable_0.getModelPartValue();

    fanCurveTableLeaf_0.setVolumeFlowTable(fileTable_0);

    fanCurveTableLeaf_0.setVolumeFlowTableX("AirFlow[m3/s]");

    fanCurveTableLeaf_0.setVolumeFlowTableP("StaticPressureDrop[Pa]");

    fanCurveTableLeaf_0.getFanCurvePressureRiseOption().setSelected(FanCurvePressureRiseOption.Type.STATIC_TO_STATIC);

    Units units_1 = 
      ((Units) simulation_0.getUnitsManager().getObject("rpm"));

    fanCurveTableLeaf_0.getOperatingRotationRate().setUnits(units_1);

    fanCurveTableLeaf_0.getOperatingRotationRate().setValue(6400.0);

    fanCurveTableLeaf_0.getDataRotationRate().setUnits(units_1);

    fanCurveTableLeaf_0.getDataRotationRate().setValue(6400.0);
	
    FanInterfaceSwirlProperties fanInterfaceSwirlProperties_0 = 
      boundaryInterface_1.getValues().get(FanInterfaceSwirlProperties.class);

    fanInterfaceSwirlProperties_0.getFanEfficiency().setValue(1.0);
	
	ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_3 = 
      ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("CS - Fan"));
	
	fanInterfaceSwirlProperties_0.setCoordinateSystem(exportedCartesianCoordinateSystem_3);
	
    ReferenceFrame referenceFrame_2 = 
      region_1.getValues().get(ReferenceFrame.class);

    LabCoordinateSystem labCoordinateSystem_2 = 
      simulation_0.getCoordinateSystemManager().getLabCoordinateSystem();

    ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_2 = 
      ((ExportedCartesianCoordinateSystem) labCoordinateSystem_2.getLocalCoordinateSystemManager().getObject("CS - Radiator"));

    referenceFrame_2.setCoordinateSystem(exportedCartesianCoordinateSystem_2);

    PorousInertialResistance porousInertialResistance_0 = 
      region_1.getValues().get(PorousInertialResistance.class);

    VectorProfile vectorProfile_0 = 
      porousInertialResistance_0.getMethod(PrincipalTensorProfileMethod.class).getXAxis();

    vectorProfile_0.setCoordinateSystem(exportedCartesianCoordinateSystem_2);

    VectorProfile vectorProfile_1 = 
      porousInertialResistance_0.getMethod(PrincipalTensorProfileMethod.class).getYAxis();

    vectorProfile_1.setCoordinateSystem(exportedCartesianCoordinateSystem_2);

    vectorProfile_1.getMethod(ConstantVectorProfileMethod.class).getQuantity().setComponents(0.0, -1.0, 0.0);

    ScalarProfile scalarProfile_0 = 
      porousInertialResistance_0.getMethod(PrincipalTensorProfileMethod.class).getProfile(2);

    scalarProfile_0.getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(56.842);

    PorousViscousResistance porousViscousResistance_0 = 
      region_1.getValues().get(PorousViscousResistance.class);

    VectorProfile vectorProfile_2 = 
      porousViscousResistance_0.getMethod(PrincipalTensorProfileMethod.class).getXAxis();

    vectorProfile_2.setCoordinateSystem(exportedCartesianCoordinateSystem_2);

    VectorProfile vectorProfile_3 = 
      porousViscousResistance_0.getMethod(PrincipalTensorProfileMethod.class).getYAxis();

    vectorProfile_3.setCoordinateSystem(exportedCartesianCoordinateSystem_2);

    vectorProfile_3.getMethod(ConstantVectorProfileMethod.class).getQuantity().setComponents(0.0, -1.0, 0.0);

    ScalarProfile scalarProfile_1 = 
      porousViscousResistance_0.getMethod(PrincipalTensorProfileMethod.class).getProfile(0);

    scalarProfile_1.getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(10000.0);

    ScalarProfile scalarProfile_2 = 
      porousViscousResistance_0.getMethod(PrincipalTensorProfileMethod.class).getProfile(1);

    scalarProfile_2.getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(10000.0);

    ScalarProfile scalarProfile_3 = 
      porousViscousResistance_0.getMethod(PrincipalTensorProfileMethod.class).getProfile(2);

    scalarProfile_3.getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(591.85);*/

    VelocityProfile velocityProfile_0 = 
      physicsContinuum_1.getInitialConditions().get(VelocityProfile.class);

	velocityProfile_0.getMethod(ConstantVectorProfileMethod.class).getQuantity().setDefinition("[${Initial-Velocity}*-1, 0.0, 0.0]");
	
  }
}
