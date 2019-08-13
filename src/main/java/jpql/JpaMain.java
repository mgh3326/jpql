package jpql;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class JpaMain {
  public static void main(String[] args) {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");
    EntityManager em = entityManagerFactory.createEntityManager();
    EntityTransaction transaction = em.getTransaction();
    transaction.begin();
    try {
      Team team = new Team();
      team.setName("teamA");
      em.persist(team);
      Member member = new Member();
      member.setUsername("teamA");
      member.setAge(10);
      member.setType(MemberType.ADMIN);
      member.setTeam(team);
      em.persist(member);
      em.flush();
      em.clear();

      String query = "select m.username, 'HELLO', true From Member m " +
              "where m.type=:userType";

      List<Object[]> result = em.createQuery(query)
              .setParameter("userType", MemberType.ADMIN)
              .getResultList();
      for (Object[] objects : result) {
        System.out.println("objects[0] = " + objects[0]);
        System.out.println("objects[1] = " + objects[1]);
        System.out.println("objects[2] = " + objects[2]);
      }

      transaction.commit();
    } catch (Exception e) {
      transaction.rollback();
    } finally {
      em.close();
    }
    entityManagerFactory.close();
  }


}
