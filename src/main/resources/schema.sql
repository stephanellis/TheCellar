    -- ----------------------------
    -- Table structure for users
    -- ----------------------------
CREATE TABLE IF NOT EXISTS users (
    user_id INTEGER AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    phone_number VARCHAR(10) NOT NULL,
    enabled TINYINT NOT NULL DEFAULT 1,
    login_count INTEGER NOT NULL DEFAULT 0,
    password_reset TINYINT,
    PRIMARY KEY (user_id),
    UNIQUE (username)
);

    -- ----------------------------
    -- Table structure for user_roles
    -- ----------------------------
CREATE TABLE IF NOT EXISTS user_roles (
    user_role_id INTEGER NOT NULL AUTO_INCREMENT,
    user_id INTEGER NOT NULL,
    role VARCHAR(50) NOT NULL DEFAULT 'ROLE_USER',
    PRIMARY KEY (user_role_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    UNIQUE (role, user_id)
);

    -- ----------------------------
    -- Table structure for email_verification_tokens
    -- ----------------------------
CREATE TABLE IF NOT EXISTS email_verification_tokens (
    token_id INTEGER NOT NULL AUTO_INCREMENT,
    user_id INTEGER NOT NULL,
    token VARCHAR(100) NOT NULL,
    date_generated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (token_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    UNIQUE (user_id)
);

    -- ----------------------------
    -- Table structure for beers
    -- ----------------------------
CREATE TABLE IF NOT EXISTS beers (
    beer_id INTEGER NOT NULL AUTO_INCREMENT,
    beer_api_ref VARCHAR(25),
    abv DECIMAL(10,4),
    beer_name VARCHAR(75) NOT NULL,
    brewer VARCHAR(75) NOT NULL,
    style VARCHAR(75),
    description LONGTEXT,
    glassware VARCHAR(25),
    beer_year VARCHAR(4) NOT NULL,
    PRIMARY KEY (beer_id)
);

    -- ----------------------------
    -- Table structure for user_beer_links
    -- ----------------------------
CREATE TABLE IF NOT EXISTS user_beer_links (
    user_beer_link_id INTEGER NOT NULL AUTO_INCREMENT,
    user_id INTEGER NOT NULL,
    beer_id INTEGER NOT NULL,
    count INTEGER NOT NULL,
    PRIMARY KEY (user_beer_link_id),
    UNIQUE (user_id,beer_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (beer_id) REFERENCES beers(beer_id)
);