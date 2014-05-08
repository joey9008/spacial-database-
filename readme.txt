



Explaination:	I used eclipse and oracle 12c ,so I downloaded and used the ojdbc7.jar from the 
			oracle website. The way to run this program is the common way. First, use the createdb.sql
				to create the tables,indexes in the oracle database. Second, use Populate.java to insert the
				data(three files: buildings.xy,students.xy,announcementSystems.xy) to the  table. Third, 
				run Hw2.java to get the GUI and then you can deal with it. The DBexecute.java is a class 
				specifically deal with the connection between the oracle database and the java eclipse.


Resolution:  2.Point Query: 
		2.1 students: Use SDO_INSIDE to select the students that are in the point circle. Use 			     			      SDO_GEOM.SDO_DISTANCE to order the result and so we can select the nearest 
			      student.
		2.2 buildings: Use SDO_ANYINTERACT to select the buildings that are intersect with the circle.
			       Use SDO_GEOM.SDO_DISTANCE to compare buildings' area and the point we select to order 			       the result.
		2.3 announcements: Similar with the 2.2.

	     3.Range Query:
		3.1 students: Use SDO_INSIDE to select the students that are in the selected range.
		3.2 buildings:Use SDO_ANYINTERACT to select the buildings that are intersect with the range.
		3.3 announcements: Similar with the 3.2.
		
	     4.Surrounding Students Query: When the mouse clicked on the map,use SDO_NN to select the nearest 
				  	   announcement to the point. Then use SDO_INSIDE to select the students
					   in the announcement circle.


	     5.Emergency Query: First, to select the nearest announcement to the clicked point similar with 4.
				Second, use SDO_INSIDE to select the students in the announcement circle similar 
				with 4. Third, for every selected student, use SDO_NN to get the second nearest
				announcement and draw it.Use HashMap to map the key(Announcement) with the value
				(students).Last use the hashmap's method get(key) to select sorted students and 
				draw them.
			      