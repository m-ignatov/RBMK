# Nuclear Reactor Control System
## Functionality
The system consists of nuclear reactors and control units (CU).
Each CU can be attached to many reactors on the condition that 
the total reactor power does not exceeded CU's capacity.
A CU can control a nuclear reactor by changing its state - running or stopped.
If a reactor is in stopped state, it does not consume any power from the CU.
CRUD operations are available for both the reactors and the CUs via the REST API.

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