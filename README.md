<div align="LEFT">

# SP1 - TMDB - Movie Backend
**School Project**

[![TMDB Reference Docs](https://img.shields.io/badge/Go%20to%20TMDC%20Docs-ffffff?style=for-the-badge&color=33B6DF)](http://developer.themoviedb.org/reference/getting-started)

</div>

---

### The Task..
**First Project of 3 Semester - Group Project**

We must build a part of a small **backend**, that can store and retrieve information about movies.

> **In Depth:**
>
> The SP-1 Movie Repository project requires building a Java backend over four days to fetch, store, and manage data for movies from the TMDD API.
> The scope involves utilizing JPA / JPQL entities and DTOs to map movie data, establishing relationships between entities,
> and implementing CRUD operations via a DAO layer ran on a Service layer executed in a Controller layer.
>
> For more details, visit 3sem.kursusmaterialer.dk/ and navigate to projects/sp1


---

### Use Case

1. **API:** Fetch TMDB Information and store in a Database.
2. **PostgreSQL:** Data will be stored and ready for use. TMDB fills our Database with entries.
3. **Advanced Querying:** Provide specialized search functionality, including case-insensitive title searches and filtered lists for specific genres.
4. **Statistical Insights:** Execute advanced SQL queries to generate analytics like Top-10 ratings, popularity rankings, and average scores.
5. **Async Performance:** Using asynchronous fetching and parallel processing to handle large datasets of movies efficiently without blocking the system.
6. **Data Enrichment:** Map and link complex relationships between entities.

---

### Development Structure

* **Branching:** Personal branching from "dev" branch. "main" is final for Project Handover.
* **IntelliJ IDE:** Using Code With Me on days we're gathered. 
* **Schedule:** Daily - Starting 11:00 local time (DK). Meeting on Discord for Code Session.
* **Different Skill Levels:** Trying to keep the project as simple as possible to acount for different skill levels
* **PR Requests:** Solved Issues from our KanBan should be implemented using Pull Requests from Personal Branch to dev.
* **KanBan:** Used to keep track of tasks and who's doing what at any time of the day. 

---

### Development & Technology

| Component | Technology |
| :--- | :--- |
| **Backend** | Java 17+ |
| **Persistence** | Jakarta / Hibernate |
| **Database** | PostgreSQL |
| **API Handling** | Async / CompletableFuture |
| **JSON Mapping** | Jackson |
| **Environment** | DotEnv |
| **Testing** | 	JUnit 5 |
| **Build Tool** | Maven |
| **Boilerplate** | Lombok |

---

<div align="center">
<sub>Created by - Andreas, Oliver, Rasmus & Jonas</sub>
</div>
