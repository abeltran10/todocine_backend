-- 1. Eliminar la foreign key actual
ALTER TABLE voto_comentario
DROP CONSTRAINT fk_voto_comentario_comentario;

-- 2. Crear la foreign key con borrado en cascada
ALTER TABLE voto_comentario
    ADD CONSTRAINT fk_voto_comentario_comentario
        FOREIGN KEY (comentario) REFERENCES comentario (id)
            ON DELETE CASCADE;