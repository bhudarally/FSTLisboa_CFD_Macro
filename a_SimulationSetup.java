// STAR-CCM+ macro: a_SimulationSetup.java
// Written by STAR-CCM+ 14.04.011
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;
import star.base.report.*;
import star.vis.*;
import star.meshing.*;
import java.util.*;
import java.io.*;
import java.nio.*;

public class a_SimulationSetup extends StarMacro {

  public void execute() {
    FieldFunctions_Annotations();
    Views();
  }

  // Simulation Configuration //

  double Ri = 1;
  String Sim_ID = "1";

  // Car Configuration //

  double Car_Velocity = 15.0;                  //Car Velocity - aligned with Beta              m.s-1
  double Corner_Radius = 0.0;                 //Cornering Radius - Minimum value 6.25m            meters 
  double Front_Ride_Height = 40;
  double Rear_Ride_Height = 40;
  double Beta = 0.0;                  //Vehicle Slip Angle                    degrees
  double SteeringInner = 0.0;           //Steering angle inner wheel (Left) - Delta inner     degrees
  double SteeringOuter = 0.0;             //Steering angle outer wheel (Rig ht) - Delta outer      degrees
  double Wind_Speed = 0;          //Wind Speed                            m.s-1
  double Yaw = 0.0;                 //Wind yaw angle - Positive left to right         degrees
  double Roll = 0.0;

  double CoGZ = 0.302;   //Height of Center of Gravity                 meters
  double Mass_Dist_Front_Total = 0.474;  //Weight Distribution - Front/Total               % [0,1]

  double Porous_Inertial_Resistance = 57;
  double Porous_Viscous_Resistance = 592.0;

  String Description = Front_Ride_Height+"_"+Rear_Ride_Height; //Max 40 Digits

      private void FieldFunctions_Annotations() {

    Simulation simulation_0 = 
      getActiveSimulation();

  // Field Functions //
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
    userFieldFunction_6.setPresentationName("Car Velocity [m/s]");
    userFieldFunction_6.setFunctionName("car_velocity[ms-1]");
    userFieldFunction_6.setDefinition(Double.toString(Car_Velocity));

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
    userFieldFunction_10.setFunctionName("steer_inner[rad]");
    userFieldFunction_10.setDefinition(Double.toString(Math.toRadians(SteeringInner)));

    UserFieldFunction userFieldFunction_11 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();
    userFieldFunction_11.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);
    userFieldFunction_11.setPresentationName("Steer-Outer [rad]");
    userFieldFunction_11.setFunctionName("steer_outer[rad]");
    userFieldFunction_11.setDefinition(Double.toString(Math.toRadians(SteeringOuter)));

