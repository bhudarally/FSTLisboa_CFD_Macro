// STAR-CCM+ macro: Main.java
// Written by STAR-CCM+ 14.04.011
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;
import java.io.*;
import star.vis.*;
import star.meshing.*;

public class Main extends StarMacro {

  public void execute() {
    Main();
  }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

  private void Main() {
	  
	Simulation simulation_0 = 
      getActiveSimulation();

  new File(resolvePath("Post_Processing\\Monitors\\Scenes")).mkdirs();
	  
	new StarScript(getActiveRootObject(), new File(resolvePath("Modules_Macro\\a_SimulationSetup.java"))).play();

	simulation_0.saveState(resolvePath("Simulation\\FST11CFD.sim"));

	new StarScript(getActiveRootObject(), new File(resolvePath("Modules_Macro\\b_CADImport.java"))).play();

	new StarScript(getActiveRootObject(), new File(resolvePath("Modules_Macro\\c_GeometryPreparation.java"))).play();

	simulation_0.saveState(resolvePath("Simulation\\FST11CFD.sim"));
	
  new StarScript(getActiveRootObject(), new File(resolvePath("Modules_Macro\\d_Domain_SimChecks.java"))).play();

  	simulation_0.saveState(resolvePath("Simulation\\FST11CFD.sim"));

  new StarScript(getActiveRootObject(), new File(resolvePath("Modules_Macro\\e_WrapperMesh.java"))).play();

  new StarScript(getActiveRootObject(), new File(resolvePath("Modules_Macro\\f_Physics.java"))).play();
	
	new StarScript(getActiveRootObject(), new File(resolvePath("Modules_Macro\\g_Reports.java"))).play();

  new StarScript(getActiveRootObject(), new File(resolvePath("Modules_Macro\\h_Solver.java"))).play();
	
	new StarScript(getActiveRootObject(), new File(resolvePath("Modules_Macro\\i_PostProcessing.java"))).play();

	//new StarScript(getActiveRootObject(), new File(resolvePath("Modules_Macro\\j_FEMReports.java"))).play();

  	simulation_0.saveState(resolvePath("Simulation\\FST11CFD.sim"));
	
  }
}
