-- Table: user_status
-- Description: Defines fixed user account states (like ACTIVE, BANNED, etc.)
CREATE TABLE user_status (
    id SMALLINT PRIMARY KEY,                    -- Internal system reference ID
    code VARCHAR(30) UNIQUE NOT NULL,           -- Enum-like identifier (e.g. ACTIVE, BANNED)
    description TEXT NOT NULL                   -- Human-readable explanation (for UI/logs)
);

-- Table: roles
-- Description: System-wide roles that users can have (e.g. ADMIN, MODERATOR)
CREATE TABLE roles (
    id SMALLINT PRIMARY KEY,                    -- Unique role ID
    code VARCHAR(30) UNIQUE NOT NULL,           -- Enum-like identifier (e.g. ADMIN, USER)
    description TEXT NOT NULL                   -- Role explanation (used in UI/logs)
);

-- Table: users
-- Description: Core user profile data
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,                              -- Unique user ID
    username VARCHAR(50) UNIQUE NOT NULL,                  -- Username
    full_name VARCHAR(100),                                -- Display name
    avatar_url TEXT,                                       -- Profile picture URL
    bio TEXT,                                              -- Short bio or about section
    location VARCHAR(100),                                 -- Optional location
    website VARCHAR(255),                                  -- Optional website
    status_id SMALLINT REFERENCES user_status(id) DEFAULT 1, -- Default: waiting for email verification
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,        -- Creation timestamp
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP         -- Last update timestamp
);

-- Table: user_credentials
-- Description: Sensitive credentials stored separately
CREATE TABLE user_credentials (
    user_id BIGINT PRIMARY KEY REFERENCES users(id) ON DELETE CASCADE,  -- FK to users
    email VARCHAR(255) UNIQUE NOT NULL,           -- Email address
    password_hash TEXT NOT NULL,                  -- Hashed password (e.g. bcrypt)
    email_verified BOOLEAN DEFAULT FALSE,         -- Email verification status
    last_login_at TIMESTAMP                       -- Last login timestamp
);

-- Table: user_roles
-- Description: Many-to-many relationship between users and roles
CREATE TABLE user_roles (
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    role_id SMALLINT NOT NULL REFERENCES roles(id),
    assigned_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, role_id)
);

-- Table: blocked_users
-- Description: Tracks user-to-user block actions
CREATE TABLE blocked_users (
    blocker_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    blocked_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    blocked_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    reason TEXT,                                             -- Optional reason for moderation/logging
    PRIMARY KEY (blocker_id, blocked_id),
    CHECK (blocker_id <> blocked_id)                         -- Cannot block self
);

-- Table: user_followers
-- Description: Tracks user follow relationships (like Twitter)
CREATE TABLE user_followers (
    follower_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    followed_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    followed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (follower_id, followed_id),
    CHECK (follower_id <> followed_id)                       -- Cannot follow self
);

-- Table: categories
CREATE TABLE categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL,
    slug VARCHAR(100) UNIQUE NOT NULL,
    description TEXT,
    parent_id BIGINT REFERENCES categories(id),
    is_active BOOLEAN DEFAULT TRUE,
    position INTEGER DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table: topics
CREATE TABLE topics (
    id BIGSERIAL PRIMARY KEY,
    category_id BIGINT NOT NULL REFERENCES categories(id),
    author_id BIGINT NOT NULL REFERENCES users(id),
    title VARCHAR(255) NOT NULL,
    slug VARCHAR(255) UNIQUE NOT NULL,
    content TEXT,
    is_locked BOOLEAN DEFAULT FALSE,
    is_pinned BOOLEAN DEFAULT FALSE,
    is_hidden BOOLEAN DEFAULT FALSE,
    view_count INTEGER DEFAULT 0,
    reply_count INTEGER DEFAULT 0,
    last_activity_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table: tags
-- Description: Represents topic tags (e.g. 'java', 'spring-boot', 'linux'). Can be created by trusted users or moderators.
CREATE TABLE tags (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL,                  -- Displayed name (e.g. "Java")
    slug VARCHAR(50) UNIQUE NOT NULL,                  -- URL-friendly version (e.g. "java")
    description TEXT,                                  -- Optional description for tooltip/search help
    created_by BIGINT REFERENCES users(id),            -- Who created the tag
    is_approved BOOLEAN DEFAULT TRUE,                  -- Used to moderate new tags
    usage_count INTEGER DEFAULT 0,                     -- How many topics use this tag (for trending/search)
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table: topic_tags
-- Description: Many-to-many relationship between topics and tags
CREATE TABLE topic_tags (
    topic_id BIGINT NOT NULL REFERENCES topics(id) ON DELETE CASCADE,
    tag_id BIGINT NOT NULL REFERENCES tags(id) ON DELETE CASCADE,
    PRIMARY KEY (topic_id, tag_id)
);

-- Table: comments (only metadata, actual content in MongoDB)
CREATE TABLE comments (
    id BIGSERIAL PRIMARY KEY,
    mongo_id VARCHAR(24) UNIQUE NOT NULL,
    topic_id BIGINT NOT NULL REFERENCES topics(id),
    author_id BIGINT NOT NULL REFERENCES users(id),
    parent_comment_id BIGINT REFERENCES comments(id),
    is_hidden BOOLEAN DEFAULT FALSE,
    like_count INTEGER DEFAULT 0,
    reply_count INTEGER DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table: media
CREATE TABLE media (
    id BIGSERIAL PRIMARY KEY,
    url TEXT NOT NULL,
    media_type VARCHAR(30) NOT NULL,
    size_in_bytes BIGINT,
    uploaded_by BIGINT REFERENCES users(id),
    uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    description TEXT
);

-- Table: topic_media
CREATE TABLE topic_media (
    topic_id BIGINT REFERENCES topics(id) ON DELETE CASCADE,
    media_id BIGINT REFERENCES media(id) ON DELETE CASCADE,
    PRIMARY KEY (topic_id, media_id)
);

-- Table: comment_media
CREATE TABLE comment_media (
    mongo_id VARCHAR(24),
    media_id BIGINT REFERENCES media(id) ON DELETE CASCADE,
    PRIMARY KEY (mongo_id, media_id)
);

-- Table: topic_reactions (like/dislike for topics)
CREATE TABLE topic_reactions (
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    topic_id BIGINT NOT NULL REFERENCES topics(id) ON DELETE CASCADE,
    reaction SMALLINT NOT NULL CHECK (reaction IN (1, -1)),
    reacted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, topic_id)
);

-- Table: comment_reactions (like/dislike for comments - MongoDB ID)
CREATE TABLE comment_reactions (
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    mongo_id VARCHAR(24) NOT NULL,
    reaction SMALLINT NOT NULL CHECK (reaction IN (1, -1)),
    reacted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, mongo_id)
);

-- Table: user_favorites (saved topics by users)
CREATE TABLE user_favorites (
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    topic_id BIGINT NOT NULL REFERENCES topics(id) ON DELETE CASCADE,
    favorited_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, topic_id)
);

