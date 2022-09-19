# BusTelematicsApp

This is an application that provides real time information for bus locations using Java Sockets.
Remastered version of AUEB project.

### Information as given from resource files:

1. #### lines.txt

   Provides information about all the lines in existence for this app.
   ##### Format:
   <line id, line code, line name>

2. ### busLocations.txt
   Provides information about the location of a bus line.
   ##### Format:
   <line id, route id, vehicle id, altitude, latitude, time>

3. ### routes.txt
   provides information about the routes of each bus line.
   ##### Format:
   <route id, line id, route code, route name>

### Messaging system

---
- For the messaging system Apache kafka is used. There can be multiple Publishers, Brokers and
Consumers. The topic is `bus.locations`, and partition is supported.
- Bus lines and routes are saved in database.
---

The producers read line by line from a txt file and every X seconds -as defined in appication.yml - and they
publish a new line.
Each line describes a new bus location. Txt file used is `busPositions.txt`.
