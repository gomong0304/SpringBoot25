package org.mbc.board.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.mbc.board.domain.Board;
import org.mbc.board.domain.QBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch {
    //                       ↑ 쿼리 DSL 용 상속               ↑  ↑ 구현체 인터페스 지정  ↑

    public BoardSearchImpl() {
        super(Board.class);
    }


    /*=======================================================================================*/

    @Override
    public Page<Board> search1(Pageable pageable){
        // 쿼리 DSL 로 다중검색용 코드 추가
        // 쿼리 DSL 의 목적은 타입 기반으로 코드를 이용함 -> Q 도메인 클래스

        QBoard board = QBoard.board; // Q 도메인 객체

        JPQLQuery<Board> query = from(board); // select * from board

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        // 다중조건일때 연산자 공식에 의해서 특수기호가 먼저 계산될때가 있다.
        // ()를 사용하면 선행되기 때문에 BooleanBuilder 가 이 역할을 한다.

        // query.where(board.title.contains("1")); // where title like 1이라는 조건이 만들어짐
        // select * from board where title like 1 이라는 조건이 최종적으로 만들어진다....
        // 결과가 나오면

        booleanBuilder.or(board.title.contains("11")); // where title like 11
        booleanBuilder.or(board.content.contains("11")); // where content like

        query.where(booleanBuilder); // (where title like 11 or content like 11)
        query.where(board.bno.gt(0L)); // pk 이용해서 빠른 검색 where 이 추가되면 and 조건
        // where (title like 11 or content like 11) and bno > 0

        // 페이징 처리용 코드
        this.getQuerydsl().applyPagination(pageable, query);

        List<Board> list = query.fetch(); // 쿼리문 실행해서 리스트에 담아라...

        long count = query.fetchCount(); // 검색후에 게시물수 파악 용..

        return null;
    }

    /*=======================================================================================*/

    @Override
    public Page<Board> searchAll(String[] types, String keyword, Pageable pageable) {
        // 인터페이스에서 만든 추상메서드를 구현하는 클래스
        QBoard board = QBoard.board; // 쿼리 DSL 객체 생성
        JPQLQuery<Board> query = from(board); // select * from board

        // 프론트에서 검색폼에 keyword 를 넣는데 keyword 가 비었을 경우도 있고 존재할 경우도 있다.
        // keyword 가 비어있으면 쓸 필요가 없고 allList 로 다 나와야지

        if ((types != null && types.length >0) && keyword != null){
            // 제목 내용 이름 값이 있고 검색어가 있으면~ 이라는 뜻

            BooleanBuilder booleanBuilder = new BooleanBuilder(); // 선실행용()

            for (String type : types){ // 파라미터로 넘어온 값을 String[] types

                switch (type) { // 프론트에서 넘어오는 String[] 값을 파악하고 적용
                    case "t": // 제목이면
                        booleanBuilder.or(board.title.contains(keyword));
                        break;

                    case "c": // 내용이면
                        booleanBuilder.or(board.content.contains(keyword));
                        break;

                    case "w": // 작성자이면
                        booleanBuilder.or(board.writer.contains(keyword));
                        break;
                }// switch 문 종료
            }// for 문 종료

            query.where(booleanBuilder); // 위에서 만든 조건을 적용 시킴 where title or content or writer ~

        }// if 문 종료

        query.where(board.bno.gt(0L)); // pk를 활용해서 인덱싱 처리용 코드
        // where (title or content or writer) and bno > 0L

        this.getQuerydsl().applyPagination(pageable, query); // 페이징 처리용 코드 + 쿼리문

        // Page<t> 클래스는 3가지의 리턴 타입을 만들어준다.

        List<Board> list = query.fetch(); // 쿼리문 실행

        long count = query.fetchCount(); // 검색된 게시물 수

        return new PageImpl<>(list, pageable, count);
        //         리턴       검색된 결과 board, 페이징 처리용, 검색된 결과
    }// searchAll 클래스 종료
}
