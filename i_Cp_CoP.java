// STAR-CCM+ macro: i_Cp_CoP.java
// Written by STAR-CCM+ 14.04.011
package macro;

import java.util.*;
import java.io.*;
import java.nio.*;
import star.common.*;
import star.base.neo.*;
import star.base.report.*;
import star.base.query.*;
import star.flow.*;
import star.vis.*;

public class i_Cp_CoP extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    simulation_0.getSceneManager().createEmptyScene("Scene");

    Scene scene_1 = 
      simulation_0.getSceneManager().getScene("Scene 1");

    scene_1.initializeAndWait();

    SceneUpdate sceneUpdate_1 = 
      scene_1.getSceneUpdate();

    scene_1.resetCamera();
	
	scene_1.setPresentationName("CP");

    ScalarDisplayer scalarDisplayer_1 = 
      scene_1.getDisplayerManager().createScalarDisplayer("Scalar");

    scalarDisplayer_1.initialize();

    Legend legend_1 = 
      scalarDisplayer_1.getLegend();

    try {
  UserLookupTable userLookupTable_0 = 
      ((UserLookupTable) simulation_0.get(LookupTableManager.class).getObject("Carreira_CP_-3+1"));
  }
  catch (Exception e){
    
  UserLookupTable userLookupTable_0 = 
      simulation_0.get(LookupTableManager.class).createLookupTable();
    userLookupTable_0.setPresentationName("Carreira_CP_-3+1");  
  userLookupTable_0.setColorMap(new ColorMap(new DoubleVector(new double[] {0.0, 1.0, 0.0, 1.0, 0.19, 0.0, 0.0, 0.9215686274509803, 0.259, 0.2478402969473144, 0.0, 0.892816517288296, 0.436, 0.0, 1.0, 1.0, 0.5, 0.3258965792972184, 0.9454054260487254, 0.9454054260487254, 0.5860655737704918, 0.0, 1.0, 0.0, 0.725, 0.6862745098039216, 0.6862745098039216, 0.6862745098039216, 0.774, 0.6862745098039216, 0.6862745098039216, 0.6862745098039216, 0.8688524590163934, 1.0, 1.0, 0.0, 0.975, 1.0, 0.0, 0.0, 1.0, 0.5019607843137255, 0.0, 0.0}), new DoubleVector(new double[] {0.0, 1.0, 1.0, 1.0}), 0));
  }
  //}
  
  UserLookupTable userLookupTable_0 = 
      ((UserLookupTable) simulation_0.get(LookupTableManager.class).getObject("Carreira_CP_-3+1"));

    legend_1.setLookupTable(userLookupTable_0);

    scene_1.close();

    Region region_0 = 
      simulation_0.getRegionManager().getRegion("Fluid");

  scalarDisplayer_1.getInputParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "Faces"), new TypePredicate(TypeOperator.Is, Boundary.class))), new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.Contains, "Mono"))), new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, InterfaceBoundary.class), new RelationshipPredicate(RelationshipOperator.DescendantOf, new IdentityPredicate(IdentityOperator.Equals, Arrays.<ClientServerObject>asList(region_0))))))), Query.STANDARD_MODIFIERS));

        SymmetricRepeat symmetricRepeat_0 = 
      ((SymmetricRepeat) simulation_0.getTransformManager().getObject("Domain.Symmetry 1"));

    scalarDisplayer_1.setVisTransform(symmetricRepeat_0);

    scalarDisplayer_1.setFillMode(ScalarFillMode.NODE_FILLED);

    try {

    UserFieldFunction userFieldFunction_0 = 
      ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("Cp"));

    scalarDisplayer_1.getScalarDisplayQuantity().setFieldFunction(userFieldFunction_0);

  }
  catch (Exception e){

    UserFieldFunction userFieldFunction_0 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();

    userFieldFunction_0.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);

    userFieldFunction_0.setPresentationName("Cp");

    userFieldFunction_0.setFunctionName("Cp");

    userFieldFunction_0.setDefinition("(${PressureMonitor}-${Pressure Avg. Inlet})/(0.5*1.225*pow(${Car-Velocity},2))"); 

    scalarDisplayer_1.getScalarDisplayQuantity().setFieldFunction(userFieldFunction_0);
    
  }

    scalarDisplayer_1.getScalarDisplayQuantity().setRange(new DoubleVector(new double[] {-3, 1}));

	
    CurrentView currentView_1 = 
      scene_1.getCurrentView();

    currentView_1.setInput(new DoubleVector(new double[] {-1.1126574104365323, 0.20225957547093776, -0.15273698745746422}), new DoubleVector(new double[] {3.3561328116456917, -4.266530646611281, 4.316053234624761}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), 1.743968486767048, 1, 30.0);

    scalarDisplayer_1.getScalarDisplayQuantity().setClip(ClipMode.NONE);

    scene_1.setBackgroundColorMode(BackgroundColorMode.SOLID);

    SolidBackgroundColor solidBackgroundColor_0 = 
      scene_1.getSolidBackgroundColor();

    solidBackgroundColor_0.setColor(new DoubleVector(new double[] {0.0, 0.0, 0.0}));

    currentView_1.setInput(new DoubleVector(new double[] {0.00490448001780453, 0.03390751617247466, 0.42083041625281464}), new DoubleVector(new double[] {-0.12711884100163373, -0.8788463081594409, 4.797109855086307}), new DoubleVector(new double[] {-0.0025919269947986463, 0.9822199685748046, 0.18771578315997728}), 0.7873877242199345, 1, 30.0);

    scene_1.setViewOrientation(new DoubleVector(new double[] {1.0, -1.0, 1.0}), new DoubleVector(new double[] {0.0, 0.0, 1.0}));

    currentView_1.setInput(new DoubleVector(new double[] {0.540409853158482, -0.26760635251974474, 0.08884004670398005}), new DoubleVector(new double[] {3.2280041192341073, -2.9552006185953714, 2.7764343127796067}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), 0.4649662949392222, 1, 30.0);

    legend_1.setTextColor(new DoubleVector(new double[] {1.0, 1.0, 1.0}));
	
	legend_1.setWidth(0.02);
	
	legend_1.setNumberOfLabels(5);
	
	legend_1.setFontString("Lucida Sans-Bold");
	
	legend_1.setTextPosition(LegendTextPosition.LEGEND_POSITION_FOLLOWING);
	
	legend_1.setHeight(0.3);
	
	legend_1.setTitleHeight(0.032);

  legend_1.setLevels(64);
	
	legend_1.setLabelHeight(0.025);
	
	legend_1.updateLayout(new DoubleVector(new double[] {0.96, 0.52}), 0.02, 0.35, 1);
	  
	ImageAnnotation2D imageAnnotation2D_0 = 
      ((ImageAnnotation2D) simulation_0.getAnnotationManager().getObject("fst_white")); 
	  
	LogoAnnotation logoAnnotation_0 = 
      ((LogoAnnotation) simulation_0.getAnnotationManager().getObject("Logo"));

    scene_1.getAnnotationPropManager().removePropsForAnnotations(logoAnnotation_0);
	 
	 FixedAspectAnnotationProp fixedAspectAnnotationProp_0 = 
      (FixedAspectAnnotationProp) scene_1.getAnnotationPropManager().createPropForAnnotation(imageAnnotation2D_0);
	  
	fixedAspectAnnotationProp_0.setHeight(0.075);
	
	fixedAspectAnnotationProp_0.setPosition(new DoubleVector(new double[] {0.905, 0.03, 0.0}));

    SimpleAnnotation simpleAnnotation_4 = 
      ((SimpleAnnotation) simulation_0.getAnnotationManager().getObject("Description"));

    FixedAspectAnnotationProp fixedAspectAnnotationProp_4 = 
      (FixedAspectAnnotationProp) scene_1.getAnnotationPropManager().createPropForAnnotation(simpleAnnotation_4);

    fixedAspectAnnotationProp_4.setHeight(0.016);

    fixedAspectAnnotationProp_4.setPosition(new DoubleVector(new double[] {0.0075, 0.97, 0.0}));

    SimpleAnnotation simpleAnnotation_3 = 
      ((SimpleAnnotation) simulation_0.getAnnotationManager().getObject("Sim_Type"));

    FixedAspectAnnotationProp fixedAspectAnnotationProp_3 = 
      (FixedAspectAnnotationProp) scene_1.getAnnotationPropManager().createPropForAnnotation(simpleAnnotation_3);

    fixedAspectAnnotationProp_3.setHeight(0.016);

    fixedAspectAnnotationProp_3.setPosition(new DoubleVector(new double[] {0.0075, 0.95, 0.0}));

      SimpleAnnotation simpleAnnotation_11 = 
      ((SimpleAnnotation) simulation_0.getAnnotationManager().getObject("Velocity"));

    FixedAspectAnnotationProp fixedAspectAnnotationProp_11 = 
      (FixedAspectAnnotationProp) scene_1.getAnnotationPropManager().createPropForAnnotation(simpleAnnotation_11);

    fixedAspectAnnotationProp_11.setHeight(0.016);

    fixedAspectAnnotationProp_11.setPosition(new DoubleVector(new double[] {0.0075, 0.93, 0.0}));

  SimpleAnnotation simpleAnnotation_10 = 
      ((SimpleAnnotation) simulation_0.getAnnotationManager().getObject("FRH"));

    FixedAspectAnnotationProp fixedAspectAnnotationProp_10 = 
      (FixedAspectAnnotationProp) scene_1.getAnnotationPropManager().createPropForAnnotation(simpleAnnotation_10);

    fixedAspectAnnotationProp_10.setHeight(0.016);

    fixedAspectAnnotationProp_10.setPosition(new DoubleVector(new double[] {0.0075, 0.91, 0.0}));

    SimpleAnnotation simpleAnnotation_2 = 
      ((SimpleAnnotation) simulation_0.getAnnotationManager().getObject("RRH"));

    FixedAspectAnnotationProp fixedAspectAnnotationProp_2 = 
      (FixedAspectAnnotationProp) scene_1.getAnnotationPropManager().createPropForAnnotation(simpleAnnotation_2);

    fixedAspectAnnotationProp_2.setHeight(0.016);

    fixedAspectAnnotationProp_2.setPosition(new DoubleVector(new double[] {0.0075, 0.89, 0.0}));

    legend_1.setLabelFormat("%-+2.2g");

    currentView_1.setInput(new DoubleVector(new double[] {-0.6956927109621422, -0.34692803176397735, 0.623441732027425}), new DoubleVector(new double[] {3.3971063352210265, -4.439727077947145, 4.7162407782105955}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), 1.3721137418469573, 1, 30.0);

    scene_1.printAndWait(resolvePath("..\\Post_Processing\\CP\\top_front.png"), 1, 1920, 1080, true, false);

	currentView_1.setInput(new DoubleVector(new double[] {-0.6956927109621422, -0.34692803176397735, 0.623441732027425}), new DoubleVector(new double[] {-4.788491757145312, -4.439727077947147, 4.716240778210595}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), 1.3721137418469573, 1, 30.0);

	scene_1.printAndWait(resolvePath("..\\Post_Processing\\CP\\top_rear.png"), 1, 1920, 1080, true, false);
	
	currentView_1.setInput(new DoubleVector(new double[] {-0.4693272606246477, 0.0027671560177493504, 0.5619501285553141}), new DoubleVector(new double[] {-0.4693272606246477, 0.0027671560177493504, 7.634044528378808}), new DoubleVector(new double[] {0.0, 1.0, 0.0}), 1.0314339431004096, 1, 30.0);

	scene_1.printAndWait(resolvePath("..\\Post_Processing\\CP\\top.png"), 1, 1920, 1080, true, false);
	
	currentView_1.setInput(new DoubleVector(new double[] {-0.4693272606246477, 0.0027671560177493504, 0.5619501285553141}), new DoubleVector(new double[] {-0.4693272606246477, -7.069327243805744, 0.5619501285553141}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), 1.0314339431004096, 1, 30.0);

	scene_1.printAndWait(resolvePath("..\\Post_Processing\\CP\\right_side.png"), 1, 1920, 1080, true, false);

	currentView_1.setInput(new DoubleVector(new double[] {-0.7921888563076198, 0.006219453446332743, 0.6047364194775077}), new DoubleVector(new double[] {-8.187144851814086, 0.006219453446332743, 0.6047364194775077}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), 0.7519691560050431, 1, 30.0);

	scene_1.printAndWait(resolvePath("..\\Post_Processing\\CP\\rear.png"), 1, 1920, 1080, true, false);
	
	currentView_1.setInput(new DoubleVector(new double[] {-0.7921888563076198, 0.006219453446332743, 0.6047364194775077}), new DoubleVector(new double[] {6.602767139198845, 0.006219453446332743, 0.6047364194775077}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), 0.7519691560050431, 1, 30.0);

	scene_1.printAndWait(resolvePath("..\\Post_Processing\\CP\\front.png"), 1, 1920, 1080, true, false);
	
	currentView_1.setInput(new DoubleVector(new double[] {-0.769086269603533, -0.04296129306718832, 0.3823805869760588}), new DoubleVector(new double[] {-4.911408773377667, -4.185283796841322, -3.7599419167980748}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), 1.3721137418469573, 1, 30.0);

	scene_1.printAndWait(resolvePath("..\\Post_Processing\\CP\\bottom_rear.png"), 1, 1920, 1080, true, false);
	
	currentView_1.setInput(new DoubleVector(new double[] {-0.7752218798465687, -0.08548056323844566, 0.30553785713475623}), new DoubleVector(new double[] {3.3252680871756986, -4.185970530260715, -3.794952109887511}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), 1.3721137418469573, 1, 30.0);

	scene_1.printAndWait(resolvePath("..\\Post_Processing\\CP\\bottom_front.png"), 1, 1920, 1080, true, false);

	currentView_1.setInput(new DoubleVector(new double[] {-0.491889878129969, -0.002656430902668391, 0.04623315700510933}), new DoubleVector(new double[] {-0.491889878129969, -0.002656430902668391, -7.020437655897966}), new DoubleVector(new double[] {0.0, -1.0, 0.0}), 1.0314339431004096, 1, 30.0);

	scene_1.printAndWait(resolvePath("..\\Post_Processing\\CP\\bottom.png"), 1, 1920, 1080, true, false);
  }
}
