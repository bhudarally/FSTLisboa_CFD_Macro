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

public class g_ReportsMonitorsPlots extends StarMacro {

  public void execute() {
    execute0();
	FieldMean();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();
	  
    Region region_1 = 
      simulation_0.getRegionManager().getRegion("Fluid");
	  
	/*InterfaceBoundary interfaceBoundary_0 = 
      ((InterfaceBoundary) region_1.getBoundaryManager().getBoundary("Car.Fan.duct_shroud [Duct_shroud]"));
	  
	Boundary boundary_25 = 
      region_1.getBoundaryManager().getBoundary("Car.Fan.Fan");
	  
	InterfaceBoundary interfaceBoundary_1 = 
      ((InterfaceBoundary) region_1.getBoundaryManager().getBoundary("Car.Fan.Fan [Fan]"));
	  
	Boundary boundary_26 = 
      region_1.getBoundaryManager().getBoundary("Car.Radiator.Radiator_shroud");
	  
	InterfaceBoundary interfaceBoundary_2 = 
      ((InterfaceBoundary) region_1.getBoundaryManager().getBoundary("Car.Radiator.Radiator_shroud [Radiator_shroud]"));
	  
	InterfaceBoundary interfaceBoundary_4 = 
      ((InterfaceBoundary) region_1.getBoundaryManager().getBoundary("Car.Radiator.Radiator_inlet [Radiator_inlet]"));
	  
	Region region_0 = 
      simulation_0.getRegionManager().getRegion("Fan");
	  
	InterfaceBoundary interfaceBoundary_3 = 
      ((InterfaceBoundary) region_0.getBoundaryManager().getBoundary("Car.Fan.Fan [Fan]"));
	  
	Region region_2 = 
      simulation_0.getRegionManager().getRegion("Radiator");
	  
	InterfaceBoundary interfaceBoundary_5 = 
      ((InterfaceBoundary) region_2.getBoundaryManager().getBoundary("Car.Radiator.Radiator_inlet [Radiator_inlet]"));
	  
	InterfaceBoundary interfaceBoundary_6 = 
      ((InterfaceBoundary) region_2.getBoundaryManager().getBoundary("Car.Radiator.Radiator_outlet [Radiator_outlet/Duct_inlet]"));*/
	  
	ElementCountReport elementCountReport_0 = 
      simulation_0.getReportManager().createReport(ElementCountReport.class);

    elementCountReport_0.setPresentationName("No. Elements");

    elementCountReport_0.getParts().setQuery(null);

	elementCountReport_0.getParts().setObjects(region_1);
	  
	CumulativeElapsedTimeReport cumulativeElapsedTimeReport_0 = 
      simulation_0.getReportManager().createReport(CumulativeElapsedTimeReport.class);
	  
	Units units_0 = 
      ((Units) simulation_0.getUnitsManager().getObject("hr"));
	  
	cumulativeElapsedTimeReport_0.setUnits(units_0);
	  
	/*PressureDropReport pressureDropReport_0 = 
      simulation_0.getReportManager().createReport(PressureDropReport.class);
	  
	pressureDropReport_0.setPresentationName("Pressure Drop Fan");
	
	pressureDropReport_0.getParts().setQuery(null);

    pressureDropReport_0.getParts().setObjects(interfaceBoundary_1);
	
	pressureDropReport_0.getLowPressureParts().setQuery(null);
	
	pressureDropReport_0.getLowPressureParts().setObjects(interfaceBoundary_3);
	
	PressureDropReport pressureDropReport_1 = 
      simulation_0.getReportManager().createReport(PressureDropReport.class);

    pressureDropReport_1.setPresentationName("Pressure Drop Radiator");

    pressureDropReport_1.getParts().setQuery(null);

    pressureDropReport_1.getParts().setObjects(interfaceBoundary_4);

    pressureDropReport_1.getLowPressureParts().setQuery(null);

    pressureDropReport_1.getLowPressureParts().setObjects(interfaceBoundary_6);
	
	MassFlowReport massFlowReport_0 = 
      simulation_0.getReportManager().createReport(MassFlowReport.class);

    massFlowReport_0.setPresentationName("Mass Flow Fan");

    massFlowReport_0.getParts().setQuery(null);
	
	massFlowReport_0.getParts().setObjects(interfaceBoundary_3);

	MassFlowReport massFlowReport_1 = 
      simulation_0.getReportManager().createReport(MassFlowReport.class);

    massFlowReport_1.setPresentationName("Mass Flow Radiator");
	
	massFlowReport_1.getParts().setQuery(null);

	massFlowReport_1.getParts().setObjects(interfaceBoundary_4);*/

    Units units_1 = 
      ((Units) simulation_0.getUnitsManager().getObject("m"));

    ForceReport forceReport_0 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
	  
	forceReport_0.getDirection().setComponents(0.0, 0.0, -1.0);

    forceReport_0.setPresentationName("Downforce_Overall");

    ForceReport forceReport_1 = 
      simulation_0.getReportManager().createReport(ForceReport.class);

    forceReport_1.copyProperties(forceReport_0);
	
	forceReport_1.getDirection().setComponents(0.0, 0.0, -1.0);

    forceReport_1.setPresentationName("Downforce_Diff_Back");

    ForceReport forceReport_2 = 
      simulation_0.getReportManager().createReport(ForceReport.class);

    forceReport_2.copyProperties(forceReport_1);
	
	forceReport_2.setPresentationName("Downforce_Diff_Lateral");

    ForceReport forceReport_3 = 
      simulation_0.getReportManager().createReport(ForceReport.class);

    forceReport_3.copyProperties(forceReport_1);

    forceReport_3.setPresentationName("Downforce_FW_Total");

    ForceReport forceReport_31 = 
      simulation_0.getReportManager().createReport(ForceReport.class);

    forceReport_31.copyProperties(forceReport_1);

    forceReport_31.setPresentationName("Downforce_FW_Main");

    ForceReport forceReport_32 = 
      simulation_0.getReportManager().createReport(ForceReport.class);

    forceReport_32.copyProperties(forceReport_1);

    forceReport_32.setPresentationName("Downforce_FW_Flap");
	
	ForceReport forceReport_4 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
	  
	forceReport_4.copyProperties(forceReport_1);

    forceReport_4.setPresentationName("Downforce_SC_EP");

    ForceReport forceReport_420 = 
      simulation_0.getReportManager().createReport(ForceReport.class);

    forceReport_420.copyProperties(forceReport_1);

    forceReport_420.setPresentationName("Downforce_SC_Total");
	
	ForceReport forceReport_5 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
	  
	forceReport_5.copyProperties(forceReport_1);

    forceReport_5.setPresentationName("Downforce_RW_Total");
	
	ForceReport forceReport_6 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
	  
	forceReport_6.copyProperties(forceReport_1);

    forceReport_6.setPresentationName("Downforce_SC1");
	
	ForceReport forceReport_7 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
	  
	forceReport_7.copyProperties(forceReport_1);

    forceReport_7.setPresentationName("Downforce_SC2");
	
	ForceReport forceReport_8 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
	  
	forceReport_8.copyProperties(forceReport_1);

    forceReport_8.setPresentationName("Downforce_SC3");
	
	ForceReport forceReport_9 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
	  
	forceReport_9.copyProperties(forceReport_1);

    forceReport_9.setPresentationName("Downforce_SC0");
	
	ForceReport forceReport_10 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
	  
	forceReport_10.copyProperties(forceReport_1);

    forceReport_10.setPresentationName("Downforce_Tyre_Front");
	
	ForceReport forceReport_11 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
	  
	forceReport_11.copyProperties(forceReport_1);

    forceReport_11.setPresentationName("Downforce_Tyre_Rear");

    ForceReport forceReport_61 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
	  
	forceReport_61.copyProperties(forceReport_1);

    forceReport_61.setPresentationName("Downforce_RW1");

    ForceReport forceReport_62 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
	  
	forceReport_62.copyProperties(forceReport_1);

    forceReport_62.setPresentationName("Downforce_RW2");

    ForceReport forceReport_63 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
	  
	forceReport_63.copyProperties(forceReport_1);

    forceReport_63.setPresentationName("Downforce_RW3");

    ForceReport forceReport_69 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
      
    forceReport_69.copyProperties(forceReport_1);

    forceReport_69.setPresentationName("Downforce_BH");
	
    ForceReport forceReport_12 = 
      simulation_0.getReportManager().createReport(ForceReport.class);

    forceReport_12.setPresentationName("Drag");

    forceReport_12.getDirection().setComponents(-1.0, 0.0, 0.0);

    forceReport_12.setPresentationName("Drag_Diff_Back");

    ForceReport forceReport_13 = 
      simulation_0.getReportManager().createReport(ForceReport.class);

    forceReport_13.copyProperties(forceReport_12);
	
	forceReport_13.setPresentationName("Drag_Diff_Lateral");
	
	ForceReport forceReport_14 = 
      simulation_0.getReportManager().createReport(ForceReport.class);

    forceReport_14.copyProperties(forceReport_12);
	
	forceReport_14.setPresentationName("Drag_FW_Total");

    ForceReport forceReport_141 = 
      simulation_0.getReportManager().createReport(ForceReport.class);

    forceReport_141.copyProperties(forceReport_12);
    
    forceReport_141.setPresentationName("Drag_FW_Main");

    ForceReport forceReport_142 = 
      simulation_0.getReportManager().createReport(ForceReport.class);

    forceReport_142.copyProperties(forceReport_12);
    
    forceReport_142.setPresentationName("Drag_FW_Flap");
	
	ForceReport forceReport_15 = 
      simulation_0.getReportManager().createReport(ForceReport.class);

    forceReport_15.copyProperties(forceReport_12);
	
	forceReport_15.setPresentationName("Drag_SC_EP");
	
	ForceReport forceReport_16 = 
      simulation_0.getReportManager().createReport(ForceReport.class);

    forceReport_16.copyProperties(forceReport_12);
	
	forceReport_16.setPresentationName("Drag_RW_Total");
	
	ForceReport forceReport_17 = 
      simulation_0.getReportManager().createReport(ForceReport.class);

    forceReport_17.copyProperties(forceReport_12);
	
	forceReport_17.setPresentationName("Drag_SC1");

	ForceReport forceReport_18 = 
      simulation_0.getReportManager().createReport(ForceReport.class);

    forceReport_18.copyProperties(forceReport_12);
	
	forceReport_18.setPresentationName("Drag_SC0");
	
	ForceReport forceReport_19 = 
      simulation_0.getReportManager().createReport(ForceReport.class);

    forceReport_19.copyProperties(forceReport_12);
	
	forceReport_19.setPresentationName("Drag_Tyre_Front");
	
	ForceReport forceReport_20 = 
      simulation_0.getReportManager().createReport(ForceReport.class);

    forceReport_20.copyProperties(forceReport_12);
	
	forceReport_20.setPresentationName("Drag_Tyre_Rear");
	
	ForceReport forceReport_21 = 
      simulation_0.getReportManager().createReport(ForceReport.class);

    forceReport_21.copyProperties(forceReport_12);
	
	forceReport_21.setPresentationName("Drag_Overall");
	
	ForceReport forceReport_22 = 
      simulation_0.getReportManager().createReport(ForceReport.class);
	  
	forceReport_22.copyProperties(forceReport_1);

    forceReport_22.setPresentationName("Downforce_Mono");
	
	ForceReport forceReport_23 = 
      simulation_0.getReportManager().createReport(ForceReport.class);

    forceReport_23.copyProperties(forceReport_12);
	
	forceReport_23.setPresentationName("Drag_Mono");
	
	ForceReport forceReport_24 = 
      simulation_0.getReportManager().createReport(ForceReport.class);

    forceReport_24.copyProperties(forceReport_12);
	
	forceReport_24.setPresentationName("Drag_SC2");

	ForceReport forceReport_25 = 
      simulation_0.getReportManager().createReport(ForceReport.class);

    forceReport_25.copyProperties(forceReport_12);
	
	forceReport_25.setPresentationName("Drag_SC3");
	
	ForceReport forceReport_64 = 
      simulation_0.getReportManager().createReport(ForceReport.class);

    forceReport_64.copyProperties(forceReport_12);
	
	forceReport_64.setPresentationName("Drag_RW1");

	ForceReport forceReport_65 = 
      simulation_0.getReportManager().createReport(ForceReport.class);

    forceReport_65.copyProperties(forceReport_12);
	
	forceReport_65.setPresentationName("Drag_RW2");

	ForceReport forceReport_66 = 
      simulation_0.getReportManager().createReport(ForceReport.class);

    forceReport_66.copyProperties(forceReport_12);
	
	forceReport_66.setPresentationName("Drag_RW3");

    ForceReport forceReport_699 = 
      simulation_0.getReportManager().createReport(ForceReport.class);

    forceReport_699.copyProperties(forceReport_12);
    
    forceReport_699.setPresentationName("Drag_BH");

    ForceReport forceReport_421 = 
      simulation_0.getReportManager().createReport(ForceReport.class);

    forceReport_421.copyProperties(forceReport_1);

    forceReport_421.setPresentationName("Drag_SC_Total");

    ForceReport forceReport_1000 = 
      simulation_0.getReportManager().createReport(ForceReport.class);

    forceReport_1000.copyProperties(forceReport_12);
    
    forceReport_1000.setPresentationName("Drag_Misc");

    ForceReport forceReport_1001 = 
      simulation_0.getReportManager().createReport(ForceReport.class);

    forceReport_1001.copyProperties(forceReport_1);

    forceReport_1001.setPresentationName("Downforce_Misc");
  
    Units units_2 = 
      simulation_0.getUnitsManager().getPreferredUnits(new IntVector(new int[] {0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));

    forceReport_0.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new RelationshipPredicate(RelationshipOperator.DescendantOf, new IdentityPredicate(IdentityOperator.Equals, Arrays.<ClientServerObject>asList(region_1))), new NamePredicate(NameOperator.Contains, "Car"))), Query.STANDARD_MODIFIERS));

    forceReport_1.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "Diff_B"), new TypePredicate(TypeOperator.Is, Boundary.class))), Query.STANDARD_MODIFIERS));

    forceReport_2.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "Diff_L"), new TypePredicate(TypeOperator.Is, Boundary.class))), Query.STANDARD_MODIFIERS));

    forceReport_3.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.Contains, "FW"))), Query.STANDARD_MODIFIERS));

    forceReport_31.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.Contains, "FW_Ma"))), Query.STANDARD_MODIFIERS));

    forceReport_32.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.Contains, "FW_F"))), Query.STANDARD_MODIFIERS));

  forceReport_4.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "SC_E"), new TypePredicate(TypeOperator.Is, Boundary.class))), Query.STANDARD_MODIFIERS));

    forceReport_420.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "SC"), new TypePredicate(TypeOperator.Is, Boundary.class))), Query.STANDARD_MODIFIERS));

    forceReport_421.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "SC"), new TypePredicate(TypeOperator.Is, Boundary.class))), Query.STANDARD_MODIFIERS));

    forceReport_5.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.Contains, "RW"), new NamePredicate(NameOperator.DoesNotContain, "Sup"))), Query.STANDARD_MODIFIERS));

    forceReport_6.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.Contains, "SC1"))), Query.STANDARD_MODIFIERS));

	forceReport_7.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "SC2"), new TypePredicate(TypeOperator.Is, Boundary.class))), Query.STANDARD_MODIFIERS));

	forceReport_8.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "SC3"), new TypePredicate(TypeOperator.Is, Boundary.class))), Query.STANDARD_MODIFIERS));
	
	forceReport_9.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "SC0"), new TypePredicate(TypeOperator.Is, Boundary.class))), Query.STANDARD_MODIFIERS));

	forceReport_10.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "Tyre_F"), new TypePredicate(TypeOperator.Is, Boundary.class))), Query.STANDARD_MODIFIERS));

	forceReport_11.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.Contains, "Tyre_R"))), Query.STANDARD_MODIFIERS));

    forceReport_12.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "Diff_B"), new TypePredicate(TypeOperator.Is, Boundary.class))), Query.STANDARD_MODIFIERS));

	forceReport_13.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "Diff_L"), new TypePredicate(TypeOperator.Is, Boundary.class))), Query.STANDARD_MODIFIERS));

	forceReport_14.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.Contains, "FW"))), Query.STANDARD_MODIFIERS));

    forceReport_141.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.Contains, "FW_Ma"))), Query.STANDARD_MODIFIERS));

    forceReport_142.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.Contains, "FW_F"))), Query.STANDARD_MODIFIERS));

	forceReport_15.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "SC_E"), new TypePredicate(TypeOperator.Is, Boundary.class))), Query.STANDARD_MODIFIERS));

	forceReport_16.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.Contains, "RW"), new NamePredicate(NameOperator.DoesNotContain, "Sup"))), Query.STANDARD_MODIFIERS));

	forceReport_17.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.Contains, "SC1"))), Query.STANDARD_MODIFIERS));

	forceReport_18.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "SC0"), new TypePredicate(TypeOperator.Is, Boundary.class))), Query.STANDARD_MODIFIERS));

	forceReport_19.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "Tyre_F"), new TypePredicate(TypeOperator.Is, Boundary.class))), Query.STANDARD_MODIFIERS));

	forceReport_20.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.Contains, "Tyre_R"))), Query.STANDARD_MODIFIERS));

  forceReport_21.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new RelationshipPredicate(RelationshipOperator.DescendantOf, new IdentityPredicate(IdentityOperator.Equals, Arrays.<ClientServerObject>asList(region_1))), new NamePredicate(NameOperator.Contains, "Car"))), Query.STANDARD_MODIFIERS));

	forceReport_22.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "Mono"), new TypePredicate(TypeOperator.Is, Boundary.class))), Query.STANDARD_MODIFIERS));

	forceReport_23.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "Mono"), new TypePredicate(TypeOperator.Is, Boundary.class))), Query.STANDARD_MODIFIERS));

	forceReport_24.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "SC2"), new TypePredicate(TypeOperator.Is, Boundary.class))), Query.STANDARD_MODIFIERS));

	forceReport_25.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "SC3"), new TypePredicate(TypeOperator.Is, Boundary.class))), Query.STANDARD_MODIFIERS));

    forceReport_61.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "RW1"), new TypePredicate(TypeOperator.Is, Boundary.class))), Query.STANDARD_MODIFIERS));

    forceReport_62.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "RW2"), new TypePredicate(TypeOperator.Is, Boundary.class))), Query.STANDARD_MODIFIERS));

    forceReport_63.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "RW3"), new TypePredicate(TypeOperator.Is, Boundary.class))), Query.STANDARD_MODIFIERS));

    forceReport_64.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "RW1"), new TypePredicate(TypeOperator.Is, Boundary.class))), Query.STANDARD_MODIFIERS));

    forceReport_65.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "RW2"), new TypePredicate(TypeOperator.Is, Boundary.class))), Query.STANDARD_MODIFIERS));

    forceReport_66.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "RW3"), new TypePredicate(TypeOperator.Is, Boundary.class))), Query.STANDARD_MODIFIERS));

    forceReport_69.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "BH"), new TypePredicate(TypeOperator.Is, Boundary.class))), Query.STANDARD_MODIFIERS));

    forceReport_699.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "BH"), new TypePredicate(TypeOperator.Is, Boundary.class))), Query.STANDARD_MODIFIERS));

    forceReport_1000.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new RelationshipPredicate(RelationshipOperator.DescendantOf, new IdentityPredicate(IdentityOperator.Equals, Arrays.<ClientServerObject>asList(region_1))), new NamePredicate(NameOperator.DoesNotContain, "FW_Main"), new NamePredicate(NameOperator.DoesNotContain, "Domain"), new NamePredicate(NameOperator.DoesNotContain, "BH"), new NamePredicate(NameOperator.DoesNotContain, "Diff_Lateral"), new NamePredicate(NameOperator.DoesNotContain, "FW_Mid"), new NamePredicate(NameOperator.DoesNotContain, "FW_Sup"), new NamePredicate(NameOperator.DoesNotContain, "FW_Flap"), new NamePredicate(NameOperator.DoesNotContain, "Driver"), new NamePredicate(NameOperator.DoesNotContain, "Diff_Back"), new NamePredicate(NameOperator.DoesNotContain, "RW1"), new NamePredicate(NameOperator.DoesNotContain, "RW2"), new NamePredicate(NameOperator.DoesNotContain, "Mono"), new NamePredicate(NameOperator.DoesNotContain, "Fan"), new NamePredicate(NameOperator.DoesNotContain, "Radiator"), new NamePredicate(NameOperator.DoesNotContain, "SC0"), new NamePredicate(NameOperator.DoesNotContain, "SC1"), new NamePredicate(NameOperator.DoesNotContain, "SC2"), new NamePredicate(NameOperator.DoesNotContain, "SC3"), new NamePredicate(NameOperator.DoesNotContain, "SC_EP"), new NamePredicate(NameOperator.DoesNotContain, "FW_EP"), new NamePredicate(NameOperator.DoesNotContain, "HR"), new NamePredicate(NameOperator.DoesNotContain, "SU_"), new NamePredicate(NameOperator.DoesNotContain, "Tyre_"), new NamePredicate(NameOperator.DoesNotContain, "RW3"), new NamePredicate(NameOperator.DoesNotContain, "RW_EP"), new NamePredicate(NameOperator.DoesNotContain, "Main_Hoop"), new NamePredicate(NameOperator.DoesNotContain, "RW_Sup"))), Query.STANDARD_MODIFIERS));
  
    forceReport_1001.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new RelationshipPredicate(RelationshipOperator.DescendantOf, new IdentityPredicate(IdentityOperator.Equals, Arrays.<ClientServerObject>asList(region_1))), new NamePredicate(NameOperator.DoesNotContain, "FW_Main"), new NamePredicate(NameOperator.DoesNotContain, "Domain"), new NamePredicate(NameOperator.DoesNotContain, "BH"), new NamePredicate(NameOperator.DoesNotContain, "Diff_Lateral"), new NamePredicate(NameOperator.DoesNotContain, "FW_Mid"), new NamePredicate(NameOperator.DoesNotContain, "FW_Sup"), new NamePredicate(NameOperator.DoesNotContain, "FW_Flap"), new NamePredicate(NameOperator.DoesNotContain, "Driver"), new NamePredicate(NameOperator.DoesNotContain, "Diff_Back"), new NamePredicate(NameOperator.DoesNotContain, "RW1"), new NamePredicate(NameOperator.DoesNotContain, "RW2"), new NamePredicate(NameOperator.DoesNotContain, "Mono"), new NamePredicate(NameOperator.DoesNotContain, "Fan"), new NamePredicate(NameOperator.DoesNotContain, "Radiator"), new NamePredicate(NameOperator.DoesNotContain, "SC0"), new NamePredicate(NameOperator.DoesNotContain, "SC1"), new NamePredicate(NameOperator.DoesNotContain, "SC2"), new NamePredicate(NameOperator.DoesNotContain, "SC3"), new NamePredicate(NameOperator.DoesNotContain, "SC_EP"), new NamePredicate(NameOperator.DoesNotContain, "FW_EP"), new NamePredicate(NameOperator.DoesNotContain, "HR"), new NamePredicate(NameOperator.DoesNotContain, "SU_"), new NamePredicate(NameOperator.DoesNotContain, "Tyre_"), new NamePredicate(NameOperator.DoesNotContain, "RW3"), new NamePredicate(NameOperator.DoesNotContain, "RW_EP"), new NamePredicate(NameOperator.DoesNotContain, "Main_Hoop"), new NamePredicate(NameOperator.DoesNotContain, "RW_Sup"))), Query.STANDARD_MODIFIERS));
  
  //

    Collection<Report> reportCol = simulation_0.getReportManager().getObjects();

    ArrayList<Report> reportList = new ArrayList<>(reportCol);

    ArrayList<Report> reportsDownF = new ArrayList<>();

    ArrayList<Report> reportsDrag = new ArrayList<>();

    ArrayList<Report> reportsExtra = new ArrayList<>();

