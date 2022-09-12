// STAR-CCM+ macro: a_ImportCAD.java
// Written by STAR-CCM+ 14.04.011
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;
import star.vis.*;
import star.meshing.*;

public class a_ImportCAD extends StarMacro {

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

    scene_0.initializeAndWait();

    PartDisplayer partDisplayer_0 = 
      ((PartDisplayer) scene_0.getDisplayerManager().getDisplayer("Outline 1"));

    partDisplayer_0.initialize();

    PartDisplayer partDisplayer_1 = 
      ((PartDisplayer) scene_0.getDisplayerManager().getDisplayer("Geometry 1"));

    partDisplayer_1.initialize();

    SceneUpdate sceneUpdate_0 = 
      scene_0.getSceneUpdate();

    HardcopyProperties hardcopyProperties_0 = 
      sceneUpdate_0.getHardcopyProperties();
	  
	CompositePart compositePart_0 = 
      ((CompositePart) simulation_0.get(SimulationPartManager.class).getPart("Car"));

    CadPart cadPart_3 = 
      ((CadPart) compositePart_0.getChildParts().getPart("Mono"));

    PartSurface partSurface_7 = 
      ((PartSurface) cadPart_3.getPartSurfaceManager().getPartSurface("Faces"));

    PartCurve partCurve_0 = 
      cadPart_3.getPartCurveManager().getPartCurve("Edges");

    cadPart_3.getPartSurfaceManager().splitPartSurfacesByPartCurves(new NeoObjectVector(new Object[] {partSurface_7}), new NeoObjectVector(new Object[] {partCurve_0}));

    PartSurface partSurface_923 = 
      ((PartSurface) cadPart_3.getPartSurfaceManager().getPartSurface("Faces 15"));

    PartSurface partSurface_924 = 
      ((PartSurface) cadPart_3.getPartSurfaceManager().getPartSurface("Faces 23"));

    PartSurface partSurface_925 = 
      ((PartSurface) cadPart_3.getPartSurfaceManager().getPartSurface("Faces 8"));

    PartSurface partSurface_926 = 
      ((PartSurface) cadPart_3.getPartSurfaceManager().getPartSurface("Faces 22"));

    PartSurface partSurface_927 = 
      ((PartSurface) cadPart_3.getPartSurfaceManager().getPartSurface("Faces 13"));

    PartSurface partSurface_928 = 
      ((PartSurface) cadPart_3.getPartSurfaceManager().getPartSurface("Faces 31"));

    PartSurface partSurface_929 = 
      ((PartSurface) cadPart_3.getPartSurfaceManager().getPartSurface("Faces 4"));

    PartSurface partSurface_930 = 
      ((PartSurface) cadPart_3.getPartSurfaceManager().getPartSurface("Faces 24"));

    PartSurface partSurface_931 = 
      ((PartSurface) cadPart_3.getPartSurfaceManager().getPartSurface("Faces 10"));

    PartSurface partSurface_932 = 
      ((PartSurface) cadPart_3.getPartSurfaceManager().getPartSurface("Faces 28"));

    PartSurface partSurface_933 = 
      ((PartSurface) cadPart_3.getPartSurfaceManager().getPartSurface("Faces 12"));

    PartSurface partSurface_934 = 
      ((PartSurface) cadPart_3.getPartSurfaceManager().getPartSurface("Faces 29"));

    PartSurface partSurface_935 = 
      ((PartSurface) cadPart_3.getPartSurfaceManager().getPartSurface("Faces 14"));

    PartSurface partSurface_936 = 
      ((PartSurface) cadPart_3.getPartSurfaceManager().getPartSurface("Faces 26"));

    PartSurface partSurface_937 = 
      ((PartSurface) cadPart_3.getPartSurfaceManager().getPartSurface("Faces 6"));

    PartSurface partSurface_938 = 
      ((PartSurface) cadPart_3.getPartSurfaceManager().getPartSurface("Faces 21"));

    PartSurface partSurface_939 = 
      ((PartSurface) cadPart_3.getPartSurfaceManager().getPartSurface("Faces 27"));

    PartSurface partSurface_940 = 
      ((PartSurface) cadPart_3.getPartSurfaceManager().getPartSurface("Faces 7"));

    PartSurface partSurface_941 = 
      ((PartSurface) cadPart_3.getPartSurfaceManager().getPartSurface("Faces 11"));

    PartSurface partSurface_942 = 
      ((PartSurface) cadPart_3.getPartSurfaceManager().getPartSurface("Faces 30"));

    PartSurface partSurface_943 = 
      ((PartSurface) cadPart_3.getPartSurfaceManager().getPartSurface("Faces 25"));

    PartSurface partSurface_944 = 
      ((PartSurface) cadPart_3.getPartSurfaceManager().getPartSurface("Faces 5"));

    PartSurface partSurface_945 = 
      ((PartSurface) cadPart_3.getPartSurfaceManager().getPartSurface("Faces 9"));

    PartSurface partSurface_946 = 
      ((PartSurface) cadPart_3.getPartSurfaceManager().getPartSurface("Faces 20"));

    cadPart_3.combinePartSurfaces(new NeoObjectVector(new Object[] {partSurface_923, partSurface_924, partSurface_925, partSurface_926, partSurface_927, partSurface_928, partSurface_929, partSurface_930, partSurface_931, partSurface_932, partSurface_933, partSurface_934, partSurface_935, partSurface_936, partSurface_937, partSurface_938, partSurface_939, partSurface_940, partSurface_941, partSurface_942, partSurface_943, partSurface_944, partSurface_945, partSurface_946}));

