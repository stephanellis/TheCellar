package com.ronbreier.batch.mappers;

import com.ronbreier.entities.EmailVerification;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by ron.breier on 4/27/2017.
 */
public class EmailVerificationRowMapper implements RowMapper<EmailVerification> {

    private Class<EmailVerification> mappedClass;

    public EmailVerificationRowMapper(Class<EmailVerification> mappedClass){
        this.mappedClass = mappedClass;
    }

    @Override
    public EmailVerification mapRow(ResultSet rs, int rowNum) throws SQLException {
        EmailVerification emailVerification = new EmailVerification();
        emailVerification.setTokenId(rs.getLong("token_id"));
        emailVerification.setUserId(rs.getLong("user_id"));
        emailVerification.setToken(rs.getString("token"));
        emailVerification.setDateGenerated(rs.getTimestamp("date_generated"));
        return emailVerification;
    }
}
