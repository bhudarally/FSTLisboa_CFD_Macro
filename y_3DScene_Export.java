// STAR-CCM+ macro: thing.java
// Written by STAR-CCM+ 15.02.009
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;
import star.vis.*;

public class y_3DScene_Export extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    SimpleAnnotation simpleAnnotation_0 = 
      ((SimpleAnnotation) simulation_0.getAnnotationManager().getObject("Description"));

try {

    Scene scene_0 = 
      simulation_0.getSceneManager().getScene("Cf");

     ScalarDisplayer scalarDisplayer_0 = 
      ((ScalarDisplayer) scene_0.getDisplayerManager().getDisplayer("Scalar 1"));

    scalarDisplayer_0.initialize();

    StreamDisplayer streamDisplayer_0 = 
      ((StreamDisplayer) scene_0.getDisplayerManager().getDisplayer("Streamline Stream 1"));

    streamDisplayer_0.initialize();

    scene_0.export3DSceneFileAndWait(resolvePath("..\\Post_Processing\\CF\\CF.sce"), "CF", simpleAnnotation_0.getText(), false, true);

    }

      catch (Exception e){

     }

    Scene scene_1 = 
      simulation_0.getSceneManager().getScene("Cf_x");

    ScalarDisplayer scalarDisplayer_1 = 
      ((ScalarDisplayer) scene_1.getDisplayerManager().getDisplayer("Scalar 1"));

    scalarDisplayer_1.initialize();

    Scene scene_2 = 
      simulation_0.getSceneManager().getScene("CP");

    ScalarDisplayer scalarDisplayer_2 = 
      ((ScalarDisplayer) scene_2.getDisplayerManager().getDisplayer("Scalar 1"));

    scalarDisplayer_2.initialize();

    Scene scene_3 = 
      simulation_0.getSceneManager().getScene("Domain");

    PartDisplayer partDisplayer_0 = 
      ((PartDisplayer) scene_3.getDisplayerManager().getDisplayer("Domain"));

    partDisplayer_0.initialize();

    PartDisplayer partDisplayer_1 = 
      ((PartDisplayer) scene_3.getDisplayerManager().getDisplayer("Car"));

    partDisplayer_1.initialize();

    PartDisplayer partDisplayer_2 = 
      ((PartDisplayer) scene_3.getDisplayerManager().getDisplayer("Mesh 1"));

    Scene scene_4 = 
      simulation_0.getSceneManager().getScene("Geometry");

      PartDisplayer partDisplayer_3 = 
      ((PartDisplayer) scene_4.getDisplayerManager().getDisplayer("Geometry 1"));

    partDisplayer_3.initialize();

    Scene scene_5 = 
      simulation_0.getSceneManager().getScene("Q_Criterion");

    PartDisplayer partDisplayer_4 = 
      ((PartDisplayer) scene_5.getDisplayerManager().getDisplayer("Geometry 1"));

    partDisplayer_4.initialize();

    ScalarDisplayer scalarDisplayer_4 = 
      ((ScalarDisplayer) scene_5.getDisplayerManager().getDisplayer("Scalar 1"));

    scalarDisplayer_4.initialize();

    Scene scene_6 = 
      simulation_0.getSceneManager().getScene("y+");

    ScalarDisplayer scalarDisplayer_5 = 
      ((ScalarDisplayer) scene_6.getDisplayerManager().getDisplayer("Scalar 1"));

    scalarDisplayer_5.initialize();

    scene_1.export3DSceneFileAndWait(resolvePath("..\\Post_Processing\\CF_X\\CF_X.sce"), "CF_X", simpleAnnotation_0.getText(), false, true);

    scene_2.export3DSceneFileAndWait(resolvePath("..\\Post_Processing\\CP\\CP.sce"), "CP", simpleAnnotation_0.getText(), false, true);

    scene_3.export3DSceneFileAndWait(resolvePath("..\\Geometry\\Overview\\Domain.sce"), "Domain", simpleAnnotation_0.getText(), false, true);

    scene_4.export3DSceneFileAndWait(resolvePath("..\\Geometry\\Overview\\Geometry.sce"), "Geometry", simpleAnnotation_0.getText(), false, true);

    scene_5.export3DSceneFileAndWait(resolvePath("..\\Post_Processing\\Qcrit\\Qcrit.sce"), "Qcrit", simpleAnnotation_0.getText(), false, true);

    scene_6.export3DSceneFileAndWait(resolvePath("..\\Post_Processing\\Y+\\Y+.sce"), "Y+", simpleAnnotation_0.getText(), false, true);
  }
}
