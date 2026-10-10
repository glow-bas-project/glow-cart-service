# GLOW Cart Service

The GLOW Cart Service is a backend microservice that manages customers' shopping carts within the GLOW platform. It is independently deployable and forms part of the platform's distributed microservice architecture.

Each customer has one cart, linked to their Keycloak account and created automatically the first time it is needed. Adding a menu item that is already in the cart increases its quantity, and the cart keeps a running total price. Customers manage their own cart through the `/carts/me` endpoints, which require a signed-in user, while other endpoints let the platform look up, update or clear a cart by its ID or by user ID.

The service is built with **Java 25** and **Quarkus**, using **PostgreSQL** for persistence and **Liquibase** for database migrations.

Technologies:
* Java 25 & Quarkus
* PostgreSQL & Liquibase
* REST API
* OpenID Connect (OIDC)
* MapStruct
* Kubernetes
* Gradle
