// STAR-CCM+ macro: p_cf.java
// Written by STAR-CCM+ 14.04.011
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;
import star.base.query.*;
import star.vis.*;

public class q_cf_streamlines extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    Units units_0 = 
      simulation_0.getUnitsManager().getPreferredUnits(new IntVector(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));

    simulation_0.getSceneManager().createEmptyScene("Scene");

    Scene scene_0 = 
      simulation_0.getSceneManager().getScene("Scene 1");

    scene_0.initializeAndWait();

    SceneUpdate sceneUpdate_0 = 
      scene_0.getSceneUpdate();

      CurrentView currentView_0 = 
       scene_0.getCurrentView();

    scene_0.resetCamera();

    scene_0.setPresentationName("Cf");

    scene_0.close();

    ScalarDisplayer scalarDisplayer_0 = 
      scene_0.getDisplayerManager().createScalarDisplayer("Scalar");

    scalarDisplayer_0.initialize();

    Legend legend_0 = 
      scalarDisplayer_0.getLegend();

    BlueRedLookupTable blueRedLookupTable_0 = 
      ((BlueRedLookupTable) simulation_0.get(LookupTableManager.class).getObject("blue-red"));

    legend_0.setLookupTable(blueRedLookupTable_0);

    scalarDisplayer_0.setFillMode(ScalarFillMode.NODE_FILLED);

    SymmetricRepeat symmetricRepeat_0 = 
      ((SymmetricRepeat) simulation_0.getTransformManager().getObject("Domain.Symmetry 1"));

    scalarDisplayer_0.setVisTransform(symmetricRepeat_0);

    scalarDisplayer_0.getInputParts().setQuery(null);

    try {

    UserFieldFunction userFieldFunction_0 = 
      ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("Cf"));

    scalarDisplayer_0.getScalarDisplayQuantity().setFieldFunction(userFieldFunction_0);

  }
  catch (Exception e){

    UserFieldFunction userFieldFunction_0 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();

    userFieldFunction_0.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);

    userFieldFunction_0.setPresentationName("Cf");

    userFieldFunction_0.setFunctionName("Cf");

    userFieldFunction_0.setDefinition("mag($${WallShearStress})/(0.5*1.225*pow(${Car-Velocity},2))");

    scalarDisplayer_0.getScalarDisplayQuantity().setFieldFunction(userFieldFunction_0);
  }
  
    legend_0.setLevels(30);
	
	legend_0.setNumberOfLabels(3);
	
	legend_0.setShadow(true);
	
	legend_0.setLabelFormat("%-+1.3f");
	
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

    scalarDisplayer_0.getScalarDisplayQuantity().setRange(new DoubleVector(new double[] {0, 0.062131310023872885}));

    scalarDisplayer_0.getScalarDisplayQuantity().setRange(new DoubleVector(new double[] {0, 0.015}));

    scalarDisplayer_0.getScalarDisplayQuantity().setClip(ClipMode.MIN);

    scene_0.setBackgroundColorMode(BackgroundColorMode.SOLID);

    SolidBackgroundColor solidBackgroundColor_0 = 
      scene_0.getSolidBackgroundColor();

    solidBackgroundColor_0.setColor(new DoubleVector(new double[] {0.0, 0.0, 0.0}));

    Units units_1 =
      simulation_0.getUnitsManager().getPreferredUnits(new IntVector(new int[] {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));

    scene_0.setTransparencyOverrideMode(SceneTransparencyOverride.MAKE_SCENE_TRANSPARENT);

    PartDisplayer partDisplayer_0 =
      ((PartDisplayer) scene_0.getCreatorDisplayer());

    partDisplayer_0.initialize();

    StreamDisplayer streamDisplayer_0 =
      scene_0.getDisplayerManager().createStreamDisplayer("Streamline Stream");

    streamDisplayer_0.initialize();

    Legend legend_1 =
      streamDisplayer_0.getLegend();

    legend_1.setLookupTable(blueRedLookupTable_0);

    scene_0.setTransparencyOverrideMode(SceneTransparencyOverride.MAKE_SCENE_TRANSPARENT);

    Region region_0 = 
      simulation_0.getRegionManager().getRegion("Fluid");

    PrimitiveFieldFunction primitiveFieldFunction_1 = 
      ((PrimitiveFieldFunction) simulation_0.getFieldFunctionManager().getFunction("CellRelativeVelocity"));

    PrimitiveFieldFunction primitiveFieldFunction_0 =
      ((PrimitiveFieldFunction) simulation_0.getFieldFunctionManager().getFunction("WallShearStress"));

    ConstrainedStreamPart constrainedStreamPart_0 = 
      simulation_0.getPartManager().createConstrainedStreamPart(new NeoObjectVector(new Object[] {}), new DoubleVector(new double[] {0.0, 0.0, 0.0}), primitiveFieldFunction_1, 0.0, 20, 2);
    
    SourceSeed sourceSeed_0 =
      constrainedStreamPart_0.getSourceSeed();

      constrainedStreamPart_0.setMode(SeedMode.PART);

      scalarDisplayer_0.getInputParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new RelationshipPredicate(RelationshipOperator.DescendantOf, new IdentityPredicate(IdentityOperator.Equals, Arrays.<ClientServerObject>asList(region_0))), new NamePredicate(NameOperator.Contains, "FW"))), Query.STANDARD_MODIFIERS));

      constrainedStreamPart_0.getInputParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new RelationshipPredicate(RelationshipOperator.DescendantOf, new IdentityPredicate(IdentityOperator.Equals, Arrays.<ClientServerObject>asList(region_0))), new NamePredicate(NameOperator.Contains, "FW"))), Query.STANDARD_MODIFIERS));

    sourceSeed_0.getSeedParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new RelationshipPredicate(RelationshipOperator.DescendantOf, new IdentityPredicate(IdentityOperator.Equals, Arrays.<ClientServerObject>asList(region_0))), new NamePredicate(NameOperator.Contains, "FW"))), Query.STANDARD_MODIFIERS));

    streamDisplayer_0.getVisibleParts().addParts(constrainedStreamPart_0);

    streamDisplayer_0.getHiddenParts().addParts();

    scene_0.setTransparencyOverrideMode(SceneTransparencyOverride.USE_DISPLAYER_PROPERTY);

    constrainedStreamPart_0.setFieldFunction(primitiveFieldFunction_1);

    streamDisplayer_0.setColorMode(StreamColorMode.CONSTANT);

    streamDisplayer_0.setDisplayerColor(new DoubleVector(new double[] {0.0, 0.0, 0.0}));

    sourceSeed_0.setRandom(true);

    StreamMarkerSettings streamMarkerSettings_0 =
      streamDisplayer_0.getStreamMarkerSettings();

    streamMarkerSettings_0.getStreamIntermediateMarkerOption().setSelected(StreamIntermediateMarkerOption.Type.DISTANCE);

    StreamIntermediateMarkerGeometricDistanceSettings streamIntermediateMarkerGeometricDistanceSettings_0 =
      streamMarkerSettings_0.getGeometricDistanceSettings();

    streamIntermediateMarkerGeometricDistanceSettings_0.getStepSizeQuantity().setValue(0.3);

    streamDisplayer_0.setVisTransform(symmetricRepeat_0);

    streamDisplayer_0.setWidth(0.001);

    ScalarDisplayer scalarDisplayer_1 =
      ((ScalarDisplayer) scene_0.getDisplayerManager().getDisplayer("Scalar 1"));
      
    scene_0.setViewOrientation(new DoubleVector(new double[] {1.0, -1.0, 1.0}), new DoubleVector(new double[] {0.0, 0.0, 1.0}));

    sourceSeed_0.setNGridPoints(new IntVector(new int[] {1, 500}));

    currentView_0.setInput(new DoubleVector(new double[] {0.0, 0.0, 0.0}), new DoubleVector(new double[] {2.986546350203028, -2.986546350203028, 2.986546350203028}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), 0.8734983028551054, 1, 30.0);

    legend_0.setTextColor(new DoubleVector(new double[] {1.0, 1.0, 1.0}));

	currentView_0.setInput(new DoubleVector(new double[] {0.6228993161960271, -0.28860215213275003, 0.10522473979900582}), new DoubleVector(new double[] {0.6228993161960271, -0.28860215213275003, -3.657267524426041}), new DoubleVector(new double[] {0.0, 1.0, 0.0}), 0.5031107427275455, 1, 30.0);

	scene_0.printAndWait(resolvePath("..\\Post_Processing\\CF\\FW_bottom.png"), 1, 1920, 1080, true, false);

    sourceSeed_0.setNGridPoints(new IntVector(new int[] {1, 500}));

    scalarDisplayer_0.getInputParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.Contains, "Diff_L"))), Query.STANDARD_MODIFIERS));
  
    constrainedStreamPart_0.getInputParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.Contains, "Diff_L"))), Query.STANDARD_MODIFIERS));
  
    sourceSeed_0.getSeedParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.Contains, "Diff_L"))), Query.STANDARD_MODIFIERS));
  
  currentView_0.setInput(new DoubleVector(new double[] {-0.60839593908895, 0.3932044922820637, 0.04058034345485462}), new DoubleVector(new double[] {-0.60839593908895, 0.3932044922820637, -3.3684936381771116}), new DoubleVector(new double[] {0.0, 1.0, 0.0}), 0.3433402839744932, 1, 30.0);
	
  scene_0.printAndWait(resolvePath("..\\Post_Processing\\CF\\Diff_Lateral_bottom.png"), 1, 1920, 1080, true, false);

    sourceSeed_0.setNGridPoints(new IntVector(new int[] {1, 300}));

    scalarDisplayer_0.getInputParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "SC"), new TypePredicate(TypeOperator.Is, Boundary.class))), Query.STANDARD_MODIFIERS));
  
    constrainedStreamPart_0.getInputParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "SC"), new TypePredicate(TypeOperator.Is, Boundary.class))), Query.STANDARD_MODIFIERS));
  
    sourceSeed_0.getSeedParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "SC"), new TypePredicate(TypeOperator.Is, Boundary.class))), Query.STANDARD_MODIFIERS));
  
  currentView_0.setInput(new DoubleVector(new double[] {-1.0624921848502218, 0.47313966732058865, 0.3007005219659089}), new DoubleVector(new double[] {-1.6738155084455606, 0.44699418536903074, -3.30630200386882}), new DoubleVector(new double[] {-5.985561342411154E-4, 0.9999743923036188, -0.00713137206733847}), 0.2444112085577733, 1, 30.0);

  scene_0.printAndWait(resolvePath("..\\Post_Processing\\CF\\SC_Bottom.png"), 1, 1920, 1080, true, false);

  sourceSeed_0.setNGridPoints(new IntVector(new int[] {1, 650}));

  scalarDisplayer_0.getInputParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "RW"), new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.DoesNotContain, "Sup"))), Query.STANDARD_MODIFIERS));
  
    constrainedStreamPart_0.getInputParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "RW"), new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.DoesNotContain, "Sup"))), Query.STANDARD_MODIFIERS));
  
    sourceSeed_0.getSeedParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "RW"), new TypePredicate(TypeOperator.Is, Boundary.class), new NamePredicate(NameOperator.DoesNotContain, "Sup"))), Query.STANDARD_MODIFIERS));
  
  currentView_0.setInput(new DoubleVector(new double[] {-1.614514816872937, 0.21940388494821986, 0.7553814709927682}), new DoubleVector(new double[] {-1.614514816872937, 0.21940388494821986, -3.254738905436681}), new DoubleVector(new double[] {0.0, 1.0, 0.0}), 0.2385745554438766, 1, 30.0);

  scene_0.printAndWait(resolvePath("..\\Post_Processing\\CF\\RW_Bottom.png"), 1, 1920, 1080, true, false);

  sourceSeed_0.setNGridPoints(new IntVector(new int[] {1, 1000}));

  scalarDisplayer_0.getInputParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "Diff_Back"), new TypePredicate(TypeOperator.Is, Boundary.class))), Query.STANDARD_MODIFIERS));
  
    constrainedStreamPart_0.getInputParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "Diff_Back"), new TypePredicate(TypeOperator.Is, Boundary.class))), Query.STANDARD_MODIFIERS));
  
    sourceSeed_0.getSeedParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "Diff_Back"), new TypePredicate(TypeOperator.Is, Boundary.class))), Query.STANDARD_MODIFIERS));
  
  currentView_0.setInput(new DoubleVector(new double[] {-1.4725047648251182, 0.34321654713347083, 0.10175395882723581}), new DoubleVector(new double[] {-1.4725047648251182, 0.34321654713347083, -3.254738905436681}), new DoubleVector(new double[] {0.0, 1.0, 0.0}), 0.35510040308639185, 1, 30.0);
  
  scene_0.printAndWait(resolvePath("..\\Post_Processing\\CF\\Diff_Back_Bottom.png"), 1, 1920, 1080, true, false);

    sourceSeed_0.setNGridPoints(new IntVector(new int[] {1, 200}));

  scalarDisplayer_0.getInputParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new RelationshipPredicate(RelationshipOperator.DescendantOf, new IdentityPredicate(IdentityOperator.Equals, Arrays.<ClientServerObject>asList(region_0))), new TypePredicate(TypeOperator.IsNot, InterfaceBoundary.class), new NamePredicate(NameOperator.DoesNotContain, "Domain"))), Query.STANDARD_MODIFIERS));
  
    constrainedStreamPart_0.getInputParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new RelationshipPredicate(RelationshipOperator.DescendantOf, new IdentityPredicate(IdentityOperator.Equals, Arrays.<ClientServerObject>asList(region_0))), new TypePredicate(TypeOperator.IsNot, InterfaceBoundary.class), new NamePredicate(NameOperator.DoesNotContain, "Domain"))), Query.STANDARD_MODIFIERS));
  
    sourceSeed_0.getSeedParts().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, Boundary.class), new RelationshipPredicate(RelationshipOperator.DescendantOf, new IdentityPredicate(IdentityOperator.Equals, Arrays.<ClientServerObject>asList(region_0))), new TypePredicate(TypeOperator.IsNot, InterfaceBoundary.class), new NamePredicate(NameOperator.DoesNotContain, "Domain"))), Query.STANDARD_MODIFIERS));
  
  currentView_0.setInput(new DoubleVector(new double[] {-0.491889878129969, -0.002656430902668391, 0.04623315700510933}), new DoubleVector(new double[] {-0.491889878129969, -0.002656430902668391, -7.020437655897966}), new DoubleVector(new double[] {0.0, 1.0, 0.0}), 1.0314339431004096, 1, 30.0);

  scene_0.printAndWait(resolvePath("..\\Post_Processing\\CF\\Underbody.png"), 1, 1920, 1080, true, false);
  
  }
}
