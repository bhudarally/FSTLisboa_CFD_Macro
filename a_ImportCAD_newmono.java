// STAR-CCM+ macro: a_ImportCAD.java
// Written by STAR-CCM+ 14.04.011
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;
import star.vis.*;
import star.meshing.*;

public class a_ImportCAD_newmono extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    PartImportManager partImportManager_0 = 
      simulation_0.get(PartImportManager.class);

    partImportManager_0.importCadPart(resolvePath("..\\Geometry\\Car.SLDPRT"), "SharpEdges", 30.0, 2, false, 1.0E-5, true, false, false, false);

    simulation_0.getSceneManager().createGeometryScene("Geometry Scene", "Outline", "Geometry", 1);

    Scene scene_0 = 
      simulation_0.getSceneManager().getScene("Geometry Scene 1");

    PartDisplayer partDisplayer_0 = 
      ((PartDisplayer) scene_0.getDisplayerManager().getDisplayer("Outline 1"));

    CompositePart compositePart_0 = 
      ((CompositePart) simulation_0.get(SimulationPartManager.class).getPart("Car"));

    CadPart cadPart_0 = 
      ((CadPart) compositePart_0.getChildParts().getPart("Mono"));

    PartSurface partSurface_0 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("Faces"));

    partDisplayer_0.getHiddenParts().addObjects(partSurface_0);

    PartDisplayer partDisplayer_1 = 
      ((PartDisplayer) scene_0.getDisplayerManager().getDisplayer("Geometry 1"));

    partDisplayer_1.getHiddenParts().addObjects(partSurface_0);

    cadPart_0.splitPartSurfaceByPatch(partSurface_0, new IntVector(new int[] {1375, 1376, 1377, 1378, 1379, 1380, 1381}), "Cockpit");

    partDisplayer_1.getVisibleParts().addParts(partSurface_0);

    partDisplayer_1.getHiddenParts().addParts();

    partDisplayer_0.getVisibleParts().addParts(partSurface_0);

    partDisplayer_0.getHiddenParts().addParts();

    PartCurve partCurve_0 = 
      cadPart_0.getPartCurveManager().getPartCurve("Edges");

    cadPart_0.getPartSurfaceManager().splitPartSurfacesByPartCurves(new NeoObjectVector(new Object[] {partSurface_0}), new NeoObjectVector(new Object[] {partCurve_0}));

    PartSurface partSurface_1 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("Faces 3"));

    PartSurface partSurface_2 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("Faces 4"));

    PartSurface partSurface_3 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("Faces 5"));

    PartSurface partSurface_4 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("Faces 6"));

    PartSurface partSurface_5 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("Faces 7"));

    PartSurface partSurface_6 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("Faces 8"));

    PartSurface partSurface_7 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("Faces 9"));

    PartSurface partSurface_8 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("Faces 10"));

    PartSurface partSurface_9 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("Faces 11"));

    PartSurface partSurface_10 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("Faces 12"));

    PartSurface partSurface_11 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("Faces 13"));

    PartSurface partSurface_12 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("Faces 14"));

    PartSurface partSurface_13 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("Faces 15"));

    PartSurface partSurface_14 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("Faces 16"));

    PartSurface partSurface_15 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("Faces 17"));

    PartSurface partSurface_16 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("Faces 18"));

    PartSurface partSurface_17 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("Faces 19"));

    PartSurface partSurface_18 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("Faces 20"));

    PartSurface partSurface_19 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("Faces 21"));

    PartSurface partSurface_20 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("Faces 22"));

    PartSurface partSurface_21 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("Faces 23"));

    PartSurface partSurface_22 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("Faces 24"));

    PartSurface partSurface_23 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("Faces 25"));

    PartSurface partSurface_24 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("Faces 26"));

    PartSurface partSurface_25 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("Faces 27"));

    PartSurface partSurface_26 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("Faces 28"));

    PartSurface partSurface_27 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("Faces 29"));

    PartSurface partSurface_28 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("Faces 30"));

    cadPart_0.combinePartSurfaces(new NeoObjectVector(new Object[] {partSurface_1, partSurface_2, partSurface_3, partSurface_4, partSurface_5, partSurface_6, partSurface_7, partSurface_8, partSurface_9, partSurface_10, partSurface_11, partSurface_12, partSurface_13, partSurface_14, partSurface_15, partSurface_16, partSurface_17, partSurface_18, partSurface_19, partSurface_20, partSurface_21, partSurface_22, partSurface_23, partSurface_24, partSurface_25, partSurface_26, partSurface_27, partSurface_28}));

    PartSurface partSurface_29 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("Faces 2"));

    cadPart_0.combinePartSurfaces(new NeoObjectVector(new Object[] {partSurface_0, partSurface_29}));

    partSurface_0.setPresentationName("Mono");

    partSurface_1.setPresentationName("Mono_SU");
  }
}
