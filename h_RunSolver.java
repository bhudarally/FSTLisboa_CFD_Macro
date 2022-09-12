// STAR-CCM+ macro: h_RunSolver.java
// Written by STAR-CCM+ 14.04.011
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;
import java.io.*;
import star.vis.*;
import star.base.query.*;
import star.meshing.*;

public class h_RunSolver extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {
	  
	Simulation simulation_0 = 
      getActiveSimulation();

	StepStoppingCriterion stepStoppingCriterion_0 = 
      ((StepStoppingCriterion) simulation_0.getSolverStoppingCriterionManager().getSolverStoppingCriterion("Maximum Steps"));
	  
	stepStoppingCriterion_0.setMaximumNumberSteps(100);
	  
	Solution solution_0 = 
      simulation_0.getSolution();

    solution_0.initializeSolution();
	
	simulation_0.getSimulationIterator().run();
	
	simulation_0.saveState(resolvePath("..\\Simulation\\FST10eCFD.sim"));
	
	simulation_0.getSceneManager().createEmptyScene("Scene");

	Scene scene_0 = 
      simulation_0.getSceneManager().getScene("Scene 1");

    scene_0.initializeAndWait();

    SceneUpdate sceneUpdate_0 = 
      scene_0.getSceneUpdate();

    scene_0.resetCamera();

    scene_0.setPresentationName("y+");

	ScalarDisplayer scalarDisplayer_1 = 
      scene_0.getDisplayerManager().createScalarDisplayer("Scalar");

    scalarDisplayer_1.initialize();

    Legend legend_1 = 
      scalarDisplayer_1.getLegend();

    scene_0.close();

    BlueRedLookupTable blueRedLookupTable_0 = 
      ((BlueRedLookupTable) simulation_0.get(LookupTableManager.class).getObject("blue-red"));

    legend_1.setLookupTable(blueRedLookupTable_0);

    Region region_0 = 
      simulation_0.getRegionManager().getRegion("Fluid");

