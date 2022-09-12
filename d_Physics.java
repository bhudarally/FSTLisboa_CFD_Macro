// Simcenter STAR-CCM+ macro: d_Physics.java
// Written by Simcenter STAR-CCM+ 15.06.007
package macro;

import java.util.*;

import star.common.*;
import star.segregatedflow.*;
import star.base.neo.*;
import star.base.query.*;
import star.base.report.*;
import star.material.*;
import star.meshing.*;
import star.motion.*;
import star.turbulence.*;
import star.vis.*;
import star.cadmodeler.*;
import star.flow.*;
import star.energy.*;
import star.kwturb.*;
import star.metrics.*;

public class d_Physics extends StarMacro {

  public void execute() {
    Models();
	AuxiliaryCalculations_WheelAirVelocities();
	BoundaryConditions();
	Interfaces();
	Reports();
	Monitors();
	Plots();
	Solver();
  }

  private void Models() {
	  
	//{ // Models

    Simulation simulation_0 = 
      getActiveSimulation();

    PhysicsContinuum physicsContinuum_0 = 
      simulation_0.getContinuumManager().createContinuum(PhysicsContinuum.class);

    physicsContinuum_0.enable(ThreeDimensionalModel.class);
    physicsContinuum_0.enable(SingleComponentGasModel.class);
    physicsContinuum_0.enable(SegregatedFlowModel.class);
    physicsContinuum_0.enable(ConstantDensityModel.class);
    physicsContinuum_0.enable(SteadyModel.class);
    physicsContinuum_0.enable(TurbulentModel.class);
    physicsContinuum_0.enable(RansTurbulenceModel.class);
    physicsContinuum_0.enable(KOmegaTurbulence.class);
    physicsContinuum_0.enable(SstKwTurbModel.class);
    physicsContinuum_0.enable(KwAllYplusWallTreatment.class);
    physicsContinuum_0.enable(CellQualityRemediationModel.class);
	
	Units units_0 = 
      ((Units) simulation_0.getUnitsManager().getObject("kg/m^3"));
	  
	Units units_1 = 
      ((Units) simulation_0.getUnitsManager().getObject("Pa-s"));

    SingleComponentGasModel singleComponentGasModel_0 = 
      physicsContinuum_0.getModelManager().getModel(SingleComponentGasModel.class);

    Gas gas_0 = 
      ((Gas) singleComponentGasModel_0.getMaterial());

    ConstantMaterialPropertyMethod constantMaterialPropertyMethod_0 = 
      ((ConstantMaterialPropertyMethod) gas_0.getMaterialProperties().getMaterialProperty(ConstantDensityProperty.class).getMethod());
    constantMaterialPropertyMethod_0.getQuantity().setValue(1.225);
    constantMaterialPropertyMethod_0.getQuantity().setUnits(units_0);

    ConstantMaterialPropertyMethod constantMaterialPropertyMethod_1 = 
      ((ConstantMaterialPropertyMethod) gas_0.getMaterialProperties().getMaterialProperty(DynamicViscosityProperty.class).getMethod());
    constantMaterialPropertyMethod_1.getQuantity().setValue(1.8E-5);
    constantMaterialPropertyMethod_1.getQuantity().setUnits(units_1);
	//}
	}
  
