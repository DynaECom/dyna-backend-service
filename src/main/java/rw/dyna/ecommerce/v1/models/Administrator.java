package rw.dyna.ecommerce.v1.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rw.dyna.ecommerce.v1.fileHandling.File;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "administrator")
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name ="user_id", referencedColumnName = "id")
public class Administrator extends User{
    private File identityDocument;
}
