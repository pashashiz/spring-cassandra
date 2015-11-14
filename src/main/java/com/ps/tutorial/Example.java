package com.ps.tutorial;

import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.cassandra.core.CassandraTemplate;

public class Example {

    private static Logger log = Logger.getLogger(Example.class);

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Config.class);
        CassandraTemplate template = (CassandraTemplate) ctx.getBean("cassandraTemplate");
        template.insert(new User(1, "Palvo", "Pohrebnyi"));
        Select select = QueryBuilder.select().from("user");
        select.where(QueryBuilder.eq("id", 1));
        User user = template.selectOne(select, User.class);
        log.debug(user);
        assert user.getId() == 1;
        ctx.close();
    }
}