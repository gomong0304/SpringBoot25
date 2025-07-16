package org.mbc.board.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity // 데이터베이스 테이블 관련 객체
@Getter
@Builder // builder 패턴 -> 세터 대신 활용
@AllArgsConstructor // 모든 필드값으로 생성자 만듬
@NoArgsConstructor // 기본 생성자
@ToString // 객체 주소가 아닌 값을 출력
public class Board extends BaseEntity{

    @Id // pk 선언함 (공백 허용 x notnull, unique, indexing(빠른검색))
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동번호 생성용
    private Long bno;           // 게시물의 번호

    @Column(length = 500, nullable = false) // nn
    private String title;       // 게시물의 제목

    @Column(length = 2000, nullable = false) // nn
    private String content;     // 게시물의 내용

    @Column(length = 50, nullable = false) // nn
    private String writer;      // 게시물의 작성자

   /* Hibernate:
    create table board (
        bno bigint not null auto_increment,
        content varchar(255),
        title varchar(255),
        writer varchar(255),
        primary key (bno)
    ) engine=InnoDB*/

    /*Hibernate: alter table if exists board add column moddate datetime(6)
    Hibernate: alter table if exists board add column regdate datetime(6)
    Hibernate: alter table if exists board add column moddate datetime(6)
    Hibernate: alter table if exists board add column reg_date datetime(6)
    Hibernate: alter table if exists board modify column content varchar(2000) not null
    Hibernate: alter table if exists board modify column title varchar(500) not null
    Hibernate: alter table if exists board modify column writer varchar(50) not null
    */

    public void change(String title, String content){
        // 제목과 내용만 수정하는 메서드 (세터 대체용)
        this.title = title;
        this.content = content;
    }
}
