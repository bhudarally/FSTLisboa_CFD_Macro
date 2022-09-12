// Simcenter STAR-CCM+ macro: a_Setup.java
// Written by Simcenter STAR-CCM+ 15.06.007
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;
import star.base.report.*;
import star.vis.*;

public class a_Setup extends StarMacro {

  public void execute() {
    Setup();
  }
  
	//{ // INPUT VARIABLES 
	double Fr_Rh = 40.0;
	double Rr_Rh = 30.0;
	double Yaw = 0.0;
	double Roll = 0.00;
	double Steer_IN = 0.0;
	double Steer_OUT = 0.0;
	double Corner_Radius = 0.0;
	double Car_velocity = 11;
	
	double CoGZ = 0.302;
	double Mass_Dist_Front_Total = 0.474;
	
	double Porous_Inertial_Resistance = 57;
	double Porous_Viscous_Resistance = 592.0;
	
	double Ri = 2.74;
  
	int SIM_ID = 25;
	//}
  
  
  private void Setup() {
	  
	//{ // Field Functions attribution

    Simulation simulation_0 = 
      getActiveSimulation();
	  
	double CoGX = - (1- Mass_Dist_Front_Total)*1.54;

    UserFieldFunction userFieldFunction_0 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();
    userFieldFunction_0.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);
    userFieldFunction_0.setPresentationName("CoGX [m]");
    userFieldFunction_0.setFunctionName("cogX");
    userFieldFunction_0.setDefinition(Double.toString(CoGX));
	
