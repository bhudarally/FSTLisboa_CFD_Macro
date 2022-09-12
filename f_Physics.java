// STAR-CCM+ macro: e_Models_Regions_Interfaces.java
// Written by STAR-CCM+ 14.04.011
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

public class f_Physics extends StarMacro {

  public void execute() {
    Models();
    AuxiliaryCalculations_WheelAirVelocities();
    BoundaryConditions();
    Interfaces();
  }

  private void Models() {
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
    physicsContinuum_0.enable(GammaTransitionModel.class);
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
  }


    private void AuxiliaryCalculations_WheelAirVelocities() {

      //Auxiliary Points - Derived Parts //

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
    pointPart_0.getInputParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.Contains, "FR_CENTER"))), Query.STANDARD_MODIFIERS));
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
    pointPart_2.getInputParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.Contains, "FL_CENTER"))), Query.STANDARD_MODIFIERS));

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
    pointPart_4.getInputParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.Contains, "RL_CENTER"))), Query.STANDARD_MODIFIERS));
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
    pointPart_6.getInputParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.Contains, "RR_CENTER"))), Query.STANDARD_MODIFIERS));
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
  
  // Coordinates Auxiliary Points Reports //
  
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
  expressionReport_0.setDefinition("(sqrt(pow(${VxFL},2)+ pow(${VyFL},2)))*cos( ${steer_inner[rad]} - atan2(${VyFL},${VxFL})) / (sqrt( pow((${FL - aux [X]}-${FL - origin [X]})*((-1*${FL - origin [Z]})/(${FL - aux [Z]}-${FL - origin [Z]})) ,2)+ pow((${FL - aux [Y]}-${FL - origin [Y]})*((-1*${FL - origin [Z]})/(${FL - aux [Z]}-${FL - origin [Z]})) ,2) + pow(${FL - origin [Z]} ,2)))");
  }
  }

    ExpressionReport expressionReport_1 = 
      simulation_0.getReportManager().createReport(ExpressionReport.class);
    expressionReport_1.setPresentationName("w - FR");
  if ( CornerRadius == 0 ) {
    expressionReport_1.setDefinition("${car_velocity[ms-1]} / (sqrt( pow((${FR - aux [X]}-${FR - origin [X]})*((-1*${FR - origin [Z]})/(${FR - aux [Z]}-${FR - origin [Z]})) ,2)+ pow((${FR - aux [Y]}-${FR - origin [Y]})*((-1*${FR - origin [Z]})/(${FR - aux [Z]}-${FR - origin [Z]})) ,2) + pow(${FR - origin [Z]} ,2)))");
  } else {
  expressionReport_1.setDefinition("(sqrt(pow(${VxFR},2)+ pow(${VyFR},2)))*cos( ${steer_outer[rad]} - atan2(${VyFR},${VxFR})) / (sqrt( pow((${FR - aux [X]}-${FR - origin [X]})*((-1*${FR - origin [Z]})/(${FR - aux [Z]}-${FR - origin [Z]})) ,2)+ pow((${FR - aux [Y]}-${FR - origin [Y]})*((-1*${FR - origin [Z]})/(${FR - aux [Z]}-${FR - origin [Z]})) ,2) + pow(${FR - origin [Z]} ,2)))");
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

  // Reference Frame //

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
  double CoolingMode = simulation_0.getReportManager().getReport("Cooling Mode").getReportMonitorValue(); 
  
  LabCoordinateSystem labCoordinateSystem_0 = 
      simulation_0.getCoordinateSystemManager().getLabCoordinateSystem();
    CartesianCoordinateSystem cartesianCoordinateSystem_0 = 
      ((CartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("Air Orientation - Car"));

  if ( CornerRadius != 0 ) {
    try{
      UserRotatingReferenceFrame userRotatingReferenceFrame_0 = 
      ((UserRotatingReferenceFrame) simulation_0.get(ReferenceFrameManager.class).getObject("Air - RF"));
    userRotatingReferenceFrame_0.getRotationRate().setDefinition("${Angular_Velocity}");
    userRotatingReferenceFrame_0.getRotationRate().setUnits(units_1);
    CylindricalCoordinateSystem cylindricalCoordinateSystem_0 = 
      ((CylindricalCoordinateSystem) cartesianCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("Air Center"));
    userRotatingReferenceFrame_0.setCoordinateSystem(cylindricalCoordinateSystem_0);}
    catch (Exception e){
      UserRotatingReferenceFrame userRotatingReferenceFrame_0 = 
      simulation_0.get(ReferenceFrameManager.class).createReferenceFrame(UserRotatingReferenceFrame.class, "Rotating");
    userRotatingReferenceFrame_0.setPresentationName("Air - RF");
    userRotatingReferenceFrame_0.getRotationRate().setDefinition("${Angular_Velocity}");
    userRotatingReferenceFrame_0.getRotationRate().setUnits(units_1);
    CylindricalCoordinateSystem cylindricalCoordinateSystem_0 = 
      ((CylindricalCoordinateSystem) cartesianCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("Air Center"));
    userRotatingReferenceFrame_0.setCoordinateSystem(cylindricalCoordinateSystem_0);}
  }
  
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
  if ( CoolingMode != 0) {
    Region region_0 = 
      simulation_0.getRegionManager().getRegion("FAN_L");
  }
  Region region_1 = 
      simulation_0.getRegionManager().getRegion("RADIATOR_L"); 
  }
  Region region_2 = 
      simulation_0.getRegionManager().getRegion("Fluid");
  Region region_3 = 
      simulation_0.getRegionManager().getRegion("RADIATOR_R");
  if ( CoolingMode != 0) {
    Region region_4 = 
      simulation_0.getRegionManager().getRegion("FAN_R");
    }

  if ( CornerRadius != 0 ) {
    UserRotatingReferenceFrame userRotatingReferenceFrame_0 = 
      ((UserRotatingReferenceFrame) simulation_0.get(ReferenceFrameManager.class).getObject("Air - RF"));
  if ( CoolingMode != 0) {
  Region region_0 = 
      simulation_0.getRegionManager().getRegion("FAN_L");

  Region region_4 = 
      simulation_0.getRegionManager().getRegion("FAN_R");

    MotionSpecification motionSpecification_0 = 
      region_0.getValues().get(MotionSpecification.class);
    motionSpecification_0.setReferenceFrame(userRotatingReferenceFrame_0);

    MotionSpecification motionSpecification_4 = 
      region_4.getValues().get(MotionSpecification.class);
    motionSpecification_4.setReferenceFrame(userRotatingReferenceFrame_0);
    }
  Region region_1 = 
      simulation_0.getRegionManager().getRegion("RADIATOR_L"); 
  
    MotionSpecification motionSpecification_1 = 
      region_1.getValues().get(MotionSpecification.class);
    motionSpecification_1.setReferenceFrame(userRotatingReferenceFrame_0);

    MotionSpecification motionSpecification_2 = 
      region_2.getValues().get(MotionSpecification.class);
    motionSpecification_2.setReferenceFrame(userRotatingReferenceFrame_0);

    MotionSpecification motionSpecification_3 = 
      region_3.getValues().get(MotionSpecification.class);
    motionSpecification_3.setReferenceFrame(userRotatingReferenceFrame_0);
    
  }
  
  // Boundary Conditions //

      // Ground //
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

      // Inlet //
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

      // Outlet //
  Boundary boundary_2 = 
      region_2.getBoundaryManager().getBoundary("Domain.Outlet");
    PressureBoundary pressureBoundary_0 = 
      ((PressureBoundary) simulation_0.get(ConditionTypeManager.class).get(PressureBoundary.class));
    boundary_2.setBoundaryType(pressureBoundary_0);

      // Slip Walls //
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

  // Wheels Rotation Boundary Condition //
  
      // FL Rotating //
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    Boundary boundary_6 = 
      region_2.getBoundaryManager().getBoundary("CFD_FULL_ASSEMBLY.SU.TYRE_FL.TYRE_FL.Faces");
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
  }
  
      // FR Rotating //
    Boundary boundary_10 = 
      region_2.getBoundaryManager().getBoundary("CFD_FULL_ASSEMBLY.SU.TYRE_FR.TYRE_FR.Faces");
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
  
      // RL Rotating //
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    Boundary boundary_14 = 
      region_2.getBoundaryManager().getBoundary("CFD_FULL_ASSEMBLY.SU.TYRE_RL.TYRE_RL.Faces");
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

  }

      // RR Rotating //
    Boundary boundary_18 = 
      region_2.getBoundaryManager().getBoundary("CFD_FULL_ASSEMBLY.SU.TYRE_RR.TYRE_RR.Faces");
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

    //Radiator POROUS REGION //

    // Radiator Left //
    PorousRegion porousRegion_0 = 
      ((PorousRegion) simulation_0.get(ConditionTypeManager.class).get(PorousRegion.class));
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
  Region region_1 = 
      simulation_0.getRegionManager().getRegion("RADIATOR_L"); 
  
    
    region_1.setRegionType(porousRegion_0);
  }
    region_3.setRegionType(porousRegion_0);
  
  Units units_3 = 
      simulation_0.getUnitsManager().getInternalUnits(new IntVector(new int[] {1, -4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));
    
  Units units_4 = 
      ((Units) simulation_0.getUnitsManager().getObject("kg/m^3-s"));

  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
  Region region_1 = 
      simulation_0.getRegionManager().getRegion("RADIATOR_L");  
    
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
  
    //Radiator Right //

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

  }

  private void Interfaces() {
    
  //{ // Fan Interfaces

    Simulation simulation_0 = 
      getActiveSimulation();
    
  double Roll = simulation_0.getReportManager().getReport("Roll [deg]").getReportMonitorValue();
  double Yaw = simulation_0.getReportManager().getReport("Yaw [rad]").getReportMonitorValue();
  double CornerRadius = simulation_0.getReportManager().getReport("Corner Radius [m]").getReportMonitorValue();
  double CoolingMode = simulation_0.getReportManager().getReport("Cooling Mode").getReportMonitorValue(); 
    
  BaffleInterface baffleInterface_0 = 
      ((BaffleInterface) simulation_0.get(ConditionTypeManager.class).get(BaffleInterface.class));
    
  FanInterface fanInterface_0 = 
      ((FanInterface) simulation_0.get(ConditionTypeManager.class).get(FanInterface.class));

  if ( CoolingMode != 0) {

  // Change table directory for fan 
    FileTable fileTable_0 = 
      (FileTable) simulation_0.getTableManager().createFromFile(resolvePath("..\\Modules_Macro\\Library\\Fan.csv"));

  //Interfaces Left Side  
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    BoundaryInterface boundaryInterface_0 = 
      ((BoundaryInterface) simulation_0.getInterfaceManager().getInterface("FAN_L/Fluid"));
    boundaryInterface_0.setPresentationName("DUCT_L");
    boundaryInterface_0.setInterfaceType(baffleInterface_0);

    BoundaryInterface boundaryInterface_1 = 
      ((BoundaryInterface) simulation_0.getInterfaceManager().getInterface("FAN_L/Fluid 2"));
    boundaryInterface_1.setPresentationName("FAN_L");
    boundaryInterface_1.setInterfaceType(fanInterface_0);
  boundaryInterface_1.swapBoundaries();
  
    BoundaryInterface boundaryInterface_2 = 
      ((BoundaryInterface) simulation_0.getInterfaceManager().getInterface("FAN_L/RADIATOR_L"));
    boundaryInterface_2.setPresentationName("RADIATOR_OUTLET_L");
  
  BoundaryInterface boundaryInterface_6 = 
      ((BoundaryInterface) simulation_0.getInterfaceManager().getInterface("Fluid/RADIATOR_L 2"));
    boundaryInterface_6.setPresentationName("RADIATOR_INLET_L");

    BoundaryInterface boundaryInterface_7 = 
      ((BoundaryInterface) simulation_0.getInterfaceManager().getInterface("Fluid/RADIATOR_L"));
    boundaryInterface_7.setPresentationName("RADIATOR_SHROUD_L");
    boundaryInterface_7.setInterfaceType(baffleInterface_0);
  }
  //Interfaces Right Side 
  
    BoundaryInterface boundaryInterface_3 = 
      ((BoundaryInterface) simulation_0.getInterfaceManager().getInterface("FAN_R/Fluid"));
    boundaryInterface_3.setPresentationName("DUCT_R");
    boundaryInterface_3.setInterfaceType(baffleInterface_0);

    BoundaryInterface boundaryInterface_4 = 
      ((BoundaryInterface) simulation_0.getInterfaceManager().getInterface("FAN_R/Fluid 2"));
    boundaryInterface_4.setPresentationName("FAN_R");
    boundaryInterface_4.setInterfaceType(fanInterface_0);
  boundaryInterface_4.swapBoundaries();

    BoundaryInterface boundaryInterface_5 = 
      ((BoundaryInterface) simulation_0.getInterfaceManager().getInterface("FAN_R/RADIATOR_R"));
    boundaryInterface_5.setPresentationName("RADIATOR_OUTLET_R");

    BoundaryInterface boundaryInterface_8 = 
      ((BoundaryInterface) simulation_0.getInterfaceManager().getInterface("Fluid/RADIATOR_R 2"));
    boundaryInterface_8.setPresentationName("RADIATOR_INLET_R");

    BoundaryInterface boundaryInterface_9 = 
      ((BoundaryInterface) simulation_0.getInterfaceManager().getInterface("Fluid/RADIATOR_R"));
    boundaryInterface_9.setPresentationName("RADIATOR_SHROUD_R");
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
      ((BoundaryInterface) simulation_0.getInterfaceManager().getInterface("FAN_L"));
    boundaryInterface_1.copyProperties(boundaryInterface_4);
    FanInterfaceSwirlProperties fanInterfaceSwirlProperties_1 = 
      boundaryInterface_1.getValues().get(FanInterfaceSwirlProperties.class);
    ExportedCartesianCoordinateSystem exportedCartesianCoordinateSystem_1 = 
      ((ExportedCartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("Fan (L) - Axis"));
    fanInterfaceSwirlProperties_1.setCoordinateSystem(exportedCartesianCoordinateSystem_1);
  }
}
else {
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
  
    BoundaryInterface boundaryInterface_2 = 
      ((BoundaryInterface) simulation_0.getInterfaceManager().getInterface("Fluid/RADIATOR_L 2"));
    boundaryInterface_2.setPresentationName("RADIATOR_OUTLET_L");
  
  BoundaryInterface boundaryInterface_6 = 
      ((BoundaryInterface) simulation_0.getInterfaceManager().getInterface("Fluid/RADIATOR_L 3"));
    boundaryInterface_6.setPresentationName("RADIATOR_INLET_L");

    BoundaryInterface boundaryInterface_7 = 
      ((BoundaryInterface) simulation_0.getInterfaceManager().getInterface("Fluid/RADIATOR_L"));
    boundaryInterface_7.setPresentationName("RADIATOR_SHROUD_L");
    boundaryInterface_7.setInterfaceType(baffleInterface_0);
  }
  //Interfaces Right Side 

    BoundaryInterface boundaryInterface_5 = 
      ((BoundaryInterface) simulation_0.getInterfaceManager().getInterface("Fluid/RADIATOR_R 2"));
    boundaryInterface_5.setPresentationName("RADIATOR_OUTLET_R");

    BoundaryInterface boundaryInterface_8 = 
      ((BoundaryInterface) simulation_0.getInterfaceManager().getInterface("Fluid/RADIATOR_R 3"));
    boundaryInterface_8.setPresentationName("RADIATOR_INLET_R");

    BoundaryInterface boundaryInterface_9 = 
      ((BoundaryInterface) simulation_0.getInterfaceManager().getInterface("Fluid/RADIATOR_R"));
    boundaryInterface_9.setPresentationName("RADIATOR_SHROUD_R");
    boundaryInterface_9.setInterfaceType(baffleInterface_0);

}
  //}
  
  //{ // Execute Automated Mesh
  
  AutoMeshOperation autoMeshOperation_0 = 
      ((AutoMeshOperation) simulation_0.get(MeshOperationManager.class).getObject("Mesh"));
    autoMeshOperation_0.execute();
  
  simulation_0.saveState(resolvePath("..\\Simulation\\FST12CFD.sim"));
  
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
  partDisplayer_2.getInputParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, InterfaceBoundary.class), new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.DoesNotContain, "Domain"), new NamePredicate(NameOperator.DoesNotContain, "FAN"), new NamePredicate(NameOperator.DoesNotContain, "RADIATOR"))))), Query.STANDARD_MODIFIERS));
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
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\Pre Checks\\Mesh\\bottom.png"), 1, 1920, 1080, true, false);
  VisView visView_1 = 
      ((VisView) simulation_0.getViewManager().getObject("bottom_front_left"));
    currentView_0.setView(visView_1);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\Pre Checks\\Mesh\\bottom_front_left.png"), 1, 1920, 1080, true, false);
  VisView visView_2 = 
      ((VisView) simulation_0.getViewManager().getObject("bottom_front_right"));
    currentView_0.setView(visView_2);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\Pre Checks\\Mesh\\bottom_front_right.png"), 1, 1920, 1080, true, false);
  VisView visView_3 = 
      ((VisView) simulation_0.getViewManager().getObject("bottom_rear_left"));
    currentView_0.setView(visView_3);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\Pre Checks\\Mesh\\bottom_rear_left.png"), 1, 1920, 1080, true, false);
  VisView visView_4 = 
      ((VisView) simulation_0.getViewManager().getObject("bottom_rear_right"));
    currentView_0.setView(visView_4);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\Pre Checks\\Mesh\\bottom_rear_right.png"), 1, 1920, 1080, true, false);
  VisView visView_5 = 
      ((VisView) simulation_0.getViewManager().getObject("front"));
    currentView_0.setView(visView_5);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\Pre Checks\\Mesh\\front.png"), 1, 1920, 1080, true, false);
  VisView visView_6 = 
      ((VisView) simulation_0.getViewManager().getObject("left"));
    currentView_0.setView(visView_6);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\Pre Checks\\Mesh\\left.png"), 1, 1920, 1080, true, false);
  VisView visView_7 = 
      ((VisView) simulation_0.getViewManager().getObject("rear"));
    currentView_0.setView(visView_7);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\Pre Checks\\Mesh\\rear.png"), 1, 1920, 1080, true, false);
  VisView visView_8 = 
      ((VisView) simulation_0.getViewManager().getObject("right"));
    currentView_0.setView(visView_8);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\Pre Checks\\Mesh\\right.png"), 1, 1920, 1080, true, false);
  VisView visView_9 = 
      ((VisView) simulation_0.getViewManager().getObject("top"));
    currentView_0.setView(visView_9);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\Pre Checks\\Mesh\\top.png"), 1, 1920, 1080, true, false);
  VisView visView_10 = 
      ((VisView) simulation_0.getViewManager().getObject("top_front_left"));
    currentView_0.setView(visView_10);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\Pre Checks\\Mesh\\top_front_left.png"), 1, 1920, 1080, true, false);
  VisView visView_11 = 
      ((VisView) simulation_0.getViewManager().getObject("top_front_right"));
    currentView_0.setView(visView_11);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\Pre Checks\\Mesh\\top_front_right.png"), 1, 1920, 1080, true, false);
  VisView visView_12 = 
      ((VisView) simulation_0.getViewManager().getObject("top_rear_left"));
    currentView_0.setView(visView_12);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\Pre Checks\\Mesh\\top_rear_left.png"), 1, 1920, 1080, true, false);
  VisView visView_13 = 
      ((VisView) simulation_0.getViewManager().getObject("top_rear_right"));
    currentView_0.setView(visView_13);
    scene_0.printAndWait(resolvePath("..\\Post_Processing\\Pre Checks\\Mesh\\top_rear_right.png"), 1, 1920, 1080, true, false);
  
  //} 
  }

}