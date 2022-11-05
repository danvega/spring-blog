# Spring Blog

This is a sample blog application that I created to use as an example of how to take a Spring Boot application to production. 
It's not enough to understand how to create applications, you need to understand how to take them to production. This document 
will walk you through what this application is, how to run it and how to execute the tests associated with it.

## About the Application 

This is a simple web application that exposes a REST API. This application uses Maven as the build tool and the current 
LTS version of Java, 17. I hope to add more functionality to this application in the future but 
for now this project uses the following dependencies: 

- Spring Web
- Spring Data JDBC
- PostgreSQL Database
- Spring Actuator
- Testcontainers

## Running the application

You can run this application from your favorite IDE or by running the following command:

```bash
./mvnw spring-boot:run
```

## Testing the application

This application uses Junit 5 and [Tescontainers](https://www.testcontainers.org/). To run the tests you will need Docker 
desktop installed and running. You need Docker to execute the tests because this application uses Testcontainers to spin 
up PostgreSQL database. This allows us to test as close to production as possible on our development machines as well as 
a clean and reproducible testing environment each time.

## Building for Production 

If you want to build an artifact that can be used in production you have 2 options. This application uses `JAR` as the 
packaging type. This means that you can run the following command to create something that is ready to be used in production.

```bash
./mvnw clean package
```

If you would like to create a Docker Image which can be used on a variety of platforms you can run the following command: 

```bash
./mvnw spring-boot:build-image
```

## Spring to Production 

This is a collection of the different platforms that I have pushed this application to. I will also include any information 
that might help you out and any related tutorials I have created for that platform. The idea for this project came from an
episode of [Spring Office Hours](https://tanzu.vmware.com/developer/tv/spring-office-hours/0015/) in which DaShaun and I 
discussed some different options for taking your Spring Boot application to production. 

![Spring Office Hours: Episode 15 - Spring to Production](https://tanzu.vmware.com/developer/tv/spring-office-hours/0015/images/0015.png)

### Local Development

When working on this application locally you will need Docker Desktop installed. To start an instance of PostgreSQL run the Docker
Compose file located in the root of the project. 

### Azure Spring Apps

Azure Spring Apps is a platform as a service (PaaS) for Spring developers. Manage the lifecycle of your Spring Boot applications with 
comprehensive monitoring and diagnostics, configuration management, service discovery, CI/CD integration, and blue-green deployments.

[https://azure.microsoft.com/en-us/products/spring-apps](https://azure.microsoft.com/en-us/products/spring-apps)

#### GitHub Actions 

You could create a new artifact each time and deploy it to Azure Spring Apps using the Azure CLI. This can be tedious though and if you want to deploy a new version of your application each time a commit is made or merged into the master branch you can use GitHub actions. The following is a workflow that I am currently using to do that. 

```yaml
name: AzureSpringCloud
on: push
env:
  ASC_PACKAGE_PATH: ${{ github.workspace }}
  JAVA_VERSION: 17
  AZURE_SUBSCRIPTION: YOUR_SUBSCRIPTION_ID_HERE

jobs:
  deploy_to_production:
    runs-on: ubuntu-latest
    name: deploy to production with artifact
    steps:
      - name: Checkout Github Action
        uses: actions/checkout@v2
        
      - name: Set up JDK ${{ env.JAVA_VERSION }}
        uses: actions/setup-java@v1
        with:
          java-version: ${{ env.JAVA_VERSION }}

      - name: maven build, clean
        run: |
          mvn clean package -DskipTests

      - name: Login via Azure CLI
        uses: azure/login@v1
        with:
          creds: ${{ secrets.AZURE_CREDENTIALS }}

      - name: deploy to production with artifact
        uses: azure/spring-cloud-deploy@v1
        with:
          azure-subscription: ${{ env.AZURE_SUBSCRIPTION }}
          action: Deploy
          service-name: spring-blog
          app-name: spring-blog
          use-staging-deployment: false
          package: ${{ env.ASC_PACKAGE_PATH }}/**/*.jar
```

### Railway

Bring your code, we'll handle the rest. Made for any language, for projects big and small. [Railway](https://railway.app/) 
is the cloud that takes the complexity out of shipping software.

Create a new empty project in Railway and start by creating a PostgreSQL database. Once you have that created you can create
a new project from GitHub. You can use the following environment variables based on the database you just created. 

```properties
spring_profiles_active=prod
PROD_DB_HOST=HOST_HERE
PROD_DB_PORT=POST_HERE
PROD_DB_NAME=railway
PROD_DB_PASSWORD=PASSWORD_HERE
PROD_DB_USERNAME=postgres
```

You don't need GitHub Actions or any type of pipeline for this setup because Railway handles this for you. Simply push your code to GitHub
and a new build and deploy will be triggered. If you want to disable this functionality you can from the settings of your project
on Railway. 

### Coming Soon

- [https://www.heroku.com](https://www.heroku.com/)
- [https://render.com](https://render.com/)
- [https://fly.io](https://fly.io/)
- [https://porter.run](https://porter.run/)
- [https://www.cloudfoundry.org/](https://www.cloudfoundry.org/)
- [https://www.digitalocean.com](https://www.digitalocean.com/)
- [https://aws.amazon.com](https://aws.amazon.com/)
- [https://cloud.google.com](https://cloud.google.com/)
- [OpenShift](https://www.redhat.com/en/technologies/cloud-computing/openshift)