    UserFieldFunction userFieldFunction_1 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();
    userFieldFunction_1.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);
    userFieldFunction_1.setPresentationName("CoG Z [m]");
    userFieldFunction_1.setFunctionName("cog_z");
    userFieldFunction_1.setDefinition(Double.toString(CoGZ));

    UserFieldFunction userFieldFunction_2 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();
    userFieldFunction_2.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);
    userFieldFunction_2.setPresentationName("Mass Dist [%Front]");
    userFieldFunction_2.setFunctionName("mass_distrib[%front]");
    userFieldFunction_2.setDefinition(Double.toString(Mass_Dist_Front_Total));

    UserFieldFunction userFieldFunction_3 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();
    userFieldFunction_3.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);
    userFieldFunction_3.setPresentationName("Porous Inertial Resistance");
    userFieldFunction_3.setFunctionName("Porous_Inertial_Resistance");
    userFieldFunction_3.setDefinition(Double.toString(Porous_Inertial_Resistance));

    UserFieldFunction userFieldFunction_4 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();
    userFieldFunction_4.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);
    userFieldFunction_4.setPresentationName("Porous Viscous Resistance");
    userFieldFunction_4.setFunctionName("Porous_Viscous_Resistance");
    userFieldFunction_4.setDefinition(Double.toString(Porous_Viscous_Resistance));

    UserFieldFunction userFieldFunction_5 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();
    userFieldFunction_5.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);
    userFieldFunction_5.setPresentationName("Ri");
    userFieldFunction_5.setFunctionName("Ri");
    userFieldFunction_5.setDefinition(Double.toString(Ri));

    UserFieldFunction userFieldFunction_6 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();
    userFieldFunction_6.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);
    userFieldFunction_6.setPresentationName("Velocity car [m/s]");
    userFieldFunction_6.setFunctionName("car_velocity[ms-1]");
    userFieldFunction_6.setDefinition(Double.toString(Car_velocity));

    UserFieldFunction userFieldFunction_7 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();
    userFieldFunction_7.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);
    userFieldFunction_7.setPresentationName("Yaw [rad]");
    userFieldFunction_7.setFunctionName("yaw[rad]");    
	userFieldFunction_7.setDefinition(Double.toString(Math.toRadians(Yaw)));
	
    UserFieldFunction userFieldFunction_8 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();
    userFieldFunction_8.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);
    userFieldFunction_8.setPresentationName("Yaw [deg]");
    userFieldFunction_8.setFunctionName("yaw[deg]");
    userFieldFunction_8.setDefinition(Double.toString(Yaw));

    UserFieldFunction userFieldFunction_9 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();
    userFieldFunction_9.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);
    userFieldFunction_9.setPresentationName("Corner Radius [m]");
    userFieldFunction_9.setFunctionName("corner_radius[m]");
    userFieldFunction_9.setDefinition(Double.toString(Corner_Radius));

    UserFieldFunction userFieldFunction_10 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();
    userFieldFunction_10.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);
    userFieldFunction_10.setPresentationName("Steer-Inner [rad]");
    userFieldFunction_10.setFunctionName("steer-inner[rad]");
    userFieldFunction_10.setDefinition(Double.toString(Math.toRadians(Steer_IN)));

    UserFieldFunction userFieldFunction_11 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();
    userFieldFunction_11.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);
    userFieldFunction_11.setPresentationName("Steer-Outer [rad]");
    userFieldFunction_11.setFunctionName("steer-outer[rad]");
    userFieldFunction_11.setDefinition(Double.toString(Math.toRadians(Steer_OUT)));

    UserFieldFunction userFieldFunction_12 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();
    userFieldFunction_12.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);
    userFieldFunction_12.setPresentationName("Steer-Inner [deg]");
    userFieldFunction_12.setFunctionName("steer-inner[deg]");
    userFieldFunction_12.setDefinition(Double.toString(Steer_IN));

    UserFieldFunction userFieldFunction_13 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();
    userFieldFunction_13.copyProperties(userFieldFunction_11);
    userFieldFunction_13.setPresentationName("Steer-Outer [deg]");
    userFieldFunction_13.setFunctionName("steer-outer[deg]");
    userFieldFunction_13.setDefinition(Double.toString(Steer_OUT));
	
	UserFieldFunction userFieldFunction_14 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();
    userFieldFunction_14.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);
    userFieldFunction_14.setPresentationName("Roll [deg]");
    userFieldFunction_14.setFunctionName("roll[deg]");
    userFieldFunction_14.setDefinition(Double.toString(Roll));

	if ( Corner_Radius != 0) {
    UserFieldFunction userFieldFunction_15 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();
    userFieldFunction_15.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);
    userFieldFunction_15.setPresentationName("Air Angular Velocity [rad/s]");
    userFieldFunction_15.setFunctionName("Angular_Velocity");
    userFieldFunction_15.setDefinition("${car_velocity[ms-1]}/${corner_radius[m]}");
	}

	UserFieldFunction userFieldFunction_24 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();
    userFieldFunction_24.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);
    userFieldFunction_24.setPresentationName("Sim ID");
    userFieldFunction_24.setFunctionName("sim_id");
    userFieldFunction_24.setDefinition(Integer.toString(SIM_ID));
	
	UserFieldFunction userFieldFunction_25 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();
    userFieldFunction_25.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);
    userFieldFunction_25.setPresentationName("Fr-RH");
    userFieldFunction_25.setFunctionName("fr_rh");
    userFieldFunction_25.setDefinition(Double.toString(Fr_Rh));
	
	UserFieldFunction userFieldFunction_26 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();
    userFieldFunction_26.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);
    userFieldFunction_26.setPresentationName("Rr-RH");
    userFieldFunction_26.setFunctionName("rr_rh");
    userFieldFunction_26.setDefinition(Double.toString(Rr_Rh));

    simulation_0.getFieldFunctionManager().getGroupsManager().createGroup("[Cooling]");
    ((ClientServerObjectGroup) simulation_0.getFieldFunctionManager().getGroupsManager().getObject("[Cooling]")).getGroupsManager().groupObjects("[Cooling]", new NeoObjectVector(new Object[] {userFieldFunction_3, userFieldFunction_4}), true);

    simulation_0.getFieldFunctionManager().getGroupsManager().createGroup("[Setup]");
    ((ClientServerObjectGroup) simulation_0.getFieldFunctionManager().getGroupsManager().getObject("[Setup]")).getGroupsManager().groupObjects("[Setup]", new NeoObjectVector(new Object[] {userFieldFunction_11, userFieldFunction_13, userFieldFunction_7, userFieldFunction_6, userFieldFunction_10, userFieldFunction_12, userFieldFunction_8, userFieldFunction_9, userFieldFunction_14, userFieldFunction_24, userFieldFunction_25, userFieldFunction_26}), true);

    simulation_0.getFieldFunctionManager().getGroupsManager().createGroup("[CoG]");
    ((ClientServerObjectGroup) simulation_0.getFieldFunctionManager().getGroupsManager().getObject("[CoG]")).getGroupsManager().groupObjects("[CoG]", new NeoObjectVector(new Object[] {userFieldFunction_1, userFieldFunction_0, userFieldFunction_2}), true);
	//}

	//{ // Annotations 
	
    SimpleAnnotation simpleAnnotation_0 = 
      simulation_0.getAnnotationManager().createAnnotation(SimpleAnnotation.class);
	simpleAnnotation_0.setPresentationName("Setup column 1");
	simpleAnnotation_0.setShadow(false);
	simpleAnnotation_0.setFontString("Arial-Bold");
	simpleAnnotation_0.setText("Fr-RH      "+Fr_Rh+" mm\nRr-RH      "+Rr_Rh+" mm\nRoll          "+Roll+"  \u00BA\nYaw         "+Yaw+"  \u00BA");
	simpleAnnotation_0.setDefaultHeight(0.095);
    simpleAnnotation_0.setDefaultPosition(new DoubleVector(new double[] {0.285, 0.015, 0.0}));
	
	String typesim;
	
	if ( Corner_Radius == 0 && Roll == 0 && Yaw == 0) {
	 typesim = "Straight      Symmetric";
	} else if ( Corner_Radius == 0 && ( Yaw != 0 || Roll != 0)) {
	 typesim = "Straight       Full Car";
	} else {
	 typesim = "C.Radius     "+Corner_Radius+" m"	;	
	}

    SimpleAnnotation simpleAnnotation_1 = 
      simulation_0.getAnnotationManager().createAnnotation(SimpleAnnotation.class);
    simpleAnnotation_1.copyProperties(simpleAnnotation_0);
	simpleAnnotation_1.setPresentationName("Setup column 2");
	simpleAnnotation_1.setText(typesim+ "\nStr OUT       "+Steer_OUT+" \u00BA\nStr IN           "+Steer_IN+" \u00BA\nVelocity      "+Car_velocity+" m/s");
	simpleAnnotation_1.setDefaultHeight(0.095);
	simpleAnnotation_1.setDefaultPosition(new DoubleVector(new double[] {0.43, 0.015, 0.0}));

    SimpleAnnotation simpleAnnotation_2 = 
      simulation_0.getAnnotationManager().createAnnotation(SimpleAnnotation.class);
	simpleAnnotation_2.copyProperties(simpleAnnotation_0);
	simpleAnnotation_2.setPresentationName("Sim ID");
    simpleAnnotation_2.setText("MC0"+SIM_ID);
	simpleAnnotation_2.setDefaultHeight(0.085);
	simpleAnnotation_2.setDefaultPosition(new DoubleVector(new double[] {0.655, 0.012, 0.0}));
	
	final String folder = resolveWorkPath();
	
	ImageAnnotation2D imageAnnotation2D_0 = 
      simulation_0.getAnnotationManager().createAnnotation(ImageAnnotation2D.class);
	imageAnnotation2D_0.setFilePath( folder +"/Files/lineBlack.png");
    imageAnnotation2D_0.setPresentationName("Line");
    imageAnnotation2D_0.setDefaultWidthStretchFactor(4.0);
	imageAnnotation2D_0.setDefaultHeight(0.0025);
	imageAnnotation2D_0.setDefaultPosition(new DoubleVector(new double[] {0.0, 0.125, 0.0}));
	
	//}
	
	//{ // Auxiliary Reports to access input variables
	
    Region region_1 = 
      simulation_0.getRegionManager().createEmptyRegion();
	  
	Units units_0 = 
      simulation_0.getUnitsManager().getPreferredUnits(new IntVector(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));

    ExpressionReport expressionReport_0 = 
      simulation_0.getReportManager().createReport(ExpressionReport.class);
    expressionReport_0.setPresentationName("CoGX");
    expressionReport_0.setDefinition("${cogX}");

    simulation_0.getRegionManager().removeRegions(Arrays.<Region>asList(region_1));

    ExpressionReport expressionReport_1 = 
      simulation_0.getReportManager().createReport(ExpressionReport.class);
    expressionReport_1.setPresentationName("CoGZ");
    expressionReport_1.setDefinition("${cog_z}");

    ExpressionReport expressionReport_2 = 
      simulation_0.getReportManager().createReport(ExpressionReport.class);
    expressionReport_2.setPresentationName("Yaw [rad]");
    expressionReport_2.setDefinition("${yaw[rad]}");

    ExpressionReport expressionReport_3 = 
      simulation_0.getReportManager().createReport(ExpressionReport.class);
    expressionReport_3.setPresentationName("Corner Radius [m]");
    expressionReport_3.setDefinition("${corner_radius[m]}");
	
	ExpressionReport expressionReport_4 = 
      simulation_0.getReportManager().createReport(ExpressionReport.class);
    expressionReport_4.setPresentationName("Roll [deg]");
    expressionReport_4.setDefinition("${roll[deg]}");
	
	ExpressionReport expressionReport_5 = 
      simulation_0.getReportManager().createReport(ExpressionReport.class);
    expressionReport_5.setPresentationName("Steer-Inner [deg]");
    expressionReport_5.setDefinition("${steer-inner[deg]}");
	
	ExpressionReport expressionReport_6 = 
      simulation_0.getReportManager().createReport(ExpressionReport.class);
    expressionReport_6.setPresentationName("Steer-Outer [deg]");
    expressionReport_6.setDefinition("${steer-outer[deg]}");
	
	ExpressionReport expressionReport_7 = 
      simulation_0.getReportManager().createReport(ExpressionReport.class);
    expressionReport_7.setPresentationName("Sim ID");
    expressionReport_7.setDefinition("${sim_id}");
	
	ExpressionReport expressionReport_8 = 
      simulation_0.getReportManager().createReport(ExpressionReport.class);
    expressionReport_8.setPresentationName("Fr-RH");
    expressionReport_8.setDefinition("${fr_rh}");
	
	ExpressionReport expressionReport_9 = 
      simulation_0.getReportManager().createReport(ExpressionReport.class);
    expressionReport_9.setPresentationName("Rr-RH");
    expressionReport_9.setDefinition("${rr_rh}");

    simulation_0.getReportManager().getGroupsManager().createGroup("[AC]");
	((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[AC]")).getGroupsManager().createGroup("Setup");
	((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[AC]")).getGroupsManager().getObject("Setup")).getGroupsManager().groupObjects("Setup", new NeoObjectVector(new Object[] {expressionReport_0, expressionReport_1, expressionReport_2, expressionReport_3, expressionReport_4, expressionReport_5, expressionReport_6, expressionReport_7, expressionReport_8, expressionReport_9}), true);
	
	//}
  
	//{ // Views
	
	Units units_1 = 
      ((Units) simulation_0.getUnitsManager().getObject("m"));
	
	VisView visView_0 = 
      simulation_0.getViewManager().createView();
    visView_0.setPresentationName("test");
    visView_0.setProjectionMode(VisProjectionMode.PARALLEL);
    ParallelScale parallelScale_0 = 
      visView_0.getParallelScale();
    parallelScale_0.setValue(0.9);
	
	VisView visView_1 = 
      simulation_0.getViewManager().createView();
    visView_1.copyProperties(visView_0);
    visView_1.setPresentationName("top");
    visView_1.getFocalPointCoordinate().setCoordinate(units_1, units_1, units_1, new DoubleVector(new double[] {-0.51, -0.125, 0.0}));
    visView_1.getPositionCoordinate().setCoordinate(units_1, units_1, units_1, new DoubleVector(new double[] {-0.51, 0.0, 15.0}));
    visView_1.getViewUpCoordinate().setCoordinate(units_1, units_1, units_1, new DoubleVector(new double[] {0.0, 1.0, 0.0}));
	
	VisView visView_2 = 
      simulation_0.getViewManager().createView();
	visView_2.copyProperties(visView_0);
    visView_2.setPresentationName("bottom");
	visView_2.getFocalPointCoordinate().setCoordinate(units_1, units_1, units_1, new DoubleVector(new double[] {-0.51, 0.125, 0.0}));
    visView_2.getPositionCoordinate().setCoordinate(units_1, units_1, units_1, new DoubleVector(new double[] {-0.51, 0.0, -15.0}));
    visView_2.getViewUpCoordinate().setCoordinate(units_1, units_1, units_1, new DoubleVector(new double[] {0.0, -1.0, 0.0}));
	
    VisView visView_3 = 
      simulation_0.getViewManager().createView();
    visView_3.copyProperties(visView_0);
    visView_3.setPresentationName("front");
	ParallelScale parallelScale_1 = 
      visView_3.getParallelScale();
    parallelScale_1.setValue(0.78);
    visView_3.getFocalPointCoordinate().setCoordinate(units_1, units_1, units_1, new DoubleVector(new double[] {0.0, 0.0, 0.57}));
    visView_3.getPositionCoordinate().setCoordinate(units_1, units_1, units_1, new DoubleVector(new double[] {10.0, 0.0, 0.57}));
    visView_3.getViewUpCoordinate().setCoordinate(units_1, units_1, units_1, new DoubleVector(new double[] {0.0, 0.0, 1.0}));
    
	VisView visView_4 = 
      simulation_0.getViewManager().createView();
    visView_4.copyProperties(visView_3);
    visView_4.setPresentationName("rear");
    visView_4.getPositionCoordinate().setCoordinate(units_1, units_1, units_1, new DoubleVector(new double[] {-10.0, 0.0, 0.57}));

    VisView visView_5 = 
      simulation_0.getViewManager().createView();
    visView_5.copyProperties(visView_0);
    visView_5.setPresentationName("left");
    visView_5.getFocalPointCoordinate().setCoordinate(units_1, units_1, units_1, new DoubleVector(new double[] {-0.52, 0.0, 0.55}));
    visView_5.getPositionCoordinate().setCoordinate(units_1, units_1, units_1, new DoubleVector(new double[] {-0.52, 10.0, 0.55}));
    visView_5.getViewUpCoordinate().setCoordinate(units_1, units_1, units_1, new DoubleVector(new double[] {0.0, 0.0, 1.0}));
	
    VisView visView_6 = 
      simulation_0.getViewManager().createView();
    visView_6.copyProperties(visView_5);
    visView_6.setPresentationName("right");
    visView_6.getPositionCoordinate().setCoordinate(units_1, units_1, units_1, new DoubleVector(new double[] {-0.52, -10.0, 0.55}));
  
	VisView visView_7 = 
      simulation_0.getViewManager().createView();
    visView_7.setPresentationName("bottom_rear_right");
    visView_7.setProjectionMode(VisProjectionMode.PARALLEL);
    visView_7.getFocalPointCoordinate().setCoordinate(units_1, units_1, units_1, new DoubleVector(new double[] {-0.77, 0.0, 0.3}));
    visView_7.getPositionCoordinate().setCoordinate(units_1, units_1, units_1, new DoubleVector(new double[] {-8.0, -8.88, -6.0}));
    visView_7.getViewUpCoordinate().setCoordinate(units_1, units_1, units_1, new DoubleVector(new double[] {0.0, 0.0, 1.0}));
	ParallelScale parallelScale_2 = 
      visView_7.getParallelScale();
    parallelScale_2.setValue(1.4);
  
	VisView visView_8 = 
      simulation_0.getViewManager().createView();
    visView_8.copyProperties(visView_7);
    visView_8.setPresentationName("bottom_rear_left");
    visView_8.getFocalPointCoordinate().setCoordinate(units_1, units_1, units_1, new DoubleVector(new double[] {-0.77, 0.0, 0.3}));
    visView_8.getPositionCoordinate().setCoordinate(units_1, units_1, units_1, new DoubleVector(new double[] {-8.0, 8.88, -6.0}));
    visView_8.getViewUpCoordinate().setCoordinate(units_1, units_1, units_1, new DoubleVector(new double[] {0.0, 0.0, 1.0}));
	
	VisView visView_9 = 
      simulation_0.getViewManager().createView();
    visView_9.copyProperties(visView_7);
    visView_9.setPresentationName("bottom_front_right");
    visView_9.getFocalPointCoordinate().setCoordinate(units_1, units_1, units_1, new DoubleVector(new double[] {-0.77, 0.0, 0.3}));
    visView_9.getPositionCoordinate().setCoordinate(units_1, units_1, units_1, new DoubleVector(new double[] {8.0, -8.88, -6.0}));
    visView_9.getViewUpCoordinate().setCoordinate(units_1, units_1, units_1, new DoubleVector(new double[] {0.0, 0.0, 1.0}));
	
	VisView visView_10 = 
      simulation_0.getViewManager().createView();
    visView_10.copyProperties(visView_7);
    visView_10.setPresentationName("bottom_front_left");
    visView_10.getFocalPointCoordinate().setCoordinate(units_1, units_1, units_1, new DoubleVector(new double[] {-0.77, 0.0, 0.3}));
    visView_10.getPositionCoordinate().setCoordinate(units_1, units_1, units_1, new DoubleVector(new double[] {8.0, 8.88, -6.0}));
    visView_10.getViewUpCoordinate().setCoordinate(units_1, units_1, units_1, new DoubleVector(new double[] {0.0, 0.0, 1.0}));
	
	VisView visView_11 = 
      simulation_0.getViewManager().createView();
    visView_11.copyProperties(visView_7);
    visView_11.setPresentationName("top_rear_right");
    visView_11.getFocalPointCoordinate().setCoordinate(units_1, units_1, units_1, new DoubleVector(new double[] {-0.77, 0.0, 0.3}));
    visView_11.getPositionCoordinate().setCoordinate(units_1, units_1, units_1, new DoubleVector(new double[] {-8.0, -8.88, 6.0}));
    visView_11.getViewUpCoordinate().setCoordinate(units_1, units_1, units_1, new DoubleVector(new double[] {0.0, 0.0, 1.0}));
	
	VisView visView_12 = 
      simulation_0.getViewManager().createView();
    visView_12.copyProperties(visView_7);
    visView_12.setPresentationName("top_rear_left");
    visView_12.getFocalPointCoordinate().setCoordinate(units_1, units_1, units_1, new DoubleVector(new double[] {-0.77, 0.0, 0.3}));
    visView_12.getPositionCoordinate().setCoordinate(units_1, units_1, units_1, new DoubleVector(new double[] {-8.0, 8.88, 6.0}));
    visView_12.getViewUpCoordinate().setCoordinate(units_1, units_1, units_1, new DoubleVector(new double[] {0.0, 0.0, 1.0}));
	
	VisView visView_13 = 
      simulation_0.getViewManager().createView();
    visView_13.copyProperties(visView_7);
    visView_13.setPresentationName("top_front_right");
    visView_13.getFocalPointCoordinate().setCoordinate(units_1, units_1, units_1, new DoubleVector(new double[] {-0.77, 0.0, 0.3}));
    visView_13.getPositionCoordinate().setCoordinate(units_1, units_1, units_1, new DoubleVector(new double[] {8.0, -8.88, 6.0}));
    visView_13.getViewUpCoordinate().setCoordinate(units_1, units_1, units_1, new DoubleVector(new double[] {0.0, 0.0, 1.0}));
	
	VisView visView_14 = 
      simulation_0.getViewManager().createView();
    visView_14.copyProperties(visView_7);
    visView_14.setPresentationName("top_front_left");
    visView_14.getFocalPointCoordinate().setCoordinate(units_1, units_1, units_1, new DoubleVector(new double[] {-0.77, 0.0, 0.3}));
    visView_14.getPositionCoordinate().setCoordinate(units_1, units_1, units_1, new DoubleVector(new double[] {8.0, 8.88, 6.0}));
    visView_14.getViewUpCoordinate().setCoordinate(units_1, units_1, units_1, new DoubleVector(new double[] {0.0, 0.0, 1.0}));
	
	VisView visView_15 = 
      simulation_0.getViewManager().createView();
    visView_15.copyProperties(visView_7);
    visView_15.setPresentationName("top_CptZ");
	ParallelScale parallelScale_3 = 
      visView_15.getParallelScale();
    parallelScale_3.setValue(1.4);
    visView_15.getFocalPointCoordinate().setCoordinate(units_1, units_1, units_1, new DoubleVector(new double[] {-0.510, -0.200, 0.0}));
    visView_15.getPositionCoordinate().setCoordinate(units_1, units_1, units_1, new DoubleVector(new double[] {-0.51, 0.0, 15.0}));
    visView_15.getViewUpCoordinate().setCoordinate(units_1, units_1, units_1, new DoubleVector(new double[] {0.0, 1.0, 0.0}));
	
	visView_7.getFocalPointCoordinate().setCoordinate(units_1, units_1, units_1, new DoubleVector(new double[] {-0.77, 0.0, 0.3}));
    visView_7.getPositionCoordinate().setCoordinate(units_1, units_1, units_1, new DoubleVector(new double[] {-8.0, -8.88, -6.0}));
    visView_7.getViewUpCoordinate().setCoordinate(units_1, units_1, units_1, new DoubleVector(new double[] {0.0, 0.0, 1.0}));
	
	simulation_0.getViewManager().removeObjects(visView_0);	
	
	//}
	}
}
