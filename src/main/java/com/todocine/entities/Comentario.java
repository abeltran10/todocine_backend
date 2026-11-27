package com.todocine.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(
        name = "COMENTARIO",
        uniqueConstraints = {
                // Restricción: Un usuario solo puede tener UN comentario raíz por película de TMDB.
                // (Nota: En SQL puro se suele filtrar por parent_id IS NULL, pero con uniqueConstraint
                // aseguramos la unicidad base y lo validamos en el servicio).
                @UniqueConstraint(columnNames = {"usuario", "movie", "parent"})
        }
)
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USUARIO", referencedColumnName = "USERNAME")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "MOVIE", referencedColumnName = "ID")
    private Movie movie;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String contenido;

    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "PARENT",
            referencedColumnName = "ID",
            foreignKey = @ForeignKey(name = "fk_comentario_parent"))
    private Comentario parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Comentario> replies = new ArrayList<>();

    @OneToMany(mappedBy = "comentario", fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private List<VotoComentario> votes = new ArrayList<>();

    public Comentario() {
    }

    public Comentario(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Comentario getParent() {
        return parent;
    }

    public void setParent(Comentario parent) {
        this.parent = parent;
    }

    public List<Comentario> getReplies() {
        return replies;
    }

    public void setReplies(List<Comentario> replies) {
        this.replies = replies;
    }

    public List<VotoComentario> getVotes() {
        return votes;
    }

    public void setVotes(List<VotoComentario> votes) {
        this.votes = votes;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Comentario that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
