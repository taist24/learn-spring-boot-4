[12-10-2026]

Today, I learned to setup the project using VS Code's Spring initliazr and how to add dependencies. I used docker-compose maven dependency which auto manages the containers when we boot-up the spring application. Furthermore, I also learned to use environment vars inside docker compose and spring's application.properties file. Also, learned that if you don't specify a volume in docker container then it will store the data in its container. In my case, it was postgresql. So, it was storing the db inside the container itself. If later the container is deleted the data also gets deleted. Hence, we use volumes which remains persistent even if services are stopped or containers are deleted they persist unless you delete the volume itself.

Then, I added the JPA which allows us to add entities to manage the database. Added a controller, service, entity, and a repo inside their respective named directories.

Next, I'll learn to create CRUD Rest API.

---

[14-10-2026]

Today, we created the following endpoints: create, delete, find users by id, and find all users. I learned to grab path param from the URL using `@PathVariable`. There's a similar Annotation called `@PathParam` which is confusing however that is for WebSocket routes. Next, I learned how to validate the request body using DTOs and validation spring boot starter pack. It has similar Annotation as they were in Nest.js such as `@NotNull`, `@NotEmpty`, `@NotBlank`. The enums are auto validated without any annotation. We just have to add the correct type for the class member and it does it automatically.

Suprisingly in controllers if you just use `@GetMapping("/")` then this does not imply the endpoint works at lets say "/users" instead it means the following "/users/" (notice the trailing slash). To fix it, I removed the param i.e. `@GetMapping`. If you want a route to support multi params such as"/index", "/", or without "/" then you can do `@GetMapping({ "/", "/index", "" })`.

To query the data using `where` is very straightforward in Hibernate. I just have to create a field called `findBy[Name of the column]` -> `findByEmail(String email)`. It auto queries the SQL.
Furthermore, I learned to use `Pageable` interface which makes implementing pagination super convenient.

[17-10-2026]

Today is Saturday. I learned to upload file using Multipart, how to access that file by setting up a static resource path, and send full URL of resource in the response. I started by adding a new field in the `User.java` entity called `avatar` followed by a multipart route in the controller. To access multipart field from the request we have to use `@RequestParam`. Alternatively, we can also use `@RequestPart`. Then I used the `Path` which is a java util method to create the `uploads/` directory and move the file over there using `transfer()` which is available on the file variable itself. I noticed that `avatar` field was not provided in the response of `getAllUsers` so I debugged and found that its due to missing getters. So, I added them and it fixed the issue. Now, to send the full path of `avatar` in the response I had to create a `UserResponseDto` and used `.map()` from `Page` to convert the objects to `UserResponseDto`.
