package org.mbc.board.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class PageResponseDTO<E> { // <E> E Entity 용 변수명 (변할 수 있는 값)
    // 페이징 처리 응답용 객체
    // dto 의 목록, 시작페이지/끝페이지 여부 등...

    // 필드
    private int page, size, total; // 현재 페이지, 페이지당 게시물 수, 총 게시물 수

    private int start; // 시작페이지 번호

    private int end; // 끝페이지 번호

    private  boolean prev; // 이전페이지 존재 여부
    private  boolean next; // 다음페이지 존재 여부

    private List<E> dtoList; // 게시물의 목록

    // 생성자
    @Builder(builderMethodName = "withAll") // PageResponseDTO.<BoardDTO>withAll() 메서드로 호출이 되면 아래가 돌게끔
    public PageResponseDTO(PageRequestDTO pageRequestDTO, List<E> dtoList, int total){
        // 전에 만든 return link; page=1&size=10&type=??&keyword=????? 처리용
        // List<Board> dtoList / List<Member> dtoList / List<Item> dtoList
        // int total -> 총 게시물 수

        if (total <=0){ // 게시물이 없으면
            return;
        }

        this.page = pageRequestDTO.getPage(); // 요청에 대한 페이지 번호
        this.size = pageRequestDTO.getSize(); // 요청에 대한 사이즈(게시물 수)
        this.total = total; // 위에 int total 로 넘어옴, 파라미터로 넘어온 값
        this.dtoList = dtoList; // 위에 List<E> dtoList 로 넘어옴, 파라미터로 넘어온 값

        this.end = (int)(Math.ceil(this.page / 10.0)) * 10; // 공식임, 화면에서의 마지막 번호
        // 만약 this.page 가 21이면 21/10.0 -> 2.1 을 ceil 처리하면 2가 나옴 2*10 -> 20 : 화면에서 마지막 번호가 20이 된다.

        this.start = this.end - 9;
        // this.end 가 20이였으면 20-9 -> this.start 가 11이 된다.

        int last = (int)(Math.ceil((total/(double)size))); // 데이터 갯수를 계산한 마지막 페이지 번호
        // 만약 88개의 게시물이면 9개의 페이지 번호가 나와야함

        this.end = end > last ? last : end; // 3항 연산자 ->  최종 활용되는 페이지 번호
        //             조건       참   거짓

        this.prev = this.start >1; // 이전 페이지 유무
        this.next = total > this.end * this.size; // 다음 페이지 유무
    }
}
