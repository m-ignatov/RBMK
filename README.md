# Nuclear Reactor Control System
## Functionality
The system consists of nuclear reactors and control units (CU).
Each CU can be attached to many reactors on the condition that 
the total reactor power does not exceeded CU's capacity.
A CU can control a nuclear reactor by changing its state - running or stopped.
If a reactor is in stopped state, it does not consume any power from the CU.
CRUD operations are available for both the reactors and the CUs via the REST API.

## REST API
#### Reactors
GET 
- ``/reactors`` fetch all reactors  
- ``/reactors/{id}`` fetch reactor by id  

POST ``/reactors``  create new reactor  
PUT ``/reactors/{id}`` Edit reactor  
DELETE ``/reactors/{id}`` Delete reactor by id

#### Control units:
GET 
- ``/control-units`` fetch all control units  
-  ``/control-units/{id}`` fetch control unit by id
   
POST
- ``/control-units`` create control unit  
- ``/control-units/{id}/attach-reactor/{reactorId}`` attach reactor with reactorId to control unit with id  
- ``/control-units/{id}/detach-reactor/{reactorId}`` detach reactor with reactorId from control unit with id    
- ``/control-units/{id}/turn-on/{reactorId}`` turn on reactor with reactorId by control unit with id    
- ``/control-units/{id}/turn-off/{reactorId}``  turn off reactor with reactorId by control unit with id  

PUT ``/control-units/{id}`` edit control unit by id   
DELETE ``/control-units/{id}`` delete control unit by id  

## Prerequisites
#### Java SE 8
- ``JAVA_HOME`` and ``PATH`` environment variables should be set in the classpath
#### PostgreSQL
- You should have a PostgreSQL instance running.
- The database credentials could be manually set from ``application.properties``
## How to build and run

1. Open any CLI (cmd.exe) at the project's root directory.

2. Run ``mvnw clean package`` to build the executable *.jar* file.

3. On completion, you will be able to see the path of the *.jar* file.

    ``C:\Users\user\RBMK\target\rbmk-0.0.1-SNAPSHOT.jar``

4. Now run the following command with the path of the *.jar* file as an argument:

    ``java -jar C:\Users\user\RBMK\target\rbmk-0.0.1-SNAPSHOT.jar`` 

5. The application should now be starting.