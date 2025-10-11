package by.tyv.account.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Accessors(chain=true)
@Table("credentials")
public class CredentialsEntity {
    @Id
    private Long id;

    @Column("created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    private String login;
    private String password;
}
