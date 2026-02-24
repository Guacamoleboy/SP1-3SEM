# Architecture of Java API Flow
___

### ClassDTO vs ClassResponseDTO.. What's the difference?

In this project, request and response objects are intentionally
separated to ensure clarity, security, and maintainability.

The separation into distinct packages for inbound and outbound DTOs is peak use of
Separation of Concerns (SoC). Each Class has a single responsibility.. 

Either Handle Input or Create output.

___

#### Flow (full):
Client → Controller -> ClassDTO → Service → DAO → Entity → Service → ClassResponseDTO → Client

#### Flow (Inbound):
Client → Controller → ClassDTO → Service → DAO → Entity

#### Flow (Outbound):
Entity → Service → ClassResponseDTO → Controller → Client

___

### ClassDTO - Inbound

Represents incoming traffic from the UI.
Contains only the fields that the client is allowed to send. 

Things that should NOT be included in inbound traffic.
- Backend generated IDs
- Timestamps
- Internal Data Fields

Use Cases:
- Creating a Role
- Updating a Role
- Removing a Role


___

### ClassResponseDTO - Outbound

Represents outgoing traffic from the backend to the client.

defines what data is exposed externally.
It could contain additional fields generated or managed by the backend, such as:
- DB / DAO generated IDs
- Timestamps
- Calculated Values
- Formatted Values
- Data from related entities
- Boolean flag logic
- Specified parts of a list instead of the entire list

Use Cases:
- Returning a new or existing Role from a Database
- Returning values from a Database
- Returning generated backend values to Client / UI
- Returning entity related data such as username from User instead of sending the entire Object
- Returning formatted values ready for display in UI
- Returning permission-based flags to control UI

___

### Thoughts process of concerns

A quick overview of what to think when sending / receiving data in general.

##### Controller way of thinking:
"How do we get the data in and out?"

##### DAO way of thinking:
"How do we get it from or save it to the database?"

##### Service way of thinking:
"How do we handle this data?"

##### Database / Entity way of thinking:
"Hoes do we store this ?"

##### ClassDTO way of thinking:
"What are we sending from the Client / UI ?"

##### ClassResponseDTO way of thinking:
"What does the client / UI need to see ?"

See how that makes more sense now? Bliss.

___

### Why Separation of Concerns?

With seperation of each we are able to handle things like:
- Prevents accidental exposure of internal fields that are NOT meant to be visible in UI.
- Client handle safety.
- Makes API contracts explicit.
- Improves long-term maintainability & stability.

___

### Architecture.md written by Guacamoleboy
