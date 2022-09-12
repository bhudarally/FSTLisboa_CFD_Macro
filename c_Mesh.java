// Simcenter STAR-CCM+ macro: c_Mesh.java
// Written by Simcenter STAR-CCM+ 15.06.007
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;
import star.resurfacer.*;
import star.prismmesher.*;
import star.base.query.*;
import star.base.neo.*;
import star.base.report.*;
import star.dualmesher.*;
import star.cadmodeler.*;
import star.meshing.*;
import star.surfacewrapper.*;

public class c_Mesh extends StarMacro {

  public void execute() {
    Surface_Wrapper();
	Imprint();
	Automated_Mesh();
  }

  private void Surface_Wrapper() {

	//{ // Surface Wrapper Default controls

    Simulation simulation_0 = 
      getActiveSimulation();
	  
	double Roll = simulation_0.getReportManager().getReport("Roll [deg]").getReportMonitorValue();
	double Yaw = simulation_0.getReportManager().getReport("Yaw [rad]").getReportMonitorValue();
	double CornerRadius = simulation_0.getReportManager().getReport("Corner Radius [m]").getReportMonitorValue(); 
	double SIMIDdouble = simulation_0.getReportManager().getReport("Sim ID").getReportMonitorValue();
	
	int intSIM = (int) SIMIDdouble;
	  
	Units units_0 = 
      ((Units) simulation_0.getUnitsManager().getObject("m"));
	Units units_1 = 
      ((Units) simulation_0.getUnitsManager().getObject("mm"));


    SolidModelPart solidModelPart_0 = 
      ((SolidModelPart) simulation_0.get(SimulationPartManager.class).getPart("Domain"));
    CompositePart compositePart_0 = 
      ((CompositePart) simulation_0.get(SimulationPartManager.class).getPart("FST10_carreira"));

    SurfaceWrapperAutoMeshOperation surfaceWrapperAutoMeshOperation_0 = 
      (SurfaceWrapperAutoMeshOperation) simulation_0.get(MeshOperationManager.class).createSurfaceWrapperAutoMeshOperation(new NeoObjectVector(new Object[] {solidModelPart_0, compositePart_0}), "Surface Wrapper");
    surfaceWrapperAutoMeshOperation_0.setPresentationName("SW - Fluid");

    MeshOperationPart meshOperationPart_0 = 
      ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("Surface Wrapper"));
    meshOperationPart_0.setPresentationName("Fluid");

    surfaceWrapperAutoMeshOperation_0.getDefaultValues().get(BaseSize.class).setValue(0.2);
    surfaceWrapperAutoMeshOperation_0.getDefaultValues().get(BaseSize.class).setUnits(units_0);

