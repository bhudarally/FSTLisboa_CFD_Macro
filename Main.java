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
    execute0();
  }

  	// ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Change Refinement ratio IF NEEDED

    double Ri = 1.0;
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

  private void execute0() {
	  
	Simulation simulation_0 = 
      getActiveSimulation();

    UserFieldFunction userFieldFunction_3 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();

    userFieldFunction_3.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);

    userFieldFunction_3.setPresentationName("Ri");

    userFieldFunction_3.setFunctionName("Ri");

    userFieldFunction_3.setDefinition(Double.toString(Ri)); 

    new File(resolvePath("Post_Processing\\Monitors\\Scenes")).mkdirs();
	  
	//new StarScript(getActiveRootObject(), new File(resolvePath("Modules_Macro\\a_ImportCAD.java"))).play();

	new StarScript(getActiveRootObject(), new File(resolvePath("Modules_Macro\\b_CSystem_RadFanRename.java"))).play();
	
    new StarScript(getActiveRootObject(), new File(resolvePath("Modules_Macro\\c_Input_Domain_Controls.java"))).play();

    simulation_0.saveState(resolvePath("Simulation\\FST10eCFD.sim"));

   new StarScript(getActiveRootObject(), new File(resolvePath("Modules_Macro\\d_SWrapper_FluidFanRad.java"))).play();

    new StarScript(getActiveRootObject(), new File(resolvePath("Modules_Macro\\e_Models_Regions_Interfaces.java"))).play();

        simulation_0.saveState(resolvePath("Simulation\\FST10eCFD.sim"));
	
	new StarScript(getActiveRootObject(), new File(resolvePath("Modules_Macro\\f_AutoMesh.java"))).play();
	
		simulation_0.saveState(resolvePath("Simulation\\FST10eCFD.sim"));

    new StarScript(getActiveRootObject(), new File(resolvePath("Modules_Macro\\g_ReportsMonitorsPlots.java"))).play();
	
	new StarScript(getActiveRootObject(), new File(resolvePath("Modules_Macro\\h_RunSolver.java"))).play();

    simulation_0.saveState(resolvePath("Simulation\\FST10eCFD.sim"));
	
	new StarScript(getActiveRootObject(), new File(resolvePath("Modules_Macro\\i_Cp_CoP.java"))).play();  

	new StarScript(getActiveRootObject(), new File(resolvePath("Modules_Macro\\j_ExportFiles.java"))).play();

    simulation_0.saveState(resolvePath("Simulation\\FST10eCFD.sim"));
	
	new StarScript(getActiveRootObject(), new File(resolvePath("Modules_Macro\\k_CpT_x.java"))).play();
	
	new StarScript(getActiveRootObject(), new File(resolvePath("Modules_Macro\\l_CpT_y.java"))).play();
	
	new StarScript(getActiveRootObject(), new File(resolvePath("Modules_Macro\\m_CpT_z.java"))).play();

    simulation_0.saveState(resolvePath("Simulation\\FST10eCFD.sim"));
	
	/*new StarScript(getActiveRootObject(), new File(resolvePath("Modules_Macro\\n_vy.java"))).play();
	
	new StarScript(getActiveRootObject(), new File(resolvePath("Modules_Macro\\o_vz.java"))).play();*/
	
	new StarScript(getActiveRootObject(), new File(resolvePath("Modules_Macro\\p_cf.java"))).play();

    simulation_0.saveState(resolvePath("Simulation\\FST10eCFD.sim"));

    //new StarScript(getActiveRootObject(), new File(resolvePath("Modules_Macro\\q_cf_streamlines.java"))).play();
	
	//new StarScript(getActiveRootObject(), new File(resolvePath("Modules_Macro\\r_Cpx_Cpz.java"))).play();
	
	new StarScript(getActiveRootObject(), new File(resolvePath("Modules_Macro\\s_Geometry.java"))).play();
	
	new StarScript(getActiveRootObject(), new File(resolvePath("Modules_Macro\\t_qcrit.java"))).play();

    simulation_0.saveState(resolvePath("Simulation\\FST10eCFD.sim"));

    new StarScript(getActiveRootObject(), new File(resolvePath("Modules_Macro\\u_CF_Plots.java"))).play();

    new StarScript(getActiveRootObject(), new File(resolvePath("Modules_Macro\\v_CP_Plots.java"))).play();

    //new StarScript(getActiveRootObject(), new File(resolvePath("Modules_Macro\\x_FEM_Info.java"))).play();

    new StarScript(getActiveRootObject(), new File(resolvePath("Modules_Macro\\y_3DScene_Export.java"))).play();
	
	simulation_0.saveState(resolvePath("Simulation\\FST10eCFD.sim"));
	
  }
}
