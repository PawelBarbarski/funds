# funds

An application for calculating investment amount distribution among investment funds depending on parameters chosen.

Project is build in Maven, packaged in one jar: funds.jar.

The application is a Web application running on Spring Boot, using JSF (through joinFaces) on frontend and Spring Data JPA on backend. H2 database is used.

Model-View-Controller pattern is used with Domain-Driven Design on backend.

Unit tests use Mockito.

You start from the home page: http://localhost:8080/funds/home

On the first page you choose the amount to invest and the investment style from the list, and press "Submit" button.
On the second page you choose the funds, at least one fund from each fund type should be chosen.
You press "Submit" to get the results table on the last page.
Amount which cannot be divided between fund types (final amounts are integral) should be returned, thus a proper message informs you about it.
Amount which cannot be divided between funds in a given type is randomly assigned to one of them.