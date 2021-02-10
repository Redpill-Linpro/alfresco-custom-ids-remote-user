# Alfresco Custom IDS Remote User Platform/Repository Module

This module allows for "external authentication" when using the IDS with Alfresco.

This means that you can inject a user id using http headers with a request on the repository which can be useful in some integration cases.

Make sure the tomcat instance for Platform/Repo is not directly accessible by users. All requests should be filtered in a proxy to prevent user spoofing.
Apache 2 Example:
```html
##############################################################
# Check a HTTP header for a key to allow remote user to be set
##############################################################
<If "%{HTTP:X-SHARED-SECRET} != 'Averysecretkey'">
    # prevent the client from setting this header, unless secret is provided
    RequestHeader unset X-Alfresco-Remote-User
</If>
```


To use use the following code in alfresco-global.properties
```properties
authentication.chain='custom-identity-service-1:custom-identity-service,alfrescoNtlm1:alfrescoNtlm,ldap1:ldap-ad'
external.authentication.enabled=true
external.authentication.proxyHeader=X-Alfresco-Remote-User
external.authentication.proxyUserName=
```

# Build instructions

Run with `./run.sh build_start` or `./run.bat build_start` and verify that it

 * Runs Alfresco Content Service (ACS)
 * (Optional) Runs Alfresco Share
 * Runs Alfresco Search Service (ASS)
 * Runs PostgreSQL database
 * Deploys the JAR assembled module
 
All the services of the project are now run as docker containers. The run script offers the next tasks:

 * `build_start`. Build the whole project, recreate the ACS docker image, start the dockerised environment composed by ACS, Share (optional), ASS 
 and PostgreSQL and tail the logs of all the containers.
 * `build_start_it_supported`. Build the whole project including dependencies required for IT execution, recreate the ACS docker image, start the dockerised environment 
 composed by ACS, Share (optional), ASS and PostgreSQL and tail the logs of all the containers.
 * `start`. Start the dockerised environment without building the project and tail the logs of all the containers.
 * `stop`. Stop the dockerised environment.
 * `purge`. Stop the dockerised container and delete all the persistent data (docker volumes).
 * `tail`. Tail the logs of all the containers.
 * `reload_acs`. Build the ACS module, recreate the ACS docker image and restart the ACS container.
 * `build_test`. Build the whole project, recreate the ACS docker image, start the dockerised environment, execute the integration tests and stop 
 the environment.
 * `test`. Execute the integration tests (the environment must be already started).

