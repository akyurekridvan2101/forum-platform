# PostgreSQL Schema Design for Forum Platform

This document describes the core relational database schema used in the **Forum Platform** backend. The PostgreSQL schema is designed with normalization, extensibility, and hybrid (NoSQL + SQL) architecture in mind.

---

## 📌 Overview

The schema covers the following domain areas:

- ✅ User Management (authentication, roles, block/follow)
- ✅ Forum Structure (categories, posts, tags)
- ✅ Reactions (likes/dislikes)
- ✅ Media Attachments (posts/comments)
- ✅ Hybrid Comment System (PostgreSQL for metadata, MongoDB for content)

---

## 📊 Entity Relationships

See `forum-schema.drawio` for the full ERD (Entity Relationship Diagram).

---

## 🧩 Key Tables

### 1. `users`
Stores core user profile information. Connected to:
- `user_credentials` (secure auth info)
- `user_roles`, `user_followers`, `blocked_users`, `posts`, `comments`, etc.

### 2. `user_credentials`
Separates sensitive authentication details like `email`, `password_hash`, and `email_verified` status.

### 3. `roles` and `user_roles`
Many-to-many relationship that supports system-wide role assignment.

### 4. `categories` & `posts`
Hierarchical forum structure. Each post belongs to a category.

### 5. `tags` & `post_tags`
Keyword classification system for posts.

### 6. `comments`
Stores metadata only. Full content resides in MongoDB (`mongo_id` is the bridge key).

### 7. `media`, `post_media`, `comment_media`
Support for attaching files and images to posts/comments.

### 8. `post_reactions`, `comment_reactions`
User engagement via likes/dislikes (values: `1` or `-1`).

### 9. `user_favorites`
Tracks posts bookmarked by users.

---

## 🔒 Constraints & Integrity

- Cascading deletes are used on foreign keys (e.g., when a user is deleted, related comments and roles are cleaned up).
- Self-referencing checks are enforced (`CHECK (user_id <> blocked_id)`).
- All `slug`, `username`, `email`, and tag `name` fields are unique.
- Timestamps (`created_at`, `updated_at`) exist on all main entities.

---

## 🧠 Hybrid Model with MongoDB

While PostgreSQL holds metadata for comments (e.g., creation time, author, reply count), the actual **comment content** is stored in MongoDB using the `mongo_id` key. This design improves flexibility for:
- Rich formatting (e.g., Markdown)
- Future versioning/edit history
- Full-text indexing with Mongo-native tools

---

## 📝 Notes

- This schema is designed to support eventual transition into a microservice architecture.
- Triggers, audit logs, and partitioning strategies are planned but deferred to a future iteration.

---

