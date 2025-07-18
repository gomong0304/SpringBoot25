package org.mbc.board.service;

import org.mbc.board.dto.BoardDTO;
import org.mbc.board.dto.PageRequestDTO;
import org.mbc.board.dto.PageResponseDTO;

public interface BoardService {
    // 조장용 코드 -> 시그니처만 필요 -> Impl 구현 클래스 -> 실행문 만든다.

    /*=========================================================================*/

    Long register(BoardDTO boardDTO); // 프론트에서 폼에 있는 내용이 DTO 로 들어온다.
    // return 은 bno 가 된다.

    /*=========================================================================*/

    BoardDTO readOne(Long bno); // 프론트에서 bno 가 넘어오면 객체가 리턴된다.

    /*=========================================================================*/

    void modify(BoardDTO boardDTO); // 프론트에서 dto 가 넘어오면 수정 작업이 진행된다.

    /*=========================================================================*/

    void remove(Long bno); // 프론트에서 bno 가 넘어오면 삭제 작업이 진행된다.

    /*=========================================================================*/

    PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO);
    // 페이징 처리에 대한 요청을 리스트로 처리하고 결과를 응답으로 보내는 메서드


}
