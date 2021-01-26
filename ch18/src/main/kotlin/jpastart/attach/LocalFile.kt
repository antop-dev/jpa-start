package jpastart.attach

import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "local_file")
class LocalFile(id: String, name: String, val path: String) : AttachFile(id, name)
