#THE CELLAR
**Note**: The Cellar is in development

This is a SpringBoot application designed to sit over a MySQL5 DB.  The UI is designed using Bootstrap 3 and the JS Libraries jQuery and DataTables.

Application requires a MYSQL5 db with a schema named "cellar" in order to to start.

Application also requires a mock email application and some valid SMTP credentials if used in actual deployment IE: the TEST profile. I use SMTP4DEV to mock emailing in DEV and Gmail in TEST.  

The Cellar is being developed in IntelliJ 2017 

##Spring Configurations
There are currently 2 Spring Profiles set up in the YAML.

####DEV
The Dev profile is designed for development machines and run on a local environment in the embedded Tomcat Server.  It also uses 
Running this profile requires three environment variable on your machine.

**CELLAR_CON_STRING_DEV** - Connection string to the local db (hint: it should start with jdbc) 

**CELLAR_USER_DEV** - the username for the cellar schema in your db

**CELLAR_PASS_DEV** - the password associated to the username

I plan to add a @Profile annotation to differentiate the diference of this profile to eliminate a couple small development pains.  For example I will make in memory users and create a Dev Login to make logging in every time you restart your app such a pain.  I will also eliminate browser caching in dev to make JS and CSS trouble shooting such a pain.  I need the caching in higher environments because of the background image.

The run configuration for DEV is **spring-boot:run -Dspring.profiles.active=dev**

####TEST
The Test Environment is what I run on a self hosted server.  The main difference at the moment is the root URL that I use to add links to emails.  I also use a live SMTP Server to send out emails.  

**CELLAR_CON_STRING_DEV** - Connection string to the db (hint: it should start with jdbc)
 
**CELLAR_USER_DEV** - the username for the cellar schema in your db

**CELLAR_PASS_DEV** - the password associated to the username

**CELLAR_MAIL_HOST_TEST** - the host address for your SMTP Server 

**CELLAR_MAIL_USER_TEST** - the username and email address for a valid email in said server

**CELLAR_MAIL_PASS_TEST** - the password associated to the above email address

This environment is my test environment that allows me to interact with the application over time and leave it deployed to run jobs or allow others to view the site.

The run configuration for TEST is **spring-boot:run -Dspring.profiles.active=test**

##Using the Application
Once you have the application up and running on your local machine.  Open a browser that actually works, like Chrome or Firefox and navigate to localhost:8080.  

There are a couple pages that are not locked out by security including the welcome page at the root and the Registrations page that you can quickly navigate to using the link on the welcome page.

The first user that you create will have elevate roles.  This user will have The following roles

#####ROLE_USER
This is the standard role that all self registered users will get.

#####ROLE_ADMIN
This role allows a user to see the admin console, currently in development.

#####ROLE_SUPER
This role is intended to be for the site owner.  There are no unique powers at the time but it is intended to do more than a standard admin can do. Because an admin will not be allowed to make changes to other admins.

At the moment, the application doens't have a ton of functionality other than creating users and inventory items.  But once all the overhead gets built in to administrate the page I will add a trading floor and various other functions.