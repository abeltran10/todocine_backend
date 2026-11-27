-- 1. Eliminar la foreign key actual
ALTER TABLE comentario
DROP CONSTRAINT fk_comentario_parent;

-- 2. Crear la foreign key con borrado en cascada
ALTER TABLE comentario
    ADD CONSTRAINT fk_comentario_parent
        FOREIGN KEY (parent) REFERENCES comentario (id)
            ON DELETE CASCADE;