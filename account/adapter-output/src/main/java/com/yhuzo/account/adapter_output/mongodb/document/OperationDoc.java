package com.yhuzo.account.adapter_output.mongodb.document;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document("hex-operations")
public class OperationDoc {

    @Id
    private Long id;
    private Instant createdAt;
    private Long ownerAccountId;
    private Long sourceAccountId;
    private Long targetAccountId;
    private Double amount;

}