-- ==========================================
-- Schema for OAuth2 Registered Clients
-- ==========================================
CREATE TABLE IF NOT EXISTS oauth2_registered_client (
    id VARCHAR(100) PRIMARY KEY,
    client_id VARCHAR(100) NOT NULL,
    client_id_issued_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    client_secret VARCHAR(200),
    client_secret_expires_at TIMESTAMP,
    client_name VARCHAR(200) NOT NULL,
    client_authentication_methods VARCHAR(1000) NOT NULL,
    authorization_grant_types VARCHAR(1000) NOT NULL,
    redirect_uris VARCHAR(1000),
    scopes VARCHAR(1000) NOT NULL,
    client_settings VARCHAR(2000) NOT NULL,
    token_settings VARCHAR(2000) NOT NULL,
    post_logout_redirect_uris VARCHAR(1000)
    );

CREATE UNIQUE INDEX IF NOT EXISTS oauth2_registered_client_client_id_idx
    ON oauth2_registered_client (client_id);

-- ==========================================
-- Seed initial clients
-- ==========================================

-- Management client (read + write)
INSERT INTO oauth2_registered_client (
    id,
    client_id,
    client_secret,
    client_name,
    client_authentication_methods,
    authorization_grant_types,
    scopes,
    client_settings,
    token_settings
)
VALUES (
           '1',
           'management-client',
           '{noop}secret', -- 'secret'
           'Management Client (read/write)',
           'client_secret_basic',
           'client_credentials',
           'users.read,users.write',
           '{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":false}',
           '{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":true,"settings.token.id-token-signature-algorithm":"RS256","settings.token.access-token-time-to-live":"PT1H","settings.token.refresh-token-time-to-live":"PT24H"}'
       );

-- Reporting client (read only)
INSERT INTO oauth2_registered_client (
    id,
    client_id,
    client_secret,
    client_name,
    client_authentication_methods,
    authorization_grant_types,
    scopes,
    client_settings,
    token_settings
)
VALUES (
           '2',
           'reporting-client',
           '{noop}secret', -- 'secret'
           'Reporting Client (read only)',
           'client_secret_basic',
           'client_credentials',
           'users.read',
           '{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":false}',
           '{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":true,"settings.token.id-token-signature-algorithm":"RS256","settings.token.access-token-time-to-live":"PT1H","settings.token.refresh-token-time-to-live":"PT24H"}'
       );