for (Report report : reportList){

  String reportName = report.getPresentationName();

  if (reportName.contains("Downf") == true) {
      reportsDownF.add(report);
}
  else if (reportName.contains("Drag") == true) {
      reportsDrag.add(report);
}
}

  simulation_0.getMonitorManager().createMonitorAndPlot(reportsDownF, true, "Reports Plot");

  simulation_0.getMonitorManager().createMonitorAndPlot(reportsDrag, true, "Reports Plot");

    Collection<Monitor> monitorsCol = simulation_0.getMonitorManager().getMonitors();

    ArrayList<Monitor> monitorListD = new ArrayList<>(monitorsCol);

    ArrayList<Monitor> monitorsDownF = new ArrayList<>();

    ArrayList<Monitor> monitorsDrag = new ArrayList<>();

for (Monitor monitor : monitorListD){

  String monitorName = monitor.getPresentationName();

  if (monitorName.contains("Downf") == true) {
      monitorsDownF.add(monitor);
}
else if (monitorName.contains("Drag") == true) {
      monitorsDrag.add(monitor);
}
}

      MonitorPlot monitorPlot_0 = 
      simulation_0.getPlotManager().createMonitorPlot(monitorsDownF, "Reports Plot");

      monitorPlot_0.open();

      monitorPlot_0.setPresentationName("Downforce");

    Cartesian2DAxisManager cartesian2DAxisManager_0 = 
      ((Cartesian2DAxisManager) monitorPlot_0.getAxisManager());
      
    Cartesian2DAxis cartesian2DAxis_0 = 
      ((Cartesian2DAxis) cartesian2DAxisManager_0.getAxis("Left Axis"));

    cartesian2DAxis_0.setMinimum(0.0);

    cartesian2DAxis_0.setMaximum(350.0);

    cartesian2DAxisManager_0.setAxesBounds(new Vector(Arrays.<AxisManager.AxisBounds>asList(new AxisManager.AxisBounds("Left Axis", 0.0, true, 350.0, true))));

    MultiColLegend multiColLegend_0 = 
      monitorPlot_0.getLegend();

    multiColLegend_0.setItemSpacing(0);

    multiColLegend_0.setFont(new java.awt.Font("Siemens Sans Global", 0, 10));

    multiColLegend_0.setPositionInfo(0.06766917556524277, 0.49038460850715637, ChartPositionOption.Type.CUSTOM);

      PlotUpdate plotUpdate_0 = 
      monitorPlot_0.getPlotUpdate();

