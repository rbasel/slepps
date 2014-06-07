slepps
======

Slightly Less Painful Prepared Statements

The Why
--------------------------

So there I was, staring at a process that had been doing a heavily SQL driven work load for the better part of a decade.  It had not aged gracefully.  There where whole chumks of code that hadn't been called from the beginning, or at least since the process had been verson controlled.  Eclipse cried under the warning generation load.  

But that's just me venting; The issue at hand, or at least the one that I thought would be a good first step towards improving the health of this code was Exception handling.

Exception handling was out of control.  Exceptions traveled in packs of six or more.  Try catches that would rethrow to be rethrone to rethrow again.  Most of them even managed to not drop the calling Exception.   

When you are writing a method to update a row, or query the database to test a condition, you have enough to worry about without having to muddle with the boiler place.  So what would it take to remove the need for try, catch, or finally in the dozens of SQL prep exection and evaluation methods.  

Slepps is my take on that solution.  It's a partial re-imagining of a C# database connectivety framework I used/supported at an old company.  I don't know if coming from the C# world allows me new insites into thewell known issues of hte prepared statement approach.  Or if my C# hammer is looking for nails.

Referenced Libraries
--------------------------
commons-lang3-3.3.2.jar

mysql-connector-java-5.1.30-bin.jar (how to best remove this)

commons-dbutils-1.5.jar


Data download
--------------------------
The meager testing (going to learn junit some day) provided uses the employee data found here:

https://launchpad.net/test-db/


