package ywoosang.springadmin.repository;

import org.springframework.jdbc.datasource.DataSourceUtils;
import ywoosang.springadmin.domain.Member;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;


public class JdbcMemberRepository implements  MemberRepository{

    // 데이터베이스를 가져오려면 datasource 를 주입받아야 한다.
    private final DataSource dataSource;

    public JdbcMemberRepository(DataSource dataSource) {
        // dataSource 에서 직접 getConnection 을 할 수도 있다. 이러면 계속 새로운 Connection 이 주어지기 때문에
        // 스프링을 통해 데이터베이스 커넥션을 쓸 때는 DataSourceUtils 를 사용한다.
        this.dataSource = dataSource;
    }

    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }

    private void close(Connection conn) throws SQLException {
        DataSourceUtils.releaseConnection(conn, dataSource);
    }

    @Override
    public Member save(Member member) {
        String sql = "insert into member(name) values(?)";

        Connection conn = null;
        PreparedStatement pstmt = null;
        //
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            // parameterIndex 1번이 sql 문의 ? 와 매칭이되어 getName() 으로 가져온 이름을 넣는다.
            pstmt.setString(1, member.getName());

            // 데이터베이스에 실제 쿼리가 실행된다.
            pstmt.executeUpdate();
            // Statmemt.RETURN_GENERATED_KEYS 와 매칭되어 생성된 ID (key) 를 가져온다.
            rs = pstmt.getGeneratedKeys();

            // 값이 있다면 값을 꺼내고 설정한다.
            if(rs.next()) {
                member.setId(rs.getLong((1)));
            } else {
                throw new SQLException("id 조회 실패");
            }
            return member;

        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            // connection 이 끝나고 나면 바로 자원을 끊어야 한다.
            close(conn,pstmt,rs);
        }
    }


    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        // 역순으로 close 해준다.
        try {
            if(rs != null) {
                rs.close();
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        try {
            if(pstmt != null) {
                pstmt.close();
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        try {
            if(conn != null) {
                close(conn);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Member> findById(Long id) {
        String sql = "select * from member where id = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1,id);

            // 조회는 Query 써야 한다.
            rs = pstmt.executeQuery();

            if(rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member);
            } else {
                return Optional.empty();
            }
        } catch(Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Optional<Member> findByName(String name) {
        String sql = "select * from member where name = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);

            // 조회는 Query 써야 한다.
            rs = pstmt.executeQuery();

            if(rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member);
            } else {
                return Optional.empty();
            }
        } catch(Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public List<Member> findAll() {
        String sql = "select * from member";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            List<Member> members = new ArrayList<>();
            while (rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                members.add(member);
            }
            return members;

        } catch (Exception e) {
            throw new IllegalStateException(e);

        } finally {
            close(conn, pstmt, rs);
        }
    }
}
