// STAR-CCM+ macro: g_ReportsMonitorsPlots.java
// Written by STAR-CCM+ 14.04.011
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;
import star.base.report.*;
import star.vis.*;
import star.energy.*;
import star.base.query.*;
import star.flow.*;
import java.io.*;
import java.nio.*;
import star.motion.*;

public class g_Reports extends StarMacro {

  public void execute() {
  	Reports();
    Monitors();
    Plots();
    ClACdACyA();
  }

  private void Reports() {

  	Simulation simulation_0 = 
      getActiveSimulation();
	  
	double Roll = simulation_0.getReportManager().getReport("Roll [deg]").getReportMonitorValue();
	double Yaw = simulation_0.getReportManager().getReport("Yaw [rad]").getReportMonitorValue();
	double CornerRadius = simulation_0.getReportManager().getReport("Corner Radius [m]").getReportMonitorValue(); 
  double CoolingMode = simulation_0.getReportManager().getReport("Cooling Mode").getReportMonitorValue();
	  
	FvRepresentation fvRepresentation_0 = 
      ((FvRepresentation) simulation_0.getRepresentationManager().getObject("Volume Mesh"));
	  
	Units units_0 = 
      ((Units) simulation_0.getUnitsManager().getObject("m"));
	  
	Units units_1 = 
      ((Units) simulation_0.getUnitsManager().getObject(""));

   // Center of Loads //
	  
	LabCoordinateSystem labCoordinateSystem_0 = 
      simulation_0.getCoordinateSystemManager().getLabCoordinateSystem();

       CenterOfLoadsReport centerOfLoadsReport_0 = 
      simulation_0.getReportManager().createReport(CenterOfLoadsReport.class);
    centerOfLoadsReport_0.setPresentationName("[CLoadX] - CoG");
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
    centerOfLoadsReport_1.setPresentationName("[CLoadY] - CoG");
    centerOfLoadsReport_1.getDirection().setComponents(0.0, 1.0, 0.0);
    centerOfLoadsReport_1.getDirection().setUnits(units_1);

    CenterOfLoadsReport centerOfLoadsReport_2 = 
      simulation_0.getReportManager().createReport(CenterOfLoadsReport.class);
    centerOfLoadsReport_2.copyProperties(centerOfLoadsReport_1);
    centerOfLoadsReport_2.setPresentationName("[CLoadZ] - CoG");
    centerOfLoadsReport_2.getDirection().setComponents(0.0, 0.0, 1.0);
    centerOfLoadsReport_2.getDirection().setUnits(units_1);

    CenterOfLoadsReport centerOfLoadsReport_3 = 
      simulation_0.getReportManager().createReport(CenterOfLoadsReport.class);
    centerOfLoadsReport_3.copyProperties(centerOfLoadsReport_0);
	centerOfLoadsReport_3.setPresentationName("[CLoadX] - Ground");

    CenterOfLoadsReport centerOfLoadsReport_4 = 
      simulation_0.getReportManager().createReport(CenterOfLoadsReport.class);
    centerOfLoadsReport_4.copyProperties(centerOfLoadsReport_1);
	centerOfLoadsReport_4.setPresentationName("[CLoadY] - Ground");

    CenterOfLoadsReport centerOfLoadsReport_5 = 
      simulation_0.getReportManager().createReport(CenterOfLoadsReport.class);
    centerOfLoadsReport_5.copyProperties(centerOfLoadsReport_2);
    centerOfLoadsReport_5.setPresentationName("[CLoadZ] - Ground");

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
	

	// Cooling //
	
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    if (CoolingMode != 0) {
	Region region_0 = 
      simulation_0.getRegionManager().getRegion("Fan_L");}
   Region region_3 = 
      simulation_0.getRegionManager().getRegion("Radiator_L");}

	  if (CoolingMode != 0) {
	Region region_1 = 
      simulation_0.getRegionManager().getRegion("Fan_R");}
	  
	Region region_2 = 
      simulation_0.getRegionManager().getRegion("Fluid");
	  
	Region region_4 = 
      simulation_0.getRegionManager().getRegion("Radiator_R");

	if (CoolingMode != 0) {
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
	MassFlowReport massFlowReport_0 = 
      simulation_0.getReportManager().createReport(MassFlowReport.class);
	Region region_0 = 
      simulation_0.getRegionManager().getRegion("Fan_L");
    massFlowReport_0.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, InterfaceBoundary.class), new NamePredicate(NameOperator.Contains, "Fan_L.Fan"), new RelationshipPredicate(RelationshipOperator.DescendantOf, new IdentityPredicate(IdentityOperator.Equals, Arrays.<ClientServerObject>asList(region_0))))), Query.STANDARD_MODIFIERS));
    massFlowReport_0.setPresentationName("[Mass Flow] - Fan (L)");
    massFlowReport_0.setRepresentation(fvRepresentation_0); }

    Region region_1 = 
      simulation_0.getRegionManager().getRegion("Fan_R");
    MassFlowReport massFlowReport_1 = 
      simulation_0.getReportManager().createReport(MassFlowReport.class);
	massFlowReport_1.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, InterfaceBoundary.class), new NamePredicate(NameOperator.Contains, "Fan_R.Fan"), new RelationshipPredicate(RelationshipOperator.DescendantOf, new IdentityPredicate(IdentityOperator.Equals, Arrays.<ClientServerObject>asList(region_1))))), Query.STANDARD_MODIFIERS));
    massFlowReport_1.setPresentationName("[Mass Flow] - Fan (R)");
	massFlowReport_1.setRepresentation(fvRepresentation_0);}
	
    
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
		Region region_3 = 
      simulation_0.getRegionManager().getRegion("Radiator_L");
    MassFlowReport massFlowReport_2 = 
      simulation_0.getReportManager().createReport(MassFlowReport.class);
    massFlowReport_2.setPresentationName("[Mass Flow] - Radiator (L)");
    massFlowReport_2.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, InterfaceBoundary.class), new NamePredicate(NameOperator.Contains, "L.Radiator_Outlet"), new RelationshipPredicate(RelationshipOperator.DescendantOf, new IdentityPredicate(IdentityOperator.Equals, Arrays.<ClientServerObject>asList(region_3))))), Query.STANDARD_MODIFIERS)); 
    massFlowReport_2.setRepresentation(fvRepresentation_0);}

    MassFlowReport massFlowReport_3 = 
      simulation_0.getReportManager().createReport(MassFlowReport.class);
    massFlowReport_3.setPresentationName("[Mass Flow] - Radiator (R)");
    massFlowReport_3.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, InterfaceBoundary.class), new NamePredicate(NameOperator.Contains, "R.Radiator_Outlet"), new RelationshipPredicate(RelationshipOperator.DescendantOf, new IdentityPredicate(IdentityOperator.Equals, Arrays.<ClientServerObject>asList(region_4))))), Query.STANDARD_MODIFIERS)); 
    massFlowReport_3.setRepresentation(fvRepresentation_0);
	
  if (CoolingMode != 0) {
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
	Region region_0 = 
      simulation_0.getRegionManager().getRegion("Fan_L");
    PressureDropReport pressureDropReport_0 = 
      simulation_0.getReportManager().createReport(PressureDropReport.class);
    pressureDropReport_0.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, InterfaceBoundary.class), new NamePredicate(NameOperator.Contains, "Fan_L.Fan"), new RelationshipPredicate(RelationshipOperator.DescendantOf, new IdentityPredicate(IdentityOperator.Equals, Arrays.<ClientServerObject>asList(region_2))))), Query.STANDARD_MODIFIERS));
    pressureDropReport_0.getLowPressureParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, InterfaceBoundary.class), new NamePredicate(NameOperator.Contains, "Fan_L.Fan"), new RelationshipPredicate(RelationshipOperator.DescendantOf, new IdentityPredicate(IdentityOperator.Equals, Arrays.<ClientServerObject>asList(region_0))))), Query.STANDARD_MODIFIERS));
    pressureDropReport_0.setRepresentation(fvRepresentation_0);
    pressureDropReport_0.setPresentationName("[PDrop] - Fan (L)");}

    Region region_1 = 
      simulation_0.getRegionManager().getRegion("Fan_R");
    PressureDropReport pressureDropReport_1 = 
      simulation_0.getReportManager().createReport(PressureDropReport.class);
    pressureDropReport_1.setPresentationName("[PDrop] - Fan (R)");
    pressureDropReport_1.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, InterfaceBoundary.class), new NamePredicate(NameOperator.Contains, "Fan_R.Fan"), new RelationshipPredicate(RelationshipOperator.DescendantOf, new IdentityPredicate(IdentityOperator.Equals, Arrays.<ClientServerObject>asList(region_2))))), Query.STANDARD_MODIFIERS));
    pressureDropReport_1.getLowPressureParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, InterfaceBoundary.class), new NamePredicate(NameOperator.Contains, "Fan_R.Fan"), new RelationshipPredicate(RelationshipOperator.DescendantOf, new IdentityPredicate(IdentityOperator.Equals, Arrays.<ClientServerObject>asList(region_1))))), Query.STANDARD_MODIFIERS));
	pressureDropReport_1.setPresentationName("[PDrop] - Fan (R)");}
	
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    Region region_3 = 
      simulation_0.getRegionManager().getRegion("Radiator_L");
    PressureDropReport pressureDropReport_2 = 
      simulation_0.getReportManager().createReport(PressureDropReport.class);
    pressureDropReport_2.setPresentationName("[PDrop] - Radiator (L)");
    pressureDropReport_2.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, InterfaceBoundary.class), new NamePredicate(NameOperator.Contains, "L.Radiator_Inlet"), new RelationshipPredicate(RelationshipOperator.DescendantOf, new IdentityPredicate(IdentityOperator.Equals, Arrays.<ClientServerObject>asList(region_2))))), Query.STANDARD_MODIFIERS));
    pressureDropReport_2.getLowPressureParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, InterfaceBoundary.class), new NamePredicate(NameOperator.Contains, "L.Radiator_Outlet"), new RelationshipPredicate(RelationshipOperator.DescendantOf, new IdentityPredicate(IdentityOperator.Equals, Arrays.<ClientServerObject>asList(region_3))))), Query.STANDARD_MODIFIERS));
    pressureDropReport_2.setRepresentation(fvRepresentation_0);}

    PressureDropReport pressureDropReport_3 = 
      simulation_0.getReportManager().createReport(PressureDropReport.class);
    pressureDropReport_3.setPresentationName("[PDrop] - Radiator (R)");
    pressureDropReport_3.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, InterfaceBoundary.class), new NamePredicate(NameOperator.Contains, "R.Radiator_Inlet"), new RelationshipPredicate(RelationshipOperator.DescendantOf, new IdentityPredicate(IdentityOperator.Equals, Arrays.<ClientServerObject>asList(region_2))))), Query.STANDARD_MODIFIERS));
    pressureDropReport_3.getLowPressureParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, InterfaceBoundary.class), new NamePredicate(NameOperator.Contains, "R.Radiator_Outlet"), new RelationshipPredicate(RelationshipOperator.DescendantOf, new IdentityPredicate(IdentityOperator.Equals, Arrays.<ClientServerObject>asList(region_4))))), Query.STANDARD_MODIFIERS));
    pressureDropReport_3.setRepresentation(fvRepresentation_0);
	
    try {
      ((ClientServerObjectGroup) simulation_0.getFieldFunctionManager().getGroupsManager().getObject("[Cooling]")).setPresentationName("[Cooling]");;
    }
    catch (Exception e)
    {
      simulation_0.getFieldFunctionManager().getGroupsManager().createGroup("[Cooling]");
    }
    ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Cooling]")).getGroupsManager().createGroup("[Monitor]");
	if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
    if (CoolingMode != 0) {
    MassFlowReport massFlowReport_1 = 
      ((MassFlowReport) simulation_0.getReportManager().getReport("[Mass Flow] - Fan (R)"));
  PressureDropReport pressureDropReport_1 = 
      ((PressureDropReport) simulation_0.getReportManager().getReport("[PDrop] - Fan (R)"));
	((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Cooling]")).getGroupsManager().getObject("[Monitor]")).getGroupsManager().groupObjects("[Monitor]", new NeoObjectVector(new Object[] {massFlowReport_1, massFlowReport_3, pressureDropReport_1, pressureDropReport_3}), true);
	   }
     else{
      ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Cooling]")).getGroupsManager().getObject("[Monitor]")).getGroupsManager().groupObjects("[Monitor]", new NeoObjectVector(new Object[] {massFlowReport_3, pressureDropReport_3}), true);
     }
  } else {

    MassFlowReport massFlowReport_2 = 
      ((MassFlowReport) simulation_0.getReportManager().getReport("[Mass Flow] - Radiator (L)"));
    PressureDropReport pressureDropReport_2 = 
      ((PressureDropReport) simulation_0.getReportManager().getReport("[PDrop] - Radiator (L)"));

    if (CoolingMode != 0) {
  MassFlowReport massFlowReport_1 = 
      ((MassFlowReport) simulation_0.getReportManager().getReport("[Mass Flow] - Fan (R)"));
  PressureDropReport pressureDropReport_1 = 
      ((PressureDropReport) simulation_0.getReportManager().getReport("[PDrop] - Fan (R)"));
	MassFlowReport massFlowReport_0 = 
      ((MassFlowReport) simulation_0.getReportManager().getReport("[Mass Flow] - Fan (L)"));
	PressureDropReport pressureDropReport_0 = 
      ((PressureDropReport) simulation_0.getReportManager().getReport("[PDrop] - Fan (L)"));
	
    ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Cooling]")).getGroupsManager().getObject("[Monitor]")).getGroupsManager().groupObjects("[Monitor]", new NeoObjectVector(new Object[] {massFlowReport_0, massFlowReport_1, massFlowReport_2, massFlowReport_3, pressureDropReport_0, pressureDropReport_1, pressureDropReport_2, pressureDropReport_3}), true);
	}
  else {
    ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Cooling]")).getGroupsManager().getObject("[Monitor]")).getGroupsManager().groupObjects("[Monitor]", new NeoObjectVector(new Object[] {massFlowReport_2, massFlowReport_3, pressureDropReport_2, pressureDropReport_3}), true);
  
  }
  }

  // DownForce Reports //

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
    forceReport_1.setPresentationName("[DownF] - Rear Diff");
    forceReport_1.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.Contains, "Diff_Ba"))), Query.STANDARD_MODIFIERS));

  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_2 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_2.copyProperties(forceReport_1);
    forceReport_2.setPresentationName("[DownF] - Tyre FL");
    forceReport_2.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "Tyre_FL"))))), Query.STANDARD_MODIFIERS));}
  
    ForceReport forceReport_3 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_3.copyProperties(forceReport_1);
    forceReport_3.setPresentationName("[DownF] - Tyre FR");
    forceReport_3.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "Tyre_FR"))))), Query.STANDARD_MODIFIERS));
  
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_4 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_4.copyProperties(forceReport_1);
    forceReport_4.setPresentationName("[DownF] - Tyre RL");
    forceReport_4.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "Tyre_RL"))))), Query.STANDARD_MODIFIERS));}
  
    ForceReport forceReport_5 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_5.copyProperties(forceReport_1);
    forceReport_5.setPresentationName("[DownF] - Tyre RR");
    forceReport_5.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "Tyre_RR"))))), Query.STANDARD_MODIFIERS));

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
    forceReport_8.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "FW_EP_R"))), new NamePredicate(NameOperator.Matches, "Car.FW_EP.Faces"))), new TypePredicate(TypeOperator.Is, Boundary.class))))), Query.STANDARD_MODIFIERS));
  
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_9 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_9.copyProperties(forceReport_1);
    forceReport_9.setPresentationName("[DownF] - FW - Flap(L)");
    forceReport_9.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.Contains, "FW_Flap_L"))), Query.STANDARD_MODIFIERS));}
  
    ForceReport forceReport_10 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_10.copyProperties(forceReport_8);
    forceReport_10.setPresentationName("[DownF] - FW - Flap(R)");
    forceReport_10.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "FW_Flap_R"))), new NamePredicate(NameOperator.Matches, "Car.FW_Flap.Faces"))), new TypePredicate(TypeOperator.Is, Boundary.class))))), Query.STANDARD_MODIFIERS));
  
    ForceReport forceReport_11 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_11.copyProperties(forceReport_10);
    forceReport_11.setPresentationName("[DownF] - FW - Main");
    forceReport_11.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.Contains, "FW_Main"))), Query.STANDARD_MODIFIERS));

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
    forceReport_14.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.Contains, "RW_EP"))), Query.STANDARD_MODIFIERS));

    ForceReport forceReport_15 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_15.copyProperties(forceReport_14);
    forceReport_15.setPresentationName("[DownF] - RW - Flap1");
    forceReport_15.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.Contains, "RW"), new NamePredicate(NameOperator.Contains, "2"))), Query.STANDARD_MODIFIERS));

    ForceReport forceReport_16 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_16.copyProperties(forceReport_15);
    forceReport_16.setPresentationName("[DownF] - RW - Flap2");
    forceReport_16.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.Contains, "RW"), new NamePredicate(NameOperator.Contains, "3"))), Query.STANDARD_MODIFIERS));

    ForceReport forceReport_17 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_17.copyProperties(forceReport_16);
    forceReport_17.setPresentationName("[DownF] - RW - Main");
    forceReport_17.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.Contains, "RW"), new NamePredicate(NameOperator.Contains, "1"))), Query.STANDARD_MODIFIERS));
  
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_18 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_18.copyProperties(forceReport_1);
    forceReport_18.setPresentationName("[DownF] - SC(L)");
    forceReport_18.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.Contains, "SC"), new NamePredicate(NameOperator.DoesNotContain, "_R"))), Query.STANDARD_MODIFIERS));}
  
    ForceReport forceReport_19 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_19.copyProperties(forceReport_13);
    forceReport_19.setPresentationName("[DownF] - SC(R)");
    forceReport_19.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.Contains, "SC"), new NamePredicate(NameOperator.DoesNotContain, "_L"))), Query.STANDARD_MODIFIERS));
  
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_20 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_20.copyProperties(forceReport_1);
    forceReport_20.setPresentationName("[DownF] - SC EP(L)");
    forceReport_20.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.Contains, "SC"), new NamePredicate(NameOperator.DoesNotContain, "_R"), new NamePredicate(NameOperator.Contains, "EP"))), Query.STANDARD_MODIFIERS));}
  
    ForceReport forceReport_21 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_21.copyProperties(forceReport_1);
    forceReport_21.setPresentationName("[DownF] - SC EP (R)");
    forceReport_21.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "SC_EP_R"))), new NamePredicate(NameOperator.Matches, "Car.SC_EP.Faces"))), new TypePredicate(TypeOperator.Is, Boundary.class))))), Query.STANDARD_MODIFIERS));
  
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_22 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_22.copyProperties(forceReport_1);
    forceReport_22.setPresentationName("[DownF] - SC0 (L)");
    forceReport_22.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.Contains, "SC0"), new NamePredicate(NameOperator.DoesNotContain, "_R"))), Query.STANDARD_MODIFIERS));}
  
    ForceReport forceReport_23 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_23.copyProperties(forceReport_21);
    forceReport_23.setPresentationName("[DownF] - SC0 (R)");
    forceReport_23.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "SC0_R"))), new NamePredicate(NameOperator.Matches, "Car.SC0.Faces"))), new TypePredicate(TypeOperator.Is, Boundary.class))))), Query.STANDARD_MODIFIERS));
  
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_24 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_24.copyProperties(forceReport_1);
    forceReport_24.setPresentationName("[DownF] - SC1 (L)");
    forceReport_24.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.Contains, "SC1"), new NamePredicate(NameOperator.DoesNotContain, "_R"))), Query.STANDARD_MODIFIERS));}  
  
    ForceReport forceReport_25 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_25.copyProperties(forceReport_1);
    forceReport_25.setPresentationName("[DownF] - SC1 (R)");
    forceReport_25.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "SC1_R"))), new NamePredicate(NameOperator.Matches, "Car.SC1.Faces"))), new TypePredicate(TypeOperator.Is, Boundary.class))))), Query.STANDARD_MODIFIERS));
  
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_26 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_26.copyProperties(forceReport_1);
    forceReport_26.setPresentationName("[DownF] - SC2 (L)");
    forceReport_26.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.Contains, "SC2"), new NamePredicate(NameOperator.DoesNotContain, "_R"))), Query.STANDARD_MODIFIERS));}
    
  ForceReport forceReport_27 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_27.copyProperties(forceReport_1);
    forceReport_27.setPresentationName("[DownF] - SC2 (R)");
    forceReport_27.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "SC2_R"))), new NamePredicate(NameOperator.Matches, "Car.SC2.Faces"))), new TypePredicate(TypeOperator.Is, Boundary.class))))), Query.STANDARD_MODIFIERS));
  
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_28 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_28.copyProperties(forceReport_1);
    forceReport_28.setPresentationName("[DownF] - SC3 (L)");
    forceReport_28.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.Contains, "SC3"), new NamePredicate(NameOperator.DoesNotContain, "_R"))), Query.STANDARD_MODIFIERS));}
  
    ForceReport forceReport_29 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_29.copyProperties(forceReport_1);
    forceReport_29.setPresentationName("[DownF] - SC3 (R)");
    forceReport_29.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "SC3_R"))), new NamePredicate(NameOperator.Matches, "Car.SC3.Faces"))), new TypePredicate(TypeOperator.Is, Boundary.class))))), Query.STANDARD_MODIFIERS));
  
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_30 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_30.copyProperties(forceReport_1);
    forceReport_30.setPresentationName("[DownF] - Diff Lateral (L)");
    forceReport_30.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "_L.Faces"), new NamePredicate(NameOperator.Contains, "Diff"), new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.DoesNotContain, "Back"))), Query.STANDARD_MODIFIERS));}

    ForceReport forceReport_31 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_31.copyProperties(forceReport_1);
    forceReport_31.setPresentationName("[DownF] - Diff Lateral (R)");
    forceReport_31.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "Diff"), new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.DoesNotContain, "_L.Faces"), new NamePredicate(NameOperator.DoesNotContain, "Back"))))), Query.STANDARD_MODIFIERS));
  
  ForceReport forceReport_32 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_32.copyProperties(forceReport_1);
    forceReport_32.setPresentationName("[DownF] - Driver | MH | HR");
    forceReport_32.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "Driver"), new NamePredicate(NameOperator.Contains, "Main_Hoop"), new NamePredicate(NameOperator.Contains, "HR"))))), Query.STANDARD_MODIFIERS));

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

    ForceReport forceReport_1000 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_1000.copyProperties(forceReport_1);
    forceReport_1000.setPresentationName("[DownF] - Misc");
    forceReport_1000.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.DoesNotContain, "Domain"), new NamePredicate(NameOperator.DoesNotContain, "BH"), new NamePredicate(NameOperator.DoesNotContain, "Diff_"), new NamePredicate(NameOperator.DoesNotContain, "FW_Mid"), new NamePredicate(NameOperator.DoesNotContain, "FW_Sup"), new NamePredicate(NameOperator.DoesNotContain, "FW_Flap"), new NamePredicate(NameOperator.DoesNotContain, "Driver"), new NamePredicate(NameOperator.DoesNotContain, "Diff_Back"), new NamePredicate(NameOperator.DoesNotContain, "RW1"), new NamePredicate(NameOperator.DoesNotContain, "RW2"), new NamePredicate(NameOperator.DoesNotContain, "Mono"), new NamePredicate(NameOperator.DoesNotContain, "Fan"), new NamePredicate(NameOperator.DoesNotContain, "Radiator"), new NamePredicate(NameOperator.DoesNotContain, "SC0"), new NamePredicate(NameOperator.DoesNotContain, "SC1"), new NamePredicate(NameOperator.DoesNotContain, "SC2"), new NamePredicate(NameOperator.DoesNotContain, "SC3"), new NamePredicate(NameOperator.DoesNotContain, "SC_EP"), new NamePredicate(NameOperator.DoesNotContain, "FW_EP"), new NamePredicate(NameOperator.DoesNotContain, "HR"), new NamePredicate(NameOperator.DoesNotContain, "SU_"), new NamePredicate(NameOperator.DoesNotContain, "Tyre_"), new NamePredicate(NameOperator.DoesNotContain, "RW"), new NamePredicate(NameOperator.DoesNotContain, "RW_EP"), new NamePredicate(NameOperator.DoesNotContain, "Main_Hoop"), new NamePredicate(NameOperator.DoesNotContain, "RW_Sup"), new NamePredicate(NameOperator.DoesNotContain, "FW"))), Query.STANDARD_MODIFIERS));
  
