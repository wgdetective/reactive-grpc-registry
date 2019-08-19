#Reactive grpc registry example

Example of registry that will register each connected in runtime processor with grpc, and can send requests with grpc to them.


## Build and run:
To build execute: 
    
    ./gradlew build
    
To run registry execute following command:

    ./gradlew :registry:bootRun

To run processors execute following command (each run will create new processor):

    ./gradlew :processor:bootRun

##Usage:
After registering of processor in registry, it's able to send request throw registry to processor using grpc.

To see the list of connected processors execute:

    http://localhost:8090/registry/all
    
To send request to some processor execute:

    http://localhost:8090/registry/call?target=Processor-6cb4ef02-0799-4038-8a8e-ae1c7c3fdd59&body=fdsf
    
 where target is name of the processor.    


