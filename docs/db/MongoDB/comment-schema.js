// This file inserts comment contents into MongoDB
// that correspond to metadata stored in the `comments` table in PostgreSQL.

// This script inserts the actual comment bodies into MongoDB.
// It complements PostgreSQL's `comments` table which holds metadata.

use forumDB;

db.comment_contents.insertMany([
  {
    // Root comment – no parent
    _id: ObjectId("66df00000000000000000001"),
    content: "What is Hibernate flush and how does it work?",
    authorUsername: "ridvan_dev",      // Matches the username in the PostgreSQL `users` table
    topicId: 301,                      // Matches the topicId in the PostgreSQL `posts` table
    createdAt: new Date("2025-07-13T12:00:00Z"),
    updatedAt: new Date("2025-07-13T12:00:00Z")
  },
  {
    _id: ObjectId("66df00000000000000000002"),
    content: "Flush reflects changes in the session to the database.",
    authorUsername: "ayse_k",
    topicId: 301,
    parentCommentId: ObjectId("66df00000000000000000001"), // Reply to the above comment
    createdAt: new Date("2025-07-13T12:05:00Z"),
    updatedAt: new Date("2025-07-13T12:05:00Z")
  },
  {
    _id: ObjectId("66df00000000000000000003"),
    content: "So can data be sent even without a commit?",
    authorUsername: "mehmet_k",
    topicId: 301,
    parentCommentId: ObjectId("66df00000000000000000002"),
    createdAt: new Date("2025-07-13T12:10:00Z"),
    updatedAt: new Date("2025-07-13T12:10:00Z")
  },
  {
    _id: ObjectId("66df00000000000000000004"),
    content: "Yes, but if the transaction is rolled back, it's undone.",
    authorUsername: "zeynep34",
    topicId: 301,
    parentCommentId: ObjectId("66df00000000000000000003"),
    createdAt: new Date("2025-07-13T12:13:00Z"),
    updatedAt: new Date("2025-07-13T12:13:00Z")
  },
  {
    _id: ObjectId("66df00000000000000000005"),
    content: "What exactly is a transaction? How is it started in JDBC?",
    authorUsername: "muratx",
    topicId: 302,
    createdAt: new Date("2025-07-13T13:00:00Z"),
    updatedAt: new Date("2025-07-13T13:00:00Z")
  },
  {
    _id: ObjectId("66df00000000000000000006"),
    content: "Connection conn = getConnection(); conn.setAutoCommit(false);",
    authorUsername: "caner_j",
    topicId: 302,
    parentCommentId: ObjectId("66df00000000000000000005"),
    createdAt: new Date("2025-07-13T13:05:00Z"),
    updatedAt: new Date("2025-07-13T13:05:00Z")
  },
  {
    _id: ObjectId("66df00000000000000000007"),
    content: "Thanks. And how is rollback done in JDBC?",
    authorUsername: "yusuf_c",
    topicId: 302,
    parentCommentId: ObjectId("66df00000000000000000006"),
    createdAt: new Date("2025-07-13T13:10:00Z"),
    updatedAt: new Date("2025-07-13T13:10:00Z")
  },
  {
    _id: ObjectId("66df00000000000000000008"),
    content: "You call conn.rollback(); it reverts everything.",
    authorUsername: "ayse_k",
    topicId: 302,
    parentCommentId: ObjectId("66df00000000000000000007"),
    createdAt: new Date("2025-07-13T13:12:00Z"),
    updatedAt: new Date("2025-07-13T13:12:00Z")
  },
  {
    _id: ObjectId("66df00000000000000000009"),
    content: "Using Spring Transactional makes this much easier.",
    authorUsername: "ridvan_dev",
    topicId: 302,
    parentCommentId: ObjectId("66df00000000000000000005"),
    createdAt: new Date("2025-07-13T13:15:00Z"),
    updatedAt: new Date("2025-07-13T13:15:00Z")
  },
  {
    _id: ObjectId("66df00000000000000000010"),
    content: "Yes, the @Transactional annotation is very useful.",
    authorUsername: "zeynep34",
    topicId: 302,
    parentCommentId: ObjectId("66df00000000000000000009"),
    createdAt: new Date("2025-07-13T13:17:00Z"),
    updatedAt: new Date("2025-07-13T13:17:00Z")
  }
]);