// Drag Reports //
  
  ForceReport forceReport_37 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_37.copyProperties(forceReport_1);
  forceReport_37.setPresentationName("[Drag] - Rear Diff");
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
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - Diff Lateral (L)"));
    forceReport_68.copyProperties(forceReport_30);
    forceReport_68.setPresentationName("[Drag] - Diff Lateral (L)");
  forceReport_68.getDirection().setComponents(-1.0, 0.0, 0.0);
    forceReport_68.getDirection().setUnits(units_1);}

    ForceReport forceReport_69 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_69.copyProperties(forceReport_31);
    forceReport_69.setPresentationName("[Drag] - Diff Lateral (R)");
  forceReport_69.getDirection().setComponents(-1.0, 0.0, 0.0);
    forceReport_69.getDirection().setUnits(units_1);

  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_70 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
  ForceReport forceReport_2 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - Tyre FL"));
    forceReport_70.copyProperties(forceReport_2);
    forceReport_70.setPresentationName("[Drag] - Tyre FL");
  forceReport_70.getDirection().setComponents(-1.0, 0.0, 0.0);
    forceReport_70.getDirection().setUnits(units_1);}

    ForceReport forceReport_71 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_71.copyProperties(forceReport_3);
    forceReport_71.setPresentationName("[Drag] - Tyre FR");
  forceReport_71.getDirection().setComponents(-1.0, 0.0, 0.0);
    forceReport_71.getDirection().setUnits(units_1);

  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_72 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
  ForceReport forceReport_4 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - Tyre RL"));
    forceReport_72.copyProperties(forceReport_4);
    forceReport_72.setPresentationName("[Drag] - Tyre RL");
  forceReport_72.getDirection().setComponents(-1.0, 0.0, 0.0);
    forceReport_72.getDirection().setUnits(units_1);}

    ForceReport forceReport_73 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_73.copyProperties(forceReport_5);
    forceReport_73.setPresentationName("[Drag] - Tyre RR");
  forceReport_73.getDirection().setComponents(-1.0, 0.0, 0.0);
    forceReport_73.getDirection().setUnits(units_1);

    ForceReport forceReport_1001 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_1001.copyProperties(forceReport_1000);
    forceReport_1001.setPresentationName("[Drag] - Misc");
  forceReport_1001.getDirection().setComponents(-1.0, 0.0, 0.0);
    forceReport_1001.getDirection().setUnits(units_1);


  // SideForce Reports //
  
    ForceReport forceReport_74 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_74.copyProperties(forceReport_37);
    forceReport_74.setPresentationName("[SideF] - Rear Diff");
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
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - Diff Lateral (L)"));
    forceReport_105.copyProperties(forceReport_68);
    forceReport_105.setPresentationName("[SideF] - Diff Lateral (L)");
  forceReport_105.getDirection().setComponents(0.0, 1.0, 0.0);
    forceReport_105.getDirection().setUnits(units_1);}

    ForceReport forceReport_106 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_106.copyProperties(forceReport_69);
    forceReport_106.setPresentationName("[SideF] - Diff Lateral (R)");
  forceReport_106.getDirection().setComponents(0.0, 1.0, 0.0);
    forceReport_106.getDirection().setUnits(units_1);

  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_107 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
  ForceReport forceReport_70 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - Tyre FL"));
    forceReport_107.copyProperties(forceReport_70);
    forceReport_107.setPresentationName("[SideF] - Tyre FL");
  forceReport_107.getDirection().setComponents(0.0, 1.0, 0.0);
    forceReport_107.getDirection().setUnits(units_1);}

    ForceReport forceReport_108 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_108.copyProperties(forceReport_71);
    forceReport_108.setPresentationName("[SideF] - Tyre FR");
  forceReport_108.getDirection().setComponents(0.0, 1.0, 0.0);
    forceReport_108.getDirection().setUnits(units_1);

  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ForceReport forceReport_109 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
  ForceReport forceReport_72 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - Tyre RL"));
    forceReport_109.copyProperties(forceReport_72);
    forceReport_109.setPresentationName("[SideF] - Tyre RL");
  forceReport_109.getDirection().setComponents(0.0, 1.0, 0.0);
    forceReport_109.getDirection().setUnits(units_1);}

    ForceReport forceReport_110 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_110.copyProperties(forceReport_73);
    forceReport_110.setPresentationName("[SideF] - Tyre RR");
  forceReport_110.getDirection().setComponents(0.0, 1.0, 0.0);
    forceReport_110.getDirection().setUnits(units_1);

    ForceReport forceReport_1002 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
    forceReport_1002.copyProperties(forceReport_1000);
    forceReport_1002.setPresentationName("[SideF] - Misc");
  forceReport_1002.getDirection().setComponents(0.0, 1.0, 0.0);
    forceReport_1002.getDirection().setUnits(units_1);
      


  //{ // Groups Forces
  
  simulation_0.getReportManager().getGroupsManager().createGroup("[Forces]");
    ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().createGroup("[Monitor]");
    ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().getObject("[Monitor]")).getGroupsManager().createGroup("[DownF]");
    ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().getObject("[Monitor]")).getGroupsManager().createGroup("[Drag]");
    ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().getObject("[Monitor]")).getGroupsManager().createGroup("[SideF]");
  
  if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
  ((ClientServerObjectGroup) ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().getObject("[Monitor]")).getGroupsManager().getObject("[DownF]")).getGroupsManager().groupObjects("[DownF]", new NeoObjectVector(new Object[] {forceReport_1, forceReport_32, forceReport_6, forceReport_8, forceReport_10, forceReport_11, forceReport_12, forceReport_13, forceReport_14, forceReport_15, forceReport_16, forceReport_17, forceReport_21, forceReport_19, forceReport_23, forceReport_25, forceReport_27, forceReport_29, forceReport_34, forceReport_36, forceReport_0, forceReport_31, forceReport_3, forceReport_5,forceReport_1000}), true);
    ((ClientServerObjectGroup) ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().getObject("[Monitor]")).getGroupsManager().getObject("[Drag]")).getGroupsManager().groupObjects("[Drag]", new NeoObjectVector(new Object[] {forceReport_37, forceReport_38, forceReport_39, forceReport_41, forceReport_43, forceReport_44, forceReport_45, forceReport_46, forceReport_47, forceReport_48, forceReport_49, forceReport_50, forceReport_51, forceReport_54, forceReport_56, forceReport_58, forceReport_60, forceReport_62, forceReport_64, forceReport_66, forceReport_67, forceReport_69, forceReport_71, forceReport_73,forceReport_1001}), true);
    ((ClientServerObjectGroup) ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().getObject("[Monitor]")).getGroupsManager().getObject("[SideF]")).getGroupsManager().groupObjects("[SideF]", new NeoObjectVector(new Object[] {forceReport_74, forceReport_75, forceReport_76, forceReport_78, forceReport_80, forceReport_81, forceReport_82, forceReport_83, forceReport_84, forceReport_85, forceReport_86, forceReport_87, forceReport_88, forceReport_91, forceReport_93, forceReport_95, forceReport_97, forceReport_99, forceReport_101, forceReport_103, forceReport_104, forceReport_106, forceReport_108, forceReport_110,forceReport_1002}), true);
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
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - Diff Lateral (L)"));
  ForceReport forceReport_2 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - Tyre FL"));
  ForceReport forceReport_4 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[DownF] - Tyre RL"));
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
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - Diff Lateral (L)"));
  ForceReport forceReport_70 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - Tyre FL"));
  ForceReport forceReport_72 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[Drag] - Tyre RL"));
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
      ((ForceReport) simulation_0.getReportManager().getReport("[SideF] - Diff Lateral (L)"));
  ForceReport forceReport_107 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[SideF] - Tyre FL"));
  ForceReport forceReport_109 = 
      ((ForceReport) simulation_0.getReportManager().getReport("[SideF] - Tyre RL"));
    
    ((ClientServerObjectGroup) ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().getObject("[Monitor]")).getGroupsManager().getObject("[DownF]")).getGroupsManager().groupObjects("[DownF]", new NeoObjectVector(new Object[] {forceReport_1, forceReport_32, forceReport_6, forceReport_7, forceReport_8, forceReport_9, forceReport_10, forceReport_11, forceReport_12, forceReport_13, forceReport_14, forceReport_15, forceReport_16, forceReport_17, forceReport_21, forceReport_20, forceReport_18, forceReport_19, forceReport_22, forceReport_23, forceReport_24, forceReport_25, forceReport_26, forceReport_27, forceReport_28, forceReport_29, forceReport_33, forceReport_34, forceReport_35, forceReport_36, forceReport_0, forceReport_30, forceReport_31, forceReport_2, forceReport_3, forceReport_4, forceReport_5,forceReport_1000}), true);
    ((ClientServerObjectGroup) ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().getObject("[Monitor]")).getGroupsManager().getObject("[Drag]")).getGroupsManager().groupObjects("[Drag]", new NeoObjectVector(new Object[] {forceReport_37, forceReport_38, forceReport_39, forceReport_40, forceReport_41, forceReport_42, forceReport_43, forceReport_44, forceReport_45, forceReport_46, forceReport_47, forceReport_48, forceReport_49, forceReport_50, forceReport_51, forceReport_52, forceReport_53, forceReport_54, forceReport_55, forceReport_56, forceReport_57, forceReport_58, forceReport_59, forceReport_60, forceReport_61, forceReport_62, forceReport_63, forceReport_64, forceReport_65, forceReport_66, forceReport_67, forceReport_68, forceReport_69, forceReport_70, forceReport_71, forceReport_72, forceReport_73,forceReport_1001}), true);
    ((ClientServerObjectGroup) ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().getObject("[Monitor]")).getGroupsManager().getObject("[SideF]")).getGroupsManager().groupObjects("[SideF]", new NeoObjectVector(new Object[] {forceReport_74, forceReport_75, forceReport_76, forceReport_77, forceReport_78, forceReport_79, forceReport_80, forceReport_81, forceReport_82, forceReport_83, forceReport_84, forceReport_85, forceReport_86, forceReport_87, forceReport_88, forceReport_89, forceReport_90, forceReport_91, forceReport_92, forceReport_93, forceReport_94, forceReport_95, forceReport_96, forceReport_97, forceReport_98, forceReport_99, forceReport_100, forceReport_101, forceReport_102, forceReport_103, forceReport_104, forceReport_105, forceReport_106, forceReport_107, forceReport_108, forceReport_109, forceReport_110,forceReport_1002}), true);
  }

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
  }

    private void Monitors() { 
  
  //{ // Last iter Monitors
   
    //{ // Solver Monitors

    Simulation simulation_0 = 
      getActiveSimulation();
    
  double Roll = simulation_0.getReportManager().getReport("Roll [deg]").getReportMonitorValue();
  double Yaw = simulation_0.getReportManager().getReport("Yaw [rad]").getReportMonitorValue();
  double CornerRadius = simulation_0.getReportManager().getReport("Corner Radius [m]").getReportMonitorValue();
  double CoolingMode = simulation_0.getReportManager().getReport("Cooling Mode").getReportMonitorValue();

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

    ResidualMonitor residualMonitor_6 = 
      ((ResidualMonitor) simulation_0.getMonitorManager().getMonitor("Intermittency"));
    residualMonitor_6.getNormalizeOption().setSelected(MonitorNormalizeOption.Type.OFF);

    simulation_0.getMonitorManager().getGroupsManager().createGroup("[Residuals]");
    ((ClientServerObjectGroup) simulation_0.getMonitorManager().getGroupsManager().getObject("[Residuals]")).getGroupsManager().groupObjects("[Residuals]", new NeoObjectVector(new Object[] {residualMonitor_5, residualMonitor_2, residualMonitor_0, residualMonitor_1, residualMonitor_3, residualMonitor_4, residualMonitor_6}), true);

    simulation_0.getMonitorManager().getGroupsManager().createGroup("[Time]");

    IterationMonitor iterationMonitor_0 = 
      ((IterationMonitor) simulation_0.getMonitorManager().getMonitor("Iteration"));

    PhysicalTimeMonitor physicalTimeMonitor_0 = 
      ((PhysicalTimeMonitor) simulation_0.getMonitorManager().getMonitor("Physical Time"));

    ((ClientServerObjectGroup) simulation_0.getMonitorManager().getGroupsManager().getObject("[Time]")).getGroupsManager().groupObjects("[Time]", new NeoObjectVector(new Object[] {iterationMonitor_0, physicalTimeMonitor_0}), true);
  //}
  
    //{ // CLoads Monitors

    CenterOfLoadsReport centerOfLoadsReport_0 = 
      ((CenterOfLoadsReport) simulation_0.getReportManager().getReport("[CLoadX] - CoG"));
    ReportMonitor reportMonitor_0 = 
      centerOfLoadsReport_0.createMonitor();

    CenterOfLoadsReport centerOfLoadsReport_1 = 
      ((CenterOfLoadsReport) simulation_0.getReportManager().getReport("[CLoadX] - Ground"));
    ReportMonitor reportMonitor_1 = 
      centerOfLoadsReport_1.createMonitor();

    CenterOfLoadsReport centerOfLoadsReport_2 = 
      ((CenterOfLoadsReport) simulation_0.getReportManager().getReport("[CLoadY] - CoG"));
    ReportMonitor reportMonitor_2 = 
      centerOfLoadsReport_2.createMonitor();

    CenterOfLoadsReport centerOfLoadsReport_3 = 
      ((CenterOfLoadsReport) simulation_0.getReportManager().getReport("[CLoadY] - Ground"));
    ReportMonitor reportMonitor_3 = 
      centerOfLoadsReport_3.createMonitor();

    CenterOfLoadsReport centerOfLoadsReport_4 = 
      ((CenterOfLoadsReport) simulation_0.getReportManager().getReport("[CLoadZ] - CoG"));
    ReportMonitor reportMonitor_4 = 
      centerOfLoadsReport_4.createMonitor();

    CenterOfLoadsReport centerOfLoadsReport_5 = 
      ((CenterOfLoadsReport) simulation_0.getReportManager().getReport("[CLoadZ] - Ground"));
    ReportMonitor reportMonitor_5 = 
      centerOfLoadsReport_5.createMonitor();

    reportMonitor_0.setPresentationName("[CLoadX] - CoG");
    reportMonitor_1.setPresentationName("[CLoadX] - Ground");
    reportMonitor_2.setPresentationName("[CLoadY] - CoG");
    reportMonitor_3.setPresentationName("[CLoadY] - Ground");
    reportMonitor_4.setPresentationName("[CLoadZ] - CoG");
    reportMonitor_5.setPresentationName("[CLoadZ] - Ground");
  
  // Cooling Monitors //

    simulation_0.getMonitorManager().getGroupsManager().createGroup("[CLoads]");
    ((ClientServerObjectGroup) simulation_0.getMonitorManager().getGroupsManager().getObject("[CLoads]")).getGroupsManager().createGroup("[Total]");
    ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getMonitorManager().getGroupsManager().getObject("[CLoads]")).getGroupsManager().getObject("[Total]")).getGroupsManager().groupObjects("[Total]", new NeoObjectVector(new Object[] {reportMonitor_0, reportMonitor_1, reportMonitor_2, reportMonitor_3, reportMonitor_4, reportMonitor_5}), true);
  
  if (CoolingMode != 0){
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
  }

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

  if (CoolingMode != 0){
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
  }

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

  if (CoolingMode != 0){
    ReportMonitor reportMonitor_7 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Mass Flow] - Fan (R)"));
  ReportMonitor reportMonitor_11 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[PDrop] - Fan (R)"));
  ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getMonitorManager().getGroupsManager().getObject("[Cooling]")).getGroupsManager().getObject("[Monitor]")).getGroupsManager().groupObjects("[Monitor]", new NeoObjectVector(new Object[] {reportMonitor_7, reportMonitor_9, reportMonitor_11, reportMonitor_13}), true);
  }
  else{
    ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getMonitorManager().getGroupsManager().getObject("[Cooling]")).getGroupsManager().getObject("[Monitor]")).getGroupsManager().groupObjects("[Monitor]", new NeoObjectVector(new Object[] {reportMonitor_9, reportMonitor_13}), true);
  }

  } else {
      ReportMonitor reportMonitor_8 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Mass Flow] - Radiator (L)"));
        ReportMonitor reportMonitor_12 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[PDrop] - Radiator (L)"));

    if (CoolingMode != 0){
    ReportMonitor reportMonitor_7 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Mass Flow] - Fan (R)"));
  ReportMonitor reportMonitor_11 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[PDrop] - Fan (R)"));
  ReportMonitor reportMonitor_6 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Mass Flow] - Fan (L)"));
  ReportMonitor reportMonitor_10 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[PDrop] - Fan (L)"));

    ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getMonitorManager().getGroupsManager().getObject("[Cooling]")).getGroupsManager().getObject("[Monitor]")).getGroupsManager().groupObjects("[Monitor]", new NeoObjectVector(new Object[] {reportMonitor_6, reportMonitor_7, reportMonitor_8, reportMonitor_9, reportMonitor_10, reportMonitor_11, reportMonitor_12, reportMonitor_13}), true);
  }
  else{
     ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getMonitorManager().getGroupsManager().getObject("[Cooling]")).getGroupsManager().getObject("[Monitor]")).getGroupsManager().groupObjects("[Monitor]", new NeoObjectVector(new Object[] {reportMonitor_8, reportMonitor_9, reportMonitor_12, reportMonitor_13}), true);
  }
  }

  // Drag DF SideF Monitors //

    simulation_0.getMonitorManager().getGroupsManager().createGroup("[Forces]");

    ((ClientServerObjectGroup) simulation_0.getMonitorManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().createGroup("[DownF]");
    ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getMonitorManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().getObject("[DownF]")).getGroupsManager().createGroup("[Monitor]");

    ((ClientServerObjectGroup) simulation_0.getMonitorManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().createGroup("[Drag]");
    ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getMonitorManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().getObject("[Drag]")).getGroupsManager().createGroup("[Monitor]");

    ((ClientServerObjectGroup) simulation_0.getMonitorManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().createGroup("[SideF]");
    ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getMonitorManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().getObject("[SideF]")).getGroupsManager().createGroup("[Monitor]");

    Collection<Report> reportCol = simulation_0.getReportManager().getObjects();
    ArrayList<Report> reportList = new ArrayList<>(reportCol);
    ArrayList<Report> reportsDownF = new ArrayList<>();
    ArrayList<Report> reportsDrag = new ArrayList<>();
    ArrayList<Report> reportsSideF = new ArrayList<>();
    ArrayList<Report> reportsMoments = new ArrayList<>();
    ArrayList<Report> reportsExtra = new ArrayList<>();

