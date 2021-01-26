package jpastart.attach

import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "cloud_file")
class CloudFile(id: String, name: String, val provider: String, val url: String) : AttachFile(id, name)