    scalarDisplayer_1.getInputParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "Faces"), new TypePredicate(TypeOperator.Is, Boundary.class))), new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.Contains, "Mono"))), new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, InterfaceBoundary.class), new RelationshipPredicate(RelationshipOperator.DescendantOf, new IdentityPredicate(IdentityOperator.Equals, Arrays.<ClientServerObject>asList(region_0))))))), Query.STANDARD_MODIFIERS));

        SymmetricRepeat symmetricRepeat_0 = 
      ((SymmetricRepeat) simulation_0.getTransformManager().getObject("Domain.Symmetry 1"));

    PrimitiveFieldFunction primitiveFieldFunction_0 = 
      ((PrimitiveFieldFunction) simulation_0.getFieldFunctionManager().getFunction("WallYplus"));

     scalarDisplayer_1.setVisTransform(symmetricRepeat_0);

    scalarDisplayer_1.setFillMode(ScalarFillMode.NODE_FILLED);

    scalarDisplayer_1.getScalarDisplayQuantity().setFieldFunction(primitiveFieldFunction_0);
    
    scalarDisplayer_1.getScalarDisplayQuantity().setRange(new DoubleVector(new double[] {0, 3}));

    scalarDisplayer_1.getScalarDisplayQuantity().setClip(ClipMode.MIN);
	  
	legend_1.setLevels(32);
	
	legend_1.setNumberOfLabels(4);
	
	legend_1.setShadow(true);
	
	legend_1.setLabelFormat("%-+1.1f");
	
	legend_1.setTextColor(new DoubleVector(new double[] {1.0, 1.0, 1.0}));
	
	legend_1.setWidth(0.02);  
	
	legend_1.setFontString("Lucida Sans-Bold");
	
	legend_1.setTextPosition(LegendTextPosition.LEGEND_POSITION_FOLLOWING);
	
	legend_1.setHeight(0.3);
	
	legend_1.setTitleHeight(0.032);
	
	legend_1.setLabelHeight(0.025);
	
	legend_1.updateLayout(new DoubleVector(new double[] {0.96, 0.52}), 0.02, 0.35, 1);
	
	ImageAnnotation2D imageAnnotation2D_0 = 
      simulation_0.getAnnotationManager().createImageAnnotation2D();
	  
	imageAnnotation2D_0.setFilePath("..\\Modules_Macro\\Library\\white.png");			

    imageAnnotation2D_0.setPresentationName("fst_white");
	
	ImageAnnotation2D imageAnnotation2D_1 = 
      simulation_0.getAnnotationManager().createImageAnnotation2D();
	
	imageAnnotation2D_1.setFilePath("..\\Modules_Macro\\Library\\black.png");
	
	imageAnnotation2D_1.setPresentationName("fst_black");
	  
	LogoAnnotation logoAnnotation_0 = 
      ((LogoAnnotation) simulation_0.getAnnotationManager().getObject("Logo"));

    scene_0.getAnnotationPropManager().removePropsForAnnotations(logoAnnotation_0);
	 
	 FixedAspectAnnotationProp fixedAspectAnnotationProp_0 = 
      (FixedAspectAnnotationProp) scene_0.getAnnotationPropManager().createPropForAnnotation(imageAnnotation2D_0);
	  
	fixedAspectAnnotationProp_0.setHeight(0.075);
	
	fixedAspectAnnotationProp_0.setPosition(new DoubleVector(new double[] {0.905, 0.03, 0.0}));

  SimpleAnnotation simpleAnnotation_4 = 
      ((SimpleAnnotation) simulation_0.getAnnotationManager().getObject("Description"));

    FixedAspectAnnotationProp fixedAspectAnnotationProp_4 = 
      (FixedAspectAnnotationProp) scene_0.getAnnotationPropManager().createPropForAnnotation(simpleAnnotation_4);

    fixedAspectAnnotationProp_4.setHeight(0.016);

    fixedAspectAnnotationProp_4.setPosition(new DoubleVector(new double[] {0.0075, 0.97, 0.0}));

    SimpleAnnotation simpleAnnotation_3 = 
      ((SimpleAnnotation) simulation_0.getAnnotationManager().getObject("Sim_Type"));

    FixedAspectAnnotationProp fixedAspectAnnotationProp_3 = 
      (FixedAspectAnnotationProp) scene_0.getAnnotationPropManager().createPropForAnnotation(simpleAnnotation_3);

    fixedAspectAnnotationProp_3.setHeight(0.016);

    fixedAspectAnnotationProp_3.setPosition(new DoubleVector(new double[] {0.0075, 0.95, 0.0}));

      SimpleAnnotation simpleAnnotation_11 = 
      ((SimpleAnnotation) simulation_0.getAnnotationManager().getObject("Velocity"));

    FixedAspectAnnotationProp fixedAspectAnnotationProp_11 = 
      (FixedAspectAnnotationProp) scene_0.getAnnotationPropManager().createPropForAnnotation(simpleAnnotation_11);

    fixedAspectAnnotationProp_11.setHeight(0.016);

    fixedAspectAnnotationProp_11.setPosition(new DoubleVector(new double[] {0.0075, 0.93, 0.0}));

  SimpleAnnotation simpleAnnotation_10 = 
      ((SimpleAnnotation) simulation_0.getAnnotationManager().getObject("FRH"));

    FixedAspectAnnotationProp fixedAspectAnnotationProp_10 = 
      (FixedAspectAnnotationProp) scene_0.getAnnotationPropManager().createPropForAnnotation(simpleAnnotation_10);

    fixedAspectAnnotationProp_10.setHeight(0.016);

    fixedAspectAnnotationProp_10.setPosition(new DoubleVector(new double[] {0.0075, 0.91, 0.0}));

    SimpleAnnotation simpleAnnotation_2 = 
      ((SimpleAnnotation) simulation_0.getAnnotationManager().getObject("RRH"));

    FixedAspectAnnotationProp fixedAspectAnnotationProp_2 = 
      (FixedAspectAnnotationProp) scene_0.getAnnotationPropManager().createPropForAnnotation(simpleAnnotation_2);

    fixedAspectAnnotationProp_2.setHeight(0.016);

    fixedAspectAnnotationProp_2.setPosition(new DoubleVector(new double[] {0.0075, 0.89, 0.0}));

    scene_0.setBackgroundColorMode(BackgroundColorMode.SOLID);

    SolidBackgroundColor solidBackgroundColor_0 = 
      scene_0.getSolidBackgroundColor();

    solidBackgroundColor_0.setColor(new DoubleVector(new double[] {0.0, 0.0, 0.0}));

    scene_0.setViewOrientation(new DoubleVector(new double[] {1.0, -1.0, 1.0}), new DoubleVector(new double[] {0.0, 0.0, 1.0}));

    CurrentView currentView_0 = 
      scene_0.getCurrentView();

    currentView_0.setInput(new DoubleVector(new double[] {0.0, 0.0, 0.0}), new DoubleVector(new double[] {2.986546350203028, -2.986546350203028, 2.986546350203028}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), 0.8734983028551054, 1, 30.0);
	
	legend_1.setTextColor(new DoubleVector(new double[] {1.0, 1.0, 1.0}));

    currentView_0.setInput(new DoubleVector(new double[] {-0.6956927109621422, -0.34692803176397735, 0.623441732027425}), new DoubleVector(new double[] {3.3971063352210265, -4.439727077947145, 4.7162407782105955}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), 1.3721137418469573, 1, 30.0);

    scene_0.printAndWait(resolvePath("..\\Post_Processing\\Y+\\top_front.png"), 1, 1920, 1080, true, false);

	currentView_0.setInput(new DoubleVector(new double[] {-0.6956927109621422, -0.34692803176397735, 0.623441732027425}), new DoubleVector(new double[] {-4.788491757145312, -4.439727077947147, 4.716240778210595}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), 1.3721137418469573, 1, 30.0);

	scene_0.printAndWait(resolvePath("..\\Post_Processing\\Y+\\top_rear.png"), 1, 1920, 1080, true, false);
	
	currentView_0.setInput(new DoubleVector(new double[] {-0.4693272606246477, 0.0027671560177493504, 0.5619501285553141}), new DoubleVector(new double[] {-0.4693272606246477, 0.0027671560177493504, 7.634044528378808}), new DoubleVector(new double[] {0.0, 1.0, 0.0}), 1.0314339431004096, 1, 30.0);

	scene_0.printAndWait(resolvePath("..\\Post_Processing\\Y+\\top.png"), 1, 1920, 1080, true, false);
	
	currentView_0.setInput(new DoubleVector(new double[] {-0.4693272606246477, 0.0027671560177493504, 0.5619501285553141}), new DoubleVector(new double[] {-0.4693272606246477, -7.069327243805744, 0.5619501285553141}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), 1.0314339431004096, 1, 30.0);

	scene_0.printAndWait(resolvePath("..\\Post_Processing\\Y+\\right_side.png"), 1, 1920, 1080, true, false);

	currentView_0.setInput(new DoubleVector(new double[] {-0.7921888563076198, 0.006219453446332743, 0.6047364194775077}), new DoubleVector(new double[] {-8.187144851814086, 0.006219453446332743, 0.6047364194775077}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), 0.7519691560050431, 1, 30.0);

	scene_0.printAndWait(resolvePath("..\\Post_Processing\\Y+\\rear.png"), 1, 1920, 1080, true, false);
	
	currentView_0.setInput(new DoubleVector(new double[] {-0.7921888563076198, 0.006219453446332743, 0.6047364194775077}), new DoubleVector(new double[] {6.602767139198845, 0.006219453446332743, 0.6047364194775077}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), 0.7519691560050431, 1, 30.0);

	scene_0.printAndWait(resolvePath("..\\Post_Processing\\Y+\\front.png"), 1, 1920, 1080, true, false);
	
	currentView_0.setInput(new DoubleVector(new double[] {-0.769086269603533, -0.04296129306718832, 0.3823805869760588}), new DoubleVector(new double[] {-4.911408773377667, -4.185283796841322, -3.7599419167980748}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), 1.3721137418469573, 1, 30.0);

	scene_0.printAndWait(resolvePath("..\\Post_Processing\\Y+\\bottom_rear.png"), 1, 1920, 1080, true, false);
	
	currentView_0.setInput(new DoubleVector(new double[] {-0.7752218798465687, -0.08548056323844566, 0.30553785713475623}), new DoubleVector(new double[] {3.3252680871756986, -4.185970530260715, -3.794952109887511}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), 1.3721137418469573, 1, 30.0);

	scene_0.printAndWait(resolvePath("..\\Post_Processing\\Y+\\bottom_front.png"), 1, 1920, 1080, true, false);

	currentView_0.setInput(new DoubleVector(new double[] {-0.491889878129969, -0.002656430902668391, 0.04623315700510933}), new DoubleVector(new double[] {-0.491889878129969, -0.002656430902668391, -7.020437655897966}), new DoubleVector(new double[] {0.0, -1.0, 0.0}), 1.0314339431004096, 1, 30.0);

	scene_0.printAndWait(resolvePath("..\\Post_Processing\\Y+\\bottom.png"), 1, 1920, 1080, true, false);

	stepStoppingCriterion_0.setMaximumNumberSteps(200);
	
	simulation_0.getSimulationIterator().run();
	
	simulation_0.saveState(resolvePath("..\\Simulation\\FST10eCFD.sim"));
	
	stepStoppingCriterion_0.setMaximumNumberSteps(300);
	
	simulation_0.getSimulationIterator().run();
	
	simulation_0.saveState(resolvePath("..\\Simulation\\FST10eCFD.sim"));
	
	stepStoppingCriterion_0.setMaximumNumberSteps(400);
	
	simulation_0.getSimulationIterator().run();
	
	simulation_0.saveState(resolvePath("..\\Simulation\\FST10eCFD.sim"));
	
	stepStoppingCriterion_0.setMaximumNumberSteps(500);
	
	simulation_0.getSimulationIterator().run();
	
	simulation_0.saveState(resolvePath("..\\Simulation\\FST10eCFD.sim"));
	
	stepStoppingCriterion_0.setMaximumNumberSteps(600);
	
	simulation_0.getSimulationIterator().run();
	
	simulation_0.saveState(resolvePath("..\\Simulation\\FST10eCFD.sim"));
	
	stepStoppingCriterion_0.setMaximumNumberSteps(700);
	
	simulation_0.getSimulationIterator().run();
	
	simulation_0.saveState(resolvePath("..\\Simulation\\FST10eCFD.sim"));
	
	stepStoppingCriterion_0.setMaximumNumberSteps(800);
	
	simulation_0.getSimulationIterator().run();
	
	simulation_0.saveState(resolvePath("..\\Simulation\\FST10eCFD.sim"));
	
	/*stepStoppingCriterion_0.setMaximumNumberSteps(900);
	
	simulation_0.getSimulationIterator().run();
	
	simulation_0.saveState(resolvePath("..\\Simulation\\FST10eCFD.sim"));
	
	stepStoppingCriterion_0.setMaximumNumberSteps(1000);
	
	simulation_0.getSimulationIterator().run();*/
  }
}
