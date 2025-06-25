# Forum Platform – Database Design

This folder contains the **database systems** used in the forum application and related **documentation** for each.

We use 3 different database technologies in this project:

---

## 📘 1. PostgreSQL

- Stores structured and relational data like **users**, **topics**, **comments**, and **likes**.
- These data are **connected** and must stay consistent.
- This folder includes:
  - `pgsql-design.md`: Design explanation
  - `pgsql-uml.png`: UML diagram
  - `schema.sql`: SQL schema (optional)

**Folder:** `docs/db/PostgreSQL/`

---

## 📗 2. MongoDB

- Stores flexible data like **notifications** and **logs**.
- It's great for data that doesn’t always follow a fixed structure.
- This folder includes:
  - `mongo-design.md`: Design explanation
  - `mongo-uml.png`: Example document structure

**Folder:** `docs/db/MongoDB/`

---

## 📕 3. Redis

- Stores fast, temporary data like **user sessions**, **counters**, and **online user info**.
- This folder includes:
  - `redis-design.md`: Description of data usage
  - `redis-uml.png`: Key structure diagram and use cases

**Folder:** `docs/db/Redis/`

---

## 🧱 Folder Structure

```text
docs/
└── db/
    ├── PostgreSQL/
    │   └── pgsql-design.md (and other files)
    ├── MongoDB/
    │   └── mongo-design.md
    ├── Redis/
    │   └── redis-design.md
    └── README.md  👈 (this file)

