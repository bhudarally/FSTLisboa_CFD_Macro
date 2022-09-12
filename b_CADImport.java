// STAR-CCM+ macro: b_CSystem_RadFanRename.java
// Written by STAR-CCM+ 14.04.011
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;
import star.cadmodeler.*;
import star.meshing.*;
import star.base.report.*;
import java.io.*;
import java.nio.*;

public class b_CADImport extends StarMacro {

  public void execute() {
    CADImport();
  }

  private void CADImport() {

    Simulation simulation_0 = 
      getActiveSimulation();

    PartImportManager partImportManager_0 = 
      simulation_0.get(PartImportManager.class);
    partImportManager_0.importCadPart(resolvePath("..\\Geometry\\Car.SLDPRT"), "SharpEdges", 30.0, 2, false, 1.0E-5, true, false, false, false);
    
  CompositePart compositePart_0 = 
      ((CompositePart) simulation_0.get(SimulationPartManager.class).getPart("Car"));

    // SU Split //
    CadPart cadPart_0 = 
      ((CadPart) compositePart_0.getChildParts().getPart("Mono"));
    PartSurface partSurface_70 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("Faces"));
    PartCurve partCurve_0 = 
      cadPart_0.getPartCurveManager().getPartCurve("Edges");
    cadPart_0.getPartSurfaceManager().splitPartSurfacesByPartCurves(new NeoObjectVector(new Object[] {partSurface_70}), new NeoObjectVector(new Object[] {partCurve_0}));
    PartSurface partSurface_7 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("Faces 8"));
    PartSurface partSurface_8 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("Faces 6"));
    PartSurface partSurface_9 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("Faces 5"));
    PartSurface partSurface_10 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("Faces 4"));
    PartSurface partSurface_11 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("Faces 15"));
    PartSurface partSurface_12 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("Faces 12"));
    PartSurface partSurface_13 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("Faces 10"));
    PartSurface partSurface_14 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("Faces 7"));
    PartSurface partSurface_15 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("Faces 9"));
    PartSurface partSurface_16 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("Faces 11"));
    PartSurface partSurface_17 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("Faces 14"));
    PartSurface partSurface_18 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("Faces 13"));
    PartSurface partSurface_19 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("Faces 23"));
    PartSurface partSurface_20 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("Faces 24"));
    PartSurface partSurface_21 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("Faces 25"));
    PartSurface partSurface_22 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("Faces 26"));
    PartSurface partSurface_23 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("Faces 27"));
    PartSurface partSurface_24 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("Faces 28"));
    PartSurface partSurface_25 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("Faces 29"));
    PartSurface partSurface_26 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("Faces 30"));
    PartSurface partSurface_27 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("Faces 31"));
    PartSurface partSurface_28 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("Faces 32"));
    PartSurface partSurface_29 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("Faces 33"));
    PartSurface partSurface_30 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("Faces 34"));
    cadPart_0.combinePartSurfaces(new NeoObjectVector(new Object[] {partSurface_7, partSurface_8, partSurface_9, partSurface_10, partSurface_11, partSurface_12, partSurface_13, partSurface_14, partSurface_15, partSurface_16, partSurface_17, partSurface_18, partSurface_19, partSurface_20, partSurface_21, partSurface_22, partSurface_23, partSurface_24, partSurface_25, partSurface_26, partSurface_27, partSurface_28, partSurface_29, partSurface_30}));
    partSurface_7.setPresentationName("Mono_SU");

        // Cockpit Split //
    PartSurface partSurface_31 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("Faces 17"));
    PartSurface partSurface_32 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("Faces 16"));
    cadPart_0.combinePartSurfaces(new NeoObjectVector(new Object[] {partSurface_31, partSurface_32}));
    partSurface_31.setPresentationName("Cockpit");

        // Mono Split //
    PartSurface partSurface_33 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("Faces 2"));
    PartSurface partSurface_34 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("Faces 3"));
    PartSurface partSurface_35 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("Faces 18"));
    PartSurface partSurface_36 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("Faces 19"));
    PartSurface partSurface_37 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("Faces 20"));
    PartSurface partSurface_38 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("Faces 21"));
    PartSurface partSurface_39 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("Faces 22"));
    cadPart_0.combinePartSurfaces(new NeoObjectVector(new Object[] {partSurface_70, partSurface_33, partSurface_34, partSurface_35, partSurface_36, partSurface_37, partSurface_38, partSurface_39}));
    partSurface_70.setPresentationName("Mono");

  }

}