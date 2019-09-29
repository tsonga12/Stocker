##                                          Architecture (Updated)

Our application is designed with a 3-tier architecture.The three layers organized as the presentation tier, business/logic tier, and persistence tier, 
where each has been packaged and handled individually.

Presentation layer, which is responsible for displaying content and information, has four packages, Customer, Order, Product and Supplier. Each package 
contains classes to receive some input coming from the user, such as data used to create new customers/products, and requests to search for 
customer/product/supplier profiles. This layer passes the data/requests to the next layer, the logic layer for processing and receives data back 
to display to the user.

Logic layer, which contains the core capabilities of the application, has two packages, Business and Objects. The objects package defines all the details 
for customers/products/suppliers/orders such as name, id, email, phone number, tags, etc. The business package has classes that contain all the operation 
for each object, such as performing basic manipulations like search, insert, delete, and update. When the presentation layer makes a request, the logic layer will execute the corresponding functions to those operations to handle the requests. 
In order to handle requests involving data, the logic layer requests the database retrieve the data, the logic layer processes it, and then sends it to 
the presentation layer.

Persistence layer, has been updated with using the SQL-based database engine to implement real database storage by joining with HSQLDB as the only database 
source. The stub database implementation is removed due to the requirement of this iteration. The architecture for implementing the database stays the same
for the previous iterations which contains four classes corresponding to the database objects, the interface is also the same as the last iterations including
performing the retrieval actions such as add, search, update and delete directly on the storage, returning data to the logic layer, if necessary. For synchronization, 
the application package contains a service class which, creating a single database object for the whole application. The dependency injection which switches 
between stub data and the real storage one no longer exists due to the uniqueness of the database source (the stub implemetation has been removed).

In this iteration, we use the test-driven development approach which makes sure that entire business layer to be thoroughly tested. Besides having the 
unit tests like the last iterations, we also include the integration and acceptance tests for each class/feature of the application. For the unit tests,
we still use the junit and in addition, we add library called “Mokito” to mock the database instead of using the real one for the faster runtime.
Conversely, the integration tests use the real database source to test all communication between logic and persistence layers. When it comes to acceptance
tests, we use the well-known library called “espresso” to run the automated tests for each feature of the application, each feature has two automated tests
(create and delete) except the Tags which only has one test (editTag). 


                    +--------------------------------+--------------------------------+-----------------------------+
                    |                                |                                |                             |
                    |        Presentation            |       Business/Logic           |        Persistence          |
                    |                                |                                |                             |
                    |                                |                                |                             |
                    +-----------------------------------------------------------------------------------------------+
                    |Display profiles                |Perform calls to database to    |Create table for each object |
                    |Show the search options, results|process requests                |containing all attributes.    |
                    |Show the delete option          |                                |Data storage                 |
                    |Show the add option             |                                |                             |
                    |Show the update/edit option     |                                |                             |
                    |Show the logic results          |                                |                             |
                    |                                |                                |                             |
                    |                                |                                |                             |
                    |                                |                                |                             |
                    |                                |                                |                             |
                    |                                |                                |                             |
                    |                                |                                |                             |
                    |                                |                                |                             |
                    |                                |                                |                             |
                    |                                |                                |                             |
                    |                                |                                |                             |
                    |                                |                                |                             |
                    |                                |                                |                             |
                    |                                |                                |                             |
                    +--------------------------------+--------------------------------+-----------------------------+
                    |                                        Domain Model                                           |
                    |                                                                                               |
                    |          Searching for products, customers, suppliers, tags and past orders                   |
                    |          Adding new customers, products, suppliers, orders, tags                              |
                    |          Deleting customers, products, suppliers, orders                                      |
                    |          Editing tags of products                                                             |    
                    |          Updating every attribute of the object (ex. name, quantity, location                 |
                    |                                                           phone number,etc )                  |
                    +-----------------------------------------------------------------------------------------------+
                                       