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
    status_id SMALLINT REFERENCES user_status(id) 
              DEFAULT 1,                                   -- Default: waiting for email verification
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