  private void AuxiliaryCalculations_WheelAirVelocities() {
	  
	//{ //Auxiliary Points - Derived Parts

    Simulation simulation_0 = 
      getActiveSimulation();
	  
	double Roll = simulation_0.getReportManager().getReport("Roll [deg]").getReportMonitorValue();
	double Yaw = simulation_0.getReportManager().getReport("Yaw [rad]").getReportMonitorValue();
	double CornerRadius = simulation_0.getReportManager().getReport("Corner Radius [m]").getReportMonitorValue(); 
	  
	Units units_0 = 
      ((Units) simulation_0.getUnitsManager().getObject("mm"));
    Units units_1 = 
      ((Units) simulation_0.getUnitsManager().getObject("m"));
	  
			
    PointPart pointPart_0 = 
      simulation_0.getPartManager().createPointPart(new NeoObjectVector(new Object[] {}), new DoubleVector(new double[] {0.0, 0.0, 0.0}));
    pointPart_0.setPresentationName("FR - aux");
    pointPart_0.getInputParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.Contains, "Rim"), new NamePredicate(NameOperator.Contains, "FR"))), Query.STANDARD_MODIFIERS));
    pointPart_0.getPointCoordinate().setCoordinate(units_0, units_1, units_1, new DoubleVector(new double[] {-300.0, 0.0, 0.0}));
    LabCoordinateSystem labCoordinateSystem_0 = 
      simulation_0.getCoordinateSystemManager().getLabCoordinateSystem();
    ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_0 = 
      ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("CSys - FR"));
    pointPart_0.setCoordinateSystem(exportedCartesianCoordinateSystem_0);

    PointPart pointPart_1 = 
      simulation_0.getPartManager().createPointPart(new NeoObjectVector(new Object[] {}), new DoubleVector(new double[] {0.0, 0.0, 0.0}));
    pointPart_1.copyProperties(pointPart_0);
    pointPart_1.setPresentationName("FR - origin");
    pointPart_1.getPointCoordinate().setCoordinate(units_0, units_1, units_1, new DoubleVector(new double[] {0.0, 0.0, 0.0}));
	
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    PointPart pointPart_2 = 
      simulation_0.getPartManager().createPointPart(new NeoObjectVector(new Object[] {}), new DoubleVector(new double[] {0.0, 0.0, 0.0}));
    pointPart_2.copyProperties(pointPart_0);
    pointPart_2.setPresentationName("FL - aux");
    pointPart_2.getInputParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.Contains, "Rim"), new NamePredicate(NameOperator.Contains, "FL"))), Query.STANDARD_MODIFIERS));

    ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_1 = 
      ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("CSys - FL"));
    pointPart_2.setCoordinateSystem(exportedCartesianCoordinateSystem_1);

    PointPart pointPart_3 = 
      simulation_0.getPartManager().createPointPart(new NeoObjectVector(new Object[] {}), new DoubleVector(new double[] {0.0, 0.0, 0.0}));
    pointPart_3.copyProperties(pointPart_2);
    pointPart_3.setPresentationName("FL - origin");
    pointPart_3.getPointCoordinate().setCoordinate(units_0, units_1, units_1, new DoubleVector(new double[] {0.0, 0.0, 0.0}));
	}
	
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    PointPart pointPart_4 = 
      simulation_0.getPartManager().createPointPart(new NeoObjectVector(new Object[] {}), new DoubleVector(new double[] {0.0, 0.0, 0.0}));
    pointPart_4.copyProperties(pointPart_0);
    pointPart_4.setPresentationName("RL - aux");
    pointPart_4.getInputParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.Contains, "Rim"), new NamePredicate(NameOperator.Contains, "RL"))), Query.STANDARD_MODIFIERS));
    ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_2 = 
      ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("CSys - RL"));
    pointPart_4.setCoordinateSystem(exportedCartesianCoordinateSystem_2);

    PointPart pointPart_5 = 
      simulation_0.getPartManager().createPointPart(new NeoObjectVector(new Object[] {}), new DoubleVector(new double[] {0.0, 0.0, 0.0}));
    pointPart_5.copyProperties(pointPart_4);
    pointPart_5.setPresentationName("RL - origin");
    pointPart_5.getPointCoordinate().setCoordinate(units_0, units_1, units_1, new DoubleVector(new double[] {0.0, 0.0, 0.0}));
	}
    PointPart pointPart_6 = 
      simulation_0.getPartManager().createPointPart(new NeoObjectVector(new Object[] {}), new DoubleVector(new double[] {0.0, 0.0, 0.0}));
    pointPart_6.copyProperties(pointPart_0);
    pointPart_6.setPresentationName("RR - aux");
    pointPart_6.getInputParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.Contains, "Rim"), new NamePredicate(NameOperator.Contains, "_RR"))), Query.STANDARD_MODIFIERS));
    ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_3 = 
      ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("CSys - RR"));
    pointPart_6.setCoordinateSystem(exportedCartesianCoordinateSystem_3);

    PointPart pointPart_7 = 
      simulation_0.getPartManager().createPointPart(new NeoObjectVector(new Object[] {}), new DoubleVector(new double[] {0.0, 0.0, 0.0}));
    pointPart_7.copyProperties(pointPart_6);
    pointPart_7.setPresentationName("RR - origin");
    pointPart_7.getPointCoordinate().setCoordinate(units_0, units_1, units_1, new DoubleVector(new double[] {0.0, 0.0, 0.0}));

    simulation_0.getPartManager().getGroupsManager().createGroup("[AC]");
	if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
	((ClientServerObjectGroup) simulation_0.getPartManager().getGroupsManager().getObject("[AC]")).getGroupsManager().groupObjects("[AC]", new NeoObjectVector(new Object[] {pointPart_0, pointPart_1, pointPart_6, pointPart_7}), true);
	} else {
	PointPart pointPart_2 = 
      ((PointPart) simulation_0.getPartManager().getObject("FL - aux"));
	PointPart pointPart_3 = 
      ((PointPart) simulation_0.getPartManager().getObject("FL - origin"));
	PointPart pointPart_4 = 
      ((PointPart) simulation_0.getPartManager().getObject("RL - aux"));
	PointPart pointPart_5 = 
      ((PointPart) simulation_0.getPartManager().getObject("RL - origin"));
    ((ClientServerObjectGroup) simulation_0.getPartManager().getGroupsManager().getObject("[AC]")).getGroupsManager().groupObjects("[AC]", new NeoObjectVector(new Object[] {pointPart_0, pointPart_1, pointPart_2, pointPart_3, pointPart_4, pointPart_5, pointPart_6, pointPart_7}), true);
	}
	//}
  
	//{ // Coordinates Auxiliary Points Reports
	
	PrimitiveFieldFunction primitiveFieldFunction_0 = 
      ((PrimitiveFieldFunction) simulation_0.getFieldFunctionManager().getFunction("Position"));
    VectorComponentFieldFunction vectorComponentFieldFunction_0 = 
      ((VectorComponentFieldFunction) primitiveFieldFunction_0.getComponentFunction(0));
	LatestMeshProxyRepresentation latestMeshProxyRepresentation_0 = 
      ((LatestMeshProxyRepresentation) simulation_0.getRepresentationManager().getObject("Latest Surface/Volume"));
	
	
	SumReport sumReport_0 = 
      simulation_0.getReportManager().createReport(SumReport.class);
    sumReport_0.setPresentationName("FR - aux [X]");
    sumReport_0.setFieldFunction(vectorComponentFieldFunction_0);
    sumReport_0.setRepresentation(latestMeshProxyRepresentation_0);
    sumReport_0.getParts().setObjects(pointPart_0);

    SumReport sumReport_1 = 
      simulation_0.getReportManager().createReport(SumReport.class);
    sumReport_1.copyProperties(sumReport_0);
    sumReport_1.setPresentationName("FR - aux [Y]");

    VectorComponentFieldFunction vectorComponentFieldFunction_1 = 
      ((VectorComponentFieldFunction) primitiveFieldFunction_0.getComponentFunction(1));
    sumReport_1.setFieldFunction(vectorComponentFieldFunction_1);

    SumReport sumReport_2 = 
      simulation_0.getReportManager().createReport(SumReport.class);
    sumReport_2.setPresentationName("FR - aux [Z]");
    sumReport_2.copyProperties(sumReport_1);

    VectorComponentFieldFunction vectorComponentFieldFunction_2 = 
      ((VectorComponentFieldFunction) primitiveFieldFunction_0.getComponentFunction(2));
    sumReport_2.setFieldFunction(vectorComponentFieldFunction_2);

    SumReport sumReport_3 = 
      simulation_0.getReportManager().createReport(SumReport.class);
    sumReport_3.copyProperties(sumReport_0);
    sumReport_3.setPresentationName("FR - origin [X]");
	sumReport_3.getParts().setQuery(null);
    sumReport_3.getParts().setObjects(pointPart_1);

    SumReport sumReport_4 = 
      simulation_0.getReportManager().createReport(SumReport.class);
    sumReport_4.copyProperties(sumReport_1);
    sumReport_4.setPresentationName("FR - origin [Y]");
	sumReport_4.getParts().setQuery(null);
    sumReport_4.getParts().setObjects(pointPart_1);

    SumReport sumReport_5 = 
      simulation_0.getReportManager().createReport(SumReport.class);
    sumReport_5.copyProperties(sumReport_2);
    sumReport_5.setPresentationName("FR - origin [Z]");
	sumReport_5.getParts().setQuery(null);
    sumReport_5.getParts().setObjects(pointPart_1);
	
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
	PointPart pointPart_2 = 
      ((PointPart) simulation_0.getPartManager().getObject("FL - aux"));
	PointPart pointPart_3 = 
      ((PointPart) simulation_0.getPartManager().getObject("FL - origin"));
	PointPart pointPart_4 = 
      ((PointPart) simulation_0.getPartManager().getObject("RL - aux"));
	PointPart pointPart_5 = 
      ((PointPart) simulation_0.getPartManager().getObject("RL - origin"));
	
    SumReport sumReport_6 = 
      simulation_0.getReportManager().createReport(SumReport.class);
    sumReport_6.copyProperties(sumReport_0);
    sumReport_6.setPresentationName("FL - aux [X]");
	sumReport_6.getParts().setQuery(null);
    sumReport_6.getParts().setObjects(pointPart_2);

    SumReport sumReport_7 = 
      simulation_0.getReportManager().createReport(SumReport.class);
    sumReport_7.copyProperties(sumReport_1);
    sumReport_7.setPresentationName("FL - aux [Y]");
	sumReport_7.getParts().setQuery(null);
    sumReport_7.getParts().setObjects(pointPart_2);

    SumReport sumReport_8 = 
      simulation_0.getReportManager().createReport(SumReport.class);
    sumReport_8.copyProperties(sumReport_2);
    sumReport_8.setPresentationName("FL - aux [Z]");
	sumReport_8.getParts().setQuery(null);
    sumReport_8.getParts().setObjects(pointPart_2);

    SumReport sumReport_9 = 
      simulation_0.getReportManager().createReport(SumReport.class);
    sumReport_9.copyProperties(sumReport_3);
    sumReport_9.setPresentationName("FL - origin [X]");
	sumReport_9.getParts().setQuery(null);
    sumReport_9.getParts().setObjects(pointPart_3);

    SumReport sumReport_10 = 
      simulation_0.getReportManager().createReport(SumReport.class);
    sumReport_10.copyProperties(sumReport_4);
    sumReport_10.setPresentationName("FL - origin [Y]");
	sumReport_10.getParts().setQuery(null);
    sumReport_10.getParts().setObjects(pointPart_3);

    SumReport sumReport_11 = 
      simulation_0.getReportManager().createReport(SumReport.class);
    sumReport_11.copyProperties(sumReport_5);
    sumReport_11.setPresentationName("FL - origin [Z]");
	sumReport_11.getParts().setQuery(null);
    sumReport_11.getParts().setObjects(pointPart_3);

    SumReport sumReport_12 = 
      simulation_0.getReportManager().createReport(SumReport.class);
    sumReport_12.copyProperties(sumReport_0);
    sumReport_12.setPresentationName("RL - aux [X]");
	sumReport_12.getParts().setQuery(null);
    sumReport_12.getParts().setObjects(pointPart_4);

    SumReport sumReport_13 = 
      simulation_0.getReportManager().createReport(SumReport.class);
    sumReport_13.copyProperties(sumReport_1);
    sumReport_13.setPresentationName("RL - aux [Y]");
	sumReport_13.getParts().setQuery(null);
    sumReport_13.getParts().setObjects(pointPart_4);

    SumReport sumReport_14 = 
      simulation_0.getReportManager().createReport(SumReport.class);
    sumReport_14.copyProperties(sumReport_2);
    sumReport_14.setPresentationName("RL - aux [Z]");
	sumReport_14.getParts().setQuery(null);
    sumReport_14.getParts().setObjects(pointPart_4);

    SumReport sumReport_15 = 
      simulation_0.getReportManager().createReport(SumReport.class);
    sumReport_15.copyProperties(sumReport_3);
    sumReport_15.setPresentationName("RL - origin [X]");
	sumReport_15.getParts().setQuery(null);
    sumReport_15.getParts().setObjects(pointPart_5);

    SumReport sumReport_16 = 
      simulation_0.getReportManager().createReport(SumReport.class);
    sumReport_16.copyProperties(sumReport_4);
    sumReport_16.setPresentationName("RL - origin [Y]");
	sumReport_16.getParts().setQuery(null);
    sumReport_16.getParts().setObjects(pointPart_5);

    SumReport sumReport_17 = 
      simulation_0.getReportManager().createReport(SumReport.class);
    sumReport_17.copyProperties(sumReport_5);
    sumReport_17.setPresentationName("RL - origin [Z]");
	sumReport_17.getParts().setQuery(null);
    sumReport_17.getParts().setObjects(pointPart_5);
	}
    SumReport sumReport_18 = 
      simulation_0.getReportManager().createReport(SumReport.class);
    sumReport_18.copyProperties(sumReport_0);
    sumReport_18.setPresentationName("RR - aux [X]");
	sumReport_18.getParts().setQuery(null); 
    sumReport_18.getParts().setObjects(pointPart_6);

    SumReport sumReport_19 = 
      simulation_0.getReportManager().createReport(SumReport.class);
    sumReport_19.copyProperties(sumReport_1);
    sumReport_19.setPresentationName("RR - aux [Y]");
	sumReport_19.getParts().setQuery(null);
    sumReport_19.getParts().setObjects(pointPart_6);

    SumReport sumReport_20 = 
      simulation_0.getReportManager().createReport(SumReport.class);
    sumReport_20.copyProperties(sumReport_2);
    sumReport_20.setPresentationName("RR - aux [Z]");
	sumReport_20.getParts().setQuery(null);
    sumReport_20.getParts().setObjects(pointPart_6);
    
    SumReport sumReport_21 = 
      simulation_0.getReportManager().createReport(SumReport.class);
    sumReport_21.copyProperties(sumReport_3);
    sumReport_21.setPresentationName("RR - origin [X]");
	sumReport_21.getParts().setQuery(null);
    sumReport_21.getParts().setObjects(pointPart_7);

    SumReport sumReport_22 = 
      simulation_0.getReportManager().createReport(SumReport.class);
    sumReport_22.copyProperties(sumReport_4);
    sumReport_22.setPresentationName("RR - origin [Y]");
	sumReport_22.getParts().setQuery(null);
    sumReport_22.getParts().setObjects(pointPart_7);

    SumReport sumReport_23 = 
      simulation_0.getReportManager().createReport(SumReport.class);
    sumReport_23.copyProperties(sumReport_5);
    sumReport_23.setPresentationName("RR - origin [Z]");
	sumReport_23.getParts().setQuery(null);
    sumReport_23.getParts().setObjects(pointPart_7);

    ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[AC]")).getGroupsManager().createGroup("Coordinates");
	if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
	((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[AC]")).getGroupsManager().getObject("Coordinates")).getGroupsManager().groupObjects("Coordinates", new NeoObjectVector(new Object[] {sumReport_0, sumReport_1, sumReport_2, sumReport_3, sumReport_4, sumReport_5, sumReport_18, sumReport_19, sumReport_20, sumReport_21, sumReport_22, sumReport_23}), true);
	} else {	
	SumReport sumReport_6 = 
      ((SumReport) simulation_0.getReportManager().getReport("FL - aux [X]"));
	SumReport sumReport_7 = 
      ((SumReport) simulation_0.getReportManager().getReport("FL - aux [Y]"));
	SumReport sumReport_8 = 
      ((SumReport) simulation_0.getReportManager().getReport("FL - aux [Z]"));
	SumReport sumReport_9 = 
      ((SumReport) simulation_0.getReportManager().getReport("FL - origin [X]"));
	SumReport sumReport_10 = 
      ((SumReport) simulation_0.getReportManager().getReport("FL - origin [Y]"));
	SumReport sumReport_11 = 
      ((SumReport) simulation_0.getReportManager().getReport("FL - origin [Z]"));
	SumReport sumReport_12 = 
      ((SumReport) simulation_0.getReportManager().getReport("RL - aux [X]"));
	SumReport sumReport_13 = 
      ((SumReport) simulation_0.getReportManager().getReport("RL - aux [Y]"));
	SumReport sumReport_14 = 
      ((SumReport) simulation_0.getReportManager().getReport("RL - aux [Z]"));
	SumReport sumReport_15 = 
      ((SumReport) simulation_0.getReportManager().getReport("RL - origin [X]"));
	SumReport sumReport_16 = 
      ((SumReport) simulation_0.getReportManager().getReport("RL - origin [Y]"));
	SumReport sumReport_17 = 
      ((SumReport) simulation_0.getReportManager().getReport("RL - origin [Z]"));	  

    ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[AC]")).getGroupsManager().getObject("Coordinates")).getGroupsManager().groupObjects("Coordinates", new NeoObjectVector(new Object[] {sumReport_0, sumReport_1, sumReport_2, sumReport_3, sumReport_4, sumReport_5, sumReport_6, sumReport_7, sumReport_8, sumReport_9, sumReport_10, sumReport_11, sumReport_12, sumReport_13, sumReport_14, sumReport_15, sumReport_16, sumReport_17, sumReport_18, sumReport_19, sumReport_20, sumReport_21, sumReport_22, sumReport_23}), true);
	}
	//}
	
	//{ // Air Angular Velocity | Wheel velocities components | Wheel angular velocities
	
	Units units_2 = 
      simulation_0.getUnitsManager().getPreferredUnits(new IntVector(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));

    
	if ( CornerRadius != 0 ) {
	UserFieldFunction userFieldFunction_0 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();
    userFieldFunction_0.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);
    userFieldFunction_0.setPresentationName("Air Angular Velocity [rad/s]");
    userFieldFunction_0.setFunctionName("Angular_Velocity");
    userFieldFunction_0.setDefinition("${car_velocity[ms-1]}/${corner_radius[m]}");
	
    UserFieldFunction userFieldFunction_1 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();
    userFieldFunction_1.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);
    userFieldFunction_1.setPresentationName("Vx FL");
    userFieldFunction_1.setFunctionName("VxFL");
    userFieldFunction_1.setDefinition("${car_velocity[ms-1]}*cos(${yaw[rad]}) - ${Angular_Velocity}*0.6\n");

    UserFieldFunction userFieldFunction_2 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();
    userFieldFunction_2.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);
    userFieldFunction_2.setPresentationName("Vx FR");
    userFieldFunction_2.setFunctionName("VxFR");
    userFieldFunction_2.setDefinition("${car_velocity[ms-1]}*cos(${yaw[rad]}) + ${Angular_Velocity}*0.6");

    UserFieldFunction userFieldFunction_3 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();
    userFieldFunction_3.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);
    userFieldFunction_3.setPresentationName("Vx RL");
    userFieldFunction_3.setFunctionName("VxRL");
    userFieldFunction_3.setDefinition("${car_velocity[ms-1]}*cos(${yaw[rad]}) - ${Angular_Velocity}*0.6");

    UserFieldFunction userFieldFunction_4 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();
    userFieldFunction_4.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);
    userFieldFunction_4.setPresentationName("Vx RR");
    userFieldFunction_4.setFunctionName("VxRR");
    userFieldFunction_4.setDefinition("${car_velocity[ms-1]}*cos(${yaw[rad]}) + ${Angular_Velocity}*0.6");

    UserFieldFunction userFieldFunction_5 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();
    userFieldFunction_5.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);
    userFieldFunction_5.setPresentationName("Vy FL");
    userFieldFunction_5.setFunctionName("VyFL");
    userFieldFunction_5.setDefinition("${car_velocity[ms-1]}*sin(${yaw[rad]}) + ${Angular_Velocity}*( 1- ${mass_distrib[%front]})*1.54");

    UserFieldFunction userFieldFunction_6 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();
    userFieldFunction_6.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);
    userFieldFunction_6.setPresentationName("Vy FR");
    userFieldFunction_6.setFunctionName("VyFR");
    userFieldFunction_6.setDefinition("${car_velocity[ms-1]}*sin(${yaw[rad]}) + ${Angular_Velocity}*( 1- ${mass_distrib[%front]})*1.54");

    UserFieldFunction userFieldFunction_7 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();
    userFieldFunction_7.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);
    userFieldFunction_7.setPresentationName("Vy RL");
    userFieldFunction_7.setFunctionName("VyRL");
    userFieldFunction_7.setDefinition("${car_velocity[ms-1]}*sin(${yaw[rad]}) - ${Angular_Velocity}*${mass_distrib[%front]}*1.54");

    UserFieldFunction userFieldFunction_8 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();
    userFieldFunction_8.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);
    userFieldFunction_8.setPresentationName("Vy RR");
    userFieldFunction_8.setFunctionName("VyRR");
    userFieldFunction_8.setDefinition("${car_velocity[ms-1]}*sin(${yaw[rad]}) - ${Angular_Velocity}*${mass_distrib[%front]}*1.54");
	
    simulation_0.getFieldFunctionManager().getGroupsManager().createGroup("[AC]");
    ((ClientServerObjectGroup) simulation_0.getFieldFunctionManager().getGroupsManager().getObject("[AC]")).getGroupsManager().groupObjects("[AC]", new NeoObjectVector(new Object[] {userFieldFunction_1, userFieldFunction_2, userFieldFunction_3, userFieldFunction_4, userFieldFunction_5, userFieldFunction_6, userFieldFunction_7, userFieldFunction_8}), true);
	}
	Units units_3 = 
      simulation_0.getUnitsManager().getPreferredUnits(new IntVector(new int[] {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ExpressionReport expressionReport_0 = 
      simulation_0.getReportManager().createReport(ExpressionReport.class);
    expressionReport_0.setPresentationName("w - FL");
	if ( CornerRadius == 0 ) {
    expressionReport_0.setDefinition("${car_velocity[ms-1]} / (sqrt( pow((${FL - aux [X]}-${FL - origin [X]})*((-1*${FL - origin [Z]})/(${FL - aux [Z]}-${FL - origin [Z]})) ,2)+ pow((${FL - aux [Y]}-${FL - origin [Y]})*((-1*${FL - origin [Z]})/(${FL - aux [Z]}-${FL - origin [Z]})) ,2) + pow(${FL - origin [Z]} ,2)))");
	} else { 
	expressionReport_0.setDefinition("(sqrt(pow(${VxFL},2)+ pow(${VyFL},2)))*cos( ${steer-inner[rad]} - atan2(${VyFL},${VxFL})) / (sqrt( pow((${FL - aux [X]}-${FL - origin [X]})*((-1*${FL - origin [Z]})/(${FL - aux [Z]}-${FL - origin [Z]})) ,2)+ pow((${FL - aux [Y]}-${FL - origin [Y]})*((-1*${FL - origin [Z]})/(${FL - aux [Z]}-${FL - origin [Z]})) ,2) + pow(${FL - origin [Z]} ,2)))");
	}
	}

    ExpressionReport expressionReport_1 = 
      simulation_0.getReportManager().createReport(ExpressionReport.class);
    expressionReport_1.setPresentationName("w - FR");
	if ( CornerRadius == 0 ) {
    expressionReport_1.setDefinition("${car_velocity[ms-1]} / (sqrt( pow((${FR - aux [X]}-${FR - origin [X]})*((-1*${FR - origin [Z]})/(${FR - aux [Z]}-${FR - origin [Z]})) ,2)+ pow((${FR - aux [Y]}-${FR - origin [Y]})*((-1*${FR - origin [Z]})/(${FR - aux [Z]}-${FR - origin [Z]})) ,2) + pow(${FR - origin [Z]} ,2)))");
	} else {
	expressionReport_1.setDefinition("(sqrt(pow(${VxFR},2)+ pow(${VyFR},2)))*cos( ${steer-outer[rad]} - atan2(${VyFR},${VxFR})) / (sqrt( pow((${FR - aux [X]}-${FR - origin [X]})*((-1*${FR - origin [Z]})/(${FR - aux [Z]}-${FR - origin [Z]})) ,2)+ pow((${FR - aux [Y]}-${FR - origin [Y]})*((-1*${FR - origin [Z]})/(${FR - aux [Z]}-${FR - origin [Z]})) ,2) + pow(${FR - origin [Z]} ,2)))");
	}
	
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ExpressionReport expressionReport_2 = 
      simulation_0.getReportManager().createReport(ExpressionReport.class);
    expressionReport_2.setPresentationName("w - RL");
	if ( CornerRadius == 0 ) {
    expressionReport_2.setDefinition("${car_velocity[ms-1]} / (sqrt( pow((${RL - aux [X]}-${RL - origin [X]})*((-1*${RL - origin [Z]})/(${RL - aux [Z]}-${RL - origin [Z]})) ,2)+ pow((${RL - aux [Y]}-${RL - origin [Y]})*((-1*${RL - origin [Z]})/(${RL - aux [Z]}-${RL - origin [Z]})) ,2) + pow(${RL - origin [Z]} ,2)))");
	} else { 
	expressionReport_2.setDefinition("(sqrt(pow(${VxRL},2)+ pow(${VyRL},2)))*cos(-1* atan2(${VyRL},${VxRL})) / (sqrt( pow((${RL - aux [X]}-${RL - origin [X]})*((-1*${RL - origin [Z]})/(${RL - aux [Z]}-${RL - origin [Z]})) ,2)+ pow((${RL - aux [Y]}-${RL - origin [Y]})*((-1*${RL - origin [Z]})/(${RL - aux [Z]}-${RL - origin [Z]})) ,2) + pow(${RL - origin [Z]} ,2)))");
	}
	}
	
    ExpressionReport expressionReport_3 = 
      simulation_0.getReportManager().createReport(ExpressionReport.class);
    expressionReport_3.setPresentationName("w - RR");
	if ( CornerRadius == 0 ) {
    expressionReport_3.setDefinition("${car_velocity[ms-1]} / (sqrt( pow((${RR - aux [X]}-${RR - origin [X]})*((-1*${RR - origin [Z]})/(${RR - aux [Z]}-${RR - origin [Z]})) ,2)+ pow((${RR - aux [Y]}-${RR - origin [Y]})*((-1*${RR - origin [Z]})/(${RR - aux [Z]}-${RR - origin [Z]})) ,2) + pow(${RR - origin [Z]} ,2)))");
	} else {
	expressionReport_3.setDefinition("(sqrt(pow(${VxRR},2)+ pow(${VyRR},2)))*cos(-1* atan2(${VyRR},${VxRR})) / (sqrt( pow((${RR - aux [X]}-${RR - origin [X]})*((-1*${RR - origin [Z]})/(${RR - aux [Z]}-${RR - origin [Z]})) ,2)+ pow((${RR - aux [Y]}-${RR - origin [Y]})*((-1*${RR - origin [Z]})/(${RR - aux [Z]}-${RR - origin [Z]})) ,2) + pow(${RR - origin [Z]} ,2)))");	
	}
	
    ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[AC]")).getGroupsManager().createGroup("Wheel Angular Velocity");
	
	
	if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
	((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[AC]")).getGroupsManager().getObject("Wheel Angular Velocity")).getGroupsManager().groupObjects("Wheel Angular Velocity", new NeoObjectVector(new Object[] {expressionReport_1, expressionReport_3}), true);
	} else {
	ExpressionReport expressionReport_0 = 
      ((ExpressionReport) simulation_0.getReportManager().getReport("w - FL"));
	ExpressionReport expressionReport_2 = 
      ((ExpressionReport) simulation_0.getReportManager().getReport("w - RL"));
    ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[AC]")).getGroupsManager().getObject("Wheel Angular Velocity")).getGroupsManager().groupObjects("Wheel Angular Velocity", new NeoObjectVector(new Object[] {expressionReport_0, expressionReport_1, expressionReport_2, expressionReport_3}), true);
	}
	
	//}
	}
  
  private void BoundaryConditions() {
	  
	//{ // Reference Frame

    Simulation simulation_0 = 
      getActiveSimulation();
	  
	Units units_0 = 
      simulation_0.getUnitsManager().getPreferredUnits(new IntVector(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));
    Units units_1 = 
      simulation_0.getUnitsManager().getInternalUnits(new IntVector(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0}));
	Units units_2 = 
      ((Units) simulation_0.getUnitsManager().getObject("m/s"));
	  
	double Roll = simulation_0.getReportManager().getReport("Roll [deg]").getReportMonitorValue();
	double Yaw = simulation_0.getReportManager().getReport("Yaw [rad]").getReportMonitorValue();
	double CornerRadius = simulation_0.getReportManager().getReport("Corner Radius [m]").getReportMonitorValue(); 
	
	LabCoordinateSystem labCoordinateSystem_0 = 
      simulation_0.getCoordinateSystemManager().getLabCoordinateSystem();
    CartesianCoordinateSystem cartesianCoordinateSystem_0 = 
      ((CartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("Air Orientation - Car"));

	if ( CornerRadius != 0 ) {
    UserRotatingReferenceFrame userRotatingReferenceFrame_0 = 
      simulation_0.get(ReferenceFrameManager.class).createReferenceFrame(UserRotatingReferenceFrame.class, "Rotating");
    userRotatingReferenceFrame_0.setPresentationName("Air - RF");
    userRotatingReferenceFrame_0.getRotationRate().setDefinition("${Angular_Velocity}");
    userRotatingReferenceFrame_0.getRotationRate().setUnits(units_1);
    CylindricalCoordinateSystem cylindricalCoordinateSystem_0 = 
      ((CylindricalCoordinateSystem) cartesianCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("Air Center"));
    userRotatingReferenceFrame_0.setCoordinateSystem(cylindricalCoordinateSystem_0);
	}
	
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    Region region_0 = 
      simulation_0.getRegionManager().getRegion("FanDuct_(L)");
	Region region_1 = 
      simulation_0.getRegionManager().getRegion("Radiator_(L)"); 
	}
	Region region_2 = 
      simulation_0.getRegionManager().getRegion("Fluid");
	Region region_3 = 
      simulation_0.getRegionManager().getRegion("Radiator_(R)");
	Region region_4 = 
      simulation_0.getRegionManager().getRegion("FanDuct_(R)");

	if ( CornerRadius != 0 ) {  
	Region region_0 = 
      simulation_0.getRegionManager().getRegion("FanDuct_(L)");
	Region region_1 = 
      simulation_0.getRegionManager().getRegion("Radiator_(L)"); 
	UserRotatingReferenceFrame userRotatingReferenceFrame_0 = 
      ((UserRotatingReferenceFrame) simulation_0.get(ReferenceFrameManager.class).getObject("Air - RF"));
    MotionSpecification motionSpecification_0 = 
      region_0.getValues().get(MotionSpecification.class);
    motionSpecification_0.setReferenceFrame(userRotatingReferenceFrame_0);

    MotionSpecification motionSpecification_1 = 
      region_1.getValues().get(MotionSpecification.class);
    motionSpecification_1.setReferenceFrame(userRotatingReferenceFrame_0);

    MotionSpecification motionSpecification_2 = 
      region_2.getValues().get(MotionSpecification.class);
    motionSpecification_2.setReferenceFrame(userRotatingReferenceFrame_0);

    MotionSpecification motionSpecification_3 = 
      region_3.getValues().get(MotionSpecification.class);
    motionSpecification_3.setReferenceFrame(userRotatingReferenceFrame_0);

    MotionSpecification motionSpecification_4 = 
      region_4.getValues().get(MotionSpecification.class);
    motionSpecification_4.setReferenceFrame(userRotatingReferenceFrame_0);
	}
	//}
	
	//{ // Boundary Conditions
	
		//{// Ground Boundary Condition 

	if ( CornerRadius != 0 ) {
    Boundary boundary_0 = 
      region_2.getBoundaryManager().getBoundary("Domain.Ground");
    boundary_0.getConditions().get(WallSlidingOption.class).setSelected(WallSlidingOption.Type.LOCAL_ROTATION_RATE);
    ReferenceFrame referenceFrame_0 = 
      boundary_0.getValues().get(ReferenceFrame.class);
	  
	CylindricalCoordinateSystem cylindricalCoordinateSystem_0 = 
      ((CylindricalCoordinateSystem) cartesianCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("Air Center"));
	UserRotatingReferenceFrame userRotatingReferenceFrame_0 = 
      ((UserRotatingReferenceFrame) simulation_0.get(ReferenceFrameManager.class).getObject("Air - RF"));
	  
    userRotatingReferenceFrame_0.setCoordinateSystem(cylindricalCoordinateSystem_0);
    referenceFrame_0.setCoordinateSystem(cylindricalCoordinateSystem_0);
    referenceFrame_0.getAxisVector().setComponents(0.0, 0.0, -1.0);
    referenceFrame_0.getAxisVector().setUnits(units_0);

    WallRelativeRotationProfile wallRelativeRotationProfile_0 = 
      boundary_0.getValues().get(WallRelativeRotationProfile.class);
    wallRelativeRotationProfile_0.getMethod(ConstantScalarProfileMethod.class).getQuantity().setDefinition("${Angular_Velocity}");
    wallRelativeRotationProfile_0.getMethod(ConstantScalarProfileMethod.class).getQuantity().setUnits(units_1);
	
	} else {
	Boundary boundary_0 = 
      region_2.getBoundaryManager().getBoundary("Domain.Ground");
    boundary_0.getConditions().get(WallSlidingOption.class).setSelected(WallSlidingOption.Type.VECTOR);

    WallRelativeVelocityProfile wallRelativeVelocityProfile_0 = 
      boundary_0.getValues().get(WallRelativeVelocityProfile.class);
	wallRelativeVelocityProfile_0.getMethod(ConstantVectorProfileMethod.class).getQuantity().setDefinition("[${car_velocity[ms-1]}*-1, 0.0, 0.0]");
	}
	//}

		//{ // Inlet Boundary Condition

    Boundary boundary_1 = 
      region_2.getBoundaryManager().getBoundary("Domain.Inlet");
    InletBoundary inletBoundary_0 = 
      ((InletBoundary) simulation_0.get(ConditionTypeManager.class).get(InletBoundary.class));
    boundary_1.setBoundaryType(inletBoundary_0);

    VelocityMagnitudeProfile velocityMagnitudeProfile_0 = 
      boundary_1.getValues().get(VelocityMagnitudeProfile.class);
	if ( CornerRadius != 0 ) {
    velocityMagnitudeProfile_0.getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(0.0);
    velocityMagnitudeProfile_0.getMethod(ConstantScalarProfileMethod.class).getQuantity().setUnits(units_2);
	
	} else {
	velocityMagnitudeProfile_0.getMethod(ConstantScalarProfileMethod.class).getQuantity().setDefinition("${car_velocity[ms-1]}");
	}	
	
	//}
	
		//{ // Outlet Boundary Condition

    Boundary boundary_2 = 
      region_2.getBoundaryManager().getBoundary("Domain.Outlet");
    PressureBoundary pressureBoundary_0 = 
      ((PressureBoundary) simulation_0.get(ConditionTypeManager.class).get(PressureBoundary.class));
    boundary_2.setBoundaryType(pressureBoundary_0);
	//}
	
		//{ // No slip walls Boundary Condition
	
	SymmetryBoundary symmetryBoundary_0 = 
      ((SymmetryBoundary) simulation_0.get(ConditionTypeManager.class).get(SymmetryBoundary.class));

    Boundary boundary_3 = 
      region_2.getBoundaryManager().getBoundary("Domain.Wall_Inner");
    boundary_3.setBoundaryType(symmetryBoundary_0);

    Boundary boundary_4 = 
      region_2.getBoundaryManager().getBoundary("Domain.Wall_Outer");
    boundary_4.setBoundaryType(symmetryBoundary_0);

    Boundary boundary_5 = 
      region_2.getBoundaryManager().getBoundary("Domain.Wall_Top");
    boundary_5.setBoundaryType(symmetryBoundary_0);
	//}
	
		//{ // Wheels Rotation Boundary Condition
	
			//{ // FL Rotating
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    Boundary boundary_6 = 
      region_2.getBoundaryManager().getBoundary("FST10_carreira.SU.RIM_FL.ColoredFace1");
    boundary_6.getConditions().get(WallSlidingOption.class).setSelected(WallSlidingOption.Type.LOCAL_ROTATION_RATE);

    ReferenceFrame referenceFrame_1 = 
      boundary_6.getValues().get(ReferenceFrame.class);
    ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_0 = 
      ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("CSys - FL"));
    referenceFrame_1.setCoordinateSystem(exportedCartesianCoordinateSystem_0);

    WallRelativeRotationProfile wallRelativeRotationProfile_1 = 
      boundary_6.getValues().get(WallRelativeRotationProfile.class);
    wallRelativeRotationProfile_1.getMethod(ConstantScalarProfileMethod.class).getQuantity().setDefinition("${w-FLReport}");
    wallRelativeRotationProfile_1.getMethod(ConstantScalarProfileMethod.class).getQuantity().setUnits(units_1);

    Boundary boundary_7 = 
      region_2.getBoundaryManager().getBoundary("FST10_carreira.SU.RIM_FL.FL_CENTER");
    boundary_7.copyProperties(boundary_6);

    Boundary boundary_8 = 
      region_2.getBoundaryManager().getBoundary("FST10_carreira.SU.TYRE_FL.ColoredFace1");
    boundary_8.copyProperties(boundary_6);

    Boundary boundary_9 = 
      region_2.getBoundaryManager().getBoundary("FST10_carreira.SU.TYRE_FL.ColoredFace2");
    boundary_9.copyProperties(boundary_6);
	}
	//}
	
			//{ // FR Rotating

    Boundary boundary_10 = 
      region_2.getBoundaryManager().getBoundary("FST10_carreira.SU.RIM_FR.ColoredFace1");
    boundary_10.getConditions().get(WallSlidingOption.class).setSelected(WallSlidingOption.Type.LOCAL_ROTATION_RATE);

    ReferenceFrame referenceFrame_2 = 
      boundary_10.getValues().get(ReferenceFrame.class);
    ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_1 = 
      ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("CSys - FR"));
    referenceFrame_2.setCoordinateSystem(exportedCartesianCoordinateSystem_1);
    referenceFrame_2.getAxisVector().setComponents(0.0, 0.0, -1.0);
    referenceFrame_2.getAxisVector().setUnits(units_0);

    WallRelativeRotationProfile wallRelativeRotationProfile_2 = 
      boundary_10.getValues().get(WallRelativeRotationProfile.class);
    wallRelativeRotationProfile_2.getMethod(ConstantScalarProfileMethod.class).getQuantity().setDefinition("${w-FRReport}");
    wallRelativeRotationProfile_2.getMethod(ConstantScalarProfileMethod.class).getQuantity().setUnits(units_1);

    Boundary boundary_11 = 
      region_2.getBoundaryManager().getBoundary("FST10_carreira.SU.RIM_FR.FR_CENTER");
    boundary_11.copyProperties(boundary_10);

    Boundary boundary_12 = 
      region_2.getBoundaryManager().getBoundary("FST10_carreira.SU.TYRE_FR.ColoredFace1");
    boundary_12.copyProperties(boundary_10);

    Boundary boundary_13 = 
      region_2.getBoundaryManager().getBoundary("FST10_carreira.SU.TYRE_FR.ColoredFace2");
    boundary_13.copyProperties(boundary_10);
	//}
	
			//{ // RL Rotating
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    Boundary boundary_14 = 
      region_2.getBoundaryManager().getBoundary("FST10_carreira.SU.RIM_RL.ColoredFace1");
    boundary_14.getConditions().get(WallSlidingOption.class).setSelected(WallSlidingOption.Type.LOCAL_ROTATION_RATE);

    ReferenceFrame referenceFrame_3 = 
      boundary_14.getValues().get(ReferenceFrame.class);
    ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_2 = 
      ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("CSys - RL"));
    referenceFrame_3.setCoordinateSystem(exportedCartesianCoordinateSystem_2);

    WallRelativeRotationProfile wallRelativeRotationProfile_3 = 
      boundary_14.getValues().get(WallRelativeRotationProfile.class);
    wallRelativeRotationProfile_3.getMethod(ConstantScalarProfileMethod.class).getQuantity().setDefinition("${w-RLReport}");
    wallRelativeRotationProfile_3.getMethod(ConstantScalarProfileMethod.class).getQuantity().setUnits(units_1);

    Boundary boundary_15 = 
      region_2.getBoundaryManager().getBoundary("FST10_carreira.SU.RIM_RL.RL_CENTER");
    boundary_15.copyProperties(boundary_14);

    Boundary boundary_16 = 
      region_2.getBoundaryManager().getBoundary("FST10_carreira.SU.TYRE_RL.ColoredFace1");
    boundary_16.copyProperties(boundary_14);

    Boundary boundary_17 = 
      region_2.getBoundaryManager().getBoundary("FST10_carreira.SU.TYRE_RL.ColoredFace2");
    boundary_17.copyProperties(boundary_14);
	}
	//}
	
			//{ // RR Rotating

    Boundary boundary_18 = 
      region_2.getBoundaryManager().getBoundary("FST10_carreira.SU.RIM_RR.ColoredFace1");
    boundary_18.getConditions().get(WallSlidingOption.class).setSelected(WallSlidingOption.Type.LOCAL_ROTATION_RATE);

    ReferenceFrame referenceFrame_4 = 
      boundary_18.getValues().get(ReferenceFrame.class);
    ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_3 = 
      ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("CSys - RR"));
    referenceFrame_4.setCoordinateSystem(exportedCartesianCoordinateSystem_3);
    referenceFrame_4.getAxisVector().setComponents(0.0, 0.0, -1.0);
    referenceFrame_4.getAxisVector().setUnits(units_0);

    WallRelativeRotationProfile wallRelativeRotationProfile_4 = 
      boundary_18.getValues().get(WallRelativeRotationProfile.class);
    wallRelativeRotationProfile_4.getMethod(ConstantScalarProfileMethod.class).getQuantity().setDefinition("${w-RRReport}");
    wallRelativeRotationProfile_4.getMethod(ConstantScalarProfileMethod.class).getQuantity().setUnits(units_1);

    Boundary boundary_19 = 
      region_2.getBoundaryManager().getBoundary("FST10_carreira.SU.TYRE_RR.ColoredFace1");
    boundary_19.copyProperties(boundary_18);

    Boundary boundary_20 = 
      region_2.getBoundaryManager().getBoundary("FST10_carreira.SU.TYRE_RR.ColoredFace2");
    boundary_20.copyProperties(boundary_18);
	
	Boundary boundary_21 = 
      region_2.getBoundaryManager().getBoundary("FST10_carreira.SU.RIM_RR.RR_CENTER");
    boundary_21.copyProperties(boundary_18);
	//}
		//}
	//}
	
	//{ // Radiator POROUS REGION

		//{ // Radiator Left 
	
    PorousRegion porousRegion_0 = 
      ((PorousRegion) simulation_0.get(ConditionTypeManager.class).get(PorousRegion.class));
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
	Region region_1 = 
      simulation_0.getRegionManager().getRegion("Radiator_(L)"); 
	
		
    region_1.setRegionType(porousRegion_0);
	}
    region_3.setRegionType(porousRegion_0);
	
	Units units_3 = 
      simulation_0.getUnitsManager().getInternalUnits(new IntVector(new int[] {1, -4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));
	  
	Units units_4 = 
      ((Units) simulation_0.getUnitsManager().getObject("kg/m^3-s"));

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
	Region region_1 = 
      simulation_0.getRegionManager().getRegion("Radiator_(L)"); 	
		
    LocalOrientation localOrientation_0 = 
      ((LocalOrientation) region_1.getValues().get(LocalOrientationManager.class).getObject("Local Orientation 1"));

    ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_4 = 
      ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("Radiator (L) - CSys"));
    localOrientation_0.setCoordinateSystem(exportedCartesianCoordinateSystem_4);

    PorousInertialResistance porousInertialResistance_0 = 
      region_1.getValues().get(PorousInertialResistance.class);
	  
    ScalarProfile scalarProfile_0 = 
      porousInertialResistance_0.getMethod(OrthotropicTensorProfileMethod.class).getProfile(2);
    scalarProfile_0.getMethod(ConstantScalarProfileMethod.class).getQuantity().setDefinition("${Porous_Inertial_Resistance}");
    scalarProfile_0.getMethod(ConstantScalarProfileMethod.class).getQuantity().setUnits(units_3);

    PorousViscousResistance porousViscousResistance_0 = 
      region_1.getValues().get(PorousViscousResistance.class);
	  
    ScalarProfile scalarProfile_1 = 
      porousViscousResistance_0.getMethod(OrthotropicTensorProfileMethod.class).getProfile(0);
    scalarProfile_1.getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(10000.0);
    scalarProfile_1.getMethod(ConstantScalarProfileMethod.class).getQuantity().setUnits(units_4);
	
    ScalarProfile scalarProfile_2 = 
      porousViscousResistance_0.getMethod(OrthotropicTensorProfileMethod.class).getProfile(1);
    scalarProfile_2.getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(10000.0);
    scalarProfile_2.getMethod(ConstantScalarProfileMethod.class).getQuantity().setUnits(units_4);

    ScalarProfile scalarProfile_3 = 
      porousViscousResistance_0.getMethod(OrthotropicTensorProfileMethod.class).getProfile(2);
    scalarProfile_3.getMethod(ConstantScalarProfileMethod.class).getQuantity().setDefinition("${Porous_Viscous_Resistance}");
    scalarProfile_3.getMethod(ConstantScalarProfileMethod.class).getQuantity().setUnits(units_4);
	}
	//}
	
		//{ // Radiator Right

    LocalOrientation localOrientation_1 = 
      ((LocalOrientation) region_3.getValues().get(LocalOrientationManager.class).getObject("Local Orientation 1"));

    ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_5 = 
      ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("Radiator (R) - CSys"));
    localOrientation_1.setCoordinateSystem(exportedCartesianCoordinateSystem_5);

    PorousInertialResistance porousInertialResistance_1 = 
      region_3.getValues().get(PorousInertialResistance.class);
	  
    ScalarProfile scalarProfile_4 = 
      porousInertialResistance_1.getMethod(OrthotropicTensorProfileMethod.class).getProfile(2);
    scalarProfile_4.getMethod(ConstantScalarProfileMethod.class).getQuantity().setDefinition("${Porous_Inertial_Resistance}");
    scalarProfile_4.getMethod(ConstantScalarProfileMethod.class).getQuantity().setUnits(units_3);

    PorousViscousResistance porousViscousResistance_1 = 
      region_3.getValues().get(PorousViscousResistance.class);

    ScalarProfile scalarProfile_5 = 
      porousViscousResistance_1.getMethod(OrthotropicTensorProfileMethod.class).getProfile(0);
    scalarProfile_5.getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(10000.0);
    scalarProfile_5.getMethod(ConstantScalarProfileMethod.class).getQuantity().setUnits(units_4);

    ScalarProfile scalarProfile_6 = 
      porousViscousResistance_1.getMethod(OrthotropicTensorProfileMethod.class).getProfile(1);
    scalarProfile_6.getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(10000.0);
    scalarProfile_6.getMethod(ConstantScalarProfileMethod.class).getQuantity().setUnits(units_4);

    ScalarProfile scalarProfile_7 = 
      porousViscousResistance_1.getMethod(OrthotropicTensorProfileMethod.class).getProfile(2);
    scalarProfile_7.getMethod(ConstantScalarProfileMethod.class).getQuantity().setDefinition("${Porous_Viscous_Resistance}");
    scalarProfile_7.getMethod(ConstantScalarProfileMethod.class).getQuantity().setUnits(units_4);
	//}
	
	//}	
	}
  
  private void Interfaces() {
	  
	//{ // Fan Interfaces

    Simulation simulation_0 = 
      getActiveSimulation();
	  
	double Roll = simulation_0.getReportManager().getReport("Roll [deg]").getReportMonitorValue();
	double Yaw = simulation_0.getReportManager().getReport("Yaw [rad]").getReportMonitorValue();
	double CornerRadius = simulation_0.getReportManager().getReport("Corner Radius [m]").getReportMonitorValue(); 
	double SIMIDdouble = simulation_0.getReportManager().getReport("Sim ID").getReportMonitorValue();
	int intSIM = (int) SIMIDdouble;
// Change table directory for fan 
    FileTable fileTable_0 = 
      (FileTable) simulation_0.getTableManager().createFromFile(resolvePath("Files\\Fan.csv"));
	  
	BaffleInterface baffleInterface_0 = 
      ((BaffleInterface) simulation_0.get(ConditionTypeManager.class).get(BaffleInterface.class));
	  
	FanInterface fanInterface_0 = 
      ((FanInterface) simulation_0.get(ConditionTypeManager.class).get(FanInterface.class));
	  
	//Interfaces Left Side  
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    BoundaryInterface boundaryInterface_0 = 
      ((BoundaryInterface) simulation_0.getInterfaceManager().getInterface("FanDuct_(L)/Fluid"));
    boundaryInterface_0.setPresentationName("Fan_shroud(L)");
    boundaryInterface_0.setInterfaceType(baffleInterface_0);

    BoundaryInterface boundaryInterface_1 = 
      ((BoundaryInterface) simulation_0.getInterfaceManager().getInterface("FanDuct_(L)/Fluid 2"));
    boundaryInterface_1.setPresentationName("Fan(L)");
    boundaryInterface_1.setInterfaceType(fanInterface_0);
	boundaryInterface_1.swapBoundaries();
	
    BoundaryInterface boundaryInterface_2 = 
      ((BoundaryInterface) simulation_0.getInterfaceManager().getInterface("FanDuct_(L)/Radiator_(L)"));
    boundaryInterface_2.setPresentationName("Radiator_outlet(L)");
	
	BoundaryInterface boundaryInterface_6 = 
      ((BoundaryInterface) simulation_0.getInterfaceManager().getInterface("Fluid/Radiator_(L)"));
    boundaryInterface_6.setPresentationName("Radiator_inlet(L)");

    BoundaryInterface boundaryInterface_7 = 
      ((BoundaryInterface) simulation_0.getInterfaceManager().getInterface("Fluid/Radiator_(L) 2"));
    boundaryInterface_7.setPresentationName("Radiator_shroud(L)");
    boundaryInterface_7.setInterfaceType(baffleInterface_0);
	}
	//Interfaces Right Side 
	
    BoundaryInterface boundaryInterface_3 = 
      ((BoundaryInterface) simulation_0.getInterfaceManager().getInterface("FanDuct_(R)/Fluid"));
    boundaryInterface_3.setPresentationName("Fan_shroud(R)");
    boundaryInterface_3.setInterfaceType(baffleInterface_0);

    BoundaryInterface boundaryInterface_4 = 
      ((BoundaryInterface) simulation_0.getInterfaceManager().getInterface("FanDuct_(R)/Fluid 2"));
    boundaryInterface_4.setPresentationName("Fan(R)");
    boundaryInterface_4.setInterfaceType(fanInterface_0);
	boundaryInterface_4.swapBoundaries();

    BoundaryInterface boundaryInterface_5 = 
      ((BoundaryInterface) simulation_0.getInterfaceManager().getInterface("FanDuct_(R)/Radiator_(R)"));
    boundaryInterface_5.setPresentationName("Radiator_outlet(R)");

    BoundaryInterface boundaryInterface_8 = 
      ((BoundaryInterface) simulation_0.getInterfaceManager().getInterface("Fluid/Radiator_(R)"));
    boundaryInterface_8.setPresentationName("Radiator_inlet(R)");

    BoundaryInterface boundaryInterface_9 = 
      ((BoundaryInterface) simulation_0.getInterfaceManager().getInterface("Fluid/Radiator_(R) 2"));
    boundaryInterface_9.setPresentationName("Radiator_shroud(R)");
    boundaryInterface_9.setInterfaceType(baffleInterface_0);
	
	// FAN settings RIGHT
	
    boundaryInterface_4.getConditions().get(AxialFanSwirlOption.class).setSelected(FanSwirlOption.Type.CALCULATED);
	
	Units units_0 = 
      ((Units) simulation_0.getUnitsManager().getObject("rpm"));

    FanCurveTable fanCurveTable_0 = 
      boundaryInterface_4.getValues().get(FanCurveTable.class);
    FanCurveTableLeaf fanCurveTableLeaf_0 = 
      fanCurveTable_0.getModelPartValue();

    fanCurveTableLeaf_0.setVolumeFlowTable(fileTable_0);
    fanCurveTableLeaf_0.setVolumeFlowTableX("AirFlow[m3/s]");
    fanCurveTableLeaf_0.setVolumeFlowTableP("StaticPressureDrop[Pa]");
    fanCurveTableLeaf_0.getFanCurvePressureRiseOption().setSelected(FanCurvePressureRiseOption.Type.STATIC_TO_STATIC);
    fanCurveTableLeaf_0.getOperatingRotationRate().setValue(6400.0);
    fanCurveTableLeaf_0.getOperatingRotationRate().setUnits(units_0);
    fanCurveTableLeaf_0.getDataRotationRate().setValue(6400.0);
    fanCurveTableLeaf_0.getDataRotationRate().setUnits(units_0);

    FanInterfaceSwirlProperties fanInterfaceSwirlProperties_0 = 
      boundaryInterface_4.getValues().get(FanInterfaceSwirlProperties.class);
    LabCoordinateSystem labCoordinateSystem_0 = 
      simulation_0.getCoordinateSystemManager().getLabCoordinateSystem();
    ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_0 = 
      ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("Fan (R) - Axis"));
    fanInterfaceSwirlProperties_0.setCoordinateSystem(exportedCartesianCoordinateSystem_0);
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
	// FAN settings LEFT
	BoundaryInterface boundaryInterface_1 = 
      ((BoundaryInterface) simulation_0.getInterfaceManager().getInterface("Fan(L)"));
    boundaryInterface_1.copyProperties(boundaryInterface_4);
    FanInterfaceSwirlProperties fanInterfaceSwirlProperties_1 = 
      boundaryInterface_1.getValues().get(FanInterfaceSwirlProperties.class);
    ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_1 = 
      ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("Fan (L) - Axis"));
    fanInterfaceSwirlProperties_1.setCoordinateSystem(exportedCartesianCoordinateSystem_1);
	}

	//}
	
	//{ // Execute Automated Mesh
	
	AutoMeshOperation autoMeshOperation_0 = 
      ((AutoMeshOperation) simulation_0.get(MeshOperationManager.class).getObject("Mesh"));
    autoMeshOperation_0.execute();
	
	simulation_0.saveState(resolvePath("..\\Simulation\\MC0"+intSIM+".sim"));
	
	star.vis.Scene scene_0 = 
      simulation_0.getSceneManager().getScene("Pre Checks");
	
	SimpleAnnotation simpleAnnotation_0 = 
      ((SimpleAnnotation) simulation_0.getAnnotationManager().getObject("Domain & Boxes"));
	scene_0.getAnnotationPropManager().removePropsForAnnotations(simpleAnnotation_0);  
	SimpleAnnotation simpleAnnotation_1 = 
      simulation_0.getAnnotationManager().createAnnotation(SimpleAnnotation.class);
	simpleAnnotation_1.copyProperties(simpleAnnotation_0);
	simpleAnnotation_1.setPresentationName("Mesh");
    simpleAnnotation_1.setText("Mesh");
	SimpleAnnotationProp simpleAnnotationProp_0 = 
      (SimpleAnnotationProp) scene_0.getAnnotationPropManager().createPropForAnnotation(simpleAnnotation_1);
	PartDisplayer partDisplayer_0 = 
      ((PartDisplayer) scene_0.getDisplayerManager().getDisplayer("Car"));  
	partDisplayer_0.setVisibilityOverrideMode(DisplayerVisibilityOverride.HIDE_ALL_PARTS);  
	PartDisplayer partDisplayer_1 = 
      ((PartDisplayer) scene_0.getDisplayerManager().getDisplayer("Domain & Boxes"));
    partDisplayer_1.setVisibilityOverrideMode(DisplayerVisibilityOverride.HIDE_ALL_PARTS);
	
	
	PartDisplayer partDisplayer_2 = 
      scene_0.getDisplayerManager().createPartDisplayer("Surface", -1, 1);
	partDisplayer_2.initialize();
	partDisplayer_2.setPresentationName("Mesh");
	partDisplayer_2.getInputParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, InterfaceBoundary.class), new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.DoesNotContain, "Domain"), new NamePredicate(NameOperator.DoesNotContain, "Fan"), new NamePredicate(NameOperator.DoesNotContain, "Radiator"))))), Query.STANDARD_MODIFIERS));
	partDisplayer_2.setMesh(true);
	partDisplayer_2.setColorMode(PartColorMode.CONSTANT);
	partDisplayer_2.setOutline(false);
    partDisplayer_2.setDisplayerColor(new DoubleVector(new double[] {0.5921568870544434, 0.5921568870544434, 0.5921568870544434}));
	FvRepresentation fvRepresentation_0 = 
      ((FvRepresentation) simulation_0.getRepresentationManager().getObject("Volume Mesh"));
    partDisplayer_2.setRepresentation(fvRepresentation_0);
	 
	CurrentView currentView_0 = 
      scene_0.getCurrentView();
	  
	VisView visView_0 = 
      ((VisView) simulation_0.getViewManager().getObject("bottom"));
    currentView_0.setView(visView_0);
    scene_0.printAndWait(resolvePath("..\\Visualization & Data\\Pre Checks\\Mesh\\bottom.png"), 1, 1920, 1080, true, false);
	VisView visView_1 = 
      ((VisView) simulation_0.getViewManager().getObject("bottom_front_left"));
    currentView_0.setView(visView_1);
    scene_0.printAndWait(resolvePath("..\\Visualization & Data\\Pre Checks\\Mesh\\bottom_front_left.png"), 1, 1920, 1080, true, false);
	VisView visView_2 = 
      ((VisView) simulation_0.getViewManager().getObject("bottom_front_right"));
    currentView_0.setView(visView_2);
    scene_0.printAndWait(resolvePath("..\\Visualization & Data\\Pre Checks\\Mesh\\bottom_front_right.png"), 1, 1920, 1080, true, false);
	VisView visView_3 = 
      ((VisView) simulation_0.getViewManager().getObject("bottom_rear_left"));
    currentView_0.setView(visView_3);
    scene_0.printAndWait(resolvePath("..\\Visualization & Data\\Pre Checks\\Mesh\\bottom_rear_left.png"), 1, 1920, 1080, true, false);
	VisView visView_4 = 
      ((VisView) simulation_0.getViewManager().getObject("bottom_rear_right"));
    currentView_0.setView(visView_4);
    scene_0.printAndWait(resolvePath("..\\Visualization & Data\\Pre Checks\\Mesh\\bottom_rear_right.png"), 1, 1920, 1080, true, false);
	VisView visView_5 = 
      ((VisView) simulation_0.getViewManager().getObject("front"));
    currentView_0.setView(visView_5);
    scene_0.printAndWait(resolvePath("..\\Visualization & Data\\Pre Checks\\Mesh\\front.png"), 1, 1920, 1080, true, false);
	VisView visView_6 = 
      ((VisView) simulation_0.getViewManager().getObject("left"));
    currentView_0.setView(visView_6);
    scene_0.printAndWait(resolvePath("..\\Visualization & Data\\Pre Checks\\Mesh\\left.png"), 1, 1920, 1080, true, false);
	VisView visView_7 = 
      ((VisView) simulation_0.getViewManager().getObject("rear"));
    currentView_0.setView(visView_7);
    scene_0.printAndWait(resolvePath("..\\Visualization & Data\\Pre Checks\\Mesh\\rear.png"), 1, 1920, 1080, true, false);
	VisView visView_8 = 
      ((VisView) simulation_0.getViewManager().getObject("right"));
    currentView_0.setView(visView_8);
    scene_0.printAndWait(resolvePath("..\\Visualization & Data\\Pre Checks\\Mesh\\right.png"), 1, 1920, 1080, true, false);
	VisView visView_9 = 
      ((VisView) simulation_0.getViewManager().getObject("top"));
    currentView_0.setView(visView_9);
    scene_0.printAndWait(resolvePath("..\\Visualization & Data\\Pre Checks\\Mesh\\top.png"), 1, 1920, 1080, true, false);
	VisView visView_10 = 
      ((VisView) simulation_0.getViewManager().getObject("top_front_left"));
    currentView_0.setView(visView_10);
    scene_0.printAndWait(resolvePath("..\\Visualization & Data\\Pre Checks\\Mesh\\top_front_left.png"), 1, 1920, 1080, true, false);
	VisView visView_11 = 
      ((VisView) simulation_0.getViewManager().getObject("top_front_right"));
    currentView_0.setView(visView_11);
    scene_0.printAndWait(resolvePath("..\\Visualization & Data\\Pre Checks\\Mesh\\top_front_right.png"), 1, 1920, 1080, true, false);
	VisView visView_12 = 
      ((VisView) simulation_0.getViewManager().getObject("top_rear_left"));
    currentView_0.setView(visView_12);
    scene_0.printAndWait(resolvePath("..\\Visualization & Data\\Pre Checks\\Mesh\\top_rear_left.png"), 1, 1920, 1080, true, false);
	VisView visView_13 = 
      ((VisView) simulation_0.getViewManager().getObject("top_rear_right"));
    currentView_0.setView(visView_13);
    scene_0.printAndWait(resolvePath("..\\Visualization & Data\\Pre Checks\\Mesh\\top_rear_right.png"), 1, 1920, 1080, true, false);
	
	//} 
	}
  
  private void Reports() {

	//{ // Center of Loads Reports

    Simulation simulation_0 = 
      getActiveSimulation();
	  
	double Roll = simulation_0.getReportManager().getReport("Roll [deg]").getReportMonitorValue();
	double Yaw = simulation_0.getReportManager().getReport("Yaw [rad]").getReportMonitorValue();
	double CornerRadius = simulation_0.getReportManager().getReport("Corner Radius [m]").getReportMonitorValue(); 
	  
	FvRepresentation fvRepresentation_0 = 
      ((FvRepresentation) simulation_0.getRepresentationManager().getObject("Volume Mesh"));
	  
	Units units_0 = 
      ((Units) simulation_0.getUnitsManager().getObject("m"));
	  
	Units units_1 = 
      ((Units) simulation_0.getUnitsManager().getObject(""));
	  
	LabCoordinateSystem labCoordinateSystem_0 = 
      simulation_0.getCoordinateSystemManager().getLabCoordinateSystem();
	  
    CenterOfLoadsReport centerOfLoadsReport_0 = 
      simulation_0.getReportManager().createReport(CenterOfLoadsReport.class);
    centerOfLoadsReport_0.setPresentationName("[CLoadX]- CoG");
    centerOfLoadsReport_0.setLocationOption(CenterOfLoadsLocationOption.ReferencePlane);
    centerOfLoadsReport_0.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, InterfaceBoundary.class), new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.DoesNotContain, "Domain"), new NamePredicate(NameOperator.DoesNotContain, "Fan"), new NamePredicate(NameOperator.DoesNotContain, "Radiator"))))), Query.STANDARD_MODIFIERS));
    centerOfLoadsReport_0.setRepresentation(fvRepresentation_0);

    ReferencePlaneDefinition referencePlaneDefinition_0 = 
      centerOfLoadsReport_0.getReferencePlane();
    referencePlaneDefinition_0.getNormalCoordinate().setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {0.0, 0.0, 1.0}));
    CartesianCoordinateSystem cartesianCoordinateSystem_0 = 
      ((CartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("CoG"));
    referencePlaneDefinition_0.setCoordinateSystem(cartesianCoordinateSystem_0);

    CenterOfLoadsReport centerOfLoadsReport_1 = 
      simulation_0.getReportManager().createReport(CenterOfLoadsReport.class);
    centerOfLoadsReport_1.copyProperties(centerOfLoadsReport_0);
    centerOfLoadsReport_1.setPresentationName("[CLoadY]- CoG");
    centerOfLoadsReport_1.getDirection().setComponents(0.0, 1.0, 0.0);
    centerOfLoadsReport_1.getDirection().setUnits(units_1);

    CenterOfLoadsReport centerOfLoadsReport_2 = 
      simulation_0.getReportManager().createReport(CenterOfLoadsReport.class);
    centerOfLoadsReport_2.copyProperties(centerOfLoadsReport_1);
    centerOfLoadsReport_2.setPresentationName("[CLoadZ]- CoG");
    centerOfLoadsReport_2.getDirection().setComponents(0.0, 0.0, 1.0);
    centerOfLoadsReport_2.getDirection().setUnits(units_1);

    CenterOfLoadsReport centerOfLoadsReport_3 = 
      simulation_0.getReportManager().createReport(CenterOfLoadsReport.class);
    centerOfLoadsReport_3.copyProperties(centerOfLoadsReport_0);
	centerOfLoadsReport_3.setPresentationName("[CLoadX]- Ground");

    CenterOfLoadsReport centerOfLoadsReport_4 = 
      simulation_0.getReportManager().createReport(CenterOfLoadsReport.class);
    centerOfLoadsReport_4.copyProperties(centerOfLoadsReport_1);
	centerOfLoadsReport_4.setPresentationName("[CLoadY]- Ground");

    CenterOfLoadsReport centerOfLoadsReport_5 = 
      simulation_0.getReportManager().createReport(CenterOfLoadsReport.class);
    centerOfLoadsReport_5.copyProperties(centerOfLoadsReport_2);
    centerOfLoadsReport_5.setPresentationName("[CLoadZ]- Ground");

    ReferencePlaneDefinition referencePlaneDefinition_1 = 
      centerOfLoadsReport_5.getReferencePlane();
    referencePlaneDefinition_1.setCoordinateSystem(labCoordinateSystem_0);

    ReferencePlaneDefinition referencePlaneDefinition_2 = 
      centerOfLoadsReport_4.getReferencePlane();
    referencePlaneDefinition_2.setCoordinateSystem(labCoordinateSystem_0);

    ReferencePlaneDefinition referencePlaneDefinition_3 = 
      centerOfLoadsReport_3.getReferencePlane();
    referencePlaneDefinition_3.setCoordinateSystem(labCoordinateSystem_0);

    simulation_0.getReportManager().getGroupsManager().createGroup("[CLoads]");
    ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[CLoads]")).getGroupsManager().createGroup("[Total]");
    ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[CLoads]")).getGroupsManager().getObject("[Total]")).getGroupsManager().groupObjects("[Total]", new NeoObjectVector(new Object[] {centerOfLoadsReport_0, centerOfLoadsReport_3, centerOfLoadsReport_1, centerOfLoadsReport_4, centerOfLoadsReport_2, centerOfLoadsReport_5}), true);
	//}
	
    //{ // Cooling  
	
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
	Region region_0 = 
      simulation_0.getRegionManager().getRegion("FanDuct_(L)");}
	  
	Region region_1 = 
      simulation_0.getRegionManager().getRegion("FanDuct_(R)");
	  
	Region region_2 = 
      simulation_0.getRegionManager().getRegion("Fluid");
	  
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
	MassFlowReport massFlowReport_0 = 
      simulation_0.getReportManager().createReport(MassFlowReport.class);
	Region region_0 = 
      simulation_0.getRegionManager().getRegion("FanDuct_(L)");
    massFlowReport_0.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, InterfaceBoundary.class), new NamePredicate(NameOperator.Contains, "Fan__L"), new RelationshipPredicate(RelationshipOperator.DescendantOf, new IdentityPredicate(IdentityOperator.Equals, Arrays.<ClientServerObject>asList(region_0))))), Query.STANDARD_MODIFIERS));
    massFlowReport_0.setPresentationName("[Mass Flow] - Fan (L)");
    massFlowReport_0.setRepresentation(fvRepresentation_0); }

    MassFlowReport massFlowReport_1 = 
      simulation_0.getReportManager().createReport(MassFlowReport.class);
	massFlowReport_1.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, InterfaceBoundary.class), new NamePredicate(NameOperator.Contains, "Fan__R"), new RelationshipPredicate(RelationshipOperator.DescendantOf, new IdentityPredicate(IdentityOperator.Equals, Arrays.<ClientServerObject>asList(region_1))))), Query.STANDARD_MODIFIERS));
    massFlowReport_1.setPresentationName("[Mass Flow] - Fan (R)");
	massFlowReport_1.setRepresentation(fvRepresentation_0);
	
    
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    MassFlowReport massFlowReport_2 = 
      simulation_0.getReportManager().createReport(MassFlowReport.class);
    massFlowReport_2.setPresentationName("[Mass Flow] - Radiator (L)");
    massFlowReport_2.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, InterfaceBoundary.class), new NamePredicate(NameOperator.Contains, "Radiator_outlet_L"))), Query.STANDARD_MODIFIERS));
    massFlowReport_2.setRepresentation(fvRepresentation_0);}

    MassFlowReport massFlowReport_3 = 
      simulation_0.getReportManager().createReport(MassFlowReport.class);
    massFlowReport_3.setPresentationName("[Mass Flow] - Radiator (R)");
    massFlowReport_3.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, InterfaceBoundary.class), new NamePredicate(NameOperator.Contains, "Radiator_outlet_R"))), Query.STANDARD_MODIFIERS));
	massFlowReport_3.setRepresentation(fvRepresentation_0);
	
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
	Region region_0 = 
      simulation_0.getRegionManager().getRegion("FanDuct_(L)");
    PressureDropReport pressureDropReport_0 = 
      simulation_0.getReportManager().createReport(PressureDropReport.class);
    pressureDropReport_0.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, InterfaceBoundary.class), new NamePredicate(NameOperator.Contains, "Fan__L"), new RelationshipPredicate(RelationshipOperator.DescendantOf, new IdentityPredicate(IdentityOperator.Equals, Arrays.<ClientServerObject>asList(region_2))))), Query.STANDARD_MODIFIERS));
    pressureDropReport_0.getLowPressureParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, InterfaceBoundary.class), new NamePredicate(NameOperator.Contains, "Fan__L"), new RelationshipPredicate(RelationshipOperator.DescendantOf, new IdentityPredicate(IdentityOperator.Equals, Arrays.<ClientServerObject>asList(region_0))))), Query.STANDARD_MODIFIERS));
    pressureDropReport_0.setRepresentation(fvRepresentation_0);
    pressureDropReport_0.setPresentationName("[PDrop] - Fan (L)");}

    PressureDropReport pressureDropReport_1 = 
      simulation_0.getReportManager().createReport(PressureDropReport.class);
    pressureDropReport_1.setPresentationName("[PDrop] - Fan (R)");
    pressureDropReport_1.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, InterfaceBoundary.class), new NamePredicate(NameOperator.Contains, "Fan__R"), new RelationshipPredicate(RelationshipOperator.DescendantOf, new IdentityPredicate(IdentityOperator.Equals, Arrays.<ClientServerObject>asList(region_2))))), Query.STANDARD_MODIFIERS));
    pressureDropReport_1.getLowPressureParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, InterfaceBoundary.class), new NamePredicate(NameOperator.Contains, "Fan__R"), new RelationshipPredicate(RelationshipOperator.DescendantOf, new IdentityPredicate(IdentityOperator.Equals, Arrays.<ClientServerObject>asList(region_1))))), Query.STANDARD_MODIFIERS));
	pressureDropReport_1.setPresentationName("[PDrop] - Fan (R)");
	
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    PressureDropReport pressureDropReport_2 = 
      simulation_0.getReportManager().createReport(PressureDropReport.class);
    pressureDropReport_2.setPresentationName("[PDrop] - Radiator (L)");
    pressureDropReport_2.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, InterfaceBoundary.class), new NamePredicate(NameOperator.Contains, "Radiator_inlet_L"), new RelationshipPredicate(RelationshipOperator.DescendantOf, new IdentityPredicate(IdentityOperator.Equals, Arrays.<ClientServerObject>asList(region_2))))), Query.STANDARD_MODIFIERS));
    pressureDropReport_2.getLowPressureParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, InterfaceBoundary.class), new NamePredicate(NameOperator.Contains, "Radiator_outlet_L"))), Query.STANDARD_MODIFIERS));
    pressureDropReport_2.setRepresentation(fvRepresentation_0);}

    PressureDropReport pressureDropReport_3 = 
      simulation_0.getReportManager().createReport(PressureDropReport.class);
    pressureDropReport_3.setPresentationName("[PDrop] - Radiator (R)");
    pressureDropReport_3.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, InterfaceBoundary.class), new NamePredicate(NameOperator.Contains, "Radiator_inlet_R"), new RelationshipPredicate(RelationshipOperator.DescendantOf, new IdentityPredicate(IdentityOperator.Equals, Arrays.<ClientServerObject>asList(region_2))))), Query.STANDARD_MODIFIERS));
    pressureDropReport_3.getLowPressureParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, InterfaceBoundary.class), new NamePredicate(NameOperator.Contains, "Radiator_outlet_R"))), Query.STANDARD_MODIFIERS));
	pressureDropReport_3.setRepresentation(fvRepresentation_0);
	
    simulation_0.getReportManager().getGroupsManager().createGroup("[Cooling]");
    ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Cooling]")).getGroupsManager().createGroup("[Monitor]");
	if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
	((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Cooling]")).getGroupsManager().getObject("[Monitor]")).getGroupsManager().groupObjects("[Monitor]", new NeoObjectVector(new Object[] {massFlowReport_1, massFlowReport_3, pressureDropReport_1, pressureDropReport_3}), true);
	} else {
	MassFlowReport massFlowReport_0 = 
      ((MassFlowReport) simulation_0.getReportManager().getReport("[Mass Flow] - Fan (L)"));
	MassFlowReport massFlowReport_2 = 
      ((MassFlowReport) simulation_0.getReportManager().getReport("[Mass Flow] - Radiator (L)"));
	PressureDropReport pressureDropReport_0 = 
      ((PressureDropReport) simulation_0.getReportManager().getReport("[PDrop] - Fan (L)"));
	PressureDropReport pressureDropReport_2 = 
      ((PressureDropReport) simulation_0.getReportManager().getReport("[PDrop] - Radiator (L)"));
    ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Cooling]")).getGroupsManager().getObject("[Monitor]")).getGroupsManager().groupObjects("[Monitor]", new NeoObjectVector(new Object[] {massFlowReport_0, massFlowReport_1, massFlowReport_2, massFlowReport_3, pressureDropReport_0, pressureDropReport_1, pressureDropReport_2, pressureDropReport_3}), true);
	}
	//}
	
	//{ // DownForce Reports 

    ForceReport forceReport_0 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_0.setPresentationName("[DownF] - Total");
    forceReport_0.getDirection().setComponents(0.0, 0.0, -1.0);
    forceReport_0.getDirection().setUnits(units_1);
    forceReport_0.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, InterfaceBoundary.class), new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.DoesNotContain, "Domain"), new NamePredicate(NameOperator.DoesNotContain, "Fan"), new NamePredicate(NameOperator.DoesNotContain, "Radiator"))))), Query.STANDARD_MODIFIERS));
    forceReport_0.setRepresentation(fvRepresentation_0);

    ForceReport forceReport_1 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_1.copyProperties(forceReport_0);
    forceReport_1.setPresentationName("[DownF] - BCKDiff");
    forceReport_1.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.Contains, "diff"))), Query.STANDARD_MODIFIERS));

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_2 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_2.copyProperties(forceReport_1);
    forceReport_2.setPresentationName("[DownF] - W FL");
    forceReport_2.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "Tyre_FL"), new NamePredicate(NameOperator.Contains, "Rim_FL"))))), Query.STANDARD_MODIFIERS));}
	
    ForceReport forceReport_3 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_3.copyProperties(forceReport_1);
    forceReport_3.setPresentationName("[DownF] - W FR");
    forceReport_3.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "Tyre_FR"), new NamePredicate(NameOperator.Contains, "Rim_FR"))))), Query.STANDARD_MODIFIERS));
	
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_4 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_4.copyProperties(forceReport_1);
    forceReport_4.setPresentationName("[DownF] - W RL");
    forceReport_4.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "Tyre_RL"), new NamePredicate(NameOperator.Contains, "Rim_RL"))))), Query.STANDARD_MODIFIERS));}
	
    ForceReport forceReport_5 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_5.copyProperties(forceReport_1);
    forceReport_5.setPresentationName("[DownF] - W RR");
    forceReport_5.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "Tyre_RR"), new NamePredicate(NameOperator.Contains, "Rim_RR"))))), Query.STANDARD_MODIFIERS));

    ForceReport forceReport_6 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_6.copyProperties(forceReport_1);
    forceReport_6.setPresentationName("[DownF] - FW");
    forceReport_6.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.Contains, "FW"))), Query.STANDARD_MODIFIERS));
	
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_7 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_7.copyProperties(forceReport_1);
    forceReport_7.setPresentationName("[DownF] - FW - EP(L)");
    forceReport_7.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.Contains, "FW_EP_L"))), Query.STANDARD_MODIFIERS));}
	
    ForceReport forceReport_8 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_8.copyProperties(forceReport_1);
    forceReport_8.setPresentationName("[DownF] - FW - EP(R)");
    forceReport_8.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.Contains, "FW_EP_R"))), Query.STANDARD_MODIFIERS));
	
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_9 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_9.copyProperties(forceReport_1);
    forceReport_9.setPresentationName("[DownF] - FW - Flap(L)");
    forceReport_9.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.Contains, "FW_FLAP_L"))), Query.STANDARD_MODIFIERS));}
	
    ForceReport forceReport_10 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_10.copyProperties(forceReport_8);
    forceReport_10.setPresentationName("[DownF] - FW - Flap(R)");
    forceReport_10.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.Contains, "FW_FLAP_R"))), Query.STANDARD_MODIFIERS));

    ForceReport forceReport_11 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_11.copyProperties(forceReport_10);
    forceReport_11.setPresentationName("[DownF] - FW - Main");
    forceReport_11.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.Contains, "FW"), new NamePredicate(NameOperator.Contains, "Main"))), Query.STANDARD_MODIFIERS));

    ForceReport forceReport_12 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_12.copyProperties(forceReport_6);
    forceReport_12.setPresentationName("[DownF] - Mono");
    forceReport_12.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.Contains, "Mono"), new NamePredicate(NameOperator.DoesNotContain, "Diff"), new NamePredicate(NameOperator.DoesNotContain, "Brack"), new NamePredicate(NameOperator.DoesNotContain, "Steer"))), Query.STANDARD_MODIFIERS));

    ForceReport forceReport_13 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_13.copyProperties(forceReport_12);
    forceReport_13.setPresentationName("[DownF] - RW");
    forceReport_13.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.Contains, "RW"), new NamePredicate(NameOperator.DoesNotContain, "Supp"))), Query.STANDARD_MODIFIERS));

    ForceReport forceReport_14 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_14.copyProperties(forceReport_13);
    forceReport_14.setPresentationName("[DownF] - RW - EP");
    forceReport_14.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.Contains, "RW"), new NamePredicate(NameOperator.Contains, "Endplate"))), Query.STANDARD_MODIFIERS));

    ForceReport forceReport_15 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_15.copyProperties(forceReport_14);
    forceReport_15.setPresentationName("[DownF] - RW - Flap1");
    forceReport_15.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.Contains, "RW"), new NamePredicate(NameOperator.Contains, "Flap1"))), Query.STANDARD_MODIFIERS));

    ForceReport forceReport_16 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_16.copyProperties(forceReport_15);
    forceReport_16.setPresentationName("[DownF] - RW - Flap2");
    forceReport_16.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.Contains, "RW"), new NamePredicate(NameOperator.Contains, "Flap2"))), Query.STANDARD_MODIFIERS));

    ForceReport forceReport_17 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_17.copyProperties(forceReport_16);
    forceReport_17.setPresentationName("[DownF] - RW - Main");
    forceReport_17.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.Contains, "RW"), new NamePredicate(NameOperator.Contains, "Main"))), Query.STANDARD_MODIFIERS));
	
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_18 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_18.copyProperties(forceReport_1);
    forceReport_18.setPresentationName("[DownF] - SC(L)");
    forceReport_18.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.Contains, "SE_"), new NamePredicate(NameOperator.DoesNotContain, "_R"))), Query.STANDARD_MODIFIERS));}
	
    ForceReport forceReport_19 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_19.copyProperties(forceReport_13);
    forceReport_19.setPresentationName("[DownF] - SC(R)");
    forceReport_19.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.Contains, "SE_"), new NamePredicate(NameOperator.Contains, "_R"))), Query.STANDARD_MODIFIERS));
	
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_20 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_20.copyProperties(forceReport_1);
    forceReport_20.setPresentationName("[DownF] - SC EP(L)");
    forceReport_20.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.Contains, "SE_"), new NamePredicate(NameOperator.DoesNotContain, "_R"), new NamePredicate(NameOperator.Contains, "Endplate"))), Query.STANDARD_MODIFIERS));}
	
    ForceReport forceReport_21 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_21.copyProperties(forceReport_1);
    forceReport_21.setPresentationName("[DownF] - SC EP (R)");
    forceReport_21.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.Contains, "SE_"), new NamePredicate(NameOperator.Contains, "_R"), new NamePredicate(NameOperator.Contains, "Endplate"))), Query.STANDARD_MODIFIERS));
	
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_22 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_22.copyProperties(forceReport_1);
    forceReport_22.setPresentationName("[DownF] - SC0 (L)");
    forceReport_22.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.Contains, "SE0"), new NamePredicate(NameOperator.DoesNotContain, "_R"))), Query.STANDARD_MODIFIERS));}
	
    ForceReport forceReport_23 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_23.copyProperties(forceReport_21);
    forceReport_23.setPresentationName("[DownF] - SC0 (R)");
    forceReport_23.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.Contains, "SE0"), new NamePredicate(NameOperator.Contains, "_R"))), Query.STANDARD_MODIFIERS));
	
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_24 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_24.copyProperties(forceReport_1);
    forceReport_24.setPresentationName("[DownF] - SC1 (L)");
    forceReport_24.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.Contains, "SE1"), new NamePredicate(NameOperator.DoesNotContain, "_R"))), Query.STANDARD_MODIFIERS));}	
	
    ForceReport forceReport_25 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_25.copyProperties(forceReport_1);
    forceReport_25.setPresentationName("[DownF] - SC1 (R)");
    forceReport_25.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.Contains, "SE1"), new NamePredicate(NameOperator.Contains, "_R"))), Query.STANDARD_MODIFIERS));

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_26 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_26.copyProperties(forceReport_1);
    forceReport_26.setPresentationName("[DownF] - SC2 (L)");
    forceReport_26.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.Contains, "SE2"), new NamePredicate(NameOperator.DoesNotContain, "_R"))), Query.STANDARD_MODIFIERS));}
    
	ForceReport forceReport_27 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_27.copyProperties(forceReport_1);
    forceReport_27.setPresentationName("[DownF] - SC2 (R)");
    forceReport_27.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.Contains, "SE2"), new NamePredicate(NameOperator.Contains, "_R"))), Query.STANDARD_MODIFIERS));

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_28 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_28.copyProperties(forceReport_1);
    forceReport_28.setPresentationName("[DownF] - SC3 (L)");
    forceReport_28.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.Contains, "SE3"), new NamePredicate(NameOperator.DoesNotContain, "_R"))), Query.STANDARD_MODIFIERS));}
	
    ForceReport forceReport_29 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_29.copyProperties(forceReport_1);
    forceReport_29.setPresentationName("[DownF] - SC3 (R)");
    forceReport_29.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.Contains, "SE3"), new NamePredicate(NameOperator.Contains, "_R"))), Query.STANDARD_MODIFIERS));

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_30 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_30.copyProperties(forceReport_1);
    forceReport_30.setPresentationName("[DownF] - UB(L)");
    forceReport_30.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "Underbody"), new NamePredicate(NameOperator.DoesNotContain, "_R"))), new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "UB"), new NamePredicate(NameOperator.DoesNotContain, "Diff"), new NamePredicate(NameOperator.DoesNotContain, "_R"))))))), Query.STANDARD_MODIFIERS));}

    ForceReport forceReport_31 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_31.copyProperties(forceReport_1);
    forceReport_31.setPresentationName("[DownF] - UB(R)");
    forceReport_31.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "Underbody"), new NamePredicate(NameOperator.Contains, "_R"))), new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "UB"), new NamePredicate(NameOperator.DoesNotContain, "Diff"), new NamePredicate(NameOperator.Contains, "_R"))))))), Query.STANDARD_MODIFIERS));

	ForceReport forceReport_32 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_32.copyProperties(forceReport_1);
    forceReport_32.setPresentationName("[DownF] - Driver | MH | HR");
    forceReport_32.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "Driver"), new NamePredicate(NameOperator.Contains, "Main_Hoop"), new NamePredicate(NameOperator.Contains, "CH.HR"))))), Query.STANDARD_MODIFIERS));

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_33 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_33.copyProperties(forceReport_1);
    forceReport_33.setPresentationName("[DownF] - SU FL");
    forceReport_33.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "SU"), new NamePredicate(NameOperator.Contains, "FL"), new NamePredicate(NameOperator.DoesNotContain, "Rim"), new NamePredicate(NameOperator.DoesNotContain, "Tyre"))))), Query.STANDARD_MODIFIERS));}

    ForceReport forceReport_34 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_34.copyProperties(forceReport_1);
    forceReport_34.setPresentationName("[DownF] - SU FR");
    forceReport_34.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "SU"), new NamePredicate(NameOperator.Contains, "FR"), new NamePredicate(NameOperator.DoesNotContain, "Rim"), new NamePredicate(NameOperator.DoesNotContain, "Tyre"))))), Query.STANDARD_MODIFIERS));

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_35 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_35.copyProperties(forceReport_1);
    forceReport_35.setPresentationName("[DownF] - SU RL");
    forceReport_35.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "SU"), new NamePredicate(NameOperator.Contains, "RL"), new NamePredicate(NameOperator.DoesNotContain, "Rim"), new NamePredicate(NameOperator.DoesNotContain, "Tyre"), new NamePredicate(NameOperator.DoesNotContain, "DPR"))))), Query.STANDARD_MODIFIERS));}
	
    ForceReport forceReport_36 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_36.copyProperties(forceReport_1);
    forceReport_36.setPresentationName("[DownF] - SU RR");
    forceReport_36.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "SU"), new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "_RR"), new NamePredicate(NameOperator.Contains, "RR_"))), new NamePredicate(NameOperator.DoesNotContain, "Rim"), new NamePredicate(NameOperator.DoesNotContain, "Tyre"), new NamePredicate(NameOperator.DoesNotContain, "DPR"))))), Query.STANDARD_MODIFIERS));
	//}
	
    //{ // Drag Forces Report
	
	ForceReport forceReport_37 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_37.copyProperties(forceReport_1);
	forceReport_37.setPresentationName("[Drag] - BCKDiff");
	forceReport_37.getDirection().setComponents(-1.0, 0.0, 0.0);
    forceReport_37.getDirection().setUnits(units_1);
	
    ForceReport forceReport_38 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_38.copyProperties(forceReport_32);
	forceReport_38.setPresentationName("[Drag] - Driver | MH | HR");
	forceReport_38.getDirection().setComponents(-1.0, 0.0, 0.0);
    forceReport_38.getDirection().setUnits(units_1);

    ForceReport forceReport_39 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_39.copyProperties(forceReport_6);
	forceReport_39.setPresentationName("[Drag] - FW");
	forceReport_39.getDirection().setComponents(-1.0, 0.0, 0.0);
    forceReport_39.getDirection().setUnits(units_1);

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_40 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
	ForceReport forceReport_7 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - FW - EP(L)"));
    forceReport_40.copyProperties(forceReport_7);
    forceReport_40.setPresentationName("[Drag] - FW - EP(L)");
	forceReport_40.getDirection().setComponents(-1.0, 0.0, 0.0);
    forceReport_40.getDirection().setUnits(units_1);}
	
    ForceReport forceReport_41 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_41.copyProperties(forceReport_8);
    forceReport_41.setPresentationName("[Drag] - FW - EP(R)");
	forceReport_41.getDirection().setComponents(-1.0, 0.0, 0.0);
    forceReport_41.getDirection().setUnits(units_1);

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_42 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
	ForceReport forceReport_9 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - FW - Flap(L)"));
    forceReport_42.copyProperties(forceReport_9);
    forceReport_42.setPresentationName("[Drag] - FW - Flap(L)");
	forceReport_42.getDirection().setComponents(-1.0, 0.0, 0.0);
    forceReport_42.getDirection().setUnits(units_1);}

    ForceReport forceReport_43 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_43.copyProperties(forceReport_10);
    forceReport_43.setPresentationName("[Drag] - FW - Flap(R)");
	forceReport_43.getDirection().setComponents(-1.0, 0.0, 0.0);
    forceReport_43.getDirection().setUnits(units_1);

    ForceReport forceReport_44 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_44.copyProperties(forceReport_11);
    forceReport_44.setPresentationName("[Drag] - FW - Main");
	forceReport_44.getDirection().setComponents(-1.0, 0.0, 0.0);
    forceReport_44.getDirection().setUnits(units_1);

    ForceReport forceReport_45 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_45.copyProperties(forceReport_12);
    forceReport_45.setPresentationName("[Drag] - Mono");
	forceReport_45.getDirection().setComponents(-1.0, 0.0, 0.0);
    forceReport_45.getDirection().setUnits(units_1);

    ForceReport forceReport_46 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_46.copyProperties(forceReport_13);
    forceReport_46.setPresentationName("[Drag] - RW");
	forceReport_46.getDirection().setComponents(-1.0, 0.0, 0.0);
    forceReport_46.getDirection().setUnits(units_1);

    ForceReport forceReport_47 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_47.copyProperties(forceReport_14);
    forceReport_47.setPresentationName("[Drag] - RW - EP");
	forceReport_47.getDirection().setComponents(-1.0, 0.0, 0.0);
    forceReport_47.getDirection().setUnits(units_1);

    ForceReport forceReport_48 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_48.copyProperties(forceReport_15);
    forceReport_48.setPresentationName("[Drag] - RW - Flap1");
	forceReport_48.getDirection().setComponents(-1.0, 0.0, 0.0);
    forceReport_48.getDirection().setUnits(units_1);

    ForceReport forceReport_49 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_49.copyProperties(forceReport_16);
    forceReport_49.setPresentationName("[Drag] - RW - Flap2");
	forceReport_49.getDirection().setComponents(-1.0, 0.0, 0.0);
    forceReport_49.getDirection().setUnits(units_1);

    ForceReport forceReport_50 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_50.copyProperties(forceReport_17);
    forceReport_50.setPresentationName("[Drag] - RW - Main");
	forceReport_50.getDirection().setComponents(-1.0, 0.0, 0.0);
    forceReport_50.getDirection().setUnits(units_1);

    ForceReport forceReport_51 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_51.copyProperties(forceReport_21);
    forceReport_51.setPresentationName("[Drag] - SC EP (R)");
	forceReport_51.getDirection().setComponents(-1.0, 0.0, 0.0);
    forceReport_51.getDirection().setUnits(units_1);

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_52 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
	ForceReport forceReport_20 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - SC EP(L)"));
    forceReport_52.copyProperties(forceReport_20);
    forceReport_52.setPresentationName("[Drag] - SC EP(L)");
	forceReport_52.getDirection().setComponents(-1.0, 0.0, 0.0);
    forceReport_52.getDirection().setUnits(units_1);
	
    ForceReport forceReport_53 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
	ForceReport forceReport_18 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - SC(L)"));
    forceReport_53.copyProperties(forceReport_18);
    forceReport_53.setPresentationName("[Drag] - SC(L)");
	forceReport_53.getDirection().setComponents(-1.0, 0.0, 0.0);
    forceReport_53.getDirection().setUnits(units_1);}

    ForceReport forceReport_54 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_54.copyProperties(forceReport_19);
    forceReport_54.setPresentationName("[Drag] - SC(R)");
	forceReport_54.getDirection().setComponents(-1.0, 0.0, 0.0);
    forceReport_54.getDirection().setUnits(units_1);

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_55 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
	ForceReport forceReport_22 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - SC0 (L)"));
    forceReport_55.copyProperties(forceReport_22);
    forceReport_55.setPresentationName("[Drag] - SC0 (L)");
	forceReport_55.getDirection().setComponents(-1.0, 0.0, 0.0);
    forceReport_55.getDirection().setUnits(units_1);}

    ForceReport forceReport_56 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_56.copyProperties(forceReport_23);
    forceReport_56.setPresentationName("[Drag] - SC0 (R)");
	forceReport_56.getDirection().setComponents(-1.0, 0.0, 0.0);
    forceReport_56.getDirection().setUnits(units_1);

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_57 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
	ForceReport forceReport_24 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - SC1 (L)"));
    forceReport_57.copyProperties(forceReport_24);
    forceReport_57.setPresentationName("[Drag] - SC1 (L)");
	forceReport_57.getDirection().setComponents(-1.0, 0.0, 0.0);
    forceReport_57.getDirection().setUnits(units_1);}

    ForceReport forceReport_58 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_58.copyProperties(forceReport_25);
    forceReport_58.setPresentationName("[Drag] - SC1 (R)");
	forceReport_58.getDirection().setComponents(-1.0, 0.0, 0.0);
    forceReport_58.getDirection().setUnits(units_1);

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_59 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
	ForceReport forceReport_26 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - SC2 (L)"));
    forceReport_59.copyProperties(forceReport_26);
    forceReport_59.setPresentationName("[Drag] - SC2 (L)");
	forceReport_59.getDirection().setComponents(-1.0, 0.0, 0.0);
    forceReport_59.getDirection().setUnits(units_1);}

    ForceReport forceReport_60 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_60.copyProperties(forceReport_27);
    forceReport_60.setPresentationName("[Drag] - SC2 (R)");
	forceReport_60.getDirection().setComponents(-1.0, 0.0, 0.0);
    forceReport_60.getDirection().setUnits(units_1);

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_61 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
	ForceReport forceReport_28 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - SC3 (L)"));
    forceReport_61.copyProperties(forceReport_28);
    forceReport_61.setPresentationName("[Drag] - SC3 (L)");
	forceReport_61.getDirection().setComponents(-1.0, 0.0, 0.0);
    forceReport_61.getDirection().setUnits(units_1);}

    ForceReport forceReport_62 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_62.copyProperties(forceReport_29);
    forceReport_62.setPresentationName("[Drag] - SC3 (R)");
	forceReport_62.getDirection().setComponents(-1.0, 0.0, 0.0);
    forceReport_62.getDirection().setUnits(units_1);

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_63 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
	ForceReport forceReport_33 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - SU FL"));
    forceReport_63.copyProperties(forceReport_33);
    forceReport_63.setPresentationName("[Drag] - SU FL");
	forceReport_63.getDirection().setComponents(-1.0, 0.0, 0.0);
    forceReport_63.getDirection().setUnits(units_1);}

    ForceReport forceReport_64 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_64.copyProperties(forceReport_34);
    forceReport_64.setPresentationName("[Drag] - SU FR");
	forceReport_64.getDirection().setComponents(-1.0, 0.0, 0.0);
    forceReport_64.getDirection().setUnits(units_1);

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_65 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
	ForceReport forceReport_35 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - SU RL"));
    forceReport_65.copyProperties(forceReport_35);
    forceReport_65.setPresentationName("[Drag] - SU RL");
	forceReport_65.getDirection().setComponents(-1.0, 0.0, 0.0);
    forceReport_65.getDirection().setUnits(units_1);}

    ForceReport forceReport_66 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_66.copyProperties(forceReport_36);
    forceReport_66.setPresentationName("[Drag] - SU RR");
	forceReport_66.getDirection().setComponents(-1.0, 0.0, 0.0);
    forceReport_66.getDirection().setUnits(units_1);

    ForceReport forceReport_67 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_67.copyProperties(forceReport_0);
    forceReport_67.setPresentationName("[Drag] - Total");
	forceReport_67.getDirection().setComponents(-1.0, 0.0, 0.0);
    forceReport_67.getDirection().setUnits(units_1);

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_68 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
	ForceReport forceReport_30 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - UB(L)"));
    forceReport_68.copyProperties(forceReport_30);
    forceReport_68.setPresentationName("[Drag] - UB(L)");
	forceReport_68.getDirection().setComponents(-1.0, 0.0, 0.0);
    forceReport_68.getDirection().setUnits(units_1);}

    ForceReport forceReport_69 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_69.copyProperties(forceReport_31);
    forceReport_69.setPresentationName("[Drag] - UB(R)");
	forceReport_69.getDirection().setComponents(-1.0, 0.0, 0.0);
    forceReport_69.getDirection().setUnits(units_1);

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_70 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
	ForceReport forceReport_2 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - W FL"));
    forceReport_70.copyProperties(forceReport_2);
    forceReport_70.setPresentationName("[Drag] - W FL");
	forceReport_70.getDirection().setComponents(-1.0, 0.0, 0.0);
    forceReport_70.getDirection().setUnits(units_1);}

    ForceReport forceReport_71 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_71.copyProperties(forceReport_3);
    forceReport_71.setPresentationName("[Drag] - W FR");
	forceReport_71.getDirection().setComponents(-1.0, 0.0, 0.0);
    forceReport_71.getDirection().setUnits(units_1);

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_72 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
	ForceReport forceReport_4 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - W RL"));
    forceReport_72.copyProperties(forceReport_4);
    forceReport_72.setPresentationName("[Drag] - W RL");
	forceReport_72.getDirection().setComponents(-1.0, 0.0, 0.0);
    forceReport_72.getDirection().setUnits(units_1);}

    ForceReport forceReport_73 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_73.copyProperties(forceReport_5);
    forceReport_73.setPresentationName("[Drag] - W RR");
	forceReport_73.getDirection().setComponents(-1.0, 0.0, 0.0);
    forceReport_73.getDirection().setUnits(units_1);
	//}
	
	//{ // SideForce Reports
	
    ForceReport forceReport_74 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_74.copyProperties(forceReport_37);
    forceReport_74.setPresentationName("[SideF] - BCKDiff");
	forceReport_74.getDirection().setComponents(0.0, 1.0, 0.0);
    forceReport_74.getDirection().setUnits(units_1);

    ForceReport forceReport_75 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_75.copyProperties(forceReport_38);
    forceReport_75.setPresentationName("[SideF] - Driver | MH | HR");
	forceReport_75.getDirection().setComponents(0.0, 1.0, 0.0);
    forceReport_75.getDirection().setUnits(units_1);

    ForceReport forceReport_76 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_76.copyProperties(forceReport_39);
    forceReport_76.setPresentationName("[SideF] - FW");
	forceReport_76.getDirection().setComponents(0.0, 1.0, 0.0);
    forceReport_76.getDirection().setUnits(units_1);

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_77 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
	ForceReport forceReport_40 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - FW - EP(L)"));
    forceReport_77.copyProperties(forceReport_40);
    forceReport_77.setPresentationName("[SideF] - FW - EP(L)");
	forceReport_77.getDirection().setComponents(0.0, 1.0, 0.0);
    forceReport_77.getDirection().setUnits(units_1);}

    ForceReport forceReport_78 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_78.copyProperties(forceReport_41);
    forceReport_78.setPresentationName("[SideF] - FW - EP(R)");
	forceReport_78.getDirection().setComponents(0.0, 1.0, 0.0);
    forceReport_78.getDirection().setUnits(units_1);

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_79 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
	ForceReport forceReport_42 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - FW - Flap(L)"));
    forceReport_79.copyProperties(forceReport_42);
    forceReport_79.setPresentationName("[SideF] - FW - Flap(L)");
	forceReport_79.getDirection().setComponents(0.0, 1.0, 0.0);
    forceReport_79.getDirection().setUnits(units_1);}

    ForceReport forceReport_80 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_80.copyProperties(forceReport_43);
    forceReport_80.setPresentationName("[SideF] - FW - Flap(R)");
	forceReport_80.getDirection().setComponents(0.0, 1.0, 0.0);
    forceReport_80.getDirection().setUnits(units_1);

    ForceReport forceReport_81 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_81.copyProperties(forceReport_44);
    forceReport_81.setPresentationName("[SideF] - FW - Main");
	forceReport_81.getDirection().setComponents(0.0, 1.0, 0.0);
    forceReport_81.getDirection().setUnits(units_1);

    ForceReport forceReport_82 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_82.copyProperties(forceReport_45);
    forceReport_82.setPresentationName("[SideF] - Mono");
	forceReport_82.getDirection().setComponents(0.0, 1.0, 0.0);
    forceReport_82.getDirection().setUnits(units_1);

    ForceReport forceReport_83 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_83.copyProperties(forceReport_46);
    forceReport_83.setPresentationName("[SideF] - RW");
	forceReport_83.getDirection().setComponents(0.0, 1.0, 0.0);
    forceReport_83.getDirection().setUnits(units_1);

    ForceReport forceReport_84 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_84.copyProperties(forceReport_47);
    forceReport_84.setPresentationName("[SideF] - RW - EP");
	forceReport_84.getDirection().setComponents(0.0, 1.0, 0.0);
    forceReport_84.getDirection().setUnits(units_1);

    ForceReport forceReport_85 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_85.copyProperties(forceReport_48);
    forceReport_85.setPresentationName("[SideF] - RW - Flap1");
	forceReport_85.getDirection().setComponents(0.0, 1.0, 0.0);
    forceReport_85.getDirection().setUnits(units_1);

    ForceReport forceReport_86 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_86.copyProperties(forceReport_49);
    forceReport_86.setPresentationName("[SideF] - RW - Flap2");
	forceReport_86.getDirection().setComponents(0.0, 1.0, 0.0);
    forceReport_86.getDirection().setUnits(units_1);

    ForceReport forceReport_87 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_87.copyProperties(forceReport_50);
    forceReport_87.setPresentationName("[SideF] - RW - Main");
	forceReport_87.getDirection().setComponents(0.0, 1.0, 0.0);
    forceReport_87.getDirection().setUnits(units_1);

    ForceReport forceReport_88 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_88.copyProperties(forceReport_51);
    forceReport_88.setPresentationName("[SideF] - SC EP (R)");
	forceReport_88.getDirection().setComponents(0.0, 1.0, 0.0);
    forceReport_88.getDirection().setUnits(units_1);

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_89 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
	ForceReport forceReport_52 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - SC EP(L)"));
    forceReport_89.copyProperties(forceReport_52);
    forceReport_89.setPresentationName("[SideF] - SC EP(L)");
	forceReport_89.getDirection().setComponents(0.0, 1.0, 0.0);
    forceReport_89.getDirection().setUnits(units_1);

    ForceReport forceReport_90 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
	ForceReport forceReport_53 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - SC(L)"));
    forceReport_90.copyProperties(forceReport_53);
    forceReport_90.setPresentationName("[SideF] - SC(L)");
	forceReport_90.getDirection().setComponents(0.0, 1.0, 0.0);
    forceReport_90.getDirection().setUnits(units_1);}

    ForceReport forceReport_91 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_91.copyProperties(forceReport_54);
    forceReport_91.setPresentationName("[SideF] - SC(R)");
	forceReport_91.getDirection().setComponents(0.0, 1.0, 0.0);
    forceReport_91.getDirection().setUnits(units_1);

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_92 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
	ForceReport forceReport_55 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - SC0 (L)"));
    forceReport_92.copyProperties(forceReport_55);
    forceReport_92.setPresentationName("[SideF] - SC0 (L)");
	forceReport_92.getDirection().setComponents(0.0, 1.0, 0.0);
    forceReport_92.getDirection().setUnits(units_1);}

    ForceReport forceReport_93 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_93.copyProperties(forceReport_56);
    forceReport_93.setPresentationName("[SideF] - SC0 (R)");
	forceReport_93.getDirection().setComponents(0.0, 1.0, 0.0);
    forceReport_93.getDirection().setUnits(units_1);

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_94 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
	ForceReport forceReport_57 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - SC1 (L)"));
    forceReport_94.copyProperties(forceReport_57);
    forceReport_94.setPresentationName("[SideF] - SC1 (L)");
	forceReport_94.getDirection().setComponents(0.0, 1.0, 0.0);
    forceReport_94.getDirection().setUnits(units_1);}

    ForceReport forceReport_95 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_95.copyProperties(forceReport_58);
    forceReport_95.setPresentationName("[SideF] - SC1 (R)");
	forceReport_95.getDirection().setComponents(0.0, 1.0, 0.0);
    forceReport_95.getDirection().setUnits(units_1);

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_96 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
	ForceReport forceReport_59 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - SC2 (L)"));
    forceReport_96.copyProperties(forceReport_59);
    forceReport_96.setPresentationName("[SideF] - SC2 (L)");
	forceReport_96.getDirection().setComponents(0.0, 1.0, 0.0);
    forceReport_96.getDirection().setUnits(units_1);}

    ForceReport forceReport_97 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_97.copyProperties(forceReport_60);
    forceReport_97.setPresentationName("[SideF] - SC2 (R)");
	forceReport_97.getDirection().setComponents(0.0, 1.0, 0.0);
    forceReport_97.getDirection().setUnits(units_1);

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_98 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
	ForceReport forceReport_61 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - SC3 (L)"));
    forceReport_98.copyProperties(forceReport_61);
    forceReport_98.setPresentationName("[SideF] - SC3 (L)");
	forceReport_98.getDirection().setComponents(0.0, 1.0, 0.0);
    forceReport_98.getDirection().setUnits(units_1);}

    ForceReport forceReport_99 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_99.copyProperties(forceReport_62);
    forceReport_99.setPresentationName("[SideF] - SC3 (R)");
	forceReport_99.getDirection().setComponents(0.0, 1.0, 0.0);
    forceReport_99.getDirection().setUnits(units_1);

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_100 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
	ForceReport forceReport_63 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - SU FL"));
    forceReport_100.copyProperties(forceReport_63);
    forceReport_100.setPresentationName("[SideF] - SU FL");
	forceReport_100.getDirection().setComponents(0.0, 1.0, 0.0);
    forceReport_100.getDirection().setUnits(units_1);}

    ForceReport forceReport_101 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_101.copyProperties(forceReport_64);
    forceReport_101.setPresentationName("[SideF] - SU FR");
	forceReport_101.getDirection().setComponents(0.0, 1.0, 0.0);
    forceReport_101.getDirection().setUnits(units_1);

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_102 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
	ForceReport forceReport_65 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - SU RL"));
    forceReport_102.copyProperties(forceReport_65);
    forceReport_102.setPresentationName("[SideF] - SU RL");
	forceReport_102.getDirection().setComponents(0.0, 1.0, 0.0);
    forceReport_102.getDirection().setUnits(units_1);}

    ForceReport forceReport_103 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_103.copyProperties(forceReport_66);
    forceReport_103.setPresentationName("[SideF] - SU RR");
	forceReport_103.getDirection().setComponents(0.0, 1.0, 0.0);
    forceReport_103.getDirection().setUnits(units_1);

    ForceReport forceReport_104 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_104.copyProperties(forceReport_67);
    forceReport_104.setPresentationName("[SideF] - Total");
	forceReport_104.getDirection().setComponents(0.0, 1.0, 0.0);
    forceReport_104.getDirection().setUnits(units_1);

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_105 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
	ForceReport forceReport_68 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - UB(L)"));
    forceReport_105.copyProperties(forceReport_68);
    forceReport_105.setPresentationName("[SideF] - UB(L)");
	forceReport_105.getDirection().setComponents(0.0, 1.0, 0.0);
    forceReport_105.getDirection().setUnits(units_1);}

    ForceReport forceReport_106 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_106.copyProperties(forceReport_69);
    forceReport_106.setPresentationName("[SideF] - UB(R)");
	forceReport_106.getDirection().setComponents(0.0, 1.0, 0.0);
    forceReport_106.getDirection().setUnits(units_1);

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_107 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
	ForceReport forceReport_70 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - W FL"));
    forceReport_107.copyProperties(forceReport_70);
    forceReport_107.setPresentationName("[SideF] - W FL");
	forceReport_107.getDirection().setComponents(0.0, 1.0, 0.0);
    forceReport_107.getDirection().setUnits(units_1);}

    ForceReport forceReport_108 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_108.copyProperties(forceReport_71);
    forceReport_108.setPresentationName("[SideF] - W FR");
	forceReport_108.getDirection().setComponents(0.0, 1.0, 0.0);
    forceReport_108.getDirection().setUnits(units_1);

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_109 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
	ForceReport forceReport_72 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - W RL"));
    forceReport_109.copyProperties(forceReport_72);
    forceReport_109.setPresentationName("[SideF] - W RL");
	forceReport_109.getDirection().setComponents(0.0, 1.0, 0.0);
    forceReport_109.getDirection().setUnits(units_1);}

    ForceReport forceReport_110 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_110.copyProperties(forceReport_73);
    forceReport_110.setPresentationName("[SideF] - W RR");
	forceReport_110.getDirection().setComponents(0.0, 1.0, 0.0);
    forceReport_110.getDirection().setUnits(units_1);
	//}
	
	//{ // Groups Forces
	
	simulation_0.getReportManager().getGroupsManager().createGroup("[Forces]");
    ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().createGroup("[Monitor]");
    ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().getObject("[Monitor]")).getGroupsManager().createGroup("[DownF]");
    ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().getObject("[Monitor]")).getGroupsManager().createGroup("[Drag]");
    ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().getObject("[Monitor]")).getGroupsManager().createGroup("[SideF]");
	
	if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
	((ClientServerObjectGroup) ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().getObject("[Monitor]")).getGroupsManager().getObject("[DownF]")).getGroupsManager().groupObjects("[DownF]", new NeoObjectVector(new Object[] {forceReport_1, forceReport_32, forceReport_6, forceReport_8, forceReport_10, forceReport_11, forceReport_12, forceReport_13, forceReport_14, forceReport_15, forceReport_16, forceReport_17, forceReport_21, forceReport_19, forceReport_23, forceReport_25, forceReport_27, forceReport_29, forceReport_34, forceReport_36, forceReport_0, forceReport_31, forceReport_3, forceReport_5}), true);
    ((ClientServerObjectGroup) ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().getObject("[Monitor]")).getGroupsManager().getObject("[Drag]")).getGroupsManager().groupObjects("[Drag]", new NeoObjectVector(new Object[] {forceReport_37, forceReport_38, forceReport_39, forceReport_41, forceReport_43, forceReport_44, forceReport_45, forceReport_46, forceReport_47, forceReport_48, forceReport_49, forceReport_50, forceReport_51, forceReport_54, forceReport_56, forceReport_58, forceReport_60, forceReport_62, forceReport_64, forceReport_66, forceReport_67, forceReport_69, forceReport_71, forceReport_73}), true);
    ((ClientServerObjectGroup) ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().getObject("[Monitor]")).getGroupsManager().getObject("[SideF]")).getGroupsManager().groupObjects("[SideF]", new NeoObjectVector(new Object[] {forceReport_74, forceReport_75, forceReport_76, forceReport_78, forceReport_80, forceReport_81, forceReport_82, forceReport_83, forceReport_84, forceReport_85, forceReport_86, forceReport_87, forceReport_88, forceReport_91, forceReport_93, forceReport_95, forceReport_97, forceReport_99, forceReport_101, forceReport_103, forceReport_104, forceReport_106, forceReport_108, forceReport_110}), true);
	} else {
	ForceReport forceReport_7 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - FW - EP(L)"));
	ForceReport forceReport_9 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - FW - Flap(L)"));
	ForceReport forceReport_20 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - SC EP(L)"));
	ForceReport forceReport_18 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - SC(L)"));
	ForceReport forceReport_22 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - SC0 (L)"));
	ForceReport forceReport_24 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - SC1 (L)"));
	ForceReport forceReport_26 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - SC2 (L)"));
	ForceReport forceReport_28 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - SC3 (L)"));
	ForceReport forceReport_33 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - SU FL"));
	ForceReport forceReport_35 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - SU RL"));
	ForceReport forceReport_30 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - UB(L)"));
	ForceReport forceReport_2 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - W FL"));
	ForceReport forceReport_4 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - W RL"));
	ForceReport forceReport_40 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - FW - EP(L)"));
	ForceReport forceReport_42 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - FW - Flap(L)"));  
	ForceReport forceReport_52 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - SC EP(L)"));  
	ForceReport forceReport_53 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - SC(L)"));  
	ForceReport forceReport_55 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - SC0 (L)"));
	ForceReport forceReport_57 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - SC1 (L)"));
	ForceReport forceReport_59 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - SC2 (L)"));
	ForceReport forceReport_61 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - SC3 (L)"));
	ForceReport forceReport_63 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - SU FL"));
	ForceReport forceReport_65 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - SU RL"));
	ForceReport forceReport_68 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - UB(L)"));
	ForceReport forceReport_70 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - W FL"));
	ForceReport forceReport_72 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - W RL"));
	ForceReport forceReport_77 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[SideF] - FW - EP(L)"));
	ForceReport forceReport_79 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[SideF] - FW - Flap(L)"));  
	ForceReport forceReport_89 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[SideF] - SC EP(L)"));  
	ForceReport forceReport_90 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[SideF] - SC(L)"));  
	ForceReport forceReport_92 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[SideF] - SC0 (L)"));
	ForceReport forceReport_94 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[SideF] - SC1 (L)"));
	ForceReport forceReport_96 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[SideF] - SC2 (L)"));
	ForceReport forceReport_98 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[SideF] - SC3 (L)"));
	ForceReport forceReport_100 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[SideF] - SU FL"));
	ForceReport forceReport_102 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[SideF] - SU RL"));
	ForceReport forceReport_105 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[SideF] - UB(L)"));
	ForceReport forceReport_107 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[SideF] - W FL"));
	ForceReport forceReport_109 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[SideF] - W RL"));
	  
    ((ClientServerObjectGroup) ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().getObject("[Monitor]")).getGroupsManager().getObject("[DownF]")).getGroupsManager().groupObjects("[DownF]", new NeoObjectVector(new Object[] {forceReport_1, forceReport_32, forceReport_6, forceReport_7, forceReport_8, forceReport_9, forceReport_10, forceReport_11, forceReport_12, forceReport_13, forceReport_14, forceReport_15, forceReport_16, forceReport_17, forceReport_21, forceReport_20, forceReport_18, forceReport_19, forceReport_22, forceReport_23, forceReport_24, forceReport_25, forceReport_26, forceReport_27, forceReport_28, forceReport_29, forceReport_33, forceReport_34, forceReport_35, forceReport_36, forceReport_0, forceReport_30, forceReport_31, forceReport_2, forceReport_3, forceReport_4, forceReport_5}), true);
    ((ClientServerObjectGroup) ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().getObject("[Monitor]")).getGroupsManager().getObject("[Drag]")).getGroupsManager().groupObjects("[Drag]", new NeoObjectVector(new Object[] {forceReport_37, forceReport_38, forceReport_39, forceReport_40, forceReport_41, forceReport_42, forceReport_43, forceReport_44, forceReport_45, forceReport_46, forceReport_47, forceReport_48, forceReport_49, forceReport_50, forceReport_51, forceReport_52, forceReport_53, forceReport_54, forceReport_55, forceReport_56, forceReport_57, forceReport_58, forceReport_59, forceReport_60, forceReport_61, forceReport_62, forceReport_63, forceReport_64, forceReport_65, forceReport_66, forceReport_67, forceReport_68, forceReport_69, forceReport_70, forceReport_71, forceReport_72, forceReport_73}), true);
    ((ClientServerObjectGroup) ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().getObject("[Monitor]")).getGroupsManager().getObject("[SideF]")).getGroupsManager().groupObjects("[SideF]", new NeoObjectVector(new Object[] {forceReport_74, forceReport_75, forceReport_76, forceReport_77, forceReport_78, forceReport_79, forceReport_80, forceReport_81, forceReport_82, forceReport_83, forceReport_84, forceReport_85, forceReport_86, forceReport_87, forceReport_88, forceReport_89, forceReport_90, forceReport_91, forceReport_92, forceReport_93, forceReport_94, forceReport_95, forceReport_96, forceReport_97, forceReport_98, forceReport_99, forceReport_100, forceReport_101, forceReport_102, forceReport_103, forceReport_104, forceReport_105, forceReport_106, forceReport_107, forceReport_108, forceReport_109, forceReport_110}), true);
	}
	//}
	
	//{ // Moments
	MomentReport momentReport_0 = 
      simulation_0.getReportManager().createReport(MomentReport.class);
    momentReport_0.setCoordinateSystem(cartesianCoordinateSystem_0);
    momentReport_0.getDirection().setComponents(1.0, 0.0, 0.0);
    momentReport_0.getDirection().setUnits(units_1);
    momentReport_0.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, InterfaceBoundary.class), new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.DoesNotContain, "Domain"), new NamePredicate(NameOperator.DoesNotContain, "Fan"), new NamePredicate(NameOperator.DoesNotContain, "Radiator"))))), Query.STANDARD_MODIFIERS));
    momentReport_0.setPresentationName("[Mx] - CoG");
	momentReport_0.setRepresentation(fvRepresentation_0);
	
	MomentReport momentReport_1 = 
      simulation_0.getReportManager().createReport(MomentReport.class);
    momentReport_1.copyProperties(momentReport_0);
    momentReport_1.setPresentationName("[My] - CoG");
    momentReport_1.getDirection().setComponents(0.0, 1.0, 0.0);
    momentReport_1.getDirection().setUnits(units_1);

    MomentReport momentReport_2 = 
      simulation_0.getReportManager().createReport(MomentReport.class);
    momentReport_2.copyProperties(momentReport_1);
    momentReport_2.setPresentationName("[Mz] - CoG");
    momentReport_2.getDirection().setComponents(0.0, 0.0, 1.0);
    momentReport_2.getDirection().setUnits(units_1);

    MomentReport momentReport_3 = 
      simulation_0.getReportManager().createReport(MomentReport.class);
    momentReport_3.copyProperties(momentReport_0);
    momentReport_3.setPresentationName("[Mx] - Origin");
    momentReport_3.setCoordinateSystem(labCoordinateSystem_0);

    MomentReport momentReport_4 = 
      simulation_0.getReportManager().createReport(MomentReport.class);
    momentReport_4.copyProperties(momentReport_1);
    momentReport_4.setPresentationName("[My] - Origin");
    momentReport_4.setCoordinateSystem(labCoordinateSystem_0);

    MomentReport momentReport_5 = 
      simulation_0.getReportManager().createReport(MomentReport.class);
    momentReport_5.copyProperties(momentReport_2);
    momentReport_5.setPresentationName("[Mz] - Origin");
    momentReport_5.setCoordinateSystem(labCoordinateSystem_0);

    simulation_0.getReportManager().getGroupsManager().createGroup("[Moments]");

    ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Moments]")).getGroupsManager().createGroup("[Monitor]");
    ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Moments]")).getGroupsManager().getObject("[Monitor]")).getGroupsManager().groupObjects("[Monitor]", new NeoObjectVector(new Object[] {momentReport_0, momentReport_3, momentReport_1, momentReport_4, momentReport_2, momentReport_5}), true);
	//}
	}  
  
  private void Monitors() { 
	
	//{ // Last iter Monitors
	 
		//{ // Solver Monitors

    Simulation simulation_0 = 
      getActiveSimulation();
	  
	double Roll = simulation_0.getReportManager().getReport("Roll [deg]").getReportMonitorValue();
	double Yaw = simulation_0.getReportManager().getReport("Yaw [rad]").getReportMonitorValue();
	double CornerRadius = simulation_0.getReportManager().getReport("Corner Radius [m]").getReportMonitorValue(); 

    ResidualMonitor residualMonitor_0 = 
      ((ResidualMonitor) simulation_0.getMonitorManager().getMonitor("Tke"));
    residualMonitor_0.getNormalizeOption().setSelected(MonitorNormalizeOption.Type.OFF);

    ResidualMonitor residualMonitor_1 = 
      ((ResidualMonitor) simulation_0.getMonitorManager().getMonitor("X-momentum"));
    residualMonitor_1.getNormalizeOption().setSelected(MonitorNormalizeOption.Type.OFF);

    ResidualMonitor residualMonitor_2 = 
      ((ResidualMonitor) simulation_0.getMonitorManager().getMonitor("Sdr"));
    residualMonitor_2.getNormalizeOption().setSelected(MonitorNormalizeOption.Type.OFF);

    ResidualMonitor residualMonitor_3 = 
      ((ResidualMonitor) simulation_0.getMonitorManager().getMonitor("Y-momentum"));
    residualMonitor_3.getNormalizeOption().setSelected(MonitorNormalizeOption.Type.OFF);

    ResidualMonitor residualMonitor_4 = 
      ((ResidualMonitor) simulation_0.getMonitorManager().getMonitor("Z-momentum"));
    residualMonitor_4.getNormalizeOption().setSelected(MonitorNormalizeOption.Type.OFF);

    ResidualMonitor residualMonitor_5 = 
      ((ResidualMonitor) simulation_0.getMonitorManager().getMonitor("Continuity"));
    residualMonitor_5.getNormalizeOption().setSelected(MonitorNormalizeOption.Type.OFF);

    simulation_0.getMonitorManager().getGroupsManager().createGroup("[Residuals]");
    ((ClientServerObjectGroup) simulation_0.getMonitorManager().getGroupsManager().getObject("[Residuals]")).getGroupsManager().groupObjects("[Residuals]", new NeoObjectVector(new Object[] {residualMonitor_5, residualMonitor_2, residualMonitor_0, residualMonitor_1, residualMonitor_3, residualMonitor_4}), true);

    simulation_0.getMonitorManager().getGroupsManager().createGroup("[Time]");

    IterationMonitor iterationMonitor_0 = 
      ((IterationMonitor) simulation_0.getMonitorManager().getMonitor("Iteration"));

    PhysicalTimeMonitor physicalTimeMonitor_0 = 
      ((PhysicalTimeMonitor) simulation_0.getMonitorManager().getMonitor("Physical Time"));

    ((ClientServerObjectGroup) simulation_0.getMonitorManager().getGroupsManager().getObject("[Time]")).getGroupsManager().groupObjects("[Time]", new NeoObjectVector(new Object[] {iterationMonitor_0, physicalTimeMonitor_0}), true);
	//}
	
		//{ // CLoads Monitors

    CenterOfLoadsReport centerOfLoadsReport_0 = 
      ((CenterOfLoadsReport) simulation_0.getReportManager().getReport("[CLoadX]- CoG"));
    ReportMonitor reportMonitor_0 = 
      centerOfLoadsReport_0.createMonitor();

    CenterOfLoadsReport centerOfLoadsReport_1 = 
      ((CenterOfLoadsReport) simulation_0.getReportManager().getReport("[CLoadX]- Ground"));
    ReportMonitor reportMonitor_1 = 
      centerOfLoadsReport_1.createMonitor();

    CenterOfLoadsReport centerOfLoadsReport_2 = 
      ((CenterOfLoadsReport) simulation_0.getReportManager().getReport("[CLoadY]- CoG"));
    ReportMonitor reportMonitor_2 = 
      centerOfLoadsReport_2.createMonitor();

    CenterOfLoadsReport centerOfLoadsReport_3 = 
      ((CenterOfLoadsReport) simulation_0.getReportManager().getReport("[CLoadY]- Ground"));
    ReportMonitor reportMonitor_3 = 
      centerOfLoadsReport_3.createMonitor();

    CenterOfLoadsReport centerOfLoadsReport_4 = 
      ((CenterOfLoadsReport) simulation_0.getReportManager().getReport("[CLoadZ]- CoG"));
    ReportMonitor reportMonitor_4 = 
      centerOfLoadsReport_4.createMonitor();

    CenterOfLoadsReport centerOfLoadsReport_5 = 
      ((CenterOfLoadsReport) simulation_0.getReportManager().getReport("[CLoadZ]- Ground"));
    ReportMonitor reportMonitor_5 = 
      centerOfLoadsReport_5.createMonitor();

    reportMonitor_0.setPresentationName("[CLoadX]- CoG");
    reportMonitor_1.setPresentationName("[CLoadX]- Ground");
    reportMonitor_2.setPresentationName("[CLoadY]- CoG");
    reportMonitor_3.setPresentationName("[CLoadY]- Ground");
    reportMonitor_4.setPresentationName("[CLoadZ]- CoG");
    reportMonitor_5.setPresentationName("[CLoadZ]- Ground");
	//}
	
		//{ // Cooling Monitors

    simulation_0.getMonitorManager().getGroupsManager().createGroup("[CLoads]");
    ((ClientServerObjectGroup) simulation_0.getMonitorManager().getGroupsManager().getObject("[CLoads]")).getGroupsManager().createGroup("[Total]");
    ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getMonitorManager().getGroupsManager().getObject("[CLoads]")).getGroupsManager().getObject("[Total]")).getGroupsManager().groupObjects("[Total]", new NeoObjectVector(new Object[] {reportMonitor_0, reportMonitor_1, reportMonitor_2, reportMonitor_3, reportMonitor_4, reportMonitor_5}), true);

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    MassFlowReport massFlowReport_0 = 
      ((MassFlowReport) simulation_0.getReportManager().getReport("[Mass Flow] - Fan (L)"));
    ReportMonitor reportMonitor_6 = 
      massFlowReport_0.createMonitor();
	reportMonitor_6.setPresentationName("[Mass Flow] - Fan (L)");}
	  
    MassFlowReport massFlowReport_1 = 
      ((MassFlowReport) simulation_0.getReportManager().getReport("[Mass Flow] - Fan (R)"));
    ReportMonitor reportMonitor_7 = 
      massFlowReport_1.createMonitor();
    reportMonitor_7.setPresentationName("[Mass Flow] - Fan (R)");

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    MassFlowReport massFlowReport_2 = 
      ((MassFlowReport) simulation_0.getReportManager().getReport("[Mass Flow] - Radiator (L)"));
    ReportMonitor reportMonitor_8 = 
      massFlowReport_2.createMonitor();
    reportMonitor_8.setPresentationName("[Mass Flow] - Radiator (L)");}

    MassFlowReport massFlowReport_3 = 
      ((MassFlowReport) simulation_0.getReportManager().getReport("[Mass Flow] - Radiator (R)"));
    ReportMonitor reportMonitor_9 = 
      massFlowReport_3.createMonitor();
    reportMonitor_9.setPresentationName("[Mass Flow] - Radiator (R)");

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    PressureDropReport pressureDropReport_0 = 
      ((PressureDropReport) simulation_0.getReportManager().getReport("[PDrop] - Fan (L)"));
    ReportMonitor reportMonitor_10 = 
      pressureDropReport_0.createMonitor();
    reportMonitor_10.setPresentationName("[PDrop] - Fan (L)");}

    PressureDropReport pressureDropReport_1 = 
      ((PressureDropReport) simulation_0.getReportManager().getReport("[PDrop] - Fan (R)"));
    ReportMonitor reportMonitor_11 = 
      pressureDropReport_1.createMonitor();
    reportMonitor_11.setPresentationName("[PDrop] - Fan (R) ");

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    PressureDropReport pressureDropReport_2 = 
      ((PressureDropReport) simulation_0.getReportManager().getReport("[PDrop] - Radiator (L)"));
    ReportMonitor reportMonitor_12 = 
      pressureDropReport_2.createMonitor();
    reportMonitor_12.setPresentationName("[PDrop] - Radiator (L)");}

    PressureDropReport pressureDropReport_3 = 
      ((PressureDropReport) simulation_0.getReportManager().getReport("[PDrop] - Radiator (R)"));
    ReportMonitor reportMonitor_13 = 
      pressureDropReport_3.createMonitor();
    reportMonitor_13.setPresentationName("[PDrop] - Radiator (R)");

    simulation_0.getMonitorManager().getGroupsManager().createGroup("[Cooling]");
    ((ClientServerObjectGroup) simulation_0.getMonitorManager().getGroupsManager().getObject("[Cooling]")).getGroupsManager().createGroup("[Monitor]");
	if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
	((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getMonitorManager().getGroupsManager().getObject("[Cooling]")).getGroupsManager().getObject("[Monitor]")).getGroupsManager().groupObjects("[Monitor]", new NeoObjectVector(new Object[] {reportMonitor_7, reportMonitor_9, reportMonitor_11, reportMonitor_13}), true);
	} else {
	ReportMonitor reportMonitor_6 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Mass Flow] - Fan (L)"));
	ReportMonitor reportMonitor_8 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Mass Flow] - Radiator (L)"));
	ReportMonitor reportMonitor_10 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[PDrop] - Fan (L)"));
	ReportMonitor reportMonitor_12 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[PDrop] - Radiator (L)"));
    ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getMonitorManager().getGroupsManager().getObject("[Cooling]")).getGroupsManager().getObject("[Monitor]")).getGroupsManager().groupObjects("[Monitor]", new NeoObjectVector(new Object[] {reportMonitor_6, reportMonitor_7, reportMonitor_8, reportMonitor_9, reportMonitor_10, reportMonitor_11, reportMonitor_12, reportMonitor_13}), true);
	}
	//}
	
		//{ // DownForce Monitors

    ForceReport forceReport_0 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - BCKDiff"));
    ReportMonitor reportMonitor_14 = 
      forceReport_0.createMonitor();

    ForceReport forceReport_1 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - Driver | MH | HR"));
    ReportMonitor reportMonitor_15 = 
      forceReport_1.createMonitor();

    ForceReport forceReport_2 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - FW"));
    ReportMonitor reportMonitor_16 = 
      forceReport_2.createMonitor();

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_3 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - FW - EP(L)"));
    ReportMonitor reportMonitor_17 = 
      forceReport_3.createMonitor();}

    ForceReport forceReport_4 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - FW - EP(R)"));
    ReportMonitor reportMonitor_18 = 
      forceReport_4.createMonitor();

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_5 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - FW - Flap(L)"));
    ReportMonitor reportMonitor_19 = 
      forceReport_5.createMonitor();}

    ForceReport forceReport_6 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - FW - Flap(R)"));
    ReportMonitor reportMonitor_20 = 
      forceReport_6.createMonitor();

    ForceReport forceReport_7 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - FW - Main"));
    ReportMonitor reportMonitor_21 = 
      forceReport_7.createMonitor();

    ForceReport forceReport_8 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - Mono"));
    ReportMonitor reportMonitor_22 = 
      forceReport_8.createMonitor();

    ForceReport forceReport_9 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - RW"));
    ReportMonitor reportMonitor_23 = 
      forceReport_9.createMonitor();

    ForceReport forceReport_10 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - RW - EP"));
    ReportMonitor reportMonitor_24 = 
      forceReport_10.createMonitor();

    ForceReport forceReport_11 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - RW - Flap1"));
    ReportMonitor reportMonitor_25 = 
      forceReport_11.createMonitor();

    ForceReport forceReport_12 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - RW - Flap2"));
    ReportMonitor reportMonitor_26 = 
      forceReport_12.createMonitor();

    ForceReport forceReport_13 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - RW - Main"));
    ReportMonitor reportMonitor_27 = 
      forceReport_13.createMonitor();

    ForceReport forceReport_14 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - SC EP (R)"));
    ReportMonitor reportMonitor_28 = 
      forceReport_14.createMonitor();

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_15 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - SC EP(L)"));
    ReportMonitor reportMonitor_29 = 
      forceReport_15.createMonitor();

    ForceReport forceReport_16 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - SC(L)"));
    ReportMonitor reportMonitor_30 = 
      forceReport_16.createMonitor();}

    ForceReport forceReport_17 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - SC(R)"));
    ReportMonitor reportMonitor_31 = 
      forceReport_17.createMonitor();

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_18 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - SC0 (L)"));
    ReportMonitor reportMonitor_32 = 
      forceReport_18.createMonitor();}

    ForceReport forceReport_19 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - SC0 (R)"));
    ReportMonitor reportMonitor_33 = 
      forceReport_19.createMonitor();

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_20 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - SC1 (L)"));
    ReportMonitor reportMonitor_34 = 
      forceReport_20.createMonitor();}

    ForceReport forceReport_21 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - SC1 (R)"));
    ReportMonitor reportMonitor_35 = 
      forceReport_21.createMonitor();

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_22 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - SC2 (L)"));
    ReportMonitor reportMonitor_36 = 
      forceReport_22.createMonitor();}

    ForceReport forceReport_23 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - SC2 (R)"));
    ReportMonitor reportMonitor_37 = 
      forceReport_23.createMonitor();

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_24 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - SC3 (L)"));
    ReportMonitor reportMonitor_38 = 
      forceReport_24.createMonitor();}

    ForceReport forceReport_25 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - SC3 (R)"));
    ReportMonitor reportMonitor_39 = 
      forceReport_25.createMonitor();

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_26 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - SU FL"));
    ReportMonitor reportMonitor_40 = 
      forceReport_26.createMonitor();}

    ForceReport forceReport_27 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - SU FR"));
    ReportMonitor reportMonitor_41 = 
      forceReport_27.createMonitor();

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_28 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - SU RL"));
    ReportMonitor reportMonitor_42 = 
      forceReport_28.createMonitor();}

    ForceReport forceReport_29 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - SU RR"));
    ReportMonitor reportMonitor_43 = 
      forceReport_29.createMonitor();

    ForceReport forceReport_30 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - Total"));
    ReportMonitor reportMonitor_44 = 
      forceReport_30.createMonitor();

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_31 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - UB(L)"));
    ReportMonitor reportMonitor_45 = 
      forceReport_31.createMonitor();}

    ForceReport forceReport_32 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - UB(R)"));
    ReportMonitor reportMonitor_46 = 
      forceReport_32.createMonitor();

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_33 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - W FL"));
    ReportMonitor reportMonitor_47 = 
      forceReport_33.createMonitor();}

    ForceReport forceReport_34 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - W FR"));
    ReportMonitor reportMonitor_48 = 
      forceReport_34.createMonitor();

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_35 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - W RL"));
    ReportMonitor reportMonitor_49 = 
      forceReport_35.createMonitor();}

    ForceReport forceReport_36 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - W RR"));
    ReportMonitor reportMonitor_50 = 
      forceReport_36.createMonitor();

    simulation_0.getMonitorManager().getGroupsManager().createGroup("[Forces]");

    ((ClientServerObjectGroup) simulation_0.getMonitorManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().createGroup("[DownF]");
    ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getMonitorManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().getObject("[DownF]")).getGroupsManager().createGroup("[Monitor]");
    
	if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
	((ClientServerObjectGroup) ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getMonitorManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().getObject("[DownF]")).getGroupsManager().getObject("[Monitor]")).getGroupsManager().groupObjects("[Monitor]", new NeoObjectVector(new Object[] {reportMonitor_14, reportMonitor_15, reportMonitor_18, reportMonitor_20, reportMonitor_21, reportMonitor_16, reportMonitor_22, reportMonitor_24, reportMonitor_25, reportMonitor_26, reportMonitor_27, reportMonitor_23, reportMonitor_28, reportMonitor_31, reportMonitor_33, reportMonitor_35, reportMonitor_37, reportMonitor_39, reportMonitor_41, reportMonitor_43, reportMonitor_44, reportMonitor_46, reportMonitor_48, reportMonitor_50}), true);
	} else {	
		
	
	ReportMonitor reportMonitor_17 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[DownF] - FW - EP(L) Monitor"));	
	ReportMonitor reportMonitor_19 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[DownF] - FW - Flap(L) Monitor"));
	ReportMonitor reportMonitor_29 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[DownF] - SC EP(L) Monitor"));
	ReportMonitor reportMonitor_30 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[DownF] - SC(L) Monitor"));
	ReportMonitor reportMonitor_32 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[DownF] - SC0 (L) Monitor"));
	ReportMonitor reportMonitor_34 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[DownF] - SC1 (L) Monitor"));
	ReportMonitor reportMonitor_36 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[DownF] - SC2 (L) Monitor"));
	ReportMonitor reportMonitor_38 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[DownF] - SC3 (L) Monitor"));
	ReportMonitor reportMonitor_40 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[DownF] - SU FL Monitor"));
	ReportMonitor reportMonitor_42 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[DownF] - SU RL Monitor"));
	ReportMonitor reportMonitor_45 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[DownF] - UB(L) Monitor"));
	ReportMonitor reportMonitor_47 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[DownF] - W FL Monitor"));
	ReportMonitor reportMonitor_49 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[DownF] - W RL Monitor"));
	
    ((ClientServerObjectGroup) ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getMonitorManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().getObject("[DownF]")).getGroupsManager().getObject("[Monitor]")).getGroupsManager().groupObjects("[Monitor]", new NeoObjectVector(new Object[] {reportMonitor_14, reportMonitor_15, reportMonitor_17, reportMonitor_18, reportMonitor_19, reportMonitor_20, reportMonitor_21, reportMonitor_16, reportMonitor_22, reportMonitor_24, reportMonitor_25, reportMonitor_26, reportMonitor_27, reportMonitor_23, reportMonitor_28, reportMonitor_29, reportMonitor_30, reportMonitor_31, reportMonitor_32, reportMonitor_33, reportMonitor_34, reportMonitor_35, reportMonitor_36, reportMonitor_37, reportMonitor_38, reportMonitor_39, reportMonitor_40, reportMonitor_41, reportMonitor_42, reportMonitor_43, reportMonitor_44, reportMonitor_45, reportMonitor_46, reportMonitor_47, reportMonitor_48, reportMonitor_49, reportMonitor_50}), true);
	}
	
	
	
	
	
	
	
	
	//}
		
		//{ // Drag Monitors

    ForceReport forceReport_37 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - BCKDiff"));
    ReportMonitor reportMonitor_51 = 
      forceReport_37.createMonitor();

    ForceReport forceReport_38 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - Driver | MH | HR"));
    ReportMonitor reportMonitor_52 = 
      forceReport_38.createMonitor();

    ForceReport forceReport_39 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - FW"));
    ReportMonitor reportMonitor_53 = 
      forceReport_39.createMonitor();

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_40 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - FW - EP(L)"));
    ReportMonitor reportMonitor_54 = 
      forceReport_40.createMonitor();}

    ForceReport forceReport_41 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - FW - EP(R)"));
    ReportMonitor reportMonitor_55 = 
      forceReport_41.createMonitor();

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_42 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - FW - Flap(L)"));
    ReportMonitor reportMonitor_56 = 
      forceReport_42.createMonitor();}

    ForceReport forceReport_43 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - FW - Flap(R)"));
    ReportMonitor reportMonitor_57 = 
      forceReport_43.createMonitor();

    ForceReport forceReport_44 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - FW - Main"));
    ReportMonitor reportMonitor_58 = 
      forceReport_44.createMonitor();

    ForceReport forceReport_45 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - Mono"));
    ReportMonitor reportMonitor_59 = 
      forceReport_45.createMonitor();

    ForceReport forceReport_46 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - RW"));
    ReportMonitor reportMonitor_60 = 
      forceReport_46.createMonitor();

    ForceReport forceReport_47 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - RW - EP"));
    ReportMonitor reportMonitor_61 = 
      forceReport_47.createMonitor();

    ForceReport forceReport_48 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - RW - Flap1"));
    ReportMonitor reportMonitor_62 = 
      forceReport_48.createMonitor();

    ForceReport forceReport_49 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - RW - Flap2"));
    ReportMonitor reportMonitor_63 = 
      forceReport_49.createMonitor();

    ForceReport forceReport_50 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - RW - Main"));
    ReportMonitor reportMonitor_64 = 
      forceReport_50.createMonitor();

    ForceReport forceReport_51 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - SC EP (R)"));
    ReportMonitor reportMonitor_65 = 
      forceReport_51.createMonitor();

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_52 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - SC EP(L)"));
    ReportMonitor reportMonitor_66 = 
      forceReport_52.createMonitor();

    ForceReport forceReport_53 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - SC(L)"));
    ReportMonitor reportMonitor_67 = 
      forceReport_53.createMonitor();}

    ForceReport forceReport_54 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - SC(R)"));
    ReportMonitor reportMonitor_68 = 
      forceReport_54.createMonitor();

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_55 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - SC0 (L)"));
    ReportMonitor reportMonitor_69 = 
      forceReport_55.createMonitor();}

    ForceReport forceReport_56 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - SC0 (R)"));
    ReportMonitor reportMonitor_70 = 
      forceReport_56.createMonitor();

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_57 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - SC1 (L)"));
    ReportMonitor reportMonitor_71 = 
      forceReport_57.createMonitor();}

    ForceReport forceReport_58 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - SC1 (R)"));
    ReportMonitor reportMonitor_72 = 
      forceReport_58.createMonitor();

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_59 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - SC2 (L)"));
    ReportMonitor reportMonitor_73 = 
      forceReport_59.createMonitor();}

    ForceReport forceReport_60 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - SC2 (R)"));
    ReportMonitor reportMonitor_74 = 
      forceReport_60.createMonitor();

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_61 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - SC3 (L)"));
    ReportMonitor reportMonitor_75 = 
      forceReport_61.createMonitor();}

    ForceReport forceReport_62 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - SC3 (R)"));
    ReportMonitor reportMonitor_76 = 
      forceReport_62.createMonitor();

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_63 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - SU FL"));
    ReportMonitor reportMonitor_77 = 
      forceReport_63.createMonitor();}

    ForceReport forceReport_64 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - SU FR"));
    ReportMonitor reportMonitor_78 = 
      forceReport_64.createMonitor();

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_65 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - SU RL"));
    ReportMonitor reportMonitor_79 = 
      forceReport_65.createMonitor();}

    ForceReport forceReport_66 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - SU RR"));
    ReportMonitor reportMonitor_80 = 
      forceReport_66.createMonitor();

    ForceReport forceReport_67 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - Total"));
    ReportMonitor reportMonitor_81 = 
      forceReport_67.createMonitor();

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_68 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - UB(L)"));
    ReportMonitor reportMonitor_82 = 
      forceReport_68.createMonitor();}

    ForceReport forceReport_69 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - UB(R)"));
    ReportMonitor reportMonitor_83 = 
      forceReport_69.createMonitor();

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_70 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - W FL"));
    ReportMonitor reportMonitor_84 = 
      forceReport_70.createMonitor();}

    ForceReport forceReport_71 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - W FR"));
    ReportMonitor reportMonitor_85 = 
      forceReport_71.createMonitor();

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_72 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - W RL"));
    ReportMonitor reportMonitor_86 = 
      forceReport_72.createMonitor();}

    ForceReport forceReport_73 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - W RR"));
    ReportMonitor reportMonitor_87 = 
      forceReport_73.createMonitor();

    ((ClientServerObjectGroup) simulation_0.getMonitorManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().createGroup("[Drag]");
    ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getMonitorManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().getObject("[Drag]")).getGroupsManager().createGroup("[Monitor]");
	
	if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
	((ClientServerObjectGroup) ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getMonitorManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().getObject("[Drag]")).getGroupsManager().getObject("[Monitor]")).getGroupsManager().groupObjects("[Monitor]", new NeoObjectVector(new Object[] {reportMonitor_51, reportMonitor_52, reportMonitor_55, reportMonitor_57, reportMonitor_58, reportMonitor_53, reportMonitor_59, reportMonitor_61, reportMonitor_62, reportMonitor_63, reportMonitor_64, reportMonitor_60, reportMonitor_65, reportMonitor_68, reportMonitor_70, reportMonitor_72, reportMonitor_74, reportMonitor_76, reportMonitor_78, reportMonitor_80, reportMonitor_81, reportMonitor_83, reportMonitor_85, reportMonitor_87}), true);
	} else {
	
	ReportMonitor reportMonitor_54 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Drag] - FW - EP(L) Monitor"));	
	ReportMonitor reportMonitor_56 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Drag] - FW - Flap(L) Monitor"));
	ReportMonitor reportMonitor_66 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Drag] - SC EP(L) Monitor"));
	ReportMonitor reportMonitor_67 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Drag] - SC(L) Monitor"));
	ReportMonitor reportMonitor_69 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Drag] - SC0 (L) Monitor"));
	ReportMonitor reportMonitor_71 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Drag] - SC1 (L) Monitor"));
	ReportMonitor reportMonitor_73 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Drag] - SC2 (L) Monitor"));
	ReportMonitor reportMonitor_75 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Drag] - SC3 (L) Monitor"));
	ReportMonitor reportMonitor_77 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Drag] - SU FL Monitor"));
	ReportMonitor reportMonitor_79 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Drag] - SU RL Monitor"));
	ReportMonitor reportMonitor_82 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Drag] - UB(L) Monitor"));
	ReportMonitor reportMonitor_84 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Drag] - W FL Monitor"));
	ReportMonitor reportMonitor_86 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Drag] - W RL Monitor"));
	

    ((ClientServerObjectGroup) ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getMonitorManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().getObject("[Drag]")).getGroupsManager().getObject("[Monitor]")).getGroupsManager().groupObjects("[Monitor]", new NeoObjectVector(new Object[] {reportMonitor_51, reportMonitor_52, reportMonitor_54, reportMonitor_55, reportMonitor_56, reportMonitor_57, reportMonitor_58, reportMonitor_53, reportMonitor_59, reportMonitor_61, reportMonitor_62, reportMonitor_63, reportMonitor_64, reportMonitor_60, reportMonitor_65, reportMonitor_66, reportMonitor_67, reportMonitor_68, reportMonitor_69, reportMonitor_70, reportMonitor_71, reportMonitor_72, reportMonitor_73, reportMonitor_74, reportMonitor_75, reportMonitor_76, reportMonitor_77, reportMonitor_78, reportMonitor_79, reportMonitor_80, reportMonitor_81, reportMonitor_82, reportMonitor_83, reportMonitor_84, reportMonitor_85, reportMonitor_86, reportMonitor_87}), true);
	}
	//}
	
		//{ // SideForce Monitors
	
    ForceReport forceReport_74 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[SideF] - BCKDiff"));
    ReportMonitor reportMonitor_88 = 
      forceReport_74.createMonitor();

    ForceReport forceReport_75 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[SideF] - Driver | MH | HR"));
    ReportMonitor reportMonitor_89 = 
      forceReport_75.createMonitor();

    ForceReport forceReport_76 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[SideF] - FW"));
    ReportMonitor reportMonitor_90 = 
      forceReport_76.createMonitor();

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_77 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[SideF] - FW - EP(L)"));
    ReportMonitor reportMonitor_91 = 
      forceReport_77.createMonitor();}

    ForceReport forceReport_78 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[SideF] - FW - EP(R)"));
    ReportMonitor reportMonitor_92 = 
      forceReport_78.createMonitor();

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_79 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[SideF] - FW - Flap(L)"));
    ReportMonitor reportMonitor_93 = 
      forceReport_79.createMonitor();}

    ForceReport forceReport_80 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[SideF] - FW - Flap(R)"));
    ReportMonitor reportMonitor_94 = 
      forceReport_80.createMonitor();

    ForceReport forceReport_81 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[SideF] - FW - Main"));
    ReportMonitor reportMonitor_95 = 
      forceReport_81.createMonitor();
  
    ForceReport forceReport_82 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[SideF] - Mono"));
    ReportMonitor reportMonitor_96 = 
      forceReport_82.createMonitor();

    ForceReport forceReport_83 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[SideF] - RW"));
    ReportMonitor reportMonitor_97 = 
      forceReport_83.createMonitor();

    ForceReport forceReport_84 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[SideF] - RW - EP"));
    ReportMonitor reportMonitor_98 = 
      forceReport_84.createMonitor();

    ForceReport forceReport_85 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[SideF] - RW - Flap1"));
    ReportMonitor reportMonitor_99 = 
      forceReport_85.createMonitor();

    ForceReport forceReport_86 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[SideF] - RW - Flap2"));
    ReportMonitor reportMonitor_100 = 
      forceReport_86.createMonitor();

    ForceReport forceReport_87 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[SideF] - RW - Main"));
    ReportMonitor reportMonitor_101 = 
      forceReport_87.createMonitor();

    ForceReport forceReport_88 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[SideF] - SC EP (R)"));
    ReportMonitor reportMonitor_102 = 
      forceReport_88.createMonitor();

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_89 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[SideF] - SC EP(L)"));
    ReportMonitor reportMonitor_103 = 
      forceReport_89.createMonitor();

    ForceReport forceReport_90 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[SideF] - SC(L)"));
    ReportMonitor reportMonitor_104 = 
      forceReport_90.createMonitor();}

    ForceReport forceReport_91 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[SideF] - SC(R)"));
    ReportMonitor reportMonitor_105 = 
      forceReport_91.createMonitor();

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_92 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[SideF] - SC0 (L)"));
    ReportMonitor reportMonitor_106 = 
      forceReport_92.createMonitor();}

    ForceReport forceReport_93 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[SideF] - SC0 (R)"));
    ReportMonitor reportMonitor_107 = 
      forceReport_93.createMonitor();

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_94 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[SideF] - SC1 (L)"));
    ReportMonitor reportMonitor_108 = 
      forceReport_94.createMonitor();}

    ForceReport forceReport_95 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[SideF] - SC1 (R)"));
    ReportMonitor reportMonitor_109 = 
      forceReport_95.createMonitor();

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_96 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[SideF] - SC2 (L)"));
    ReportMonitor reportMonitor_110 = 
      forceReport_96.createMonitor();}

    ForceReport forceReport_97 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[SideF] - SC2 (R)"));
    ReportMonitor reportMonitor_111 = 
      forceReport_97.createMonitor();

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_98 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[SideF] - SC3 (L)"));
    ReportMonitor reportMonitor_112 = 
      forceReport_98.createMonitor();}

    ForceReport forceReport_99 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[SideF] - SC3 (R)"));
    ReportMonitor reportMonitor_113 = 
      forceReport_99.createMonitor();

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_100 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[SideF] - SU FL"));
    ReportMonitor reportMonitor_114 = 
      forceReport_100.createMonitor();}

    ForceReport forceReport_101 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[SideF] - SU FR"));
    ReportMonitor reportMonitor_115 = 
      forceReport_101.createMonitor();

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_102 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[SideF] - SU RL"));
    ReportMonitor reportMonitor_116 = 
      forceReport_102.createMonitor();}

    ForceReport forceReport_103 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[SideF] - SU RR"));
    ReportMonitor reportMonitor_117 = 
      forceReport_103.createMonitor();

    ForceReport forceReport_104 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[SideF] - Total"));
    ReportMonitor reportMonitor_118 = 
      forceReport_104.createMonitor();

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_105 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[SideF] - UB(L)"));
    ReportMonitor reportMonitor_119 = 
      forceReport_105.createMonitor();}

    ForceReport forceReport_106 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[SideF] - UB(R)"));
    ReportMonitor reportMonitor_120 = 
      forceReport_106.createMonitor();

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_107 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[SideF] - W FL"));
    ReportMonitor reportMonitor_121 = 
      forceReport_107.createMonitor();}

    ForceReport forceReport_108 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[SideF] - W FR"));
    ReportMonitor reportMonitor_122 = 
      forceReport_108.createMonitor();

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_109 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[SideF] - W RL"));
    ReportMonitor reportMonitor_123 = 
      forceReport_109.createMonitor();}

    ForceReport forceReport_110 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[SideF] - W RR"));
    ReportMonitor reportMonitor_124 = 
      forceReport_110.createMonitor();

    ((ClientServerObjectGroup) simulation_0.getMonitorManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().createGroup("[SideF]");
    ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getMonitorManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().getObject("[SideF]")).getGroupsManager().createGroup("[Monitor]");
    
	if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
	((ClientServerObjectGroup) ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getMonitorManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().getObject("[SideF]")).getGroupsManager().getObject("[Monitor]")).getGroupsManager().groupObjects("[Monitor]", new NeoObjectVector(new Object[] {reportMonitor_88, reportMonitor_89, reportMonitor_92, reportMonitor_94, reportMonitor_95, reportMonitor_90, reportMonitor_96, reportMonitor_98, reportMonitor_99, reportMonitor_100, reportMonitor_101, reportMonitor_97, reportMonitor_102, reportMonitor_105, reportMonitor_107, reportMonitor_109, reportMonitor_111, reportMonitor_113, reportMonitor_115, reportMonitor_117, reportMonitor_118, reportMonitor_120, reportMonitor_122, reportMonitor_124}), true);	
	} else {
		
	ReportMonitor reportMonitor_91 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[SideF] - FW - EP(L) Monitor"));	
	ReportMonitor reportMonitor_93 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[SideF] - FW - Flap(L) Monitor"));
	ReportMonitor reportMonitor_103 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[SideF] - SC EP(L) Monitor"));
	ReportMonitor reportMonitor_104 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[SideF] - SC(L) Monitor"));
	ReportMonitor reportMonitor_106 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[SideF] - SC0 (L) Monitor"));
	ReportMonitor reportMonitor_108 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[SideF] - SC1 (L) Monitor"));
	ReportMonitor reportMonitor_110 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[SideF] - SC2 (L) Monitor"));
	ReportMonitor reportMonitor_112 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[SideF] - SC3 (L) Monitor"));
	ReportMonitor reportMonitor_114 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[SideF] - SU FL Monitor"));
	ReportMonitor reportMonitor_116 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[SideF] - SU RL Monitor"));
	ReportMonitor reportMonitor_119 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[SideF] - UB(L) Monitor"));
	ReportMonitor reportMonitor_121 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[SideF] - W FL Monitor"));
	ReportMonitor reportMonitor_123 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[SideF] - W RL Monitor"));	
		
	((ClientServerObjectGroup) ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getMonitorManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().getObject("[SideF]")).getGroupsManager().getObject("[Monitor]")).getGroupsManager().groupObjects("[Monitor]", new NeoObjectVector(new Object[] {reportMonitor_88, reportMonitor_89, reportMonitor_91, reportMonitor_92, reportMonitor_93, reportMonitor_94, reportMonitor_95, reportMonitor_90, reportMonitor_96, reportMonitor_98, reportMonitor_99, reportMonitor_100, reportMonitor_101, reportMonitor_97, reportMonitor_102, reportMonitor_103, reportMonitor_104, reportMonitor_105, reportMonitor_106, reportMonitor_107, reportMonitor_108, reportMonitor_109, reportMonitor_110, reportMonitor_111, reportMonitor_112, reportMonitor_113, reportMonitor_114, reportMonitor_115, reportMonitor_116, reportMonitor_117, reportMonitor_118, reportMonitor_119, reportMonitor_120, reportMonitor_121, reportMonitor_122, reportMonitor_123, reportMonitor_124}), true);
	}
	//}
	
		//{ // Moments Monitors
	
    MomentReport momentReport_0 = 
      ((MomentReport) simulation_0.getReportManager().getReport("[Mx] - CoG"));
    ReportMonitor reportMonitor_125 = 
      momentReport_0.createMonitor();

    MomentReport momentReport_1 = 
      ((MomentReport) simulation_0.getReportManager().getReport("[Mx] - Origin"));
    ReportMonitor reportMonitor_126 = 
      momentReport_1.createMonitor();

    MomentReport momentReport_2 = 
      ((MomentReport) simulation_0.getReportManager().getReport("[My] - CoG"));
    ReportMonitor reportMonitor_127 = 
      momentReport_2.createMonitor();

    MomentReport momentReport_3 = 
      ((MomentReport) simulation_0.getReportManager().getReport("[My] - Origin"));
    ReportMonitor reportMonitor_128 = 
      momentReport_3.createMonitor();

    MomentReport momentReport_4 = 
      ((MomentReport) simulation_0.getReportManager().getReport("[Mz] - CoG"));
    ReportMonitor reportMonitor_129 = 
      momentReport_4.createMonitor();

    MomentReport momentReport_5 = 
      ((MomentReport) simulation_0.getReportManager().getReport("[Mz] - Origin"));
    ReportMonitor reportMonitor_130 = 
      momentReport_5.createMonitor();

    simulation_0.getMonitorManager().getGroupsManager().createGroup("[Moments]");

    ((ClientServerObjectGroup) simulation_0.getMonitorManager().getGroupsManager().getObject("[Moments]")).getGroupsManager().createGroup("[Monitor]");
    ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getMonitorManager().getGroupsManager().getObject("[Moments]")).getGroupsManager().getObject("[Monitor]")).getGroupsManager().groupObjects("[Monitor]", new NeoObjectVector(new Object[] {reportMonitor_125, reportMonitor_126, reportMonitor_127, reportMonitor_128, reportMonitor_129, reportMonitor_130}), true);
	//}
	//}
	
	//{ // Mean Monitors	
	
		//{ // CLoads Mean Monitors
	
    StatisticsReport statisticsReport_0 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_0.setPresentationName("[CLoadX]- CoG Mean");
    statisticsReport_0.setSampleFilterOption(SampleFilterOption.LastNSamples);
	statisticsReport_0.setMonitor(reportMonitor_0);

    LastNSamplesFilter lastNSamplesFilter_0 = 
      ((LastNSamplesFilter) statisticsReport_0.getSampleFilterManager().getObject("Last N Samples"));
    lastNSamplesFilter_0.setNSamples(250);

    StatisticsReport statisticsReport_1 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_1.copyProperties(statisticsReport_0);
    statisticsReport_1.setPresentationName("[CLoadY]- CoG Mean");
	statisticsReport_1.setMonitor(reportMonitor_2);

    StatisticsReport statisticsReport_2 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_2.copyProperties(statisticsReport_1);
    statisticsReport_2.setPresentationName("[CLoadZ]- CoG Mean");
    statisticsReport_2.setMonitor(reportMonitor_4);

    StatisticsReport statisticsReport_3 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_3.copyProperties(statisticsReport_0);
    statisticsReport_3.setPresentationName("[CLoadX]- Ground Mean");
    statisticsReport_3.setMonitor(reportMonitor_1);

    StatisticsReport statisticsReport_4 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_4.copyProperties(statisticsReport_3);
    statisticsReport_4.setPresentationName("[CLoadY]- Ground Mean");
    statisticsReport_4.setMonitor(reportMonitor_3);

    StatisticsReport statisticsReport_5 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_5.copyProperties(statisticsReport_4);
    statisticsReport_5.setPresentationName("[CLoadZ]- Ground Mean");
    statisticsReport_5.setMonitor(reportMonitor_5);

    ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[CLoads]")).getGroupsManager().createGroup("[Avg.]");
    ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[CLoads]")).getGroupsManager().getObject("[Avg.]")).getGroupsManager().groupObjects("[Avg.]", new NeoObjectVector(new Object[] {statisticsReport_0, statisticsReport_3, statisticsReport_1, statisticsReport_4, statisticsReport_2, statisticsReport_5}), true);
	//}
	
		//{ // Cooling Mean Monitors

    StatisticsReport statisticsReport_6 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_6.setSampleFilterOption(SampleFilterOption.LastNSamples);
    statisticsReport_6.setPresentationName("[MFlow] - Fan (R) Mean");
    statisticsReport_6.setMonitor(reportMonitor_7);

    LastNSamplesFilter lastNSamplesFilter_1 = 
      ((LastNSamplesFilter) statisticsReport_6.getSampleFilterManager().getObject("Last N Samples"));
    lastNSamplesFilter_1.setNSamples(250);

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    StatisticsReport statisticsReport_7 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
	ReportMonitor reportMonitor_6 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Mass Flow] - Fan (L)"));
    statisticsReport_7.copyProperties(statisticsReport_6);
    statisticsReport_7.setPresentationName("[MFlow] - Fan (L) Mean");
    statisticsReport_7.setMonitor(reportMonitor_6);}

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    StatisticsReport statisticsReport_8 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
	ReportMonitor reportMonitor_8 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Mass Flow] - Radiator (L)"));
    statisticsReport_8.copyProperties(statisticsReport_6);
    statisticsReport_8.setPresentationName("[MFlow] - Radiator (L) Mean");
    statisticsReport_8.setMonitor(reportMonitor_8);}

    StatisticsReport statisticsReport_9 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_9.copyProperties(statisticsReport_6);
    statisticsReport_9.setPresentationName("[MFlow] - Radiator (R) Mean");
    statisticsReport_9.setMonitor(reportMonitor_9);

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    StatisticsReport statisticsReport_10 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
	ReportMonitor reportMonitor_10 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[PDrop] - Fan (L)"));
    statisticsReport_10.copyProperties(statisticsReport_6);
    statisticsReport_10.setPresentationName("[PDrop] - Fan (L) Mean");
    statisticsReport_10.setMonitor(reportMonitor_10);}

    StatisticsReport statisticsReport_11 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_11.copyProperties(statisticsReport_6);
    statisticsReport_11.setPresentationName("[PDrop] - Fan (R) Mean");
    statisticsReport_11.setMonitor(reportMonitor_11);

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    StatisticsReport statisticsReport_12 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
	ReportMonitor reportMonitor_12 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[PDrop] - Radiator (L)"));
    statisticsReport_12.copyProperties(statisticsReport_6);
    statisticsReport_12.setPresentationName("[PDrop] - Radiator (L) Mean");
    statisticsReport_12.setMonitor(reportMonitor_12);}

    StatisticsReport statisticsReport_13 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_13.copyProperties(statisticsReport_6);
    statisticsReport_13.setPresentationName("[PDrop] - Radiator (R) Mean");
    statisticsReport_13.setMonitor(reportMonitor_13);
	
    ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Cooling]")).getGroupsManager().createGroup("[Avg.]");
	if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
	((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Cooling]")).getGroupsManager().getObject("[Avg.]")).getGroupsManager().groupObjects("[Avg.]", new NeoObjectVector(new Object[] {statisticsReport_6, statisticsReport_9, statisticsReport_11, statisticsReport_13}), true);
	} else {
	StatisticsReport statisticsReport_7 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[MFlow] - Fan (L) Mean"));	
	StatisticsReport statisticsReport_8 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[MFlow] - Radiator (L) Mean"));
	StatisticsReport statisticsReport_10 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[PDrop] - Fan (L) Mean"));
	StatisticsReport statisticsReport_12 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[PDrop] - Radiator (L) Mean"));
	  
    ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Cooling]")).getGroupsManager().getObject("[Avg.]")).getGroupsManager().groupObjects("[Avg.]", new NeoObjectVector(new Object[] {statisticsReport_6, statisticsReport_7, statisticsReport_8, statisticsReport_9, statisticsReport_10, statisticsReport_11, statisticsReport_12, statisticsReport_13}), true);
	}
	//}
	
		//{ // DownForce Mean Monitors

    StatisticsReport statisticsReport_14 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_14.setPresentationName("[DownF] - BCKDiff Mean");
    statisticsReport_14.setSampleFilterOption(SampleFilterOption.LastNSamples);

    LastNSamplesFilter lastNSamplesFilter_2 = 
      ((LastNSamplesFilter) statisticsReport_14.getSampleFilterManager().getObject("Last N Samples"));
    lastNSamplesFilter_2.setNSamples(250);

    statisticsReport_14.setMonitor(reportMonitor_14);

    StatisticsReport statisticsReport_15 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_15.copyProperties(statisticsReport_14);
    statisticsReport_15.setPresentationName("[DownF] - FW Mean");
    statisticsReport_15.setMonitor(reportMonitor_16);

    StatisticsReport statisticsReport_16 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_16.copyProperties(statisticsReport_15);
    statisticsReport_16.setPresentationName("[DownF] - Mono Mean");
    statisticsReport_16.setMonitor(reportMonitor_22);

    StatisticsReport statisticsReport_17 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_17.copyProperties(statisticsReport_15);
    statisticsReport_17.setPresentationName("[DownF] - RW Mean");
    statisticsReport_17.setMonitor(reportMonitor_23);

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    StatisticsReport statisticsReport_18 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
	ReportMonitor reportMonitor_30 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[DownF] - SC(L) Monitor"));
    statisticsReport_18.copyProperties(statisticsReport_15);
    statisticsReport_18.setPresentationName("[DownF] - SC(L) Mean");
    statisticsReport_18.setMonitor(reportMonitor_30);}

    StatisticsReport statisticsReport_19 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_19.copyProperties(statisticsReport_17);
    statisticsReport_19.setPresentationName("[DownF] - SC(R) Mean");
    statisticsReport_19.setMonitor(reportMonitor_31);

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    StatisticsReport statisticsReport_20 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
	ReportMonitor reportMonitor_40 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[DownF] - SU FL Monitor"));
    statisticsReport_20.copyProperties(statisticsReport_17);
    statisticsReport_20.setPresentationName("[DownF] - SU FL Mean");
    statisticsReport_20.setMonitor(reportMonitor_40);}

    StatisticsReport statisticsReport_21 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_21.copyProperties(statisticsReport_17);
    statisticsReport_21.setPresentationName("[DownF] - SU FR Mean");
    statisticsReport_21.setMonitor(reportMonitor_41);

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    StatisticsReport statisticsReport_22 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
	ReportMonitor reportMonitor_42 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[DownF] - SU RL Monitor"));
    statisticsReport_22.copyProperties(statisticsReport_17);
    statisticsReport_22.setPresentationName("[DownF] - SU RL Mean");
    statisticsReport_22.setMonitor(reportMonitor_42);}

    StatisticsReport statisticsReport_23 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_23.copyProperties(statisticsReport_17);
    statisticsReport_23.setPresentationName("[DownF] - SU RR Mean");
    statisticsReport_23.setMonitor(reportMonitor_43);

    StatisticsReport statisticsReport_24 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_24.copyProperties(statisticsReport_17);
    statisticsReport_24.setPresentationName("[DownF] - Total Mean");
    statisticsReport_24.setMonitor(reportMonitor_44);

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    StatisticsReport statisticsReport_25 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
	ReportMonitor reportMonitor_45 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[DownF] - UB(L) Monitor"));
    statisticsReport_25.copyProperties(statisticsReport_17);
    statisticsReport_25.setPresentationName("[DownF] - UB(L) Mean");
    statisticsReport_25.setMonitor(reportMonitor_45);}

    StatisticsReport statisticsReport_26 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_26.copyProperties(statisticsReport_17);
    statisticsReport_26.setPresentationName("[DownF] - UB(R) Mean");
    statisticsReport_26.setMonitor(reportMonitor_46);

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    StatisticsReport statisticsReport_27 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
	ReportMonitor reportMonitor_47 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[DownF] - W FL Monitor"));
    statisticsReport_27.copyProperties(statisticsReport_17);
    statisticsReport_27.setPresentationName("[DownF] - W FL Mean");
    statisticsReport_27.setMonitor(reportMonitor_47);}

    StatisticsReport statisticsReport_28 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_28.copyProperties(statisticsReport_17);
    statisticsReport_28.setPresentationName("[DownF] - W FR Mean");
    statisticsReport_28.setMonitor(reportMonitor_48);

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    StatisticsReport statisticsReport_29 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
	ReportMonitor reportMonitor_49 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[DownF] - W RL Monitor"));
    statisticsReport_29.copyProperties(statisticsReport_17);
    statisticsReport_29.setPresentationName("[DownF] - W RL Mean");
    statisticsReport_29.setMonitor(reportMonitor_49);}

    StatisticsReport statisticsReport_30 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_30.copyProperties(statisticsReport_17);
    statisticsReport_30.setPresentationName("[DownF] - W RR Mean");
    statisticsReport_30.setMonitor(reportMonitor_50);

    ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().createGroup("[Avg.]");
    ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().getObject("[Avg.]")).getGroupsManager().createGroup("[DownF]");
	//}
	
		//{ // Drag Mean Monitors
	
    StatisticsReport statisticsReport_31 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_31.copyProperties(statisticsReport_14);
    statisticsReport_31.setPresentationName("[Drag] - BCKDiff Mean");
    statisticsReport_31.setMonitor(reportMonitor_51);

    StatisticsReport statisticsReport_32 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_32.copyProperties(statisticsReport_15);
    statisticsReport_32.setPresentationName("[Drag] - FW Mean");
    statisticsReport_32.setMonitor(reportMonitor_53);

    StatisticsReport statisticsReport_33 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_33.copyProperties(statisticsReport_16);
    statisticsReport_33.setPresentationName("[Drag] - Mono Mean");
    statisticsReport_33.setMonitor(reportMonitor_59);

    StatisticsReport statisticsReport_34 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_34.copyProperties(statisticsReport_17);
    statisticsReport_34.setPresentationName("[Drag] - RW Mean");
    statisticsReport_34.setMonitor(reportMonitor_60);

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    StatisticsReport statisticsReport_35 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
	StatisticsReport statisticsReport_18 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[DownF] - SC(L) Mean"));
    statisticsReport_35.copyProperties(statisticsReport_18);
    statisticsReport_35.setPresentationName("[Drag] - SC(L) Mean");
	ReportMonitor reportMonitor_67 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Drag] - SC(L) Monitor"));
    statisticsReport_35.setMonitor(reportMonitor_67);}

    StatisticsReport statisticsReport_36 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_36.copyProperties(statisticsReport_19);
    statisticsReport_36.setPresentationName("[Drag] - SC(R) Mean");
    statisticsReport_36.setMonitor(reportMonitor_68);

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    StatisticsReport statisticsReport_37 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
	StatisticsReport statisticsReport_20 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[DownF] - SU FL Mean"));
    statisticsReport_37.copyProperties(statisticsReport_20);
    statisticsReport_37.setPresentationName("[Drag] - SU FL Mean");
	ReportMonitor reportMonitor_77 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Drag] - SU FL Monitor"));
    statisticsReport_37.setMonitor(reportMonitor_77);}

    StatisticsReport statisticsReport_38 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_38.copyProperties(statisticsReport_21);
    statisticsReport_38.setPresentationName("[Drag] - SU FR Mean");
    statisticsReport_38.setMonitor(reportMonitor_78);

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    StatisticsReport statisticsReport_39 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
	StatisticsReport statisticsReport_22 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[DownF] - SU RL Mean"));
    statisticsReport_39.copyProperties(statisticsReport_22);
    statisticsReport_39.setPresentationName("[Drag] - SU RL Mean");
	ReportMonitor reportMonitor_79 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Drag] - SU RL Monitor"));
    statisticsReport_39.setMonitor(reportMonitor_79);}

    StatisticsReport statisticsReport_40 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_40.copyProperties(statisticsReport_23);
    statisticsReport_40.setPresentationName("[Drag] - SU RR Mean");
    statisticsReport_40.setMonitor(reportMonitor_80);

    StatisticsReport statisticsReport_41 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_41.copyProperties(statisticsReport_24);
    statisticsReport_41.setPresentationName("[Drag] - Total Mean");
    statisticsReport_41.setMonitor(reportMonitor_81);

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    StatisticsReport statisticsReport_42 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
	StatisticsReport statisticsReport_25 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[DownF] - UB(L) Mean"));
    statisticsReport_42.copyProperties(statisticsReport_25);
    statisticsReport_42.setPresentationName("[Drag] - UB(L) Mean");
	ReportMonitor reportMonitor_82 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Drag] - UB(L) Monitor"));
    statisticsReport_42.setMonitor(reportMonitor_82);}

    StatisticsReport statisticsReport_43 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_43.copyProperties(statisticsReport_26);
    statisticsReport_43.setPresentationName("[Drag] - UB(R) Mean");
    statisticsReport_43.setMonitor(reportMonitor_83);

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    StatisticsReport statisticsReport_44 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
	StatisticsReport statisticsReport_27 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[DownF] - W FL Mean"));
    statisticsReport_44.copyProperties(statisticsReport_27);
    statisticsReport_44.setPresentationName("[Drag] - W FL Mean");
	ReportMonitor reportMonitor_84 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Drag] - W FL Monitor"));
    statisticsReport_44.setMonitor(reportMonitor_84);}

    StatisticsReport statisticsReport_45 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_45.copyProperties(statisticsReport_28);
    statisticsReport_45.setPresentationName("[Drag] - W FR Mean");
    statisticsReport_45.setMonitor(reportMonitor_85);

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    StatisticsReport statisticsReport_46 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
	StatisticsReport statisticsReport_29 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[DownF] - W RL Mean"));
    statisticsReport_46.copyProperties(statisticsReport_29);
    statisticsReport_46.setPresentationName("[Drag] - W RL Mean");
	ReportMonitor reportMonitor_86 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Drag] - W RL Monitor"));
    statisticsReport_46.setMonitor(reportMonitor_86);}

    StatisticsReport statisticsReport_47 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_47.copyProperties(statisticsReport_30);
    statisticsReport_47.setPresentationName("[Drag] - W RR Mean");
    statisticsReport_47.setMonitor(reportMonitor_87);
	
	if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
	((ClientServerObjectGroup) ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().getObject("[Avg.]")).getGroupsManager().getObject("[DownF]")).getGroupsManager().groupObjects("[DownF]", new NeoObjectVector(new Object[] {statisticsReport_14, statisticsReport_15, statisticsReport_16, statisticsReport_17, statisticsReport_19, statisticsReport_21, statisticsReport_23, statisticsReport_24, statisticsReport_26, statisticsReport_28, statisticsReport_30}), true);	
	} else {
	StatisticsReport statisticsReport_18 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[DownF] - SC(L) Mean"));
	StatisticsReport statisticsReport_20 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[DownF] - SU FL Mean"));
    StatisticsReport statisticsReport_22 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[DownF] - SU RL Mean"));
    StatisticsReport statisticsReport_25 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[DownF] - UB(L) Mean"));
    StatisticsReport statisticsReport_27 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[DownF] - W FL Mean"));
    StatisticsReport statisticsReport_29 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[DownF] - W RL Mean"));
	((ClientServerObjectGroup) ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().getObject("[Avg.]")).getGroupsManager().getObject("[DownF]")).getGroupsManager().groupObjects("[DownF]", new NeoObjectVector(new Object[] {statisticsReport_14, statisticsReport_15, statisticsReport_16, statisticsReport_17, statisticsReport_18, statisticsReport_19, statisticsReport_20, statisticsReport_21, statisticsReport_22, statisticsReport_23, statisticsReport_24, statisticsReport_25, statisticsReport_26, statisticsReport_27, statisticsReport_28, statisticsReport_29, statisticsReport_30}), true);
    }
	
	((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().getObject("[Avg.]")).getGroupsManager().createGroup("[Drag]");
	//}

		//{ // SideForce Mean Monitors

    StatisticsReport statisticsReport_48 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_48.copyProperties(statisticsReport_31);
    statisticsReport_48.setPresentationName("[SideF] - BCKDiff Mean");
    statisticsReport_48.setMonitor(reportMonitor_88);

    StatisticsReport statisticsReport_49 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_49.copyProperties(statisticsReport_32);
    statisticsReport_49.setPresentationName("[SideF] - FW Mean");
    statisticsReport_49.setMonitor(reportMonitor_90);

    StatisticsReport statisticsReport_50 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_50.copyProperties(statisticsReport_33);
    statisticsReport_50.setPresentationName("[SideF] - Mono Mean");
    statisticsReport_50.setMonitor(reportMonitor_96);

    StatisticsReport statisticsReport_51 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_51.copyProperties(statisticsReport_34);
    statisticsReport_51.setPresentationName("[SideF] - RW Mean");
    statisticsReport_51.setMonitor(reportMonitor_97);

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    StatisticsReport statisticsReport_52 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
	StatisticsReport statisticsReport_35 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[Drag] - SC(L) Mean"));
    statisticsReport_52.copyProperties(statisticsReport_35);
	ReportMonitor reportMonitor_104 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[SideF] - SC(L) Monitor"));
    statisticsReport_52.setPresentationName("[SideF] - SC(L) Mean");
    statisticsReport_52.setMonitor(reportMonitor_104);}

    StatisticsReport statisticsReport_53 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_53.copyProperties(statisticsReport_36);
    statisticsReport_53.setPresentationName("[SideF] - SC(R) Mean");
    statisticsReport_53.setMonitor(reportMonitor_105);

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    StatisticsReport statisticsReport_54 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
	StatisticsReport statisticsReport_37 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[Drag] - SU FL Mean"));
    statisticsReport_54.copyProperties(statisticsReport_37);
	ReportMonitor reportMonitor_114 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[SideF] - SU FL Monitor"));
    statisticsReport_54.setPresentationName("[SideF] - SU FL Mean");
    statisticsReport_54.setMonitor(reportMonitor_114);}

    StatisticsReport statisticsReport_55 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_55.copyProperties(statisticsReport_38);
    statisticsReport_55.setPresentationName("[SideF] - SU FR Mean");
    statisticsReport_55.setMonitor(reportMonitor_115);

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    StatisticsReport statisticsReport_56 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
	StatisticsReport statisticsReport_39 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[Drag] - SU RL Mean"));
    statisticsReport_56.copyProperties(statisticsReport_39);
	ReportMonitor reportMonitor_116 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[SideF] - SU RL Monitor"));
    statisticsReport_56.setPresentationName("[SideF] - SU RL Mean");
    statisticsReport_56.setMonitor(reportMonitor_116);}

    StatisticsReport statisticsReport_57 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_57.copyProperties(statisticsReport_40);
    statisticsReport_57.setPresentationName("[SideF] - SU RR Mean");
    statisticsReport_57.setMonitor(reportMonitor_117);

    StatisticsReport statisticsReport_58 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_58.copyProperties(statisticsReport_41);
    statisticsReport_58.setPresentationName("[SideF] - Total Mean");
    statisticsReport_58.setMonitor(reportMonitor_118);

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    StatisticsReport statisticsReport_59 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
	StatisticsReport statisticsReport_42 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[Drag] - UB(L) Mean"));
    statisticsReport_59.copyProperties(statisticsReport_42);
	ReportMonitor reportMonitor_119 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[SideF] - UB(L) Monitor"));
    statisticsReport_59.setPresentationName("[SideF] - UB(L) Mean");
    statisticsReport_59.setMonitor(reportMonitor_119);}

    StatisticsReport statisticsReport_60 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_60.copyProperties(statisticsReport_43);
    statisticsReport_60.setPresentationName("[SideF] - UB(R) Mean");
    statisticsReport_60.setMonitor(reportMonitor_120);

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    StatisticsReport statisticsReport_61 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
	StatisticsReport statisticsReport_44 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[Drag] - W FL Mean"));
    statisticsReport_61.copyProperties(statisticsReport_44);
	ReportMonitor reportMonitor_121 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[SideF] - W FL Monitor"));
    statisticsReport_61.setPresentationName("[SideF] - W FL Mean");
    statisticsReport_61.setMonitor(reportMonitor_121);}
	
    StatisticsReport statisticsReport_62 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_62.copyProperties(statisticsReport_45);
    statisticsReport_62.setPresentationName("[SideF] - W FR Mean");
    statisticsReport_62.setMonitor(reportMonitor_122);

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    StatisticsReport statisticsReport_63 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
	StatisticsReport statisticsReport_46 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[Drag] - W RL Mean"));
    statisticsReport_63.copyProperties(statisticsReport_46);
	ReportMonitor reportMonitor_123 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[SideF] - W RL Monitor"));	
    statisticsReport_63.setPresentationName("[SideF] - W RL Mean");
    statisticsReport_63.setMonitor(reportMonitor_123);}

    StatisticsReport statisticsReport_64 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_64.copyProperties(statisticsReport_47);
    statisticsReport_64.setPresentationName("[SideF] - W RR Mean");
    statisticsReport_64.setMonitor(reportMonitor_124);

	if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
	((ClientServerObjectGroup) ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().getObject("[Avg.]")).getGroupsManager().getObject("[Drag]")).getGroupsManager().groupObjects("[Drag]", new NeoObjectVector(new Object[] {statisticsReport_31, statisticsReport_32, statisticsReport_33, statisticsReport_34, statisticsReport_36, statisticsReport_38, statisticsReport_40, statisticsReport_41, statisticsReport_43, statisticsReport_45, statisticsReport_47}), true);
	} else {
	StatisticsReport statisticsReport_35 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[Drag] - SC(L) Mean"));	
	StatisticsReport statisticsReport_37 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[Drag] - SU FL Mean"));
    StatisticsReport statisticsReport_39 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[Drag] - SU RL Mean"));
   	StatisticsReport statisticsReport_42 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[Drag] - UB(L) Mean"));
    StatisticsReport statisticsReport_44 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[Drag] - W FL Mean"));
	StatisticsReport statisticsReport_46 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[Drag] - W RL Mean"));	
    ((ClientServerObjectGroup) ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().getObject("[Avg.]")).getGroupsManager().getObject("[Drag]")).getGroupsManager().groupObjects("[Drag]", new NeoObjectVector(new Object[] {statisticsReport_31, statisticsReport_32, statisticsReport_33, statisticsReport_34, statisticsReport_35, statisticsReport_36, statisticsReport_37, statisticsReport_38, statisticsReport_39, statisticsReport_40, statisticsReport_41, statisticsReport_42, statisticsReport_43, statisticsReport_44, statisticsReport_45, statisticsReport_46, statisticsReport_47}), true);
	}

    ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().getObject("[Avg.]")).getGroupsManager().createGroup("[SideF]");
    
	if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
	((ClientServerObjectGroup) ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().getObject("[Avg.]")).getGroupsManager().getObject("[SideF]")).getGroupsManager().groupObjects("[SideF]", new NeoObjectVector(new Object[] {statisticsReport_48, statisticsReport_49, statisticsReport_50, statisticsReport_51, statisticsReport_53, statisticsReport_55, statisticsReport_57, statisticsReport_58, statisticsReport_60, statisticsReport_62, statisticsReport_64}), true);
	} else {
	StatisticsReport statisticsReport_52 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[SideF] - SC(L) Mean"));	
	StatisticsReport statisticsReport_54 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[SideF] - SU FL Mean"));
    StatisticsReport statisticsReport_56 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[SideF] - SU RL Mean"));
   	StatisticsReport statisticsReport_59 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[SideF] - UB(L) Mean"));
    StatisticsReport statisticsReport_61 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[SideF] - W FL Mean"));
	StatisticsReport statisticsReport_63 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[SideF] - W RL Mean"));		
	((ClientServerObjectGroup) ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().getObject("[Avg.]")).getGroupsManager().getObject("[SideF]")).getGroupsManager().groupObjects("[SideF]", new NeoObjectVector(new Object[] {statisticsReport_48, statisticsReport_49, statisticsReport_50, statisticsReport_51, statisticsReport_52, statisticsReport_53, statisticsReport_54, statisticsReport_55, statisticsReport_56, statisticsReport_57, statisticsReport_58, statisticsReport_59, statisticsReport_60, statisticsReport_61, statisticsReport_62, statisticsReport_63, statisticsReport_64}), true);
	}
	//}
	
		//{ // Moments Mean Monitors
	
    StatisticsReport statisticsReport_65 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_65.setPresentationName("[Mx] - CoG Mean");
    statisticsReport_65.setSampleFilterOption(SampleFilterOption.LastNSamples);

    LastNSamplesFilter lastNSamplesFilter_3 = 
      ((LastNSamplesFilter) statisticsReport_65.getSampleFilterManager().getObject("Last N Samples"));
    lastNSamplesFilter_3.setNSamples(250);

    statisticsReport_65.setMonitor(reportMonitor_125);

    StatisticsReport statisticsReport_66 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_66.copyProperties(statisticsReport_65);
    statisticsReport_66.setPresentationName("[My] - CoG Mean");
    statisticsReport_66.setMonitor(reportMonitor_127);

    StatisticsReport statisticsReport_67 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_67.copyProperties(statisticsReport_65);
    statisticsReport_67.setPresentationName("[Mz] - CoG Mean");
    statisticsReport_67.setMonitor(reportMonitor_129);

    StatisticsReport statisticsReport_68 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_68.copyProperties(statisticsReport_65);
    statisticsReport_68.setPresentationName("[Mx] - Origin Mean");
    statisticsReport_68.setMonitor(reportMonitor_126);

    StatisticsReport statisticsReport_69 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_69.copyProperties(statisticsReport_68);
    statisticsReport_69.setPresentationName("[My] - Origin Mean");
    statisticsReport_69.setMonitor(reportMonitor_128);

    StatisticsReport statisticsReport_70 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_70.copyProperties(statisticsReport_68);
    statisticsReport_70.setPresentationName("[Mz] - Origin Mean");
    statisticsReport_70.setMonitor(reportMonitor_130);

    ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Moments]")).getGroupsManager().createGroup("[Avg.]");
    ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Moments]")).getGroupsManager().getObject("[Avg.]")).getGroupsManager().groupObjects("[Avg.]", new NeoObjectVector(new Object[] {statisticsReport_65, statisticsReport_68, statisticsReport_66, statisticsReport_69, statisticsReport_67, statisticsReport_70}), true);
	//}
	
		//{ // Solver Mean Monitors
	
    StatisticsReport statisticsReport_71 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_71.setPresentationName("Continuity");
    statisticsReport_71.setSampleFilterOption(SampleFilterOption.LastNSamples);

    LastNSamplesFilter lastNSamplesFilter_4 = 
      ((LastNSamplesFilter) statisticsReport_71.getSampleFilterManager().getObject("Last N Samples"));
    lastNSamplesFilter_4.setNSamples(250);

    statisticsReport_71.setMonitor(residualMonitor_5);

    StatisticsReport statisticsReport_72 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_72.copyProperties(statisticsReport_71);
    statisticsReport_72.setPresentationName("Sdr");
    statisticsReport_72.setMonitor(residualMonitor_2);

    StatisticsReport statisticsReport_73 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_73.copyProperties(statisticsReport_71);
    statisticsReport_73.setPresentationName("Tke");
    statisticsReport_73.setMonitor(residualMonitor_0);

    StatisticsReport statisticsReport_74 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_74.copyProperties(statisticsReport_71);
    statisticsReport_74.setPresentationName("x-mome");
    statisticsReport_74.setMonitor(residualMonitor_1);

    StatisticsReport statisticsReport_75 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_75.copyProperties(statisticsReport_74);
    statisticsReport_75.setPresentationName("y-mome");
    statisticsReport_75.setMonitor(residualMonitor_3);

    StatisticsReport statisticsReport_76 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_76.copyProperties(statisticsReport_75);
    statisticsReport_76.setPresentationName("z-mome");
    statisticsReport_76.setMonitor(residualMonitor_4);

    simulation_0.getReportManager().getGroupsManager().createGroup("[Solver]");
    ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Solver]")).getGroupsManager().createGroup("Residuals");
    ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Solver]")).getGroupsManager().getObject("Residuals")).getGroupsManager().groupObjects("Residuals", new NeoObjectVector(new Object[] {statisticsReport_71, statisticsReport_72, statisticsReport_73, statisticsReport_74, statisticsReport_75, statisticsReport_76}), true);

    IteratorElapsedTimeReport iteratorElapsedTimeReport_0 = 
      simulation_0.getReportManager().createReport(IteratorElapsedTimeReport.class);

    SimulationIteratorTimeReportMonitor simulationIteratorTimeReportMonitor_0 = 
      (SimulationIteratorTimeReportMonitor) iteratorElapsedTimeReport_0.createMonitor();

    ((ClientServerObjectGroup) simulation_0.getMonitorManager().getGroupsManager().getObject("[Time]")).getGroupsManager().groupObjects("[Time]", new NeoObjectVector(new Object[] {simulationIteratorTimeReportMonitor_0}), true);

    StatisticsReport statisticsReport_77 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_77.setPresentationName("Time iter Mean");
    statisticsReport_77.setSampleFilterOption(SampleFilterOption.LastNSamples);
    statisticsReport_77.setMonitor(simulationIteratorTimeReportMonitor_0);

    LastNSamplesFilter lastNSamplesFilter_5 = 
      ((LastNSamplesFilter) statisticsReport_77.getSampleFilterManager().getObject("Last N Samples"));
    lastNSamplesFilter_5.setNSamples(250);
	
	Units units_0 = 
      ((Units) simulation_0.getUnitsManager().getObject("hr"));
	  
	Units units_1 = 
      ((Units) simulation_0.getUnitsManager().getObject("GiB"));

    CumulativeElapsedTimeReport cumulativeElapsedTimeReport_0 = 
      simulation_0.getReportManager().createReport(CumulativeElapsedTimeReport.class);
    cumulativeElapsedTimeReport_0.setUnits(units_0);

    IterationMaximumMemoryReport iterationMaximumMemoryReport_0 = 
      simulation_0.getReportManager().createReport(IterationMaximumMemoryReport.class);
    iterationMaximumMemoryReport_0.setUnits(units_1);

    ElementCountReport elementCountReport_0 = 
      simulation_0.getReportManager().createReport(ElementCountReport.class);
    elementCountReport_0.setPresentationName("No. cells");
    elementCountReport_0.getParts().setQuery(new Query(new TypePredicate(TypeOperator.Is, Region.class), Query.STANDARD_MODIFIERS));

    ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Solver]")).getGroupsManager().createGroup("Time");
    ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Solver]")).getGroupsManager().getObject("Time")).getGroupsManager().groupObjects("Time", new NeoObjectVector(new Object[] {iteratorElapsedTimeReport_0, statisticsReport_77, cumulativeElapsedTimeReport_0}), true);
    ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Solver]")).getGroupsManager().groupObjects("[Solver]", new NeoObjectVector(new Object[] {iterationMaximumMemoryReport_0, elementCountReport_0}), true);
	//}
	
		//{ // Group CLoads Mean Monitors

    ReportMonitor reportMonitor_131 = 
      statisticsReport_0.createMonitor();
    ReportMonitor reportMonitor_132 = 
      statisticsReport_3.createMonitor();
    ReportMonitor reportMonitor_133 = 
      statisticsReport_1.createMonitor();
    ReportMonitor reportMonitor_134 = 
      statisticsReport_4.createMonitor();
    ReportMonitor reportMonitor_135 = 
      statisticsReport_2.createMonitor();
    ReportMonitor reportMonitor_136 = 
      statisticsReport_5.createMonitor();

    ((ClientServerObjectGroup) simulation_0.getMonitorManager().getGroupsManager().getObject("[CLoads]")).getGroupsManager().createGroup("[Avg.]");
    ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getMonitorManager().getGroupsManager().getObject("[CLoads]")).getGroupsManager().getObject("[Avg.]")).getGroupsManager().groupObjects("[Avg.]", new NeoObjectVector(new Object[] {reportMonitor_131, reportMonitor_132, reportMonitor_133, reportMonitor_134, reportMonitor_135, reportMonitor_136}), true);
	
	//}

		//{ // Group Cooling Mean Monitors

    ReportMonitor reportMonitor_137 = 
      statisticsReport_6.createMonitor();
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
	StatisticsReport statisticsReport_7 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[MFlow] - Fan (L) Mean"));	
    ReportMonitor reportMonitor_138 = 
      statisticsReport_7.createMonitor();
	StatisticsReport statisticsReport_8 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[MFlow] - Radiator (L) Mean"));
    ReportMonitor reportMonitor_139 = 
      statisticsReport_8.createMonitor();}
    ReportMonitor reportMonitor_140 = 
      statisticsReport_9.createMonitor();
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
	StatisticsReport statisticsReport_10 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[PDrop] - Fan (L) Mean"));
    ReportMonitor reportMonitor_141 = 
      statisticsReport_10.createMonitor();}
    ReportMonitor reportMonitor_142 = 
      statisticsReport_11.createMonitor();
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
	StatisticsReport statisticsReport_12 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[PDrop] - Radiator (L) Mean"));
    ReportMonitor reportMonitor_143 = 
      statisticsReport_12.createMonitor();}
    ReportMonitor reportMonitor_144 = 
      statisticsReport_13.createMonitor();

    ((ClientServerObjectGroup) simulation_0.getMonitorManager().getGroupsManager().getObject("[Cooling]")).getGroupsManager().createGroup("[Avg.]");
    if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
	((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getMonitorManager().getGroupsManager().getObject("[Cooling]")).getGroupsManager().getObject("[Avg.]")).getGroupsManager().groupObjects("[Avg.]", new NeoObjectVector(new Object[] {reportMonitor_137, reportMonitor_140, reportMonitor_142, reportMonitor_144}), true);	
	} else {
	ReportMonitor reportMonitor_138 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[MFlow] - Fan (L) Mean Monitor"));
	ReportMonitor reportMonitor_139 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[MFlow] - Radiator (L) Mean Monitor"));
	ReportMonitor reportMonitor_141 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[PDrop] - Fan (L) Mean Monitor"));
	ReportMonitor reportMonitor_143 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[PDrop] - Radiator (L) Mean Monitor"));
    	
    ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getMonitorManager().getGroupsManager().getObject("[Cooling]")).getGroupsManager().getObject("[Avg.]")).getGroupsManager().groupObjects("[Avg.]", new NeoObjectVector(new Object[] {reportMonitor_137, reportMonitor_138, reportMonitor_139, reportMonitor_140, reportMonitor_141, reportMonitor_142, reportMonitor_143, reportMonitor_144}), true);
	}
	//}

		//{ // Group DownForce Mean Monitors

    ReportMonitor reportMonitor_145 = 
      statisticsReport_14.createMonitor();
    ReportMonitor reportMonitor_146 = 
      statisticsReport_15.createMonitor();
    ReportMonitor reportMonitor_147 = 
      statisticsReport_16.createMonitor();
    ReportMonitor reportMonitor_148 = 
      statisticsReport_17.createMonitor();
	
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
	StatisticsReport statisticsReport_18 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[DownF] - SC(L) Mean"));
    ReportMonitor reportMonitor_149 = 
      statisticsReport_18.createMonitor();}
	  
    ReportMonitor reportMonitor_150 = 
      statisticsReport_19.createMonitor();
	
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
	StatisticsReport statisticsReport_20 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[DownF] - SU FL Mean"));	
    ReportMonitor reportMonitor_151 = 
      statisticsReport_20.createMonitor();}
	  
    ReportMonitor reportMonitor_152 = 
      statisticsReport_21.createMonitor();
	
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
	StatisticsReport statisticsReport_22 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[DownF] - SU RL Mean"));
    ReportMonitor reportMonitor_153 = 
      statisticsReport_22.createMonitor();}
	  
    ReportMonitor reportMonitor_154 = 
      statisticsReport_23.createMonitor();
    ReportMonitor reportMonitor_155 = 
      statisticsReport_24.createMonitor();
	  
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
	StatisticsReport statisticsReport_25 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[DownF] - UB(L) Mean"));
    ReportMonitor reportMonitor_156 = 
      statisticsReport_25.createMonitor();}
	  
    ReportMonitor reportMonitor_157 = 
      statisticsReport_26.createMonitor();
	  
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
	StatisticsReport statisticsReport_27 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[DownF] - W FL Mean"));
    ReportMonitor reportMonitor_158 = 
      statisticsReport_27.createMonitor();}
	  
    ReportMonitor reportMonitor_159 = 
      statisticsReport_28.createMonitor();
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
	StatisticsReport statisticsReport_29 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[DownF] - W RL Mean"));	
    ReportMonitor reportMonitor_160 = 
      statisticsReport_29.createMonitor();}
	  
    ReportMonitor reportMonitor_161 = 
      statisticsReport_30.createMonitor();

    ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getMonitorManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().getObject("[DownF]")).getGroupsManager().createGroup("[Avg.]");
    
	if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
	((ClientServerObjectGroup) ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getMonitorManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().getObject("[DownF]")).getGroupsManager().getObject("[Avg.]")).getGroupsManager().groupObjects("[Avg.]", new NeoObjectVector(new Object[] {reportMonitor_145, reportMonitor_146, reportMonitor_147, reportMonitor_148, reportMonitor_150, reportMonitor_152, reportMonitor_154, reportMonitor_155, reportMonitor_157, reportMonitor_159, reportMonitor_161}), true);	
	} else {
		
	ReportMonitor reportMonitor_149 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[DownF] - SC(L) Mean Monitor"));
	ReportMonitor reportMonitor_151 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[DownF] - SU FL Mean Monitor"));
	ReportMonitor reportMonitor_153 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[DownF] - SU RL Mean Monitor"));
	ReportMonitor reportMonitor_156 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[DownF] - UB(L) Mean Monitor"));
	ReportMonitor reportMonitor_158 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[DownF] - W FL Mean Monitor"));
	ReportMonitor reportMonitor_160 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[DownF] - W RL Mean Monitor"));
		
	((ClientServerObjectGroup) ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getMonitorManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().getObject("[DownF]")).getGroupsManager().getObject("[Avg.]")).getGroupsManager().groupObjects("[Avg.]", new NeoObjectVector(new Object[] {reportMonitor_145, reportMonitor_146, reportMonitor_147, reportMonitor_148, reportMonitor_149, reportMonitor_150, reportMonitor_151, reportMonitor_152, reportMonitor_153, reportMonitor_154, reportMonitor_155, reportMonitor_156, reportMonitor_157, reportMonitor_158, reportMonitor_159, reportMonitor_160, reportMonitor_161}), true);
	}
	//}

		//{ // Group Drag Mean Monitors

    ReportMonitor reportMonitor_162 = 
      statisticsReport_31.createMonitor();
    ReportMonitor reportMonitor_163 = 
      statisticsReport_32.createMonitor();
    ReportMonitor reportMonitor_164 = 
      statisticsReport_33.createMonitor();
    ReportMonitor reportMonitor_165 = 
      statisticsReport_34.createMonitor();
	  
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
	StatisticsReport statisticsReport_35 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[Drag] - SC(L) Mean"));	
    ReportMonitor reportMonitor_166 = 
      statisticsReport_35.createMonitor();}
	  
    ReportMonitor reportMonitor_167 = 
      statisticsReport_36.createMonitor();  
	  
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
	StatisticsReport statisticsReport_37 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[Drag] - SU FL Mean"));	
    ReportMonitor reportMonitor_168 = 
      statisticsReport_37.createMonitor();}
	  
    ReportMonitor reportMonitor_169 = 
      statisticsReport_38.createMonitor();
	    
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
	StatisticsReport statisticsReport_39 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[Drag] - SU RL Mean"));	
    ReportMonitor reportMonitor_170 = 
      statisticsReport_39.createMonitor();}
	  
    ReportMonitor reportMonitor_171 = 
      statisticsReport_40.createMonitor();
    ReportMonitor reportMonitor_172 = 
      statisticsReport_41.createMonitor();
	    
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
	StatisticsReport statisticsReport_42 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[Drag] - UB(L) Mean"));
    ReportMonitor reportMonitor_173 = 
      statisticsReport_42.createMonitor();}
	  
    ReportMonitor reportMonitor_174 = 
      statisticsReport_43.createMonitor();
	    
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
	StatisticsReport statisticsReport_44 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[Drag] - W FL Mean"));
    ReportMonitor reportMonitor_175 = 
      statisticsReport_44.createMonitor();}
	  
    ReportMonitor reportMonitor_176 = 
      statisticsReport_45.createMonitor();
	    
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
	StatisticsReport statisticsReport_46 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[Drag] - W RL Mean"));	
    ReportMonitor reportMonitor_177 = 
      statisticsReport_46.createMonitor();}
	  
    ReportMonitor reportMonitor_178 = 
      statisticsReport_47.createMonitor();

    ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getMonitorManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().getObject("[Drag]")).getGroupsManager().createGroup("[Avg.]");
    
	
	if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
	((ClientServerObjectGroup) ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getMonitorManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().getObject("[Drag]")).getGroupsManager().getObject("[Avg.]")).getGroupsManager().groupObjects("[Avg.]", new NeoObjectVector(new Object[] {reportMonitor_162, reportMonitor_163, reportMonitor_164, reportMonitor_165, reportMonitor_167, reportMonitor_169, reportMonitor_171, reportMonitor_172, reportMonitor_174, reportMonitor_176, reportMonitor_178}), true);
	} else {
		
	ReportMonitor reportMonitor_166 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Drag] - SC(L) Mean Monitor"));	
	ReportMonitor reportMonitor_168 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Drag] - SU FL Mean Monitor"));
	ReportMonitor reportMonitor_170 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Drag] - SU RL Mean Monitor"));
	ReportMonitor reportMonitor_173 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Drag] - UB(L) Mean Monitor"));
	ReportMonitor reportMonitor_175 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Drag] - W FL Mean Monitor"));
	ReportMonitor reportMonitor_177 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Drag] - W RL Mean Monitor"));
	
	((ClientServerObjectGroup) ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getMonitorManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().getObject("[Drag]")).getGroupsManager().getObject("[Avg.]")).getGroupsManager().groupObjects("[Avg.]", new NeoObjectVector(new Object[] {reportMonitor_162, reportMonitor_163, reportMonitor_164, reportMonitor_165, reportMonitor_166, reportMonitor_167, reportMonitor_168, reportMonitor_169, reportMonitor_170, reportMonitor_171, reportMonitor_172, reportMonitor_173, reportMonitor_174, reportMonitor_175, reportMonitor_176, reportMonitor_177, reportMonitor_178}), true);
	}
	
		//}
	
		//{ // Group SideForce Mean Monitors

    ReportMonitor reportMonitor_179 = 
      statisticsReport_48.createMonitor();
    ReportMonitor reportMonitor_180 = 
      statisticsReport_49.createMonitor();
    ReportMonitor reportMonitor_181 = 
      statisticsReport_50.createMonitor();
    ReportMonitor reportMonitor_182 = 
      statisticsReport_51.createMonitor();
	
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
	StatisticsReport statisticsReport_52 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[SideF] - SC(L) Mean"));	
    ReportMonitor reportMonitor_183 = 
      statisticsReport_52.createMonitor();}
	  
    ReportMonitor reportMonitor_184 = 
      statisticsReport_53.createMonitor();
	  
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
	StatisticsReport statisticsReport_54 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[SideF] - SU FL Mean"));
    ReportMonitor reportMonitor_185 = 
      statisticsReport_54.createMonitor();}
	  
    ReportMonitor reportMonitor_186 = 
      statisticsReport_55.createMonitor();
	  
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
	StatisticsReport statisticsReport_56 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[SideF] - SU RL Mean"));
    ReportMonitor reportMonitor_187 = 
      statisticsReport_56.createMonitor();}
	  
    ReportMonitor reportMonitor_188 = 
      statisticsReport_57.createMonitor();
    ReportMonitor reportMonitor_189 = 
      statisticsReport_58.createMonitor();
	  
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
	StatisticsReport statisticsReport_59 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[SideF] - UB(L) Mean"));	
    ReportMonitor reportMonitor_190 = 
      statisticsReport_59.createMonitor();}
	  
    ReportMonitor reportMonitor_191 = 
      statisticsReport_60.createMonitor();
	  
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
	StatisticsReport statisticsReport_61 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[SideF] - W FL Mean"));
    ReportMonitor reportMonitor_192 = 
      statisticsReport_61.createMonitor();}
	  
    ReportMonitor reportMonitor_193 = 
      statisticsReport_62.createMonitor();
	  
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
	StatisticsReport statisticsReport_63 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[SideF] - W RL Mean"));		
    ReportMonitor reportMonitor_194 = 
      statisticsReport_63.createMonitor();}
	  
    ReportMonitor reportMonitor_195 = 
      statisticsReport_64.createMonitor();

    ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getMonitorManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().getObject("[SideF]")).getGroupsManager().createGroup("[Avg.]");
    
	if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
	((ClientServerObjectGroup) ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getMonitorManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().getObject("[SideF]")).getGroupsManager().getObject("[Avg.]")).getGroupsManager().groupObjects("[Avg.]", new NeoObjectVector(new Object[] {reportMonitor_179, reportMonitor_180, reportMonitor_181, reportMonitor_182, reportMonitor_184, reportMonitor_186, reportMonitor_188, reportMonitor_189, reportMonitor_191, reportMonitor_193, reportMonitor_195}), true);
	} else {
		
	ReportMonitor reportMonitor_183 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[SideF] - SC(L) Mean Monitor"));	
	ReportMonitor reportMonitor_185 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[SideF] - SU FL Mean Monitor"));
	ReportMonitor reportMonitor_187 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[SideF] - SU RL Mean Monitor"));
	ReportMonitor reportMonitor_190 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[SideF] - UB(L) Mean Monitor"));
	ReportMonitor reportMonitor_192 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[SideF] - W FL Mean Monitor"));
	ReportMonitor reportMonitor_194 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[SideF] - W RL Mean Monitor"));	
		
		
	((ClientServerObjectGroup) ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getMonitorManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().getObject("[SideF]")).getGroupsManager().getObject("[Avg.]")).getGroupsManager().groupObjects("[Avg.]", new NeoObjectVector(new Object[] {reportMonitor_179, reportMonitor_180, reportMonitor_181, reportMonitor_182, reportMonitor_183, reportMonitor_184, reportMonitor_185, reportMonitor_186, reportMonitor_187, reportMonitor_188, reportMonitor_189, reportMonitor_190, reportMonitor_191, reportMonitor_192, reportMonitor_193, reportMonitor_194, reportMonitor_195}), true);
	}
	//}
	
		//{ // Group Moments Mean Monitors

    ReportMonitor reportMonitor_196 = 
      statisticsReport_65.createMonitor();
    ReportMonitor reportMonitor_197 = 
      statisticsReport_68.createMonitor();
    ReportMonitor reportMonitor_198 = 
      statisticsReport_66.createMonitor();
    ReportMonitor reportMonitor_199 = 
      statisticsReport_69.createMonitor();
    ReportMonitor reportMonitor_200 = 
      statisticsReport_67.createMonitor();
    ReportMonitor reportMonitor_201 = 
      statisticsReport_70.createMonitor();

    ((ClientServerObjectGroup) simulation_0.getMonitorManager().getGroupsManager().getObject("[Moments]")).getGroupsManager().createGroup("[Avg.]");
    ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getMonitorManager().getGroupsManager().getObject("[Moments]")).getGroupsManager().getObject("[Avg.]")).getGroupsManager().groupObjects("[Avg.]", new NeoObjectVector(new Object[] {reportMonitor_196, reportMonitor_197, reportMonitor_198, reportMonitor_199, reportMonitor_200, reportMonitor_201}), true);
	//}
	//}
	
	//{ // Field Functions Mean

    FieldMeanMonitor fieldMeanMonitor_0 = 
      simulation_0.getMonitorManager().createMonitor(FieldMeanMonitor.class);
	fieldMeanMonitor_0.getParts().setQuery(new Query(new TypePredicate(TypeOperator.Is, Region.class), Query.STANDARD_MODIFIERS));

    Lambda2Function lambda2Function_0 = 
      ((Lambda2Function) simulation_0.getFieldFunctionManager().getFunction("Lambda2"));
    fieldMeanMonitor_0.setPresentationName("L2");
    fieldMeanMonitor_0.setFieldFunction(lambda2Function_0);

    StarUpdate starUpdate_0 = 
      fieldMeanMonitor_0.getStarUpdate();
    IterationUpdateFrequency iterationUpdateFrequency_0 = 
      starUpdate_0.getIterationUpdateFrequency();
    iterationUpdateFrequency_0.setStart(750);

    FieldMeanMonitor fieldMeanMonitor_1 = 
      simulation_0.getMonitorManager().createMonitor(FieldMeanMonitor.class);
    fieldMeanMonitor_1.copyProperties(fieldMeanMonitor_0);
    fieldMeanMonitor_1.setPresentationName("Qcrit");
    QcriterionFunction qcriterionFunction_0 = 
      ((QcriterionFunction) simulation_0.getFieldFunctionManager().getFunction("Qcriterion"));
    fieldMeanMonitor_1.setFieldFunction(qcriterionFunction_0);

    FieldMeanMonitor fieldMeanMonitor_2 = 
      simulation_0.getMonitorManager().createMonitor(FieldMeanMonitor.class);
    fieldMeanMonitor_2.copyProperties(fieldMeanMonitor_1);
    fieldMeanMonitor_2.setPresentationName("Y+");
    PrimitiveFieldFunction primitiveFieldFunction_0 = 
      ((PrimitiveFieldFunction) simulation_0.getFieldFunctionManager().getFunction("WallYplus"));
    fieldMeanMonitor_2.setFieldFunction(primitiveFieldFunction_0);

    FieldMeanMonitor fieldMeanMonitor_3 = 
      simulation_0.getMonitorManager().createMonitor(FieldMeanMonitor.class);
    fieldMeanMonitor_3.copyProperties(fieldMeanMonitor_2);
    fieldMeanMonitor_3.setPresentationName("Pressure");
    PrimitiveFieldFunction primitiveFieldFunction_1 = 
      ((PrimitiveFieldFunction) simulation_0.getFieldFunctionManager().getFunction("Pressure"));
    fieldMeanMonitor_3.setFieldFunction(primitiveFieldFunction_1);

    FieldMeanMonitor fieldMeanMonitor_4 = 
      simulation_0.getMonitorManager().createMonitor(FieldMeanMonitor.class);
    fieldMeanMonitor_4.copyProperties(fieldMeanMonitor_3);
    fieldMeanMonitor_4.setPresentationName("Total Pressure");
    PrimitiveFieldFunction primitiveFieldFunction_2 = 
      ((PrimitiveFieldFunction) simulation_0.getFieldFunctionManager().getFunction("TotalPressure"));
	  
	if ( CornerRadius == 0 ) {
	fieldMeanMonitor_4.setFieldFunction(primitiveFieldFunction_2);
	} else {
	UserRotatingReferenceFrame userRotatingReferenceFrame_0 = 
      ((UserRotatingReferenceFrame) simulation_0.get(ReferenceFrameManager.class).getObject("Air - RF"));
    fieldMeanMonitor_4.setFieldFunction(primitiveFieldFunction_2.getFunctionInReferenceFrame(userRotatingReferenceFrame_0));	
	}

    FieldMeanMonitor fieldMeanMonitor_5 = 
      simulation_0.getMonitorManager().createMonitor(FieldMeanMonitor.class);
    fieldMeanMonitor_5.copyProperties(fieldMeanMonitor_4);
    fieldMeanMonitor_5.setPresentationName("V[i]");
    PrimitiveFieldFunction primitiveFieldFunction_3 = 
      ((PrimitiveFieldFunction) simulation_0.getFieldFunctionManager().getFunction("Velocity"));
	  
	if ( CornerRadius == 0 ) {  
	VectorComponentFieldFunction vectorComponentFieldFunction_0 = 
      ((VectorComponentFieldFunction) primitiveFieldFunction_3.getComponentFunction(0));
    fieldMeanMonitor_5.setFieldFunction(vectorComponentFieldFunction_0);
	} else { 
	UserRotatingReferenceFrame userRotatingReferenceFrame_0 = 
      ((UserRotatingReferenceFrame) simulation_0.get(ReferenceFrameManager.class).getObject("Air - RF"));	
    VectorComponentFieldFunction vectorComponentFieldFunction_0 = 
      ((VectorComponentFieldFunction) primitiveFieldFunction_3.getFunctionInReferenceFrame(userRotatingReferenceFrame_0).getComponentFunction(0));
    fieldMeanMonitor_5.setFieldFunction(vectorComponentFieldFunction_0);
	}     

    FieldMeanMonitor fieldMeanMonitor_6 = 
      simulation_0.getMonitorManager().createMonitor(FieldMeanMonitor.class);
    fieldMeanMonitor_6.copyProperties(fieldMeanMonitor_5);
    fieldMeanMonitor_6.setPresentationName("V[j]");
	
	if ( CornerRadius == 0 ) {
	VectorComponentFieldFunction vectorComponentFieldFunction_1 = 
      ((VectorComponentFieldFunction) primitiveFieldFunction_3.getComponentFunction(1));
    fieldMeanMonitor_6.setFieldFunction(vectorComponentFieldFunction_1);
	
	} else {  
	UserRotatingReferenceFrame userRotatingReferenceFrame_0 = 
      ((UserRotatingReferenceFrame) simulation_0.get(ReferenceFrameManager.class).getObject("Air - RF"));
    VectorComponentFieldFunction vectorComponentFieldFunction_1 = 
      ((VectorComponentFieldFunction) primitiveFieldFunction_3.getFunctionInReferenceFrame(userRotatingReferenceFrame_0).getComponentFunction(1));
    fieldMeanMonitor_6.setFieldFunction(vectorComponentFieldFunction_1);
	}   
	  
    FieldMeanMonitor fieldMeanMonitor_7 = 
      simulation_0.getMonitorManager().createMonitor(FieldMeanMonitor.class);
    fieldMeanMonitor_7.copyProperties(fieldMeanMonitor_6);
    fieldMeanMonitor_7.setPresentationName("V[k]");
	
	if ( CornerRadius == 0 ) {
	VectorComponentFieldFunction vectorComponentFieldFunction_2 = 
      ((VectorComponentFieldFunction) primitiveFieldFunction_3.getComponentFunction(2));
    fieldMeanMonitor_7.setFieldFunction(vectorComponentFieldFunction_2);
	
	} else { 
	UserRotatingReferenceFrame userRotatingReferenceFrame_0 = 
      ((UserRotatingReferenceFrame) simulation_0.get(ReferenceFrameManager.class).getObject("Air - RF"));	
    VectorComponentFieldFunction vectorComponentFieldFunction_2 = 
      ((VectorComponentFieldFunction) primitiveFieldFunction_3.getFunctionInReferenceFrame(userRotatingReferenceFrame_0).getComponentFunction(2));
    fieldMeanMonitor_7.setFieldFunction(vectorComponentFieldFunction_2);
	}  
	   
    FieldMeanMonitor fieldMeanMonitor_8 = 
      simulation_0.getMonitorManager().createMonitor(FieldMeanMonitor.class);
    fieldMeanMonitor_8.copyProperties(fieldMeanMonitor_7);
    fieldMeanMonitor_8.setPresentationName("Wss[i]");
    PrimitiveFieldFunction primitiveFieldFunction_4 = 
      ((PrimitiveFieldFunction) simulation_0.getFieldFunctionManager().getFunction("WallShearStress"));
    VectorComponentFieldFunction vectorComponentFieldFunction_3 = 
      ((VectorComponentFieldFunction) primitiveFieldFunction_4.getComponentFunction(0));
    fieldMeanMonitor_8.setFieldFunction(vectorComponentFieldFunction_3);

    FieldMeanMonitor fieldMeanMonitor_9 = 
      simulation_0.getMonitorManager().createMonitor(FieldMeanMonitor.class);
    fieldMeanMonitor_9.copyProperties(fieldMeanMonitor_8);
    fieldMeanMonitor_9.setPresentationName("Wss[j]");
    VectorComponentFieldFunction vectorComponentFieldFunction_4 = 
      ((VectorComponentFieldFunction) primitiveFieldFunction_4.getComponentFunction(1));
    fieldMeanMonitor_9.setFieldFunction(vectorComponentFieldFunction_4);

    FieldMeanMonitor fieldMeanMonitor_10 = 
      simulation_0.getMonitorManager().createMonitor(FieldMeanMonitor.class);
    fieldMeanMonitor_10.copyProperties(fieldMeanMonitor_9);
    fieldMeanMonitor_10.setPresentationName("Wss[k]");
    VectorComponentFieldFunction vectorComponentFieldFunction_5 = 
      ((VectorComponentFieldFunction) primitiveFieldFunction_4.getComponentFunction(2));
    fieldMeanMonitor_10.setFieldFunction(vectorComponentFieldFunction_5);
	
	//{ // Pressure Inlet Avg. 
	
	AreaAverageReport areaAverageReport_0 = 
      simulation_0.getReportManager().createReport(AreaAverageReport.class);
    areaAverageReport_0.setPresentationName("Pressure Avg. Inlet");

    PrimitiveFieldFunction primitiveFieldFunction_5 = 
      ((PrimitiveFieldFunction) simulation_0.getFieldFunctionManager().getFunction("PressureMonitor"));
    areaAverageReport_0.setFieldFunction(primitiveFieldFunction_5);
    areaAverageReport_0.getParts().setQuery(null);
	Region region_0 = 
      simulation_0.getRegionManager().getRegion("Fluid");
    Boundary boundary_0 = 
      region_0.getBoundaryManager().getBoundary("Domain.Inlet");
    areaAverageReport_0.getParts().setObjects(boundary_0);
	
    ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[AC]")).getGroupsManager().groupObjects("[AC]", new NeoObjectVector(new Object[] {areaAverageReport_0}), true);
  
    simulation_0.getMonitorManager().getGroupsManager().createGroup("[FF]");
    ((ClientServerObjectGroup) simulation_0.getMonitorManager().getGroupsManager().getObject("[FF]")).getGroupsManager().groupObjects("[FF]", new NeoObjectVector(new Object[] {fieldMeanMonitor_0, fieldMeanMonitor_3, fieldMeanMonitor_1, fieldMeanMonitor_4, fieldMeanMonitor_5, fieldMeanMonitor_6, fieldMeanMonitor_7, fieldMeanMonitor_8, fieldMeanMonitor_9, fieldMeanMonitor_10, fieldMeanMonitor_2}), true);
	//}
  
  }
  
  private void Plots() {

	//{ // Center of Loads PLOT

    Simulation simulation_0 = 
      getActiveSimulation();
	  
	double Roll = simulation_0.getReportManager().getReport("Roll [deg]").getReportMonitorValue();
	double Yaw = simulation_0.getReportManager().getReport("Yaw [rad]").getReportMonitorValue();
	double CornerRadius = simulation_0.getReportManager().getReport("Corner Radius [m]").getReportMonitorValue(); 
	  
    ReportMonitor reportMonitor_0 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[CLoadX]- CoG"));

    MonitorPlot monitorPlot_0 = 
      simulation_0.getPlotManager().createMonitorPlot(new NeoObjectVector(new Object[] {reportMonitor_0}), "[CLoadX]- CoG Plot");
    monitorPlot_0.open();
    monitorPlot_0.setTitle("Centre of Loads");
    monitorPlot_0.setPresentationName("[CLoad]");

    MonitorDataSet monitorDataSet_0 = 
      ((MonitorDataSet) monitorPlot_0.getDataSetManager().getDataSet("[CLoadX]- CoG"));
    monitorDataSet_0.setSeriesNameLocked(true);
    monitorDataSet_0.setSeriesName("[X]- CoG");

    Cartesian2DAxisManager cartesian2DAxisManager_0 = 
      ((Cartesian2DAxisManager) monitorPlot_0.getAxisManager());
    Cartesian2DAxis cartesian2DAxis_0 = 
      ((Cartesian2DAxis) cartesian2DAxisManager_0.getAxis("Left Axis"));

    AxisTitle axisTitle_0 = 
      cartesian2DAxis_0.getTitle();
    axisTitle_0.setText("Coordinate (m)");
    cartesian2DAxis_0.setReverse(true);
    cartesian2DAxis_0.setMinimum(-1.0);
    cartesian2DAxis_0.setLockMaximum(true);

    ReportMonitor reportMonitor_1 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[CLoadX]- CoG Mean Monitor"));
    ReportMonitor reportMonitor_2 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[CLoadX]- Ground Mean Monitor"));
    ReportMonitor reportMonitor_3 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[CLoadY]- CoG Mean Monitor"));
    ReportMonitor reportMonitor_4 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[CLoadY]- Ground Mean Monitor"));
    ReportMonitor reportMonitor_5 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[CLoadZ]- CoG Mean Monitor"));
    ReportMonitor reportMonitor_6 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[CLoadZ]- Ground Mean Monitor"));
    ReportMonitor reportMonitor_7 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[CLoadX]- Ground"));
    ReportMonitor reportMonitor_8 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[CLoadY]- CoG"));
    ReportMonitor reportMonitor_9 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[CLoadY]- Ground"));
    ReportMonitor reportMonitor_10 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[CLoadZ]- CoG"));
    ReportMonitor reportMonitor_11 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[CLoadZ]- Ground"));

    monitorPlot_0.getDataSetManager().addDataProviders(new NeoObjectVector(new Object[] {reportMonitor_1, reportMonitor_2, reportMonitor_3, reportMonitor_4, reportMonitor_5, reportMonitor_6, reportMonitor_7, reportMonitor_8, reportMonitor_9, reportMonitor_10, reportMonitor_11}));

    MonitorDataSet monitorDataSet_1 = 
      ((MonitorDataSet) monitorPlot_0.getDataSetManager().getDataSet("[CLoadX]- CoG Mean Monitor"));
    monitorDataSet_1.setSeriesNameLocked(true);
    monitorDataSet_1.setSeriesName("[X]- CoG Mean");

    MonitorDataSet monitorDataSet_2 = 
      ((MonitorDataSet) monitorPlot_0.getDataSetManager().getDataSet("[CLoadX]- Ground"));
    monitorDataSet_2.setSeriesNameLocked(true);
    monitorDataSet_2.setSeriesName("[X]- Ground");

    MonitorDataSet monitorDataSet_3 = 
      ((MonitorDataSet) monitorPlot_0.getDataSetManager().getDataSet("[CLoadX]- Ground Mean Monitor"));
    monitorDataSet_3.setSeriesNameLocked(true);
    monitorDataSet_3.setSeriesName("[X]- Ground Mean");

    MonitorDataSet monitorDataSet_4 = 
      ((MonitorDataSet) monitorPlot_0.getDataSetManager().getDataSet("[CLoadY]- CoG"));
    monitorDataSet_4.setSeriesNameLocked(true);
    monitorDataSet_4.setSeriesName("[Y]- CoG");

    MonitorDataSet monitorDataSet_5 = 
      ((MonitorDataSet) monitorPlot_0.getDataSetManager().getDataSet("[CLoadY]- CoG Mean Monitor"));
    monitorDataSet_5.setSeriesNameLocked(true);
    monitorDataSet_5.setSeriesName("[Y]- CoG Mean");

    MonitorDataSet monitorDataSet_6 = 
      ((MonitorDataSet) monitorPlot_0.getDataSetManager().getDataSet("[CLoadY]- Ground"));
    monitorDataSet_6.setSeriesNameLocked(true);
    monitorDataSet_6.setSeriesName("[Y]- Ground");

    MonitorDataSet monitorDataSet_7 = 
      ((MonitorDataSet) monitorPlot_0.getDataSetManager().getDataSet("[CLoadY]- Ground Mean Monitor"));
    monitorDataSet_7.setSeriesNameLocked(true);
    monitorDataSet_7.setSeriesName("[Y]- Ground Mean");

    MonitorDataSet monitorDataSet_8 = 
      ((MonitorDataSet) monitorPlot_0.getDataSetManager().getDataSet("[CLoadZ]- CoG"));
    monitorDataSet_8.setSeriesNameLocked(true);
    monitorDataSet_8.setSeriesName("[Z]- CoG");

    MonitorDataSet monitorDataSet_9 = 
      ((MonitorDataSet) monitorPlot_0.getDataSetManager().getDataSet("[CLoadZ]- CoG Mean Monitor"));
    monitorDataSet_9.setSeriesNameLocked(true);
    monitorDataSet_9.setSeriesName("[Z]- CoG Mean");

    MonitorDataSet monitorDataSet_10 = 
      ((MonitorDataSet) monitorPlot_0.getDataSetManager().getDataSet("[CLoadZ]- Ground"));
    monitorDataSet_10.setSeriesNameLocked(true);
    monitorDataSet_10.setSeriesName("[Z]- Ground");

    MonitorDataSet monitorDataSet_11 = 
      ((MonitorDataSet) monitorPlot_0.getDataSetManager().getDataSet("[CLoadZ]- Ground Mean Monitor"));
    monitorDataSet_11.setSeriesNameLocked(true);
    monitorDataSet_11.setSeriesName("[Z]- Ground Mean");

    MultiColLegend multiColLegend_0 = 
      monitorPlot_0.getLegend();
    multiColLegend_0.getChartPositionOption().setSelected(ChartPositionOption.Type.CUSTOM);
    multiColLegend_0.setRelativeYPosition(0.25);
	monitorPlot_0.close();
	//}
	
	//{ // Cooling PLOT

    ReportMonitor reportMonitor_12 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Mass Flow] - Fan (R)"));

    MonitorPlot monitorPlot_1 = 
      simulation_0.getPlotManager().createMonitorPlot(new NeoObjectVector(new Object[] {reportMonitor_12}), "[Mass Flow] - Fan (R) Plot");
    monitorPlot_1.open();
    monitorPlot_1.setPresentationName("[Cooling]");
    monitorPlot_1.setTitle("Cooling");

    Cartesian2DAxisManager cartesian2DAxisManager_1 = 
      ((Cartesian2DAxisManager) monitorPlot_1.getAxisManager());
    Cartesian2DAxis cartesian2DAxis_1 = 
      ((Cartesian2DAxis) cartesian2DAxisManager_1.getAxis("Left Axis"));

    AxisTitle axisTitle_1 = 
      cartesian2DAxis_1.getTitle();
    axisTitle_1.setText("Mass Flow (kg/s)");

    cartesian2DAxis_1.setMinimum(0.115);
    cartesian2DAxis_1.setMaximum(0.15);

    MonitorDataSet monitorDataSet_13 = 
      ((MonitorDataSet) monitorPlot_1.getDataSetManager().getDataSet("[Mass Flow] - Fan (R)"));
    monitorDataSet_13.setSeriesNameLocked(true);
    monitorDataSet_13.setSeriesName("[MFlow] Fan (R)");
	
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ReportMonitor reportMonitor_13 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[MFlow] - Fan (L) Mean Monitor"));}
    ReportMonitor reportMonitor_14 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[MFlow] - Fan (R) Mean Monitor"));
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ReportMonitor reportMonitor_15 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[MFlow] - Radiator (L) Mean Monitor"));}
    ReportMonitor reportMonitor_16 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[MFlow] - Radiator (R) Mean Monitor"));
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ReportMonitor reportMonitor_17 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Mass Flow] - Fan (L)"));
    ReportMonitor reportMonitor_18 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Mass Flow] - Radiator (L)"));}
    ReportMonitor reportMonitor_19 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Mass Flow] - Radiator (R)"));

	if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
	monitorPlot_1.getDataSetManager().addDataProviders(new NeoObjectVector(new Object[] {reportMonitor_14, reportMonitor_16, reportMonitor_19}));
	} else {
	ReportMonitor reportMonitor_13 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[MFlow] - Fan (L) Mean Monitor"));
	ReportMonitor reportMonitor_15 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[MFlow] - Radiator (L) Mean Monitor"));
	ReportMonitor reportMonitor_17 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Mass Flow] - Fan (L)"));
	ReportMonitor reportMonitor_18 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Mass Flow] - Radiator (L)"));
		
    monitorPlot_1.getDataSetManager().addDataProviders(new NeoObjectVector(new Object[] {reportMonitor_13, reportMonitor_14, reportMonitor_15, reportMonitor_16, reportMonitor_17, reportMonitor_18, reportMonitor_19}));
	}
	
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    MonitorDataSet monitorDataSet_14 = 
      ((MonitorDataSet) monitorPlot_1.getDataSetManager().getDataSet("[Mass Flow] - Fan (L)"));
    monitorDataSet_14.setSeriesNameLocked(true);
    monitorDataSet_14.setSeriesName("[MFlow] Fan (L)");

    MonitorDataSet monitorDataSet_15 = 
      ((MonitorDataSet) monitorPlot_1.getDataSetManager().getDataSet("[MFlow] - Fan (L) Mean Monitor"));
    monitorDataSet_15.setSeriesNameLocked(true);
    monitorDataSet_15.setSeriesName("[MFlow] Fan (L) Mean");}

    MonitorDataSet monitorDataSet_16 = 
      ((MonitorDataSet) monitorPlot_1.getDataSetManager().getDataSet("[MFlow] - Fan (R) Mean Monitor"));
    monitorDataSet_16.setSeriesNameLocked(true);
    monitorDataSet_16.setSeriesName("[MFlow] Fan (R) Mean");

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    MonitorDataSet monitorDataSet_17 = 
      ((MonitorDataSet) monitorPlot_1.getDataSetManager().getDataSet("[MFlow] - Radiator (L) Mean Monitor"));
    monitorDataSet_17.setSeriesNameLocked(true);
    monitorDataSet_17.setSeriesName("[MFlow] Radiator (L) Mean");}

    MonitorDataSet monitorDataSet_18 = 
      ((MonitorDataSet) monitorPlot_1.getDataSetManager().getDataSet("[MFlow] - Radiator (R) Mean Monitor"));
    monitorDataSet_18.setSeriesNameLocked(true);
    monitorDataSet_18.setSeriesName("[MFlow] Radiator (R) Mean");

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    MonitorDataSet monitorDataSet_19 = 
      ((MonitorDataSet) monitorPlot_1.getDataSetManager().getDataSet("[Mass Flow] - Radiator (L)"));
    monitorDataSet_19.setSeriesNameLocked(true);
    monitorDataSet_19.setSeriesName("[MFlow] Radiator (L)");}

    MonitorDataSet monitorDataSet_20 = 
      ((MonitorDataSet) monitorPlot_1.getDataSetManager().getDataSet("[Mass Flow] - Radiator (R)"));
    monitorDataSet_20.setSeriesNameLocked(true);
    monitorDataSet_20.setSeriesName("[MFlow] Radiator (R)");

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ReportMonitor reportMonitor_20 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[PDrop] - Fan (L) Mean Monitor"));}
    ReportMonitor reportMonitor_21 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[PDrop] - Fan (R) Mean Monitor"));
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ReportMonitor reportMonitor_22 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[PDrop] - Radiator (L) Mean Monitor"));}
    ReportMonitor reportMonitor_23 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[PDrop] - Radiator (R) Mean Monitor"));
    if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
	ReportMonitor reportMonitor_24 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[PDrop] - Fan (L)"));}
    ReportMonitor reportMonitor_25 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[PDrop] - Fan (R)"));
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ReportMonitor reportMonitor_26 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[PDrop] - Radiator (L)"));}
    ReportMonitor reportMonitor_27 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[PDrop] - Radiator (R)"));

	if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
	monitorPlot_1.getDataSetManager().addDataProviders(new NeoObjectVector(new Object[] {reportMonitor_21, reportMonitor_23, reportMonitor_25, reportMonitor_27}));
	} else {
	ReportMonitor reportMonitor_20 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[PDrop] - Fan (L) Mean Monitor"));
    ReportMonitor reportMonitor_22 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[PDrop] - Radiator (L) Mean Monitor"));
    ReportMonitor reportMonitor_24 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[PDrop] - Fan (L)"));
    ReportMonitor reportMonitor_26 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[PDrop] - Radiator (L)"));
    monitorPlot_1.getDataSetManager().addDataProviders(new NeoObjectVector(new Object[] {reportMonitor_20, reportMonitor_21, reportMonitor_22, reportMonitor_23, reportMonitor_24, reportMonitor_25, reportMonitor_26, reportMonitor_27}));
	}
	
    Cartesian2DAxis cartesian2DAxis_2 = 
      (Cartesian2DAxis) cartesian2DAxisManager_1.createAxis(Cartesian2DAxis.Position.Right);
    cartesian2DAxis_2.setLockMinimum(true);
    cartesian2DAxis_2.setMaximum(90.0);

    AxisTitle axisTitle_2 = 
      cartesian2DAxis_2.getTitle();
    axisTitle_2.setText("Pressure Drop (Pa)");
	
	
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    MonitorDataSet monitorDataSet_21 = 
      ((MonitorDataSet) monitorPlot_1.getDataSetManager().getDataSet("[PDrop] - Fan (L)"));
    monitorDataSet_21.setYAxis(cartesian2DAxis_2);
    monitorDataSet_21.setSeriesNameLocked(true);
    monitorDataSet_21.setSeriesName("[PDrop] Fan (L)");}

    MonitorDataSet monitorDataSet_22 = 
      ((MonitorDataSet) monitorPlot_1.getDataSetManager().getDataSet("[PDrop] - Fan (R)"));
    monitorDataSet_22.setYAxis(cartesian2DAxis_2);
	monitorDataSet_22.setSeriesNameLocked(true);
    monitorDataSet_22.setSeriesName("[PDrop] Fan (R)");

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    MonitorDataSet monitorDataSet_23 = 
      ((MonitorDataSet) monitorPlot_1.getDataSetManager().getDataSet("[PDrop] - Radiator (L)"));
    monitorDataSet_23.setYAxis(cartesian2DAxis_2);
    monitorDataSet_23.setSeriesNameLocked(true);
    monitorDataSet_23.setSeriesName("[PDrop] Radiator (L)");}

    MonitorDataSet monitorDataSet_24 = 
      ((MonitorDataSet) monitorPlot_1.getDataSetManager().getDataSet("[PDrop] - Radiator (R)"));
    monitorDataSet_24.setYAxis(cartesian2DAxis_2);
    monitorDataSet_24.setSeriesNameLocked(true);
    monitorDataSet_24.setSeriesName("[PDrop] Radiator (R)");

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    MonitorDataSet monitorDataSet_25 = 
      ((MonitorDataSet) monitorPlot_1.getDataSetManager().getDataSet("[PDrop] - Fan (L) Mean Monitor"));
    monitorDataSet_25.setYAxis(cartesian2DAxis_2);
    monitorDataSet_25.setSeriesNameLocked(true);
    monitorDataSet_25.setSeriesName("[PDrop] Fan (L) Mean");}

    MonitorDataSet monitorDataSet_26 = 
      ((MonitorDataSet) monitorPlot_1.getDataSetManager().getDataSet("[PDrop] - Fan (R) Mean Monitor"));
    monitorDataSet_26.setYAxis(cartesian2DAxis_2);
    monitorDataSet_26.setSeriesNameLocked(true);
    monitorDataSet_26.setSeriesName("[PDrop] Fan (R) Mean");

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    MonitorDataSet monitorDataSet_27 = 
      ((MonitorDataSet) monitorPlot_1.getDataSetManager().getDataSet("[PDrop] - Radiator (L) Mean Monitor"));
    monitorDataSet_27.setYAxis(cartesian2DAxis_2);
    monitorDataSet_27.setSeriesNameLocked(true);
    monitorDataSet_27.setSeriesName("[PDrop] Radiator (L) Mean");}

    MonitorDataSet monitorDataSet_28 = 
      ((MonitorDataSet) monitorPlot_1.getDataSetManager().getDataSet("[PDrop] - Radiator (R) Mean Monitor"));
    monitorDataSet_28.setYAxis(cartesian2DAxis_2);
    monitorDataSet_28.setSeriesNameLocked(true);
    monitorDataSet_28.setSeriesName("[PDrop] Radiator (R) Mean");

    MultiColLegend multiColLegend_1 = 
      monitorPlot_1.getLegend();
    multiColLegend_1.getChartPositionOption().setSelected(ChartPositionOption.Type.SOUTH);
    multiColLegend_1.setLegendLayout(MultiColLegend.LegendLayout.HORIZONTAL);
    multiColLegend_1.setNumRows(2);

	if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
	monitorPlot_1.setDataSeriesOrder(new NeoObjectVector(new Object[] {monitorDataSet_13, monitorDataSet_16, monitorDataSet_20, monitorDataSet_18, monitorDataSet_22, monitorDataSet_26, monitorDataSet_24, monitorDataSet_28}));
	} else {
	MonitorDataSet monitorDataSet_14 = 
      ((MonitorDataSet) monitorPlot_1.getDataSetManager().getDataSet("[Mass Flow] - Fan (L)"));
    MonitorDataSet monitorDataSet_15 = 
      ((MonitorDataSet) monitorPlot_1.getDataSetManager().getDataSet("[MFlow] - Fan (L) Mean Monitor"));
    MonitorDataSet monitorDataSet_17 = 
      ((MonitorDataSet) monitorPlot_1.getDataSetManager().getDataSet("[MFlow] - Radiator (L) Mean Monitor"));
    MonitorDataSet monitorDataSet_19 = 
      ((MonitorDataSet) monitorPlot_1.getDataSetManager().getDataSet("[Mass Flow] - Radiator (L)"));
    MonitorDataSet monitorDataSet_21 = 
      ((MonitorDataSet) monitorPlot_1.getDataSetManager().getDataSet("[PDrop] - Fan (L)"));
    MonitorDataSet monitorDataSet_23 = 
      ((MonitorDataSet) monitorPlot_1.getDataSetManager().getDataSet("[PDrop] - Radiator (L)"));
    MonitorDataSet monitorDataSet_25 = 
      ((MonitorDataSet) monitorPlot_1.getDataSetManager().getDataSet("[PDrop] - Fan (L) Mean Monitor"));
    MonitorDataSet monitorDataSet_27 = 
      ((MonitorDataSet) monitorPlot_1.getDataSetManager().getDataSet("[PDrop] - Radiator (L) Mean Monitor"));
    	
    monitorPlot_1.setDataSeriesOrder(new NeoObjectVector(new Object[] {monitorDataSet_14, monitorDataSet_15, monitorDataSet_13, monitorDataSet_16, monitorDataSet_19, monitorDataSet_17, monitorDataSet_20, monitorDataSet_18, monitorDataSet_21, monitorDataSet_25, monitorDataSet_22, monitorDataSet_26, monitorDataSet_23, monitorDataSet_27, monitorDataSet_24, monitorDataSet_28}));
	}
	monitorPlot_1.close();
	
    multiColLegend_1.setItemSpacing(4);
    multiColLegend_1.getChartPositionOption().setSelected(ChartPositionOption.Type.SOUTH);
	//}
	
	//{ // DownForce PLOT

    ReportMonitor reportMonitor_28 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[DownF] - Total Monitor"));

    MonitorPlot monitorPlot_2 = 
      simulation_0.getPlotManager().createMonitorPlot(new NeoObjectVector(new Object[] {reportMonitor_28}), "[DownF] - Total Monitor Plot");

    monitorPlot_2.open();
    monitorPlot_2.setPresentationName("[DownF]");
    monitorPlot_2.setTitle("DownF");

    MonitorDataSet monitorDataSet_29 = 
      ((MonitorDataSet) monitorPlot_2.getDataSetManager().getDataSet("[DownF] - Total Monitor"));
    monitorDataSet_29.setSeriesNameLocked(true);
    monitorDataSet_29.setSeriesName("Total");

    Cartesian2DAxisManager cartesian2DAxisManager_2 = 
      ((Cartesian2DAxisManager) monitorPlot_2.getAxisManager());
    Cartesian2DAxis cartesian2DAxis_3 = 
      ((Cartesian2DAxis) cartesian2DAxisManager_2.getAxis("Left Axis"));
    cartesian2DAxis_3.setLockMinimum(true);
    cartesian2DAxis_3.setMaximum(300.0);

    AxisTitle axisTitle_3 = 
      cartesian2DAxis_3.getTitle();
    axisTitle_3.setText("Force (N)");

    ReportMonitor reportMonitor_29 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[DownF] - BCKDiff Mean Monitor"));
    ReportMonitor reportMonitor_30 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[DownF] - FW Mean Monitor"));
    ReportMonitor reportMonitor_31 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[DownF] - Mono Mean Monitor"));
    ReportMonitor reportMonitor_32 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[DownF] - RW Mean Monitor"));
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ReportMonitor reportMonitor_33 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[DownF] - SC(L) Mean Monitor"));}
    ReportMonitor reportMonitor_34 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[DownF] - SC(R) Mean Monitor"));
    ReportMonitor reportMonitor_35 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[DownF] - Total Mean Monitor"));
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
	ReportMonitor reportMonitor_36 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[DownF] - UB(L) Mean Monitor"));}
    ReportMonitor reportMonitor_37 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[DownF] - UB(R) Mean Monitor"));
    ReportMonitor reportMonitor_38 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[DownF] - BCKDiff Monitor"));
    ReportMonitor reportMonitor_39 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[DownF] - FW Monitor"));
    ReportMonitor reportMonitor_40 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[DownF] - Mono Monitor"));
    ReportMonitor reportMonitor_41 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[DownF] - RW Monitor"));
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ReportMonitor reportMonitor_42 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[DownF] - SC(L) Monitor"));}
    ReportMonitor reportMonitor_43 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[DownF] - SC(R) Monitor"));
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ReportMonitor reportMonitor_44 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[DownF] - UB(L) Monitor"));}
    ReportMonitor reportMonitor_45 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[DownF] - UB(R) Monitor"));

	if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
	monitorPlot_2.getDataSetManager().addDataProviders(new NeoObjectVector(new Object[] {reportMonitor_29, reportMonitor_30, reportMonitor_31, reportMonitor_32, reportMonitor_34, reportMonitor_35, reportMonitor_37, reportMonitor_38, reportMonitor_39, reportMonitor_40, reportMonitor_41, reportMonitor_43, reportMonitor_45}));
	} else {
	ReportMonitor reportMonitor_33 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[DownF] - SC(L) Mean Monitor"));
	ReportMonitor reportMonitor_36 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[DownF] - UB(L) Mean Monitor"));
	ReportMonitor reportMonitor_42 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[DownF] - SC(L) Monitor"));
	ReportMonitor reportMonitor_44 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[DownF] - UB(L) Monitor"));
		
    monitorPlot_2.getDataSetManager().addDataProviders(new NeoObjectVector(new Object[] {reportMonitor_29, reportMonitor_30, reportMonitor_31, reportMonitor_32, reportMonitor_33, reportMonitor_34, reportMonitor_35, reportMonitor_36, reportMonitor_37, reportMonitor_38, reportMonitor_39, reportMonitor_40, reportMonitor_41, reportMonitor_42, reportMonitor_43, reportMonitor_44, reportMonitor_45}));
	}
    MonitorDataSet monitorDataSet_30 = 
      ((MonitorDataSet) monitorPlot_2.getDataSetManager().getDataSet("[DownF] - BCKDiff Mean Monitor"));
    monitorDataSet_30.setSeriesNameLocked(true);
    monitorDataSet_30.setSeriesName("BCKDiff Mean");

    MonitorDataSet monitorDataSet_31 = 
      ((MonitorDataSet) monitorPlot_2.getDataSetManager().getDataSet("[DownF] - BCKDiff Monitor"));
    monitorDataSet_31.setSeriesNameLocked(true);
    monitorDataSet_31.setSeriesName("BCKDiff");

    MonitorDataSet monitorDataSet_32 = 
      ((MonitorDataSet) monitorPlot_2.getDataSetManager().getDataSet("[DownF] - FW Mean Monitor"));
    monitorDataSet_32.setSeriesNameLocked(true);
    monitorDataSet_32.setSeriesName("FW Mean");

    MonitorDataSet monitorDataSet_33 = 
      ((MonitorDataSet) monitorPlot_2.getDataSetManager().getDataSet("[DownF] - FW Monitor"));
    monitorDataSet_33.setSeriesNameLocked(true);
    monitorDataSet_33.setSeriesName("FW");

    MonitorDataSet monitorDataSet_34 = 
      ((MonitorDataSet) monitorPlot_2.getDataSetManager().getDataSet("[DownF] - Mono Mean Monitor"));
    monitorDataSet_34.setSeriesNameLocked(true);
    monitorDataSet_34.setSeriesName("Mono Mean");

    MonitorDataSet monitorDataSet_35 = 
      ((MonitorDataSet) monitorPlot_2.getDataSetManager().getDataSet("[DownF] - Mono Monitor"));
    monitorDataSet_35.setSeriesNameLocked(true);
    monitorDataSet_35.setSeriesName("Mono");

    MonitorDataSet monitorDataSet_36 = 
      ((MonitorDataSet) monitorPlot_2.getDataSetManager().getDataSet("[DownF] - RW Mean Monitor"));
    monitorDataSet_36.setSeriesNameLocked(true);
    monitorDataSet_36.setSeriesName("RW Mean");

    MonitorDataSet monitorDataSet_37 = 
      ((MonitorDataSet) monitorPlot_2.getDataSetManager().getDataSet("[DownF] - RW Monitor"));
    monitorDataSet_37.setSeriesNameLocked(true);
    monitorDataSet_37.setSeriesName("RW");

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    MonitorDataSet monitorDataSet_38 = 
      ((MonitorDataSet) monitorPlot_2.getDataSetManager().getDataSet("[DownF] - SC(L) Mean Monitor"));
    monitorDataSet_38.setSeriesNameLocked(true);
    monitorDataSet_38.setSeriesName("SC(L) Mean");

    MonitorDataSet monitorDataSet_39 = 
      ((MonitorDataSet) monitorPlot_2.getDataSetManager().getDataSet("[DownF] - SC(L) Monitor"));
    monitorDataSet_39.setSeriesNameLocked(true);
    monitorDataSet_39.setSeriesName("SC(L)");}

    MonitorDataSet monitorDataSet_40 = 
      ((MonitorDataSet) monitorPlot_2.getDataSetManager().getDataSet("[DownF] - SC(R) Mean Monitor"));
    monitorDataSet_40.setSeriesNameLocked(true);
    monitorDataSet_40.setSeriesName("SC(R) Mean");

    MonitorDataSet monitorDataSet_41 = 
      ((MonitorDataSet) monitorPlot_2.getDataSetManager().getDataSet("[DownF] - SC(R) Monitor"));
    monitorDataSet_41.setSeriesNameLocked(true);
    monitorDataSet_41.setSeriesName("SC(R)");

    MonitorDataSet monitorDataSet_42 = 
      ((MonitorDataSet) monitorPlot_2.getDataSetManager().getDataSet("[DownF] - Total Mean Monitor"));
    monitorDataSet_42.setSeriesNameLocked(true);
    monitorDataSet_42.setSeriesName("Total Mean");

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    MonitorDataSet monitorDataSet_43 = 
      ((MonitorDataSet) monitorPlot_2.getDataSetManager().getDataSet("[DownF] - UB(L) Mean Monitor"));
    monitorDataSet_43.setSeriesNameLocked(true);
    monitorDataSet_43.setSeriesName("UB(L) Mean");

    MonitorDataSet monitorDataSet_44 = 
      ((MonitorDataSet) monitorPlot_2.getDataSetManager().getDataSet("[DownF] - UB(L) Monitor"));
    monitorDataSet_44.setSeriesNameLocked(true);
    monitorDataSet_44.setSeriesName("UB(L)");}

    MonitorDataSet monitorDataSet_45 = 
      ((MonitorDataSet) monitorPlot_2.getDataSetManager().getDataSet("[DownF] - UB(R) Mean Monitor"));
    monitorDataSet_45.setSeriesNameLocked(true);
    monitorDataSet_45.setSeriesName("UB(R) Mean");

    MonitorDataSet monitorDataSet_46 = 
      ((MonitorDataSet) monitorPlot_2.getDataSetManager().getDataSet("[DownF] - UB(R) Monitor"));
    monitorDataSet_46.setSeriesNameLocked(true);
    monitorDataSet_46.setSeriesName("UB(R)");

	if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
	monitorPlot_2.setDataSeriesOrder(new NeoObjectVector(new Object[] {monitorDataSet_29, monitorDataSet_42, monitorDataSet_33, monitorDataSet_32, monitorDataSet_37, monitorDataSet_36, monitorDataSet_46, monitorDataSet_45, monitorDataSet_31, monitorDataSet_30, monitorDataSet_41, monitorDataSet_40, monitorDataSet_35, monitorDataSet_34}));
	} else {
	MonitorDataSet monitorDataSet_38 = 
      ((MonitorDataSet) monitorPlot_2.getDataSetManager().getDataSet("[DownF] - SC(L) Mean Monitor"));
	MonitorDataSet monitorDataSet_39 = 
      ((MonitorDataSet) monitorPlot_2.getDataSetManager().getDataSet("[DownF] - SC(L) Monitor"));
    MonitorDataSet monitorDataSet_43 = 
      ((MonitorDataSet) monitorPlot_2.getDataSetManager().getDataSet("[DownF] - UB(L) Mean Monitor"));	
	MonitorDataSet monitorDataSet_44 = 
      ((MonitorDataSet) monitorPlot_2.getDataSetManager().getDataSet("[DownF] - UB(L) Monitor"));
		
    monitorPlot_2.setDataSeriesOrder(new NeoObjectVector(new Object[] {monitorDataSet_29, monitorDataSet_42, monitorDataSet_33, monitorDataSet_32, monitorDataSet_37, monitorDataSet_36, monitorDataSet_44, monitorDataSet_43, monitorDataSet_46, monitorDataSet_45, monitorDataSet_31, monitorDataSet_30, monitorDataSet_39, monitorDataSet_38, monitorDataSet_41, monitorDataSet_40, monitorDataSet_35, monitorDataSet_34}));
	}
	monitorPlot_2.close();
    MultiColLegend multiColLegend_2 = 
      monitorPlot_2.getLegend();
    multiColLegend_2.getChartPositionOption().setSelected(ChartPositionOption.Type.SOUTH);
    multiColLegend_2.setLegendLayout(MultiColLegend.LegendLayout.HORIZONTAL);
    multiColLegend_2.setNumRows(2);
	//}
	
	//{ // Drag Force PLOT 

    ReportMonitor reportMonitor_46 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Drag] - Total Monitor"));

    MonitorPlot monitorPlot_3 = 
      simulation_0.getPlotManager().createMonitorPlot(new NeoObjectVector(new Object[] {reportMonitor_46}), "[Drag] - Total Monitor Plot");
    monitorPlot_3.open();
    monitorPlot_3.setPresentationName("[Drag]");
    monitorPlot_3.setTitle("Drag");

    MonitorDataSet monitorDataSet_47 = 
      ((MonitorDataSet) monitorPlot_3.getDataSetManager().getDataSet("[Drag] - Total Monitor"));
    monitorDataSet_47.setSeriesNameLocked(true);
    monitorDataSet_47.setSeriesName("Total");

    Cartesian2DAxisManager cartesian2DAxisManager_3 = 
      ((Cartesian2DAxisManager) monitorPlot_3.getAxisManager());
    Cartesian2DAxis cartesian2DAxis_4 = 
      ((Cartesian2DAxis) cartesian2DAxisManager_3.getAxis("Left Axis"));
    cartesian2DAxis_4.setLockMinimum(true);
    cartesian2DAxis_4.setMaximum(150.0);

    AxisTitle axisTitle_4 = 
      cartesian2DAxis_4.getTitle();
    axisTitle_4.setText("Force (N)");

    ReportMonitor reportMonitor_47 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Drag] - BCKDiff Mean Monitor"));
    ReportMonitor reportMonitor_48 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Drag] - FW Mean Monitor"));
    ReportMonitor reportMonitor_49 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Drag] - Mono Mean Monitor"));
    ReportMonitor reportMonitor_50 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Drag] - RW Mean Monitor"));
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ReportMonitor reportMonitor_51 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Drag] - SC(L) Mean Monitor"));}
    ReportMonitor reportMonitor_52 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Drag] - SC(R) Mean Monitor"));
    ReportMonitor reportMonitor_53 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Drag] - Total Mean Monitor"));
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ReportMonitor reportMonitor_54 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Drag] - UB(L) Mean Monitor"));}
    ReportMonitor reportMonitor_55 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Drag] - UB(R) Mean Monitor"));
    ReportMonitor reportMonitor_56 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Drag] - BCKDiff Monitor"));
    ReportMonitor reportMonitor_57 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Drag] - FW Monitor"));
    ReportMonitor reportMonitor_58 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Drag] - Mono Monitor"));
    ReportMonitor reportMonitor_59 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Drag] - RW Monitor"));
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
	ReportMonitor reportMonitor_60 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Drag] - SC(L) Monitor"));}
    ReportMonitor reportMonitor_61 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Drag] - SC(R) Monitor"));
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ReportMonitor reportMonitor_62 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Drag] - UB(L) Monitor"));}
    ReportMonitor reportMonitor_63 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Drag] - UB(R) Monitor"));

	if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
	monitorPlot_3.getDataSetManager().addDataProviders(new NeoObjectVector(new Object[] {reportMonitor_47, reportMonitor_48, reportMonitor_49, reportMonitor_50, reportMonitor_52, reportMonitor_53, reportMonitor_55, reportMonitor_56, reportMonitor_57, reportMonitor_58, reportMonitor_59, reportMonitor_61, reportMonitor_63}));
	} else {
	ReportMonitor reportMonitor_51 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Drag] - SC(L) Mean Monitor"));	
	ReportMonitor reportMonitor_54 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Drag] - UB(L) Mean Monitor"));	
	ReportMonitor reportMonitor_60 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Drag] - SC(L) Monitor"));	
	ReportMonitor reportMonitor_62 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Drag] - UB(L) Monitor"));	

    monitorPlot_3.getDataSetManager().addDataProviders(new NeoObjectVector(new Object[] {reportMonitor_47, reportMonitor_48, reportMonitor_49, reportMonitor_50, reportMonitor_51, reportMonitor_52, reportMonitor_53, reportMonitor_54, reportMonitor_55, reportMonitor_56, reportMonitor_57, reportMonitor_58, reportMonitor_59, reportMonitor_60, reportMonitor_61, reportMonitor_62, reportMonitor_63}));
	}
    MonitorDataSet monitorDataSet_48 = 
      ((MonitorDataSet) monitorPlot_3.getDataSetManager().getDataSet("[Drag] - BCKDiff Mean Monitor"));
    monitorDataSet_48.setSeriesNameLocked(true);
    monitorDataSet_48.setSeriesName("BCKDiff Mean");

    MonitorDataSet monitorDataSet_49 = 
      ((MonitorDataSet) monitorPlot_3.getDataSetManager().getDataSet("[Drag] - BCKDiff Monitor"));
    monitorDataSet_49.setSeriesNameLocked(true);
    monitorDataSet_49.setSeriesName("BCKDiff");

    MonitorDataSet monitorDataSet_50 = 
      ((MonitorDataSet) monitorPlot_3.getDataSetManager().getDataSet("[Drag] - FW Mean Monitor"));
    monitorDataSet_50.setSeriesNameLocked(true);
    monitorDataSet_50.setSeriesName("FW Mean");

    MonitorDataSet monitorDataSet_51 = 
      ((MonitorDataSet) monitorPlot_3.getDataSetManager().getDataSet("[Drag] - FW Monitor"));
    monitorDataSet_51.setSeriesNameLocked(true);
    monitorDataSet_51.setSeriesName("FW");

    MonitorDataSet monitorDataSet_52 = 
      ((MonitorDataSet) monitorPlot_3.getDataSetManager().getDataSet("[Drag] - Mono Mean Monitor"));
    monitorDataSet_52.setSeriesNameLocked(true);
    monitorDataSet_52.setSeriesName("Mono Mean");

    MonitorDataSet monitorDataSet_53 = 
      ((MonitorDataSet) monitorPlot_3.getDataSetManager().getDataSet("[Drag] - Mono Monitor"));
    monitorDataSet_53.setSeriesNameLocked(true);
    monitorDataSet_53.setSeriesName("Mono");

    MonitorDataSet monitorDataSet_54 = 
      ((MonitorDataSet) monitorPlot_3.getDataSetManager().getDataSet("[Drag] - RW Mean Monitor"));
    monitorDataSet_54.setSeriesNameLocked(true);
    monitorDataSet_54.setSeriesName("RW Mean");

    MonitorDataSet monitorDataSet_55 = 
      ((MonitorDataSet) monitorPlot_3.getDataSetManager().getDataSet("[Drag] - RW Monitor"));
    monitorDataSet_55.setSeriesNameLocked(true);
    monitorDataSet_55.setSeriesName("RW");

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    MonitorDataSet monitorDataSet_56 = 
      ((MonitorDataSet) monitorPlot_3.getDataSetManager().getDataSet("[Drag] - SC(L) Mean Monitor"));
    monitorDataSet_56.setSeriesNameLocked(true);
    monitorDataSet_56.setSeriesName("SC(L) Mean");

    MonitorDataSet monitorDataSet_57 = 
      ((MonitorDataSet) monitorPlot_3.getDataSetManager().getDataSet("[Drag] - SC(L) Monitor"));
    monitorDataSet_57.setSeriesNameLocked(true);
    monitorDataSet_57.setSeriesName("SC(L)");}

    MonitorDataSet monitorDataSet_58 = 
      ((MonitorDataSet) monitorPlot_3.getDataSetManager().getDataSet("[Drag] - SC(R) Mean Monitor"));
    monitorDataSet_58.setSeriesNameLocked(true);
    monitorDataSet_58.setSeriesName("SC(R) Mean");

    MonitorDataSet monitorDataSet_59 = 
      ((MonitorDataSet) monitorPlot_3.getDataSetManager().getDataSet("[Drag] - SC(R) Monitor"));
    monitorDataSet_59.setSeriesNameLocked(true);
    monitorDataSet_59.setSeriesName("SC(R)");

    MonitorDataSet monitorDataSet_60 = 
      ((MonitorDataSet) monitorPlot_3.getDataSetManager().getDataSet("[Drag] - Total Mean Monitor"));
    monitorDataSet_60.setSeriesNameLocked(true);
    monitorDataSet_60.setSeriesName("Total Mean");

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    MonitorDataSet monitorDataSet_61 = 
      ((MonitorDataSet) monitorPlot_3.getDataSetManager().getDataSet("[Drag] - UB(L) Mean Monitor"));
    monitorDataSet_61.setSeriesNameLocked(true);
    monitorDataSet_61.setSeriesName("UB(L) Mean");

    MonitorDataSet monitorDataSet_62 = 
      ((MonitorDataSet) monitorPlot_3.getDataSetManager().getDataSet("[Drag] - UB(L) Monitor"));
    monitorDataSet_62.setSeriesNameLocked(true);
    monitorDataSet_62.setSeriesName("UB(L)");}

    MonitorDataSet monitorDataSet_63 = 
      ((MonitorDataSet) monitorPlot_3.getDataSetManager().getDataSet("[Drag] - UB(R) Mean Monitor"));
    monitorDataSet_63.setSeriesNameLocked(true);
    monitorDataSet_63.setSeriesName("UB(R) Mean");

    MonitorDataSet monitorDataSet_64 = 
      ((MonitorDataSet) monitorPlot_3.getDataSetManager().getDataSet("[Drag] - UB(R) Monitor"));
    monitorDataSet_64.setSeriesNameLocked(true);
    monitorDataSet_64.setSeriesName("UB(R)");

    MultiColLegend multiColLegend_3 = 
      monitorPlot_3.getLegend();
    multiColLegend_3.getChartPositionOption().setSelected(ChartPositionOption.Type.SOUTH);
    multiColLegend_3.setLegendLayout(MultiColLegend.LegendLayout.HORIZONTAL);
    multiColLegend_3.setNumRows(2);

	if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
	monitorPlot_3.setDataSeriesOrder(new NeoObjectVector(new Object[] {monitorDataSet_47, monitorDataSet_60, monitorDataSet_51, monitorDataSet_50, monitorDataSet_55, monitorDataSet_54, monitorDataSet_64, monitorDataSet_63, monitorDataSet_49, monitorDataSet_48, monitorDataSet_59, monitorDataSet_58, monitorDataSet_53, monitorDataSet_52}));
	} else {
	MonitorDataSet monitorDataSet_61 = 
      ((MonitorDataSet) monitorPlot_3.getDataSetManager().getDataSet("[Drag] - UB(L) Mean Monitor"));
	MonitorDataSet monitorDataSet_62 = 
      ((MonitorDataSet) monitorPlot_3.getDataSetManager().getDataSet("[Drag] - UB(L) Monitor"));	
	MonitorDataSet monitorDataSet_56 = 
      ((MonitorDataSet) monitorPlot_3.getDataSetManager().getDataSet("[Drag] - SC(L) Mean Monitor"));	
	MonitorDataSet monitorDataSet_57 = 
      ((MonitorDataSet) monitorPlot_3.getDataSetManager().getDataSet("[Drag] - SC(L) Monitor"));	
		
    monitorPlot_3.setDataSeriesOrder(new NeoObjectVector(new Object[] {monitorDataSet_47, monitorDataSet_60, monitorDataSet_51, monitorDataSet_50, monitorDataSet_55, monitorDataSet_54, monitorDataSet_62, monitorDataSet_61, monitorDataSet_64, monitorDataSet_63, monitorDataSet_49, monitorDataSet_48, monitorDataSet_57, monitorDataSet_56, monitorDataSet_59, monitorDataSet_58, monitorDataSet_53, monitorDataSet_52}));
	}
	
	monitorPlot_3.close();
	//}
	
	//{ // Side Force PLOT

    ReportMonitor reportMonitor_64 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[SideF] - Total Monitor"));

    MonitorPlot monitorPlot_4 = 
      simulation_0.getPlotManager().createMonitorPlot(new NeoObjectVector(new Object[] {reportMonitor_64}), "[SideF] - Total Monitor Plot");
    monitorPlot_4.open();
    monitorPlot_4.setPresentationName("[SideF]");
    monitorPlot_4.setTitle("Side Force");

    Cartesian2DAxisManager cartesian2DAxisManager_4 = 
      ((Cartesian2DAxisManager) monitorPlot_4.getAxisManager());
    Cartesian2DAxis cartesian2DAxis_5 = 
      ((Cartesian2DAxis) cartesian2DAxisManager_4.getAxis("Left Axis"));
    cartesian2DAxis_5.setMinimum(-10.0);
    cartesian2DAxis_5.setMaximum(10.0);

    AxisTitle axisTitle_5 = 
      cartesian2DAxis_5.getTitle();
    axisTitle_5.setText("Force (N)");

    MonitorDataSet monitorDataSet_65 = 
      ((MonitorDataSet) monitorPlot_4.getDataSetManager().getDataSet("[SideF] - Total Monitor"));
    monitorDataSet_65.setSeriesNameLocked(true);
    monitorDataSet_65.setSeriesName("Total");

    ReportMonitor reportMonitor_65 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[SideF] - BCKDiff Mean Monitor"));
    ReportMonitor reportMonitor_66 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[SideF] - FW Mean Monitor"));
    ReportMonitor reportMonitor_67 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[SideF] - Mono Mean Monitor"));
    ReportMonitor reportMonitor_68 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[SideF] - RW Mean Monitor"));
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ReportMonitor reportMonitor_69 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[SideF] - SC(L) Mean Monitor"));}
    ReportMonitor reportMonitor_70 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[SideF] - SC(R) Mean Monitor"));
    ReportMonitor reportMonitor_71 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[SideF] - Total Mean Monitor"));
    if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
	ReportMonitor reportMonitor_72 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[SideF] - UB(L) Mean Monitor"));}
    ReportMonitor reportMonitor_73 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[SideF] - UB(R) Mean Monitor"));
    ReportMonitor reportMonitor_74 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[SideF] - BCKDiff Monitor"));
    ReportMonitor reportMonitor_75 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[SideF] - FW Monitor"));
    ReportMonitor reportMonitor_76 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[SideF] - Mono Monitor"));
    ReportMonitor reportMonitor_77 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[SideF] - RW Monitor"));
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ReportMonitor reportMonitor_78 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[SideF] - SC(L) Monitor"));}
    ReportMonitor reportMonitor_79 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[SideF] - SC(R) Monitor"));
    if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
	ReportMonitor reportMonitor_80 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[SideF] - UB(L) Monitor"));}
    ReportMonitor reportMonitor_81 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[SideF] - UB(R) Monitor"));

	if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
	monitorPlot_4.getDataSetManager().addDataProviders(new NeoObjectVector(new Object[] {reportMonitor_65, reportMonitor_66, reportMonitor_67, reportMonitor_68, reportMonitor_70, reportMonitor_71, reportMonitor_73, reportMonitor_74, reportMonitor_75, reportMonitor_76, reportMonitor_77, reportMonitor_79, reportMonitor_81}));
	} else {
	ReportMonitor reportMonitor_69 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[SideF] - SC(L) Mean Monitor"));	
	ReportMonitor reportMonitor_72 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[SideF] - UB(L) Mean Monitor"));	
	ReportMonitor reportMonitor_78 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[SideF] - SC(L) Monitor"));	
	ReportMonitor reportMonitor_80 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[SideF] - UB(L) Monitor"));	
		
    monitorPlot_4.getDataSetManager().addDataProviders(new NeoObjectVector(new Object[] {reportMonitor_65, reportMonitor_66, reportMonitor_67, reportMonitor_68, reportMonitor_69, reportMonitor_70, reportMonitor_71, reportMonitor_72, reportMonitor_73, reportMonitor_74, reportMonitor_75, reportMonitor_76, reportMonitor_77, reportMonitor_78, reportMonitor_79, reportMonitor_80, reportMonitor_81}));
	}
    MonitorDataSet monitorDataSet_66 = 
      ((MonitorDataSet) monitorPlot_4.getDataSetManager().getDataSet("[SideF] - BCKDiff Mean Monitor"));
    monitorDataSet_66.setSeriesNameLocked(true);
    monitorDataSet_66.setSeriesName("BCKDiff Mean");

    MonitorDataSet monitorDataSet_67 = 
      ((MonitorDataSet) monitorPlot_4.getDataSetManager().getDataSet("[SideF] - BCKDiff Monitor"));
    monitorDataSet_67.setSeriesNameLocked(true);
    monitorDataSet_67.setSeriesName("BCKDiff");

    MonitorDataSet monitorDataSet_68 = 
      ((MonitorDataSet) monitorPlot_4.getDataSetManager().getDataSet("[SideF] - FW Mean Monitor"));
    monitorDataSet_68.setSeriesNameLocked(true);
    monitorDataSet_68.setSeriesName("FW Mean");

    MonitorDataSet monitorDataSet_69 = 
      ((MonitorDataSet) monitorPlot_4.getDataSetManager().getDataSet("[SideF] - FW Monitor"));
    monitorDataSet_69.setSeriesNameLocked(true);
    monitorDataSet_69.setSeriesName("FW");

    MonitorDataSet monitorDataSet_70 = 
      ((MonitorDataSet) monitorPlot_4.getDataSetManager().getDataSet("[SideF] - Mono Mean Monitor"));
    monitorDataSet_70.setSeriesNameLocked(true);
    monitorDataSet_70.setSeriesName("Mono Mean");

    MonitorDataSet monitorDataSet_71 = 
      ((MonitorDataSet) monitorPlot_4.getDataSetManager().getDataSet("[SideF] - Mono Monitor"));
    monitorDataSet_71.setSeriesNameLocked(true);
    monitorDataSet_71.setSeriesName("Mono");

    MonitorDataSet monitorDataSet_72 = 
      ((MonitorDataSet) monitorPlot_4.getDataSetManager().getDataSet("[SideF] - RW Mean Monitor"));
    monitorDataSet_72.setSeriesNameLocked(true);
    monitorDataSet_72.setSeriesName("RW Mean");

    MonitorDataSet monitorDataSet_73 = 
      ((MonitorDataSet) monitorPlot_4.getDataSetManager().getDataSet("[SideF] - RW Monitor"));
    monitorDataSet_73.setSeriesNameLocked(true);
    monitorDataSet_73.setSeriesName("RW");

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    MonitorDataSet monitorDataSet_74 = 
      ((MonitorDataSet) monitorPlot_4.getDataSetManager().getDataSet("[SideF] - SC(L) Mean Monitor"));
    monitorDataSet_74.setSeriesNameLocked(true);
    monitorDataSet_74.setSeriesName("SC(L) Mean");

    MonitorDataSet monitorDataSet_75 = 
      ((MonitorDataSet) monitorPlot_4.getDataSetManager().getDataSet("[SideF] - SC(L) Monitor"));
    monitorDataSet_75.setSeriesNameLocked(true);
    monitorDataSet_75.setSeriesName("SC(L)");}

    MonitorDataSet monitorDataSet_76 = 
      ((MonitorDataSet) monitorPlot_4.getDataSetManager().getDataSet("[SideF] - SC(R) Mean Monitor"));
    monitorDataSet_76.setSeriesNameLocked(true);
    monitorDataSet_76.setSeriesName("SC(R) Mean");

    MonitorDataSet monitorDataSet_77 = 
      ((MonitorDataSet) monitorPlot_4.getDataSetManager().getDataSet("[SideF] - SC(R) Monitor"));
    monitorDataSet_77.setSeriesNameLocked(true);
    monitorDataSet_77.setSeriesName("SC(R)");

    MonitorDataSet monitorDataSet_78 = 
      ((MonitorDataSet) monitorPlot_4.getDataSetManager().getDataSet("[SideF] - Total Mean Monitor"));
    monitorDataSet_78.setSeriesNameLocked(true);
    monitorDataSet_78.setSeriesName("Total Mean");

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    MonitorDataSet monitorDataSet_79 = 
      ((MonitorDataSet) monitorPlot_4.getDataSetManager().getDataSet("[SideF] - UB(L) Mean Monitor"));
    monitorDataSet_79.setSeriesNameLocked(true);
    monitorDataSet_79.setSeriesName("UB(L) Mean");

    MonitorDataSet monitorDataSet_80 = 
      ((MonitorDataSet) monitorPlot_4.getDataSetManager().getDataSet("[SideF] - UB(L) Monitor"));
    monitorDataSet_80.setSeriesNameLocked(true);
    monitorDataSet_80.setSeriesName("UB(L)");}

    MonitorDataSet monitorDataSet_81 = 
      ((MonitorDataSet) monitorPlot_4.getDataSetManager().getDataSet("[SideF] - UB(R) Mean Monitor"));
    monitorDataSet_81.setSeriesNameLocked(true);
    monitorDataSet_81.setSeriesName("UB(R) Mean");

    MonitorDataSet monitorDataSet_82 = 
      ((MonitorDataSet) monitorPlot_4.getDataSetManager().getDataSet("[SideF] - UB(R) Monitor"));
    monitorDataSet_82.setSeriesNameLocked(true);
    monitorDataSet_82.setSeriesName("UB(R)");

    MultiColLegend multiColLegend_4 = 
      monitorPlot_4.getLegend();
    multiColLegend_4.getChartPositionOption().setSelected(ChartPositionOption.Type.SOUTH);
    multiColLegend_4.setLegendLayout(MultiColLegend.LegendLayout.HORIZONTAL);
    multiColLegend_4.setNumRows(2);

	if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
	monitorPlot_4.setDataSeriesOrder(new NeoObjectVector(new Object[] {monitorDataSet_65, monitorDataSet_78, monitorDataSet_69, monitorDataSet_68, monitorDataSet_73, monitorDataSet_72, monitorDataSet_82, monitorDataSet_81, monitorDataSet_67, monitorDataSet_66, monitorDataSet_77, monitorDataSet_76, monitorDataSet_71, monitorDataSet_70}));
	} else {
	MonitorDataSet monitorDataSet_79 = 
      ((MonitorDataSet) monitorPlot_4.getDataSetManager().getDataSet("[SideF] - UB(L) Mean Monitor"));
	MonitorDataSet monitorDataSet_80 = 
      ((MonitorDataSet) monitorPlot_4.getDataSetManager().getDataSet("[SideF] - UB(L) Monitor"));
	MonitorDataSet monitorDataSet_74 = 
      ((MonitorDataSet) monitorPlot_4.getDataSetManager().getDataSet("[SideF] - SC(L) Mean Monitor"));
	MonitorDataSet monitorDataSet_75 = 
      ((MonitorDataSet) monitorPlot_4.getDataSetManager().getDataSet("[SideF] - SC(L) Monitor"));

    monitorPlot_4.setDataSeriesOrder(new NeoObjectVector(new Object[] {monitorDataSet_65, monitorDataSet_78, monitorDataSet_69, monitorDataSet_68, monitorDataSet_73, monitorDataSet_72, monitorDataSet_80, monitorDataSet_79, monitorDataSet_82, monitorDataSet_81, monitorDataSet_67, monitorDataSet_66, monitorDataSet_75, monitorDataSet_74, monitorDataSet_77, monitorDataSet_76, monitorDataSet_71, monitorDataSet_70}));
	}
	
	monitorPlot_4.close();
	//}
	
	//{ // Residuals change of minimum and maximum PLOT

    ResidualPlot residualPlot_0 = 
      ((ResidualPlot) simulation_0.getPlotManager().getPlot("Residuals"));

    Cartesian2DAxisManager cartesian2DAxisManager_5 = 
      ((Cartesian2DAxisManager) residualPlot_0.getAxisManager());
    Cartesian2DAxis cartesian2DAxis_6 = 
      ((Cartesian2DAxis) cartesian2DAxisManager_5.getAxis("Left Axis"));
    cartesian2DAxis_6.setMinimum(1.0E-6);
    cartesian2DAxis_6.setMaximum(1.0);
	//}
	
	//{ // Moments PLOT

    ReportMonitor reportMonitor_82 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Mx] - CoG Monitor"));

    MonitorPlot monitorPlot_5 = 
      simulation_0.getPlotManager().createMonitorPlot(new NeoObjectVector(new Object[] {reportMonitor_82}), "[Mx] - CoG Monitor Plot");
    monitorPlot_5.open();
    monitorPlot_5.setPresentationName("[Moments]");
	monitorPlot_5.setTitle("Moments");

    MonitorDataSet monitorDataSet_83 = 
      ((MonitorDataSet) monitorPlot_5.getDataSetManager().getDataSet("[Mx] - CoG Monitor"));
    monitorDataSet_83.setSeriesNameLocked(true);
    monitorDataSet_83.setSeriesName("CoG - [x]");

    Cartesian2DAxisManager cartesian2DAxisManager_6 = 
      ((Cartesian2DAxisManager) monitorPlot_5.getAxisManager());
    Cartesian2DAxis cartesian2DAxis_7 = 
      ((Cartesian2DAxis) cartesian2DAxisManager_6.getAxis("Left Axis"));
    cartesian2DAxis_7.setMinimum(-20.0);
    cartesian2DAxis_7.setMaximum(15.0);

    AxisTitle axisTitle_6 = 
      cartesian2DAxis_7.getTitle();
    axisTitle_6.setText("Moment (N-m)");

    ReportMonitor reportMonitor_83 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Mx] - CoG Mean Monitor"));
    ReportMonitor reportMonitor_84 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Mx] - Origin Mean Monitor"));
    ReportMonitor reportMonitor_85 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[My] - CoG Mean Monitor"));
    ReportMonitor reportMonitor_86 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[My] - Origin Mean Monitor"));
    ReportMonitor reportMonitor_87 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Mz] - CoG Mean Monitor"));
    ReportMonitor reportMonitor_88 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Mz] - Origin Mean Monitor"));
    ReportMonitor reportMonitor_89 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Mx] - Origin Monitor"));
    ReportMonitor reportMonitor_90 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[My] - CoG Monitor"));
    ReportMonitor reportMonitor_91 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[My] - Origin Monitor"));
    ReportMonitor reportMonitor_92 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Mz] - CoG Monitor"));
    ReportMonitor reportMonitor_93 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Mz] - Origin Monitor"));

    monitorPlot_5.getDataSetManager().addDataProviders(new NeoObjectVector(new Object[] {reportMonitor_83, reportMonitor_84, reportMonitor_85, reportMonitor_86, reportMonitor_87, reportMonitor_88, reportMonitor_89, reportMonitor_90, reportMonitor_91, reportMonitor_92, reportMonitor_93}));

    MonitorDataSet monitorDataSet_84 = 
      ((MonitorDataSet) monitorPlot_5.getDataSetManager().getDataSet("[Mx] - CoG Mean Monitor"));
    monitorDataSet_84.setSeriesNameLocked(true);
    monitorDataSet_84.setSeriesName("CoG - [x] Mean");

    MonitorDataSet monitorDataSet_85 = 
      ((MonitorDataSet) monitorPlot_5.getDataSetManager().getDataSet("[Mx] - Origin Mean Monitor"));
    monitorDataSet_85.setSeriesNameLocked(true);
    monitorDataSet_85.setSeriesName("Origin - [x] Mean");

    MonitorDataSet monitorDataSet_86 = 
      ((MonitorDataSet) monitorPlot_5.getDataSetManager().getDataSet("[Mx] - Origin Monitor"));
    monitorDataSet_86.setSeriesNameLocked(true);
    monitorDataSet_86.setSeriesName("Origin - [x]");

    MonitorDataSet monitorDataSet_87 = 
      ((MonitorDataSet) monitorPlot_5.getDataSetManager().getDataSet("[My] - CoG Mean Monitor"));
    monitorDataSet_87.setSeriesNameLocked(true);
    monitorDataSet_87.setSeriesName("CoG - [y] Mean");

    MonitorDataSet monitorDataSet_88 = 
      ((MonitorDataSet) monitorPlot_5.getDataSetManager().getDataSet("[My] - CoG Monitor"));
    monitorDataSet_88.setSeriesNameLocked(true);
    monitorDataSet_88.setSeriesName("CoG - [y]");

    MonitorDataSet monitorDataSet_89 = 
      ((MonitorDataSet) monitorPlot_5.getDataSetManager().getDataSet("[My] - Origin Mean Monitor"));
    monitorDataSet_89.setSeriesNameLocked(true);
    monitorDataSet_89.setSeriesName("Origin - [y] Mean");

    MonitorDataSet monitorDataSet_90 = 
      ((MonitorDataSet) monitorPlot_5.getDataSetManager().getDataSet("[My] - Origin Monitor"));
    monitorDataSet_90.setSeriesNameLocked(true);
    monitorDataSet_90.setSeriesName("Origin - [y]");

    MonitorDataSet monitorDataSet_91 = 
      ((MonitorDataSet) monitorPlot_5.getDataSetManager().getDataSet("[Mz] - CoG Mean Monitor"));
    monitorDataSet_91.setSeriesNameLocked(true);
    monitorDataSet_91.setSeriesName("CoG - [z] Mean");

    MonitorDataSet monitorDataSet_92 = 
      ((MonitorDataSet) monitorPlot_5.getDataSetManager().getDataSet("[Mz] - CoG Monitor"));
    monitorDataSet_92.setSeriesNameLocked(true);
    monitorDataSet_92.setSeriesName("CoG - [z]");

    MonitorDataSet monitorDataSet_93 = 
      ((MonitorDataSet) monitorPlot_5.getDataSetManager().getDataSet("[Mz] - Origin Mean Monitor"));
    monitorDataSet_93.setSeriesNameLocked(true);
    monitorDataSet_93.setSeriesName("Origin - [z] Mean");

    MonitorDataSet monitorDataSet_94 = 
      ((MonitorDataSet) monitorPlot_5.getDataSetManager().getDataSet("[Mz] - Origin Monitor"));
    monitorDataSet_94.setSeriesNameLocked(true);
    monitorDataSet_94.setSeriesName("Origin - [z]");

    monitorPlot_5.setDataSeriesOrder(new NeoObjectVector(new Object[] {monitorDataSet_83, monitorDataSet_84, monitorDataSet_88, monitorDataSet_87, monitorDataSet_92, monitorDataSet_91, monitorDataSet_86, monitorDataSet_85, monitorDataSet_90, monitorDataSet_89, monitorDataSet_94, monitorDataSet_93}));
	monitorPlot_5.close();
    MultiColLegend multiColLegend_5 = 
      monitorPlot_5.getLegend();
    multiColLegend_5.getChartPositionOption().setSelected(ChartPositionOption.Type.SOUTH);
    multiColLegend_5.setLegendLayout(MultiColLegend.LegendLayout.HORIZONTAL);
    multiColLegend_5.setNumRows(2);
	//}
	
	//{ // Time solver iteration PLOT

    SimulationIteratorTimeReportMonitor simulationIteratorTimeReportMonitor_0 = 
      ((SimulationIteratorTimeReportMonitor) simulation_0.getMonitorManager().getMonitor("Solver Iteration Elapsed Time Monitor"));

    MonitorPlot monitorPlot_6 = 
      simulation_0.getPlotManager().createMonitorPlot(new NeoObjectVector(new Object[] {simulationIteratorTimeReportMonitor_0}), "Solver Iteration Elapsed Time Monitor Plot");
    monitorPlot_6.open();
    monitorPlot_6.setPresentationName("[Time] Solver Iteration");

    MonitorDataSet monitorDataSet_95 = 
      ((MonitorDataSet) monitorPlot_6.getDataSetManager().getDataSet("Solver Iteration Elapsed Time Monitor"));
    monitorDataSet_95.setSeriesNameLocked(true);
    monitorDataSet_95.setSeriesName("Solver Iteration");

    monitorPlot_6.setTitle("Solver Iteration Elapsed Time");
	monitorPlot_6.close();
    Cartesian2DAxisManager cartesian2DAxisManager_7 = 
      ((Cartesian2DAxisManager) monitorPlot_6.getAxisManager());
    Cartesian2DAxis cartesian2DAxis_8 = 
      ((Cartesian2DAxis) cartesian2DAxisManager_7.getAxis("Left Axis"));

    AxisTitle axisTitle_7 = 
      cartesian2DAxis_8.getTitle();
    axisTitle_7.setText("Time (s)");
    cartesian2DAxis_8.setMinimum(10.0);
    cartesian2DAxis_8.setMaximum(60.0);
	//}
  }
  
  private void Solver() {
	  
	//{ // Initialization & 1 step

    Simulation simulation_0 = 
      getActiveSimulation();
	  
	double Roll = simulation_0.getReportManager().getReport("Roll [deg]").getReportMonitorValue();
	double Yaw = simulation_0.getReportManager().getReport("Yaw [rad]").getReportMonitorValue();
	double CornerRadius = simulation_0.getReportManager().getReport("Corner Radius [m]").getReportMonitorValue(); 
	double SIMIDdouble = simulation_0.getReportManager().getReport("Sim ID").getReportMonitorValue();
	int intSIM = (int) SIMIDdouble;
	
	simulation_0.saveState(resolvePath("..\\Simulation\\MC0"+intSIM+".sim"));

    StepStoppingCriterion stepStoppingCriterion_0 = 
      ((StepStoppingCriterion) simulation_0.getSolverStoppingCriterionManager().getSolverStoppingCriterion("Maximum Steps"));
    stepStoppingCriterion_0.setMaximumNumberSteps(1);
	
	if ( CornerRadius == 0) {  
	PhysicsContinuum physicsContinuum_0 = 
      ((PhysicsContinuum) simulation_0.getContinuumManager().getContinuum("Physics 1"));
	  
	Units units_0 = 
      simulation_0.getUnitsManager().getInternalUnits(new IntVector(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}));
	
	VelocityProfile velocityProfile_0 = 
      physicsContinuum_0.getInitialConditions().get(VelocityProfile.class);
	
	velocityProfile_0.getMethod(ConstantVectorProfileMethod.class).getQuantity().setDefinition("[${car_velocity[ms-1]}*-1, 0.0, 0.0]");
    velocityProfile_0.getMethod(ConstantVectorProfileMethod.class).getQuantity().setUnits(units_0);
	
	 LabCoordinateSystem labCoordinateSystem_0 = 
      simulation_0.getCoordinateSystemManager().getLabCoordinateSystem();
    CartesianCoordinateSystem cartesianCoordinateSystem_0 = 
      ((CartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("Air Orientation - Car"));
    velocityProfile_0.setCoordinateSystem(cartesianCoordinateSystem_0);
	}

    Solution solution_0 = 
      simulation_0.getSolution();
    solution_0.initializeSolution();
    simulation_0.getSimulationIterator().run();
	//}
	
	//{ // Boundary condition check
	
	star.vis.Scene scene_0 = 
      simulation_0.getSceneManager().getScene("Pre Checks");
	
	SimpleAnnotation simpleAnnotation_0 = 
      ((SimpleAnnotation) simulation_0.getAnnotationManager().getObject("Mesh"));
	scene_0.getAnnotationPropManager().removePropsForAnnotations(simpleAnnotation_0);  
	  
	PartDisplayer partDisplayer_0 = 
      ((PartDisplayer) scene_0.getDisplayerManager().getDisplayer("Mesh"));  
	partDisplayer_0.setVisibilityOverrideMode(DisplayerVisibilityOverride.HIDE_ALL_PARTS);  
	
	ScalarDisplayer scalarDisplayer_0 = 
      scene_0.getDisplayerManager().createScalarDisplayer("Scalar");
	scalarDisplayer_0.initialize();
	
	Legend legend_0 = 
      scalarDisplayer_0.getLegend();
    BlueRedLookupTable blueRedLookupTable_0 = 
      ((BlueRedLookupTable) simulation_0.get(LookupTableManager.class).getObject("blue-red"));
    legend_0.setLookupTable(blueRedLookupTable_0);
	legend_0.setLevels(54);
    legend_0.setWidth(0.21);
    legend_0.setFontString("Arial-Bold");
    legend_0.setShadow(false);
    legend_0.setHeight(0.025);
    legend_0.setTitleHeight(0.03);
    legend_0.setLabelHeight(0.02);
    legend_0.setPositionCoordinate(new DoubleVector(new double[] {0.02, 0.03}));
    legend_0.setLabelFormat("%1.1f");
    legend_0.setRampType(RampType.LINEAR);
    legend_0.setNumberOfLabels(5);
	
	PrimitiveFieldFunction primitiveFieldFunction_0 = 
      ((PrimitiveFieldFunction) simulation_0.getFieldFunctionManager().getFunction("Velocity"));
	  
	if ( CornerRadius == 0) {
	
	VectorMagnitudeFieldFunction vectorMagnitudeFieldFunction_0 = 
      ((VectorMagnitudeFieldFunction) primitiveFieldFunction_0.getMagnitudeFunction());
	scalarDisplayer_0.getScalarDisplayQuantity().setFieldFunction(vectorMagnitudeFieldFunction_0);

	} else {
	UserRotatingReferenceFrame userRotatingReferenceFrame_0 = 
      ((UserRotatingReferenceFrame) simulation_0.get(ReferenceFrameManager.class).getObject("Air - RF"));
	  
	VectorMagnitudeFieldFunction vectorMagnitudeFieldFunction_0 = 
      ((VectorMagnitudeFieldFunction) primitiveFieldFunction_0.getFunctionInReferenceFrame(userRotatingReferenceFrame_0).getMagnitudeFunction());
	  
	scalarDisplayer_0.getScalarDisplayQuantity().setFieldFunction(vectorMagnitudeFieldFunction_0);
	}
	
	scalarDisplayer_0.getInputParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.DoesNotContain, "Top"), new NamePredicate(NameOperator.DoesNotContain, "Inlet"), new NamePredicate(NameOperator.DoesNotContain, "Outlet"), new NamePredicate(NameOperator.DoesNotContain, "Outer"))), Query.STANDARD_MODIFIERS));
	scalarDisplayer_0.setFillMode(ScalarFillMode.NODE_FILLED);
    scalarDisplayer_0.getScalarDisplayQuantity().setClip(ClipMode.NONE);
	
	CurrentView currentView_0 = 
      scene_0.getCurrentView();
	
    scene_0.export3DSceneFileAndWait(resolvePath("..\\Visualization & Data\\Pre Checks\\Boundary Conditions\\Scene_BC.sce"), "CP", simpleAnnotation_0.getText(), false, true);
	
	currentView_0.setInput(new DoubleVector(new double[] {-10.0, 0.0, 4.0}), new DoubleVector(new double[] {-10.0, -60.0, 4.0}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), 20.0, 1, 30.0);
	scene_0.printAndWait(resolvePath("..\\Visualization & Data\\Pre Checks\\Boundary Conditions\\side.png"), 1, 1920, 1080, true, false);
	
	currentView_0.setInput(new DoubleVector(new double[] {-10.0, 0.0, 0.0}), new DoubleVector(new double[] {-10.0, 0.0, 60.0}), new DoubleVector(new double[] {0.0, 1.0, 0.0}), 20.0, 1, 30.0);
	scene_0.printAndWait(resolvePath("..\\Visualization & Data\\Pre Checks\\Boundary Conditions\\top.png"), 1, 1920, 1080, true, false);
	
	currentView_0.setInput(new DoubleVector(new double[] {-10.0, 0.0, 4.0}), new DoubleVector(new double[] {60.0, 0.0, 4.0}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), 6.0, 1, 30.0);
	scene_0.printAndWait(resolvePath("..\\Visualization & Data\\Pre Checks\\Boundary Conditions\\front.png"), 1, 1920, 1080, true, false);
	//}
	
	//{ // Run solver


    stepStoppingCriterion_0.setMaximumNumberSteps(500);
    simulation_0.getSimulationIterator().run();
	simulation_0.saveState(resolvePath("..\\Simulation\\MC0"+intSIM+".sim"));
	
	stepStoppingCriterion_0.setMaximumNumberSteps(1000);
    simulation_0.getSimulationIterator().run();
	simulation_0.saveState(resolvePath("..\\Simulation\\MC0"+intSIM+".sim"));
	//}
	}
}
