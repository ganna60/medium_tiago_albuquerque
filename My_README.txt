

2.  - Configuration Server
    Another improvement we can make in our system is to use a centralized configuration server.
    Right now, we have local configurations for each microservice, usually at application.yml file.
    But we can change that to a client-server approach for storing and serving distributed configurations across
    multiple applications and environments, ideally versioned under Git version control and able to be modified
    at application runtime.

    So, let’s set up a Config Server and configure the clients to consumes the configuration on startup and then
    refreshes the configuration without restarting the client. We will use Spring Cloud Config for this purpose:


Enable the config server with proper annotation ‘EnableConfigServer’ at main class

It is possible to use a local directory as source of configs, or a git remote repository.
In this case, we will use the same GitHub repository of this project, on a ‘configs’ folder.
So, we need to set this configuration at servers application.yml fil

Now we need to extract client’s configuration in specific application.yml files,
and store them in the configured git repository.
But each microservice has to load the configuration at startup time,
so we have to use another Spring config file that is load one step before: bootstrap.yml,
which will have a reference to locate the config server: