// STAR-CCM+ macro: h_RunSolver.java
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

public class h_Solver extends StarMacro {

  public void execute() {
    InitialVelocity();
    Solver();
  }

  private void InitialVelocity() {

    Simulation simulation_0 = 
      getActiveSimulation();
	  
	double Roll = simulation_0.getReportManager().getReport("Roll [deg]").getReportMonitorValue();
	double Yaw = simulation_0.getReportManager().getReport("Yaw [rad]").getReportMonitorValue();
	double CornerRadius = simulation_0.getReportManager().getReport("Corner Radius [m]").getReportMonitorValue(); 
	
	if ( CornerRadius == 0) {  
	PhysicsContinuum physicsContinuum_0 = 
      ((PhysicsContinuum) simulation_0.getContinuumManager().getContinuum("Physics 1"));
	  
	Units units_0 = 
      simulation_0.getUnitsManager().getInternalUnits(new IntVector(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}));
	
	VelocityProfile velocityProfile_0 = 
      physicsContinuum_0.getInitialConditions().get(VelocityProfile.class);
	
	velocityProfile_0.getMethod(ConstantVectorProfileMethod.class).getQuantity().setDefinition("[${car_velocity[ms-1]}*-1*0.8, 0.0, 0.0]");
  velocityProfile_0.getMethod(ConstantVectorProfileMethod.class).getQuantity().setUnits(units_0);
  LabCoordinateSystem labCoordinateSystem_0 = 
    simulation_0.getCoordinateSystemManager().getLabCoordinateSystem();
  CartesianCoordinateSystem cartesianCoordinateSystem_0 = 
    ((CartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("Air Orientation - Car"));
  velocityProfile_0.setCoordinateSystem(cartesianCoordinateSystem_0);
	}
}

private void Solver(){

	Simulation simulation_0 = 
      getActiveSimulation();
	  
	double Roll = simulation_0.getReportManager().getReport("Roll [deg]").getReportMonitorValue();
	double Yaw = simulation_0.getReportManager().getReport("Yaw [rad]").getReportMonitorValue();
	double CornerRadius = simulation_0.getReportManager().getReport("Corner Radius [m]").getReportMonitorValue(); 

	StepStoppingCriterion stepStoppingCriterion_0 = 
    ((StepStoppingCriterion) simulation_0.getSolverStoppingCriterionManager().getSolverStoppingCriterion("Maximum Steps"));
    stepStoppingCriterion_0.setMaximumNumberSteps(1);

    Solution solution_0 = 
      simulation_0.getSolution();
    solution_0.initializeSolution();
    simulation_0.getSimulationIterator().run();

  // BC Check //
	
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
	
    scene_0.export3DSceneFileAndWait(resolvePath("..\\Post_Processing\\Pre Checks\\Boundary Conditions\\Scene_BC.sce"), "CP", simpleAnnotation_0.getText(), false, true);
	
	currentView_0.setInput(new DoubleVector(new double[] {-10.0, 0.0, 4.0}), new DoubleVector(new double[] {-10.0, -60.0, 4.0}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), 20.0, 1, 30.0);
	scene_0.printAndWait(resolvePath("..\\Post_Processing\\Pre Checks\\Boundary Conditions\\side.png"), 1, 1920, 1080, true, false);
	
	currentView_0.setInput(new DoubleVector(new double[] {-10.0, 0.0, 0.0}), new DoubleVector(new double[] {-10.0, 0.0, 60.0}), new DoubleVector(new double[] {0.0, 1.0, 0.0}), 20.0, 1, 30.0);
	scene_0.printAndWait(resolvePath("..\\Post_Processing\\Pre Checks\\Boundary Conditions\\top.png"), 1, 1920, 1080, true, false);
	
	currentView_0.setInput(new DoubleVector(new double[] {-10.0, 0.0, 4.0}), new DoubleVector(new double[] {60.0, 0.0, 4.0}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), 6.0, 1, 30.0);
	scene_0.printAndWait(resolvePath("..\\Post_Processing\\Pre Checks\\Boundary Conditions\\front.png"), 1, 1920, 1080, true, false);

	// Run Solver //

    stepStoppingCriterion_0.setMaximumNumberSteps(100);
    simulation_0.getSimulationIterator().run();
	simulation_0.saveState(resolvePath("..\\Simulation\\FST11CFD.sim"));

	stepStoppingCriterion_0.setMaximumNumberSteps(200);
    simulation_0.getSimulationIterator().run();
	simulation_0.saveState(resolvePath("..\\Simulation\\FST11CFD.sim"));

	stepStoppingCriterion_0.setMaximumNumberSteps(300);
    simulation_0.getSimulationIterator().run();
	simulation_0.saveState(resolvePath("..\\Simulation\\FST11CFD.sim"));

	stepStoppingCriterion_0.setMaximumNumberSteps(400);
    simulation_0.getSimulationIterator().run();
	simulation_0.saveState(resolvePath("..\\Simulation\\FST11CFD.sim"));

	stepStoppingCriterion_0.setMaximumNumberSteps(500);
    simulation_0.getSimulationIterator().run();
	simulation_0.saveState(resolvePath("..\\Simulation\\FST11CFD.sim"));

	stepStoppingCriterion_0.setMaximumNumberSteps(600);
    simulation_0.getSimulationIterator().run();
	simulation_0.saveState(resolvePath("..\\Simulation\\FST11CFD.sim"));

	stepStoppingCriterion_0.setMaximumNumberSteps(700);
    simulation_0.getSimulationIterator().run();
	simulation_0.saveState(resolvePath("..\\Simulation\\FST11CFD.sim"));

	stepStoppingCriterion_0.setMaximumNumberSteps(800);
    simulation_0.getSimulationIterator().run();
	simulation_0.saveState(resolvePath("..\\Simulation\\FST11CFD.sim"));

	stepStoppingCriterion_0.setMaximumNumberSteps(900);
    simulation_0.getSimulationIterator().run();
	simulation_0.saveState(resolvePath("..\\Simulation\\FST11CFD.sim"));

	stepStoppingCriterion_0.setMaximumNumberSteps(1000);
    simulation_0.getSimulationIterator().run();
	simulation_0.saveState(resolvePath("..\\Simulation\\FST11CFD.sim"));

	}
}