DELETE FROM tweets;
DELETE FROM usuarios;

INSERT INTO usuarios (id, username) VALUES (1, 'alice01');
INSERT INTO usuarios (id, username) VALUES (2, 'bob_user');

INSERT INTO tweets (id, autor_id, texto, fecha_creacion, tweet_origen_id)
VALUES (100, 1, 'Hola Mini Twitter!', CURRENT_TIMESTAMP, NULL);

INSERT INTO tweets (id, autor_id, texto, fecha_creacion, tweet_origen_id)
VALUES (101, 2, 'Otro tweet de prueba', CURRENT_TIMESTAMP, NULL);