for (Report report : reportList){

  String reportName = report.getPresentationName();

  if (reportName.contains("[DownF]") == true) {
      reportsDownF.add(report);
      }
  else if (reportName.contains("[Drag]") == true) {
      reportsDrag.add(report);
      }
else if (reportName.contains("[SideF]") == true) {
      reportsSideF.add(report);
      }
}

  simulation_0.getMonitorManager().createMonitorAndPlot(reportsDownF, true, "Reports Plot");
  simulation_0.getMonitorManager().createMonitorAndPlot(reportsDrag, true, "Reports Plot");
  simulation_0.getMonitorManager().createMonitorAndPlot(reportsSideF, true, "Reports Plot");

    Collection<Monitor> monitorsCol = simulation_0.getMonitorManager().getMonitors();
    ArrayList<Monitor> monitorListD = new ArrayList<>(monitorsCol);
    ArrayList<Monitor> monitorsDownF = new ArrayList<>();
    ArrayList<Monitor> monitorsDrag = new ArrayList<>();
    ArrayList<Monitor> monitorsSideF = new ArrayList<>();

for (Monitor monitor : monitorListD){

  String monitorName = monitor.getPresentationName();

  if (monitorName.contains("[DownF]") == true) {
      monitorsDownF.add(monitor);
      ((ClientServerObjectGroup) ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getMonitorManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().getObject("[DownF]")).getGroupsManager().getObject("[Monitor]")).getGroupsManager().groupObjects("[Monitor]", new NeoObjectVector(new Object[] {monitor}), true);
}
else if (monitorName.contains("[Drag]") == true) {
      monitorsDrag.add(monitor);
      ((ClientServerObjectGroup) ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getMonitorManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().getObject("[Drag]")).getGroupsManager().getObject("[Monitor]")).getGroupsManager().groupObjects("[Monitor]", new NeoObjectVector(new Object[] {monitor}), true);
  
}
else if (monitorName.contains("[SideF]") == true) {
      monitorsSideF.add(monitor);
      ((ClientServerObjectGroup) ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getMonitorManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().getObject("[SideF]")).getGroupsManager().getObject("[Monitor]")).getGroupsManager().groupObjects("[Monitor]", new NeoObjectVector(new Object[] {monitor}), true);
}
}

    // Moments Monitors //
  
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

