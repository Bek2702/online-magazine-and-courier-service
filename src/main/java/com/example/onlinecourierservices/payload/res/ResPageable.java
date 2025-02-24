package com.example.onlinecourierservices.payload.res;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResPageable {

    private int size;
    private int page;
    private int totalPage;
    private Long totalElements;
    private Object body;

}
