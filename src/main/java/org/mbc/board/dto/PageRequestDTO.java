package org.mbc.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Data
@Builder // 세터를 사용하지 않고 빌더 패턴을 사용하려면 아래 2개의 @ 필수! (@AllArgsConstructor, @NoArgsConstructor)
@AllArgsConstructor
@NoArgsConstructor
@Log4j2
public class PageRequestDTO {
    // 페이징 처리에 요청용 (프론트에서 요청이 오면 동작해야한다.)
    // 페이징에 관련된 정보(page, size), 검색 종류(types -> 제목t 내용c 작성자w), 키워드(검색단어) 처리용
    // -> BoardRepositoryTests 에서 진행

    @Builder.Default // 빌더패턴시작시 초기값이 들어감
    private int page = 1; // 첫페이지

    @Builder.Default
    private int size = 5; // 게시물 수

    private  String type; // t, c, w, tc, tw, twc...(다중 검색용)

    private  String keyword; // 폼박스에 검색 내용

    private  String link; // 프론트에 페이징 번호 클릭시 처리되는 문자열
    // ex) list?page=3&type=w&keyword=kkw

    /*===============================================================================================*/

    public String getLink(){
        if(link == null){ // String page + size + type + .. 옛날에 쓰던 방식
            StringBuilder builder = new StringBuilder(); // String + 연산자로 사용하면 객체가 많이 생김
            // 이를 해결하기 위한 기법

            builder.append("page=" + this.page); // page=1
            builder.append("&size=" + this.size); // page=1&size=10

            if (type != null && type.length() >0){
                // 타입이 있을때
                builder.append("$type=" + type); // page=1&size=10&type=??
            }// 타입이 있을때 if문 종료

            if (keyword != null){
                try {
                    builder.append("&keyword=" + URLEncoder.encode(keyword,"UTF-8"));
                }catch (UnsupportedEncodingException e){
                    log.info(e.getStackTrace());
                    log.info("UTF-8 처리중 오류 발생");
                }// try문 종료
            }// 키워드 if문 종료
            link = builder.toString(); // 최종 결과물이 문자열로 변환되어 link에 저장
        }// if문 종료

        return link; // page=1&size=10&type=??&keyword=?????

    }// 메서드 종료

    /*===============================================================================================*/

    // 추가 메서드
    public  String[] getTypes(){ // 프론트에서 문자열이 여러개가 넘어오면 배열로 변환한다.
        if(type == null || type.isEmpty()){
            return null;
        }
        return type.split(" "); // split 글자 쪼개기 -> 띄어쓰기 넣어주자. 차후에 프론트 form 박스 확인.
        // 문자열로 넘어온 값을 분할하여 배열에 꼽는다.
    }// getTypes 메서드 종료

    /*===============================================================================================*/

    // 테스트용 코드(BoardRepositoryTests 에서 했던거)를 dto 로 만들어 메서드 처리함
    public Pageable getPageable(String...props){ // String...props -> 배열이 몇개가 들어올지 모를때 사용
        return PageRequest.of(this.page-1, this.size, Sort.by(props).descending());
        //                    ↑ 페이지 번호             ↑ 게시물 수  ↑ 다중 정렬
        // Sort.by(props) -> 이름으로는 내림차순 정렬인데 제목으로도 정렬을 넣을수있다. 다중정렬 할수있다.
    }// getPageable(String...props) 메서드 종료


}// PageRequestDTO 클래스 종료
