package br.com.nlw.events.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.nlw.events.dto.SubscriptionRankingItem;
import br.com.nlw.events.model.Event;
import br.com.nlw.events.model.Subscription;
import br.com.nlw.events.model.User;

public interface SubscriptionRepo extends CrudRepository<Subscription, Integer> {
	public Subscription findByEventAndSubscriber(Event event, User user);
	
	@Query(value = "SELECT  COUNT(subscribed_user_id) AS subscribers, indication_user_id as userId, user.user_name as name"
			+ " FROM db_events.tbl_subscription as subscription "
			+ " INNER JOIN db_events.tbl_user as user on user.user_id = subscription.indication_user_id "
			+ " where indication_user_id is not null and event_id = :eventId "
			+ " GROUP BY userId "
			+ " ORDER BY subscribers DESC", nativeQuery = true)
	public List<SubscriptionRankingItem> generateRanking(@Param("eventId") Integer eventId);

}
