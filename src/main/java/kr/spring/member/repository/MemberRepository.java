package kr.spring.member.repository;

import kr.spring.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

//저장도 하고 데이터를 가져올 수도 있지만 디테일하게 메소드를 만들어서 사용할 수도 있음!
//jpql을 이용하거나 querydsl을 이용하거나
public interface MemberRepository extends JpaRepository<Member, Long> {

    // 문자로 된 이메일을 넣어서 검색하겠다.
    // 이메일이 있거나 없거나. 이걸 member로 해도 되지만 자바에서는 최근에 Optional을 사용한다.
    // 결과가 있을 수도 있고, 없을 수도 있는 상태
    // Optional<Member> findByEmail(String email);
    Member findByEmail(String email);  // 이메일 받아오기

}
