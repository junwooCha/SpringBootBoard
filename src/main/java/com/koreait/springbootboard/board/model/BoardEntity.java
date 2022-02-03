package com.koreait.springbootboard.board.model;

import lombok.Data;

@Data
public class BoardEntity {
    private int iboard;
    private int icategory;
    private String title;
    private String ctnt;
    private int iuser;
    private int hits;
    private int isdel;
    private String rdt;
    private String mdt;
    private String lastip;
}