//

        MonitorPlot monitorPlot_1 = 
        simulation_0.getPlotManager().createMonitorPlot(monitorsDrag, "Reports Plot");

      monitorPlot_1.open();

      monitorPlot_1.setPresentationName("Drag");

      Cartesian2DAxisManager cartesian2DAxisManager_1 = 
      ((Cartesian2DAxisManager) monitorPlot_1.getAxisManager());
      
    Cartesian2DAxis cartesian2DAxis_1 = 
      ((Cartesian2DAxis) cartesian2DAxisManager_1.getAxis("Left Axis"));

    cartesian2DAxisManager_1.setAxesBounds(new Vector(Arrays.<AxisManager.AxisBounds>asList(new AxisManager.AxisBounds("Left Axis", 0.0, true, 150.0, true))));

    MultiColLegend multiColLegend_1 = 
      monitorPlot_1.getLegend();

    multiColLegend_1.setItemSpacing(0);

    multiColLegend_1.setFont(new java.awt.Font("Siemens Sans Global", 0, 10));

    multiColLegend_1.setPositionInfo(0.06766917556524277, 0.49038460850715637, ChartPositionOption.Type.CUSTOM);

      PlotUpdate plotUpdate_1 = 
      monitorPlot_1.getPlotUpdate();

      //
	
	/*simulation_0.getMonitorManager().createMonitorAndPlot(new NeoObjectVector(new Object[] {massFlowReport_0}), true, "%1$s Plot");
	
	ReportMonitor reportMonitor_26 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("Mass Flow Fan Monitor"));
	  
	MonitorPlot monitorPlot_2 = 
      simulation_0.getPlotManager().createMonitorPlot(new NeoObjectVector(new Object[] {reportMonitor_26}), "Mass Flow Fan Monitor Plot");
	  
	monitorPlot_2.open();

    monitorPlot_2.setPresentationName("Mass Flow");
	
	ReportMonitor reportMonitor_27 = 
      massFlowReport_1.createMonitor();
	  
	monitorPlot_2.getDataSetManager().addDataProviders(new NeoObjectVector(new Object[] {reportMonitor_27}));
	
	simulation_0.getMonitorManager().createMonitorAndPlot(new NeoObjectVector(new Object[] {pressureDropReport_0}), true, "%1$s Plot");
	
	ReportMonitor reportMonitor_28 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("Pressure Drop Fan Monitor"));

    MonitorPlot monitorPlot_3 = 
      simulation_0.getPlotManager().createMonitorPlot(new NeoObjectVector(new Object[] {reportMonitor_28}), "Pressure Drop Fan Monitor Plot");

    monitorPlot_3.open();
	
	monitorPlot_3.setPresentationName("Pressure Drop");
	
	ReportMonitor reportMonitor_29 = 
      pressureDropReport_1.createMonitor();

    monitorPlot_3.getDataSetManager().addDataProviders(new NeoObjectVector(new Object[] {reportMonitor_29}));*/

	IteratorElapsedTimeReport iteratorElapsedTimeReport_0 = 
      simulation_0.getReportManager().createReport(IteratorElapsedTimeReport.class);

	simulation_0.getMonitorManager().createMonitorAndPlot(new NeoObjectVector(new Object[] {iteratorElapsedTimeReport_0}), true, "%1$s Plot");

	SimulationIteratorTimeReportMonitor simulationIteratorTimeReportMonitor_0 = 
      ((SimulationIteratorTimeReportMonitor) simulation_0.getMonitorManager().getMonitor("Solver Iteration Elapsed Time Monitor"));

	MonitorPlot monitorPlot_4 = 
      simulation_0.getPlotManager().createMonitorPlot(new NeoObjectVector(new Object[] {simulationIteratorTimeReportMonitor_0}), "Solver Iteration Elapsed Time Monitor Plot");

    monitorPlot_4.open();

    monitorPlot_4.setPresentationName("Solver Iteration Elapsed Time");

    ResidualMonitor residualMonitor_0 = 
      ((ResidualMonitor) simulation_0.getMonitorManager().getMonitor("Continuity"));

    residualMonitor_0.getNormalizeOption().setSelected(MonitorNormalizeOption.Type.OFF);

    ResidualMonitor residualMonitor_1 = 
      ((ResidualMonitor) simulation_0.getMonitorManager().getMonitor("Sdr"));

    residualMonitor_1.getNormalizeOption().setSelected(MonitorNormalizeOption.Type.OFF);

    ResidualMonitor residualMonitor_2 = 
      ((ResidualMonitor) simulation_0.getMonitorManager().getMonitor("Tke"));

    residualMonitor_2.getNormalizeOption().setSelected(MonitorNormalizeOption.Type.OFF);

    ResidualMonitor residualMonitor_3 = 
      ((ResidualMonitor) simulation_0.getMonitorManager().getMonitor("X-momentum"));

    residualMonitor_3.getNormalizeOption().setSelected(MonitorNormalizeOption.Type.OFF);

    ResidualMonitor residualMonitor_4 = 
      ((ResidualMonitor) simulation_0.getMonitorManager().getMonitor("Y-momentum"));

    residualMonitor_4.getNormalizeOption().setSelected(MonitorNormalizeOption.Type.OFF);

    ResidualMonitor residualMonitor_5 = 
      ((ResidualMonitor) simulation_0.getMonitorManager().getMonitor("Z-momentum"));

    residualMonitor_5.getNormalizeOption().setSelected(MonitorNormalizeOption.Type.OFF);
	
	ResidualMonitor residualMonitor_6 = 
      ((ResidualMonitor) simulation_0.getMonitorManager().getMonitor("Intermittency"));

    residualMonitor_6.getNormalizeOption().setSelected(MonitorNormalizeOption.Type.OFF);
	
	ResidualPlot residualPlot_0 = 
      ((ResidualPlot) simulation_0.getPlotManager().getPlot("Residuals"));

    residualPlot_0.open();

  try {

    UserFieldFunction userFieldFunction_3 = 
      ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("CL.A"));

  }
  catch (Exception e){

  UserFieldFunction userFieldFunction_3 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();

    userFieldFunction_3.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);

    userFieldFunction_3.setFunctionName("CL.A");

    userFieldFunction_3.setPresentationName("CL.A");

    userFieldFunction_3.setDefinition("${Pressure}*mag($${Area})*$${Normal}[2]*-2 / (0.5*1.225*pow(${Car-Velocity},2)) + $${WallShearStress}[2]*mag($${Area})*-2 / (0.5*1.225*pow(${Car-Velocity},2))");

  }

  try {

    UserFieldFunction userFieldFunction_4 = 
      ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("CD.A"));

  }
  catch (Exception e){

  UserFieldFunction userFieldFunction_4 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();

    userFieldFunction_4.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);

    userFieldFunction_4.setFunctionName("CD.A");

    userFieldFunction_4.setPresentationName("CD.A");

    userFieldFunction_4.setDefinition("${Pressure}*mag($${Area})*$${Normal}[0]*-2 / (0.5*1.225*pow(${Car-Velocity},2)) + $${WallShearStress}[0]*mag($${Area})*-2 / (0.5*1.225*pow(${Car-Velocity},2))");
  
  }

  try {

    SumReport sumReport_2 = 
      ((SumReport) simulation_0.getReportManager().getReport("CL.A"));

    SumReport sumReport_3 = 
      ((SumReport) simulation_0.getReportManager().getReport("CD.A"));

  }
  catch (Exception e){

  SumReport sumReport_2 = 
      simulation_0.getReportManager().createReport(SumReport.class);

    UserFieldFunction userFieldFunction_4 = 
      ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("CD.A"));

      UserFieldFunction userFieldFunction_3 = 
      ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("CL.A"));

    sumReport_2.setFieldFunction(userFieldFunction_3);

    sumReport_2.setPresentationName("CL.A");

    sumReport_2.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new RelationshipPredicate(RelationshipOperator.DescendantOf, new IdentityPredicate(IdentityOperator.Equals, Arrays.<ClientServerObject>asList(region_1))), new NamePredicate(NameOperator.DoesNotContain, "Domain"))), Query.STANDARD_MODIFIERS));
  
 SumReport sumReport_3 = 
      simulation_0.getReportManager().createReport(SumReport.class);

    sumReport_3.copyProperties(sumReport_2);

    sumReport_3.setPresentationName("CD.A");

    sumReport_3.setFieldFunction(userFieldFunction_4);

  }

    SumReport sumReport_2 = 
      ((SumReport) simulation_0.getReportManager().getReport("CD.A"));

    ReportMonitor reportMonitor_201 = 
      sumReport_2.createMonitor();

    SumReport sumReport_3 = 
      ((SumReport) simulation_0.getReportManager().getReport("CL.A"));

    ReportMonitor reportMonitor_200 = 
      sumReport_3.createMonitor();

    //

    StatisticsReport statisticsReport_1 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);

    statisticsReport_1.setSampleFilterOption(SampleFilterOption.LastNSamples);

    LastNSamplesFilter lastNSamplesFilter_0 = 
      ((LastNSamplesFilter) statisticsReport_1.getSampleFilterManager().getObject("Last N Samples"));

    lastNSamplesFilter_0.setNSamples(100);

    statisticsReport_1.setMonitor(reportMonitor_200);

    statisticsReport_1.setPresentationName("CL_Mean");

    //

    StatisticsReport statisticsReport_2 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);

    statisticsReport_2.copyProperties(statisticsReport_1);

    statisticsReport_2.setMonitor(reportMonitor_201);

    statisticsReport_2.setPresentationName("CD_Mean");

    //


    StatisticsReport statisticsReport_3 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);

    statisticsReport_3.copyProperties(statisticsReport_1);

    ReportMonitor reportMonitor_0 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("Downforce_Overall Monitor"));

    statisticsReport_3.setMonitor(reportMonitor_0);

    statisticsReport_3.setPresentationName("DF_Mean");

    //

    StatisticsReport statisticsReport_4 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);

    statisticsReport_4.copyProperties(statisticsReport_1);

    ReportMonitor reportMonitor_1 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("Downforce_Diff_Back Monitor"));

    statisticsReport_4.setMonitor(reportMonitor_1);

    statisticsReport_4.setPresentationName("DF_Diff_Back_Mean");

    //

    StatisticsReport statisticsReport_5 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);

    statisticsReport_5.copyProperties(statisticsReport_1);

    ReportMonitor reportMonitor_2 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("Downforce_Diff_Lateral Monitor"));

    statisticsReport_5.setMonitor(reportMonitor_2);

    statisticsReport_5.setPresentationName("DF_Diff_Lateral_Mean");

    //

    StatisticsReport statisticsReport_6 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);

    statisticsReport_6.copyProperties(statisticsReport_1);

    ReportMonitor reportMonitor_3 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("Downforce_FW_Total Monitor"));

    statisticsReport_6.setMonitor(reportMonitor_3);

    statisticsReport_6.setPresentationName("DF_FW_Total_Mean");

    //

    StatisticsReport statisticsReport_7 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);

    statisticsReport_7.copyProperties(statisticsReport_1);

    ReportMonitor reportMonitor_31 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("Downforce_FW_Main Monitor"));

    statisticsReport_7.setMonitor(reportMonitor_31);

    statisticsReport_7.setPresentationName("DF_FW_Main_Mean");

    //

    StatisticsReport statisticsReport_8 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);

    statisticsReport_8.copyProperties(statisticsReport_1);

    ReportMonitor reportMonitor_32 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("Downforce_FW_Flap Monitor"));

    statisticsReport_8.setMonitor(reportMonitor_32);

    statisticsReport_8.setPresentationName("DF_FW_Flap_Mean");

    //

    StatisticsReport statisticsReport_9 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);

    statisticsReport_9.copyProperties(statisticsReport_1);

    ReportMonitor reportMonitor_4 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("Downforce_SC_EP Monitor"));

    statisticsReport_9.setMonitor(reportMonitor_4);

    statisticsReport_9.setPresentationName("DF_SC_EP_Mean");

    //

    StatisticsReport statisticsReport_10 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);

    statisticsReport_10.copyProperties(statisticsReport_1);

    ReportMonitor reportMonitor_5 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("Downforce_RW_Total Monitor"));

    statisticsReport_10.setMonitor(reportMonitor_5);

    statisticsReport_10.setPresentationName("DF_RW_Total_Mean");

    //

    StatisticsReport statisticsReport_14 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);

    statisticsReport_14.copyProperties(statisticsReport_1);

    ReportMonitor reportMonitor_9 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("Downforce_SC0 Monitor"));

    statisticsReport_14.setMonitor(reportMonitor_9);

    statisticsReport_14.setPresentationName("DF_SC0_Mean");

    //

    StatisticsReport statisticsReport_11 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);

    statisticsReport_11.copyProperties(statisticsReport_1);

    ReportMonitor reportMonitor_6 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("Downforce_SC1 Monitor"));

    statisticsReport_11.setMonitor(reportMonitor_6);

    statisticsReport_11.setPresentationName("DF_SC1_Mean");

    //

    StatisticsReport statisticsReport_12 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);

    statisticsReport_12.copyProperties(statisticsReport_1);

    ReportMonitor reportMonitor_7 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("Downforce_SC2 Monitor"));

    statisticsReport_12.setMonitor(reportMonitor_7);

    statisticsReport_12.setPresentationName("DF_SC2_Mean");

    //

    StatisticsReport statisticsReport_13 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);

    statisticsReport_13.copyProperties(statisticsReport_1);

    ReportMonitor reportMonitor_8 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("Downforce_SC3 Monitor"));

    statisticsReport_13.setMonitor(reportMonitor_8);

    statisticsReport_13.setPresentationName("DF_SC3_Mean");

    //

    StatisticsReport statisticsReport_15 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);

    statisticsReport_15.copyProperties(statisticsReport_1);

    ReportMonitor reportMonitor_10 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("Downforce_Tyre_Front Monitor"));

    statisticsReport_15.setMonitor(reportMonitor_10);

    statisticsReport_15.setPresentationName("DF_Tyre_Front_Mean");

    //

    StatisticsReport statisticsReport_16 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);

    statisticsReport_16.copyProperties(statisticsReport_1);

    ReportMonitor reportMonitor_11 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("Downforce_Tyre_Rear Monitor"));

    statisticsReport_16.setMonitor(reportMonitor_11);

    statisticsReport_16.setPresentationName("DF_Tyre_Rear_Mean");

    //

    StatisticsReport statisticsReport_17 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);

    statisticsReport_17.copyProperties(statisticsReport_1);

    ReportMonitor reportMonitor_22 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("Downforce_Mono Monitor"));

    statisticsReport_17.setMonitor(reportMonitor_22);

    statisticsReport_17.setPresentationName("DF_Mono_Mean");

    //

    StatisticsReport statisticsReport_19 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);

    statisticsReport_19.copyProperties(statisticsReport_1);

    ReportMonitor reportMonitor_61 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("Downforce_RW1 Monitor"));

    statisticsReport_19.setMonitor(reportMonitor_61);

    statisticsReport_19.setPresentationName("DF_RW1_Mean");

    //

    StatisticsReport statisticsReport_119 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);

    statisticsReport_119.copyProperties(statisticsReport_1);

    ReportMonitor reportMonitor_62 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("Downforce_RW2 Monitor"));

    statisticsReport_119.setMonitor(reportMonitor_62);

    statisticsReport_119.setPresentationName("DF_RW2_Mean");

    //

    StatisticsReport statisticsReport_120 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);

    statisticsReport_120.copyProperties(statisticsReport_1);

    ReportMonitor reportMonitor_63 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("Downforce_RW3 Monitor"));

    statisticsReport_120.setMonitor(reportMonitor_63);

    statisticsReport_120.setPresentationName("DF_RW3_Mean");

    //

    StatisticsReport statisticsReport_121 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);

    statisticsReport_121.copyProperties(statisticsReport_1);

    ReportMonitor reportMonitor_69 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("Downforce_BH Monitor"));

    statisticsReport_121.setMonitor(reportMonitor_69);

    statisticsReport_121.setPresentationName("DF_BH_Mean");

