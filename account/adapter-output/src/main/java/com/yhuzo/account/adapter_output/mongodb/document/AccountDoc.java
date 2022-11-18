package com.yhuzo.account.adapter_output.mongodb.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@Builder
@AllArgsConstructor
@Document("hex-accounts")
public class AccountDoc {

    @Id
    private Long id;

    @Version
    private Long version;

    private AmountDoc amount;

    @Getter
    @Setter
    private static class AmountDoc {
        private String currency;
        private Double amount;
    }
}