    partSurface_923.setPresentationName("Mono_SU");

    PartSurface partSurface_947 = 
      ((PartSurface) cadPart_3.getPartSurfaceManager().getPartSurface("Faces 17"));

    PartSurface partSurface_948 = 
      ((PartSurface) cadPart_3.getPartSurfaceManager().getPartSurface("Faces 18"));

    PartSurface partSurface_949 = 
      ((PartSurface) cadPart_3.getPartSurfaceManager().getPartSurface("Faces 19"));

    cadPart_3.combinePartSurfaces(new NeoObjectVector(new Object[] {partSurface_947, partSurface_948, partSurface_949}));

    partSurface_947.setPresentationName("Cockpit");

      /*PartSurface partSurface_950 = 
      ((PartSurface) cadPart_3.getPartSurfaceManager().getPartSurface("Faces 2"));

    PartSurface partSurface_951 = 
      ((PartSurface) cadPart_3.getPartSurfaceManager().getPartSurface("Faces 3"));

    PartSurface partSurface_952 = 
      ((PartSurface) cadPart_3.getPartSurfaceManager().getPartSurface("Faces 16"));

    PartSurface partSurface_953 = 
      ((PartSurface) cadPart_3.getPartSurfaceManager().getPartSurface("Faces 32"));

    PartSurface partSurface_954 = 
      ((PartSurface) cadPart_3.getPartSurfaceManager().getPartSurface("Faces 33"));

    PartSurface partSurface_955 = 
      ((PartSurface) cadPart_3.getPartSurfaceManager().getPartSurface("Faces 34"));*/

    try {

      PartSurface partSurface_950 = 
      ((PartSurface) cadPart_3.getPartSurfaceManager().getPartSurface("Faces 2"));

      cadPart_3.combinePartSurfaces(new NeoObjectVector(new Object[] {partSurface_7, partSurface_950}));
  }
  catch (Exception e){
        
  }

    try {

      PartSurface partSurface_951 = 
      ((PartSurface) cadPart_3.getPartSurfaceManager().getPartSurface("Faces 3"));

      cadPart_3.combinePartSurfaces(new NeoObjectVector(new Object[] {partSurface_7, partSurface_951}));
  }
  catch (Exception e){
        
  }

    try {

      PartSurface partSurface_952 = 
      ((PartSurface) cadPart_3.getPartSurfaceManager().getPartSurface("Faces 16"));

      cadPart_3.combinePartSurfaces(new NeoObjectVector(new Object[] {partSurface_7, partSurface_952}));
  }
  catch (Exception e){
        
  }

    try {

      PartSurface partSurface_953 = 
      ((PartSurface) cadPart_3.getPartSurfaceManager().getPartSurface("Faces 32"));

      cadPart_3.combinePartSurfaces(new NeoObjectVector(new Object[] {partSurface_7, partSurface_953}));
  }
  catch (Exception e){
        
  }

    try {

      PartSurface partSurface_954 = 
      ((PartSurface) cadPart_3.getPartSurfaceManager().getPartSurface("Faces 33"));

      cadPart_3.combinePartSurfaces(new NeoObjectVector(new Object[] {partSurface_7, partSurface_954}));
  }
  catch (Exception e){
        
  }  

    try {

      PartSurface partSurface_955 = 
      ((PartSurface) cadPart_3.getPartSurfaceManager().getPartSurface("Faces 34"));

      cadPart_3.combinePartSurfaces(new NeoObjectVector(new Object[] {partSurface_7, partSurface_955}));
  }
  catch (Exception e){
        
  }

  try {

      PartSurface partSurface_956 = 
      ((PartSurface) cadPart_3.getPartSurfaceManager().getPartSurface("Faces 35"));

      cadPart_3.combinePartSurfaces(new NeoObjectVector(new Object[] {partSurface_7, partSurface_956}));
  }
  catch (Exception e){
        
  }

  try {

      PartSurface partSurface_957 = 
      ((PartSurface) cadPart_3.getPartSurfaceManager().getPartSurface("Faces 36"));

      cadPart_3.combinePartSurfaces(new NeoObjectVector(new Object[] {partSurface_7, partSurface_957}));
  }
  catch (Exception e){
        
  }

  try {

      PartSurface partSurface_958 = 
      ((PartSurface) cadPart_3.getPartSurfaceManager().getPartSurface("Faces 37"));

      cadPart_3.combinePartSurfaces(new NeoObjectVector(new Object[] {partSurface_7, partSurface_958}));
  }
  catch (Exception e){
        
  }

  try {

      PartSurface partSurface_959 = 
      ((PartSurface) cadPart_3.getPartSurfaceManager().getPartSurface("Faces 38"));

      cadPart_3.combinePartSurfaces(new NeoObjectVector(new Object[] {partSurface_7, partSurface_959}));
  }
  catch (Exception e){
        
  }

  try {

      PartSurface partSurface_960 = 
      ((PartSurface) cadPart_3.getPartSurfaceManager().getPartSurface("Faces 39"));

      cadPart_3.combinePartSurfaces(new NeoObjectVector(new Object[] {partSurface_7, partSurface_960}));
  }
  catch (Exception e){
        
  }

    partSurface_7.setPresentationName("Mono");

    scene_0.resetCamera();
  }
}
