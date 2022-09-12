// STAR-CCM+ macro: A_MAIN.java
// Written by STAR-CCM+ 14.04.011
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;
import java.io.*;
import star.vis.*;
import star.meshing.*;

public class A_MAIN extends StarMacro {

  public void execute() {
    Macro_Modules_execution();
  }

  private void Macro_Modules_execution() {
	  
	Simulation simulation_0 = 
      getActiveSimulation();

	//{ // Create folders for Monitors and Simulation

    new File(resolvePath("..\\Visualization & Data\\Results\\Monitors\\Center of Loads")).mkdirs();
    new File(resolvePath("..\\Visualization & Data\\Results\\Monitors\\Cooling")).mkdirs();
    new File(resolvePath("..\\Visualization & Data\\Results\\Monitors\\DownF")).mkdirs();
    new File(resolvePath("..\\Visualization & Data\\Results\\Monitors\\Drag")).mkdirs();
    new File(resolvePath("..\\Visualization & Data\\Results\\Monitors\\Moments")).mkdirs();
    new File(resolvePath("..\\Visualization & Data\\Results\\Monitors\\SideF")).mkdirs();
    new File(resolvePath("..\\Visualization & Data\\Results\\Monitors\\Solver Time")).mkdirs();
    new File(resolvePath("..\\Visualization & Data\\Results\\Monitors\\Residuals")).mkdirs();
	
    new File(resolvePath("..\\Simulation")).mkdirs();
	
    new File(resolvePath("..\\Visualization & Data\\Results\\Reports")).mkdirs();
	//}
	
	//{ // Execute Modules Macro 

    new StarScript(getActiveRootObject(), new File(resolvePath("a_Setup.java"))).play();
    new StarScript(getActiveRootObject(), new File(resolvePath("b_Geometry.java"))).play();
    new StarScript(getActiveRootObject(), new File(resolvePath("c_Mesh.java"))).play();
    new StarScript(getActiveRootObject(), new File(resolvePath("d_Physics.java"))).play();
    new StarScript(getActiveRootObject(), new File(resolvePath("e_PostPro.java"))).play();
	//}
  }
}
