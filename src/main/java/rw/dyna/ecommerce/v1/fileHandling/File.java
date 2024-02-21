package rw.dyna.ecommerce.v1.fileHandling;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import rw.dyna.ecommerce.v1.audits.InitiatorAudit;
import rw.dyna.ecommerce.v1.enums.EFileSizeType;
import rw.dyna.ecommerce.v1.enums.EFileStatus;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="files", uniqueConstraints = {@UniqueConstraint(columnNames = "path")})
public class File extends InitiatorAudit {
    @Id
    @GeneratedValue(generator = "fileUUID")
    @GenericGenerator(name= "fileUUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    private String name;

    private String path;

    @Transient
    private String url;

    private int size;

    @Column(name = "size_type")
    @Enumerated(EnumType.STRING)
    private EFileSizeType sizeType;

    private String type;


    @Column
    @Enumerated(EnumType.STRING)
    private EFileStatus status;

    public File(String directory, String fileName,String extension, String fileBaseName ){super();}

    public String getUrl(){return "/files/load-file/" +  name;}
}
