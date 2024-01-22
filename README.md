This is coursework for Tampere University course Software Design. The work was done in a group of four. My focus was mostly on the controllers.


- Prototype.fig: Figma file, a prototype for the user interface 

- COMP.SE.100_design_document: Design document which describes the overall structure of our project 


- class_diagram_V0.3.drawio: The same class diagram we have in the design document as a drawio file.

- class_diagram_V3.0.png: The same class diagram we have in the design document as a png file.

- WeatherApp is the application itself 

Apache NetBeans IDE is recommended to run this application since it what we use but it might also work with other Java IDEs. To run the program open the WeatherApp project in NetBeans and run it.

The class diagram png and drawio files are included because it could be hard to read ftom a screenshot without zooming. They are marked with the final_submission tag.

The program has four tabs. On "Weather" first write a Finnish city name and choose a date. Then press the radio buttons to choose which data to show in the graph. 

On "Road info" tab you need to write in coordinates from the given range. After giving the coordinates "Search" button needs to be clicked. After that choose a date if you want to see info about maintenance tasks or traffic messages. Road condition forecast doesn't need a date but it needs a street id so that has to be chosen from the dropdown list. Road condition forecast also needs you to choose the data shown from the other dropdown list. After these steps press "Go" and the graph will show. 

On "Combined" tab write in coordinates like on "Road info" or you can also choose a city from the dropdown list. After this click "Search". Then choose a date. If you want to see info about "weather & overall road condition" you need to choose a street id from the dropdown list. "weather & road maintenance" doesn't need a street id. After choosing these click a radio button and then "Go" to show the data.

On every tab there's "Load preference" and "Save preference". "Save preference" will ask you to give a name for the preference and then click "OK". "Load preference" needs you to choose a save from the dropdown and then click "OK".

On "Settings" tab you can load and save datasets by choosing the file from your own file explorer.
