package cms_back.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@Entity
@Table(name = "facilities")
@SQLDelete(sql = "UPDATE facilities SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class Facility {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String name;

    /** 예: "GPU", "Server", "Workstation" 등 — 자유 텍스트 카테고리. */
    @Column(length = 100)
    private String category;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String specification;

    @Column(length = 500)
    private String image;

    @Column(nullable = false)
    private int quantity = 1;

    @Column(nullable = false)
    private int sortOrder = 0;

    @Column(nullable = false, columnDefinition = "TINYINT(1) NOT NULL DEFAULT 1")
    private boolean visible = true;

    private LocalDateTime deletedAt;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(length = 120) private String createdBy;
    @Column(length = 120) private String updatedBy;

    protected Facility() {}

    public static Facility create(String name, String category, String description, String specification,
                                  String image, int quantity, int sortOrder, boolean visible, String actor) {
        Facility f = new Facility();
        f.name = name;
        f.category = category;
        f.description = description;
        f.specification = specification;
        f.image = image;
        f.quantity = quantity;
        f.sortOrder = sortOrder;
        f.visible = visible;
        f.createdAt = LocalDateTime.now();
        f.updatedAt = f.createdAt;
        f.createdBy = actor;
        f.updatedBy = actor;
        return f;
    }

    public void update(String name, String category, String description, String specification,
                       String image, int quantity, int sortOrder, boolean visible, String actor) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.specification = specification;
        this.image = image;
        this.quantity = quantity;
        this.sortOrder = sortOrder;
        this.visible = visible;
        this.updatedAt = LocalDateTime.now();
        this.updatedBy = actor;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public String getDescription() { return description; }
    public String getSpecification() { return specification; }
    public String getImage() { return image; }
    public int getQuantity() { return quantity; }
    public int getSortOrder() { return sortOrder; }
    public boolean isVisible() { return visible; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public String getCreatedBy() { return createdBy; }
    public String getUpdatedBy() { return updatedBy; }
}