    UserFieldFunction userFieldFunction_12 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();
    userFieldFunction_12.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);
    userFieldFunction_12.setPresentationName("Steer-Inner [deg]");
    userFieldFunction_12.setFunctionName("steer_inner[deg]");
    userFieldFunction_12.setDefinition(Double.toString(SteeringInner));

    UserFieldFunction userFieldFunction_13 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();
    userFieldFunction_13.copyProperties(userFieldFunction_11);
    userFieldFunction_13.setPresentationName("Steer-Outer [deg]");
    userFieldFunction_13.setFunctionName("steer_outer[deg]");
    userFieldFunction_13.setDefinition(Double.toString(SteeringOuter));
  
  UserFieldFunction userFieldFunction_14 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();
    userFieldFunction_14.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);
    userFieldFunction_14.setPresentationName("Roll [deg]");
    userFieldFunction_14.setFunctionName("roll[deg]");
    userFieldFunction_14.setDefinition(Double.toString(Roll));

  if ( Corner_Radius != 0) {
    UserFieldFunction userFieldFunction_50 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();
    userFieldFunction_50.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);
    userFieldFunction_50.setPresentationName("CosYaw");
    userFieldFunction_50.setFunctionName("cosYaw");
    userFieldFunction_50.setDefinition("cos(${yaw[rad]})");
  }

  if ( Corner_Radius != 0) {
    UserFieldFunction userFieldFunction_51 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();
    userFieldFunction_51.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);
    userFieldFunction_51.setPresentationName("Car Angular Velocity [rad/s]");
    userFieldFunction_51.setFunctionName("Car_Angular_Velocity");
    userFieldFunction_51.setDefinition("${car_velocity[ms-1]}/(${corner_radius[m]}*${cosYaw})");
  }

  UserFieldFunction userFieldFunction_24 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();
    userFieldFunction_24.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);
    userFieldFunction_24.setPresentationName("Sim ID");
    userFieldFunction_24.setFunctionName("sim_id");
    userFieldFunction_24.setDefinition(Sim_ID);
  
  UserFieldFunction userFieldFunction_25 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();
    userFieldFunction_25.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);
    userFieldFunction_25.setPresentationName("Fr-RH");
    userFieldFunction_25.setFunctionName("fr_rh");
    userFieldFunction_25.setDefinition(Double.toString(Front_Ride_Height));
  
  UserFieldFunction userFieldFunction_26 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();
    userFieldFunction_26.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);
    userFieldFunction_26.setPresentationName("Rr-RH");
    userFieldFunction_26.setFunctionName("rr_rh");
    userFieldFunction_26.setDefinition(Double.toString(Rear_Ride_Height));

  UserFieldFunction userFieldFunction_27 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();
    userFieldFunction_27.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);
    userFieldFunction_27.setPresentationName("Beta");
    userFieldFunction_27.setFunctionName("beta");
    userFieldFunction_27.setDefinition(Double.toString(Beta));

    try {
      ((ClientServerObjectGroup) simulation_0.getFieldFunctionManager().getGroupsManager().getObject("[Cooling]")).setPresentationName("[Cooling]");;
    }
    catch (Exception e)
    {
      simulation_0.getFieldFunctionManager().getGroupsManager().createGroup("[Cooling]");
    }
    ((ClientServerObjectGroup) simulation_0.getFieldFunctionManager().getGroupsManager().getObject("[Cooling]")).getGroupsManager().groupObjects("[Cooling]", new NeoObjectVector(new Object[] {userFieldFunction_3, userFieldFunction_4}), true);

    simulation_0.getFieldFunctionManager().getGroupsManager().createGroup("[Setup]");
    ((ClientServerObjectGroup) simulation_0.getFieldFunctionManager().getGroupsManager().getObject("[Setup]")).getGroupsManager().groupObjects("[Setup]", new NeoObjectVector(new Object[] {userFieldFunction_11, userFieldFunction_13, userFieldFunction_7, userFieldFunction_6, userFieldFunction_10, userFieldFunction_12, userFieldFunction_8, userFieldFunction_9, userFieldFunction_14, userFieldFunction_25, userFieldFunction_26,userFieldFunction_27}), true);

    simulation_0.getFieldFunctionManager().getGroupsManager().createGroup("[CoG]");
    ((ClientServerObjectGroup) simulation_0.getFieldFunctionManager().getGroupsManager().getObject("[CoG]")).getGroupsManager().groupObjects("[CoG]", new NeoObjectVector(new Object[] {userFieldFunction_1, userFieldFunction_0, userFieldFunction_2}), true);

    // Annotations //
  
    SimpleAnnotation simpleAnnotation_0 = 
      simulation_0.getAnnotationManager().createAnnotation(SimpleAnnotation.class);
  simpleAnnotation_0.setPresentationName("Setup column 1");
  simpleAnnotation_0.setShadow(false);
  simpleAnnotation_0.setFontString("Arial-Bold");
  simpleAnnotation_0.setText("Fr-RH      "+Front_Ride_Height+" mm\nRr-RH      "+Rear_Ride_Height+" mm\nRoll          "+Roll+"  \u00BA\nYaw         "+Yaw+"  \u00BA");
  simpleAnnotation_0.setDefaultHeight(0.095);
    simpleAnnotation_0.setDefaultPosition(new DoubleVector(new double[] {0.285, 0.005, 0.0}));
  
  String typesim;
  
  if ( Corner_Radius == 0 && Roll == 0 && Yaw == 0) {
   typesim = "Straight      Symmetric";
  } else if ( Corner_Radius == 0 && ( Yaw != 0 || Roll != 0)) {
   typesim = "Straight       Full Car";
  } else {
   typesim = "C.Radius     "+Corner_Radius+" m" ; 
  }

    SimpleAnnotation simpleAnnotation_1 = 
      simulation_0.getAnnotationManager().createAnnotation(SimpleAnnotation.class);
    simpleAnnotation_1.copyProperties(simpleAnnotation_0);
  simpleAnnotation_1.setPresentationName("Setup column 2");
  simpleAnnotation_1.setText(typesim+ "\nStr OUT       "+SteeringOuter+" \u00BA\nStr IN           "+SteeringInner+" \u00BA\nVelocity      "+Car_Velocity+" m/s");
  simpleAnnotation_1.setDefaultHeight(0.095);
  simpleAnnotation_1.setDefaultPosition(new DoubleVector(new double[] {0.43, 0.005, 0.0}));

    SimpleAnnotation simpleAnnotation_2 = 
      simulation_0.getAnnotationManager().createAnnotation(SimpleAnnotation.class);
  simpleAnnotation_2.copyProperties(simpleAnnotation_0);
  simpleAnnotation_2.setPresentationName("Description");
  simpleAnnotation_2.setFontString("Arial-BoldItalic");
  simpleAnnotation_2.setText(Description);
  simpleAnnotation_2.setDefaultHeight(0.025);
  simpleAnnotation_2.setDefaultPosition(new DoubleVector(new double[] {0.287, 0.095, 0.0}));

  SimpleAnnotation simpleAnnotation_3 = 
      simulation_0.getAnnotationManager().createAnnotation(SimpleAnnotation.class);
  simpleAnnotation_3.copyProperties(simpleAnnotation_0);
  simpleAnnotation_3.setPresentationName("Sim ID");
  simpleAnnotation_3.setFontString("Arial-BoldItalic");
  simpleAnnotation_3.setText(Sim_ID);
  simpleAnnotation_3.setDefaultHeight(0.025);
  simpleAnnotation_3.setDefaultPosition(new DoubleVector(new double[] {0.287, 0.095, 0.0}));

  ImageAnnotation2D imageAnnotation2D_0 = 
      simulation_0.getAnnotationManager().createAnnotation(ImageAnnotation2D.class);
  imageAnnotation2D_0.setFilePath("..\\Modules_Macro\\Library\\lineBlack.png");
    imageAnnotation2D_0.setPresentationName("Line");
    imageAnnotation2D_0.setDefaultWidthStretchFactor(4.0);
  imageAnnotation2D_0.setDefaultHeight(0.0025);
  imageAnnotation2D_0.setDefaultPosition(new DoubleVector(new double[] {0.0, 0.125, 0.0}));
  imageAnnotation2D_0.reload();

  ImageAnnotation2D imageAnnotation2D_1 = 
      simulation_0.getAnnotationManager().createAnnotation(ImageAnnotation2D.class);
  imageAnnotation2D_1.setFilePath("..\\Modules_Macro\\Library\\fst_white.png");
    imageAnnotation2D_1.setPresentationName("FST White Icon");
    imageAnnotation2D_1.setDefaultWidthStretchFactor(1.0);
  imageAnnotation2D_1.setDefaultHeight(0.095);
  imageAnnotation2D_1.setDefaultPosition(new DoubleVector(new double[] {0.69, 0.01, 0.0}));
  imageAnnotation2D_1.reload();

  ImageAnnotation2D imageAnnotation2D_2 = 
      simulation_0.getAnnotationManager().createAnnotation(ImageAnnotation2D.class);
  imageAnnotation2D_2.setFilePath("..\\Modules_Macro\\Library\\fst_black.png");
    imageAnnotation2D_2.setPresentationName("FST Black Icon");
    imageAnnotation2D_2.setDefaultWidthStretchFactor(1.0);
  imageAnnotation2D_2.setDefaultHeight(0.095);
  imageAnnotation2D_2.setDefaultPosition(new DoubleVector(new double[] {0.69, 0.01, 0.0}));
  imageAnnotation2D_2.reload();
  
  //}
  
  // Auxiliary Reports to access input variables //

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
    expressionReport_5.setDefinition("${steer_inner[deg]}");
  
  ExpressionReport expressionReport_6 = 
      simulation_0.getReportManager().createReport(ExpressionReport.class);
    expressionReport_6.setPresentationName("Steer-Outer [deg]");
    expressionReport_6.setDefinition("${steer_outer[deg]}");
  
  ExpressionReport expressionReport_8 = 
      simulation_0.getReportManager().createReport(ExpressionReport.class);
    expressionReport_8.setPresentationName("Fr-RH");
    expressionReport_8.setDefinition("${fr_rh}");
  
  ExpressionReport expressionReport_9 = 
      simulation_0.getReportManager().createReport(ExpressionReport.class);
    expressionReport_9.setPresentationName("Rr-RH");
    expressionReport_9.setDefinition("${rr_rh}");

  ExpressionReport expressionReport_10 = 
      simulation_0.getReportManager().createReport(ExpressionReport.class);
    expressionReport_10.setPresentationName("Car-Velocity [ms-1]");
    expressionReport_10.setDefinition("${car_velocity[ms-1]}");

    simulation_0.getReportManager().getGroupsManager().createGroup("[AC]");
  ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[AC]")).getGroupsManager().createGroup("Setup");
  ((ClientServerObjectGroup) ((ClientServerObjectGroup) simulation_0.getReportManager().getGroupsManager().getObject("[AC]")).getGroupsManager().getObject("Setup")).getGroupsManager().groupObjects("Setup", new NeoObjectVector(new Object[] {expressionReport_0, expressionReport_1, expressionReport_2, expressionReport_3, expressionReport_4, expressionReport_5, expressionReport_6, expressionReport_8, expressionReport_9, expressionReport_10}), true);

}
  private void Views() {

    Simulation simulation_0 = 
      getActiveSimulation();

    // Views

  Units units_1 = 
      ((Units) simulation_0.getUnitsManager().getObject("m"));
  
  VisView visView_1 = 
      simulation_0.getViewManager().createView();
    visView_1.setPresentationName("top");
    visView_1.getFocalPointCoordinate().setCoordinate(units_1, units_1, units_1, new DoubleVector(new double[] {-0.51, -0.125, 0.0}));
    visView_1.getPositionCoordinate().setCoordinate(units_1, units_1, units_1, new DoubleVector(new double[] {-0.51, 0.0, 15.0}));
    visView_1.getViewUpCoordinate().setCoordinate(units_1, units_1, units_1, new DoubleVector(new double[] {0.0, 1.0, 0.0}));
    visView_1.setProjectionMode(VisProjectionMode.PARALLEL);
    ParallelScale parallelScale_0 = 
      visView_1.getParallelScale();
    parallelScale_0.setValue(0.9);
  
  VisView visView_2 = 
      simulation_0.getViewManager().createView();
  visView_2.copyProperties(visView_1);
    visView_2.setPresentationName("bottom");
  visView_2.getFocalPointCoordinate().setCoordinate(units_1, units_1, units_1, new DoubleVector(new double[] {-0.51, 0.125, 0.0}));
    visView_2.getPositionCoordinate().setCoordinate(units_1, units_1, units_1, new DoubleVector(new double[] {-0.51, 0.0, -15.0}));
    visView_2.getViewUpCoordinate().setCoordinate(units_1, units_1, units_1, new DoubleVector(new double[] {0.0, -1.0, 0.0}));
  
    VisView visView_3 = 
      simulation_0.getViewManager().createView();
    visView_3.copyProperties(visView_1);
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
    visView_5.copyProperties(visView_1);
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
    visView_7.getFocalPointCoordinate().setCoordinate(units_1, units_1, units_1, new DoubleVector(new double[] {-0.77, 0.0, 0.3}));
    visView_7.getPositionCoordinate().setCoordinate(units_1, units_1, units_1, new DoubleVector(new double[] {-8.0, -8.88, -6.0}));
    visView_7.getViewUpCoordinate().setCoordinate(units_1, units_1, units_1, new DoubleVector(new double[] {0.0, 0.0, 1.0}));
    visView_7.setProjectionMode(VisProjectionMode.PARALLEL);
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
    visView_15.getFocalPointCoordinate().setCoordinate(units_1, units_1, units_1, new DoubleVector(new double[] {-0.87, -0.200, 0.0}));
    visView_15.getPositionCoordinate().setCoordinate(units_1, units_1, units_1, new DoubleVector(new double[] {-0.51, 0.0, 15.0}));
    visView_15.getViewUpCoordinate().setCoordinate(units_1, units_1, units_1, new DoubleVector(new double[] {0.0, 1.0, 0.0}));

  VisView visView_16 = 
      simulation_0.getViewManager().createView();
    visView_16.copyProperties(visView_6);
    visView_16.setPresentationName("right_CptY");
    visView_16.getFocalPointCoordinate().setCoordinate(units_1, units_1, units_1, new DoubleVector(new double[] {-0.52, 0.0, 0.66}));
  }

}
