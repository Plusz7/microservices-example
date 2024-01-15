# microservices-example
Spring Microservices, 

This project is based on a Manning's Spring Microservices. It's a learning process about implementing new technologies, not a real project with clean code, or tests.

- connection to config repo: https://github.com/Plusz7/configuration-repo 
- db on local, and docker
- basic crud with License.class
- added messages.properties with locale feature
  
- changes were made in configuration repo to match licensing service docker configuration
- created dockerfile and docker-compose.yml to create a container with licensing service and mysql database connected within the container

- created organization service
- created a Eureka Server to monitor licensing service and organization service, each has their own db
