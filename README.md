# Find My Bus App

This is a remastered version of an AUEB project that that provides real time information for bus
locations asynchronous communication.
In this version the Java Sockets are replaced by Apache Kafka and some basic database integration is
also implemented, to add some storage, mainly for constant data.

## How to run:

For this project we need:

- postgres
- kafka
- zookeeper

...Pending how to run different profiles from docker compose...

## How it works:

During initialization of application, data about the lines and the routes of buses are stored in
database. More about the data in the section `Data explanation` below.

We have two active profiles for this application:

- producer
- consumer

In order for the application to run as expected we need to run one of each.
We can configure how many producers we need, and provide an id for each producer, so that each can
be aware of the bus lines they are responsible for.

When a Producer application starts, depending on how many producers exist, and their specific id,
they are assigned a number of bus lines. A line can be assigned to one producer only.

Then every X seconds - where X is configurable - each producer send a message to kafka
topic `bus.locations` about where a
line
exists. The line to send is the next line they read from the `busLocations.txt` file.

The Consumer application has a kafka Listener that receives those messages.

## Data explanation:

During initialization of application data about the lines and the routes of buses are stored in
database. This information includes the following tables:

| line |              | route   |              |
|------|--------------|---------|--------------|
| id   | integer      | id      | integer      |
| code | integer      | line_id | integer      |
| name | varchar(255) | code    | integer      |
|      |              | name    | varchar(255) |

1. The line table contains information about a bus line.

- E.g. A line object can have:
    - a unique id `817`,
    - a numeric code like `26`, this is the code the buses usually display so that passengers can
      identify them,
    - a line name such as `IPPOKRATOUS - VOTANIKOS`, this is the name of the line, that accompanies
      the previous line code.

2. The route table contains information about a route of a line.

- E.g. A route object can have:
    - a unique id `1799`
    - a numeric foreign key, which is an existing line id to bind this route to, such as `817` as
      stated previously,
    - a route name, which is the line name depending on the direction of the route.
        - E.g. If the route
          starts at `IPPOKRATOUS` stop and finishes at `VOTANIKOS` stop then the route name should
          be `IPPOKRATOUS-VOTANIKOS`.
        - However, the line starts at `VOTANIKOS` stop and finishes
          art `IPPOKRATOUS` stop, then the route name should be `VOTANIKOS-IPPOKRATOUS`.
    - a numeric route code, either `1` or `2` for our cases depending on the direction of the route.
      for routes that have different starts.
        - E.g. Above, if route with name `IPPOKRATOUS-VOTANIKOS` has `code` equal to `1` then the
          route with name `VOTANIKOS-IPPOKRATOUS` will have `code` equal to `2`.