[20-10-2026]

Today we learned how to take accept multiple fields in a multipart form along with the file type input. Then, we created a custom annotation and a validator for validating the enum values. Spring Boot does it by default but if you don't pass the key or value then it gives Conversion error. So, I added a validator which extracts the values from the supplied enum and make sure's the input contains value from the enum's set of values. Next, I added a AuthController and added a register user endpoint which inserts the user into the database with a hashed password and a role of either USER or ADMIN. When I added the spring boot security starter pack all the endpoint became protected and by default form login was enforced. So, since we are working on a REST API I disabled the CSRF, Form Login, and Basic HTTP Auth, and allowed the `/auth/**` and `/error` routes. The reason for whitelisting `/error` route is because spring boot internally uses that endpoint to throw errors. If its restricted then we get 403 error from the API instead of validation errors. The final piece was to send the newly created user back to the response however without exposing the `password` field from the `User` entity. To resolve this I created a new `UserResponseDto` and mapped the fields.

Right now, all the endpoints are protected however they are throwing 403 error instead of 401. Will work on it next.

[23-10-2026]

Last time we generated a Response DTO for omitting the password field. Today, I learned an alternative and easier approach i.e. to use @JsonIgnore Annotation on the getter.

Today we integrated the Authentication and Issuing JWT Token to the user on Login. The first obvious step was to add the `/auth/login` route. To issue the JWT token we had to setup a few things before hand:

1. Generate a Private Key and Public Key with RSA algo and store it in `/secrets/`
2. Add the path of the JWT file in the `application.properties`. The keys are custom so we had to do an extra config to allow the custom keys. To do that we added an `additional-spring-configuration-metadata.json` in the `/resource/META-INF` directory.
3. Then we created a `JwtConfig` service which loaded the private and public key from the path and returned the instance of `RSAPrivateKey` and `RSAPublicKey`.
4. Then, we defined two methods called `jwtEncoder` and `jwtDecoder`. The `jwtEncoder` uses private and public key to generate an RSA key which is used to instantiate the `NimbusJwtEncoder` (used for encoding the JWT). Similarly, the `jwtDecoder` uses both private and public key to instantiate the decoder class used for decoding the JWT.
5. Then, we define a `userDetailsService` bean which queries the databse by using email. If user is found we build a UserDetails object and send it back else throw an exception.
6. The last piece of puzzle is to add an AuthenticationManager and set the DaoAuthenticationProvider to UsersDetailsService and passwordEncoder.
