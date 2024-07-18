package com.tr.d_teknoloji.notebook.model.jpa.note;

import com.tr.d_teknoloji.notebook.model.jpa.audit.UserDateStatusAudit;
import com.tr.d_teknoloji.notebook.model.jpa.category.Category;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "note",
        schema = "note",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uc_note_title",
                        columnNames = {"title"})
        })
@SQLDelete(
        sql = "update note set DATA_STATUS = 0 where id = ? and version = ?")
@Where(clause = "DATA_STATUS <> 0")
public class Note extends UserDateStatusAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "category_id",
            referencedColumnName = "id",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_note_category"))
    @ToString.Exclude
    private Category category;

    @Column(name = "category_id")
    private Long categoryId;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Note note = (Note) o;
        return Objects.equals(id, note.id) && Objects.equals(title, note.title) && Objects.equals(content, note.content) && Objects.equals(category, note.category) && Objects.equals(categoryId, note.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, title, content, category, categoryId);
    }
}