//

    StatisticsReport statisticsReport_20 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);

    statisticsReport_20.copyProperties(statisticsReport_1);

    ReportMonitor reportMonitor_1000 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("Downforce_Misc Monitor"));

    statisticsReport_20.setMonitor(reportMonitor_1000);

    statisticsReport_20.setPresentationName("DF_Misc_Mean");

//

    StatisticsReport statisticsReport_1005 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);

    statisticsReport_1005.copyProperties(statisticsReport_1);

    ReportMonitor reportMonitor_1005 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("Downforce_SC_Total Monitor"));

    statisticsReport_1005.setMonitor(reportMonitor_1005);

    statisticsReport_1005.setPresentationName("DF_SC_Total_Mean");

//

    StatisticsReport statisticsReport_336 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);

    statisticsReport_336.copyProperties(statisticsReport_1);

    ReportMonitor reportMonitor_21 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("Drag_Overall Monitor"));

    statisticsReport_336.setMonitor(reportMonitor_21);

    statisticsReport_336.setPresentationName("Drag_Mean");

//

    StatisticsReport statisticsReport_24 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);

    statisticsReport_24.copyProperties(statisticsReport_1);

    ReportMonitor reportMonitor_12 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("Drag_Diff_Back Monitor"));

    statisticsReport_24.setMonitor(reportMonitor_12);

    statisticsReport_24.setPresentationName("Drag_Diff_Back_Mean");

    //

    StatisticsReport statisticsReport_25 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);

    statisticsReport_25.copyProperties(statisticsReport_1);

    ReportMonitor reportMonitor_13 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("Drag_Diff_Lateral Monitor"));

    statisticsReport_25.setMonitor(reportMonitor_13);

    statisticsReport_25.setPresentationName("Drag_Diff_Lateral_Mean");

    //

    StatisticsReport statisticsReport_26 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);

    statisticsReport_26.copyProperties(statisticsReport_1);

    ReportMonitor reportMonitor_14 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("Drag_FW_Total Monitor"));

    statisticsReport_26.setMonitor(reportMonitor_14);

    statisticsReport_26.setPresentationName("Drag_FW_Total_Mean");

    //

    StatisticsReport statisticsReport_27 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);

    statisticsReport_27.copyProperties(statisticsReport_1);

    ReportMonitor reportMonitor_141 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("Drag_FW_Main Monitor"));

    statisticsReport_27.setMonitor(reportMonitor_141);

    statisticsReport_27.setPresentationName("Drag_FW_Main_Mean");

    //

    StatisticsReport statisticsReport_28 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);

    statisticsReport_28.copyProperties(statisticsReport_1);

    ReportMonitor reportMonitor_142 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("Drag_FW_Flap Monitor"));

    statisticsReport_28.setMonitor(reportMonitor_142);

    statisticsReport_28.setPresentationName("Drag_FW_Flap_Mean");

    //

    StatisticsReport statisticsReport_29 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);

    statisticsReport_29.copyProperties(statisticsReport_1);

    ReportMonitor reportMonitor_15 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("Drag_SC_EP Monitor"));

    statisticsReport_29.setMonitor(reportMonitor_15);

    statisticsReport_29.setPresentationName("Drag_SC_EP_Mean");

    //

    StatisticsReport statisticsReport_30 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);

    statisticsReport_30.copyProperties(statisticsReport_1);

    ReportMonitor reportMonitor_16 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("Drag_RW_Total Monitor"));

    statisticsReport_30.setMonitor(reportMonitor_16);

    statisticsReport_30.setPresentationName("Drag_RW_Total_Mean");

    //

    StatisticsReport statisticsReport_31 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);

    statisticsReport_31.copyProperties(statisticsReport_1);

    ReportMonitor reportMonitor_18 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("Drag_SC0 Monitor"));

    statisticsReport_31.setMonitor(reportMonitor_18);

    statisticsReport_31.setPresentationName("Drag_SC0_Mean");

    //

    StatisticsReport statisticsReport_32 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);

    statisticsReport_32.copyProperties(statisticsReport_1);

    ReportMonitor reportMonitor_17 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("Drag_SC1 Monitor"));

    statisticsReport_32.setMonitor(reportMonitor_17);

    statisticsReport_32.setPresentationName("Drag_SC1_Mean");

    //

    StatisticsReport statisticsReport_33 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);

    statisticsReport_33.copyProperties(statisticsReport_1);

    ReportMonitor reportMonitor_24 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("Drag_SC2 Monitor"));

    statisticsReport_33.setMonitor(reportMonitor_24);

    statisticsReport_33.setPresentationName("Drag_SC2_Mean");

    //

    StatisticsReport statisticsReport_34 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);

    statisticsReport_34.copyProperties(statisticsReport_1);

    ReportMonitor reportMonitor_25 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("Drag_SC3 Monitor"));

    statisticsReport_34.setMonitor(reportMonitor_25);

    statisticsReport_34.setPresentationName("Drag_SC3_Mean");

    //

    StatisticsReport statisticsReport_35 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);

    statisticsReport_35.copyProperties(statisticsReport_1);

    ReportMonitor reportMonitor_19 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("Drag_Tyre_Front Monitor"));

    statisticsReport_35.setMonitor(reportMonitor_19);

    statisticsReport_35.setPresentationName("Drag_Tyre_Front_Mean");

    //

    StatisticsReport statisticsReport_36 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);

    statisticsReport_36.copyProperties(statisticsReport_1);

    ReportMonitor reportMonitor_20 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("Drag_Tyre_Rear Monitor"));

    statisticsReport_36.setMonitor(reportMonitor_20);

    statisticsReport_36.setPresentationName("Drag_Tyre_Rear_Mean");

    //

    StatisticsReport statisticsReport_37 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);

    statisticsReport_37.copyProperties(statisticsReport_1);

    ReportMonitor reportMonitor_23 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("Drag_Mono Monitor"));

    statisticsReport_37.setMonitor(reportMonitor_23);

    statisticsReport_37.setPresentationName("Drag_Mono_Mean");

    //

    StatisticsReport statisticsReport_38 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);

    statisticsReport_38.copyProperties(statisticsReport_1);

    ReportMonitor reportMonitor_64 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("Drag_RW1 Monitor"));

    statisticsReport_38.setMonitor(reportMonitor_64);

    statisticsReport_38.setPresentationName("Drag_RW1_Mean");

    //

    StatisticsReport statisticsReport_39 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);

    statisticsReport_39.copyProperties(statisticsReport_1);

    ReportMonitor reportMonitor_65 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("Drag_RW2 Monitor"));

    statisticsReport_39.setMonitor(reportMonitor_65);

    statisticsReport_39.setPresentationName("Drag_RW2_Mean");

    //

    StatisticsReport statisticsReport_40 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);

    statisticsReport_40.copyProperties(statisticsReport_1);

    ReportMonitor reportMonitor_66 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("Drag_RW3 Monitor"));

    statisticsReport_40.setMonitor(reportMonitor_66);

    statisticsReport_40.setPresentationName("Drag_RW3_Mean");

    //

    StatisticsReport statisticsReport_41 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);

    statisticsReport_41.copyProperties(statisticsReport_1);

    ReportMonitor reportMonitor_699 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("Drag_BH Monitor"));

    statisticsReport_41.setMonitor(reportMonitor_699);

    statisticsReport_41.setPresentationName("Drag_BH_Mean");

    StatisticsReport statisticsReport_22 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);

    statisticsReport_22.copyProperties(statisticsReport_1);

    ReportMonitor reportMonitor_1010 = 
      ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("Drag_Misc Monitor"));

    statisticsReport_22.setMonitor(reportMonitor_1010);

    statisticsReport_22.setPresentationName("Drag_Misc_Mean");


  /*StatisticsReport statisticsReport_500 = 
    simulation_0.getReportManager().createReport(StatisticsReport.class);

    statisticsReport_500.copyProperties(statisticsReport_1);

    statisticsReport_500.setMonitor(reportMonitor_26);

    statisticsReport_500.setPresentationName("Mass_Fan_Mean");

    StatisticsReport statisticsReport_501 = 
    simulation_0.getReportManager().createReport(StatisticsReport.class);

    statisticsReport_501.copyProperties(statisticsReport_1);

    statisticsReport_501.setMonitor(reportMonitor_27);

    statisticsReport_501.setPresentationName("Mass_Radiator_Mean");

    StatisticsReport statisticsReport_502 = 
    simulation_0.getReportManager().createReport(StatisticsReport.class);

    statisticsReport_502.copyProperties(statisticsReport_1);

    statisticsReport_502.setMonitor(reportMonitor_28);

    statisticsReport_502.setPresentationName("PressureD_Fan_Mean");

    StatisticsReport statisticsReport_503 = 
    simulation_0.getReportManager().createReport(StatisticsReport.class);

    statisticsReport_503.copyProperties(statisticsReport_1);

    statisticsReport_503.setMonitor(reportMonitor_29);

    statisticsReport_503.setPresentationName("PressureD_Radiator_Mean");*/


  MomentReport momentReport_0 = 
    simulation_0.getReportManager().createReport(MomentReport.class);
    
  momentReport_0.setPresentationName("My_origin");
  
  momentReport_0.getDirection().setComponents(0.0, 1.0, 0.0);

    momentReport_0.getParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new RelationshipPredicate(RelationshipOperator.DescendantOf, new IdentityPredicate(IdentityOperator.Equals, Arrays.<ClientServerObject>asList(region_1))), new NamePredicate(NameOperator.DoesNotContain, "Domain"))), Query.STANDARD_MODIFIERS));
  
  ReportMonitor reportMonitor_600 = 
      momentReport_0.createMonitor();

      //

    StatisticsReport statisticsReport_600 = 
      simulation_0.getReportManager().createReport(StatisticsReport.class);

    statisticsReport_600.copyProperties(statisticsReport_1);

    statisticsReport_600.setMonitor(reportMonitor_600);

    statisticsReport_600.setPresentationName("My_origin_Mean");

  try {

    ExpressionReport expressionReport_0 = 
      ((ExpressionReport) simulation_0.getReportManager().getReport("Pressure Distribution Back Axle (VD Method)"));

  }
  catch (Exception e){

  ExpressionReport expressionReport_0 = 
      simulation_0.getReportManager().createReport(ExpressionReport.class);
    
  expressionReport_0.setPresentationName("Pressure Distribution Back Axle (VD Method)");
  
  expressionReport_0.setDefinition("-((${My_origin_Mean})+(${Drag_Mean}*${CoGZ}))/(1.54*${DF_Mean})");
  }


  }
  
  private void FieldMean(){
	  
	  Simulation simulation_0 = 
      getActiveSimulation();
	  
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
    iterationUpdateFrequency_0.setStart(700);

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
	fieldMeanMonitor_4.setFieldFunction(primitiveFieldFunction_2);


    FieldMeanMonitor fieldMeanMonitor_5 = 
      simulation_0.getMonitorManager().createMonitor(FieldMeanMonitor.class);
    fieldMeanMonitor_5.copyProperties(fieldMeanMonitor_4);
    fieldMeanMonitor_5.setPresentationName("V[i]");
    PrimitiveFieldFunction primitiveFieldFunction_3 = 
      ((PrimitiveFieldFunction) simulation_0.getFieldFunctionManager().getFunction("Velocity"));
  VectorComponentFieldFunction vectorComponentFieldFunction_0 = 
      ((VectorComponentFieldFunction) primitiveFieldFunction_3.getComponentFunction(0));
    fieldMeanMonitor_5.setFieldFunction(vectorComponentFieldFunction_0); 

    FieldMeanMonitor fieldMeanMonitor_6 = 
      simulation_0.getMonitorManager().createMonitor(FieldMeanMonitor.class);
    fieldMeanMonitor_6.copyProperties(fieldMeanMonitor_5);
    fieldMeanMonitor_6.setPresentationName("V[j]");
	VectorComponentFieldFunction vectorComponentFieldFunction_1 = 
      ((VectorComponentFieldFunction) primitiveFieldFunction_3.getComponentFunction(1));
    fieldMeanMonitor_6.setFieldFunction(vectorComponentFieldFunction_1); 
    
    FieldMeanMonitor fieldMeanMonitor_7 = 
      simulation_0.getMonitorManager().createMonitor(FieldMeanMonitor.class);
    fieldMeanMonitor_7.copyProperties(fieldMeanMonitor_6);
    fieldMeanMonitor_7.setPresentationName("V[k]");
	VectorComponentFieldFunction vectorComponentFieldFunction_2 = 
      ((VectorComponentFieldFunction) primitiveFieldFunction_3.getComponentFunction(2));
    fieldMeanMonitor_7.setFieldFunction(vectorComponentFieldFunction_2);
     
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
  
	simulation_0.getReportManager().getGroupsManager().createGroup("[AC]");
    ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[AC]")).getGroupsManager().groupObjects("[AC]", new NeoObjectVector(new Object[] {areaAverageReport_0}), true);
  
    simulation_0.getMonitorManager().getGroupsManager().createGroup("[FF]");
    ((ClientServerObjectGroup) simulation_0.getMonitorManager().getGroupsManager().getObject("[FF]")).getGroupsManager().groupObjects("[FF]", new NeoObjectVector(new Object[] {fieldMeanMonitor_0, fieldMeanMonitor_3, fieldMeanMonitor_4, fieldMeanMonitor_5, fieldMeanMonitor_6, fieldMeanMonitor_7, fieldMeanMonitor_8, fieldMeanMonitor_9, fieldMeanMonitor_10, fieldMeanMonitor_2}), true);
  //}
  }
}
