package ywoosang.springadmin.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import ywoosang.springadmin.domain.Member;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcTemplateMemberRepository implements MemberRepository {

    // injection 을 받을 수 없다.
    // 따라서 dataSource 를 inject 받고 이를 이용한다.
    private final JdbcTemplate jdbcTemplate;

    // 생성자가 하나만 있을 경우 Autowired 를 생략할 수 있다. 자동으로 붙는다. 생성자가 두 개 이상이면 자동으로 붙지 않는다.
    // @Autowired
    public JdbcTemplateMemberRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Member save(Member member) {
        // SimpleJdbcInsert 로 sql 문 없이 insert 를 할 수 있다. 내부적으로 sql 문을 만들어준다.
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", member.getName());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        member.setId(key.longValue());
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        // 템플릿 메소드 패턴을 이용해 jdbc 코드를 줄인 것이 jdbcTemplate 이다.
        // 쿼리를 실행하고 결과를 rowMapper 로 받는다.
        // 세번째 파라미터에 ? 에 들어갈 값을 써준다.
        List<Member> result = jdbcTemplate.query("select * from member where id = ?", memberRowMapper(), id);

        // 결과를 Optional 로 바꿔 반환
        return result.stream().findAny();
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = jdbcTemplate.query("select * from member where name = ?", memberRowMapper(), name);
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return List.of();
    }

    private RowMapper<Member> memberRowMapper() {
//        return new RowMapper<Member>() {
//            @Override
//            public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
//                Member member = new Member();
//                member.setId(rs.getLong("id"));
//                member.setName(rs.getString("name"));
//                return member;
//
//            }
//        }
        // 자바 8 lambda 스타일로 바꿀 수 있다.
        return (rs, rowNum) -> {
            Member member = new Member();
            member.setId(rs.getLong("id"));
            member.setName(rs.getString("name"));
            return member;
        };
    }
}
