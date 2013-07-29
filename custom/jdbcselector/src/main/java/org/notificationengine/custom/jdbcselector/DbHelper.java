package org.notificationengine.custom.jdbcselector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import javax.sql.DataSource;

import org.notificationengine.domain.Recipient;
import org.notificationengine.domain.Subscription;
import org.notificationengine.domain.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

@Component(value=JdbcSelectorConstants.DB_HELPER)
public class DbHelper {

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Value("${jdbc.sql.order}")
	private String sqlOrder;
	
	@Value("${jdbc.sql.recipient.alias}")
	private String recipientAlias;
	
	@Value("${jdbc.sql.topic.param}")
	private String topicParam;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

	public Collection<Subscription> retrieveSubscriptionsForTopic(
			final String topicName) {
		
		SqlParameterSource namedParameters = new MapSqlParameterSource(this.topicParam, topicName);

	    return this.namedParameterJdbcTemplate.query(this.sqlOrder, namedParameters, new RowMapper<Subscription>() {

			@Override
			public Subscription mapRow(ResultSet rs, int cptLine)
					throws SQLException {
				
				Topic topic = new Topic(topicName);
				
				Recipient recipient = new Recipient(rs.getString(recipientAlias));
				
				Subscription subscription = new Subscription(topic, recipient);

				return subscription;
			}	    	
		});
	}
	
	
}