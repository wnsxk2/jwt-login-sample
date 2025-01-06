-- CREATE TABLE users (
--     id SERIAL PRIMARY KEY,
--     email VARCHAR(255) UNIQUE NOT NULL,
--     password_hash TEXT, -- 비밀번호 기반 로그인 사용자용 (소셜 로그인의 경우 NULL 가능)
--     created_at TIMESTAMP DEFAULT NOW(),
--     updated_at TIMESTAMP DEFAULT NOW()
-- );

-- CREATE TABLE tokens (
--     id SERIAL PRIMARY KEY,
--     user_id INT REFERENCES users(id) ON DELETE CASCADE,
--     uuid VARCHAR(255) NOT NULL, -- 기기 식별자
--     refresh_token TEXT NOT NULL,
--     refresh_token_expires_at TIMESTAMP NOT NULL,
--     created_at TIMESTAMP DEFAULT NOW(),
--     updated_at TIMESTAMP DEFAULT NOW(),
--     UNIQUE(user_id, uuid) -- 동일 사용자와 기기에 대해 중복 방지
-- );

-- CREATE TABLE social_logins (
--     id SERIAL PRIMARY KEY,
--     user_id INT REFERENCES users(id) ON DELETE CASCADE,
--     provider VARCHAR(50) NOT NULL, -- 예: 'kakao', 'google'
--     provider_user_id VARCHAR(255) NOT NULL, -- 소셜 로그인 제공자의 고유 사용자 ID
--     access_token TEXT NOT NULL,
--     refresh_token TEXT NOT NULL,
--     access_token_expires_at TIMESTAMP,
--     refresh_token_expires_at TIMESTAMP,
--     created_at TIMESTAMP DEFAULT NOW(),
--     updated_at TIMESTAMP DEFAULT NOW(),
--     UNIQUE(provider, provider_user_id)
-- );

-- CREATE TABLE login_logs (
--     id SERIAL PRIMARY KEY,
--     user_id INT REFERENCES users(id) ON DELETE CASCADE,
--     uuid VARCHAR(255) NOT NULL, -- 기기 식별자
--     login_time TIMESTAMP DEFAULT NOW(),
--     ip_address VARCHAR(45), -- IPv4 및 IPv6 지원
--     UNIQUE(user_id, uuid, login_time)
-- );

CREATE TABLE users(
    token VARCHAR(255), 
    username VARCHAR(255) NOT NULL, 
    password VARCHAR(255) NOT NULL, 
    id VARCHAR(255) NOT NULL
);