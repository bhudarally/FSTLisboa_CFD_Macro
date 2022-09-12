// Simcenter STAR-CCM+ macro: cadimport2.java
// Written by Simcenter STAR-CCM+ 15.06.008
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;
import star.vis.*;
import star.meshing.*;

public class b_CADimport extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    PartImportManager partImportManager_0 = 
      simulation_0.get(PartImportManager.class);

    partImportManager_0.importCadPart(resolvePath("..\\Geometry\\Car.stp"), "SharpEdges", 30.0, 2, true, 1.0E-5, true, false, false, false, true, NeoProperty.fromString("{\'STEP\': 1, \'NX\': 1, \'CATIAV5\': 1, \'SE\': 1, \'JT\': 1}"), true);

    MeshPartFactory meshPartFactory_0 = 
      simulation_0.get(MeshPartFactory.class);

    CompositePart compositePart_3 = 
      ((CompositePart) simulation_0.get(SimulationPartManager.class).getPart("CFD_FULL_ASSEMBLY"));

    CompositePart compositePart_2 = 
      ((CompositePart) compositePart_3.getChildParts().getPart("SU"));

    CompositePart compositePart_1 = 
      ((CompositePart) compositePart_2.getChildParts().getPart("SU_NO_TYRES"));

    CompositePart compositePart_0 = 
      ((CompositePart) compositePart_1.getChildParts().getPart("MONO"));

    CadPart cadPart_0 = 
      ((CadPart) compositePart_0.getChildParts().getPart("MONO"));

    CadPart cadPart_1 = 
      ((CadPart) compositePart_0.getChildParts().getPart("EL_BOX"));

    CadPart cadPart_2 = 
      ((CadPart) compositePart_0.getChildParts().getPart("BRACKET_TOP_50"));

    CadPart cadPart_3 = 
      ((CadPart) compositePart_0.getChildParts().getPart("BRACKET"));

    meshPartFactory_0.combineMeshParts(cadPart_0, new NeoObjectVector(new Object[] {cadPart_1, cadPart_2, cadPart_3}));

    PartSurface partSurface_0 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("COCKPIT"));

    PartSurface partSurface_1 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("ColoredFace1"));

    PartSurface partSurface_2 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("ColoredFace1 2"));

    PartSurface partSurface_3 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("ColoredFace1 3"));

    PartSurface partSurface_4 = 
      ((PartSurface) cadPart_0.getPartSurfaceManager().getPartSurface("ColoredFace1 4"));

    cadPart_0.combinePartSurfaces(new NeoObjectVector(new Object[] {partSurface_0, partSurface_1, partSurface_2, partSurface_3, partSurface_4}));

    partSurface_0.setPresentationName("MONO");

    CadPart cadPart_4 = 
      ((CadPart) compositePart_1.getChildParts().getPart("AADOWN_FL"));

    CadPart cadPart_5 = 
      ((CadPart) compositePart_1.getChildParts().getPart("AAUP_FL"));

    CadPart cadPart_6 = 
      ((CadPart) compositePart_1.getChildParts().getPart("DAMPER_FL"));

    CadPart cadPart_7 = 
      ((CadPart) compositePart_1.getChildParts().getPart("PUSH_FL"));

    CadPart cadPart_8 = 
      ((CadPart) compositePart_1.getChildParts().getPart("STEER_FL"));

    CadPart cadPart_9 = 
      ((CadPart) compositePart_1.getChildParts().getPart("UPR_FL"));

    meshPartFactory_0.combineMeshParts(cadPart_4, new NeoObjectVector(new Object[] {cadPart_5, cadPart_6, cadPart_7, cadPart_8, cadPart_9}));

    cadPart_4.setPresentationName("SU_FL");

    CadPart cadPart_10 = 
      ((CadPart) compositePart_1.getChildParts().getPart("AADOWN_FR"));

    CadPart cadPart_11 = 
      ((CadPart) compositePart_1.getChildParts().getPart("AAUP_FR"));

    CadPart cadPart_12 = 
      ((CadPart) compositePart_1.getChildParts().getPart("DAMPER_FR"));

    CadPart cadPart_13 = 
      ((CadPart) compositePart_1.getChildParts().getPart("PUSH_FR"));

    CadPart cadPart_14 = 
      ((CadPart) compositePart_1.getChildParts().getPart("STEER_FR"));

    CadPart cadPart_15 = 
      ((CadPart) compositePart_1.getChildParts().getPart("UPR_FR"));

    meshPartFactory_0.combineMeshParts(cadPart_10, new NeoObjectVector(new Object[] {cadPart_11, cadPart_12, cadPart_13, cadPart_14, cadPart_15}));

    cadPart_10.setPresentationName("SU_FR");

    CadPart cadPart_16 = 
      ((CadPart) compositePart_1.getChildParts().getPart("AADOWN_RL"));

    CadPart cadPart_17 = 
      ((CadPart) compositePart_1.getChildParts().getPart("AAUP_RL"));

    CadPart cadPart_18 = 
      ((CadPart) compositePart_1.getChildParts().getPart("DAMPER_RL"));

    CadPart cadPart_19 = 
      ((CadPart) compositePart_1.getChildParts().getPart("PUSH_RL"));

    CadPart cadPart_20 = 
      ((CadPart) compositePart_1.getChildParts().getPart("TIE_RL"));

    CadPart cadPart_21 = 
      ((CadPart) compositePart_1.getChildParts().getPart("UPR_RL"));

    meshPartFactory_0.combineMeshParts(cadPart_16, new NeoObjectVector(new Object[] {cadPart_17, cadPart_18, cadPart_19, cadPart_20, cadPart_21}));

    cadPart_16.setPresentationName("SU_RL");

    CadPart cadPart_22 = 
      ((CadPart) compositePart_1.getChildParts().getPart("AADOWN_RR"));

    CadPart cadPart_23 = 
      ((CadPart) compositePart_1.getChildParts().getPart("AAUP_RR"));

    CadPart cadPart_24 = 
      ((CadPart) compositePart_1.getChildParts().getPart("DAMPER_RR"));

    CadPart cadPart_25 = 
      ((CadPart) compositePart_1.getChildParts().getPart("PUSH_RR"));

    CadPart cadPart_26 = 
      ((CadPart) compositePart_1.getChildParts().getPart("TIE_RR"));

    CadPart cadPart_27 = 
      ((CadPart) compositePart_1.getChildParts().getPart("UPR_RR"));

    meshPartFactory_0.combineMeshParts(cadPart_22, new NeoObjectVector(new Object[] {cadPart_23, cadPart_24, cadPart_25, cadPart_26, cadPart_27}));

    cadPart_22.setPresentationName("SU_RR");

    CompositePart compositePart_4 = 
      ((CompositePart) compositePart_2.getChildParts().getPart("TYRE_FL"));

    CadPart cadPart_28 = 
      ((CadPart) compositePart_4.getChildParts().getPart("BODY-MOVE/COPY3[1]"));

    CadPart cadPart_29 = 
      ((CadPart) compositePart_4.getChildParts().getPart("BODY-MOVE/COPY3[2]"));

    meshPartFactory_0.combineMeshParts(cadPart_28, new NeoObjectVector(new Object[] {cadPart_29}));

    cadPart_28.setPresentationName("TYRE_FL");

    PartSurface partSurface_5 = 
      ((PartSurface) cadPart_28.getPartSurfaceManager().getPartSurface("ColoredFace1"));

    PartSurface partSurface_6 = 
      ((PartSurface) cadPart_28.getPartSurfaceManager().getPartSurface("ColoredFace1 2"));

    cadPart_28.combinePartSurfaces(new NeoObjectVector(new Object[] {partSurface_5, partSurface_6}));

    partSurface_5.setPresentationName("Faces");

    CompositePart compositePart_5 = 
      ((CompositePart) compositePart_2.getChildParts().getPart("TYRE_FR"));

    CadPart cadPart_30 = 
      ((CadPart) compositePart_5.getChildParts().getPart("BODY-MOVE/COPY3[1]"));

    CadPart cadPart_31 = 
      ((CadPart) compositePart_5.getChildParts().getPart("BODY-MOVE/COPY3[2]"));

    meshPartFactory_0.combineMeshParts(cadPart_30, new NeoObjectVector(new Object[] {cadPart_31}));

    cadPart_30.setPresentationName("TYRE_FR");

    PartSurface partSurface_7 = 
      ((PartSurface) cadPart_30.getPartSurfaceManager().getPartSurface("ColoredFace1"));

    PartSurface partSurface_8 = 
      ((PartSurface) cadPart_30.getPartSurfaceManager().getPartSurface("ColoredFace1 2"));

    cadPart_30.combinePartSurfaces(new NeoObjectVector(new Object[] {partSurface_7, partSurface_8}));

    partSurface_7.setPresentationName("Faces");

    CompositePart compositePart_6 = 
      ((CompositePart) compositePart_2.getChildParts().getPart("TYRE_RL"));

    CadPart cadPart_32 = 
      ((CadPart) compositePart_6.getChildParts().getPart("BODY-MOVE/COPY3[1]"));

    CadPart cadPart_33 = 
      ((CadPart) compositePart_6.getChildParts().getPart("BODY-MOVE/COPY3[2]"));

    meshPartFactory_0.combineMeshParts(cadPart_32, new NeoObjectVector(new Object[] {cadPart_33}));

    cadPart_32.setPresentationName("TYRE_RL");

    PartSurface partSurface_9 = 
      ((PartSurface) cadPart_32.getPartSurfaceManager().getPartSurface("ColoredFace1"));

    PartSurface partSurface_10 = 
      ((PartSurface) cadPart_32.getPartSurfaceManager().getPartSurface("ColoredFace1 2"));

    cadPart_32.combinePartSurfaces(new NeoObjectVector(new Object[] {partSurface_9, partSurface_10}));

    partSurface_9.setPresentationName("Faces");

    CompositePart compositePart_7 = 
      ((CompositePart) compositePart_2.getChildParts().getPart("TYRE_RR"));

    CadPart cadPart_34 = 
      ((CadPart) compositePart_7.getChildParts().getPart("BODY-MOVE/COPY3[1]"));

    CadPart cadPart_35 = 
      ((CadPart) compositePart_7.getChildParts().getPart("BODY-MOVE/COPY3[2]"));

    meshPartFactory_0.combineMeshParts(cadPart_34, new NeoObjectVector(new Object[] {cadPart_35}));

    cadPart_34.setPresentationName("TYRE_RR");

    PartSurface partSurface_11 = 
      ((PartSurface) cadPart_34.getPartSurfaceManager().getPartSurface("ColoredFace1"));

    PartSurface partSurface_12 = 
      ((PartSurface) cadPart_34.getPartSurfaceManager().getPartSurface("ColoredFace1 2"));

    cadPart_34.combinePartSurfaces(new NeoObjectVector(new Object[] {partSurface_11, partSurface_12}));

    partSurface_11.setPresentationName("Faces");

  }
}
