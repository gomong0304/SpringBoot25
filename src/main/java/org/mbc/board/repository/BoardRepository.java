package org.mbc.board.repository;

import org.mbc.board.domain.Board;
import org.mbc.board.repository.search.BoardSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardSearch { // 다중검색용 BoardSearch 추가 p451
    // extends JpaRepository<엔티티클래스, PK 타입>
    // JpaRepository 는  JPA 에서 미리 만들어 놓은 인터페이스로 CRUD 와 페이징처리, 정렬 등이 존재한다.

    // 테스트코드에서 JpaRepository에 내장된 CRUD 와 페이징 정렬 기법을 테스트함.
    // package org.mbc.board.repository; 의 BoardRepositoryTests 에서
    // Board result=boardRepository.save(board); -> DB 에 INSERT 쿼리가 동작
    // Optional<Board> result = boardRepository.findById(bno); -> bno 값을 select 쿼리가 동작
    // boardRepository.save(board); -> bno(pk) 가 있으면 update 쿼리가 동작
    // boardRepository.deleteById(bno); -> bno 가 있으면 delete 쿼리가 동작

    // 쿼리 메서드 우리가 만드는 메서드에 이름이 쿼리문이 된다.
    // https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html
    // 단점 : 실제로 사용하려면 상당히 길고 복잡한 메서드 명이 됨으로 많이 사용되지는 않는다.

    Page<Board> findByTitleContainingOrderByBnoDesc(String keyword, Pageable pageable);
    // 인터페이스에 구현메서드 임으로 실행문이 없다 -> ; 로 끝남
    // findBy 찾아오겠다 -> select
    // Title 제목필드 -> Containing
    // OrderBy 정렬진행 -> BnoDesc 번호를 내림차순

    // @Query 쿼리메서드와 병합 -> JPQL 이라고 부름
    // 제목으로 찾겠다!      Board b ->  Board 를 b라는 이름으로 쓰기 위해 약어로 쓰겠다
    @Query("SELECT b from Board b where b.title like concat('%',:keyword, '%')") // 찾아온다..2
    Page<Board> findKeyword(String keyword, Pageable pageable); // 메서드를 받아서, 검색조건을 받아서..1
    // findKeyword 메서드가 실행하면 파라미터로 keyword 를 받는다.(제목 검색 단어 where)
    // 쿼리문에 객체가 넘어가야 됨으로 Board 가 클래스 명이 되어야한다.
    // select * from board where title like'%keyword%'
    // 단점 : join 과 같은 복잡한 쿼리를 사용하지 못함
    //      원하는 속성들만 추철해서 객체배열 처리하거나 DTO 로 처리 안됨
    //      네이티브 쿼리 속성값을 TRUE 로 지정해서 특정 DB 에서 동작하는 SQL 사용이 안됨

    // 네이티브 쿼리 : 진짜 쿼리문을 사용하는 기법
    @Query(value = "select now()", nativeQuery = true) // nativeQuery = true 진짜 쿼리문으로 동작한다
    String getTime();
}
