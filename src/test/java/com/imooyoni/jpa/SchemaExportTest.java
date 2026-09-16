package com.imooyoni.jpa;

import com.fixelsoft.util.web.generator.JpaSchemaGenerator;
import com.fixelsoft.util.web.generator.impl.JpaSchemaSqlGenerator;
import com.imooyoni.board.data.domain.article.Article;
import com.imooyoni.board.data.domain.board.BoardEntity;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.persistence.autoconfigure.EntityScan;

/**
 * JPA Entity로부터 DDL 스키마를 SQL 파일로 내보내는 테스트
 * <p>
 * Hibernate 7에서는 SchemaExport 클래스가 제거되어
 * JPA 표준 API인 Persistence.generateSchema()를 사용합니다.
 * <p>
 * 생성된 SQL을 DB 클라이언트에서 실행하거나 jOOQ 코드 생성에 활용할 수 있습니다.
 */
@DataJpaTest(showSql = false, properties = {
        "spring.flyway.enabled=false"
        , "spring.jpa.hibernate.ddl-auto=none"})
public class SchemaExportTest {

    @Autowired
    @Qualifier("entityManagerFactory")
    protected EntityManagerFactory entityManagerFactory;

    @Test
    public void firstTest() {
        JpaSchemaGenerator utility = new JpaSchemaSqlGenerator(entityManagerFactory);
        utility.generate(
                "src/test/resources/create_schema.sql"
                , BoardEntity.class
                , Article.class
        );
    }

    @SpringBootConfiguration
    @EnableAutoConfiguration
    @EntityScan(basePackages = "com.imooyoni.board.data")
    static class Config {
    }
}