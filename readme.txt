           
   <<<<<<<<<<<<<<<<<<<<<<< MACRO StarCCM+ FST Symmetric Simulation   >>>>>>>>>>>>>>>>>>>>>>>> 


------------------------------ATTENTION TO CAPITAL LETTERS AND BLANK SPACES--------------------------------------------

Status: Complete

0. Copy folders all folders "Modules_Macro", "Geometry", "Simulation" and file "Main.java" into your simulation folder

1. Adjust ride heights and then run solidworks Macro for full car : "Half Car - CAD_CFD_Preparation.swp"

					OR
	
	Combine bodies, split suspension y=+-270mm and rename manually solid bodies in Solidworks Part to: (no blank spaces) 
	-> Diff_Back       
	-> Diff_Lateral
	-> Diff_Lateral_LID
	-> Driver
	-> Fan
	-> FW_Main
	-> FW_EP
	-> FW_Flap
	-> FW_Mid
	-> FW_Sup
	-> HR
	-> Main_Hoop
	-> Mono
	-> BH
	-> Radiator
	-> RW1
	-> RW2
	-> RW3	
	-> RW_EP
	-> RW_Support
	-> SC0
	-> SC1	
	-> SC2
	-> SC3
	-> SC_EP
	-> SU_Front
	-> SU_Rear
	-> Tyre_Front
	-> Tyre_Rear
	OPTIONAL PARTS - USE IF YOU ARE DEVELOPING A NEW COMPONENT
	-> Misc
	-> Misc2

	*TIP*
	1.1 If your Assembly as a part and only change the name of Solid Bodies, name will remain the same if there are no features created.
	In this case, you can play the main with all sub macros and there is no need to rename manually. 

3. Save solidworks part file as "Car.SLDPRT" inside Geometry Folder

4. Create a new Star CCM+ file with Parallel on Local Host with 21 or 6 compute processes, depending on the workstation.

5a. Change the following module_macro: "c_Input_Domain_Controls" and choose the following inputs: 
	->Car Velocity (m/s)
	->Height of CoG (m)

5b. If necessary change the refinement value on the file "Main.java".

6. Play Macro: "Main.java" (Recommended disk space of 40GB)

7. Enjoy

----------------------------------------------------* COMMENTS *---------------------------------------------------------------------------
	

1. Data will be available in "Post_Processing" folder:
	-> It is possible to check convergence inside the folder "Monitors". If appropriate it is possible to average force values with
	the .csv files inside foldes "Files" or Reports.csv
	-> 14 different views for each of the following variables: Cp, CfX, CpX , CpZ and Q criterion 	
	-> Images for Cp along x/y/z and velocity along y/z
	-> Cp and Cf over the various aerodynamic elements
	-> Accumulated force plots
	-> Mass Flow rate and Pressure Drop in both radiator and fan
	-> Total Solver time and per iteration exported as images/scenes/file
	-> No. Elements also present in Reports file
	

2. Geometry folder also contains 14 views for easier visualization of configuration in study

3. Information for the FEM simulations is also extracted into csv files.

4. Simulation will be automatically saved aswell

5. Allows the use of extra bodies on the car (Misc and Misc2) if developing a new aerodynamic component.



-------------------------------------------------* Changelog v1 Corner *------------------------------------------------------------------------


1. All images of Total Pressure Coefficient now display velocity vectors with LIC for easier visualization of flow behaviour and energy 
	at the same time
	
2. StarCCM+ annotation with water mark is now replaced by FSTLisboa logo either black or white

3. Colorbar is now smaller (there is no reason not to be), positioned vertically and with numbers on the left side for 
	better usage of all window

4. Mono Forces were also included in Reports (Drag and Downforce)
	
5. Residual scene automatically opens itself which it didn't previously. All scenes of post process are now closed to 
	try reducing computacional resources needed 

6. All views were also repositioned due to the new positioning of geometry, color bar and better use of window

8. Images created along the car have now the coordinate value in the bottom left side corner and in file name

9. While simulation is running it is now saved when reaching the hundreds value to prevent data loss if pc lacks resources.
	
10. When simulation reaches 100 iterations a y+ scene is created and images are saved into "Post_processing//Check//Y+" folder to
	check if the simulation is running correctly.

11. Mass flow rate and Pressure Drop Report, Monitor and Plot

12. Added Report of No. Elements

13. Report of Total Solver elapsed time

14. Added report, monitor and plot of solver iteration elapsed time

15. When domain is created, images of the domain & refinements and all coordinate systems created are plotted into a folder 
	"Post_Processing//Check//Domain & Axis". These images should be checked to see if domain is correct accordingly to the 
	radius input, and check if the the coordinate systems of wheels, radiators, fans and CoG are all correct.

16. After the 1st iteration, images of ground and Air velocity are plot to be able to check if all boundary conditions are correct
	"Post_processing//Check//Boundary Conditions"

18. Wheel angular velocity depends on the wheel center position and orientation, car velocity. Orientation
	of this vector is dependant on the face renamed as wheel center. 

19. Weight distribution changes the position of the CoG and consequently changes the position of the corner center

20. All force and Moment Reports and Coefficients are taken in CoG position

21. Car setup is included in Reports.csv to check setup of the simulation in an easier way

22. Adimensionalization is done with the corresponding free stream air velocity in each position

23. Radiator and fan surfaces are renamed automatically (works for FST10e version)

-------------------------------------------------* Changelog v2 *------------------------------------------------------------------------

1. Changed to a polyhedral mesh

2. Added the ability to run the same post processing file without having repeated field functions.

3. Added the option to change refinement ratio and wind speed.

4. Added Cp, Cf, Cf streamlines and accumulated force plots to the post processing.

5. Added auto renaming of the Mono surfaces in order to better adjust prism layer extrusion.

6. Automatic creation of the Post_Processing/Monitors/Scenes folder in order to avoid errors with the Scenes export.

7. Added Misc and Misc2 support for easier car development.

-------------------------------------------------* Changelog v2.1 *------------------------------------------------------------------------

1. Added automatic fillet of the contact patch of each wheel.

2. Closed the scenes before making the hardcopy, it makes the post processing slightly faster and allows it to work on the 4 core workstations.

3. Cleaned up the code.

4. Swapped adimensional velocity for regular velocity.

=======================================================================================================================================
Miguel Carreira
João Morgado
	