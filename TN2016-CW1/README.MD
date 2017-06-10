## About
This program is an implementation of A* algorithm. <br />
A client calls a taxi. From each Taxi, an A* algorithm is executed in order to find it`s distance from the client.<br />
After that the closest Taxi is appeared to the client.<br />
As heuristic function for A* we used it`s Euclidean distance from the client.<br />

## Input Files
You can see input files form in TN2016-CW1<br />
- nodes.csv is the file of the points in our theoretical graph. It was extracted from a map of Athens from QGIS<br />
- taxis.csv information about each taxi
- client.csv information about the client

## Output Files
- map.kml The output is a map stored in kml form and is the route of each taxi, pointing as red the route of the closest taxi
It`s in a format easily viewed by google maps
