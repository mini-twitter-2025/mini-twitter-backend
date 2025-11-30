-- Usuarios
INSERT INTO usuarios (id, username) VALUES (1, 'ada_lovelace');
INSERT INTO usuarios (id, username) VALUES (2, 'alan_turing');
INSERT INTO usuarios (id, username) VALUES (3, 'grace_hopper');
INSERT INTO usuarios (id, username) VALUES (4, 'linus_torvalds');
INSERT INTO usuarios (id, username) VALUES (5, 'margaret_hamilton');

-- Tweets
INSERT INTO tweets (id, autor_id, texto, fecha_creacion, tweet_origen_id)
VALUES (100, 1, '¡Hola mundo! Este es el primer tweet de Mini Twitter 🚀', CURRENT_TIMESTAMP - INTERVAL '2' DAY, NULL);

INSERT INTO tweets (id, autor_id, texto, fecha_creacion, tweet_origen_id)
VALUES (101, 2, '¿Pueden creer que las máquinas piensan? 🤔', CURRENT_TIMESTAMP - INTERVAL '1' DAY, NULL);

INSERT INTO tweets (id, autor_id, texto, fecha_creacion, tweet_origen_id)
VALUES (102, 3, 'Es más fácil pedir perdón que pedir permiso.', CURRENT_TIMESTAMP - INTERVAL '12' HOUR, NULL);

INSERT INTO tweets (id, autor_id, texto, fecha_creacion, tweet_origen_id)
VALUES (104, 5, 'No se trata solo de escribir código, se trata de llevar al hombre a la luna 🌕', CURRENT_TIMESTAMP - INTERVAL '2' HOUR, NULL);

INSERT INTO tweets (id, autor_id, texto, fecha_creacion, tweet_origen_id)
VALUES (105, 1, 'Programar es un arte.', CURRENT_TIMESTAMP - INTERVAL '1' HOUR, NULL);

-- Retweets
INSERT INTO tweets (id, autor_id, texto, fecha_creacion, tweet_origen_id)
VALUES (106, 2, '¡Hola mundo! Este es el primer tweet de Mini Twitter 🚀', CURRENT_TIMESTAMP - INTERVAL '30' MINUTE, 100);

INSERT INTO tweets (id, autor_id, texto, fecha_creacion, tweet_origen_id)
VALUES (107, 3, '¿Pueden creer que las máquinas piensan? 🤔', CURRENT_TIMESTAMP - INTERVAL '10' MINUTE, 101);