    PartsMinimumSurfaceSize partsMinimumSurfaceSize_0 = 
      surfaceWrapperAutoMeshOperation_0.getDefaultValues().get(PartsMinimumSurfaceSize.class);
    partsMinimumSurfaceSize_0.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsMinimumSurfaceSize_0.getAbsoluteSizeValue()).setValue(0.5);
    ((ScalarPhysicalQuantity) partsMinimumSurfaceSize_0.getAbsoluteSizeValue()).setUnits(units_1);
	//}
	
	//{ // Surface Wrapper Custom controls
	
	// SURFACE WRAPPER FLUID 

    CompositePart compositePart_1 = 
      ((CompositePart) compositePart_0.getChildParts().getPart("AR"));

    SurfaceCustomMeshControl surfaceCustomMeshControl_0 = 
      surfaceWrapperAutoMeshOperation_0.getCustomMeshControls().createSurfaceControl();
    surfaceCustomMeshControl_0.setPresentationName("AR");
    surfaceCustomMeshControl_0.getGeometryObjects().setQuery(null);
    surfaceCustomMeshControl_0.getGeometryObjects().setObjects(compositePart_1);
    surfaceCustomMeshControl_0.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);
    surfaceCustomMeshControl_0.getCustomConditions().get(PartsMinimumSurfaceSizeOption.class).setSelected(PartsMinimumSurfaceSizeOption.Type.CUSTOM);
    surfaceCustomMeshControl_0.getCustomConditions().get(PartsSurfaceCurvatureOption.class).setSelected(PartsSurfaceCurvatureOption.Type.CUSTOM_VALUES);

    PartsTargetSurfaceSize partsTargetSurfaceSize_0 = 
      surfaceCustomMeshControl_0.getCustomValues().get(PartsTargetSurfaceSize.class);
    partsTargetSurfaceSize_0.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_0.getAbsoluteSizeValue()).setValue(2.5);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_0.getAbsoluteSizeValue()).setUnits(units_1);

    PartsMinimumSurfaceSize partsMinimumSurfaceSize_1 = 
      surfaceCustomMeshControl_0.getCustomValues().get(PartsMinimumSurfaceSize.class);
    partsMinimumSurfaceSize_1.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsMinimumSurfaceSize_1.getAbsoluteSizeValue()).setValue(0.75);
    ((ScalarPhysicalQuantity) partsMinimumSurfaceSize_1.getAbsoluteSizeValue()).setUnits(units_1);

    SurfaceCurvature surfaceCurvature_0 = 
      surfaceCustomMeshControl_0.getCustomValues().get(SurfaceCurvature.class);
    surfaceCurvature_0.setEnableCurvatureDeviationDist(true);
    surfaceCurvature_0.setNumPointsAroundCircle(50.0);
    surfaceCurvature_0.getCurvatureDeviationDistance().setValue(1.0);
    surfaceCurvature_0.getCurvatureDeviationDistance().setUnits(units_1);

    SurfaceCustomMeshControl surfaceCustomMeshControl_1 = 
      surfaceWrapperAutoMeshOperation_0.getCustomMeshControls().createSurfaceControl();
    surfaceCustomMeshControl_1.setPresentationName("CH");

    CompositePart compositePart_4 = 
      ((CompositePart) compositePart_0.getChildParts().getPart("SU"));
    CompositePart compositePart_3 = 
      ((CompositePart) compositePart_4.getChildParts().getPart("SU_MN_ASSY"));
    CompositePart compositePart_2 = 
      ((CompositePart) compositePart_3.getChildParts().getPart("MONO"));

    surfaceCustomMeshControl_1.getGeometryObjects().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Matches, "CH"), new NamePredicate(NameOperator.Contains, "Driver"), new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "Mono"), new RelationshipPredicate(RelationshipOperator.DescendantOf, new IdentityPredicate(IdentityOperator.Equals, Arrays.<ClientServerObject>asList(compositePart_2))))))), Query.STANDARD_MODIFIERS));
    surfaceCustomMeshControl_1.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);

    PartsTargetSurfaceSize partsTargetSurfaceSize_1 = 
      surfaceCustomMeshControl_1.getCustomValues().get(PartsTargetSurfaceSize.class);
    partsTargetSurfaceSize_1.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_1.getAbsoluteSizeValue()).setValue(5.0);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_1.getAbsoluteSizeValue()).setUnits(units_1);

    SurfaceCustomMeshControl surfaceCustomMeshControl_2 = 
      surfaceWrapperAutoMeshOperation_0.getCustomMeshControls().createSurfaceControl();
    surfaceCustomMeshControl_2.setPresentationName("CH-HR-MH");
    CompositePart compositePart_5 = 
      ((CompositePart) compositePart_0.getChildParts().getPart("CH"));
    surfaceCustomMeshControl_2.getGeometryObjects().setQuery(new Query(new RelationshipPredicate(RelationshipOperator.MajorDescendantOf, new IdentityPredicate(IdentityOperator.Equals, Arrays.<ClientServerObject>asList(compositePart_5))), Query.STANDARD_MODIFIERS));
    surfaceCustomMeshControl_2.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);
    PartsTargetSurfaceSize partsTargetSurfaceSize_2 = 
      surfaceCustomMeshControl_2.getCustomValues().get(PartsTargetSurfaceSize.class);
    partsTargetSurfaceSize_2.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_2.getAbsoluteSizeValue()).setValue(3.0);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_2.getAbsoluteSizeValue()).setUnits(units_1);

    SurfaceCustomMeshControl surfaceCustomMeshControl_3 = 
      surfaceWrapperAutoMeshOperation_0.getCustomMeshControls().createSurfaceControl();
    surfaceCustomMeshControl_3.setPresentationName("CPatch");
    surfaceCustomMeshControl_3.getGeometryObjects().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new RelationshipPredicate(RelationshipOperator.DescendantOf, new IdentityPredicate(IdentityOperator.Equals, Arrays.<ClientServerObject>asList(compositePart_4))), new NamePredicate(NameOperator.Contains, "face2"))), Query.STANDARD_MODIFIERS));
    surfaceCustomMeshControl_3.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);
    PartsTargetSurfaceSize partsTargetSurfaceSize_3 = 
      surfaceCustomMeshControl_3.getCustomValues().get(PartsTargetSurfaceSize.class);
    partsTargetSurfaceSize_3.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_3.getAbsoluteSizeValue()).setValue(2.0);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_3.getAbsoluteSizeValue()).setUnits(units_1);

    SurfaceCustomMeshControl surfaceCustomMeshControl_4 = 
      surfaceWrapperAutoMeshOperation_0.getCustomMeshControls().createSurfaceControl();
    surfaceCustomMeshControl_4.setPresentationName("FW");
    surfaceCustomMeshControl_4.getGeometryObjects().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "FW"), new TypePredicate(TypeOperator.Is, GeometryPart.class))), Query.STANDARD_MODIFIERS));
    surfaceCustomMeshControl_4.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);
    surfaceCustomMeshControl_4.getCustomConditions().get(PartsMinimumSurfaceSizeOption.class).setSelected(PartsMinimumSurfaceSizeOption.Type.CUSTOM);
    PartsTargetSurfaceSize partsTargetSurfaceSize_4 = 
      surfaceCustomMeshControl_4.getCustomValues().get(PartsTargetSurfaceSize.class);
    partsTargetSurfaceSize_4.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_4.getAbsoluteSizeValue()).setValue(2.0);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_4.getAbsoluteSizeValue()).setUnits(units_1);
    PartsMinimumSurfaceSize partsMinimumSurfaceSize_2 = 
      surfaceCustomMeshControl_4.getCustomValues().get(PartsMinimumSurfaceSize.class);
    partsMinimumSurfaceSize_2.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsMinimumSurfaceSize_2.getAbsoluteSizeValue()).setValue(0.3);
    ((ScalarPhysicalQuantity) partsMinimumSurfaceSize_2.getAbsoluteSizeValue()).setUnits(units_1);

    CurveCustomMeshControl curveCustomMeshControl_0 = 
      surfaceWrapperAutoMeshOperation_0.getCustomMeshControls().createCurveControl();
    curveCustomMeshControl_0.setPresentationName("MN - Edge");

    CadPart cadPart_0 = 
      ((CadPart) compositePart_2.getChildParts().getPart("MONO"));

    curveCustomMeshControl_0.getGeometryObjects().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, PartCurve.class), new RelationshipPredicate(RelationshipOperator.DescendantOf, new IdentityPredicate(IdentityOperator.Equals, Arrays.<ClientServerObject>asList(cadPart_0))))), Query.STANDARD_MODIFIERS));
    curveCustomMeshControl_0.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);
    PartsTargetSurfaceSize partsTargetSurfaceSize_5 = 
      curveCustomMeshControl_0.getCustomValues().get(PartsTargetSurfaceSize.class);
    partsTargetSurfaceSize_5.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_5.getAbsoluteSizeValue()).setValue(3.0);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_5.getAbsoluteSizeValue()).setUnits(units_1);

    SurfaceCustomMeshControl surfaceCustomMeshControl_5 = 
      surfaceWrapperAutoMeshOperation_0.getCustomMeshControls().createSurfaceControl();
    surfaceCustomMeshControl_5.setPresentationName("Radiator & Fan");
    surfaceCustomMeshControl_5.getGeometryObjects().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, GeometryPart.class), new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "fan"), new NamePredicate(NameOperator.Contains, "radiator"))))), Query.STANDARD_MODIFIERS));
    surfaceCustomMeshControl_5.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);
    PartsTargetSurfaceSize partsTargetSurfaceSize_6 = 
      surfaceCustomMeshControl_5.getCustomValues().get(PartsTargetSurfaceSize.class);
    partsTargetSurfaceSize_6.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_6.getAbsoluteSizeValue()).setValue(2.0);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_6.getAbsoluteSizeValue()).setUnits(units_1);

    SurfaceCustomMeshControl surfaceCustomMeshControl_6 = 
      surfaceWrapperAutoMeshOperation_0.getCustomMeshControls().createSurfaceControl();
    surfaceCustomMeshControl_6.setPresentationName("SU");
    surfaceCustomMeshControl_6.getGeometryObjects().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new RelationshipPredicate(RelationshipOperator.DescendantOf, new IdentityPredicate(IdentityOperator.Equals, Arrays.<ClientServerObject>asList(compositePart_3))), new TypePredicate(TypeOperator.Is, GeometryPart.class), new NamePredicate(NameOperator.DoesNotContain, "Mono"))), Query.STANDARD_MODIFIERS));
    surfaceCustomMeshControl_6.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);
    PartsTargetSurfaceSize partsTargetSurfaceSize_7 = 
      surfaceCustomMeshControl_6.getCustomValues().get(PartsTargetSurfaceSize.class);
    partsTargetSurfaceSize_7.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_7.getAbsoluteSizeValue()).setValue(3.5);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_7.getAbsoluteSizeValue()).setUnits(units_1);

    SurfaceCustomMeshControl surfaceCustomMeshControl_7 = 
      surfaceWrapperAutoMeshOperation_0.getCustomMeshControls().createSurfaceControl();
    surfaceCustomMeshControl_7.setPresentationName("TE- AR");
    surfaceCustomMeshControl_7.getGeometryObjects().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, PartSurface.class), new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.EndsWith, "TE"), new NamePredicate(NameOperator.Contains, "Gurney"))))), Query.STANDARD_MODIFIERS));
    surfaceCustomMeshControl_7.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);
    surfaceCustomMeshControl_7.getCustomConditions().get(PartsMinimumSurfaceSizeOption.class).setSelected(PartsMinimumSurfaceSizeOption.Type.CUSTOM);
    PartsTargetSurfaceSize partsTargetSurfaceSize_8 = 
      surfaceCustomMeshControl_7.getCustomValues().get(PartsTargetSurfaceSize.class);
    partsTargetSurfaceSize_8.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_8.getAbsoluteSizeValue()).setValue(1.0);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_8.getAbsoluteSizeValue()).setUnits(units_1);
    PartsMinimumSurfaceSize partsMinimumSurfaceSize_3 = 
      surfaceCustomMeshControl_7.getCustomValues().get(PartsMinimumSurfaceSize.class);
    partsMinimumSurfaceSize_3.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsMinimumSurfaceSize_3.getAbsoluteSizeValue()).setValue(0.3);
    ((ScalarPhysicalQuantity) partsMinimumSurfaceSize_3.getAbsoluteSizeValue()).setUnits(units_1);

    SurfaceCustomMeshControl surfaceCustomMeshControl_8 = 
      surfaceWrapperAutoMeshOperation_0.getCustomMeshControls().createSurfaceControl();
    surfaceCustomMeshControl_8.setPresentationName("Wheels");
    surfaceCustomMeshControl_8.getGeometryObjects().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "Rim"), new NamePredicate(NameOperator.Contains, "Tyre"))), Query.STANDARD_MODIFIERS));
    surfaceCustomMeshControl_8.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);
    surfaceCustomMeshControl_8.getCustomConditions().get(PartsMinimumSurfaceSizeOption.class).setSelected(PartsMinimumSurfaceSizeOption.Type.CUSTOM);
    PartsTargetSurfaceSize partsTargetSurfaceSize_9 = 
      surfaceCustomMeshControl_8.getCustomValues().get(PartsTargetSurfaceSize.class);
    partsTargetSurfaceSize_9.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_9.getAbsoluteSizeValue()).setValue(2.0);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_9.getAbsoluteSizeValue()).setUnits(units_1);
    PartsMinimumSurfaceSize partsMinimumSurfaceSize_4 = 
      surfaceCustomMeshControl_8.getCustomValues().get(PartsMinimumSurfaceSize.class);
    partsMinimumSurfaceSize_4.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsMinimumSurfaceSize_4.getAbsoluteSizeValue()).setValue(0.2);
    ((ScalarPhysicalQuantity) partsMinimumSurfaceSize_4.getAbsoluteSizeValue()).setUnits(units_1);

    CurveCustomMeshControl curveCustomMeshControl_1 = 
      surfaceWrapperAutoMeshOperation_0.getCustomMeshControls().createCurveControl();
    curveCustomMeshControl_1.setPresentationName("UB");
    curveCustomMeshControl_1.getGeometryObjects().setQuery(new Query(new NamePredicate(NameOperator.Contains, "underbody"), Query.STANDARD_MODIFIERS));
    curveCustomMeshControl_1.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);
    PartsTargetSurfaceSize partsTargetSurfaceSize_10 = 
      curveCustomMeshControl_1.getCustomValues().get(PartsTargetSurfaceSize.class);
    partsTargetSurfaceSize_10.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_10.getAbsoluteSizeValue()).setValue(2.0);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_10.getAbsoluteSizeValue()).setUnits(units_1);

    PartsOneGroupContactPreventionSet partsOneGroupContactPreventionSet_0 = 
      surfaceWrapperAutoMeshOperation_0.getContactPreventionSet().createPartsOneGroupContactPreventionSet();
    partsOneGroupContactPreventionSet_0.setPresentationName("Ground");
	
	// Contact prevention more fine to prevent FW | Diffuser Back | Lateral Diffuser | Mono | Inner wall from getting mesh glued each other
	// uncomment 1st line -> Faster
	// uncomment 2nd line -> Slower with finer contact prevention
	
    partsOneGroupContactPreventionSet_0.getPartSurfaceGroup().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "Tyre"), new NamePredicate(NameOperator.Contains, "Ground"))), Query.STANDARD_MODIFIERS));
    // partsOneGroupContactPreventionSet_0.getPartSurfaceGroup().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "Tyre"), new NamePredicate(NameOperator.Contains, "FW"), new NamePredicate(NameOperator.Contains, "Ground"), new NamePredicate(NameOperator.Contains, "inner"), new NamePredicate(NameOperator.Contains, "underbody"), new NamePredicate(NameOperator.Contains, "vane"), new NamePredicate(NameOperator.Contains, "bck"), new RelationshipPredicate(RelationshipOperator.DescendantOf, new IdentityPredicate(IdentityOperator.Equals, Arrays.<ClientServerObject>asList(cadPart_0))))), Query.STANDARD_MODIFIERS));
	
	partsOneGroupContactPreventionSet_0.getFloor().setValue(0.5);
    partsOneGroupContactPreventionSet_0.getFloor().setUnits(units_1);

    PartsOneGroupContactPreventionSet partsOneGroupContactPreventionSet_1 = 
      surfaceWrapperAutoMeshOperation_0.getContactPreventionSet().createPartsOneGroupContactPreventionSet();
    partsOneGroupContactPreventionSet_1.setPresentationName("Damper-MN");
    partsOneGroupContactPreventionSet_1.getPartSurfaceGroup().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "DPR"), new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, GeometryPart.class), new NamePredicate(NameOperator.Contains, "Mono"), new RelationshipPredicate(RelationshipOperator.AncestorOf, new IdentityPredicate(IdentityOperator.Equals, Arrays.<ClientServerObject>asList(cadPart_0))))))), Query.STANDARD_MODIFIERS));
    partsOneGroupContactPreventionSet_1.getFloor().setValue(0.2);
    partsOneGroupContactPreventionSet_1.getFloor().setUnits(units_1);

    PartsOneGroupContactPreventionSet partsOneGroupContactPreventionSet_2 = 
      surfaceWrapperAutoMeshOperation_0.getContactPreventionSet().createPartsOneGroupContactPreventionSet();
    partsOneGroupContactPreventionSet_2.setPresentationName("Driver");
    partsOneGroupContactPreventionSet_2.getPartSurfaceGroup().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "VD"), new NamePredicate(NameOperator.Matches, "HR"), new NamePredicate(NameOperator.Contains, "Pad"))), Query.STANDARD_MODIFIERS));
    partsOneGroupContactPreventionSet_2.getFloor().setValue(2.0);
    partsOneGroupContactPreventionSet_2.getFloor().setUnits(units_1);

    PartsOneGroupContactPreventionSet partsOneGroupContactPreventionSet_3 = 
      surfaceWrapperAutoMeshOperation_0.getContactPreventionSet().createPartsOneGroupContactPreventionSet();
    partsOneGroupContactPreventionSet_3.setPresentationName("FW-EndP");
    partsOneGroupContactPreventionSet_3.getPartSurfaceGroup().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "FW"), new TypePredicate(TypeOperator.Is, GeometryPart.class), new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "EP"), new NamePredicate(NameOperator.Contains, "Main"), new NamePredicate(NameOperator.Contains, "Flap"))))), Query.STANDARD_MODIFIERS));
    partsOneGroupContactPreventionSet_3.getFloor().setValue(1.0);
    partsOneGroupContactPreventionSet_3.getFloor().setUnits(units_1);

    PartsOneGroupContactPreventionSet partsOneGroupContactPreventionSet_4 = 
      surfaceWrapperAutoMeshOperation_0.getContactPreventionSet().createPartsOneGroupContactPreventionSet();
    partsOneGroupContactPreventionSet_4.setPresentationName("FW-Supp");
    partsOneGroupContactPreventionSet_4.getPartSurfaceGroup().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, GeometryPart.class), new NamePredicate(NameOperator.Contains, "FW"), new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "Main"), new NamePredicate(NameOperator.Contains, "Mid"), new NamePredicate(NameOperator.Contains, "Supp"))))), Query.STANDARD_MODIFIERS));
    partsOneGroupContactPreventionSet_4.getFloor().setValue(1.0);
    partsOneGroupContactPreventionSet_4.getFloor().setUnits(units_1);

    PartsOneGroupContactPreventionSet partsOneGroupContactPreventionSet_5 = 
      surfaceWrapperAutoMeshOperation_0.getContactPreventionSet().createPartsOneGroupContactPreventionSet();
    partsOneGroupContactPreventionSet_5.setPresentationName("Mono-Diff");
    partsOneGroupContactPreventionSet_5.getPartSurfaceGroup().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "DIFFUSER_BCK"), new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new RelationshipPredicate(RelationshipOperator.DescendantOf, new IdentityPredicate(IdentityOperator.Equals, Arrays.<ClientServerObject>asList(cadPart_0))), new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "Lid"), new NamePredicate(NameOperator.Contains, "Damper"), new NamePredicate(NameOperator.Contains, "1"))))))), Query.STANDARD_MODIFIERS));
    partsOneGroupContactPreventionSet_5.getFloor().setValue(0.25);
    partsOneGroupContactPreventionSet_5.getFloor().setUnits(units_1);

    PartsOneGroupContactPreventionSet partsOneGroupContactPreventionSet_6 = 
      surfaceWrapperAutoMeshOperation_0.getContactPreventionSet().createPartsOneGroupContactPreventionSet();
    partsOneGroupContactPreventionSet_6.setPresentationName("Mono-FW");
    partsOneGroupContactPreventionSet_6.getPartSurfaceGroup().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, GeometryPart.class), new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "Mono"), new NamePredicate(NameOperator.Contains, "FW_Supp"))))), Query.STANDARD_MODIFIERS));
    partsOneGroupContactPreventionSet_6.getFloor().setValue(3.0);
    partsOneGroupContactPreventionSet_6.getFloor().setUnits(units_1);

    PartsOneGroupContactPreventionSet partsOneGroupContactPreventionSet_7 = 
      surfaceWrapperAutoMeshOperation_0.getContactPreventionSet().createPartsOneGroupContactPreventionSet();
    partsOneGroupContactPreventionSet_7.setPresentationName("Mono-SU");
    partsOneGroupContactPreventionSet_7.getPartSurfaceGroup().setQuery(new Query(new NamePredicate(NameOperator.Contains, "SU_MN"), Query.STANDARD_MODIFIERS));
    partsOneGroupContactPreventionSet_7.getFloor().setValue(2.0);
    partsOneGroupContactPreventionSet_7.getFloor().setUnits(units_1);

    CompositePart compositePart_6 = 
      ((CompositePart) compositePart_0.getChildParts().getPart("PT"));
	  
	// SURFACE WRAPPER RADIATOR RIGHT

    SolidModelPart solidModelPart_1 = 
      ((SolidModelPart) compositePart_6.getChildParts().getPart("RADIATOR_R"));

    SurfaceWrapperAutoMeshOperation surfaceWrapperAutoMeshOperation_1 = 
      (SurfaceWrapperAutoMeshOperation) simulation_0.get(MeshOperationManager.class).createSurfaceWrapperAutoMeshOperation(new NeoObjectVector(new Object[] {solidModelPart_1}), "Surface Wrapper");
    surfaceWrapperAutoMeshOperation_1.setPresentationName("SW - Radiator_(R)");

    SurfaceWrapperAutoMesher surfaceWrapperAutoMesher_0 = 
      ((SurfaceWrapperAutoMesher) surfaceWrapperAutoMeshOperation_1.getMeshers().getObject("Surface Wrapper"));
    surfaceWrapperAutoMesher_0.setDoWrapperMeshAlignment(true);

    MeshOperationPart meshOperationPart_1 = 
      ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("Surface Wrapper"));
    meshOperationPart_1.setPresentationName("Radiator_(R)");

    surfaceWrapperAutoMeshOperation_1.getDefaultValues().get(BaseSize.class).setValue(5.0);
    surfaceWrapperAutoMeshOperation_1.getDefaultValues().get(BaseSize.class).setUnits(units_1);

    PartsTargetSurfaceSize partsTargetSurfaceSize_11 = 
      surfaceWrapperAutoMeshOperation_1.getDefaultValues().get(PartsTargetSurfaceSize.class);
    partsTargetSurfaceSize_11.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_11.getAbsoluteSizeValue()).setValue(2.0);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_11.getAbsoluteSizeValue()).setUnits(units_1);
	
	// SURFACE WRAPPER RADIATOR LEFT
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    simulation_0.get(MeshOperationManager.class).createCopyOfMeshOperation("star.surfacewrapper.SurfaceWrapperAutoMeshOperation", "Copy of SW - Radiator_(R)");

    SurfaceWrapperAutoMeshOperation surfaceWrapperAutoMeshOperation_2 = 
      ((SurfaceWrapperAutoMeshOperation) simulation_0.get(MeshOperationManager.class).getObject("Copy of SW - Radiator_(R)"));
    surfaceWrapperAutoMeshOperation_2.copyProperties(surfaceWrapperAutoMeshOperation_1);
    surfaceWrapperAutoMeshOperation_2.setPresentationName("SW - Radiator_(L)");
    MeshOperationPart meshOperationPart_2 = 
      ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("Copy of SW - Radiator_(R)"));
    meshOperationPart_2.setPresentationName("Radiator_(L)");
    surfaceWrapperAutoMeshOperation_2.getInputGeometryObjects().setQuery(null);

    SolidModelPart solidModelPart_2 = 
      ((SolidModelPart) compositePart_6.getChildParts().getPart("RADIATOR_L"));
    surfaceWrapperAutoMeshOperation_2.getInputGeometryObjects().setObjects(solidModelPart_2);
	}
	// SURFACE WRAPPER FAN RIGHT

    simulation_0.get(MeshOperationManager.class).createCopyOfMeshOperation("star.surfacewrapper.SurfaceWrapperAutoMeshOperation", "Copy of SW - Radiator_(L)");

    SurfaceWrapperAutoMeshOperation surfaceWrapperAutoMeshOperation_3 = 
      ((SurfaceWrapperAutoMeshOperation) simulation_0.get(MeshOperationManager.class).getObject("Copy of SW - Radiator_(L)"));
    surfaceWrapperAutoMeshOperation_3.copyProperties(surfaceWrapperAutoMeshOperation_1);
    surfaceWrapperAutoMeshOperation_3.setPresentationName("SW - FanDuct_(R)");
    MeshOperationPart meshOperationPart_3 = 
      ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("Copy of SW - Radiator_(L)"));
    meshOperationPart_3.setPresentationName("FanDuct_(R)");
    surfaceWrapperAutoMeshOperation_3.getInputGeometryObjects().setQuery(null);
	
    SolidModelPart solidModelPart_3 = 
      ((SolidModelPart) compositePart_6.getChildParts().getPart("FAN_DUCT_R"));
    surfaceWrapperAutoMeshOperation_3.getInputGeometryObjects().setObjects(solidModelPart_3);
	
	// SURFACE WRAPPER FAN LEFT
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {

    simulation_0.get(MeshOperationManager.class).createCopyOfMeshOperation("star.surfacewrapper.SurfaceWrapperAutoMeshOperation", "Copy of SW - FanDuct_(R)");

    SurfaceWrapperAutoMeshOperation surfaceWrapperAutoMeshOperation_4 = 
      ((SurfaceWrapperAutoMeshOperation) simulation_0.get(MeshOperationManager.class).getObject("Copy of SW - FanDuct_(R)"));
    surfaceWrapperAutoMeshOperation_4.copyProperties(surfaceWrapperAutoMeshOperation_3);
    surfaceWrapperAutoMeshOperation_4.setPresentationName("SW - FanDuct_(L)");
    MeshOperationPart meshOperationPart_4 = 
      ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("Copy of SW - FanDuct_(R)"));
    meshOperationPart_4.setPresentationName("FanDuct_(L)");
    surfaceWrapperAutoMeshOperation_4.getInputGeometryObjects().setQuery(null);

    SolidModelPart solidModelPart_4 = 
      ((SolidModelPart) compositePart_6.getChildParts().getPart("FAN_DUCT_L"));
    surfaceWrapperAutoMeshOperation_4.getInputGeometryObjects().setObjects(solidModelPart_4);	
	}
	
	surfaceWrapperAutoMeshOperation_0.execute();
	surfaceWrapperAutoMeshOperation_1.execute();
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
	SurfaceWrapperAutoMeshOperation surfaceWrapperAutoMeshOperation_2 = 
      ((SurfaceWrapperAutoMeshOperation) simulation_0.get(MeshOperationManager.class).getObject("SW - Radiator_(L)"));
	surfaceWrapperAutoMeshOperation_2.execute();}
	surfaceWrapperAutoMeshOperation_3.execute();
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
	SurfaceWrapperAutoMeshOperation surfaceWrapperAutoMeshOperation_4 = 
      ((SurfaceWrapperAutoMeshOperation) simulation_0.get(MeshOperationManager.class).getObject("SW - FanDuct_(L)"));
	surfaceWrapperAutoMeshOperation_4.execute();}
	
	// simulation_0.saveState(resolvePath("..\\Simulation\\MC0"+intSIM+".sim"));
	
	//}
	}
  
  private void Imprint() {
	  
	//{ 1st Imprint try

	double tolerance = 5.0E-4;

	Simulation simulation_0 = 
		getActiveSimulation();
  
	double Roll = simulation_0.getReportManager().getReport("Roll [deg]").getReportMonitorValue();
	double Yaw = simulation_0.getReportManager().getReport("Yaw [rad]").getReportMonitorValue();
	double CornerRadius = simulation_0.getReportManager().getReport("Corner Radius [m]").getReportMonitorValue(); 
	double SIMIDdouble = simulation_0.getReportManager().getReport("Sim ID").getReportMonitorValue();  
	 

	MeshOperationPart meshOperationPart_0 = 
		((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("FanDuct_(R)"));
	MeshOperationPart meshOperationPart_2 = 
		((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("Fluid"));
	MeshOperationPart meshOperationPart_3 = 
		((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("Radiator_(R)"));
	
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
		
	MeshOperationPart meshOperationPart_1 = 
      ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("FanDuct_(L)"));
	MeshOperationPart meshOperationPart_4 = 
		((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("Radiator_(L)"));
		
	ImprintPartsOperation imprintPartsOperation_0 = 
		(ImprintPartsOperation) simulation_0.get(MeshOperationManager.class).createImprintPartsOperation(new NeoObjectVector(new Object[] {meshOperationPart_0, meshOperationPart_1, meshOperationPart_2, meshOperationPart_3, meshOperationPart_4}));
	}
	
	if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
	ImprintPartsOperation imprintPartsOperation_0 = 
		(ImprintPartsOperation) simulation_0.get(MeshOperationManager.class).createImprintPartsOperation(new NeoObjectVector(new Object[] {meshOperationPart_0, meshOperationPart_2, meshOperationPart_3}));
	}
	
	ImprintPartsOperation imprintPartsOperation_0 = 
      ((ImprintPartsOperation) simulation_0.get(MeshOperationManager.class).getObject("Imprint"));
	  
	imprintPartsOperation_0.setLinkOutputPartName(false);
	imprintPartsOperation_0.getTolerance().setValue(tolerance);
	imprintPartsOperation_0.execute();

	PartContact partContact_0 = 
	simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_2,meshOperationPart_0);
	PartContact partContact_2 = 
	simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_2,meshOperationPart_3);
	PartContact partContact_4 = 
	simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_0,meshOperationPart_3);
	
	
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
	MeshOperationPart meshOperationPart_1 = 
      ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("FanDuct_(L)"));
	MeshOperationPart meshOperationPart_4 = 
		((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("Radiator_(L)"));
	
	PartContact partContact_1 = 
	simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_2,meshOperationPart_1);
	PartContact partContact_3 = 
	simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_2,meshOperationPart_4);
	PartContact partContact_5 = 
	simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_1,meshOperationPart_4); 
	
	PartSurface partSurface_0 = 
	((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.FAN_DUCT_L.FAN__L"));
	PartSurface partSurface_1 = 
	((PartSurface) meshOperationPart_1.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.FAN_DUCT_L.FAN__L"));
	PartSurface partSurface_2 = 
	((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.FAN_DUCT_L.DUCT__L"));
	PartSurface partSurface_3 = 
	((PartSurface) meshOperationPart_1.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.FAN_DUCT_L.DUCT__L"));
	PartSurface partSurface_4 = 
	((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.RADIATOR_L.RADIATOR_SHROUD_L"));
	PartSurface partSurface_5 = 
	((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.RADIATOR_L.RADIATOR_SHROUD_L"));
	PartSurface partSurface_6 = 
	((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.RADIATOR_L.RADIATOR_INLET_L"));
	PartSurface partSurface_7 = 
	((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.RADIATOR_L.RADIATOR_INLET_L"));
	PartSurface partSurface_16 = 
	((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.RADIATOR_L.RADIATOR_OUTLET_L"));
	PartSurface partSurface_17 = 
	((PartSurface) meshOperationPart_1.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.FAN_DUCT_L.DUCT_INLET_L"));
	try {
	InPlacePartSurfaceContact inPlacePartSurfaceContact_1 = 
	((InPlacePartSurfaceContact) partContact_1.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_4,partSurface_3));
	partContact_1.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_1); 
	}
	catch (Exception e){}
	try {
	InPlacePartSurfaceContact inPlacePartSurfaceContact_3 = 
	((InPlacePartSurfaceContact) partContact_3.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_2,partSurface_5));
	partContact_3.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_3);  
	}
	catch (Exception e){}
	try {
	InPlacePartSurfaceContact inPlacePartSurfaceContact_4 = 
	((InPlacePartSurfaceContact) partContact_1.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_1,partSurface_2));
	partContact_1.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_4);  
	}
	catch (Exception e){}
	}
	
	PartSurface partSurface_8 = 
	((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.FAN_DUCT_R.FAN__R"));
	PartSurface partSurface_9 = 
	((PartSurface) meshOperationPart_0.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.FAN_DUCT_R.FAN__R"));
	PartSurface partSurface_10 = 
	((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.FAN_DUCT_R.DUCT__R"));
	PartSurface partSurface_11 = 
	((PartSurface) meshOperationPart_0.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.FAN_DUCT_R.DUCT__R"));
	PartSurface partSurface_12 = 
	((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.RADIATOR_R.RADIATOR_SHROUD_R"));
	PartSurface partSurface_13 = 
	((PartSurface) meshOperationPart_3.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.RADIATOR_R.RADIATOR_SHROUD_R"));
	PartSurface partSurface_14 = 
	((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.RADIATOR_R.RADIATOR_INLET_R"));
	PartSurface partSurface_15 = 
	((PartSurface) meshOperationPart_3.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.RADIATOR_R.RADIATOR_INLET_R"));
	PartSurface partSurface_18 = 
	((PartSurface) meshOperationPart_3.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.RADIATOR_R.RADIATOR_OUTLET_R"));
	PartSurface partSurface_19 = 
	((PartSurface) meshOperationPart_0.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.FAN_DUCT_R.DUCT_INLET_R"));

	try {
	InPlacePartSurfaceContact inPlacePartSurfaceContact_0 = 
	((InPlacePartSurfaceContact) partContact_0.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_11,partSurface_12));
	partContact_0.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_0); 
	}
	catch(Exception e){}

	try {
	InPlacePartSurfaceContact inPlacePartSurfaceContact_2 = 
	((InPlacePartSurfaceContact) partContact_2.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_10,partSurface_13));
	partContact_2.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_2);  
	}
	catch (Exception e){}

	try {
	InPlacePartSurfaceContact inPlacePartSurfaceContact_5 = 
	((InPlacePartSurfaceContact) partContact_0.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_10,partSurface_9));
	partContact_0.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_5);  
	}
	catch (Exception e){}

	try {
	
		
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
	MeshOperationPart meshOperationPart_1 = 
      ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("FanDuct_(L)"));
	MeshOperationPart meshOperationPart_4 = 
		((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("Radiator_(L)"));
		
	PartContact partContact_10 = 
	simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_2,meshOperationPart_1);	
	PartContact partContact_11 = 
	simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_4,meshOperationPart_2);
	PartContact partContact_14 = 
	simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_4,meshOperationPart_1);
	
	PartSurface partSurface_0 = 
	((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.FAN_DUCT_L.FAN__L"));
	PartSurface partSurface_1 = 
	((PartSurface) meshOperationPart_1.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.FAN_DUCT_L.FAN__L"));
	PartSurface partSurface_2 = 
	((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.FAN_DUCT_L.DUCT__L"));
	PartSurface partSurface_3 = 
	((PartSurface) meshOperationPart_1.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.FAN_DUCT_L.DUCT__L"));
	PartSurface partSurface_4 = 
	((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.RADIATOR_L.RADIATOR_SHROUD_L"));
	PartSurface partSurface_5 = 
	((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.RADIATOR_L.RADIATOR_SHROUD_L"));
	PartSurface partSurface_6 = 
	((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.RADIATOR_L.RADIATOR_INLET_L"));
	PartSurface partSurface_7 = 
	((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.RADIATOR_L.RADIATOR_INLET_L"));
	PartSurface partSurface_16 = 
	((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.RADIATOR_L.RADIATOR_OUTLET_L"));
	PartSurface partSurface_17 = 
	((PartSurface) meshOperationPart_1.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.FAN_DUCT_L.DUCT_INLET_L"));	
		
	// Fan (L)
	InPlacePartSurfaceContact inPlacePartSurfaceContact_10 = 
	((InPlacePartSurfaceContact) partContact_10.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_0,partSurface_1));
	MetaData metaData_10 = 
	inPlacePartSurfaceContact_10.getMetaData();

	// Duct (L)
	InPlacePartSurfaceContact inPlacePartSurfaceContact_11 = 
	((InPlacePartSurfaceContact) partContact_10.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_2,partSurface_3));
	MetaData metaData_11 = 
	inPlacePartSurfaceContact_11.getMetaData();

	// Rad_Shroud (L)
	InPlacePartSurfaceContact inPlacePartSurfaceContact_12 = 
	((InPlacePartSurfaceContact) partContact_11.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_4,partSurface_5));
	MetaData metaData_12 = 
	inPlacePartSurfaceContact_12.getMetaData();

	// Rad_Inlet (L)
	InPlacePartSurfaceContact inPlacePartSurfaceContact_13 = 
	((InPlacePartSurfaceContact) partContact_11.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_6,partSurface_7));
	MetaData metaData_13 = 
	inPlacePartSurfaceContact_13.getMetaData();
	
	// Rad_outlet/Fan_inlet (L)
	InPlacePartSurfaceContact inPlacePartSurfaceContact_18 = 
	((InPlacePartSurfaceContact) partContact_14.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_16,partSurface_17));
	MetaData metaData_18 = 
	inPlacePartSurfaceContact_18.getMetaData();
		
	}
		
	PartContact partContact_12 = 
	simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_0,meshOperationPart_2);
	PartContact partContact_13 = 
	simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_2,meshOperationPart_3);
	PartContact partContact_15 = 
	simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_0,meshOperationPart_3);

	// Fan (R)
	InPlacePartSurfaceContact inPlacePartSurfaceContact_14 = 
	((InPlacePartSurfaceContact) partContact_12.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_8,partSurface_9));
	MetaData metaData_14 = 
	inPlacePartSurfaceContact_14.getMetaData();

	// Duct (R)
	InPlacePartSurfaceContact inPlacePartSurfaceContact_15 = 
	((InPlacePartSurfaceContact) partContact_12.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_10,partSurface_11));
	MetaData metaData_15 = 
	inPlacePartSurfaceContact_15.getMetaData();

	// Rad_Shroud (R)
	InPlacePartSurfaceContact inPlacePartSurfaceContact_16 = 
	((InPlacePartSurfaceContact) partContact_13.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_12,partSurface_13));
	MetaData metaData_16 = 
	inPlacePartSurfaceContact_16.getMetaData();

	// Rad_Inlet (R)
	InPlacePartSurfaceContact inPlacePartSurfaceContact_17 = 
	((InPlacePartSurfaceContact) partContact_13.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_14,partSurface_15));
	MetaData metaData_17 = 
	inPlacePartSurfaceContact_17.getMetaData();

	// Rad_outlet/Fan_inlet (R)
	InPlacePartSurfaceContact inPlacePartSurfaceContact_19 = 
	((InPlacePartSurfaceContact) partContact_15.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_18,partSurface_19));
	MetaData metaData_19 = 
	inPlacePartSurfaceContact_19.getMetaData();

	}
	//}
	
	//{ 2nd | 3rd | 4th | 5th Imprint try
	
	catch (Exception ef){

	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
	MeshOperationPart meshOperationPart_1 = 
      ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("FanDuct_(L)"));
	MeshOperationPart meshOperationPart_4 = 
		((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("Radiator_(L)"));
		
	PartContact partContact_10 = 
	simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_2,meshOperationPart_1);
	PartContact partContact_11 = 
	simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_4,meshOperationPart_2);
	PartContact partContact_14 = 
	simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_4,meshOperationPart_1);
	simulation_0.get(PartContactManager.class).removeObjects(partContact_10, partContact_11, partContact_14);
	
	}
	
	PartContact partContact_12 = 
	simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_0,meshOperationPart_2);
	PartContact partContact_13 = 
	simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_2,meshOperationPart_3);
	PartContact partContact_15 = 
	simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_0,meshOperationPart_3);
	simulation_0.get(PartContactManager.class).removeObjects(partContact_12, partContact_13, partContact_15);

	tolerance = 1.0E-3;
	imprintPartsOperation_0.getTolerance().setValue(tolerance);
	imprintPartsOperation_0.execute();
	
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
	MeshOperationPart meshOperationPart_1 = 
      ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("FanDuct_(L)"));
	MeshOperationPart meshOperationPart_4 = 
		((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("Radiator_(L)"));

	PartContact partContact_100 = 
	simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_2,meshOperationPart_1);
	PartContact partContact_101 = 
	simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_4,meshOperationPart_2);
	PartContact partContact_104 = 
	simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_4,meshOperationPart_1);
	
	PartSurface partSurface_0 = 
	((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.FAN_DUCT_L.FAN__L"));
	PartSurface partSurface_1 = 
	((PartSurface) meshOperationPart_1.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.FAN_DUCT_L.FAN__L"));
	PartSurface partSurface_2 = 
	((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.FAN_DUCT_L.DUCT__L"));
	PartSurface partSurface_3 = 
	((PartSurface) meshOperationPart_1.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.FAN_DUCT_L.DUCT__L"));
	PartSurface partSurface_4 = 
	((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.RADIATOR_L.RADIATOR_SHROUD_L"));
	PartSurface partSurface_5 = 
	((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.RADIATOR_L.RADIATOR_SHROUD_L"));
	PartSurface partSurface_6 = 
	((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.RADIATOR_L.RADIATOR_INLET_L"));
	PartSurface partSurface_7 = 
	((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.RADIATOR_L.RADIATOR_INLET_L"));
	PartSurface partSurface_16 = 
	((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.RADIATOR_L.RADIATOR_OUTLET_L"));
	PartSurface partSurface_17 = 
	((PartSurface) meshOperationPart_1.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.FAN_DUCT_L.DUCT_INLET_L"));	
	
	try {
	InPlacePartSurfaceContact inPlacePartSurfaceContact_1 = 
	((InPlacePartSurfaceContact) partContact_100.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_4,partSurface_3));
	partContact_100.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_1); 
	}
	catch (Exception eeee){}
	
	try {
	InPlacePartSurfaceContact inPlacePartSurfaceContact_3 = 
	((InPlacePartSurfaceContact) partContact_101.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_2,partSurface_5));
	partContact_101.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_3);  
	}
	catch (Exception eeeeee){}
	
	try {
	InPlacePartSurfaceContact inPlacePartSurfaceContact_4 = 
	((InPlacePartSurfaceContact) partContact_100.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_1,partSurface_2));
	partContact_100.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_4);  
	}
	catch (Exception eeeeeee){}
	}
	
	PartContact partContact_102 = 
	simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_0,meshOperationPart_2);
	PartContact partContact_103 = 
	simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_2,meshOperationPart_3);
	PartContact partContact_105 = 
	simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_0,meshOperationPart_3);

	try {
	InPlacePartSurfaceContact inPlacePartSurfaceContact_0 = 
	((InPlacePartSurfaceContact) partContact_102.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_11,partSurface_12));
	partContact_102.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_0); 
	}
	catch (Exception eee){}

	try {
	InPlacePartSurfaceContact inPlacePartSurfaceContact_2 = 
	((InPlacePartSurfaceContact) partContact_103.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_10,partSurface_13));
	partContact_103.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_2);  
	}
	catch (Exception eeeee){}

	try {
	InPlacePartSurfaceContact inPlacePartSurfaceContact_5 = 
	((InPlacePartSurfaceContact) partContact_102.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_10,partSurface_9));
	partContact_102.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_5);  
	}
	catch (Exception eeeeeeee){}

	try {
		
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
	MeshOperationPart meshOperationPart_1 = 
      ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("FanDuct_(L)"));
	MeshOperationPart meshOperationPart_4 = 
		((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("Radiator_(L)"));

	PartContact partContact_100 = 
	simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_2,meshOperationPart_1);
	PartContact partContact_101 = 
	simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_4,meshOperationPart_2);
	PartContact partContact_104 = 
	simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_4,meshOperationPart_1);
	
	PartSurface partSurface_0 = 
	((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.FAN_DUCT_L.FAN__L"));
	PartSurface partSurface_1 = 
	((PartSurface) meshOperationPart_1.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.FAN_DUCT_L.FAN__L"));
	PartSurface partSurface_2 = 
	((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.FAN_DUCT_L.DUCT__L"));
	PartSurface partSurface_3 = 
	((PartSurface) meshOperationPart_1.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.FAN_DUCT_L.DUCT__L"));
	PartSurface partSurface_4 = 
	((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.RADIATOR_L.RADIATOR_SHROUD_L"));
	PartSurface partSurface_5 = 
	((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.RADIATOR_L.RADIATOR_SHROUD_L"));
	PartSurface partSurface_6 = 
	((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.RADIATOR_L.RADIATOR_INLET_L"));
	PartSurface partSurface_7 = 
	((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.RADIATOR_L.RADIATOR_INLET_L"));
	PartSurface partSurface_16 = 
	((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.RADIATOR_L.RADIATOR_OUTLET_L"));
	PartSurface partSurface_17 = 
	((PartSurface) meshOperationPart_1.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.FAN_DUCT_L.DUCT_INLET_L"));	
	
	 
	// Fan (L)
	InPlacePartSurfaceContact inPlacePartSurfaceContact_10 = 
	((InPlacePartSurfaceContact) partContact_100.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_0,partSurface_1));
	MetaData metaData_10 = 
	inPlacePartSurfaceContact_10.getMetaData();

	// Duct (L)
	InPlacePartSurfaceContact inPlacePartSurfaceContact_11 = 
	((InPlacePartSurfaceContact) partContact_100.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_2,partSurface_3));
	MetaData metaData_11 = 
	inPlacePartSurfaceContact_11.getMetaData();

	// Rad_Shroud (L)
	InPlacePartSurfaceContact inPlacePartSurfaceContact_12 = 
	((InPlacePartSurfaceContact) partContact_101.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_4,partSurface_5));
	MetaData metaData_12 = 
	inPlacePartSurfaceContact_12.getMetaData();

	// Rad_Inlet (L)
	InPlacePartSurfaceContact inPlacePartSurfaceContact_13 = 
	((InPlacePartSurfaceContact) partContact_101.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_6,partSurface_7));
	MetaData metaData_13 = 
	inPlacePartSurfaceContact_13.getMetaData();
	
	// Rad_outlet/Fan_inlet (L)
	InPlacePartSurfaceContact inPlacePartSurfaceContact_18 = 
	((InPlacePartSurfaceContact) partContact_104.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_16,partSurface_17));
	MetaData metaData_18 = 
	inPlacePartSurfaceContact_18.getMetaData();
	}

	// Fan (R)
	InPlacePartSurfaceContact inPlacePartSurfaceContact_14 = 
	((InPlacePartSurfaceContact) partContact_102.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_8,partSurface_9));
	MetaData metaData_14 = 
	inPlacePartSurfaceContact_14.getMetaData();

	// Duct (R)
	InPlacePartSurfaceContact inPlacePartSurfaceContact_15 = 
	((InPlacePartSurfaceContact) partContact_102.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_10,partSurface_11));
	MetaData metaData_15 = 
	inPlacePartSurfaceContact_15.getMetaData();

	// Rad_Shroud (R)
	InPlacePartSurfaceContact inPlacePartSurfaceContact_16 = 
	((InPlacePartSurfaceContact) partContact_103.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_12,partSurface_13));
	MetaData metaData_16 = 
	inPlacePartSurfaceContact_16.getMetaData();

	// Rad_Inlet (R)
	InPlacePartSurfaceContact inPlacePartSurfaceContact_17 = 
	((InPlacePartSurfaceContact) partContact_103.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_14,partSurface_15));
	MetaData metaData_17 = 
	inPlacePartSurfaceContact_17.getMetaData();

	// Rad_outlet/Fan_inlet (R)
	InPlacePartSurfaceContact inPlacePartSurfaceContact_19 = 
	((InPlacePartSurfaceContact) partContact_105.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_18,partSurface_19));
	MetaData metaData_19 = 
	inPlacePartSurfaceContact_19.getMetaData();

	}
	
	//}
	
	//{ 3rd | 4th | 5th Imprint try
	
	catch (Exception eff){
		
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
	MeshOperationPart meshOperationPart_1 = 
      ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("FanDuct_(L)"));
	MeshOperationPart meshOperationPart_4 = 
		((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("Radiator_(L)"));
		
	PartContact partContact_100 = 
	simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_2,meshOperationPart_1);
	PartContact partContact_101 = 
	simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_4,meshOperationPart_2);
	PartContact partContact_104 = 
	simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_4,meshOperationPart_1);
	simulation_0.get(PartContactManager.class).removeObjects(partContact_100, partContact_101, partContact_104);

	}	

	simulation_0.get(PartContactManager.class).removeObjects(partContact_102, partContact_103, partContact_105);

	tolerance = 1.0E-4;
	imprintPartsOperation_0.getTolerance().setValue(tolerance);
	imprintPartsOperation_0.execute();
	
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
	MeshOperationPart meshOperationPart_1 = 
      ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("FanDuct_(L)"));
	MeshOperationPart meshOperationPart_4 = 
		((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("Radiator_(L)"));


	PartContact partContact_1000 = 
	simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_2,meshOperationPart_1);
	PartContact partContact_1001 = 
	simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_4,meshOperationPart_2);
	PartContact partContact_1004 = 
	simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_4,meshOperationPart_1);
	
	PartSurface partSurface_0 = 
	((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.FAN_DUCT_L.FAN__L"));
	PartSurface partSurface_1 = 
	((PartSurface) meshOperationPart_1.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.FAN_DUCT_L.FAN__L"));
	PartSurface partSurface_2 = 
	((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.FAN_DUCT_L.DUCT__L"));
	PartSurface partSurface_3 = 
	((PartSurface) meshOperationPart_1.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.FAN_DUCT_L.DUCT__L"));
	PartSurface partSurface_4 = 
	((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.RADIATOR_L.RADIATOR_SHROUD_L"));
	PartSurface partSurface_5 = 
	((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.RADIATOR_L.RADIATOR_SHROUD_L"));
	PartSurface partSurface_6 = 
	((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.RADIATOR_L.RADIATOR_INLET_L"));
	PartSurface partSurface_7 = 
	((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.RADIATOR_L.RADIATOR_INLET_L"));
	PartSurface partSurface_16 = 
	((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.RADIATOR_L.RADIATOR_OUTLET_L"));
	PartSurface partSurface_17 = 
	((PartSurface) meshOperationPart_1.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.FAN_DUCT_L.DUCT_INLET_L"));	
	
	try {
	InPlacePartSurfaceContact inPlacePartSurfaceContact_1 = 
	((InPlacePartSurfaceContact) partContact_1000.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_4,partSurface_3));
	partContact_1000.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_1); 
	}
	catch (Exception eeee){}
	
	try {
	InPlacePartSurfaceContact inPlacePartSurfaceContact_3 = 
	((InPlacePartSurfaceContact) partContact_1001.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_2,partSurface_5));
	partContact_1001.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_3);  
	}
	catch (Exception eeeeee){}
	
	try {
	InPlacePartSurfaceContact inPlacePartSurfaceContact_4 = 
	((InPlacePartSurfaceContact) partContact_1000.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_1,partSurface_2));
	partContact_1000.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_4);  
	}
	catch (Exception eeeeeee){}
	
	}
	
	PartContact partContact_1002 = 
	simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_0,meshOperationPart_2);
	PartContact partContact_1003 = 
	simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_2,meshOperationPart_3);
	PartContact partContact_1005 = 
	simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_0,meshOperationPart_3);

	try {
	InPlacePartSurfaceContact inPlacePartSurfaceContact_0 = 
	((InPlacePartSurfaceContact) partContact_1002.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_11,partSurface_12));
	partContact_1002.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_0); 
	}
	catch (Exception eee){}

	try {
	InPlacePartSurfaceContact inPlacePartSurfaceContact_2 = 
	((InPlacePartSurfaceContact) partContact_1003.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_10,partSurface_13));
	partContact_1003.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_2);  
	}
	catch (Exception eeeee){}

	try {
	InPlacePartSurfaceContact inPlacePartSurfaceContact_5 = 
	((InPlacePartSurfaceContact) partContact_1002.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_10,partSurface_9));
	partContact_1002.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_5);  
	}
	catch (Exception eeeeeeeeeeeeee){}

	try {
		
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
	MeshOperationPart meshOperationPart_1 = 
      ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("FanDuct_(L)"));
	MeshOperationPart meshOperationPart_4 = 
		((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("Radiator_(L)"));
		
	PartContact partContact_1000 = 
	simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_2,meshOperationPart_1);
	PartContact partContact_1001 = 
	simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_4,meshOperationPart_2);
	PartContact partContact_1004 = 
	simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_4,meshOperationPart_1);
	
	PartSurface partSurface_0 = 
	((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.FAN_DUCT_L.FAN__L"));
	PartSurface partSurface_1 = 
	((PartSurface) meshOperationPart_1.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.FAN_DUCT_L.FAN__L"));
	PartSurface partSurface_2 = 
	((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.FAN_DUCT_L.DUCT__L"));
	PartSurface partSurface_3 = 
	((PartSurface) meshOperationPart_1.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.FAN_DUCT_L.DUCT__L"));
	PartSurface partSurface_4 = 
	((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.RADIATOR_L.RADIATOR_SHROUD_L"));
	PartSurface partSurface_5 = 
	((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.RADIATOR_L.RADIATOR_SHROUD_L"));
	PartSurface partSurface_6 = 
	((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.RADIATOR_L.RADIATOR_INLET_L"));
	PartSurface partSurface_7 = 
	((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.RADIATOR_L.RADIATOR_INLET_L"));
	PartSurface partSurface_16 = 
	((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.RADIATOR_L.RADIATOR_OUTLET_L"));
	PartSurface partSurface_17 = 
	((PartSurface) meshOperationPart_1.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.FAN_DUCT_L.DUCT_INLET_L"));

	// Fan (L)
	InPlacePartSurfaceContact inPlacePartSurfaceContact_10 = 
	((InPlacePartSurfaceContact) partContact_1000.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_0,partSurface_1));
	MetaData metaData_10 = 
	inPlacePartSurfaceContact_10.getMetaData();

	// Duct (L)
	InPlacePartSurfaceContact inPlacePartSurfaceContact_11 = 
	((InPlacePartSurfaceContact) partContact_1000.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_2,partSurface_3));
	MetaData metaData_11 = 
	inPlacePartSurfaceContact_11.getMetaData();

	// Rad_Shroud (L)
	InPlacePartSurfaceContact inPlacePartSurfaceContact_12 = 
	((InPlacePartSurfaceContact) partContact_1001.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_4,partSurface_5));
	MetaData metaData_12 = 
	inPlacePartSurfaceContact_12.getMetaData();

	// Rad_Inlet (L)
	InPlacePartSurfaceContact inPlacePartSurfaceContact_13 = 
	((InPlacePartSurfaceContact) partContact_1001.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_6,partSurface_7));
	MetaData metaData_13 = 
	inPlacePartSurfaceContact_13.getMetaData();

	// Rad_outlet/Fan_inlet (L)
	InPlacePartSurfaceContact inPlacePartSurfaceContact_18 = 
	((InPlacePartSurfaceContact) partContact_1004.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_16,partSurface_17));
	MetaData metaData_18 = 
	inPlacePartSurfaceContact_18.getMetaData();
	}

	// Fan (R)
	InPlacePartSurfaceContact inPlacePartSurfaceContact_14 = 
	((InPlacePartSurfaceContact) partContact_1002.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_8,partSurface_9));
	MetaData metaData_14 = 
	inPlacePartSurfaceContact_14.getMetaData();

	// Duct (R)
	InPlacePartSurfaceContact inPlacePartSurfaceContact_15 = 
	((InPlacePartSurfaceContact) partContact_1002.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_10,partSurface_11));
	MetaData metaData_15 = 
	inPlacePartSurfaceContact_15.getMetaData();

	// Rad_Shroud (R)
	InPlacePartSurfaceContact inPlacePartSurfaceContact_16 = 
	((InPlacePartSurfaceContact) partContact_1003.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_12,partSurface_13));
	MetaData metaData_16 = 
	inPlacePartSurfaceContact_16.getMetaData();

	// Rad_Inlet (R)
	InPlacePartSurfaceContact inPlacePartSurfaceContact_17 = 
	((InPlacePartSurfaceContact) partContact_1003.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_14,partSurface_15));
	MetaData metaData_17 = 
	inPlacePartSurfaceContact_17.getMetaData();

	// Rad_outlet/Fan_inlet (R)
	InPlacePartSurfaceContact inPlacePartSurfaceContact_19 = 
	((InPlacePartSurfaceContact) partContact_1005.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_18,partSurface_19));
	MetaData metaData_19 = 
	inPlacePartSurfaceContact_19.getMetaData();

	}
	//}
	
	//{ 4th | 5th Imprint try
	
	catch (Exception efff){
		
		
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
	MeshOperationPart meshOperationPart_1 = 
      ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("FanDuct_(L)"));
	MeshOperationPart meshOperationPart_4 = 
		((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("Radiator_(L)"));
		
	PartContact partContact_1000 = 
	simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_2,meshOperationPart_1);
	PartContact partContact_1001 = 
	simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_4,meshOperationPart_2);
	PartContact partContact_1004 = 
	simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_4,meshOperationPart_1);
	simulation_0.get(PartContactManager.class).removeObjects(partContact_1000, partContact_1001, partContact_1004);

	}	

	simulation_0.get(PartContactManager.class).removeObjects(partContact_1002, partContact_1003, partContact_1005);		

	tolerance = 5.0E-5;
	imprintPartsOperation_0.getTolerance().setValue(tolerance);
	imprintPartsOperation_0.execute();
	
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
	MeshOperationPart meshOperationPart_1 = 
      ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("FanDuct_(L)"));
	MeshOperationPart meshOperationPart_4 = 
		((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("Radiator_(L)"));
	
	PartContact partContact_10000 = 
	simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_2,meshOperationPart_1);
	PartContact partContact_10001 = 
	simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_4,meshOperationPart_2);
	PartContact partContact_10004 = 
	simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_4,meshOperationPart_1);
	
	PartSurface partSurface_0 = 
	((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.FAN_DUCT_L.FAN__L"));
	PartSurface partSurface_1 = 
	((PartSurface) meshOperationPart_1.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.FAN_DUCT_L.FAN__L"));
	PartSurface partSurface_2 = 
	((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.FAN_DUCT_L.DUCT__L"));
	PartSurface partSurface_3 = 
	((PartSurface) meshOperationPart_1.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.FAN_DUCT_L.DUCT__L"));
	PartSurface partSurface_4 = 
	((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.RADIATOR_L.RADIATOR_SHROUD_L"));
	PartSurface partSurface_5 = 
	((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.RADIATOR_L.RADIATOR_SHROUD_L"));
	PartSurface partSurface_6 = 
	((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.RADIATOR_L.RADIATOR_INLET_L"));
	PartSurface partSurface_7 = 
	((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.RADIATOR_L.RADIATOR_INLET_L"));
	PartSurface partSurface_16 = 
	((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.RADIATOR_L.RADIATOR_OUTLET_L"));
	PartSurface partSurface_17 = 
	((PartSurface) meshOperationPart_1.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.FAN_DUCT_L.DUCT_INLET_L"));	
	
	try {
	InPlacePartSurfaceContact inPlacePartSurfaceContact_1 = 
	((InPlacePartSurfaceContact) partContact_10000.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_4,partSurface_3));
	partContact_10000.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_1); 
	}
	catch (Exception eeee){}
	
	try {
	InPlacePartSurfaceContact inPlacePartSurfaceContact_3 = 
	((InPlacePartSurfaceContact) partContact_10001.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_2,partSurface_5));
	partContact_10001.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_3);  
	}
	catch (Exception eeeeee){}
	
	try {
	InPlacePartSurfaceContact inPlacePartSurfaceContact_4 = 
	((InPlacePartSurfaceContact) partContact_10000.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_1,partSurface_2));
	partContact_10000.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_4);  
	}
	catch (Exception eeeeeee){}
	
	}
	
	PartContact partContact_10002 = 
	simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_0,meshOperationPart_2);
	PartContact partContact_10003 = 
	simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_2,meshOperationPart_3);
	PartContact partContact_10005 = 
	simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_0,meshOperationPart_3);

	try {
	InPlacePartSurfaceContact inPlacePartSurfaceContact_0 = 
	((InPlacePartSurfaceContact) partContact_10002.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_11,partSurface_12));
	partContact_10002.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_0); 
	}
	catch (Exception eee){}

	try {
	InPlacePartSurfaceContact inPlacePartSurfaceContact_2 = 
	((InPlacePartSurfaceContact) partContact_10003.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_10,partSurface_13));
	partContact_10003.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_2);  
	}
	catch (Exception eeeee){}

	try {
	InPlacePartSurfaceContact inPlacePartSurfaceContact_5 = 
	((InPlacePartSurfaceContact) partContact_10002.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_10,partSurface_9));
	partContact_10002.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_5);  
	}
	catch (Exception eeeeeeeeeeeeeeeeeeee){}

	try {
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
	MeshOperationPart meshOperationPart_1 = 
      ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("FanDuct_(L)"));
	MeshOperationPart meshOperationPart_4 = 
		((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("Radiator_(L)"));	
		
	PartContact partContact_10000 = 
	simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_2,meshOperationPart_1);
	PartContact partContact_10001 = 
	simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_4,meshOperationPart_2);
	PartContact partContact_10004 = 
	simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_4,meshOperationPart_1);	
	
	PartSurface partSurface_0 = 
	((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.FAN_DUCT_L.FAN__L"));
	PartSurface partSurface_1 = 
	((PartSurface) meshOperationPart_1.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.FAN_DUCT_L.FAN__L"));
	PartSurface partSurface_2 = 
	((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.FAN_DUCT_L.DUCT__L"));
	PartSurface partSurface_3 = 
	((PartSurface) meshOperationPart_1.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.FAN_DUCT_L.DUCT__L"));
	PartSurface partSurface_4 = 
	((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.RADIATOR_L.RADIATOR_SHROUD_L"));
	PartSurface partSurface_5 = 
	((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.RADIATOR_L.RADIATOR_SHROUD_L"));
	PartSurface partSurface_6 = 
	((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.RADIATOR_L.RADIATOR_INLET_L"));
	PartSurface partSurface_7 = 
	((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.RADIATOR_L.RADIATOR_INLET_L"));
	PartSurface partSurface_16 = 
	((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.RADIATOR_L.RADIATOR_OUTLET_L"));
	PartSurface partSurface_17 = 
	((PartSurface) meshOperationPart_1.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.FAN_DUCT_L.DUCT_INLET_L"));

	// Fan (L)
	InPlacePartSurfaceContact inPlacePartSurfaceContact_10 = 
	((InPlacePartSurfaceContact) partContact_10000.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_0,partSurface_1));
	MetaData metaData_10 = 
	inPlacePartSurfaceContact_10.getMetaData();

	// Duct (L)
	InPlacePartSurfaceContact inPlacePartSurfaceContact_11 = 
	((InPlacePartSurfaceContact) partContact_10000.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_2,partSurface_3));
	MetaData metaData_11 = 
	inPlacePartSurfaceContact_11.getMetaData();

	// Rad_Shroud (L)
	InPlacePartSurfaceContact inPlacePartSurfaceContact_12 = 
	((InPlacePartSurfaceContact) partContact_10001.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_4,partSurface_5));
	MetaData metaData_12 = 
	inPlacePartSurfaceContact_12.getMetaData();

	// Rad_Inlet (L)
	InPlacePartSurfaceContact inPlacePartSurfaceContact_13 = 
	((InPlacePartSurfaceContact) partContact_10001.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_6,partSurface_7));
	MetaData metaData_13 = 
	inPlacePartSurfaceContact_13.getMetaData();
	
	// Rad_outlet/Fan_inlet (L)
	InPlacePartSurfaceContact inPlacePartSurfaceContact_18 = 
	((InPlacePartSurfaceContact) partContact_10004.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_16,partSurface_17));
	MetaData metaData_18 = 
	inPlacePartSurfaceContact_18.getMetaData();

	}
	
	// Fan (R)
	InPlacePartSurfaceContact inPlacePartSurfaceContact_14 = 
	((InPlacePartSurfaceContact) partContact_10002.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_8,partSurface_9));
	MetaData metaData_14 = 
	inPlacePartSurfaceContact_14.getMetaData();

	// Duct (R)
	InPlacePartSurfaceContact inPlacePartSurfaceContact_15 = 
	((InPlacePartSurfaceContact) partContact_10002.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_10,partSurface_11));
	MetaData metaData_15 = 
	inPlacePartSurfaceContact_15.getMetaData();

	// Rad_Shroud (R)
	InPlacePartSurfaceContact inPlacePartSurfaceContact_16 = 
	((InPlacePartSurfaceContact) partContact_10003.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_12,partSurface_13));
	MetaData metaData_16 = 
	inPlacePartSurfaceContact_16.getMetaData();

	// Rad_Inlet (R)
	InPlacePartSurfaceContact inPlacePartSurfaceContact_17 = 
	((InPlacePartSurfaceContact) partContact_10003.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_14,partSurface_15));
	MetaData metaData_17 = 
	inPlacePartSurfaceContact_17.getMetaData();

	// Rad_outlet/Fan_inlet (R)
	InPlacePartSurfaceContact inPlacePartSurfaceContact_19 = 
	((InPlacePartSurfaceContact) partContact_10005.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_18,partSurface_19));
	MetaData metaData_19 = 
	inPlacePartSurfaceContact_19.getMetaData();
	}
	//}
	
	//{ 5th Imprint try
	
	catch (Exception effffff){
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
	MeshOperationPart meshOperationPart_1 = 
      ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("FanDuct_(L)"));
	MeshOperationPart meshOperationPart_4 = 
		((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("Radiator_(L)"));
		
	PartContact partContact_10000 = 
	simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_2,meshOperationPart_1);
	PartContact partContact_10001 = 
	simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_4,meshOperationPart_2);
	PartContact partContact_10004 = 
	simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_4,meshOperationPart_1);
	simulation_0.get(PartContactManager.class).removeObjects(partContact_10000, partContact_10001, partContact_10004);
	}

	simulation_0.get(PartContactManager.class).removeObjects(partContact_10002, partContact_10003, partContact_10005);

	tolerance = 1.0E-5;
	imprintPartsOperation_0.getTolerance().setValue(tolerance);
	imprintPartsOperation_0.execute();
	
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
	MeshOperationPart meshOperationPart_1 = 
      ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("FanDuct_(L)"));
	MeshOperationPart meshOperationPart_4 = 
		((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("Radiator_(L)"));
	
	PartContact partContact_100000 = 
	simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_2,meshOperationPart_1);
	PartContact partContact_100001 = 
	simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_4,meshOperationPart_2);
	PartContact partContact_100004 = 
	simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_4,meshOperationPart_1);
	
	PartSurface partSurface_0 = 
	((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.FAN_DUCT_L.FAN__L"));
	PartSurface partSurface_1 = 
	((PartSurface) meshOperationPart_1.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.FAN_DUCT_L.FAN__L"));
	PartSurface partSurface_2 = 
	((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.FAN_DUCT_L.DUCT__L"));
	PartSurface partSurface_3 = 
	((PartSurface) meshOperationPart_1.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.FAN_DUCT_L.DUCT__L"));
	PartSurface partSurface_4 = 
	((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.RADIATOR_L.RADIATOR_SHROUD_L"));
	PartSurface partSurface_5 = 
	((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.RADIATOR_L.RADIATOR_SHROUD_L"));
	PartSurface partSurface_6 = 
	((PartSurface) meshOperationPart_2.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.RADIATOR_L.RADIATOR_INLET_L"));
	PartSurface partSurface_7 = 
	((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.RADIATOR_L.RADIATOR_INLET_L"));
	PartSurface partSurface_16 = 
	((PartSurface) meshOperationPart_4.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.RADIATOR_L.RADIATOR_OUTLET_L"));
	PartSurface partSurface_17 = 
	((PartSurface) meshOperationPart_1.getPartSurfaceManager().getPartSurface("FST10_carreira.PT.FAN_DUCT_L.DUCT_INLET_L"));	
	
	try {
	InPlacePartSurfaceContact inPlacePartSurfaceContact_1 = 
	((InPlacePartSurfaceContact) partContact_100000.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_4,partSurface_3));
	partContact_100000.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_1); 
	}
	catch (Exception eeee){}
	
	try {
	InPlacePartSurfaceContact inPlacePartSurfaceContact_3 = 
	((InPlacePartSurfaceContact) partContact_100001.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_2,partSurface_5));
	partContact_100001.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_3);  
	}
	catch (Exception eeeeee){}
	
	try {
	InPlacePartSurfaceContact inPlacePartSurfaceContact_4 = 
	((InPlacePartSurfaceContact) partContact_100000.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_1,partSurface_2));
	partContact_100000.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_4);  
	}
	catch (Exception eeeeeee){}
	
	}
	
	PartContact partContact_100002 = 
	simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_0,meshOperationPart_2);
	PartContact partContact_100003 = 
	simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_2,meshOperationPart_3);
	PartContact partContact_100005 = 
	simulation_0.get(PartContactManager.class).getPartContact(meshOperationPart_0,meshOperationPart_3);

	try {
	InPlacePartSurfaceContact inPlacePartSurfaceContact_0 = 
	((InPlacePartSurfaceContact) partContact_100002.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_11,partSurface_12));
	partContact_100002.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_0); 
	}
	catch (Exception eee){}

	try {
	InPlacePartSurfaceContact inPlacePartSurfaceContact_2 = 
	((InPlacePartSurfaceContact) partContact_100003.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_10,partSurface_13));
	partContact_100003.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_2);  
	}
	catch (Exception eeeee){}

	try {
	InPlacePartSurfaceContact inPlacePartSurfaceContact_5 = 
	((InPlacePartSurfaceContact) partContact_100002.get(PartSurfaceContactManager.class).getPartSurfaceContact(partSurface_10,partSurface_9));
	partContact_100002.get(PartSurfaceContactManager.class).remove(inPlacePartSurfaceContact_5);  
	}
	catch (Exception eeeeeeeeeeeeeeeeeeeeeeeeee){}
	//}
	}
	}
	}
}
	}
   
  private void Automated_Mesh() {
	  
	//{ // AssigningSWToRegions 
	
	Simulation simulation_0 = 
      getActiveSimulation();
	  
	double Roll = simulation_0.getReportManager().getReport("Roll [deg]").getReportMonitorValue();
	double Yaw = simulation_0.getReportManager().getReport("Yaw [rad]").getReportMonitorValue();
	double CoGX = simulation_0.getReportManager().getReport("CoGX").getReportMonitorValue();
	double CoGZ = simulation_0.getReportManager().getReport("CoGZ").getReportMonitorValue();
	double CornerRadius = simulation_0.getReportManager().getReport("Corner Radius [m]").getReportMonitorValue();  
	double SIMIDdouble = simulation_0.getReportManager().getReport("Sim ID").getReportMonitorValue();
	
	int intSIM = (int) SIMIDdouble;	
	
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    MeshOperationPart meshOperationPart_0 = 
      ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("FanDuct_(L)"));
	}
    MeshOperationPart meshOperationPart_1 = 
      ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("FanDuct_(R)"));

    MeshOperationPart meshOperationPart_2 = 
      ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("Fluid"));
	  
	if ( CornerRadius != 0 || Roll != 0 || Yaw != 0) {
    MeshOperationPart meshOperationPart_3 = 
      ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("Radiator_(L)"));
	}
	
    MeshOperationPart meshOperationPart_4 = 
      ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("Radiator_(R)"));

	if ( CornerRadius == 0 && Roll == 0 && Yaw == 0) {
	simulation_0.getRegionManager().newRegionsFromParts(new NeoObjectVector(new Object[] {meshOperationPart_1, meshOperationPart_2, meshOperationPart_4}), "OneRegionPerPart", null, "OneBoundaryPerPartSurface", null, "OneFeatureCurve", null, RegionManager.CreateInterfaceMode.BOUNDARY);	
	AutoMeshOperation autoMeshOperation_0 = 
      simulation_0.get(MeshOperationManager.class).createAutoMeshOperation(new StringVector(new String[] {"star.resurfacer.ResurfacerAutoMesher", "star.resurfacer.AutomaticSurfaceRepairAutoMesher", "star.dualmesher.DualAutoMesher", "star.prismmesher.PrismAutoMesher"}), new NeoObjectVector(new Object[] {meshOperationPart_1, meshOperationPart_2, meshOperationPart_4}));
  
	} else {
	MeshOperationPart meshOperationPart_0 = 
      ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("FanDuct_(L)"));
	  
	MeshOperationPart meshOperationPart_3 = 
      ((MeshOperationPart) simulation_0.get(SimulationPartManager.class).getPart("Radiator_(L)"));
	 
    simulation_0.getRegionManager().newRegionsFromParts(new NeoObjectVector(new Object[] {meshOperationPart_0, meshOperationPart_1, meshOperationPart_2, meshOperationPart_3, meshOperationPart_4}), "OneRegionPerPart", null, "OneBoundaryPerPartSurface", null, "OneFeatureCurve", null, RegionManager.CreateInterfaceMode.BOUNDARY);
    AutoMeshOperation autoMeshOperation_0 = 
      simulation_0.get(MeshOperationManager.class).createAutoMeshOperation(new StringVector(new String[] {"star.resurfacer.ResurfacerAutoMesher", "star.resurfacer.AutomaticSurfaceRepairAutoMesher", "star.dualmesher.DualAutoMesher", "star.prismmesher.PrismAutoMesher"}), new NeoObjectVector(new Object[] {meshOperationPart_0, meshOperationPart_1, meshOperationPart_2, meshOperationPart_3, meshOperationPart_4}));
    }
	AutoMeshOperation autoMeshOperation_0 = 
      ((AutoMeshOperation) simulation_0.get(MeshOperationManager.class).getObject("Automated Mesh"));
	
	autoMeshOperation_0.setMeshPartByPart(true);
    autoMeshOperation_0.getMesherParallelModeOption().setSelected(MesherParallelModeOption.Type.PARALLEL);
    autoMeshOperation_0.setPresentationName("Mesh");
	//}
  
	//{ // Default values Automated Mesh   
	  
	Units units_0 = 
      simulation_0.getUnitsManager().getPreferredUnits(new IntVector(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));
    Units units_1 = 
      simulation_0.getUnitsManager().getInternalUnits(new IntVector(new int[] {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));
	Units units_2 = 
      ((Units) simulation_0.getUnitsManager().getObject("mm"));

    PrismAutoMesher prismAutoMesher_0 = 
      ((PrismAutoMesher) autoMeshOperation_0.getMeshers().getObject("Prism Layer Mesher"));
    prismAutoMesher_0.getPrismStretchingOption().setSelected(PrismStretchingOption.Type.WALL_THICKNESS);
    prismAutoMesher_0.setGapFillPercentage(49.0);
    prismAutoMesher_0.setLayerChoppingPercentage(75.0);

    autoMeshOperation_0.getDefaultValues().get(BaseSize.class).setDefinition("0.35*(pow(${Ri},1/3))");

    PartsMinimumSurfaceSize partsMinimumSurfaceSize_0 = 
      autoMeshOperation_0.getDefaultValues().get(PartsMinimumSurfaceSize.class);
    partsMinimumSurfaceSize_0.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsMinimumSurfaceSize_0.getAbsoluteSizeValue()).setDefinition("0.0013*(pow(${Ri},1/3))");

    SurfaceGrowthRate surfaceGrowthRate_0 = 
      autoMeshOperation_0.getDefaultValues().get(SurfaceGrowthRate.class);
    surfaceGrowthRate_0.setGrowthRateOption(SurfaceGrowthRate.GrowthRateOption.USER_SPECIFIED);
    surfaceGrowthRate_0.getGrowthRateScalar().setValue(1.35);
    surfaceGrowthRate_0.getGrowthRateScalar().setUnits(units_0);

    NumPrismLayers numPrismLayers_0 = 
      autoMeshOperation_0.getDefaultValues().get(NumPrismLayers.class);
    IntegerValue integerValue_0 = 
      numPrismLayers_0.getNumLayersValue();
    integerValue_0.getQuantity().setDefinition("11/(pow(${Ri},1/3))");

    autoMeshOperation_0.getDefaultValues().get(PrismWallThickness.class).setDefinition("2.0E-5*(pow(${Ri},1/3))");

    PrismThickness prismThickness_0 = 
      autoMeshOperation_0.getDefaultValues().get(PrismThickness.class);
    prismThickness_0.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) prismThickness_0.getAbsoluteSizeValue()).setValue(6.0);
    ((ScalarPhysicalQuantity) prismThickness_0.getAbsoluteSizeValue()).setUnits(units_2);

    PartsTetPolyGrowthRate partsTetPolyGrowthRate_0 = 
      autoMeshOperation_0.getDefaultValues().get(PartsTetPolyGrowthRate.class);
    partsTetPolyGrowthRate_0.setGrowthRate(1.25);
	//}
	
	//{ // AR  controls 
	
	//AR - Diff

    SurfaceCustomMeshControl surfaceCustomMeshControl_0 = 
      autoMeshOperation_0.getCustomMeshControls().createSurfaceControl();
    surfaceCustomMeshControl_0.setPresentationName("AR - Diff");
    surfaceCustomMeshControl_0.getGeometryObjects().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "vane"), new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "ub"), new NamePredicate(NameOperator.Contains, "lower"))))), Query.STANDARD_MODIFIERS));
    surfaceCustomMeshControl_0.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);
    surfaceCustomMeshControl_0.getCustomConditions().get(PartsMinimumSurfaceSizeOption.class).setSelected(PartsMinimumSurfaceSizeOption.Type.CUSTOM);
    surfaceCustomMeshControl_0.getCustomConditions().get(PartsSurfaceCurvatureOption.class).setSelected(PartsSurfaceCurvatureOption.Type.CUSTOM_VALUES);
	
    PartsCustomizePrismMesh partsCustomizePrismMesh_0 = 
      surfaceCustomMeshControl_0.getCustomConditions().get(PartsCustomizePrismMesh.class);
    partsCustomizePrismMesh_0.getCustomPrismOptions().setSelected(PartsCustomPrismsOption.Type.CUSTOMIZE);

    PartsCustomizePrismMeshControls partsCustomizePrismMeshControls_0 = 
      partsCustomizePrismMesh_0.getCustomPrismControls();
    partsCustomizePrismMeshControls_0.setCustomizeNumLayers(true);
    partsCustomizePrismMeshControls_0.setCustomizeTotalThickness(true);

    PartsTargetSurfaceSize partsTargetSurfaceSize_0 = 
      surfaceCustomMeshControl_0.getCustomValues().get(PartsTargetSurfaceSize.class);
    partsTargetSurfaceSize_0.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_0.getAbsoluteSizeValue()).setDefinition("0.004*(pow(${Ri},1/3))");

    NumPrismLayers numPrismLayers_1 = 
      surfaceCustomMeshControl_0.getCustomValues().get(CustomPrismValuesManager.class).get(NumPrismLayers.class);
    IntegerValue integerValue_1 = 
      numPrismLayers_1.getNumLayersValue();
    integerValue_1.getQuantity().setDefinition("18/(pow(${Ri},1/3))");

    PrismThickness prismThickness_1 = 
      surfaceCustomMeshControl_0.getCustomValues().get(CustomPrismValuesManager.class).get(PrismThickness.class);
    prismThickness_1.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) prismThickness_1.getAbsoluteSizeValue()).setValue(6.0);
    ((ScalarPhysicalQuantity) prismThickness_1.getAbsoluteSizeValue()).setUnits(units_2);

    PartsMinimumSurfaceSize partsMinimumSurfaceSize_1 = 
      surfaceCustomMeshControl_0.getCustomValues().get(PartsMinimumSurfaceSize.class);
    partsMinimumSurfaceSize_1.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsMinimumSurfaceSize_1.getAbsoluteSizeValue()).setDefinition("7.5E-4*(pow(${Ri},1/3))");

    SurfaceCurvature surfaceCurvature_0 = 
      surfaceCustomMeshControl_0.getCustomValues().get(SurfaceCurvature.class);
    surfaceCurvature_0.setEnableCurvatureDeviationDist(true);
    surfaceCurvature_0.setNumPointsAroundCircle(50.0);
    surfaceCurvature_0.getCurvatureDeviationDistance().setValue(1.0);
    surfaceCurvature_0.getCurvatureDeviationDistance().setUnits(units_2);
	
	//AR - FW controls

    SurfaceCustomMeshControl surfaceCustomMeshControl_1 = 
      autoMeshOperation_0.getCustomMeshControls().createSurfaceControl();
    surfaceCustomMeshControl_1.setPresentationName("AR - FW");
    surfaceCustomMeshControl_1.getGeometryObjects().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "fw"), new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "lower"), new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.EndsWith, "TE"), new NamePredicate(NameOperator.DoesNotContain, "EP"))), new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "EP"), new NamePredicate(NameOperator.DoesNotContain, "TE"))))))), Query.STANDARD_MODIFIERS));
    surfaceCustomMeshControl_1.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);
    surfaceCustomMeshControl_1.getCustomConditions().get(PartsMinimumSurfaceSizeOption.class).setSelected(PartsMinimumSurfaceSizeOption.Type.CUSTOM);
    surfaceCustomMeshControl_1.getCustomConditions().get(PartsSurfaceCurvatureOption.class).setSelected(PartsSurfaceCurvatureOption.Type.CUSTOM_VALUES);

    PartsCustomizePrismMesh partsCustomizePrismMesh_1 = 
      surfaceCustomMeshControl_1.getCustomConditions().get(PartsCustomizePrismMesh.class);
    partsCustomizePrismMesh_1.getCustomPrismOptions().setSelected(PartsCustomPrismsOption.Type.CUSTOMIZE);

    PartsCustomizePrismMeshControls partsCustomizePrismMeshControls_1 = 
      partsCustomizePrismMesh_1.getCustomPrismControls();
    partsCustomizePrismMeshControls_1.setCustomizeNumLayers(true);
    partsCustomizePrismMeshControls_1.setCustomizeTotalThickness(true);

    PartsTargetSurfaceSize partsTargetSurfaceSize_1 = 
      surfaceCustomMeshControl_1.getCustomValues().get(PartsTargetSurfaceSize.class);
    partsTargetSurfaceSize_1.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_1.getAbsoluteSizeValue()).setDefinition("0.004*(pow(${Ri},1/3))");

    NumPrismLayers numPrismLayers_2 = 
      surfaceCustomMeshControl_1.getCustomValues().get(CustomPrismValuesManager.class).get(NumPrismLayers.class);
    IntegerValue integerValue_2 = 
      numPrismLayers_2.getNumLayersValue();
    integerValue_2.getQuantity().setDefinition("18/(pow(${Ri},1/3))");

    PrismThickness prismThickness_2 = 
      surfaceCustomMeshControl_1.getCustomValues().get(CustomPrismValuesManager.class).get(PrismThickness.class);
    prismThickness_2.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) prismThickness_2.getAbsoluteSizeValue()).setValue(4.0);
    ((ScalarPhysicalQuantity) prismThickness_2.getAbsoluteSizeValue()).setUnits(units_2);

    PartsMinimumSurfaceSize partsMinimumSurfaceSize_2 = 
      surfaceCustomMeshControl_1.getCustomValues().get(PartsMinimumSurfaceSize.class);
    partsMinimumSurfaceSize_2.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsMinimumSurfaceSize_2.getAbsoluteSizeValue()).setDefinition("7.5E-4*(pow(${Ri},1/3))");

    SurfaceCurvature surfaceCurvature_1 = 
      surfaceCustomMeshControl_1.getCustomValues().get(SurfaceCurvature.class);
    surfaceCurvature_1.setEnableCurvatureDeviationDist(true);
    surfaceCurvature_1.setNumPointsAroundCircle(50.0);
    surfaceCurvature_1.getCurvatureDeviationDistance().setValue(1.0);
    surfaceCurvature_1.getCurvatureDeviationDistance().setUnits(units_2);
	
	//AR - FW EP TE 

    SurfaceCustomMeshControl surfaceCustomMeshControl_2 = 
      autoMeshOperation_0.getCustomMeshControls().createSurfaceControl();
    surfaceCustomMeshControl_2.copyProperties(surfaceCustomMeshControl_1);
    surfaceCustomMeshControl_2.setPresentationName("AR - FW EP TE");
    surfaceCustomMeshControl_2.getGeometryObjects().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "EP"), new NamePredicate(NameOperator.EndsWith, "TE"))), Query.STANDARD_MODIFIERS));

    PartsMinimumSurfaceSize partsMinimumSurfaceSize_3 = 
      surfaceCustomMeshControl_2.getCustomValues().get(PartsMinimumSurfaceSize.class);
    ((ScalarPhysicalQuantity) partsMinimumSurfaceSize_3.getAbsoluteSizeValue()).setDefinition("4.5E-4*(pow(${Ri},1/3))");
	
	//AR - RW

    SurfaceCustomMeshControl surfaceCustomMeshControl_3 = 
      autoMeshOperation_0.getCustomMeshControls().createSurfaceControl();
    surfaceCustomMeshControl_3.copyProperties(surfaceCustomMeshControl_1);
    surfaceCustomMeshControl_3.setPresentationName("AR - RW");
    surfaceCustomMeshControl_3.getGeometryObjects().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "RW"), new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "Lower"), new NamePredicate(NameOperator.Contains, "Gurney"), new NamePredicate(NameOperator.EndsWith, "TE"))))), Query.STANDARD_MODIFIERS));

    PartsCustomizePrismMesh partsCustomizePrismMesh_2 = 
      surfaceCustomMeshControl_3.getCustomConditions().get(PartsCustomizePrismMesh.class);

    PartsCustomizePrismMeshControls partsCustomizePrismMeshControls_2 = 
      partsCustomizePrismMesh_2.getCustomPrismControls();
    partsCustomizePrismMeshControls_2.setCustomizeTotalThickness(false);

    PartsTargetSurfaceSize partsTargetSurfaceSize_2 = 
      surfaceCustomMeshControl_3.getCustomValues().get(PartsTargetSurfaceSize.class);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_2.getAbsoluteSizeValue()).setDefinition("0.005*(pow(${Ri},1/3))");

    NumPrismLayers numPrismLayers_3 = 
      surfaceCustomMeshControl_3.getCustomValues().get(CustomPrismValuesManager.class).get(NumPrismLayers.class);
    IntegerValue integerValue_3 = 
      numPrismLayers_3.getNumLayersValue();
    integerValue_3.getQuantity().setDefinition("12/(pow(${Ri},1/3))");
	
	//AR - RW EP

    SurfaceCustomMeshControl surfaceCustomMeshControl_4 = 
      autoMeshOperation_0.getCustomMeshControls().createSurfaceControl();
    surfaceCustomMeshControl_4.setPresentationName("AR - RW EP");
    surfaceCustomMeshControl_4.getGeometryObjects().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "RW"), new NamePredicate(NameOperator.Contains, "Endplate"))), Query.STANDARD_MODIFIERS));
    surfaceCustomMeshControl_4.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);

    PartsCustomizePrismMesh partsCustomizePrismMesh_3 = 
      surfaceCustomMeshControl_4.getCustomConditions().get(PartsCustomizePrismMesh.class);
    partsCustomizePrismMesh_3.getCustomPrismOptions().setSelected(PartsCustomPrismsOption.Type.CUSTOMIZE);

    PartsCustomizePrismMeshControls partsCustomizePrismMeshControls_3 = 
      partsCustomizePrismMesh_3.getCustomPrismControls();
    partsCustomizePrismMeshControls_3.setCustomizeNumLayers(true);
    partsCustomizePrismMeshControls_3.setCustomizeTotalThickness(true);

    PartsTargetSurfaceSize partsTargetSurfaceSize_3 = 
      surfaceCustomMeshControl_4.getCustomValues().get(PartsTargetSurfaceSize.class);
    partsTargetSurfaceSize_3.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_3.getAbsoluteSizeValue()).setDefinition("0.007*(pow(${Ri},1/3))");

    NumPrismLayers numPrismLayers_4 = 
      surfaceCustomMeshControl_4.getCustomValues().get(CustomPrismValuesManager.class).get(NumPrismLayers.class);
    IntegerValue integerValue_4 = 
      numPrismLayers_4.getNumLayersValue();
    integerValue_4.getQuantity().setDefinition("6/(pow(${Ri},1/3))");

    PrismThickness prismThickness_3 = 
      surfaceCustomMeshControl_4.getCustomValues().get(CustomPrismValuesManager.class).get(PrismThickness.class);
    prismThickness_3.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) prismThickness_3.getAbsoluteSizeValue()).setValue(3.0);
    ((ScalarPhysicalQuantity) prismThickness_3.getAbsoluteSizeValue()).setUnits(units_2);
	
	//AR - SE

    SurfaceCustomMeshControl surfaceCustomMeshControl_5 = 
      autoMeshOperation_0.getCustomMeshControls().createSurfaceControl();
    surfaceCustomMeshControl_5.copyProperties(surfaceCustomMeshControl_1);
    surfaceCustomMeshControl_5.setPresentationName("AR - SE");
    surfaceCustomMeshControl_5.getGeometryObjects().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "SE"), new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "lower"), new NamePredicate(NameOperator.EndsWith, "TE"))))), Query.STANDARD_MODIFIERS));

    PartsTargetSurfaceSize partsTargetSurfaceSize_4 = 
      surfaceCustomMeshControl_5.getCustomValues().get(PartsTargetSurfaceSize.class);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_4.getAbsoluteSizeValue()).setDefinition("0.005*(pow(${Ri},1/3))");

    NumPrismLayers numPrismLayers_5 = 
      surfaceCustomMeshControl_5.getCustomValues().get(CustomPrismValuesManager.class).get(NumPrismLayers.class);
    IntegerValue integerValue_5 = 
      numPrismLayers_5.getNumLayersValue();
    integerValue_5.getQuantity().setDefinition("8/(pow(${Ri},1/3))");
	
	//AR - SE EP Bck

    SurfaceCustomMeshControl surfaceCustomMeshControl_6 = 
      autoMeshOperation_0.getCustomMeshControls().createSurfaceControl();
    surfaceCustomMeshControl_6.setPresentationName("AR - SE EP Bck");
    surfaceCustomMeshControl_6.getGeometryObjects().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "SE"), new NamePredicate(NameOperator.Contains, "BHND"))), Query.STANDARD_MODIFIERS));
    surfaceCustomMeshControl_6.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);

    PartsCustomizePrismMesh partsCustomizePrismMesh_4 = 
      surfaceCustomMeshControl_6.getCustomConditions().get(PartsCustomizePrismMesh.class);
    partsCustomizePrismMesh_4.getCustomPrismOptions().setSelected(PartsCustomPrismsOption.Type.DISABLE);

    PartsTargetSurfaceSize partsTargetSurfaceSize_5 = 
      surfaceCustomMeshControl_6.getCustomValues().get(PartsTargetSurfaceSize.class);
    partsTargetSurfaceSize_5.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_5.getAbsoluteSizeValue()).setDefinition("0.008*(pow(${Ri},1/3))");
	
	//AR - Upper Diff

    SurfaceCustomMeshControl surfaceCustomMeshControl_7 = 
      autoMeshOperation_0.getCustomMeshControls().createSurfaceControl();
    surfaceCustomMeshControl_7.copyProperties(surfaceCustomMeshControl_0);
    surfaceCustomMeshControl_7.setPresentationName("AR - Upper Diff");
    surfaceCustomMeshControl_7.getGeometryObjects().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.DoesNotContain, "vane"), new NamePredicate(NameOperator.DoesNotContain, "lower"), new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "UB_"), new NamePredicate(NameOperator.Contains, "Underbody"))))), Query.STANDARD_MODIFIERS));

    PartsTargetSurfaceSize partsTargetSurfaceSize_6 = 
      surfaceCustomMeshControl_7.getCustomValues().get(PartsTargetSurfaceSize.class);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_6.getAbsoluteSizeValue()).setDefinition("0.007*(pow(${Ri},1/3))");
	
	//AR - Upper FW

    SurfaceCustomMeshControl surfaceCustomMeshControl_8 = 
      autoMeshOperation_0.getCustomMeshControls().createSurfaceControl();
    surfaceCustomMeshControl_8.copyProperties(surfaceCustomMeshControl_1);
    surfaceCustomMeshControl_8.setPresentationName("AR - Upper FW");
    surfaceCustomMeshControl_8.getGeometryObjects().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "fw"), new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "supp"), new NamePredicate(NameOperator.Contains, "Midplate"), new NamePredicate(NameOperator.Contains, "upper"))))), Query.STANDARD_MODIFIERS));

    PartsTargetSurfaceSize partsTargetSurfaceSize_7 = 
      surfaceCustomMeshControl_8.getCustomValues().get(PartsTargetSurfaceSize.class);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_7.getAbsoluteSizeValue()).setDefinition("0.0072*(pow(${Ri},1/3))");
	
	//AR - Upper RW

    SurfaceCustomMeshControl surfaceCustomMeshControl_9 = 
      autoMeshOperation_0.getCustomMeshControls().createSurfaceControl();
    surfaceCustomMeshControl_9.copyProperties(surfaceCustomMeshControl_3);
    surfaceCustomMeshControl_9.setPresentationName("AR - Upper RW");
    surfaceCustomMeshControl_9.getGeometryObjects().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "RW"), new NamePredicate(NameOperator.Contains, "Upper"))), Query.STANDARD_MODIFIERS));

    PartsTargetSurfaceSize partsTargetSurfaceSize_8 = 
      surfaceCustomMeshControl_9.getCustomValues().get(PartsTargetSurfaceSize.class);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_8.getAbsoluteSizeValue()).setDefinition("0.0077*(pow(${Ri},1/3))");
	
	//AR - Upper SE

    SurfaceCustomMeshControl surfaceCustomMeshControl_10 = 
      autoMeshOperation_0.getCustomMeshControls().createSurfaceControl();
    surfaceCustomMeshControl_10.copyProperties(surfaceCustomMeshControl_5);
    surfaceCustomMeshControl_10.setPresentationName("AR - Upper SE");
    surfaceCustomMeshControl_10.getGeometryObjects().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "SE"), new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "upper"), new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "Endplate"), new NamePredicate(NameOperator.DoesNotContain, "BHND"))))))), Query.STANDARD_MODIFIERS));

    PartsTargetSurfaceSize partsTargetSurfaceSize_9 = 
      surfaceCustomMeshControl_10.getCustomValues().get(PartsTargetSurfaceSize.class);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_9.getAbsoluteSizeValue()).setDefinition("0.0077*(pow(${Ri},1/3))");
	//}
	
	//{ // CHASSIS controls 	
	//CH

    SurfaceCustomMeshControl surfaceCustomMeshControl_11 = 
      autoMeshOperation_0.getCustomMeshControls().createSurfaceControl();
    surfaceCustomMeshControl_11.setPresentationName("CH");
    surfaceCustomMeshControl_11.getGeometryObjects().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "BH_"), new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "RW"), new NamePredicate(NameOperator.Contains, "SUPP"))), new NamePredicate(NameOperator.Contains, "Hoop"), new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "Mono"), new NamePredicate(NameOperator.DoesNotContain, "bck"), new NamePredicate(NameOperator.DoesNotContain, "bracket"))), new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.EndsWith, "MN"), new NamePredicate(NameOperator.DoesNotContain, "diff"))))), Query.STANDARD_MODIFIERS));
    surfaceCustomMeshControl_11.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);

    PartsTargetSurfaceSize partsTargetSurfaceSize_10 = 
      surfaceCustomMeshControl_11.getCustomValues().get(PartsTargetSurfaceSize.class);
    partsTargetSurfaceSize_10.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_10.getAbsoluteSizeValue()).setDefinition("0.006*(pow(${Ri},1/3))");
	
	// CH - Driver

    SurfaceCustomMeshControl surfaceCustomMeshControl_12 = 
      autoMeshOperation_0.getCustomMeshControls().createSurfaceControl();
    surfaceCustomMeshControl_12.setPresentationName("CH - Driver");
    surfaceCustomMeshControl_12.getGeometryObjects().setQuery(new Query(new NamePredicate(NameOperator.Contains, "Driver"), Query.STANDARD_MODIFIERS));
    surfaceCustomMeshControl_12.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);
    surfaceCustomMeshControl_12.getCustomConditions().get(PartsMinimumSurfaceSizeOption.class).setSelected(PartsMinimumSurfaceSizeOption.Type.CUSTOM);

    PartsTargetSurfaceSize partsTargetSurfaceSize_11 = 
      surfaceCustomMeshControl_12.getCustomValues().get(PartsTargetSurfaceSize.class);
    partsTargetSurfaceSize_11.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_11.getAbsoluteSizeValue()).setDefinition("0.006*(pow(${Ri},1/3))");

    PartsMinimumSurfaceSize partsMinimumSurfaceSize_4 = 
      surfaceCustomMeshControl_12.getCustomValues().get(PartsMinimumSurfaceSize.class);
    partsMinimumSurfaceSize_4.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsMinimumSurfaceSize_4.getAbsoluteSizeValue()).setDefinition("0.002*(pow(${Ri},1/3))");
	
	// CH - HR

    SurfaceCustomMeshControl surfaceCustomMeshControl_13 = 
      autoMeshOperation_0.getCustomMeshControls().createSurfaceControl();
    surfaceCustomMeshControl_13.setPresentationName("CH - HR");
    surfaceCustomMeshControl_13.getGeometryObjects().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "HR"), new NamePredicate(NameOperator.Contains, "CH"))), Query.STANDARD_MODIFIERS));
    surfaceCustomMeshControl_13.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);
    surfaceCustomMeshControl_13.getCustomConditions().get(PartsMinimumSurfaceSizeOption.class).setSelected(PartsMinimumSurfaceSizeOption.Type.CUSTOM);

    PartsCustomizePrismMesh partsCustomizePrismMesh_5 = 
      surfaceCustomMeshControl_13.getCustomConditions().get(PartsCustomizePrismMesh.class);
    partsCustomizePrismMesh_5.getCustomPrismOptions().setSelected(PartsCustomPrismsOption.Type.DISABLE);

    PartsTargetSurfaceSize partsTargetSurfaceSize_12 = 
      surfaceCustomMeshControl_13.getCustomValues().get(PartsTargetSurfaceSize.class);
    partsTargetSurfaceSize_12.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_12.getAbsoluteSizeValue()).setDefinition("0.006*(pow(${Ri},1/3))");

    PartsMinimumSurfaceSize partsMinimumSurfaceSize_5 = 
      surfaceCustomMeshControl_13.getCustomValues().get(PartsMinimumSurfaceSize.class);
    partsMinimumSurfaceSize_5.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsMinimumSurfaceSize_5.getAbsoluteSizeValue()).setDefinition("0.00125*(pow(${Ri},1/3))");
	
	// CH-Diff

    SurfaceCustomMeshControl surfaceCustomMeshControl_14 = 
      autoMeshOperation_0.getCustomMeshControls().createSurfaceControl();
    surfaceCustomMeshControl_14.copyProperties(surfaceCustomMeshControl_12);
    surfaceCustomMeshControl_14.setPresentationName("CH-Diff");
    surfaceCustomMeshControl_14.getGeometryObjects().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "diff"), new NamePredicate(NameOperator.Contains, "bck"))), Query.STANDARD_MODIFIERS));
	//}
	
	//{ // Contact Patch  

    SurfaceCustomMeshControl surfaceCustomMeshControl_15 = 
      autoMeshOperation_0.getCustomMeshControls().createSurfaceControl();
    surfaceCustomMeshControl_15.setPresentationName("CPatch");
    surfaceCustomMeshControl_15.getGeometryObjects().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "tyre"), new NamePredicate(NameOperator.Contains, "2"))), Query.STANDARD_MODIFIERS));
    surfaceCustomMeshControl_15.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);
    surfaceCustomMeshControl_15.getCustomConditions().get(PartsMinimumSurfaceSizeOption.class).setSelected(PartsMinimumSurfaceSizeOption.Type.CUSTOM);

    PartsCustomizePrismMesh partsCustomizePrismMesh_6 = 
      surfaceCustomMeshControl_15.getCustomConditions().get(PartsCustomizePrismMesh.class);
    partsCustomizePrismMesh_6.getCustomPrismOptions().setSelected(PartsCustomPrismsOption.Type.DISABLE);

    PartsTargetSurfaceSize partsTargetSurfaceSize_13 = 
      surfaceCustomMeshControl_15.getCustomValues().get(PartsTargetSurfaceSize.class);
    partsTargetSurfaceSize_13.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_13.getAbsoluteSizeValue()).setDefinition("0.004*(pow(${Ri},1/3))");

    PartsMinimumSurfaceSize partsMinimumSurfaceSize_6 = 
      surfaceCustomMeshControl_15.getCustomValues().get(PartsMinimumSurfaceSize.class);
    partsMinimumSurfaceSize_6.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsMinimumSurfaceSize_6.getAbsoluteSizeValue()).setDefinition("0.001*(pow(${Ri},1/3))");
	//}
	
	//{ // Density Boxes Controls  
	// DB_0_Car

    VolumeCustomMeshControl volumeCustomMeshControl_0 = 
      autoMeshOperation_0.getCustomMeshControls().createVolumeControl();
    volumeCustomMeshControl_0.setPresentationName("DB_0_Car");
    volumeCustomMeshControl_0.getGeometryObjects().setQuery(new Query(new NamePredicate(NameOperator.Contains, "DB_0_Car"), Query.STANDARD_MODIFIERS));

    VolumeControlResurfacerSizeOption volumeControlResurfacerSizeOption_0 = 
      volumeCustomMeshControl_0.getCustomConditions().get(VolumeControlResurfacerSizeOption.class);
    volumeControlResurfacerSizeOption_0.setVolumeControlBaseSizeOption(true);

    VolumeControlDualMesherSizeOption volumeControlDualMesherSizeOption_0 = 
      volumeCustomMeshControl_0.getCustomConditions().get(VolumeControlDualMesherSizeOption.class);
    volumeControlDualMesherSizeOption_0.setVolumeControlBaseSizeOption(true);

    VolumeControlSize volumeControlSize_0 = 
      volumeCustomMeshControl_0.getCustomValues().get(VolumeControlSize.class);
    volumeControlSize_0.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) volumeControlSize_0.getAbsoluteSizeValue()).setDefinition("0.016*(pow(${Ri},1/3))");
	
	// DB_1_Close

    VolumeCustomMeshControl volumeCustomMeshControl_1 = 
      autoMeshOperation_0.getCustomMeshControls().createVolumeControl();
    volumeCustomMeshControl_1.copyProperties(volumeCustomMeshControl_0);
    volumeCustomMeshControl_1.setPresentationName("DB_1_Close");
    volumeCustomMeshControl_1.getGeometryObjects().setQuery(new Query(new NamePredicate(NameOperator.Contains, "DB_1"), Query.STANDARD_MODIFIERS));

    VolumeControlSize volumeControlSize_1 = 
      volumeCustomMeshControl_1.getCustomValues().get(VolumeControlSize.class);
    ((ScalarPhysicalQuantity) volumeControlSize_1.getAbsoluteSizeValue()).setDefinition("0.06*(pow(${Ri},1/3))");
	
	// DB_2_Wake

    VolumeCustomMeshControl volumeCustomMeshControl_2 = 
      autoMeshOperation_0.getCustomMeshControls().createVolumeControl();
    volumeCustomMeshControl_2.copyProperties(volumeCustomMeshControl_1);
    volumeCustomMeshControl_2.setPresentationName("DB_2_Wake");
    volumeCustomMeshControl_2.getGeometryObjects().setQuery(new Query(new NamePredicate(NameOperator.Contains, "DB_2"), Query.STANDARD_MODIFIERS));

    VolumeControlSize volumeControlSize_2 = 
      volumeCustomMeshControl_2.getCustomValues().get(VolumeControlSize.class);
    ((ScalarPhysicalQuantity) volumeControlSize_2.getAbsoluteSizeValue()).setDefinition("0.15*(pow(${Ri},1/3))");
	
	// DB_Diff

    VolumeCustomMeshControl volumeCustomMeshControl_3 = 
      autoMeshOperation_0.getCustomMeshControls().createVolumeControl();
    volumeCustomMeshControl_3.copyProperties(volumeCustomMeshControl_2);
    volumeCustomMeshControl_3.setPresentationName("DB_Diff");
    volumeCustomMeshControl_3.getGeometryObjects().setQuery(new Query(new NamePredicate(NameOperator.Contains, "DB_Diff"), Query.STANDARD_MODIFIERS));

    VolumeControlSize volumeControlSize_3 = 
      volumeCustomMeshControl_3.getCustomValues().get(VolumeControlSize.class);
    ((ScalarPhysicalQuantity) volumeControlSize_3.getAbsoluteSizeValue()).setDefinition("0.005*(pow(${Ri},1/3))");
	
	// DB_FW

    VolumeCustomMeshControl volumeCustomMeshControl_4 = 
      autoMeshOperation_0.getCustomMeshControls().createVolumeControl();
    volumeCustomMeshControl_4.copyProperties(volumeCustomMeshControl_3);
    volumeCustomMeshControl_4.setPresentationName("DB_FW");
	volumeCustomMeshControl_4.getGeometryObjects().setQuery(new Query(new NamePredicate(NameOperator.Contains, "DB_FW"), Query.STANDARD_MODIFIERS));

    VolumeControlSize volumeControlSize_4 = 
      volumeCustomMeshControl_4.getCustomValues().get(VolumeControlSize.class);
    ((ScalarPhysicalQuantity) volumeControlSize_4.getAbsoluteSizeValue()).setDefinition("0.0045*(pow(${Ri},1/3))");
	//}
	
	//{ // Domain  

    SurfaceCustomMeshControl surfaceCustomMeshControl_16 = 
      autoMeshOperation_0.getCustomMeshControls().createSurfaceControl();
    surfaceCustomMeshControl_16.setPresentationName("Domain");
    surfaceCustomMeshControl_16.getGeometryObjects().setQuery(new Query(new NamePredicate(NameOperator.Contains, "domain"), Query.STANDARD_MODIFIERS));
    surfaceCustomMeshControl_16.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);

    PartsCustomizePrismMesh partsCustomizePrismMesh_7 = 
      surfaceCustomMeshControl_16.getCustomConditions().get(PartsCustomizePrismMesh.class);
    partsCustomizePrismMesh_7.getCustomPrismOptions().setSelected(PartsCustomPrismsOption.Type.DISABLE);

    PartsTargetSurfaceSize partsTargetSurfaceSize_14 = 
      surfaceCustomMeshControl_16.getCustomValues().get(PartsTargetSurfaceSize.class);
    partsTargetSurfaceSize_14.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_14.getAbsoluteSizeValue()).setDefinition("0.35*(pow(${Ri},1/3))");
	//} 
	
	//{ // Prism L. Disable

    SurfaceCustomMeshControl surfaceCustomMeshControl_17 = 
      autoMeshOperation_0.getCustomMeshControls().createSurfaceControl();
    surfaceCustomMeshControl_17.setPresentationName("Prism L. Disable");
    surfaceCustomMeshControl_17.getGeometryObjects().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "hoop"), new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "Mono"), new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "Lid"), new NamePredicate(NameOperator.Contains, "cockpit"), new NamePredicate(NameOperator.Contains, "dash"), new NamePredicate(NameOperator.Contains, "Damper"))))))), Query.STANDARD_MODIFIERS));

    PartsCustomizePrismMesh partsCustomizePrismMesh_8 = 
      surfaceCustomMeshControl_17.getCustomConditions().get(PartsCustomizePrismMesh.class);
    partsCustomizePrismMesh_8.getCustomPrismOptions().setSelected(PartsCustomPrismsOption.Type.DISABLE);
	//}
	
	//{ // PT controls 
	// PT - Inter

    SurfaceCustomMeshControl surfaceCustomMeshControl_18 = 
      autoMeshOperation_0.getCustomMeshControls().createSurfaceControl();
    surfaceCustomMeshControl_18.setPresentationName("PT - Inter");
    surfaceCustomMeshControl_18.getGeometryObjects().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "duct"), new NamePredicate(NameOperator.Contains, "inlet"))), new NamePredicate(NameOperator.Contains, "fan__"), new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "radiator"), new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "inlet"), new NamePredicate(NameOperator.Contains, "outlet"))))))), Query.STANDARD_MODIFIERS));
    surfaceCustomMeshControl_18.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);

    PartsCustomizePrismMesh partsCustomizePrismMesh_9 = 
      surfaceCustomMeshControl_18.getCustomConditions().get(PartsCustomizePrismMesh.class);
    partsCustomizePrismMesh_9.getCustomPrismOptions().setSelected(PartsCustomPrismsOption.Type.DISABLE);

    PartsTargetSurfaceSize partsTargetSurfaceSize_15 = 
      surfaceCustomMeshControl_18.getCustomValues().get(PartsTargetSurfaceSize.class);
    partsTargetSurfaceSize_15.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_15.getAbsoluteSizeValue()).setDefinition("0.003*(pow(${Ri},1/3))");
	
	// PT - Radiator & Fan

    SurfaceCustomMeshControl surfaceCustomMeshControl_19 = 
      autoMeshOperation_0.getCustomMeshControls().createSurfaceControl();
    surfaceCustomMeshControl_19.copyProperties(surfaceCustomMeshControl_18);
    surfaceCustomMeshControl_19.setPresentationName("PT - Radiator & Fan");
    surfaceCustomMeshControl_19.getGeometryObjects().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new TypePredicate(TypeOperator.Is, GeometryPart.class), new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "Radiator"), new NamePredicate(NameOperator.Contains, "Fan"))))), new NamePredicate(NameOperator.Contains, "PT"))), Query.STANDARD_MODIFIERS));

    PartsTargetSurfaceSize partsTargetSurfaceSize_16 = 
      surfaceCustomMeshControl_19.getCustomValues().get(PartsTargetSurfaceSize.class);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_16.getAbsoluteSizeValue()).setDefinition("0.005*(pow(${Ri},1/3))");
	//}
	
	//{ // Suspension Controls 
	// SU

    SurfaceCustomMeshControl surfaceCustomMeshControl_20 = 
      autoMeshOperation_0.getCustomMeshControls().createSurfaceControl();
    surfaceCustomMeshControl_20.setPresentationName("SU");

    CompositePart compositePart_3 = 
      ((CompositePart) simulation_0.get(SimulationPartManager.class).getPart("FST10_carreira"));
    CompositePart compositePart_2 = 
      ((CompositePart) compositePart_3.getChildParts().getPart("SU"));
    CompositePart compositePart_1 = 
      ((CompositePart) compositePart_2.getChildParts().getPart("SU_MN_ASSY"));
    CompositePart compositePart_0 = 
      ((CompositePart) compositePart_1.getChildParts().getPart("MONO"));
    CadPart cadPart_0 = 
      ((CadPart) compositePart_0.getChildParts().getPart("MONO"));

    GeometryObjectProxy geometryObjectProxy_0 = 
      meshOperationPart_2.getOrCreateProxyForObject(cadPart_0);
    surfaceCustomMeshControl_20.getGeometryObjects().setQuery(new Query(new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "SU_MN_ASSY"), new NamePredicate(NameOperator.DoesNotContain, "Steer_MN"), new NamePredicate(NameOperator.DoesNotEndWith, "MN"), new InheritedPartPredicate(InheritedPartOperator.DoesNotInherit, Arrays.<ClientServerObject>asList(geometryObjectProxy_0)))), Query.STANDARD_MODIFIERS));
    surfaceCustomMeshControl_20.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);
    surfaceCustomMeshControl_20.getCustomConditions().get(PartsMinimumSurfaceSizeOption.class).setSelected(PartsMinimumSurfaceSizeOption.Type.CUSTOM);

    PartsCustomizePrismMesh partsCustomizePrismMesh_10 = 
      surfaceCustomMeshControl_20.getCustomConditions().get(PartsCustomizePrismMesh.class);
    partsCustomizePrismMesh_10.getCustomPrismOptions().setSelected(PartsCustomPrismsOption.Type.DISABLE);

    PartsTargetSurfaceSize partsTargetSurfaceSize_17 = 
      surfaceCustomMeshControl_20.getCustomValues().get(PartsTargetSurfaceSize.class);
    partsTargetSurfaceSize_17.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_17.getAbsoluteSizeValue()).setDefinition("0.01*(pow(${Ri},1/3))");

    PartsMinimumSurfaceSize partsMinimumSurfaceSize_7 = 
      surfaceCustomMeshControl_20.getCustomValues().get(PartsMinimumSurfaceSize.class);
    partsMinimumSurfaceSize_7.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);
    ((ScalarPhysicalQuantity) partsMinimumSurfaceSize_7.getAbsoluteSizeValue()).setDefinition("0.00175*(pow(${Ri},1/3))");
	
	// SU - Wheel

    SurfaceCustomMeshControl surfaceCustomMeshControl_21 = 
      autoMeshOperation_0.getCustomMeshControls().createSurfaceControl();
    surfaceCustomMeshControl_21.copyProperties(surfaceCustomMeshControl_20);
    surfaceCustomMeshControl_21.setPresentationName("SU - Wheel");
    surfaceCustomMeshControl_21.getGeometryObjects().setQuery(new Query(new CompoundPredicate(CompoundOperator.Or, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "Rim"), new CompoundPredicate(CompoundOperator.And, Arrays.<QueryPredicate>asList(new NamePredicate(NameOperator.Contains, "Tyre"), new NamePredicate(NameOperator.Contains, "Face1"))))), Query.STANDARD_MODIFIERS));
    
	PartsTargetSurfaceSize partsTargetSurfaceSize_18 = 
      surfaceCustomMeshControl_21.getCustomValues().get(PartsTargetSurfaceSize.class);
    ((ScalarPhysicalQuantity) partsTargetSurfaceSize_18.getAbsoluteSizeValue()).setDefinition("0.007*(pow(${Ri},1/3))");
	
    PartsMinimumSurfaceSize partsMinimumSurfaceSize_8 = 
      surfaceCustomMeshControl_21.getCustomValues().get(PartsMinimumSurfaceSize.class);
    ((ScalarPhysicalQuantity) partsMinimumSurfaceSize_8.getAbsoluteSizeValue()).setDefinition("0.0015*(pow(${Ri},1/3))");
	
	simulation_0.saveState(resolvePath("..\\Simulation\\MC0"+intSIM+".sim"));
	//}
	} 
}
