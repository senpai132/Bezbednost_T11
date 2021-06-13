package com.example.bolnicaServer.repository;

import com.example.bolnicaServer.model.template.RuleTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RuleRepository extends JpaRepository<RuleTemplate, Integer> {
    RuleTemplate findByUseFunction(String useFunction);
}
