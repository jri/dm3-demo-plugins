
DeepaMehta 4 Demo Plugins
=========================

A set of demo plugins for the [DeepaMehta 4](http://github.com/jri/deepamehta) platform.

These plugins demonstrate both:

* How to setup a DeepaMehta 4 plugin development project
* How to develop a DeepaMehta 4 plugin

Licensed under GNU General Public License Version 3.


Requirements
------------

For building and running the demo plugins you need nothing more than **Java**, **Git**, and **Maven** installed.  
You Do *not* need a DeepaMehta 4 installation.


Install and run
---------------

Each demo plugin is installed and run by this procedure:

    cd demo-plugin-1
    mvn install -DskipTests=true

This installs the plugin in your local Maven repository.  
`-DskipTests=true` is important because the included integration tests relies on a installed plugin.

    mvn pax:run

This provisions an entire DeepaMehta 4 OSGi runtime environment within the plugin directory.  
The plugin is provisioned and the DeepaMehta 4 web client is opened in a browser.


Testing
-------

Plugins provide integration tests.

    mvn test

This provisions (again) an DeepaMehta 4 OSGi runtime environment on-the-fly and runs the tests.  
Important: before running the tests install and run the plugin as described above.


Learn more
----------

For more information about plugin development visit the DeepaMehta wiki:  
<https://trac.deepamehta.de/wiki>
