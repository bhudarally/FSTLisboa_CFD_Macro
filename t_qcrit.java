// STAR-CCM+ macro: s_qcrit.java
// Written by STAR-CCM+ 14.04.011
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;
import star.base.query.*;
import star.vis.*;
import star.flow.*;

public class t_qcrit extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    Units units_0 = 
      simulation_0.getUnitsManager().getPreferredUnits(new IntVector(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));

    Units units_1 = 
      simulation_0.getUnitsManager().getPreferredUnits(new IntVector(new int[] {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));

    Region region_0 = 
      simulation_0.getRegionManager().getRegion("Fluid");

    Units units_2 = 
      simulation_0.getUnitsManager().getPreferredUnits(new IntVector(new int[] {0, 0, -2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));

    PrimitiveFieldFunction primitiveFieldFunction_0 = 
      ((PrimitiveFieldFunction) simulation_0.getFieldFunctionManager().getFunction("QcritMonitor"));

    IsoPart isoPart_0 = 
      simulation_0.getPartManager().createIsoPart(new NeoObjectVector(new Object[] {region_0}), primitiveFieldFunction_0);

    isoPart_0.setMode(IsoMode.ISOVALUE_SINGLE);

    SingleIsoValue singleIsoValue_0 = 
      isoPart_0.getSingleIsoValue();

    singleIsoValue_0.getValueQuantity().setValue(10000.0);

    singleIsoValue_0.getValueQuantity().setUnits(units_2);

    simulation_0.getSceneManager().createEmptyScene("Scene");

    Scene scene_0 = 
      simulation_0.getSceneManager().getScene("Scene 1");

    scene_0.initializeAndWait();

    SceneUpdate sceneUpdate_1 = 
      scene_0.getSceneUpdate();

    scene_0.resetCamera();

    scene_0.setPresentationName("Q_Criterion");

    scene_0.close();

    PartDisplayer partDisplayer_0 = 
      scene_0.getDisplayerManager().createPartDisplayer("Geometry", -1, 4);

    partDisplayer_0.initialize();

    partDisplayer_0.setOutline(true);
	
	partDisplayer_0.setFeatures(true);

    partDisplayer_0.setSurface(true);

    partDisplayer_0.getInputParts().setQuery(null);
	
	partDisplayer_0.setColorMode(PartColorMode.CONSTANT);

    partDisplayer_0.getInputParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "Faces"), new TypePredicate(TypeOperator.Is, Boundary.class))), new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.Contains, "Mono"))), new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, InterfaceBoundary.class), new RelationshipPredicate(RelationshipOperator.DescendantOf, new IdentityPredicate(IdentityOperator.Equals, Arrays.<ClientServerObject>asList(region_0))))))), Query.STANDARD_MODIFIERS));
    
    SymmetricRepeat symmetricRepeat_0 = 
      ((SymmetricRepeat) simulation_0.getTransformManager().getObject("Domain.Symmetry 1"));

    partDisplayer_0.setVisTransform(symmetricRepeat_0);

    ScalarDisplayer scalarDisplayer_1 = 
      scene_0.getDisplayerManager().createScalarDisplayer("Scalar");

    scalarDisplayer_1.initialize();

    Legend legend_0 = 
      scalarDisplayer_1.getLegend();

  try {
  UserLookupTable userLookupTable_0 = 
      ((UserLookupTable) simulation_0.get(LookupTableManager.class).getObject("Carreira_CPT_-1+1"));
  }
  catch (Exception e){
    
  UserLookupTable userLookupTable_0 = 
      simulation_0.get(LookupTableManager.class).createLookupTable();
    userLookupTable_0.setPresentationName("Carreira_CPT_-1+1"); 
  userLookupTable_0.setColorMap(new ColorMap(new DoubleVector(new double[] {0.0, 0.0, 0.0, 0.0, 0.072, 1.0, 0.4117647058823529, 0.7058823529411765, 0.12, 1.0, 0.4117647058823529, 0.7058823529411765, 0.24, 0.0, 0.0, 1.0, 0.26, 0.0, 0.0, 1.0, 0.49, 0.0, 1.0, 1.0, 0.51, 0.0, 1.0, 1.0, 0.627, 0.0, 1.0, 0.0, 0.75, 1.0, 1.0, 0.0, 0.9640287769784173, 1.0, 0.0, 0.0, 0.9892086330935251, 1.0, 0.0, 0.0}), new DoubleVector(new double[] {0.0, 1.0, 1.0, 1.0}), 0));
  }

  UserLookupTable userLookupTable_0 = 
      ((UserLookupTable) simulation_0.get(LookupTableManager.class).getObject("Carreira_CPT_-1+1"));

    legend_0.setLookupTable(userLookupTable_0);

    isoPart_0.setPresentationName("Q_crit");

    scalarDisplayer_1.getInputParts().setQuery(null);

    scalarDisplayer_1.getInputParts().setObjects(isoPart_0);

    CurrentView currentView_1 = 
      scene_0.getCurrentView();

    currentView_1.setInput(new DoubleVector(new double[] {6.410281308971486E-4, 0.0061553824677450745, -0.05678916659940755}), new DoubleVector(new double[] {-0.047493997121306045, -0.4645109700833814, 3.2679490064261087}), new DoubleVector(new double[] {-0.0010587800788549474, 0.9901641965631173, 0.13990619296250376}), 0.8660254037844386, 0, 30.0);

    try {
  UserFieldFunction userFieldFunction_0 = 
    ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("v_avg"));
  }
  catch (Exception e){
  UserFieldFunction userFieldFunction_111 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();
    userFieldFunction_111.getTypeOption().setSelected(FieldFunctionTypeOption.Type.VECTOR);
    userFieldFunction_111.setPresentationName("Velocity Avg.");
    userFieldFunction_111.setFunctionName("v_avg");
    Units units_3 = 
      simulation_0.getUnitsManager().getPreferredUnits(new IntVector(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}));
    userFieldFunction_111.setDefinition("[${V[i]Monitor},${V[j]Monitor},${V[k]Monitor}]");
  }

  try {
	  
	 UserFieldFunction userFieldFunction_0 = 
      ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("CpT"));

    scalarDisplayer_1.getScalarDisplayQuantity().setFieldFunction(userFieldFunction_0);

  }
  catch (Exception e){

    UserFieldFunction userFieldFunction_0 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();

    userFieldFunction_0.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);

    userFieldFunction_0.setFunctionName("CpT");

    userFieldFunction_0.setPresentationName("CpT");

    userFieldFunction_0.setDefinition("(${TotalPressureMonitor}-${Pressure Avg. Inlet})/(0.5*1.225*pow(${Car-Velocity},2))");

    scalarDisplayer_1.getScalarDisplayQuantity().setFieldFunction(userFieldFunction_0);
    
  }

    scalarDisplayer_1.getScalarDisplayQuantity().setRange(new DoubleVector(new double[] {-1.0, 1.0}));

    scalarDisplayer_1.getScalarDisplayQuantity().setClip(ClipMode.NONE);
	
	scene_0.setBackgroundColorMode(BackgroundColorMode.SOLID);

    SolidBackgroundColor solidBackgroundColor_0 = 
      scene_0.getSolidBackgroundColor();

    solidBackgroundColor_0.setColor(new DoubleVector(new double[] {0.0, 0.0, 0.0}));

	legend_0.setShadow(true);
	
	legend_0.setLabelFormat("%-+1.1f");
	
	legend_0.setNumberOfLabels(4);
	
	legend_0.setTextColor(new DoubleVector(new double[] {1.0, 1.0, 1.0}));
	
	legend_0.setWidth(0.02);  
	
	legend_0.setFontString("Lucida Sans-Bold");
	
	legend_0.setTextPosition(LegendTextPosition.LEGEND_POSITION_FOLLOWING);
	
	legend_0.setHeight(0.3);
	
	legend_0.setTitleHeight(0.032);
	
	legend_0.setLabelHeight(0.025);
	
	legend_0.updateLayout(new DoubleVector(new double[] {0.96, 0.52}), 0.02, 0.35, 1);
	
	ImageAnnotation2D imageAnnotation2D_0 = 
      ((ImageAnnotation2D) simulation_0.getAnnotationManager().getObject("fst_white")); 
	  
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

    scalarDisplayer_1.setFillMode(ScalarFillMode.NODE_FILLED);

    scalarDisplayer_1.setVisTransform(symmetricRepeat_0);
	
	currentView_1.setInput(new DoubleVector(new double[] {-0.6956927109621422, -0.34692803176397735, 0.623441732027425}), new DoubleVector(new double[] {3.3971063352210265, -4.439727077947145, 4.7162407782105955}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), 1.3721137418469573, 1, 30.0);

    scene_0.printAndWait(resolvePath("..\\Post_Processing\\Qcrit\\top_front.png"), 1, 1920, 1080, true, false);
	
	currentView_1.setInput(new DoubleVector(new double[] {-0.6956927109621422, -0.34692803176397735, 0.623441732027425}), new DoubleVector(new double[] {-4.788491757145312, -4.439727077947147, 4.716240778210595}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), 1.3721137418469573, 1, 30.0);

	scene_0.printAndWait(resolvePath("..\\Post_Processing\\Qcrit\\top_rear.png"), 1, 1920, 1080, true, false);
	
	currentView_1.setInput(new DoubleVector(new double[] {-0.4693272606246477, 0.0027671560177493504, 0.5619501285553141}), new DoubleVector(new double[] {-0.4693272606246477, 0.0027671560177493504, 7.634044528378808}), new DoubleVector(new double[] {0.0, 1.0, 0.0}), 1.0314339431004096, 1, 30.0);

	scene_0.printAndWait(resolvePath("..\\Post_Processing\\Qcrit\\top.png"), 1, 1920, 1080, true, false);
	
	currentView_1.setInput(new DoubleVector(new double[] {-0.4693272606246477, 0.0027671560177493504, 0.5619501285553141}), new DoubleVector(new double[] {-0.4693272606246477, -7.069327243805744, 0.5619501285553141}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), 1.0314339431004096, 1, 30.0);

	scene_0.printAndWait(resolvePath("..\\Post_Processing\\Qcrit\\right_side.png"), 1, 1920, 1080, true, false);

	currentView_1.setInput(new DoubleVector(new double[] {-0.7921888563076198, 0.006219453446332743, 0.6047364194775077}), new DoubleVector(new double[] {-8.187144851814086, 0.006219453446332743, 0.6047364194775077}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), 0.7519691560050431, 1, 30.0);

	scene_0.printAndWait(resolvePath("..\\Post_Processing\\Qcrit\\rear.png"), 1, 1920, 1080, true, false);
	
	currentView_1.setInput(new DoubleVector(new double[] {-0.7921888563076198, 0.006219453446332743, 0.6047364194775077}), new DoubleVector(new double[] {6.602767139198845, 0.006219453446332743, 0.6047364194775077}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), 0.7519691560050431, 1, 30.0);

	scene_0.printAndWait(resolvePath("..\\Post_Processing\\Qcrit\\front.png"), 1, 1920, 1080, true, false);
	
	currentView_1.setInput(new DoubleVector(new double[] {-0.769086269603533, -0.04296129306718832, 0.3823805869760588}), new DoubleVector(new double[] {-4.911408773377667, -4.185283796841322, -3.7599419167980748}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), 1.3721137418469573, 1, 30.0);

	scene_0.printAndWait(resolvePath("..\\Post_Processing\\Qcrit\\bottom_rear.png"), 1, 1920, 1080, true, false);
	
	currentView_1.setInput(new DoubleVector(new double[] {-0.7752218798465687, -0.08548056323844566, 0.30553785713475623}), new DoubleVector(new double[] {3.3252680871756986, -4.185970530260715, -3.794952109887511}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), 1.3721137418469573, 1, 30.0);

	scene_0.printAndWait(resolvePath("..\\Post_Processing\\Qcrit\\bottom_front.png"), 1, 1920, 1080, true, false);

	currentView_1.setInput(new DoubleVector(new double[] {-0.491889878129969, -0.002656430902668391, 0.04623315700510933}), new DoubleVector(new double[] {-0.491889878129969, -0.002656430902668391, -7.020437655897966}), new DoubleVector(new double[] {0.0, -1.0, 0.0}), 1.0314339431004096, 1, 30.0);

	scene_0.printAndWait(resolvePath("..\\Post_Processing\\Qcrit\\bottom.png"), 1, 1920, 1080, true, false);
  }
}
