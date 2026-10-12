[12-10-2026]

Today, I learned to setup the project using VS Code's Spring initliazr and how to add dependencies. I used docker-compose maven dependency which auto manages the containers when we boot-up the spring application. Furthermore, I also learned to use environment vars inside docker compose and spring's application.properties file. Also, learned that if you don't specify a volume in docker container then it will store the data in its container. In my case, it was postgresql. So, it was storing the db inside the container itself. If later the container is deleted the data also gets deleted. Hence, we use volumes which remains persistent even if services are stopped or containers are deleted they persist unless you delete the volume itself.

Then, I added the JPA which allows us to add entities to manage the database. Added a controller, service, entity, and a repo inside their respective named directories.

Next, I'll learn to create CRUD Rest API.
