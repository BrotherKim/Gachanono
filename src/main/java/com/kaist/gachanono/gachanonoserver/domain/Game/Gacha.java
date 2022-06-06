package com.kaist.gachanono.gachanonoserver.domain.Game;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
public class Gacha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String gachaname;
    
    @Column(nullable = false)
    private String templatefilename;
}
