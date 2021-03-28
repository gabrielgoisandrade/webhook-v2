# Issue Webhook

<p>API RESTful feita para integração com o serviço de Webhook do GitHub — voltado apenas para issues.</p> 
<p>A API tem como objetivo receber, processar e armazenar as informações geradas ao abrir, modificar ou excluir uma issue.</p>

### Observações

<p>Essa API é voltada para fins de <b>ESTUDO</b>, com o propósito de fixar conhecimento sobre RESTful. Logo, erros são 
esperados. Mas isso não tira o potencial desse projeto evoluir. Errando que se aprende, né?</p>

<p>A API foi feita seguindo o modelo
de <a  href="https://martinfowler.com/articles/richardsonMaturityModel.html">amadurecimento de Richardson</a>, TDD, BDD, clean code,
SOLID e boas práticas para desenvolvimento de APIs REST.</p>

<p>As tecnologias usadas foram Kotlin como linguagem principal, Spring Boot, Postgres como banco de dados relacional,
Jacoco para cobertura de código, TravisCI para build e Heroku para o deploy.</p>

<a href="https://documenter.getpostman.com/view/7776218/TzCJgprx"> Veja aqui a documentação completa e teste diretamente no Postman!</a>

---
<h3 style="text-align: center">

![Kotlin]
![Spring]
![PostgreSQL]
![Heroku]

![TravisCI]
![Codecov]

</h3>

[Kotlin]: https://img.shields.io/badge/kotlin-%230095D5.svg?&style=for-the-badge&logo=kotlin&logoColor=white

[Spring]: https://img.shields.io/badge/spring%20-%236DB33F.svg?&style=for-the-badge&logo=spring&logoColor=white

[PostgreSQL]: https://img.shields.io/badge/postgres-%23316192.svg?&style=for-the-badge&logo=postgresql&logoColor=white

[TravisCI]: https://travis-ci.com/gabrielgoisandrade/webhook-v2.svg?token=MCyqP8WYfwqYysV4S5Px&branch=main

[Codecov]: https://codecov.io/gh/gabrielgoisandrade/webhook-v2/branch/main/graph/badge.svg?token=OBLIWQZZE4

[Heroku]: https://img.shields.io/badge/heroku%20-%23430098.svg?&style=for-the-badge&logo=heroku&logoColor=white