//{ // Mean Monitors  
  
    //{ // CLoads Mean Monitors
  
    StatisticsReport statisticsReport_0 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_0.setPresentationName("[CLoadX] - CoG Mean");
    statisticsReport_0.setSampleFilterOption(SampleFilterOption.LastNSamples);
  statisticsReport_0.setMonitor(reportMonitor_0);

    LastNSamplesFilter lastNSamplesFilter_0 = 
      ((LastNSamplesFilter) statisticsReport_0.getSampleFilterManager().getObject("Last N Samples"));
    lastNSamplesFilter_0.setNSamples(100);

    StatisticsReport statisticsReport_1 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_1.copyProperties(statisticsReport_0);
    statisticsReport_1.setPresentationName("[CLoadY] - CoG Mean");
  statisticsReport_1.setMonitor(reportMonitor_2);

    StatisticsReport statisticsReport_2 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_2.copyProperties(statisticsReport_1);
    statisticsReport_2.setPresentationName("[CLoadZ] - CoG Mean");
    statisticsReport_2.setMonitor(reportMonitor_4);

    StatisticsReport statisticsReport_3 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_3.copyProperties(statisticsReport_0);
    statisticsReport_3.setPresentationName("[CLoadX] - Ground Mean");
    statisticsReport_3.setMonitor(reportMonitor_1);

    StatisticsReport statisticsReport_4 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_4.copyProperties(statisticsReport_3);
    statisticsReport_4.setPresentationName("[CLoadY] - Ground Mean");
    statisticsReport_4.setMonitor(reportMonitor_3);

    StatisticsReport statisticsReport_5 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_5.copyProperties(statisticsReport_4);
    statisticsReport_5.setPresentationName("[CLoadZ] - Ground Mean");
    statisticsReport_5.setMonitor(reportMonitor_5);

    ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[CLoads]")).getGroupsManager().createGroup("[Avg.]");
    ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[CLoads]")).getGroupsManager().getObject("[Avg.]")).getGroupsManager().groupObjects("[Avg.]", new NeoObjectVector(new Object[] {statisticsReport_0, statisticsReport_3, statisticsReport_1, statisticsReport_4, statisticsReport_2, statisticsReport_5}), true);
  //}
  
    // Cooling Mean Monitors //

    if (CoolingMode != 0) {
    StatisticsReport statisticsReport_6 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_6.setSampleFilterOption(SampleFilterOption.LastNSamples);
    statisticsReport_6.setPresentationName("[MFlow] - Fan (R) Mean");
    ReportMonitor reportMonitor_7 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Mass Flow] - Fan (R)"));
    statisticsReport_6.setMonitor(reportMonitor_7);

    LastNSamplesFilter lastNSamplesFilter_1 = 
      ((LastNSamplesFilter) statisticsReport_6.getSampleFilterManager().getObject("Last N Samples"));
    lastNSamplesFilter_1.setNSamples(100);

  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    StatisticsReport statisticsReport_7 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
  ReportMonitor reportMonitor_6 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Mass Flow] - Fan (L)"));
    statisticsReport_7.copyProperties(statisticsReport_6);
    statisticsReport_7.setPresentationName("[MFlow] - Fan (L) Mean");
    statisticsReport_7.setMonitor(reportMonitor_6);}
  }

  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    StatisticsReport statisticsReport_8 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
  ReportMonitor reportMonitor_8 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Mass Flow] - Radiator (L)"));
    statisticsReport_8.setPresentationName("[MFlow] - Radiator (L) Mean");
    statisticsReport_8.setMonitor(reportMonitor_8);
    LastNSamplesFilter lastNSamplesFilter_1 = 
      ((LastNSamplesFilter) statisticsReport_8.getSampleFilterManager().getObject("Last N Samples"));
    lastNSamplesFilter_1.setNSamples(100);
    }

    StatisticsReport statisticsReport_9 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_9.setPresentationName("[MFlow] - Radiator (R) Mean");
    statisticsReport_9.setMonitor(reportMonitor_9);
    LastNSamplesFilter lastNSamplesFilter_1 = 
      ((LastNSamplesFilter) statisticsReport_9.getSampleFilterManager().getObject("Last N Samples"));
    lastNSamplesFilter_1.setNSamples(100);

  if (CoolingMode != 0) {
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    StatisticsReport statisticsReport_10 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
  ReportMonitor reportMonitor_10 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[PDrop] - Fan (L)"));
    statisticsReport_10.copyProperties(statisticsReport_9);
    statisticsReport_10.setPresentationName("[PDrop] - Fan (L) Mean");
    statisticsReport_10.setMonitor(reportMonitor_10);}

    StatisticsReport statisticsReport_11 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_11.copyProperties(statisticsReport_9);
    statisticsReport_11.setPresentationName("[PDrop] - Fan (R) Mean");
    ReportMonitor reportMonitor_11 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[PDrop] - Fan (R)"));
    statisticsReport_11.setMonitor(reportMonitor_11);
  }

  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    StatisticsReport statisticsReport_12 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
  ReportMonitor reportMonitor_12 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[PDrop] - Radiator (L)"));
    statisticsReport_12.copyProperties(statisticsReport_9);
    statisticsReport_12.setPresentationName("[PDrop] - Radiator (L) Mean");
    statisticsReport_12.setMonitor(reportMonitor_12);}

    StatisticsReport statisticsReport_13 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_13.copyProperties(statisticsReport_9);
    statisticsReport_13.setPresentationName("[PDrop] - Radiator (R) Mean");
    statisticsReport_13.setMonitor(reportMonitor_13);
  
    ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Cooling]")).getGroupsManager().createGroup("[Avg.]");
  if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
  if (CoolingMode != 0) {
  StatisticsReport statisticsReport_6 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[MFlow] - Fan (R) Mean")); 
  StatisticsReport statisticsReport_11 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[PDrop] - Fan (R) Mean"));
  ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Cooling]")).getGroupsManager().getObject("[Avg.]")).getGroupsManager().groupObjects("[Avg.]", new NeoObjectVector(new Object[] {statisticsReport_6, statisticsReport_9, statisticsReport_11, statisticsReport_13}), true);
  }
  else{
    ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Cooling]")).getGroupsManager().getObject("[Avg.]")).getGroupsManager().groupObjects("[Avg.]", new NeoObjectVector(new Object[] {statisticsReport_9, statisticsReport_13}), true);
  }
  } else {
    StatisticsReport statisticsReport_8 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[MFlow] - Radiator (L) Mean"));
    StatisticsReport statisticsReport_12 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[PDrop] - Radiator (L) Mean"));
  if (CoolingMode != 0) {
  StatisticsReport statisticsReport_6 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[MFlow] - Fan (R) Mean")); 
  StatisticsReport statisticsReport_11 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[PDrop] - Fan (R) Mean"));
  StatisticsReport statisticsReport_7 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[MFlow] - Fan (L) Mean"));  
  StatisticsReport statisticsReport_10 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[PDrop] - Fan (L) Mean"));
    
    ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Cooling]")).getGroupsManager().getObject("[Avg.]")).getGroupsManager().groupObjects("[Avg.]", new NeoObjectVector(new Object[] {statisticsReport_6, statisticsReport_7, statisticsReport_8, statisticsReport_9, statisticsReport_10, statisticsReport_11, statisticsReport_12, statisticsReport_13}), true);
  }
  else{
    ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Cooling]")).getGroupsManager().getObject("[Avg.]")).getGroupsManager().groupObjects("[Avg.]", new NeoObjectVector(new Object[] {statisticsReport_8, statisticsReport_9, statisticsReport_12, statisticsReport_13}), true);
  }
  }
  

  // DownForce Mean Monitors //

    StatisticsReport statisticsReport_14 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_14.setPresentationName("[DownF] - Rear Diff Mean");
    statisticsReport_14.setSampleFilterOption(SampleFilterOption.LastNSamples);

    LastNSamplesFilter lastNSamplesFilter_2 = 
      ((LastNSamplesFilter) statisticsReport_14.getSampleFilterManager().getObject("Last N Samples"));
    lastNSamplesFilter_2.setNSamples(100);
    ReportMonitor reportMonitor_14 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[DownF] - Rear Diff Monitor"));

    statisticsReport_14.setMonitor(reportMonitor_14);

    StatisticsReport statisticsReport_15 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_15.copyProperties(statisticsReport_14);
    statisticsReport_15.setPresentationName("[DownF] - FW Mean");
    ReportMonitor reportMonitor_16 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[DownF] - FW Monitor"));
    statisticsReport_15.setMonitor(reportMonitor_16);

    StatisticsReport statisticsReport_16 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_16.copyProperties(statisticsReport_15);
    statisticsReport_16.setPresentationName("[DownF] - Mono Mean");
    ReportMonitor reportMonitor_22 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[DownF] - Mono Monitor"));
    statisticsReport_16.setMonitor(reportMonitor_22);

    StatisticsReport statisticsReport_17 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_17.copyProperties(statisticsReport_15);
    statisticsReport_17.setPresentationName("[DownF] - RW Mean");
    ReportMonitor reportMonitor_23 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[DownF] - RW Monitor"));
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
    ReportMonitor reportMonitor_31 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[DownF] - SC(R) Monitor"));
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
    ReportMonitor reportMonitor_41 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[DownF] - SU FR Monitor"));
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
    ReportMonitor reportMonitor_43 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[DownF] - SU RR Monitor"));
    statisticsReport_23.setMonitor(reportMonitor_43);

    StatisticsReport statisticsReport_24 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_24.copyProperties(statisticsReport_17);
    statisticsReport_24.setPresentationName("[DownF] - Total Mean");
    ReportMonitor reportMonitor_44 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[DownF] - Total Monitor"));
    statisticsReport_24.setMonitor(reportMonitor_44);

  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    StatisticsReport statisticsReport_25 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
  ReportMonitor reportMonitor_45 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[DownF] - Diff Lateral (L) Monitor"));
    statisticsReport_25.copyProperties(statisticsReport_17);
    statisticsReport_25.setPresentationName("[DownF] - Diff Lateral (L) Mean");
    statisticsReport_25.setMonitor(reportMonitor_45);}

    StatisticsReport statisticsReport_26 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_26.copyProperties(statisticsReport_17);
    statisticsReport_26.setPresentationName("[DownF] - Diff Lateral (R) Mean");
    ReportMonitor reportMonitor_46 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[DownF] - Diff Lateral (R) Monitor"));
    statisticsReport_26.setMonitor(reportMonitor_46);

  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    StatisticsReport statisticsReport_27 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
  ReportMonitor reportMonitor_47 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[DownF] - Tyre FL Monitor"));
    statisticsReport_27.copyProperties(statisticsReport_17);
    statisticsReport_27.setPresentationName("[DownF] - Tyre FL Mean");
    statisticsReport_27.setMonitor(reportMonitor_47);}

    StatisticsReport statisticsReport_28 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_28.copyProperties(statisticsReport_17);
    statisticsReport_28.setPresentationName("[DownF] - Tyre FR Mean");
    ReportMonitor reportMonitor_48 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[DownF] - Tyre FR Monitor"));
    statisticsReport_28.setMonitor(reportMonitor_48);

  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    StatisticsReport statisticsReport_29 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
  ReportMonitor reportMonitor_49 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[DownF] - Tyre RL Monitor"));
    statisticsReport_29.copyProperties(statisticsReport_17);
    statisticsReport_29.setPresentationName("[DownF] - Tyre RL Mean");
    statisticsReport_29.setMonitor(reportMonitor_49);}

    StatisticsReport statisticsReport_30 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_30.copyProperties(statisticsReport_17);
    statisticsReport_30.setPresentationName("[DownF] - Tyre RR Mean");
    ReportMonitor reportMonitor_50 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[DownF] - Tyre FR Monitor"));
    statisticsReport_30.setMonitor(reportMonitor_50);

    StatisticsReport statisticsReport_1000 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_1000.copyProperties(statisticsReport_17);
    statisticsReport_1000.setPresentationName("[DownF] - Misc Mean");
    ReportMonitor reportMonitor_1000 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[DownF] - Misc Monitor"));
    statisticsReport_1000.setMonitor(reportMonitor_1000);

    ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().createGroup("[Avg.]");
    ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().getObject("[Avg.]")).getGroupsManager().createGroup("[DownF]");
  
      //{ // Drag Mean Monitors
  
    StatisticsReport statisticsReport_31 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_31.copyProperties(statisticsReport_14);
    statisticsReport_31.setPresentationName("[Drag] - Rear Diff Mean");
    ReportMonitor reportMonitor_51 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Drag] - Rear Diff Monitor"));
    statisticsReport_31.setMonitor(reportMonitor_51);

    StatisticsReport statisticsReport_32 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_32.copyProperties(statisticsReport_15);
    statisticsReport_32.setPresentationName("[Drag] - FW Mean");
    ReportMonitor reportMonitor_53 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Drag] - FW Monitor"));
    statisticsReport_32.setMonitor(reportMonitor_53);

    StatisticsReport statisticsReport_33 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_33.copyProperties(statisticsReport_16);
    statisticsReport_33.setPresentationName("[Drag] - Mono Mean");
    ReportMonitor reportMonitor_59 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Drag] - Mono Monitor"));
    statisticsReport_33.setMonitor(reportMonitor_59);

    StatisticsReport statisticsReport_34 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_34.copyProperties(statisticsReport_17);
    statisticsReport_34.setPresentationName("[Drag] - RW Mean");
    ReportMonitor reportMonitor_60 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Drag] - RW Monitor"));
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
    ReportMonitor reportMonitor_68 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Drag] - SC(R) Monitor"));
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
    ReportMonitor reportMonitor_78 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Drag] - SU FR Monitor"));
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
    ReportMonitor reportMonitor_80 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Drag] - SU RR Monitor"));
    statisticsReport_40.setMonitor(reportMonitor_80);

    StatisticsReport statisticsReport_41 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_41.copyProperties(statisticsReport_24);
    statisticsReport_41.setPresentationName("[Drag] - Total Mean");
    ReportMonitor reportMonitor_81 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Drag] - Total Monitor"));
    statisticsReport_41.setMonitor(reportMonitor_81);

  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    StatisticsReport statisticsReport_42 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
  StatisticsReport statisticsReport_25 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[DownF] - Diff Lateral (L) Mean"));
    statisticsReport_42.copyProperties(statisticsReport_25);
    statisticsReport_42.setPresentationName("[Drag] - Diff Lateral (L) Mean");
  ReportMonitor reportMonitor_82 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Drag] - Diff Lateral (L) Monitor"));
    statisticsReport_42.setMonitor(reportMonitor_82);}

    StatisticsReport statisticsReport_43 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_43.copyProperties(statisticsReport_26);
    statisticsReport_43.setPresentationName("[Drag] - Diff Lateral (R) Mean");
    ReportMonitor reportMonitor_83 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Drag] - Diff Lateral (R) Monitor"));
    statisticsReport_43.setMonitor(reportMonitor_83);

  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    StatisticsReport statisticsReport_44 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
  StatisticsReport statisticsReport_27 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[DownF] - Tyre FL Mean"));
    statisticsReport_44.copyProperties(statisticsReport_27);
    statisticsReport_44.setPresentationName("[Drag] - Tyre FL Mean");
  ReportMonitor reportMonitor_84 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Drag] - Tyre FL Monitor"));
    statisticsReport_44.setMonitor(reportMonitor_84);}

    StatisticsReport statisticsReport_45 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_45.copyProperties(statisticsReport_28);
    statisticsReport_45.setPresentationName("[Drag] - Tyre FR Mean");
    ReportMonitor reportMonitor_85 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Drag] - Tyre FR Monitor"));
    statisticsReport_45.setMonitor(reportMonitor_85);

  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    StatisticsReport statisticsReport_46 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
  StatisticsReport statisticsReport_29 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[DownF] - Tyre RL Mean"));
    statisticsReport_46.copyProperties(statisticsReport_29);
    statisticsReport_46.setPresentationName("[Drag] - Tyre RL Mean");
  ReportMonitor reportMonitor_86 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Drag] - Tyre RL Monitor"));
    statisticsReport_46.setMonitor(reportMonitor_86);}

    StatisticsReport statisticsReport_47 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_47.copyProperties(statisticsReport_30);
    statisticsReport_47.setPresentationName("[Drag] - Tyre RR Mean");
    ReportMonitor reportMonitor_87 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Drag] - Tyre RR Monitor"));
    statisticsReport_47.setMonitor(reportMonitor_87);

    StatisticsReport statisticsReport_1001 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_1001.copyProperties(statisticsReport_17);
    statisticsReport_1001.setPresentationName("[Drag] - Misc Mean");
    ReportMonitor reportMonitor_1001 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Drag] - Misc Monitor"));
    statisticsReport_1001.setMonitor(reportMonitor_1001);
  
  if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
  ((ClientServerObjectGroup) ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().getObject("[Avg.]")).getGroupsManager().getObject("[DownF]")).getGroupsManager().groupObjects("[DownF]", new NeoObjectVector(new Object[] {statisticsReport_14, statisticsReport_15, statisticsReport_16, statisticsReport_17, statisticsReport_19, statisticsReport_21, statisticsReport_23, statisticsReport_24, statisticsReport_26, statisticsReport_28, statisticsReport_30, statisticsReport_1000}), true);  
  } else {
  StatisticsReport statisticsReport_18 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[DownF] - SC(L) Mean"));
  StatisticsReport statisticsReport_20 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[DownF] - SU FL Mean"));
    StatisticsReport statisticsReport_22 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[DownF] - SU RL Mean"));
    StatisticsReport statisticsReport_25 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[DownF] - Diff Lateral (L) Mean"));
    StatisticsReport statisticsReport_27 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[DownF] - Tyre FL Mean"));
    StatisticsReport statisticsReport_29 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[DownF] - Tyre RL Mean"));
  ((ClientServerObjectGroup) ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().getObject("[Avg.]")).getGroupsManager().getObject("[DownF]")).getGroupsManager().groupObjects("[DownF]", new NeoObjectVector(new Object[] {statisticsReport_14, statisticsReport_15, statisticsReport_16, statisticsReport_17, statisticsReport_18, statisticsReport_19, statisticsReport_20, statisticsReport_21, statisticsReport_22, statisticsReport_23, statisticsReport_24, statisticsReport_25, statisticsReport_26, statisticsReport_27, statisticsReport_28, statisticsReport_29, statisticsReport_30, statisticsReport_1000}), true);
    }
  
  ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().getObject("[Avg.]")).getGroupsManager().createGroup("[Drag]");
  //}

    //{ // SideForce Mean Monitors

    StatisticsReport statisticsReport_48 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_48.copyProperties(statisticsReport_31);
    statisticsReport_48.setPresentationName("[SideF] - Rear Diff Mean");
    ReportMonitor reportMonitor_88 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[SideF] - Rear Diff Monitor"));
    statisticsReport_48.setMonitor(reportMonitor_88);

    StatisticsReport statisticsReport_49 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_49.copyProperties(statisticsReport_32);
    statisticsReport_49.setPresentationName("[SideF] - FW Mean");
    ReportMonitor reportMonitor_90 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[SideF] - FW Monitor"));
    statisticsReport_49.setMonitor(reportMonitor_90);

    StatisticsReport statisticsReport_50 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_50.copyProperties(statisticsReport_33);
    statisticsReport_50.setPresentationName("[SideF] - Mono Mean");
    ReportMonitor reportMonitor_96 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[SideF] - Mono Monitor"));
    statisticsReport_50.setMonitor(reportMonitor_96);

    StatisticsReport statisticsReport_51 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_51.copyProperties(statisticsReport_34);
    statisticsReport_51.setPresentationName("[SideF] - RW Mean");
    ReportMonitor reportMonitor_97 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[SideF] - RW Monitor"));
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
    ReportMonitor reportMonitor_105 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[SideF] - SC(R) Monitor"));
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
    ReportMonitor reportMonitor_115 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[SideF] - SU FR Monitor"));
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
    ReportMonitor reportMonitor_117 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[SideF] - SU RR Monitor"));
    statisticsReport_57.setMonitor(reportMonitor_117);

    StatisticsReport statisticsReport_58 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_58.copyProperties(statisticsReport_41);
    statisticsReport_58.setPresentationName("[SideF] - Total Mean");
    ReportMonitor reportMonitor_118 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[SideF] - Total Monitor"));
    statisticsReport_58.setMonitor(reportMonitor_118);

  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    StatisticsReport statisticsReport_59 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
  StatisticsReport statisticsReport_42 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[Drag] - Diff Lateral (L) Mean"));
    statisticsReport_59.copyProperties(statisticsReport_42);
  ReportMonitor reportMonitor_119 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[SideF] - Diff Lateral (L) Monitor"));
    statisticsReport_59.setPresentationName("[SideF] - Diff Lateral (L) Mean");
    statisticsReport_59.setMonitor(reportMonitor_119);}

    StatisticsReport statisticsReport_60 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_60.copyProperties(statisticsReport_43);
    statisticsReport_60.setPresentationName("[SideF] - Diff Lateral (R) Mean");
    ReportMonitor reportMonitor_120 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[SideF] - Diff Lateral (R) Monitor"));
    statisticsReport_60.setMonitor(reportMonitor_120);

  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    StatisticsReport statisticsReport_61 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
  StatisticsReport statisticsReport_44 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[Drag] - Tyre FL Mean"));
    statisticsReport_61.copyProperties(statisticsReport_44);
  ReportMonitor reportMonitor_121 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[SideF] - Tyre FL Monitor"));
    statisticsReport_61.setPresentationName("[SideF] - Tyre FL Mean");
    statisticsReport_61.setMonitor(reportMonitor_121);}
  
    StatisticsReport statisticsReport_62 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_62.copyProperties(statisticsReport_45);
    statisticsReport_62.setPresentationName("[SideF] - Tyre FR Mean");
    ReportMonitor reportMonitor_122 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[SideF] - Tyre FR Monitor"));
    statisticsReport_62.setMonitor(reportMonitor_122);

  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    StatisticsReport statisticsReport_63 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
  StatisticsReport statisticsReport_46 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[Drag] - Tyre RL Mean"));
    statisticsReport_63.copyProperties(statisticsReport_46);
  ReportMonitor reportMonitor_123 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[SideF] - Tyre RL Monitor"));  
    statisticsReport_63.setPresentationName("[SideF] - Tyre RL Mean");
    statisticsReport_63.setMonitor(reportMonitor_123);}

    StatisticsReport statisticsReport_64 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_64.copyProperties(statisticsReport_47);
    statisticsReport_64.setPresentationName("[SideF] - Tyre RR Mean");
    ReportMonitor reportMonitor_124 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[SideF] - Tyre RR Monitor"));
    statisticsReport_64.setMonitor(reportMonitor_124);

    StatisticsReport statisticsReport_1002 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_1002.copyProperties(statisticsReport_47);
    statisticsReport_1002.setPresentationName("[SideF] - Misc Mean");
    ReportMonitor reportMonitor_1002 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[SideF] - Misc Monitor"));
    statisticsReport_1002.setMonitor(reportMonitor_1002);



  if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
  ((ClientServerObjectGroup) ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().getObject("[Avg.]")).getGroupsManager().getObject("[Drag]")).getGroupsManager().groupObjects("[Drag]", new NeoObjectVector(new Object[] {statisticsReport_31, statisticsReport_32, statisticsReport_33, statisticsReport_34, statisticsReport_36, statisticsReport_38, statisticsReport_40, statisticsReport_41, statisticsReport_43, statisticsReport_45, statisticsReport_47,statisticsReport_1001}), true);
  } else {
  StatisticsReport statisticsReport_35 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[Drag] - SC(L) Mean"));  
  StatisticsReport statisticsReport_37 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[Drag] - SU FL Mean"));
    StatisticsReport statisticsReport_39 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[Drag] - SU RL Mean"));
    StatisticsReport statisticsReport_42 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[Drag] - Diff Lateral (L) Mean"));
    StatisticsReport statisticsReport_44 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[Drag] - Tyre FL Mean"));
  StatisticsReport statisticsReport_46 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[Drag] - Tyre RL Mean")); 
    ((ClientServerObjectGroup) ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().getObject("[Avg.]")).getGroupsManager().getObject("[Drag]")).getGroupsManager().groupObjects("[Drag]", new NeoObjectVector(new Object[] {statisticsReport_31, statisticsReport_32, statisticsReport_33, statisticsReport_34, statisticsReport_35, statisticsReport_36, statisticsReport_37, statisticsReport_38, statisticsReport_39, statisticsReport_40, statisticsReport_41, statisticsReport_42, statisticsReport_43, statisticsReport_44, statisticsReport_45, statisticsReport_46, statisticsReport_47,statisticsReport_1001}), true);
  }

    ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().getObject("[Avg.]")).getGroupsManager().createGroup("[SideF]");
    
  if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
  ((ClientServerObjectGroup) ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().getObject("[Avg.]")).getGroupsManager().getObject("[SideF]")).getGroupsManager().groupObjects("[SideF]", new NeoObjectVector(new Object[] {statisticsReport_48, statisticsReport_49, statisticsReport_50, statisticsReport_51, statisticsReport_53, statisticsReport_55, statisticsReport_57, statisticsReport_58, statisticsReport_60, statisticsReport_62, statisticsReport_64,statisticsReport_1002}), true);
  } else {
  StatisticsReport statisticsReport_52 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[SideF] - SC(L) Mean")); 
  StatisticsReport statisticsReport_54 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[SideF] - SU FL Mean"));
    StatisticsReport statisticsReport_56 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[SideF] - SU RL Mean"));
    StatisticsReport statisticsReport_59 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[SideF] - Diff Lateral (L) Mean"));
    StatisticsReport statisticsReport_61 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[SideF] - Tyre FL Mean"));
  StatisticsReport statisticsReport_63 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[SideF] - Tyre RL Mean"));    
  ((ClientServerObjectGroup) ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[Forces]")).getGroupsManager().getObject("[Avg.]")).getGroupsManager().getObject("[SideF]")).getGroupsManager().groupObjects("[SideF]", new NeoObjectVector(new Object[] {statisticsReport_48, statisticsReport_49, statisticsReport_50, statisticsReport_51, statisticsReport_52, statisticsReport_53, statisticsReport_54, statisticsReport_55, statisticsReport_56, statisticsReport_57, statisticsReport_58, statisticsReport_59, statisticsReport_60, statisticsReport_61, statisticsReport_62, statisticsReport_63, statisticsReport_64,statisticsReport_1002}), true);
  }
  //}
  
    //{ // Moments Mean Monitors
  
    StatisticsReport statisticsReport_65 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_65.setPresentationName("[Mx] - CoG Mean");
    statisticsReport_65.setSampleFilterOption(SampleFilterOption.LastNSamples);

    LastNSamplesFilter lastNSamplesFilter_3 = 
      ((LastNSamplesFilter) statisticsReport_65.getSampleFilterManager().getObject("Last N Samples"));
    lastNSamplesFilter_3.setNSamples(100);

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
    lastNSamplesFilter_4.setNSamples(100);

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
    lastNSamplesFilter_5.setNSamples(100);
  
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
  
    /*//{ // Group CLoads Mean Monitors

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
      ((StatisticsReport) simulation_0.getReportManager().getReport("[DownF] - Diff Lateral (L) Mean"));
    ReportMonitor reportMonitor_156 = 
      statisticsReport_25.createMonitor();}
    
    ReportMonitor reportMonitor_157 = 
      statisticsReport_26.createMonitor();
    
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
  StatisticsReport statisticsReport_27 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[DownF] - Tyre FL Mean"));
    ReportMonitor reportMonitor_158 = 
      statisticsReport_27.createMonitor();}
    
    ReportMonitor reportMonitor_159 = 
      statisticsReport_28.createMonitor();
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
  StatisticsReport statisticsReport_29 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[DownF] - Tyre RL Mean"));  
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
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[DownF] - Diff Lateral (L) Mean Monitor"));
  ReportMonitor reportMonitor_158 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[DownF] - Tyre FL Mean Monitor"));
  ReportMonitor reportMonitor_160 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[DownF] - Tyre RL Mean Monitor"));
    
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
      ((StatisticsReport) simulation_0.getReportManager().getReport("[Drag] - Diff Lateral (L) Mean"));
    ReportMonitor reportMonitor_173 = 
      statisticsReport_42.createMonitor();}
    
    ReportMonitor reportMonitor_174 = 
      statisticsReport_43.createMonitor();
      
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
  StatisticsReport statisticsReport_44 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[Drag] - Tyre FL Mean"));
    ReportMonitor reportMonitor_175 = 
      statisticsReport_44.createMonitor();}
    
    ReportMonitor reportMonitor_176 = 
      statisticsReport_45.createMonitor();
      
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
  StatisticsReport statisticsReport_46 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[Drag] - Tyre RL Mean")); 
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
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Drag] - Diff Lateral (L) Mean Monitor"));
  ReportMonitor reportMonitor_175 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Drag] - Tyre FL Mean Monitor"));
  ReportMonitor reportMonitor_177 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Drag] - Tyre RL Mean Monitor"));
  
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
      ((StatisticsReport) simulation_0.getReportManager().getReport("[SideF] - Diff Lateral (L) Mean")); 
    ReportMonitor reportMonitor_190 = 
      statisticsReport_59.createMonitor();}
    
    ReportMonitor reportMonitor_191 = 
      statisticsReport_60.createMonitor();
    
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
  StatisticsReport statisticsReport_61 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[SideF] - Tyre FL Mean"));
    ReportMonitor reportMonitor_192 = 
      statisticsReport_61.createMonitor();}
    
    ReportMonitor reportMonitor_193 = 
      statisticsReport_62.createMonitor();
    
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
  StatisticsReport statisticsReport_63 = 
      ((StatisticsReport) simulation_0.getReportManager().getReport("[SideF] - Tyre RL Mean"));    
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
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[SideF] - Diff Lateral (L) Mean Monitor"));
  ReportMonitor reportMonitor_192 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[SideF] - Tyre FL Mean Monitor"));
  ReportMonitor reportMonitor_194 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[SideF] - Tyre RL Mean Monitor")); 
    
    
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
  //}*/
  
  //{ // Field Functions Mean

    FieldMeanMonitor fieldMeanMonitor_0 = 
      simulation_0.getMonitorManager().createMonitor(FieldMeanMonitor.class);
  fieldMeanMonitor_0.getParts().setQuery(new Query(new TypePredicate(TypeOperator.Is, Region.class), Query.STANDARD_MODIFIERS));

    QcriterionFunction qcriterionFunction_0 = 
      ((QcriterionFunction) simulation_0.getFieldFunctionManager().getFunction("Qcriterion"));
    fieldMeanMonitor_0.setPresentationName("Qcrit");
    fieldMeanMonitor_0.setFieldFunction(qcriterionFunction_0);

    StarUpdate starUpdate_0 = 
      fieldMeanMonitor_0.getStarUpdate();
    IterationUpdateFrequency iterationUpdateFrequency_0 = 
      starUpdate_0.getIterationUpdateFrequency();
    iterationUpdateFrequency_0.setStart(900);

    FieldMeanMonitor fieldMeanMonitor_2 = 
      simulation_0.getMonitorManager().createMonitor(FieldMeanMonitor.class);
    fieldMeanMonitor_2.copyProperties(fieldMeanMonitor_0);
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
    fieldMeanMonitor_8.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, InterfaceBoundary.class), new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.DoesNotContain, "Domain"), new NamePredicate(NameOperator.DoesNotContain, "Fan"), new NamePredicate(NameOperator.DoesNotContain, "Radiator"))))), Query.STANDARD_MODIFIERS));
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
    ((ClientServerObjectGroup) simulation_0.getMonitorManager().getGroupsManager().getObject("[FF]")).getGroupsManager().groupObjects("[FF]", new NeoObjectVector(new Object[] {fieldMeanMonitor_0, fieldMeanMonitor_3, fieldMeanMonitor_4, fieldMeanMonitor_5, fieldMeanMonitor_6, fieldMeanMonitor_7, fieldMeanMonitor_8, fieldMeanMonitor_9, fieldMeanMonitor_10, fieldMeanMonitor_2}), true);
  //}
  
  }

    private void Plots() {

  //{ // Center of Loads PLOT

    Simulation simulation_0 = 
      getActiveSimulation();
    
  double Roll = simulation_0.getReportManager().getReport("Roll [deg]").getReportMonitorValue();
  double Yaw = simulation_0.getReportManager().getReport("Yaw [rad]").getReportMonitorValue();
  double CornerRadius = simulation_0.getReportManager().getReport("Corner Radius [m]").getReportMonitorValue();
  double CoolingMode = simulation_0.getReportManager().getReport("Cooling Mode").getReportMonitorValue();
  double CarVelocity = simulation_0.getReportManager().getReport("Car-Velocity [ms-1]").getReportMonitorValue();
    
    ReportMonitor reportMonitor_0 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[CLoadX] - CoG"));

    MonitorPlot monitorPlot_0 = 
      simulation_0.getPlotManager().createMonitorPlot(new NeoObjectVector(new Object[] {reportMonitor_0}), "[CLoadX] - CoG Plot");
    monitorPlot_0.open();
    monitorPlot_0.setTitle("Centre of Loads");
    monitorPlot_0.setPresentationName("[CLoad]");

    MonitorDataSet monitorDataSet_0 = 
      ((MonitorDataSet) monitorPlot_0.getDataSetManager().getDataSet("[CLoadX] - CoG"));
    monitorDataSet_0.setSeriesNameLocked(true);
    monitorDataSet_0.setSeriesName("[X] - CoG");

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

    /*ReportMonitor reportMonitor_1 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[CLoadX] - CoG Mean Monitor"));
    ReportMonitor reportMonitor_2 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[CLoadX] - Ground Mean Monitor"));
    ReportMonitor reportMonitor_3 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[CLoadY] - CoG Mean Monitor"));
    ReportMonitor reportMonitor_4 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[CLoadY] - Ground Mean Monitor"));
    ReportMonitor reportMonitor_5 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[CLoadZ] - CoG Mean Monitor"));
    ReportMonitor reportMonitor_6 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[CLoadZ] - Ground Mean Monitor"));*/
    ReportMonitor reportMonitor_7 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[CLoadX] - Ground"));
    ReportMonitor reportMonitor_8 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[CLoadY] - CoG"));
    ReportMonitor reportMonitor_9 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[CLoadY] - Ground"));
    ReportMonitor reportMonitor_10 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[CLoadZ] - CoG"));
    ReportMonitor reportMonitor_11 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[CLoadZ] - Ground"));

    monitorPlot_0.getDataSetManager().addDataProviders(new NeoObjectVector(new Object[] {reportMonitor_7, reportMonitor_8, reportMonitor_9, reportMonitor_10, reportMonitor_11}));

    /*MonitorDataSet monitorDataSet_1 = 
      ((MonitorDataSet) monitorPlot_0.getDataSetManager().getDataSet("[CLoadX] - CoG Mean Monitor"));
    monitorDataSet_1.setSeriesNameLocked(true);
    monitorDataSet_1.setSeriesName("[X] - CoG Mean");*/

    MonitorDataSet monitorDataSet_2 = 
      ((MonitorDataSet) monitorPlot_0.getDataSetManager().getDataSet("[CLoadX] - Ground"));
    monitorDataSet_2.setSeriesNameLocked(true);
    monitorDataSet_2.setSeriesName("[X] - Ground");

    /*MonitorDataSet monitorDataSet_3 = 
      ((MonitorDataSet) monitorPlot_0.getDataSetManager().getDataSet("[CLoadX] - Ground Mean Monitor"));
    monitorDataSet_3.setSeriesNameLocked(true);
    monitorDataSet_3.setSeriesName("[X] - Ground Mean");*/

    MonitorDataSet monitorDataSet_4 = 
      ((MonitorDataSet) monitorPlot_0.getDataSetManager().getDataSet("[CLoadY] - CoG"));
    monitorDataSet_4.setSeriesNameLocked(true);
    monitorDataSet_4.setSeriesName("[Y] - CoG");

    /*MonitorDataSet monitorDataSet_5 = 
      ((MonitorDataSet) monitorPlot_0.getDataSetManager().getDataSet("[CLoadY] - CoG Mean Monitor"));
    monitorDataSet_5.setSeriesNameLocked(true);
    monitorDataSet_5.setSeriesName("[Y] - CoG Mean");*/

    MonitorDataSet monitorDataSet_6 = 
      ((MonitorDataSet) monitorPlot_0.getDataSetManager().getDataSet("[CLoadY] - Ground"));
    monitorDataSet_6.setSeriesNameLocked(true);
    monitorDataSet_6.setSeriesName("[Y] - Ground");

    /*MonitorDataSet monitorDataSet_7 = 
      ((MonitorDataSet) monitorPlot_0.getDataSetManager().getDataSet("[CLoadY] - Ground Mean Monitor"));
    monitorDataSet_7.setSeriesNameLocked(true);
    monitorDataSet_7.setSeriesName("[Y] - Ground Mean");*/

    MonitorDataSet monitorDataSet_8 = 
      ((MonitorDataSet) monitorPlot_0.getDataSetManager().getDataSet("[CLoadZ] - CoG"));
    monitorDataSet_8.setSeriesNameLocked(true);
    monitorDataSet_8.setSeriesName("[Z] - CoG");

    /*MonitorDataSet monitorDataSet_9 = 
      ((MonitorDataSet) monitorPlot_0.getDataSetManager().getDataSet("[CLoadZ] - CoG Mean Monitor"));
    monitorDataSet_9.setSeriesNameLocked(true);
    monitorDataSet_9.setSeriesName("[Z] - CoG Mean");*/

    MonitorDataSet monitorDataSet_10 = 
      ((MonitorDataSet) monitorPlot_0.getDataSetManager().getDataSet("[CLoadZ] - Ground"));
    monitorDataSet_10.setSeriesNameLocked(true);
    monitorDataSet_10.setSeriesName("[Z] - Ground");

    /*MonitorDataSet monitorDataSet_11 = 
      ((MonitorDataSet) monitorPlot_0.getDataSetManager().getDataSet("[CLoadZ] - Ground Mean Monitor"));
    monitorDataSet_11.setSeriesNameLocked(true);
    monitorDataSet_11.setSeriesName("[Z] - Ground Mean");*/

    MultiColLegend multiColLegend_0 = 
      monitorPlot_0.getLegend();
    multiColLegend_0.getChartPositionOption().setSelected(ChartPositionOption.Type.CUSTOM);
    multiColLegend_0.setRelativeYPosition(0.25);
  monitorPlot_0.close();
  //}
  
  //{ // Cooling PLOT

    ReportMonitor reportMonitor_12 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Mass Flow] - Radiator (R)"));

    MonitorPlot monitorPlot_1 = 
      simulation_0.getPlotManager().createMonitorPlot(new NeoObjectVector(new Object[] {reportMonitor_12}), "[Mass Flow] - Radiator (R) Plot");
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

    cartesian2DAxis_1.setMinimum(0.1);
    cartesian2DAxis_1.setMaximum(0.25);

    MonitorDataSet monitorDataSet_13 = 
      ((MonitorDataSet) monitorPlot_1.getDataSetManager().getDataSet("[Mass Flow] - Radiator (R)"));
    monitorDataSet_13.setSeriesNameLocked(true);
    monitorDataSet_13.setSeriesName("[MFlow] Radiator (R)");
  
  /*if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ReportMonitor reportMonitor_13 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[MFlow] - Fan (L) Mean Monitor"));}
    ReportMonitor reportMonitor_14 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[MFlow] - Fan (R) Mean Monitor"));
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ReportMonitor reportMonitor_15 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[MFlow] - Radiator (L) Mean Monitor"));}
    ReportMonitor reportMonitor_16 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[MFlow] - Radiator (R) Mean Monitor"));*/
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
  if ( CoolingMode != 0) {
    ReportMonitor reportMonitor_17 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Mass Flow] - Fan (L)"));
    }
    ReportMonitor reportMonitor_18 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Mass Flow] - Radiator (L)"));}

    if ( CoolingMode != 0) {
    ReportMonitor reportMonitor_19 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Mass Flow] - Fan (R)"));
    monitorPlot_1.getDataSetManager().addDataProviders(new NeoObjectVector(new Object[] {reportMonitor_19}));
    }

  if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
  //monitorPlot_1.getDataSetManager().addDataProviders(new NeoObjectVector(new Object[] {reportMonitor_14, reportMonitor_16, reportMonitor_19}));
  
  } else {
  /*ReportMonitor reportMonitor_13 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[MFlow] - Fan (L) Mean Monitor"));
  ReportMonitor reportMonitor_15 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[MFlow] - Radiator (L) Mean Monitor"));*/
    ReportMonitor reportMonitor_18 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Mass Flow] - Radiator (L)"));
  if ( CoolingMode != 0) {
  ReportMonitor reportMonitor_17 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Mass Flow] - Fan (L)"));
  monitorPlot_1.getDataSetManager().addDataProviders(new NeoObjectVector(new Object[] {reportMonitor_17, reportMonitor_18}));
    }
  else{
    monitorPlot_1.getDataSetManager().addDataProviders(new NeoObjectVector(new Object[] {reportMonitor_18}));
  }
    //monitorPlot_1.getDataSetManager().addDataProviders(new NeoObjectVector(new Object[] {reportMonitor_13, reportMonitor_14, reportMonitor_15, reportMonitor_16, reportMonitor_17, reportMonitor_18, reportMonitor_19}));
  }
  
  if (CoolingMode != 0) {
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    MonitorDataSet monitorDataSet_14 = 
      ((MonitorDataSet) monitorPlot_1.getDataSetManager().getDataSet("[Mass Flow] - Fan (L)"));
    monitorDataSet_14.setSeriesNameLocked(true);
    monitorDataSet_14.setSeriesName("[MFlow] Fan (L)");}

    /*MonitorDataSet monitorDataSet_15 = 
      ((MonitorDataSet) monitorPlot_1.getDataSetManager().getDataSet("[MFlow] - Fan (L) Mean Monitor"));
    monitorDataSet_15.setSeriesNameLocked(true);
    monitorDataSet_15.setSeriesName("[MFlow] Fan (L) Mean");}

    MonitorDataSet monitorDataSet_16 = 
      ((MonitorDataSet) monitorPlot_1.getDataSetManager().getDataSet("[MFlow] - Fan (R) Mean Monitor"));
    monitorDataSet_16.setSeriesNameLocked(true);
    monitorDataSet_16.setSeriesName("[MFlow] Fan (R) Mean");*/

    MonitorDataSet monitorDataSet_20 = 
      ((MonitorDataSet) monitorPlot_1.getDataSetManager().getDataSet("[Mass Flow] - Fan (R)"));
    monitorDataSet_20.setSeriesNameLocked(true);
    monitorDataSet_20.setSeriesName("[MFlow] Fan (R)");

  }

  /*if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    MonitorDataSet monitorDataSet_17 = 
      ((MonitorDataSet) monitorPlot_1.getDataSetManager().getDataSet("[MFlow] - Radiator (L) Mean Monitor"));
    monitorDataSet_17.setSeriesNameLocked(true);
    monitorDataSet_17.setSeriesName("[MFlow] Radiator (L) Mean");}

    MonitorDataSet monitorDataSet_18 = 
      ((MonitorDataSet) monitorPlot_1.getDataSetManager().getDataSet("[MFlow] - Radiator (R) Mean Monitor"));
    monitorDataSet_18.setSeriesNameLocked(true);
    monitorDataSet_18.setSeriesName("[MFlow] Radiator (R) Mean");*/

  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    MonitorDataSet monitorDataSet_19 = 
      ((MonitorDataSet) monitorPlot_1.getDataSetManager().getDataSet("[Mass Flow] - Radiator (L)"));
    monitorDataSet_19.setSeriesNameLocked(true);
    monitorDataSet_19.setSeriesName("[MFlow] Radiator (L)");}

 /* if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ReportMonitor reportMonitor_20 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[PDrop] - Fan (L) Mean Monitor"));}
    ReportMonitor reportMonitor_21 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[PDrop] - Fan (R) Mean Monitor"));
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ReportMonitor reportMonitor_22 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[PDrop] - Radiator (L) Mean Monitor"));}
    ReportMonitor reportMonitor_23 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[PDrop] - Radiator (R) Mean Monitor"));*/
    if (CoolingMode != 0) {
    if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
  ReportMonitor reportMonitor_24 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[PDrop] - Fan (L)"));}
    ReportMonitor reportMonitor_25 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[PDrop] - Fan (R)"));
    }
  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    ReportMonitor reportMonitor_26 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[PDrop] - Radiator (L)"));}
    ReportMonitor reportMonitor_27 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[PDrop] - Radiator (R)"));

  if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
    if (CoolingMode != 0) {
    ReportMonitor reportMonitor_25 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[PDrop] - Fan (R)"));
  monitorPlot_1.getDataSetManager().addDataProviders(new NeoObjectVector(new Object[] {reportMonitor_25, reportMonitor_27}));}
  else {
    monitorPlot_1.getDataSetManager().addDataProviders(new NeoObjectVector(new Object[] {reportMonitor_27}));
  }

  } else {
    ReportMonitor reportMonitor_26 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[PDrop] - Radiator (L)"));
  /*ReportMonitor reportMonitor_20 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[PDrop] - Fan (L) Mean Monitor"));
    ReportMonitor reportMonitor_22 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[PDrop] - Radiator (L) Mean Monitor"));*/
    if (CoolingMode != 0) {
    ReportMonitor reportMonitor_25 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[PDrop] - Fan (R)"));
    ReportMonitor reportMonitor_24 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[PDrop] - Fan (L)"));
    
    monitorPlot_1.getDataSetManager().addDataProviders(new NeoObjectVector(new Object[] {reportMonitor_24, reportMonitor_25, reportMonitor_26, reportMonitor_27}));
  }
  else {
    monitorPlot_1.getDataSetManager().addDataProviders(new NeoObjectVector(new Object[] {reportMonitor_26, reportMonitor_27}));
  
   }
  }
  
    Cartesian2DAxis cartesian2DAxis_2 = 
      (Cartesian2DAxis) cartesian2DAxisManager_1.createAxis(Cartesian2DAxis.Position.Right);
    cartesian2DAxis_2.setLockMinimum(true);
    cartesian2DAxis_2.setMaximum(150);

    AxisTitle axisTitle_2 = 
      cartesian2DAxis_2.getTitle();
    axisTitle_2.setText("Pressure Drop (Pa)");
  
  if (CoolingMode != 0) {
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
  }

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

  /*if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
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
    monitorDataSet_28.setSeriesName("[PDrop] Radiator (R) Mean");*/

    MultiColLegend multiColLegend_1 = 
      monitorPlot_1.getLegend();
    multiColLegend_1.getChartPositionOption().setSelected(ChartPositionOption.Type.SOUTH);
    multiColLegend_1.setLegendLayout(MultiColLegend.LegendLayout.HORIZONTAL);
    multiColLegend_1.setNumRows(2);

  if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
  if (CoolingMode != 0) {
    MonitorDataSet monitorDataSet_20 = 
      ((MonitorDataSet) monitorPlot_1.getDataSetManager().getDataSet("[Mass Flow] - Fan (R)"));
    MonitorDataSet monitorDataSet_22 = 
      ((MonitorDataSet) monitorPlot_1.getDataSetManager().getDataSet("[PDrop] - Fan (R)"));
    monitorPlot_1.setDataSeriesOrder(new NeoObjectVector(new Object[] {monitorDataSet_13, monitorDataSet_20, monitorDataSet_24, monitorDataSet_22}));
  }
  else {
    monitorPlot_1.setDataSeriesOrder(new NeoObjectVector(new Object[] {monitorDataSet_13, monitorDataSet_24}));
  }
  } else {
  
    /*MonitorDataSet monitorDataSet_15 = 
      ((MonitorDataSet) monitorPlot_1.getDataSetManager().getDataSet("[MFlow] - Fan (L) Mean Monitor"));
    MonitorDataSet monitorDataSet_17 = 
      ((MonitorDataSet) monitorPlot_1.getDataSetManager().getDataSet("[MFlow] - Radiator (L) Mean Monitor"));*/
    MonitorDataSet monitorDataSet_19 = 
      ((MonitorDataSet) monitorPlot_1.getDataSetManager().getDataSet("[Mass Flow] - Radiator (L)"));
    MonitorDataSet monitorDataSet_23 = 
      ((MonitorDataSet) monitorPlot_1.getDataSetManager().getDataSet("[PDrop] - Radiator (L)"));
    /*MonitorDataSet monitorDataSet_25 = 
      ((MonitorDataSet) monitorPlot_1.getDataSetManager().getDataSet("[PDrop] - Fan (L) Mean Monitor"));
    MonitorDataSet monitorDataSet_27 = 
      ((MonitorDataSet) monitorPlot_1.getDataSetManager().getDataSet("[PDrop] - Radiator (L) Mean Monitor"));*/
      
    //monitorPlot_1.setDataSeriesOrder(new NeoObjectVector(new Object[] {monitorDataSet_14, monitorDataSet_15, monitorDataSet_13, monitorDataSet_16, monitorDataSet_19, monitorDataSet_17, monitorDataSet_20, monitorDataSet_18, monitorDataSet_21, monitorDataSet_25, monitorDataSet_22, monitorDataSet_26, monitorDataSet_23, monitorDataSet_27, monitorDataSet_24, monitorDataSet_28}));

    if (CoolingMode != 0) {
    MonitorDataSet monitorDataSet_14 = 
      ((MonitorDataSet) monitorPlot_1.getDataSetManager().getDataSet("[Mass Flow] - Fan (L)"));
    MonitorDataSet monitorDataSet_21 = 
      ((MonitorDataSet) monitorPlot_1.getDataSetManager().getDataSet("[PDrop] - Fan (L)"));
    MonitorDataSet monitorDataSet_20 = 
      ((MonitorDataSet) monitorPlot_1.getDataSetManager().getDataSet("[Mass Flow] - Fan (R)"));
    MonitorDataSet monitorDataSet_22 = 
      ((MonitorDataSet) monitorPlot_1.getDataSetManager().getDataSet("[PDrop] - Fan (R)"));
    monitorPlot_1.setDataSeriesOrder(new NeoObjectVector(new Object[] {monitorDataSet_13, monitorDataSet_19, monitorDataSet_20, monitorDataSet_14,monitorDataSet_24, monitorDataSet_23,  monitorDataSet_22, monitorDataSet_21}));
  }
  else {
    monitorPlot_1.setDataSeriesOrder(new NeoObjectVector(new Object[] {monitorDataSet_13, monitorDataSet_24, monitorDataSet_23, monitorDataSet_19}));
  }
  }
  
    multiColLegend_1.setItemSpacing(4);
    multiColLegend_1.getChartPositionOption().setSelected(ChartPositionOption.Type.SOUTH);

    // Force Plots

    Collection<Monitor> monitorsCol = simulation_0.getMonitorManager().getMonitors();
    ArrayList<Monitor> monitorListD = new ArrayList<>(monitorsCol);
    ArrayList<Monitor> monitorsDownF = new ArrayList<>();
    ArrayList<Monitor> monitorsDrag = new ArrayList<>();
    ArrayList<Monitor> monitorsSideF = new ArrayList<>();

