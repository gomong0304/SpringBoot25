package org.mbc.board.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.mbc.board.dto.BoardDTO;
import org.mbc.board.dto.PageRequestDTO;
import org.mbc.board.dto.PageResponseDTO;
import org.mbc.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class BoardServiceTests {

    @Autowired
    private BoardService boardService;

    @Test
    public void testRegister(){

        log.info("등록용 테스트 서비스 실행중............");
        log.info(boardService.getClass().getName()); // 객체 생성용 테스트
        // org.mbc.board.service.BoardServiceImpl$$SpringCGLIB$$0

        BoardDTO boardDTO = BoardDTO.builder()
                .title("서비스에서 만든 제목")
                .content("서비스에서 만든 내용")
                .writer("서비스님")
                .build(); // 세터 대신 @Builder

        Long bno = boardService.register(boardDTO); // 서비스 구현 메서드로 동작함

        log.info("test 결과 bno : " + bno); //  테스트 결과 :

        /*Hibernate:
        insert into board (content, moddate, regdate, title, writer) values (?, ?, ?, ?, ?)*/
    }

    /*=====================================================================================*/

    @Test
    public void testModify(){

        BoardDTO boardDTO = BoardDTO.builder()
                .bno(101L)
                .title("서비스에서 수정된 제목")
                .content("서비스에서 수정된 내용")
                .build();
        boardService.modify(boardDTO); // 프론트에서 객체가 넘어가 수정이 되었는지 테스트

        /*Hibernate:
        select
            b1_0.bno,
            b1_0.content,
            b1_0.moddate,
            b1_0.regdate,
            b1_0.title,
            b1_0.writer
        from board b1_0 where b1_0.bno=?

        Hibernate:
        update board set content=?, moddate=?, title=?, writer=? where bno=?*/
    }

    /*=====================================================================================*/

    @Test
    public void testList(){
        // 프론트에서 넘어오는 데이터를 이용해서 페이징과 검색과 정렬 처리용으로 최종 테스트
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .type("tcw") // 제목 내용 작성자
                .keyword("1") // 1을 찾는다
                .page(1) // 현재 페이지는 1페이지
                .size(10) // 게시물 10개씩 보여달라
                .build();

        PageResponseDTO<BoardDTO> responseDTO = boardService.list(pageRequestDTO);

        log.info(responseDTO);

        /*Hibernate:
        select
            b1_0.bno,
            b1_0.content,
            b1_0.moddate,
            b1_0.regdate,
            b1_0.title,
            b1_0.writer
        from board b1_0 where b1_0.bno>? order by b1_0.bno desc limit ?, ?

        Hibernate: select count(b1_0.bno) from board b1_0 where b1_0.bno>?*/
    }
}
