package entity

import javax.persistence.*

@Entity
data class ProblemDetail(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int? = null,
        @Column(columnDefinition = "text")
        var description: String? = null,
        @Column(columnDefinition = "text")
        var refCode_c: String? = null,
        @Column(columnDefinition = "text")
        var refCode_cpp: String? = null,
        @Column(columnDefinition = "text")
        var refCode_java: String? = null,
        @OneToOne
        @JoinColumn(unique = true)
        var problem: Problem? = null
) {
    override fun toString(): String {
        return "ProblemDetail(id=$id, description=$description, refCode_c=$refCode_c, refCode_cpp=$refCode_cpp, refCode_java=$refCode_java)"
    }
}
