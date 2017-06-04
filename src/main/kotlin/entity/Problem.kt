package entity

import java.util.*
import javax.persistence.*

@Entity
data class Problem(
        @Id
        @Column(name = "id")
        var tpid: Int? = null,
        @Column(unique = true)
        var gpid: String? = null,
        var tid: String? = null,
        @Column(columnDefinition = "date")
        var updatetime: Date? = null,
        var title: String? = null,
        var lanqiaotitle: String? = null,
        var checkpoint: String? = null,
        @OneToOne(mappedBy = "problem", cascade = arrayOf(CascadeType.ALL), orphanRemoval = true)
        var problemDetail: ProblemDetail? = null
) {
    fun addDetails(details: ProblemDetail) {
        this.problemDetail = details
        details.problem = this
    }

    fun removeDetails() {
        if (problemDetail != null) {
            this.problemDetail = null
            problemDetail?.problem = null
        }
    }

    override fun toString(): String {
        return "Problem(tpid=$tpid, gpid=$gpid, tid=$tid, updatetime=$updatetime, title=$title, lanqiaotitle=$lanqiaotitle, checkpoint=$checkpoint)"
    }
}