for (Monitor monitor : monitorListD){

  String monitorName = monitor.getPresentationName();

  if (monitorName.contains("[DownF]") == true && !monitorName.contains("Mean") == true) {
      monitorsDownF.add(monitor);
}
else if (monitorName.contains("[Drag]") == true && !monitorName.contains("Mean") == true) {
      monitorsDrag.add(monitor);
}
else if (monitorName.contains("[SideF]") == true && !monitorName.contains("Mean") == true) {
      monitorsSideF.add(monitor);
}
  }

     // Downforce //

    MonitorPlot monitorPlot_2 = 
     simulation_0.getPlotManager().createMonitorPlot(monitorsDownF, "Reports Plot");
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
    if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
      cartesian2DAxis_3.setMaximum((double)Math.ceil((double)(5*0.5*1.225*Math.pow(CarVelocity,2)/2)/50f)*50);
    }
    else {
      cartesian2DAxis_3.setMaximum((double)Math.ceil((double)(5*0.5*1.225*Math.pow(CarVelocity,2))/50f)*50);
    }

    AxisTitle axisTitle_3 = 
      cartesian2DAxis_3.getTitle();
    axisTitle_3.setText("Force (N)");

    MultiColLegend multiColLegend_2 = 
      monitorPlot_2.getLegend();
    multiColLegend_2.getChartPositionOption().setSelected(ChartPositionOption.Type.SOUTH);
    multiColLegend_2.setLegendLayout(MultiColLegend.LegendLayout.HORIZONTAL);
    multiColLegend_2.setNumRows(2);

    // Drag //

    MonitorPlot monitorPlot_3 = 
     simulation_0.getPlotManager().createMonitorPlot(monitorsDrag, "Reports Plot");
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
    if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
      cartesian2DAxis_4.setMaximum((double)Math.ceil((double)(2.0*0.5*1.225*Math.pow(CarVelocity,2)/2)/25f)*25);
    }
    else {
      cartesian2DAxis_4.setMaximum((double)Math.ceil((double)(2.0*0.5*1.225*Math.pow(CarVelocity,2))/25f)*25);
    }

    AxisTitle axisTitle_4 = 
      cartesian2DAxis_4.getTitle();
    axisTitle_4.setText("Force (N)");

    MultiColLegend multiColLegend_3 = 
      monitorPlot_3.getLegend();
    multiColLegend_3.getChartPositionOption().setSelected(ChartPositionOption.Type.SOUTH);
    multiColLegend_3.setLegendLayout(MultiColLegend.LegendLayout.HORIZONTAL);
    multiColLegend_3.setNumRows(2);

    // Side F //

    MonitorPlot monitorPlot_4 = 
     simulation_0.getPlotManager().createMonitorPlot(monitorsSideF, "Reports Plot");
    monitorPlot_4.open();
    monitorPlot_4.setPresentationName("[SideF]");
    monitorPlot_4.setTitle("SideF");

    MonitorDataSet monitorDataSet_48 = 
      ((MonitorDataSet) monitorPlot_4.getDataSetManager().getDataSet("[SideF] - Total Monitor"));
    monitorDataSet_48.setSeriesNameLocked(true);
    monitorDataSet_48.setSeriesName("Total");

    Cartesian2DAxisManager cartesian2DAxisManager_4 = 
      ((Cartesian2DAxisManager) monitorPlot_4.getAxisManager());
    Cartesian2DAxis cartesian2DAxis_5 = 
      ((Cartesian2DAxis) cartesian2DAxisManager_4.getAxis("Left Axis"));
    if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
      cartesian2DAxis_5.setMinimum(-(double)Math.ceil((double)(0.25*0.5*1.225*Math.pow(CarVelocity,2)/2)/10f)*10);
    cartesian2DAxis_5.setMaximum((double)Math.ceil((double)(0.25*0.5*1.225*Math.pow(CarVelocity,2)/2)/10f)*10);
    }
    else {
      cartesian2DAxis_5.setMinimum(-(double)Math.ceil((double)(0.25*0.5*1.225*Math.pow(CarVelocity,2))/10f)*10);
    cartesian2DAxis_5.setMaximum((double)Math.ceil((double)(0.25*0.5*1.225*Math.pow(CarVelocity,2))/10f)*10);
    }

    AxisTitle axisTitle_5 = 
      cartesian2DAxis_5.getTitle();
    axisTitle_5.setText("Force (N)");

    MultiColLegend multiColLegend_4 = 
      monitorPlot_4.getLegend();
    multiColLegend_4.getChartPositionOption().setSelected(ChartPositionOption.Type.SOUTH);
    multiColLegend_4.setLegendLayout(MultiColLegend.LegendLayout.HORIZONTAL);
    multiColLegend_4.setNumRows(2);

    // Residuals //

    ResidualPlot residualPlot_0 = 
      ((ResidualPlot) simulation_0.getPlotManager().getPlot("Residuals"));

    Cartesian2DAxisManager cartesian2DAxisManager_5 = 
      ((Cartesian2DAxisManager) residualPlot_0.getAxisManager());
    Cartesian2DAxis cartesian2DAxis_6 = 
      ((Cartesian2DAxis) cartesian2DAxisManager_5.getAxis("Left Axis"));
    cartesian2DAxis_6.setMinimum(1.0E-6);
    cartesian2DAxis_6.setMaximum(1000.0);
  //}

    // Time Plot

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
    Cartesian2DAxisManager cartesian2DAxisManager_7 = 
      ((Cartesian2DAxisManager) monitorPlot_6.getAxisManager());
    Cartesian2DAxis cartesian2DAxis_8 = 
      ((Cartesian2DAxis) cartesian2DAxisManager_7.getAxis("Left Axis"));

    AxisTitle axisTitle_7 = 
      cartesian2DAxis_8.getTitle();
    axisTitle_7.setText("Time (s)");

    residualPlot_0.open();

  }

  private void ClACdACyA() {

    Simulation simulation_0 = 
      getActiveSimulation();
    
  double Roll = simulation_0.getReportManager().getReport("Roll [deg]").getReportMonitorValue();
  double Yaw = simulation_0.getReportManager().getReport("Yaw [rad]").getReportMonitorValue();
  double CornerRadius = simulation_0.getReportManager().getReport("Corner Radius [m]").getReportMonitorValue();

  Units units_3 = 
      simulation_0.getUnitsManager().getInternalUnits(new IntVector(new int[] {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0}));

  Units units_4 = 
      simulation_0.getUnitsManager().getInternalUnits(new IntVector(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}));

  Units units_0 = 
      ((Units) simulation_0.getUnitsManager().getObject("m^2"));

  // CL.A

  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    try {
    ForceCoefficientReport forceCoefficientReport_10 = 
      ((ForceCoefficientReport) simulation_0.getReportManager().getReport("[Aero] CL.A"));
  }
  catch (Exception e){
  ForceCoefficientReport forceCoefficientReport_0 = 
      simulation_0.getReportManager().createReport(ForceCoefficientReport.class);
      forceCoefficientReport_0.setPresentationName("[Aero] CL.A");
      forceCoefficientReport_0.getReferenceDensity().setUnits(units_3);
      forceCoefficientReport_0.getReferenceDensity().setValue(1.225);
      forceCoefficientReport_0.getReferenceVelocity().setDefinition("${car_velocity[ms-1]}");
      forceCoefficientReport_0.getReferenceVelocity().setUnits(units_4);
      forceCoefficientReport_0.getReferenceArea().setValue(1.0);
      forceCoefficientReport_0.getReferenceArea().setUnits(units_0);
      forceCoefficientReport_0.getDirection().setComponents(0.0, 0.0, -1.0);
      forceCoefficientReport_0.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, InterfaceBoundary.class), new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.DoesNotContain, "Domain"), new NamePredicate(NameOperator.DoesNotContain, "Fan"), new NamePredicate(NameOperator.DoesNotContain, "Radiator"))))), Query.STANDARD_MODIFIERS));
    ReportMonitor reportMonitor_100 = 
      forceCoefficientReport_0.createMonitor();
    StatisticsReport statisticsReport_100 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_100.setSampleFilterOption(SampleFilterOption.LastNSamples);
    LastNSamplesFilter lastNSamplesFilter_0 = 
      ((LastNSamplesFilter) statisticsReport_100.getSampleFilterManager().getObject("Last N Samples"));
    lastNSamplesFilter_0.setNSamples(100);
    statisticsReport_100.setMonitor(reportMonitor_100);
    statisticsReport_100.setPresentationName("[Aero] CL.A Mean");
      }

    }
  else{
    try {
    ForceCoefficientReport forceCoefficientReport_10 = 
      ((ForceCoefficientReport) simulation_0.getReportManager().getReport("[Aero] CL.A"));
  }
  catch (Exception e){
  ForceCoefficientReport forceCoefficientReport_0 = 
      simulation_0.getReportManager().createReport(ForceCoefficientReport.class);
      forceCoefficientReport_0.setPresentationName("[Aero] CL.A");
      forceCoefficientReport_0.getReferenceDensity().setUnits(units_3);
      forceCoefficientReport_0.getReferenceDensity().setValue(1.225);
      forceCoefficientReport_0.getReferenceVelocity().setDefinition("${car_velocity[ms-1]}");
      forceCoefficientReport_0.getReferenceVelocity().setUnits(units_4);
      forceCoefficientReport_0.getReferenceArea().setValue(0.5);
      forceCoefficientReport_0.getReferenceArea().setUnits(units_0);
      forceCoefficientReport_0.getDirection().setComponents(0.0, 0.0, -1.0);
      forceCoefficientReport_0.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, InterfaceBoundary.class), new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.DoesNotContain, "Domain"), new NamePredicate(NameOperator.DoesNotContain, "Fan"), new NamePredicate(NameOperator.DoesNotContain, "Radiator"))))), Query.STANDARD_MODIFIERS));
    ReportMonitor reportMonitor_100 = 
      forceCoefficientReport_0.createMonitor();
    StatisticsReport statisticsReport_100 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_100.setSampleFilterOption(SampleFilterOption.LastNSamples);
    LastNSamplesFilter lastNSamplesFilter_0 = 
      ((LastNSamplesFilter) statisticsReport_100.getSampleFilterManager().getObject("Last N Samples"));
    lastNSamplesFilter_0.setNSamples(100);
    statisticsReport_100.setMonitor(reportMonitor_100);
    statisticsReport_100.setPresentationName("[Aero] CL.A Mean");
      }
  }

  // CD.A

  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    try {
    ForceCoefficientReport forceCoefficientReport_11 = 
      ((ForceCoefficientReport) simulation_0.getReportManager().getReport("[Aero] CD.A"));
  }
  catch (Exception e){
  ForceCoefficientReport forceCoefficientReport_10 = 
      simulation_0.getReportManager().createReport(ForceCoefficientReport.class);
      forceCoefficientReport_10.setPresentationName("[Aero] CD.A");
      forceCoefficientReport_10.getReferenceDensity().setUnits(units_3);
      forceCoefficientReport_10.getReferenceDensity().setValue(1.225);
      forceCoefficientReport_10.getReferenceVelocity().setDefinition("${car_velocity[ms-1]}");
      forceCoefficientReport_10.getReferenceVelocity().setUnits(units_4);
      forceCoefficientReport_10.getReferenceArea().setValue(1.0);
      forceCoefficientReport_10.getReferenceArea().setUnits(units_0);
      forceCoefficientReport_10.getDirection().setComponents(-1.0, 0.0, 0.0);
      forceCoefficientReport_10.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, InterfaceBoundary.class), new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.DoesNotContain, "Domain"), new NamePredicate(NameOperator.DoesNotContain, "Fan"), new NamePredicate(NameOperator.DoesNotContain, "Radiator"))))), Query.STANDARD_MODIFIERS));
    ReportMonitor reportMonitor_101 = 
      forceCoefficientReport_10.createMonitor();
    StatisticsReport statisticsReport_101 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_101.setSampleFilterOption(SampleFilterOption.LastNSamples);
    LastNSamplesFilter lastNSamplesFilter_0 = 
      ((LastNSamplesFilter) statisticsReport_101.getSampleFilterManager().getObject("Last N Samples"));
    lastNSamplesFilter_0.setNSamples(100);
    statisticsReport_101.setMonitor(reportMonitor_101);
    statisticsReport_101.setPresentationName("[Aero] CD.A Mean");
      }

    }
  else{
    try {
    ForceCoefficientReport forceCoefficientReport_11 = 
      ((ForceCoefficientReport) simulation_0.getReportManager().getReport("[Aero] CD.A"));
  }
  catch (Exception e){
  ForceCoefficientReport forceCoefficientReport_10 = 
      simulation_0.getReportManager().createReport(ForceCoefficientReport.class);
      forceCoefficientReport_10.setPresentationName("[Aero] CD.A");
      forceCoefficientReport_10.getReferenceDensity().setUnits(units_3);
      forceCoefficientReport_10.getReferenceDensity().setValue(1.225);
      forceCoefficientReport_10.getReferenceVelocity().setDefinition("${car_velocity[ms-1]}");
      forceCoefficientReport_10.getReferenceVelocity().setUnits(units_4);
      forceCoefficientReport_10.getReferenceArea().setValue(0.5);
      forceCoefficientReport_10.getReferenceArea().setUnits(units_0);
      forceCoefficientReport_10.getDirection().setComponents(-1.0, 0.0, 0.0);
      forceCoefficientReport_10.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, InterfaceBoundary.class), new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.DoesNotContain, "Domain"), new NamePredicate(NameOperator.DoesNotContain, "Fan"), new NamePredicate(NameOperator.DoesNotContain, "Radiator"))))), Query.STANDARD_MODIFIERS));
    ReportMonitor reportMonitor_101 = 
      forceCoefficientReport_10.createMonitor();
    StatisticsReport statisticsReport_101 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_101.setSampleFilterOption(SampleFilterOption.LastNSamples);
    LastNSamplesFilter lastNSamplesFilter_0 = 
      ((LastNSamplesFilter) statisticsReport_101.getSampleFilterManager().getObject("Last N Samples"));
    lastNSamplesFilter_0.setNSamples(100);
    statisticsReport_101.setMonitor(reportMonitor_101);
    statisticsReport_101.setPresentationName("[Aero] CD.A Mean");
      }
  }

  // CY.A

  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    try {
    ForceCoefficientReport forceCoefficientReport_12 = 
      ((ForceCoefficientReport) simulation_0.getReportManager().getReport("[Aero] CY.A"));
  }
  catch (Exception e){
ForceCoefficientReport forceCoefficientReport_12 = 
      simulation_0.getReportManager().createReport(ForceCoefficientReport.class);
      forceCoefficientReport_12.setPresentationName("[Aero] CY.A");
      forceCoefficientReport_12.getReferenceDensity().setUnits(units_3);
      forceCoefficientReport_12.getReferenceDensity().setValue(1.225);
      forceCoefficientReport_12.getReferenceVelocity().setDefinition("${car_velocity[ms-1]}");
      forceCoefficientReport_12.getReferenceVelocity().setUnits(units_4);
      forceCoefficientReport_12.getReferenceArea().setValue(1.0);
      forceCoefficientReport_12.getReferenceArea().setUnits(units_0);
      forceCoefficientReport_12.getDirection().setComponents(0.0, -1.0, 0.0);
      forceCoefficientReport_12.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, InterfaceBoundary.class), new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.DoesNotContain, "Domain"), new NamePredicate(NameOperator.DoesNotContain, "Fan"), new NamePredicate(NameOperator.DoesNotContain, "Radiator"))))), Query.STANDARD_MODIFIERS));
    ReportMonitor reportMonitor_102 = 
      forceCoefficientReport_12.createMonitor();
    StatisticsReport statisticsReport_102 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_102.setSampleFilterOption(SampleFilterOption.LastNSamples);
    LastNSamplesFilter lastNSamplesFilter_0 = 
      ((LastNSamplesFilter) statisticsReport_102.getSampleFilterManager().getObject("Last N Samples"));
    lastNSamplesFilter_0.setNSamples(100);
    statisticsReport_102.setMonitor(reportMonitor_102);
    statisticsReport_102.setPresentationName("[Aero] CY.A Mean");
      }

    }
  else{
    try {
    ForceCoefficientReport forceCoefficientReport_12 = 
      ((ForceCoefficientReport) simulation_0.getReportManager().getReport("[Aero] CY.A"));
  }
  catch (Exception e){
ForceCoefficientReport forceCoefficientReport_12 = 
      simulation_0.getReportManager().createReport(ForceCoefficientReport.class);
      forceCoefficientReport_12.setPresentationName("[Aero] CY.A");
      forceCoefficientReport_12.getReferenceDensity().setUnits(units_3);
      forceCoefficientReport_12.getReferenceDensity().setValue(1.225);
      forceCoefficientReport_12.getReferenceVelocity().setDefinition("${car_velocity[ms-1]}");
      forceCoefficientReport_12.getReferenceVelocity().setUnits(units_4);
      forceCoefficientReport_12.getReferenceArea().setValue(0.5);
      forceCoefficientReport_12.getReferenceArea().setUnits(units_0);
      forceCoefficientReport_12.getDirection().setComponents(0.0, -1.0, 0.0);
      forceCoefficientReport_12.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, InterfaceBoundary.class), new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.DoesNotContain, "Domain"), new NamePredicate(NameOperator.DoesNotContain, "Fan"), new NamePredicate(NameOperator.DoesNotContain, "Radiator"))))), Query.STANDARD_MODIFIERS));
    ReportMonitor reportMonitor_102 = 
      forceCoefficientReport_12.createMonitor();
    StatisticsReport statisticsReport_102 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);
    statisticsReport_102.setSampleFilterOption(SampleFilterOption.LastNSamples);
    LastNSamplesFilter lastNSamplesFilter_0 = 
      ((LastNSamplesFilter) statisticsReport_102.getSampleFilterManager().getObject("Last N Samples"));
    lastNSamplesFilter_0.setNSamples(100);
    statisticsReport_102.setMonitor(reportMonitor_102);
    statisticsReport_102.setPresentationName("[Aero] CY.A Mean");
      }
  }

  // CoP

    ExpressionReport expressionReport_1000 = 
      simulation_0.getReportManager().createReport(ExpressionReport.class);
    
  expressionReport_1000.setPresentationName("[Aero] Aero Balance (Front)");
  
  expressionReport_1000.setDefinition("(1+((${[My] - Origin Mean})/(1.54*${[DownF] - Total Mean})))*100");

  if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
  ExpressionReport expressionReport_10000 = 
      simulation_0.getReportManager().createReport(ExpressionReport.class);
    
  expressionReport_10000.setPresentationName("[Aero] Aero Balance (Inboard)");
  
  expressionReport_10000.setDefinition("(0.5-((${[Mx] - Origin Mean})/(1.2*${[DownF] - Total Mean})))*100");
  }

    ForceCoefficientReport forceCoefficientReport_10 = 
      ((ForceCoefficientReport) simulation_0.getReportManager().getReport("[Aero] CL.A"));

    ForceCoefficientReport forceCoefficientReport_11 = 
      ((ForceCoefficientReport) simulation_0.getReportManager().getReport("[Aero] CD.A"));

    ForceCoefficientReport forceCoefficientReport_12 = 
      ((ForceCoefficientReport) simulation_0.getReportManager().getReport("[Aero] CY.A"));

    StatisticsReport statisticsReport_100 = 
    ((StatisticsReport) simulation_0.getReportManager().getReport("[Aero] CL.A Mean"));

    StatisticsReport statisticsReport_101 = 
    ((StatisticsReport) simulation_0.getReportManager().getReport("[Aero] CD.A Mean"));

    StatisticsReport statisticsReport_102 = 
    ((StatisticsReport) simulation_0.getReportManager().getReport("[Aero] CY.A Mean"));

    simulation_0.getReportManager().getGroupsManager().createGroup("[AeroCoeffs]");
    ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[AeroCoeffs]")).getGroupsManager().createGroup("[Avg.]");
    ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[AeroCoeffs]")).getGroupsManager().createGroup("[Monitor]");
    ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[AeroCoeffs]")).getGroupsManager().getObject("[Monitor]")).getGroupsManager().groupObjects("[Monitor]", new NeoObjectVector(new Object[] {forceCoefficientReport_10,forceCoefficientReport_11,forceCoefficientReport_12}), true);
    ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[AeroCoeffs]")).getGroupsManager().getObject("[Avg.]")).getGroupsManager().groupObjects("[Avg.]", new NeoObjectVector(new Object[] {statisticsReport_100,statisticsReport_101,statisticsReport_102}), true);

    ReportMonitor reportMonitor_100 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Aero] CL.A Monitor"));

    ReportMonitor reportMonitor_101 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Aero] CD.A Monitor"));

    ReportMonitor reportMonitor_102 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("[Aero] CY.A Monitor"));

   simulation_0.getMonitorManager().getGroupsManager().createGroup("[AeroCoeffs]");
    ((ClientServerObjectGroup) simulation_0.getMonitorManager().getGroupsManager().getObject("[AeroCoeffs]")).getGroupsManager().createGroup("[Monitor]");
    ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getMonitorManager().getGroupsManager().getObject("[AeroCoeffs]")).getGroupsManager().getObject("[Monitor]")).getGroupsManager().groupObjects("[Monitor]", new NeoObjectVector(new Object[] {reportMonitor_100,reportMonitor_101,reportMonitor_102}), true);

    simulation_0.saveState(resolvePath("..\\Simulation\\FST11CFD.sim"));

  }


}
