package com.softwaremanage.meditestlab.pojo.train_module;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ability")
public class Ability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ability_id")
    private Integer abilityId;

    @Column(name = "ability_name", nullable = false)
    private String abilityName;

    @Column(name = "inspector_id", nullable = false)
    private Integer inspectorId;

    @Column(name = "project_id", nullable = false)
    private Integer projectId;

    @Column(name = "s_id", nullable = false)
    private Integer sId;
}
