# MongoDB Design – Forum Platform

This document outlines the MongoDB schema design used in conjunction with the forum platform. While PostgreSQL is used for structured metadata and relationships, MongoDB is leveraged to store rich, nested, or unstructured content such as comment bodies.

---

## 📌 Purpose of Using MongoDB

* To decouple large and frequently updated text bodies from relational constraints
* To support nested comment trees and flexible JSON-like storage
* To allow scalable write/read throughput for user-generated content

---

## 🗂️ Collection: `comment_contents`

This collection stores actual comment text and associated metadata.

### Sample Document Structure

```json
{
  "_id": ObjectId("66df00000000000000000001"),
  "content": "What is Hibernate flush and how does it work?",
  "authorUsername": "ridvan_dev",
  "topicId": 301,
  "parentCommentId": null,
  "createdAt": ISODate("2025-07-13T12:00:00Z"),
  "updatedAt": ISODate("2025-07-13T12:00:00Z")
}
```

### Fields

* `_id`: Unique ObjectId
* `content`: The actual comment body (rich text / markdown allowed)
* `authorUsername`: Redundant for quick display (linked to PostgreSQL `users.username`)
* `topicId`: Links to a post (PostgreSQL `posts.id`)
* `parentCommentId`: Optional reference to another MongoDB comment
* `createdAt`, `updatedAt`: Timestamps

---

## 🔄 Integration with PostgreSQL

* The `comments` table in PostgreSQL stores `mongo_id` (24-char string) to track comment metadata
* Relationships, authorship, likes, and reply counts are managed relationally
* Actual comment body (text) lives in MongoDB for performance and flexibility

---

## 📷 Media Embeds

* Each comment can be linked with multiple media files via the `comment_media` table in PostgreSQL
* MongoDB holds only content, while media remains relational for reuse and moderation

---

## ❤️ Reactions & Moderation

* Likes/dislikes are stored in PostgreSQL `comment_reactions`
* Moderation flags (e.g., `is_hidden`) live in relational DB for centralized control
* MongoDB does **not** handle permissions or visibility

---

## 📈 Benefits of Hybrid Model

* Combines strong consistency (PostgreSQL) with flexibility (MongoDB)
* Optimized for reading nested discussions
* Comment performance is decoupled from the relational load

---

## 🌐 Future Enhancements

* Text indexing (via Atlas Search or ElasticSearch)
* Versioning support for edited comments
* Soft-deletes with archival flags
* Embedded replies if parent trees are limited in depth

---

