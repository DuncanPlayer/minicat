# Minicat!

Minicat with netty,just like tomcat a little,yet Users can request data asynchronously through ajax and encapsulated the JDBC.
This way, users don't have to focus on the configuration of the framework , but only on business development; Although also need some configuration in minicat

 - If you want to request data asynchronously and return JSON data, just configure mapping.properties，like class full name and method name.
**Well**, there is a bug in ReflectMethod class that only matches String, Integer,Long, so hopefully you can find a better way to deal
 - And you also have to configure your static resource address

## Here's the configuration file

#Key:user Request Directory,Value:class full name and method name  
/usr/getone=com.messi.web.minicat.test.Test,getUser  
#Configure the location of html, css, JS files  
resoucesLocation=src\\main\\resources  
#The path of the picture  
pictureLocation=G:\\image\\imags
