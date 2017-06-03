package repository

import entity.Problem
import org.hibernate.SessionFactory
import org.hibernate.boot.MetadataSources
import org.hibernate.boot.registry.StandardServiceRegistryBuilder

object ProblemRepository {
    private val sessionFactory: SessionFactory by lazy(LazyThreadSafetyMode.NONE) {
        val registry = StandardServiceRegistryBuilder().configure().build()
        MetadataSources(registry).buildMetadata().buildSessionFactory()
    }

    fun saveProblems(list: List<Problem>) = sessionFactory.currentSession.use {
        it.beginTransaction()
        list.forEach { p -> it.save(p) }
        it.transaction.commit()
    }

    fun getProblems(): MutableList<Problem> = sessionFactory.currentSession.use {
        it.beginTransaction()
        val list = it.createQuery("from Problem", Problem::class.java).list()
        it.transaction.commit()
        list
    }

    fun getProblemsById(id: Int): Problem = sessionFactory.currentSession.use {
        it.beginTransaction()
        val student = it.get(Problem::class.java, id)
        it.transaction.commit()
        student
    }

    fun close() = sessionFactory.close()
}